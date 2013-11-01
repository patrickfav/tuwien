package services.interfaces;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import entities.Product;
import exceptions.UnknownProductFault;
import java.math.BigDecimal;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * SupplierService Interface
 * Handles Products
 */
@WebService(
		name= "SupplierService",
		targetNamespace="http://infosys.tuwien.ac.at/aic10/ass1/dto/shipping")
public interface SupplierService {
	
	/**
	 * Prints Product and total price
	 * @param p
	 * @param amount
	 * @return BigDecimal
	 * @throws UnknownProductFault
	 */
    public BigDecimal order(@WebParam(name="p") Product p, @WebParam(name="amount") int amount)
            throws UnknownProductFault;
}
