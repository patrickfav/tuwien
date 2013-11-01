package data.helper;

import entities.Addresses;
import entities.Customer;
import entities.Item;
import entities.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DataGenerator {

    private static DataGenerator instance = null;

    private Random r;

    // Addresses
    private String[] cities = new String[] {"Wien", "Salzburg", "Graz", "Innsbruck", "New York", "London", "Berlin", "Moskau"};
    private String[] streets = new String[] {"Argentinierstrasse", "Mozartplatz", "Bergstrasse", "Lichtengasse", "Domplatz", "Linzerstrasse", "Kuhngasse", "Roter Platz"};
    private String[] zipCodes = new String[] {"1100", "2032", "3423", "4543", "5423", "6543", "7423", "8342"};
    private Boolean[] bools = new Boolean[] {true, false};

    // Customer
    private String[] names = new String[] {"Alexander Bandner","Uschi Fellner","Karl Zeiss","Albert Planck","Susanne Lorenz","Paula Resch","Dietmar Hoenes","Joachim Bauer","Ingeborg Hauser","Otto Pollack"};

    // Products
    private String[] products = new String[] {"Moby Dick", "War and Peace", "David Copperfield", "Laptop", "Car", "PC", "Barby", "Rambo", "TV", "Cat", "Penzil"};
    private BigDecimal[] prices = new BigDecimal[] {new BigDecimal(10),new BigDecimal(18),new BigDecimal(13),new BigDecimal(800),new BigDecimal(15000),new BigDecimal(600),new BigDecimal(5),new BigDecimal(12),new BigDecimal(400),new BigDecimal(60),new BigDecimal(3)};

    public DataGenerator() {
        r = new Random();
    }

    public static DataGenerator getInstance() {
        if (instance == null) {
            instance = new DataGenerator();
        }
        return instance;
    }

    public Addresses getRandomAddress() {
        Addresses a = new Addresses();
        a.setId(UUID.randomUUID().toString());
        a.setCity(cities[r.nextInt(cities.length)]);
        a.setDoor(r.nextInt(30));
        a.setHouse(r.nextInt(100));
        a.setStreet(streets[r.nextInt(streets.length)]);
        a.setZipCode(zipCodes[r.nextInt(zipCodes.length)]);
        a.setIsBilling(bools[r.nextInt(bools.length)]);
        a.setIsOther(bools[r.nextInt(bools.length)]);
        a.setIsShipping(bools[r.nextInt(bools.length)]);
        return a;
    }

    public Customer getRandomCustomer() {
        Customer c = new Customer();
        c.setId(UUID.randomUUID().toString());
        c.setName(names[r.nextInt(names.length)]);

        List<Addresses> addresses = new ArrayList<Addresses>();
        addresses.add(getRandomAddress());
        c.setAddresses(addresses);
        
        return c;
    }

    public Product getRandomProduct() {
        Product p = new Product();
        int i = r.nextInt(products.length);
        p.setId(UUID.randomUUID().toString());
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

}
