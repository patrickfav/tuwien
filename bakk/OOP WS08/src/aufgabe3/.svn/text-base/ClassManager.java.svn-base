/**
 * 
 */
package aufgabe3;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

/**
 * 
 * saves and loads the specified data to/from a file
 *
 */
public class ClassManager implements Serializable{
	private static final long serialVersionUID = 1580739845248574744L;
	HashMap<String, Object> dump = new HashMap<String, Object>();
	private final String filePrefix="Data_";
	private final String filePostfix=".dat";
	private final String fileDir="data";
	
	/**
	 * addObjectToFile
	 * 
	 * adds the transfered object into a hashtable and saves the hashtable in a file
	 * @param name of the object
	 * @param object to be saved
	 * @return if the saving was successful
	 */
	public boolean addObjectToFile(String name, Object object){
		dump.put(name, object);
		return saveDataToFilesystem();
	}
	
	/**
	 * getObjectFromFile
	 * 
	 * retrieves the hashtable from the diskspace and returns the object that is identified by the transfered name 
	 * @param name for object that should be found
	 * @return found object
	 */
	@SuppressWarnings("unchecked")
	public Object getObjectFromFile(String name){
		Object o = null;
		
		String[] fileList;
		File dir = new File(fileDir);
		if (!dir.exists()){
			new File (fileDir).mkdirs();
		}
		fileList = dir.list(new FilenameFilterForDataFiles(filePostfix));
		Arrays.sort(fileList, Collections.reverseOrder());		
		if (fileList != null && fileList.length>0) {//falls es .table dateien im verzeichnis gibt
			ObjectInputStream is;
			try {
				is = new ObjectInputStream(new FileInputStream(fileDir+System.getProperty("file.separator")+fileList[0]));
				HashMap<String, Object> dump = (HashMap<String, Object>) is.readObject();
				o = dump.get(name);
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return o;
	}
	
	/**
	 * saveDataToFilesystem
	 * 
	 * saves the hashtable with the objects to the backupfile file
	 * @return boolean if it was successful
	 */
	private boolean saveDataToFilesystem(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
		ObjectOutputStream os;
		String s = fileDir+System.getProperty("file.separator")+filePrefix+sdf.format(new Date())+filePostfix;
		try {
			os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(s)));
			os.writeObject(dump);
			os.close();
			return true;
		} catch (Exception e) {
			System.err.println(e);
			return false;
		}
	}
}
