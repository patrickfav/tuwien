package findbugs;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import at.ac.tuwien.ifs.bpse.domain.Student;


/**
 * This class can be used for importing and exporting xml-files. It uses dom4j
 * to generate/read an XML-file. This is much more flexible than using simple
 * Sting concationation (as used in the {@link HtmlExport}-Class. More
 * informaton about dom4j is available on the dom4j-website.
 *
 * @author The SE-Team
 * @version 1.0
 * @see Export
 * @see Import
 * @see <a href="http://dom4j.org" target="newWindow">dom4j-website</a>
 */
public class XmlExportImport implements Export, Import {

	/**
	 * Retrieves the logger for this class.
	 */
	private static Log log = LogFactory.getLog(XmlExportImport.class);

	/**
	 * The Document Factory instance is retrieved once.
	 */
	private final DocumentFactory documentFactory = DocumentFactory.getInstance();

	/**
	 * The Document to Export/Import.
	 */
	private Document doc = null;

	/**
	 * Default encoding in which to input/ouput text.
	 */
	private String encoding = "utf-8";

	/**
	 * Creates a new Instance. Nothing special here.
	 *
	 */
	public XmlExportImport() {
		super();
	}

	/**
	 * Used for display purposes in the UI models. Very important as it is
	 * implemented to stringify this object.
	 *
	 * @return String "XML" for this export
	 *
	 */
	@Override
    public String toString() {
		return "XML";
	}

	/**
	 * This method is required to give the GUI components Feedback which File
	 * extension this Export Type usually creates.
	 */
	public String getExtension() {
		return "xml";
	}

	/**
	 * Returns the encoding of the XML-File. eg. "utf-8"
	 *
	 * @return the encoding of the xml-file.
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * Sets the encoding of the xml-file. Xml-files can be created using
	 * different character-encodings. The most common and widely used encoding
	 * is "utf-8", which is the default value.
	 *
	 * @param encoding
	 *            the encoding if the xml-file.
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Returns the Xml-Document.
	 *
	 * @return the XML-Document.
	 */
	public Document getDocument() {
		return doc;
	}

	/**
	 * Generates Root Element of XML Document.
	 *
	 * @return root Element
	 */
	private Element generateRoot() {
		log.debug("Generate Root Element");
		doc = documentFactory.createDocument();
		Element root = doc.addElement("students");
		return root;
	}

	/**
	 * Generate XML Dom Tree in Memory from Studenten-Objects.
	 *
	 * @param studenten
	 *            List of Student Objects
	 */
	public void generateXML(List<Student> studenten) {
		log.info("Start Generating XML");
		Element rootEl = generateRoot();

		for (Student s : studenten) {
			// generate Elements
			log.debug("Generate <student> Element:");
			Element studentEl = rootEl.addElement("student");
			Element vornameEl = studentEl.addElement("firstname");
			Element nachnameEl = studentEl.addElement("lastname");
			Element matnrEl = studentEl.addElement("matnr");
			Element emailEl = studentEl.addElement("email");
			// set Element Content
			vornameEl.setText(s.getFirstname());
			nachnameEl.setText(s.getLastname());
			matnrEl.setText(s.getMatnr());
			emailEl.setText(s.getEmail());
			// Set ID as attribute
			String id = s.getId() + "";
			studentEl.addAttribute("id", id);
			log.debug("(" + id + ") " + s.getFirstname() + " " + s.getLastname()
					+ " " + s.getMatnr() + " " + s.getEmail());
		}
		log.info("Finished Generating XML");
	}

	/**
	 * Saves the XML-Document to a file.
	 *
	 * @param filename
	 *            the filename.
	 * @throws IOException
	 *             on errors while writing the file.
	 */
	public void save(String filename) throws IOException {
		log.info("Saving XML Data to file \"" + filename + "\"");
		FileOutputStream fos = new FileOutputStream(filename);
		OutputFormat outForm = OutputFormat.createPrettyPrint();
		outForm.setEncoding(encoding);
		XMLWriter xwriter = new XMLWriter(fos, outForm);
		xwriter.write(doc);
	}

	/**
	 * Reads the XML-Document from a file on the filesystem.
	 *
	 * @param filename
	 *            The file to read.
	 * @throws DocumentException
	 *             on errors while reading.
	 */
	public void readXml(String filename) throws DocumentException {
		log.info("Reading XML Data from file \"" + filename + "\"");
		SAXReader xmlReader = new SAXReader();
		doc = xmlReader.read(filename);
	}

	/**
	 * Writes the given Students to the given file. This mehtod just calls other
	 * (private) methods of this class.
	 *
	 * @see #generateXML(List)
	 * @see #save(String)
	 */
	public void write(List<Student> studenten, String filename)
			throws IOException {
		generateXML(studenten);
		save(filename);
	}

	/**
	 * This method reads a List of Students from a xml-file. It uses a
	 * dom4j-Document which provides easy to use methods for accessing the
	 * elements of the XML-Tree.
	 *
	 * @see #readXml(String)
	 */
	public List<Student> read(String filename) throws IOException {
		try {
			readXml(filename);
		} catch (DocumentException e) {
			log.error("" + e);
			throw new IOException("" + e);
		}
		List<Student> studenten = new ArrayList<Student>();
		List<?> studentenEl = doc.getRootElement().elements();
		for (Object o: studentenEl) {
			Element e = (Element) o;
			Student s = new Student();
			s.setId(new Integer(e.attributeValue("id")));
			s.setFirstname(e.elementText("firstname"));
			s.setLastname(e.elementText("lastname"));
			s.setEmail(e.elementText("email"));
			s.setMatnr(e.elementText("matnr"));
			studenten.add(s);
		}
		return studenten;
	}

}
