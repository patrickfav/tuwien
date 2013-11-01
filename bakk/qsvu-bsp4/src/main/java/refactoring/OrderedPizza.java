package refactoring;

public class OrderedPizza
{
	private final int quantity;
	private final Pizza pizza;

	public OrderedPizza(int quantity, Pizza pizza) {
		this.quantity = quantity;
		this.pizza = pizza;
	}

	public Pizza getPizza() {
		return pizza;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public double calculatePrice() {
		return getSumPrice();
	}
	
	private double getSumPrice() {
		return quantity * pizza.getPriceInEuro();
	}
}
