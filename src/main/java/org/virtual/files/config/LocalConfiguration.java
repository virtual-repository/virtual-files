package org.virtual.files.config;

import static lombok.AccessLevel.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
}
