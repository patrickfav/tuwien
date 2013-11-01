package client;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.namespace.QName;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;

import client.services.CustomerService;
import client.services.CustomerWrapperService;
import client.services.DeliveryStatus;
import client.services.Item;
import client.services.RegistryService;
import client.services.RegistryService_Service;
import client.services.ShippingService;
import client.services.ShippingService_Service;
import client.services.SupplierService;
import client.services.SupplierService_Service;
import client.services.SupplierService_Service1;
import client.services.UnknownAddressFault_Exception;
import client.services.UnknownProductFault_Exception;
import client.services.WarehouseService;
import client.services.WarehouseService_Service;
import data.DataBackend;
import entities.Customer;

/**
 * @author Favre-Bulle, Rauscha Advanced Internet Computing Assignment 1
 */

public class Client {

	private static final QName SHIPPING_SERVICE_NAME = new QName("http://infosys.tuwien.ac.at/aic10/ass1/dto/shipping","ShippingService");
	private static final QName WAREHOUSE_SERVICE_NAME = new QName("http://infosys.tuwien.ac.at/aic10/ass1/dto/shipping","WarehouseService");
	private static final QName REGISTRY_SERVICE_NAME = new QName("http://infosys.tuwien.ac.at/aic10/ass1/dto/shipping","RegistryService");
	private static final QName SUPPLIER_SERVICE_NAME = new QName("http://infosys.tuwien.ac.at/aic10/ass1/dto/shipping","SupplierService");
	private static final QName CUSTOMER_SERVICE_NAME = new QName("http://infosys.tuwien.ac.at/aic10/ass1/dto/shipping","CustomerService");
	
	private DataBackend testBackend;
	
	private ShippingService shippingPort;
	private WarehouseService warehousePort;
	private RegistryService registryPort;
	private SupplierService supplier1Port;
	private SupplierService supplier2Port;
	private CustomerWrapperService customerPort;
	
	private Client() {

		testBackend = DataBackend.getInstance();
		testBackend.fillDataBackend();
		
		URL wsdlURLShipping = ShippingService_Service.WSDL_LOCATION;
		ShippingService_Service ss = new ShippingService_Service(wsdlURLShipping, SHIPPING_SERVICE_NAME);
		shippingPort = ss.getShippingPT();
		
		URL wsdlURLWarehouse = WarehouseService_Service.WSDL_LOCATION;
		WarehouseService_Service ws = new WarehouseService_Service(wsdlURLWarehouse, WAREHOUSE_SERVICE_NAME);
		warehousePort = ws.getWarehousePT();
		
		URL wsdlURLRegistry = RegistryService_Service.WSDL_LOCATION;
		RegistryService_Service rs = new RegistryService_Service(wsdlURLRegistry, REGISTRY_SERVICE_NAME);
		registryPort = rs.getRegistryPT();
		
		URL wsdlURLSupplier1 = SupplierService_Service1.WSDL_LOCATION;
		SupplierService_Service1 sss1 = new SupplierService_Service1(wsdlURLSupplier1, SUPPLIER_SERVICE_NAME);
		supplier1Port = sss1.getSupplierPT();
		
		URL wsdlURLSupplier2 = SupplierService_Service.WSDL_LOCATION;
		SupplierService_Service sss2 = new SupplierService_Service(wsdlURLSupplier2, SUPPLIER_SERVICE_NAME);
		supplier2Port = sss2.getSupplierPT();
		
		URL wsdlURLCustomer = CustomerService.WSDL_LOCATION;
		CustomerService cs = new CustomerService(wsdlURLCustomer, CUSTOMER_SERVICE_NAME);
		customerPort = cs.getCustomerManagementPT();
		
	}

	public static void main(String args[]) throws Exception {
		
		Client client = new Client();
		
		// SOAP Shipping Test
		client.executeShippingTest();
		
		// SOAP Warehouse Check Availability Test
		client.executeWarehouseCheckAvailabilityTest();
		
		// SOAP Warehouse Order Test
		client.executeWarehouseOrderTest();
		
		// SOAP Supplier 1 Order Test
		client.executeSupplier1OrderTest();
		
		// SOAP Supplier 2 Order Test
		client.executeSupplier2OrderTest();
		
		// SOAP Registry Test
		client.executeRegistryTest();
		
		// RESTFUL Get Customer Test
		client.executeGetCustomerTest();
		
		// RESTFUL Get Customer Test
		client.executeUpdateCustomerTest();

		
		client.executeAddCustomerTest();

		
		// RESTFUL Update Account Customer Test
		client.executeUpdateAccountCustomerTest();
		
		// RESTFUL Delete Customer Test
		client.executeDeleteCustomerTest();

	}

	private void executeWarehouseCheckAvailabilityTest() {
		System.out.println("=============================================");
		System.out.println("Check Warehouse for availability");
		entities.Product product = DataBackend.getInstance().getWarehouseList().get(0).getProduct();
		entities.Product product1 = DataBackend.getInstance().getWarehouseList().get(1).getProduct();
		entities.Product product2 = DataBackend.getInstance().getSupplier1List().get(2).getProduct();
		DeliveryStatus d1 = warehousePort.checkAvailability(convertProduct(product), 1);
		DeliveryStatus d2 = warehousePort.checkAvailability(convertProduct(product1), 100);
		DeliveryStatus d3 = warehousePort.checkAvailability(convertProduct(product2), 1);
		
		System.out.println("1x "+product+": "+d1.isAvailable()+" - "+d1.getDeliveryTime()+" days (available)");
		System.out.println("100x "+product1+": "+d2.isAvailable()+" - "+d2.getDeliveryTime()+" days (amount too high)");
		System.out.println("1x "+product2+": "+d3.isAvailable()+" - "+d3.getDeliveryTime()+" days (not in warehouse)");
	}
	
	private void executeWarehouseOrderTest() {
		System.out.println("=============================================");
		System.out.println("Order Products from Warehouse");
		entities.Product product = DataBackend.getInstance().getWarehouseList().get(0).getProduct();
		System.out.println("Order 5x " +  product.getName() +" from Warehouse:");
		try {
			System.out.println("Total: € " + warehousePort.order(convertProduct(product), 5));
		} catch (UnknownProductFault_Exception e) {
			System.out.println("UnknownProductFault_Exception");
		}
	}
	
	private void executeSupplier1OrderTest() {
		System.out.println("=============================================");
		System.out.println("Order Products from Supplier 1");
		entities.Product product = DataBackend.getInstance().getSupplier1List().get(1).getProduct();
		System.out.println("Order 5x " +  product.getName() +" from Supplier 1:");
		try {
			System.out.println("Total: € " + supplier1Port.order(convertProduct(product), 5));
		} catch (UnknownProductFault_Exception e) {
			System.out.println("UnknownProductFault_Exception");
		}
	}
	
	private void executeSupplier2OrderTest() {
		System.out.println("=============================================");
		System.out.println("Order Products from Supplier 2");
		entities.Product product = DataBackend.getInstance().getSupplier2List().get(0).getProduct();
		System.out.println("Order 5x " +  product.getName() +" from Supplier 2:");
		try {
			System.out.println("Total: € " + supplier2Port.order(convertProduct(product), 5));
		} catch (UnknownProductFault_Exception e) {
			System.out.println("UnknownProductFault_Exception");
		}
	}

	private void executeRegistryTest() {
		System.out.println("=============================================");
		System.out.println("Test Supplier Registry");
		
		// Create Product
		System.out.println("Product should be delivered by Supplier1:");
		entities.Product product = DataBackend.getInstance().getSupplier1List().get(0).getProduct();
		try {
			System.out.println(registryPort.getProductSupplier(convertProduct(product).getId()).getAddress());
		} catch (UnknownProductFault_Exception e) {
			System.out.println("UnknownProductFault_Exception");
		}
		
		// Create Product
		System.out.println("Product should be delivered by Supplier2:");
		entities.Product product3 = DataBackend.getInstance().getSupplier2List().get(0).getProduct();
		try {
			System.out.println(registryPort.getProductSupplier(convertProduct(product3).getId()).getAddress());
		} catch (UnknownProductFault_Exception e) {
			System.out.println("UnknownProductFault_Exception");
		}
		
		// Create Product
		System.out.println("Invalid Product should throw UnknownProductFault:");
		entities.Product product2 = DataBackend.getInstance().getWarehouseList().get(0).getProduct();
		try {
			System.out.println(registryPort.getProductSupplier(convertProduct(product2).getId()).getAddress());
		} catch (UnknownProductFault_Exception e) {
			System.out.println("UnknownProductFault_Exception");
		}
	}

	private void executeShippingTest() throws UnknownAddressFault_Exception, UnknownProductFault_Exception {
		System.out.println("=============================================");
		System.out.println("Ship Items to this Customer");
		entities.Addresses address = DataBackend.getInstance().getCustomers().get(0).getAddresses().get(0);
		
		// Get 3 Products
		entities.Product product = DataBackend.getInstance().getWarehouseList().get(0).getProduct();
		Item i = createItem(convertProduct(product), 1);
		entities.Product product1 = DataBackend.getInstance().getWarehouseList().get(1).getProduct();
		Item i1 = createItem(convertProduct(product1), 1);
		entities.Product product2 = DataBackend.getInstance().getWarehouseList().get(2).getProduct();
		Item i2 = createItem(convertProduct(product2), 1);
		
		// Create List
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(i);
		items.add(i1);
		items.add(i2);
		
		// Ship items
		shippingPort.shipItems(items, convertAddress(address));
		
		System.out.println("Shipping: "+items);
		
		System.out.println("Test unknown Address");
		entities.Addresses unknownAddresse = DataBackend.getInstance().getCustomers().get(1).getAddresses().get(0);
		unknownAddresse.setId("123456");
		
		try {
			// Ship items
			shippingPort.shipItems(items, convertAddress(unknownAddresse));
		} catch(UnknownAddressFault_Exception e) {
			System.out.println("UnknownAddressFault_Exception");
		}
		
		System.out.println("Test unknown Product");
		product2.setId("12345");
		Item i3 = createItem(convertProduct(product2), 1);
		items.add(i3);
		try {
			// Ship items
			shippingPort.shipItems(items, convertAddress(address));
		} catch(UnknownProductFault_Exception e) {
			System.out.println("UnknownProductFault_Exception");
		}
	}

	/**
	 * Executes getCustomer Test
	 * @throws Exception
	 */
	public void executeGetCustomerTest() throws Exception {
		System.out.println("=============================================");
		System.out.println("Sent HTTP GET request to query customer info");
		DataBackend db = DataBackend.getInstance();
		Customer c = db.getCustomers().get(0);
		URL url = new URL("http://localhost:9000/customerservice/customers/" + c.getId() + "/");
		InputStream in = url.openStream();
		System.out.println(getStringFromInputStream(in));
		System.out.println("Search nonexistant customer");
		url = new URL("http://localhost:9000/customerservice/customers/xxxxxx/");
		in = url.openStream();
		System.out.println(getStringFromInputStream(in));
	}
	
	public void executeUpdateCustomerTest() throws Exception {
		System.out.println("=============================================");
		System.out.println("Update Customer Test:");
		Customer c = DataBackend.getInstance().getCustomers().get(0);
		System.out.println("Original: "+c);
		System.out.println("Change name and update");
		c.setName("new name");
		customerPort.updateCustomer(convertCustomer(c));
		System.out.println("Get from WS: "+ customerPort.getCustomer(c.getId()).getName());
	}
	
	public void executeDeleteCustomerTest() throws Exception {
		System.out.println("=============================================");
		System.out.println("Delete Customer Test:");
		customerPort.deleteCustomer(DataBackend.getInstance().getCustomers().get(0).getId());
		System.out.println("Customer deleted");
	}
	
	public void executeUpdateAccountCustomerTest() throws Exception {
		System.out.println("=============================================");
		System.out.println("Update Account Test (Open Balance 34):");
		Customer c = DataBackend.getInstance().getCustomers().get(0);
		client.services.Customer cust = customerPort.getCustomer(c.getId());
		cust.setOpenBalance(new BigDecimal(34));
		System.out.println("Customer updated!\nNew Balance: " + cust.getOpenBalance());
	}
	
	public void executeAddCustomerTest() throws Exception {
		System.out.println("=============================================");
		System.out.println("Adding Customer Test:");
		client.services.Customer c = new client.services.Customer();
		c.setId("1234");
		c.setName("Peter Fox");
		c.setOpenBalance(new BigDecimal(12));
		System.out.println("Adding customer: "+c.getName()+" - "+c.getId()+" - "+c.getOpenBalance());
		customerPort.addCustomer(c);
		System.out.println("Added successfully");
		System.out.println("Getting from WS: "+customerPort.getCustomer("1234").getName());
	}
	
	/*
	 * PRIVATES *******************************************************************
	 */

	private client.services.Product convertProduct(entities.Product p) {

		client.services.Product clientP = new client.services.Product();
		clientP.setId(p.getId());
		clientP.setName(p.getName());
		clientP.setSingleUnitPrice(p.getSingleUnitPrice());
		return clientP;

	}

	private client.services.Addresses convertAddress(entities.Addresses a) {
		client.services.Addresses clientA = new client.services.Addresses();

		clientA.setCity(a.getCity());
		clientA.setDoor(a.getDoor());
		clientA.setHouse(a.getHouse());
		clientA.setId(a.getId());
		clientA.setIsBilling(a.isIsBilling());
		clientA.setIsOther(a.isIsOther());
		clientA.setIsShipping(a.isIsShipping());
		clientA.setStreet(a.getStreet());
		clientA.setZipCode(a.getZipCode());

		return clientA;
	}
	
	private client.services.Customer convertCustomer(entities.Customer c) {
		client.services.Customer custA = new client.services.Customer();
		
		custA.setId(c.getId());
		custA.setName(c.getName());
		custA.setOpenBalance(c.getOpenBalance());
		
		return custA;
	}
	
	private client.services.Item createItem(client.services.Product p,
			int quantity) {
		client.services.Item item = new client.services.Item();
		item.setProduct(p);
		item.setQuantity(quantity);

		return item;
	}
	
	private String getStringFromInputStream(InputStream in) throws Exception {
        CachedOutputStream bos = new CachedOutputStream();
        IOUtils.copy(in, bos);
        in.close();
        bos.close();
        return bos.getOut().toString();
    }
}
