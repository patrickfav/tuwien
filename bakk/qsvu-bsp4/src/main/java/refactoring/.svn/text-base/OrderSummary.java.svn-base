package refactoring;

public class OrderSummary {
	
	private Customer c;
	
	public OrderSummary(Customer c) {
		this.c = c;
	}
	
	public String getOrderSummary() {
		
		String result = "Order Record for " + c.getName() + "\n";
		for (int i = 0; i < c.getOrderedPizzas().size(); ++i) {
			OrderedPizza order = c.getOrderedPizzas().get(i);
			int orderPrice = order.getQuantity() * order.getPizza().getPriceInEuro();
			result += String.format("    %2d %-20s %3d\n", order.getQuantity(), order.getPizza().getName(), orderPrice);
		}
		result += String.format("    %-23s %3d\n", "Total", c.getTotalPrice());
		result += String.format("    %-23s %3d\n", "Free Normal Pizzas", c.getPizzaPoints() / 100);
		return result;
	}
}
