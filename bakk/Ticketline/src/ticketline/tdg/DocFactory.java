package ticketline.tdg;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

public class DocFactory {
	private static final Log LOG = LogFactory.getLog("ticketline.tdg");

	private final Hashtable<String, Document> docs;

	private SAXReader xmlReader;

	public DocFactory() {
		docs = new Hashtable<String, Document>();
		xmlReader = new SAXReader();
		DocumentFactory factory = new org.dom4j.dom.DOMDocumentFactory();
		xmlReader.setDocumentFactory(factory);
	}

	public Document get(String req) {
		Document doc = (Document) docs.get(req);
		if (doc == null) {
			try {
				// check if the file or URL exists
				if (checkExists(req)) {
					doc = xmlReader.read(req);
				} else {
					// use the class loader to load the document
					doc = xmlReader.read(new InputSource(getClass()
							.getClassLoader().getResourceAsStream(req)));
				}

				docs.put(req, doc);
			} catch (Exception e) {
				LOG.error("There was an error while reading " + req, e);
			}
		}
		return doc;
	}

	public org.w3c.dom.Document getW3c(String s) {
		return (org.w3c.dom.Document) get(s);
	}

	private boolean checkExists(String s) {
		File f = new File(s);
		if (f.exists()) {
			return true;
		}

		// file does not exist, try to resolve URL
		try {
			URL url = new URL(s);
			if (url.getContent() != null) {
				return true;
			}
		} catch (IOException e) {
			// ignore this exception
		}

		return false;
	}
}