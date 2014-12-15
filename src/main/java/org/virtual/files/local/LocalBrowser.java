package org.virtual.files.local;

import java.util.Collection;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.virtual.files.config.ServiceConfiguration;
import org.virtualrepository.AssetType;
import org.virtualrepository.spi.Browser;
import org.virtualrepository.spi.MutableAsset;

@RequiredArgsConstructor
public class LocalBrowser implements Browser {

	@NonNull
	ServiceConfiguration service;
	
	@Override
	public Iterable<? extends MutableAsset> discover(
			Collection<? extends AssetType> types) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
