package org.acme;

import static org.junit.Assert.*;

import java.io.InputStream;

import lombok.SneakyThrows;

import org.junit.Ignore;
import org.junit.Test;
import org.virtualrepository.Asset;
import org.virtualrepository.VirtualRepository;
import org.virtualrepository.comet.CometAsset;
import org.virtualrepository.csv.CsvAsset;
import org.virtualrepository.csv.CsvCodelist;
import org.virtualrepository.impl.Repository;
import org.virtualrepository.sdmx.SdmxCodelist;
import org.virtualrepository.tabular.Row;
import org.virtualrepository.tabular.Table;

public class ApiTest {

	VirtualRepository repository = new Repository();
	
	@Test
	public void browse() {
		
		assertTrue(repository.discover(CsvCodelist.type)>0);
	}
	
	
	@Test
	public void browseAndRetrieveCsvAsset() {
		
		repository.discover(CsvAsset.type);
		
		Asset a = repository.iterator().next();
		
		assertNotNull(repository.retrieve(a,InputStream.class));
		
		for (Row row : repository.retrieve(a,Table.class))
			System.out.println(row);
	}
	
	@Test
	public void browseAndRetrieveCsvCodelist() {
		
		repository.discover(CsvCodelist.type);
		
		Asset a = repository.iterator().next();
		
		assertNotNull(repository.retrieve(a,InputStream.class));
		assertNotNull(repository.retrieve(a,Table.class));
		
		
		for (Row row : repository.retrieve(a,Table.class))
			System.out.println(row);

	}
	
	@Test
	public void browseAndRetrieveSdmxCodelist() {
		
		repository.discover(SdmxCodelist.type);
		
		Asset a = repository.iterator().next();
		
		assertNotNull(repository.retrieve(a,InputStream.class));

	}
	
	@Test
	public void browseAndRetrieveComet() {
		
		repository.discover(CometAsset.type);
		
		Asset a = repository.iterator().next();
		
		assertNotNull(repository.retrieve(a,InputStream.class));

	}
	
	@Test @Ignore @SneakyThrows  //interactive 
	public void localChangeDetection() {
		
		assertTrue(repository.discover(CometAsset.type)>0);
		
		for (;;) {
			Thread.sleep(8000);
			System.out.println();
			repository.discover(CometAsset.type);

		}
	}
}
