package ticketline.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import com.toedter.calendar.JDateChooser;

import ticketline.bl.GuiMemory;
import ticketline.bl.ITrail;
import ticketline.bl.Trail;
import ticketline.cfg.ConfigFactory;
import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.AuffuehrungDAO;
import ticketline.dao.interfaces.VeranstaltungDAO;
import ticketline.db.Auffuehrung;
import ticketline.db.AuffuehrungKey;
import ticketline.db.Belegung;
import ticketline.db.Ort;
import ticketline.db.Veranstaltung;
import ticketline.db.VeranstaltungKey;
import ticketline.gui.components.DateTimeCellRenderer;
import ticketline.gui.components.DefaultSearchTable;
import ticketline.gui.components.NoEditTableModel;

/**
 * 
 * Reservation/Sales Step 2 Panel
 * @author FlorianR, ReneN, PatrickF, AndiS
 * @version 1.2
 *
 */
public class AuffuehrungenSuchenGui extends JPanel implements ActionListener, ListSelectionListener, DocumentListener,ChangeListener,ItemListener, ITrail  {

	private static final long serialVersionUID = 1L;

	private static ResourceBundle lang;
	private static Logger logger = Logger.getLogger(AuffuehrungenSuchenGui.class);
	private static Integer panel_width = ConfigFactory.getConfig().getDefaultPanelWidth();
	private static Integer panel_heigth = ConfigFactory.getConfig().getDefaultPanelHeight();
	
	private Trail trail;
	private Trail subtrailOrtSuchen;
	private Trail subtrailVeranstaltungSuchen;
	
	private JLayeredPane pn_form;
	private JLayeredPane pn_list;
	
    private JTextField	 tf_act;
    private JTextField	 tf_ort; 
    
    private JLabel	 lb_date_from;
    private JLabel	 lb_date_to;
    private JLabel	 lb_storno;
    private JLabel	 lb_price;
    private JLabel	 lb_act;
    private JLabel	 lb_ort;
    private JLabel   lb_seats;
    
    private JButton	 btn_search;
    private JButton	 btn_next;
    private JButton	 btn_back;
    private JButton  btn_selhall;
    private JButton  btn_selevent;
    private JButton	 btn_deselectEvent;
    private JButton	 btn_deselectOrt;
    private JButton  btn_deselectDateFrom;
    private JButton  btn_deselectDateTo;
    
    private ImageIcon del;
    
    private JComboBox cb_price;
    
    private ButtonGroup bg_storno;
    private JRadioButton rb_yes;
    private JRadioButton rb_no;
    private JRadioButton rb_both;
    
    private JDateChooser jdc_dateFrom;
    private JDateChooser jdc_dateTo;
    
    private Calendar timeSet;
    private Date date;
    private SpinnerDateModel sm_from;
    private SpinnerDateModel sm_to;
    private JSpinner sp_time_from;
    private JSpinner sp_time_to;
    private JSpinner.DateEditor de;    
    
    private SpinnerNumberModel nm;
    private JSpinner sp_seats;
    
    // Table
	private Object[][] 	data = {};
	private String[]    colnames;
	private DefaultSearchTable tb_results;
	private NoEditTableModel tm;
	private JScrollPane scrl_results;
    
	private Double[] percentageColumnWidth;
	//private int colNum;
	
	//Additional Entities
	private Ort ort;
	private Veranstaltung event;
	private ArrayList<Veranstaltung> eventList;
	
    public AuffuehrungenSuchenGui() {
    	Gui.setPanelSize();

    	//delete some gui memory stuff
		if(GuiMemory.isVeranstaltungSet())
			GuiMemory.detachVeranstaltung();
		if(GuiMemory.isOrtSet())
			GuiMemory.detachOrt();
    	
    	lang = ConfigFactory.getConfig().getLanguageBundle();
    	
    	pn_form = new JLayeredPane();
    	pn_list = new JLayeredPane();
    	
    	
    	cb_price = new JComboBox();
    	tf_act = new JTextField(15);
    	tf_ort = new JTextField(15);
    	
    	jdc_dateFrom = new JDateChooser(/*new Date()*/);
    	jdc_dateTo = new JDateChooser();
    	
    	ort = new Ort();
    	eventList = new ArrayList<Veranstaltung>();
    	//TODO event = new Veranstaltung();
    	
    	date = Calendar.getInstance().getTime();
    	timeSet = Calendar.getInstance();
    	timeSet.set(2000, 1, 1, 0, 0);
    	sm_from = new SpinnerDateModel(timeSet.getTime(), null, null, Calendar.HOUR_OF_DAY);
    	timeSet.set(2000, 1, 1, 23, 59);
    	sm_to = new SpinnerDateModel(timeSet.getTime(), null, null, Calendar.HOUR_OF_DAY);
    	
    	sp_time_from = new JSpinner(sm_from);
    	de = new JSpinner.DateEditor(sp_time_from, "HH:mm");
    	sp_time_from.setEditor(de);
    	
    	
    	sp_time_to = new JSpinner(sm_to);
    	de = new JSpinner.DateEditor(sp_time_to, "HH:mm");
    	sp_time_to.setEditor(de);
    	
    	
    	lb_date_from = new JLabel(lang.getString("LBL_SEARCHACT_FROM"));
      	lb_date_to = new JLabel(lang.getString("LBL_SEARCHACT_TO"));
    	lb_storno = new JLabel(lang.getString("LBL_SEARCHACT_STORNO"));
    	lb_price = new JLabel(lang.getString("LBL_SEARCHACT_PRICE"));
    	lb_act = new JLabel(lang.getString("LBL_SEARCHACT_EVENT"));
    	lb_ort = new JLabel(lang.getString("LBL_SEARCHACT_EVENTLOC"));
       	lb_seats = new JLabel(lang.getString("LBL_SEARCHACT_FREESEATS"));
       	
       	//x-del imageicon
    	del = new ImageIcon("images/x.png");
       	
    	btn_search = new JButton(lang.getString("BTN_SEARCHACT_SEARCH"));
    	btn_next = new JButton(lang.getString("BTN_SEARCHACT_NEXT"));
    	btn_next.setEnabled(false);  	
    	btn_back = new JButton(lang.getString("BTN_SEARCHACT_BACK"));
    	btn_selhall = new JButton(lang.getString("BTN_SEARCHACT_SELECT"));
    	btn_selevent = new JButton(lang.getString("BTN_SEARCHACT_SELECT"));
    	btn_deselectEvent = new JButton(del);
    	btn_deselectOrt = new JButton(del);
    	btn_deselectDateFrom = new JButton(del);
    	btn_deselectDateTo = new JButton(del);
    	
    	bg_storno = new ButtonGroup();
    	rb_yes = new JRadioButton(lang.getString("RB_SEARCHACT_YES"));
    	rb_no = new JRadioButton(lang.getString("RB_SEARCHACT_NO"));
    	rb_both = new JRadioButton(lang.getString("RB_SEARCHACT_BOTH"),true);
    	bg_storno.add(rb_yes);
    	bg_storno.add(rb_no);
    	bg_storno.add(rb_both);
    	
    	
    	
    	//Intitialize Combo Box
    	cb_price.addItem("Mindestpreis");
    	cb_price.addItem("Standardpreis");
    	cb_price.addItem("Maximalpreis");
    	cb_price.addItem("Egal");
    	
    	cb_price.setSelectedIndex(3);
    	
    	// Free Seats Spinner
		nm = new SpinnerNumberModel(2,0,999,1);
		sp_seats = new JSpinner(nm);
    	
    	// Initialize Table Colnames
    	colnames = new String[6];
    	colnames[0] = lang.getString("TCN_SEARCHACT_ACT");
    	colnames[1] = lang.getString("TCN_SEARCHACT_DATE");
    	colnames[2] = lang.getString("TCN_SEARCHACT_HALL");
    	colnames[3] = lang.getString("TCN_SEARCHACT_EVENTLOC");
    	colnames[4] = lang.getString("TCN_SEARCHACT_LOCATION");
    	colnames[5] = lang.getString("TCN_SEARCHACT_FREESEAT");
    	
    	// Table
    	percentageColumnWidth = new Double[]{0.222,0.166,0.166,0.166,0.166,0.1};
    	
    	tb_results = new DefaultSearchTable();
    	tm = new NoEditTableModel(data,colnames);
    	tb_results.setModel(tm);
    	tb_results.setPreferredColumnWidths(percentageColumnWidth);
    	tb_results.getColumn(lang.getString("TCN_SEARCHACT_DATE")).setCellRenderer(new DateTimeCellRenderer());
    	tb_results.getSelectionModel().addListSelectionListener(this);
    	scrl_results = new JScrollPane(tb_results);
    	
    	// Layout
    	GridBagLayout gb_reskauf2 = new GridBagLayout();
    	setLayout(gb_reskauf2);
    	setSize(panel_width, panel_heigth);
 		GridBagConstraints c = new GridBagConstraints();
 		c.anchor = GridBagConstraints.LINE_START;
 		c.insets = new Insets(5,5,5,5);
 		
 		
 		/**
 		 * Form Panel
 		 */
 		GridBagLayout gb_form = new GridBagLayout();
 		pn_form.setLayout(gb_form);
 		pn_form.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SEARCHACT")));
 		
 		// Column 1
 		c.gridx = 0;
 		
 		c.gridy = 0;
 		gb_form.setConstraints(lb_date_from, c);
 		pn_form.add(lb_date_to);
 		
 		c.gridy = 1;
 		gb_form.setConstraints(lb_date_to, c);
 		pn_form.add(lb_date_from);
 		
 		c.gridy = 2;
 		gb_form.setConstraints(lb_act, c);
 		pn_form.add(lb_act);
 		
 		c.gridy = 3;
 		gb_form.setConstraints(lb_ort, c);
 		pn_form.add(lb_ort);
 		
 		/*c.gridy = 4;
 		gb_form.setConstraints(lb_price, c);
 		pn_form.add(lb_price);*/
 		
 		c.gridy = 5;
 		gb_form.setConstraints(lb_seats, c);
 		pn_form.add(lb_seats);
 		
 		c.gridy = 6;
 		gb_form.setConstraints(lb_storno, c);
 		pn_form.add(lb_storno);

 		// Column 2
 		c.gridx = 1;
 		c.gridy = 0;
 		c.anchor = GridBagConstraints.LINE_START;
 		jdc_dateFrom.setPreferredSize(new Dimension(100,20));
 		((JTextField)jdc_dateFrom.getDateEditor()).setEditable(false);
 		((JTextField)jdc_dateFrom.getDateEditor()).getDocument().addDocumentListener(this);
 		gb_form.setConstraints(jdc_dateFrom, c);
 		pn_form.add(jdc_dateFrom);
 		c.anchor = GridBagConstraints.LINE_END;
 		gb_form.setConstraints(sp_time_from, c);
 		sp_time_from.addChangeListener(this);
 		pn_form.add(sp_time_from);
 		
 		c.gridy = 1;
 		c.anchor = GridBagConstraints.LINE_START;
 		jdc_dateTo.setPreferredSize(new Dimension(100,20));
 		((JTextField)jdc_dateTo.getDateEditor()).setEditable(false);
 		((JTextField)jdc_dateTo.getDateEditor()).getDocument().addDocumentListener(this);
 		gb_form.setConstraints(jdc_dateTo, c);
 		pn_form.add(jdc_dateTo);
 		c.anchor = GridBagConstraints.LINE_END;
 		gb_form.setConstraints(sp_time_to, c);
 		sp_time_to.addChangeListener(this);
 		pn_form.add(sp_time_to);
 		c.gridy = 2;
 		tf_act.addActionListener(this);
 		tf_act.getDocument().addDocumentListener(this);
 		tf_act.setActionCommand("search");
 		
 		gb_form.setConstraints(tf_act, c);
 		pn_form.add(tf_act);
 		
 		c.gridy = 3;
 		gb_form.setConstraints(tf_ort, c);
 		tf_ort.setEnabled(false);
 		if(GuiMemory.isSaalSet()) {
 			ort = GuiMemory.detachOrt();
 			tf_ort.setText(ort.getComp_id().getBezeichnung());
 		}
 		pn_form.add(tf_ort);
 		
 		/*c.gridy = 4;
 		c.anchor = GridBagConstraints.LINE_START;
 		gb_form.setConstraints(cb_price, c);
 		pn_form.add(cb_price);*/
 		
 		c.gridy = 5;
 		c.anchor = GridBagConstraints.LINE_START;
 		gb_form.setConstraints(sp_seats, c);
 		sp_seats.addChangeListener(this);
 		pn_form.add(sp_seats);
 		
 		c.gridy = 6;
 		c.anchor = GridBagConstraints.LINE_START;
 		gb_form.setConstraints(rb_yes, c);
 		rb_yes.addItemListener(this);
 		pn_form.add(rb_yes);
 		c.anchor = GridBagConstraints.CENTER;
 		gb_form.setConstraints(rb_no, c);
 		rb_no.addItemListener(this);
 		pn_form.add(rb_no);
 		c.anchor = GridBagConstraints.LINE_END;
 		gb_form.setConstraints(rb_both, c);
 		rb_both.addItemListener(this);
 		pn_form.add(rb_both);
 		
 		
 		
 		//Column3
 		c.gridx = 2;
 		
 		c.gridy = 0;
 		btn_deselectDateFrom.addActionListener(this);
 		btn_deselectDateFrom.setActionCommand("deselectDateFrom");
 		gb_form.setConstraints(btn_deselectDateFrom, c);
 		btn_deselectDateFrom.setPreferredSize(ConfigFactory.getConfig().getDeSelectButtonDimension());	
 		pn_form.add(btn_deselectDateFrom);
 		
 		c.gridy = 1;
 		btn_deselectDateTo.addActionListener(this);
 		btn_deselectDateTo.setActionCommand("deselectDateTo");
 		gb_form.setConstraints(btn_deselectDateTo, c);
 		btn_deselectDateTo.setPreferredSize(ConfigFactory.getConfig().getDeSelectButtonDimension());	
 		pn_form.add(btn_deselectDateTo);
 		
 		c.gridy = 2;
 		btn_deselectEvent.addActionListener(this);
 		btn_deselectEvent.setActionCommand("deselectEvent");
 		gb_form.setConstraints(btn_deselectEvent, c);
 		btn_deselectEvent.setPreferredSize(ConfigFactory.getConfig().getDeSelectButtonDimension());	
 		pn_form.add(btn_deselectEvent);
 		
 		c.gridy = 3;
 		btn_deselectOrt.addActionListener(this);
 		btn_deselectOrt.setActionCommand("deselectOrt");
 		gb_form.setConstraints(btn_deselectOrt, c);
 		btn_deselectOrt.setPreferredSize(ConfigFactory.getConfig().getDeSelectButtonDimension());	
 		pn_form.add(btn_deselectOrt);
 		
 		//Column4
 		c.gridx = 3;
 		c.gridy = 2;
 		btn_selevent.addActionListener(this);
 		btn_selevent.setActionCommand("selevent");
 		gb_form.setConstraints(btn_selevent, c);
 		btn_selevent.setPreferredSize(ConfigFactory.getConfig().getDefaultSubSelectButtonDimension());	
 		pn_form.add(btn_selevent);
 		c.gridy = 3;
 		btn_selhall.addActionListener(this);
 		btn_selhall.setActionCommand("selhall");
 		gb_form.setConstraints(btn_selhall, c);
 		btn_selhall.setPreferredSize(ConfigFactory.getConfig().getDefaultSubSelectButtonDimension());	
 		pn_form.add(btn_selhall);
 		
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
 		pn_form.setMinimumSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(),320));
 		pn_form.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), (ConfigFactory.getConfig().getDefaultPanelHeight()/2)+40));
 		gb_reskauf2.setConstraints(pn_form, c);
 		add(pn_form);
 		
 		
 		/**
 		 * List Panel
 		 */
 		GridBagLayout gb_list = new GridBagLayout();
 		pn_list.setLayout(gb_list);
 		pn_list.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SEARCHACTRESULTS")));
 		
 		// Column 1
 		c.gridx = 0;
 		c.gridy = 0;
 		c.anchor = GridBagConstraints.CENTER;
 		scrl_results.setMinimumSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(),200));	
 		scrl_results.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), (ConfigFactory.getConfig().getDefaultPanelHeight()/2)-40));
 		gb_list.setConstraints(scrl_results, c);
 		pn_list.add(scrl_results);
 		
 		// ADD LIST PANEL
 		c.gridx = 0;
 		c.gridy = 1;
 		gb_reskauf2.setConstraints(pn_list, c);
 		add(pn_list);
 		
 		// ADD BACK BUTTON
 		c.gridx = 0;
 		c.gridy = 2;
 		c.anchor = GridBagConstraints.LINE_START;
 		btn_back.addActionListener(this);
 		btn_back.setActionCommand("back");
 		gb_reskauf2.setConstraints(btn_back, c);
 		add(btn_back);
 		
 		// ADD NEXT BUTTON
 		c.gridx = 0;
 		c.gridy = 2;
 		c.anchor = GridBagConstraints.LINE_END;
 		btn_next.addActionListener(this);
 		btn_next.setActionCommand("next");
 		gb_reskauf2.setConstraints(btn_next, c);
 		add(btn_next);
 		
 		//this sets the next buttons only visible when in trail (see setTrail() )
 		btn_back.setVisible(false);
		btn_next.setVisible(false);
 		
 		setVisible(true); 		
 		
 		//Load Subtrails
 		loadSubtrails();
 		
 		logger.info("AuffuehrungSuchenGui has started.");
    }
    
    /*
     * ACTION PERFORMED - ACTION LISTENER
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent evt) {
		
		// Back
		if(evt.getActionCommand().equals("back")){
			trail.getPrevious(this).setTrail(trail);
			Gui.load((Component)trail.getPrevious(this));
			setVisible(true);
		}
		// Next
		if(evt.getActionCommand().equals("next")){
			trail.getNext(this).setTrail(trail);
			Gui.load((Component)trail.getNext(this));
			setVisible(true);
		}
		//Select Hall
		if(evt.getActionCommand().equals("selhall")){
			//this.actionPerformed(new ActionEvent(this,new Random().nextInt(),"deselectOrt"));
			//Handle Subtrail to SaalSuchen Gui
			subtrailOrtSuchen.getNext(this).setTrail(subtrailOrtSuchen);
			//Load SaalSuchen Gui
			Gui.load((Component)subtrailOrtSuchen.getNext(this));
			setVisible(true);
		}
		//Select Event
		if(evt.getActionCommand().equals("selevent")){
			subtrailVeranstaltungSuchen.getNext(this).setTrail(subtrailVeranstaltungSuchen);
			Gui.load((Component)subtrailVeranstaltungSuchen.getNext(this));
			setVisible(true);
		}
		// deselect Event
		if(evt.getActionCommand().equals("deselectEvent")){		
			eventList = null;
			tf_act.setText(new String());
			GuiMemory.detachVeranstaltung();
			this.actionPerformed(new ActionEvent(this,new Random().nextInt(),"search"));
		}
		// deselect Hall
		if(evt.getActionCommand().equals("deselectOrt")){		
			ort = new Ort();
			tf_ort.setText(new String());
			GuiMemory.detachOrt();
			this.actionPerformed(new ActionEvent(this,new Random().nextInt(),"search"));
		}
		// deselect DateFrom
		if(evt.getActionCommand().equals("deselectDateFrom")) {
			((javax.swing.JTextField)jdc_dateFrom.getDateEditor()).setText("");
			timeSet.set(2000, 1, 1, 0, 0);
	    	sm_from.setValue(timeSet.getTime());
			this.actionPerformed(new ActionEvent(this,new Random().nextInt(),"search"));
		}
		// deselect DateTo
		if(evt.getActionCommand().equals("deselectDateTo")) {
			((javax.swing.JTextField)jdc_dateTo.getDateEditor()).setText("");
			timeSet.set(2000, 1, 1, 23, 59);
	    	sm_to.setValue(timeSet.getTime());
			this.actionPerformed(new ActionEvent(this,new Random().nextInt(),"search"));
		}
		//Search
		if(evt.getActionCommand().equals("search")){
			
			AuffuehrungDAO adao = DAOFactory.getAuffuehrungDAO();
			
			
			AuffuehrungKey ak = new AuffuehrungKey();
			Auffuehrung a = new Auffuehrung();
			
			Date from = jdc_dateFrom.getDate();
			Date to = jdc_dateTo.getDate();
			
			//Merge Time with Date for FROM
			if(from!=null){
				from.setHours(sm_from.getDate().getHours());
				from.setMinutes(sm_from.getDate().getMinutes());
			}
			
			//Merge Time with Date for TO
			if(to!=null){
				to.setHours(sm_to.getDate().getHours());
				to.setMinutes(sm_to.getDate().getMinutes());
			}
			
			if(!tf_ort.getText().equals("")){
				ak.setOrtbez(ort.getComp_id().getBezeichnung());
				ak.setOrt(ort.getComp_id().getOrt());
			}
			//only add null evenet to eventList if no event is present
			if(eventList == null || (tf_act.getText().equals("") && eventList.size() < 1)){
				eventList = new ArrayList<Veranstaltung>();
				eventList.add(null);
			}
			
			if(rb_yes.isSelected()){
				a.setStorniert(true);
			}else if(rb_no.isSelected()){
				a.setStorniert(false);
			}else{
				a.setStorniert(null);
			}
			
			if(cb_price.getSelectedIndex()==3){
				a.setPreis(null);
			}else{
				a.setPreis(String.valueOf(cb_price.getSelectedIndex()));
			}
			
			a.setComp_id(ak);

			try{
				//cycle through all possible events
				List<Auffuehrung> result = new ArrayList<Auffuehrung>();
				for(int i=0;i<eventList.size();i++) {
					a.setVeranstaltung(eventList.get(i));
					result.addAll(adao.searchWithDate(a, from, to));
				}
				
				if(result.size() <= 0 || result == null){
					emptyTable();
					JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHACT_NOFOUND"),lang.getString("OPH_SEARCHACT_NOFOUND"),JOptionPane.INFORMATION_MESSAGE);
					logger.debug("No Event Results found");
				}else{
					Auffuehrung[] filterAuffuehrung = new Auffuehrung[result.size()]; 
					Integer[] freeSeats = new Integer[result.size()];
					int resultCount = 0;
					
					int i = 0;
					for(Auffuehrung af:result){
						
						//get the count of free seats
						int sum = 0;
						Set<Belegung> belegungen = af.getBelegungen();
						for(Belegung b : belegungen)
						{
							sum+=b.getAnzfrei();
						}
						
						//exclude results with not enough free seats
						if(sum >= nm.getNumber().intValue()){
							filterAuffuehrung[i] = af;
							freeSeats[i] = sum;
							i++;
							resultCount++;
						}
	
					}
					
					if(resultCount<1){
						emptyTable();
						JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHACT_NOFOUND"),lang.getString("OPH_SEARCHACT_NOFOUND"),JOptionPane.INFORMATION_MESSAGE);
						logger.debug("No Event Results found");			
					}else{

						data = new Object[resultCount][6];
						
						for(int j = 0; j<resultCount;j++){
							data[j][0] = filterAuffuehrung[j].getVeranstaltung().getComp_id().getBezeichnung();
			    			data[j][1] = filterAuffuehrung[j].getComp_id().getDatumuhrzeit();
							data[j][2] = filterAuffuehrung[j].getSaal().getComp_id().getBezeichnung();
							data[j][3] = filterAuffuehrung[j].getSaal().getOrt().getComp_id().getBezeichnung();
							data[j][4] = filterAuffuehrung[j].getSaal().getOrt().getComp_id().getOrt();
							data[j][5] = freeSeats[j];
	
						}
						tm = new NoEditTableModel(data,colnames);
						tb_results.setPreferredColumnWidths(percentageColumnWidth);
						tb_results.setModel(tm);
						tb_results.getColumn(lang.getString("TCN_SEARCHACT_DATE")).setCellRenderer(new DateTimeCellRenderer());
						
						//updating title
				    	pn_list.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SEARCHACTRESULTS")+" ("+data.length+" "+ lang.getString("PNL_SEARCHCUSTOMERHITS") +")"));
				    	
				    	logger.debug("ActionEvent:Search:ActTable: updating");
					}
				}
			} catch(Exception e) {
				//System.out.println(e.getStackTrace());
				JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHACT_SEARCHERROR"),lang.getString("OPH_SEARCHACT_SEARCHERROR"),JOptionPane.WARNING_MESSAGE);
				logger.warn("ActionEvent:Search:BL:Warning: "+e.getMessage()+" - "+e.toString()+" - " +e.getStackTrace());
			}
		}
	}
	
	/*
	 * LIST EVENT LISTENER
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	
	@Override
	public void valueChanged(ListSelectionEvent lev) {
		if(tb_results.getSelectedRow() != -1 && tb_results.getValueAt(tb_results.getSelectedRow(),0) !=null && !tb_results.getValueAt(tb_results.getSelectedRow(),0).equals("")) {

			AuffuehrungKey ak = new AuffuehrungKey();
			ak.setDatumuhrzeit((Date)tb_results.getValueAt(tb_results.getSelectedRow(),1));
			ak.setSaalbez((String)tb_results.getValueAt(tb_results.getSelectedRow(),2));
			ak.setOrtbez((String)tb_results.getValueAt(tb_results.getSelectedRow(),3));
			ak.setOrt((String)tb_results.getValueAt(tb_results.getSelectedRow(),4));
			
			AuffuehrungDAO adao = DAOFactory.getAuffuehrungDAO();
			Auffuehrung a = adao.get(ak);
			
			if(a != null){
				btn_next.setEnabled(true);
				btn_next.setText(new MessageFormat(lang.getString("BTN_SEARCHACT_NEXT_CAT")).format(new String[] { a.getVeranstaltung().getComp_id().getBezeichnung()}));
				GuiMemory.attachAuffuehrung(a);
			}
		}
		//if nothing selected
		if(tb_results.getSelectedRow() == -1 || tb_results.getValueAt(tb_results.getSelectedRow(),0) ==null || tb_results.getValueAt(tb_results.getSelectedRow(),0).equals("")) {		
			btn_next.setText(lang.getString("BTN_SEARCHACT_NEXT"));
			btn_next.setEnabled(false);
		}
	}
	
	
	
	
	/*
	 * ITRAIL METHODS
	 * @see ticketline.bl.ITrail#setTrail(ticketline.bl.Trail)
	 */
	@Override
	public void setTrail(Trail trail) {
		this.trail = trail;	
		
		//updates the button in the left pane
		try{
			GuiMemory.getActionListener("VerkauferTab").actionPerformed(new ActionEvent(this,new Random().nextInt(),"btnDoItProminent"));
		}
		catch(NullPointerException ex){}
		
		btn_next.setVisible(true);
		
		//if trail that comes form "kunde suchen"
		if(trail.getTrailNumber() == 1 || trail.getTrailNumber() == 10) {
			btn_back.setVisible(true);
		}
	}
	
	private void loadSubtrails(){
		//Saal suchen
		subtrailOrtSuchen = new Trail(3);
		subtrailOrtSuchen.addNode(this);
		//Veranstaltung suchen
		subtrailVeranstaltungSuchen = new Trail(4);
		subtrailVeranstaltungSuchen.addNode(this);
	}
	
	@Override
	public void dummySearch() {
		DAOFactory.getAuffuehrungDAO().getAll();
	}
	
	@Override
	public void reloadChanges() {
		if(GuiMemory.isOrtSet()) {
			//setting intern saal object and textfield
			ort = GuiMemory.getOrt();
			tf_ort.setText(ort.getComp_id().getBezeichnung()+ ": " + ort.getComp_id().getOrt());
		} else {
			ort = null;
			tf_ort.setText("");
		}
		
		if(GuiMemory.isVeranstaltungSet()) {
			//setting intern veranstaltung object and textfield, but only if comes from event search gui
			if(GuiMemory.isSignalSet() && GuiMemory.getSignal().equals("SIGEVTSEARCH")) {
				eventList = new ArrayList<Veranstaltung>();
				eventList.add(0,GuiMemory.getVeranstaltung());
				tf_act.setText(eventList.get(0).getComp_id().getBezeichnung());
			}
		}
		
		if(!GuiMemory.isSignalSet() || (GuiMemory.isSignalSet() && !GuiMemory.getSignal().equals("SIGIGNORE"))) {
			//automaticall search
			this.actionPerformed(new ActionEvent(this,new Random().nextInt(),"search"));
			System.out.println("searching...");
		} else if(GuiMemory.isSignalSet() && GuiMemory.getSignal().equals("SIGIGNORE")) {
			//avoid double signaling
			GuiMemory.detachSignal();
		}
	}
	
	/*
	 * CHANGE LISTENER
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent arg0) {
		this.actionPerformed(new ActionEvent(jdc_dateTo,new Random().nextInt(),"search"));
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
		//System.out.println("insertUpdate");
		checkTextFieldForEventSearch(de);
		checkTextFields(de);
		
	}

	@Override
	public void removeUpdate(DocumentEvent de) {
		//System.out.println("removeUpdate");
		checkTextFieldForEventSearch(de);
	}
	
	/*
	 * ITEM LISTENER
	 */
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		this.actionPerformed(new ActionEvent(arg0,new Random().nextInt(),"search"));
	}
	
	/*
	 * PRIVATE METHODS
	 */
	
	/**
	 * Handles the Event Textfield input (dynamically)
	 */
	private void checkTextFieldForEventSearch(DocumentEvent de) {
		System.out.print("Triggering searchevent (source: "+de.getDocument().toString()+"/ changelength: "+de.getLength());
		//if source is the tf_act textfield
		if(de.getDocument().equals(tf_act.getDocument()) && de.getDocument().getLength() > 0) {
			//only search when signal is right, to prevent double search when selecting special event in VeranstlungSuchenGui
			if(!GuiMemory.isSignalSet() || (GuiMemory.isSignalSet() && !GuiMemory.getSignal().equals("SIGEVTSEARCH") && !GuiMemory.getSignal().equals("SIGIGNORE"))) {

				System.out.print(" - updating event with user input");
				eventList = null;
				GuiMemory.detachVeranstaltung();
				
				//only search if at least 3 letters have been put in the tf
				if(tf_act.getText().length() > 2) {
					VeranstaltungDAO vdao = DAOFactory.getVeranstaltungDAO();
					
					VeranstaltungKey vk = new VeranstaltungKey();
					Veranstaltung v = new Veranstaltung();
					vk.setBezeichnung(tf_act.getText().trim());
					v.setComp_id(vk);
						
					List<Veranstaltung> result_event = new ArrayList<Veranstaltung>();
					try {
						result_event = vdao.search(v);
					} catch(Exception e) {
						//System.out.println(e.getStackTrace());
						JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHACT_SEARCHERROR"),lang.getString("OPH_SEARCHACT_SEARCHERROR"),JOptionPane.WARNING_MESSAGE);
						logger.warn("ActionEvent:Search:BL:Warning: "+e.getMessage()+" - "+e.toString()+" - " +e.getStackTrace());
					}
						
					if(result_event.size() > 0) {
						eventList = new ArrayList<Veranstaltung>(result_event);
						GuiMemory.attachVeranstaltung(eventList.get(0));
						//System.out.println("Found:"+result_event.get(0).getComp_id().getBezeichnung());
						btn_search.setEnabled(true);
						this.actionPerformed(new ActionEvent(tf_act,new Random().nextInt(),"search"));
					} else {
						emptyTable();
						btn_search.setEnabled(false);
					}
				} else {
					
					btn_search.setEnabled(true);
					emptyTable();
				}
			} else {
				//after reading the signals detach it
				GuiMemory.detachSignal();
			}
		}
		
		System.out.println();
	}
	/**
	 * Handles the other Textfield input (dynamically)
	 */
	private void checkTextFields(DocumentEvent de) {
		if(de.getDocument().equals(((JTextField)jdc_dateFrom.getDateEditor()).getDocument())) {
			this.actionPerformed(new ActionEvent(jdc_dateFrom,new Random().nextInt(),"search"));
		} else if(de.getDocument().equals(((JTextField)jdc_dateTo.getDateEditor()).getDocument())) {
			this.actionPerformed(new ActionEvent(jdc_dateTo,new Random().nextInt(),"search"));
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
    	pn_list.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SEARCHACTRESULTS")));
	}
	
}
