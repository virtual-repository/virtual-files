package org.virtual.files;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;

import org.virtualrepository.spi.Importer;
import org.virtualrepository.spi.Publisher;
import org.virtualrepository.spi.ServiceProxy;

public class BaseProxy implements ServiceProxy {

	@NonNull @Getter
	BaseBrowser browser;
	
	@Getter
	List<Importer<?,?>> importers = new ArrayList<Importer<?,?>>();
	
	@Getter
	List<Publisher<?,?>> publishers = new ArrayList<Publisher<?,?>>();
	
	
	public BaseProxy(Provider provider) {
		
		this.browser = new BaseBrowser(provider);
		
		this.importers.add(
			new BaseImporter(provider)
		);
	}
}
