package dst3.dynload.io;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DirectoryIndex {
	private File directory;
	private Map<File,Long> fileMap;
	
	public DirectoryIndex(File directory) {
		super();
		
		if(!directory.isDirectory())
			throw new IllegalArgumentException(directory+ "is not a directory");
		
		this.directory = directory;
		this.fileMap = new HashMap<File,Long>();
	}
	
	
	public File getDirectory() {
		return directory;
	}
	public Map<File,Long> getFileMap() {
		return fileMap;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof DirectoryIndex)
			return directory.getAbsoluteFile().equals(((DirectoryIndex) o).getDirectory().getAbsoluteFile());
		
			
		return false;

	}
	
}
