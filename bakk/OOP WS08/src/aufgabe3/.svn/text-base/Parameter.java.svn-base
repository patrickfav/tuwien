package aufgabe3;

import java.io.Serializable;

/**
 * Saves a Parameter. The main key is the name. Ohter values are stored eg. min/max/threshold value
 * (which are in context with the baseunit value). Also stores the symbol string for the baseunit, 
 * helps finding the unit object. Values are only saved in the defined baseunit to keep integrity.
 * 
 * @author Patrick Favre-Bulle
 *
 */

public class Parameter implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3116612625284237239L;
	private String name;
	private float max_value;
	private float min_value;
	private float threshold;
	private String unit_sym;
	
	
	/**
	 * CONSTRUCTOR
	 * 
	 * @param name
	 * @param max_value
	 * @param min_value
	 * @param threshold
	 * @param sym
	 */
	public Parameter(String name, float max_value, float min_value,
			float threshold, String sym) {
		this.name = name;
		this.max_value = max_value;
		this.min_value = min_value;
		this.threshold = threshold;
		this.unit_sym = sym;
	}
	
	/*
	 * GETTER AND SETTER METHODS
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public float getMax_value() {
		return max_value;
	}
	public void setMax_value(float max_value) {
		this.max_value = max_value;
	}
	
	public float getMin_value() {
		return min_value;
	}
	public void setMin_value(float min_value) {
		this.min_value = min_value;
	}
	
	public float getThreshold() {
		return threshold;
	}
	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}
	public String getUnitSymbol() {
		return unit_sym;
	}
	public void setUnit(String unit) {
		this.unit_sym = unit;
	}
	
	
	
	
}