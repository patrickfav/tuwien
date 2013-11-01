package aufgabe9.flo;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/* Create gifts */
		Gift book = new Book("Harry Potter", 25, 3, 14);
		Gift swatch = new Swatch("Swatch", 3, 20, 5);
		Gift ball = new Ball("Fussball", 20);
		Gift rubikscube = new RubiksCube("Rubiks Cube", 5);		
		Gift wine = new Wine("Wine", 6, 25);
		Gift vase = new Vase("Vase", 15, 30);
		
		/* Print gifts volume */
		System.out.println("----- Gifts Volume -----");
		System.out.println("Book: " + book.volume() + "cm3");
		System.out.println("Swatch: " + swatch.volume() + "cm3");
		System.out.println("Fussball: " + ball.volume() + "cm3");
		System.out.println("Rubiks Cube: " + rubikscube.volume() + "cm3");
		System.out.println("Wine: " + wine.volume() + "cm3");
		System.out.println("Vase: " + vase.volume() + "cm3");
		
		/* Create boxes */
		Box circlebox1 = new CircleBox(0.5f, 10, 20);
		Box circlebox2 = new CircleBox(0.5f, 15, 15);
		Box circlebox3 = new CircleBox(0.5f, 20, 30);
		Box squarebox1 = new SquareBox(0.5f, 5);
		Box squarebox2 = new SquareBox(0.5f, 6);
		Box squarebox3 = new SquareBox(0.5f, 7);
		Box rectanglebox1 = new RectangleBox(0.5f, 25, 3, 14);
		Box rectanglebox2 = new RectangleBox(0.5f, 15, 14, 18);
		Box rectanglebox3 = new RectangleBox(0.5f, 10, 16, 13);
		
		/* Print boxes volume */
		System.out.println("----- Boxes Volume -----");		
		System.out.println("Rectanglebox 1: " + rectanglebox1.volume() + "cm3");
		System.out.println("Rectanglebox 2: " + rectanglebox2.volume() + "cm3");
		System.out.println("Rectanglebox 3: " + rectanglebox3.volume() + "cm3");
		System.out.println("Circlebox 1: " + circlebox1.volume() + "cm3");
		System.out.println("Circlebox 2: " + circlebox2.volume() + "cm3");
		System.out.println("Circlebox 3: " + circlebox3.volume() + "cm3");
		System.out.println("Squarebox 1: " + squarebox1.volume() + "cm3");
		System.out.println("Squarebox 2: " + squarebox2.volume() + "cm3");
		System.out.println("Squarebox 3: " + squarebox3.volume() + "cm3");
				
		/* Create store */
		Store store = new Store();
		
		/* Add boxes to store */
		store.addBox(circlebox1);
		store.addBox(squarebox1);
		store.addBox(rectanglebox1);
		store.addBox(circlebox2);
		store.addBox(squarebox2);
		store.addBox(rectanglebox2);
		store.addBox(circlebox3);
		store.addBox(squarebox3);
		store.addBox(rectanglebox3);
		
		/* Create Santas Sack */
		Sack sack = new Sack();
		
		/* Get volume of Santas sack */
		System.out.println("----- Santas Sack Volume -----");
		System.out.println(sack.volume() + "cm3");
		
		/* Pack gifts into boxes */
		System.out.println("----- Pack gifts -----");
		
		Gift gift1 = book.pack(store);
		Gift gift2 = ball.pack(store);
		Gift gift3 = rubikscube.pack(store);
		Gift gift4 = wine.pack(store);
		Gift gift5 = vase.pack(store);
		Gift gift6 = swatch.pack(store);
		System.out.println("# Pack RubiksCube three times #");
		Gift gift7 = gift3.pack(store);
		Gift gift8 = gift7.pack(store);
		
		/* Print gifts volume */
		System.out.println("----- Packed Gifts Volume -----");
		System.out.println("Gift 1: " + gift1.volume() + "cm3");
		System.out.println("Gift 2: " + gift2.volume() + "cm3");
		System.out.println("Gift 3: " + gift8.volume() + "cm3");
		System.out.println("Gift 4: " + gift4.volume() + "cm3");
		System.out.println("Gift 5: " + gift5.volume() + "cm3");
		System.out.println("Gift 6: " + gift6.volume() + "cm3");
		
		/* Add gifts to Santas sack */
		sack.addGift(gift1);
		sack.addGift(gift2);
		sack.addGift(gift4);
		sack.addGift(gift5);
		sack.addGift(gift6);
		sack.addGift(gift8);
		
		/* Get volume of Santas sack */
		System.out.println("----- Santas Sack Volume -----");
		System.out.println(sack.volume() + "cm3");
		
		/* Print Santas gifts */
		System.out.println("----- Santas Presents -----");
		sack.gifts();

	}

}
