package server.handler.crudhandler;

import indexing.IndexStorage;
import indexing.entities.IndexedService;
import indexing.entities.RestRequest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import persistence.HibernateManager;
import server.handler.AbstractRestHandler;
import exceptions.DatabaseException;

public class CreateHandler extends AbstractRestHandler{

	@Override
	public void handle(String arg0, Request arg1, HttpServletRequest arg2, HttpServletResponse arg3) throws IOException, ServletException {
		throw new ServletException("unsupported method");
	}
	
	public void handle(RestRequest req, HttpServletResponse response) throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		HibernateManager hbManager = HibernateManager.getInstance();
		IndexStorage indexStorage = IndexStorage.getInstance();
		IndexedService is;
		Object o = null;
		
		response.setContentType("text/html;charset=utf-8");
		
		if(indexStorage.getServiceMap().containsKey(req.getName())) {
			/* get the index for the service name */
			is = indexStorage.getServiceMap().get(req.getName());
			
			/* deserilaize */
			try {
				o = deserialize(req, is.getClassReference().getClazz(), is.getContentType(), response);
			} catch(Exception e) {
				e.printStackTrace();
				response.getWriter().println(getErrorPage(COULD_NOT_DESERIALIZE_ENTITY));
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			
			
			/* validate */
			if(!validateEntity(o)) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().println(getErrorPage(VALIDATION_ERROR));
				return;
			}
				
			
			/* try to save */
			try {
				hbManager.persist(o);
				response.setStatus(HttpServletResponse.SC_CREATED);
				
				/* print out serialized entity */
				displayEntity(o,is,response,req);
			} catch(DatabaseException e) {
				response.getWriter().println(getErrorPage(COULD_NOT_CREATE_ENTITY));
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			/* call entity interceptors */
			is.callAllCreateInterceptors(o);
		} else {
			/* entity/service  not found */
			response.getWriter().println(getErrorPage(SERVICE_NOT_FOUND));
			response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}
		
		/* call global interceptors */
		indexStorage.callAllCreateInterceptors(o);
	}
}
