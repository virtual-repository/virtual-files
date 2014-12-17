package org.virtual.files.transforms;

import java.io.InputStream;

import org.virtualrepository.csv.CsvAsset;
import org.virtualrepository.csv.CsvTable;
import org.virtualrepository.spi.Transform;
import org.virtualrepository.tabular.Table;

public class ToTable<A extends CsvAsset> implements Transform<A, InputStream,Table> {

	@Override
	public Class<InputStream> inputAPI() {
		return InputStream.class;
	}
	
	@Override
	public Class<Table> outputAPI() {
		return Table.class;
	}
	
	@Override
	public Table apply(A asset, InputStream stream) throws Exception {
		
		return new CsvTable(asset, stream);
	}
}
