package ticketline.tdg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;

/**
 * Die Konfiguration für den Testdatengenerator. Diese Klasse reduziert die
 * Zugriffe auf das darunterliegende XML Dokument und sammelt die
 * unterschiedlichen Konfigurationsparameter.
 */
public class TDGConfig {

	final Document conf;

	final Cache cache;

	public TDGConfig(Document conf) {
		this.conf = conf;
		this.cache = new Cache();
	}

	Element getFieldForTable(String tableName, String fieldName) {
		XPath p = cache.getXPath("//file[id='" + tableName + "']/field[@name='"
				+ fieldName + "']");
		Element e = (Element) p.selectSingleNode(conf);
		return e;
	}

	String getDocumentName(String tableName) {
		XPath xpDocName = cache.getXPath("//file[id='" + tableName + "']/name");
		Node node = xpDocName.selectSingleNode(conf);
		String docName = null;
		if (node == null) {
			// no node found, where the file name is describer => use default
			XPath xpDefaultPrefix = cache
					.getXPath("/config/mapping-file-prefix");
			docName = xpDefaultPrefix.selectSingleNode(conf).getText()
					+ tableName + ".hbm.xml";
		} else {
			docName = node.getText();
		}
		return docName;
	}

	List getJoinedSubclasses(Document doc) {
		XPath p = cache.getXPath("//joined-subclass", doc);
		return p.selectNodes(doc);
	}

	String getFileNameForType(String type) {
		XPath p = cache.getXPath("//type[name='" + type + "']/file");
		Element e = (Element) p.selectSingleNode(conf);
		return e.getText();
	}

	List getTableSequence() {
		// will only be called once ... no cache needed
		XPath p = conf.createXPath("/config/tablesequence");
		Element e = (Element) p.selectSingleNode(conf);
		String seq = e.getTextTrim();

		// add each element in the sequence to the list
		List<String> v = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(seq, ";");
		while (st.hasMoreTokens()) {
			v.add(st.nextToken());
		}

		return v;
	}

	String getConfigFile() {
		// will only be called once ... no cache needed
		XPath p = conf.createXPath("/config/configfile");
		return p.selectSingleNode(conf).getText();
	}

	String getMappedMethodname(String table, String methodName) {
		XPath p = cache.getXPath("//method-mapping/class[@name='" + table
				+ "']/method[@name='" + methodName + "']");
		List l = p.selectNodes(conf);
		Element methodelement = (Element) l.get(0);
		String s = methodelement.attributeValue("mapto");
		return new String(s.substring(0, 1).toUpperCase() + s.substring(1));
	}

	boolean isMemberOfJoinedSubClass(String className) {
		XPath p = cache.getXPath("//superclass/subclass[@name='" + className
				+ "']");
		Object o = p.selectSingleNode(conf);
		return o != null;
	}

	int getQuantityXpath(String table) {
		// will only be called once per table ... no cache needed
		XPath xpath = conf.createXPath("//quantity/table[@name='" + table
				+ "']/@qty");
		return xpath.numberValueOf(conf).intValue();
	}

	/**
	 * Assoziert ein Dokument mit einem XPath Cache.
	 */
	private class Cache extends HashMap {

		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		public Cache() {
			put(TDGConfig.this.conf, new XPathCache());
		}

		@SuppressWarnings("unchecked")
		XPath getXPath(String xpathString, Document document) {
			XPathCache xpCache = (XPathCache) get(document);
			if (xpCache == null) {
				xpCache = new XPathCache();
				put(document, xpCache);
			}
			return xpCache.getXPath(xpathString);
		}

		XPath getXPath(String xpathString) {
			return getXPath(xpathString, TDGConfig.this.conf);
		}
	}

	/**
	 * Speichert alle bisher verwendeten XPaths für ein Dokument in einer Map,
	 * damit schneller darauf zugegriffen werden kann.
	 */
	private class XPathCache extends HashMap {

		/**
		 * UID.
		 */
		private static final long serialVersionUID = 5766782273656723657L;

		XPath getXPath(String xpathString) {
			return getXPath(xpathString, TDGConfig.this.conf);
		}

		@SuppressWarnings("unchecked")
		XPath getXPath(String xpathString, Document doc) {
			XPath xpath = (XPath) get(xpathString);
			if (xpath == null) {
				xpath = doc.createXPath(xpathString);
				put(xpathString, xpath);
			}
			return xpath;
		}
	}
}
