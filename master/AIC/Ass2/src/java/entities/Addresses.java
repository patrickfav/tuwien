package entities;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Addresses implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String street;
	private String city;
	private int house;
	private int door;
	private String zipCode;
	private boolean isShipping;
	private boolean isBilling;
	private boolean isOther;

	public Addresses() {
	}

	@XmlElement
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@XmlElement
	public int getDoor() {
		return door;
	}

	public void setDoor(int door) {
		this.door = door;
	}

	@XmlElement
	public int getHouse() {
		return house;
	}

	public void setHouse(int house) {
		this.house = house;
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public boolean isIsBilling() {
		return isBilling;
	}

	public void setIsBilling(boolean isBilling) {
		this.isBilling = isBilling;
	}

	@XmlElement
	public boolean isIsOther() {
		return isOther;
	}

	public void setIsOther(boolean isOther) {
		this.isOther = isOther;
	}

	@XmlElement
	public boolean isIsShipping() {
		return isShipping;
	}

	public void setIsShipping(boolean isShipping) {
		this.isShipping = isShipping;
	}

	@XmlElement
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@XmlElement
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String toString() {
		String out = id + " " + street + " " + house + "/" + door + " "
				+ zipCode + " " + city;
		return out;
	}

	public boolean checkAdress() {
		if (street == null || street.equals("") || city == null
				|| city.equals("") || zipCode == null || zipCode.equals(""))
			return false;

		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Addresses) {
			Addresses p = (Addresses) o;
			
			if(p.getId().equals(this.getId()))
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
