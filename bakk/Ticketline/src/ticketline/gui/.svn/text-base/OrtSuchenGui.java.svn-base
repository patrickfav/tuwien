package ticketline.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import ticketline.bl.GuiMemory;
import ticketline.bl.ITrail;
import ticketline.bl.Trail;
import ticketline.cfg.ConfigFactory;
import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.AuffuehrungDAO;
import ticketline.dao.interfaces.OrtDAO;
import ticketline.db.Auffuehrung;
import ticketline.db.AuffuehrungKey;
import ticketline.db.Ort;
import ticketline.db.OrtKey;
import ticketline.gui.components.DefaultSearchTable;
import ticketline.gui.components.NoEditTableModel;

/**
 * Ort Suchen Gui
 * 
 * @author PatrickF
 * @version 0.2
 */
public class OrtSuchenGui extends JPanel implements ActionListener,ListSelectionListener,DocumentListener,ITrail {
	
	private static final long serialVersionUID = 1L;
	
	private static ResourceBundle lang;
	private static Integer panel_width = ConfigFactory.getConfig().getDefaultPanelWidth();
	private static Integer panel_heigth = ConfigFactory.getConfig().getDefaultPanelHeight();
	
	private static Logger logger = Logger.getLogger(OrtSuchenGui.class);

	private Trail trail;
	
	private Ort ort;
	
	private JLayeredPane pn_form;
	private JLayeredPane pn_list;
	
	private JLabel lbl_event;
	private JLabel lbl_location;
	private JLabel lbl_street;
	private JLabel lbl_plz;
	private JLabel lbl_state;
	private JLabel lbl_description;
	private JLabel lbl_salelocation;
	private JLabel lbl_eventlocation;
	private JLabel lbl_kiosk;
	
	private JTextField tf_event;
	private JTextField tf_location;
	private JTextField tf_street;
	private JTextField tf_plz;
	private JTextField tf_state;
	private JTextField tf_description;
	private JCheckBox cb_salelocation;
	private JCheckBox cb_eventlocation;
	private JCheckBox cb_kiosk;
	
	private JButton btn_search;
	private JButton btn_select;
	private JButton btn_back;
	private JButton	btn_deselectEvent;
	
	private ImageIcon del;
	
	private String[] colnames;
	private Object[][]	data;
	private DefaultSearchTable	tb_results;
	private TableModel tm;
	private JScrollPane scrl_table;
	
	private Double[] percentageColumnWidth;
	private int colNum;
	private ArrayList<Ort> oarr;
	
	public OrtSuchenGui() {
		Gui.setPanelSize();
		
		lang = ConfigFactory.getConfig().getLanguageBundle();
		
		pn_form = new JLayeredPane();
    	pn_list = new JLayeredPane();
    	
    	lbl_event = new JLabel(lang.getString("LBL_LOCATION_EVENT"));
    	lbl_location = new JLabel(lang.getString("LBL_LOCATION_NAME"));
    	lbl_street = new JLabel(lang.getString("LBL_LOCATION_STREET"));
    	lbl_plz = new JLabel(lang.getString("LBL_LOCATION_PLZ"));
    	lbl_state = new JLabel(lang.getString("LBL_LOCATION_STATE"));
    	lbl_description = new JLabel(lang.getString("LBL_LOCATION_DESCRIPTION"));
    	lbl_salelocation = new JLabel();
    	lbl_eventlocation = new JLabel();
    	lbl_kiosk = new JLabel();
    	
    	tf_location = new JTextField(15);
    	tf_street = new JTextField(20);
    	tf_plz = new JTextField(5);
    	tf_state = new JTextField(15);
    	tf_description = new JTextField(20);
    	tf_event = new JTextField(15);
    	
    	cb_salelocation = new JCheckBox(lang.getString("LBL_LOCATION_SALELOCATION"),false);
    	cb_eventlocation = new JCheckBox(lang.getString("LBL_LOCATION_EVENTLOCATION"),false);
    	cb_kiosk = new JCheckBox(lang.getString("LBL_LOCATION_KIOSK"),false);
    	
    	//x-del imageicon
    	del = new ImageIcon("images/x.png");
    	
    	btn_search = new JButton(lang.getString("BTN_LOCATION_SEARCH"));
    	btn_select = new JButton(lang.getString("BTN_LOCATION_SELECT"));
    	btn_back = new JButton(lang.getString("BTN_LOCATION_BACK"));
    	btn_deselectEvent = new JButton(del);
		
    	// Initialize Table Colnames
		colNum = 4;
    	colnames = new String[colNum];
    	colnames[0] = lang.getString("TCN_LOCATION_NAME");
    	colnames[1] = lang.getString("TCN_LOCATION_DESCRIPTION");
    	colnames[2] = lang.getString("TCN_LOCATION_STREET");
    	colnames[3] = lang.getString("TCN_LOCATION_OPTIONS");
    	
    	// Initialize Table
    	percentageColumnWidth = new Double[]{0.25, 0.25, 0.25, 0.25};
    	
    	tb_results = new DefaultSearchTable();
    	tm = new NoEditTableModel(data,colnames);
    	tb_results.setModel(tm);
    	tb_results.getSelectionModel().addListSelectionListener(this);
    	tb_results.setPreferredColumnWidths(percentageColumnWidth);
	    scrl_table = new JScrollPane(tb_results);	
    	
	    
	    // Layout
    	GridBagLayout gb_searchPlace = new GridBagLayout();
    	setLayout(gb_searchPlace);
    	setSize(panel_width, panel_heigth);
 		GridBagConstraints c = new GridBagConstraints();
 		c.anchor = GridBagConstraints.LINE_START;
 		c.insets = new Insets(5,5,5,5);

 		/**
 		 * Form Panel
 		 */
 		GridBagLayout gb_form = new GridBagLayout();
 		pn_form.setLayout(gb_form);
 		pn_form.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_LOCATION")));
 		
    	
	    // Column 1
 		c.gridx = 0;
		c.gridy = 0;
		gb_form.setConstraints(lbl_event, c);
		pn_form.add(lbl_event,c);
		c.gridy = 1;
		gb_form.setConstraints(lbl_location, c);
		pn_form.add(lbl_location,c);
		c.gridy = 2;
		gb_form.setConstraints(lbl_street, c);
		pn_form.add(lbl_street,c);
		c.gridy = 3;
		gb_form.setConstraints(lbl_plz, c);
		pn_form.add(lbl_plz,c);
		c.gridy = 4;
		gb_form.setConstraints(lbl_state, c);
		pn_form.add(lbl_state,c);
		c.gridy = 5;
		gb_form.setConstraints(lbl_description, c);
		pn_form.add(lbl_description,c);
		c.gridy = 6;
		gb_form.setConstraints(lbl_salelocation, c);
		pn_form.add(lbl_salelocation,c);
		c.gridy = 7;
		gb_form.setConstraints(lbl_eventlocation, c);
		pn_form.add(lbl_eventlocation,c);
		c.gridy = 8;
		gb_form.setConstraints(lbl_kiosk, c);
		pn_form.add(lbl_kiosk,c);
		
		
		// Column 2
		c.gridx = 1;
		c.gridy = 0;
		gb_form.setConstraints(tf_event, c);
		tf_event.setEnabled(false);
		pn_form.add(tf_event,c);	
		c.gridy = 1;
		gb_form.setConstraints(tf_location, c);
		tf_location.addActionListener(this);
		tf_location.setActionCommand("search");
		tf_location.getDocument().addDocumentListener(this);
		pn_form.add(tf_location,c);	
		c.gridy = 2;
		gb_form.setConstraints(tf_street, c);
		tf_street.addActionListener(this);
		tf_street.setActionCommand("search");
		tf_street.getDocument().addDocumentListener(this);
		pn_form.add(tf_street,c);	
		c.gridy = 3;
		gb_form.setConstraints(tf_plz, c);
		tf_plz.addActionListener(this);
		tf_plz.setActionCommand("search");
		pn_form.add(tf_plz,c);
		c.gridy = 4;
		gb_form.setConstraints(tf_state, c);
		tf_state.addActionListener(this);
		tf_state.setActionCommand("search");
		tf_state.getDocument().addDocumentListener(this);
		pn_form.add(tf_state,c);
		c.gridy = 5;
		gb_form.setConstraints(tf_description, c);
		tf_description.addActionListener(this);
		tf_description.setActionCommand("search");
		tf_description.getDocument().addDocumentListener(this);
		pn_form.add(tf_description,c);
		c.gridy = 6;
		gb_form.setConstraints(cb_salelocation, c);
		pn_form.add(cb_salelocation,c);
		c.gridy = 7;
		gb_form.setConstraints(cb_eventlocation, c);
		pn_form.add(cb_eventlocation,c);
		c.gridy = 8;
		gb_form.setConstraints(cb_kiosk, c);
		pn_form.add(cb_kiosk,c);
		
		//Column 3
		/*c.gridx = 3;
		c.gridy = 0;
		gb_form.setConstraints(btn_deselectEvent, c);
		btn_deselectEvent.addActionListener(this);
 		btn_deselectEvent.setActionCommand("deselectEvent");
		pn_form.add(btn_deselectEvent,c);	*/
		
		// Add Search Button
 		c.gridx = 0;
 		c.gridy = 8;
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
 		pn_form.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), (ConfigFactory.getConfig().getDefaultPanelHeight()/2)+60));
 		gb_searchPlace.setConstraints(pn_form, c);
 		add(pn_form);
		
 		/**
 		 * List Panel
 		 */
 		GridBagLayout gb_list = new GridBagLayout();
 		pn_list.setLayout(gb_list);
 		pn_list.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_LOCATIONRESULTS")));
 		
 		// Column 1
 		c.gridx = 0;
 		c.gridy = 0;
 		c.anchor = GridBagConstraints.CENTER;
 		scrl_table.setMinimumSize(new Dimension(570,600));	
 		scrl_table.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), (ConfigFactory.getConfig().getDefaultPanelHeight()/2)-60));
 		gb_list.setConstraints(scrl_table, c);
 		pn_list.add(scrl_table);
 		
 		// ADD LIST PANEL
 		c.gridx = 0;
 		c.gridy = 1;
 		gb_searchPlace.setConstraints(pn_list, c);
 		add(pn_list);
 		
 		// ADD BACK BUTTON
 		c.gridx = 0;
 		c.gridy = 2;
 		c.anchor = GridBagConstraints.LINE_START;
 		btn_back.addActionListener(this);
		btn_back.setActionCommand("back");
 		gb_searchPlace.setConstraints(btn_back, c);
 		add(btn_back);
 		
 		// ADD SELECT BUTTON
 		c.gridx = 0;
 		c.gridy = 2;
 		c.anchor = GridBagConstraints.LINE_END;
 		btn_select.addActionListener(this);
		btn_select.setActionCommand("select");
 		gb_searchPlace.setConstraints(btn_select, c);
 		btn_select.setEnabled(false);
 		add(btn_select);
		
		setVisible(true);

		
		logger.info("OrtSuchenGui started");
	}
	
	/*
     * ACTION PERFORMED - ACTION LISTENER
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
	
	public void actionPerformed(ActionEvent evt) {
		// Select
		if(evt.getActionCommand().equals("select")){
			GuiMemory.attachOrt(ort);
			GuiMemory.attachSignal("SIGEVTSEARCH");
			Gui.load((Component)trail.getPrevious(this));
			setVisible(true);
		}
		// Back
		if(evt.getActionCommand().equals("back")){
			//delete saal
			//GuiMemory.detachOrt();
			GuiMemory.attachSignal("SIGIGNORE");
			Gui.load((Component)trail.getPrevious(this));
			setVisible(true);
		}
		// deselect Event
		if(evt.getActionCommand().equals("deselectEvent")){		
			tf_event.setText(new String());
			GuiMemory.detachVeranstaltung();
			this.actionPerformed(new ActionEvent(this,new Random().nextInt(),"search"));
		}
		// Action: Search
		if(evt.getActionCommand().equals("search")){
			OrtKey ok = new OrtKey();
			Ort o = new Ort();
			
			if(!tf_location .getText().equals(""))
				ok.setBezeichnung(tf_location.getText().trim());
			if(!tf_description.getText().equals(""))
				ok.setOrt(tf_description.getText().trim());
			if(!tf_street.getText().equals(""))
				o.setStrasse(tf_street.getText().trim());
			if(!tf_state.getText().equals(""))
				o.setBundesland(tf_state.getText().trim());
			if(!tf_plz.getText().equals("")) {
				try {
					o.setPlz(new Integer(tf_plz.getText()).toString());
				} catch(Exception e) {
					JOptionPane.showMessageDialog(this, lang.getString("OPT_LOCATION_INPUTERROR_NUMBER"),lang.getString("OPH_LOCATION_INPUTERROR"),JOptionPane.WARNING_MESSAGE);
					logger.warn("ActionEvent:Search:Input:Warning: "+e.getMessage());
				}
			}
			if(cb_eventlocation.getSelectedObjects() != null)
				o.setAuffuehrungsort(true);
			if(cb_kiosk.getSelectedObjects() != null)
				o.setKiosk(true);
			if(cb_salelocation.getSelectedObjects() != null)
				o.setVerkaufsstelle(true);
			
			o.setComp_id(ok);
			
			OrtDAO odao = DAOFactory.getOrtDAO();
			
			
			oarr = new ArrayList<Ort>();
			//gather only locations with given veranstaltung (if set in gui mem)
			if(GuiMemory.isVeranstaltungSet()) {
				
				AuffuehrungDAO adao = DAOFactory.getAuffuehrungDAO();
				
				AuffuehrungKey ak = new AuffuehrungKey();
				Auffuehrung a = new Auffuehrung();
				
				a.setVeranstaltung(GuiMemory.getVeranstaltung());
				a.setComp_id(ak);
				
				List<Auffuehrung> result_auffuehrung = new ArrayList<Auffuehrung>();
				try {
					result_auffuehrung = adao.search(a);
				} catch(Exception e) {
					JOptionPane.showMessageDialog(this, lang.getString("OPT_LOCATION_SEARCHERROR"),lang.getString("OPH_LOCATION_SEARCHERROR"),JOptionPane.WARNING_MESSAGE);
					logger.warn("ActionEvent:Search:BL:Warning: "+e.getMessage());
				}
				
				//if results are found
				if(result_auffuehrung.size() > 0)
					for(int l=0;l<result_auffuehrung.size();l++)
						oarr = addToOrtArray(oarr,result_auffuehrung.get(l).getSaal().getOrt());
				
				//System.out.println("search with veranstaltung "+oarr.size()+" veranstaltung: "+GuiMemory.getVeranstaltung().getComp_id().getBezeichnung() +" ("+GuiMemory.getVeranstaltung().getComp_id().getKategorie()+")");
			}
			
			//search
			try {
				List<Ort> result = odao.search(o);
			
				if(result.size() == 0) {
					JOptionPane.showMessageDialog(this, lang.getString("OPT_LOCATION_NOFOUND"),lang.getString("OPH_LOCATION_INFO"),JOptionPane.INFORMATION_MESSAGE);
				} else {
					//if search with veranstaltung, only show result with given veranstaltung
					if(GuiMemory.isVeranstaltungSet()) {
						ArrayList<Ort> newResult = new ArrayList<Ort>();
						for(int i=0;i<result.size();i++) {
							if(containsOrt(oarr,result.get(i)))
								newResult.add(result.get(i));
						}
						result = new ArrayList<Ort>(newResult);
						//System.out.println("should work "+newResult.size() +" "+oarr.size());
					}
					
					data = new Object[result.size()][colNum];
					int i = 0;
					String opt_v,opt_a,opt_k;
					for(Ort or: result){
						data[i][0] = or.getComp_id().getBezeichnung();
						data[i][1] = or.getComp_id().getOrt();
						data[i][2] = or.getStrasse();
						
						//option strings
						opt_v = "nein";
						opt_a = " / nein";
						opt_k = " / nein";
						
						if(or.isVerkaufsstelle())
							opt_v = "ja";
						if(or.isAuffuehrungsort())
							opt_a = " / ja";	
						if(or.isKiosk())
							opt_k = " / ja";
						
						data[i][3] = opt_v+opt_a+opt_k;
						i++;
					}
					tm = new NoEditTableModel(data,colnames);
			    	tb_results.setModel(tm);
			    	tb_results.setPreferredColumnWidths(percentageColumnWidth);
			    	//updating title
			    	pn_list.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_LOCATIONRESULTS")+" ("+data.length+" "+ lang.getString("PNL_SEARCHCUSTOMERHITS") +")"));
					logger.debug("ActionEvent:Search:LocationTable: updating");
				}
			} catch(Exception e) {
				JOptionPane.showMessageDialog(this, lang.getString("OPT_LOCATION_SEARCHERROR"),lang.getString("OPH_LOCATION_SEARCHERROR"),JOptionPane.WARNING_MESSAGE);
				logger.warn("ActionEvent:Search:BL:Warning: "+e.getMessage());
			}
			
		}
	}

	
	/*
	 * LIST EVENT LISTENER
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if(tb_results.getSelectedRow() != -1 && tb_results.getValueAt(tb_results.getSelectedRow(),0) !=null && !tb_results.getValueAt(tb_results.getSelectedRow(),0).equals("")) {
			OrtKey ok = new OrtKey((String) tb_results.getValueAt(tb_results.getSelectedRow(),0),(String) tb_results.getValueAt(tb_results.getSelectedRow(),1));
			ort = DAOFactory.getOrtDAO().get(ok);
			
			btn_select.setEnabled(true);
			btn_select.setText(lang.getString("BTN_LOCATION_SELECT")+" [ "+ort.getComp_id().getOrt()+": "+ort.getComp_id().getBezeichnung()+"]");
		}
		//if nothing selected
		if(tb_results.getSelectedRow() == -1 || tb_results.getValueAt(tb_results.getSelectedRow(),0) ==null || tb_results.getValueAt(tb_results.getSelectedRow(),0).equals("")) {
			
			btn_select.setText(lang.getString("BTN_LOCATION_SELECT"));
			btn_select.setEnabled(false);
		}	
		
	}
	
	/*
	 * ITRAIL METHODS
	 * @see ticketline.bl.ITrail#setTrail(ticketline.bl.Trail)
	 */
	
	@Override
	public void setTrail(Trail trail) {
		this.trail = trail;
	}

	@Override
	public void reloadChanges() {
		//set the textfield for veranstaltung
		if(GuiMemory.isVeranstaltungSet()) {
			tf_event.setText(GuiMemory.getVeranstaltung().getComp_id().getBezeichnung()+" ("+GuiMemory.getVeranstaltung().getComp_id().getKategorie()+")");
		} else {
			tf_event.setText("");
		}
		
		emptyFields();
		this.actionPerformed(new ActionEvent(this,new Random().nextInt(),"search"));
	}

	@Override
	public void dummySearch() {
		DAOFactory.getOrtDAO().getAll();
	}
	
	/*
	 * DOCUMENT LISTENER
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */

	@Override
	public void changedUpdate(DocumentEvent de) {
	}

	@Override
	public void insertUpdate(DocumentEvent de) {
		checkTextFields(de);
	}

	@Override
	public void removeUpdate(DocumentEvent de) {
		checkTextFields(de);
	}
	
	/*
	 * PRIVATE METHODS
	 */
	
	/**
	 * Handles the textfield input (dynamically)
	 */
	private void checkTextFields(DocumentEvent de) {
		if(de.getDocument().getLength() > 2) {
			if(de.getDocument().equals(tf_description.getDocument())) {
				this.actionPerformed(new ActionEvent(tf_description,new Random().nextInt(),"search"));
			} else if(de.getDocument().equals(tf_location.getDocument())) {
				this.actionPerformed(new ActionEvent(tf_location,new Random().nextInt(),"search"));
			} else if(de.getDocument().equals(tf_street.getDocument())) {
				this.actionPerformed(new ActionEvent(tf_street,new Random().nextInt(),"search"));
			} else if(de.getDocument().equals(tf_state.getDocument())) {
				this.actionPerformed(new ActionEvent(tf_state,new Random().nextInt(),"search"));
			}
		} else {
			emptyTable();
		}
	}
	
	/**
	 * Emptys the search Table
	 */
	private void emptyTable() {
		Object[][] dd = new Object[][] {};
		tm = new NoEditTableModel(dd,colnames);
		tb_results.setPreferredColumnWidths(percentageColumnWidth);
    	tb_results.setModel(tm);
    	pn_list.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_LOCATIONRESULTS")));
	}
	
	/**
	 * Empty all textfields
	 */
	private void emptyFields() {
		tf_description.setText("");
		tf_location.setText("");
		tf_state.setText("");
		tf_street.setText("");
	}
	
	/**
	 * Trys to add an Ort to the given array, but ignores doubles.
	 * @param arr Ort ArrayList - the list to add
	 * @param o Ort
	 */
	private ArrayList<Ort> addToOrtArray(ArrayList<Ort> arr,Ort o) {	
		boolean found = containsOrt(arr,o);
		
		if(!found)
			arr.add(o);
		
		return arr;
	}
	
	/**
	 * Searches in the array if the given element is in the array and returns
	 * the result as boolean
	 * @param arr - haystack
	 * @param o - needle
	 * @return - if found
	 */
	private boolean containsOrt(ArrayList<Ort> arr,Ort o) {
		boolean found = false;
		
		for(int k=0;k<arr.size();k++) {
			if(arr.get(k).equals(o)){
				found = true;
				break;
			}
		}
		return found;
	}
}
