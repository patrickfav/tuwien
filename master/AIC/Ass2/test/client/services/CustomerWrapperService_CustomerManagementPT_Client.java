
package client.services;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.3
 * Thu Nov 11 19:03:34 CET 2010
 * Generated source version: 2.2.3
 * 
 */

public final class CustomerWrapperService_CustomerManagementPT_Client {

    private static final QName SERVICE_NAME = new QName("http://infosys.tuwien.ac.at/aic10/ass1/dto/customer", "CustomerService");

    private CustomerWrapperService_CustomerManagementPT_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = CustomerService.WSDL_LOCATION;
        if (args.length > 0) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        CustomerService ss = new CustomerService(wsdlURL, SERVICE_NAME);
        CustomerWrapperService port = ss.getCustomerManagementPT();  
        
        {
        System.out.println("Invoking getCustomer...");
        java.lang.String _getCustomer_id = "";
        client.services.Customer _getCustomer__return = port.getCustomer(_getCustomer_id);
        System.out.println("getCustomer.result=" + _getCustomer__return);


        }
        {
        System.out.println("Invoking updateCustomer...");
        client.services.Customer _updateCustomer_customer = null;
        port.updateCustomer(_updateCustomer_customer);


        }
        {
        System.out.println("Invoking updateAccount...");
        client.services.Customer _updateAccount_customer = null;
        java.math.BigDecimal _updateAccount_valueChanged = new java.math.BigDecimal("0");
        port.updateAccount(_updateAccount_customer, _updateAccount_valueChanged);


        }
        {
        System.out.println("Invoking deleteCustomer...");
        java.lang.String _deleteCustomer_id = "";
        port.deleteCustomer(_deleteCustomer_id);


        }
        {
        System.out.println("Invoking addCustomer...");
        client.services.Customer _addCustomer_customer = null;
        port.addCustomer(_addCustomer_customer);


        }
        {
        System.out.println("Invoking notify...");
        java.lang.String _notify_customer = "";
        java.lang.String _notify_message = "";
        port.notify(_notify_customer, _notify_message);


        }

        System.exit(0);
    }

}