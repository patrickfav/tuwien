package server.handler;

import indexing.IndexStorage;
import indexing.entities.IndexedService;
import indexing.entities.RestRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.handler.AbstractHandler;

import persistence.HibernateManager;
import security.SecureChannelAsym;
import server.handler.crudhandler.entities.SearchResults;

import com.google.gson.Gson;

import constants.CRUDAction;
import constants.ContentType;

public abstract class AbstractRestHandler extends AbstractHandler {
	
	protected static Logger log = Logger.getLogger(HttpRequestHandler.class);
	
	protected static final String PERSON_SERVICE = "persons";
	
	protected static final String URLERROR_NO_ID_GIVEN = "URLERROR_NO_ID_GIVEN";
	protected static final String WRONG_ENTITY_NAME = "WRONG_ENTITY_NAME";
	protected static final String SERVICE_NOT_FOUND = "SERVICE_NOT_FOUND";
	protected static final String ENTITY_WITH_ID_NOT_FOUND = "ENTITY_WITH_ID_NOT_FOUND";
	protected static final String CONTENT_TYPE_NOT_ALLOWED = "CONTENT_TYPE_NOT_ALLOWED";
	protected static final String DATABASE_ERROR = "DATABASE_ERROR";
	protected static final String INTERNAL_EXCEPTION = "INTERNAL_EXCEPTION";
	protected static final String COULD_NOT_CREATE_ENTITY = "COULD_NOT_CREATE_ENTITY";
	protected static final String COULD_NOT_UPDATE_ENTITY = "COULD_NOT_UPDATE_ENTITY";
	protected static final String COULD_NOT_SEARCH_ENTITY = "COULD_NOT_SEARCH_ENTITY";
	protected static final String COULD_NOT_SIMPLESEARCH_ENTITY = "COULD_NOT_SIMPLESEARCH_ENTITY ";
	protected static final String COULD_NOT_DESERIALIZE_ENTITY = "COULD_NOT_DESERIALIZE_ENTITY";
	protected static final String COULD_NOT_SERIALIZE_ENTITY = "COULD_NOT_SERIALIZE_ENTITY";
	protected static final String COULD_NOT_READ_POST_CONTENT = "COULD_NOT_READ_POST_CONTENT";
	protected static final String ENTITY_FOR_UPDATE_DOES_NOT_EXIST = "ENTITY_FOR_UPDATE_DOES_NOT_EXIST";
	protected static final String ENTITY_FOR_DELETE_DOES_NOT_EXIST = "ENTITY_FOR_DELETE_DOES_NOT_EXIST";
	protected static final String WRONG_SEARCH_FIELD_GIVEN = "WRONG_SEARCH_FIELD_GIVEN";
	protected static final String NO_SEARCH_RESULTS = "NO_SEARCH_RESULTS";
	protected static final String VALIDATION_ERROR = "VALIDATION_ERROR";
	protected static final String COULD_NOT_DECRYPT = "COULD_NOT_DECRYPT";
	protected static final String COULD_NOT_ENCRYPT = "COULD_NOT_ENCRYPT";

	
	
	/**
	 * Serializes from Object to String depending on the content type
	 * 
	 * @param Object to serialize
	 * @param contentType
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected String serialize(Object o,String contentType, HttpServletResponse response) throws UnsupportedEncodingException {
		ByteArrayOutputStream f;
		if(contentType.equals(ContentType.JSON)) {
			Gson gson = new Gson();
			//response.setContentType("application/json");
			return gson.toJson(o);
		} else if(contentType.equals(ContentType.XML)) {
			f = new ByteArrayOutputStream();
			JAXB.marshal(o, f);
			response.setContentType("text/xml;charset=utf-8");
			return f.toString("utf-8");
		} else {
			return null;
		}
	}
	
	/**
	 * Deserializes from String to Object
	 * 
	 * @param String to deserialize
	 * @param contentType
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected <T> T deserialize(RestRequest r,Class<T> clazz, String contentType, HttpServletResponse response) {
		if(contentType.equals(ContentType.JSON)) {
			Gson gson = new Gson();
			return gson.fromJson(r.getContent().toString(), clazz);
		} else if(contentType.equals(ContentType.XML)) {
			return JAXB.unmarshal(r.getContentReader(), clazz);
		} else {
			return null;
		}
	}
	
	protected String getWelcomeMessage() {
		String out = "<h1>Welcome to the RestService Framwork v2</h1>" +
				"<h2>Usage</h2>" +
				"<p>" +
				"GET: '/{entityName_plural}/{id} <br />" +
				"POST: '/{entityName_plural}' <br />" +
				"PUT: '/{entityName_plural}' <br />" +
				"DELETE: '/{entityName_plural}/{id}'" +
				"</p>" +
				"<h2>Available Services</h2>" +
				"<ul>";
				
				for(String s:IndexStorage.getInstance().getServiceMap().keySet()) {
					out +="<li>/"+s+" ("+IndexStorage.getInstance().getServiceMap().get(s).getContentType() +
							", enc[int:"+IndexStorage.getInstance().getServiceMap().get(s).isRequiresEncryptionInternal()+
							", ext:"+IndexStorage.getInstance().getServiceMap().get(s).isRequiresEncryptionExternal()+"])</li>";
				}
				out  += "</ul>";
		return out;
	}
	
	protected String getErrorPage(String errMsg) {
		log.warn(errMsg);
		return "<h1>Error</h1>" + errMsg;
	}
	
	protected String getNoResultsPage(String msg) {
		return "<h1>Search</h1>" + msg;
	}
	
	/**
	 * Displays one entity
	 * @param o
	 * @param is
	 * @param response
	 * @throws IOException
	 */
	protected void displayEntity(Object o, IndexedService is, HttpServletResponse response, RestRequest r) throws IOException {
		try {
			String s = serialize(o,is.getContentType(),response);
			
			if(s == null) {
				/* error while serializing */
				response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				response.getWriter().println(getErrorPage(CONTENT_TYPE_NOT_ALLOWED));
			} else {
				/* ok */

				try {
					/* check index if encryption is needed */
					s = encryptResponseIfNecessary(s,r);
					
					if(r.isNeedsEncryption())
						response.setContentType("text/plain;charset=utf-8");
					
				} catch (Exception e) {
					response.getWriter().println(getErrorPage(COULD_NOT_ENCRYPT));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					e.printStackTrace();
					return;
				}
				response.setContentType("text/xml;charset=utf-8");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().println(s);
			}
		} catch(UnsupportedEncodingException e) {
			/* error while serializing */
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println(getErrorPage(COULD_NOT_SERIALIZE_ENTITY));
		}
		
	}
	
	/**
	 * Displays multiple entities
	 * @param entityList
	 * @param is
	 * @param response
	 * @throws IOException
	 * @throws JAXBException 
	 */
	protected void displayEntities(List<Object> entityList,IndexedService is, HttpServletResponse response, RestRequest r) throws IOException, JAXBException {
		if(is.getContentType().equals(ContentType.JSON)) {
			displayEntity(entityList,is,response,r);
		} else if(is.getContentType().equals(ContentType.XML)) {
			
			String out = "";
			SearchResults sr = new SearchResults();
			sr.setResults(entityList);
			
			JAXBContext ctx = JAXBContext.newInstance(SearchResults.class, is.getClassReference().getClazz());
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			Marshaller m = ctx.createMarshaller();
			m.marshal(sr, result);
			
			out = result.toString("utf-8");
			
			try {
				/* check index if encryption is needed */
				out = encryptResponseIfNecessary(out,r);
				
				if(r.isNeedsEncryption())
					response.setContentType("text/plain;charset=utf-8");
			} catch (Exception e) {
				response.getWriter().println(getErrorPage(COULD_NOT_ENCRYPT));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				e.printStackTrace();
				return;
			}
			
			/* ok */
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println(out);
		}
	}
	
	/**
	 * Validates an entity. Returns false if validation was not successfull
	 * @param <T>
	 * @param obj
	 * @return
	 */
	protected <T> boolean validateEntity(T obj) {
		Set<ConstraintViolation<T>> violations = HibernateManager.getInstance().validateEntity(obj);
		
		if(violations.size() == 0)
			return true;
		
		log.warn("Validation failed: ");
		
		for(ConstraintViolation<T> cv:violations) {
			log.warn(cv.getMessage());
		}
		
		return false;
	}
	
	/**
	 * For RestService - decrypts a received message if config requires it
	 */
	protected RestRequest decryptIfNecessary(RestRequest r)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		
		if (r.isNeedsEncryption()
				&& r.getName() != null
				&& !r.getName().equals("")
				&& (r.getCrudAction().equals(CRUDAction.CREATE)
						|| r.getCrudAction().equals(CRUDAction.UPDATE) ||
						r.getCrudAction().equals(CRUDAction.SEARCH))) {

			String content = r.getContent().toString();
			String decrypted = HttpRequestHandler.scAsymRestServer.decryptRSAWithPrivateKey(content);
			r.setContentReader(new StringReader(decrypted));

		}

		return r;
	}
	
	/**
	 * encrypts the message if the restservice annotation requires it 
	 */
	protected String encryptResponseIfNecessary(String s,RestRequest r) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		if(r.isNeedsEncryption() && s != null && !s.equals("")) {
			return HttpRequestHandler.scAsymRestServer.encryptRSAWithPublicKey(r.getAddr(), s);
		}
		
		return s;
	}
	
}
