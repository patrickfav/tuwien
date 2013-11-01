package services;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import data.DataBackend;
import entities.Product;

import javax.jws.WebService;
import services.abstracts.SupplierServiceAbstract;

@WebService(endpointInterface = "services.interfaces.SupplierService",serviceName = "SupplierService", portName="SupplierPT", targetNamespace="http://infosys.tuwien.ac.at/aic10/ass1/dto/shipping")
public class SupplierServiceImpl2 extends SupplierServiceAbstract {

    public SupplierServiceImpl2() {
        this.printPrefix = "[SupplierServiceImpl2]: ";
    }

	@Override
	public Product search(Product p) {
		return DataBackend.getInstance().searchSupplier2(p);
	}
}
