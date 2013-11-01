package aufgabe9;

import java.util.ArrayList;

public class Sack {
	public ArrayList<Gift> gift_list;
	
	public Sack() {
		this.gift_list = new ArrayList<Gift>();
	}
	
	//adds any kind of gift (boxed or unboxed)
	public void addGift(Gift gift) {
		gift_list.add(gift);
	}
	
	//returns the sum of all of the gifts volumes
	public float volume() {
		float sum_vol= 0;
		
		for(int i=0;i<gift_list.size();i++) {
			sum_vol += gift_list.get(i).volume();
		}

		return sum_vol;
	}
	
	//returns all the gifts names
	public void gifts() {
		for(int i=0;i<gift_list.size();i++) {
			System.out.println(gift_list.get(i).getName());
		}
	}
}
