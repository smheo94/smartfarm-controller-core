package com.kt.cmmn.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RestClientUtil {
    public static void setIgnoreCertificateSSL(RestTemplate template) {
        template.setRequestFactory(
                new SimpleClientHttpRequestFactory() {
                    @Override
                    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                        if (connection instanceof HttpsURLConnection) {
                            ((HttpsURLConnection) connection).setHostnameVerifier((hostname, session) -> true); // 호스트 검증을 항상 pass하고
                            SSLContext sc;
                            try {
                                sc = SSLContext.getInstance("SSL"); // SSLContext를 생성하여
                                sc.init(null, new TrustManager[]{new X509TrustManager() { // 공개키 암호화 설정을 무력화시킨다.
                                    @Override
                                    public X509Certificate[] getAcceptedIssuers() {
                                        return null;
                                    }

                                    @Override
                                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                                    }

                                    @Override
                                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                                    }
                                }}, new SecureRandom());
                                ((HttpsURLConnection) connection).setSSLSocketFactory(sc.getSocketFactory());
                            } catch (NoSuchAlgorithmException e) {
                                log.info("setIgnoreCertificateSSL NoSuchAlgorithmException Exception :{}", e);
                            } catch (KeyManagementException e) {
                                log.info("setIgnoreCertificateSSL KeyManagementException Exception :{}", e);
                            }
                        }
                        super.prepareConnection(connection, httpMethod);
                    }
                });
    }
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
            //log.info("RestClient : {}", Thread.currentThread().getId());
            log.warn("RestClient Error : {}, {}, {}", url, method, e );
        }
        return result;
    }

    public static ResponseEntity<HashMap<String, Object>> post(String url, Map<String, Object> data, boolean toCamel) {
        RestTemplate client = new RestTemplate();
        setIgnoreCertificateSSL(client);
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
                setIgnoreCertificateSSL(client);
                restClientMap.put(oauthServerKey, client);
                log.info("Oauth2RestTemplate Create :{}", Thread.currentThread().getId());
            }
        }
    }
    public OAuth2RestTemplate getClient() {
        return this.client;
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
