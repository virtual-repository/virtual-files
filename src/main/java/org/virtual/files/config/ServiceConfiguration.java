package org.virtual.files.config;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.*;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.*;
import static org.virtual.files.common.Constants.*;
import static org.virtual.files.local.LocalConfiguration.*;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import org.virtual.files.local.LocalConfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Data
@NoArgsConstructor

@JsonTypeInfo(use = NAME, include = PROPERTY, property = type_discriminant_property)  
@JsonSubTypes({@Type(value = LocalConfiguration.class,name=tag)})

public abstract class ServiceConfiguration {

	@NonNull @JsonProperty
	private QName name;
	
	@NonNull @JsonProperty
	private Map<String,String> properties = new HashMap<>();
	
	@JsonProperty 
	private boolean overwrite = true;
	
	public ServiceConfiguration add(String name, String val) {
		properties.put(name,val);
		return this;
	}
	
	public abstract String type();
	public abstract void validate();
	
}
