package org.virtual.files.producers;

import org.virtual.files.index.AssetInfo;
import org.virtualrepository.spi.MutableAsset;

public interface AssetProducer<A  extends MutableAsset> {

	
	boolean handles(AssetInfo asset);
	
	A transform(String id,AssetInfo asset); 
	
}
