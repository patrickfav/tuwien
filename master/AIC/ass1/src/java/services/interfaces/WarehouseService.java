package services.interfaces;

import entities.Product;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface WarehouseService extends SupplierService {
    public boolean check_availability(@WebParam(name="product") Product product, @WebParam(name="amount") int amount);
}
