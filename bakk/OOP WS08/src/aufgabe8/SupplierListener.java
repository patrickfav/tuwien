package aufgabe8;
/**
 * Interface which describes what methodes the observable can call from its observers
 *
 */
public interface SupplierListener {
	/**
	 * tells the Listener that it should deliver its product
	 * 
	 */
	public void deliverStick();
}
