package org.virtual.files.config;

import static lombok.AccessLevel.*;
import static org.virtual.files.common.Constants.*;
import static org.virtual.files.common.Utils.*;

import java.io.File;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@EqualsAndHashCode(callSuper=true)
@RequiredArgsConstructor(staticName="local") //cannot use the tag below, seemingly
@NoArgsConstructor
public class LocalConfiguration extends ServiceConfiguration {

	public static final String tag = "local";
	
	@JsonProperty @Setter(PRIVATE)
	String type = tag;

	@JsonProperty @NonNull
	private String location;
	
	@Override @SneakyThrows
	public void validate() {
		
		File folder = new File(location);
		
		validDirectory(folder);
		
		File assetfile = new File(folder,index_file_name);
		
		valid(assetfile);
		
	}
}
