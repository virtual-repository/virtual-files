package org.virtual.files;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;


@Data
@RequiredArgsConstructor(staticName="group")
public class AssetIndex {

	 @NonNull @JsonProperty
	 private Set<AssetEntry> assets = new HashSet<>();
	 
}
