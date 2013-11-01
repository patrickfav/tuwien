package util.convert;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import util.MessageUtilsBean;

/**
 * Similar to the built in BigDecimal Converter, but takes care of localization
 * e.g. Locale.GERMAN results in a String representation like (3,4)
 * 
 * @author inso
 *
 */
@Name("BigDecimalLocaleConverter")
@Scope(ScopeType.STATELESS)
@org.jboss.seam.annotations.faces.Converter(forClass = BigDecimal.class)
@BypassInterceptors
public class BigDecimalLocaleConverter implements Converter {

	
	private static final char DOT = '.';
	
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {

		if (context == null || component == null)
			throw new NullPointerException("context or component");

		if (value == null)
			return null;
		
		if("".equals(value.trim()))	// treat an empty string like a null value
			return null;

		try {
			return convertToBigDecimal(context.getViewRoot().getLocale(), value);

		} catch (NumberFormatException nfe) {
			throw new ConverterException(MessageUtilsBean.formatErrorFacesMessage("error.bigdecimal"));
		}

	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException {

		if (context == null || component == null)
			throw new NullPointerException("context or component");

		if (value == null)
			return null;
		
		// play nice with strings
		if (value instanceof String) {
			return (String) value;	
		}
		
		if (value instanceof BigDecimal) {
			BigDecimal bd = (BigDecimal) value;
			return formatBigDecimal(context.getViewRoot().getLocale(), bd);
		} else {
			throw new ConverterException(MessageUtilsBean.formatErrorFacesMessage("error.nobigdecimal"));
		}

	}
	
	/*
	 * converts 4.3 to 4,3 (for Locale.GERMAN)
	 */
	public static String internationalize(final Locale locale, final String value) {
		if(locale.equals(Locale.ENGLISH))
			return value;
		
		BigDecimal bd = convertToBigDecimal(locale, value);
		return formatBigDecimal(locale, bd);
	}
	
	public String internationalize(final String value) {
		return BigDecimalLocaleConverter.internationalize(
				FacesContext.getCurrentInstance().getViewRoot().getLocale(), 
				value);
	}
	
	public static List<String> internationalizeList(final Locale locale, final List<String> values) {
		List<String> intern = new LinkedList<String>();
		for(String s : values) {
			if("".equals(s))
				intern.add(s);
			else
				intern.add(internationalize(locale, s));
		}
		return intern;
	}
	
	/*
	 * converts 4.3 to locale specifc str
	 */
	public static String uninternationalize(final Locale locale, final String value) {
		if(locale.equals(Locale.ENGLISH))
			return value;
		
		return formatBigDecimal(Locale.ENGLISH, convertToBigDecimal(locale, value));
	}
	
	public static List<String> uninternationalize(final Locale locale, final List<String> values) {
		List<String> intern = new LinkedList<String>();
		for(String s : values) {
			if("".equals(s))
				intern.add(s);
			else
				intern.add(uninternationalize(locale, s));
		}
		return intern;
	}
	
	public static String formatBigDecimal(final Locale locale, final BigDecimal value) {
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		nf.setGroupingUsed(false);
		nf.setMaximumFractionDigits(20);
		
		return nf.format(value);
	}

	public static BigDecimal convertToBigDecimal(final Locale locale, final String value) {
		DecimalFormatSymbols dfs = ((DecimalFormat) NumberFormat
				.getNumberInstance(locale)).getDecimalFormatSymbols();

		String toConvert = value;
		char decimalSep = dfs.getDecimalSeparator();
		if (!new Character(DOT).equals(decimalSep))
			toConvert = value.replace(decimalSep, DOT);

		BigDecimal result = new BigDecimal(toConvert);
		return result;
	}

}
