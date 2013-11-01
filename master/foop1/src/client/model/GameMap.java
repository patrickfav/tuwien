package client.model;

import java.io.Serializable;
import java.util.UUID;

public class GameMap implements Serializable {
	private static final long serialVersionUID = 7703773153669476972L;
	
	private String id;
	private String name;
	
	private long roundsLimit; //how many rounds will the game be
	private long currentRound; //current round of the game
	
	private int height; //height or y-axis
	private int width; //width or x-axis
	
	private double wallPercentage; // how many percentage of the map are filled with walls
	
	public GameMap() {
		id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getRoundsLimit() {
		return roundsLimit;
	}
	public void setRoundsLimit(long roundsLimit) {
		this.roundsLimit = roundsLimit;
	}
	public long getCurrentRound() {
		return currentRound;
	}
	public void setCurrentRound(long currentRound) {
		this.currentRound = currentRound;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWallPercentage(double wallPercentage) {
		this.wallPercentage = wallPercentage;
	}

	public double getWallPercentage() {
		return wallPercentage;
	}
	
	@Override
	public String toString() {
		return name +" "+width+"x"+height+" ("+currentRound+"/"+roundsLimit+")";
	}
	
}
