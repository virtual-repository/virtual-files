package org.virtual.files.providers;

import org.virtual.files.AssetEntry;
import org.virtualrepository.spi.MutableAsset;

public interface AssetProvider<T  extends MutableAsset> {

	
	boolean handles(AssetEntry asset);
	
	T transform(AssetEntry asset); 
	
}