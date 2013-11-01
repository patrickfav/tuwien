package ticketline.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import ticketline.gui.components.DefaultSearchTable;
import ticketline.gui.components.NoEditTableModel;

import ticketline.bl.GuiMemory;
import ticketline.bl.ITrail;
import ticketline.bl.Trail;
import ticketline.cfg.ConfigFactory;
import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.SaalDAO;
import ticketline.db.Ort;
import ticketline.db.Saal;
import ticketline.db.SaalKey;
import ticketline.db.Transaktion;

/**
* 
* Saal Suchen GUI
* @author ReneN, FlorianR, PatrickF
* @version 0.2
*/
public class SaalSuchenGui extends JPanel implements ActionListener,ListSelectionListener,ITrail{
	
	private static final long serialVersionUID = -7532605018344844799L;
	
	private static ResourceBundle lang;
	private static Integer panel_width = ConfigFactory.getConfig().getDefaultPanelWidth();
	private static Integer panel_heigth = ConfigFactory.getConfig().getDefaultPanelHeight();
	
	private static Logger logger = Logger.getLogger(SaalSuchenGui.class);
	
	private Trail subtrailOrtSuchen;
	
	private Trail trail;
	
	//saal
	Saal saal;
	Ort ort;
	
	//Transaction
	private Transaktion ta;
	
	private JLayeredPane pn_form;
	private JLayeredPane pn_list;
	
	private JTextField	 tf_identifier;
    private JTextField	 tf_type;
    private JTextField	 tf_place;
    
    private JLabel	 lb_identifier;
    private JLabel	 lb_type;
    private JLabel	 lb_place;
    private JLabel	 lb_numberseats;
    
    private JButton	 btn_search;
    private JButton	 btn_select;
    private JButton	 btn_back;
    private JButton	 btn_selectPlace;
    private JButton	 btn_deselectPlace;
    
    private ImageIcon del;
    
    private SpinnerNumberModel nm;
    private JSpinner sp_seats;
    
    // Table
    private String[]	colnames;
	private Object[][] 	data;
	private TableModel 	tm;
	private DefaultSearchTable	tb_results;
	private JScrollPane scrl_results;
    
	private Double[] percentageColumnWidth;
	private int colNum;
	
    public SaalSuchenGui() {
    	Gui.setPanelSize();
    	
    	lang = ConfigFactory.getConfig().getLanguageBundle();
    	
    	
    	
    	pn_form = new JLayeredPane();
    	pn_list = new JLayeredPane();
    	
    	tf_identifier = new JTextField(15);
    	tf_type = new JTextField(15);
    	tf_place = new JTextField(15);
    	
    	lb_identifier = new JLabel(lang.getString("LBL_SEARCHHALL_BEZ"));
    	lb_type = new JLabel(lang.getString("LBL_SEARCHHALL_TYPE"));
    	lb_place = new JLabel(lang.getString("LBL_SEARCHHALL_PLACE"));
    	lb_numberseats = new JLabel(lang.getString("LBL_SEARCHHALL_SEAT"));
    	
    	//x-del imageicon
    	del = new ImageIcon("images/x.png");
    	
    	btn_search = new JButton(lang.getString("BTN_SEARCHHALL_SEARCH"));
    	btn_select = new JButton(lang.getString("BTN_SEARCHHALL_SELECT"));
    	btn_back = new JButton(lang.getString("BTN_SEARCHHALL_BACK"));
    	btn_selectPlace = new JButton(lang.getString("BTN_SEARCHHALL_SELECTPLACE"));
    	btn_deselectPlace = new JButton(del);
    	
    	// Seats Spinner
    	nm = new SpinnerNumberModel(999,0,10000,1);
    	sp_seats = new JSpinner(nm);
    	
    	// Initialize Table Colnames
    	colNum = 5;
    	colnames = new String[colNum];
    	colnames[0] = lang.getString("TCN_SEARCHHALL_BEZ");
    	colnames[1] = lang.getString("TCN_SEARCHHALL_PLACE");
    	colnames[2] = lang.getString("TCN_SEARCHHALL_PLACEBEZ");
    	colnames[3] = lang.getString("TCN_SEARCHHALL_TYPE");
    	colnames[4] = lang.getString("TCN_SEARCHHALL_COUNTSEATS");
    	
    	// Initialize Table
    	percentageColumnWidth = new Double[]{0.225, 0.225, 0.225, 0.225, 0.1};
    	
    	tb_results = new DefaultSearchTable();
    	tm = new NoEditTableModel(data,colnames);
    	tb_results.setModel(tm);
    	tb_results.getSelectionModel().addListSelectionListener(this);
    	tb_results.setPreferredColumnWidths(percentageColumnWidth);
    	scrl_results = new JScrollPane(tb_results);
    	
    	// Layout
    	GridBagLayout gb_searchhall = new GridBagLayout();
    	setLayout(gb_searchhall);
    	setSize(panel_width, panel_heigth);
 		GridBagConstraints c = new GridBagConstraints();
 		c.anchor = GridBagConstraints.LINE_START;
 		c.insets = new Insets(5,5,5,5);
 		
 		
 		/**
 		 * Form Panel
 		 */
 		GridBagLayout gb_form = new GridBagLayout();
 		pn_form.setLayout(gb_form);
 		pn_form.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SEARCHHALL")));
 		
 		// Column 1
 		c.gridx = 0;
 		gb_form.setConstraints(lb_identifier, c);
 		pn_form.add(lb_identifier);		
 		c.gridy = 1;
 		gb_form.setConstraints(lb_type, c);
 		pn_form.add(lb_type);
 		c.gridy = 2;
 		gb_form.setConstraints(lb_place, c);
 		pn_form.add(lb_place);
 		c.gridy = 3;
 		gb_form.setConstraints(lb_numberseats, c);
 		pn_form.add(lb_numberseats);
 		
 		// Column 2
 		c.gridx = 1;
 		c.gridy = 0;
 		gb_form.setConstraints(tf_identifier, c);
 		pn_form.add(tf_identifier);
 		c.gridy = 1;
 		gb_form.setConstraints(tf_type, c);
 		pn_form.add(tf_type);
 		c.gridy = 2;
 		gb_form.setConstraints(tf_place, c);
 		tf_place.setEnabled(false);
 		pn_form.add(tf_place);
 		c.gridy = 3;
 		gb_form.setConstraints(sp_seats, c);
 		pn_form.add(sp_seats);
 		
 		// Add deselect Place Button
 		c.gridx = 2;
 		c.gridy = 2;
 		btn_deselectPlace.addActionListener(this);
 		btn_deselectPlace.setActionCommand("deselectPlace");
 		gb_form.setConstraints(btn_deselectPlace, c);
 		btn_deselectPlace.setPreferredSize(ConfigFactory.getConfig().getDeSelectButtonDimension());	
 		pn_form.add(btn_deselectPlace);
 		
 		// Add Select Place Button
 		c.gridx = 3;
 		c.gridy = 2;
 		btn_selectPlace.addActionListener(this);
 		btn_selectPlace.setActionCommand("selectPlace");
 		gb_form.setConstraints(btn_selectPlace, c);
 		btn_selectPlace.setPreferredSize(ConfigFactory.getConfig().getDefaultSubSelectButtonDimension());	
 		pn_form.add(btn_selectPlace);

 		// Add Search Button
 		c.gridx = 0;
 		c.gridy = 4;
 		btn_search.addActionListener(this);
 		btn_search.setActionCommand("search");
 		gb_form.setConstraints(btn_search, c);
 		pn_form.add(btn_search);
 		
 		// ADD FORM PANEL
 		c.gridx = 0;
 		c.gridy = 0;
 		c.gridheight = 1;
 		c.gridwidth = 1;
 		pn_form.setMinimumSize(new Dimension(570,600));	
 		pn_form.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), ConfigFactory.getConfig().getDefaultPanelHeight()/2));
 		gb_searchhall.setConstraints(pn_form, c);
 		add(pn_form);
 		
 		
 		/**
 		 * List Panel
 		 */
 		GridBagLayout gb_list = new GridBagLayout();
 		pn_list.setLayout(gb_list);
 		pn_list.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SEARCHHALLRESULTS")));
 		
 		// Column 1
 		c.gridx = 0;
 		c.gridy = 0;
 		c.anchor = GridBagConstraints.CENTER;
 		scrl_results.setMinimumSize(new Dimension(570,600));	
 		scrl_results.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), ConfigFactory.getConfig().getDefaultPanelHeight()/2));
 		gb_list.setConstraints(scrl_results, c);
 		pn_list.add(scrl_results);
 		
 		// ADD LIST PANEL
 		c.gridx = 0;
 		c.gridy = 1;
 		gb_searchhall.setConstraints(pn_list, c);
 		add(pn_list);
 		
 		// ADD BACK BUTTON
 		c.gridx = 0;
 		c.gridy = 2;
 		c.anchor = GridBagConstraints.LINE_START;
 		btn_back.addActionListener(this);
 		btn_back.setActionCommand("back");
 		gb_searchhall.setConstraints(btn_back, c);
 		add(btn_back);
 		
 		// ADD SELECT BUTTON
 		c.gridx = 0;
 		c.gridy = 2;
 		c.anchor = GridBagConstraints.LINE_END;
 		btn_select.addActionListener(this);
 		btn_select.setActionCommand("select");
 		gb_searchhall.setConstraints(btn_select, c);
 		btn_select.setEnabled(false);
 		add(btn_select);
		 	
 		logger.info("Saal suchen started.");
 		setVisible(true); 		
 		
 		loadSubtrails();
}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// Action: Search
		if(evt.getActionCommand().equals("search")){
			SaalKey sk = new SaalKey();
			Saal s = new Saal();
			
			if(!tf_identifier.getText().equals(""))
				sk.setBezeichnung(tf_identifier.getText().trim());
			if(!tf_place.getText().equals(""))
				sk.setOrtbez(tf_place.getText().trim());
			if(!sp_seats.getValue().equals("")) {
				try {
					s.setAnzplaetze((new Integer((Integer) sp_seats.getValue())));
				} catch(Exception e) {
					JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHHALL_INPUTERROR_NUMBER"),lang.getString("OPH_SEARCHHALL_INPUTERROR"),JOptionPane.WARNING_MESSAGE);
					logger.warn("ActionEvent:Search:Input:Warning: "+e.getMessage());
				}
			}
			
			s.setComp_id(sk);
			
			SaalDAO sdao = DAOFactory.getSaalDAO();
			
			try {
				List<Saal> result = sdao.search(s);
			
				if(result.size() == 0) {
					JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHHALL_NOFOUND"),lang.getString("OPH_SEARCHHALL_INFO"),JOptionPane.INFORMATION_MESSAGE);
				} else {
					data = new Object[result.size()][colNum];
					int i = 0;
					for(Saal sr: result){
						data[i][0] = sr.getComp_id().getBezeichnung();
						data[i][1] = sr.getComp_id().getOrt();
						data[i][2] = sr.getComp_id().getOrtbez();
						data[i][3] = sr.getTyp();
						data[i][4] = sr.getAnzplaetze();
						i++;
					}
					tm = new NoEditTableModel(data,colnames);
			    	tb_results.setModel(tm);
			    	tb_results.setPreferredColumnWidths(percentageColumnWidth);
					
					logger.debug("ActionEvent:Search:HallTable: updating");
				}
			} catch(Exception e) {
				JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHHALL_SEARCHERROR"),lang.getString("OPH_SEARCHHALL_SEARCHERROR"),JOptionPane.WARNING_MESSAGE);
				logger.warn("ActionEvent:Search:BL:Warning: "+e.getMessage());
			}
			
		}
		
		// Back
		if(evt.getActionCommand().equals("back")){
			//delete saal
			GuiMemory.detachSaal();
			Gui.load((Component)trail.getPrevious(this));
			setVisible(true);
		}
		
		// Select
		if(evt.getActionCommand().equals("select")){		
			GuiMemory.attachSaal(saal);
			Gui.load((Component)trail.getPrevious(this));
			setVisible(true);
		}
		
		// Select Place
		if(evt.getActionCommand().equals("selectPlace")){		
			subtrailOrtSuchen.getNext(this).setTrail(subtrailOrtSuchen);
			Gui.load((Component)subtrailOrtSuchen.getNext(this));
			setVisible(true);
		}
		
		// deselect Place
		if(evt.getActionCommand().equals("deselectPlace")){		
			ort = new Ort();
			tf_place.setText(new String());
		}
	}

	@Override
	public void setTrail(Trail trail) {
		this.trail = trail;
	}
	
	private void loadSubtrails(){
		subtrailOrtSuchen = new Trail(3);
		subtrailOrtSuchen.addNode(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if(tb_results.getSelectedRow() != -1 && tb_results.getValueAt(tb_results.getSelectedRow(),0) !=null && !tb_results.getValueAt(tb_results.getSelectedRow(),0).equals("")) {
			SaalKey sk = new SaalKey((String) tb_results.getValueAt(tb_results.getSelectedRow(),0),(String) tb_results.getValueAt(tb_results.getSelectedRow(),2),(String) tb_results.getValueAt(tb_results.getSelectedRow(),1));
			saal = DAOFactory.getSaalDAO().get(sk);
			
			//TODO
			// what should be added to the transaktion ????
			//ta.set
			
			btn_select.setEnabled(true);
			btn_select.setText(lang.getString("BTN_SEARCHHALL_SELECT") + " [" +saal.getComp_id().getOrtbez()+":" +saal.getComp_id().getBezeichnung()+"]");
			
			//updates the transaktion and saal
			//GuiMemory.updateTransaktion(ta);
			//GuiMemory.attachSaal(saal);
		}
		//if nothing selected
		if(tb_results.getSelectedRow() == -1 || tb_results.getValueAt(tb_results.getSelectedRow(),0) ==null || tb_results.getValueAt(tb_results.getSelectedRow(),0).equals("")) {
			
			btn_select.setText(lang.getString("BTN_SEARCHHALL_SELECT"));
			btn_select.setEnabled(false);
		}
		
	}

	@Override
	public void reloadChanges() {
		if(GuiMemory.isOrtSet()) {
			//setting intern saal object and textfield
			ort = GuiMemory.detachOrt();
			tf_place.setText(ort.getComp_id().getBezeichnung());
		}
	}

	@Override
	public void dummySearch() {
		DAOFactory.getSaalDAO().getAll();
	}
}
