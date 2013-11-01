package services.interfaces.warehouse;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import services.interfaces.supplier.SupplierService;
import services.interfaces.*;
import entities.DeliveryStatus;
import entities.Product;
import exceptions.UnknownProductFault;
import java.math.BigDecimal;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * WarehouseService Interface
 * Handles Availability
 */
@WebService(name= "WarehouseService", targetNamespace = "http://infosys.tuwien.ac.at/aic10/ass1/dto/warehouse")
public interface WarehouseService {
    
	/**
	 * Checks if Product is available
	 * @param product
	 * @param amount
	 * @return boolean
	 */
        @WebMethod(operationName = "check_availability")
        @WebResult(targetNamespace = "http://infosys.tuwien.ac.at/aic10/ass1/dto/warehouse", name = "warehouseAvailabilityResult")
	public DeliveryStatus check_availability(@WebParam(name="product") Product product, @WebParam(name="amount") int amount);

        @WebMethod(operationName = "order")
        @WebResult(targetNamespace = "http://infosys.tuwien.ac.at/aic10/ass1/dto/warehouse", name = "warehouseOrderResult")
        public BigDecimal order(@WebParam(name="p") Product p, @WebParam(name="amount") int amount)
            throws UnknownProductFault;
}
