package aufgabe5.flo;

public interface AnnotatedIterator<A, B> extends Iterator<A> {
	public Iterator<B> iterator();
}
