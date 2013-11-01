package aufgabe2;

//import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
//import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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
	 * CONSTRUCTOR
	 * Initializes the Object with a dump-data-variable
	 * @param dump
	 */
	public Units(byte[] dump) {
		this.loadUnits(dump);
	}
	
	/**
	 * EMPTY CONSTRUCTOR
	 */
	public Units() {
		
	}
	
	/**
	 * Adds a Basic Unit. Returns false if there was a problem adding the Unit 
	 * or the given key is null.
	 * 
	 * @param description
	 * @param symbol
	 * @param type
	 * @return
	 */
	public boolean addBaseUnit(String description, String symbol, String type) {
		Unit bunit = new Unit(description,symbol,type);
		
		baseunits.put(symbol,bunit);
		
		return true;
	}
	
	/**
	 * Adds a derived unit. For the parameters see DerivedUnit Class.
	 * Returns true if added successfully.
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
	 * Returns true if the key was found and deleted.
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
	 * Returns the whole Unit object.
	 * 
	 * @param sym
	 * @return
	 */
	public Unit getBaseUnitBySymbol(String sym) {
		Unit tempbnit = new Unit();
		tempbnit = baseunits.get(sym);
		
		return tempbnit;
	}
	
	/**
	 * Same as getBaseUnitSymbol() but for derived units.
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
	 * Needs the Symbol and the value and will return the converted BasicUnit value.
	 * If the symbol is the basicunit value or the symbol is unknown the untouched 
	 * value will be returned.
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
	 * Returns the string representation (=key) 
	 * for the Parameter whe a Unit symbol is given.
	 * If the symbol could not be found in the hashmaps, 
	 * an empty string will be returned
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
	 * Fetch Methods: Returns the symbols of base, derived or all Units
	 * @return
	 */
	public ArrayList<String> fetchBaseUnits(){
		ArrayList<String> blist = new ArrayList<String>(baseunits.keySet());
		return blist;
	}
	/**
	 * Fetch Method
	 * @return
	 */
	public ArrayList<String> fetchDerivedUnits(){
		ArrayList<String> dlist = new ArrayList<String>(derivedunits.keySet());
		return dlist;
	}
	/**
	 * Fetch Method
	 * @return
	 */
	public ArrayList<String> fetchAllUnits () {
		ArrayList<String> ulist = new ArrayList<String>();
		
		ulist.addAll(this.fetchBaseUnits());
		ulist.addAll(this.fetchDerivedUnits());
		
		return ulist;
	}
	
	/**
	 * Dumps/serializes the object into an byte-array
	 * @return
	 */
	public byte[] dumpUnits() {
		
		 try {
			// Serialize to a byte array
		    ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
		    ObjectOutput out = new ObjectOutputStream(bos) ;
		    out.writeObject(baseunits);
		    
		    // Get the bytes of the serialized object
		    byte dump[] = bos.toByteArray();
		        
		    return dump;
		 } catch (IOException e) {
		 }
		return null;	
	}
	
	/**
	 * Loads that dumped/serialized object
	 * @param dump
	 */
	public void loadUnits(byte dump[]) {
		/*try {
	        // Deserialize from a byte array
			ByteArrayInputStream arr_in= new ByteArrayInputStream(dump);
			ObjectInputStream in = new ObjectInputStream(arr_in);
	        HashMap baseunits2= (HashMap<String,Unit>) in.readObject();
	        in.close();
	       
		} catch (ClassNotFoundException e) {
	    } catch (IOException e) {
	    }*/
	}
}
