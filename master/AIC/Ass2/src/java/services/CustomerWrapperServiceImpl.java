package services;

import data.dao.CustomerDao;
import java.math.BigDecimal;

import javax.jws.WebService;

import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;

import entities.Customer;
import server.Server;
import services.interfaces.customer.CustomerService;
import services.interfaces.customer.CustomerWrapperService;

@WebService(endpointInterface = "services.interfaces.customer.CustomerWrapperService", serviceName = "CustomerService", portName = "CustomerManagementPT", targetNamespace = "http://infosys.tuwien.ac.at/aic10/ass1/dto/customer")
public class CustomerWrapperServiceImpl implements CustomerWrapperService {

	private CustomerService customerService;

	public CustomerWrapperServiceImpl() {

		JAXRSClientFactoryBean sf = new JAXRSClientFactoryBean();
		sf.setResourceClass(CustomerService.class);
		sf.setAddress(Server.ADDRESS);
		BindingFactoryManager manager = sf.getBus().getExtension(
				BindingFactoryManager.class);
		JAXRSBindingFactory factory = new JAXRSBindingFactory();
		factory.setBus(sf.getBus());
		manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID,
				factory);

		customerService = sf.create(CustomerService.class);
		WebClient.client(customerService).accept("application/json");
	}

	@Override
	public void addCustomer(Customer customer) {
		customerService.addCustomer(customer);
	}

	@Override
	public void deleteCustomer(String id) {
		customerService.deleteCustomer(id);
	}

	@Override
	public Customer getCustomer(String id) {
		return customerService.getCustomer(id);
	}

	@Override
	public void notifyC(String customer, String message) {
		customerService.notifyC(CustomerDao.getInstance().getCustomerById(customer),message);
	}

	@Override
	public void updateCustomer(Customer customer) {
		customerService.updateCustomer(customer);
	}

	@Override
	public void updateAccount(Customer customer, BigDecimal valueChanged) {
		customerService.updateAccount(customer,valueChanged);
	}

}
