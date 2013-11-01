package entities;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Addresses implements Serializable {

    @XmlAttribute
    private String id;
    @XmlElement
    private String street;
    @XmlElement
    private String city;
    @XmlElement
    private int house;
    @XmlElement
    private int door;
    @XmlElement
    private String zipCode;
    @XmlElement
    private boolean isShipping;
    @XmlElement
    private boolean isBilling;
    @XmlElement
    private boolean isOther;

    public Addresses() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getDoor() {
        return door;
    }

    public void setDoor(int door) {
        this.door = door;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsBilling() {
        return isBilling;
    }

    public void setIsBilling(boolean isBilling) {
        this.isBilling = isBilling;
    }

    public boolean isIsOther() {
        return isOther;
    }

    public void setIsOther(boolean isOther) {
        this.isOther = isOther;
    }

    public boolean isIsShipping() {
        return isShipping;
    }

    public void setIsShipping(boolean isShipping) {
        this.isShipping = isShipping;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String toString() {
        String out = street + "/" +house + "/" + door + zipCode +" "+city;
        return out;
    }

    public boolean checkAdress(){
        if(street == null ||street.equals("") || city == null || city.equals("") || zipCode == null  || zipCode.equals(""))
            return false;

        return true;
    }
}
