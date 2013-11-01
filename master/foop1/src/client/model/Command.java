package client.model;

import java.io.Serializable;
import java.util.List;

import client.model.enums.CommandType;

public class Command implements Serializable{
	
	private static final long serialVersionUID = -7169622255717660806L;

	private CommandType type;
	private Pacman pacman; //optional
	private GameMap map; //optional
	private List<Pacman> pacmen; //optional
	
	public CommandType getType() {
		return type;
	}
	public void setType(CommandType type) {
		this.type = type;
	}
	public GameMap getMap() {
		return map;
	}
	public void setMap(GameMap map) {
		this.map = map;
	}
	public List<Pacman> getPacmen() {
		return pacmen;
	}
	public void setPacmen(List<Pacman> pacmen) {
		this.pacmen = pacmen;
	}
	public void setPacman(Pacman pacman) {
		this.pacman = pacman;
	}
	public Pacman getPacman() {
		return pacman;
	}
	
	
	
	
}
