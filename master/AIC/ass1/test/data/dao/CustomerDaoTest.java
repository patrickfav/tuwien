package data.dao;

import data.helper.DataGenerator;
import entities.Customer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class CustomerDaoTest {

    private CustomerDao customerDao;
    private DataGenerator dataGenerator;
    private Customer c1;

    @Before
    public void setUp() {
        customerDao = CustomerDao.getInstance();
        dataGenerator = DataGenerator.getInstance();
        c1 = dataGenerator.getRandomCustomer();
    }

    @Test
    public void addCustomerTest() {
        int beforeInsert = customerDao.getAllCustomers().size();
        customerDao.addCustomer(c1);
        int afterInsert = customerDao.getAllCustomers().size();
        assertEquals(beforeInsert+1, afterInsert);
    }

    @Test
    public void deleteCustomerTest() {
        customerDao.addCustomer(c1);
        int beforeDelete = customerDao.getAllCustomers().size();
        customerDao.deleteCustomer(c1);
        int afterDelete = customerDao.getAllCustomers().size();
        assertEquals(beforeDelete-1, afterDelete);
    }

    @Test
    public void getCustomerByIdTest() {
        customerDao.addCustomer(c1);
        Customer c2 = customerDao.getCustomerById(c1.getId());
        assertEquals(c1, c2);
    }
}
