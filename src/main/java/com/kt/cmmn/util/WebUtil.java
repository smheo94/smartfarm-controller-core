package com.kt.cmmn.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {
    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String X_REQUESTED_WITH = "X-Requested-With";

    private static final String CONTENT_TYPE = "Content-type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    public static boolean isAjax(HttpServletRequest request) {
        return XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH));
    }

    public static boolean isContentTypeJson(HttpServletRequest request) {
        return request.getHeader(CONTENT_TYPE).contains(CONTENT_TYPE_JSON);
    }
    public static String remoteAddress(HttpServletRequest request ) {
        if ( request == null ) {
            return null;
        }
        String remoteAddr = request.getHeader("x-real-ip");
        if(StringUtils.isEmpty(remoteAddr)) {
            remoteAddr = request.getHeader("x-forwarded-for");
        }
        if ( remoteAddr == null )
            remoteAddr = request.getRemoteAddr();

        if ( "0:0:0:0:0:0:0:1".equals(remoteAddr) )
            remoteAddr = "127.0.0.1";
        return remoteAddr;
    }
}
