package dao.space;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.RandomCoordinator;
import org.mozartspaces.core.Capi;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.MzsConstants.RequestTimeout;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.TransactionReference;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.NotificationManager;
import org.mozartspaces.notifications.Operation;

import products.PartsContainer;
import products.parts.Arm;
import products.parts.Body;
import products.parts.Head;
import products.parts.Leg;
import products.parts.abstracts.Cap;
import products.parts.abstracts.Product;
import space.SpaceManager;
import dao.interfaces.IPartDao;
import exceptions.IDaoSaveException;

public class SpacePartDao implements IPartDao{
	
	private static Logger log = Logger.getLogger(SpacePartDao.class);
	private static final long TRANSACTION_TIMEOUT = MzsConstants.TransactionTimeout.INFINITE;
	private static final long SAVE_TIMEOUT = MzsConstants.RequestTimeout.INFINITE;
	private static final long READ_TIMEOUT = 0;
	private static final long TAKE_TIMEOUT = RequestTimeout.TRY_ONCE;
	
	private SpaceManager man;
	private Capi capi;
	private URI site_uri;
	private NotificationManager notifManager;
	private List<Notification> notifyList;
	
	public SpacePartDao() {
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
	
	public void registerPartsListener(NotificationListener listener) {
		Set<Operation> opSet = new HashSet<Operation>();
		opSet.add(Operation.WRITE);
		opSet.add(Operation.TAKE);
		opSet.add(Operation.DELETE);
		
		TransactionReference tx =null;
		try {
			//tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			notifyList.add(notifManager.createNotification(man.getContainer_arms(),listener,opSet,tx,null /*RequestContext*/));
			notifyList.add(notifManager.createNotification(man.getContainer_legs(),listener,opSet,tx,null /*RequestContext*/));
			notifyList.add(notifManager.createNotification(man.getContainer_heads(),listener,opSet,tx,null /*RequestContext*/));
			notifyList.add(notifManager.createNotification(man.getContainer_caps(),listener,opSet,tx,null /*RequestContext*/));
			notifyList.add(notifManager.createNotification(man.getContainer_bodies(),listener,opSet,tx,null /*RequestContext*/));
			
			//capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.error("Error while creating notifications part: "+e.getMessage());
			e.printStackTrace();
			/*if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.error("Able to roll back.");
				} catch(MzsCoreException e1) {
				}
			}*/
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void saveArm(Arm arm) throws IDaoSaveException {
		TransactionReference tx =null;
		try {
			tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			capi.write(new Entry(arm,FifoCoordinator.newCoordinationData()), man.getContainer_arms(), SAVE_TIMEOUT, tx);
			capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.error("Error while saving part "+arm+". "+e.getMessage());
			if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.error("Able to roll back.");
					throw new  IDaoSaveException(e.getMessage(), e.getCause());
				} catch(MzsCoreException e1) {
					throw new  IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}
			throw new  IDaoSaveException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public void saveLeg(Leg leg) throws IDaoSaveException {
		TransactionReference tx =null;
		try {
			tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			capi.write(new Entry(leg,FifoCoordinator.newCoordinationData()), man.getContainer_legs(), SAVE_TIMEOUT, tx);
			capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.error("Error while saving part "+leg+". "+e.getMessage());
			if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.error("Able to roll back.");
					throw new  IDaoSaveException(e.getMessage(), e.getCause());
				} catch(MzsCoreException e1) {
					throw new  IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}
			throw new  IDaoSaveException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public void saveBody(Body body) throws IDaoSaveException {
		TransactionReference tx =null;
		try {
			tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			capi.write(new Entry(body,FifoCoordinator.newCoordinationData()), man.getContainer_bodies(), SAVE_TIMEOUT, tx);
			capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.error("Error while saving part "+body+". "+e.getMessage());
			if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.error("Able to roll back.");
					throw new  IDaoSaveException(e.getMessage(), e.getCause());
				} catch(MzsCoreException e1) {
					throw new  IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}
			throw new  IDaoSaveException(e.getMessage(), e.getCause());
		}
		
	}

	@Override
	public void saveCap(Cap cap) throws IDaoSaveException {
		TransactionReference tx =null;
		try {
			tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			capi.write(new Entry(cap,FifoCoordinator.newCoordinationData()), man.getContainer_caps(), SAVE_TIMEOUT, tx);
			capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.error("Error while saving part "+cap+". "+e.getMessage());
			if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.error("Able to roll back.");
					throw new  IDaoSaveException(e.getMessage(), e.getCause());
				} catch(MzsCoreException e1) {
					throw new  IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}
			throw new  IDaoSaveException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public void saveHead(Head head) throws IDaoSaveException {
		TransactionReference tx =null;
		try {
			tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			capi.write(new Entry(head,FifoCoordinator.newCoordinationData()), man.getContainer_heads(), SAVE_TIMEOUT, tx);
			capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.error("Error while saving part "+head+". "+e.getMessage());
			if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.error("Able to roll back.");
					throw new  IDaoSaveException(e.getMessage(), e.getCause());
				} catch(MzsCoreException e1) {
					throw new  IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}
			throw new  IDaoSaveException(e.getMessage(), e.getCause());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PartsContainer getAllPartsAtomic() {
		TransactionReference tx =null;
		PartsContainer pc = new PartsContainer();
		try {
			
			/* blocking read */
			tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			capi.read(man.getContainer_arms(),Arrays.asList(FifoCoordinator.newSelector(2)),RequestTimeout.INFINITE, tx);
			capi.read(man.getContainer_legs(),Arrays.asList(FifoCoordinator.newSelector(2)),RequestTimeout.INFINITE, tx);
			capi.read(man.getContainer_heads(),Arrays.asList(FifoCoordinator.newSelector(1)),RequestTimeout.INFINITE, tx);
			capi.read(man.getContainer_caps(),Arrays.asList(FifoCoordinator.newSelector(1)),RequestTimeout.INFINITE, tx);
			capi.read(man.getContainer_bodies(),Arrays.asList(RandomCoordinator.newSelector(1)),RequestTimeout.INFINITE, tx);
			capi.commitTransaction(tx);
			
			tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			ArrayList<Arm> a = (ArrayList<Arm>)(ArrayList<?>) capi.take(man.getContainer_arms(),Arrays.asList(FifoCoordinator.newSelector(2)),TAKE_TIMEOUT, tx);
			ArrayList<Leg> l = (ArrayList<Leg>)(ArrayList<?>) capi.take(man.getContainer_legs(),Arrays.asList(FifoCoordinator.newSelector(2)),TAKE_TIMEOUT, tx);
			ArrayList<Head> h = (ArrayList<Head>)(ArrayList<?>) capi.take(man.getContainer_heads(),Arrays.asList(FifoCoordinator.newSelector(1)),TAKE_TIMEOUT, tx);
			ArrayList<Cap> c = (ArrayList<Cap>)(ArrayList<?>) capi.take(man.getContainer_caps(),Arrays.asList(FifoCoordinator.newSelector(1)),TAKE_TIMEOUT, tx);
			ArrayList<Body> b = (ArrayList<Body>)(ArrayList<?>) capi.take(man.getContainer_bodies(),Arrays.asList(RandomCoordinator.newSelector(1)),TAKE_TIMEOUT, tx);
			
			capi.commitTransaction(tx);
			
			
			log.info("All parts could be retreived");
			
			pc.setBody(b.get(0));
			pc.setCap(c.get(0));
			pc.setHead(h.get(0));
			pc.setLeftArm(a.get(0));
			pc.setRightArm(a.get(1));
			pc.setLeftLeg(l.get(0));
			pc.setRightLeg(l.get(1));
			
		} catch(MzsCoreException e) {
			log.error("Could not retreive all parts at once.");
			pc = null;
			if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.error("Able to roll back.");
				} catch(MzsCoreException e1) {
					//throw new  IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}
			
			//throw new  IDaoSaveException(e.getMessage(), e.getCause());
		}
		return pc;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllParts() {
		TransactionReference tx =null;
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			products.addAll((ArrayList<Product>)(ArrayList<?>) capi.read(man.getContainer_arms(),Arrays.asList(FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL)),READ_TIMEOUT, tx));
			products.addAll((ArrayList<Product>)(ArrayList<?>) capi.read(man.getContainer_legs(),Arrays.asList(FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL)),READ_TIMEOUT, tx));
			products.addAll((ArrayList<Product>)(ArrayList<?>) capi.read(man.getContainer_heads(),Arrays.asList(FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL)),READ_TIMEOUT, tx));
			products.addAll((ArrayList<Product>)(ArrayList<?>) capi.read(man.getContainer_caps(),Arrays.asList(FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL)),READ_TIMEOUT, tx));
			products.addAll((ArrayList<Product>)(ArrayList<?>) capi.read(man.getContainer_bodies(),Arrays.asList(FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL)),READ_TIMEOUT, tx));
			
			capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.error("Error while retrieving getAllParts: "+e.getMessage());
			if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.error("Able to roll back.");
				} catch(MzsCoreException e1) {
					//throw new  IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}
			//throw new  IDaoSaveException(e.getMessage(), e.getCause());
		}
		return products;
	}
	
}
