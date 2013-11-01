package services.interfaces;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import entities.DeliveryStatus;
import entities.Product;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * WarehouseService Interface
 * Handles Availability
 */
@WebService(
		name= "WarehouseService",
		targetNamespace="http://infosys.tuwien.ac.at/aic10/ass1/dto/shipping")
public interface WarehouseService extends SupplierService {
    
	/**
	 * Checks if Product is available
	 * @param product
	 * @param amount
	 * @return boolean
	 */
	public DeliveryStatus check_availability(@WebParam(name="product") Product product, @WebParam(name="amount") int amount);
}
