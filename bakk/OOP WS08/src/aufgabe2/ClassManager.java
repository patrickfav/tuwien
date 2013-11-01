/**
 * 
 */
package aufgabe2;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 * 
 * saves and loads the specified data to/from a file
 *
 */
public class ClassManager implements Serializable{
	private static final long serialVersionUID = 1580739845248574744L;
	HashMap<String, Object> dump = new HashMap<String, Object>();
	
	/**
	 * addObjectToFile
	 * 
	 * adds the transfered object into a hashtable and saves the hashtable in a file
	 * @param name
	 * @param object
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
	 * @param name
	 * @return found object
	 */
	@SuppressWarnings("unchecked")
	public Object getObjectFromFile(String name){
		Object o = null;
		File tmp = new File("Data.dump");
		if (tmp.canRead()){
			ObjectInputStream is;
			try {
				is = new ObjectInputStream(new FileInputStream(tmp));
				HashMap<String, Object> dump = (HashMap<String, Object>) is.readObject();
				o = dump.get(name);
				is.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		else{System.out.println("Can't read file");}
		return o;
	}
	
	/**
	 * saveDataToFilesystem
	 * 
	 * saves the hashtable with the objects to the file
	 * @return boolean if it was successful
	 */
	private boolean saveDataToFilesystem(){
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Data.dump")));
			os.writeObject(dump);
			os.close();
			return true;
		} catch (Exception e) {
			System.err.println(e);
			return false;
		}
	}
}
