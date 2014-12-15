package org.virtual.files.config;

import static org.virtual.files.config.Configuration.Mode.*;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;


@Data
@RequiredArgsConstructor(staticName="config")
public class Configuration {

	public static enum Mode { development, production; }
	
	@JsonProperty
	Mode mode = production;
	
	@NonNull @JsonProperty
	List<ServiceConfiguration> services = new ArrayList<>();
	
}
