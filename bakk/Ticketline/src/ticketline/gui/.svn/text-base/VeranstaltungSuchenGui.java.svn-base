package ticketline.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
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
import ticketline.dao.interfaces.VeranstaltungDAO;
import ticketline.db.Veranstaltung;
import ticketline.db.VeranstaltungKey;
import ticketline.gui.components.DefaultSearchTable;
import ticketline.gui.components.NoEditTableModel;

/**
* Veranstaltung Suchen GUI
* @author ReneN, FlorianR,PatrickF
* @version 0.7
*/
public class VeranstaltungSuchenGui extends JPanel implements ActionListener,ListSelectionListener,DocumentListener,ITrail{
	
	private static final long serialVersionUID = 1L;
	
	private static ResourceBundle lang;
	private static Integer panel_width = ConfigFactory.getConfig().getDefaultPanelWidth();
	private static Integer panel_heigth = ConfigFactory.getConfig().getDefaultPanelHeight();
	private static Logger logger = Logger.getLogger(VeranstaltungSuchenGui.class);
	
	private Trail trail;
	
	private Veranstaltung veranstaltung;
	
	private JLayeredPane pn_form;
	private JLayeredPane pn_list; 
	
	private JLabel lbl_bez;
	private JLabel lbl_cat;
	private JLabel lbl_time;
	private JLabel lbl_content;
	
	private JTextField tf_bez;
	private JTextField tf_cat;
	private JTextField tf_content;
	
	private JButton btn_search;
	private JButton btn_back;
	private JButton btn_select;
	
	SpinnerNumberModel nm;
    JSpinner sp_duration;
	
	private String[]	colnames;
	private Object[][] 	data = {};
	
	private NoEditTableModel 	tm;
	private DefaultSearchTable	tb_results;
	private JScrollPane scrl_table;
	
	private Double[] percentageColumnWidth;
	private int colNum;
	
	public VeranstaltungSuchenGui(){
		Gui.setPanelSize();
		
		lang = ConfigFactory.getConfig().getLanguageBundle();
		
		pn_form = new JLayeredPane();
		pn_list = new JLayeredPane();
		
		lbl_bez = new JLabel(lang.getString("LBL_SEARCHEVENT_BEZ"));
		lbl_cat = new JLabel(lang.getString("LBL_SEARCHEVENT_CAT"));
		lbl_time = new JLabel(lang.getString("LBL_SEARCHEVENT_LAST"));
		lbl_content = new JLabel(lang.getString("LBL_SEARCHEVENT_CONTENT"));
		
		tf_bez = new JTextField(20);
		tf_cat = new JTextField(20);
		tf_content = new JTextField(20);
		
		// Duration Spinner
		nm = new SpinnerNumberModel(240,10,3600,1);
		sp_duration = new JSpinner(nm);
		
		btn_search = new JButton(lang.getString("BTN_SEARCHEVENT_SEARCH"));
		btn_back = new JButton(lang.getString("BTN_SEARCHEVENT_BACK"));
		btn_select = new JButton(lang.getString("BTN_SEARCHEVENT_SELECT"));
		
		// Initialize Table Colnames
		colNum = 3;
    	colnames = new String[colNum];
    	colnames[0] = lang.getString("TCN_SEARCHEVENT_DESCRIPTION");
    	colnames[1] = lang.getString("TCN_SEARCHEVENT_CATEGORY");
    	colnames[2] = lang.getString("TCN_SEARCHEVENT_LENGTH");
		
		// Initialize Table
		percentageColumnWidth = new Double[]{0.475, 0.475, 0.05};
		
    	tb_results = new DefaultSearchTable();
    	tm = new NoEditTableModel(data,colnames);
    	tb_results.setModel(tm);
    	tb_results.setPreferredColumnWidths(percentageColumnWidth);
    	tb_results.getSelectionModel().addListSelectionListener(this);
    	scrl_table = new JScrollPane(tb_results);
		
		// Layout
    	GridBagLayout gb_searchevent = new GridBagLayout();
    	setLayout(gb_searchevent);
    	setSize(panel_width, panel_heigth);
 		GridBagConstraints c = new GridBagConstraints();
 		c.anchor = GridBagConstraints.LINE_START;
 		c.insets = new Insets(5,5,5,5);

 		/**
 		 * Form Panel
 		 */
 		GridBagLayout gb_form = new GridBagLayout();
		pn_form.setLayout(gb_form);
		pn_form.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SEARCHEVENT")));
		
		// Column 1
		c.gridx = 0;
		c.gridy = 0;
		gb_form.setConstraints(lbl_bez, c);
		pn_form.add(lbl_bez);
		c.gridy = 1;
		gb_form.setConstraints(lbl_cat, c);
		pn_form.add(lbl_cat);
		c.gridy = 2;
		gb_form.setConstraints(lbl_time, c);
		pn_form.add(lbl_time);
		c.gridy = 3;
		gb_form.setConstraints(lbl_content, c);
		pn_form.add(lbl_content);
		
		// Column 2
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		gb_form.setConstraints(tf_bez, c);
		tf_bez.getDocument().addDocumentListener(this);
		pn_form.add(tf_bez);	
		c.gridy = 1;
		gb_form.setConstraints(tf_cat, c);
		tf_cat.getDocument().addDocumentListener(this);
		pn_form.add(tf_cat);
		c.gridy = 2;
		gb_form.setConstraints(sp_duration, c);
		pn_form.add(sp_duration);	
		c.gridy = 3;
		gb_form.setConstraints(tf_content, c);
		tf_content.getDocument().addDocumentListener(this);
		pn_form.add(tf_content);
		
		// Add Search Button
		c.gridx = 0;
		c.gridy = 4;
		gb_form.setConstraints(btn_search, c);
		btn_search.addActionListener(this);
		btn_search.setActionCommand("search");
		pn_form.add(btn_search);
		
		// ADD FORM PANEL
 		c.gridx = 0;
 		c.gridy = 0;
 		c.gridheight = 1;
 		c.gridwidth = 1;
 		pn_form.setMinimumSize(new Dimension(570,600));	
 		pn_form.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), ConfigFactory.getConfig().getDefaultPanelHeight()/2));
 		gb_searchevent.setConstraints(pn_form, c);
 		add(pn_form);
			
		/**
 		 * List Panel
 		 */
 		GridBagLayout gb_list = new GridBagLayout();
 		pn_list.setLayout(gb_list);
 		pn_list.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SEARCHEVENTRESULTS")));
 		
 		// Column 1
 		c.gridx = 0;
 		c.gridy = 0;
 		c.anchor = GridBagConstraints.CENTER;
 		scrl_table.setMinimumSize(new Dimension(570,600));	
 		scrl_table.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), ConfigFactory.getConfig().getDefaultPanelHeight()/2));
 		gb_list.setConstraints(scrl_table, c);
 		pn_list.add(scrl_table);
 		
 		// ADD LIST PANEL
 		c.gridx = 0;
 		c.gridy = 1;
 		gb_searchevent.setConstraints(pn_list, c);
 		add(pn_list);
 		
 		// ADD Back BUTTON
 		c.gridx = 0;
 		c.gridy = 2;
 		c.anchor = GridBagConstraints.LINE_START;
 		btn_back.addActionListener(this);
		btn_back.setActionCommand("back");
 		gb_searchevent.setConstraints(btn_back, c);
 		add(btn_back);
 		
 		// ADD Select BUTTON
 		c.gridx = 0;
 		c.gridy = 2;
 		c.anchor = GridBagConstraints.LINE_END;
 		btn_select.addActionListener(this);
		btn_select.setActionCommand("select");
		btn_select.setEnabled(false);
 		gb_searchevent.setConstraints(btn_select, c);
 		add(btn_select);
 		
 		//automaticall search
		this.actionPerformed(new ActionEvent(this,new Random().nextInt(),"search"));
 		
 		setVisible(true);
	}

	
	/*
     * ACTION PERFORMED - ACTION LISTENER
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		
		// Select
		if(evt.getActionCommand().equals("select")){
			GuiMemory.attachVeranstaltung(veranstaltung);
			//GuiMemory.detachOrt();
			GuiMemory.attachSignal("SIGEVTSEARCH");
			Gui.load((Component)trail.getPrevious(this));
			setVisible(true);
		}
		// Back
		if(evt.getActionCommand().equals("back")){
			//delete saal
			//GuiMemory.detachVeranstaltung();
			GuiMemory.attachSignal("SIGIGNORE");
			Gui.load((Component)trail.getPrevious(this));			
			setVisible(true);
		}
		// Action: Search
		if(evt.getActionCommand().equals("search")){
			VeranstaltungKey vk = new VeranstaltungKey();
			Veranstaltung v = new Veranstaltung();
			
			if(!tf_bez.getText().equals(""))
				vk.setBezeichnung(tf_bez.getText().trim());
			if(!tf_cat.getText().equals(""))
				vk.setKategorie(tf_cat.getText().trim());
			if(!sp_duration.getValue().equals("")) {
				try {
					v.setDauer(new Integer((Integer) sp_duration.getValue()));
				} catch(Exception e) {
					JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHEVENT_INPUTERROR_NUMBER"),lang.getString("OPH_SEARCHEVENT_INPUTERROR"),JOptionPane.WARNING_MESSAGE);
					logger.warn("ActionEvent:Search:Input:Warning: "+e.getMessage()+" - "+e.toString()+" - " +e.getStackTrace());
				}
			}
			
			v.setComp_id(vk);
			
			VeranstaltungDAO vdao = DAOFactory.getVeranstaltungDAO();
			
			try {
				List<Veranstaltung> result = vdao.search(v);
			
				if(result.size() == 0) {
					JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHEVENT_NOFOUND"),lang.getString("OPH_SEARCHEVENT_INFO"),JOptionPane.INFORMATION_MESSAGE);
					emptyTable();
				} else {
					data = new Object[result.size()][colNum];
					int i = 0;
					for(Veranstaltung vr: result){
						data[i][0] = vr.getComp_id().getBezeichnung();
						data[i][1] = vr.getComp_id().getKategorie();
						data[i][2] = vr.getDauer();
						i++;
					}
					tm = new NoEditTableModel(data,colnames);
			    	tb_results.setModel(tm);
			    	tb_results.setPreferredColumnWidths(percentageColumnWidth);
			    	
			    	//updating title
			    	pn_list.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SEARCHEVENTRESULTS")+" ("+data.length+" "+ lang.getString("PNL_SEARCHCUSTOMERHITS") +")"));

					logger.debug("ActionEvent:Search:EventTable: updating");
				}
			} catch(Exception e) {
				JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCHEVENT_SEARCHERROR"),lang.getString("OPH_SEARCHEVENT_SEARCHERROR"),JOptionPane.WARNING_MESSAGE);
				logger.warn("ActionEvent:Search:BL:Warning: "+e.getMessage()+" - "+e.toString()+" - " +e.getStackTrace());
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
			VeranstaltungKey vk = new VeranstaltungKey((String) tb_results.getValueAt(tb_results.getSelectedRow(),0),(String) tb_results.getValueAt(tb_results.getSelectedRow(),1));
			veranstaltung = DAOFactory.getVeranstaltungDAO().get(vk);
			
			btn_select.setEnabled(true);
			btn_select.setText(lang.getString("BTN_SEARCHEVENT_SELECT") + " [" +veranstaltung.getComp_id().getBezeichnung()+"]");
		}
		//if nothing selected
		if(tb_results.getSelectedRow() == -1 || tb_results.getValueAt(tb_results.getSelectedRow(),0) ==null || tb_results.getValueAt(tb_results.getSelectedRow(),0).equals("")) {
			
			btn_select.setText(lang.getString("BTN_SEARCHEVENT_SELECT"));
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
		//GuiMemory.detachOrt();
	}

	@Override
	public void dummySearch() {
		DAOFactory.getVeranstaltungDAO().getAll();
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
	
	/**
	 * Handles the textfield input (dynamically)
	 */
	private void checkTextFields(DocumentEvent de) {
		if(de.getDocument().getLength() > 2) {
			if(de.getDocument().equals(tf_bez.getDocument())) {
				this.actionPerformed(new ActionEvent(tf_bez,new Random().nextInt(),"search"));
			} else if(de.getDocument().equals(tf_cat.getDocument())) {
				this.actionPerformed(new ActionEvent(tf_cat,new Random().nextInt(),"search"));
			} else if(de.getDocument().equals(tf_content.getDocument())) {
				this.actionPerformed(new ActionEvent(tf_content,new Random().nextInt(),"search"));
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
    	pn_list.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SEARCHEVENTRESULTS")));
	}

	/**
	 * Empty all textfields
	 */
	private void emptyTextFields(){
		tf_bez.setText("");
		tf_cat.setText("");
		tf_content.setText("");
	}
}
