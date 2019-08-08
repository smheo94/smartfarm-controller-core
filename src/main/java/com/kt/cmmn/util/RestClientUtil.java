package com.kt.cmmn.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RestClientUtil {
    public static ConcurrentHashMap<String, OAuth2RestTemplate> restClientMap = new ConcurrentHashMap<>();
    public static ParameterizedTypeReference<HashMap<String, Object>> hashmapResponseType = new ParameterizedTypeReference<HashMap<String, Object>>() {};
    private static ResponseEntity<HashMap<String, Object>> restTemplatePost(RestTemplate client , String url, HttpMethod method,  Object data,
                                                                            boolean toCamel) {
        ResponseEntity<HashMap<String, Object>> result = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            if (data != null) {
                if (toCamel && data instanceof Map) {
                    data = MapUtils.camelizeMap((Map) data);
                }
            }
            HttpEntity param = new HttpEntity<>(data, headers);
            result = client.exchange(url, method, param, hashmapResponseType);
        } catch(Exception e) {
            log.info("RestClient : {}", Thread.currentThread().getId());
            log.warn("RestClient Error : {}, {}, {}", url, method, e );
        }
        return result;
    }

    public static ResponseEntity<HashMap<String, Object>> post(String url, Map<String, Object> data, boolean toCamel) {

        RestTemplate client = new RestTemplate();
        return restTemplatePost(client, url, HttpMethod.POST, data, toCamel);
    }
    OAuth2RestTemplate client = null;
    public RestClientUtil( String tokenUrl, String clientId, String passwd) {
        String oauthServerKey =  String.format("%s_%s_%s", tokenUrl, clientId, passwd);
        synchronized (restClientMap) {
            if (restClientMap.containsKey(oauthServerKey)) {
                client = restClientMap.get(oauthServerKey);
                log.info("Oauth2RestTemplate Get : {}", Thread.currentThread().getId());
            } else {
                ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
                resourceDetails.setAccessTokenUri(String.format("%s/oauth/token", tokenUrl));
                resourceDetails.setClientId(clientId);
                resourceDetails.setClientSecret(passwd);
                resourceDetails.setGrantType("client_credentials");
                resourceDetails.setScope(Arrays.asList("read", "write"));
                DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
                client = new OAuth2RestTemplate(resourceDetails, clientContext);
                restClientMap.put(oauthServerKey, client);
                log.info("Oauth2RestTemplate Create :{}", Thread.currentThread().getId());
            }
        }
    }
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> tClass, Object... urlData){
        return client.getForEntity(url, tClass, urlData);
    }

    public ResponseEntity<HashMap<String, Object>> oauth2Post(String url, Map<String, Object> data, boolean toCamel) {
        return restTemplatePost(client, url, HttpMethod.POST, data, toCamel);
    }
    public ResponseEntity<HashMap<String, Object>> oauth2Post(String url, Object data, boolean toCamel) {
        return restTemplatePost(client, url, HttpMethod.POST, data, toCamel);
    }

    public ResponseEntity<HashMap<String, Object>> oauth2exchange(String url, HttpMethod method, Map<String, Object> data, boolean toCamel) {
        return restTemplatePost(client, url, method, data, toCamel);
    }

}
