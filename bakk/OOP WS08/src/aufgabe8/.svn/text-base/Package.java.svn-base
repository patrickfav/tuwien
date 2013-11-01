package aufgabe8;

import java.util.ArrayList;
/**
 * This class represents a package of Sticks. It can only carry two smoothedSticks objects per instance
 *
 */
public class Package extends Product{
	private ArrayList<SmoothedStick> content = new ArrayList<SmoothedStick>();
	//private SmoothedStick[] content = new SmoothedStick[2]();
	
	public void addSmoothedStick(SmoothedStick s) {
		if (content.size() > 2) throw new FactoryException("Package Error: tried to insert Smoothed Stick" 
				+ s.toString() + " although instance "+ this.toString() +" has already capacity "
				+ content.size());
		else {
			content.add(s);
		}
	}
	
	public int getVolumeOfPackage(){
		return content.size();
	}
}