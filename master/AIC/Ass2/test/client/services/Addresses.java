
package client.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addresses complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addresses">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="door" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="house" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="isBilling" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isOther" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isShipping" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="street" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="zipCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addresses", propOrder = {
    "city",
    "door",
    "house",
    "isBilling",
    "isOther",
    "isShipping",
    "street",
    "zipCode"
})
public class Addresses {

    protected String city;
    protected int door;
    protected int house;
    protected boolean isBilling;
    protected boolean isOther;
    protected boolean isShipping;
    protected String street;
    protected String zipCode;
    @XmlAttribute
    protected String id;

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the door property.
     * 
     */
    public int getDoor() {
        return door;
    }

    /**
     * Sets the value of the door property.
     * 
     */
    public void setDoor(int value) {
        this.door = value;
    }

    /**
     * Gets the value of the house property.
     * 
     */
    public int getHouse() {
        return house;
    }

    /**
     * Sets the value of the house property.
     * 
     */
    public void setHouse(int value) {
        this.house = value;
    }

    /**
     * Gets the value of the isBilling property.
     * 
     */
    public boolean isIsBilling() {
        return isBilling;
    }

    /**
     * Sets the value of the isBilling property.
     * 
     */
    public void setIsBilling(boolean value) {
        this.isBilling = value;
    }

    /**
     * Gets the value of the isOther property.
     * 
     */
    public boolean isIsOther() {
        return isOther;
    }

    /**
     * Sets the value of the isOther property.
     * 
     */
    public void setIsOther(boolean value) {
        this.isOther = value;
    }

    /**
     * Gets the value of the isShipping property.
     * 
     */
    public boolean isIsShipping() {
        return isShipping;
    }

    /**
     * Sets the value of the isShipping property.
     * 
     */
    public void setIsShipping(boolean value) {
        this.isShipping = value;
    }

    /**
     * Gets the value of the street property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the value of the street property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreet(String value) {
        this.street = value;
    }

    /**
     * Gets the value of the zipCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the value of the zipCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZipCode(String value) {
        this.zipCode = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
