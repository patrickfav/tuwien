package util.convert;

import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.jboss.seam.annotations.intercept.BypassInterceptors;

import util.MessageUtilsBean;

/**
 * A Converter that converts a multiline string delimited by "\n" and converts
 * it into a List of one line strings. Blank lines by the definition of
 * org.apache.commons.lang.StringUtils.isBlank(String) are eliminated
 * @author inso
 *
 */
@org.jboss.seam.annotations.faces.Converter
@BypassInterceptors
public class StringListConverter extends BlankStringAwareConverter implements
		Converter {
	
	public static final String MAX_WIDTH_ATTRIBUTE_KEY = "converterMaxWidth";

	public Object getAsObject(FacesContext fc, UIComponent comp, String str) {

		if (fc == null || comp == null)
			throw new NullPointerException("context or component");

		String[] values = str.split("\n");
		List<String> strList = new LinkedList<String>();
		
		Integer maxLength = (Integer)comp.getAttributes().get(MAX_WIDTH_ATTRIBUTE_KEY);
		
		if(maxLength == null)
			maxLength = Integer.MAX_VALUE;

		for (String s : values) {
			if (super.getAsObject(fc, comp, s) == null)
				continue;
			
			if(s.trim().length() > maxLength)
				throw new ConverterException(MessageUtilsBean.formatErrorFacesMessage("error.texttoolong", "", maxLength));

			strList.add(s.trim());
		}

		return strList.isEmpty() ? null : strList;
	}

	@SuppressWarnings("unchecked")
	public String getAsString(FacesContext fc, UIComponent comp, Object strList) {

		if (fc == null || comp == null)
			throw new NullPointerException("context or component");

		if (strList == null)
			return null;

		if (strList instanceof List) {
			return listToNewlineDelimitedString((List) strList);
		} else {
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Can only handle String lists!"));
		}

	}

	protected String listToNewlineDelimitedString(List<String> list) {
		StringBuffer sb = new StringBuffer();

		int i = 0;
		for (String s : list) {
			i++;
			sb.append(s);
			if (i < list.size())
				sb.append("\n");
		}

		return sb.length() == 0 ? null : sb.toString();
	}
}
