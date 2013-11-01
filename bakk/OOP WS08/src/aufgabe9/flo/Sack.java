package aufgabe9.flo;

import java.util.ArrayList;
import java.util.Iterator;

public class Sack {
	
	public ArrayList<Gift> gift_list;
	
	// instances new sack
	public Sack() {
		this.gift_list = new ArrayList<Gift>();
	}
	
	// adds new gift
	public void addGift(Gift box) {
		gift_list.add(box);
	}

	// returns volume
	public float volume() {
		float sum_volume = 0;
		for(Iterator<Gift> i = gift_list.iterator(); i.hasNext();){
			// sum all volumes
			sum_volume += i.next().volume();
		}
		return sum_volume;
	}
	
	// print gifts name
	public void gifts() {
		for(Iterator<Gift> i = gift_list.iterator(); i.hasNext();){
			Gift gift = i.next();
			// while there is a box in a box
			while(gift instanceof Box) {
				Box box = (Box) gift;
				gift = box.getGift();
			}
			// if gift is the present -> print name
			if(gift instanceof abstractGift) {
				abstractGift present = (abstractGift) gift;
				System.out.println(present.getName());
			}
		}
	}
}
