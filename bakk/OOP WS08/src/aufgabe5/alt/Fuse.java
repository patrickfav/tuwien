/**
 * 
 */
package aufgabe5.alt;

/**
 * @author GEMEH
 *
 */
public class Fuse implements Comperator<Fuse>{
	int persistency;
	
	public Fuse(int persistency){
		this.persistency=persistency;
	}
	
	public int time(){
		return persistency;
	}

	@Override
	public boolean faster(Fuse that) {
		if (this.time() < that.time()) return true;
		return false;
	}
}