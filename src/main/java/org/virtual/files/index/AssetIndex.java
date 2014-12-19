package org.virtual.files.index;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;


@Data
@RequiredArgsConstructor(staticName="index")
@NoArgsConstructor
public class AssetIndex {

	 @NonNull @JsonProperty
	 private String id;
	 
	 @JsonProperty
	 private Set<AssetInfo> assets = new HashSet<>();
	 
	 
	 
	public  Optional<AssetInfo> lookup(@NonNull String name) {
		 
		return assets.stream().filter($->name.equals($.name())).findFirst();
	 }
	 
	 
}
