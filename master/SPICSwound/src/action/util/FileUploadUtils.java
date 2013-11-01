package util;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.log.Log;

@Name("FileUploadUtils")
@Scope(ScopeType.STATELESS)
public class FileUploadUtils {

	@Logger
	private Log log;
	
	public void test() {
		log.info("test called");
	}
	
	public void typeRejected(String acceptedTypes, String componentId) {
		log.warn("fileupload utils (for fileUpload #0: invalid file type, expected: #1 displaying error message!", componentId, acceptedTypes);
		FacesMessages.instance().addFromResourceBundle(Severity.ERROR, "error.typerejected", acceptedTypes);
	}
	
	public void typeRejected(String acceptedTypes) {
		typeRejected(acceptedTypes, null);
	}
	
	public void sizeRejected(String componentId) {
		log.warn("fileupload utils: filesized too large for component #0", componentId);
		FacesMessages.instance().addFromResourceBundle(Severity.ERROR, "error.sizerejected");
	}
	
	public void sizeRejected() {
		sizeRejected(null);
	}
}
