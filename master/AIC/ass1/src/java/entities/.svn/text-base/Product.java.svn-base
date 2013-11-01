package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product implements Serializable {

    @XmlAttribute
    private String id;

    @XmlElement
    private String name;
    @XmlElement
    private BigDecimal singleUnitPrice;

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSingleUnitPrice() {
        return singleUnitPrice;
    }

    public void setSingleUnitPrice(BigDecimal singleUnitPrice) {
        this.singleUnitPrice = singleUnitPrice;
    }

}
