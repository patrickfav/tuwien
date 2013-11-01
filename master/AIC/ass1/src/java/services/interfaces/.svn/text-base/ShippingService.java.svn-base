package services.interfaces;

import entities.Addresses;
import entities.Item;
import exceptions.UnknownAddressFault;
import exceptions.UnknownProductFault;
import java.util.UUID;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface ShippingService {

    public UUID ship_items(@WebParam(name = "items") Item[] items, @WebParam(name = "address") Addresses addres)
            throws UnknownAddressFault, UnknownProductFault;
}
