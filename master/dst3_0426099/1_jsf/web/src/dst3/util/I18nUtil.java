package dst3.util;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class I18nUtil {
	
	private ResourceBundle rb;
	
	private static I18nUtil instance;
	
	public static I18nUtil getInstance(FacesContext fc) {
		if(instance == null)
			instance = new I18nUtil(fc);
		
		return instance;
	}
	
	private I18nUtil(FacesContext fc) {
		rb = fc.getApplication().getResourceBundle(fc, "m"); 
	}
	
	public String getString(String key) {
		try {
			return rb.getString(key);
		} catch(Exception e) {
			return "[Err] "+key+" could not be found in ResourceBundle";
		}
	}
}
