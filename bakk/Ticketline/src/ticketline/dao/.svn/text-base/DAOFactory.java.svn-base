package ticketline.dao;

import ticketline.dao.hibernate.ArtikelDAOHibernate;
import ticketline.dao.hibernate.AuffuehrungDAOHibernate;
import ticketline.dao.hibernate.BelegungDAOHibernate;
import ticketline.dao.hibernate.BestellungDAOHibernate;
import ticketline.dao.hibernate.EngagementDAOHibernate;
import ticketline.dao.hibernate.KategorieDAOHibernate;
import ticketline.dao.hibernate.KuenstlerDAOHibernate;
import ticketline.dao.hibernate.KundeDAOHibernate;
import ticketline.dao.hibernate.MitarbeiterDAOHibernate;
import ticketline.dao.hibernate.OrtDAOHibernate;
import ticketline.dao.hibernate.ReiheDAOHibernate;
import ticketline.dao.hibernate.SaalDAOHibernate;
import ticketline.dao.hibernate.TicketcardDAOHibernate;
import ticketline.dao.hibernate.TransaktionDAOHibernate;
import ticketline.dao.hibernate.VeranstaltungDAOHibernate;
import ticketline.dao.interfaces.ArtikelDAO;
import ticketline.dao.interfaces.AuffuehrungDAO;
import ticketline.dao.interfaces.BelegungDAO;
import ticketline.dao.interfaces.BestellungDAO;
import ticketline.dao.interfaces.EngagementDAO;
import ticketline.dao.interfaces.KategorieDAO;
import ticketline.dao.interfaces.KuenstlerDAO;
import ticketline.dao.interfaces.KundeDAO;
import ticketline.dao.interfaces.MitarbeiterDAO;
import ticketline.dao.interfaces.OrtDAO;
import ticketline.dao.interfaces.ReiheDAO;
import ticketline.dao.interfaces.SaalDAO;
import ticketline.dao.interfaces.TicketcardDAO;
import ticketline.dao.interfaces.TransaktionDAO;
import ticketline.dao.interfaces.VeranstaltungDAO;

/**
 * DAO Factory for ticketline.
 *
 * @author geezmo
 */
public class DAOFactory {

	private static ArtikelDAO artikelDAO;

	private static AuffuehrungDAO auffuehrungDAO;

	private static BelegungDAO belegungDAO;

	private static BestellungDAO bestellungDAO;

	private static EngagementDAO engagementDAO;

	private static KategorieDAO kategorieDAO;

	private static KuenstlerDAO kuenstlerDAO;

	private static KundeDAO kundeDAO;

	private static MitarbeiterDAO mitarbeiterDAO;

	private static OrtDAO ortDAO;

	private static ReiheDAO reiheDAO;

	private static SaalDAO saalDAO;

	private static TicketcardDAO ticketcardDAO;

	private static TransaktionDAO transaktionDAO;

	private static VeranstaltungDAO veranstaltungDAO;

	/**
	 * @return Returns the artikelDAO.
	 */
	public static ArtikelDAO getArtikelDAO() {
		if (artikelDAO == null) {
			artikelDAO = new ArtikelDAOHibernate();
		}
		return artikelDAO;
	}

	/**
	 * DAO for specific object.
	 *
	 * @return Returns the auffuehrungDAO.
	 */
	public static AuffuehrungDAO getAuffuehrungDAO() {
		if (auffuehrungDAO == null) {
			auffuehrungDAO = new AuffuehrungDAOHibernate();
		}
		return auffuehrungDAO;
	}

	/**
	 * DAO for specific object.
	 *
	 * @return Returns the belegungDAO.
	 */
	public static BelegungDAO getBelegungDAO() {
		if (belegungDAO == null) {
			belegungDAO = new BelegungDAOHibernate();
		}
		return belegungDAO;
	}

	/**
	 * DAO for specific object.
	 *
	 * @return Returns the bestellungDAO.
	 */
	public static BestellungDAO getBestellungDAO() {
		if (bestellungDAO == null) {
			bestellungDAO = new BestellungDAOHibernate();
		}
		return bestellungDAO;
	}

	/**
	 * DAO for specific object.
	 *
	 * @return Returns the engagementDAO.
	 */
	public static EngagementDAO getEngagementDAO() {
		if (engagementDAO == null) {
			engagementDAO = new EngagementDAOHibernate();
		}
		return engagementDAO;
	}

	/**
	 * DAO for specific object.
	 *
	 * @return Returns the kategorieDAO.
	 */
	public static KategorieDAO getKategorieDAO() {
		if (kategorieDAO == null) {
			kategorieDAO = new KategorieDAOHibernate();
		}
		return kategorieDAO;
	}

	/**
	 * DAO for specific object.
	 *
	 * @return Returns the kuenstlerDAO.
	 */
	public static KuenstlerDAO getKuenstlerDAO() {
		if (kuenstlerDAO == null) {
			kuenstlerDAO = new KuenstlerDAOHibernate();
		}
		return kuenstlerDAO;
	}

	/**
	 * DAO for specific object.
	 *
	 * @return Returns the kundeDAO.
	 */
	public static KundeDAO getKundeDAO() {
		if (kundeDAO == null) {
			kundeDAO = new KundeDAOHibernate();
		}
		return kundeDAO;
	}

	/**
	 * DAO for specific object.
	 *
	 * @return Returns the mitarbeiterDAO.
	 */
	public static MitarbeiterDAO getMitarbeiterDAO() {
		if (mitarbeiterDAO == null) {
			mitarbeiterDAO = new MitarbeiterDAOHibernate();
		}
		return mitarbeiterDAO;
	}

	/**
	 * DAO for specific object.
	 *
	 * @return Returns the ortDAO.
	 */
	public static OrtDAO getOrtDAO() {
		if (ortDAO == null) {
			ortDAO = new OrtDAOHibernate();
		}
		return ortDAO;
	}

	/**
	 * DAO for specific object.
	 *
	 * @return Returns the reiheDAO.
	 */
	public static ReiheDAO getReiheDAO() {
		if (reiheDAO == null) {
			reiheDAO = new ReiheDAOHibernate();
		}
		return reiheDAO;
	}

	/**
	 * DAO for specific object.
	 *
	 * @return Returns the saalDAO.
	 */
	public static SaalDAO getSaalDAO() {
		if (saalDAO == null) {
			saalDAO = new SaalDAOHibernate();
		}
		return saalDAO;
	}

	/**
	 * DAO for specific object.
	 *
	 * @return Returns the ticketcardDAO.
	 */
	public static TicketcardDAO getTicketcardDAO() {
		if (ticketcardDAO == null) {
			ticketcardDAO = new TicketcardDAOHibernate();
		}
		return ticketcardDAO;
	}

	/**
	 * DAO for specific object.
	 *
	 * @return Returns the transaktionDAO.
	 */
	public static TransaktionDAO getTransaktionDAO() {
		if (transaktionDAO == null) {
			transaktionDAO = new TransaktionDAOHibernate();
		}
		return transaktionDAO;
	}

	/**
	 * DAO for specific object.
	 *
	 * @return Returns the veranstaltungDAO.
	 */
	public static VeranstaltungDAO getVeranstaltungDAO() {
		if (veranstaltungDAO == null) {
			veranstaltungDAO = new VeranstaltungDAOHibernate();
		}
		return veranstaltungDAO;
	}
}
