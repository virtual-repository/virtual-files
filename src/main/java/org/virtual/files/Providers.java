package org.virtual.files;

import org.virtual.files.config.LocalConfiguration;
import org.virtual.files.config.ServiceConfiguration;
import org.virtual.files.local.LocalProvider;

public class Providers {

	public static Provider providerFor(ServiceConfiguration configuration) {
		
		
		switch(configuration.type()) {
		
			case "local" : return new LocalProvider( (LocalConfiguration) configuration);
		
			default: 
				throw new IllegalArgumentException("unknown type "+configuration.type());
		}
		
	}
}
