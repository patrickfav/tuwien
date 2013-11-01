package aufgabe9;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		 * Gifts
		 */
		
		AbstractGift book1 = new Book("Harry Potter",2.0f, 20.0f, 15.0f);
		AbstractGift book2 = new Book("Herr der Ringe",4.0f, 25.0f, 20.0f);
		
		AbstractGift ball1 = new Ball("Fussball",20.0f, 20.0f);
		AbstractGift ball2 = new Ball("Basketball",25.0f, 25.0f);
		
		AbstractGift rubik1 = new RubiksCube("3x3",10.0f, 10.0f);
		AbstractGift rubik2 = new RubiksCube("4x4",15.0f, 15.0f);
		
		/*
		 * Boxes
		 */
		Box cb1 = new CircleBox(null, 0.5f,8.0f, 15.0f);
		Box cb2 = new CircleBox(null, 0.5f,22.0f, 22.0f);
		Box cb3 = new CircleBox(null, 0.5f,40.0f, 35.0f);
		
		Box rb1 = new RectangleBox(null, 0.5f,0.5f, 5.0f,10.0f);
		Box rb2 = new RectangleBox(null, 0.5f,4.0f, 22.0f,18.0f);
		Box rb3 = new RectangleBox(null, 0.5f,6.0f, 30.0f,23.0f);
		
		Box sb1 = new SquareBox(null, 0.5f,8.0f, 30.0f);
		Box sb2 = new SquareBox(null, 0.5f,2.0f, 4.0f);
		Box sb3 = new SquareBox(null, 0.5f,17.0f, 17.0f);
		
		
		/*
		 * Boxes
		 */
		
		Store s = new Store();
		
		s.addBox(cb1);
		s.addBox(cb2);
		s.addBox(cb3);
		s.addBox(rb1);
		s.addBox(rb2);
		s.addBox(rb3);
		s.addBox(sb1);
		s.addBox(sb2);
		s.addBox(sb3);
		
		
		
		s.addGift(book1);
		s.addGift(book2);
		s.addGift(ball1);
		s.addGift(ball2);
		s.addGift(rubik1);
		s.addGift(rubik2);
		
		
		/*
		 * Square Gift finds Circle Box
		 */
		System.out.println("=======================");
		Gift packedgift0 = s.pack("3x3");
		
		//get box info
		System.out.println(packedgift0.getName());
		System.out.println(packedgift0.getGiftType());
		System.out.println(packedgift0.volume());
		System.out.println(packedgift0.getHeight());
		
		
		/*
		 * Rectangle Gift finds Rectangle Box
		 */
		System.out.println("=======================");
		
		Gift packedgift1 = s.pack("Harry Potter");
		
		//get box info
		System.out.println(packedgift1.getName());
		System.out.println(packedgift1.getGiftType());
		System.out.println(packedgift1.volume());
		System.out.println(packedgift1.getHeight());
		
		//repack
		System.out.println("Repack "+packedgift1);
		Gift packedgift2 = packedgift1.pack(rb3);
		System.out.println(packedgift2.getName());
		System.out.println(packedgift2.getGiftType());
		System.out.println(packedgift2.volume());
		System.out.println(packedgift2.getHeight());
		
		System.out.println("Unpack "+packedgift2);
		//first unpack
		Gift g1 = packedgift2.unpack();
		System.out.println(g1.getGiftType());
		System.out.println(g1.volume());
		System.out.println(g1.getHeight());
		
		System.out.println("Unpack "+g1);
		//first unpack
		Gift g2 = g1.unpack();
		System.out.println(g2.getGiftType());
		System.out.println(g2.volume());
		System.out.println(g2.getHeight());
		
		
		/*
		 * Circle Gift finds Circle Box
		 */
		System.out.println("=======================");
		
		Gift packedgift3 = s.pack("Fussball");
		
		//get box info
		System.out.println(packedgift3.getName());
		System.out.println(packedgift3.getGiftType());
		System.out.println(packedgift3.volume());
		System.out.println(packedgift3.getHeight());
		
		
		/*
		 * Circle Gift does not find box - creates one
		 */
		System.out.println("=======================");
		
		Gift packedgift4 = s.pack("Basketball");
		
		//get box info
		System.out.println(packedgift4.getName());
		System.out.println(packedgift4.getGiftType());
		System.out.println(packedgift4.volume());
		System.out.println(packedgift4.getHeight());
		
		
		/*
		 * Cube Gift finds Square box
		 */
		System.out.println("=======================");
		
		Gift packedgift5 = s.pack("4x4");
		
		//get box info
		System.out.println(packedgift5.getName());
		System.out.println(packedgift5.getGiftType());
		System.out.println(packedgift5.volume());
		System.out.println(packedgift5.getHeight());
		
		System.out.println("=======================");
		
		
		/*
		 * SACK
		 */
		
		Sack sack = new Sack();
		sack.addGift(packedgift0);
		sack.addGift(packedgift1);
		sack.addGift(packedgift2);
		sack.addGift(packedgift3);
		sack.addGift(packedgift4);
		sack.addGift(packedgift5);
		
		System.out.println(sack.volume());
		sack.gifts();
	}

}
