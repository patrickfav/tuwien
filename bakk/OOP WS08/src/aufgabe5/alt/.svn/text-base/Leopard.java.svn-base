/**
 * 
 */
package aufgabe5.alt;

/**
 * @author GEMEH
 *
 */
public class Leopard extends Racer /*implements Comperator<Horse>*/{
	int duration;
	
	public Leopard(int speed, int duration){
		super(speed);
		this.duration = duration;
	}
	
	public int getDuration(){
		return duration;
	}

/*	@Override
	public boolean faster(Horse that) {
		if(this.speed() > that.speed()) return true;
		return false;
	}*/

	@Override
	public boolean faster(Racer that) {
		if(this.speed() > that.speed()) return true;
		return false;
	}
	
}