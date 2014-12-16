package org.virtual.files;

import static java.util.Arrays.*;
import static org.virtual.files.common.Constants.*;
import static org.virtual.files.providers.AssetProducers.*;

import java.util.List;

import org.virtual.files.providers.AssetProducer;
import org.virtualrepository.Property;
import org.virtualrepository.spi.MutableAsset;

public class Assets {
	
	public static List<AssetProducer<?>> producers = asList(csvcodelistProducer);

	public static boolean handles(AssetEntry entry) {
		
		for (AssetProducer<?> $ : producers)
			if ($.handles(entry))
				return true;
		
		return false;
	}

	public static MutableAsset assetFor(String id, AssetEntry entry) {
		
		for (AssetProducer<?> $ : producers)
			if ($.handles(entry))
				return resolvable(entry,$.transform(id,entry));
		
		throw new IllegalArgumentException("no producers exist for asset "+entry);
	}

	
	
	///////////////////////////////////////////////////////////////////////////////////
	
	
	static MutableAsset resolvable(AssetEntry entry,MutableAsset asset) {
		
		asset.properties().add(new Property(index_entry_property, entry,false));
		
		return asset;
	}
	
}
