package swag.models;

import javax.persistence.*;
import java.util.*;
import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class BuildListEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToOne()
	private Building building;

	@OneToOne()
	private BaseSquare square;

	@Temporal(TemporalType.TIMESTAMP)
	private Date start_time;

	@Temporal(TemporalType.TIMESTAMP)
	private Date end_time;

	private Boolean isBuilding;

	@Id
	@GeneratedValue
	private Long id;

	public BuildListEntry() {
	}

	public void setBuilding(Building building) {
		this.building = building;

	}

	public Building getBuilding() {
		return building;
	}

	public void setSquare(BaseSquare square) {
		this.square = square;

	}

	public BaseSquare getSquare() {
		return square;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;

	}

	public Date getStart_time() {
		return start_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;

	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setIsBuilding(Boolean isBuilding) {
		this.isBuilding = isBuilding;

	}

	public Boolean getIsBuilding() {
		return isBuilding;
	}

	public void setId(Long id) {
		this.id = id;

	}

	public Long getId() {
		return id;
	}

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof BuildListEntry) {
			return this.getId().equals(((BuildListEntry) cmp).getId());
		}
		return false;
	}
}
