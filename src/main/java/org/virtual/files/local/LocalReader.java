package org.virtual.files.local;

import java.io.InputStream;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.virtual.files.config.ServiceConfiguration;
import org.virtualrepository.csv.CsvCodelist;
import org.virtualrepository.impl.Type;
import org.virtualrepository.spi.Importer;

@RequiredArgsConstructor
public class LocalReader implements Importer<CsvCodelist,InputStream> {

	@NonNull
	ServiceConfiguration service;
	
	@Override
	public Class<InputStream> api() {
		return InputStream.class;
	}
	
	@Override
	public Type<? extends CsvCodelist> type() {
		return CsvCodelist.type;
	}
	
	@Override
	public InputStream retrieve(CsvCodelist asset) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
