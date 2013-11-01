package aufgabe8.flo;

import java.util.ArrayList;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Container c1 = new Container(false, 20);
		Container c2 = new Container(false, 20);
		Container c3 = new Container(false, 20);
		
		Timber t1 = new Timber(2);
		Timber t2 = new Timber(2);
		
		ArrayList<Timber> al1 = new ArrayList<Timber>();
		al1.add(t1);
		al1.add(t2);
		
		SplittMachine s1 = new SplittMachine(false, al1, c1, 50);
		GrindingMachine g1 = new GrindingMachine(false, c2, c1, 50);
		
		PackagingMachine p1 = new PackagingMachine(false, c3, c2,30 );
		
		Factory f1 = new Factory(10000);
		
		f1.registerInventory(c1);		
		f1.registerInventory(c2);		
		f1.registerInventory(c3);
		f1.registerInventory(s1);
		f1.registerInventory(g1);
		f1.registerInventory(p1);
		f1.startProduction();
		
		
		
		
	}

}
