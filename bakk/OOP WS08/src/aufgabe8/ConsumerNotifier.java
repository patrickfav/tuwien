package aufgabe8;

/**
 * Interface which describes the methodes of the Observable to the observers
 *
 */
public interface ConsumerNotifier{
	/**
	 * registeres Listener
	 * @param listener
	 */
	public void registerConsumerListener(ConsumerListener listener);
	/**
	 * derigisteres Listener
	 * @param listener
	 */
	public void derigisterConsumerListener(ConsumerListener listener);
}
