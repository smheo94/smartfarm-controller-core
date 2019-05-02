package egovframework.cmmn.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RestClientUtil {

    public static ParameterizedTypeReference<HashMap<String, Object>> hashmapResponseType =
            new ParameterizedTypeReference<HashMap<String, Object>>() {};
    private static ResponseEntity<HashMap<String, Object>> restTemplatePost(RestTemplate client , String url, HttpMethod method, Map<String, Object> data,
                                                                            boolean toCamel) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if( data != null  ) {
            if (toCamel) {
                data = MapUtils.camelizeMap(data);
            }
        }
        HttpEntity<Map<String,Object>> param = new HttpEntity<>(data, headers);
        ResponseEntity<HashMap<String, Object> > result = client.exchange(url, method, param, hashmapResponseType);
        return result;
    }

    public static ResponseEntity<HashMap<String, Object>> post(String url, Map<String, Object> data, boolean toCamel) {

        RestTemplate client = new RestTemplate();
        return restTemplatePost(client, url, HttpMethod.POST, data, toCamel);
    }

    ClientCredentialsResourceDetails resourceDetails = null;

    public RestClientUtil(String tokenUrl, String clientId, String passwd) {
        resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setAccessTokenUri(String.format("%s/oauth/token", tokenUrl));
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret(passwd);
        resourceDetails.setGrantType("client_credentials");
        resourceDetails.setScope(Arrays.asList("read", "write"));

    }

    public ResponseEntity<HashMap<String, Object>> oauth2Post(String url, Map<String, Object> data, boolean toCamel) {
        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
        OAuth2RestTemplate client = new OAuth2RestTemplate(resourceDetails, clientContext);
        return restTemplatePost(client, url, HttpMethod.POST, data, toCamel);
    }

    public ResponseEntity<HashMap<String, Object>> oauth2exchange(String url, HttpMethod method, Map<String, Object> data, boolean toCamel) {
        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
        OAuth2RestTemplate client = new OAuth2RestTemplate(resourceDetails, clientContext);
        return restTemplatePost(client, url, method, data, toCamel);
    }

}
