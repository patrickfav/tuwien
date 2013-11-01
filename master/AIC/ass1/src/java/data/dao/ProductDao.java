package data.dao;

import data.DataBackend;
import entities.Product;
import java.util.Iterator;
import java.util.Set;


public class ProductDao {

    private static ProductDao instance = null;
    private DataBackend db;

    public ProductDao() {
        db = DataBackend.getInstance();
    }

    public static ProductDao getInstance() {
        if (instance == null) {
            instance = new ProductDao();
        }
        return instance;
    }

    public void addProduct(Product p) {
        db.getProducts().add(p);
    }

    public Set<Product> getAllProducts() {
        return db.getProducts();
    }

    public Product searchProduct(Product p) {
        if(db.getProducts().contains(p)) {
            return p;
        } else {
            return null;
        }
    }

    public Product getProductById(String id) {
        Iterator it = db.getProducts().iterator();
        while(it.hasNext()) {
            Product p = (Product)it.next();
            if(p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public void deleteProduct(Product p) {
        db.getProducts().remove(p);
    }

    public void deleteAllProducts() {
        db.getProducts().clear();
    }
}
