package server;

import java.io.File;
import java.net.InetAddress;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import indexing.AnnotationsCrawler;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import persistence.HibernateManager;
import server.handler.HttpRequestHandler;

public class RestServer {
	
	private static Logger log = Logger.getLogger(RestServer.class);
	
	private static final int MAX_IDLE_TIME = 30000;
	private static final int REQUEST_HEADERSIZE = 8192;
	private static final int MAX_REQUEST_THREADS = 50;
	private static final String SERVER_PK_PATH = "keys/server.pem";
	
	public static final String SEARCH_URL_KEYWORD = "search";
	
	private int port;
	private String host;
	private Server server;
	private ServerThread serverThread;
	private HibernateManager hbManager;
	
	public RestServer(String host, int port) throws Exception {
		new RestServer(host,port, new HashMap<InetAddress, PublicKey>());
	}
	
	public RestServer(String host, int port, Map<InetAddress, PublicKey> clientKeys) throws Exception {
		log.info("Starting to initialize server...");
		
		this.port = port;
		this.host = host;
		
		/* index annotations */
		AnnotationsCrawler crawler = new AnnotationsCrawler();
		crawler.indexClasspath();
		
		/* start db */
		hbManager = HibernateManager.getInstance();
		
		/* allocation for server thread */
		serverThread = new ServerThread(clientKeys);
		
		
	}
	
	public void startServer() {
		serverThread.start();
	}
	
	public void stopServer() throws Exception {
		log.info("Stopping Webserver and closing DB");
		serverThread.stopServer();
		hbManager.getEm().close();
		hbManager.getEmf().close();
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
	
	public class ServerThread extends Thread{
		
		public ServerThread(Map<InetAddress, PublicKey> clientKeys) {
			server = new Server();
			
			SelectChannelConnector httpConnector = new SelectChannelConnector();
			httpConnector.setHost(host);
			httpConnector.setPort(port);
			httpConnector.setMaxIdleTime(MAX_IDLE_TIME);
			httpConnector.setLowResourcesMaxIdleTime(MAX_IDLE_TIME);
			httpConnector.setRequestHeaderSize(REQUEST_HEADERSIZE);
			httpConnector.setThreadPool(new QueuedThreadPool(MAX_REQUEST_THREADS));
	
			server.setConnectors(new Connector[] { httpConnector });

			server.setHandler(new HttpRequestHandler(SERVER_PK_PATH,clientKeys));
		}
		
		@Override
		public void run() {
			log.info("Starting Jetty Webserver with host address " + host + " and port "
					+ port);
			try {
				server.start();
				server.join();
			} catch (Exception e) {
				log.fatal("Could not start Server. Shutting down.");
				e.printStackTrace();
			}
		}
		
		public void stopServer() throws Exception {
			log.info("Stopping server...");
			server.stop();
		}
	}
}
