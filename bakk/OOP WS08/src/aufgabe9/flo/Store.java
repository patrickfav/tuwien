package aufgabe9.flo;

import java.util.ArrayList;
import java.util.Iterator;

public class Store {
	
	public ArrayList<Box> box_list;
	
	// instances new store
	public Store() {
		this.box_list = new ArrayList<Box>();
	}
	
	// adds new box
	public void addBox(Box box) {
		box_list.add(box);
	}
	
	// searches for a fitting box
	public Box searchBox(Form form, float height) {
		for(Iterator<Box> i = box_list.iterator(); i.hasNext();){
			Box box = i.next();			
			// if gift is Rectangle and box is Rectangle
			if(box.getForm() instanceof Rectangle && form instanceof Rectangle) {
				Rectangle rectangle = (Rectangle) box.getForm();
				Rectangle inputform = (Rectangle) form;
				// if length and width is equal
				if(rectangle.getLength() == inputform.getLength() && rectangle.getWidth() == inputform.getWidth()) {
					// remove box from store
					i.remove();
					System.out.println("RectangleBox gefunden");
					// return box
					return box;
				}
			// if gift is Square and box is Square
			} else if(box.getForm() instanceof Square && form instanceof Square) {
				Square square = (Square) box.getForm();
				Square inputform = (Square) form;
				// if length is equal
				if(square.getLength() == inputform.getLength()) {
					// remove box from store
					i.remove();
					System.out.println("SquareBox gefunden");
					// return box
					return box;
				}
			// if gift is Circle and box is Circle
			} else if(box.getForm() instanceof Circle && form instanceof Circle) {
				Circle circle = (Circle) box.getForm();
				Circle inputform = (Circle) form;
				// if radius is equal
				if(circle.getR() == inputform.getR()) {
					// remove box from store
					i.remove();
					System.out.println("CircleBox gefunden");
					// return box
					return box;
				}
			// if gift is Circle and box is Square
			} else if(box.getForm() instanceof Square && form instanceof Circle) {
				Square square = (Square) box.getForm();
				Circle inputform = (Circle) form;
				// if length is diameter
				if(square.getLength() == (inputform.getR()*2)) {
					// remove box from store
					i.remove();
					System.out.println("SquareBox gefunden");
					// return box
					return box;
				}
			}
		}
		// if no box was found return null
		System.out.println("Neue Box wird erstellt");
		return null;
	}
}
