package services;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import data.DataBackend;
import data.dao.CustomerDao;

import java.math.BigDecimal;
import java.util.Date;

import javax.ws.rs.core.Response;

import services.interfaces.customer.CustomerService;

import entities.Customer;


public class CustomerManagementService implements CustomerService {

	private String printPrefix = "[CustomerManagementService]: ";
	
    public Customer getCustomer(String id) {
    	System.out.println("=============================================");
        for (Customer c : DataBackend.getInstance().getCustomers()) {
            if (c.getId().equals(id)) {
                System.out.println(printDate() + " Returning customer with ID " + c.getId() + " and name " + c.getName());
                return c;
            }
        }
        System.out.println(printDate() + " No customer with ID " + id + " found!");
        return null;
    }

    public Response updateCustomer(Customer customer) {
    	System.out.println("=============================================");
        if(CustomerDao.getInstance().updateCustomer(customer)) {
        	System.out.println(printDate() + " Customer with ID " + customer.getId() + " updated!");
        	return Response.ok().build();
        } else {
        	System.out.println(printDate() + " Customer with ID " + customer.getId() + " could not be updated!");
        	return Response.notModified().build();
        }
    }

    public Response addCustomer(Customer customer) {
    	System.out.println("=============================================");
    	if(CustomerDao.getInstance().addCustomer(customer)) {
    		System.out.println(printDate() + " Customer with ID " + customer.getId() + " added!");
    		return Response.ok().build();
        } else {
        	System.out.println(printDate() + " Customer with ID " + customer.getId() + " could not be added!");
        	return Response.notModified().build();
        }
    }

    public Response deleteCustomer(String id) {
    	System.out.println("=============================================");
    	Customer c = CustomerDao.getInstance().getCustomerById(id);
    	if(c != null && CustomerDao.getInstance().deleteCustomer(c)) {
    		System.out.println(printDate() + " Customer with ID " + c.getId() + " deleted!");
        	return Response.ok().build();
        } else {
        	System.out.println(printDate() + " Customer could not be deleted!");
        	return Response.notModified().build();
        }
    }
    
    public Response notifyC(Customer customer,String message) {
        System.out.println("=============================================");
        System.out.println(printDate() + " Customer with ID " + customer.getId() + " notifyied!");
        CustomerDao.getInstance().notifyCustomer(customer,message);
    	return Response.ok().build();	
    }
    
    public Response updateAccount(Customer customer,BigDecimal change) {
    	System.out.println("=============================================");
    	if(CustomerDao.getInstance().updateAccount(customer, change)) {
    		System.out.println(printDate() + " Customer with ID " + customer.getId() + " value changed ("+change+")!");
        	return Response.ok().build();
        } else {
        	System.out.println(printDate() + " Customer with ID " + customer.getId() + " value could not be changed!");
        	return Response.notModified().build();
        }	
    }

    private String printDate() {
       	return printPrefix + new Date();
    }
}
