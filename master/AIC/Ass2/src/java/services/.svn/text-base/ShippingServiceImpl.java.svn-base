package services;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import data.DataBackend;
import entities.Addresses;
import entities.Item;
import entities.Product;
import entities.internal.StockStatus;
import exceptions.UnknownAddressFault;
import exceptions.UnknownProductFault;
import java.util.Date;
import java.util.UUID;
import javax.jws.WebService;
import services.interfaces.shipping.ShippingService;

@WebService(endpointInterface = "services.interfaces.shipping.ShippingService", serviceName = "ShippingService", portName = "ShippingPT", targetNamespace = "http://infosys.tuwien.ac.at/aic10/ass1/dto/shipping")
public class ShippingServiceImpl implements ShippingService {

	private String printPrefix = "[ShippingServiceImpl]: ";

	@Override
	public UUID ship_items(Item[] items, Addresses address)
			throws UnknownAddressFault, UnknownProductFault {

		System.out.println("=============================================");

                
		if (address == null || !address.checkAdress() || !DataBackend.getInstance().checkAdress(address)) {
			throw new UnknownAddressFault();
		}

		if (items == null || items.length == 0) {
			throw new UnknownProductFault();
		}
		
		//check if product exists
		for(Item i: items) {
			if((DataBackend.getInstance().searchWarehouse(i.getProduct()) == null)
				&&
			(DataBackend.getInstance().searchSupplier1(i.getProduct()) == null)
				&&
			(DataBackend.getInstance().searchSupplier2(i.getProduct()) == null))
				throw new UnknownProductFault();
		}
		
		System.out.println(printPrefix + new Date());
		String itemsString = "";
		for (int i = 0; i < items.length; i++) {
                        Product p;
                        StockStatus s = DataBackend.getInstance().searchWarehouse(items[i].getProduct());
                        if(s == null) {
                            p = DataBackend.getInstance().searchSupplier2(items[i].getProduct());
                        } else {
                            p = s.getProduct();
                        }

			itemsString += " " + p;
			// DataBackend.getInstance().deleteProduct(items[i].getProduct());
		}
		System.out.println(printPrefix + "Sending Items: " + itemsString);
		System.out.println(printPrefix + "To Address: " + address);

		return UUID.randomUUID();
	}
}
