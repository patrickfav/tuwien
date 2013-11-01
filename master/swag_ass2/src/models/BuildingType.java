package models;

import javax.persistence.*;
import java.util.*;

@Entity
public class BuildingType {

	@Id
	@GeneratedValue
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	private Integer output;

	public void setOutput(Integer output) {
		this.output = output;
	}

	public Integer getOutput() {
		return output;
	}

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@OneToMany()
	private List<BuildingType> buildingtype = new LinkedList<BuildingType>();

	public void setBuildingtype(List<BuildingType> buildingtype) {
		this.buildingtype = buildingtype;
	}

	public List<BuildingType> getBuildingtype() {
		return buildingtype;
	}

}
