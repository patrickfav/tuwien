package ticketline.tdg;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ticketline.dao.DAOFactory;
import ticketline.db.Auffuehrung;
import ticketline.db.Belegung;
import ticketline.db.BelegungKey;
import ticketline.db.Kategorie;
import ticketline.db.Kunde;
import ticketline.db.Reihe;
import ticketline.db.ReiheKey;
import ticketline.db.Saal;
import ticketline.db.Transaktion;
import ticketline.db.TransaktionKey;

/**
 * @author hiasi
 */
public class HbmStatic {

	private static final String CONF = "tdg.cfg.xml";

	public static void createReihen(Saal saal) {

		int gesamtPlaetzeSaal = saal.getAnzplaetze();

		// System.out.println("Saalname:"+saal.getComp_id().getBezeichnung());
		Set kategorien = DAOFactory.getSaalDAO().initSet(saal.getKategorien());
		// System.out.println(kategorien.size());

		for (Iterator iter = kategorien.iterator(); iter.hasNext();) {
			Kategorie kategorie = (Kategorie) iter.next();
			int gesamtPlaetzeKategorie = gesamtPlaetzeSaal / kategorien.size();
			int i = 1;
			kategorie.getReihen().clear();
			while (gesamtPlaetzeKategorie > 0) {
				int random = Integer.parseInt(new HbmUtil(CONF)
						.randomInt(1, 30));
				int anzahlPlaetze = random;
				if (gesamtPlaetzeKategorie - random < 0)
					anzahlPlaetze = gesamtPlaetzeKategorie;
				gesamtPlaetzeKategorie -= anzahlPlaetze;

				ReiheKey reiheKey = new ReiheKey("reihe" + i, kategorie
						.getComp_id().getBezeichnung(), kategorie.getComp_id()
						.getSaalbez(), kategorie.getComp_id().getOrtbez(),
						kategorie.getComp_id().getOrt());

				Reihe reihe = new Reihe(reiheKey, 1, anzahlPlaetze, true,
						kategorie, new HashSet());

				try {
					DAOFactory.getReiheDAO().save(reihe);

					Set auffuehrungen = DAOFactory.getSaalDAO().initSet(
							saal.getAuffuehrungen());
					for (Iterator iterator = auffuehrungen.iterator(); iterator
							.hasNext();) {
						Auffuehrung auffuehrung = (Auffuehrung) iterator.next();
						BelegungKey belegungKey = new BelegungKey(reihe
								.getComp_id().getBezeichnung(), kategorie
								.getComp_id().getBezeichnung(), kategorie
								.getComp_id().getSaalbez(), kategorie
								.getComp_id().getOrtbez(), kategorie
								.getComp_id().getOrt(), auffuehrung
								.getComp_id().getDatumuhrzeit());
						String bel = "";
						for (int j = 0; j < reihe.getAnzplaetze(); j++)
							bel += "F";
						Belegung belegung = new Belegung(belegungKey, bel, bel
								.length(), 0, 0, reihe, auffuehrung,
								new HashSet());

						try {
							DAOFactory.getBelegungDAO().save(belegung);
						} catch (Exception e) {
							e.printStackTrace();
						}

						HbmUtil util = new HbmUtil(CONF);

						if (Integer.parseInt(util.randomInt(1, 3)) == 1) {
							int gesamtReihe = belegung.getBelegung().length();
							int anzNeuReserviert = Integer.parseInt(util
									.randomInt(1, gesamtReihe));
							if (gesamtReihe - anzNeuReserviert < 2)
								continue;
							int startNeuReserviert = Integer.parseInt(util
									.randomInt(1, gesamtReihe
											- anzNeuReserviert));

							belegung.setAnzfrei(belegung.getAnzfrei()
									- anzNeuReserviert);
							belegung.setAnzres(anzNeuReserviert);

							String b1 = "";
							String b2 = belegung.getBelegung();
							b1 = b2.substring(0, startNeuReserviert - 1);
							for (int j = 0; j < anzNeuReserviert; j++)
								b1 += "R";
							b1 += b2.substring(startNeuReserviert
									+ anzNeuReserviert - 1);
							belegung.setBelegung(b1);

							Kunde kunde = (Kunde) new HbmUtil(CONF)
									.getRandomObject("Kunde");
							// Mitarbeiter mitarbeiter = (Mitarbeiter)new
							// HbmUtil(CONF).getRandomObject("Mitarbeiter");
							Date date = util.randomDate(2000, 10);
							TransaktionKey transaktionKey = new TransaktionKey(
									date, kunde.getKartennr(), new Integer(3));

							BigDecimal preis = new BigDecimal(0);
							if (auffuehrung.getPreis().equals("0"))
								preis = kategorie.getPreismin();
							if (auffuehrung.getPreis().equals("1"))
								preis = kategorie.getPreisstd();
							if (auffuehrung.getPreis().equals("2"))
								preis = kategorie.getPreismax();
							preis = preis.multiply(kunde.getErmaessigung())
									.multiply(new BigDecimal(anzNeuReserviert));

							Transaktion transaktion = new Transaktion(
									transaktionKey, true, false, preis,
									belegung.getReihe().getStartplatz(),
									belegung.getReihe().getAnzplaetze(),
									belegung, saal.getOrt());

							transaktion.setStartplatz(startNeuReserviert);

							transaktion.setAnzplaetze(anzNeuReserviert);

							try {
								DAOFactory.getTransaktionDAO()
										.save(transaktion);
							} catch (Exception e) {
							}

						}

					}
					i++;
				} catch (Exception e) {
					System.out.println("doppelte reihe!");
				}

			}
		}
	}

	public static void main(String[] args) {
		List list = DAOFactory.getSaalDAO().getAll();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Saal saal = (Saal) iter.next();

			createReihen(saal);

		}

	}
}