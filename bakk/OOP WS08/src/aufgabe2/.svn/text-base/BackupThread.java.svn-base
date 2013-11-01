package aufgabe2;

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
	 * @param name - of object
	 * @param o - object
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
	 * defines what the thread should do
	 */
	public void run() {
		ClassManager cm = new ClassManager();
		while (runVariable){
			cm.addObjectToFile(name, o);
			try {
				//Thread.sleep(3000);
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
