package aufgabe2;

import java.io.File;
import java.io.FilenameFilter;

public class FilenameFilerForDataFiles implements FilenameFilter{
	String extension;
	
	public FilenameFilerForDataFiles(String extension){
		this.extension=extension;
	}

	public boolean accept(File dir, String name) {
		return name.toLowerCase().endsWith( extension );
	}
}
