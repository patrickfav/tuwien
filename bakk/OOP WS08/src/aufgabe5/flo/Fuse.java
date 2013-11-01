package aufgabe5.flo;

/**
 * @author GEMEH, FLRAU, PAFAV
 * 
 * Creates a Fuse
 * Implements the interface Comparator to compare Fuses
 * 
 */
public class Fuse implements Comparator<Fuse>{
	int persistency;
	
	public Fuse(int persistency){
		// creates a fuse with persistency
		this.persistency = persistency;
	}
	
	public int time(){
		// returns the persistency
		return persistency;
	}

	public boolean faster(Fuse that) {
		// expects a racer
		if (this.time() < that.time()) return true;
		return false;
		// true if this is faster than that
	}
}