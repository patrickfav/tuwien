package server.handler.external;

import indexing.entities.RestRequest;
import indexing.entities.lookup.Location;
import indexing.entities.lookup.LookupResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import server.RestServer;
import server.handler.AbstractRestHandler;
import server.handler.ExternalHttpRequestHandler;
import server.handler.crudhandler.entities.SearchResults;

public class ExternalSearchHandler extends AbstractRestHandler {
	
	private static Logger log = Logger.getLogger(ExternalSearchHandler.class);
	
	private static HttpClient httpClient;
	private ContentExchange exchange;

	private static final long HTTP_SERVICE_TIMEOUT = 10000;
	private static final String META_SERVICE_HOST = "http://localhost:8181/";
	private static final String META_SERVICE_METHOD = "locations";
	
	private static final String META_SERVICE_INTERRUPT = "A timeout occured while requesting Meta Service.";
	private static final String META_SERVICE_ERROR = "An error ocured while contacting the Meta Service.";
	private static final String LOOKUP_ERROR = "A lookup error occured.";
	
	public ExternalSearchHandler() throws Exception{
		httpClient = new HttpClient();
		httpClient.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
		httpClient.setTimeout(HTTP_SERVICE_TIMEOUT);
		httpClient.start();
	}
	
	@Override
	public void handle(String arg0, Request arg1, HttpServletRequest arg2, HttpServletResponse arg3) throws IOException, ServletException {
		throw new ServletException("unsupported method");
	}

	public void handle(RestRequest req, HttpServletResponse response) throws IOException{
		
		response.setContentType("text/xml;charset=utf-8");
		
		exchange = new ContentExchange();
		exchange.setMethod("GET");
		exchange.setURL(META_SERVICE_HOST + META_SERVICE_METHOD);
		
		httpClient.send(exchange);
		
		int exchangeState;
		try {
			exchangeState = exchange.waitForDone();
		} catch (InterruptedException e) {
			response.getWriter().println(getErrorPage(META_SERVICE_INTERRUPT));
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		if(!(exchangeState == HttpExchange.STATUS_COMPLETED) || !(exchange.getResponseStatus() == HttpServletResponse.SC_OK)){
			response.getWriter().println(getErrorPage(META_SERVICE_ERROR));
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		try {
			/* Metaservice Response */
			ByteArrayInputStream input = new ByteArrayInputStream (ExternalHttpRequestHandler.scAsymExternal.decryptRSAWithPrivateKey(exchange.getResponseContent()).getBytes()); 
			JAXBContext jc = JAXBContext.newInstance (LookupResponse.class, Location.class);
			Unmarshaller u = jc.createUnmarshaller();
			LookupResponse lookupResponse = (LookupResponse) u.unmarshal(input);
			
			if(lookupResponse == null || lookupResponse.getLocations() == null || lookupResponse.getLocations().size() == 0){
				response.getWriter().println(getErrorPage(LOOKUP_ERROR));
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			
			List<Object> allResults = new ArrayList<Object>();
			String contentTypeResponse = "text/xml;charset=utf-8";
			
			for(Location l : lookupResponse.getLocations()){
				log.info("Contacting location " + l.getAddress());
				String searchRequest = l.getAddress() + "/" + RestServer.SEARCH_URL_KEYWORD + "/" + req.getSearchText();
				log.info("Sending Request: " + searchRequest);
				
				exchange = new ContentExchange();
				exchange.setMethod("GET");
				exchange.setURL(searchRequest);
				
				httpClient.send(exchange);
	
				try {
					exchangeState = exchange.waitForDone();
				} catch (InterruptedException e) {
					log.info("Error contacting location " + l.getAddress());
					continue;
				}
				
				if(!(exchangeState == HttpExchange.STATUS_COMPLETED) || !(exchange.getResponseStatus() == HttpServletResponse.SC_OK)){
					log.info("Error contacting location " + l.getAddress());
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
					results = gson.fromJson(ExternalHttpRequestHandler.scAsymExternal.decryptRSAWithPrivateKey(exchange.getResponseContent()), SearchResults.class);
				} else {
					response.setContentType("text/xml;charset=utf-8");
					ByteArrayInputStream in = new ByteArrayInputStream(ExternalHttpRequestHandler.scAsymExternal.decryptRSAWithPrivateKey(exchange.getResponseContent()).getBytes());
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
			
			response.getWriter().print(result);
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.getWriter().close();
	}
}
