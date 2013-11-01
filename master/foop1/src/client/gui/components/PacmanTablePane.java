package client.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.mozartspaces.core.Entry;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.Operation;

import space.dao.ClientDao;
import space.exceptions.IDaoSaveException;
import util.GuiUtil;
import util.ReflectionUtil;
import util.SoundUtil;
import client.gui.cfg.GameCellType;
import client.gui.cfg.MediaPaths;
import client.model.Command;
import client.model.GameBonus;
import client.model.GameMap;
import client.model.Pacman;
import client.model.Wall;
import client.model.enums.CommandType;
import client.model.enums.GameColor;
import client.model.enums.Movement;

public class PacmanTablePane extends JPanel implements NotificationListener{
	private static final long serialVersionUID = -1157857496299816230L;
	private static final Color BG_TABLE = new Color(249,249,249);
	
	private static Logger log = Logger.getLogger("PacmanTablePane");
	
	private RotatedIcon greenPacIcon,bluePacIcon,redPacIcon;
	private Icon wallIcon,greenWallIcon,blueWallIcon,redWallIcon;
	private Icon greenPacamanWallIcon,bluePacamanWallIcon,redPacamanWallIcon;
	private Icon fruit1Icon,fruit2Icon,fruit3Icon;
	
	private JTable table;
	
	private GameMap map;
	private Pacman myPacman;
	private List<Pacman> pacmen;
	private List<Wall> walls;
	private List<GameBonus> gameBonuses;
	private ClientDao cDao;

	
	public PacmanTablePane(ClientDao cDao, GameMap m, Pacman p) {
		super();
		
		/* load images */
		greenPacIcon = new RotatedIcon(GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_PACMAN_GREEN,"",this),0.0);
		bluePacIcon = new RotatedIcon(GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_PACMAN_BLUE,"",this),0.0);
		redPacIcon = new RotatedIcon(GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_PACMAN_RED,"",this),0.0);
		
		wallIcon = GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_WALL,"",this);
		greenWallIcon = GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_WALL_GREEN,"",this);
		blueWallIcon = GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_WALL_BLUE,"",this);
		redWallIcon = GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_WALL_RED,"",this);
		
		greenPacamanWallIcon = GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_WALL_PACMAN_GREEN,"",this);
		bluePacamanWallIcon = GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_WALL_PACMAN_BLUE,"",this);
		redPacamanWallIcon = GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_WALL_PACMAN_RED,"",this);
		
		fruit1Icon = GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_FRUIT1,"",this);
		fruit2Icon = GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_FRUIT2,"",this);
		fruit3Icon = GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_FRUIT3,"",this);

		this.map = m;
		this.myPacman = p;
		this.cDao = cDao;
		
		pacmen = cDao.getPacmenForMap(map.getId());
		walls = cDao.getWallsForMap(map.getId());
		gameBonuses =cDao.getBonusesForMap(map.getId());
		buildGUI();
	}
	
	public void buildGUI() {
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 5, 5, 5);

		table = new JTable() {
			private static final long serialVersionUID = 5495151291069287469L;

			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // Disallow the editing of any cell
			}
		};
		
		drawTable(map);
		
		table.setRowHeight(36);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setShowGrid(false);
		table.setFocusable(false);
		table.setColumnSelectionAllowed(false);
		table.setRowMargin(0);
		
		TableColumn column;
		for(int i=0;i< table.getColumnModel().getColumnCount();i++)  {
			column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(36);
			column.setResizable(false);
			table.getColumnModel().getColumn(i).setCellRenderer(new ImageRenderer());
		}
		
		c.gridx = 0;
		c.gridy = 0;
		add(table, c);

	}
	
	private void drawTable(GameMap m) {
		DefaultTableModel dtm = new DefaultTableModel();
		GameCellType[][] contentArray = new GameCellType[m.getHeight()][m.getWidth()];

		String[] col = new String[m.getWidth()];
		
		for (int i = 0; i < m.getWidth(); i++) {
			col[i] = "";
		}

		for (int i = 0; i < m.getHeight(); i++) {
			for (int j = 0; j < m.getWidth(); j++) {
				contentArray[i][j] = GameCellType.NONE;
			}
		}
		
		dtm.setDataVector(contentArray, col);
		table.setModel(dtm);
		
		/* set walls */
		for(Wall w: walls) {
			log.info("Wall at "+w.getY()+","+w.getX());
			table.setValueAt(GuiUtil.getWallCellTypeForColor(w.getColor()), w.getY(), w.getX());
		}
		
		/* set pacmen */
		for(Pacman p:pacmen) {
			log.info("Pacman at "+p.getY()+","+p.getX());
			if(!p.isDead())
				table.setValueAt(GuiUtil.getPacmanCellTypeForColor(p.getColor()), p.getY(), p.getX());
		}
		
		/* set bonuses */
		for(GameBonus g:gameBonuses) {
			log.info("Bonus at "+g.getY()+","+g.getX());
			table.setValueAt(GuiUtil.getFruitCellType(g.getType()), g.getY(), g.getX());
		}
	}
	
	private void redrawWall(Wall w) {
		table.setValueAt(GuiUtil.getWallCellTypeForColor(w.getColor()), w.getY(), w.getX());
	}
	private void drawBonus(GameBonus gm) {
		log.info("Drawing Bonus");
		table.setValueAt(GuiUtil.getFruitCellType(gm.getType()), gm.getY(), gm.getX());
	}
	private void resetCell(int x,int y) {
		table.setValueAt(GameCellType.NONE, y, x);
	}
	
	
	/**
	 * Internal moving method
	 * @param p
	 */
	private void movePacman(Pacman p) {
		int found =-1;
		
		//log.info("before:"+ReflectionUtil.printObjectDetails(p));
		
		for(int i=0;i<pacmen.size();i++) {
			if(p.getId().equals(pacmen.get(i).getId())) {
				/* only move if position has changed */
				if(pacmen.get(i).getY() >= 0 && pacmen.get(i).getX() >=0
					&& p.getY() >= 0 && p.getX() >=0) {
					
					//log.info("found:"+ReflectionUtil.printObjectDetails(pacmen.get(i)));
					
					table.setValueAt(GameCellType.NONE, pacmen.get(i).getY(), pacmen.get(i).getX());
					table.setValueAt(GuiUtil.getPacmanCellTypeForColor(p.getColor()), p.getY(), p.getX());
					
					for(Wall w:walls) {
						/* reconstruct source cell */
						if(!w.getColor().equals(GameColor.NONE) && w.getX() == pacmen.get(i).getX() && w.getY() == pacmen.get(i).getY()) {
							table.setValueAt(GuiUtil.getWallCellTypeForColor(w.getColor()), pacmen.get(i).getY(),  pacmen.get(i).getX());
							//log.info("w1");
						}
						/* draw wall with pacman */
						if(!w.getColor().equals(GameColor.NONE) && w.getX() == p.getX() && w.getY() == p.getY() && w.getColor().equals(p.getColor()) && !p.isDead()) {
							SoundUtil.playBazing();
							table.setValueAt(GuiUtil.getWallWithPacmanCellTypeForColor(w.getColor()), p.getY(), p.getX());
							//log.info("w2");
						}
						/* if pacman got killed by wall */
						if(!w.getColor().equals(GameColor.NONE) && w.getX() == p.getX() && w.getY() == p.getY() && !w.getColor().equals(p.getColor()) && p.isDead()) {
							table.setValueAt(GuiUtil.getWallCellTypeForColor(w.getColor()), p.getY(),  p.getX());
							log.info("Pronounce Dead by Wall:"+p);
							//log.info("w3");
						}
					}
					
					/* playing sounds */
					int foundGB = -1;
					for(int j=0;j<gameBonuses.size();j++) {
						if(gameBonuses.get(j).getX()==p.getX() && gameBonuses.get(j).getY()==p.getY()) {
							SoundUtil.playMjam();
							foundGB = j;
							break;
						}
					}
					
					/* delete bonus from internal list - hack because notification doesn't work with this (see comment below) */
					if(foundGB != -1) {
						gameBonuses.remove(foundGB);
						//log.info("del bonus");
					}
					
					/* find possible killing */
					for(Pacman pIter:pacmen) {
						if(pIter.getX()==p.getX() && pIter.getY()==p.getY() && !pIter.getId().equals(p.getId()) && !pIter.isDead()) {
							SoundUtil.playEat();
							//log.info("eat");
							if(p.isDead()) {
								//log.info("pacman dead");
								table.setValueAt(GuiUtil.getPacmanCellTypeForColor(pIter.getColor()), p.getY(), p.getX());
								
								/* remove remains */
								if(table.getValueAt(pacmen.get(i).getY(), pacmen.get(i).getX()).equals(GuiUtil.getPacmanCellTypeForColor(p.getColor()))
										|| table.getValueAt(p.getY(), p.getX()).equals(GuiUtil.getPacmanCellTypeForColor(p.getColor()))) {
									table.setValueAt(GameCellType.NONE, pacmen.get(i).getY(), pacmen.get(i).getX());
									//log.info("w1");
								}
								
								log.info("Pronounce Dead by Pacman:"+p);
							}
							break;
						}
					}
					
					if(!p.isDead() && !(pacmen.get(i).getX() == p.getX() && pacmen.get(i).getY() == p.getY())) {
						/* rotate picture */
						Double rot = GuiUtil.getMovementDirection(pacmen.get(i).getX(), pacmen.get(i).getY(),p.getX(), p.getY());
						
						if (p.getColor().equals(GameColor.GREEN)) {
							greenPacIcon = new RotatedIcon(greenPacIcon.getIcon(), rot);
						} else if (p.getColor().equals(GameColor.BLUE)) {
							bluePacIcon = new RotatedIcon(bluePacIcon.getIcon(), rot);
						} else if (p.getColor().equals(GameColor.RED)) {
							redPacIcon = new RotatedIcon(redPacIcon.getIcon(), rot);
						}
					} else if(p.isDead()) {
						p.setX(-1);
						p.setY(-1);
					}

				} 
				
				found = i;
				
				log.info("Old: "+pacmen.get(i).getX()+","+pacmen.get(i).getY()+" - New: "+p.getX()+","+p.getY());
			
				break;
			}
		}
		
		if(found != -1) {
			pacmen.set(found, p);
			//log.info("after:"+ReflectionUtil.printObjectDetails(p));
		}
		
	}
	
	/**
	 * Move from a keyboard action 
	 * @param mov
	 * @return
	 */
	public boolean movePacman(Movement mov) {
		for(Pacman p:pacmen) {
			if(p.getId().equals(myPacman.getId())) {
				myPacman = new Pacman(p);
				int currentX = p.getX();
				int currentY = p.getY();
				
				if(mov.equals(Movement.UP)) {
					if(currentY-1 >= 0 && !checkIfWall(currentX,currentY-1)) {
						myPacman.setY(currentY-1);
						movePacman(myPacman);
						sendMoveCommand(myPacman);
						return true;
					}
				} else if(mov.equals(Movement.DOWN)) {
					if(currentY+1 < map.getHeight() && !checkIfWall(currentX,currentY+1)) {
						myPacman.setY(currentY+1);
						movePacman(myPacman);
						sendMoveCommand(myPacman);
						return true;
					}
				} else if(mov.equals(Movement.LEFT)) {
					if(currentX-1 >= 0 && !checkIfWall(currentX-1,currentY)) {
						myPacman.setX(currentX-1);
						movePacman(myPacman);
						sendMoveCommand(myPacman);
						return true;
					}
				} else if(mov.equals(Movement.RIGHT)) {
					if(currentX+1 < map.getWidth() && !checkIfWall(currentX+1,currentY)) {
						myPacman.setX(currentX+1);
						movePacman(myPacman);
						sendMoveCommand(myPacman);
						return true;
					}
				}
				
				break;
			}
		}
		return false;
	}
	
	/**
	 * Send the move command to the space
	 * @param p
	 */
	private void sendMoveCommand(Pacman p) {
		Command c = new Command();
		c.setPacman(p);
		c.setType(CommandType.MOVEPACMAN);
		try {
			cDao.sendCommand(c);
		} catch (IDaoSaveException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if a None-Color-Wall is at these coordinates
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean checkIfWall(int x,int y) {
		for(Wall w:walls) {
			if(w.getColor().equals(GameColor.NONE) && w.getX() == x && w.getY() == y)
				return true;
		}
		
		return false;
	}
	
	/* Custom Cell Renderer for Table to show images *******************************************/
	public class ImageRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = -4940831930868596551L;
		
		private JLabel lbl = new JLabel();
		
		public ImageRenderer() {
			lbl.setOpaque(true);
			lbl.setBackground(BG_TABLE);
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {

			if(value instanceof GameCellType) {
				
				GameCellType gct = (GameCellType) value;
				
				if(gct.equals(GameCellType.NONE)) {
					lbl.setIcon(null);
				} else {
					//RotatedIcon ri = new RotatedIcon(getIconForType(gct), RotatedIcon.Rotate.DOWN);
					lbl.setIcon(getIconForType(gct));
				}
			} else {
				lbl.setIcon(null);
				
			}
			return lbl;
		}
		
		
		private Icon getIconForType(GameCellType gct) {
			if(gct.equals(GameCellType.PACMAN_GREEN)) {
				return greenPacIcon;
			} else if(gct.equals(GameCellType.PACMAN_BLUE)) {
				return bluePacIcon;
			} else if(gct.equals(GameCellType.PACMAN_RED)) {
				return redPacIcon;
			} else if(gct.equals(GameCellType.WALL)) {
				return wallIcon;
			} else if(gct.equals(GameCellType.WALL_GREEN)) {
				return greenWallIcon;
			} else if(gct.equals(GameCellType.WALL_BLUE)) {
				return blueWallIcon;
			} else if(gct.equals(GameCellType.WALL_RED)) {
				return redWallIcon;
			} else if(gct.equals(GameCellType.WALL_PACMAN_GREEN)) {
				return greenPacamanWallIcon;
			} else if(gct.equals(GameCellType.WALL_PACMAN_BLUE)) {
				return bluePacamanWallIcon;
			} else if(gct.equals(GameCellType.WALL_PACMAN_RED)) {
				return redPacamanWallIcon;
			} else if(gct.equals(GameCellType.FRUIT1)) {
				return fruit1Icon;
			} else if(gct.equals(GameCellType.FRUIT2)) {
				return fruit2Icon;
			} else if(gct.equals(GameCellType.FRUIT3)) {
				return fruit3Icon;
			}
			
			return null;
		}
		
		 // The following methods override the defaults for performance reasons
		@Override
		public void validate() {}
		@Override
		public void revalidate() {}
		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
		@Override
		public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
	}
	
	/* SPACE NOTIFY LISTENER METHODS ************************************* */
	
	@Override
	public void entryOperationFinished(Notification arg0, Operation op,List<? extends Serializable> arg2) {
		for(Object o:arg2) {
			if(o instanceof Entry) {
				if(((Entry) o).getValue() instanceof Pacman) {
					Pacman p = (Pacman) ((Entry) o).getValue();
					log.info("Notification! "+p);

					movePacman(p);
					
					if(p.getId().equals(myPacman.getId())) {
						myPacman = p;
					} 
				
				/* wall has changed */
				} else if(((Entry) o).getValue() instanceof Wall) {
					Wall notifyWall = (Wall) ((Entry) o).getValue();
					log.info("Wall notification "+notifyWall);
					
					int found = -1;
					for(int i=0;i< walls.size();i++) {
						if(walls.get(i).getId().equals(notifyWall.getId())) {
							if(!walls.get(i).getColor().equals(notifyWall.getColor())) {
								redrawWall(notifyWall);
							}
							found = i;
						}
					}
					
					/* refresh wall in list*/
					if(found != -1)
						walls.set(found, notifyWall);
				
				/* bonuses have changed */
				} else if(((Entry) o).getValue() instanceof GameBonus) {
					GameBonus gb = (GameBonus) ((Entry) o).getValue();
					
					log.info("Bonus notification Operation:"+op+" - "+gb);
					
					if(op.equals(Operation.WRITE)) {
						drawBonus(gb);
						gameBonuses.add(gb);
					} else if(op.equals(Operation.DELETE)) { /* bug: notifiaction for DELETE operations doesn't seem to work */
						int found = -1;
						for(int i=0;i<gameBonuses.size();i++) {
							if(gameBonuses.get(i).getId().equals(gb.getId())) {
								resetCell(gb.getX(), gb.getY());
								found = i;
							}
						}
						
						if(found != -1) {
							log.info("removing bonus "+gameBonuses.remove(found));
							
						}
					}
					
					
					
				}
			}
		}
		
	}
}
