package space;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.mozartspaces.capi3.Coordinator;
import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.ImplicitCoordinator;
import org.mozartspaces.capi3.KeyCoordinator;
import org.mozartspaces.capi3.LabelCoordinator;
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
	private static Logger log = Logger.getLogger("SpaceManager");
	
	public static final String CONTAINER_COMNMANDS = "Commands";
	public static final String CONTAINER_GAMEMAPS = "Gamemaps";
	public static final String CONTAINER_PACAMEN = "Pacmen";
	public static final String CONTAINER_WALLS = "Walls";
	public static final String CONTAINER_BONI = "Boni";
	
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
	
	private ContainerReference container_commands;
	private ContainerReference container_gamemaps;
	private ContainerReference container_pacmen;
	private ContainerReference container_walls;
	private ContainerReference container_boni;
	
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
			List<Coordinator> coords = Arrays.asList(new FifoCoordinator(),new KeyCoordinator());
			List<ImplicitCoordinator> coords2 = Arrays.asList(new FifoCoordinator(),new RandomCoordinator());
			List<Coordinator> coords3 = Arrays.asList(new FifoCoordinator(),new KeyCoordinator(),new LabelCoordinator());
			
			/* CONTAINERS *****************************************/
			
			try {
				container_commands = capi.lookupContainer(CONTAINER_COMNMANDS,spaceURI,LOOKUP_TIMEOUT,tx);
				log.info("Container "+CONTAINER_COMNMANDS+" found.");
			} catch(MzsCoreException e) {
				log.info("Container "+CONTAINER_COMNMANDS+" not found. Creating.");
				container_commands = capi.createContainer(CONTAINER_COMNMANDS,spaceURI,CREATE_TIMEOUT,coords2,null,tx);
			}
			
			try {
				container_gamemaps = capi.lookupContainer(CONTAINER_GAMEMAPS,spaceURI,LOOKUP_TIMEOUT,tx);
				log.info("Container "+CONTAINER_GAMEMAPS+" found.");
			} catch(MzsCoreException e) {
				log.info("Container "+CONTAINER_GAMEMAPS+" not found. Creating.");
				container_gamemaps = capi.createContainer(CONTAINER_GAMEMAPS,spaceURI,CREATE_TIMEOUT,coords,null,tx);
			}
			
			try {
				container_pacmen = capi.lookupContainer(CONTAINER_PACAMEN,spaceURI,LOOKUP_TIMEOUT,tx);
				log.info("Container "+CONTAINER_PACAMEN+" found.");
			} catch(MzsCoreException e) {
				log.info("Container "+CONTAINER_PACAMEN+" not found. Creating.");
				container_pacmen = capi.createContainer(CONTAINER_PACAMEN,spaceURI,CREATE_TIMEOUT,coords3,null,tx);
			}
			
			
			try {
				container_walls = capi.lookupContainer(CONTAINER_WALLS,spaceURI,LOOKUP_TIMEOUT,tx);
				log.info("Container "+CONTAINER_WALLS+" found.");
			} catch(MzsCoreException e) {
				log.info("Container "+CONTAINER_WALLS+" not found. Creating.");
				container_walls = capi.createContainer(CONTAINER_WALLS,spaceURI,CREATE_TIMEOUT,coords3,null,tx);
			}
			
			try {
				container_boni = capi.lookupContainer(CONTAINER_BONI,spaceURI,LOOKUP_TIMEOUT,tx);
				log.info("Container "+CONTAINER_BONI+" found.");
			} catch(MzsCoreException e) {
				log.info("Container "+CONTAINER_BONI+" not found. Creating.");
				container_boni = capi.createContainer(CONTAINER_BONI,spaceURI,CREATE_TIMEOUT,coords3,null,tx);
			}
			
	
		} catch(Exception e) {
			log.warning("Error while instanciating Space.");
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
			capi.destroyContainer(container_commands, null);
	        capi.destroyContainer(container_gamemaps, null);
	        capi.destroyContainer(container_pacmen, null);
	        capi.destroyContainer(container_walls, null);
	        capi.destroyContainer(container_boni, null);
	        
        } catch (Exception e) {
        	log.warning("Error while shutting down... \n"+e.getMessage());
			//e.printStackTrace();
		}
		// shutdown the core
        core.shutdown(true);
        log.info("Completed. Goodby!");
        
        System.exit(0);
	}


	public ContainerReference getContainer_commands() {
		return container_commands;
	}


	public ContainerReference getContainer_gamemaps() {
		return container_gamemaps;
	}


	public ContainerReference getContainer_pacmen() {
		return container_pacmen;
	}


	public ContainerReference getContainer_walls() {
		return container_walls;
	}


	public ContainerReference getContainer_boni() {
		return container_boni;
	}
	
}
