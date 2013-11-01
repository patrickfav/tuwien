package swag.models;

import javax.persistence.*;

import java.util.*;

import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class MilitaryCreateListEntry implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int hashSalt = 3434543;
	
	@Id
	@GeneratedValue
	private Long id;

	private Integer count;
	
	@OneToOne
	private Troop troop;

	@Temporal(TemporalType.TIMESTAMP)
	private Date start_time;

	@Temporal(TemporalType.TIMESTAMP)
	private Date end_time;
	
	public MilitaryCreateListEntry() {
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Troop getTroop() {
		return troop;
	}

	public void setTroop(Troop troop) {
		this.troop = troop;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getCount() {
		return count;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getStart_time() {
		return start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof MilitaryCreateListEntry) {
			return this.getId().equals(((MilitaryCreateListEntry) cmp).getId());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return hashSalt+id.intValue();
	}
}
