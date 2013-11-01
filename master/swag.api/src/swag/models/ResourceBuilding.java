package swag.models;

import javax.persistence.*;
import java.util.*;
import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class ResourceBuilding extends BuildingType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private ResourceType ressource;

	private Long baseProduction;
	
	private Double levelFactor;
	
	private Double nextToResourceFactor;
	
	public ResourceBuilding() {
	}

	public void setRessource(ResourceType ressource) {
		this.ressource = ressource;

	}

	public ResourceType getRessource() {
		return ressource;
	}

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof ResourceBuilding) {
			return this.getId().equals(((ResourceBuilding) cmp).getId());
		}
		return false;
	}

	public void setBaseProduction(Long baseProduction) {
		this.baseProduction = baseProduction;
	}

	public Long getBaseProduction() {
		return baseProduction;
	}

	public void setLevelFactor(Double levelFactor) {
		this.levelFactor = levelFactor;
	}

	public Double getLevelFactor() {
		return levelFactor;
	}

	public void setNextToResourceFactor(Double nextToResourceFactor) {
		this.nextToResourceFactor = nextToResourceFactor;
	}

	public Double getNextToResourceFactor() {
		return nextToResourceFactor;
	}
}
