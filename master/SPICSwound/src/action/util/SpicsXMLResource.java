package util;

import java.io.IOException;
import java.util.Enumeration;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;
import org.jboss.seam.security.AuthorizationException;
import org.jboss.seam.security.Identity;
import org.jboss.seam.servlet.ContextualHttpServletRequest;
import org.jboss.seam.transaction.Transaction;
import org.jboss.seam.web.AbstractResource;

import util.xml.IXMLImportExport;
import util.xml.XMLImportExport;
import entities.TrialForm;

@Scope(ScopeType.APPLICATION)
@Name("spicsXmlResource")
@Install(precedence = Install.BUILT_IN)
@BypassInterceptors
public class SpicsXMLResource extends AbstractResource {

	public static final String SPICS_FILE_RESOURCE_PATH = "/seam/resource/spicsxml";

	private static final String RESOURCE_PATH = "/spicsxml";

	private static final String PARAM_TFID = "tfid";

	private static Log log = Logging.getLog(SpicsXMLResource.class);

	@Override
	public void getResource(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		new ContextualHttpServletRequest(request) {
			@Override
			public void process() throws IOException {
				doWork(request, response);
			}
		}.run();

	}

	private void doWork(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		if (!Identity.instance().isLoggedIn()) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		Enumeration<?> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			log.info("parameter: #0", e.nextElement());
		}

		EntityManager em = (EntityManager) Component
				.getInstance("entityManager");

		if (em != null) {
			Long tfId = saveParseLong(request.getParameter(PARAM_TFID));
			log.info("trying to serve up serialized version of trialform with id #0", tfId);
			try {
				if (tfId != null) {

					Transaction.instance().begin();
					TrialForm tf = em.find(TrialForm.class, tfId);
					
					if (tf == null) {
						log.warn("requested trialform was not found (id: #0)", tfId);
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
						Transaction.instance().commit();
						return;
					}

					Identity.instance().checkPermission(tf.getTrial(), 
							SpicsPermissions.EXPORT_TRIAL_FORMS);

					String filename = ((tf.getName() == null || "".equals(tf
							.getName())) ? "formular" : tf.getName() + ".zip");

					response.setContentType("application/zip");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"" + filename + "\"");
					/*
					 * the following three header elements are needed in order
					 * for the downloads to work properly under IE (at least
					 * version 7)
					 */
					response.addHeader("Pragma", "public");
					response.addDateHeader("Expires", 0);
					response.addHeader("Cache-Control",
							"must-revalidate, post-check=0, pre-check=0");

					response.setStatus(HttpServletResponse.SC_OK);

					ServletOutputStream os = response.getOutputStream();
					IXMLImportExport xmlExport = new XMLImportExport();
					xmlExport.writeTrialFormToZip(tf, os);
					
					Transaction.instance().commit();
					os.flush();
					
				} else {
					// insufficient parameters specified
					log.warn("insufficient parameters specified!");
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
			} catch (AuthorizationException e2) {
				log.warn("AuthorizationException #0", e2.getMessage());
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			} catch (Exception e1) {
				e1.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} 
		} else {
			log.error("injected entitymanager was null, could not proceed");
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public String getResourcePath() {
		return RESOURCE_PATH;
	}

	private Long saveParseLong(String s) {
		if (s != null) {
			try {
				return Long.parseLong(s);
			} catch (NumberFormatException e) {
			} // ignore
		}
		return null;
	}

}
