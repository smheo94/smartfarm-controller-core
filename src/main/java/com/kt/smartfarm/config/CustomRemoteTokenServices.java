package com.kt.smartfarm.config;

import com.kt.cmmn.util.CorsFilter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.RestOperations;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Vector;

public class CustomRemoteTokenServices extends RemoteTokenServices {
    List<String> matcher = new Vector<>();
    List<RemoteTokenServices> matchRemoteTokenService  = new Vector<>();
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    RemoteTokenServices defaultRemoteTokenServices = defaultRemoteTokenServices = new RemoteTokenServices();
    public CustomRemoteTokenServices() {
        matcher.add("/**");
        matchRemoteTokenService.add(defaultRemoteTokenServices);
    }
    public void addNewMatchRemoteTokenServices(String matchPath, RemoteTokenServices remoteTokenServices) {
        matcher.add(matchPath);
        matchRemoteTokenService.add(remoteTokenServices);
    }
    public void setRestTemplate(RestOperations restTemplate) {
        defaultRemoteTokenServices.setRestTemplate(restTemplate);
    }
    public void setCheckTokenEndpointUrl(String checkTokenEndpointUrl) {
        defaultRemoteTokenServices.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
    }
    public void setClientId(String clientId) {
        defaultRemoteTokenServices.setClientId(clientId);
    }
    public void setClientSecret(String clientSecret) {
        defaultRemoteTokenServices.setClientSecret(clientSecret);
    }
    public void setAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
        defaultRemoteTokenServices.setAccessTokenConverter(accessTokenConverter);
    }
    public void setTokenName(String tokenName) {
        defaultRemoteTokenServices.setTokenName(tokenName);
    }
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        RemoteTokenServices remoteTokenServices = matchRemoteTokenService.get(0);
        ServletRequest request = CorsFilter.threadLocalRequest.get();
        if( request != null ) {
            HttpServletRequest req = (HttpServletRequest) request;
            String uri = req.getRequestURI();
            this.logger.info(uri);
            for (int i = 0; i < matcher.size(); i++) {
                String match = matcher.get(i);
                if (antPathMatcher.match(match, uri)) {
                    remoteTokenServices = matchRemoteTokenService.get(i);
                }
            }
            RemoteTokenServiceCache hash = RemoteTokenServiceCache.getHash(req, remoteTokenServices);
            if( hash != null ) {
                return hash.auth;
            }
            OAuth2Authentication oAuth2Authentication = remoteTokenServices.loadAuthentication(accessToken);
            RemoteTokenServiceCache.putHash(req, remoteTokenServices, oAuth2Authentication);
            return oAuth2Authentication;
        }
        return remoteTokenServices.loadAuthentication(accessToken);
    }
    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }
}
