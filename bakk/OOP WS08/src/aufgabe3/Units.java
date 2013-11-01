package aufgabe3;

//import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * This is the management class for the Unit and DerivedUnit bin classes.
 * The object are saved in 2 seperate hashmaps to have easy seperated and combined access.
 * 
 * @author Patrick Favre-Bulle
 *
 */

class Units implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1562405859027611765L;
	private HashMap<String,Unit> baseunits = new HashMap<String,Unit>();
	private HashMap<String,DerivedUnit> derivedunits = new HashMap<String,DerivedUnit>();
	
	
	/**
	 * EMPTY CONSTRUCTOR
	 */
	public Units() {
		
	}
	
	/**
	 * Adds a Basic Unit. Given symbol must be unique or otherwise a already set BaseUnit
	 * will be overwritten. Parameter type must be a valid (set) parameter-name (see "Parameter).
	 * 
	 * @param description
	 * @param symbol
	 * @param type
	 * @return
	 */
	public boolean addBaseUnit(String description, String symbol, String type) {
		BaseUnit bunit = new BaseUnit(description,symbol,type);
		
		baseunits.put(symbol,bunit);
		
		return true;
	}
	
	/**
	 * Adds a derived unit. Given symbol must be unique or otherwise a already set BaseUnit
	 * will be overwritten. Parameter type must be a valid (set) parameter-name (see "Parameter).
	 * 
	 * @param description
	 * @param symbol
	 * @param type
	 * @param offset
	 * @param factor
	 * @param offsetfirst
	 * @return
	 */
	public boolean addDerivedUnit(String description, String symbol, String type, float offset,float factor, boolean offsetfirst) {
		DerivedUnit dunit = new DerivedUnit(description,symbol,type,offset,factor,offsetfirst);
		derivedunits.put(symbol,dunit);
		return true;
	}
	
	/**
	 * Deletes a Unit found by the main key "symbol".
	 * Sym must be a valid Units Symbol.
	 * 
	 * @param sym
	 * @return
	 */
	public boolean deleteUnit(String sym) {
		if(baseunits.remove(sym) != null)
			return true;
		
		if(derivedunits.remove(sym) != null)
			return true;
			
		return false;
	}
	
	/**
	 * Searches the BaseUnits HashMap for a certain symbol.
	 * Sym must be a valid Units Symbol.
	 * 
	 * @param sym
	 * @return
	 */
	public Unit getBaseUnitBySymbol(String sym) {
		Unit tempbnit = new BaseUnit();
		tempbnit = baseunits.get(sym);
		
		return tempbnit;
	}
	
	/**
	 * Same as getBaseUnitSymbol() but for derived units.
	 * Sym must be a valid Units Symbol.
	 * 
	 * @param sym
	 * @return
	 */
	public DerivedUnit getDerivedUnitBySymbol(String sym) {
		DerivedUnit tempdunit = new DerivedUnit();
		tempdunit = derivedunits.get(sym);
		
		return tempdunit;
	}
	/**
	 * Needs a valid units smybol and a the value.
	 * Will return the (in baseunit) converted value, if the symbol is from a derivedunit. 
	 * 
	 * @param sym
	 * @param value
	 * @return
	 */
	public float getBaseUnitValue(String sym ,float value) {
		DerivedUnit dunit = this.getDerivedUnitBySymbol(sym);
		
		if(dunit != null) {
			if(dunit.isOffsetfirst()) {
				value = (value + dunit.getOffset())*dunit.getFactor();
			}
			else {
				value = value * dunit.getFactor() + dunit.getOffset();
			}
		}
		
		return value;
	}
	
	/**
	 * Requests a valid units symbol. Will return the string representation of the connected
	 * parameter (= parameter name).
	 * 
	 * @param sym
	 * @return
	 */
	public String getParameterBySymbol(String sym) {
		DerivedUnit dunit = this.getDerivedUnitBySymbol(sym);
		Unit bunit = this.getBaseUnitBySymbol(sym);
		
		if(dunit != null) {
			return dunit.getPar_name();
		}
		else if (bunit != null) {
			return bunit.getPar_name();
		}
		else {
			return "";
		}
	}
	
	/**
	 * Fetch Method
	 * Returns the symbols of all registered baseunits as an ArrayList
	 * @return
	 */
	public ArrayList<String> fetchBaseUnits(){
		ArrayList<String> blist = new ArrayList<String>(baseunits.keySet());
		return blist;
	}
	/**
	 * Fetch Method
	 * Returns the symbols of all registered derivedunits as an ArrayList
	 * @return
	 */
	public ArrayList<String> fetchDerivedUnits(){
		ArrayList<String> dlist = new ArrayList<String>(derivedunits.keySet());
		return dlist;
	}
	/**
	 * Fetch Method
	 * Returns the symbols of all registered units as an ArrayList
	 * @return
	 */
	public ArrayList<String> fetchAllUnits () {
		ArrayList<String> ulist = new ArrayList<String>();
		
		ulist.addAll(this.fetchBaseUnits());
		ulist.addAll(this.fetchDerivedUnits());
		
		return ulist;
	}
	

}
