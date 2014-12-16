package org.virtual.files.config;

import static java.util.stream.Collectors.*;
import static org.virtual.files.Proxies.*;
import static org.virtual.files.common.Constants.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtual.files.common.CommonProducers;
import org.virtual.files.common.Utils;
import org.virtualrepository.Property;
import org.virtualrepository.RepositoryService;

import dagger.Module;
import dagger.Provides;

@Module(includes=CommonProducers.class, library=true)
@Slf4j
public class ConfigurationProducers {
	
	@Provides
	@Singleton
	List<RepositoryService> services(@NonNull Configuration configuration) {
		
		List<RepositoryService> services = new ArrayList<>();
		
		for (ServiceConfiguration $ : configuration.services())
			try {
				
				$.validate();
				
				services.add(new RepositoryService($.name(),
												  proxyFor($),
												  propertiesOf($).toArray(new Property[0])));
				
		
			}
			catch(Exception e) {
				
				log.error("invalid configuration for service "+$.name()+": ignoring it (see cause)",e);
				
			}
			
		return services;

	}

	@Provides
	@Singleton
	Configuration configuration(ConfigurationLocator locator, ConfigurationContext ctx) {
		
		Logger log = LoggerFactory.getLogger(ConfigurationLocator.class);
				
		File location = locator.locate();
		
		String path = location.getAbsolutePath();
		
		InputStream stream = null;
		
		try {
		
			if (Utils.isValid(location)) {
				
				log.info("loading configuration @ {}",path);
					
				stream = new FileInputStream(location);
					
			}
			else {
				
				stream = ConfigurationLocator.class.getResourceAsStream("/"+config_filename);

				if (stream == null)
					throw new AssertionError("no configuration found on file system or classpath");

				log.info("starting with classpath configuration, persisting @ {}",path);
				
				
				
			}
			
			return ctx.bind(stream);
		}
		
		catch(Exception e) {
			throw new RuntimeException("cannot read the configuration @ " + path, e);
		}
	}

	
	/////////////////////////////////////////////////////////
	
	private List<Property> propertiesOf(ServiceConfiguration configuration) {
		
		return configuration.properties().entrySet().stream().map(
				
				$ -> new Property($.getKey(), $.getValue())
		
		).collect(toList());
		
				
	}
	
}
