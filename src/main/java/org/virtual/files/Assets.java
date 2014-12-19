package org.virtual.files;

import static java.util.Arrays.*;
import static org.virtual.files.common.Constants.*;
import static org.virtual.files.index.AssetInfo.*;
import static org.virtual.files.producers.AssetProducers.*;

import java.util.List;

import org.virtual.files.index.AssetInfo;
import org.virtual.files.producers.AssetProducer;
import org.virtualrepository.Asset;
import org.virtualrepository.Property;
import org.virtualrepository.spi.MutableAsset;

public class Assets {
	
	public static List<AssetProducer<?>> producers = asList(csvcodelistProducer,csvProducer,sdmxCodelistProducer,cometMappingProducer);

	public static boolean handles(AssetInfo entry) {
		
		for (AssetProducer<?> $ : producers)
			if ($.handles(entry))
				return true;
		
		return false;
	}

	public static MutableAsset assetFor(String id, AssetInfo entry) {
		
		for (AssetProducer<?> $ : producers)
			if ($.handles(entry))
				return asset(entry,$.transform(id,entry));
		
		throw new IllegalArgumentException("no producers exist for asset "+entry);
	}
	
	public static AssetInfo updateInfoWith(AssetInfo info, Asset asset) {
		
		for (Property prop : asset.properties())
			if (prop.is(String.class)) //can only copy string-valued properties
				info.properties().put(prop.name(),prop.value(String.class));
		
		return info;
	}

	public static AssetInfo infoFor(String path, Asset asset) {
		
		AssetInfo info = info(asset.name(),asset.type().name(),path);
				
		return updateInfoWith(info, asset);
	}

	
	
	///////////////////////////////////////////////////////////////////////////////////
	
	
	private static MutableAsset asset(AssetInfo entry,MutableAsset asset) {
		
		asset.properties().add(new Property(index_entry_property, entry,false));
		
		entry.properties().entrySet().stream().forEach(
				$->new Property($.getKey(), $.getValue())
		);
		
		return asset;
	}
	
}
