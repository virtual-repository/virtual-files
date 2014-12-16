package org.virtual.files;

import static org.virtual.files.common.Constants.*;

import java.io.InputStream;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.virtualrepository.Asset;
import org.virtualrepository.Properties;
import org.virtualrepository.Property;
import org.virtualrepository.impl.Type;
import org.virtualrepository.spi.Importer;

@RequiredArgsConstructor
public class BaseImporter implements Importer<Asset, InputStream> {

	//baseclasses know how/where to get the stream
	@NonNull 
	Provider provider;
	
	@Override
	public InputStream retrieve(Asset asset) throws Exception {
		
		AssetEntry entry = entryIn(asset);
		
		return provider.resolve(entry);
	}
	
	
	@Override
	public Type<? extends Asset> type() {
		return Type.any;
	}
	
	@Override
	public Class<InputStream> api() {
		return InputStream.class;
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	
	AssetEntry entryIn(Asset asset) {
	
		Properties props = asset.properties();
		
		if (!props.contains(index_entry_property))
			throw new IllegalArgumentException("unresolvable asset: no property "+index_entry_property);
		
		Property property = props.lookup(index_entry_property);
		
		if (!property.is(AssetEntry.class))
			throw new IllegalArgumentException("invalid asset: unexpdcted value for property "+index_entry_property);
		
		return property.value(AssetEntry.class);
		
	}
}
