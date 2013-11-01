package data.dao;

import data.helper.DataGenerator;
import entities.Customer;
import entities.Product;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class ProductDaoTest {

    private ProductDao productDao;
    private DataGenerator dataGenerator;
    private Product p1;

    @Before
    public void setUp() {
        productDao = ProductDao.getInstance();
        dataGenerator = DataGenerator.getInstance();
        p1 = dataGenerator.getRandomProduct();
    }

    @Test
    public void addProductTest() {
        int beforeInsert = productDao.getAllProducts().size();
        productDao.addProduct(p1);
        int afterInsert = productDao.getAllProducts().size();
        assertEquals(beforeInsert+1, afterInsert);
    }

    @Test
    public void deleteProductTest() {
        productDao.addProduct(p1);
        int beforeDelete = productDao.getAllProducts().size();
        productDao.deleteProduct(p1);
        int afterDelete = productDao.getAllProducts().size();
        assertEquals(beforeDelete-1, afterDelete);
    }

    @Test
    public void getProductByIdTest() {
        productDao.addProduct(p1);
        Product p2 = productDao.getProductById(p1.getId());
        assertEquals(p1, p2);
    }
}
