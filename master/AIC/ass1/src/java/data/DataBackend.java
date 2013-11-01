package data;

import data.helper.DataGenerator;
import entities.Customer;
import entities.Product;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DataBackend {

    private static DataBackend instance = null;

    private Set<Customer> customers;
    private Set<Product> products;

    public DataBackend() {
        customers = Collections.synchronizedSet(new HashSet());
        products = Collections.synchronizedSet(new HashSet());
    }

    public static DataBackend getInstance() {
        if (instance == null) {
            instance = new DataBackend();
        }
        return instance;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void fillDataBackend() {

        DataGenerator dg = DataGenerator.getInstance();

        // Generate Products
        products.add(dg.getRandomProduct());
        products.add(dg.getRandomProduct());
        products.add(dg.getRandomProduct());
        products.add(dg.getRandomProduct());

        // Generate Customers
        customers.add(dg.getRandomCustomer());
        customers.add(dg.getRandomCustomer());
        customers.add(dg.getRandomCustomer());
        customers.add(dg.getRandomCustomer());
    }

    public void clearDataBackend() {
        customers.clear();
        products.clear();
    }

}
