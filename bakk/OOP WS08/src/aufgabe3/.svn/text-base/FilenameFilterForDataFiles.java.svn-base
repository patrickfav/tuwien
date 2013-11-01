package aufgabe3;

import java.io.File;
import java.io.FilenameFilter;

public class FilenameFilterForDataFiles implements FilenameFilter{
	String extension;
	
	/**
	 * CONSTRUCTOR
	 * 
	 * Creates an Instanze of the class with the given extension on which the directory should be filtered
	 * @param extension - String which defines the filter criteria
	 */
	public FilenameFilterForDataFiles(String extension){
		this.extension=extension;
	}
	
	/**
	 * ACCEPT
	 * 
	 * returns only files with the given extension
	 * 
	 * @param dir - directory which should be filtered
	 * @param name - name is the extension is the string for the filter
	 */
	public boolean accept(File dir, String name) {
		return name.toLowerCase().endsWith( extension );
	}
}
