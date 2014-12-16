package org.virtual.files.local;

import static org.virtual.files.common.Utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import lombok.Getter;
import lombok.NonNull;

import org.virtual.files.AssetEntry;
import org.virtual.files.UniversalImporter;
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
		
		this.browser = new LocalBrowser(service);
		
		this.importers.add(
			new UniversalImporter(provider(service.location()))
		);
	}
	
	
	////////////////////////////////////////////////////////////////////////
	
	Function<AssetEntry,InputStream> provider(String location) {
		
		return $-> {
		
			try {
				return new FileInputStream(new File(location,$.path()));
			}
			catch(Exception e) {
				throw unchecked(e);
			}
		};
	}
}
