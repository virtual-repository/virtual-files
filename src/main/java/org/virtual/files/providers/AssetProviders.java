package org.virtual.files.providers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.virtual.files.AssetEntry;
import org.virtualrepository.AssetType;
import org.virtualrepository.csv.CsvCodelist;
import org.virtualrepository.spi.MutableAsset;

public class AssetProviders {
	
	@RequiredArgsConstructor
	static abstract class BaseProvider<T extends MutableAsset>  implements AssetProvider<T> {
		
		@NonNull
		final AssetType type;
		
		@Override
		public boolean handles(AssetEntry asset) {
			return type.name().equals(asset.type());
		}
	}
	
	
	public static AssetProvider<CsvCodelist> csvcodelistProvider = new BaseProvider<CsvCodelist>(CsvCodelist.type) {

		public CsvCodelist transform(AssetEntry asset) {
			
			return new CsvCodelist(asset.name().toString(),asset.name().toString(),0);
		};
	};
	

	
}
