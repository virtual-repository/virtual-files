package org.virtual.files;

import org.virtual.files.config.LocalConfiguration;
import org.virtual.files.config.ServiceConfiguration;
import org.virtual.files.local.LocalProxy;
import org.virtualrepository.spi.ServiceProxy;

public class Proxies {

	public static ServiceProxy proxyFor(ServiceConfiguration configuration) {
		
		switch(configuration.type()) {
		
			case "local" : return new LocalProxy( (LocalConfiguration) configuration);
		
			default: 
				throw new IllegalArgumentException("unknown type "+configuration.type());
		}
		
	}
}
