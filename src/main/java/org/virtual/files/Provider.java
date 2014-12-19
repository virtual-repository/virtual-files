package org.virtual.files;

import java.io.InputStream;

import org.virtual.files.config.ServiceConfiguration;
import org.virtual.files.index.AssetIndex;
import org.virtual.files.index.AssetInfo;
import org.virtualrepository.Asset;

//provides basic access some file-based storage for assets, driven by asset-metadata
public interface Provider {

	ServiceConfiguration configuration();
	
	AssetIndex index();
	
	//verify provider-specific requirements
	void validate(AssetInfo info);
	
	//gets file content
	InputStream load(AssetInfo info);
	
	//create/update file content
	void create(Asset asset, AssetInfo info, InputStream stream);
	
	//create/update file content
	void update(Asset asset, AssetInfo info, InputStream stream);
}
