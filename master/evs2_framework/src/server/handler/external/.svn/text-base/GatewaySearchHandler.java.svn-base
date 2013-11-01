package server.handler.external;

import indexing.IndexStorage;
import indexing.entities.IndexedService;
import indexing.entities.RestRequest;
import indexing.entities.lookup.Storage;
import indexing.entities.lookup.StorageLookupResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.naming.directory.SearchResult;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpExchange;
import org.eclipse.jetty.server.Request;

import com.google.gson.Gson;

import constants.ContentType;

import security.SecureChannelAsym;
import server.RestServer;
import server.handler.AbstractRestHandler;
import server.handler.crudhandler.entities.SearchResults;

public class GatewaySearchHandler extends AbstractRestHandler {
	
	private static Logger log = Logger.getLogger(GatewaySearchHandler.class);
	
	private static HttpClient httpClient;
	private ContentExchange exchange;
	private String locationIndexHost;

	private static final long HTTP_SERVICE_TIMEOUT = 10000;
	private static final String STORAGE_SERVICE_METHOD = "storages";
	
	private static final String INDEX_SERVICE_INTERRUPT = "A timeout occured while requesting Location Index.";
	private static final String INDEX_SERVICE_ERROR = "An error ocured while contacting the Location Index.";
	private static final String LOOKUP_ERROR = "A lookup error occured.";
	
	private static String GATEWAY_PRIVATEKEY="keys/location1.pem";
	private static String EXTERNAL_SERVICE_PUBLICKEY="keys/externalservice.pub.pem";
	
	public static SecureChannelAsym scAsymGateway;
	
	public GatewaySearchHandler(String locationIndexHost) throws Exception{
		log.debug("Startet GatewaySearchHandler with Index " + locationIndexHost);
		httpClient = new HttpClient();
		httpClient.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
		httpClient.setTimeout(HTTP_SERVICE_TIMEOUT);
		httpClient.start();
		this.locationIndexHost = locationIndexHost;
		
		try {
			if(scAsymGateway == null)
				scAsymGateway = new SecureChannelAsym(GATEWAY_PRIVATEKEY,EXTERNAL_SERVICE_PUBLICKEY);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void handle(String arg0, Request arg1, HttpServletRequest arg2, HttpServletResponse arg3) throws IOException, ServletException {
		throw new ServletException("unsupported method");
	}

	public void handle(RestRequest req, HttpServletResponse response) throws IOException{

		exchange = new ContentExchange();
		exchange.setMethod("GET");
		exchange.setURL(locationIndexHost + STORAGE_SERVICE_METHOD);
		
		httpClient.send(exchange);
		
		int exchangeState;
		try {
			exchangeState = exchange.waitForDone();
		} catch (InterruptedException e) {
			response.getWriter().println(getErrorPage(INDEX_SERVICE_INTERRUPT));
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		if(!(exchangeState == HttpExchange.STATUS_COMPLETED) || !(exchange.getResponseStatus() == HttpServletResponse.SC_OK)){
			response.getWriter().println(getErrorPage(INDEX_SERVICE_ERROR));
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		try {
			ByteArrayInputStream input = new ByteArrayInputStream (exchange.getResponseContent().getBytes()); 
			JAXBContext jc = JAXBContext.newInstance (StorageLookupResponse.class, Storage.class);
			Unmarshaller u = jc.createUnmarshaller();
			StorageLookupResponse lookupResponse = (StorageLookupResponse) u.unmarshal(input);
			
			if(lookupResponse == null || lookupResponse.getStorages() == null || lookupResponse.getStorages().size() == 0){
				response.getWriter().println(getErrorPage(LOOKUP_ERROR));
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			
			List<Object> allResults = new ArrayList<Object>();
			String contentTypeResponse = "text/xml;charset=utf-8";
			
			for(Storage l : lookupResponse.getStorages()){
				log.info("Contacting storage " + l.getAddress());
				String searchRequest = l.getAddress() + "/" + RestServer.SEARCH_URL_KEYWORD + "/" + req.getSearchText();
				log.info("Sending Request: " + searchRequest);
				
				exchange = new ContentExchange();
				exchange.setMethod("GET");
				exchange.setURL(searchRequest);
				
				httpClient.send(exchange);
	
				try {
					exchangeState = exchange.waitForDone();
				} catch (InterruptedException e) {
					log.info("Error contacting storage " + l.getAddress());
					continue;
				}
				
				if(!(exchangeState == HttpExchange.STATUS_COMPLETED) || !(exchange.getResponseStatus() == HttpServletResponse.SC_OK)){
					log.info("Error contacting storage " + l.getAddress());
					continue;
				}
				SearchResults results;
				
				String contentType = null; // exchange.getResponseFields().getStringField("ContentType"); = Nullpointer
				if(contentType == null) {
					contentType = "text/xml;charset=utf-8";
				}
				contentTypeResponse = contentType;
				
				if(contentType.equals(ContentType.JSON)) {
					response.setContentType("text/json;charset=utf-8");
					Gson gson = new Gson();
					results = gson.fromJson(exchange.getResponseContent(), SearchResults.class);
				} else {
					response.setContentType("text/xml;charset=utf-8");
					ByteArrayInputStream in = new ByteArrayInputStream(exchange.getResponseContent().getBytes());
					JAXBContext jc1 = JAXBContext.newInstance(SearchResults.class);
					Unmarshaller u1 = jc1.createUnmarshaller();
					results = (SearchResults) u1.unmarshal(in);
				}
				
				allResults.addAll(results.getIt());

			}
			
			SearchResults sr = new SearchResults();
			sr.setResults(allResults);
			
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			if(contentTypeResponse.equals(ContentType.JSON)) {
				Gson gson = new Gson();
				result.write(gson.toJson(sr).getBytes());
			} else {
				JAXBContext ctx = JAXBContext.newInstance(SearchResults.class, Object.class);
				Marshaller m = ctx.createMarshaller();
				m.marshal(sr, result);
			}
			
			response.getWriter().print(GatewaySearchHandler.scAsymGateway.encryptRSAWithServerPublicKey(result.toString("utf-8")));
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.getWriter().close();
	}
}
