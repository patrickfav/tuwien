package gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import org.apache.log4j.Logger;

import entities.DateOption;
import entities.Peer;

public class EventOptionsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -2799548491775317310L;
	private static Logger log = Logger.getLogger(EventOptionsPanel.class);
	
	private List<DateOption> dateOptions;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

	private SpinnerDateModel dateModel;
	private JSpinner dateSpinner;

	private JButton addButton;
	private JPanel container;
	private GridBagConstraints c;

	public EventOptionsPanel(List<DateOption> dateOptions) {
		super();
		this.dateOptions = dateOptions;

		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.insets = new Insets(5, 10, 5, 10);

		container = new JPanel();
		container.setLayout(new GridBagLayout());
		
		paintOptions();
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		this.add(container, c);

		// Add Option
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		dateModel = new SpinnerDateModel();
		dateSpinner = new JSpinner(dateModel);
		((JSpinner.DefaultEditor) dateSpinner.getEditor()).getTextField()
				.setColumns(10);
		this.add(dateSpinner, c);

		// Add Button
		c.gridx = 1;
		c.gridy = 1;
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		addButton.setActionCommand("addOption");
		this.add(addButton, c);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("addOption")) {
			log.debug("Date Spinner: " + dateSpinner.getValue().toString());
			DateOption newDateOption = new DateOption();
			newDateOption.setDate((Date)dateSpinner.getValue());
			dateOptions.add(newDateOption);
		} else {
			log.debug("Command: " + Integer.valueOf(command) + " " + dateOptions.size() );
			dateOptions.remove(Integer.parseInt(command));
		}
		paintOptions();
	}
	
	public void paintOptions() {
		container.removeAll();
		// Display Options
		for (int i = 0; i < dateOptions.size(); i++) {
			c.gridx = 0;
			c.gridy = i;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			DateOption dateoption = dateOptions.get(i);
			JLabel option = new JLabel(sdf.format(dateoption.getDate()));
			container.add(option, c);
			
			c.gridx = 1;
			c.gridy = i;
			
			if(dateoption.getPeersForDate().size() > 0) {
				String peers = "(";
				for(Peer p : dateoption.getPeersForDate()) {
					peers += p.getName() + ", ";
				}
				peers = peers.substring(0, peers.length()-2) + ")";
				JLabel peerLabel = new JLabel(peers);
				container.add(peerLabel, c);
			} else {
				
				JButton optionButton = new JButton("X");
				optionButton.addActionListener(this);
				optionButton.setActionCommand(i + "");
				container.add(optionButton, c);
			}
		}
		container.revalidate();
		//container.repaint();
		
	}

	public List<DateOption> getDateOptions() {
		return dateOptions;
	}

	public void setDateOptions(List<DateOption> dateOptions) {
		this.dateOptions = dateOptions;
	}

}
