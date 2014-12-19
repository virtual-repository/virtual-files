package org.virtual.files.local;

import static java.nio.file.StandardWatchEventKinds.*;
import static java.util.concurrent.Executors.*;
import static org.virtual.files.common.Utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;

import lombok.Cleanup;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.virtual.files.Provider;
import org.virtual.files.common.Constants;
import org.virtual.files.index.AssetIndex;
import org.virtual.files.index.AssetInfo;
import org.virtualrepository.Asset;

@Slf4j
public class LocalProvider implements Provider {

	@Getter
	private AssetIndex index;

	@Getter
	private LocalConfiguration configuration;
	

	//to watch the index
	final private ExecutorService executor = newSingleThreadExecutor();
	
	
	public LocalProvider(@NonNull LocalConfiguration configuration) {
		
		this.configuration=configuration;
		
		connectTo(indexfile());
				
	}
		
	
	
	@Override
	public void validate(AssetInfo asset) {
		
		//so far: just validate contents is accessible
		valid(contentfileOf(asset)); 
	
	}

	
	@Override @SneakyThrows
	public synchronized InputStream load(@NonNull AssetInfo asset) {
		
		//consumer closes this
		return new FileInputStream(contentfileOf(asset));
		
	}

	@Override
	public synchronized void update(Asset asset, AssetInfo info, InputStream contents) {
		
		//store contents
		store(contentfileOf(info), contents);
		
		//properties may have changed
		store(indexfile());
		
		
	}
	
	@Override @SneakyThrows
	public synchronized void create(Asset asset, AssetInfo info, InputStream contents) {

		File file = contentfileOf(info);
		
		//store contents
		store(file, contents);
		
		index.assets().add(info);

		try {
			
			store(indexfile());
		
		}
		catch(RuntimeException e) { //intercept just to try and clear up contents

			if (!file.delete())
				log.error("cannot delete {} after failed publication",file);

			throw e;
			
		}

		
		
		
		
	}
	
	
	@Override
	public String toString() {
		return configuration.name()+"@"+configuration.location();
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private File indexfile() {
		return new File(configuration.location(), Constants.index_file_name);
	}
	
	private File contentfileOf(AssetInfo entry) {
		return new File(configuration.location(), entry.path());
	}
	
	

	
	//get it first time, then watch for changes 
	private void connectTo(File indexfile) {
	
		index = parse(indexfile);
		
		executor.execute(new Watcher(indexfile));
	}
	
	
	AssetIndex parse(File indexfile) {
		
		try {
	
			@Cleanup FileInputStream stream = new FileInputStream(indexfile);
			
			return mapper.readValue(stream, AssetIndex.class);
			
		}
		catch(Exception e) {
			throw unchecked("cannot load index file", e);
		}
	}
	
	void store(File indexfile) {
		
		try {
		
			@Cleanup FileOutputStream stream = new FileOutputStream(indexfile);
			
			mapper.writeValue(stream,index);
			
			
		}
		catch(Exception e) {
			throw unchecked("cannot update index file", e);
		}
		
			
	}
	
	void store(File file, InputStream stream) {
		
		//creates any directory in the path, in case it does not exist already
		new File(file.getAbsolutePath()).getParentFile().mkdirs();
		
		try {
		
			byte[] bytes = new byte[2048];
			
			@Cleanup FileOutputStream out = new FileOutputStream(file);
		
			int read = 0;
			
			while ((read=stream.read(bytes))!=-1) 	out.write(bytes,0,read);
			
			out.flush();
		
		} 
		catch(Exception e) {
			
			throw unchecked(this+": cannot store asset in "+file, e);

		}
	}
	
	@RequiredArgsConstructor
	class Watcher implements Runnable  {
		
		private final File indexfile;
		
		public void run() {
		
			try {
				
				@Cleanup WatchService watcher = FileSystems.getDefault().newWatchService();
	
				Path indexpath = Paths.get(indexfile.getPath());
				Path parentpath = indexpath.getParent();
				
				parentpath.register(watcher, ENTRY_MODIFY, ENTRY_CREATE);
				
				
				
				while (true) {
	
					WatchKey key = watcher.take();
				   
				    for (WatchEvent<?> event: key.pollEvents()) {
				        
				    	if (event.kind() == OVERFLOW)
				            continue;
				        
				        @SuppressWarnings("all")
				        WatchEvent<Path> ev = (WatchEvent) event;
				        
				       if (indexpath .equals(parentpath.resolve(ev.context()))) {
				        
				        	log.info("detected change to index for {}: reparsing",LocalProvider.this);
						
				        	index =parse(indexfile);
				       }
				       
				       boolean valid = key.reset();
				       
				       if (!valid)
				           break;
				       
				    }
				 
				
				}
				
			}
			catch(Exception e) {
				log.error("error whilst watching "+indexfile,e);
			}
		
		
		}
	}
	
	

}
