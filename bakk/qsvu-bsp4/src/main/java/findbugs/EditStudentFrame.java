package findbugs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import at.ac.tuwien.ifs.bpse.basis.helper.Constants;
import at.ac.tuwien.ifs.bpse.dao.IStudentDAO;
import at.ac.tuwien.ifs.bpse.domain.Student;

/**
 * The EditStudentFrame provides functionality for editing and creating
 * Students. It utilizes a JPanel with a ContentPane and a BorderLayout. The
 * CenterPanel contains the DataFields and the SouthPanel all the Buttons.
 * 
 * @author The SE-Team
 * @version 1.2
 */
public class EditStudentFrame extends JFrame implements ActionListener {
	/**
	 * Default serialVersionUID, generated by eclipse.
	 */
	private static final long serialVersionUID = 7321985214545293077L;

	/**
	 * Retrieves the logger for this class.
	 */
	private static Log log = LogFactory.getLog(EditStudentFrame.class);

	/**
	 * The Data Access Object for Students.
	 */
	private IStudentDAO studentDAO;

	/**
	 * The to-be-edited Student.
	 */
	private Student student;

	/**
	 * The XML Bean Factory from Spring.
	 */
	private XmlBeanFactory xbf;

	/**
	 * The list of ActionListeners for this Frame.
	 */
	private List<ActionListener> updateListeners;

	/**
	 * Enumeration of different Modes the EditStudentFrame can be used with.
	 * Possible modes are:
	 * <ul>
	 * <li><code>Create</code> for creating a new Student.</li>
	 * <li><code>Update</code> for updating an existing Student.</li>
	 * </ul>
	 * 
	 * @author The SE-Team
	 * @see EditStudentFrame#EditStudentFrame(Student, Mode)
	 */
	public enum Mode {
		/**
		 * Use this mode to create a Student.
		 */
		Create,

		/**
		 * Use this mode to update a Student.
		 */
		Update,
	};

	/**
	 * The current mode of operation, gets set in constructor.
	 * 
	 * @see EditStudentFrame#EditStudentFrame(Student, Mode)
	 */
	private Mode mode;

	/**
	 * Textfield for the Database-assigned ID.
	 */
	private JLabel idLabel;

	/**
	 * Textfields for StudentData.
	 */
	private RegexTextField txtMatrNr, txtVorname, txtNachname, txtEmail;

	/**
	 * associated Regexes.
	 */
	private final static String regexMatrNr = "\\d{7}", regexVorname = ".+",
			regexNachname = ".+",
			regexEmail = ".{2,}@[a-zA-Z0-9\\.-]+\\.[a-z]{2,4}";

	/**
	 * The "Action"-Button.
	 */
	private JButton saveButton;

	private JPanel centerBox;

	/**
	 * Create and show EditStudentFrame for creating a new student.
	 * 
	 * @see #EditStudentFrame(Student, Mode)
	 */
	public EditStudentFrame() {
		this(null, Mode.Create);
	}
	
	/**
	 * Create and show EditStudentFrame with the given student.
	 * 
	 * @param student
	 *            The Student to show.
	 * @param mode
	 *            Create or Update. Create is default.
	 * @see Mode
	 * @since 1.1
	 */
	public EditStudentFrame(Student student, Mode mode) {
		super();
		this.mode = mode;
		initDAO();
		log.info("Initializing EditStudentFrame");
		this.student = student;
		// Handler for WindowClosing
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				super.windowClosing(arg0);
				closeFrame();
			}
		});
		initModels();
		initComponents();
	}

	/**
	 * Adds ActionListener to be notified when the database has been changed.
	 * The component's <code>actionPerformed()</code> method will be invoked
	 * on database changes.
	 * 
	 * @param l
	 *            the ActionListener to be added
	 * @since 1.1
	 */
	public void addUpdateListener(ActionListener l) {
		updateListeners.add(l);
	}

	/**
	 * Notify all suscribers of the database-change event. UpdateListeners have
	 * to register via <code>addUpdateListener()</code> in order to be
	 * notified.
	 * 
	 * @see #addUpdateListener(ActionListener)
	 * @since 1.1
	 */
	private void notifyUpdateListener() {
		ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"Save");
		for (ActionListener l : updateListeners) {
			l.actionPerformed(evt);
		}
	}

	/**
	 * Sets a Log Marker and calls <code>this.dispose()</code>.
	 */
	private void closeFrame() {
		log.info("Close EditStudentFrame");
		this.dispose();
	}

	/**
	 * This method is called once by the constructor to initialize the
	 * components in the Model.
	 */
	private void initComponents() {
		// InitFrame
		switch (this.mode) {
		case Create:
			setTitle("Create new Student");
			break;
		case Update:
			setTitle("Update Student \"(" + student.getId() + ") "
					+ student.getFirstname() + " " + student.getLastname() + "\"");
			break;
		}
		// Fill Frame
		JPanel mainPanel = new JPanel();
		getContentPane().add(mainPanel);
		mainPanel.setLayout(new BorderLayout(5, 5));
		// CenterPanel contains DataFields
		centerBox = new JPanel();
		centerBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		centerBox.setLayout(new GridLayout(0, 2, 5, 5));
		// ID
		// TODO: verenglischen?
		idLabel = new JLabel("<assigned from DB>");
		if (this.mode != Mode.Create) {
			JLabel lblId = new JLabel("Id:");
			centerBox.add(lblId);
			//idLabel.setEditable(false);
			centerBox.add(idLabel);
		}
		// Matrikel Number
		JLabel lblMatrNr = new JLabel("Matrikelnummer:");
		centerBox.add(lblMatrNr);
		txtMatrNr = new RegexTextField("");
		txtMatrNr.setRegex(regexMatrNr);
		txtMatrNr.setValidationIndicator(lblMatrNr);
		centerBox.add(txtMatrNr);
		// First Name
		JLabel lblVorname = new JLabel("Vorname:");
		centerBox.add(lblVorname);
		txtVorname = new RegexTextField("");
		txtVorname.setRegex(regexVorname);
		txtVorname.setValidationIndicator(lblVorname);
		centerBox.add(txtVorname);
		// Last Name
		JLabel lblNachname = new JLabel("Nachname:");
		centerBox.add(lblNachname);
		txtNachname = new RegexTextField("");
		txtNachname.setRegex(regexNachname);
		txtNachname.setValidationIndicator(lblNachname);
		centerBox.add(txtNachname);
		// Email
		JLabel lblEmail = new JLabel("Email:");
		centerBox.add(lblEmail);
		txtEmail = new RegexTextField("");
		txtEmail.setRegex(regexEmail);
		txtEmail.setValidationIndicator(lblEmail);
		centerBox.add(txtEmail);

		// Fill with data
		if (mode != Mode.Create) {
			idLabel.setText(student.getId()+"");
			txtMatrNr.setText(student.getMatnr());
			txtVorname.setText(student.getFirstname());
			txtNachname.setText(student.getLastname());
			txtEmail.setText(student.getEmail());
		}
		mainPanel.add(centerBox, BorderLayout.CENTER);
		// SouthPanel contains the Buttons
		JPanel south = new JPanel();
		saveButton = new JButton();
		switch (mode) {
		case Create:
			saveButton.setText("Create");
			break;
		case Update:
			saveButton.setText("Update");
			break;
		}
		saveButton.addActionListener(this);
		JButton cancel = new JButton("Close");
		cancel.addActionListener(this);
		south.add(saveButton);
		south.add(cancel);
		mainPanel.add(south, BorderLayout.SOUTH);
	}

	/**
	 * No models are used for now.
	 * 
	 */
	private void initModels() {
		// No models here
	}

	/**
	 * Initialize the Student Data Access Object <code>studentDAO</code> via
	 * the Spring Bean Factory <code>xbf</code> and the list of
	 * ActionListeners <code>updateListeners</code> listening for Database
	 * changes.
	 */
	private void initDAO() {
		ClassPathResource res = new ClassPathResource(Constants.SPRINGBEANS);
		xbf = new XmlBeanFactory(res);
		studentDAO = (IStudentDAO) xbf.getBean("StudentDAO");
		updateListeners = new ArrayList<ActionListener>();
	}

	/**
	 * Check which Action was performed and notify the UpdateListener.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		log.info("Action performed: \"" + action + "\"");
		if (action.equals("Close")) {
			closeFrame();
		} else if (action.equals("Update")) {
			if (validateInput()) {
				studentDAO
						.updateStudent(getStudentFromFrame());
				notifyUpdateListener();
			}
		} else if (action.equals("Create")) {
			if (validateInput()) {
				Student st = getStudentFromFrame();
				Student save = studentDAO.saveStudent(st);
				System.out.println("before");
				if (save != null) {
					student = st;
					this.mode = Mode.Update;
					idLabel.setText(student.getId()+"");
					System.out.println("after");
					((JButton) e.getSource()).setText("Update");
					centerBox.add(idLabel, 0);
					centerBox.add(new JLabel("ID: "), 0);
					this.repaint();
					this.pack();
				}
				notifyUpdateListener();
			}
		} 
	}

	/**
	 * Validate the data entered into the GUI.
	 * 
	 * @return true iff the data entered is valid.
	 * @see #getStudentFromFrame()
	 * @see RegexTextField#isMatching()
	 */
	private boolean validateInput() {
		return (txtMatrNr.isMatching() && txtVorname.isMatching()
				&& txtNachname.isMatching() && txtEmail.isMatching());
	}

	/**
	 * Converts the data entered into the textFields to a new Student-object.
	 * Use in combination with <code>validateInput()</code>
	 * 
	 * @return a new Student with the data from the GUI.
	 * @see #validateInput()
	 */
	private Student getStudentFromFrame() {
		Student stud = new Student();
        if(!idLabel.getText().equals("<assigned from DB>"))
            stud.setId(Integer.parseInt(idLabel.getText()));
        stud.setMatnr(txtMatrNr.getText());
		stud.setFirstname(txtVorname.getText());
		stud.setLastname(txtNachname.getText());
		stud.setEmail(txtEmail.getText());
		return stud;
	}

}
