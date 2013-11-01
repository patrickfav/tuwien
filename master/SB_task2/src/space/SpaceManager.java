package space;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.mozartspaces.capi3.Coordinator;
import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.ImplicitCoordinator;
import org.mozartspaces.capi3.LabelCoordinator;
import org.mozartspaces.capi3.LindaCoordinator;
import org.mozartspaces.capi3.RandomCoordinator;
import org.mozartspaces.core.Capi;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.DefaultMzsCore;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.MzsConstants.RequestTimeout;
import org.mozartspaces.core.MzsCore;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.TransactionReference;

public class SpaceManager {
	private static Logger log = Logger.getLogger(SpaceManager.class);
	
	public static final String CONTAINER_AESSEMBLED = "Assembled Teddybears";
	public static final String CONTAINER_CHECKED = "Checked Teddybears";
	public static final String CONTAINER_SHIPPED = "Shipped Teddybears";
	public static final String CONTAINER_DEFECT = "Defect Teddybears";
	
	public static final String CONTAINER_PARTS_HEADS = "Parts: Heads";
	public static final String CONTAINER_PARTS_BODIES = "Parts: Bodies";
	public static final String CONTAINER_PARTS_ARMS = "Parts: Arms";
	public static final String CONTAINER_PARTS_LEGS = "Parts: Legs";
	public static final String CONTAINER_PARTS_CAPS = "Parts: Caps";
	
	public static final long LOOKUP_TIMEOUT = RequestTimeout.TRY_ONCE;
	public static final int CREATE_TIMEOUT = MzsConstants.Container.UNBOUNDED;
	public static final String SITE_URI = "xvsm://localhost";
	public static final int PORT = 53143;

	public static final int ACCESS_TYPE_SERVER = 0;
	public static final int ACCESS_TYPE_WORKER = 1;
	
	private static int access_mode = ACCESS_TYPE_WORKER;
	
	private MzsCore core;
	private Capi capi;
	private URI spaceURI;
	
	private ContainerReference container_assmebled;
	private ContainerReference container_checked;
	private ContainerReference container_shipped;
	private ContainerReference container_defect;
	private ContainerReference container_heads;
	private ContainerReference container_bodies;
	private ContainerReference container_arms;
	private ContainerReference container_legs;
	private ContainerReference container_caps;
	
	private static SpaceManager spaceManager;
	
	public static SpaceManager getInstance() {
		if(spaceManager == null) {
			try {
				
				return spaceManager = new SpaceManager(access_mode);
			} catch (MzsCoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return spaceManager;
	}
	
	
	public static void setMode(int accessType) {
		access_mode = accessType;
	}
	
	private SpaceManager(int accessType) throws MzsCoreException {
		TransactionReference tx = null;
		
		int core_port = 0;
		
		if(accessType == ACCESS_TYPE_SERVER) {
			log.info("======= SERVER MODE =======");
			core_port = PORT;
		}
		
		log.info("Creating Mozartpace...");
		try {
			core = DefaultMzsCore.newInstance(core_port);
			capi = new Capi(core);
			
			log.info("Looking up containers...");
			//tx = capi.createTransaction(MzsConstants.TransactionTimeout.INFINITE, null);
			
			spaceURI = new URI(SITE_URI+":"+PORT);
			List<ImplicitCoordinator> coords = Arrays.asList(new FifoCoordinator(),new RandomCoordinator());
			List<ImplicitCoordinator> coords2 = Arrays.asList(new FifoCoordinator(),new LindaCoordinator());
			/* TEDDY CONTAINERS *****************************************/
			
			try {
				container_assmebled = capi.lookupContainer(CONTAINER_AESSEMBLED,spaceURI,LOOKUP_TIMEOUT,tx);
				log.info("Container "+CONTAINER_AESSEMBLED+" found.");
			} catch(MzsCoreException e) {
				log.info("Container "+CONTAINER_AESSEMBLED+" not found. Creating.");
				container_assmebled = capi.createContainer(CONTAINER_AESSEMBLED,spaceURI,CREATE_TIMEOUT,coords2,null,tx);
			}
			
			try {
				container_checked = capi.lookupContainer(CONTAINER_CHECKED,spaceURI,LOOKUP_TIMEOUT,tx);
				log.info("Container "+CONTAINER_CHECKED+" found.");
			} catch(MzsCoreException e) {
				log.info("Container "+CONTAINER_CHECKED+" not found. Creating.");
				container_checked = capi.createContainer(CONTAINER_CHECKED,spaceURI,CREATE_TIMEOUT,coords,null,tx);
			}
			
			try {
				container_shipped = capi.lookupContainer(CONTAINER_SHIPPED,spaceURI,LOOKUP_TIMEOUT,tx);
				log.info("Container "+CONTAINER_SHIPPED+" found.");
			} catch(MzsCoreException e) {
				log.info("Container "+CONTAINER_SHIPPED+" not found. Creating.");
				container_shipped = capi.createContainer(CONTAINER_SHIPPED,spaceURI,CREATE_TIMEOUT,coords,null,tx);
			}
			
			try {
				container_defect = capi.lookupContainer(CONTAINER_DEFECT,spaceURI,LOOKUP_TIMEOUT,tx);
				log.info("Container "+CONTAINER_DEFECT+" found.");
			} catch(MzsCoreException e) {
				log.info("Container "+CONTAINER_DEFECT+" not found. Creating.");
				container_defect = capi.createContainer(CONTAINER_DEFECT,spaceURI,CREATE_TIMEOUT,coords,null,tx);
			}
			
			/* PARTS CONTAINERS *****************************************/
			
			try {
				container_heads = capi.lookupContainer(CONTAINER_PARTS_HEADS,spaceURI,LOOKUP_TIMEOUT,tx);
				log.info("Container "+CONTAINER_PARTS_HEADS+" found.");
			} catch(MzsCoreException e) {
				log.info("Container "+CONTAINER_PARTS_HEADS+" not found. Creating.");
				container_heads = capi.createContainer(CONTAINER_PARTS_HEADS,spaceURI,CREATE_TIMEOUT,coords,null,tx);
			}	
			try {
				container_bodies = capi.lookupContainer(CONTAINER_PARTS_BODIES,spaceURI,LOOKUP_TIMEOUT,tx);
				log.info("Container "+CONTAINER_PARTS_BODIES+" found.");
			} catch(MzsCoreException e) {
				log.info("Container "+CONTAINER_PARTS_BODIES+" not found. Creating.");
				container_bodies = capi.createContainer(CONTAINER_PARTS_BODIES,spaceURI,CREATE_TIMEOUT,coords,null,tx);
			}
			try {
				container_arms = capi.lookupContainer(CONTAINER_PARTS_ARMS,spaceURI,LOOKUP_TIMEOUT,tx);
				log.info("Container "+CONTAINER_PARTS_ARMS+" found.");
			} catch(MzsCoreException e) {
				log.info("Container "+CONTAINER_PARTS_ARMS+" not found. Creating.");
				container_arms = capi.createContainer(CONTAINER_PARTS_ARMS,spaceURI,CREATE_TIMEOUT,coords,null,tx);
	        }		
			try {
				container_legs = capi.lookupContainer(CONTAINER_PARTS_LEGS,spaceURI,LOOKUP_TIMEOUT,tx);
				log.info("Container "+CONTAINER_PARTS_LEGS+" found.");
			} catch(MzsCoreException e) {
				log.info("Container "+CONTAINER_PARTS_LEGS+" not found. Creating.");
				container_legs = capi.createContainer(CONTAINER_PARTS_LEGS,spaceURI,CREATE_TIMEOUT,coords,null,tx);
			}		
			try {
				container_caps = capi.lookupContainer(CONTAINER_PARTS_CAPS,spaceURI,LOOKUP_TIMEOUT,tx);
				log.info("Container "+CONTAINER_PARTS_CAPS+" found.");
			} catch(MzsCoreException e) {
				log.info("Container "+CONTAINER_PARTS_CAPS+" not found. Creating.");
				container_caps = capi.createContainer(CONTAINER_PARTS_CAPS,spaceURI,CREATE_TIMEOUT,coords,null,tx);
			}	
	
		} catch(Exception e) {
			log.error("Error while instanciating Space.");
			shutdown();
		}
	}
	
	public Capi getCapi() {
		return capi;
	}
	
	public MzsCore getCore() {
		return core;
	}
	
	public void shutdown() {
		log.info("Shutting Space down...");
		 // destroy the container
        try {
			capi.destroyContainer(container_assmebled, null);
	        capi.destroyContainer(container_checked, null);
	        capi.destroyContainer(container_shipped, null);
	        capi.destroyContainer(container_defect, null);
	        capi.destroyContainer(container_heads, null);
	        capi.destroyContainer(container_bodies, null);
	        capi.destroyContainer(container_arms, null);
	        capi.destroyContainer(container_legs, null);
	        capi.destroyContainer(container_caps, null);
        } catch (Exception e) {
        	log.error("Error while shutting down...");
			e.printStackTrace();
		}
		// shutdown the core
        core.shutdown(true);
        log.info("Completed. Goodby!");
	}


	public ContainerReference getContainer_assmebled() {
		return container_assmebled;
	}

	public ContainerReference getContainer_checked() {
		return container_checked;
	}

	public ContainerReference getContainer_shipped() {
		return container_shipped;
	}

	public ContainerReference getContainer_defect() {
		return container_defect;
	}

	public ContainerReference getContainer_heads() {
		return container_heads;
	}

	public ContainerReference getContainer_bodies() {
		return container_bodies;
	}

	public ContainerReference getContainer_arms() {
		return container_arms;
	}

	public ContainerReference getContainer_legs() {
		return container_legs;
	}

	public ContainerReference getContainer_caps() {
		return container_caps;
	}
	
	
}
