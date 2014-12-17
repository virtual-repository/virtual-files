package org.virtual.files.producers;

import org.virtual.files.index.AssetEntry;
import org.virtualrepository.spi.MutableAsset;

public interface AssetProducer<T  extends MutableAsset> {

	
	boolean handles(AssetEntry asset);
	
	T transform(String id,AssetEntry asset); 
	
}
