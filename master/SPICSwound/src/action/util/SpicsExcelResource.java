package util;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

import util.excel.ExcelReaderWriter;
import util.excel.IExcelReaderWriter;
import entities.TrialData;

@Scope(ScopeType.APPLICATION)
@Name("spicsExcelResource")
@Install(precedence = Install.BUILT_IN)
@BypassInterceptors
public class SpicsExcelResource extends AbstractResource {

	public static final String SPICS_FILE_RESOURCE_PATH = "/seam/resource/spicsexcel";

	private static final String RESOURCE_PATH = "/spicsexcel";

	private static final String PARAM_TFID = "tdid";

	private static Log log = Logging.getLog(SpicsExcelResource.class);

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

		EntityManager em = (EntityManager) Component
				.getInstance("entityManager");

		if (em != null) {
			Long tdId = saveParseLong(request.getParameter(PARAM_TFID));
			try {
				if (tdId != null) {
					Transaction.instance().begin();

					TrialData td = em.find(TrialData.class, tdId);
					
					Transaction.instance().commit();
					
					if (td == null) {
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
						return;
					}

					Identity.instance().checkPermission(
							td.getPatient(),
							SpicsPermissions.VIEW_PATIENT);
					
					String filename = escapeString(String.format(
							"patient%s_%s_data%s.xls", td.getPatient()
									.getKennnummer(), td.getTrialform()
									.getName(), td.getId()));

					response.setContentType("application/xls");
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
					IExcelReaderWriter xlsWriter = new ExcelReaderWriter();
					xlsWriter.writeTrialDataToXls(td, os);
					os.flush();
				} else {
					// insufficient parameters specified
					log.warn("insufficient parameters specified!");
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
			} catch (AuthorizationException authExc) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			} catch (Exception e1) {
				e1.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} else {
			log.warn("injected entitymanager was null, could not proceed");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
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

	private static String escapeString(String s) {

		s = StringUtils.remove(s, '\\');
		s = StringUtils.remove(s, '"');
		s = StringUtils.remove(s, '/');

		return s;
	}

}
