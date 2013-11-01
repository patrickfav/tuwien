package space.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.KeyCoordinator;
import org.mozartspaces.capi3.LabelCoordinator;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.TransactionReference;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.Operation;

import space.exceptions.IDaoSaveException;
import client.model.GameBonus;
import client.model.GameMap;
import client.model.Pacman;
import client.model.Wall;

public class ServerDao extends ClientDao{
	private static Logger log = Logger.getLogger("ServerDao");

	public ServerDao() {
		super();
	}
	
	/* NOTIFICATION LISTENER METHODS ************************************************************** */
	
	public synchronized void registerCommandListener(NotificationListener listener) {
		Set<Operation> opSet = new HashSet<Operation>();
		opSet.add(Operation.WRITE);
		registerListener(listener,getMan().getContainer_commands(),opSet);
	}
	
	/* SAVE METHODS ************************************************************** */
	
	public synchronized void createMap(GameMap m, List<Pacman> pacmen, List<Wall> walls,
			List<GameBonus> bonuses) throws IDaoSaveException {
		TransactionReference tx = null;
		try {
			tx = getCapi().createTransaction(TRANSACTION_TIMEOUT, getSite_uri());
			getCapi().write(new Entry(m, FifoCoordinator.newCoordinationData(),KeyCoordinator.newCoordinationData(m.getId())), getMan().getContainer_gamemaps(), SAVE_TIMEOUT, tx);

			for (Pacman p : pacmen) {
				getCapi().write(new Entry(p, FifoCoordinator.newCoordinationData(),KeyCoordinator.newCoordinationData(p.getId()),LabelCoordinator.newCoordinationData(p.getGameMapId())), getMan().getContainer_pacmen(), SAVE_TIMEOUT, tx);
			}

			for (Wall w : walls) {
				getCapi().write(new Entry(w, FifoCoordinator.newCoordinationData(),KeyCoordinator.newCoordinationData(w.getId()),LabelCoordinator.newCoordinationData(w.getGameMapId())), getMan().getContainer_walls(), SAVE_TIMEOUT, tx);
			}

			for (GameBonus g : bonuses) {
				getCapi().write(new Entry(g, FifoCoordinator.newCoordinationData(), KeyCoordinator.newCoordinationData(g.getId()), LabelCoordinator.newCoordinationData(g .getGameMapId())), getMan().getContainer_boni(), SAVE_TIMEOUT, tx);
			}

			getCapi().commitTransaction(tx);
		} catch (MzsCoreException e) {
			log.warning("Error while saving  " + m + ". " + e.getMessage());
			if (tx != null) {
				try {
					getCapi().rollbackTransaction(tx);
					log.warning("Able to roll back.");
					throw new IDaoSaveException(e.getMessage(), e.getCause());
				} catch (MzsCoreException e1) {
					throw new IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}
			throw new IDaoSaveException(e.getMessage(), e.getCause());
		}

	}

	public synchronized void saveBonuses(List<GameBonus> bonuses) throws IDaoSaveException {
		TransactionReference tx = null;
		try {
			tx = getCapi().createTransaction(TRANSACTION_TIMEOUT, getSite_uri());
			for (GameBonus g : bonuses) {
				getCapi().write(
						new Entry(g, FifoCoordinator.newCoordinationData(),
								KeyCoordinator.newCoordinationData(g.getId()),
								LabelCoordinator.newCoordinationData(g
										.getGameMapId())), getMan()
								.getContainer_boni(), SAVE_TIMEOUT, tx);
			}

			getCapi().commitTransaction(tx);
		} catch (MzsCoreException e) {
			log.warning("Error while saving  " + bonuses + ". " + e.getMessage());
			if (tx != null) {
				try {
					getCapi().rollbackTransaction(tx);
					log.warning("Able to roll back.");
					throw new IDaoSaveException(e.getMessage(), e.getCause());
				} catch (MzsCoreException e1) {
					throw new IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}
			throw new IDaoSaveException(e.getMessage(), e.getCause());
		}

	}

	
	/* GETTER METHODS ************************************************************** */
	
	public synchronized GameMap getMap(String id) {
		TransactionReference tx =null;
		List<GameMap> gm = new ArrayList<GameMap>();
		try {
			//tx = getCapi().createTransaction(TRANSACTION_TIMEOUT, getSite_uri());
			gm = getCapi().read(getMan().getContainer_gamemaps(),KeyCoordinator.newSelector(id),READ_TIMEOUT, tx);
			//getCapi().commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.warning("Error while retrieving all maps: "+e.getMessage());
			/*if(tx != null) {
				try {
					getCapi().rollbackTransaction(tx);
					log.warning("Able to roll back.");
				} catch(MzsCoreException e1) {
					//throw new  IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}*/
			//throw new  IDaoSaveException(e.getMessage(), e.getCause());
		}
		return gm.get(0);
	}
	
	/* UPDATE METHODS ************************************************************** */
	
	public synchronized void updatePacman(Pacman p) throws IDaoSaveException {
		TransactionReference tx = null;
		try {
			//tx = getCapi().createTransaction(TRANSACTION_TIMEOUT, getSite_uri());
			getCapi().take(getMan().getContainer_pacmen(),
					KeyCoordinator.newSelector(p.getId()), TAKE_TIMEOUT, tx);

			getCapi().write(new Entry(p, FifoCoordinator.newCoordinationData(),
					KeyCoordinator.newCoordinationData(p.getId()),
					LabelCoordinator.newCoordinationData(p.getGameMapId())),
					getMan().getContainer_pacmen(), SAVE_TIMEOUT, tx);

			//getCapi().commitTransaction(tx);
		} catch (MzsCoreException e) {
			log.warning("Error while updating pacman " + p + ". " + e.getMessage());
			/*if (tx != null) {
				try {
					getCapi().rollbackTransaction(tx);
					log.warning("Able to roll back.");
					throw new IDaoSaveException(e.getMessage(), e.getCause());
				} catch (MzsCoreException e1) {
					throw new IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}*/
			throw new IDaoSaveException(e.getMessage(), e.getCause());
		}
	}
	
	public synchronized void updateWall(Wall w) throws IDaoSaveException {
		TransactionReference tx = null;
		try {
			//tx = getCapi().createTransaction(TRANSACTION_TIMEOUT, getSite_uri());
			
			
			getCapi().take(getMan().getContainer_walls(),
						KeyCoordinator.newSelector(w.getId()), TAKE_TIMEOUT, tx);
			
			
			
			getCapi().write(
						new Entry(w, FifoCoordinator.newCoordinationData(),
								KeyCoordinator.newCoordinationData(w.getId()),
								LabelCoordinator.newCoordinationData(w
										.getGameMapId())), getMan()
								.getContainer_walls(), SAVE_TIMEOUT, tx);
			

			//getCapi().commitTransaction(tx);
		} catch (MzsCoreException e) {
			log.warning("Error while saving  " + w + ". " + e.getMessage());
			/*if (tx != null) {
				try {
					getCapi().rollbackTransaction(tx);
					log.warning("Able to roll back.");
					throw new IDaoSaveException(e.getMessage(), e.getCause());
				} catch (MzsCoreException e1) {
					throw new IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}*/
			throw new IDaoSaveException(e.getMessage(), e.getCause());
		}

	}
	
	public synchronized void updateMap(GameMap m) throws IDaoSaveException {
		TransactionReference tx = null;
		try {
			//tx = getCapi().createTransaction(TRANSACTION_TIMEOUT, getSite_uri());
			getCapi().take(getMan().getContainer_gamemaps(),
					KeyCoordinator.newSelector(m.getId()), TAKE_TIMEOUT, tx);

			getCapi().write(new Entry(m, FifoCoordinator.newCoordinationData(),
					KeyCoordinator.newCoordinationData(m.getId())),
					getMan().getContainer_gamemaps(), SAVE_TIMEOUT, tx);

			//getCapi().commitTransaction(tx);
		} catch (MzsCoreException e) {
			log.warning("Error while updating pacman " + m + ". " + e.getMessage());
			/*if (tx != null) {
				try {
					getCapi().rollbackTransaction(tx);
					log.warning("Able to roll back.");
					throw new IDaoSaveException(e.getMessage(), e.getCause());
				} catch (MzsCoreException e1) {
					throw new IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}*/
			throw new IDaoSaveException(e.getMessage(), e.getCause());
		}
	}
	
	/* DELETE METHODS ************************************************************** */
	
	public synchronized void deleteBonus(String id) {
		TransactionReference tx =null;
		try {
			//tx = getCapi().createTransaction(TRANSACTION_TIMEOUT, getSite_uri());
			getCapi().take(getMan().getContainer_boni(),KeyCoordinator.newSelector(id),TAKE_TIMEOUT, tx);
			//getCapi().commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.warning("Error while retrieving deleting bonus "+e.getMessage());
			/*if(tx != null) {
				try {
					getCapi().rollbackTransaction(tx);
					log.warning("Able to roll back.");
				} catch(MzsCoreException e1) {
					//throw new  IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}*/
			//throw new  IDaoSaveException(e.getMessage(), e.getCause());
		}
	}
}
