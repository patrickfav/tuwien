package util.xml;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import entities.TrialForm;

public interface IXMLImportExport {
	
	public static final String XML_FILENAME = "documentation.xml";

	/**
	 * Takes a TrialForm and writes it to the given outputstream as XML
	 * 
	 * @param tf - The TrialForm to be marshalled to XML
	 * @param os - the OutputStream the XML should be written to
	 * @throws XmlImportExportException if the export fails
	 */
	public void writeTrialFormToXml(TrialForm tf, OutputStream os) throws XmlImportExportException;
	
	/**
	 * Takes a TrialForm and writes it to the given outputstream as ZIP
	 * 
	 * @param tf - The TrialForm to be marshalled to ZIP
	 * @param os - the OutputStream the ZIP should be written to
	 * @throws XmlImportExportException if the export fails
	 */
	public void writeTrialFormToZip(TrialForm tf, OutputStream os) throws XmlImportExportException;
	
	/**
	 * reads in an xml file and parses its content into a TrialForm Entity Graph
	 * 
	 * @param xmlFile - the file to read
	 * @return TrialForm - a new TrialForm entity graph
	 * @throws XmlImportExportException - if the import fails
	 */
	public TrialForm readTrialFormFromXml(File xmlFile) throws XmlImportExportException;
	
	/**
	 * reads in an zip file and parses its content into a TrialForm Entity Graph
	 * 
	 * @param zipFile - the file to read
	 * @return TrialForm - a new TrialForm entity graph
	 * @throws XmlImportExportException - if the import fails
	 */
	public TrialForm readTrialFormFromZip(File zipFile) throws XmlImportExportException;
	
	public TrialForm readTrialFormFromZip(InputStream inputStream) throws XmlImportExportException;
}
