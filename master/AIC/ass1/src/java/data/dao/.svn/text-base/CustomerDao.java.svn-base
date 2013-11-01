package data.dao;

import data.DataBackend;
import entities.Customer;
import java.util.Iterator;
import java.util.Set;


public class CustomerDao {

    private static CustomerDao instance = null;
    private DataBackend db;

    public CustomerDao() {
        db = DataBackend.getInstance();
    }

    public static CustomerDao getInstance() {
        if (instance == null) {
            instance = new CustomerDao();
        }
        return instance;
    }

    public void addCustomer(Customer c) {
        db.getCustomers().add(c);
    }

    public Set<Customer> getAllCustomers() {
        return db.getCustomers();
    }

    public Customer getCustomerById(String id) {
        Iterator it = db.getCustomers().iterator();
        while(it.hasNext()) {
            Customer c = (Customer)it.next();
            if(c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public void deleteCustomer(Customer c) {
        db.getCustomers().remove(c);
    }

    public void deleteAllCustomers() {
        db.getCustomers().clear();
    }
}
