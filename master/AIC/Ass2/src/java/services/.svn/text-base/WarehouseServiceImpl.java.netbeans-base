package services;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import data.DataBackend;
import entities.DeliveryStatus;
import entities.Product;
import entities.internal.StockStatus;
import exceptions.UnknownProductFault;
import java.math.BigDecimal;

import javax.jws.WebService;
import services.interfaces.warehouse.WarehouseService;

@WebService(endpointInterface = "services.interfaces.warehouse.WarehouseService", serviceName = "WarehouseService", portName = "WarehousePT", targetNamespace = "http://infosys.tuwien.ac.at/aic10/ass1/dto/warehouse")
public class WarehouseServiceImpl implements WarehouseService {

	protected String printPrefix;

	public WarehouseServiceImpl() {
		this.printPrefix = "[WarehouseServiceImpl]: ";
	}

	public DeliveryStatus check_availability(Product product, int amount) {
		System.out.println("=============================================");
		StockStatus ss = searchStockStatus(product);
		DeliveryStatus ds = new DeliveryStatus();
		if (ss != null) {
			ds.setDelivery_time(ss.getDelivery_time());
			if (DataBackend.getInstance().checkProductInWarehouse(product) >= amount) {
				ds.setAvailable(true);
				System.out.println(printPrefix + "Product with ID " + product.getId() + " available! Delivery Time: " + ss.getDelivery_time());
			} else {
				ds.setAvailable(false);
				System.out.println(printPrefix + "Product with ID " + product.getId() + " is not available! Delivery Time: " + ss.getDelivery_time());
			}
			return ds;
		}
		ds.setAvailable(false);
		ds.setDelivery_time(0);
		return ds;
	}

	public BigDecimal order(Product p, int amount) throws UnknownProductFault {
		StockStatus status = searchStockStatus(p);
		Product product = status.getProduct();
		System.out.println("=============================================");
		if (product != null) {
			BigDecimal total = product.getSingleUnitPrice().multiply(
					new BigDecimal(amount));
			System.out.println(printPrefix + "Total Price: € " + total);
			return total;
		} else {
			throw new UnknownProductFault();
		}
	}

	private StockStatus searchStockStatus(Product p) {
		return DataBackend.getInstance().searchWarehouse(p);
	}
}
