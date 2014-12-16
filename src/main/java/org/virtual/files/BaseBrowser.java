package org.virtual.files;

import static java.util.stream.Collectors.*;
import static org.virtual.files.AssetEntry.*;
import static org.virtual.files.Assets.*;

import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.virtualrepository.AssetType;
import org.virtualrepository.spi.Browser;
import org.virtualrepository.spi.MutableAsset;

@Slf4j
public abstract class BaseBrowser implements Browser {

	@Override
	public final Iterable<? extends MutableAsset> discover(Collection<? extends AssetType> types) throws Exception {
		
		AssetIndex index = index();
		
		List<MutableAsset> assets = index.assets().stream()
				
				.filter($->isValid($))
				.filter($->$.hasOneof(types))
				.filter($->handles($))
				.map($->assetFor($))
		
		.collect(toList());
		
		log.info("{} discovered {} assets from an index of {}",this,assets.size(),index.assets().size());
		
		return assets;
	}
	
	
	boolean isValid(AssetEntry entry) {
		
		try {
			
			//basic validation is by cloning
			asset(entry.name(), entry.type(), entry.path());
			
			//delegate to subclasses
			validate(entry);
			
			return true;
		
		}
		catch(Exception e) {
			
			log.warn("discarding invalid entry {} ({})",entry,e.getMessage());
			return false;
		}
		
	}
	
	protected abstract AssetIndex index();
	
	protected void validate(AssetEntry entry){};
	
	
	
}
