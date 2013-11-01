package refactoring;

public class ChildrenPizza extends Pizza{
	
	private static final int pSize = 0;
	
	public ChildrenPizza(String name, int priceInEuro) {
		super(name, priceInEuro,pSize);	
	}
	
	/*
	 * Default Konstruktor
	 */
	public ChildrenPizza() {
		super("default",10,pSize);
	}
}
