package org.virtual.files;

import static org.virtual.files.Assets.*;
import static org.virtual.files.index.AssetEntry.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.virtual.files.index.AssetEntry;
import org.virtual.files.index.AssetIndex;
import org.virtualrepository.AssetType;
import org.virtualrepository.spi.Browser;
import org.virtualrepository.spi.MutableAsset;

@Slf4j
@RequiredArgsConstructor
public class BaseBrowser implements Browser {
	
	@NonNull
	Provider provider;
	
	@Override
	public final Iterable<? extends MutableAsset> discover(Collection<? extends AssetType> types) throws Exception {
		
		AssetIndex index = provider.index();
		
		List<MutableAsset> assets = new ArrayList<>();
		
		for (AssetEntry $ : index.assets())
			
			if ($.hasOneof(types))
				
				try {
					validate($);
					
					String id = index.id()+"-"+$.name().toString();
					
					assets.add(assetFor(id,$));
				}
				catch(Exception e) {
					
					log.warn("discarding invalid entry "+$,e);
				}
		
		
		log.info("{} discovered {} assets from an index of {}",this,assets.size(),index.assets().size());
		
		return assets;
	}
	
	
	void validate(AssetEntry entry) {
		
		//basic validation is by cloning
		asset(entry.name(), entry.type(), entry.path());
			
		//delegate to subclasses
		provider.validate(entry);
			
		
		
	}
	
	
	
}
