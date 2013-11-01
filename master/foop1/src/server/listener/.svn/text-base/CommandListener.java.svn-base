package server.listener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.mozartspaces.core.Entry;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.Operation;

import server.cfg.FruitPoints;
import server.cfg.GameSettings;
import space.dao.ServerDao;
import space.exceptions.IDaoSaveException;
import util.ReflectionUtil;
import util.ServerUtil;
import client.model.Command;
import client.model.GameBonus;
import client.model.GameMap;
import client.model.Pacman;
import client.model.Wall;
import client.model.enums.BonusType;
import client.model.enums.CommandType;
import client.model.enums.GameColor;


public class CommandListener implements NotificationListener{
	private static Logger log = Logger.getLogger("CommandListener");
	
	private ServerDao sDao;
	private Random r = new Random();
	
	
	public CommandListener(ServerDao sDao) {
		this.sDao=sDao;	
	}
	
	/**
	 * A listener for commands:
	 * 		- create map
	 * 		- move pacman
	 * 		- register/unregister pacman
	 */
	@Override
	public void entryOperationFinished(Notification arg0, Operation arg1, List<? extends Serializable> list) {
		log.info("Command received");
		
		for(Object o:list) {
			if(o instanceof Entry) {
				if(((Entry)o).getValue() instanceof Command) {
					Command c = (Command) ((Entry)o).getValue();
					
					log.info(ReflectionUtil.printObjectDetails(c));
					
					if(c.getType().equals(CommandType.CREATEMAP)) {
						GameMap map = c.getMap();
						map.setCurrentRound(1);
						
						
						log.info(ReflectionUtil.printObjectDetails(map));
						
						/* create Walls */
						List<Wall> walls = new ArrayList<Wall>();
						Wall newWall;
						for (int i = 0; i < map.getHeight(); i++) {
							for (int j = 0; j < map.getWidth(); j++) {
								if(r.nextDouble() < map.getWallPercentage()) {
									newWall = new Wall(j,i,map.getId());
									
									if(r.nextDouble()< GameSettings.COLOR_WALL_OCCURRENCE) {
										newWall.setColor(ServerUtil.getRandomGameColor());
									}
									
									walls.add(newWall);
								}
							}
						}
						
						/* position pacmen */
						List<Pacman> pacmen = new ArrayList<Pacman>();
						int randX;
						int randY;
						boolean retry;
						
						for(Pacman p:c.getPacmen()) {
							
							/* check for walls */
							do{
								retry = false;
								
								randX=r.nextInt(map.getWidth());
								randY=r.nextInt(map.getHeight());
								
								for(Wall w:walls) {
									if(w.getX() == randX && w.getY()==randY) {
										retry = true;
										break;
									}
								}
								
							} while(retry);
							
							p.setX(randX);
							p.setY(randY);
							p.setRound(0);
							p.setPlayed(!p.isHuman());
							pacmen.add(p);
						}
						
						
						/* position fruits */
						int fruitCellCount = ((Long) Math.max(Math.round(new Double(map.getHeight()*c.getMap().getWidth())*GameSettings.BONUS_OCCURRENCE), 1l)).intValue();
						BonusType bType;
						
						List<GameBonus> bonuses = new ArrayList<GameBonus>();
						for(int i=0;i<fruitCellCount;i++) {
							
							/* check for walls and pacmen */
							do{
								retry = false;
								
								randX=r.nextInt(map.getWidth());
								randY=r.nextInt(map.getHeight());
								
								for(Wall w:walls) {
									if(w.getX() == randX && w.getY()==randY) {
										retry = true;
										break;
									}
								}
								
								for(Pacman p:pacmen) {
									if(p.getX() == randX && p.getY()==randY) {
										retry = true;
										break;
									}
								}
								
							} while(retry);
							
							
							bType = ServerUtil.getRandomBonusType();
							
							bonuses.add(new GameBonus(map.getId(),randX,randY,bType,FruitPoints.getPointsForType(bType)));
						}
						
						
						try {
							sDao.createMap(map, pacmen, walls, bonuses);
						} catch (IDaoSaveException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					/* MOVING PACMAN ************************************************************************** */	
					} else if(c.getType().equals(CommandType.MOVEPACMAN)) {
						/* synchronizing hack */
						try {
							Thread.sleep(25);
						} catch (InterruptedException e2) {
							e2.printStackTrace();
						}
						
						log.info("Moving pacman");
						
						Pacman p = c.getPacman();
						GameMap m = sDao.getMap(p.getGameMapId());
						boolean moveOK = true;
						
						if(p.getRound() < m.getCurrentRound() && m.getCurrentRound() <= m.getRoundsLimit()) {
							
							/* pacman that have to be updated */
							List<Pacman> changedPacmen = new ArrayList<Pacman>();
							
							List<Pacman> pacmen = sDao.getPacmenForMap(m.getId());
							List<Wall> walls = sDao.getWallsForMap(m.getId());
							List<GameBonus> bonuses = sDao.getBonusesForMap(m.getId());
							
							p.setRound( p.getRound()+1);
							
							for(Wall w:walls) {
								if(w.getX() == p.getX() && w.getY() == p.getY()) {
									if(w.getColor().equals(GameColor.NONE)) { /* cant move through wall */
										moveOK = false;
									} else if(!w.getColor().equals(p.getColor())) {
										p.setDead(true);
										
										/* pacman gets points from wall-killed pacman from same color */
										for(Pacman pIter:pacmen) {
											if(pIter.getColor().equals(w.getColor()) && !pIter.isDead()) {
												pIter.setRoundPoints(p.getRoundPoints()+p.getOverallPoints());
												p.setRoundPoints(0);
												p.setOverallPoints(0);
												changedPacmen.add(pIter);
												break;
											}
										}
									}
								}
							}
							
							/* check other pacmen */
							for(Pacman pIter:pacmen) {
								if(!pIter.getColor().equals(p.getColor()) && pIter.getX() == p.getX() && pIter.getY() == p.getY() && !pIter.isDead()) {
									if(ServerUtil.pacmanFight(p.getColor(),pIter.getColor())<0) {
										log.info(pIter+" kills "+p);
										p.setDead(true);
										pIter.setRoundPoints(p.getRoundPoints()+p.getOverallPoints());
										p.setRoundPoints(0);
										p.setOverallPoints(0);
									} else if(ServerUtil.pacmanFight(p.getColor(),pIter.getColor())>0) {
										log.info(p+" kills "+pIter);
										pIter.setDead(true);
										p.setRoundPoints(pIter.getRoundPoints()+pIter.getOverallPoints());
										pIter.setRoundPoints(0);
										pIter.setOverallPoints(0);
									}
									changedPacmen.add(pIter);
								}
							}
							
							/* check if ate fruit */
							for(GameBonus gb:bonuses) {
								if(gb.getX() == p.getX() && gb.getY() == p.getY()) {
									p.setRoundPoints(p.getRoundPoints()+gb.getPoints());
									sDao.deleteBonus(gb.getId());
								}
							}
							
							if(moveOK) {
								
								log.info("Updating Pacmen " + changedPacmen);
								changedPacmen.add(p);
								for (Pacman pIter : changedPacmen) {
									try {
										sDao.updatePacman(pIter);
									} catch (IDaoSaveException e1) {
										e1.printStackTrace();
									}
								}
								
							}
							
						}
					
					/* REGISTER PLAYER ************************************************************************** */	
					} else if(c.getType().equals(CommandType.REGISTER)) {
						try {
							
							Pacman p = sDao.getPacman(c.getPacman().getId());
							log.info("Register Pacman "+p);
							
							p.setPlayed(true);
							
							sDao.updatePacman(p);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					/* UNREGISTER PLAYER ************************************************************************** */	
					} else if(c.getType().equals(CommandType.UNREGISTER)) {
						try {
							
							Pacman p = sDao.getPacman(c.getPacman().getId());
							log.info("Unregister Pacman "+p);
							
							p.setPlayed(false);
							
							sDao.updatePacman(p);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
