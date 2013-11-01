package dst2.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PriceStep implements Serializable{

	private static final long serialVersionUID = 8762528986861432796L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="priceStepSequencer")
	private Long id;
	
	private int numberOfHistoricalJobs;
	
	private BigDecimal price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumberOfHistoricalJobs() {
		return numberOfHistoricalJobs;
	}

	public void setNumberOfHistoricalJobs(int numberOfHistoricalJobs) {
		this.numberOfHistoricalJobs = numberOfHistoricalJobs;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		String id_out = "";
		
		if(id != null)
			id_out= "[id:"+id+"] ";
		
		return id_out+"numJobs: "+numberOfHistoricalJobs+" price:"+price;
	}
	
}
