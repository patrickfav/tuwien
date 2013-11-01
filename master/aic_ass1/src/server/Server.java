package server;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import data.DataBackend;
import entities.Customer;
import entities.internal.StockStatus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.xml.ws.Endpoint;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import services.CustomerManagementService;
import services.CustomerWrapperServiceImpl;
import services.RegistryServiceImpl;
import services.ShippingServiceImpl;
import services.SupplierServiceImpl1;
import services.SupplierServiceImpl2;
import services.WarehouseServiceImpl;
import services.interfaces.CustomerWrapperService;
import services.interfaces.RegistryService;
import services.interfaces.ShippingService;
import services.interfaces.SupplierService;
import services.interfaces.WarehouseService;

public class Server {

	public static final String ADDRESS = "http://localhost:9000/";

	public Server() throws Exception {

		System.out.println("Starting Services...");

		// Create and fill Database
		DataBackend.getInstance().fillDataBackend();

		// Create Services
		ShippingService shipping = new ShippingServiceImpl();
		SupplierService supplier1 = new SupplierServiceImpl1();
		SupplierService supplier2 = new SupplierServiceImpl2();
		RegistryService registry = new RegistryServiceImpl();
		WarehouseService warehouse = new WarehouseServiceImpl();
		CustomerWrapperService customer = new CustomerWrapperServiceImpl();

		// Publish Services
		Endpoint.publish(ADDRESS + "shippingservice", shipping);
		Endpoint.publish(ADDRESS + "supplierservice1", supplier1);
		Endpoint.publish(ADDRESS + "supplierservice2", supplier2);
		Endpoint.publish(ADDRESS + "warehouseservice", warehouse);
		Endpoint.publish(ADDRESS + "registryservice", registry);
		Endpoint.publish(ADDRESS + "customermanagementservice", customer);

		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setResourceClasses(CustomerManagementService.class);
		sf.setResourceProvider(CustomerManagementService.class,
				new SingletonResourceProvider(new CustomerManagementService()));
		sf.setAddress(ADDRESS);

		BindingFactoryManager manager = sf.getBus().getExtension(
				BindingFactoryManager.class);
		manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID,
				new JAXRSBindingFactory());

		JAXRSBindingFactory factory = new JAXRSBindingFactory();
		factory.setBus(sf.getBus());
		manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID,
				factory);
		
		sf.create();

		System.out.println("Services up. Hit enter to exit.");
		System.out.println("=============================================");
		
		System.out.println("Generated Products:");
		System.out.println("---------------------------------------------");
		for (StockStatus s : DataBackend.getInstance().getWarehouseList()) {
			System.out.println(s);
		}

		System.out.println("\nGenerated Customers:");
		System.out.println("---------------------------------------------");
		for (Customer c : DataBackend.getInstance().getCustomers()) {
			System.out.println(c);
		}

		// Wait for new line
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				System.in));
		stdIn.readLine();

		// Clear Database
		DataBackend.getInstance().clearDataBackend();

		System.out.println("=============================================");
		System.out.println("Services down.");
		
		// Shutdown
		sf.getBus().shutdown(true);
	}

	public static void main(String args[]) throws Exception {
		Server server = new Server();
	}

}
