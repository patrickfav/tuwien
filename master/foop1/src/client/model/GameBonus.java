package client.model;

import java.io.Serializable;
import java.util.UUID;

import client.model.enums.BonusType;

public class GameBonus implements Serializable {
	private static final long serialVersionUID = 2804176621937187255L;
	
	private String id;
	private String gameMapId;
	
	private int x; //x-position
	private int y; //y-position
	
	private BonusType type;
	private long points; //what is it worth?
	

	public GameBonus(String gameMapId, int x, int y, BonusType type, long points) {
		super();
		id=UUID.randomUUID().toString();
		this.gameMapId = gameMapId;
		this.x = x;
		this.y = y;
		this.type = type;
		this.points = points;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public BonusType getType() {
		return type;
	}
	public void setType(BonusType type) {
		this.type = type;
	}
	public long getPoints() {
		return points;
	}
	public void setPoints(long points) {
		this.points = points;
	}
	
	@Override
	public String toString() {
		return type.toString() +" Gamebonus ["+points+"] ("+x+","+y+")";
	}
	
}
