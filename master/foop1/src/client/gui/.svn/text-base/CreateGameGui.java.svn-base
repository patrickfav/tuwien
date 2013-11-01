package client.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;

import space.dao.ClientDao;
import space.exceptions.IDaoSaveException;
import client.model.Command;
import client.model.GameMap;
import client.model.Pacman;
import client.model.enums.CommandType;
import client.model.enums.GameColor;


public class CreateGameGui extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 7836127147445153270L;
	private static final String AC_CREATE = "create";
	private static final String AC_CANCEL = "cancel";
	
	private JLabel lbl_mapName,lbl_mapRounds,lbl_mapWidth,lbl_mapHeight,lbl_wallPercentage,lbl_greenPacman,lbl_redPacman,lbl_bluePacman;
	private JTextField tf_mapName;
	private JSpinner sp_mapRounds,sp_mapWidth,sp_mapHeight,sp_wallPercentage;
	private JButton btn_save,btn_cancel;
	private JRadioButton rb_green_human,rb_green_cpu,rb_red_human,rb_red_cpu,rb_blue_human,rb_blue_cpu;
	private ButtonGroup gr_green,gr_red,gr_blue;
	
	private ClientDao cDao;
	private Random r = new Random();
	
	public CreateGameGui(ClientDao cDao) {
		super("Create Game");
		this.cDao = cDao;
		buildGUI();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	public void buildGUI() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.fill= GridBagConstraints.HORIZONTAL;
		lbl_mapName = new JLabel("Name:");
		tf_mapName = new JTextField(8);
		tf_mapName.setText("PGame "+r.nextInt(99));
		
		c.gridx = 0;
		c.gridy = 0;
		getContentPane().add(lbl_mapName, c);
		
		c.gridx = 1;
		c.gridy = 0;
		getContentPane().add(tf_mapName, c);
		
		
		lbl_mapRounds = new JLabel("Rounds:");
		sp_mapRounds = new JSpinner(new SpinnerNumberModel(160, 1, 9999, 1));
		
		c.gridx = 0;
		c.gridy = 1;
		getContentPane().add(lbl_mapRounds, c);
		
		c.gridx = 1;
		c.gridy = 1;
		getContentPane().add(sp_mapRounds, c);
		
		lbl_mapWidth = new JLabel("Width:");
		sp_mapWidth = new JSpinner(new SpinnerNumberModel(21, 10, 200, 1));
		
		c.gridx = 0;
		c.gridy = 2;
		getContentPane().add(lbl_mapWidth, c);
		
		c.gridx = 1;
		c.gridy = 2;
		getContentPane().add(sp_mapWidth, c);
		
		lbl_mapHeight = new JLabel("Height:");
		sp_mapHeight = new JSpinner(new SpinnerNumberModel(16, 10, 200, 1));
		
		c.gridx = 0;
		c.gridy = 3;
		getContentPane().add(lbl_mapHeight, c);
		
		c.gridx = 1;
		c.gridy = 3;
		getContentPane().add(sp_mapHeight, c);
		
		
		lbl_wallPercentage = new JLabel("Wall %");
		sp_wallPercentage = new JSpinner(new SpinnerNumberModel(25, 5, 75, 1));
		
		c.gridx = 0;
		c.gridy = 4;
		getContentPane().add(lbl_wallPercentage, c);
		
		c.gridx = 1;
		c.gridy = 4;
		getContentPane().add(sp_wallPercentage, c);
		
		
		/* ADDING PANEL *********************************************** */
		JPanel playerPanel = new JPanel();
		GridBagConstraints c2 = new GridBagConstraints();
		c2.insets = new Insets(5, 5, 5, 5);
		
		playerPanel.setLayout(new GridBagLayout());
		playerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Player Settings"));
		
		lbl_greenPacman = new JLabel("Green:");
		c2.gridx = 0;
		c2.gridy = 0;
		playerPanel.add(lbl_greenPacman,c2);
		
		rb_green_human = new JRadioButton("Human");
		rb_green_human.setSelected(true);
		c2.gridx = 1;
		c2.gridy = 0;
		playerPanel.add(rb_green_human,c2);
		
		rb_green_cpu = new JRadioButton("CPU");
		c2.gridx = 2;
		c2.gridy = 0;
		playerPanel.add(rb_green_cpu,c2);
		
		gr_green = new ButtonGroup();
		gr_green.add(rb_green_human);
		gr_green.add(rb_green_cpu);
		
		
		lbl_redPacman = new JLabel("Red:");
		c2.gridx = 0;
		c2.gridy = 1;
		playerPanel.add(lbl_redPacman,c2);
		
		rb_red_human = new JRadioButton("Human");
		
		c2.gridx = 1;
		c2.gridy = 1;
		playerPanel.add(rb_red_human,c2);
		
		rb_red_cpu = new JRadioButton("CPU");
		rb_red_cpu.setSelected(true);
		c2.gridx = 2;
		c2.gridy = 1;
		playerPanel.add(rb_red_cpu,c2);
		
		gr_red = new ButtonGroup();
		gr_red.add(rb_red_human);
		gr_red.add(rb_red_cpu);
		
		
		lbl_bluePacman = new JLabel("Blue:");
		c2.gridx = 0;
		c2.gridy = 2;
		playerPanel.add(lbl_bluePacman,c2);
		
		rb_blue_human = new JRadioButton("Human");
		rb_blue_human.setSelected(true);
		c2.gridx = 1;
		c2.gridy = 2;
		playerPanel.add(rb_blue_human,c2);
		
		rb_blue_cpu = new JRadioButton("CPU");
		c2.gridx = 2;
		c2.gridy = 2;
		playerPanel.add(rb_blue_cpu,c2);
		
		gr_blue = new ButtonGroup();
		gr_blue.add(rb_blue_human);
		gr_blue.add(rb_blue_cpu);
		
		
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth=2;
		getContentPane().add(playerPanel, c);
		
		
		/* SAVE BUTTON *********************************************** */
		
		btn_cancel = new JButton("Cancel");
		btn_cancel.setActionCommand(AC_CANCEL);
		btn_cancel.addActionListener(this);
		c.gridx = 0;
		c.gridy = 10;
		c.gridwidth=1;
		getContentPane().add(btn_cancel, c);
		
		btn_save = new JButton("Create");
		btn_save.setActionCommand(AC_CREATE);
		btn_save.addActionListener(this);
		c.gridx = 1;
		c.gridy = 10;
		getContentPane().add(btn_save, c);
	}

	private void createMap(GameMap map,List<Pacman> pacmen) throws IDaoSaveException {
		Command c = new Command();
		c.setType(CommandType.CREATEMAP);
		c.setMap(map);
		c.setPacmen(pacmen);
		cDao.sendCommand(c);
	}
	
	private void openMainFrame() {
		JFrame f = new GameSelctorGui(cDao);
		f.pack();
		f.setVisible(true);
		this.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(AC_CREATE)) {
			
			GameMap m = new GameMap();
			m.setName(tf_mapName.getText());
			m.setRoundsLimit((Integer)sp_mapRounds.getValue());
			m.setHeight((Integer)sp_mapHeight.getValue());
			m.setWidth((Integer)sp_mapWidth.getValue());
			m.setWallPercentage(new Double(((Integer) sp_wallPercentage.getValue()))/100);
			
			List<Pacman> pacmen = new ArrayList<Pacman>();
			pacmen.add(new Pacman(GameColor.GREEN, m.getId(),rb_green_human.isSelected()));
			pacmen.add(new Pacman(GameColor.RED, m.getId(),rb_red_human.isSelected()));
			pacmen.add(new Pacman(GameColor.BLUE, m.getId(),rb_blue_human.isSelected()));
			
			
			try {
				createMap(m,pacmen);
			} catch (IDaoSaveException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this,"Could not create Map","Error", JOptionPane.ERROR_MESSAGE);
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			openMainFrame();
		} else if(e.getActionCommand().equals(AC_CANCEL))  {
			openMainFrame();
		}
		
	}

}
