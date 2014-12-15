package org.virtual.files.local;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;

import org.virtual.files.config.LocalConfiguration;
import org.virtualrepository.spi.Importer;
import org.virtualrepository.spi.Publisher;
import org.virtualrepository.spi.ServiceProxy;

public class LocalProxy implements ServiceProxy {

	
	@NonNull @Getter
	LocalBrowser browser;
	
	@Getter
	List<Importer<?,?>> importers = new ArrayList<Importer<?,?>>();
	
	@Getter
	List<Publisher<?,?>> publishers = new ArrayList<Publisher<?,?>>();
	
	
	public LocalProxy(LocalConfiguration service) {
		
		//hardcoded for now
		
		this.browser = new LocalBrowser(service);
		this.importers.add(new LocalStreamReader(service));
	}
	
	
	
}
