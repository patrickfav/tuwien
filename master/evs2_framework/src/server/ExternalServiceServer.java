package server;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import server.handler.ExternalHttpRequestHandler;

public class ExternalServiceServer {
	private static Logger log = Logger.getLogger(ExternalServiceServer.class);
	
	private static final int MAX_IDLE_TIME = 30000;
	private static final int REQUEST_HEADERSIZE = 8192;
	private static final int MAX_REQUEST_THREADS = 50;
	
	private int port;
	private String host;
	private Server server;
	private ServerThread serverThread;
	
	public ExternalServiceServer(String host, int port) throws Exception {
		log.info("Starting to initialize external service...");
		
		this.port = port;
		this.host = host;
		
		/* allocation for server thread */
		serverThread = new ServerThread();
	}
	
	public void startServer() {
		serverThread.start();
	}
	
	public void stopServer() throws Exception {
		log.info("Stopping external service.");
		serverThread.stopServer();
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
	
	public class ServerThread extends Thread{
		
		public ServerThread() throws Exception {
			server = new Server();
			
			SelectChannelConnector httpConnector = new SelectChannelConnector();
			httpConnector.setHost(host);
			httpConnector.setPort(port);
			httpConnector.setMaxIdleTime(MAX_IDLE_TIME);
			httpConnector.setLowResourcesMaxIdleTime(MAX_IDLE_TIME);
			httpConnector.setRequestHeaderSize(REQUEST_HEADERSIZE);
			httpConnector.setThreadPool(new QueuedThreadPool(MAX_REQUEST_THREADS));
	
			server.setConnectors(new Connector[] { httpConnector });

			server.setHandler(new ExternalHttpRequestHandler());
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
