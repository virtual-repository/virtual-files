package org.virtual.files.index;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.virtualrepository.AssetType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@RequiredArgsConstructor(staticName="asset")
@NoArgsConstructor
public class AssetEntry {
	

	@NonNull @JsonProperty
	private QName name;
	
	@NonNull @JsonProperty
	private String type;
	
	@JsonProperty
	private Map<String,String> properties = new HashMap<String, String>();
	
	@NonNull @JsonProperty
	private String path;
	
	
	public boolean hasOneof(Collection<? extends AssetType> types) {
		
		return types.stream().anyMatch($->$.name().equals(type));
	}
}