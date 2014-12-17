package org.virtual.files;

import static java.util.Arrays.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;

import org.virtual.files.transforms.ToTable;
import org.virtualrepository.Asset;
import org.virtualrepository.csv.CsvAsset;
import org.virtualrepository.csv.CsvCodelist;
import org.virtualrepository.spi.ImportAdapter;
import org.virtualrepository.spi.Importer;
import org.virtualrepository.spi.Publisher;
import org.virtualrepository.spi.ServiceProxy;
import org.virtualrepository.spi.Transform;

public class BaseProxy implements ServiceProxy {

	@NonNull @Getter
	BaseBrowser browser;
	
	@Getter
	List<Importer<?,?>> importers = new ArrayList<Importer<?,?>>();
	
	@Getter
	List<Publisher<?,?>> publishers = new ArrayList<Publisher<?,?>>();
	
	
	public BaseProxy(Provider provider) {
		
		this.browser = new BaseBrowser(provider);
		
		BaseImporter base = new BaseImporter(provider);
		
		this.importers.addAll(asList(
			base,
			adapt(base, new ToTable<CsvAsset>()),
			adapt(base, new ToTable<CsvCodelist>())
		));
	}
	

	////////////////////////////////////////////////////////////////////////////////////// helpers
	
	<A extends Asset,O> Importer<A,O> adapt(BaseImporter base,Transform<A, InputStream, O> transform) {
		
		@SuppressWarnings("all") //universal importer is safe to specialise
		Importer<A,InputStream> safebase = (Importer) base;
		
		return ImportAdapter.adapt(safebase, transform);
	}
	
}
