package org.virtual.files;

import static java.util.Arrays.*;
import static org.virtual.files.providers.AssetProducers.*;

import java.util.List;

import org.virtual.files.providers.AssetProducer;
import org.virtualrepository.spi.MutableAsset;

public class Assets {
	
	public static List<AssetProducer<?>> producers = asList(csvcodelistProducers);

	public static boolean handles(AssetEntry entry) {
		
		for (AssetProducer<?> $ : producers)
			if ($.handles(entry))
				return true;
		
		return false;
	}

	public static MutableAsset assetFor(AssetEntry entry) {
		
		for (AssetProducer<?> $ : producers)
			if ($.handles(entry))
				return $.transform(entry);
		
		throw new IllegalArgumentException("no producers exist for asset "+entry);
	}

}
