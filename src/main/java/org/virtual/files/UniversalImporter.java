package org.virtual.files;

import java.io.InputStream;
import java.util.function.Function;

import org.virtualrepository.Asset;
import org.virtualrepository.impl.Type;

//asset-agnostic, percolates the underlying stream AS the API
public class UniversalImporter extends BaseImporter<Asset,InputStream> {

	public UniversalImporter(Function<AssetEntry,InputStream> provider) {
		
		
		super(provider,($,stream) -> stream); //no parsing, the stream IS the API
	
	}
	
	@Override
	public Type<? extends Asset> type() {
		return Type.any;
	}
	
	@Override
	public Class<InputStream> api() {
		return InputStream.class;
	}
}
