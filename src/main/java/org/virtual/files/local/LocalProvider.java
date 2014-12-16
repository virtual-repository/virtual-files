package org.virtual.files.local;

import static org.virtual.files.common.Utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;

import org.virtual.files.AssetEntry;
import org.virtual.files.AssetIndex;
import org.virtual.files.Provider;
import org.virtual.files.common.Constants;
import org.virtual.files.config.LocalConfiguration;

public class LocalProvider implements Provider {

	final private AssetIndex index;
	
	private LocalConfiguration config;
	
	@SneakyThrows //we know it exists
	public LocalProvider(@NonNull LocalConfiguration config) {
		
		this.config=config;
		
		File assetfile = new File(config.location(), Constants.index_file_name);
		
		@Cleanup FileInputStream stream = new FileInputStream(assetfile);
		
		index = assetsFrom(stream);

	}
	
	@Override
	public void validate(AssetEntry entry) {
		
		valid(new File(config.location(),entry.path()));
	
	}

	@Override
	public AssetIndex index() {
		return index;
	}
	
	@Override
	public InputStream resolve(AssetEntry entry) {
		
		try {
			return new FileInputStream(new File(config.location(),entry.path()));
		}
		catch(Exception e) {
			throw unchecked(e);
		}
	}
	
	
	@Override
	public String toString() {
		return config.name()+"@"+config.location();
	}
}
