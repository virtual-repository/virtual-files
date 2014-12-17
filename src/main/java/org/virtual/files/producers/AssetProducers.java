package org.virtual.files.producers;

import java.net.URI;
import java.nio.charset.Charset;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.virtual.files.index.AssetEntry;
import org.virtualrepository.AssetType;
import org.virtualrepository.comet.CometAsset;
import org.virtualrepository.csv.CsvAsset;
import org.virtualrepository.csv.CsvCodelist;
import org.virtualrepository.sdmx.SdmxCodelist;
import org.virtualrepository.spi.MutableAsset;

//service-independent entry->asset transformers
public class AssetProducers {
	
	@RequiredArgsConstructor
	static abstract class BaseProducer<T extends MutableAsset>  implements AssetProducer<T> {
		
		@NonNull
		final AssetType type;
		
		@Override
		public boolean handles(AssetEntry asset) {
			return type.name().equals(asset.type());
		}
	}
	
	
	public static AssetProducer<CsvCodelist> csvcodelistProducer = new BaseProducer<CsvCodelist>(CsvCodelist.type) {

		public CsvCodelist transform(String id,AssetEntry entry) {
			
			String code = entry.properties().get("codeColumn");

			int codecol = code == null? 0 : Integer.valueOf(code);

			CsvCodelist asset = new CsvCodelist(id,entry.name().toString(),codecol);
			
			return configure(asset,entry);
			
		};
	};
	
	public static AssetProducer<CsvAsset> csvProducer = new BaseProducer<CsvAsset>(CsvAsset.type) {

		public CsvAsset transform(String id,AssetEntry entry) {
			
			CsvAsset asset = new CsvAsset(id,entry.name().toString());
			
			return configure(asset,entry);
			
		};
	};
	
	
	public static AssetProducer<SdmxCodelist> sdmxCodelistProducer = new BaseProducer<SdmxCodelist>(SdmxCodelist.type) {

		public SdmxCodelist transform(String id,AssetEntry entry) {
			
			SdmxCodelist asset= new SdmxCodelist(get(entry,"urn"),id,get(entry,"version"),entry.name().toString()); 
			
			
			asset.setStatus(get(entry,"status"));
			asset.setURI(URI.create(get(entry,"uri")));
			asset.setAgency(get(entry,"agency"));
			
			return asset;
			
		};
	};
	
	
	public static AssetProducer<CometAsset> cometMappingProducer = new BaseProducer<CometAsset>(CometAsset.type) {

		public CometAsset transform(String id,AssetEntry entry) {
			
			CometAsset asset= new CometAsset(id,entry.name().toString()); 
			
			
			
			
			return asset;
			
		};
	};
	

	/////////////////////////////////////////////////////////////////////////////// helpers
	
	private static <T extends CsvAsset> T configure(T asset, AssetEntry entry) {
		
		String headerFlag = entry.properties().get("hasHeader");
		
		boolean hasHeader = headerFlag == null ? false: Boolean.valueOf(headerFlag);
		
		asset.hasHeader(hasHeader);
		
		String encoding = entry.properties().get("encoding");
		
		if (encoding!=null)
			asset.setEncoding(Charset.forName(encoding));
		
		String delimiter = entry.properties().get("delimiter");
		
		if (delimiter!=null && delimiter.length()==1)
			asset.setDelimiter(delimiter.charAt(0));
		
		return asset;
	}
	
		
	private static String get(AssetEntry entry, String name) {
		
		return get(entry,name,"unknown");
	}

	private static String get(AssetEntry entry, String name, String def) {
		
		String val = entry.properties().get(name);

		return val == null ? def : val;
	}
}
