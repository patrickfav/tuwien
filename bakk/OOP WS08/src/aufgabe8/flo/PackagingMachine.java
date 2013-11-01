package aufgabe8.flo;

public class PackagingMachine extends AbstractMachine implements InventoryListener, Runnable, LoggingInterface, SupplierListener, ConsumerListener{
	private Thread machineThread=null;
	private boolean debugmode = false;
	private ConsumerNotifier consumer;
	private SupplierNotifier supplier;
	private int processTime;
	private boolean working = false;
	private SmoothedStick smoothedStick = null;
	private Package pack = null;
	private boolean alreadyAskedForSmoothedStick = false;
	private boolean notAlreadyInformedAboutReadiness = true;
	
	public PackagingMachine(boolean debugmode, SupplierNotifier supplier, ConsumerNotifier consumer, int processTime) {
		this.debugmode=debugmode;
		this.consumer=consumer;
		this.processTime = processTime;
		this.supplier=supplier;
	}

//**** Methodes for factory
	/**
	 * interrupts the machine Thread even if it is in a sleep cycle
	 */
	public void eStop() {
		stopworking();
		if (machineThread != null) machineThread.interrupt();	
	}

	/**
	 * sets the controlervarible of the tread to true and starts the thread
	 */
	public void startworking() {
		write(true, "startworking called");
		working = true;
		machineThread = new Thread(this);
		machineThread.setName("PackingMachine-Thread_"+this.toString());
		machineThread.start();
	}

	/**
	 * tells the machine to stop gracefully
	 */
	public void stopworking() {
		write(true, "stopworking called");
		working = false;
	}

//**** Methodes for thread
	public void run() {
		while(working){
			if (pack == null){//if no package is availible right now
				pack = new Package();
				write(true, "new Package " + pack.toString() + " has been created");
			}
			if(pack.getVolumeOfPackage() < 2 && !alreadyAskedForSmoothedStick && notAlreadyInformedAboutReadiness){//if the volume of the package is < 2 or if a smoothed stick hasn't been already requested
			consumer.registerConsumerListener(this);
			write(true, "registered at consumer "+consumer.toString());
			alreadyAskedForSmoothedStick = true;
			}//
			else if(pack.getVolumeOfPackage() == 2 && notAlreadyInformedAboutReadiness){// if the package contains two smoothed Sticks
				supplier.registerSupplierListener(this);
				write(true, "registered at supplier "+supplier.toString());
				notAlreadyInformedAboutReadiness = false;
			}			
		}//working
		if (supplier != null) supplier.derigisterSupplierListener(this);
		if (consumer != null) consumer.derigisterConsumerListener(this);
	}

//**** Methodes for logging
	/**
	 * checks if a normal message should be written out or if it is a debug message(if the instance
	 * isn't in debugmode then debug messages won't be written)
	 */
	public void write(boolean debug, String message) {
		String prefix = "PackagingMachine -"+this.toString()+"-> ";
		if(debug){
			if (debugmode) System.out.println(prefix + message);
		}
		else System.out.println(prefix + message);
	}


	public void writeCompleteStatus() {
		// TODO Auto-generated method stub
		
	}

//**** Methodes for consumer
	public void collectProduct(Product stick) {
		if(stick instanceof SmoothedStick){//if it's a smoothed stick
			write(true, "deregistered at consumer "+ consumer.toString());
			consumer.derigisterConsumerListener(this);
			smoothedStick = (SmoothedStick) stick;
			try {
				write(true, "working");
				Thread.sleep(processTime);
				write(true, "finished");
			} catch (InterruptedException e) {
				throw new FactoryException("PackagingMachine has been interrupted while processing" + e.toString());
			}
			write(true, "added  smoothedStick "+ smoothedStick.toString() + " to pack "+ pack.toString());
			pack.addSmoothedStick(smoothedStick);
			smoothedStick = null;
			alreadyAskedForSmoothedStick = false;
		}
		else throw new FactoryException("Packageing Error: wanted Smoothed Stick" 
				+" but got "+ this.toString() +" which has another type");
	}

//**** Methodes for supplier
	public void deliverStick() {
		write(true, "deregistered at supplier "+ supplier.toString());
		supplier.derigisterSupplierListener(this);
		supplier.collectProduct(pack);
		write(true, "told supplier "+ supplier.toString()+ " to collect Product " +pack.toString());
		pack = null;
		notAlreadyInformedAboutReadiness  = true;
	}
}