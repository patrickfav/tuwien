package refactoring;

public class XXLPizza extends Pizza{
	
	private static final int pSize = 2;
	
	public XXLPizza(String name, int priceInEuro) {
		super(name, priceInEuro,2);	
	}
	
	/*
	 * Default Konstruktor
	 */
	public XXLPizza() {
		super("default",10,pSize);
	}
}
