package ticketline.tdg;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ticketline.dao.DAOFactory;
import ticketline.db.Saal;

/**
 * Der Testdatengenerator
 */
public class HbmTDG {

	private static final Log LOG = LogFactory.getLog("ticketline.tdg");

	private static final String CONF = "tdg.cfg.xml";

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		LOG.info("Starting ...");
		HbmUtil hm = new HbmUtil(CONF);
		Hashtable everything = hm.readTables();
		Hashtable<String, Object> z = new Hashtable<String, Object>();
		// getTableSequence
		List tablesequence = hm.getTableSequence();
		for (Iterator tsIterator = tablesequence.iterator(); tsIterator
				.hasNext();) {
			String table = (String) tsIterator.next();
			int qty = hm.getQuantity(table);
			LOG.info("Filling table " + table + " with " + qty + " data sets.");
			for (int j = 0; j < qty; j++) {

				if (hm.hasJoinedSubClass(table)) {
					hm.processJoinedSubClass(table);
				} else {

					List fields = (List) everything.get(table);

					for (Iterator i = fields.iterator(); i.hasNext();) {
						String field = (String) i.next();
						Object value = hm.getValue(table, field);
						if (value != null) {
							z.put(field, value);
							// log.info("Table: " +table+" field: "+field+"
							// value: "+ value);
						} else {
							// log.info("Table: " +table+" field: "+field+" is
							// Null!!!!!!!!!!!!!!!!!!1");
						}
					}

					// log.info("Processing Foreingkeys of table "+table);
					z.putAll(hm.processForeignKeys(table));
					hm.writeObject(table, z);
					z.clear();
				}
			}

		}

		LOG.info("Static Data Creation starting");
		// add a kategorie to every saal that has no kategorie and create rows
		List l = DAOFactory.getSaalDAO().getAll();
		String test = "";
		for (Iterator iter = l.iterator(); iter.hasNext();) {

			Saal s = (Saal) iter.next();
			test = test + " " + s.getComp_id().getBezeichnung();

			HbmStatic.createReihen(s);

		}
		System.out.println(test);
		LOG.info("Data Generation finished.");
	}
}