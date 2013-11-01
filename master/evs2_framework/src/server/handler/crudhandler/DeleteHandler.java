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
import exceptions.DeleteException;

public class DeleteHandler extends AbstractRestHandler{

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
				o = hbManager.remove(Long.valueOf(req.getId()), is.getClassReference().getClazz());
			} catch(DatabaseException e) {
				/* error while retrieving data */
				response.getWriter().println(getErrorPage(DATABASE_ERROR));
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			} catch (DeleteException e) {
				response.getWriter().println(getErrorPage(ENTITY_FOR_DELETE_DOES_NOT_EXIST));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			/* call entity interceptors */
			is.callAllDeleteInterceptors(o);
		} else {
			/* entity/service  not found */
			response.getWriter().println(getErrorPage(SERVICE_NOT_FOUND));
			response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}
		
		/* call global interceptors */
		indexStorage.callAllDeleteInterceptors(o);
	}
}
