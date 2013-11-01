package util.httperror;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;

public class HttpErrorRedirectFilter implements Filter {

	public static final String SPICS_ERROR_PAGE = "/error.seam";
	public static final String HTTP_ERROR_CODE_PARAM = "httpErr";
	public static final String HTTP_ERROR_MSG_PARAM = "httpErrMsg";
	private Log log;
	
	public void destroy() {
		log = null;

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		
		if (response instanceof HttpServletResponse) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			HttpErrorRedirectResponseWrapper wrappedResponse = new HttpErrorRedirectResponseWrapper(httpResponse);
			
			chain.doFilter(request, wrappedResponse);
		
			if(wrappedResponse.isError()) {
				
				String contextPath = ((HttpServletRequest)request).getContextPath();
				
				log.info("redirecting to spics error page! ");
				// send redirect to our error page!
				String errorURL = contextPath + SPICS_ERROR_PAGE + "?" + HTTP_ERROR_CODE_PARAM + "=" + wrappedResponse.getErrorCode();
				if(wrappedResponse.getErrorMsg() != null) {
					errorURL += "&" + HTTP_ERROR_MSG_PARAM + "=" + wrappedResponse.getErrorMsg();
				}
				log.info("error URL: #0", errorURL);
				wrappedResponse.sendRedirect(errorURL);
			}
		}

	}

	public void init(FilterConfig cfg) throws ServletException {
		log = Logging.getLog(HttpErrorRedirectFilter.class);
		if(log == null) {
			System.out.println("WARN: HttpErrorRedirectFilter: failed to initialize logger!");
		} else {
			log.info("initialized");
		}

	}

}
