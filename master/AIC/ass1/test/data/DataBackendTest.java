package data;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataBackendTest {

    DataBackend db;

    @Before
    public void setUp() {
        db = DataBackend.getInstance();
        db.fillDataBackend();
    }

    @Test
    public void customersFilledTest() {     
        assertFalse(db.getCustomers().isEmpty());
    }

    @Test
    public void productsFilledTest() {
        assertFalse(db.getProducts().isEmpty());
    }

   @Test
   public void customersClearedTest() {
       db.clearDataBackend();
       assertTrue(db.getCustomers().isEmpty());
   }

   @Test
   public void productsClearedTest() {
       db.clearDataBackend();
       assertTrue(db.getProducts().isEmpty());
   }
}
