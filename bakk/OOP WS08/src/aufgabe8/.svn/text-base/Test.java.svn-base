package aufgabe8;

import java.util.ArrayList;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/* Factory 1
		 * 2 timbers split into 2 sticks
		 */
		
		// Build factory
		Factory f1 = new Factory(10000);
		
		// Chop timber
		Timber t1 = new Timber(2);
		Timber t2 = new Timber(2);
		
		// Add Timber 
		ArrayList<Timber> al1 = new ArrayList<Timber>();
		al1.add(t1);
		al1.add(t2);
		
		// Build containers
		Container c1 = new Container(false, 20);
		Container c2 = new Container(false, 20);
		Container c3 = new Container(false, 20);
		
		// Build machines
		SplittMachine s1 = new SplittMachine(false, al1, c1, 50);
		GrindingMachine g1 = new GrindingMachine(false, c2, c1, 50);	
		PackagingMachine p1 = new PackagingMachine(false, c3, c2, 30);
		
		// Add inventory to factory
		f1.registerInventory(c1);		
		f1.registerInventory(c2);		
		f1.registerInventory(c3);
		f1.registerInventory(s1);
		f1.registerInventory(g1);
		f1.registerInventory(p1);
		
		// Start production
		f1.startProduction();
		
		
		/* Factory 2 
		 * 3 timbers split into 10 sticks
		 */
		
		// Build factory
		Factory f2 = new Factory(10000);
		
		// Chop timber
		Timber t3 = new Timber(10);
		Timber t4 = new Timber(10);
		Timber t5 = new Timber(10);
		
		// Add timber
		ArrayList<Timber> al2 = new ArrayList<Timber>();
		al2.add(t3);
		al2.add(t4);
		al2.add(t5);
		
		// Build containers
		Container c4 = new Container(false, 30);
		Container c5 = new Container(false, 5);
		Container c6 = new Container(false, 30);
		
		// Build machines
		SplittMachine s2 = new SplittMachine(false, al2, c4, 50);
		GrindingMachine g2 = new GrindingMachine(false, c5, c4, 50);		
		PackagingMachine p2 = new PackagingMachine(false, c6, c5, 30);
		
		// Add inventory to factory
		f2.registerInventory(c4);		
		f2.registerInventory(c5);		
		f2.registerInventory(c6);
		f2.registerInventory(s2);
		f2.registerInventory(g2);
		f2.registerInventory(p2);
		
		// Start production
		f2.startProduction();
		
		
		/* Factory 3 
		 * 4 timbers split into 8 sticks
		 */
		
		// Build factory
		Factory f3 = new Factory(10000);
		
		// Chop timber
		Timber t6 = new Timber(8);
		Timber t7 = new Timber(8);
		Timber t8 = new Timber(8);
		Timber t9 = new Timber(8);
		
		// Add timber
		ArrayList<Timber> al3 = new ArrayList<Timber>();
		al3.add(t6);
		al3.add(t7);
		al3.add(t8);
		al3.add(t9);
		
		// Build containers
		Container c7 = new Container(false, 36);
		Container c8 = new Container(false, 36);
		Container c9 = new Container(false, 36);
		
		// Build machines
		SplittMachine s3 = new SplittMachine(false, al3, c7, 50);
		GrindingMachine g3 = new GrindingMachine(false, c8, c7, 50);		
		PackagingMachine p3 = new PackagingMachine(false, c9, c8, 30);
		
		// Add inventory to factory
		f3.registerInventory(c7);		
		f3.registerInventory(c8);		
		f3.registerInventory(c9);
		f3.registerInventory(s3);
		f3.registerInventory(g3);
		f3.registerInventory(p3);
		
		// Start production
		f3.startProduction();
		
		
	}

}
