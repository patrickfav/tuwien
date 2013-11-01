package ticketline.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import ticketline.bl.GuiMemory;
import ticketline.bl.ITrail;
import ticketline.bl.Trail;
import ticketline.cfg.ConfigFactory;
import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.KategorieDAO;
import ticketline.db.Auffuehrung;
import ticketline.db.Belegung;
import ticketline.db.Kategorie;
import ticketline.db.KategorieKey;
import ticketline.db.Reihe;
import ticketline.gui.components.DefaultSearchTable;
import ticketline.gui.components.NoEditTableModel;
/**
 * 
 * Reservation/Sales Step 1 Panel
 * @author FlorianR, Patrick F
 * @version 0.5
 *
 */
public class KategorieWaehlenGui extends JPanel implements ActionListener, ListSelectionListener, ITrail {

	private static final long serialVersionUID = 1L;
	
	private static ResourceBundle lang;
	private static Logger logger = Logger.getLogger(KategorieWaehlenGui.class);
	private static Integer panel_width = ConfigFactory.getConfig().getDefaultPanelWidth();
	private static Integer panel_heigth = ConfigFactory.getConfig().getDefaultPanelHeight();
	
	private Trail trail;
	
	private JLayeredPane pn_list;
	
	private String[] colnames;
	private Object[][] 	data = {};
	private Double[] percentageColumnWidth;
	
	private DefaultSearchTable tb_results;
	private NoEditTableModel tm;
	private JScrollPane scrl_results;
	
    private JButton	 btn_back;
    private JButton	 btn_next;
    
    private Auffuehrung auffuehrung;
    private Kategorie category;
    
    public KategorieWaehlenGui() {
    	
    	lang = ConfigFactory.getConfig().getLanguageBundle();
    	  	
    	pn_list = new JLayeredPane();
    	
    	btn_back = new JButton(lang.getString("BTN_SELECTCATEGORY_BACK"));
    	btn_next = new JButton(lang.getString("BTN_SELECTCATEGORY_NEXT"));
    	
    	
    	
    	// Initialize Table Colnames
    	colnames = new String[5];
    	colnames[0] = lang.getString("LBL_SELECTCATEGORY_NAME");
    	colnames[1] = lang.getString("LBL_SELECTCATEGORY_MIN");
    	colnames[2] = lang.getString("LBL_SELECTCATEGORY_MAX");
    	colnames[3] = lang.getString("LBL_SELECTCATEGORY_STD");
    	colnames[4] = lang.getString("LBL_SELECTCATEGORY_SEATS");
    	
    	// Table
    	percentageColumnWidth = new Double[]{0.4,0.15,0.15,0.15,0.15};
    	
    	tb_results = new DefaultSearchTable();
    	tm = new NoEditTableModel(data,colnames);
    	tb_results.setModel(tm);
    	tb_results.setPreferredColumnWidths(percentageColumnWidth);
    	tb_results.getSelectionModel().addListSelectionListener(this);
    	scrl_results = new JScrollPane(tb_results);
    	scrl_results.setMinimumSize(new Dimension(500,500));	
    	scrl_results.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth()-50, ConfigFactory.getConfig().getDefaultPanelHeight()-50));
    	
    	if(GuiMemory.isKategorieSet()) {
 			category = GuiMemory.detachKategorie();
 			btn_next.setText(category.getComp_id().getBezeichnung());
 		}
    	
    	// Layout
    	GridBagLayout gb_reskauf3 = new GridBagLayout();
    	setLayout(gb_reskauf3);
    	setSize(panel_width, panel_heigth);
 		GridBagConstraints c = new GridBagConstraints();
 		c.anchor = GridBagConstraints.LINE_START;
 		c.insets = new Insets(5,5,5,5);
 		
 		/*
 		 * List Panel
 		 */
 		GridBagLayout gb_list = new GridBagLayout();
 		pn_list.setLayout(gb_list);
 		pn_list.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SELECTCATEGORY")));
 		pn_list.setMinimumSize(new Dimension(600,600));	
 		pn_list.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), ConfigFactory.getConfig().getDefaultPanelHeight()));
 		
 		// Column 1
 		c.gridx = 0;
 		c.gridy = 0;
 		c.anchor = GridBagConstraints.CENTER;
 		scrl_results.setPreferredSize(new Dimension(500,500));
 		gb_list.setConstraints(scrl_results, c);
 		pn_list.add(scrl_results);
 		
 		// ADD LIST PANEL
 		c.gridx = 0;
 		c.gridy = 0;
 		gb_reskauf3.setConstraints(pn_list, c);
 		add(pn_list);
 		
 		// ADD RESET BUTTON
 		c.gridx = 0;
 		c.gridy = 1;
 		c.anchor = GridBagConstraints.LINE_START;
 		btn_back.addActionListener(this);
 		btn_back.setActionCommand("back");
 		gb_reskauf3.setConstraints(btn_back, c);
 		add(btn_back);

 		// ADD NEXT BUTTON
 		c.anchor = GridBagConstraints.LINE_END;
 		btn_next.addActionListener(this);
		btn_next.setActionCommand("next");
		btn_next.setEnabled(false);
 		gb_reskauf3.setConstraints(btn_next, c);
 		add(btn_next);
 		
 		setVisible(true);
    }
    
    /*
     * ACTION PERFORMED - ACTION LISTENER
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    
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
		
	}

    /*
	 * LIST EVENT LISTENER
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
    
    @Override
	public void valueChanged(ListSelectionEvent arg0) {
		if(tb_results.getSelectedRow() != -1 && tb_results.getValueAt(tb_results.getSelectedRow(),0) !=null && !tb_results.getValueAt(tb_results.getSelectedRow(),0).equals("")) {
			
			auffuehrung = GuiMemory.detachAuffuehrung();
			KategorieKey kk = new KategorieKey();
			kk.setBezeichnung(tb_results.getValueAt(tb_results.getSelectedRow(),0).toString());
			kk.setOrt(auffuehrung.getComp_id().getOrt());
			kk.setOrtbez(auffuehrung.getComp_id().getOrtbez());
			kk.setSaalbez(auffuehrung.getSaal().getComp_id().getBezeichnung());
			
			KategorieDAO kdao = DAOFactory.getKategorieDAO();
			Kategorie k = kdao.get(kk);
			btn_next.setEnabled(true);
			btn_next.setText(new MessageFormat(lang.getString("BTN_SELECTCATEGORY_NEXTSELECTSEAT")).format(new String[] { tb_results.getValueAt(tb_results.getSelectedRow(),0).toString() }));
			if(ConfigFactory.getConfig().getDefaultPanelWidth() < 700){
				btn_next.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth()/2,22));
	 		}					
			GuiMemory.attachKategorie(k);
			GuiMemory.attachAuffuehrung(auffuehrung);
		
		} else {
			btn_next.setText(lang.getString("BTN_SELECTCATEGORY_NEXT"));
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
		//getData();
	}

	@Override
	public void reloadChanges() {
		getData();
	}
	
	@Override
	public void dummySearch() {
		DAOFactory.getKategorieDAO().getAll();
	}
	
	/*
	 * PRIVATE METHODS
	 */
	
	private void getData(){
		Kategorie k = new Kategorie();
		KategorieDAO kdao = DAOFactory.getKategorieDAO();
		Auffuehrung a = GuiMemory.detachAuffuehrung();
		
		k.setSaal(a.getSaal());
		
		//String[] colnames = {lang.getString("LBL_SELECTCATEGORY_NAME"), lang.getString("LBL_SELECTCATEGORY_MIN"), lang.getString("LBL_SELECTCATEGORY_MAX"), lang.getString("LBL_SELECTCATEGORY_STD")};
		
		try {
			List<Kategorie> result = kdao.search(k);
			if(result.size() == 0){
				System.out.println("Nothing Found!");
			} else {
				data = new Object[result.size()][5];
				int i = 0;
				for(Kategorie kat:result){
					
					//get the count of free seats
					int sum = 0;
					int sum1 = 0;
					int sum2 = 0;
					Set<Reihe> reihen = kat.getReihen();
					Set<Belegung> reference_belegungen = a.getBelegungen();
					for(Reihe r : reihen) {
						Set<Belegung> belegung = r.getBelegungen();
						for(Belegung b : belegung) {
							if(reference_belegungen.contains(b)) {
								sum+=b.getAnzfrei();
								sum1+=b.getAnzres();
								sum2+=b.getAnzverk();
								//System.out.println(sum+"/"+sum1+"/"+sum2);
							}
						}
					}
					
					if(sum+sum1+sum2 > 0) {
					
						data[i][0] = kat.getComp_id().getBezeichnung();
						data[i][1] = kat.getPreismin()+" "+ConfigFactory.getConfig().getCurrencySym();
						data[i][2] = kat.getPreismax()+" "+ConfigFactory.getConfig().getCurrencySym();
						data[i][3] = kat.getPreisstd()+" "+ConfigFactory.getConfig().getCurrencySym();
						data[i][4] = sum/*+"/"+sum1+"/"+sum2*/;
						i++;
					}
				}
				tm = new NoEditTableModel(data,colnames);
				tb_results.setPreferredColumnWidths(percentageColumnWidth);
		    	tb_results.setModel(tm);
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, lang.getString("OPT_SELECTCATEGORY_SEARCHERROR"),lang.getString("OPH_SELECTCATEGORY_SEARCHERROR"),JOptionPane.WARNING_MESSAGE);
			logger.warn("ActionEvent:Search:BL:Warning: "+e.getMessage()+" - "+e.toString()+" - " +e.getStackTrace());
		}
		
		GuiMemory.attachAuffuehrung(a);
	}

	

	

}
