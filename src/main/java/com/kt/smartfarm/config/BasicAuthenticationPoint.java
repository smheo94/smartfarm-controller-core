package com.kt.smartfarm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class BasicAuthenticationPoint extends BasicAuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException, ServletException {
        response.addHeader("WWW-Authenticate", "Basic realm=" +getRealmName());
        log.info("unauthorized : {}", authEx);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("오류가 발생했습니다. 관리자에게 문의하세요");
        //writer.println("HTTP Status 401 - " + authEx.getMessage());
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("KT-Smartfarm");
        super.afterPropertiesSet();
    }

}