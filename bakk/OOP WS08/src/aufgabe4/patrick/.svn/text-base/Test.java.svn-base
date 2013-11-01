package aufgabe4.patrick;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/**************************
		 * 		BLACKBOX
		 **************************/
		char fc1 = '+';
		char fc2 = '-';
		
		System.out.println("BlackBox with char '"+fc1+"'");
		Box box = new BlackBox(5,4,fc1);
		box.print();
		
		System.out.println();
		System.out.println();
		System.out.println("BlackBox changed to char '"+fc2+"' and different size");
		//change properties Blackbox 1
		box.setFrameChar(fc2);
		box.setSize(7,3);
		box.print();
		
		System.out.println();
		System.out.println();
		
		
		/**************************
		 * 		WHITEBOX
		 **************************/
		char fc3 = '-';
		
		System.out.println("WhiteBox with char '"+fc3+"'");
		
		Box box2 = new WhiteBox(24,8,fc3);
		box2.print();
		
		System.out.println();
		System.out.println();
		
		/**************************
		 * 		GREYBOX
		 **************************/
		char fc4a = 'x';
		char fc4b = 'o';
		char fc5 = 'y';
		
		System.out.println("GreyBox with border '"+fc4a+"' and fill '"+fc4b+"'");
		Box box3 = new GreyBox(14,6,fc4a,fc4b);
		box3.print();
		
		//change boder
		System.out.println();
		System.out.println("GreyBox with changed border '"+fc5+"'");
		box3.setFrameChar(fc5);
		box3.print();
		
		System.out.println();
		System.out.println();
		
		/**************************
		 * 		COLOREDBOX
		 **************************/
		
		char fc6a = '#';
		char fc6b = ':';
		char fc7a = '*';
		char fc7b = '.';
		
		System.out.println("ColoredBox with border '"+fc6a+"' and fill '"+fc6b+"'");
		AbstractExtendedBox box4 = new ColoredBox(7,5,fc6a,fc6b);
		box4.print();
		
		//change border and fill
		System.out.println();
		System.out.println("ColoredBox with changed border '"+fc7a+"' and fill '"+fc7b+"'");
		box4.setFrameChar(fc7a);
		box4.setFillChar(fc7b);
		box4.print();
		
		
		/**************************
		 * 	CASTING/SUBTYPING TEST
		 **************************/
		
		System.out.println();
		System.out.println("Cast ColoredBox Object in Box Object");
		Box box5 = box4;
		box5.print();
		
		
		System.out.println();
		System.out.println("Cast GreyBox Object in AbstractSimpleBox Object and change frame char");
		AbstractSimpleBox box6 = (AbstractSimpleBox) box3;
		box6.setFrameChar('z');
		box6.print();
		
		
		/*
		System.out.println();
		System.out.println("Cast WhiteBox Object in BlackBox Object");
		AbstractSimpleBox box7 = (AbstractSimpleBox) box2;
		box7.print();
		*/
	}

}
