package server.handler;

import indexing.entities.RestRequest;
import indexing.entities.lookup.StorageLookupResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXB;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Request;

import server.LocationIndexServer;
import constants.CRUDAction;
import exceptions.HttpContentException;

public class LookupStorageHttpRequestHandler extends AbstractRestHandler {

	private static Logger log = Logger.getLogger(LookupLocationHttpRequestHandler.class);
	
	private static final String INVALID_LOOKUP = "Invalid lookup request.";
	
	@Override
	public void handle(String arg0, Request baseRequest,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		baseRequest.setHandled(true);
		RestRequest r;
		
		/* analyse request */
		try {
			r = analyseRequest(arg0,baseRequest.getMethod(),request);
		} catch (MalformedURLException e){
			if(e.getMessage().equals(INVALID_LOOKUP)) {
				response.getWriter().println(getErrorPage(INVALID_LOOKUP));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			
			return;
		} catch (HttpContentException e) {
			response.getWriter().println(getErrorPage(COULD_NOT_READ_POST_CONTENT));
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		/* delegate to specific handler */
		try {
			if(r.getCrudAction().equals(CRUDAction.STORAGES)) {
				log.info("Got valid lookup request.");
				StorageLookupResponse lookupResponse = new StorageLookupResponse();
				lookupResponse.setStorages(LocationIndexServer.getStorages());
				ByteArrayOutputStream f = new ByteArrayOutputStream();
				JAXB.marshal(lookupResponse, f);
				response.setContentType("text/html;charset=utf-8");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().println(f.toString("utf-8"));
				f.close();
			} else if(r.getCrudAction().equals(CRUDAction.NONE)) { 
				/* just show welcome message */
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().println(getWelcomeMessage());
			} else {
				log.warn("Wrong method: "+r);
				response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			}
		} catch (Exception e) {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println(getErrorPage(INTERNAL_EXCEPTION+": "+e.getMessage()));
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		response.getWriter().flush();
		response.getWriter().close();
	}

	private RestRequest analyseRequest(String requestUrl, String method,HttpServletRequest request) throws MalformedURLException, HttpContentException {
		RestRequest r = new RestRequest();
		
		if(requestUrl.length() <= 1) {
			r.setCrudAction(CRUDAction.NONE);
			return r;
		}
		
		String[] urlParts = requestUrl.substring(1).split("/"); //omit the first slash
		r.setCrudAction(method);
		
		/* checks if search request */
		if(method.equals(CRUDAction.READ) && (urlParts.length == 1) && urlParts[0].equals(LocationIndexServer.LOOKUP_URL_KEYWORD)) {
			r.setCrudAction(CRUDAction.STORAGES);	
		} else {
			throw new MalformedURLException(INVALID_LOOKUP);
		}
		
		try {
			r.setContentReader(request.getReader());
			request.getReader().close();
		} catch (Exception e) { 
			throw new HttpContentException("Could not read the post content");
		}
		
		return r;
	}

}
