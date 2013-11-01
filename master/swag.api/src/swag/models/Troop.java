package swag.models;

import javax.persistence.*;

import java.util.*;

import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class Troop implements Serializable {

	private static final long serialVersionUID = -2849668606052591419L;
	private static final int hashSalt = 8456293;
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String smallImage;
	
	private String miniImage;
	
	private Integer points;
	
	private Integer strength;

	private Integer speed;

	private Integer armor;

	private MilitaryType type;
	
	private Integer trainingTime;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Map<ResourceType,Long> resourceCost = new HashMap<ResourceType,Long>();

	public Troop() {
	}

	public void setId(Long id) {
		this.id = id;

	}

	public Long getId() {
		return id;
	}

	public void setStrength(Integer strength) {
		this.strength = strength;

	}

	public Integer getStrength() {
		return strength;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;

	}

	public Integer getSpeed() {
		return speed;
	}

	public void setArmor(Integer armor) {
		this.armor = armor;

	}

	public Integer getArmor() {
		return armor;
	}

	public void setType(MilitaryType type) {
		this.type = type;

	}

	public MilitaryType getType() {
		return type;
	}

	@Override
	public boolean equals(Object cmp) {
		//System.out.println("In equals");
		if (cmp instanceof Troop) {
			//System.out.println("Returning "+this.getId().equals(((Troop) cmp).getId()));
			return this.getId().equals(((Troop) cmp).getId());
		}
		//System.out.println("Returning false");
		return false;
	}
	
	@Override
	public int hashCode() {
		return hashSalt+id.intValue();
		
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getPoints() {
		return points;
	}

	public Map<ResourceType, Long> getResourceCost() {
		return resourceCost;
	}

	public void setResourceCost(Map<ResourceType, Long> resourceCost) {
		this.resourceCost = resourceCost;
	}
	
	public String getCostsFor(ResourceType type) {
		return resourceCost.get(type) + "";
	}

	public Integer getTrainingTime() {
		return trainingTime;
	}

	public void setTrainingTime(Integer trainingTime) {
		this.trainingTime = trainingTime;
	}

	public void setMiniImage(String miniImage) {
		this.miniImage = miniImage;
	}

	public String getMiniImage() {
		return miniImage;
	}
}
