package services;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import data.DataBackend;
import entities.internal.StockStatus;
import services.interfaces.RegistryService;
import java.util.HashMap;
import exceptions.UnknownProductFault;
import javax.jws.WebService;

import org.xmlsoap.schemas.ws._2004._08.addressing.AttributedURI;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;
import server.Server;

@WebService(endpointInterface = "services.interfaces.RegistryService", serviceName = "RegistryService", portName = "RegistryPT",
                targetNamespace = "http://infosys.tuwien.ac.at/aic10/ass1/dto/shipping")
public class RegistryServiceImpl implements RegistryService {

    private EndpointReferenceType supplier1;
    private EndpointReferenceType supplier2;
    private HashMap<String, EndpointReferenceType> products = new HashMap<String, EndpointReferenceType>();

    public RegistryServiceImpl() {
        supplier1 = new EndpointReferenceType();
        supplier2 = new EndpointReferenceType();

        AttributedURI uri_supplier1 = new AttributedURI();
        uri_supplier1.setValue(Server.ADDRESS + "supplier1?wsdl");
        supplier1.setAddress(uri_supplier1);

        AttributedURI uri_supplier2 = new AttributedURI();
        uri_supplier2.setValue(Server.ADDRESS + "supplier2?wsdl");
        supplier2.setAddress(uri_supplier2);

        for (StockStatus s : DataBackend.getInstance().getSupplier1List()) {
            products.put(s.getProduct().getId(), supplier1);
        }

        for (StockStatus s : DataBackend.getInstance().getSupplier2List()) {
            products.put(s.getProduct().getId(), supplier2);
        }
    }

    public EndpointReferenceType getProductSupplier(String productId) throws UnknownProductFault {
        EndpointReferenceType tmp = products.get(productId);
        if (tmp == null) {
            throw new UnknownProductFault();
        } else {
            return tmp;
        }
    }
}
