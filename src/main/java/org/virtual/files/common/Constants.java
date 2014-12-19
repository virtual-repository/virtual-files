package org.virtual.files.common;

import static java.time.format.DateTimeFormatter.*;

import java.time.LocalDateTime;

import org.virtualrepository.Property;



public class Constants {
	
	public static final String config_property = "vfiles.config";
	public static final String config_filename = "vfiles.json";
	
	public static final String type_discriminant_property = "type";
	public static final String index_file_name = "vindex.json";
	
	public static final String index_entry_property = "vfile_info";
	
	
	public static final String file_path_hint ="vfile_path";
	public static final String created_property ="vfile_ created";
	public static final String modified_property ="vfile_lastModified";
	
	public static final String file_default_extension ="asset";
	
	public static final String no_overwrite_hint ="vfile_no_overwrite";
	public static final String file_extension_hint ="vfile_extension";
	
	
	public static Property now(String name) {
		return new Property(name, LocalDateTime.now().format(ISO_DATE_TIME));
	}
	
}
