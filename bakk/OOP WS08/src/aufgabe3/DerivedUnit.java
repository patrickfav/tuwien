package aufgabe3;

/**
 * Inherited from the Unit class, this class saves non-baseunits, so called derived units.
 * Derived units have a factor and offset value which "describes" the way to convert it
 * to a baseunit value.
 * 
 * @author Patrick Favre-Bulle
 *
 */
class DerivedUnit extends Unit {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4452144712345850125L;
	private float offset;
	private float factor;
	private boolean offsetfirst;
	
	/**
	 * CONSTRUCTOR
	 * 
	 * @param description
	 * @param symbol
	 * @param par_name
	 * @param offset
	 * @param factor
	 * @param offsetfirst
	 */
	public DerivedUnit(String description, String symbol, String par_name,float offset, float factor, boolean offsetfirst) {
		super(description, symbol, par_name);
		this.offset = offset;
		this.factor = factor;
		this.offsetfirst = offsetfirst;
	}
	
	/**
	 * EMPTY CONSTRUCTOR
	 */
	public DerivedUnit() {
		super();
	}
	
	/*
	 * GETTER AND SETTER METHODS
	 */
	public float getOffset() {
		return offset;
	}
	public void setOffset(float offset) {
		this.offset = offset;
	}
	public float getFactor() {
		return factor;
	}
	public void setFactor(float factor) {
		this.factor = factor;
	}
	public boolean isOffsetfirst() {
		return offsetfirst;
	}
	public void setOffsetfirst(boolean offsetfirst) {
		this.offsetfirst = offsetfirst;
	}
}
