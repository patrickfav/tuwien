package server.handler;

import indexing.entities.RestRequest;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Request;

import security.SecureChannelAsym;
import server.RestServer;
import server.handler.external.ExternalSearchHandler;

import constants.CRUDAction;
import exceptions.HttpContentException;

public class ExternalHttpRequestHandler extends AbstractRestHandler {

	private static Logger log = Logger.getLogger(ExternalHttpRequestHandler.class);
	private static String EXTERNAL_SERVICE_PRIVATEKEY="keys/externalservice.pem";
	private static String META_SERVICE_PUBLICKEY="keys/metaservice.pub.pem";
	
	private static final String INVALID_SEARCH = "Invalid search request.";
	
	private ExternalSearchHandler searchHandler;
	public static SecureChannelAsym scAsymExternal;
	
	public ExternalHttpRequestHandler() throws Exception{
		searchHandler = new ExternalSearchHandler();
		
		
		try {
			if(scAsymExternal == null)
				scAsymExternal = new SecureChannelAsym(EXTERNAL_SERVICE_PRIVATEKEY,META_SERVICE_PUBLICKEY);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void handle(String arg0, Request baseRequest,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		baseRequest.setHandled(true);
		RestRequest r;
		
		/* analyse request */
		try {
			r = analyseRequest(arg0,baseRequest.getMethod(),request);
		} catch (MalformedURLException e){
			if(e.getMessage().equals(INVALID_SEARCH)) {
				response.getWriter().println(getErrorPage(INVALID_SEARCH));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			} else if(e.getMessage().equals(WRONG_ENTITY_NAME)) {
				response.getWriter().println(getErrorPage(WRONG_ENTITY_NAME));
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
			if(r.getCrudAction().equals(CRUDAction.SEARCH)) {
				log.info("Got valid search request.");
				searchHandler.handle(r, response);
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
		if(method.equals(CRUDAction.READ) && (urlParts.length == 2) && urlParts[0].equals(RestServer.SEARCH_URL_KEYWORD)) {
			r.setCrudAction(CRUDAction.SEARCH);
			r.setSearchText(urlParts[1]);
		} else {
			throw new MalformedURLException(INVALID_SEARCH);
		}
		
		return r;
	}
}
