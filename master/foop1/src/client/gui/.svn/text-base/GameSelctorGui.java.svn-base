package client.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.mozartspaces.core.Entry;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.Operation;

import space.dao.ClientDao;
import space.exceptions.IDaoSaveException;
import client.model.Command;
import client.model.GameMap;
import client.model.Pacman;
import client.model.enums.CommandType;

public class GameSelctorGui extends JFrame implements ActionListener, NotificationListener {

	private static final long serialVersionUID = 7241091116731550075L;
	private static String AC_NEW = "new";
	private static String AC_JOIN = "join";
	
	private static Logger log = Logger.getLogger("GameSelctorGui");
	
	private JLabel lbl_selectGame,lbl_selectPlayer;
	private JComboBox cb_gameMaps, cb_player;
	private JButton btn_newMap,btn_join;
	private JPanel panel;
	private boolean canJoin =false;
	
	
	private ClientDao cDao;
	
	public GameSelctorGui(ClientDao cDao) {
		super("Select Pacman Game");
		this.cDao = cDao;
		buildGUI();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		cDao.registerGameMapListener(this);
	}
	
	
	public void buildGUI() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		
		panel = new JPanel();
		
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Choose your Game"));
		
		
		List<GameMap> maps  = getAllGameMaps();
		
		lbl_selectGame = new JLabel("Select Game:");
		
		c.gridx = 0;
		c.gridy = 0;
		panel.add(lbl_selectGame, c);
		
		if(maps != null && maps.size() > 0) {
			cb_gameMaps = new JComboBox(getAllGameMaps().toArray());
			cb_gameMaps.addActionListener(this);
			c.gridx = 1;
			c.gridy = 0;
			panel.add(cb_gameMaps, c);
		}
		
		btn_newMap = new JButton("Create new game");
		btn_newMap.setActionCommand(AC_NEW);
		btn_newMap.addActionListener(this);
		
		c.gridx = 2;
		c.gridy = 0;
		panel.add(btn_newMap, c);
		
		if(cb_gameMaps != null && cb_gameMaps.getSelectedItem() != null && cb_gameMaps.getModel().getSize() > 0) {
			lbl_selectPlayer = new JLabel("Select Player:");
			
			c.gridx = 0;
			c.gridy = 1;
			panel.add(lbl_selectPlayer, c);
			
			cb_player = new JComboBox(getPacmenForMap((((GameMap)cb_gameMaps.getSelectedItem()).getId())).toArray());
			canJoin = (cb_player.getModel().getSize() > 0);
			cb_player.setEnabled(canJoin);
			c.gridx = 1;
			c.gridy = 1;
			c.gridwidth =2;
			c.fill= GridBagConstraints.HORIZONTAL;
			panel.add(cb_player, c);
			
			btn_join = new JButton("Join Game");
			btn_join.setEnabled(canJoin);
			btn_join.setActionCommand(AC_JOIN);
			btn_join.addActionListener(this);
			c.gridx = 0;
			c.gridy = 1;
			c.fill= GridBagConstraints.HORIZONTAL;
			getContentPane().add(btn_join, c);
		}
		
		/* add panel */
		c.gridx = 0;
		c.gridy = 0;
		c.fill= GridBagConstraints.HORIZONTAL;
		getContentPane().add(panel, c);
	}
	
	private void updatePlayersList() {
		cb_player.setModel(new DefaultComboBoxModel(getPacmenForMap((((GameMap)cb_gameMaps.getSelectedItem()).getId())).toArray()));
		canJoin = (cb_player.getModel().getSize() > 0);
		cb_player.setEnabled(canJoin);
		btn_join.setEnabled(canJoin);
	}
	
	private List<GameMap> getAllGameMaps() {
		return cDao.getAllGameMaps();
	}
	
	private List<Pacman> getPacmenForMap(String gameMapId) {
		log.info("Pacman size: "+cDao.getPacmenForMap(gameMapId).size());
		
		List<Pacman> list = new ArrayList<Pacman>();
		
		/* only show playable pacmen */
		for(Pacman p: cDao.getPacmenForMap(gameMapId)) {
			if(!p.isDead() && p.isHuman() && !p.isPlayed()) {
				list.add(p);
			}
		}
		
		return list;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(AC_NEW)) {
			log.info("Button create clicked");
			JFrame f = new CreateGameGui(cDao);
			f.pack();
			f.setVisible(true);
			this.dispose();
		} else if(e.getActionCommand().equals(AC_JOIN)) {
			log.info("Join Game "+cb_gameMaps.getSelectedItem()+" as Player "+cb_player.getSelectedItem());
			
			/* send command*/
			Command c = new Command();
			c.setType(CommandType.REGISTER);
			c.setPacman((Pacman) cb_player.getSelectedItem());
			
			try {
				cDao.sendCommand(c);
			} catch (IDaoSaveException e1) {
				e1.printStackTrace();
			}
			
			/* create gui*/
			JFrame f = new PacmanGui(cDao, (GameMap)cb_gameMaps.getSelectedItem(), (Pacman) cb_player.getSelectedItem());
			f.pack();
			f.setVisible(true);
			this.dispose();
			
		} else if( e.getSource() instanceof JComboBox) {
			updatePlayersList();
		}
		
	}

	/* SPACE NOTIFY LISTENER ************************************* */
	
	@Override
	public void entryOperationFinished(Notification arg0, Operation arg1,
			List<? extends Serializable> arg2) {
		for(Object o:arg2) {
			if(o instanceof Entry) {
				if(((Entry) o).getValue() instanceof GameMap) {
					/* redraw gui */
					getContentPane().removeAll();
					buildGUI();
					pack();
				}
			}
		}
		
	}
}
