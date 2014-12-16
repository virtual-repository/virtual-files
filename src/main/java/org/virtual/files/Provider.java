package org.virtual.files;

import java.io.InputStream;

public interface Provider {

	AssetIndex index();
	
	void validate(AssetEntry entry);
	
	InputStream resolve(AssetEntry entry);
}
