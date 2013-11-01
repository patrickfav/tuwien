package models;

import javax.persistence.*;
import java.util.*;

@Entity
public class BuildingCosts {

	@Id
	@GeneratedValue
	private Integer level;

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getLevel() {
		return level;
	}

	private Integer neededWood;

	public void setNeededWood(Integer neededWood) {
		this.neededWood = neededWood;
	}

	public Integer getNeededWood() {
		return neededWood;
	}

	private Integer needIron;

	public void setNeedIron(Integer needIron) {
		this.needIron = needIron;
	}

	public Integer getNeedIron() {
		return needIron;
	}

}
