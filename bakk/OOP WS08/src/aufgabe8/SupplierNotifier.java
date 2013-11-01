package aufgabe8;

/**
 * Interface which represents the methodes which defines the methodes
 * of the Observalbe instance that are known by the observers
 *
 */
public interface SupplierNotifier {
	/**
	 * registeres the Listener
	 * @param listener
	 */
	public void registerSupplierListener(SupplierListener listener);
	/**
	 * derigisteres the Listener
	 * @param listener
	 */
	public void derigisterSupplierListener(SupplierListener listener);
	/**
	 * collects Stick from Listener
	 * @param stick
	 */
	public boolean collectProduct(Product product);
}
