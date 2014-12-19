package org.acme;

import static java.util.Arrays.*;
import static org.junit.Assert.*;
import static org.virtual.files.common.Utils.*;
import static org.virtual.files.config.Configuration.*;
import static org.virtual.files.index.AssetInfo.*;
import static org.virtual.files.index.AssetIndex.*;
import static org.virtual.files.local.LocalConfiguration.*;

import java.util.HashSet;

import org.junit.Test;
import org.virtual.files.config.Configuration;
import org.virtual.files.index.AssetIndex;
import org.virtualrepository.csv.CsvAsset;

public class ConfigurationTest {

	
	@Test
	public void configurationRoundTrips() {
		
		Configuration configuration = config()
				.mode(Configuration.Mode.development)
				.services(asList(
						
						local("src/test/resources").add("prop", "val")
						
						));     
		
		assertEquals(configuration,jsonRoundtrip(configuration));
	}
	
	@Test
	public void assetsRoundTrip() {
		
		AssetIndex assets = index("someid")
				.assets(new HashSet<>(asList(
					
						info("file1", CsvAsset.type.name(), "/some/path/file1.txt"),
						info("file1", CsvAsset.type.name(), "/some/path/file2.txt")
				)));     
		
		assertEquals(assets,jsonRoundtrip(assets));
	}
}
