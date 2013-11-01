package refactoring;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class CustomerTest
{
	private static final Pizza QUATTRO_FORMAGGI = new ChildrenPizza("Quattro Formaggi", 5);
	private static final Pizza SALAMI = new NormalPizza("Salami", 7);
	private static final Pizza DIAVOLO = new XXLPizza("Diavolo", 12);

	private static OrderedPizza order(int amount, Pizza pizza) {
		return new OrderedPizza(amount, pizza);
	}
	
	private static Customer createCustomer(String name, OrderedPizza... orderedPizzas) {
		return new Customer(name, orderedPizzas);
	}

	@Test
	public void getOrderSummary_noOrders() {
		Customer c = createCustomer("Max Mustermann");
		String result = "Order Record for Max Mustermann\n" +
		                "    Total                     0\n" +
		                "    Free Normal Pizzas        0\n";
		assertThat(new OrderSummary(c).getOrderSummary(), is(result));
	}

	@Test
	public void getOrderSummary_oneOrder() {
		Customer c = createCustomer("Max Mustermann", order(1, QUATTRO_FORMAGGI));
		String result = "Order Record for Max Mustermann\n" +
		                "     1 Quattro Formaggi       5\n" +
		                "    Total                     5\n" +
		                "    Free Normal Pizzas        0\n";
		assertThat(new OrderSummary(c).getOrderSummary(), is(result));
	}
	
	@Test
    public void getOrderSummary_withFreePizzas() {
		Customer c = createCustomer("Max Mustermann", order(9, QUATTRO_FORMAGGI), order(5, SALAMI), order(10, DIAVOLO));
		String result = "Order Record for Max Mustermann\n" +
		                "     9 Quattro Formaggi      45\n" +
		                "     5 Salami                35\n" +
		                "    10 Diavolo              120\n" +
		                "    Total                   200\n" +
		                "    Free Normal Pizzas        2\n";
		assertThat(new OrderSummary(c).getOrderSummary(), is(result));
    }

	@Test
    public void getOrderSummary_withFreePizzas2() {
		Customer c = createCustomer("Max Mustermann", order(9, QUATTRO_FORMAGGI), order(5, SALAMI), order(9, DIAVOLO));
		String result = "Order Record for Max Mustermann\n" +
		                "     9 Quattro Formaggi      45\n" +
		                "     5 Salami                35\n" +
		                "     9 Diavolo              108\n" +
		                "    Total                   188\n" +
		                "    Free Normal Pizzas        1\n";
		assertThat(new OrderSummary(c).getOrderSummary(), is(result));
    }
	
	@Test
	public void testGetterInPizza() {
		Pizza p = new NormalPizza("Diavolo",11);
		
		assertEquals("Diavolo",p.getName());
		assertEquals(11,p.getPriceInEuro());
		assertEquals(new NormalPizza().getSize(),p.getSize());
	}
	
	@Test
	public void testCalculatePrice() {
		OrderedPizza o = new OrderedPizza(10, DIAVOLO);
		
		assertEquals(120,o.calculatePrice(),1);
	}
}
