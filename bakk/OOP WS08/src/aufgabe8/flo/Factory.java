package aufgabe8.flo;

import java.util.ArrayList;
/**
 * main class which represents the factory
 * it manages the inventory and terminates the whole process if the production hasn't stopped
 * after the specific time.
 *
 */
public class Factory implements FactoryNotifyer, LoggingInterface {
	private ArrayList<InventoryListener> inventoryList;
	private int workingtime;
	
	
	
	public Factory(int workingtime) {
		this.inventoryList = new ArrayList<InventoryListener>();
		this.workingtime = workingtime;
	}

	/**
	 * starts the production of the registered inventory
	 */
	public void startProduction() {
		
		for (int i =0; i< inventoryList.size(); i++){
			InventoryListener inventory = inventoryList.get(i);
			inventory.startworking();
		}
		/**
		 * innerClass for controlling that the inventory stops working after the given time
		 */
		Thread workperiode = new Thread(new Runnable(){
			public void run() {
				try {
					/**
					 * waits 9/10 of the given time then asks gracefully for a stop
					 */
					Thread.sleep(workingtime/10*9);
					stopProduction();
					/**
					 * waits 1/10 of the given time and if then inventory is still working it demands an
					 * emergancy stop
					 */
					Thread.sleep(workingtime/10);
					if (inventoryList.size() >= 0) emergencyStop();
				}
				catch (InterruptedException e){
					
				}
			}			
		});
		workperiode.setName("ManagerThread_of_the_Factory");
		workperiode.start();
	}
	
	/**
	 * tells all registered inventory to stop the production graceful
	 */
	public void stopProduction() {
		for (int i =0; i< inventoryList.size(); i++){
			InventoryListener inventory = inventoryList.get(i);
			inventory.stopworking();
		}
	}
	
	/**
	 * demands all registered inventory to interrupt the production
	 */
	public void emergencyStop() {
		for (int i =0; i< inventoryList.size(); i++){
			InventoryListener inventory = inventoryList.get(i);
			inventory.eStop();
		}
	}
	
	/**
	 * adds the delivered inventory instance to the registered ones
	 */
	public void registerInventory(InventoryListener inventory) {
		inventoryList.add(inventory);
	}
	
	/**
	 * removes the delivered inventory instance to the registered ones
	 */
	public void derigisterInventory(InventoryListener inventory) {
		int i = inventoryList.indexOf(inventory);
		if (i >=0) inventoryList.remove(i);
	}
	
	/**
	 * checks if a normal message should be written out or if it is a debug message(if the instance
	 * isn't in debugmode then debug messages won't be written)
	 */
	public void write(boolean debug, String message) {
		String prefix = "Factory -"+this.toString()+"-> ";
		if(debug){
			if (debugmode) System.out.println(prefix + message);
		}
		else System.out.println(prefix + message);
	}
	
	public void writeCompleteStatus() {
	}
	
}