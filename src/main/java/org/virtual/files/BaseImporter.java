package org.virtual.files;

import static org.virtual.files.common.Constants.*;

import java.io.InputStream;
import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.virtualrepository.Asset;
import org.virtualrepository.Properties;
import org.virtualrepository.Property;
import org.virtualrepository.spi.Importer;

@RequiredArgsConstructor
public abstract class BaseImporter<A extends Asset,T> implements Importer<A, T> {

	//baseclasses know how/where to get the stream
	@NonNull 
	Function<AssetEntry,InputStream> provider;
	
	//transforms know how to parse it
	@NonNull 
	BiFunction<AssetEntry,InputStream,T> transform;
	
	@Override
	public T retrieve(A asset) throws Exception {
		
		AssetEntry entry = entryIn(asset);
		
		return transform.apply(entry, provider.apply(entry));
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////
	
	AssetEntry entryIn(A asset) {
	
		Properties props = asset.properties();
		
		if (!props.contains(index_entry_property))
			throw new IllegalArgumentException("unresolvable asset: no property "+index_entry_property);
		
		Property property = props.lookup(index_entry_property);
		
		if (!property.is(AssetEntry.class))
			throw new IllegalArgumentException("invalid asset: unexpdcted value for property "+index_entry_property);
		
		return property.value(AssetEntry.class);
		
	}
}
