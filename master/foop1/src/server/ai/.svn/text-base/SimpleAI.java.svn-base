package server.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import client.model.GameMap;
import client.model.Pacman;
import client.model.Wall;
import client.model.enums.GameColor;
import client.model.enums.Movement;

public class SimpleAI {
	private static Logger log = Logger.getLogger("SimpleAI");
	private static Random r = new Random();
	
	/**
	 * This simple AI algorithm makes a pacman hunt down his eatable opponent.
	 * If this opponent is dead or further away from its eatable opponent,
	 * it runs away from it the pacman it can be eaten by
	 * @param possibleMov
	 * @param my
	 * @param all
	 * @return
	 */
	public static Movement nextMovement( List<Movement> possibleMov,Pacman my,List<Pacman> all) {
		log.info("Simple AI move");
		
		GameColor eatableColor = null;
		GameColor eatenbyColor = null;
		
		Pacman eatablePacman = null;
		Pacman eatenbyPacman = null;

		int distanceEnemy = Integer.MAX_VALUE;
		int distancePrey = Integer.MAX_VALUE;

		List<Movement> priorityList = new ArrayList<Movement>();
		
		if(my.getColor().equals(GameColor.GREEN)) {
			eatableColor = GameColor.RED;
			eatenbyColor = GameColor.BLUE;
		} else if(my.getColor().equals(GameColor.BLUE)) {
			eatableColor = GameColor.GREEN;
			eatenbyColor = GameColor.RED;
		} if(my.getColor().equals(GameColor.RED)) {
			eatableColor = GameColor.BLUE;
			eatenbyColor = GameColor.GREEN;
		}
		
		log.info("Simple AI eatable color:"+eatableColor+" for "+my);
		
		boolean preyIsDead = false;
		boolean enemyIsDead = false;
		

		if(eatableColor != null && eatenbyColor != null) {
			
			for(Pacman p:all) {
				if(p.getColor().equals(eatableColor)) {
					if(p.isDead()) {
						preyIsDead = true;
					}
					eatablePacman=p;
					distancePrey = Math.abs(my.getX()-p.getX())+Math.abs(my.getY()-p.getY());
				}
				if(p.getColor().equals(eatenbyColor)) {
					if(p.isDead()) {
						enemyIsDead = true;
					}
					eatenbyPacman = p;
					distanceEnemy = Math.abs(my.getX()-p.getX())+Math.abs(my.getY()-p.getY());
				}
			}
			
			log.info("DistPrey: "+distancePrey+" (D:"+preyIsDead+"), DistEnemy:"+distanceEnemy+" (D:"+enemyIsDead+")");
			
			if(distancePrey <= distanceEnemy || enemyIsDead) {
				log.info("Mode: Hunt down");
				priorityList = calculateHuntRoute(my,eatablePacman);
			} else if(distancePrey > distanceEnemy || preyIsDead) {
				log.info("Mode: Runaway");
				priorityList = calculateRunawayRoute(my,eatenbyPacman);
			}
			
		}
		
		/* see if it is possible to return right movements */
		for(Movement m:priorityList) {
			if(possibleMov.contains(m)) {
				return m;
			}
		}
		
		/* else just return a random movment */
		return possibleMov.get(r.nextInt(possibleMov.size()));
	}
	
	/**
	 * Calculates a priority list for the next movement steps to go to destination
	 * @param origin
	 * @param destination
	 * @return
	 */
	private static List<Movement> calculateHuntRoute(Pacman origin, Pacman destination) {
		Movement xAxis;
		Movement yAxis;
		List<Movement> priorityList = new ArrayList<Movement>();
		
		int vectorX;
		int vectorY;
		
		vectorX = origin.getX()-destination.getX();
		vectorY = origin.getY()-destination.getY();
		
		if(vectorX > 0) {
			/* left */
			xAxis = Movement.LEFT;	
		} else if(vectorX < 0) {
			/* right */
			xAxis = Movement.RIGHT;
		} else {
			xAxis = Movement.NONE;
		}
		
		if(vectorY > 0) {
			/* up */
			yAxis = Movement.UP;
		} else if(vectorY < 0) {
			/* down */
			yAxis = Movement.DOWN;
		} else {
			yAxis = Movement.NONE;
		}
		
		if(Math.abs(vectorX) >= Math.abs(vectorY)) {
			priorityList.add(yAxis);
			priorityList.add(xAxis);
		} else {
			priorityList.add(xAxis);
			priorityList.add(yAxis);
		}
		
		log.info("Simple AI Priority List "+priorityList);
		
		return priorityList;
	}
	
	/**
	 * Same as calculateHuntRoute, but trys to find a route to get away from destination
	 * @param origin
	 * @param destination
	 * @return
	 */
	private static List<Movement> calculateRunawayRoute(Pacman origin, Pacman destination) {
		List<Movement> priorityList = new ArrayList<Movement>();
		
		for(Movement m: calculateHuntRoute(origin,destination)) {
			priorityList.add(Movement.getOpposite(m));
		}
		
		return priorityList;
	}
	
	/**
	 *  Returns the possible Movements in context to the current position
	 *  (taken e.g. wall into consideration )
	 * @param p
	 * @param m
	 * @param walls
	 * @return
	 */
	public static List<Movement> getPossibleMovments(Pacman p,GameMap m, List<Wall> walls) {
		List<Movement> movments = new ArrayList<Movement>();
		
		if((p.getX()-1) >= 0 && !checkIfWall((p.getX()-1),p.getY(),walls,p)) {
			movments.add(Movement.LEFT);
		}
		if((p.getX()+1) < m.getWidth() && !checkIfWall((p.getX()+1),p.getY(),walls,p)) {
			movments.add(Movement.RIGHT);
		}
		if((p.getY()-1) >= 0 && !checkIfWall(p.getX(),(p.getY()-1),walls,p)) {
			movments.add(Movement.UP);
		}
		if((p.getY()+1) < m.getHeight() && !checkIfWall(p.getX(),(p.getY()+1),walls,p)) {
			movments.add(Movement.DOWN);
		}
		
		return movments;
		
	}
	
	private static boolean checkIfWall(int x,int y, List<Wall> walls, Pacman p) {
		for(Wall w:walls) {
			if(!w.getColor().equals(p.getColor()) && w.getX() == x && w.getY() == y)
				return true;
		}
		
		return false;
	}
}
