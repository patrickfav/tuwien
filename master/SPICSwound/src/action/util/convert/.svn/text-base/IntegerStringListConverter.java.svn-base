package util.convert;

import java.util.LinkedList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.IntegerConverter;

import org.jboss.seam.annotations.intercept.BypassInterceptors;

import util.MessageUtilsBean;

/**
 * A converter that converts a multiline (delimited by \n) into a list of 
 * string integer values and does type checking
 * (e.g. "1\n2\n3" -> {"1", "2", "3"}
 *   
 * @author inso
 *
 */
@org.jboss.seam.annotations.faces.Converter
@BypassInterceptors
public class IntegerStringListConverter extends StringListConverter implements Converter{

	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext fc, UIComponent comp, String str) throws ConverterException {
		
		try {
			List<String> strList = (List<String>)super.getAsObject(fc, comp, str);
		
			if(strList == null)
				return null;
			
			List<String> checkedList = new LinkedList<String>();
			IntegerConverter intconv = new IntegerConverter();
			
			for(String s : strList) {
				Integer i = ((Integer)intconv.getAsObject(fc, comp, s));
				if(i != null)
					checkedList.add(i.toString());

			}
			
			return checkedList.isEmpty() ? null : checkedList;
		} catch (ConverterException e) {
			throw new ConverterException(MessageUtilsBean.formatErrorFacesMessage("javax.faces.converter.IntegerConverter.INTEGER"));
		}
	}

	@SuppressWarnings("unchecked")
	public String getAsString(FacesContext fc, UIComponent comp, Object o) {
		if (o instanceof List) {
			List<String> lst = (List<String>) o;
			return super.listToNewlineDelimitedString(lst);
		}
		throw new ConverterException("Illegal type passed: " + o.getClass());
	}
	

}
