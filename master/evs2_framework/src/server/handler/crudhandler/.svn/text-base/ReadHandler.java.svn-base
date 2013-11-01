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

public class ReadHandler extends AbstractRestHandler{

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
			try {
				/* search for id */
				o = hbManager.find(Long.valueOf(req.getId()), is.getClassReference().getClazz());
			} catch(DatabaseException e) {
				/* error while retrieving data */
				response.getWriter().println(getErrorPage(DATABASE_ERROR));
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			if(o == null) {
				/* entity with given id not found in db*/
				response.getWriter().println(getErrorPage(ENTITY_WITH_ID_NOT_FOUND));
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			} else {
				/* print out serialized entity */
				displayEntity(o,is,response,req);
			}
			/* call entity interceptors */
			is.callAllReadInterceptors(o);
		} else {
			/* entity/service  not found */
			response.getWriter().println(getErrorPage(SERVICE_NOT_FOUND));
			response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
		}
		
		/* call global interceptors */
		indexStorage.callAllReadInterceptors(o);
	}
}
