package org.virtual.files.config;

import org.virtual.files.local.LocalProxy;
import org.virtualrepository.spi.ServiceProxy;

public class ProxyProvider {

	public static ServiceProxy proxyFor(ServiceConfiguration configuration) {
		
		switch(configuration.type()) {
		
			case "local" : return new LocalProxy( (LocalConfiguration) configuration);
		
			default: 
				throw new IllegalArgumentException("unknown type "+configuration.type());
		}
		
	}
}
