package aufgabe5;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SortedSet<Fuse> fuseSet = new SortedSet<Fuse>();
		
		Fuse fuse1 = new Fuse(100000);
		Fuse fuse2 = new Fuse(200000);
		Fuse fuse3 = new Fuse(300000);
		Fuse fuse4 = new Fuse(400000);
		Fuse fuse5 = new Fuse(500000);
		
		fuseSet.insert(fuse1);
		fuseSet.insert(fuse2);
		fuseSet.insert(fuse3);
		fuseSet.insert(fuse4);
		fuseSet.insert(fuse5);
		
		System.out.println("Durchbrennzeiten in Millisekunden:");
		for(Iterator<Fuse> i = fuseSet.getIterator(); i.hasNext();) {
		        Fuse s = (Fuse)i.next();
		        long a = s.getTimeToElapse();		
		        System.out.println(a + "ms");
		 } 
	}

}
