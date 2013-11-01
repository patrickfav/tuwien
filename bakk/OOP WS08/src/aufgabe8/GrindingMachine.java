package aufgabe8;

public class GrindingMachine extends AbstractMachine implements InventoryListener, Runnable, LoggingInterface, SupplierListener, ConsumerListener{
	private Thread machineThread=null;
	private boolean debugmode = false;
	private SupplierNotifier supplier;
	private ConsumerNotifier consumer;
	private int processTime;
	private boolean working = false;
	//private Stick stick=null;
	private SmoothedStick smoothedStick = null;
	private boolean notAlreadyAskedForMaterial = true;
	private boolean notAlreadyInformedAboutReadiness = true;
	private int count;
	
	public GrindingMachine(boolean debugmode, SupplierNotifier supplier, ConsumerNotifier consumer, int processTime) {
		this.debugmode=debugmode;
		this.supplier=supplier;
		this.consumer=consumer;
		this.processTime = processTime;
		count =0;
	}

//**** Methodes for factory
	/**
	 * interrupts the machine Thread even if it is in a sleep cycle
	 */
	public void eStop() {
		write(false,"GrindingMachine performing e-Stop.");
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
		machineThread.setName("GrindingMachine-Thread_"+this.toString());
		machineThread.start();		
		write(false,"GrindingMachine started.");
	}

	/**
	 * tells the machine to stop gracefully
	 */
	public void stopworking() {
		write(true, "stopworking called");
		working = false;
		write(false,"GrindingMachine stopping.");
		write(false,"GrindingMachine has delivered " +count +" products.");
	}

//**** Methodes for thread
	public void run() {
		while(working){
			if(smoothedStick == null && notAlreadyAskedForMaterial){//if no endproduct is waiting for the delivery, make an endproduct
				consumer.registerConsumerListener(this);
				write(true, "registered at consumer "+consumer.toString());
				notAlreadyAskedForMaterial = false;
			}//if smoothedStick == null
			else if(smoothedStick != null && notAlreadyInformedAboutReadiness) {//endproduct availible for delivery
				supplier.registerSupplierListener(this);
				write(true, "registered at supplier "+supplier.toString());
				notAlreadyInformedAboutReadiness = false;
			}// smoothedStick != null
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
		String prefix = "GrindingMachine -"+this.toString()+"-> ";
		if(debug){
			if (debugmode) System.out.println(prefix + message);
		}
		else System.out.println(prefix + message);
	}


	public void writeCompleteStatus() {
		// TODO Auto-generated method stub
		
	}

//**** Methodes for supplier
	public void deliverStick() {
		write(true, "deregistered at supplier "+ supplier.toString());
		supplier.derigisterSupplierListener(this);
		write(true, "smoothedStick==null:"+ (smoothedStick==null));
		if (smoothedStick!=null) {
			write(true, "told supplier " + supplier.toString()
					+ " to collect Product " + smoothedStick.toString());
			supplier.collectProduct(smoothedStick);
			count++;
		}
		smoothedStick = null;
		notAlreadyAskedForMaterial = true;
		notAlreadyInformedAboutReadiness  = true;
	}

//**** Methodes for consumer
	public void collectProduct(Product product) {
		write(true, "deregistered at consumer "+ consumer.toString());
		consumer.derigisterConsumerListener(this);
		if(product instanceof Stick) {
			try {
				write(true, "working");
				Thread.sleep(processTime);
				write(true, "finished");
			} catch (InterruptedException e) {
				throw new FactoryException("GrindingMachine has been interrupted while processing" + e.toString());
			}

			smoothedStick = new SmoothedStick();
			write(true, "created smoothedStick "+ smoothedStick.toString());
		}
	}
}
