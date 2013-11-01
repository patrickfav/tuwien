package entities;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private BigDecimal singleUnitPrice;

	public Product() {
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public BigDecimal getSingleUnitPrice() {
		return singleUnitPrice;
	}

	public void setSingleUnitPrice(BigDecimal singleUnitPrice) {
		this.singleUnitPrice = singleUnitPrice;
	}

        @Override
	public String toString() {
		return id + " " + name + " " + singleUnitPrice + "euro";
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Product) {
			Product p = (Product) o;
			
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
