package aufgabe8.flo;
/**
 * This interface tells the oberservable which mehtodes it can call from its listeners
 *
 */
public interface ConsumerListener {
	/**
	 * collects delivered stick from observable
	 * @param stick
	 */
	public void collectProduct(Product product);
}
