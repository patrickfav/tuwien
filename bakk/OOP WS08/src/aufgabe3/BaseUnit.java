package aufgabe3;

class BaseUnit extends Unit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1055085101700209327L;
	
	/**
	 * CONSTRUCTOR
	 * 
	 * @param description
	 * @param symbol
	 * @param par_name
	 */
	public BaseUnit(String description, String symbol, String par_name) {
		super(description, symbol, par_name);
	}
	
	/**
	 * EMPTY CONSTRUCTOR
	 */
	
	public BaseUnit() {
		super();
	}
}
