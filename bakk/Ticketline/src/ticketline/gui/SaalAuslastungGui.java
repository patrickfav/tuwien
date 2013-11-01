package ticketline.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.toedter.calendar.JDateChooser;

import ticketline.bl.GuiMemory;
import ticketline.bl.ITrail;
import ticketline.bl.Trail;
import ticketline.cfg.ConfigFactory;
import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.BelegungDAO;
import ticketline.db.Belegung;
import ticketline.db.Saal;
import ticketline.db.SaalKey;
import ticketline.gui.components.PieChart;
import ticketline.gui.components.PieItem;

/**
 * 
 * Hall Efficiency Panel
 * @author FlorianR,PatrickM
 * @version 0.2
 *
 */
public class SaalAuslastungGui extends JPanel implements ActionListener, ITrail  {

	// Serial Version
	private static final long serialVersionUID = 1L;
	
	// Static
	private static ResourceBundle lang;
	private static Logger logger = Logger.getLogger(SaalAuslastungGui.class);
	private static Integer panel_width = ConfigFactory.getConfig().getDefaultPanelWidth();
	private static Integer panel_heigth = ConfigFactory.getConfig().getDefaultPanelHeight();

	// Trail
	private Trail trail;
	
	// Panels
	private JLayeredPane pn_form;
	private JPanel pn_chart;
	
	// Labels
    private JLabel lb_hall;
    private JLabel lb_from;
    private JLabel lb_to;
    
    //Legend for Graph
    private JPanel legend;
    
    private JLabel lbl_bought;
    private JLabel lbl_reserved;
    private JLabel lbl_free;
    
    private JPanel pn_green;
    private JPanel pn_red;
    private JPanel pn_cyan;
    
    // TextFields
    private JTextField tf_hall;
  
    // Buttons
    private JButton btn_select_hall;
    private JButton btn_execute;
    
    // Radio Buttons
    private ButtonGroup bg_period;
    private JRadioButton rb_defined;
    private JRadioButton rb_custom;
    
    // Date Chooser
    private JDateChooser jdc_from;
    private JDateChooser jdc_to;
    
    // ComboBoxes
    private JComboBox cb_periods;
    
    // Periods
    private String[] periods;
    
    // Hall, Event
    private Saal saal;
    
    //RB Values
    private boolean isDefined = true;
    private boolean isCustom = false;
    
    /**
	 * Constructor
	 */
    public SaalAuslastungGui() {
    	logger.debug("SaalAuslastungGui started");
    	Gui.setPanelSize();
    	
        
    	// Initialize Language
    	lang = ConfigFactory.getConfig().getLanguageBundle();
    	
    	//Initialize Legend
    	legend = new JPanel(new GridBagLayout());
    	pn_green = new JPanel();
    	pn_green.setBackground(Color.green);
    	pn_green.setSize(40, 40);
    	pn_red = new JPanel();
    	pn_red.setBackground(Color.red);
    	pn_red.setSize(40, 40);
    	pn_cyan = new JPanel();
    	pn_cyan.setBackground(Color.cyan);
    	pn_cyan.setSize(40, 40);
    	lbl_bought = new JLabel(lang.getString("LBL_BOUGHT"));
    	lbl_reserved = new JLabel(lang.getString("LBL_RESERVED"));
    	lbl_free = new JLabel(lang.getString("LBL_FREE"));
    	
    	
    	GridBagConstraints lc = new GridBagConstraints();
    	lc.gridx = 0;
    	lc.gridy = 0;
    	lc.anchor = GridBagConstraints.PAGE_START;
    	legend.add(lbl_free,lc);
    	lc.gridx = 1;
    	lc.gridy = 0;
    	lc.insets = new Insets(0,10,0,0);
    	legend.add(pn_green,lc);
    	lc.gridx = 0;
    	lc.gridy = 2;
    	lc.insets = new Insets(0,0,0,0);
    	legend.add(lbl_bought,lc);
    	lc.gridx = 1;
    	lc.gridy = 2;
    	lc.insets = new Insets(0,10,0,0);
    	legend.add(pn_red,lc);
    	lc.gridx = 0;
    	lc.gridy = 3;
    	lc.insets = new Insets(0,0,0,0);
    	legend.add(lbl_reserved,lc);
    	lc.gridx = 1;
    	lc.gridy = 3;
    	lc.insets = new Insets(0,10,0,0);
    	legend.add(pn_cyan,lc);
    	
    	// Initialize Language
    	pn_form = new JLayeredPane();
    	pn_chart = new JPanel(new GridBagLayout());
    	
    	// Initialize Labels
    	lb_hall = new JLabel(lang.getString("LBL_EFFICIENCY_HALL"));
    	lb_from = new JLabel(lang.getString("LBL_EFFICIENCY_FROM"));
    	lb_to = new JLabel(lang.getString("LBL_EFFICIENCY_TO"));
    	
    	// Initialize TextFields
    	tf_hall = new JTextField(15);
    	
    	// Initialize Buttons
    	btn_select_hall = new JButton(lang.getString("BTN_EFFICIENCY_SELECT_HALL"));
    	btn_execute = new JButton(lang.getString("BTN_EFFICIENCY_EXECUTE"));
    	
    	// Initialize Radio Buttons
    	bg_period = new ButtonGroup();
    	rb_defined = new JRadioButton(lang.getString("RB_EFFICIENCY_DEFINED"));
    	rb_defined.addActionListener(this);
    	rb_defined.setActionCommand(lang.getString("RB_EFFICIENCY_DEFINED"));
    	rb_custom = new JRadioButton(lang.getString("RB_EFFICIENCY_CUSTOM"));
    	rb_custom.addActionListener(this);
    	rb_custom.setActionCommand(lang.getString("RB_EFFICIENCY_CUSTOM"));
    	bg_period.add(rb_defined);
    	bg_period.add(rb_custom);
    	
    	// Initialize Date Chooses
    	jdc_from = new JDateChooser();
    	jdc_to = new JDateChooser(); 	
    	jdc_from.setPreferredSize(new Dimension(100,20));
    	jdc_to.setPreferredSize(new Dimension(100,20));
    	
    	// Initialize Periods
    	periods = new String[5];
    	periods[0] = "";
    	periods[1] = lang.getString("CB_EFFICIENCY_LAST_WEEK");
    	periods[2] = lang.getString("CB_EFFICIENCY_LAST_MONTH");
    	periods[3] = lang.getString("CB_EFFICIENCY_LAST_QUARTER");
    	periods[4] = lang.getString("CB_EFFICIENCY_LAST_YEAR");
    	
    	// Initialize ComboBoxes
    	cb_periods = new JComboBox(periods);
    	
    	// Set Layout
    	GridBagLayout gb_hallEfficency = new GridBagLayout();
    	setLayout(gb_hallEfficency);
    	setMinimumSize(new Dimension(570,600));
    	setSize(panel_width, panel_heigth);
 		GridBagConstraints c = new GridBagConstraints();
 		c.anchor = GridBagConstraints.LINE_START;
 		c.insets = new Insets(5,5,5,5);		
 		
 		/**
 		 * Form Panel
 		 */
 		// Set Layout
 		GridBagLayout gb_form = new GridBagLayout();
 		pn_form.setLayout(gb_form);
 		pn_form.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_EFFICIENCY_FORM")));
 		pn_form.setMinimumSize(new Dimension(570,600));	
 		pn_form.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), ConfigFactory.getConfig().getDefaultPanelHeight()/2));


 		// Column 1
 		c.gridx = 0;
 		c.gridy = 0;
 		gb_form.setConstraints(lb_hall, c);
 		pn_form.add(lb_hall);
 		c.gridy = 1;
 		c.gridwidth = 2;
 		gb_form.setConstraints(rb_defined, c);
 		rb_defined.setSelected(true);
 		pn_form.add(rb_defined);
 		c.gridy = 2;
 		c.insets = new Insets(0,25,0,0);
 		c.anchor = GridBagConstraints.LINE_START;
 		gb_form.setConstraints(cb_periods, c);
 		pn_form.add(cb_periods);
 			
 		// Column 2
 		c.anchor = GridBagConstraints.LINE_START;
 		c.gridx = 1;
 		c.gridy = 0;
 		c.gridwidth = 1;
 		c.insets = new Insets(0,0,0,0);
 		gb_form.setConstraints(tf_hall, c);
 		if(GuiMemory.isSaalSet()) {
 			saal = GuiMemory.getSaal();
 			tf_hall.setText(saal.getComp_id().getBezeichnung());
 		}
 		pn_form.add(tf_hall);
 		
 		c.gridx = 2;
 		c.gridy = 0;
 		c.gridwidth = 2;
 		gb_form.setConstraints(btn_select_hall, c);
 		btn_select_hall.addActionListener(this);
 		btn_select_hall.setActionCommand("selhall");
 		pn_form.add(btn_select_hall);
 		
 		// Column 3 		
 		c.gridx = 2;
 		c.gridy = 1;
 		c.gridwidth = 2;
 		gb_form.setConstraints(rb_custom, c);
 		pn_form.add(rb_custom);
 		c.gridy = 2;
 		c.gridwidth = 1;
 		c.insets = new Insets(0,25,0,0);
 		gb_form.setConstraints(lb_from, c);
 		pn_form.add(lb_from);
 		c.gridy = 3;
 		gb_form.setConstraints(lb_to, c);
 		pn_form.add(lb_to);
 		
 		// Column 4
 		c.gridx = 3;
 		c.gridy = 2;
 		c.insets = new Insets(0,0,0,0);
 		gb_form.setConstraints(jdc_from, c);
 		pn_form.add(jdc_from);
 		c.gridy = 3;
 		gb_form.setConstraints(jdc_to, c);
 		pn_form.add(jdc_to);
 		
 		// Add Execute Button
 		c.gridx = 0;
 		c.gridy = 5;
 		btn_execute.addActionListener(this);
 		btn_execute.setActionCommand("execute");
 		gb_form.setConstraints(btn_execute, c);
 		pn_form.add(btn_execute);
 		
 		// Add Form Panel
 		c.gridx = 0;
 		c.gridy = 0;
 		c.gridheight = 1;
 		c.gridwidth = 1;
 		gb_hallEfficency.setConstraints(pn_form, c);
 		add(pn_form);
 		
 		/**
 		 * Chart Panel
 		 */
 		
 		// Set Layout
 		GridBagLayout gb_chart = new GridBagLayout();
 		pn_chart.setLayout(gb_chart);
 		pn_chart.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_EFFICIENCY_CHART")));
 		pn_chart.setMinimumSize(new Dimension(570,600));	
 		pn_chart.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), ConfigFactory.getConfig().getDefaultPanelHeight()/2));
 		
 		// Add Chart Panel
 		c.gridx = 0;
 		c.gridy = 1;
 		gb_hallEfficency.setConstraints(pn_chart, c);
 		add(pn_chart);
 		
 		// Set Visible
 		setVisible(true); 
 		
 		//Load Subtrails
 		loadTrails();
 		
 		logger.debug("SaalAuslastungGui Created Successfully");
    }   
    private void loadTrails(){
		//Saal suchen
		trail = new Trail(2);
		trail.addNode(this);
	}
    @SuppressWarnings("deprecation")
	private void calculate() {
    	boolean ok = true;
		//If no User Input was made at all, then give you Info Message
		//that Input needs to be made
		if(isCustom) {
			if(tf_hall.getText().equals("")) {
				JOptionPane.showMessageDialog(this, lang.getString("OPT_HALLCALC_NOINPUT"),lang.getString("OPH_HALLCALC_NOINPUT"),JOptionPane.INFORMATION_MESSAGE);
				ok = false;
			}
		} else if(isDefined) {
			if(tf_hall.getText().equals("")) {
				JOptionPane.showMessageDialog(this, lang.getString("OPT_HALLCALC_NOINPUT"),lang.getString("OPH_HALLCALC_NOINPUT"),JOptionPane.INFORMATION_MESSAGE);
				ok = false;
			}
		}
		
		if(ok) {
			int anzFree = 0;
			int anzRes = 0;
			int anzBought = 0;
			int anzTotal = 0;
			Date begin = null;
			Date end = null;
			Date current = null;
			String pastLength = null;
			BelegungDAO bDAO = DAOFactory.getBelegungDAO();
			
			if(isCustom) {
				begin = jdc_from.getDate();
				end = jdc_to.getDate();
			}
			if(isDefined) {
				pastLength = cb_periods.getSelectedItem().toString();
				current = new Date(System.currentTimeMillis());
				GregorianCalendar gc = new GregorianCalendar(current.getYear(),current.getMonth(),current.getDay());
				
				if(pastLength.equals(lang.getString("CB_EFFICIENCY_LAST_WEEK"))) {
					gc.add(Calendar.DATE, -7);
					begin = gc.getTime();
				} else if(pastLength.equals(lang.getString("CB_EFFICIENCY_LAST_MONTH"))) {
					gc.add(Calendar.MONTH, -1);
					begin = gc.getTime();
				} else if(pastLength.equals(lang.getString("CB_EFFICIENCY_LAST_QUARTER"))) {
					gc.add(Calendar.MONTH, -3);
					begin = gc.getTime();
				} else if(pastLength.equals(lang.getString("CB_EFFICIENCY_LAST_YEAR"))) {
					gc.add(Calendar.YEAR, -1);
					begin = gc.getTime();
				}
				end = current;
			}	
			
			List<Belegung> results = bDAO.getBelegungen(saal,begin, end);
			if(results.size() == 0) {
				JOptionPane.showMessageDialog(this, lang.getString("OPT_HALLCALC_RESULT"),lang.getString("OPH_HALLCALC_RESULT"),JOptionPane.INFORMATION_MESSAGE);
			} else {
				logger.debug("ActionEvent:Search:ResNr:Results: " + results.size());
				
				for(Belegung b : results) {
					anzFree += b.getAnzfrei();
					anzRes += b.getAnzres();
					anzBought += b.getAnzverk();
				}
				anzTotal = anzFree + anzRes + anzBought;
				
				pn_chart.removeAll();
				PieItem[] pies = {new PieItem(anzFree + "," + ((anzFree*100)/anzTotal) + "%"), new PieItem(anzBought + "," + ((anzBought*100)/anzTotal) + "%"), new PieItem(anzRes + "," + ((anzRes*100)/anzTotal) + "%")};
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = 0;
				c.gridy = 0;
				pn_chart.add(new PieChart(200, pies),c);
				c.gridx = 1;
				pn_chart.add(legend);
				pn_chart.revalidate();
				pn_chart.repaint();
			}
		}
    }
	@Override
	public void actionPerformed(ActionEvent evt) {
		//Select Hall
		if(evt.getActionCommand().equals("selhall")){
			//Handle Trail to SaalSuchen Gui
			trail.getNext(this).setTrail(trail);
			//Load SaalSuchen Gui
			Gui.load((Component)trail.getNext(this));
			setVisible(true);
		}
		else if(evt.getActionCommand().equals("execute")){
			saal = new Saal();
			SaalKey key = new SaalKey();
			key.setBezeichnung(tf_hall.getText());
			saal.setComp_id(key);
			
			calculate();
		} else if(evt.getActionCommand().equals(lang.getString("RB_EFFICIENCY_CUSTOM"))) {
			isCustom = true;
			isDefined = false;
		} else if(evt.getActionCommand().equals(lang.getString("RB_EFFICIENCY_DEFINED"))) {
			isCustom = false;
			isDefined = true;
		}
		
	}

	@Override
	public void setTrail(Trail trail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reloadChanges() {
		if(GuiMemory.isSaalSet()) {
			//setting intern saal object and textfield
			saal = GuiMemory.detachSaal();
			tf_hall.setText(saal.getComp_id().getBezeichnung());
			calculate();
		}
	}

	@Override
	public void dummySearch() {
		// TODO Auto-generated method stub
		
	}


}
