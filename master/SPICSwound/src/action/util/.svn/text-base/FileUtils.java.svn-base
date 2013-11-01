package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.MimetypesFileTypeMap;

public class FileUtils {
	
	private static FileUtils _instance;
	
	private MimetypesFileTypeMap mimeMap;
	
	private FileUtils() {
		mimeMap = new MimetypesFileTypeMap();
	}
	
    // Returns the contents of the file in a byte array.
    public byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        	return null;	// should not happen!
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
    
    public String getContentType(File file) {
    	return mimeMap.getContentType(file);
    }
    
    public String getContentType(String filename) {
    	return mimeMap.getContentType(filename);
    }
    
    public static FileUtils instance() {
    	if(_instance == null)
    		_instance = new FileUtils();
    	
    	return _instance;
    }

}
