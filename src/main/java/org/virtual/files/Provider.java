package org.virtual.files;

import java.io.InputStream;

import org.virtual.files.index.AssetEntry;
import org.virtual.files.index.AssetIndex;

public interface Provider {

	AssetIndex index();
	
	void validate(AssetEntry entry);
	
	InputStream resolve(AssetEntry entry);
}
