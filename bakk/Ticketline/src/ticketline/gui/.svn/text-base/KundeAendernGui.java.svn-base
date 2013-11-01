package ticketline.gui;


import java.awt.Color;
import java.awt.Component;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import ticketline.bl.GuiMemory;
import ticketline.bl.ITrail;
import ticketline.bl.Trail;
import ticketline.dao.DAOFactory;
import ticketline.dao.hibernate.KundeDAOHibernate;
import ticketline.dao.interfaces.KundeDAO;
import ticketline.db.Kunde;


/**
 * Costumer Edit GUI
 * 
 * @author AndiS, PatrickF, DanielZ, ReneN
 * @version 0.3
 */

public class KundeAendernGui extends KundeGui implements ActionListener,ITrail{
	
	private static final long serialVersionUID = 1L;
	private KundeDAOHibernate dao = new KundeDAOHibernate();
	private KundeDAO kDAO = DAOFactory.getKundeDAO();
	private Kunde customer;
	
	public KundeAendernGui()
	{
		btn_execute.addActionListener(this);
		btn_execute.setActionCommand("edit");
		btn_back.setActionCommand("back");
		btn_back.addActionListener(this);
		
		reloadChanges();
		
		logger.debug("KundeAendernGui Created Successfully.");

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		execute(e);
	}
	private void execute(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("edit")){			
			customer = kDAO.get(new Integer(tf_cardnumber.getText()));
			
			if(testFields()){ //test if all required fields are filled and then save all												
				customer.setTyp("K");
				customer.setErmaechtigung(false);
				customer.setGruppe("Standard");
				customer.setGesperrt(false);
				customer.setTkgueltigbis(new Date());				
				if(rb_gender_male.getSelectedObjects() == null){				
					customer.setGeschlecht("W");
				}
				else{
					customer.setGeschlecht("M");				
				}

				dao.update(customer);
				JOptionPane.showMessageDialog(this, lang.getString("OPH_EDITCOSTUMER_SUCCESS"),lang.getString("OPT_EDITCOSTUMER_SUCCESS2"),JOptionPane.INFORMATION_MESSAGE);
				logger.info("Customer edited");
			}			
		}		
		if(e.getActionCommand().equals("back")){
			Gui.load((Component)trail.getPrevious(this));
			setVisible(true);
		}
	}
	private boolean testFields(){
		boolean ok = true;
		String justNumbersCheck = "[0-9]+";
		String justLettersCheck = "[a-zA-Z-‰ˆ¸ﬂ÷ƒ‹ÈË\\s]+";
		String reductionCheck = "([0-9]+([.][0-9]+)?)";
		String digitsCheck = "[0-9]*";
		String emailCheck = "([a-zA-Z0-9]+[@][a-zA-Z0-9]+[.][a-z]+)*";
		String lettersCheck = "[a-zA-Z‰ˆ¸ﬂ÷ƒ‹,\\-\\s]*";
		
		if(tf_firstname.getText().matches(justLettersCheck)){
			customer.setVname(tf_firstname.getText().trim());
			tf_firstname.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray)); //set border to previous state			
		} 
		else{ 
			tf_firstname.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red)); //change border to show that field is required
			ok = false;
		}
		if(tf_surname.getText().matches(justLettersCheck)){
			customer.setNname(tf_surname.getText().trim());
			tf_surname.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));			
		} 
		else{ 
			tf_surname.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red)); 
			ok = false;
		}
		if(tf_plz.getText().trim().matches(digitsCheck)){
			customer.setPlz(tf_plz.getText().trim());
			tf_plz.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		}
		else{ 
			tf_plz.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red)); 
			ok = false;
		}
		if(tf_phone.getText().matches(digitsCheck)){
			customer.setTelnr(tf_phone.getText().trim());
			tf_phone.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		}
		else{
			ok = false;
			tf_phone.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red));
		}
		if(tf_mail.getText().matches(emailCheck)){
			customer.setEmail(tf_mail.getText());
			tf_mail.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		}
		else{
			ok = false;
			tf_mail.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red));
		}
		if(tf_account.getText().matches(justNumbersCheck)){
			customer.setKontonr(tf_account.getText());
			tf_account.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		}
		else{
			ok = false;
			tf_account.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red));
		}
		if(tf_balance.getText().matches(justNumbersCheck)){
			customer.setKontostand(new java.math.BigDecimal(tf_balance.getText()));
			tf_balance.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		}
		else{
			ok = false;
			tf_balance.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red));
		}
		if(tf_reduction.getText().matches(reductionCheck)){
			customer.setErmaessigung(new java.math.BigDecimal(tf_reduction.getText()));
			tf_reduction.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		}
		else{
			tf_reduction.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red)); 
			ok = false;
		}
		if(jdc_birthday.getDate() != null && jdc_birthday.getDate().before(new Date())){
			Date birthDate = jdc_birthday.getDate();				
			customer.setGeburtsdatum(birthDate);
			jdc_birthday.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		}
		else{
			jdc_birthday.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red)); 
			ok = false;
		}
		if(jdc_validDate.getDate() != null && jdc_validDate.getDate().after(new Date())){
			Date validDate = jdc_validDate.getDate();
			customer.setKkgueltigbis(validDate);
			jdc_validDate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		}
		else{			
			jdc_validDate.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red)); 
			ok = false;
		}		
		if(tf_street.getText().matches(lettersCheck)){
			tf_street.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			customer.setStrasse(tf_street.getText().trim());
		}
		else{
			ok = false;
			tf_street.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red)); 
		}
		if(tf_location.getText().matches(lettersCheck)){
			tf_location.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			customer.setOrt(tf_location.getText().trim());
		}
		else{
			ok = false;
			tf_location.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red)); 
		}
		if(tf_preferences.getText().matches(lettersCheck)){
			tf_preferences.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			customer.setVorlieben(tf_preferences.getText().trim());
		}
		else{
			ok = false;
			tf_preferences.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red)); 
		}
		
		if(!ok){
			JOptionPane.showMessageDialog(this, lang.getString("OPH_CREATECOSTUMER_INPUTERROR"),lang.getString("OPT_CREATECOSTUMER_INPUTERROR_NUMBER"),JOptionPane.WARNING_MESSAGE);
			logger.debug("One/Some field(s) is/are not filled.");
			return false;
		}		
		logger.info("Required fields are filled");
		return true;	
		
	}

	@Override
	public void dummySearch() {
		// TODO Auto-generated method stub		
	}

	@Override
	public void reloadChanges() {
		try{
			setDefaultButton();
			//retrieving costumer from gui memory
			if(GuiMemory.isKundeSet()) {
				k = GuiMemory.getKunde();
				tf_cardnumber.setText(k.getKartennr().toString());
				tf_firstname.setText(k.getVname());
				tf_surname.setText(k.getNname());
				rb_gender_male = new JRadioButton(lang.getString("RB_CUSTOMER_MALE"),true);
				rb_gender_female = new JRadioButton(lang.getString("RB_CUSTOMER_FEMALE"),false);
				jdc_birthday.setDate(k.getGeburtsdatum());
				tf_street.setText(k.getStrasse());
				tf_plz.setText(k.getPlz());
				tf_mail.setText(k.getEmail());
				tf_location.setText(k.getOrt());
				tf_phone.setText(k.getTelnr());
				tf_account.setText(k.getKontonr());
				tf_balance.setText(k.getKontostand().toString());
				tf_reduction.setText(k.getErmaessigung().toString());
				jdc_validDate.setDate(k.getKkgueltigbis());
				tf_preferences.setText(k.getVorlieben());
			}
		}catch(Exception e){
			logger.warn("No customer found");
		}
		
		//Customize the Panel for Edit Costumer
		setBorder(new TitledBorder(lang.getString("PNL_CUSTOMER_EDIT")));
		tf_cardnumber.setEditable(false);
		btn_reset.setVisible(false);
		btn_execute.setText(lang.getString("BTN_SEARCHCUSTOMER_SAVE"));
	}
	@Override
	public void setTrail(Trail trail) {
		this.trail = trail;
	}
	public void setDefaultButton(){
		tf_cardnumber.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		tf_firstname.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		tf_surname.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		rb_gender_male.setSelected(true);
		rb_gender_female.setSelected(false);
		tf_street.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		tf_plz.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		tf_mail.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		tf_location.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		tf_phone.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		tf_account.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		tf_balance.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		tf_reduction.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		tf_preferences.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		jdc_birthday.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		jdc_validDate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		
	}
}