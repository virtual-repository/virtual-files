package org.virtual.files.local;

import java.util.Collection;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.virtual.files.config.LocalConfiguration;
import org.virtualrepository.AssetType;
import org.virtualrepository.spi.Browser;
import org.virtualrepository.spi.MutableAsset;

@RequiredArgsConstructor
public class LocalBrowser implements Browser {

	@NonNull
	LocalConfiguration service;
	
	@Override
	public Iterable<? extends MutableAsset> discover(
			Collection<? extends AssetType> types) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
