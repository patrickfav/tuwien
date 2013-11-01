package services.interfaces;

import entities.Product;
import exceptions.UnknownProductFault;
import java.math.BigDecimal;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface SupplierService {
    BigDecimal order(@WebParam(name="p") Product p, @WebParam(name="amount") int amount)
            throws UnknownProductFault;
}
