package aufgabe8.flo;

import java.util.ArrayList;
import java.util.Random;

public class Container implements SupplierNotifier, ConsumerNotifier, InventoryListener, LoggingInterface {
	private int count;
	private ArrayList<Product> content;
	private ArrayList<SupplierListener> suppliers;
	private ArrayList<ConsumerListener> consumers;
	private final int capacity;
	private int actualvolume;
	private Thread collectorThread;
	private Thread supplierThread;
	private boolean working = false;
	private boolean debugmode = false;
	
	public Container(boolean debugmode, int capacity) {
		count = 0;
		this.capacity = capacity;
		this.suppliers = new ArrayList<SupplierListener>();
		this.consumers = new ArrayList<ConsumerListener>();
		this.content = new ArrayList<Product>();
		actualvolume = 0;
		this.debugmode=debugmode;
	}
	
	/**
	 * adds the delivered supplierListener instance to the registered ones
	 */
	public synchronized void registerSupplierListener(SupplierListener listener) {
		write(true, "the Supplier "+ listener.toString() +" has registered itself");
		suppliers.add(listener);
	}
	
	/**
	 * removes the delivered supplierListener instance to the registered ones
	 */
	public synchronized void derigisterSupplierListener(SupplierListener listener) {
		int i = suppliers.indexOf(listener);
		if (i >=0) {
			write(true, "the Supplier "+ listener.toString() +" has derigistered itself");
			suppliers.remove(i);
		}
	}
	
	/**
	 * adds stick to container
	 */
	public boolean collectProduct(Product product) {
		if (actualvolume < capacity /*&& !suppliers.isEmpty()*/) {
			write(true, "is product null:"+(product == null));
			content.add(product);
			count++;
			actualvolume++;
			write(true, "added stick " + product.toString()
					+ " to container. Acutal volume " + actualvolume
					+ ". Count is " + count);
			return true;
		}
		else return false;
	}
	
	/**
	 * adds the delivered ConsumerListener instance to the registered ones
	 */
	public synchronized void registerConsumerListener(ConsumerListener listener) {
		write(true, "adding Listener " +listener.toString() +"to container.");
		consumers.add(listener);
	}
	
	/**
	 * removes the delivered ConsumerListener instance to the registered ones
	 */
	public synchronized void derigisterConsumerListener(ConsumerListener listener) {
		int i = consumers.indexOf(listener);
		if (i >=0) {
			write(true, "removing Listener " +listener.toString());
			consumers.remove(i);
		}
	}
	
	/**
	 * starts the collector thread and the supplier thread for an instance of container
	 * while the variable working = true these threads ask for products until the full 
	 * capacity isn't reached and they try to provide the products to registered consumers
	 * till the actualvolume is 0 
	 */
	public void startworking() {
		write(true, "startworking has been called.");
		working = true;
		collectorThread = new Thread(new Runnable(){

			public void run() {
				write(true, "collectorThread started.");
				while(working){
					write(true, "actualvolume=" +actualvolume +" capacity="+capacity);
					while(actualvolume < capacity){
						if (suppliers.size() > 0){
							Random r = new Random();
							int i = Math.round(r.nextInt(suppliers.size()));
							write(true, "selected the item nr " +i +" to provide the product.");
							SupplierListener randSupplier = suppliers.get(i);
							randSupplier.deliverStick();
							write(true, "asked " + randSupplier.toString() +" for it's product.");
						}
						if (!working) break;
					}
				}
				write(true, "collectorThread ended.");
			}
			
		});
		supplierThread = new Thread(new Runnable(){

			public void run() {
				write(true, "supplierThread started.");
				while(working){
					while(actualvolume >0){
						if (consumers.size() > 0) {
							Random r = new Random();
							int i = Math.round(r.nextInt(consumers.size()));
							ConsumerListener randConsumer = consumers.get(i);
							randConsumer.collectProduct(content.get(0));
							write(true, "delivered  " + randConsumer.toString()	+ " the product "+ content.get(0).toString() + ".");
							content.remove(0);
							actualvolume--;
						}
						if (!working) break;
					}					
				}
				write(true, "supplierThread ended.");
				
			}
			
		});
		collectorThread.setName(this.toString()+".collectorThread");
		supplierThread.setName(this.toString()+".supplierThread");
		collectorThread.start();
		supplierThread.start();
	}
	
	/**
	 * signals the collector and the supplier threads to stop by setting there run variable to false
	 */
	public void stopworking() {
		write(true, "stopworking has been called.");
		working = false;
	}
	
	/**
	 * interrupts the collector and the supplier Threads even if they are in a sleep cycle
	 */
	public void eStop() {
		stopworking();
		write(true, "both Threads have been interrupted.");
	}
	
	/**
	 * checks if a normal message should be written out or if it is a debug message(if the instance
	 * isn't in debugmode then debug messages won't be written)
	 */
	public void write(boolean debug, String message) {
		String prefix = "Container -"+this.toString()+"-> ";
		if(debug){
			if (debugmode) System.out.println(prefix + message);
		}
		else System.out.println(prefix + message);
	}
	
	public void writeCompleteStatus() {
	}
	
	public int getCount() {
		return count;
	}


}
