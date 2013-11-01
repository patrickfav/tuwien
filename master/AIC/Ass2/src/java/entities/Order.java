package entities;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private String id;
    private Date orderDate;
    private List<Item> items;

    public Order() {
    }

    @XmlAttribute
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @XmlElement
    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    @Override
	public boolean equals(Object o) {
		if(o instanceof Order) {
			Order o1 = (Order) o;
			
			if(o1.getId().equals(this.getId()))
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
    
}
