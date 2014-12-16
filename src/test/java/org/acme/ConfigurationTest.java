package org.acme;

import static java.util.Arrays.*;
import static org.junit.Assert.*;
import static org.virtual.files.AssetIndex.*;
import static org.virtual.files.AssetEntry.*;
import static org.virtual.files.common.Utils.*;
import static org.virtual.files.config.Configuration.*;
import static org.virtual.files.config.LocalConfiguration.*;

import java.util.HashSet;

import javax.xml.namespace.QName;

import org.junit.Test;
import org.virtual.files.AssetIndex;
import org.virtual.files.config.Configuration;
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
					
						asset(new QName("file1"), CsvAsset.type.name(), "/some/path/file1.txt"),
						asset(new QName("file1"), CsvAsset.type.name(), "/some/path/file2.txt")
				)));     
		
		assertEquals(assets,jsonRoundtrip(assets));
	}
}
