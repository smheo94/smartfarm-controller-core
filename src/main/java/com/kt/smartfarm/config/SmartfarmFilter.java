package com.kt.smartfarm.config;


import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SmartfarmFilter implements  javax.servlet.Filter {

//    @Override
//    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        try {        	
//            super.doDispatch(new ContentCachingRequestWrapper(request), new ContentCachingResponseWrapper(response));
//        } catch (Exception e) {
//            super.doDispatch(request, response);
//        }
//    }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ServletRequest chgRequest = request;
		ServletResponse chgResponse = response;
		try {
			chgRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
			chgResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);
		} catch (Exception e) {
			System.err.println("Do Filter Error : " + e.toString());
		}
		chain.doFilter(chgRequest, chgResponse);
		//혹시 intercept에서 응답 복사가 안되면  아래부분을 주석을 해지하여야 함
		if (chgResponse instanceof ContentCachingResponseWrapper) {
			((ContentCachingResponseWrapper) chgResponse).copyBodyToResponse();
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}

