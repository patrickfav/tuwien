package findbugs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.ac.tuwien.ifs.bpse.domain.Student;


/**
 * Simple implementation of the Export-Interface writing to a HTML-File.
 * <b>Warning:</b> The implementation of HTML Generation in this class is only
 * for extremly simple test purposes!! In a real-world project you would
 * <i>never</i> generate HTML using String concatenation because this is far
 * too error prone and unstable!<br>
 * You could use template engines like Apache Velocity. This was not done here
 * for keeping the code as simple and understandable as possible.
 * 
 * @author The SE-Team
 * @version 1.0
 * @see Export
 */
public class HtmlExport implements Export {

	/**
	 * Retrieves the logger for this class.
	 */
	private static Log log = LogFactory.getLog(HtmlExport.class);

	/**
	 * Start of html document (header...).
	 */
	private String html_start;

	/**
	 * End of html document.
	 */
	private String html_end;

	/**
	 * Actual content of html document.
	 */
	private StringBuffer html_body;

	/**
	 * Title of document.
	 */
	private String title;

	/**
	 * Creates a new instance, nothing special here.
	 */
	public HtmlExport() {
		super();
	}

	/**
	 * Used for display purposes in the UI models. Very important as it is
	 * implemented to stringify this object
	 * 
	 * @return String "HTML" for this export
	 * 
	 */
	public String toString() {
		return "HTML";
	}

	/**
	 * This method is required to give the GUI components Feedback which File
	 * extension this Export Type creates.
	 * 
	 * @return String "html" for this export
	 */
	public String getExtension() {
		return "html";
	}

	/**
	 * Returns the Title of the HTML-File.
	 * 
	 * @return The content of the title-tag.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the Title of the HTML-File.
	 * 
	 * @param title
	 *            The new content of the title-tag.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Initialise Document (generate new one).
	 */
	private void newDoc() {
		html_start = "<html>\n<head>\n<title>" + title + "</title>\n</head>\n"
				+ "<body>\n";
		html_body = new StringBuffer();
		html_end = "\n</body>\n</html>";
	}

	/**
	 * Render html code for one student (actually one table row).
	 * 
	 * @param s
	 *            Student object
	 * @return html code of one Student data (table row)
	 */
	private String renderStudent(Student s) {
		log.debug("Rendering Student \"" + s.getFirstname() + " "
				+ s.getLastname() + "\"");
		StringBuffer h = new StringBuffer();
		h.append("<tr>\n");
		h.append("<td>" + s.getId() + "</td>\n");
		h.append("<td>" + s.getFirstname() + " " + s.getLastname() + "</td>\n");
		h.append("<td>" + s.getMatnr() + "</td>\n");
		h.append("<td>" + s.getEmail() + "</td>\n");
		h.append("</tr>\n");
		return h.toString();
	}

	/**
	 * Export list of students to html document. This is the implemented Method
	 * from the Export interface.
	 * 
	 * @param studenten
	 *            List of students
	 * @param filename
	 *            filename of html document to be generated
	 */
	public void write(List<Student> studenten, String filename)
			throws IOException {
		log.info("Exporting " + studenten.size() + " Students to HTML \""
				+ filename + "\"");
		newDoc();
		html_body.append("<table width=\"75%\" border=\"1\">\n");
		html_body.append("<tr>\n");
		html_body
				.append("<th>ID</th>\n<th>Name</th>\n<th>Matrikelnummer</th>\n<th>Email</th>\n");
		html_body.append("</tr>\n");
		for (Student s: studenten) {
			html_body.append(renderStudent(s));
		}
		html_body.append("</table>");
		log.debug("Writing HTML File");
		FileWriter fw = new FileWriter(filename);
		fw.write(html_start + html_body.toString() + html_end);
		fw.close();
		log.info("Finished Export");
	}

}

