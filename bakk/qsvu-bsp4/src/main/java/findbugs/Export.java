package findbugs;

import java.io.IOException;
import java.util.List;

import at.ac.tuwien.ifs.bpse.domain.Student;



/**
 * This interface specifies methods for exporting Students.
 * 
 * @author The SE-Team
 * @version 1.0
 */
public interface Export {

	/**
	 * Writes the given Students to the given file. The file-structure is
	 * determined by the implementing class.
	 * 
	 * @param studenten
	 *            The <code>Student</code>s to export.
	 * @param filename
	 *            The destination-file.
	 * @throws IOException
	 *             on error while writing the file.
	 */
	public void write(List<Student> studenten, String filename)
			throws IOException;

	/**
	 * Returns the used file-extension. This method is required to give the GUI
	 * components feedback which file extension this Export Type will create.
	 * 
	 * @return file-extension, eg. <code>xml</code>
	 */
	public String getExtension();

}