/**
 * 
 */
package aufgabe4.georg_2;

/**
 * @author GEMEH
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BoxImpl bB = new BlackBox(5,8, '*');
		System.out.println("Test_b1");
		bB.print();
		System.out.println("Test_b2");
		bB.setFrameChar('?');
		bB.print();
		System.out.println("Test_b3");
		bB.setSize(30, 30);
		bB.setFrameChar('+');
		bB.print();
		BoxImpl wB = new WhiteBox(4,8,'*');
		System.out.println("Test_w1");
		wB.print();
		System.out.println("Test_w2");
		wB.setFrameChar('/');
		wB.print();
		System.out.println("Test_w3");
		wB.setSize(30, 30);
		wB.setFrameChar('$');
		wB.print();
		BoxImpl gB = new GreyBox(12,6,'*', '|');
		System.out.println("Test_w1");
		gB.print();
		System.out.println("Test_w2");
		gB.setFrameChar('?');
		gB.print();
		System.out.println("Test_w3");
		gB.setSize(30, 30);
		gB.setFrameChar('%');
		gB.print();
		FlexibleBoxImpl cB = new ColoredBox(20,18,'|', '=');
		System.out.println("Test_w1");
		cB.print();
		System.out.println("Test_w2");
		cB.setFrameChar('?');
		cB.print();
		System.out.println("Test_w3");
		cB.setFillChar('&');
		cB.print();
		System.out.println("Test_w4");
		cB.setSize(30, 30);
		cB.setFrameChar('%');
		cB.print();
	}

}
