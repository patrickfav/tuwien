package aufgabe9;

import java.util.ArrayList;

public class Store {
	private ArrayList<Box> box_list;
	private ArrayList<AbstractGift> gifts_list;
	private AbstractGift current_gift;
	private Box current_box;
	private boolean debug = true;
	
	
	public Store() {
		this.box_list = new ArrayList<Box>();
		this.gifts_list = new ArrayList<AbstractGift>();
	}
	
	//adds given box to the list of boxes
	public void addBox(Box box) {
		box_list.add(box);
	}
	
	//adds given gift to the list of gifts
	public void addGift(AbstractGift g) {
		gifts_list.add(g);
	}
	
	//searches for the gift specified by the given name
	//if found a suitable box is searched
	//if no suitable box is found a new box will be created
	public Gift pack(String name) {
		
		write("Trying to find gift "+name);
		
		for(int i=0;i<gifts_list.size();i++) {
			if (gifts_list.get(i).getName().equals(name)) {
				current_gift = gifts_list.get(i);
				
				write("Trying to find box for gift "+current_gift);
				//cycles through boxes
				for(int j=0;j<box_list.size();j++) {
					current_box = box_list.get(j);
					//if the box fits
					if(current_gift.checkIfFitsInBox(current_box)) {
						//removes the boxes and gifts
						box_list.remove(j);
						gifts_list.remove(i);
						
						write(current_gift+" Gift fits in box "+current_box);
						
						return current_gift.pack(current_box);
					}
					
					write(current_gift+" Gift does not fit in box "+current_box);
				}
				
				write("No box found that fits.");
				
				//makes a new box that fits
				if(current_gift.getForm() instanceof Rectangle) {
					Box rb = current_gift.makeNewRectangleBox();
					write("Creating new box "+rb+" (Volume: "+rb.volume()+" Height: "+rb.getHeight()+")");
					return current_gift.pack(rb);
				}
				else if(current_gift.getForm() instanceof Square) {
					Box sb = current_gift.makeNewSquareBox();
					write("Creating new box "+sb+" (Volume: "+sb.volume()+" Height: "+sb.getHeight()+")");
					return current_gift.pack(sb);
				}
				else if(current_gift.getForm() instanceof Circle) {
					Box cb = current_gift.makeNewCircleBox();
					write("Creating new box "+cb+" (Volume: "+cb.volume()+" Height: "+cb.getHeight()+")");
					return current_gift.pack(cb);
				}
				else {
					write("Error: Could not create new box.");
					return null;
				}
			}
		}
		
		
		return null;
		
	}
	
	//debug output
	private void write(String msg) {
		if(debug) {
			System.out.println(msg);
		}
	}
}
