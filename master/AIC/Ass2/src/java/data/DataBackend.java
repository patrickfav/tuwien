package data;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import data.helper.DataGenerator;
import entities.Addresses;
import entities.Customer;
import entities.Product;
import entities.internal.StockStatus;

public class DataBackend {

	private static final int SALT = 1;
	private static final int MAX_QUANTITY = 5;
	
	private static DataBackend instance = null;
	
	private List<Customer> customers;
	private List<StockStatus> products;
	private List<StockStatus> products_1;
	private List<StockStatus> products_2;

	public DataBackend() {
		customers = Collections.synchronizedList(new ArrayList<Customer>());
		products = Collections.synchronizedList(new ArrayList<StockStatus>());
		products_1 = Collections.synchronizedList(new ArrayList<StockStatus>());
		products_2 = Collections.synchronizedList(new ArrayList<StockStatus>());
	}

	public static DataBackend getInstance() {
		if (instance == null) {
			instance = new DataBackend();
		}
		return instance;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	public List<StockStatus> getWarehouseList() {
		return products;
	}
	
	public List<StockStatus> getSupplier1List() {
		return products_1;
	}
	
	public List<StockStatus> getSupplier2List() {
		return products_2;
	}
	/**
	 * Adding to stocks
	 * @param s
	 */
	public void addToWarehouse(StockStatus s) {
		products.add(s);
	}
	
	public void addToSupplier1(StockStatus s) {
		products_1.add(s);
	}
	
	public void addToSupplier2(StockStatus s) {
		products_2.add(s);
	}
	
	/**
	 * Checks if Product is in the warehouse/supplier.
	 * Returns 0 if not existant, else the quantity;
	 * @param p
	 * @return
	 */
	public int checkProductInWarehouse(Product p) {
		StockStatus s = searchProducts(p,products);
		return getQuantityFromStock(s);
	}
	
	public int checkProductInSupplier1(Product p) {
		StockStatus s = searchProducts(p,products_1);
		return getQuantityFromStock(s);
	}
	
	public int checkProductInSupplier2(Product p) {
		StockStatus s = searchProducts(p,products_2);
		return getQuantityFromStock(s);
	}
	
	
	/**
	 * Decreases the quantity of the product in the stock.
	 * Returns the quantity of actual ship-able products.
	 * (eg if youd like to ship 3, and there are only 2
	 * available, it'll return 2)
	 * @param p
	 * @param quantity
	 * @return
	 */
	public int shipFromWarehouse(Product p, int quantity) {
		StockStatus s = searchProducts(p,products);
		if(s != null) {
			products.remove(s);
			int out = s.getQuantity()-quantity;
			if(out < 0) {
				s.setQuantity(0);
				products.add(s);
				return s.getQuantity();
			} else {
				s.setQuantity(s.getQuantity()-quantity);
				products.add(s);
				return out;
			}
		} else {
			return 0;
		}
	}
	public int shipFromSupplier1(Product p, int quantity) {
		StockStatus s = searchProducts(p,products_1);
		if(s != null) {
			products_1.remove(s);
			int out = s.getQuantity()-quantity;
			if(out < 0) {
				s.setQuantity(0);
				products_1.add(s);
				return s.getQuantity();
			} else {
				s.setQuantity(s.getQuantity()-quantity);
				products_1.add(s);
				return out;
			}
		} else {
			return 0;
		}
	}
	public int shipFromSupplier2(Product p, int quantity) {
		StockStatus s = searchProducts(p,products_2);
		if(s != null) {
			products_2.remove(s);
			int out = s.getQuantity()-quantity;
			if(out < 0) {
				s.setQuantity(0);
				products_2.add(s);
				return s.getQuantity();
			} else {
				s.setQuantity(s.getQuantity()-quantity);
				products_2.add(s);
				return out;
			}
		} else {
			return 0;
		}
	}
	/**
	 * Search Methods
	 */
	
	public StockStatus searchWarehouse(Product p) {
		StockStatus s = searchProducts(p,products);
		if(s != null) {
			return s;
		}
		return null;
	}
	public Product searchSupplier1(Product p) {
		StockStatus s = searchProducts(p,products_1);
		if(s != null) {
			return s.getProduct();
		}
		return null;
	}
	public Product searchSupplier2(Product p) {
		StockStatus s = searchProducts(p,products_2);
		if(s != null) {
			return s.getProduct();
		}
		return null;
	}
	/**
	 * Delete methods
	 * @param p
	 */
	public void deleteProductFromWarehouse(Product p) {
		StockStatus s = searchProducts(p,products);
		if(s != null) {
			products.remove(s);
		}
	}
	
	public void deleteProductFromSupplier1(Product p) {
		StockStatus s = searchProducts(p,products_1);
		if(s != null) {
			products_1.remove(s);
		}
	}
	
	public void deleteProductFromSupplier2(Product p) {
		StockStatus s = searchProducts(p,products_2);
		if(s != null) {
			products_2.remove(s);
		}
	}
	
	public boolean checkAdress(Addresses a) {
		boolean out = false;
		for(Customer c: customers) {
			for(Addresses a1: c.getAddresses()) {
				if(a1.equals(a))
					out = true;
			}
		}
		
		return out;
	}
	
	public void fillDataBackend() {

		DataGenerator dg = new DataGenerator(SALT);
		
		addToSupplier1(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToSupplier1(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToSupplier1(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToSupplier1(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToSupplier1(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToSupplier1(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToSupplier1(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToSupplier1(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		
		addToSupplier2(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToSupplier2(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToSupplier2(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToSupplier2(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToSupplier2(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToSupplier2(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToSupplier2(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToSupplier2(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		
		addToWarehouse(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToWarehouse(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToWarehouse(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToWarehouse(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToWarehouse(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToWarehouse(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToWarehouse(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToWarehouse(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToWarehouse(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));
		addToWarehouse(dg.getRandomStockStatus(dg.nextInt(MAX_QUANTITY)));

		// Generate Customers
		customers.add(dg.getRandomCustomer());
		customers.add(dg.getRandomCustomer());
		customers.add(dg.getRandomCustomer());
		customers.add(dg.getRandomCustomer());
		customers.add(dg.getRandomCustomer());
		customers.add(dg.getRandomCustomer());
	}

	public void clearDataBackend() {
		customers.clear();
		products_1.clear();
		products_2.clear();
	}
	
	private int getQuantityFromStock(StockStatus s) {
		if(s == null) {
			return 0;
		} else {
			return s.getQuantity();
		}
	}
	
	private StockStatus searchProducts(Product p,List<StockStatus> list) {
		
		for(StockStatus s: list) {
			if(s.getProduct().equals(p)) {
				return s;
			}
		}
		
		return null;
	}
	
}
