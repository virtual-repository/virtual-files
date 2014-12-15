package org.virtual.files;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.virtual.files.common.CommonProducers;
import org.virtual.files.config.ConfigurationProducers;
import org.virtualrepository.RepositoryService;
import org.virtualrepository.spi.Lifecycle;

import dagger.Module;
import dagger.ObjectGraph;

@Module(injects=Plugin.class,includes={CommonProducers.class, ConfigurationProducers.class})
public class Plugin implements org.virtualrepository.spi.Plugin,Lifecycle {

	@Inject
	List<RepositoryService> services;
	
	@Override
	public void init() throws Exception {
		
		//self-injects and bootstraps DI
		ObjectGraph.create(this).inject(this); 
		
	}
	
	@Override
	public Collection<RepositoryService> services() {
		return services;
	}
}
