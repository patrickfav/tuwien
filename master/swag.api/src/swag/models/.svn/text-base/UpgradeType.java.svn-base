package swag.models;

import javax.persistence.*;
import java.util.*;
import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class UpgradeType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private Integer costPerLevel;

	private Integer upgardeTickTime;

	private Integer pointsPerLevel;

	private Integer maxLevel;

	@Enumerated(EnumType.STRING)
	private UpgradeTypeEnum type;

	public UpgradeType() {
	}

	public void setId(Long id) {
		this.id = id;

	}

	public Long getId() {
		return id;
	}

	public void setCostPerLevel(Integer costPerLevel) {
		this.costPerLevel = costPerLevel;

	}

	public Integer getCostPerLevel() {
		return costPerLevel;
	}

	public void setUpgardeTickTime(Integer upgardeTickTime) {
		this.upgardeTickTime = upgardeTickTime;

	}

	public Integer getUpgardeTickTime() {
		return upgardeTickTime;
	}

	public void setPointsPerLevel(Integer pointsPerLevel) {
		this.pointsPerLevel = pointsPerLevel;

	}

	public Integer getPointsPerLevel() {
		return pointsPerLevel;
	}

	public void setMaxLevel(Integer maxLevel) {
		this.maxLevel = maxLevel;

	}

	public Integer getMaxLevel() {
		return maxLevel;
	}

	public void setType(UpgradeTypeEnum type) {
		this.type = type;

	}

	public UpgradeTypeEnum getType() {
		return type;
	}

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof UpgradeType) {
			return this.getId().equals(((UpgradeType) cmp).getId());
		}
		return false;
	}
}
