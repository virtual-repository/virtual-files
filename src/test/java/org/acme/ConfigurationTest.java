package org.acme;

import static java.util.Arrays.*;
import static org.junit.Assert.*;
import static org.virtual.files.common.Utils.*;
import static org.virtual.files.config.Configuration.*;
import static org.virtual.files.config.LocalConfiguration.*;

import org.junit.Test;
import org.virtual.files.config.Configuration;

public class ConfigurationTest {

	
	@Test
	public void configurationRoundTrips() {
		
		Configuration configuration = config()
				.mode(Configuration.Mode.development)
				.services(asList(
						
						local("src/test/resources")
						
						));     
		
		assertEquals(configuration,jsonRoundtrip(configuration));
	}
}
