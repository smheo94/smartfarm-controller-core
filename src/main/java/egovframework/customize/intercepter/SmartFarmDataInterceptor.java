package egovframework.customize.intercepter;

import com.fasterxml.jackson.databind.ObjectMapper;
import egovframework.cmmn.util.InterceptPre;

import egovframework.cmmn.util.Result;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UriComponentsBuilder;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;

public class SmartFarmDataInterceptor extends HandlerInterceptorAdapter {
    public  final String SYSTEM_TYPE_SMARTFARM = "Smartfarm";
    public final String X_HEADER_GSM_KEY = "X-Smartfarm-Gsm-Key";
    String systemType;
    String myGSMKey;
    public SmartFarmDataInterceptor(String config, String myGSMKey) {
        this.systemType = config;
        this.myGSMKey = myGSMKey;
    }

    public boolean startTran = false;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String headerGsmKey = request.getHeader(X_HEADER_GSM_KEY);
        if( SYSTEM_TYPE_SMARTFARM.equalsIgnoreCase(systemType) ) {
            if( headerGsmKey == null || headerGsmKey != myGSMKey) {
                return false;
            }
            return false;
        } else if( headerGsmKey == null) {
            return true;
        }
        startTran = false;
        if( handler instanceof HandlerMethod) {
            String controllerName = "";
            String actionName = "";
            // there are cases where this handler isn't an instance of HandlerMethod, so the cast fails.
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if( handlerMethod.getMethod().getAnnotation(InterceptPre.class) != null ) {
                System.out.printf( "제어기 연동이 필요 합니다.");
                //TODO : 제어기에 데이터를 보낸다.
                ResponseEntity<Result> result = sendProxyRequest(request);
                boolean callResult = isFail(result);
                if(  callResult == false  ) {
                    //System.out.printf( "데이터 롤백을 진행 합니다.");
                    response.setContentType("application/json");
                    if( result != null ) {
                        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                        response.getWriter().flush();
                    }
                    //TODO: 더이상 진행하지 않고 오류를 Response에 셋팅합니다.
                    return false;
                }
            } else if( handlerMethod.getMethod().getAnnotation(InterceptPre.class) != null ) {
                System.out.printf( "DB 트렌젝션을 시작하세요. 롤백을 할 수 있어야 합니다.");
                //TODO : DB 트렌젝션을 시작하세요. 롤백을 할 수 있어야 합니다.
                startTran =true;
            }
            controllerName = handlerMethod.getBean().getClass().getSimpleName().replace("Controller", "");
            actionName = handlerMethod.getMethod().getName();
            System.out.printf( "==================== c %s, a %s\r\n", controllerName, actionName);
        }
        return true;
    }



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws  Exception {
        String headerGsmKey = request.getHeader(X_HEADER_GSM_KEY);
        if( SYSTEM_TYPE_SMARTFARM.equals(systemType) ) {
            //스마트팜에서 Post 필터가 필요 없음
            return;
        } else if( headerGsmKey == null) {
            //헤더가 없는경우 제어기로 내릴 수 없음
            return;
        }
        String controllerName = "";
        String actionName = "";
        if( handler instanceof HandlerMethod) {

            // there are cases where this handler isn't an instance of HandlerMethod, so the cast fails.
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if( handlerMethod.getMethod().getAnnotation(InterceptPre.class) != null ) {
                System.out.printf( "제어기에 데이터를 보냅니다.");
                ResponseEntity<Result> result = sendProxyRequest(request);
                boolean callResult = isFail(result);
                //TODO : 제어기에 데이터를 보냅니다.
                if( callResult == false && startTran  ) {
                    System.out.printf( "데이터 롤백을 진행 합니다.");
                    //TODO: 데이터 롤백을 진행 합니다.
                }
            }
            controllerName = handlerMethod.getBean().getClass().getSimpleName().replace("Controller", "");
            actionName = handlerMethod.getMethod().getName();
            System.out.printf( "==================== postHandle c %s, a %s\r\n", controllerName, actionName);
        }
    }



    public String readBody(HttpServletRequest request) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String buffer;
        while ((buffer = input.readLine()) != null) {
            if (builder.length() > 0) {
                builder.append("\n");
            }
            builder.append(buffer);
        }
        return builder.toString();
    }

    public boolean isFail(ResponseEntity<Result> result) {

        if( result == null || result.getStatusCode().is5xxServerError() || result.getStatusCode().is4xxClientError() ) {
            return false;
        }
        final Result body = result.getBody();
        if( body != null ) {
            final HttpStatus resultStatus = HttpStatus.valueOf(body.status);
            if( resultStatus.is4xxClientError() || resultStatus.is5xxServerError() ) {
                return false;
            }
        }
        return true;
    }
    public ResponseEntity<Result> sendProxyRequest(HttpServletRequest request) throws URISyntaxException, IOException {
        String server  = "192.168.10.99";
        Integer port = 58902;
        String requestUrl = request.getRequestURI();

        URI uri = new URI("http", null, server, port, null, null, null);
        uri = UriComponentsBuilder.fromUri(uri).path(requestUrl)
                .query(request.getQueryString()).build(true).toUri();

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(readBody(request), headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(uri, org.springframework.http.HttpMethod.valueOf(request.getMethod()) , httpEntity, Result.class);
    }



}
