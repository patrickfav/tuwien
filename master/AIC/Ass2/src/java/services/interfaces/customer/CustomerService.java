package services.interfaces.customer;

import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import entities.Customer;

@Path("/customerservice/")
@Produces("application/json")
public interface CustomerService {

	@GET
	@Path("/customers/{id}/")
	@Produces("application/json")
	public Customer getCustomer(@PathParam("id") String id);
	
	@PUT
    @Path("/customers/")
    @Consumes("application/json")
    public Response updateCustomer(Customer customer);
	
	@POST
    @Path("/customers/")
    @Consumes("application/json")
    public Response addCustomer(Customer customer);
	
	@DELETE
    @Path("/customers/{id}/")
    @Consumes("application/json")
    public Response deleteCustomer(@PathParam("id") String id);
	
	@PUT
    @Path("/customers/{message}/")
    @Consumes("application/json")
    public Response notifyC(Customer customer,@PathParam("message")String message);
	
	@PUT
    @Path("/customers/{valueChanged}/")
    @Consumes("application/json")
    public Response updateAccount(Customer customer,@PathParam("valueChanged")BigDecimal change);
}
