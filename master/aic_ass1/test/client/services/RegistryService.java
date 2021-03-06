package client.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.3
 * Thu Nov 11 17:09:59 CET 2010
 * Generated source version: 2.2.3
 * 
 */
 
@WebService(targetNamespace = "http://infosys.tuwien.ac.at/aic10/ass1/dto/shipping", name = "RegistryService")
@XmlSeeAlso({ObjectFactory.class})
public interface RegistryService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getProductSupplier", targetNamespace = "http://infosys.tuwien.ac.at/aic10/ass1/dto/shipping", className = "client.services.GetProductSupplier")
    @ResponseWrapper(localName = "getProductSupplierResponse", targetNamespace = "http://infosys.tuwien.ac.at/aic10/ass1/dto/shipping", className = "client.services.GetProductSupplierResponse")
    @WebMethod
    public client.services.EndpointReferenceType getProductSupplier(
        @WebParam(name = "product", targetNamespace = "")
        java.lang.String product
    ) throws UnknownProductFault_Exception;
}
