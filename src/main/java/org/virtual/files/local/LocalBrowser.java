package org.virtual.files.local;

import static org.virtual.files.common.Utils.*;

import java.io.File;
import java.io.FileInputStream;

import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;

import org.virtual.files.AssetEntry;
import org.virtual.files.AssetIndex;
import org.virtual.files.BaseBrowser;
import org.virtual.files.common.Constants;
import org.virtual.files.config.LocalConfiguration;

public class LocalBrowser extends BaseBrowser {

	final AssetIndex index;
	
	LocalConfiguration config;
	
	@SneakyThrows //we know it exists
	public LocalBrowser(@NonNull LocalConfiguration config) {
		
		this.config=config;
		
		File assetfile = new File(config.location(), Constants.index_file_name);
		
		@Cleanup FileInputStream stream = new FileInputStream(assetfile);
		
		index = assetsFrom(stream);

	}
	
	@Override
	protected void $validate(AssetEntry entry) {
		
		valid(new File(config.location(),entry.path()));
	
	}

	@Override
	protected AssetIndex index() {
		return index;
	}
	
	@Override
	public String toString() {
		return config.name()+"@"+config.location();
	}
}
