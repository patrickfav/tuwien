package services;

import entities.Product;
import exceptions.UnknownProductFault;
import java.math.BigDecimal;
import javax.jws.WebService;
import services.interfaces.WarehouseService;

@WebService(endpointInterface = "services.WarehouseServiceImpl",serviceName = "WarehouseService", portName="WarehousePT", targetNamespace="http://infosys.tuwien.ac.at/aic10/ass1/dto/warehouse")
public class WarehouseServiceImpl implements WarehouseService {

    public boolean check_availability(Product product, int amount) {

        return true;
    }

    public BigDecimal order(Product p, int amount) throws UnknownProductFault {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
