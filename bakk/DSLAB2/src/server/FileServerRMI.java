package server;

import interfaces.IFileServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;

import error.FileServerFileNotFound;

public class FileServerRMI implements IFileServer{
	
	
	private static final long serialVersionUID = -8859278740756182639L;
	private String path;
	
	public FileServerRMI(String path) {
		this.path = path;
	}
	
	/**
	 * Gets the textfile as string
	 */
	@Override
	public String download(String path) throws RemoteException, FileServerFileNotFound{
		String returnS = new String();
		try{
			returnS = readFile(this.path+path);
		} catch(FileServerFileNotFound e) {
			throw new FileServerFileNotFound(this.path+path+ " file could not be found");
		}
		
		return returnS;
	}
	
	/**
	 * Reads a file with the given absolut path an returns it as a String
	 * @param path
	 * @return
	 * @throws FileServerFileNotFound
	 */
	private String readFile(String path) throws FileServerFileNotFound{
		File file = new File(path);
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;

        try
        {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            // repeat until all lines is read
            while ((text = reader.readLine()) != null){
                contents.append(text).append(System.getProperty("line.separator"));
            }
        } catch (FileNotFoundException e){
        	throw new FileServerFileNotFound(e.toString());
        } catch (IOException e){
        	throw new FileServerFileNotFound(e.toString());
        } finally{
            try {
                if (reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // show file contents here
        return contents.toString();
	}
}
