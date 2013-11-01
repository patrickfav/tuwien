package refactoring;

import java.util.Arrays;
import java.util.List;

public class Customer
{
	private final String name;
	private final List<OrderedPizza> orderedPizzas;

	public Customer(String name, OrderedPizza... orderedPizzas) {
		this.name = name;
		this.orderedPizzas = Arrays.asList(orderedPizzas);
	}

	public String getName() {
		return name;
	}

	public void addOrder(OrderedPizza pizza) {
		orderedPizzas.add(pizza);
	}
	
	public int getPizzaPoints() {
		int pizzaPoints =0;
		
		for (int i = 0; i < orderedPizzas.size(); ++i) {
			OrderedPizza order = orderedPizzas.get(i);
			if (order.getPizza().getSize() == new NormalPizza().getSize()) {
				pizzaPoints += order.getQuantity() * 10;
			} else if (order.getPizza().getSize() == new XXLPizza().getSize()) {
				pizzaPoints += order.getQuantity() * 15;
			}
		}
		return pizzaPoints;	
	}
	
	public int getTotalPrice() {
		int totalPrice = 0;
		
		for (int i = 0; i < orderedPizzas.size(); ++i) {
			OrderedPizza order = orderedPizzas.get(i);
			totalPrice += order.getQuantity() * order.getPizza().getPriceInEuro();
		}
		return totalPrice;
	}
	
	public List<OrderedPizza> getOrderedPizzas() {
		return orderedPizzas;
	}
}
