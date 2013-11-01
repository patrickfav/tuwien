package util.httperror;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class HttpErrorRedirectResponseWrapper extends
		HttpServletResponseWrapper {
	
	private boolean isError = false;
	private int errorCode;
	private String errorMsg;

	public HttpErrorRedirectResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	public void sendError(int sc, String msg) throws IOException {
		this.isError = true;
		this.errorCode = sc;
		this.errorMsg = msg;
	}

	public void sendError(int sc) throws IOException {
		this.isError = true;
		this.errorCode = sc;
	}

	/**
	 * deprecated on servlet response... 
	 */
	@Deprecated
	public void setStatus(int sc, String sm) {
		if(isErrorCode(sc)) {
			this.isError = true;
			this.errorCode = sc;
			this.errorMsg = sm;
		} else {
			// we do not care about other status codes
			super.setStatus(sc, sm);
		}
	}

	@Override
	public void setStatus(int sc) {
		if(isErrorCode(sc)) {
			this.isError = true;
			this.errorCode = sc;
		} else {
			// we do not care about other status codes
			super.setStatus(sc);
		}
	}
	
	public boolean isError() {
		return isError;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	private boolean isErrorCode(int sc) {
		return sc >= HttpServletResponse.SC_BAD_REQUEST;
	}

}
