package swag.utils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class MessageUtil {
   
    public static String loadMessage(FacesContext fc, String key) {
        ResourceBundle bundle;
        try{
            bundle = fc.getApplication().getResourceBundle(fc, "m");
            return bundle.getString(key);
        }catch (Exception e){
        	return "(invalid key)";
        }
       
    }
   
    public static String loadMessage(FacesContext context, String key, Object ...params){
        String message = loadMessage(context, key);
        MessageFormat mf = new MessageFormat(message, context.getViewRoot().getLocale());
        return mf.format(params, new StringBuffer(), null).toString();
    }
   
}