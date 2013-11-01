package server.handler;

import indexing.IndexStorage;
import indexing.entities.IndexedService;
import indexing.entities.RestRequest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.security.PublicKey;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import network.NetworkHelper;
import network.NetworkService;

import org.eclipse.jetty.server.Request;

import security.SecureChannelAsym;
import server.RestServer;
import server.handler.crudhandler.CreateHandler;
import server.handler.crudhandler.DeleteHandler;
import server.handler.crudhandler.ReadHandler;
import server.handler.crudhandler.SearchHandler;
import server.handler.crudhandler.SimpleSearchHandler;
import server.handler.crudhandler.UpdateHandler;
import constants.CRUDAction;
import exceptions.HttpContentException;


public class HttpRequestHandler extends AbstractRestHandler {
	public static SecureChannelAsym scAsymRestServer;
	
	private ReadHandler readHandler;
	private CreateHandler createHandler;
	private UpdateHandler updateHandler;
	private DeleteHandler deleteHandler;
	private SearchHandler searchHandler;
	private SimpleSearchHandler simpleSearchHandler;
	
	private NetworkService netService;
	
	public HttpRequestHandler(String serverPKPath,Map<InetAddress, PublicKey> clientKeys) {
		readHandler = new ReadHandler();
		createHandler = new CreateHandler();
		updateHandler = new UpdateHandler();
		deleteHandler = new DeleteHandler();
		searchHandler = new SearchHandler();
		simpleSearchHandler= new SimpleSearchHandler();
		
		netService = new NetworkService();
		
		try {
			if(scAsymRestServer == null)
				scAsymRestServer = new SecureChannelAsym(serverPKPath,clientKeys);
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
			if(e.getMessage().equals(URLERROR_NO_ID_GIVEN)) {
				response.getWriter().println(getErrorPage(URLERROR_NO_ID_GIVEN));
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
		
		log.info("RestRequest: "+r);
		
		/* decrypts if needed */
		try {
			/* check index if decryption is needed */
			r = decryptIfNecessary(r);
		} catch (Exception e) {
			response.getWriter().println(getErrorPage(COULD_NOT_DECRYPT));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			e.printStackTrace();
			return;
		}
		
		/* delegate to specific handler */
		try {
			if(r.getCrudAction().equals(CRUDAction.READ)) {
				readHandler.handle(r, response);
			} else if(r.getCrudAction().equals(CRUDAction.CREATE)) {
				createHandler.handle(r, response);
			} else if(r.getCrudAction().equals(CRUDAction.UPDATE)) {
				updateHandler.handle(r, response);
			} else if(r.getCrudAction().equals(CRUDAction.DELETE)) {
				deleteHandler.handle(r, response);
			} else if(r.getCrudAction().equals(CRUDAction.SEARCH)) {
				searchHandler.handle(r, response);
			} else if(r.getCrudAction().equals(CRUDAction.SIMPLESEARCH)) {
				simpleSearchHandler.handle(r, response);
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
			return;
		}
		
		response.getWriter().flush();
		response.getWriter().close();	
	}
	
	/*
	 * PRIVATE
	 */
	
	private RestRequest analyseRequest(String requestUrl, String method,HttpServletRequest request) throws MalformedURLException, HttpContentException {
		RestRequest r = new RestRequest();

		r.setAddr(NetworkHelper.getAddressForIp(request.getRemoteAddr()));
		
		if(requestUrl.length() <= 1) {
			r.setCrudAction(CRUDAction.NONE);
			return r;
		}
		
		String[] urlParts = requestUrl.substring(1).split("/"); //omit the first slash
		r.setCrudAction(method);
		
		/* checks if simple search request (for task 2)*/
		if(method.equals(CRUDAction.READ) && urlParts.length == 2 && urlParts[0].equals(RestServer.SEARCH_URL_KEYWORD)) {
			r.setCrudAction(CRUDAction.SIMPLESEARCH);
			r.setSimpleSearchKeyword(urlParts[1]);
			r.setName(PERSON_SERVICE);
		} else {
		
			/* checks if entity name has a "s" as last char */
			if (!urlParts[0].substring(urlParts[0].length()-1).equals("s")){
				throw new MalformedURLException(WRONG_ENTITY_NAME);
			}
			r.setName(urlParts[0]);
			
			
			
			/* checks if search request */
			if(method.equals(CRUDAction.UPDATE)) {
				if(urlParts.length == 2) {
					if(urlParts[1].equals(RestServer.SEARCH_URL_KEYWORD)) {
						r.setCrudAction(CRUDAction.SEARCH);
					} 
				} else {
					throw new MalformedURLException(URLERROR_NO_ID_GIVEN);
				}
			}
			
			/* check for id with certain methods */
			if(method.equals(CRUDAction.DELETE) || method.equals(CRUDAction.READ) || method.equals(CRUDAction.UPDATE)) {
				/* must have an id given and thus length must be > 2 */
				if(urlParts.length < 2) {
					throw new MalformedURLException(URLERROR_NO_ID_GIVEN);
				}
				/* id is empty */
				if(urlParts[1].length() <= 0) {
					throw new MalformedURLException(URLERROR_NO_ID_GIVEN);
				}
				
				r.setId(urlParts[1]);
			}
		
		
			/* with create, update and search  the content of the request must be read */
			if(method.equals(CRUDAction.CREATE) || method.equals(CRUDAction.UPDATE) || method.equals(CRUDAction.SEARCH)) {
				try {
					r.setContentReader(request.getReader());
					request.getReader().close();
				} catch (Exception e) { 
					throw new HttpContentException("Could not read the post content");
				}
			}
			
			/* sets if encryption is needed */
			if(IndexStorage.getInstance().getServiceMap().containsKey(r.getName())) {
				IndexedService is = IndexStorage.getInstance().getServiceMap().get(r.getName());
				r.setNeedsEncryption(netService.hasToBeEncrypted(r.getAddr(), is));
			} else {
				r.setNeedsEncryption(false);
			}
		}
		return r;
	}
}
