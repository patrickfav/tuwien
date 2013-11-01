package data.helper;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import entities.Addresses;
import entities.Customer;
import entities.Item;
import entities.Product;
import entities.internal.StockStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    private Random r;

    // Addresses
    private String[] cities = new String[] {"Wien", "Salzburg", "Graz", "Innsbruck", "New York", "London", "Berlin", "Moskau", "Zurich", "Geneva","Vancouver","Auckland","Frankfurt"};
    private String[] streets = new String[] {"Argentinierstrasse", "Mozartplatz", "Bergstrasse", "Lichtengasse", "Domplatz", "Linzerstrasse", "Kuhngasse", "Roter Platz"};
    private String[] zipCodes = new String[] {"1010","1020","1030","1040","1050","1100", "2032", "3423", "4543", "5423", "6543", "7423", "8342","9467","9678"};

    // Customer
    private String[] names = new String[] {"Alexander Bandner","Uschi Fellner","Karl Zeiss","Albert Planck","Susanne Lorenz","Paula Resch","Dietmar Hoenes","Joachim Bauer","Ingeborg Hauser","Otto Pollack"};

    // Products
    private String[] products = new String[] {"Moby Dick", "War and Peace", "David Copperfield", "Laptop", "Car", "PC", "Barby", "Rambo", "TV", "Cat", "Penzil"};
    private BigDecimal[] prices = new BigDecimal[] {new BigDecimal(10),new BigDecimal(18),new BigDecimal(13),new BigDecimal(800),new BigDecimal(15000),new BigDecimal(600),new BigDecimal(5),new BigDecimal(12),new BigDecimal(400),new BigDecimal(60),new BigDecimal(3)};

    //CONSTRUCTOR
    public DataGenerator(int salt) {
        r = new Random(salt);
    }
    
    public DataGenerator() {
        r = new Random();
    }

    public Addresses getRandomAddress() {
        Addresses a = new Addresses();
        a.setId("a"+Math.abs(r.nextInt()));
        a.setCity(cities[r.nextInt(cities.length)]);
        a.setDoor(r.nextInt(30));
        a.setHouse(r.nextInt(100));
        a.setStreet(streets[r.nextInt(streets.length)]);
        a.setZipCode(zipCodes[r.nextInt(zipCodes.length)]);
        a.setIsBilling(r.nextBoolean());
        a.setIsOther(r.nextBoolean());
        a.setIsShipping(r.nextBoolean());
        return a;
    }

    public Customer getRandomCustomer() {
        Customer c = new Customer();
        c.setId("c"+Math.abs(r.nextInt()));
        c.setName(names[r.nextInt(names.length)]);
        c.setOpenBalance(new BigDecimal(0));
        
        List<Addresses> addresses = new ArrayList<Addresses>();
        addresses.add(getRandomAddress());
        c.setAddresses(addresses);
        
        return c;
    }

    public Product getRandomProduct() {
        Product p = new Product();
        int i = r.nextInt(products.length);
        p.setId("p"+Math.abs(r.nextInt()));
        p.setName(products[i]);
        p.setSingleUnitPrice(prices[i]);
        return p;
    }

    public List<Item> getRandomItems(int count) {
        List<Item> items = new ArrayList<Item>();
        for(int i = 0; i < count; i++) {
            Item it = new Item();
            it.setQuantity(1);
            it.setProduct(getRandomProduct());
            items.add(it);
        }
        return items;
    }
    
    public StockStatus getRandomStockStatus(int quanitity) {
    	return new StockStatus(getRandomProduct(),quanitity,r.nextInt(14));
    }
    
    public int nextInt(int i) {
    	return r.nextInt(i);
    }
}
