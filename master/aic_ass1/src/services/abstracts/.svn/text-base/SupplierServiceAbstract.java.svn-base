package services.abstracts;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import entities.Product;
import exceptions.UnknownProductFault;
import java.math.BigDecimal;

import services.interfaces.SupplierService;

public abstract class SupplierServiceAbstract implements SupplierService {

    protected String printPrefix;
    
    public BigDecimal order(Product p, int amount) throws UnknownProductFault {
        Product product = search(p);
        System.out.println("=============================================");
        if(product != null) {
            BigDecimal total = product.getSingleUnitPrice().multiply(new BigDecimal(amount));
            System.out.println(printPrefix + "Total Price: € " + total);
            return total;
        } else {
           throw new UnknownProductFault();
        }
    }
    
    public abstract Product search(Product p);

}