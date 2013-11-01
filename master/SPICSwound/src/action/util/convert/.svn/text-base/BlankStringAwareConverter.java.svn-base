package util.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * a very lightweight converter to prevent string entries which only 
 * consist of whitespaces as they are obviously not recognised by JSFs
 * "required"
 * 
 * @author inso
 *
 */
@Name("blankStringAwareConverter")
@Converter()
@BypassInterceptors
public class BlankStringAwareConverter implements javax.faces.convert.Converter {

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		if(StringUtils.isBlank(value))
			return null;
		
		return value;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
		if (value instanceof String) {
			String s = (String) value;
			return s;
		}
		
		return null;
	}

}
