package swag.models;

import javax.persistence.*;

import java.util.*;

import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class BuildingType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String image;
	
	private String smallImage;

	private Integer points;

	private Integer buildingTime;

	private Integer baseUpgradeTime;

	@ElementCollection(fetch = FetchType.EAGER)
	private Map<ResourceType,Long> resourceCost = new HashMap<ResourceType,Long>();

	private Double bonusFactor;

	private Integer maxLevel;

	public BuildingType() {
	}

	public void setId(Long id) {
		this.id = id;

	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public void setImage(String image) {
		this.image = image;

	}

	public String getImage() {
		return image;
	}

	public void setPoints(Integer points) {
		this.points = points;

	}

	public Integer getPoints() {
		return points;
	}

	public void setBuildingTime(Integer buildingTime) {
		this.buildingTime = buildingTime;

	}

	public Integer getBuildingTime() {
		return buildingTime;
	}

	public void setBaseUpgradeTime(Integer baseUpgradeTime) {
		this.baseUpgradeTime = baseUpgradeTime;

	}

	public Integer getBaseUpgradeTime() {
		return baseUpgradeTime;
	}

	/*
		
		public void setBaseCost(List<Resource> baseCost) {
			this.baseCost = baseCost;
		
		}
		
		public List<Resource> getBaseCost() {
			return baseCost;
		}
	 */

	public void setBonusFactor(Double bonusFactor) {
		this.bonusFactor = bonusFactor;

	}

	public Double getBonusFactor() {
		return bonusFactor;
	}

	public void setMaxLevel(Integer maxLevel) {
		this.maxLevel = maxLevel;

	}

	public Integer getMaxLevel() {
		return maxLevel;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof BuildingType) {
			return this.getId().equals(((BuildingType) cmp).getId());
		}
		return false;
	}

	public void setResourceCost(Map<ResourceType,Long> resourceCost) {
		this.resourceCost = resourceCost;
	}

	public Map<ResourceType,Long> getResourceCost() {
		return resourceCost;
	}
	
	public String getCostsFor(ResourceType type) {
		return resourceCost.get(type) + "";
	}
}
