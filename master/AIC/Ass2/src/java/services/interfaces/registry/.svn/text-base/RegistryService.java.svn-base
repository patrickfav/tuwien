package services.interfaces.registry;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import exceptions.UnknownProductFault;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;

/**
 * RegistryService Interface
 * Handles Supplier 
 */
@WebService(name= "RegistryService", targetNamespace = "http://infosys.tuwien.ac.at/aic10/ass1/dto/registry")
public interface RegistryService {

	/**
	 * Returns the right Supplier for an product ID
	 * @param productId
	 * @return EndpointReferenceType
	 * @throws UnknownProductFault
	 */
    public EndpointReferenceType getProductSupplier(
            @WebParam(name = "product") String productId) throws UnknownProductFault;
}
