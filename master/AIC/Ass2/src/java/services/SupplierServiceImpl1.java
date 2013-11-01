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

@WebService(endpointInterface = "services.interfaces.supplier.SupplierService",
serviceName = "SupplierService", portName="SupplierPT", targetNamespace="http://infosys.tuwien.ac.at/aic10/ass1/dto/supplier")
public class SupplierServiceImpl1 extends SupplierServiceAbstract {
	
    public SupplierServiceImpl1() {
        this.printPrefix = "[SupplierServiceImpl1]: ";
    }

	@Override
	public Product search(Product p) {
		return DataBackend.getInstance().searchSupplier1(p);
	}
  
}
