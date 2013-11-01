package ticketline.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import ticketline.bl.GuiMemory;
import ticketline.bl.ITrail;
import ticketline.bl.Trail;
import ticketline.cfg.ConfigFactory;
import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.ArtikelDAO;
import ticketline.dao.interfaces.BestellungDAO;
import ticketline.db.Artikel;
import ticketline.db.Bestellung;
import ticketline.db.BestellungKey;
import ticketline.db.Kunde;
import ticketline.gui.components.DefaultSearchTable;
import ticketline.gui.components.NoEditTableModel;

/**
 * This Class provides the User Interface for
 * selling Promotional Materials Step 2
 * 
 * @author PatrickM, ReneN, AndiS
 * @version 0.1
 *
 */
public class WerbematerialWaehlen extends JPanel implements ActionListener,ListSelectionListener,ITrail {
	
	private static final long serialVersionUID = 1L;
	private static ResourceBundle lang;
	private static Integer panel_width = ConfigFactory.getConfig().getDefaultPanelWidth();
	private static Integer panel_heigth = ConfigFactory.getConfig().getDefaultPanelHeight();
	
	private static Logger logger = Logger.getLogger(ReservierungTicketsSuchenBearbeitenGui.class);
	
	private Trail trail;
	
	private JLayeredPane pn_article;
	private JLayeredPane pn_list;
	
    // Table
    private String[] colnames;	
	private Object[][] data;
	
	private DefaultSearchTable tb_articles;
	private NoEditTableModel tModel;
	private JScrollPane scrl_articles;
	
	// List
	private JList lst_chosen;
	private DefaultListModel lModel;
	private JScrollPane scrl_chosen;
	
	private JButton	 btn_add;
	private JButton	 btn_remove;
	private JButton	 btn_reset;
	private JButton	 btn_next;
	
	private JLabel	lbl_sum;
	
    private SpinnerNumberModel nm;
    private JSpinner sp_num;
	
	private Vector<Artikel> selectedArtikel = new Vector<Artikel>();
	private Vector<Artikel> list = new Vector<Artikel>();
	
	private int[] listToSelected;
	private int[] count; 
	
	public WerbematerialWaehlen() {
		logger.debug("WerbematerialienVerkaufenSchritt2Gui started"); 	

		lang = ConfigFactory.getConfig().getLanguageBundle();
		
    	pn_article = new JLayeredPane();
    	pn_list = new JLayeredPane();
    	
    	btn_add = new JButton(lang.getString("BTN_SELECTARTICLE_NEXT"));
    	btn_remove = new JButton(lang.getString("BTN_SELECTARTICLE_REMOVE"));
    	btn_reset = new JButton(lang.getString("BTN_SELECTARTICLE_RESET"));
    	btn_next = new JButton(lang.getString("BTN_SELECTARTICLE_FINISH"));
    	
    	lbl_sum = new JLabel(lang.getString("LBL_SELECTARTICLE_SUM") + ": 0");
    	
    	// Initialize Table Colnames
    	colnames = new String[5];
    	colnames[0] = lang.getString("LBL_SELECTARTICLE_NUMBER");
    	colnames[1] = lang.getString("LBL_SELECTARTICLE_SHORTDESC");
    	colnames[2] = lang.getString("LBL_SELECTARTICLE_CATEGORY");
    	colnames[3] = lang.getString("LBL_SELECTARTICLE_DESC");
    	colnames[4] = lang.getString("LBL_SELECTARTICLE_PRICE");
    	
    	// Initialize Table
    	tb_articles = new DefaultSearchTable();
    	tb_articles.setAutoCreateRowSorter(true);
    	tb_articles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	tModel = new NoEditTableModel(data,colnames);
    	tb_articles.setModel(tModel);
    	tb_articles.getSelectionModel().addListSelectionListener(this);
    	scrl_articles = new JScrollPane(tb_articles);
    	scrl_articles.setMinimumSize(new Dimension(600,600));	
    	scrl_articles.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth()-50, ConfigFactory.getConfig().getDefaultPanelHeight()-50));
    
    	// Initialize List
    	lst_chosen = new JList();
    	lst_chosen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	lModel = new DefaultListModel();
    	lst_chosen.setModel(lModel);
    	lst_chosen.getSelectionModel().addListSelectionListener(this);
    	scrl_chosen = new JScrollPane(lst_chosen);
    	scrl_chosen.setMinimumSize(new Dimension(600,600));	
    	scrl_chosen.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth()-50, ConfigFactory.getConfig().getDefaultPanelHeight()-50));
    	
    	// Num Spinner
		nm = new SpinnerNumberModel(1,0,999,1);
		sp_num = new JSpinner(nm);
    	
    	// Layout
    	GridBagLayout gb_main = new GridBagLayout();
    	setLayout(gb_main);
    	setSize(panel_width, panel_heigth);
 		GridBagConstraints c = new GridBagConstraints();
 		c.anchor = GridBagConstraints.LINE_START;
 		c.insets = new Insets(5,5,5,5);
 		
 		
 		/**
 		 * Article Panel
 		 */
 		
 		// Set Layout
 		GridBagLayout gb_article = new GridBagLayout();
 		pn_article.setLayout(gb_article);
 		pn_article.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SELECTARTICLE")));
 		
 		// Add Table
 		c.gridx = 0;
 		c.gridy = 0;
 		c.anchor = GridBagConstraints.CENTER;
 		scrl_articles.setMinimumSize(new Dimension(570,600));	
 		scrl_articles.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), (ConfigFactory.getConfig().getDefaultPanelHeight()/2)-50));
 		gb_article.setConstraints(scrl_articles, c);
 		pn_article.add(scrl_articles);
 		
 		// Add Add Button
 		c.gridx = 0;
 		c.gridy = 1;
 		c.anchor = GridBagConstraints.LINE_END;
 		btn_add.addActionListener(this);
		btn_add.setActionCommand("add");
 		gb_article.setConstraints(btn_add, c);
 		btn_add.setEnabled(false);
 		pn_article.add(btn_add);
 		
 		// Add Spinner
 		c.gridx = 0;
 		c.gridy = 1;
 		c.anchor = GridBagConstraints.LINE_START;
 		gb_article.setConstraints(sp_num, c);
 		pn_article.add(sp_num);
 		
 		// Add Article Panel
 		c.gridx = 0;
 		c.gridy = 0;
 		gb_main.setConstraints(pn_article, c);
 		add(pn_article);
 		
 		
 		
 		
 		
 		/**
 		 * List Panel
 		 */
 		
 		// Set Layout
 		GridBagLayout gb_list = new GridBagLayout();
 		pn_list.setLayout(gb_list);
 		pn_list.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SELECTARTICLE_BILL")));
 		
 		// Add Table
 		c.gridx = 0;
 		c.gridy = 0;
 		c.anchor = GridBagConstraints.CENTER;
 		scrl_chosen.setMinimumSize(new Dimension(570,600));	
 		scrl_chosen.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), (ConfigFactory.getConfig().getDefaultPanelHeight()/2)-50));
 		gb_list.setConstraints(scrl_chosen, c);
 		pn_list.add(scrl_chosen);
 		
 		
 		// Add Reset Button
 		c.gridx = 0;
 		c.gridy = 1;
 		c.anchor = GridBagConstraints.LINE_START;
 		btn_reset.addActionListener(this);
 		btn_reset.setActionCommand("reset");
 		gb_list.setConstraints(btn_reset, c);
 		btn_reset.setEnabled(true);
 		pn_list.add(btn_reset);
 		
 		// Add Remove Button
 		c.gridx = 0;
 		c.gridy = 1;
 		c.anchor = GridBagConstraints.LINE_END;
 		btn_remove.addActionListener(this);
 		btn_remove.setActionCommand("remove");
 		gb_list.setConstraints(btn_remove, c);
 		btn_remove.setEnabled(false);
 		pn_list.add(btn_remove);
 		
 		// Add List Panel
 		c.gridx = 0;
 		c.gridy = 1;
 		gb_main.setConstraints(pn_list, c);
 		add(pn_list);
 		
 		
 		/**
 		 * Trail Buttons
 		 */
 		
 		// Add Next Button
 		c.gridx = 0;
 		c.gridy = 2;
 		c.anchor = GridBagConstraints.LINE_END;
 		btn_next.addActionListener(this);
		btn_next.setActionCommand("next");
 		gb_main.setConstraints(btn_next, c);
 		btn_next.setEnabled(false);
 		add(btn_next);
 		
 		// Add Next Button
 		c.gridx = 0;
 		c.gridy = 2;
 		c.anchor = GridBagConstraints.LINE_START;
 		gb_main.setConstraints(lbl_sum, c);
 		add(lbl_sum);
 		
 		// Set Visible
 		setVisible(true); 		
 		
 		loadArtikelList();
 		
		logger.debug("WerbematerialienVerkaufenSchritt2Gui Created Successfully");
	}
	
	public void actionPerformed(ActionEvent evt) {
		// Add
		if(evt.getActionCommand().equals("add")){
			
			if(tb_articles.getSelectedRow() != -1 && tb_articles.getValueAt(tb_articles.getSelectedRow(),0) !=null && !tb_articles.getValueAt(tb_articles.getSelectedRow(),0).equals("")) {

				ArtikelDAO adao = DAOFactory.getArtikelDAO();
				for(int i = 0; i<nm.getNumber().intValue();i++)
					selectedArtikel.add(adao.get((Integer)tb_articles.getValueAt(tb_articles.getSelectedRow(),0)));
				loadSelectedList();
			}
			
			btn_next.setEnabled(true);

		}
		// Remove
		if(evt.getActionCommand().equals("remove")){
			
			if(lst_chosen.getSelectedIndex()!=-1) {
				
				int artnum = listToSelected[lst_chosen.getSelectedIndex()];
				
				for(int i = 0; i<selectedArtikel.size(); i++)
				{
					if(selectedArtikel.get(i).getArtikelnr()==artnum)
					{
						selectedArtikel.remove(selectedArtikel.get(i));
						i--;
					}
				}
				loadSelectedList();
			}
			
			if(lModel.getSize() > 0 ) {
				btn_next.setEnabled(true);
			}
			else{
				btn_next.setEnabled(false);
			}
		}
		// Reset
		if(evt.getActionCommand().equals("reset")){

			selectedArtikel.clear();
			loadSelectedList();
			
			btn_next.setEnabled(false);
			
		}
		// Next
		if(evt.getActionCommand().equals("next")){
			
			Integer sum = 0;
			
			//Calculate the sum of the bill
			sum = calcSum();
			
			//Show Question
			int n = JOptionPane.showConfirmDialog(
				    this,
				    new MessageFormat(lang.getString("OPT_SELECTARTICLE_QUESTION")).format(new String[] { sum.toString() }).toString(),
				    lang.getString("OPH_SELECTARTICLE_QUESTION"),
				    JOptionPane.YES_NO_OPTION);
			
			//Show Information
			if(n==0)
			{
				BestellungDAO bdao = DAOFactory.getBestellungDAO();
				
				
				for(Artikel a : list)
				{
					Kunde k = GuiMemory.getKunde();
					
					Bestellung b = new Bestellung();
					BestellungKey bkey = new BestellungKey();
					
					bkey.setArtikelnr(a.getArtikelnr());
					bkey.setDatumuhrzeit(new Date());
					bkey.setKartennr(k.getKartennr());
					
					b.setComp_id(bkey);
					b.setArtikel(a);
					b.setKunde(k);
					b.setMenge(count[a.getArtikelnr()]);
					b.setZahlart("bar");
					
					bdao.save(b);
				}
				
				JOptionPane.showMessageDialog(this,lang.getString("OPT_SELECTARTICLE_SOLD"),lang.getString("OPH_SELECTARTICLE_SOLD"),JOptionPane.INFORMATION_MESSAGE);
				
				nm.setValue(1);
				tb_articles.clearSelection();
				
				btn_next.setEnabled(false);
				selectedArtikel.clear();
				loadSelectedList();
			}
			
		}

	}
	


	@Override
	public void dummySearch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reloadChanges() {
		
	}
	
	private int calcSum()
	{
		int sum = 0;
		
		for(Artikel a : selectedArtikel)
		{
			sum += a.getPreis().intValue();
		}
		
		return sum;
	}
	
	private void loadSelectedList()
	{
		lModel.clear();
		
		list = new Vector<Artikel>();
		
		count = new int[tModel.getRowCount()+1];
		
		for(Artikel a : selectedArtikel)
		{
			count[a.getArtikelnr()]++;
			
			if(!list.contains(a))
			{
				listToSelected[list.size()] = a.getArtikelnr();
				list.add(a);
			}
		}
		
		for(Artikel a : list)
		{
			lModel.addElement("Anzahl:  " + count[a.getArtikelnr()] +  "                         ArtikelNr: " + a.getArtikelnr() + "   Bezeichnung: " + a.getKurzbezeichnung() );
		}
		

		lbl_sum.setText(lang.getString("LBL_SELECTARTICLE_SUM") + ": " + calcSum());
	}
	
	private void loadArtikelList()
	{
		ArtikelDAO adao = DAOFactory.getArtikelDAO();
		
		try {
			List<Artikel> result = adao.getAll(); 
			if(result.size() == 0){
				System.out.println("Nothing Found!");
			} else {
				data = new Object[result.size()][5];
				int i = 0;
				for(Artikel art:result){
					data[i][0] = art.getArtikelnr();
					data[i][1] = art.getKurzbezeichnung();
					
					switch(art.getKategorie().charAt(0))
					{
						case('0'): data[i][2]="T_Shirt"; 	break;
						case('1'): data[i][2]="Poster";		break;
						case('2'): data[i][2]="CD/LP";		break;
						case('3'): data[i][2]="Video/DVD";	break;
						case('4'): data[i][2]="Sonstiges";	break;
					}
					data[i][3] = art.getBeschreibung();
					data[i][4] = art.getPreis();
					i++;
				}
				tModel = new NoEditTableModel(data,colnames);
		    	tb_articles.setModel(tModel);
		    	
		    	listToSelected = new int[result.size()+1];
			}
		} catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		
		if(lst_chosen.getSelectedIndex() != -1 ) {
			btn_remove.setEnabled(true);
		}
		else{
			btn_remove.setEnabled(false);
		}
			
		if(tb_articles.getSelectedRow() != -1 && tb_articles.getValueAt(tb_articles.getSelectedRow(),0) !=null && !tb_articles.getValueAt(tb_articles.getSelectedRow(),0).equals("")) {
			

			ArtikelDAO adao = DAOFactory.getArtikelDAO();
			Artikel art = adao.get((Integer)tb_articles.getValueAt(tb_articles.getSelectedRow(),0));
			btn_add.setEnabled(true);
			btn_add.setText(new MessageFormat(lang.getString("BTN_SELECTARTICLE_NEXTSELECTARTICLE")).format(new String[] { tb_articles.getValueAt(tb_articles.getSelectedRow(),0).toString() }));
			if(ConfigFactory.getConfig().getDefaultPanelWidth() < 700){
				btn_add.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth()/2,22));
	 		}					

		
		} else {
			btn_add.setText(lang.getString("BTN_SELECTARTICLE_NEXT"));
			btn_add.setEnabled(false);
		}
	}
	
	@Override
	public void setTrail(Trail trail) {
		this.trail = trail;
		//getData();
	}

}
