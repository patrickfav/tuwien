
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package client.services;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.3
 * Thu Nov 11 17:10:22 CET 2010
 * Generated source version: 2.2.3
 * 
 */

@javax.jws.WebService(
                      serviceName = "SupplierService",
                      portName = "SupplierPT",
                      targetNamespace = "http://infosys.tuwien.ac.at/aic10/ass1/dto/shipping",
                      wsdlLocation = "http://localhost:9000/supplierservice1?wsdl",
                      endpointInterface = "client.services.SupplierService")
                      
public class SupplierService1Impl implements SupplierService {

    private static final Logger LOG = Logger.getLogger(SupplierServiceImpl.class.getName());

    /* (non-Javadoc)
     * @see client.services.SupplierService#order(client.services.Product  p ,)int  amount )*
     */
    public java.math.BigDecimal order(client.services.Product p,int amount) throws UnknownProductFault_Exception    { 
        LOG.info("Executing operation order");
        System.out.println(p);
        System.out.println(amount);
        try {
            java.math.BigDecimal _return = new java.math.BigDecimal("0");
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new UnknownProductFault_Exception("UnknownProductFault...");
    }

}
