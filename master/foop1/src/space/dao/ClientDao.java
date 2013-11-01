package space.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.KeyCoordinator;
import org.mozartspaces.capi3.LabelCoordinator;
import org.mozartspaces.core.Capi;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.MzsConstants.RequestTimeout;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.TransactionReference;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.NotificationManager;
import org.mozartspaces.notifications.Operation;

import space.SpaceManager;
import space.exceptions.IDaoSaveException;
import client.model.Command;
import client.model.GameBonus;
import client.model.GameMap;
import client.model.Pacman;
import client.model.Wall;

public class ClientDao {
	private static Logger log = Logger.getLogger("ClientDao");
	
	public static final long TRANSACTION_TIMEOUT = MzsConstants.TransactionTimeout.INFINITE;
	public static final long SAVE_TIMEOUT = MzsConstants.RequestTimeout.INFINITE;
	public static final long READ_TIMEOUT = RequestTimeout.TRY_ONCE;
	public static final long TAKE_TIMEOUT = RequestTimeout.TRY_ONCE;
	
	private SpaceManager man;
	private Capi capi;
	private URI site_uri;
	private NotificationManager notifManager;
	private List<Notification> notifyList;
	
	public ClientDao() {
		man = SpaceManager.getInstance();
		capi = man.getCapi();
		notifManager = new NotificationManager(man.getCore());
		notifyList = new ArrayList<Notification>();
		
		try {
			site_uri = new URI(SpaceManager.SITE_URI+":"+SpaceManager.PORT);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* NOTIFICATION LISTENER METHODS ************************************************************** */
	
	public synchronized void registerMovementListener(NotificationListener listener) {
		Set<Operation> opSet = new HashSet<Operation>();
		opSet.add(Operation.WRITE);
		registerListener(listener,man.getContainer_pacmen(),opSet);
	}
	
	public synchronized void registerGameMapListener(NotificationListener listener) {
		Set<Operation> opSet = new HashSet<Operation>();
		opSet.add(Operation.WRITE);
		registerListener(listener,man.getContainer_gamemaps(),opSet);
	}
	
	public synchronized void registerWallListener(NotificationListener listener) {
		Set<Operation> opSet = new HashSet<Operation>();
		opSet.add(Operation.WRITE);
		registerListener(listener,man.getContainer_walls(),opSet);
	}
	
	public synchronized void registerBonusListener(NotificationListener listener) {
		Set<Operation> opSet = new HashSet<Operation>();
		opSet.add(Operation.WRITE);
		opSet.add(Operation.DELETE);
		opSet.add(Operation.TAKE);
		registerListener(listener,man.getContainer_boni(),opSet);
	}
	
	protected synchronized void registerListener(NotificationListener listener,ContainerReference ref,Set<Operation> op) {

		TransactionReference tx = null;
		try {
			// tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			notifyList.add(notifManager.createNotification(ref, listener, op, tx,null /* RequestContext */));
			// capi.commitTransaction(tx);
		} catch (MzsCoreException e) {
			log.warning("Error while creating notifications: " + e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public synchronized void deleteAllNotificationListener() {
		for(Notification n:notifyList) {
			try {
				n.destroy();
			} catch (MzsCoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	/* SEND COMMANDS ************************************************************** */
	public synchronized void sendCommand(Command c) throws IDaoSaveException {
		TransactionReference tx =null;
		try {
			//tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			capi.write(new Entry(c,FifoCoordinator.newCoordinationData()), man.getContainer_commands(), SAVE_TIMEOUT, tx);
			//capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.warning("Error while sending command "+c+". "+e.getMessage());
			/*if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.warning("Able to roll back.");
					throw new  IDaoSaveException(e.getMessage(), e.getCause());
				} catch(MzsCoreException e1) {
					throw new  IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}*/
			throw new  IDaoSaveException(e.getMessage(), e.getCause());
		}
	}
	
	/* GETTER METHODS ************************************************************** */
	
	public synchronized List<GameMap> getAllGameMaps() {
		TransactionReference tx =null;
		ArrayList<GameMap> teddies = new ArrayList<GameMap>();
		try {
			//tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			teddies = capi.read(man.getContainer_gamemaps(),Arrays.asList(FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL)),READ_TIMEOUT, tx);
			//capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.warning("Error while retrieving all maps: "+e.getMessage());
			/*if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.warning("Able to roll back.");
				} catch(MzsCoreException e1) {
					//throw new  IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}*/
			//throw new  IDaoSaveException(e.getMessage(), e.getCause());
		}
		return teddies;
	}
	
	public synchronized List<Pacman> getPacmenForMap(String gameMapId) {
		TransactionReference tx =null;
		ArrayList<Pacman> pacmen = new ArrayList<Pacman>();
		try {
			//tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			pacmen = capi.read(man.getContainer_pacmen(),Arrays.asList(LabelCoordinator.newSelector(gameMapId,MzsConstants.Selecting.COUNT_ALL)),READ_TIMEOUT, tx);
			//capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.warning("Error while retrieving all pacmen: "+e.getMessage());
			e.printStackTrace();
			/*if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.warning("Able to roll back.");
				} catch(MzsCoreException e1) {
					//throw new  IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}*/
			//throw new  IDaoSaveException(e.getMessage(), e.getCause());
		}
		return pacmen;
	}
	
	public synchronized List<Wall> getWallsForMap(String gameMapId) {
		TransactionReference tx =null;
		ArrayList<Wall> walls = new ArrayList<Wall>();
		try {
			//tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			walls = capi.read(man.getContainer_walls(),Arrays.asList(LabelCoordinator.newSelector(gameMapId,MzsConstants.Selecting.COUNT_ALL)),READ_TIMEOUT, tx);
			//capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.warning("Error while retrieving all walls: "+e.getMessage());
			e.printStackTrace();
			/*if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.warning("Able to roll back.");
				} catch(MzsCoreException e1) {
					//throw new  IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}*/
			//throw new  IDaoSaveException(e.getMessage(), e.getCause());
		}
		return walls;
	}
	
	public synchronized List<GameBonus> getBonusesForMap(String gameMapId) {
		TransactionReference tx =null;
		ArrayList<GameBonus> bonuses = new ArrayList<GameBonus>();
		try {
			//tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			bonuses = capi.read(man.getContainer_boni(),Arrays.asList(LabelCoordinator.newSelector(gameMapId,MzsConstants.Selecting.COUNT_ALL)),READ_TIMEOUT, tx);
			//capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			/*log.warning("Error while retrieving all bonuses: "+e.getMessage());
			e.printStackTrace();
			if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.warning("Able to roll back.");
				} catch(MzsCoreException e1) {
					//throw new  IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}*/
			//throw new  IDaoSaveException(e.getMessage(), e.getCause());
		}
		return bonuses;
	}
	
	public synchronized Pacman getPacman(String id) {
		TransactionReference tx =null;
		List<Pacman> pacman = new ArrayList<Pacman>();
		try {
			//tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			pacman = capi.read(man.getContainer_pacmen(),KeyCoordinator.newSelector(id),READ_TIMEOUT, tx);
			//capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.warning("Error while retrieving all maps: "+e.getMessage());
			/*if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.warning("Able to roll back.");
				} catch(MzsCoreException e1) {
					//throw new  IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}*/
			//throw new  IDaoSaveException(e.getMessage(), e.getCause());
		}
		return pacman.get(0);
	}


	/* GETTER AND SETTER ************************************************* */
	
	public SpaceManager getMan() {
		return man;
	}
	public Capi getCapi() {
		return capi;
	}
	public URI getSite_uri() {
		return site_uri;
	}
	public NotificationManager getNotifManager() {
		return notifManager;
	}
	public List<Notification> getNotifyList() {
		return notifyList;
	}
	
	
}
