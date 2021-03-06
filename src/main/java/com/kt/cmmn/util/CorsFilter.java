package com.kt.cmmn.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements javax.servlet.Filter {
		public void destroy() {

	    }
		public static ThreadLocal<ServletRequest> threadLocalRequest = new ThreadLocal<>();
	    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
	            throws IOException, ServletException {

	        HttpServletResponse response = (HttpServletResponse) res;
//	        HttpServletRequest request = (HttpServletRequest) req;
			threadLocalRequest.set(req);
	        response.setHeader("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE"); // CRUD
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Accept, Authorization, x-smartfarm-gsm-key");
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
