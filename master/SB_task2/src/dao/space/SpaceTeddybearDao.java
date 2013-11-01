package dao.space;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.mozartspaces.capi3.CoordinationData;
import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.LindaCoordinator;
import org.mozartspaces.core.Capi;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.TransactionReference;
import org.mozartspaces.core.MzsConstants.RequestTimeout;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.NotificationManager;
import org.mozartspaces.notifications.Operation;

import products.Teddybear;
import products.factory.ProductsFactory;
import space.SpaceManager;
import dao.interfaces.ITeddybearDao;
import exceptions.IDaoReadException;
import exceptions.IDaoSaveException;

public class SpaceTeddybearDao implements ITeddybearDao {
	
	private static Logger log = Logger.getLogger(SpaceTeddybearDao.class);
	
	private static final long TRANSACTION_TIMEOUT = MzsConstants.TransactionTimeout.INFINITE;
	private static final long SAVE_TIMEOUT = MzsConstants.RequestTimeout.INFINITE;
	private static final long READ_TIMEOUT = 0;
	private static final long TAKE_TIMEOUT = RequestTimeout.TRY_ONCE;
	
	private SpaceManager man;
	private Capi capi;
	private URI site_uri;
	private NotificationManager notifManager;
	private List<Notification> notifyList;
	
	public SpaceTeddybearDao() {
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
	
	private void registerTeddyListener(NotificationListener listener,ContainerReference ref) {
		Set<Operation> opSet = new HashSet<Operation>();
		opSet.add(Operation.WRITE);
		opSet.add(Operation.TAKE);
		opSet.add(Operation.DELETE);
		
		TransactionReference tx =null;
		try {
			//tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			notifyList.add(notifManager.createNotification(ref,listener,opSet,tx,null /*RequestContext*/));
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
	
	public void registerAssembledListener(NotificationListener listener) {
		registerTeddyListener(listener,man.getContainer_assmebled());
	}
	public void registerCheckedListener(NotificationListener listener) {
		registerTeddyListener(listener,man.getContainer_checked());
	}
	public void registerShippedListener(NotificationListener listener) {
		registerTeddyListener(listener,man.getContainer_shipped());
	}
	public void registerDefectListener(NotificationListener listener) {
		registerTeddyListener(listener,man.getContainer_defect());
	}
	
	
	private void save(Teddybear teddybear,ContainerReference ref,CoordinationData coord) throws IDaoSaveException {
		TransactionReference tx =null;
		try {
			tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			capi.write(new Entry(teddybear,coord), ref, SAVE_TIMEOUT, tx);
			capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.error("Error while saving teddybear "+teddybear+". "+e.getMessage());
			if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.error("Able to roll back.");
				} catch(MzsCoreException e1) {
					throw new  IDaoSaveException(e1.getMessage(), e1.getCause());
				}
			}
			throw new  IDaoSaveException(e.getMessage(), e.getCause());
		}
	}
	
	
	public void saveToAssembled(Teddybear teddybear) throws IDaoSaveException {
		save(teddybear,man.getContainer_assmebled(),LindaCoordinator.newCoordinationData());
	}

	@Override
	public void saveToChecked(Teddybear teddybear) throws IDaoSaveException {
		save(teddybear,man.getContainer_checked(),FifoCoordinator.newCoordinationData());
	}
	@Override
	public void saveToDefect(Teddybear teddybear) throws IDaoSaveException {
		save(teddybear,man.getContainer_defect(),FifoCoordinator.newCoordinationData());
	}
	@Override
	public void saveToShipping(Teddybear teddybear) throws IDaoSaveException {
		save(teddybear,man.getContainer_shipped(),FifoCoordinator.newCoordinationData());
	}
	
	private List<Teddybear> getAllFromContainer(ContainerReference ref) {
		TransactionReference tx =null;
		ArrayList<Teddybear> teddies = new ArrayList<Teddybear>();
		try {
			tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			teddies = capi.read(ref,Arrays.asList(FifoCoordinator.newSelector(MzsConstants.Selecting.COUNT_ALL)),READ_TIMEOUT, tx);
			capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.error("Error while retrieving all teddies: "+e.getMessage());
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
		return teddies;
	}

	@Override
	public List<Teddybear> getAssembled() {
		return getAllFromContainer(man.getContainer_assmebled());
	}


	@Override
	public List<Teddybear> getShipped() {
		return getAllFromContainer(man.getContainer_shipped());
	}


	@Override
	public List<Teddybear> getDefect() {
		return getAllFromContainer(man.getContainer_defect());
	}


	@Override
	public List<Teddybear> getChecked() {
		return getAllFromContainer(man.getContainer_checked());
	}

	@Override
	public Teddybear takeAssembledForDefectCheck() throws IDaoReadException {
		return takeAssembled(ProductsFactory.createDefectCheckTemplate());
	}
	
	@Override
	public Teddybear takeAssembledForWeightCheck() throws IDaoReadException {
		return takeAssembled(ProductsFactory.createWeightCheckTemplate());
	}
	
	public Teddybear takeAssembled(Teddybear template) throws IDaoReadException {
		TransactionReference tx =null;
		List<Teddybear> list = new ArrayList<Teddybear>();
		try {
			/* blocking read */
			capi.read(man.getContainer_assmebled(),Arrays.asList(LindaCoordinator.newSelector(template,1)),RequestTimeout.INFINITE, null);
			
			tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			//capi.take(new Entry(teddybear), ref, MzsConstants.RequestTimeout.INFINITE, tx);
			list = capi.take(man.getContainer_assmebled(),Arrays.asList(LindaCoordinator.newSelector(template,1)),TAKE_TIMEOUT, tx);
			capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.error("Error while takeing teddybear. "+e.getMessage());
			if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.error("Able to roll back.");
					throw new  IDaoReadException(e.getMessage(), e.getCause());
				} catch(MzsCoreException e1) {
					throw new  IDaoReadException(e1.getMessage(), e1.getCause());
				}
			}
			throw new  IDaoReadException(e.getMessage(), e.getCause());
		}
		
		return list.get(0);
	}


	@Override
	public Teddybear takeChecked() throws IDaoReadException {
		TransactionReference tx =null;
		List<Teddybear> list = new ArrayList<Teddybear>();
		try {
			/* blocking read */
			capi.read(man.getContainer_checked(),Arrays.asList(FifoCoordinator.newSelector(1)),RequestTimeout.INFINITE, null);
			
			tx = capi.createTransaction(TRANSACTION_TIMEOUT, site_uri);
			//capi.take(new Entry(teddybear), ref, MzsConstants.RequestTimeout.INFINITE, tx);
			list = capi.take(man.getContainer_checked(),Arrays.asList(FifoCoordinator.newSelector(1)),TAKE_TIMEOUT, tx);
			capi.commitTransaction(tx);
		} catch(MzsCoreException e) {
			log.error("Error while takeing teddybear. "+e.getMessage());
			if(tx != null) {
				try {
					capi.rollbackTransaction(tx);
					log.error("Able to roll back.");
					throw new  IDaoReadException(e.getMessage(), e.getCause());
				} catch(MzsCoreException e1) {
					throw new  IDaoReadException(e1.getMessage(), e1.getCause());
				}
			}
			throw new  IDaoReadException(e.getMessage(), e.getCause());
		}
		
		return list.get(0);
	}

}
