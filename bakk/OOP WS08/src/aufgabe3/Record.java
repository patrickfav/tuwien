package aufgabe3;

import java.io.Serializable;

public class Record implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7828303915138845232L;
	private float value;
	private Parameter parameter;
		
	/**
	 * Constructor
	 * @param value
	 * @param parameter
	 */
	public Record(float value, Parameter parameter) {
		this.value = value;
		this.parameter = parameter;
	}
	/**
	 * Returns value of record
	 * @return
	 */
	public float getValue() {
		return value;
	}
	/**
	 * Setter to change the value
	 * @param value
	 */
	public void setValue(float value) {
		this.value = value;
	}
	/**
	 * Returns the Parameter Object
	 * @return
	 */
	public Parameter getParameter() {
		return parameter;
	}
	/**
	 * Setter to change the parameter
	 * @param parameter
	 */
	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}
}
