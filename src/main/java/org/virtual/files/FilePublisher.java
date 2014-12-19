package org.virtual.files;

import static java.lang.String.*;
import static org.virtual.files.Assets.*;
import static org.virtual.files.common.Constants.*;
import static org.virtual.files.common.Utils.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.virtual.files.index.AssetInfo;
import org.virtualrepository.Asset;
import org.virtualrepository.AssetType;
import org.virtualrepository.impl.Type;
import org.virtualrepository.spi.Publisher;

@RequiredArgsConstructor
public class FilePublisher implements Publisher<Asset, InputStream> {

	@NonNull 
	Provider provider;
	
	public static final Map<AssetType,String> extensions = new HashMap<>();
	
	@Override
	public void publish(Asset asset, InputStream content) throws Exception {
		
		//update or create?
		Optional<AssetInfo> info = provider.index().lookup(asset.name());
		
		if (info.isPresent())
			
			update(asset, info.get(), content);

		else 

			create(asset,content);
	}
	
	
	
	@Override
	public Type<? extends Asset> type() {
		return Type.any;
	}
	
	@Override
	public Class<InputStream> api() {
		return InputStream.class;
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	

	private void create(Asset asset, InputStream content) {
		
		asset.properties().add(now(created_property));
		
		provider.create(asset,infoFor(pathFor(asset), asset),content);
		
	}
	
	private void update(Asset asset, AssetInfo info, InputStream content) {
		
		if (!provider.configuration().overwrite() || hint(asset,no_overwrite_hint)!=null)
			throw new IllegalArgumentException("asset "+asset.name()+" already exists and no overwrite is allowed.");

		asset.properties().add(now(modified_property));
		
		provider.update(asset, updateInfoWith(info,asset), content);
		
	}
	private String pathFor(Asset asset) {
		
		//baseline
		String path = hintOr(asset, file_path_hint, asset.name().replaceAll("\\W+", "_"));
		
		String extension = hintOr(asset, file_extension_hint, file_default_extension);
		
		return format("%s.%s", path,extension);
		
	}
}
