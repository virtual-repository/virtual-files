package org.virtual.files.config;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.*;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.*;
import static org.virtual.files.common.Constants.*;
import static org.virtual.files.config.LocalConfiguration.*;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
	QName name;
	
	@NonNull @JsonProperty
	Map<String,String> properties = new HashMap<>();
	
	public ServiceConfiguration add(String name, String val) {
		properties.put(name,val);
		return this;
	}
	
	abstract String type();
}
