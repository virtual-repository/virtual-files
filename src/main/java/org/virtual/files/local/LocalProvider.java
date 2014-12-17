package org.virtual.files.local;

import static java.nio.file.StandardWatchEventKinds.*;
import static org.virtual.files.common.Utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Cleanup;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.virtual.files.Provider;
import org.virtual.files.common.Constants;
import org.virtual.files.config.LocalConfiguration;
import org.virtual.files.index.AssetEntry;
import org.virtual.files.index.AssetIndex;

@Slf4j
public class LocalProvider implements Provider {

	@Getter
	private AssetIndex index;
	
	final private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	private LocalConfiguration config;
	
	
	
	@SneakyThrows //we know it exists
	public LocalProvider(@NonNull LocalConfiguration config) {
		
		this.config=config;
		
		File indexfile = new File(config.location(), Constants.index_file_name);
		
		index = parse(indexfile);
		
		executor.execute(new Watcher(indexfile));
	}
		
	@Override
	public void validate(AssetEntry entry) {
		
		valid(new File(config.location(),entry.path()));
	
	}

	
	@Override
	public InputStream resolve(AssetEntry entry) {
		
		try {
			return new FileInputStream(new File(config.location(),entry.path()));
		}
		catch(Exception e) {
			throw unchecked(e);
		}
	}
	
	
	
	@Override
	public String toString() {
		return config.name()+"@"+config.location();
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SneakyThrows
	AssetIndex parse(File index) {
		
		@Cleanup FileInputStream stream = new FileInputStream(index);
		
		return assetsFrom(stream);
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
