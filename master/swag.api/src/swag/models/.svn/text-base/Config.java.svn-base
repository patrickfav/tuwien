package swag.models;

import javax.persistence.*;

import java.util.*;

import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class Config implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long tickTime;

	private Integer numberOfSquares;

	private Integer baseSquares;

	private Integer maxUsers;

	@Enumerated(EnumType.STRING)
	private ResourceType baseResources;

	private Double privilegedMapSquaresPercentage;

	private Double privilegedBaseSquaresPercentage;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Map<ResourceType,Long> startResourceStock = new HashMap<ResourceType,Long>(); //sets this amount to all resources when first entering the map
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Map<ResourceType,Long> baseProductionPerTick= new HashMap<ResourceType,Long>();//how many resources (all) for base production
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Map<ResourceType,Long> baseCosts = new HashMap<ResourceType,Long>(); //costs for building a new base
	
	@Id
	@GeneratedValue
	private Long id;

	public Config() {
	}
	
	
	public void setTickTime(Long tickTime) {
		//in ms
		this.tickTime = tickTime;
		
	}

	public Long getTickTime() {
		return tickTime;
	}

	public void setNumberOfSquares(Integer numberOfSquares) {
		this.numberOfSquares = numberOfSquares;

	}

	public Integer getNumberOfSquares() {
		return numberOfSquares;
	}

	public void setBaseSquares(Integer baseSquares) {
		this.baseSquares = baseSquares;

	}

	public Integer getBaseSquares() {
		return baseSquares;
	}

	public void setMaxUsers(Integer maxUsers) {
		this.maxUsers = maxUsers;

	}

	public Integer getMaxUsers() {
		return maxUsers;
	}

	public void setBaseResources(ResourceType baseResources) {
		this.baseResources = baseResources;

	}

	public ResourceType getBaseResources() {
		return baseResources;
	}

	public void setPrivilegedMapSquaresPercentage(
			Double privilegedMapSquaresPercentage) {
		this.privilegedMapSquaresPercentage = privilegedMapSquaresPercentage;

	}

	public Double getPrivilegedMapSquaresPercentage() {
		return privilegedMapSquaresPercentage;
	}

	public void setPrivilegedBaseSquaresPercentage(
			Double privilegedBaseSquaresPercentage) {
		this.privilegedBaseSquaresPercentage = privilegedBaseSquaresPercentage;

	}

	public Double getPrivilegedBaseSquaresPercentage() {
		return privilegedBaseSquaresPercentage;
	}

	public void setId(Long id) {
		this.id = id;

	}

	public Long getId() {
		return id;
	}

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof Config) {
			return this.getId().equals(((Config) cmp).getId());
		}
		return false;
	}


	public void setStartResourceStock(Map<ResourceType,Long> startResourceStock) {
		this.startResourceStock = startResourceStock;
	}


	public Map<ResourceType,Long> getStartResourceStock() {
		return startResourceStock;
	}


	public void setBaseProductionPerTick(Map<ResourceType,Long> baseProductionPerTick) {
		this.baseProductionPerTick = baseProductionPerTick;
	}


	public Map<ResourceType,Long> getBaseProductionPerTick() {
		return baseProductionPerTick;
	}


	public void setBaseCosts(Map<ResourceType,Long> baseCosts) {
		this.baseCosts = baseCosts;
	}


	public Map<ResourceType,Long> getBaseCosts() {
		return baseCosts;
	}
}
