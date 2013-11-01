package services;

import data.dao.ProductDao;
import entities.Product;
import exceptions.UnknownProductFault;
import java.math.BigDecimal;
import javax.jws.WebService;
import services.interfaces.SupplierService;

@WebService(endpointInterface = "services.SupplierServiceImpl2",serviceName = "SupplierService", portName="SupplierPT", targetNamespace="http://infosys.tuwien.ac.at/aic10/ass1/dto/supplier")
public class SupplierServiceImpl2 implements SupplierService {

    private String printPrefix = "[SupplierServiceImpl2]: ";

    public BigDecimal order(Product p, int amount) throws UnknownProductFault {
        Product product = ProductDao.getInstance().searchProduct(p);
        if(product != null) {
            BigDecimal total = product.getSingleUnitPrice().multiply(new BigDecimal(amount));
            System.out.println(printPrefix + "Total Price: € " + total);
            return total;
        } else {
           throw new UnknownProductFault();
        }
    }

}
