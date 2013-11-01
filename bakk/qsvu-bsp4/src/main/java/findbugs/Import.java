package findbugs;

import java.io.IOException;
import java.util.List;

import at.ac.tuwien.ifs.bpse.domain.Student;

/**
 * This Interface specifies methods for importing Students.
 * 
 * @author The SE-Team
 * @version 1.0
 */
public interface Import {

	/**
	 * Loads the Students from the given file. The expected file-structure is
	 * determined by the implementing class.
	 * 
	 * @param filename
	 *            The file to read from.
	 * @return A List of Stundents retrieved from the file.
	 * @throws IOException
	 *             on any IOException.
	 */
	public List<Student> read(String filename) throws IOException;

	/**
	 * Returns the used file-extension. This method is required to give the GUI
	 * components Feedback which File extension this Export Type creates.
	 * 
	 * @return file-extension, eg. <code>.xml</code>
	 */
	public String getExtension();

}

