package util.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * using richfaces calendar now
 * @author inso
 *
 */
@Deprecated
@BypassInterceptors
public class SeamInputDateConverter implements Converter {

	

	public Object getAsObject(FacesContext fc, UIComponent ui, String str)
			throws ConverterException {
		
		ResourceBundle rb = ResourceBundle.getBundle("messages",fc.getViewRoot().getLocale(),Thread.currentThread().getContextClassLoader());
		try {
	//		System.out.println("SeamInputDateConverter: trying to convert value " + str);
			
			String dTypeDF = (String)ui.getAttributes().get("datatype.dateformat");
			
			if("".equals(str))
				return null;
			
			if(dTypeDF.equals(str)) {	// this was just the placeholderstring - we don't want that
				return null;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat((String)ui.getAttributes().get("dateformat"));
			sdf.setLenient(false);
			return sdf.parse(str);
		} catch (ParseException e) {
			// TODO internationalization
			
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error.datatype")+": ", rb.getString("error.date1")+ str + " " + rb.getString("error.date2")));
		}
	}

	public String getAsString(FacesContext fc, UIComponent ui, Object o)
			throws ConverterException {
		
		if(o == null)
			return "";
		
		if (o instanceof Date) {
			Date d = (Date) o;
			SimpleDateFormat sdf = new SimpleDateFormat((String)ui.getAttributes().get("dateformat"));
			sdf.setLenient(false);
			return sdf.format(d);
		} else {
			throw new IllegalArgumentException("The SeamInputDateConverter can only be invoced on objects of type Date");
		}
	}

}
