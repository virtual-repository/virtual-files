package org.virtual.files;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;

import org.virtual.files.config.ServiceConfiguration;
import org.virtual.files.local.LocalBrowser;
import org.virtual.files.local.LocalReader;
import org.virtualrepository.spi.Browser;
import org.virtualrepository.spi.Importer;
import org.virtualrepository.spi.Publisher;
import org.virtualrepository.spi.ServiceProxy;

public class Proxy implements ServiceProxy {

	
	@NonNull @Getter
	Browser browser;
	
	@Getter
	List<Importer<?,?>> importers = new ArrayList<Importer<?,?>>();
	
	@Getter
	List<Publisher<?,?>> publishers = new ArrayList<Publisher<?,?>>();
	
	
	public Proxy(ServiceConfiguration service) {
		
		//hardcoded for now
		
		this.browser = new LocalBrowser(service);
		this.importers.add(new LocalReader(service));
	}
}
