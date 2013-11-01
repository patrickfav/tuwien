package swag.models;

import javax.persistence.*;
import java.util.*;
import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class UpgradeListEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private UpgradeType type;

	private Integer levelTo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date start_time;

	@Temporal(TemporalType.TIMESTAMP)
	private Date end_time;

	public UpgradeListEntry() {
	}

	public void setId(Long id) {
		this.id = id;

	}

	public Long getId() {
		return id;
	}

	public void setType(UpgradeType type) {
		this.type = type;

	}

	public UpgradeType getType() {
		return type;
	}

	public void setLevelTo(Integer levelTo) {
		this.levelTo = levelTo;

	}

	public Integer getLevelTo() {
		return levelTo;
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

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof UpgradeListEntry) {
			return this.getId().equals(((UpgradeListEntry) cmp).getId());
		}
		return false;
	}
}
