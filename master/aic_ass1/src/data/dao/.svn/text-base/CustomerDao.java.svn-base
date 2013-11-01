package data.dao;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import data.DataBackend;
import entities.Customer;

import java.math.BigDecimal;
import java.util.List;

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

	public boolean addCustomer(Customer c) {
		return db.getCustomers().add(c);
	}

	public List<Customer> getAllCustomers() {
		return db.getCustomers();
	}

	public Customer getCustomerById(String id) {
		for (Customer c : db.getCustomers()) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}

	public boolean updateCustomer(Customer customer) {
		for (Customer c : db.getCustomers()) {
			if (c.getId().equals(customer.getId())) {
				c.setAddresses(customer.getAddresses());
				c.setName(customer.getName());
				c.setOpenBalance(customer.getOpenBalance());
				c.setOrders(customer.getOrders());
				return true;
			}
		}
		return false;
	}

	public boolean deleteCustomer(Customer c) {
		return db.getCustomers().remove(c);
	}

	public void deleteAllCustomers() {
		db.getCustomers().clear();
	}

	public boolean updateAccount(Customer customer, BigDecimal valueChanged) {
		Customer c = getCustomerById(customer.getId());
		if(c != null) {
			c.setOpenBalance(valueChanged);
			return true;
		} else {
			return false;
		}
	}
}
