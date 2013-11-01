package example23;

import fit.ColumnFixture;

/**
 * @author Favre-Bulle, Patrick
 * @matNum 0426099
 * @exercise Exercise 2-3
 * 
 */
public class DoubleFixture extends ColumnFixture{
	
	public String string2parse;
	
	public double result() {
		return Double.parseDouble(string2parse);
	}
}
