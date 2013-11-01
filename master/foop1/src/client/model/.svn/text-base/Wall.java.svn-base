package client.model;

import java.io.Serializable;
import java.util.UUID;

import client.model.enums.GameColor;

public class Wall implements Serializable{
	
	private static final long serialVersionUID = 2305273572387917119L;
	
	private String id;
	private String gameMapId;
	
	private int x; //x-position
	private int y; //y-position
	
	private GameColor color;
	
	
	public Wall(int x,int y, String gameMapId) {
		id = UUID.randomUUID().toString();
		color = GameColor.NONE;
		this.gameMapId = gameMapId;
		this.x=x;
		this.y=y;
	}
	
	
	public String getGameMapId() {
		return gameMapId;
	}

	public void setGameMapId(String gameMapId) {
		this.gameMapId = gameMapId;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public GameColor getColor() {
		return color;
	}

	public void setColor(GameColor color) {
		this.color = color;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return color.toString() +" Wall ("+x+","+y+")";
	}
	
}
