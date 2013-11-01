package ticketline.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Vector;

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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import ticketline.bl.GuiMemory;
import ticketline.bl.ITrail;
import ticketline.bl.Trail;
import ticketline.cfg.ConfigFactory;
import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.TransaktionDAO;
import ticketline.db.Kunde;
import ticketline.db.Transaktion;
import ticketline.db.TransaktionKey;
import ticketline.gui.components.DefaultSearchTable;
import ticketline.gui.components.NoEditTableModel;

/**
 * This Class provides the User Interface for
 * Searching and Editing Reservations
 * 
 * @author PatrickM
 * @version 0.5
 *
 */
public class ReservierungTicketsSuchenBearbeitenGui extends JPanel implements ActionListener,ListSelectionListener,KeyListener, ITrail {

	private static final long serialVersionUID = 1L;
	
	private static ResourceBundle lang;
	private static Integer panel_width = ConfigFactory.getConfig().getDefaultPanelWidth();
	private static Integer panel_heigth = ConfigFactory.getConfig().getDefaultPanelHeight();
	
	private static Logger logger = Logger.getLogger(ReservierungTicketsSuchenBearbeitenGui.class);
	
	private JLayeredPane pn_form;
	private JLayeredPane pn_list;
	private JPanel buttons;

	private JLabel lbl_reservationNr;
	private JLabel lbl_customerSelection;
	
	private JTextField tf_reservationNr;
	
	private NoEditTableModel tm;
    private Vector<String> tableColumns;	
	private Vector<Vector<String>> tableData;
	private DefaultSearchTable tbl_results;
	private JScrollPane tableScrollPane;

	private JButton btn_search;

	private JButton btn_sell;
	private JButton btn_rescind;
	private JButton btn_edit;
	
	private JRadioButton rb_ticket;
	private JRadioButton rb_reservation;
	
	private ButtonGroup  selector   = new ButtonGroup();
	
	// Stores whether a Radio Button has been pressed or not
	private boolean isTicket = false;
	private boolean isReservation = true;

	// Stores the Transaktion Key for easier DB Access
	// for all Search Results
    private Vector<TransaktionKey> keyData;
    
    // Trail
	private Trail trail;
	
	//Misc
	private Random rn;
	private Double columnWidths[] = new Double[]{5.0,10.0,10.0,20.0,10.0,20.0,20.0,5.0};
	private Kunde k;
	
	public ReservierungTicketsSuchenBearbeitenGui() {
		logger.debug("ReservierungSuchenBearbeitenGui started");
		
		Gui.setPanelSize();
		
		//Set Trail
		trail = new Trail(5);
		trail.addNode(this);
		
		//randomizer
    	rn = new Random();
		
		lang = ConfigFactory.getConfig().getLanguageBundle();
		
 		GridBagConstraints c = new GridBagConstraints();
		tableColumns = new Vector<String>();
		tableData = new Vector<Vector<String>>();
		keyData = new Vector<TransaktionKey>();
		
		pn_form = new JLayeredPane();
		pn_list = new JLayeredPane();
		buttons = new JPanel(new GridBagLayout());
		
		//Set up customer Selection Panel
		k = GuiMemory.getKunde();
		int number = k.getKartennr();
		String surname = k.getNname();
		String name = k.getVname();
		lbl_customerSelection = new JLabel(surname + " " + name + "[" + number + "]");
		
		//Set up RadioButtons
		rb_ticket = new JRadioButton(lang.getString("LBL_TICKET"));
		rb_ticket.addActionListener(this);
		rb_ticket.addKeyListener(this);
		rb_reservation = new JRadioButton(lang.getString("LBL_RESERVATION"),true);
		rb_reservation.addActionListener(this);
		rb_reservation.addKeyListener(this);
		
		//Set up for RadioButtons
		selector.add(rb_ticket);
		selector.add(rb_reservation);
		
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		buttons.add(rb_ticket,c);
		c.gridx = 1;
		buttons.add(rb_reservation,c);
		
		//User Input Field
		lbl_reservationNr = new JLabel(lang.getString("LBL_RESTICK_NUMBER"));
		tf_reservationNr = new JTextField(6);
		
		//Initialize Table Columns
		tableColumns.add(lang.getString("LBL_RESTICK_NUMBER"));
		tableColumns.add(lang.getString("LBL_RESTICK_DATE"));
		tableColumns.add(lang.getString("LBL_RESTICK_TIME"));
		tableColumns.add(lang.getString("LBL_RESTICK_EVENT"));
		tableColumns.add(lang.getString("LBL_RESTICK_CATEGORY"));
		tableColumns.add(lang.getString("LBL_RESTICK_HALL"));
		tableColumns.add(lang.getString("LBL_RESTICK_LOCDESCRIPTION"));
		tableColumns.add(lang.getString("LBL_RESTICK_NROFSEATS"));
		
		//Initialize Table
		tbl_results = new DefaultSearchTable();
    	tbl_results.setAutoCreateRowSorter(true);
    	tbl_results.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tm = new NoEditTableModel(tableData, tableColumns);
		tbl_results.setModel(tm);
    	tbl_results.getSelectionModel().addListSelectionListener(this);
    	setColumnWidths();
    	
    	tableScrollPane = new JScrollPane(tbl_results);
    	tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		btn_search = new JButton(lang.getString("BTN_RESTICK_SEARCH"));
		btn_sell = new JButton(lang.getString("BTN_RESTICK_SELL"));
		btn_sell.setEnabled(false);
		btn_rescind = new JButton(lang.getString("BTN_RESTICK_RESCIND"));
		btn_rescind.setEnabled(false);
		btn_edit = new JButton(lang.getString("BTN_RESTICK_EDIT"));
		btn_edit.setEnabled(false);
		
    	// Layout
    	GridBagLayout gb_resSearchEdit = new GridBagLayout();
    	setLayout(gb_resSearchEdit);
    	setSize(panel_width, panel_heigth);
 		c.anchor = GridBagConstraints.LINE_START;
 		c.insets = new Insets(5,5,5,5);
 		
 		
 		/**
 		 * Form Panel
 		 */
 		GridBagLayout gb_form = new GridBagLayout();
 		pn_form.setLayout(gb_form);
 		pn_form.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_RESERVATIONSEARCH")));
		
 		//Customer Selection Label
 		c.gridx = 0;
 		c.gridy = 0;
 		c.gridwidth = 2;
 		c.anchor = GridBagConstraints.CENTER;
 		pn_form.add(lbl_customerSelection,c);
 		
		//Search Panel Radio Panel
		c.gridx = 0;
		c.gridy = 1;
		pn_form.add(buttons,c);
 		
		//Search Panel Row 1 Labels
		c.gridx = 0;
		c.gridy = 2;
 		c.gridwidth = 1;
 		c.anchor = GridBagConstraints.FIRST_LINE_START;
		pn_form.add(lbl_reservationNr,c);
		
		//Search Panel Row 2 TextFields
		c.gridx = 1;
		c.gridy = 2;
		tf_reservationNr.addKeyListener(this);
		pn_form.add(tf_reservationNr,c);

		
		// Add Search Button
 		c.gridx = 2;
 		c.gridy = 3;
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
 		gb_resSearchEdit.setConstraints(pn_form, c);
 		add(pn_form);
		
		/**
 		 * List Panel
 		 */
 		GridBagLayout gb_list = new GridBagLayout();
 		pn_list.setLayout(gb_list);
 		pn_list.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_RESTICK_RESULTS")));	
		
		//Results Panel
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		tableScrollPane.setMinimumSize(new Dimension(570,600));	
		tableScrollPane.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), ConfigFactory.getConfig().getDefaultPanelHeight()/2));
		pn_list.add(tableScrollPane,c);
		
		// ADD LIST PANEL
 		c.gridx = 0;
 		c.gridy = 1;
 		gb_resSearchEdit.setConstraints(pn_list, c);
 		add(pn_list);
 		
 		// ADD EDIT BUTTON
 		c.gridx = 0;
 		c.gridy = 2;
 		c.anchor = GridBagConstraints.LINE_START;
 		btn_edit.addActionListener(this);
 		btn_edit.setActionCommand("edit");
 		gb_resSearchEdit.setConstraints(btn_edit, c);
 		add(btn_edit);
 		
 		// ADD SELL BUTTON
 		c.gridx = 0;
 		c.gridy = 2;
 		c.anchor = GridBagConstraints.CENTER;
 		btn_sell.addActionListener(this);
 		btn_sell.setActionCommand("buy");
 		gb_resSearchEdit.setConstraints(btn_sell, c);
 		add(btn_sell);
 		
 		// ADD STORNO BUTTON
 		c.gridx = 0;
 		c.gridy = 2;
 		c.anchor = GridBagConstraints.LINE_END;
 		btn_rescind.addActionListener(this);
 		btn_rescind.setActionCommand("storno");
 		gb_resSearchEdit.setConstraints(btn_rescind, c);
 		add(btn_rescind);
		
		setVisible(true); 
		btn_edit.setEnabled(false);
		btn_sell.setEnabled(false);
		btn_rescind.setEnabled(false);
		
		logger.debug("ReservierungSuchenBearbeitenGui Created Successfully");
	}
	
	private void setColumnWidths() {
		tbl_results.setPreferredColumnWidths(columnWidths);
	}

	/**
	 * Set Trail
	 * @param trail 
	 */
	public void setTrail(Trail trail){
		this.trail = trail;
		//updates the button in the left pane
		try{
			GuiMemory.getActionListener("VerkauferTab").actionPerformed(new ActionEvent(this,new Random().nextInt(),"btnTicketSeProminent"));
		}
		catch(NullPointerException ex){}
	}
	
	public void actionPerformed(ActionEvent evt) {
		if(evt.getActionCommand().equals("search")){
			TransaktionDAO tDao = DAOFactory.getTransaktionDAO();
			TransaktionKey transKey = new TransaktionKey();
			transKey.setKundennr(GuiMemory.getKunde().getKartennr());
			keyData.clear();
			tableData.clear();
			int resNr = -1;
			boolean ok = true;
			String errorMessage = null;
			
			if(!tf_reservationNr.getText().equals("") && isReservation) {
				try {
					resNr = new Integer(tf_reservationNr.getText().trim());
					if(resNr <= 0)
					{
						ok = false;
					}
				} catch (Exception e) {
					errorMessage = e.getMessage();
					ok = false;
				}
			} 
			
			if(ok) {
				List<Transaktion> result = tDao.searchTransaktion(transKey, resNr, isTicket, isReservation);
				if(result.size() == 0) {
					JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHRESTICK_NOFOUND"),lang.getString("OPH_SEARCHRESTICK_INFO"),JOptionPane.INFORMATION_MESSAGE);
					tableData.clear();
					tm = new NoEditTableModel(tableData,tableColumns);
					tbl_results.setModel(tm);
					setColumnWidths();
				} else {
					logger.debug("ActionEvent:Search:ResNr:Results: " + result.size());
					for(Transaktion ta : result) {
						Vector<String> temp = new Vector<String>();
						TransaktionKey transkey = ta.getComp_id();
						keyData.add(transkey);
					
						String dtmp = ta.getComp_id().getDatumuhrzeit().toString();
						String ymd = dtmp.substring(0,10);
						String hms = dtmp.substring(11);
					
						temp.add(ta.getResnr().toString());
						temp.add(ymd);
						temp.add(hms);
						temp.add(ta.getBelegung().getAuffuehrung().getVeranstaltung().getComp_id().getBezeichnung());
						temp.add(ta.getBelegung().getComp_id().getKategoriebez());
						temp.add(ta.getBelegung().getComp_id().getSaalbez());
						temp.add(ta.getBelegung().getComp_id().getOrtbez().toString());
						temp.add(ta.getAnzplaetze().toString());
					
						tableData.add(temp);
					}
				
					logger.debug("ActionEvent:Search:ResNr:Updating");
					tm = new NoEditTableModel(tableData,tableColumns);
					tbl_results.setModel(tm);
					setColumnWidths();
				}	
			} else {
				JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHRESTICK_INPUTERROR_NUMBER"),lang.getString("OPH_SEARCHRESTICK_INPUTERROR"),JOptionPane.WARNING_MESSAGE);
				logger.warn("ActionEvent:Search:Input:Warning: "+ errorMessage);
				
				tm = new NoEditTableModel(tableData,tableColumns);
				tbl_results.setModel(tm);
				setColumnWidths();
				tf_reservationNr.setText("");
			}
			//updating title
	    	pn_list.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_RESTICK_RESULTS")+" (" + tableData.size() + " " + lang.getString("PNL_RESTICK_RESULT") + ")"));
		} else if(evt.getActionCommand().equals("storno")){
			
			String txt = null;
			
			if(rb_ticket.isSelected()) 
				txt = lang.getString("OPT_RESTICK_STORNOYESNO_TICKET");
			else 
				txt = lang.getString("OPT_RESTICK_STORNOYESNO_RES");
			
			if(JOptionPane.showConfirmDialog(null, txt, lang.getString("OPH_RESTICK_STORNOYESNO"),
				JOptionPane.OK_CANCEL_OPTION) == 0) {
				logger.debug("ActionEvent:Rescind:RescindActivated");
				TransaktionDAO tdao = DAOFactory.getTransaktionDAO();
				TransaktionKey tkey = new TransaktionKey();
				
				int row = tbl_results.convertRowIndexToModel(tbl_results.getSelectedRow());	

				tkey = keyData.get(row);
				
				logger.debug("ActionEvent:Rescind:TransaktionKey: " + tkey.toString());
				
				Transaktion edit = tdao.get(tkey);
				edit.setStorniert(true);
				tdao.update(edit);
				
				tableData.remove(row);
				tm = new NoEditTableModel(tableData,tableColumns);
				tbl_results.setModel(tm);
				setColumnWidths();
				
				logger.debug("ActionEvent:Rescind:Transaktion Rescinded");
			}
			
		} else if(evt.getActionCommand().equals("buy")){
			TransaktionDAO tdao = DAOFactory.getTransaktionDAO();
			TransaktionKey tkey = new TransaktionKey();

			int row = tbl_results.convertRowIndexToModel(tbl_results.getSelectedRow());	
			
			tkey = keyData.get(row);
			
			logger.debug("ActionEvent:Buy:TransaktionKey: " + tkey.toString());
			
			Transaktion edit = tdao.get(tkey);
			double price = edit.getPreis().doubleValue();
			price = price*edit.getAnzplaetze();
			
			if(JOptionPane.showConfirmDialog(null, lang.getString("OPT_RESTICK_BUYYESNO") + " " +  price + " " + lang.getString("LBL_CURRENCY"),
					lang.getString("OPH_RESTICK_BUYYESNO"), JOptionPane.OK_CANCEL_OPTION) == 0) {
				logger.debug("ActionEvent:Buy:BuyActivated");
				
				edit.setVerkauft(true);
				tdao.update(edit);
				
				tableData.remove(row);
				tm = new NoEditTableModel(tableData,tableColumns);
				tbl_results.setModel(tm);
				setColumnWidths();
				
				logger.debug("ActionEvent:Buy:Transaktion Bought: Price: " + price);
			}
		} else if(evt.getActionCommand().equals("edit")){
			logger.debug("ActionEvent:Edit:EditActivated");
			TransaktionDAO tdao = DAOFactory.getTransaktionDAO();
			TransaktionKey tkey = new TransaktionKey();
			
			int row = tbl_results.convertRowIndexToModel(tbl_results.getSelectedRow());	

			tkey = keyData.get(row);
			
			logger.debug("ActionEvent:Buy:TransaktionKey: " + tkey.toString());
			
			
			Transaktion edit = tdao.get(tkey);
			
			GuiMemory.attachTransaktion(edit);

			logger.debug("ActionEvent:Edit:Transaktion Sent to Edit");
			
			trail.getNext(this).setTrail(trail);
			Gui.load((Component)trail.getNext(this));
			setVisible(true);
		} else if(evt.getActionCommand().equals(lang.getString("LBL_TICKET"))) {
			isTicket = true;
			isReservation = false;
			tf_reservationNr.setEditable(false);
		} else if(evt.getActionCommand().equals(lang.getString("LBL_RESERVATION"))) {
			isTicket = false;
			isReservation = true;
			tf_reservationNr.setEditable(true);
		}
	}

	@Override
	/**
	 * Table Listner
	 * @parm ListSelectionEventEvent e
	 */
	public void valueChanged(ListSelectionEvent arg0) {
		
		//if a selection in the Results has been made, it can be sold,edited,or rescinded depending on what it is
		if(tbl_results.getSelectedRow() != -1 && tbl_results.getValueAt(tbl_results.getSelectedRow(),0) !=null && !tbl_results.getValueAt(tbl_results.getSelectedRow(),0).equals("")) {
			if(isTicket) {
				btn_rescind.setEnabled(true);
				btn_edit.setEnabled(true);
			} else if(isReservation) {
				btn_rescind.setEnabled(true);
				btn_sell.setEnabled(true);
				btn_edit.setEnabled(true);
			}
		}
		
		//if nothing selected
		if(tbl_results.getSelectedRow() == -1 || tbl_results.getValueAt(tbl_results.getSelectedRow(),0) ==null || tbl_results.getValueAt(tbl_results.getSelectedRow(),0).equals("")) {
			btn_sell.setEnabled(false);
			btn_rescind.setEnabled(false);
			btn_edit.setEnabled(false);
		}
	}

	@Override
	public void reloadChanges() {
		k = GuiMemory.getKunde();
		String surname = k.getNname();
		String name = k.getVname();
		int number = k.getKartennr();
		
		lbl_customerSelection.setText(surname + " " + name + "[" + number + "]");
	}

	@Override
	public void dummySearch() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent ke) {
		// enter key event -> search
		if(ke.getKeyChar() == '\n') {
			this.actionPerformed(new ActionEvent(ke,rn.nextInt(),"search"));
		}
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
