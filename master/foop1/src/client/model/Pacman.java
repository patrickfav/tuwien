package client.model;

import java.io.Serializable;
import java.util.UUID;

import client.model.enums.GameColor;
import client.model.enums.Movement;

public class Pacman implements Serializable {

	private static final long serialVersionUID = -510716863069279000L;
	
	private String id;
	
	private GameColor color;
	
	private String gameMapId;
	private long round; // the current round this pacman is in
	
	private int x; //x-position
	private int y; //y-position
	
	private long roundPoints; //points of this round
	private long overallPoints; //sum of points
	
	private boolean human; //if played by human or cpu
	private boolean dead; //if pacman is dead
	private boolean played; //if a player already plays this pacman
	
	public Pacman(GameColor color,String gameMapId, boolean human) {
		this.color = color;
		this.gameMapId = gameMapId;
		
		id = UUID.randomUUID().toString();
		round = 0;
		roundPoints = 0;
		overallPoints =0;
		dead =false;
		played =false;
		this.human = human;
	}
	
	public Pacman(Pacman p) {
		id = p.getId();
		color =p.getColor();
		gameMapId =p.getGameMapId();
		round = p.getRound();
		x=p.getX();
		y=p.getY();
		roundPoints=p.getRoundPoints();
		overallPoints=p.getOverallPoints();
		human=p.isHuman();
		dead=p.isDead();
		played=p.isPlayed();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GameColor getColor() {
		return color;
	}

	public void setColor(GameColor color) {
		this.color = color;
	}

	public String getGameMapId() {
		return gameMapId;
	}

	public void setGameMapId(String gameMapId) {
		this.gameMapId = gameMapId;
	}

	public long getRound() {
		return round;
	}

	public void setRound(long round) {
		this.round = round;
	}

	public long getRoundPoints() {
		return roundPoints;
	}

	public void setRoundPoints(long roundPoints) {
		this.roundPoints = roundPoints;
	}

	public long getOverallPoints() {
		return overallPoints;
	}

	public void setOverallPoints(long overallPoints) {
		this.overallPoints = overallPoints;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public void setPlayed(boolean played) {
		this.played = played;
	}

	public boolean isPlayed() {
		return played;
	}

	public void setHuman(boolean human) {
		this.human = human;
	}

	public boolean isHuman() {
		return human;
	}
	
	@Override
	public String toString() {
		return color.toString() +" Pacman ("+overallPoints+") R:"+round+(dead?"[dead] ":"")+(!human?"[cpu] ":"");
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
	
	public void translateMovement(Movement m) {
		if(m.equals(Movement.LEFT)) {
			x = x-1;
		} else if(m.equals(Movement.RIGHT)) {
			x = x+1;
		} else if(m.equals(Movement.UP)) {
			y = y-1;
		} else if(m.equals(Movement.DOWN)) {
			y = y+1;
		}
	}
}
