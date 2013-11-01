package ticketline.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
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
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.log4j.Logger;

import ticketline.bl.GuiMemory;
import ticketline.bl.ITrail;
import ticketline.bl.Trail;
import ticketline.cfg.ConfigFactory;
import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.KundeDAO;
import ticketline.db.Kunde;
import ticketline.db.Transaktion;
import ticketline.gui.components.DateCellRenderer;
import ticketline.gui.components.DateTimeCellRenderer;
import ticketline.gui.components.DefaultSearchTable;
import ticketline.gui.components.NoEditTableModel;
import ticketline.gui.components.RadioButtonCellRenderer;

/**
 * 
 * Search Customer Panel
 * @author FlorianR, PatrickF, ReneN
 * @version 0.6
 *
 */
public class KundeSuchenGui extends JPanel implements ActionListener,ListSelectionListener,DocumentListener, ITrail {
	
	// Serial Version
	private static final long serialVersionUID = 1L;
	
	// Static
	private static ResourceBundle lang;
	private static Logger logger = Logger.getLogger(KundeSuchenGui.class);
	private static Integer panel_width = ConfigFactory.getConfig().getDefaultPanelWidth();
	private static Integer panel_heigth = ConfigFactory.getConfig().getDefaultPanelHeight();
	
	// Trail
	private Trail trail;
	private Trail subtrail;
	private Trail subtrail_edit;
	
	//Transaction
	private Transaktion ta;
	
	//Dummy User
	private Integer dummyID = ConfigFactory.getConfig().getDummyUser();
	private Integer choosenID;
	
	// Panels
	private JLayeredPane pn_form;
	private JLayeredPane pn_list;
	
	// Textfields
	private JTextField tf_nname;
    private JTextField tf_vname;
    private JTextField tf_cardNr;
    private JTextField tf_plz;  
    
    // Labels
    private JLabel lbl_nname;
    private JLabel lbl_vname;
    private JLabel lbl_gender;
    private JLabel lbl_cardNr;
    private JLabel lbl_plz;
    
    // Buttons
    private JButton btn_search;
    private JButton	btn_next;
    private JButton	btn_next2;
    private JButton	btn_edit;
    
    // Radio Buttons
    private ButtonGroup bg_gender;
    private ButtonGroup bg_list;
    private JRadioButton rb_male;
    private JRadioButton rb_female;
    private JRadioButton rb_any;
    private JRadioButton rb_anonym;
    private JRadioButton rb_choosen;
    private JRadioButton[] rb_costumerList;
    
    // Table
    private String[] colnames;	
	private Object[][] data;
	
	private DefaultSearchTable tb_results;
	private NoEditTableModel tModel;
	private JScrollPane scrl_results;
    
	// Misc
	private Random rn;
	private Double[] percentageColumnWidth;
	private int colNum;
	
	/**
	 * Constructor
	 */
    public KundeSuchenGui() {
    	Gui.setPanelSize();
    	
    	// Initialize Language
    	lang = ConfigFactory.getConfig().getLanguageBundle();
    	
    	// Initialize Transaction
    	ta = new Transaktion();
    	
    	//randomizer
    	rn = new Random();
    	
    	subtrail = new Trail(6);
		subtrail.addNode(this);
		
		subtrail_edit = new Trail(7);
		subtrail_edit.addNode(this);
    	
    	// Initialize Panels
    	pn_form = new JLayeredPane();
    	pn_list = new JLayeredPane();
    	
    	// Initialize Textfields
    	tf_nname = new JTextField(20);
    	tf_vname = new JTextField(20);
    	tf_cardNr = new JTextField(6);
    	tf_plz = new JTextField(6);
    	
    	// Initialize Labels
    	lbl_nname = new JLabel(lang.getString("LBL_SEARCHCUSTOMER_NNAME"));
    	lbl_vname = new JLabel(lang.getString("LBL_SEARCHCUSTOMER_VNAME"));
    	lbl_gender = new JLabel(lang.getString("LBL_SEARCHCUSTOMER_GENDER"));
    	lbl_cardNr = new JLabel(lang.getString("LBL_SEARCHCUSTOMER_CARDNR"));
    	lbl_plz = new JLabel(lang.getString("LBL_SEARCHCUSTOMER_PLZ"));
    	
    	// Initialize Buttons
    	btn_search = new JButton(lang.getString("BTN_SEARCHCUSTOMER_SEARCH"));
    	btn_next = new JButton(lang.getString("BTN_SEARCHCUSTOMER_NEXT"));
    	btn_next2 = new JButton(lang.getString("BTN_SEARCHCUSTOMER_NEXT2"));  	
    	btn_edit = new JButton(lang.getString("BTN_SEARCHCUSTOMER_EDIT"));
    	
    	// Initialize Radio Buttons
    	bg_gender = new ButtonGroup();
    	rb_male = new JRadioButton(lang.getString("RB_SEARCHCUSTOMER_MALE"));
    	rb_female = new JRadioButton(lang.getString("RB_SEARCHCUSTOMER_FEMALE"));
    	rb_any = new JRadioButton(lang.getString("RB_SEARCHCUSTOMER_BOTH"),true);

    	bg_gender.add(rb_male);
    	bg_gender.add(rb_female);
    	bg_gender.add(rb_any); 	
    	
    	
    	// Initialize Table Colnames
    	colNum = 6;
    	colnames = new String[colNum];
    	colnames[0] = new String("");
    	colnames[1] = lang.getString("TCN_SEARCHCUSTOMER_NR");
    	colnames[2] = lang.getString("TCN_SEARCHCUSTOMER_NAME");
    	colnames[3] = lang.getString("TCN_SEARCHCUSTOMER_FIRSTNAME");
    	colnames[4] = lang.getString("TCN_SEARCHCUSTOMER_BD");
    	colnames[5] = lang.getString("TCN_SEARCHCUSTOMER_PLZ");
    	
    	// Initialize Table
    	percentageColumnWidth = new Double[]{0.006, 0.094, 0.24, 0.23, 0.23,0.1};
    	tb_results = new DefaultSearchTable(false,false);
    	tModel = new NoEditTableModel(setInitialRows(),colnames);
    	tb_results.setModel(tModel);
    	tb_results.getColumn("").setCellRenderer(new RadioButtonCellRenderer());
    	tb_results.getColumn(lang.getString("TCN_SEARCHCUSTOMER_BD")).setCellRenderer(new DateCellRenderer());
    	tb_results.setPreferredColumnWidths(percentageColumnWidth);
    	tb_results.getSelectionModel().addListSelectionListener(this);
    	scrl_results = new JScrollPane(tb_results);
    	
    	// Set Layout
    	GridBagLayout gb_searchCustomer = new GridBagLayout();
    	setLayout(gb_searchCustomer);
    	setSize(new Dimension(panel_width, panel_heigth));
 		GridBagConstraints c = new GridBagConstraints();
 		c.anchor = GridBagConstraints.LINE_START;
 		c.insets = new Insets(5,5,5,5);		
 		
 		/**
 		 * Form Panel
 		 */
 		
 		// Set Layout
 		GridBagLayout gb_form = new GridBagLayout();
 		pn_form.setLayout(gb_form);
 		pn_form.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SEARCHCUSTOMER")));
 		
 		// Column 1
 		c.gridx = 0;
 		gb_form.setConstraints(lbl_nname, c);
 		pn_form.add(lbl_nname);		
 		c.gridy = 1;
 		gb_form.setConstraints(lbl_vname, c);
 		pn_form.add(lbl_vname);
 		c.gridy = 2;
 		gb_form.setConstraints(lbl_gender, c);
 		pn_form.add(lbl_gender);
 		c.gridy = 3;
 		gb_form.setConstraints(lbl_cardNr, c);
 		pn_form.add(lbl_cardNr);
 		c.gridy = 4;
 		gb_form.setConstraints(lbl_plz, c);
 		pn_form.add(lbl_plz);
 		
 		// Column 2
 		c.gridx = 1;
 		c.gridy = 0;
 		gb_form.setConstraints(tf_nname, c);
 		tf_nname.addActionListener(this);
 		tf_nname.setActionCommand("search");
 		tf_nname.getDocument().addDocumentListener(this);
 		pn_form.add(tf_nname);		
 		c.gridy = 1;
 		gb_form.setConstraints(tf_vname, c);
 		tf_vname.addActionListener(this);
 		tf_vname.setActionCommand("search");
 		tf_vname.getDocument().addDocumentListener(this);
 		pn_form.add(tf_vname);
 		c.gridy = 2;
 		c.anchor = GridBagConstraints.LINE_START;
 		gb_form.setConstraints(rb_male, c);
 		pn_form.add(rb_male);
 		c.anchor = GridBagConstraints.CENTER;
 		gb_form.setConstraints(rb_female, c);
 		pn_form.add(rb_female);
 		c.anchor = GridBagConstraints.LINE_END;
 		gb_form.setConstraints(rb_any, c);
 		pn_form.add(rb_any);
 		c.gridy = 3;
 		c.anchor = GridBagConstraints.LINE_START;
 		gb_form.setConstraints(tf_cardNr, c);
 		tf_cardNr.addActionListener(this);
 		tf_cardNr.setActionCommand("search");
 		pn_form.add(tf_cardNr);
 		c.gridy = 4;
 		gb_form.setConstraints(tf_plz, c);
 		tf_plz.addActionListener(this);
 		tf_plz.setActionCommand("search");
 		pn_form.add(tf_plz);
 		
 		// Add Search Button
 		c.gridx = 0;
 		c.gridy = 7;
 		btn_search.addActionListener(this);
 		btn_search.setActionCommand("search");
 		gb_form.setConstraints(btn_search, c);
 		pn_form.add(btn_search);
 		
 		// Add Form Panel
 		c.gridx = 0;
 		c.gridy = 0;
 		c.gridheight = 1;
 		c.gridwidth = 1;
 		pn_form.setMinimumSize(new Dimension(570,600));	
 		pn_form.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), ConfigFactory.getConfig().getDefaultPanelHeight()/2));
 		gb_searchCustomer.setConstraints(pn_form, c);
 		add(pn_form);
 		
 		
 		/**
 		 * List Panel
 		 */
 		
 		// Set Layout
 		GridBagLayout gb_list = new GridBagLayout();
 		pn_list.setLayout(gb_list);
 		pn_list.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SEARCHHALLRESULTS")));
 		
 		// Column 1
 		c.gridx = 0;
 		c.gridy = 0;
 		c.anchor = GridBagConstraints.CENTER;
 		scrl_results.setMinimumSize(new Dimension(570,600));	
 		scrl_results.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), (ConfigFactory.getConfig().getDefaultPanelHeight()/2)-50));
 		gb_list.setConstraints(scrl_results, c);
 		pn_list.add(scrl_results);
 		
 		// Add List Panel
 		c.gridx = 0;
 		c.gridy = 1;
 		gb_searchCustomer.setConstraints(pn_list, c);
 		add(pn_list);
 		
 		// Add Edit Button
 		c.gridx = 0;
 		c.gridy = 3;
 		c.anchor = GridBagConstraints.LINE_START;
 		btn_edit.addActionListener(this);
		btn_edit.setActionCommand("edit");
 		gb_searchCustomer.setConstraints(btn_edit, c);
 		// If Kunde is set enable Edit Button
		if(GuiMemory.isKundeSet() && !GuiMemory.getKunde().getKartennr().equals(dummyID)) {
			btn_edit.setEnabled(true);
		} else {
			btn_edit.setEnabled(false);
		}
 		add(btn_edit);
 		
 		// Add Next Button
 		c.gridx = 0;
 		c.gridy = 2;
 		c.anchor = GridBagConstraints.LINE_END;
 		btn_next.addActionListener(this);
		btn_next.setActionCommand("next");
 		gb_searchCustomer.setConstraints(btn_next, c);
 		add(btn_next);
 		
 		// Add Next2 Button
 		c.gridx = 0;
 		c.gridy = 3;
 		c.anchor = GridBagConstraints.LINE_END;
 		btn_next2.addActionListener(this);
		btn_next2.setActionCommand("next2");
 		gb_searchCustomer.setConstraints(btn_next2, c);
 		add(btn_next2);
 		
 		// Set Visible
 		setVisible(true); 		
 		
    }
    
    
    /*
     * ACTION PERFORMED - ACTION LISTENER
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
	@Override
	public void actionPerformed(ActionEvent ev) {
		// Action: Next
		if(ev.getActionCommand().equals("next")){
			trail.getNext(this).setTrail(trail);
			Gui.load((Component)trail.getNext(this));
			setVisible(true);
		}
		
		// Action: Next2
		if(ev.getActionCommand().equals("next2")){
			subtrail.getNext(this).setTrail(subtrail);
			Gui.load((Component)subtrail.getNext(this));
			setVisible(true);
		}
		
		// Action: Edit
		if(ev.getActionCommand().equals("edit")){
			subtrail_edit.getNext(this).setTrail(subtrail_edit);
			Gui.load((Component)subtrail_edit.getNext(this));
			setVisible(true);
		}
		// Action: Search
		if(ev.getActionCommand().equals("search")){
			Kunde k = new Kunde();
			
			if(!tf_vname.getText().equals(""))
				k.setVname(tf_vname.getText().trim());
			if(!tf_nname.getText().equals(""))
				k.setNname(tf_nname.getText().trim());
			if(!tf_cardNr.getText().equals("")) {
				try {
					Integer knr = new Integer(tf_cardNr.getText().trim());
					k.setKartennr(knr);
					if(knr < 0)
						throw new Exception();
				} catch(Exception e) {
					JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHCUSTOMER_INPUTERROR_NUMBER"),lang.getString("OPH_SEARCHCUSTOMER_INPUTERROR"),JOptionPane.WARNING_MESSAGE);
					logger.warn("ActionEvent:Search:Input:Warning: "+e.getMessage());
					return;
				}
			}
			if(!tf_plz.getText().equals("")) {
				try {
					k.setPlz(new Integer(tf_plz.getText().trim()).toString());
				} catch(Exception e) {
					JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHCUSTOMER_INPUTERROR_NUMBER"),lang.getString("OPH_SEARCHCUSTOMER_INPUTERROR"),JOptionPane.WARNING_MESSAGE);
					logger.warn("ActionEvent:Search:Input:Warning: "+e.getMessage()+" - "+e.toString()+" - " +e.getStackTrace());
				}
			}

			if(rb_any.getSelectedObjects() == null)
				if(rb_male.getSelectedObjects() == null) {
					k.setGeschlecht("W");
				} else {
					k.setGeschlecht("M");
				}
			
			KundeDAO kdao = DAOFactory.getKundeDAO();
			
			try {
				List<Kunde> result = kdao.search(k);
			
				if(result.size() == 0) {
					JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHCUSTOMER_NOFOUND"),lang.getString("OPH_SEARCHCUSTOMER_INFO"),JOptionPane.INFORMATION_MESSAGE);
				} else {
					
					int i,j=0;

					bg_list = new ButtonGroup();
					
					Object[][] fRows = setInitialRows();
					
					//costumer is already set, display it!
					if(GuiMemory.isKundeSet() && !GuiMemory.getKunde().getKartennr().equals(dummyID)) {
						//initializes the data array for the table
						data = new Object[result.size()+2][colNum];
						rb_costumerList = new JRadioButton[result.size()+2];
						rb_costumerList[0] = rb_anonym;
						rb_costumerList[1] = rb_choosen;
						
						bg_list.add(rb_anonym);
						bg_list.add(rb_choosen);
						
						data[0] = fRows[0];
						data[1] = fRows[1];
						
						choosenID = GuiMemory.getKunde().getKartennr();
						i=2;
					} else { //costumer is not set, only first row is displayed
						//initializes the data array for the table
						data = new Object[result.size()+1][colNum];
						rb_costumerList = new JRadioButton[result.size()+1];
						rb_costumerList[0] = rb_anonym;

						bg_list.add(rb_anonym);
						
						data[0] = fRows[0];
						
						choosenID = dummyID;
						i=1;
					}
					
					//iterates through the results
					
					for(Kunde kr: result){	
						if(!kr.getKartennr().equals(dummyID)) {
							if(!choosenID.equals(kr.getKartennr())) {
								rb_costumerList[i] = new JRadioButton("",false);
								
								if(GuiMemory.isKundeSet())
									if(GuiMemory.getKunde().getKartennr() == kr.getKartennr())
										rb_costumerList[i] = new JRadioButton("",true);
		
								
								data[i][0] = rb_costumerList[i];
								data[i][1] = kr.getKartennr();
								data[i][2] = kr.getNname();
								data[i][3] = kr.getVname();
					    		data[i][4] = kr.getGeburtsdatum();
					    		data[i][5] = kr.getPlz();
								
								bg_list.add(rb_costumerList[i]);
								i++;
							} else {
								j--;
							}
						} else {
							j--;
						}
					}
					
					//cuts array rows, if there are too many
					if(j != 0) {
						Object[][] temp = new Object[data.length+j][colNum];
						for(i=0;i<data.length+j;i++)
							temp[i] = data[i];
						
						data = new Object[data.length+j][colNum];
						data = temp;
					}
					
					//updating table
					tModel.setDataVector(data,colnames);
					tb_results.setModel(tModel);
			    	//for correct rendering of the radiobuttons
			    	tb_results.getColumn("").setCellRenderer(new RadioButtonCellRenderer());
			    	tb_results.getColumn(lang.getString("TCN_SEARCHCUSTOMER_BD")).setCellRenderer(new DateCellRenderer());
			    	tb_results.setPreferredColumnWidths(percentageColumnWidth);
			    	
			    	//updating title
			    	pn_list.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SEARCHCUSTOMERRESULTS")+" ("+data.length+" "+ lang.getString("PNL_SEARCHCUSTOMERHITS") +")"));
			    	
					logger.debug("ActionEvent:Search:UserTable: updating");
				}
			} catch(Exception e) {
				//e.printStackTrace();
	            //System.out.println("ERROR: " + e.getMessage());
				JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHCUSTOMER_SEARCHERROR"),lang.getString("OPH_SEARCHCUSTOMER_SEARCHERROR"),JOptionPane.WARNING_MESSAGE);
				logger.warn("ActionEvent:Search:BL:Warning: "+e.getMessage()+" - "+e.toString()+" - " +e.getStackTrace());
			}	
		}
	}
    
	
	/*
	 * LIST EVENT LISTENER
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
    	
		//maps the columns
    	//for(int x = 0; x < colnames.length; x++) {
    	//	columns_mapping[x] = tb_results.convertColumnIndexToView(x);
    	//}	
		
		if(tb_results.getSelectedRow() != -1 && tb_results.getValueAt(tb_results.getSelectedRow(),1) !=null && !tb_results.getValueAt(tb_results.getSelectedRow(),1).equals("")) {
			Kunde k = DAOFactory.getKundeDAO().get((Integer) tb_results.getValueAt(tb_results.getSelectedRow(),1));
			
			//setting the radiobutton to selected
			rb_costumerList[tb_results.getSelectedRow()].setSelected(true);
			tModel.setValueAt(rb_costumerList[tb_results.getSelectedRow()], tb_results.getSelectedRow(),0);
			
			tb_results.repaint();
			
			if(!k.getKartennr().equals(dummyID)) {
				btn_edit.setEnabled(true);
			} else {
				btn_edit.setEnabled(false);
			}
			
			//updates the transaktion and Kunde
			//GuiMemory.updateTransaktion(ta);
			GuiMemory.attachKunde(k);
			
			//updates the verkaufTab panel button
			GuiMemory.getActionListener("VerkauferTab").actionPerformed(new ActionEvent(this,rn.nextInt(),"updateCostumerBtn"));
		}
	}
	
 
	
    /*
	 * ITRAIL METHODS
	 * @see ticketline.bl.ITrail#setTrail(ticketline.bl.Trail)
	 */
   
	public void setTrail(Trail trail){
		this.trail = trail;
		if(trail.getTrailNumber() == 8){
			btn_next2.setText(lang.getString("BTN_SEARCHVUSTOMER_PROMO"));
			btn_next.setVisible(false);
			subtrail = trail;
		}
	}
	@Override
	public void reloadChanges() {
		//nothing to load
	}
	@Override
	public void dummySearch() {
		DAOFactory.getKundeDAO().getAll();
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
     *  sets dynamically the first 2 rows, depending if a costumer is set
     *  @return first or first + second row
     */
    private Object[][] setInitialRows() {
		Object[] firstRow = new Object[colNum];
		Object[] secondRow = new Object[colNum];
		
		bg_list = new ButtonGroup();
		
		rb_costumerList = new JRadioButton[2];
		
		rb_anonym = new JRadioButton("",!(GuiMemory.isKundeSet() && !GuiMemory.getKunde().getKartennr().equals(dummyID)));
		rb_costumerList[0] = rb_anonym;
		bg_list.add(rb_anonym);
    	
		//initializes firstrow = the row with anonymous costumer
		firstRow[0] = rb_anonym;
		firstRow[1] = dummyID;
		firstRow[2] = lang.getString("BTN_SELLER_SEARCH");

    	//if costumer is set?
    	if(GuiMemory.isKundeSet() && !GuiMemory.getKunde().getKartennr().equals(dummyID)) {
    		rb_choosen = new JRadioButton("",true);
    		rb_costumerList[1] = rb_choosen;
    		bg_list.add(rb_choosen);
    		
    		//initializes firstrow = the row with anonymous costumer
    		secondRow[0] = rb_choosen;
    		secondRow[1] = GuiMemory.getKunde().getKartennr();
    		secondRow[2] = GuiMemory.getKunde().getNname();
    		secondRow[3] = GuiMemory.getKunde().getVname();
    		secondRow[4] = GuiMemory.getKunde().getGeburtsdatum();
    		secondRow[5] = GuiMemory.getKunde().getPlz();
    		
    		return new Object[][] {firstRow, secondRow};
    	}
    	
    	return new Object[][] {firstRow};
    	
    }
    
    /**
	 * Handles the textfield input (dynamically)
	 */
	private void checkTextFields(DocumentEvent de) {
		if(de.getDocument().getLength() > 2) {
			if(de.getDocument().equals(tf_nname.getDocument())) {
				this.actionPerformed(new ActionEvent(tf_nname,new Random().nextInt(),"search"));
			} else if(de.getDocument().equals(tf_vname.getDocument())) {
				this.actionPerformed(new ActionEvent(tf_vname,new Random().nextInt(),"search"));
			} else if(de.getDocument().equals(tf_plz.getDocument())) {
				this.actionPerformed(new ActionEvent(tf_plz,new Random().nextInt(),"search"));
			}
		}
	}
}
