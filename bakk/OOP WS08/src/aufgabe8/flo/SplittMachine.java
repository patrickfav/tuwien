package aufgabe8.flo;

import java.util.ArrayList;

public class SplittMachine implements Machine, InventoryListener, Runnable, LoggingInterface, SupplierListener {
	private boolean debugmode = false;
	private ArrayList<Timber> pileofTimber;
	private SupplierNotifier supplier;
	private boolean working = false;
	private Thread machineThread=null;
	private Timber timber = null;
	private int processTime;
	private Stick stick = null;
	private boolean hasStickToDeliver = false;
	
	
	public SplittMachine(boolean debugmode, ArrayList<Timber> pileofTimber , SupplierNotifier supplier, int processTime) {
		this.debugmode=debugmode;
		this.pileofTimber=pileofTimber;
		this.supplier=supplier;
		this.processTime = processTime;
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
		working = true;
		machineThread = new Thread(this);
		machineThread.setName("SplittingMachine-Thread_"+this.toString());
		machineThread.start();
	}

	/**
	 * tells the machine to stop gracefully
	 */
	public void stopworking() {
		working = false;		
	}
	
	/**
	 * returns one timber out of the pile of timber
	 */
	private Timber getTimberFromPile(){
		Timber t= pileofTimber.get(0);
		pileofTimber.remove(0);
		return t;
	}

	
//**** Methodes for thread
	public void run() {
		
		
		while (working){
			if (timber == null){
				if (pileofTimber.size() >0){
					timber = getTimberFromPile();
					write(true, "got Timber " + timber.toString()+ " from Pile.");
				}
			}
			
				if (timber != null) {
					while ((timber.getVolume() > 0) && !hasStickToDeliver) {
						timber.getStick();
						write(true, "extracting stick");
						try {
							write(true, "working");
							Thread.sleep(processTime);
							write(true, "finished");
						} catch (InterruptedException e) {
							throw new FactoryException("SplittMachine has been interrupted while processing" + e.toString());
						}
						write(true, "stick extracted.");
						stick = new Stick();
						write(true, "got Stick " + stick.toString()
								+ " from timber " + timber.toString());
						supplier.registerSupplierListener(this);
						hasStickToDeliver = true;
					}//stick
					if (timber.getVolume() == 0)
						timber = null;
				}
			
		}
		if (supplier != null) supplier.derigisterSupplierListener(this);
		/*while(working){
			if (stick == null) {
				if (pileofTimber.size() > 0) {
					timber = getTimberFromPile();
					write(true, "got Timber " + timber.toString()
							+ " from Pile.");
					while (timber.getStick()) {
						write(true, "extracting stick");
						try {
							Thread.sleep(processTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						write(true, "stick extracted.");
						stick = new Stick();
						write(true, "got Stick " + stick.toString()
								+ " from timber " + timber.toString());
						supplier.registerSupplierListener(this);
					}//stick
				}//pileOfTimber
			}//waitingToDeliver
		}//working
*/		
	}

//**** Methodes for logging	
	/**
	 * checks if a normal message should be written out or if it is a debug message(if the instance
	 * isn't in debugmode then debug messages won't be written)
	 */
	public void write(boolean debug, String message) {
		String prefix = "SplittMachine -"+this.toString()+"-> ";
		if(debug){
			if (debugmode) System.out.println(prefix + message);
		}
		else System.out.println(prefix + message);
		
	}


	public void writeCompleteStatus() {
		// TODO Auto-generated method stub
		
	}

//**** Methodes for supplier	
	/**
	 * delivers Stick to container
	 */
	public void deliverStick() {
		write(true, "stick==null:"+(stick == null));
		write(true, "gave " +stick.toString() +" to supplier "+ supplier.toString());
		supplier.derigisterSupplierListener(this);
		supplier.collectProduct(stick);
		hasStickToDeliver = false;
		stick = null;		
	}

}
