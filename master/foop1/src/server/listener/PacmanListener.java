package server.listener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import org.mozartspaces.core.Entry;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.Operation;

import server.ai.SimpleAI;
import server.cfg.FruitPoints;
import server.cfg.GameSettings;
import space.dao.ServerDao;
import space.exceptions.IDaoSaveException;
import util.ServerUtil;
import client.model.Command;
import client.model.GameBonus;
import client.model.GameMap;
import client.model.Pacman;
import client.model.Wall;
import client.model.enums.BonusType;
import client.model.enums.CommandType;
import client.model.enums.GameColor;
import client.model.enums.Movement;

public class PacmanListener implements NotificationListener {
	private static Logger log = Logger.getLogger("PacmanListener");

	private ServerDao sDao;
	private Random r = new Random();

	public PacmanListener(ServerDao sDao) {
		this.sDao = sDao;
	}
	
	/**
	 * Whenever a pacman will be updated/saved, this listener will be called.
	 * It's responsibilities are: moving cpu pacman, advance to next round, 
	 * refreshing walls and bonus in a new round
	 * 
	 */
	@Override
	public synchronized void entryOperationFinished(Notification arg0, Operation arg1, List<? extends Serializable> list) {
		for (Object o : list) {
			log.info("Pacman change received "+((Entry) o).getValue());
			
			/* synchronizing hack */
			try {
				Thread.sleep(100);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			
			if (o instanceof Entry) {
				if (((Entry) o).getValue() instanceof Pacman) {
					
					Pacman p = (Pacman)((Entry) o).getValue();
					
					/* ignore cases for non playing pacman */
					if(p.isPlayed()) {
						
						GameMap m = sDao.getMap(p.getGameMapId());
						List<Wall> walls = null;
						List<Pacman> pacmen = sDao.getPacmenForMap(m.getId());
						
						/* check if next round */
						boolean nextRound = true;
						
						/* pacman that have to be updated */
						List<Pacman> changedPacmen = new ArrayList<Pacman>();
						List<Command> newCommands = new ArrayList<Command>();
						
						log.info("Found pacmen: "+pacmen);
						
						for(Pacman pIter:pacmen) {
							if(pIter.getRound() != m.getCurrentRound() && !pIter.isDead()) {
								/* move computer player */
								if(!pIter.isHuman()) {
									log.info("Moving CPU Player "+pIter);
									if(walls == null)
										walls = sDao.getWallsForMap(m.getId());
									
									List<Movement> mov = SimpleAI.getPossibleMovments(pIter,m,walls);
									
									if(mov.size() > 0) {
										Movement actualMov = SimpleAI.nextMovement(mov,pIter,pacmen);
										pIter.translateMovement(actualMov);
										log.info("Moving CPU "+mov+" and choose "+actualMov);
									} 
									
									/* send move command */
									Command c = new Command();
									c.setType(CommandType.MOVEPACMAN);
									c.setPacman(pIter);
									
									newCommands.add(c);
								}
								nextRound = false;
								break;
							}
						}
						
						/* move computer player */
						if(newCommands.size() > 0) {
							for(Command c:newCommands) {
								try {
									sDao.sendCommand(c);
								} catch (IDaoSaveException e) {
									e.printStackTrace();
								}
							}
						}
						
						
						log.info("Change to next round? "+nextRound);
						
						/* changing to next round */
						if(nextRound && (m.getCurrentRound()+1) <= m.getRoundsLimit()) {
							m.setCurrentRound(m.getCurrentRound()+1);
							
							/*refresh pacman points counter */
							for(Pacman pIter:pacmen) {
								if(pIter.getRoundPoints() > 0) {
									pIter.setOverallPoints(pIter.getOverallPoints()+pIter.getRoundPoints());
									pIter.setRoundPoints(0l);
									changedPacmen.add(pIter);
								}
							}
							
							/* refresh gamebonuses */
							if(m.getCurrentRound() % GameSettings.BONUS_REFRESH_ROUND_COUNT  == 0) {
								if(walls == null)
									walls = sDao.getWallsForMap(m.getId());
								
								List<GameBonus> bonuses = sDao.getBonusesForMap(m.getId());
								
								double bonusePercentage = new Double(bonuses.size()) / new Double(m.getHeight()*m.getWidth());
								
								log.info("BonusPercentage: "+bonusePercentage+" - max:"+GameSettings.BONUS_LIMIT_THRESHOLD);
								
								if(bonusePercentage < GameSettings.BONUS_LIMIT_THRESHOLD) {
									log.info("Refreshing Bonuses");
									long maxNewBonuses = Math.round(new Double(m.getHeight()*m.getWidth())*(GameSettings.BONUS_OCCURRENCE-bonusePercentage));
									/* random new bonus count*/
									long addBonusCount = Math.max(maxNewBonuses,1);
									
									log.info("MaxNewBonuses: "+maxNewBonuses+" - adding Bonus:"+addBonusCount);
									
									List<GameBonus> addBonuses = new ArrayList<GameBonus>();
									boolean retry;
									int randX;
									int randY;
									BonusType bType;
									
									for(long i=0;i<addBonusCount;i++) {
										
										
										/* check for walls and pacmen */
										do{
											retry = false;
											
											randX=r.nextInt(m.getWidth());
											randY=r.nextInt(m.getHeight());
											
											for(Wall w:walls) {
												if(w.getX() == randX && w.getY()==randY) {
													retry = true;
													break;
												}
											}
											
											for(Pacman pIter:pacmen) {
												if(pIter.getX() == randX && pIter.getY()==randY) {
													retry = true;
													break;
												}
											}
											
										} while(retry);
										
										bType = ServerUtil.getRandomBonusType();
										addBonuses.add(new GameBonus(m.getId(), randX, randY, bType,FruitPoints.getPointsForType(bType)));
									}
									
									try {
										sDao.saveBonuses(addBonuses);
									} catch (IDaoSaveException e) {
										e.printStackTrace();
									}
								}
							}
							
							/* refresh walls */
							if(m.getCurrentRound() % GameSettings.WALL_REFRESH_ROUND_COUNT  == 0) {
								log.info("Refreshing Walls");
								if(walls == null)
									walls = sDao.getWallsForMap(m.getId());
								
								Map<String,Wall> changedWalls = new HashMap<String,Wall>();
								
								boolean ignore;
								for(Wall w: walls) {
									ignore = false;
									
									/* first change to white color */
									if(!w.getColor().equals(GameColor.NONE)) {
										/* if a pacman is on a color wall*/
										for(Pacman pIter:pacmen) {
											if(!pIter.isDead() && pIter.getX() == w.getX() && pIter.getY() == w.getY()) {
												ignore=true;
											}
										}
										if(!ignore) {
											w.setColor(GameColor.NONE);
											changedWalls.put(w.getId(),w);
										}
									}
									
									if(!ignore && r.nextDouble()< GameSettings.COLOR_WALL_OCCURRENCE) {
										GameColor gc = ServerUtil.getRandomGameColor();
										log.info("Wall change color to "+gc);
										w.setColor(gc);
										changedWalls.put(w.getId(),w);
									}
								}
								for(Wall w:changedWalls.values()) {
									try {
										sDao.updateWall(w);
									} catch (IDaoSaveException e) {
										e.printStackTrace();
									}
								}
								
							}
							
							
							
							/* update map */
							try {
								log.info("Updating map (from pacman notify) "+m);
								sDao.updateMap(m);
							} catch (IDaoSaveException e) {
								e.printStackTrace();
							}
							
							/* update all changed pacmen */
							if(changedPacmen.size() > 0) {
								for(Pacman pIter:changedPacmen) {
									try {
										sDao.updatePacman(pIter);
									} catch (IDaoSaveException e1) {
										e1.printStackTrace();
									}
								}
							}
							
						}
					}
				}
			}
		}
	}
}
