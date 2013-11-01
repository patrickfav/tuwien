package util;

import java.io.IOException;

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

import entities.Patient;
import entities.TrialAttachment;

@Scope(ScopeType.APPLICATION)
@Name("spicsFileResource")
@Install(precedence = Install.BUILT_IN)
@BypassInterceptors
public class SpicsFileResource extends AbstractResource {

	public static final String SPICS_FILE_RESOURCE_PATH = "/seam/resource/spicsfile";
	
	private static final String RESOURCE_PATH = "/spicsfile";
	
	public static final String PARAM_TDID = "tdid"; // deprecated
	public static final String PARAM_AID = "aid";	// deprecated
	public static final String PARAM_TAID = "taid";
	
	private static Log log = Logging.getLog(SpicsFileResource.class);

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
		
		if(log == null) {
			System.out.println("SpicsFileResource: logger null ....");
		}
		
		
		EntityManager em = (EntityManager)Component.getInstance("entityManager");
		
		if(em != null) {
			// deprecated
			Long tdId = saveParseLong(request.getParameter(PARAM_TDID));
			Long aId =  saveParseLong(request.getParameter(PARAM_AID));
			
			Long taId = saveParseLong(request.getParameter(PARAM_TAID));

			try {
				if(tdId != null && aId != null) {
					throw new IllegalArgumentException("this usage method of SpicsFileResource has been deprecated, use parameter taId instead!");
//					Transaction.instance().begin();
//					
//					FileValue fv = em.find(FileValue.class, new Value.ValueId(aId, tdId));
//									
//					Transaction.instance().commit();
//					
//					sendTrialAttachment(response, fv.getFile());
				} else if(taId != null) {
					Transaction.instance().begin();
					TrialAttachment ta = em.find(TrialAttachment.class, taId);
					Transaction.instance().commit();
					
					if(ta.getTrial() != null) {
						Identity.instance().checkPermission(ta.getTrial(), SpicsPermissions.VIEW_TRIAL_DATA);
					} else {
						Patient p = ta.getFileSet().getValue().getTrialData().getPatient();
						Identity.instance().checkPermission(p, SpicsPermissions.VIEW_PATIENT);
					}
					
					sendTrialAttachment(response, ta);
				} else {
					// insufficient parameters specified
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
			} catch (AuthorizationException e1) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			} catch (Exception e2) {
				e2.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}

	}
	
	private void sendTrialAttachment(HttpServletResponse response, TrialAttachment ta) throws IOException {
		sendFileAsResponse(response, ta.getContentType(), ta.getFileName(), ta.getData());
	}

	
	private void sendFileAsResponse(HttpServletResponse response, String contentType, String fileName, byte[] data) throws IOException {
		response.setContentType(contentType);
		response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
		/* 
		 * the following three header elements are needed in order for the downloads to work
		 * properly under IE (at least version 7)
		 */
		response.addHeader("Pragma", "public");
		response.addDateHeader("Expires", 0);
		response.addHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentLength(data.length);
		ServletOutputStream os = response.getOutputStream();
		os.write(data);
		os.flush();
	}
	
	@Override
	public String getResourcePath() {
		return RESOURCE_PATH;
	}
	
	private Long saveParseLong(String s) {
		if(s != null) {
			try {
				return Long.parseLong(s);
			} catch (NumberFormatException e) { }	// ignore
		}
		return null;
	}

}
