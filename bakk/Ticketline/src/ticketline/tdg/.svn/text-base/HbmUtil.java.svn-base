package ticketline.tdg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.ManyToOne;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.RootClass;
import org.hibernate.mapping.Subclass;
import org.hibernate.mapping.Value;

public class HbmUtil {

	static String[] small = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
			"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
			"x", "y", "z", " ", " ", " ", " ", " ", " " };

	static String[] big = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
			"X", "Y", "Z", " ", " ", " ", " ", " ", " " };

	static String[] number = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };

	private static final Log LOG = LogFactory.getLog("ticketline.hbmutil");

	private final DocFactory docFac;

	private final Random random;

	private final TDGConfig tdgConf;

	public HbmUtil(String config) {
		this.docFac = new DocFactory();
		this.random = new Random();
		LOG.info("Reading Configuration ...");
		this.tdgConf = new TDGConfig(docFac.get(config));
	}

	// get table sequence from cfg file
	public List getTableSequence() {
		LOG.debug("Retrieving table sequence ...");
		return tdgConf.getTableSequence();
	}

	// get Tables and fields and return them in a Hasttable with Table as key
	// and fields as Vector
	public Hashtable readTables() {
		LOG.info("Reading Table configuration ...");
		Hashtable<String, List<String>> t = new Hashtable<String, List<String>>();

		String configFile = tdgConf.getConfigFile();
		Configuration hbmConfig = new Configuration();

		try {
			hbmConfig.configure(docFac.getW3c(configFile));
		} catch (Exception e) {
			LOG.error("Could not read hibernate configuration from "
					+ configFile, e);
			return null;
		}

		Iterator classMappings = hbmConfig.getClassMappings();
		while (classMappings.hasNext()) {
			Object o = classMappings.next();
			// only parse root classes
			if (o instanceof Subclass) {
				continue;
			}
			RootClass rootClass = (RootClass) o;
			String name = rootClass.getNodeName();
			name = new String(name.substring(name.lastIndexOf('.') + 1));
			Iterator propertyIterator = rootClass.getPropertyIterator();
			List<String> l = new ArrayList<String>();
			while (propertyIterator.hasNext()) {
				Property prop = (Property) propertyIterator.next();
				Value value = prop.getValue();
				if (value.isSimpleValue() && !(value instanceof ManyToOne)) {
					l.add(prop.getName());
				}
			}
			LOG.info("Adding " + name + " => " + l);
			t.put(name, l);
		}
		LOG.info("Table configuration read.");

		return t;
	}

	/**
	 * Returns a object with the value of the requested Table.field
	 *
	 * @param table
	 *            the table
	 * @param field
	 *            the field
	 * @return
	 */
	public Object getValue(String table, String field) {
		Object s = null;
		LOG.debug("trying to get " + field + " from " + table);
		Element e = tdgConf.getFieldForTable(table, field);
		String type = e.attributeValue("type");
		String nullable = e.attributeValue("nullable");
		Random r = new Random();
		if (nullable == "true" && r.nextBoolean()) {
			s = null;
		} else {
			// make sure no null object is returned
			while (s == null) {
				s = getTypeValue(type);
			}
		}
		return s;
	}

	private Object getTypeValue(String type) {
		// define built-in data types
		boolean found = false;
		Object s = null;

		if (type.indexOf("RandInt") != -1) {
			String start = type
					.substring("RandInt".length(), type.indexOf("$"));
			String offset = type.substring(type.indexOf("$") + 1);
			s = randomInt(Integer.parseInt(start), Integer.parseInt(offset));
			found = true;
		}
		if (type.indexOf("RandString") != -1) {
			int n = Integer.parseInt(type.substring("RandString".length()));
			s = randomString(n);
			found = true;
		}
		if (type.indexOf("RandBool") != -1) {
			s = randomBool();
			found = true;
		}
		if (type.indexOf("RandDate") != -1) {
			int n = Integer.parseInt(type.substring("RandDate".length(), type
					.indexOf("$")));
			int m = Integer.parseInt(type.substring(type.indexOf("$") + 1));
			s = randomDate(n, m);
			found = true;
		}
		if (type.indexOf("RandTimestamp") != -1) {
			int n = Integer.parseInt(type.substring("RandTimestamp".length(),
					type.indexOf("$")));
			int m = Integer.parseInt(type.substring(type.indexOf("$") + 1));
			s = randomTimeStamp(n, m);
			found = true;
		}
		if (type.indexOf("List") != -1) {
			s = randomList(type);
			found = true;
		}

		if (!found) {
			s = getTypeValuefromFile(type);
		}

		return s;
	}

	// get random value of Type type out of the textfile
	private String getTypeValuefromFile(String type) {
		String fileName = null;
		// get right file matching the type
		fileName = tdgConf.getFileNameForType(type);

		// read file and return random line from the file
		String s = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(getFileReader(fileName));
			List<String> lines = new ArrayList<String>();
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			int num = random.nextInt(lines.size());
			s = (String) lines.get(num);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				LOG.error("Could not close test data file.", e);
			}
		}

		return s;
	}

	private Reader getFileReader(String filename) throws FileNotFoundException {
		File f = new File(filename);
		if (f.exists()) {
			return new FileReader(f);
		}

		// try to locate via class path
		InputStream stream = getClass().getResourceAsStream("/" + filename);
		if (stream == null) {
			throw new FileNotFoundException("Could not find " + filename
					+ " in file system nor in class path.");
		}
		return new InputStreamReader(stream);
	}

	private Timestamp randomTimeStamp(int n, int offset) {
		Date d = randomDate(n, offset);
		Timestamp t = new Timestamp(d.getTime());
		return t;
	}

	public String randomList(String list) {
		String s = "";
		String t = "";
		Random r = new Random();

		t = list.substring(list.indexOf("[") + 1, list.length() - 1);
		StringTokenizer st = new StringTokenizer(t, ";");
		int j = r.nextInt(st.countTokens());

		for (int i = 0; i < j; i++) {
			st.nextToken();
		}

		s = st.nextToken();

		return s;
	}

	public String randomInt(int start, int offset) {
		String s = "";
		Random r = new Random();
		int i = 0;
		if (offset >= 0) {
			i = start + r.nextInt(offset);
		} else {
			i = start - r.nextInt(Math.abs(offset));
		}

		s = Integer.toString(i);
		return s;
	}

	private String randomString(int length) {
		String s = "";
		try {
			Thread.sleep(10);
			Random r = new Random();
			for (int i = 0; i < length; i++) {

				if (r.nextBoolean()) {
					// gro�buchstaben
					s = s + small[r.nextInt(small.length - 1)];
				} else {
					// lkeinbuchstabe
					s = s + big[r.nextInt(big.length - 1)];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return s;
	}

	public Date randomDate(int n, int offset) {
		Calendar c = new GregorianCalendar();

		Random r = new Random();
		final int year, month, day;
		if (offset < 0) {
			year = n - r.nextInt(Math.abs(offset));
		} else {
			year = n + r.nextInt(offset);
		}
		month = r.nextInt(12);
		day = r.nextInt(27) + 1;

		// set calendar properties
		c.set(year, month, day);

		return c.getTime();
	}

	private String randomBool() {
		String s = "";
		try {
			Thread.sleep(10);
			Random r = new Random();
			s = Boolean.toString(r.nextBoolean());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return s;
	}

	public Hashtable processForeignKeys(String table) {
		String foreignClass = "";
		Hashtable<String, Object> h = new Hashtable<String, Object>();
		Vector<String> v = new Vector<String>();
		try {
			Document doc = getDocumentForTable(table);

			// normal many to one
			XPath p = doc.createXPath("//many-to-one/column");
			List l = p.selectNodes(doc);
			LOG.debug("many to one: " + l.size());
			for (int i = 0; i < l.size(); i++) {
				Element e = (Element) l.get(i);
				String colname = e.attributeValue("name");
				p = doc.createXPath("//key-property[@name='" + colname + "'] ");
				List m = p.selectNodes(doc);
				if (m.size() == 0) {
					// colname not in key-properties
					foreignClass = e.getParent().attributeValue("class");
					String methodname = e.getParent().attributeValue("name");
					// methodname=methodname.substring(0,1).toUpperCase() +
					// methodname.substring( 1);
					foreignClass = foreignClass.substring(foreignClass
							.lastIndexOf(".") + 1);
					if (!v.contains(foreignClass)) {
						LOG.debug("ForeignKey Call to: getrandomObject("
								+ foreignClass + ")");
						v.addElement(foreignClass);
					}

					h.put(methodname, getRandomObject(foreignClass));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return h;
	}

	public Object getRandomObject(String table) {
		Object o = new Object();
		List l;
		try {
			LOG.debug(table + "DAOHibernate.getall");
			Class c = Class.forName("ticketline.dao.hibernate." + table
					+ "DAOHibernate");
			Method m = c.getDeclaredMethod("getAll", new Class[] {});

			l = (List) m.invoke(c.newInstance(), (Object[]) null);
			if (l.size() == 0) {
				LOG.error("NO Data in Database!");
				return null;
			}
			Random r = new Random();
			int j = r.nextInt(l.size());
			o = l.get(j);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	// returns the objectKEY object of a random table object
	public Object getRandomObjectKey(String table) {
		LOG.debug("Getting a random KEY for " + table);
		Object o = new Object();
		Object key = new Object();
		List l;
		try {
			LOG.debug(table + "DAOHibernate.getall");
			Class c = Class.forName("ticketline.dao.hibernate." + table
					+ "DAOHibernate");
			Method m = c.getDeclaredMethod("getAll", new Class[] {});

			l = (List) m.invoke(c.newInstance(), (Object[]) null);
			if (l.size() == 0) {
				LOG.error("NO Data in Database!");
			}
			Random r = new Random();
			int j = r.nextInt(l.size());
			o = l.get(j);

			m = o.getClass().getDeclaredMethod("getComp_id", (Class[]) null);
			key = m.invoke(o, (Object[]) null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}

	public void writeObject(String table, Hashtable h) {
		String methodname = "";
		String t;
		Class type = null;
		Object value = null;
		LOG.info("Trying to write to DB: " + table + ", keys: " + h.size());

		try {
			Class c = Class.forName("ticketline.db." + table);
			Method[] ma = c.getDeclaredMethods();
			Enumeration f = h.keys();
			Object classobject = c.newInstance();
			while (f.hasMoreElements()) {
				t = (String) f.nextElement();

				methodname = "set" + t.substring(0, 1).toUpperCase()
						+ t.substring(1);
				if (t != null) {

					// get parameter type
					for (int j = 0; j < ma.length; j++) {
						if (ma[j].getName().equals(methodname)) {
							Class[] types = ma[j].getParameterTypes();
							type = types[0];
							// found method, exit loop
							break;
						}
					}
					Object i = null;
					value = h.get(t.toString());
					if (type == (Integer.class)) {
						i = new Integer(value.toString());
					} else if (type == int.class) {
						i = new Integer(value.toString());
					} else if (type == String.class) {
						i = new String(value.toString());
					} else if (type == boolean.class) {
						i = new Boolean(value.toString());
					} else if (type == BigDecimal.class) {
						i = new BigDecimal(value.toString());
					} else {
						i = value;
					}

					LOG.info("Call to: " + table + "." + methodname + "("
							+ value + ")");
					Method m = c.getDeclaredMethod(methodname,
							new Class[] { type });

					Object[] obj = { i };

					m.invoke(classobject, obj);
				}
			}

			if (hasCompositeId(table)) {
				LOG.debug("Starting to process Key ..... ");
				Object compID = processKey(table, h);
				Class key = Class.forName("ticketline.db." + table + "Key");
				Method m = c.getDeclaredMethod("setComp_id",
						new Class[] { key });
				Object[] obj = { compID };
				m.invoke(classobject, obj);
				LOG.debug("finished");
			}

			// Save Class with DAO
			Class d = Class.forName("ticketline.dao.hibernate." + table
					+ "DAOHibernate");
			methodname = "save";
			LOG.debug("Call to: DAOSAVE-" + table + "." + methodname + "("
					+ classobject.getClass().toString() + ")");
			Method m = d.getDeclaredMethod(methodname,
					new Class[] { classobject.getClass() });
			Object[] obj = { classobject };
			m.invoke(d.newInstance(), obj);
			LOG.info("Object done.");

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	// checks if a table has a normal or a composite ID
	private boolean hasCompositeId(String table) {
		boolean b = false;
		try {
			Document doc = getDocumentForTable(table);
			XPath p = doc.createXPath("//composite-id");
			List l = p.selectNodes(doc);
			if (l.size() == 0) {
				b = false;
			} else {
				b = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return b;
	}

	// checks if a column of a table is part of a composite id or a single id
	private boolean checkSingleKey(String table, String column) {
		boolean b = true;
		try {
			Document doc = getDocumentForTable(table);
			XPath p = doc.createXPath("//many-to-one/column[@name='" + column
					+ "']");
			Element el = (Element) p.selectSingleNode(doc);
			List m = el.getParent().elements("column");
			if (m.size() == 1) {
				b = true;
			} else {
				b = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return b;
	}

	// processes a composite Key
	private Object processKey(String table, Hashtable h) {
		Hashtable<String, Object> keys = new Hashtable<String, Object>();
		Object classobject = null;
		Object key = null;
		LOG.info("Generating Key of Table" + table);
		try {
			Class c = Class.forName("ticketline.db." + table + "Key");
			classobject = c.newInstance();

			Document doc = getDocumentForTable(table);
			XPath p = doc.createXPath("//key-property");
			List l = p.selectNodes(doc);
			for (Iterator iter = l.iterator(); iter.hasNext();) {
				Element e = (Element) iter.next();
				String col = e.attributeValue("column");
				p = doc
						.createXPath("//many-to-one/column[@name='" + col
								+ "']");
				List m = p.selectNodes(doc);
				if (m.size() == 0) {
					// local key : ticketline.db.TABLEKey.setCOL(h.get(COL));
					String methodname = "set"
							+ col.substring(0, 1).toUpperCase()
							+ col.substring(1);
					// special treatment for java.sql.timestamp
					Class[] type = new Class[1];
					if (e.attributeValue("type").equals("java.sql.Timestamp")) {
						type[0] = Date.class;
					} else {
						type[0] = Class.forName(e.attributeValue("type"));
					}

					Method met = c.getDeclaredMethod(methodname, type);
					Object[] value = { getValue(table, col) };
					// makes sure that only not-null values are used for keys
					while (value[0] == null) {
						value[0] = getValue(table, col);
					}

					LOG.info("KEY: callto: " + table + "Key" + methodname + "("
							+ value[0].toString() + ")");
					met.invoke(classobject, value);

				} else {
					// foreignkey
					// check if its composite or single key
					if (checkSingleKey(table, col)) {
						Element el = (Element) m.get(0);
						String foreign = el.getParent().attributeValue("class");
						String foreignClass = foreign.substring(foreign
								.lastIndexOf(".") + 1);
						String methodname = el.attributeValue("name");
						methodname = "get"
								+ tdgConf
										.getMappedMethodname(table, methodname);
						// check if foreign key references a cjhild of a
						// joinedsubclass
						Class[] a = new Class[1];
						Object[] ret = new Object[1];
						if (tdgConf.isMemberOfJoinedSubClass(foreignClass)) {
							// get value from foreign key class
							Object f = getRandomObject(foreignClass);
							Method dm = getMethodOfJoinedSubClass(foreignClass,
									methodname);
							ret[0] = dm.invoke(f, (Object[]) null);
							a[0] = dm.getReturnType();
						} else {
							// get value from foreign key class
							Object f = getRandomObject(foreignClass);
							Method dm = f.getClass().getDeclaredMethod(
									methodname, (Class[]) null);
							ret[0] = dm.invoke(f, (Object[]) null);
							a[0] = dm.getReturnType();
						}
						// col=col.substring( foreignClass.length() );
						LOG.info("KEY(single foreign ): Callto:" + table
								+ "KEY.set" + col + "(" + foreignClass + "."
								+ methodname + ")");

						// set value in current class
						methodname = "set" + col.substring(0, 1).toUpperCase()
								+ col.substring(1);
						Method cm = c.getDeclaredMethod(methodname, a);
						cm.invoke(classobject, ret);

					} else {
						Element el = (Element) m.get(0);
						String foreign = el.getParent().attributeValue("class");
						String foreignClass = foreign.substring(foreign
								.lastIndexOf(".") + 1);
						String methodname = el.attributeValue("name");
						methodname = "get"
								+ tdgConf
										.getMappedMethodname(table, methodname);
						// col=col.substring( foreignClass.length() );
						LOG.info("KEY(composite foreign): Callto:" + table
								+ "KEY.set" + col + "(" + foreignClass + "KEY."
								+ methodname + ")");
						if (keys.get(foreignClass) == null) {
							key = getRandomObjectKey(foreignClass);
							keys.put(foreignClass, key);
						} else {
							key = keys.get(foreignClass);
						}

						Method fckeym = key.getClass().getDeclaredMethod(
								methodname, (Class[]) null);
						Object[] ret = { fckeym.invoke(key, (Object[]) null) };
						Class[] a = { fckeym.getReturnType() };
						// set value in current key class
						methodname = "set" + col.substring(0, 1).toUpperCase()
								+ col.substring(1);
						Method curkeym = c.getDeclaredMethod(methodname, a);
						curkeym.invoke(classobject, ret);
					}

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return classobject;
	}

	// check if a table contains a joined sub class
	public boolean hasJoinedSubClass(String table) {
		boolean b = false;
		try {
			Document doc = getDocumentForTable(table);
			List l = tdgConf.getJoinedSubclasses(doc);
			if (l.size() != 0) {
				b = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return b;
	}

	// deal with a joined Sub Class
	@SuppressWarnings("unchecked")
	public void processJoinedSubClass(String table) {
		Vector<String> v = new Vector<String>();
		List f;
		String fieldname;
		String field;
		Object value;
		Hashtable<String, Object> z = new Hashtable<String, Object>();
		try {
			// get a random subclass
			Random r = new Random();
			Document doc = getDocumentForTable(table);
			List l = tdgConf.getJoinedSubclasses(doc);
			Element el = (Element) l.get(r.nextInt(l.size()));
			String subclass = el.attributeValue("name");

			// get fields of superclass;
			XPath p = doc.createXPath("//class[@name='ticketline.db." + table
					+ "']/property");
			f = p.selectNodes(doc);
			for (int i = 0; i <= (f.size() - 1); i++) {
				Element e = (Element) f.get(i);
				fieldname = e.attributeValue("name");
				LOG.debug("fieldname:" + e.attributeValue("name"));
				v.addElement(fieldname);
			}
			// get fields of subclass
			p = doc.createXPath("//joined-subclass[@name='" + subclass
					+ "']/property");
			f = p.selectNodes(doc);
			for (int i = 0; i <= (f.size() - 1); i++) {
				Element e = (Element) f.get(i);
				fieldname = e.attributeValue("name");
				LOG.debug("fieldname:" + e.attributeValue("name"));
				v.addElement(fieldname);
			}

			// get Value for fields
			for (Enumeration b = v.elements(); b.hasMoreElements();) {
				field = (String) b.nextElement();
				value = getValue(subclass, field);
				if (value != null) {
					z.put(field, value);
					LOG.debug("SUB-CLASS: Table: " + subclass + " field: "
							+ field + " value: " + value);
				} else {
					LOG.debug("Table: " + subclass + " field: " + field
							+ " is Null!!!!!!!!!!!!!!!!!!1");
				}
			}

			// get foreign keys
			z.putAll(processForeignKeys(table));

			// write to database

			String methodname = "";
			String t;
			Class type = null;
			value = null;
			LOG
					.info("trying to write to DB: " + subclass + " keys:"
							+ z.size());

			Class c = Class.forName("ticketline.db." + subclass);
			Class d = Class.forName("ticketline.db." + table);
			Object classobject;
			boolean sc = false;
			classobject = c.newInstance();
			Enumeration w = z.keys();
			while (w.hasMoreElements()) {
				t = (String) w.nextElement();
				methodname = "set" + t.substring(0, 1).toUpperCase()
						+ t.substring(1);
				if (t != null) {
					// get parameter type of subclass method
					Method[] ma = c.getDeclaredMethods();
					for (int j = 0; j < ma.length; j++) {
						if (ma[j].getName().equals(methodname)) {
							Class[] types = ma[j].getParameterTypes();
							type = types[0];
							sc = true;
						}
					}
					// get parameter type of super-class method
					Method[] ma2 = d.getDeclaredMethods();
					for (int j = 0; j < ma2.length; j++) {
						if (ma2[j].getName().equals(methodname)) {
							Class[] types2 = ma2[j].getParameterTypes();
							type = types2[0];
							sc = false;
						}
					}

					Object i = null;
					value = z.get(t.toString());
					// convert String back into right classes
					if (type == (Integer.class)) {
						i = new Integer(value.toString());
					} else if (type == int.class) {
						i = new Integer(value.toString());
					} else if (type == String.class) {
						i = new String(value.toString());
					} else if (type == boolean.class) {
						i = new Boolean(value.toString());
					} else if (type == BigDecimal.class) {
						i = new BigDecimal(value.toString());
					} else {
						i = value;
					}

					Method m;
					LOG.debug("Call to: " + subclass + "." + methodname + "("
							+ value + ")");
					if (sc) {
						m = c.getDeclaredMethod(methodname,
								new Class[] { type });
					} else {
						m = d.getDeclaredMethod(methodname,
								new Class[] { type });
					}

					Object[] obj = { i };
					m.invoke(classobject, obj);
				}
			}
			// Save Class with DAO
			Class e = Class.forName("ticketline.dao.hibernate." + subclass
					+ "DAOHibernate");
			methodname = "save";
			LOG.info("Call to: DAOSAVE-" + table + "." + methodname + "("
					+ classobject.getClass().toString() + ")");
			Method m = e.getDeclaredMethod(methodname,
					new Class[] { classobject.getClass() });
			Object[] obj = { classobject };
			m.invoke(e.newInstance(), obj);
			LOG.info("Object done.");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Method getMethodOfJoinedSubClass(String table, String methodname) {
		Method ret = null;
		try {
			Class s = Class.forName("ticketline.db." + table);
			Class t = s.getSuperclass();
			Method[] ma = t.getDeclaredMethods();
			for (int i = 0; i < ma.length; i++) {
				if (ma[i].getName().equals(methodname)) {
					ret = ma[i];
				}
			}
			table = t.getName().substring(t.getName().lastIndexOf(".") + 1);
			Document doc = getDocumentForTable(table);
			List l = tdgConf.getJoinedSubclasses(doc);
			for (Iterator iter = l.iterator(); iter.hasNext();) {
				Element el = (Element) iter.next();
				Class c = Class.forName("ticketline.db."
						+ el.attributeValue("name"));
				Method[] ma2 = c.getDeclaredMethods();
				for (int i = 0; i < ma2.length; i++) {
					if (ma2[i].getName().equals(methodname)) {
						ret = ma2[i];
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public int getQuantity(String table) {
		return tdgConf.getQuantityXpath(table);
	}

	private Document getDocumentForTable(String table) {
		return docFac.get(tdgConf.getDocumentName(table));
	}
}