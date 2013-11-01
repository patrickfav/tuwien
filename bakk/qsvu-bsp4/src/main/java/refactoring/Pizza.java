package refactoring;

public abstract class Pizza
{

	protected final String name;
	protected final int priceInEuro;
	protected final int size;
	
	
	public Pizza(String name, int priceInEuro,int size) {
		this.name = name;
		this.priceInEuro = priceInEuro;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public int getPriceInEuro() {
		return priceInEuro;
	}

	public int getSize() {
		return size;
	}
}
