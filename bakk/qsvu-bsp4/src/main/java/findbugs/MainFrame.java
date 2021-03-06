package findbugs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import at.ac.tuwien.ifs.bpse.basis.export_import.Export;
import at.ac.tuwien.ifs.bpse.basis.helper.Constants;
import at.ac.tuwien.ifs.bpse.dao.IStudentDAO;
import at.ac.tuwien.ifs.bpse.domain.Student;

/**
 * The MainFrame is, as the name says, the main frame of this application. It
 * utilizes a JPanel with a ContentPane and a BorderLayout.
 * 
 * @author The SE-Team
 * @version 1.1
 */
public class MainFrame extends JFrame implements ActionListener {
	/**
	 * serialVersionUID, generated by eclipse.
	 */
	private static final long serialVersionUID = -7167629968906183715L;

	/**
	 * Retrieves the logger for this class.
	 */
	private static Log log = LogFactory.getLog(MainFrame.class);

	/**
	 * The Student Table Model, default order is Matrikel Number.
	 * 
	 * @see #initModels()
	 */
	private StudentenTableModel studentenTM;

	/**
	 * The Table to display the Students from the Database.
	 */
	private JTable table;

	/**
	 * Holds the item to be exported.
	 */
	private ComboBoxModel exportComboModel;

	private IStudentDAO studentDAO = null;

	/**
	 * The XML Bean Factory from Spring.
	 * 
	 * @see #initDAO()
	 */
	private XmlBeanFactory xbf;

	/**
	 * The Buttons for editing and deleting Students.
	 */
	private JButton editButton, deleteButton;

	/**
	 * The default Constructor for the MainFrame, initiating the DAO, the Models
	 * and the Conponents. Additionally it defines a ActionListener to clean up
	 * before exit.
	 * 
	 * @see #terminateApplication()
	 */
	public MainFrame() {
		super();
		initDAO();
		log.info("Initialising MainFrame");
		// Window Listener for Closing Frame
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				super.windowClosing(arg0);
				terminateApplication();
			}
		});
		initModels();
		initComponents();
	}

	/**
	 * Initializes the DAOs. Loads the XmlBean.
	 */
	private void initDAO() {
		ClassPathResource res = new ClassPathResource(Constants.SPRINGBEANS);
		xbf = new XmlBeanFactory(res);
		studentDAO = (IStudentDAO) xbf.getBean("StudentDAO");
	}

	/**
	 * Initializes the Models. Creates the StudentenTableModel (for the Table)
	 * and loads the ExportComboModel from the XmlBean.
	 * 
	 * @see StudentenTableModel
	 * @see ExportComboModel
	 */
	private void initModels() {
		studentenTM = new StudentenTableModel("Matrikelnummer");
		exportComboModel = (ComboBoxModel) xbf.getBean("ExportComboModel");
	}

	/**
	 * Initializes all the components of the GUI.
	 */
	private void initComponents() {
		// define main panel
		JPanel mainPanel = new JPanel();
		getContentPane().add(mainPanel);
		mainPanel.setLayout(new BorderLayout(5, 5));
		// define north, center and south panel
		// a JToolBar is actually very similar to a panel; just a little bit
		// more flexible
		JToolBar northPanel = new JToolBar();
		northPanel.setFloatable(false);
		northPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 10));
		JPanel southPanel = new JPanel();
		JPanel eastPanel = new JPanel(new BorderLayout());
		JPanel eastNorthPanel = new JPanel(new GridLayout(0, 1, 5, 5));
		eastPanel.add(eastNorthPanel, BorderLayout.NORTH);
		eastPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
		mainPanel.add(eastPanel, BorderLayout.EAST);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		// define Label for Drop Down Field in North Panel
		northPanel.add(new JLabel("Sortiert nach "));
		// define Drop Down Field in North Panel
		JComboBox selectOrderCB = new JComboBox();
		selectOrderCB.setEditable(false);
		selectOrderCB.addItem("Matrikelnummer");
		selectOrderCB.addItem("Nachname");
		northPanel.add(selectOrderCB);
		selectOrderCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				if (ie.getStateChange() == ItemEvent.SELECTED) {
					log.debug("Order Combo State Changed: " + ie.getItem());
					sortOrderChanged(ie.getItem().toString());
				}
			}
		});
		// define label for export
		northPanel.addSeparator(new Dimension(20, 5));
		northPanel.add(new JLabel("Exportieren nach"));
		JComboBox exportCB = new JComboBox();
		exportCB
				.setToolTipText("Exportformat auswaehlen, danach <Export> druecken");
		
		exportCB.setModel(exportComboModel);
		northPanel.add(exportCB);
		northPanel.addSeparator(new Dimension(5, 5));
		JButton exportBtn = new JButton("Export");
		exportBtn.setToolTipText("Exportieren der Daten in eine Datei");
		exportBtn.addActionListener(this);
		northPanel.add(exportBtn);
		// define table in center
		table = new JTable();
		table.setModel(studentenTM);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setShowGrid(true);
		table.setGridColor(Color.LIGHT_GRAY);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				updateButtonStatus();
				if (e.getClickCount() >= 2) {
					int row = table.getSelectedRow();
					if (row > -1) {
						editStudent();
					}
				}
			}
		});
		table.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				updateButtonStatus();
			}

			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_DELETE) {
					deleteStudent();
				}
			}
		});
		JScrollPane tableScrollPane = new JScrollPane(table);
		tableScrollPane.setWheelScrollingEnabled(true);
		centerPanel.add(tableScrollPane);
		// define Exit Button in South Panel
		JButton exitButton = new JButton("Exit");
		exitButton.setToolTipText("Programm beenden");
		southPanel.add(exitButton);
		exitButton.addActionListener(this);

		editButton = new JButton("Edit");
		editButton.setEnabled(false);
		editButton.addActionListener(this);
		eastNorthPanel.add(editButton);
		JButton createButton = new JButton("Create");
		createButton.addActionListener(this);
		eastNorthPanel.add(createButton);
		deleteButton = new JButton("Delete");
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(this);
		eastNorthPanel.add(deleteButton);

	}

	private void updateButtonStatus() {
		if (table.getSelectedRowCount() == 1) {
			editButton.setEnabled(true);
			deleteButton.setEnabled(true);
		} else {
			editButton.setEnabled(false);
			deleteButton.setEnabled(false);
		}
	}

	private void placeNewFrame(JFrame frame) {
		frame.pack();

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Point p = this.getLocation();
		// First try:
		// new Window right next to this one:
		if (screen.width > (this.getLocation().x + this.getWidth() + frame
				.getWidth())) {
			p.x += this.getWidth();
		}
		// Second try:
		// new Window left of this one:
		else if (this.getLocation().x - frame.getWidth() >= 0) {
			p.x -= frame.getWidth();
		}
		// Fallthrough:
		// Place centered over this frame
		else {
			p.x += (this.getWidth() - frame.getWidth()) / 2;
			p.y += (this.getHeight() - frame.getHeight()) / 2;
		}
		frame.setLocation(p);
	}

	public void actionPerformed(ActionEvent ae) {
		String cmd = ae.getActionCommand();
		log.debug("Action Performed \"" + cmd + "\"");
		if (cmd.equals("Exit")) {
			terminateApplication();
		} else if (cmd.equals("Export")) {
			export();
		} else if (cmd.equals("Create")) {
			createStudent();
		} else if (cmd.equals("Edit")) {
			editStudent();
		} else if (cmd.equals("Delete")) {
			deleteStudent();
		}
	}

	// Business Methods

	/**
	 * Handle a create-request from the GUI
	 */
	private void createStudent() {
		EditStudentFrame esf = new EditStudentFrame();
		placeNewFrame(esf);
		esf.pack();
		esf.addUpdateListener(new UpdateListener());
		esf.setVisible(true);
	}

	/**
	 * Handle a edit-request from the GUI
	 *
	 */
	private void editStudent() {
		int row = table.getSelectedRow();
		if (row > -1) {
			EditStudentFrame esf = new EditStudentFrame(studentenTM
					.getStudentAt(row), EditStudentFrame.Mode.Update);
			placeNewFrame(esf);
			esf.pack();
			esf.addUpdateListener(new UpdateListener());
			esf.setVisible(true);
		}
	}

	/**
	 * Handle a delete-request from the GUI
	 *
	 */
	private void deleteStudent() {
		int row = table.getSelectedRow();
		if (row > -1) {
			Student victim = studentenTM.getStudentAt(row);
			int yesno = JOptionPane.showConfirmDialog(this, "Student \""
					+ victim.getFirstname() + " " + victim.getLastname() + " ("
					+ victim.getMatnr() + ")\"" + " wirklich l�schen?", "Delete",
					JOptionPane.YES_NO_OPTION);
			if (yesno == 0) {
				if (studentDAO.deleteStudent(victim.getId())) {
					studentenTM.reload();
					JOptionPane.showMessageDialog(this, "L�schen erfolgreich!");
				} else {
					JOptionPane.showMessageDialog(this, "Fehler beim l�schen");
				}
			}
		}
	}

	/**
	 * Clean up and exit
	 */
	private void terminateApplication() {
		log.info("Closing MainFrame and Exit");
		System.exit(0);
	}

	/**
	 * Change order and reload
	 * 
	 * @param order
	 *            the new order
	 * @see StudentenTableModel#setOrder(String)
	 */
	private void sortOrderChanged(String order) {
		log.info("Sort Order Changed to \"" + order + "\"");
		studentenTM.setOrder(order);
	}

	/**
	 * Export current view to XML or HTML file. Before saving a
	 * FileSelect-Dialog is shown to choose a filename and a location to save.
	 */
	private void export() {
		Export export = (Export) exportComboModel.getSelectedItem();
		// Create a file dialog to choose filename for export
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Filenamen fuer Export eingeben");
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = jfc.showSaveDialog(this);
		// check if Export was confirmed or canceled by user
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String filename = jfc.getSelectedFile().getPath();
			String extension = export.getExtension();
			if (!extension.startsWith(".")) {
				extension = "." + extension;
			}
			if (!filename.endsWith(extension)) {
				filename += extension;
			}
			log.info("Filename for export = \"" + filename + "\"");
			try {
				export.write(studentenTM.getStudenten(), filename);
				JOptionPane.showMessageDialog(this,
						"Daten wurden erfolgreich exportiert!", "File Export",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this,
						"Beim Export ist ein Fehler aufgetreten!",
						"File Export", JOptionPane.WARNING_MESSAGE);
				log.error("File Writing Error: " + e);
			}
		}

	}

	/**
	 * UpdateListener waits for database-changes and udates the table.
	 * 
	 * @author The SE-Team
	 * @see EditStudentFrame#addUpdateListener(ActionListener)
	 * @since 1.1
	 */
	class UpdateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			log.info("Update studentTM");
			studentenTM.reload();
		}
	}

}
