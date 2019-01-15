package egovframework.cmmn.util;

import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CorsFilter implements javax.servlet.Filter {
	 public void destroy() {
	        // TODO Auto-generated method stub
	        
	    }

	    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
	            throws IOException, ServletException {
	        // TODO Auto-generated method stub

	        HttpServletResponse response = (HttpServletResponse) res;
	        
	        response.setHeader("Access-Control-Allow-Methods", "*"); // CRUD
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Accept, Authorization, X-Smartfarm-Gsm-Key");
	        response.setHeader("Access-Control-Allow-Origin", "*");

	        // manual multi domain example
	        //response.addHeader("Access-Control-Allow-Origin", "http://www.ozit.co.kr");
	        //response.addHeader("Access-Control-Allow-Origin", "http://abc.ozit.co.kr");
	        //response.addHeader("Access-Control-Allow-Origin", "http://test.ozrank.co.kr");

			ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

	        filterChain.doFilter(req, responseWrapper);

			responseWrapper.copyBodyToResponse();
	    }

	    public void init(FilterConfig arg0) throws ServletException {
	        // TODO Auto-generated method stub
	        
	    }
}
