package server;

import javax.xml.ws.Endpoint;
import services.SupplierServiceImpl1;


public class Server {

    protected Server() throws Exception {
        System.out.println("Starting Server");
        SupplierServiceImpl1 supplierImplementor = new SupplierServiceImpl1();
        String supplierAddress = "http://localhost:9000/supplierService";
        Endpoint.publish(supplierAddress, supplierImplementor);

    }

    public static void main(String args[]) throws Exception {
        new Server();
        System.out.println("Server ready...");

        Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
    }

}
