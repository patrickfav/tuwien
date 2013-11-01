package aufgabe3;

/**
 * 
 * Saves each five minutes all data to a file
 *
 */
public class BackupThread implements Runnable{
	private Thread t = null;
	private boolean runVariable = true;
	private String name;
	private Object o;
	
	/**
	 * CONSTRUCTOR
	 * 
	 * creates a BackupThread Instance with the transferred object to save
	 * 
	 * @param name - of object
	 * @param o - object to be saved
	 */
	public BackupThread(String name, Object o) {
		this.name = name;
		this.o=o;
		if ( t == null){
			t = new Thread(this);
		}
	}

	/**
	 * run
	 * 
	 * while the variable runVariable is true(can be set false with the stop methode) the BackupThread works with a pause of five minutes
	 * 
	 * adds the given object to the hashtable and saves it
	 */
	public synchronized void run() {
		ClassManager cm = new ClassManager();
		while (runVariable){
			cm.addObjectToFile(name, o);
			try {
				Thread.sleep(300000);
			} catch (InterruptedException e) {
			}
		}
	}
	
	/**
	 * start
	 * 
	 * starts the thread
	 */
	public void start(){
		t.start();
	}
	
	/**
	 * interrupt
	 * 
	 * interrupts the thread even if it is in the sleep cyle
	 */
	public void interrupt(){
	    if (t != null)                
	      t.interrupt();              
	  } 
	
	/**
	 * stop
	 * 
	 * signals the thread that it should stop
	 */
	public void stop(){
		runVariable = false;
	}

}
