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

public class FileProxy implements ServiceProxy {

	@NonNull @Getter
	FileBrowser browser;
	
	@Getter
	List<Importer<?,?>> importers = new ArrayList<Importer<?,?>>();
	
	@Getter
	List<Publisher<?,?>> publishers = new ArrayList<Publisher<?,?>>();
	
	
	public FileProxy(Provider provider) {
		
		this.browser = new FileBrowser(provider);
		
		FileImporter base = new FileImporter(provider);
		
		this.importers.addAll(asList(
			base,
			adapt(base, new ToTable<CsvAsset>()),
			adapt(base, new ToTable<CsvCodelist>())
		));
		
		FilePublisher basepub = new FilePublisher(provider);
		
		this.publishers.addAll(asList(
			basepub
		));
		
	}
	

	////////////////////////////////////////////////////////////////////////////////////// helpers
	
	<A extends Asset,O> Importer<A,O> adapt(FileImporter base,Transform<A, InputStream, O> transform) {
		
		@SuppressWarnings("all") //universal importer is safe to specialise
		Importer<A,InputStream> safebase = (Importer) base;
		
		return ImportAdapter.adapt(safebase, transform);
	}
	
}
