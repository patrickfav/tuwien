package dst1.helper;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MathHelper {
	public static double round(BigDecimal d, int decimalPlace){
	    d = d.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
	    return d.doubleValue();
	}
	public static double round(double d, int decimalPlace){
	    BigDecimal bd = new BigDecimal(Double.toString(d));
	    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
	    return bd.doubleValue();
	 }
	public static String formatWithLeadingZeros(int value, int fillZeroUpToDigits) {
		
		StringBuffer s = new StringBuffer();
		
		for(int i=0;i<fillZeroUpToDigits;i++) {
			s.append("0");
		}
		
		DecimalFormat leadingZeroFormat = new DecimalFormat(s.toString());
		return leadingZeroFormat.format(new Integer(value));
	}
}
