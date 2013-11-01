package aufgabe5.georg;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SortedList<Fuse> sL = new SortedList<Fuse>();
		SortedSet<Fuse> fuseSet = new SortedSet<Fuse>(sL);
		
		Fuse fuse1 = new Fuse(700000);
		Fuse fuse2 = new Fuse(100000);
		Fuse fuse3 = new Fuse(200000);
		Fuse fuse4 = new Fuse(900000);
		Fuse fuse5 = new Fuse(500000);
		
		fuseSet.insert(fuse1);
		fuseSet.insert(fuse2);
		fuseSet.insert(fuse3);
		fuseSet.insert(fuse4);
		fuseSet.insert(fuse5);
		
		System.out.println("Durchbrennzeiten in Millisekunden:");
		for(Iterator<Fuse> i = fuseSet.iterator(); i.hasNext();) {
		        Fuse s = (Fuse)i.next();
		        int a = s.persistency;		
		        System.out.println(a + "ms");
		 }
		
		
		AnnotatedList<Horse,Leopard> aL = new AnnotatedList<Horse,Leopard>();
		AnnotatedSet<Horse,Leopard> annotatedSet = new AnnotatedSet<Horse,Leopard>(aL);
		
		Horse horse1 = new Horse(20, 100);
		Horse horse2 = new Horse(12, 30);
		Horse horse3 = new Horse(30, 20);
		Horse horse4 = new Horse(14, 150);
		Horse horse5 = new Horse(21, 60);		
		annotatedSet.insert(horse1);
		annotatedSet.insert(horse2);
		annotatedSet.insert(horse3);
		annotatedSet.insert(horse4);
		annotatedSet.insert(horse5);
		
		Leopard leopard1 = new Leopard(40, 100);
		Leopard leopard2 = new Leopard(24, 30);
		Leopard leopard3 = new Leopard(50, 20);
		
		annotatedSet.annotate(horse2, leopard1);
		annotatedSet.annotate(horse2, leopard3);
		annotatedSet.annotate(horse5, leopard3);
		annotatedSet.annotate(horse2, leopard2);
		
		
		SortedList<Racer> sL2 = new SortedList<Racer>();
		SortedSet<Racer> racerSet = new SortedSet<Racer>(sL2);
		

		
		racerSet.insert(horse1);
		racerSet.insert(horse2);
		racerSet.insert(horse3);
		racerSet.insert(horse4);
		racerSet.insert(horse5);
		
		System.out.println("Geschwindigkeit in Millisekunden:");
		for(Iterator<Racer> i = racerSet.iterator(); i.hasNext();) {
			Horse h = (Horse)i.next();
		        int a = h.speed;		
		        System.out.println(a + "kmh");
		 }
	}

}
