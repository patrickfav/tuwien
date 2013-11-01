package util.httperror;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.Messages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.log.Log;

@Name("SpicsError")
@Scope(ScopeType.EVENT)
public class SpicsError implements Serializable{

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;
	
	@RequestParameter(HttpErrorRedirectFilter.HTTP_ERROR_CODE_PARAM)
	private Integer httpErrorCode;
	
	@RequestParameter(HttpErrorRedirectFilter.HTTP_ERROR_MSG_PARAM)
	private String httpErrorMsg;

	public boolean isHttpError() {
		return httpErrorCode != null;
	}
	
	public Integer getHttpErrorCode() {
		return httpErrorCode;
	}

	public void setHttpErrorCode(Integer httpErrorCode) {
		this.httpErrorCode = httpErrorCode;
	}

	public String getHttpErrorMsg() {
		return httpErrorMsg;
	}

	public void setHttpErrorMsg(String httpErrorMsg) {
		this.httpErrorMsg = httpErrorMsg;
	}
	
	public void preErrorPageLoad() {
		log.info("preErrorPageLoad called, params: #0, #1", httpErrorCode, httpErrorMsg);
		if(httpErrorCode != null) {
			if(httpErrorMsg != null) {
				FacesMessages.instance().add(Severity.ERROR, httpErrorMsg);
			} else {
				// try to add from resource bundle
				String msgKey = "error.http" + httpErrorCode;
				
				if(Messages.instance().containsKey(msgKey)) {
					FacesMessages.instance().addFromResourceBundleOrDefault(Severity.ERROR, msgKey, "");
				}
			}
		}
	}

}
