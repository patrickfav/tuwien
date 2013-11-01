/**
 * 
 */
package aufgabe5.georg;

/**
 * @author GEMEH
 *
 */
public class Horse extends Racer{
	int stamina;
	
	public Horse(int speed, int stamina){
		super(speed);
		this.stamina = stamina;
	}
	
	public int getStamina(){
		return stamina;
	}

	@Override
	public boolean faster(Racer that) {
		if(this.speed() > that.speed()) return true;
		return false;
	}

/*	@Override
	public boolean faster(Horse that) {
		if(this.speed() > that.speed()) return true;
		return false;
	}*/
}