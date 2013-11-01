package services.interfaces;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import entities.Addresses;
import entities.Item;
import exceptions.UnknownAddressFault;
import exceptions.UnknownProductFault;
import java.util.UUID;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * ShippingService Interface
 * Handles Shipping 
 */
@WebService(
		name= "ShippingService",
		targetNamespace = "http://infosys.tuwien.ac.at/aic10/ass1/dto/shipping")
public interface ShippingService {

	/**
	 * Ships Items to address
	 * @param items
	 * @param addres
	 * @return UUID
	 * @throws UnknownAddressFault
	 * @throws UnknownProductFault
	 */
    public UUID ship_items(@WebParam(name = "items") Item[] items, @WebParam(name = "address") Addresses addres)
            throws UnknownAddressFault, UnknownProductFault;
}
