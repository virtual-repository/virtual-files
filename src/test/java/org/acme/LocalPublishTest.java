package org.acme;

import static java.util.UUID.*;
import static org.junit.Assert.*;
import static org.virtual.files.common.Constants.*;
import static org.virtual.files.common.Utils.*;
import static org.virtual.files.local.LocalConfiguration.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import javax.xml.namespace.QName;

import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.virtual.files.FileProxy;
import org.virtual.files.FilePublisher;
import org.virtual.files.common.Constants;
import org.virtual.files.index.AssetIndex;
import org.virtual.files.index.AssetInfo;
import org.virtual.files.local.LocalConfiguration;
import org.virtual.files.local.LocalProvider;
import org.virtualrepository.Asset;
import org.virtualrepository.Property;
import org.virtualrepository.RepositoryService;
import org.virtualrepository.csv.CsvAsset;

public class LocalPublishTest {
	
	File testdir = new File("src/test/resources/"+randomUUID());
	
	LocalConfiguration config;

	AssetIndex index = index();
	
	FilePublisher publisher;
	
	@Before
	public void setup() {
	
		testdir.mkdirs();
		
		config = local(testdir.getAbsolutePath());
		
		config.name(new QName("local"));

		index= index();
		
		store(index);
		
		 publisher = new FilePublisher(new LocalProvider(config));
	}
	
	
	
	@Test(expected=Exception.class)
	public void index_must_exist() {

		new FilePublisher(new LocalProvider(local("some/invented/path")));
		
	}
	
	@Test @SneakyThrows
	public void assets_can_be_published() {

		publisher.publish(someAsset(),someContent());
		
		AssetIndex index = loadIndex();
		
		assertTrue(index.assets().size()==1);
		
		indexisValid();
		
		assertTrue(index.assets().iterator().next().properties().containsKey(created_property));
	}
	
	@Test @SneakyThrows
	public void assets_with_funny_names_can_be_published() {

		publisher.publish(someAsset("I have spaces"),someContent());
		
		indexisValid();
	}
	
	@Test @SneakyThrows
	public void publication_hints_are_followed() {

		Asset a = someAsset("I have spaces");
		
		a.properties().add(new Property(file_path_hint,"a/b"),new Property(file_extension_hint,"ext"));
		
		publisher.publish(a,someContent());
		
		indexisValid();
		
	}
	
	@Test @SneakyThrows
	public void assets_can_be_updated() {

		Asset a = someAsset();
		
		publisher.publish(someAsset(),someContent());
		
		AssetIndex index =loadIndex();
		
		int size= index.assets().size();
		
		AssetInfo info = index.assets().iterator().next();
		
		assertFalse(info.properties().containsKey(modified_property));
		
		publisher.publish(a, streamOf("new content"));

		index = loadIndex();
		
		info = index.assets().iterator().next();
		
		assertTrue(info.properties().containsKey(modified_property));
		
		assertEquals("new content",loadContentsOf(info));
		
		assertEquals(size,loadIndex().assets().size());
	}

	

	@Test(expected=Exception.class) 
	@SneakyThrows
	public void assets_updates_can_be_disabled() {

		Asset a = someAsset();
		
		publisher.publish(a,someContent());
	
		config.overwrite(false);
		
		publisher.publish(a,someContent());
		
	}
	
	@Test(expected=Exception.class) 
	@SneakyThrows
	public void assets_updates_can_be_selectively_disabled() {

		Asset a = someAsset();
		
		publisher.publish(a,someContent());
	
		a.properties().add(new Property(no_overwrite_hint,"true"));
		
		publisher.publish(a,someContent());
		
	}
	
	
	@After
	public void teardown() {
	
		deleteTestFolder();

	}
	
	//////////////////////////////////////////////////////////////////////////////////////// helpers

	AssetIndex index() {
		return AssetIndex.index("local");
	}
	
	
	//////////////////////////
	
	File indexfile() {
		return new File(config.location(), Constants.index_file_name); 
	}
	
	@SneakyThrows
	void store(AssetIndex index) {
		
		@Cleanup FileOutputStream stream = new FileOutputStream(indexfile());
		
		mapper.writeValue(stream,index);
					
	}
	
	@SneakyThrows
	AssetIndex loadIndex() {
		
		@Cleanup FileInputStream stream = new FileInputStream(indexfile());
		
		return mapper.readValue(stream,AssetIndex.class);
					
	}
	
	
	
	@SneakyThrows
	void indexisValid() {
		
		loadIndex().assets().stream().forEach($->{

			System.err.println($);
			$.validate();
			contentsOf($).exists();
				
		});
			
	}
	
	//////////////////////
	
	CsvAsset someAsset(String name) {
		
		return new CsvAsset(name,new RepositoryService(new QName("bogus"), new FileProxy(new LocalProvider(config))));
	}

	CsvAsset someAsset() {
		
		return someAsset("somename");
	}

	File contentsOf(AssetInfo info) {
		
		return new File(config.location(),info.path());
	}
	
	InputStream someContent() {
		
		return streamOf("one,two,three\n1,2,3");
	}
	
	InputStream streamOf(@NonNull String name) {
		
		return new ByteArrayInputStream(name.getBytes());
	}
	
	@SneakyThrows
	String loadContentsOf(AssetInfo info) {
		
		@Cleanup FileInputStream stream = new FileInputStream(contentsOf(info));
		
		byte[] bytes = new byte[2048];
		
		@Cleanup ByteArrayOutputStream out = new ByteArrayOutputStream();
	
		int read = 0;
		
		while ((read=stream.read(bytes))!=-1) 	out.write(bytes,0,read);
		
		out.flush();
		
		return new String(out.toByteArray());
		
	}
	
	
	
	
	@SneakyThrows
	void deleteTestFolder() {
		
		   Path directory = Paths.get(testdir.getAbsolutePath());
		   Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
			   @Override
			   public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				   Files.delete(file);
				   return FileVisitResult.CONTINUE;
			   }

			   @Override
			   public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				   Files.delete(dir);
				   return FileVisitResult.CONTINUE;
			   }

		   });
	}
	
}
