package swag.models;

import javax.persistence.*;
import java.util.*;
import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class BaseSquare implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne()
	private Building building;

	private Integer x;

	private Integer y;

	@OneToOne()
	private Resource resource;

	/*@ManyToMany(mappedBy="null")
		private List<ResourceType> ressourcesBonus = new LinkedList<ResourceType>();
	 */

	public BaseSquare() {
	}

	public void setId(Long id) {
		this.id = id;

	}

	public Long getId() {
		return id;
	}

	public void setBuilding(Building building) {
		this.building = building;

	}

	public Building getBuilding() {
		return building;
	}

	public void setX(Integer x) {
		this.x = x;

	}

	public Integer getX() {
		return x;
	}

	public void setY(Integer y) {
		this.y = y;

	}

	public Integer getY() {
		return y;
	}

	public void setResource(Resource resource) {
		this.resource = resource;

	}

	public Resource getResource() {
		return resource;
	}

	/*
		
		public void setRessourcesBonus(List<ResourceType> ressourcesBonus) {
			this.ressourcesBonus = ressourcesBonus;
		
		}
		
		public List<ResourceType> getRessourcesBonus() {
			return ressourcesBonus;
		}
	 */

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof BaseSquare) {
			return this.getId().equals(((BaseSquare) cmp).getId());
		}
		return false;
	}
}
