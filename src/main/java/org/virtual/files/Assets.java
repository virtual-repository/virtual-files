package org.virtual.files;

import static java.util.Arrays.*;
import static org.virtual.files.providers.AssetProviders.*;

import java.util.List;

import org.virtual.files.providers.AssetProvider;
import org.virtualrepository.spi.MutableAsset;

public class Assets {
	
	public static List<AssetProvider<?>> providers = asList(csvcodelistProvider);

	public static boolean handles(AssetEntry asset) {
		
		for (AssetProvider<?> provider : providers)
			if (provider.handles(asset))
				return true;
		
		return false;
	}

	public static MutableAsset assetFor(AssetEntry asset) {
		
		for (AssetProvider<?> provider : providers)
			if (provider.handles(asset))
				return provider.transform(asset);
		
		throw new IllegalArgumentException("cannot handle asset "+asset);
	}

}
