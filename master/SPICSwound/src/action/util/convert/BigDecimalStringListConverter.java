package util.convert;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.jboss.seam.annotations.intercept.BypassInterceptors;

import util.MessageUtilsBean;

/**
 * A converter that converts a String into an internationalized 
 * String List of BigDecimal values and back. 
 * 
 * @author inso
 *
 */
@org.jboss.seam.annotations.faces.Converter
@BypassInterceptors
public class BigDecimalStringListConverter extends StringListConverter
		implements Converter {
	
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext fc, UIComponent comp, String str) {
		try {
		List<String> strList = (List<String>) super.getAsObject(fc, comp, str);

		if(strList == null)
			return null;
		

		return BigDecimalLocaleConverter.uninternationalize(fc
					.getViewRoot().getLocale(), strList);
		
		} catch (Exception e) {
			throw new ConverterException(MessageUtilsBean.formatErrorFacesMessage("error.nobigdecimal"));
		}
	}

	@SuppressWarnings("unchecked")
	public String getAsString(FacesContext fc, UIComponent comp, Object o) {
		if (o instanceof List) {
			List<String> bdList = (List<String>) o;

			return super
					.listToNewlineDelimitedString(BigDecimalLocaleConverter
							.internationalizeList(fc.getViewRoot().getLocale(),
									bdList));

		}
		throw new ConverterException(
				"can only convert Lists of BigDecimal values");
	}

}
