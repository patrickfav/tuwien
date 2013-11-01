package util.xml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import util.MessageUtilsBean;
import entities.Attribute;
import entities.AttributeGroup;
import entities.Image;
import entities.TrialForm;
import entities.constraint.DateConstraint;
import entities.constraint.DecimalConstraint;
import entities.constraint.IntegerConstraint;
import entities.constraint.StringConstraint;
import entities.formelement.CheckBox;
import entities.formelement.DatePicker;
import entities.formelement.DropDown;
import entities.formelement.FPRSScale;
import entities.formelement.FileUpload;
import entities.formelement.RadioButtons;
import entities.formelement.TextArea;
import entities.formelement.TextField;
import entities.formelement.VASScale;
import entities.formelement.VancouverScarScale;

public class XMLImportExport implements IXMLImportExport {

	public static final Class<?>[] CLASSES_TO_BE_BOUND_TF = { TrialForm.class,
			CheckBox.class, DateConstraint.class, DatePicker.class,
			DecimalConstraint.class, DropDown.class, FileUpload.class,
			FPRSScale.class, IntegerConstraint.class,
			entities.formelement.List.class, StringConstraint.class,
			TextField.class, TextArea.class, VancouverScarScale.class,
			VASScale.class, RadioButtons.class };

	public TrialForm readTrialFormFromXml(File xmlFile)
			throws XmlImportExportException {
		JAXBContext ctx;
		try {
			ctx = JAXBContext.newInstance(CLASSES_TO_BE_BOUND_TF);
			Unmarshaller um = ctx.createUnmarshaller();
			return (TrialForm) um.unmarshal(xmlFile);

		} catch (JAXBException e) {
			throw new XmlImportExportException(e);
		}
	}

	// doesn't handle images!
	public TrialForm readTrialFormFromZip(InputStream inputStream)
			throws XmlImportExportException {
		
		TrialForm trialForm = null;
		
		ZipInputStream zipStream = new ZipInputStream(inputStream);
		
		ZipEntry entry = null;
		try {
			while(null != (entry = zipStream.getNextEntry())){
				
				if (IXMLImportExport.XML_FILENAME.equals(entry.getName())){
					JAXBContext ctx;
					try {
						ctx = JAXBContext.newInstance(CLASSES_TO_BE_BOUND_TF);
						Unmarshaller um = ctx.createUnmarshaller();
						trialForm = (TrialForm) um.unmarshal(zipStream);
						break;
					} catch (JAXBException e) {
						throw new XmlImportExportException(e);
					}
				}
				zipStream.closeEntry();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (trialForm == null)
				zipStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return trialForm;
	}

	public TrialForm readTrialFormFromZip(File file)
			throws XmlImportExportException {

		ZipFile zipFile;
		try {
			zipFile = new ZipFile(file);
		} catch (ZipException e) {
			throw new XmlImportExportException(e);
		} catch (IOException e) {
			throw new XmlImportExportException(e);
		}

		ZipEntry docEntry = zipFile.getEntry(IXMLImportExport.XML_FILENAME);

		if (docEntry == null) {
			throw new XmlImportExportException(MessageUtilsBean
					.formatMessage("error.nodocumentationinarchive"));
		}

		// read trial form
		TrialForm trialForm = null;
		JAXBContext ctx;
		try {
			ctx = JAXBContext.newInstance(CLASSES_TO_BE_BOUND_TF);
			Unmarshaller um = ctx.createUnmarshaller();
			trialForm = (TrialForm) um.unmarshal(zipFile
					.getInputStream(docEntry));

		} catch (JAXBException e) {
			throw new XmlImportExportException(e);
		} catch (IOException e) {
			throw new XmlImportExportException(e);
		}

		// read images
		ZipEntry entry;
		InputStream is;
		Map<String, byte[]> imageMap = new HashMap<String, byte[]>();
		Enumeration<? extends ZipEntry> entries = zipFile.entries();

		try {
			while (entries.hasMoreElements()) {

				entry = entries.nextElement();

				// skip documentation file
				if (IXMLImportExport.XML_FILENAME.equals(entry.getName())) {
					continue;
				}

				byte data[] = new byte[(int) entry.getSize()];
				is = new BufferedInputStream(zipFile.getInputStream(entry));
				is.read(data, 0, data.length);
				is.close();

				imageMap.put(entry.getName(), data);
			}
		} catch (IOException e) {
			throw new XmlImportExportException(e);
		}

		// set images in trialform
		for (AttributeGroup attrGroup : trialForm.getAttributeGroups()) {
			for (Attribute attr : attrGroup.getAttributes()) {

				Image image = attr.getImageComment();

				if (image == null || image.getId() == null) {
					continue;
				}

				byte[] data = imageMap.get(image.getId().toString());

				if (data == null) {
					throw new XmlImportExportException(MessageUtilsBean
							.formatMessage("error.imagenotfoundinarchive",
									image.getFileName()));
				}

				image.setData(data);
				image.setId(null); // a new id will be created by the db
			}
		}

		return trialForm;
	}

	public void writeTrialFormToXml(TrialForm tf, OutputStream os)
			throws XmlImportExportException {
		try {
			JAXBContext ctx = JAXBContext.newInstance(CLASSES_TO_BE_BOUND_TF);
			Marshaller m = ctx.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			m.marshal(tf, os);
		} catch (JAXBException e) {
			throw new XmlImportExportException(e);
		}

	}

	public void writeTrialFormToZip(TrialForm tf, OutputStream os)
			throws XmlImportExportException {

		ZipOutputStream zipStream = new ZipOutputStream(os);

		try {
			// write documentation xml to zip
			zipStream.putNextEntry(new ZipEntry(IXMLImportExport.XML_FILENAME));
			writeTrialFormToXml(tf, zipStream);
			zipStream.closeEntry();

			// write image attachments to zip
			for (AttributeGroup attrGroup : tf.getAttributeGroups()) {
				for (Attribute attr : attrGroup.getAttributes()) {

					Image image = attr.getImageComment();

					if (image == null || image.getData() == null
							|| image.getId() == null) {
						continue;
					}

					zipStream.putNextEntry(new ZipEntry(image.getId()
							.toString()));
					zipStream.write(image.getData());
					zipStream.closeEntry();
				}
			}

			zipStream.close();
		} catch (IOException e) {
			throw new XmlImportExportException(e);
		}
	}
}
