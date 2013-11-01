package aufgabe5.commented;

/**
 * @author GEMEH, FLRAU, PAFAV
 * 
 * Creates a Racer
 * Implements the interface Comparator to compare Racers
 * 
 */
public abstract class Racer implements Comparator<Racer> {
	protected int speed;	
	
	public Racer(int speed){
		// creates a racer with speed
		this.speed = speed;
	}
	
	public int speed(){
		// returns speed
		return speed;
	}

}