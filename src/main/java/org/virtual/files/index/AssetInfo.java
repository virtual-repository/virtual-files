package org.virtual.files.index;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.virtualrepository.AssetType;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@RequiredArgsConstructor(staticName="info")
@NoArgsConstructor
public class AssetInfo {
	

	@NonNull @JsonProperty
	private String name;
	
	@NonNull @JsonProperty
	private String type;
	
	@NonNull @JsonProperty
	private String path;
	
	@JsonProperty
	private Map<String,String> properties = new HashMap<String, String>();

	
	public void validate() {
		
		info(name,type,path);
	}
	
	public boolean hasOneof(Collection<? extends AssetType> types) {
		
		return types.stream().anyMatch($->$.name().equals(type));
	}
}
