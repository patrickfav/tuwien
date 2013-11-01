package ticketline.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import ticketline.cfg.ConfigFactory;
import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.KuenstlerDAO;
import ticketline.db.Kuenstler;
import ticketline.gui.components.DefaultSearchTable;
import ticketline.gui.components.NoEditTableModel;

/**
*Kuenstler Suchen GUI
* 
* @author ReneN, DanielZ
* @version 0.1
*/
public class KuenstlerSuchenGui extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private static Integer panel_width = ConfigFactory.getConfig().getDefaultPanelWidth();
	private static Integer panel_heigth = ConfigFactory.getConfig().getDefaultPanelHeight();
	private static ResourceBundle lang;
	private static Logger logger = Logger.getLogger(KuenstlerSuchenGui.class);
		
	private JPanel mainpane;
	private JPanel opt_panel;
	
	private JLabel lbl_vname;	
	private JLabel lbl_nname;
	private JLabel lbl_sex;
	
	private JTextField tf_nname;
	private JTextField tf_vname;
	
	private JRadioButton rbt_male;
	private JRadioButton rbt_female;
	private JRadioButton rbt_both;
	
	private ButtonGroup group;
	
	private JButton bt_search;	
	
	private Object[][] 	data = {};
	private JTable		table;
	private TableModel 	tm;
	private DefaultSearchTable	tb_results;
	private JScrollPane scrl_table;
	private int colnum;
	
	public KuenstlerSuchenGui() {
		logger.debug("KuenstlerSuchenGui started");
		Gui.setPanelSize();
		
		lang = ConfigFactory.getConfig().getLanguageBundle();
		
		String[] colnames = {lang.getString("LBL_ARTIST_VNAME"), lang.getString("LBL_ARTIST_NNAME"), lang.getString("LBL_ARTIST_SEX")};
		
		mainpane = new JPanel();
		opt_panel = new JPanel();
		
		lbl_vname = new JLabel(lang.getString("LBL_ARTIST_VNAME"));
		lbl_nname = new JLabel(lang.getString("LBL_ARTIST_NNAME"));
		
		tf_vname = new JTextField(20);	
		tf_nname = new JTextField(20);
		
		group = new ButtonGroup();
		
		lbl_sex = new JLabel(lang.getString("LBL_ARTIST_SEX"));
		rbt_male = new JRadioButton(lang.getString("RB_ARTIST_MALE"));
		rbt_female = new JRadioButton(lang.getString("RB_ARTIST_FEMALE"));
		rbt_both = new JRadioButton(lang.getString("RB_ARTIST_BOTH"),true);
		group.add(rbt_male);
		group.add(rbt_female);
		group.add(rbt_both);
		
		tb_results = new DefaultSearchTable();
    	tm = new NoEditTableModel(data,colnames);
    	tb_results.setModel(tm);
    	//tb_results.setPreferredColumnWidths(percentageColumnWidth);
    	scrl_table = new JScrollPane(tb_results);
		
		bt_search = new JButton(lang.getString("BTN_ARTIST_SEARCH"));
		
		mainpane.setLayout(new BorderLayout(5, 5));
		add(mainpane);
		
		JLayeredPane lpane = new JLayeredPane();
		
		//Set title of Layered Pane
		lpane.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SEARCHARTIST")));
		lpane.setLayout(new GridBagLayout());
		lpane.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), ConfigFactory.getConfig().getDefaultPanelHeight()/2));
		lpane.setMinimumSize(new Dimension(600,650));
		setSize(panel_width, panel_heigth);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(5,5,5,5);
		lpane.add(lbl_vname,c);
	
		c.gridx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		lpane.add(tf_vname,c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_END;
		c.fill = GridBagConstraints.NONE;
		lpane.add(lbl_nname,c);
		
		c.gridx = 3;
		lpane.add(tf_nname,c);
		
		c.gridx = 1;
		c.gridy = 2;
		lpane.add(lbl_sex,c);
		
		opt_panel.setLayout(new BorderLayout(5, 5));
		opt_panel.add(rbt_male,BorderLayout.LINE_START);
		opt_panel.add(rbt_female,BorderLayout.CENTER);
		opt_panel.add(rbt_both,BorderLayout.LINE_END);
		c.gridx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		lpane.add(opt_panel,c);
		
		c.gridx = 6;
		c.gridy = 6;
		c.insets = new Insets(10,5,5,5);
		bt_search.addActionListener(this);
		bt_search.setActionCommand("search");
		lpane.add(bt_search,c);
		
		lpane.setVisible(true); 
		mainpane.add(lpane,BorderLayout.PAGE_START);
		
		JLayeredPane p_results = new JLayeredPane();
		p_results.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SEARCHARTISTRESULTS")));
		p_results.setLayout(new GridBagLayout());
		//p_results.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), ConfigFactory.getConfig().getDefaultPanelHeight()/2));
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.BOTH;
		/*DefaultTableModel tm = new DefaultTableModel(data, colnames);
		table.setModel(tm);*/
		scrl_table.setMinimumSize(new Dimension(500,200));
		scrl_table.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), ConfigFactory.getConfig().getDefaultPanelHeight()/2));
				
		p_results.add(scrl_table,c);
		
		p_results.setVisible(true);
		mainpane.add(p_results);
								
		logger.debug("KuenstlerSuchenGui Created Successfully");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("search")){
			Kuenstler k = new Kuenstler();
			colnum = 3;
			String[] colnames = {lang.getString("LBL_ARTIST_VNAME"), lang.getString("LBL_ARTIST_NNAME"), lang.getString("LBL_ARTIST_SEX")};
			
			if(!tf_nname.getText().trim().equals("")){
				k.setNname(tf_nname.getText().trim());
			}
			if(!tf_vname.getText().trim().equals("")){
				k.setVname(tf_vname.getText().trim());
			}
			if(rbt_male.isSelected()){
				k.setGeschlecht("M");
			}
			if(rbt_female.isSelected()){
				k.setGeschlecht("W");
			}
			if(rbt_both.isSelected()){
				k.setGeschlecht(null);
			}

			KuenstlerDAO dao = DAOFactory.getKuenstlerDAO();
			
			try{
				List<Kuenstler> kuenstlerList = dao.search(k);
				data = new Object[kuenstlerList.size()][colnum];
				if(kuenstlerList.size() == 0){
					int i = 0;
					for(Kuenstler kuenst: kuenstlerList){
						data[i][0] = "";
						data[i][1] = "";
						data[i][2] = "";
						i++;
					}
					tm = new NoEditTableModel(data,colnames);
					tb_results.setModel(tm);
					JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHART_NOTFOUND"),lang.getString("OPH_SEARCHART_NOTFOUND"),JOptionPane.WARNING_MESSAGE);
					
				}
				else{					
					int i = 0;
					for(Kuenstler kuenst: kuenstlerList){
						data[i][0] = kuenst.getVname();
						data[i][1] = kuenst.getNname();
						data[i][2] = kuenst.getGeschlecht();
						i++;
					}
					tm = new NoEditTableModel(data,colnames);
			    	tb_results.setModel(tm);
					
					logger.debug("ActionEvent:Search:HallTable: updating");
				}
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHART_SEARCHERROR"),lang.getString("OPH_SEARCHART_SEARCHERROR"),JOptionPane.WARNING_MESSAGE);
				logger.warn("ActionEvent:Search:BL:Warning: "+ex.getMessage());
			}
		}
	}
}
