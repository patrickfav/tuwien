package aufgabe5.flo;

/**
 * @author GEMEH, FLRAU, PAFAV
 * 
 * Test class
 * 
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SortedSet<Fuse> fuseSet = new SortedSet<Fuse>();
		// create new sortedSet for Fuses
		
		Fuse fuse1 = new Fuse(700000);
		Fuse fuse2 = new Fuse(100000);
		Fuse fuse3 = new Fuse(200000);
		Fuse fuse4 = new Fuse(900000);
		Fuse fuse5 = new Fuse(500000);
		// create 5 new Fuses
		
		fuseSet.insert(fuse1);
		fuseSet.insert(fuse2);
		fuseSet.insert(fuse3);
		fuseSet.insert(fuse4);
		fuseSet.insert(fuse5);
		// insert the created Fuses into the SoertedSet
		
		System.out.println("Fuseing in milliseconds:");
		for(Iterator<Fuse> i = fuseSet.iterator(); i.hasNext();) {
		        Fuse s = (Fuse)i.next();
		        int a = s.time();		
		        System.out.println(a + "ms");
		}
		// show the sorted Fuses
		
		AnnotatedSet<Horse,Leopard> annotatedSet = new AnnotatedSet<Horse,Leopard>();
		// create new AnnotatedSet for Horses with Leopard notes
		
		Horse horse1 = new Horse(20, 100);
		Horse horse2 = new Horse(12, 30);
		Horse horse3 = new Horse(30, 20);
		Horse horse4 = new Horse(14, 150);
		Horse horse5 = new Horse(21, 60);
		// create 5 new Horses
		
		annotatedSet.insert(horse1);
		annotatedSet.insert(horse2);
		annotatedSet.insert(horse3);
		annotatedSet.insert(horse4);
		annotatedSet.insert(horse5);
		// insert the created horses into the annotatedSet
		
		Leopard leopard1 = new Leopard(40, 100);
		Leopard leopard2 = new Leopard(24, 30);
		Leopard leopard3 = new Leopard(30, 40);
		Leopard leopard4 = new Leopard(63, 45);
		Leopard leopard5 = new Leopard(45, 20);
		// create 5 new Leopards
		
		annotatedSet.annotate(horse1, leopard1);
		annotatedSet.annotate(horse2, leopard3);
		annotatedSet.annotate(horse5, leopard3);
		annotatedSet.annotate(horse2, leopard2);
		annotatedSet.annotate(horse3, leopard1);
		annotatedSet.annotate(horse4, leopard5);
		annotatedSet.annotate(horse5, leopard4);
		annotatedSet.annotate(horse4, leopard2);
		// add the Leopards to the Horses	
		
		System.out.println("Horses with Notes:");
		for(Iterator<Horse> i = annotatedSet.iterator(); i.hasNext();) {
			Horse s = (Horse)i.next();
			int a = s.getStamina();		
			System.out.println("Horse: " + a + "s");
			System.out.println("Notes:");
			for(Iterator<Leopard> n = annotatedSet.iterator().iterator(); n.hasNext();) {
				Leopard u = (Leopard)n.next();
				int o = u.getDuration();
				System.out.println(o + "m");			        
			}
			System.out.println("---");
		}
		// show the Horses with their Leopards

		SortedSet<Racer> racerSet = new SortedSet<Racer>();
		// create new SortedSet with Racers
		
		racerSet.insert(horse1);
		racerSet.insert(horse2);
		racerSet.insert(horse3);
		racerSet.insert(horse4);
		racerSet.insert(horse5);
		// insert the 5 Horses to the SortedSet

		racerSet.insert(leopard1);
		racerSet.insert(leopard2);
		racerSet.insert(leopard3);
		racerSet.insert(leopard4);
		racerSet.insert(leopard5);
		// insert the 5 Leopards to the SortedSet
		
		System.out.println("Distance of high power:");
		for(Iterator<Racer> i = racerSet.iterator(); i.hasNext();) {
			Racer h = (Racer)i.next();
			int a = h.speed;
			System.out.println(a + "km/h");
		}
		// show the Sorted Racer Set
	}

}
