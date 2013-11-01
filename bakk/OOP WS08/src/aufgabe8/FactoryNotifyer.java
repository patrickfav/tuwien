package aufgabe8;

/**
 * Interface for the observers
 * lets them know how to derigister and how to register at the observable instance
 *
 */
public interface FactoryNotifyer {
	/**
	 * Registers the inventory
	 * @param inventory
	 */
	public void registerInventory(InventoryListener inventory);
	/**
	 * Derigisteres the inventory
	 * @param inventory
	 */
	public void derigisterInventory(InventoryListener inventory);
}
