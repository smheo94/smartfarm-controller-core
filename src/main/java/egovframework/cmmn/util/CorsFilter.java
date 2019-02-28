package egovframework.cmmn.util;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsFilter implements javax.servlet.Filter {
		public void destroy() {
	        
	    }

	    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
	            throws IOException, ServletException {
	    
	        HttpServletResponse response = (HttpServletResponse) res;
//	        HttpServletRequest request = (HttpServletRequest) req;
	        
	        response.setHeader("Access-Control-Allow-Methods", "*"); // CRUD
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Accept, Authorization, X-Smartfarm-Gsm-Key");
	        response.setHeader("Access-Control-Allow-Origin", "*");

//			ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
//			ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
			
//	        filterChain.doFilter(requestWrapper, responseWrapper);
			filterChain.doFilter(req, res);

//			responseWrapper.copyBodyToResponse();
	    }

	    public void init(FilterConfig arg0) throws ServletException {
	        
	    }
}
