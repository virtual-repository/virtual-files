package org.acme;

import org.junit.Test;
import org.virtualrepository.VirtualRepository;
import org.virtualrepository.csv.CsvCodelist;
import org.virtualrepository.impl.Repository;

public class ApiTest {

	VirtualRepository repository = new Repository();
	
	@Test
	public void browse() {
		
		repository.discover(CsvCodelist.type);
	}
}
