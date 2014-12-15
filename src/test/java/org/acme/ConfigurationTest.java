package org.acme;

import static java.util.Arrays.*;
import static org.junit.Assert.*;
import static org.virtual.files.common.Utils.*;
import static org.virtual.files.config.Configuration.*;

import org.junit.Test;
import org.virtual.files.config.Configuration;

public class ConfigurationTest {

	
	@Test
	public void configurationRoundTrips() {
		
		Configuration configuration = config()
				.mode(Configuration.Mode.development)
				.services(asList());     
		
		assertEquals(configuration,jsonRoundtrip(configuration));
	}
}
