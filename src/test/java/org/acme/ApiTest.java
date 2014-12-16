package org.acme;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Test;
import org.virtualrepository.Asset;
import org.virtualrepository.VirtualRepository;
import org.virtualrepository.csv.CsvCodelist;
import org.virtualrepository.impl.Repository;

public class ApiTest {

	VirtualRepository repository = new Repository();
	
	@Test
	public void browse() {
		
		assertTrue(repository.discover(CsvCodelist.type)>0);
	}
	
	@Test
	public void browseAndRetrieve() {
		
		repository.discover(CsvCodelist.type);
		
		Asset a = repository.iterator().next();
		
		assertNotNull(repository.retrieve(a,InputStream.class));
	}
}
