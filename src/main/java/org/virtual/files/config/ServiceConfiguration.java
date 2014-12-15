package org.virtual.files.config;

import javax.xml.namespace.QName;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@RequiredArgsConstructor(staticName="service")
@NoArgsConstructor 
public class ServiceConfiguration {

	
	@NonNull @JsonProperty
	QName name;
}
