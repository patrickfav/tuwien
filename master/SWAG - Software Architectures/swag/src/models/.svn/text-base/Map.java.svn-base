package models;

import javax.persistence.*;
import java.util.*;

@Entity
public class Map {

	@Id
	@GeneratedValue
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	@OneToMany()
	private List<MapSquare> mapSquares = new LinkedList<MapSquare>();

	public void setMapSquares(List<MapSquare> mapSquares) {
		this.mapSquares = mapSquares;
	}

	public List<MapSquare> getMapSquares() {
		return mapSquares;
	}

}
