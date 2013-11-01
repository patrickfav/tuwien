package swag.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LongEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1949369977370651979L;

	@Id
	@GeneratedValue
	private Long id;
	
	private Long value;
	
	public LongEntity() {
	}
	
	public LongEntity(Long val) {
		value = val;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "LEnt:"+String.valueOf(value);
	}
	
}
