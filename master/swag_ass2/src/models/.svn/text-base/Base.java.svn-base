package models;

import javax.persistence.*;
import java.util.*;

@Entity
public class Base {

	@OneToMany()
	private List<BaseSquare> baseSquares = new LinkedList<BaseSquare>();

	public void setBaseSquares(List<BaseSquare> baseSquares) {
		this.baseSquares = baseSquares;
	}

	public List<BaseSquare> getBaseSquares() {
		return baseSquares;
	}

	@Id
	@GeneratedValue
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	@OneToOne()
	private Resources resources;

	public void setResources(Resources resources) {
		this.resources = resources;
	}

	public Resources getResources() {
		return resources;
	}

}
