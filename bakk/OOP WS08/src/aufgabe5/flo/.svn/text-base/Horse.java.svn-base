package aufgabe5.flo;

/**
 * @author GEMEH, FLRAU, PAFAV
 * 
 * Horse is a type of Racer
 * 
 */
public class Horse extends Racer {
	int stamina;
	
	public Horse(int speed, int stamina){
		// creates a racer with speed and stamina
		super(speed);
		this.stamina = stamina;
	}
	
	public int getStamina(){
		// returns stamina
		return stamina;
	}
	
	public boolean faster(Racer that) {
		// expects a racer
		if(this.speed() > that.speed()) return true;
		return false;
		// true if this is faster than that
	}
}