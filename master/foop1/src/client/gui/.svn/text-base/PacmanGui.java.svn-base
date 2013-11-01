package client.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import org.mozartspaces.core.Entry;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.Operation;

import space.dao.ClientDao;
import space.exceptions.IDaoSaveException;
import util.GuiUtil;
import util.SoundUtil;
import client.gui.cfg.MediaPaths;
import client.gui.components.PacmanTablePane;
import client.model.Command;
import client.model.GameMap;
import client.model.Pacman;
import client.model.enums.CommandType;
import client.model.enums.GameColor;
import client.model.enums.Movement;


public class PacmanGui extends JFrame implements KeyListener, NotificationListener, ActionListener{

	private static final long serialVersionUID = 7241091116731550075L;
	private static final String AC_LEAVE= "leave";
	
	private static Logger log = Logger.getLogger("PacmanGui");
	
	private JLabel lbl_round,lbl_myPacman,lbl_greenPoints,lbl_redPoints,lbl_bluePoints;
	private JLabel lbl_accept1,lbl_accept2,lbl_accept3;
	private JLabel lbl_skull1,lbl_skull2,lbl_skull3;
	private JLabel lbl_cpu1,lbl_cpu2,lbl_cpu3;
	private JLabel lbl_sleep1,lbl_sleep2,lbl_sleep3;
	private JPanel scorePanel;
	private PacmanTablePane tablePane;
	private Icon acceptIcon,skullIcon,computerIcon,sleepIcon;
	
	private GameMap map;
	private Pacman myPacman;
	private ClientDao cDao;
	
	private boolean alreadyMovedThisRound = false;
	
	public PacmanGui(ClientDao cDao, GameMap m, Pacman p) {
		super("Pacman");
		
		this.map = m;
		this.myPacman = cDao.getPacman(p.getId());
		this.cDao = cDao;
		
		acceptIcon = GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_ACCEPT,"",this);
		skullIcon = GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_SKULL,"",this);
		computerIcon = GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_COMPUTER,"",this);
		sleepIcon = GuiUtil.createImageIcon(MediaPaths.IMAGE_PATH+MediaPaths.IMG_SLEEP,"",this);
		
		buildGUI();
		
		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
		addKeyListener(this);
		
		addWindowListener(new CloseListener());
		cDao.registerMovementListener(this);
		cDao.registerGameMapListener(this);
		
		if(m.getCurrentRound() == myPacman.getRound())
			alreadyMovedThisRound = true;
	}
	
	public void buildGUI() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		
		/* adding header */
		lbl_round = new JLabel("Round: "+map.getCurrentRound()+" of "+map.getRoundsLimit());
		lbl_round.setFont(new Font("SansSerif",Font.BOLD,36));
		c.gridx = 0;
		c.gridy = 0;
		getContentPane().add(lbl_round, c);
		
		lbl_myPacman = new JLabel(GuiUtil.getControlStringForColor(myPacman.getColor()),GuiUtil.createImageIcon(GuiUtil.getImgPathForColor(myPacman.getColor()), "", this),JLabel.LEFT);
		c.gridx = 5;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_END;
		getContentPane().add(lbl_myPacman, c);
		
		/*btn_leave = new JButton("Leave");
		btn_leave.setActionCommand(AC_LEAVE);
		btn_leave.addActionListener(this);
		btn_leave.set
		c.gridx = 5;
		c.gridy = 0;
		c.gridwidth=1;
		getContentPane().add(btn_leave, c);*/
		
		/* adding score panel */
		scorePanel = new JPanel();
		
		GridBagConstraints c2 = new GridBagConstraints();
		c2.insets = new Insets(5, 5, 5, 5);
		c2.fill=GridBagConstraints.HORIZONTAL;
		scorePanel.setLayout(new GridBagLayout());
		scorePanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		/* green pcaman */
		lbl_cpu1= new JLabel(computerIcon);
		lbl_cpu1.setVisible(false);
		c2.gridx = 0;
		c2.gridy = 0;
		scorePanel.add(lbl_cpu1,c2);
		
		lbl_greenPoints = new JLabel("",GuiUtil.createImageIcon(GuiUtil.getSmallImgPathForColor(GameColor.GREEN), "", this),JLabel.LEFT);
		c2.gridx = 1;
		c2.gridy = 0;
		scorePanel.add(lbl_greenPoints,c2);
		
		lbl_accept1= new JLabel(acceptIcon);
		lbl_accept1.setVisible(false);
		c2.gridx = 2;
		c2.gridy = 0;
		scorePanel.add(lbl_accept1,c2);
		
		lbl_skull1= new JLabel(skullIcon);
		lbl_skull1.setVisible(false);
		c2.gridx = 3;
		c2.gridy = 0;
		scorePanel.add(lbl_skull1,c2);
		
		lbl_sleep1= new JLabel(sleepIcon);
		lbl_sleep1.setVisible(false);
		c2.gridx = 4;
		c2.gridy = 0;
		scorePanel.add(lbl_sleep1,c2);
		
		c2.gridx = 5;
		c2.gridy = 0;
		c2.fill=GridBagConstraints.VERTICAL;
		scorePanel.add(new JSeparator(SwingConstants.VERTICAL),c2);
		
		
		
		/* blue pcaman */
		lbl_cpu2= new JLabel(computerIcon);
		lbl_cpu2.setVisible(false);
		c2.gridx = 6;
		c2.gridy = 0;
		scorePanel.add(lbl_cpu2,c2);
		
		lbl_bluePoints = new JLabel("",GuiUtil.createImageIcon(GuiUtil.getSmallImgPathForColor(GameColor.BLUE), "", this),JLabel.LEFT);
		c2.gridx = 7;
		c2.gridy = 0;
		scorePanel.add(lbl_bluePoints,c2);
		
		lbl_accept2= new JLabel(acceptIcon);
		lbl_accept2.setVisible(false);
		c2.gridx = 8;
		c2.gridy = 0;
		scorePanel.add(lbl_accept2,c2);
		
		lbl_skull2= new JLabel(skullIcon);
		lbl_skull2.setVisible(false);
		c2.gridx = 9;
		c2.gridy = 0;
		scorePanel.add(lbl_skull2,c2);
		
		lbl_sleep2= new JLabel(sleepIcon);
		lbl_sleep2.setVisible(false);
		c2.gridx = 10;
		c2.gridy = 0;
		scorePanel.add(lbl_sleep2,c2);
		
		c2.gridx = 11;
		c2.gridy = 0;
		c2.fill=GridBagConstraints.VERTICAL;
		scorePanel.add(new JSeparator(SwingConstants.VERTICAL),c2);
		
		/* red pcaman */
		lbl_cpu3= new JLabel(computerIcon);
		lbl_cpu3.setVisible(false);
		c2.gridx = 12;
		c2.gridy = 0;
		scorePanel.add(lbl_cpu3,c2);
		
		lbl_redPoints = new JLabel("",GuiUtil.createImageIcon(GuiUtil.getSmallImgPathForColor(GameColor.RED), "", this),JLabel.LEFT);
		c2.gridx = 13;
		c2.gridy = 0;
		scorePanel.add(lbl_redPoints,c2);
		
		lbl_accept3= new JLabel(acceptIcon);
		lbl_accept3.setVisible(false);
		c2.gridx = 14;
		c2.gridy = 0;
		scorePanel.add(lbl_accept3,c2);
		
		lbl_skull3= new JLabel(skullIcon);
		lbl_skull3.setVisible(false);
		c2.gridx = 15;
		c2.gridy = 0;
		scorePanel.add(lbl_skull3,c2);
		
		lbl_sleep3= new JLabel(sleepIcon);
		lbl_sleep3.setVisible(false);
		c2.gridx = 16;
		c2.gridy = 0;
		scorePanel.add(lbl_sleep3,c2);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 6;
		c.fill= GridBagConstraints.HORIZONTAL;
		getContentPane().add(scorePanel, c);
		
		
		refreshScoreBoard();
		
		/* Adding table panel */
		tablePane = new PacmanTablePane(cDao,map,myPacman);
		cDao.registerMovementListener(tablePane);
		cDao.registerBonusListener(tablePane);
		cDao.registerWallListener(tablePane);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 6;
		c.insets = new Insets(0, 0, 0, 0);
		getContentPane().add(tablePane, c);
		
	}

	public void redraw() {
		this.removeAll();
		this.buildGUI();
	}
	
	/**
	 * Refreshes the score board
	 */
	private void refreshScoreBoard() {
		List<Pacman> pacmen = cDao.getPacmenForMap(map.getId());
		for(Pacman p:pacmen) {
			if(p.getColor().equals(GameColor.GREEN)) {
				lbl_greenPoints.setText(getScoreString(p));
				lbl_accept1.setVisible(p.getRound()==map.getCurrentRound() && !p.isDead());
				lbl_skull1.setVisible(p.isDead());
				lbl_cpu1.setVisible(!p.isHuman());
				lbl_sleep1.setVisible(!p.isPlayed());
			} else if(p.getColor().equals(GameColor.BLUE)) {
				lbl_bluePoints.setText(getScoreString(p));
				lbl_accept2.setVisible(p.getRound()==map.getCurrentRound() && !p.isDead());
				lbl_skull2.setVisible(p.isDead());
				lbl_cpu2.setVisible(!p.isHuman());
				lbl_sleep2.setVisible(!p.isPlayed());
			} else if(p.getColor().equals(GameColor.RED)) {
				lbl_redPoints.setText(getScoreString(p));
				lbl_accept3.setVisible(p.getRound()==map.getCurrentRound() && !p.isDead());
				lbl_skull3.setVisible(p.isDead());
				lbl_cpu3.setVisible(!p.isHuman());
				lbl_sleep3.setVisible(!p.isPlayed());
			}
		}
	}
	
	private String getScoreString(Pacman p) {
		return "R:"+p.getRoundPoints()+"/"+p.getOverallPoints();
	}
	
	/**
	 * Checks if the game should end (no more pacman, end of rounds)
	 * @param pacmen
	 */
	private void checkEnd(List<Pacman> pacmen) {
		int aliveCount = 0;
		for(Pacman p:pacmen) {
			if(!p.isDead())
				aliveCount++;
		}
		
		if(map.getCurrentRound() >= map.getRoundsLimit() || aliveCount <= 1) {
			/* calculate winner */
			long max = -1;
			Pacman winner = null;
			for(Pacman p:pacmen) {
				if(!p.isDead()) {
					if(p.getOverallPoints() > max) {
						max = p.getOverallPoints();
						winner = p;
					}
				}
			}
			
			if(winner != null) {
				if(winner.getId().equals(myPacman.getId())) {
					SoundUtil.playWin();
					JOptionPane.showMessageDialog(this,"Game finished! You Won!","Finished", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this,"Game finished! "+winner+" Won!","Finished", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this,"Game finished!","Finished", JOptionPane.INFORMATION_MESSAGE);
			}
			
			
		}
	}
	
	/* KEY LISTENER METHODS **************************************/
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!alreadyMovedThisRound && !myPacman.isDead()) {
			log.info("Key Released: "+e.getKeyCode()+" - "+e.getKeyChar());
			Movement mov = GuiUtil.translateKeyCode(myPacman.getColor(),e.getKeyCode());
			
			if(!mov.equals(Movement.NONE)) {
				alreadyMovedThisRound = tablePane.movePacman(mov);
				
				if(!alreadyMovedThisRound)
					SoundUtil.playNope();
			} else {
				SoundUtil.playNope();
			}
		} else {
			SoundUtil.playNope();
		}
	}

	/* WINDOW CLOSING LISTENER **************************************/
	
	public class CloseListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			log.info("Closing application");
			unregister();
			
			dispose();
			System.exit(0);
		}
	}
	
	private void unregister() {
		log.info("Unregister Pacman");
		
		/* send unregister command*/
		Command c = new Command();
		c.setType(CommandType.UNREGISTER);
		c.setPacman(myPacman);
		
		try {
			cDao.sendCommand(c);
			//cDao.deleteAllNotificationListener();
		} catch (IDaoSaveException e1) {
			e1.printStackTrace();
		}
		
	}

	/* SPACE NOTIFY LISTENER ************************************* */
	
	@Override
	public void entryOperationFinished(Notification arg0, Operation arg1,
			List<? extends Serializable> arg2) {
		for(Object o:arg2) {
			if(o instanceof Entry) {
				if(((Entry) o).getValue() instanceof Pacman) {
					Pacman p = (Pacman)((Entry) o).getValue();
					if(p.getId().equals(myPacman.getId())) {
						myPacman = p;
						if(p.isDead() && p.isPlayed()) {
							SoundUtil.playGameOver();
							JOptionPane.showMessageDialog(this,"You died!","Game Over", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					refreshScoreBoard();
				} else if(((Entry) o).getValue() instanceof GameMap) {
					GameMap m = (GameMap) ((Entry) o).getValue();
					log.info("Map notification");
					if(m.getId().equals(map.getId())) {
						map = m;
						lbl_round.setText("Round: "+map.getCurrentRound()+" of "+map.getRoundsLimit());
						
						refreshScoreBoard();
						checkEnd(cDao.getPacmenForMap(map.getId()));
						alreadyMovedThisRound = (myPacman.getRound() == map.getCurrentRound());
					}
				}
			}
		}
		
	}

	/*
	 * ACTION EVENT
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(AC_LEAVE)) {
			unregister();
			JFrame f = new GameSelctorGui(cDao);
			f.pack();
			f.setVisible(true);
			this.dispose();
		}
		
	}

}
