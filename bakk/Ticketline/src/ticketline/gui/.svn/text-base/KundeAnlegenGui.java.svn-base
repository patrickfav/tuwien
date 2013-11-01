package ticketline.gui;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import ticketline.dao.hibernate.KundeDAOHibernate;
import ticketline.db.Kunde;
import org.apache.log4j.Logger;


/**
 * Costumer Create GUI
 * 
 * @author AndiS, DanielZ
 * @version 0.3
 */

public class KundeAnlegenGui extends KundeGui implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(KundeAnlegenGui.class);
	private Kunde customer;
	
	public KundeAnlegenGui()
	{	
		btn_back.setVisible(false);
		//Customize the Panel for Create Costumer
		tf_cardnumber.setEditable(false);
		setBorder(new TitledBorder(lang.getString("PNL_CUSTOMER_CREATE")));
		btn_execute.setText(lang.getString("BTN_CUSTOMER_CREATE"));		
		btn_execute.addActionListener(this);
		btn_execute.setActionCommand("execute");	
		
		logger.debug("KundeAnlegenGui Created Successfully.");
		
		//setButtonsDefault();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		reset(e);
		execute(e);
		
	}
	private void execute(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("execute")){
			customer = new Kunde();
			KundeDAOHibernate dao = new KundeDAOHibernate();
			
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
				
				dao.save(customer);
				JOptionPane.showMessageDialog(this, lang.getString("OPH_CREATECOSTUMER_SUCCESS"),lang.getString("OPT_CREATECOSTUMER_SUCCESS2"),JOptionPane.INFORMATION_MESSAGE);
				logger.info("Customer:Create:Customer created");
			}			
		}
	}
	private boolean testFields(){
		boolean ok = true;
		String justNumbersCheck = "[0-9]+";
		String justLettersCheck = "[a-zA-ZäöüßÖÄÜ\\s]+";
		String reductionCheck = "([0-9]+[.]?[0-9]*)";
		String digitsCheck = "[0-9]*";
		String emailCheck = "([a-zA-Z0-9]+[@][a-zA-Z0-9]+[.][a-z]+)*";
		String lettersCheck = "[a-zA-ZäöüßÖÄÜ\\s]*";
		
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
			return false;
		}
		logger.info("Required fields are filled");
		return true;		
	}	
}

