/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import data.dao.ProductDao;
import entities.Addresses;
import entities.Item;
import exceptions.UnknownAddressFault;
import exceptions.UnknownProductFault;
import java.util.Date;
import java.util.UUID;
import javax.jws.WebService;
import services.interfaces.ShippingService;

/**
 *
 * @author PatrickF
 */
 @WebService(endpointInterface = "services.interfaces.ShippingService",serviceName = "ShippingService", portName="ShippingPT",
                targetNamespace="http://infosys.tuwien.ac.at/aic10/ass1/dto/shipping")
public class ShippingServiceImpl implements ShippingService{
    private String printPrefix = "[ShippingServiceImpl]: ";

    @Override
    public UUID ship_items(Item[] items, Addresses address)  throws UnknownAddressFault, UnknownProductFault{

        if(address == null || address.checkAdress()) {
            throw new UnknownAddressFault();
        }

        if(items == null || items.length==0) {
            throw new UnknownProductFault();
        }

        System.out.println(printPrefix + new Date());
        for (int i = 0; i < items.length; i++) {
            ProductDao.getInstance().deleteProduct(items[i].getProduct());
        }
        System.out.println(printPrefix + "Sending Items:" + items);
        System.out.println(printPrefix + "To Adress: " + address);

        return UUID.randomUUID();
    }
}
