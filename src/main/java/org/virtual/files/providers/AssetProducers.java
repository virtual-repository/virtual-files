package org.virtual.files.providers;

import static java.lang.Integer.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.virtual.files.AssetEntry;
import org.virtualrepository.AssetType;
import org.virtualrepository.csv.CsvAsset;
import org.virtualrepository.csv.CsvCodelist;
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

		public CsvCodelist transform(String id,AssetEntry asset) {
			
			String code = asset.properties().get("codeColumn");
			
			int codecol = code == null? 0 : valueOf(code);
			
			return new CsvCodelist(id,asset.name().toString(),codecol);
			
		};
	};
	
	public static AssetProducer<CsvAsset> csvProducer = new BaseProducer<CsvAsset>(CsvAsset.type) {

		public CsvAsset transform(String id,AssetEntry asset) {
			
			return new CsvAsset(id,asset.name().toString());
			
		};
	};
	

	
}
