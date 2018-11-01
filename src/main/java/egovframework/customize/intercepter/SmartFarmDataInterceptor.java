package egovframework.customize.intercepter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.smartfarm.supervisor.mapper.GsmEnvMapper;

import egovframework.cmmn.util.InterceptIgnoreGSMKey;
import egovframework.cmmn.util.InterceptPost;
import egovframework.cmmn.util.InterceptPre;

import egovframework.cmmn.util.Result;
import egovframework.customize.message.ApplicationMessage;
import egovframework.customize.service.GsmEnvVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.WebUtils;


import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Objects;

import static egovframework.customize.message.ApplicationMessage.NOT_FOUND_GSM_INFO;

public class SmartFarmDataInterceptor extends HandlerInterceptorAdapter {
    private static final Log LOG = LogFactory.getLog( SmartFarmDataInterceptor.class );


    public  final String SYSTEM_TYPE_SMARTFARM = "Smartfarm";
    public static final String X_HEADER_GSM_KEY = "X-Smartfarm-Gsm-Key";
    String systemType;
    String myGSMKey;
    GsmEnvMapper gsmEnvMapper;
    public SmartFarmDataInterceptor(String config, String myGSMKey, GsmEnvMapper gsmMapper) {
        this.systemType = config;
        this.myGSMKey = "3785";
        this.gsmEnvMapper = gsmMapper;
    }

    public boolean startTran = false;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       
        if( handler instanceof HandlerMethod) {
            // there are cases where this handler isn't an instance of HandlerMethod, so the cast fails.
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            
        	 String headerGsmKey = request.getHeader(X_HEADER_GSM_KEY);
             if(headerGsmKey == null){
             	headerGsmKey = response.getHeader(X_HEADER_GSM_KEY);	
             }
             
             if( SYSTEM_TYPE_SMARTFARM.equalsIgnoreCase(systemType) ) {
                 if( handlerMethod.getMethod().getAnnotation(InterceptIgnoreGSMKey.class) ==null && (headerGsmKey == null || !Objects.equals(headerGsmKey, myGSMKey))) {
                     setErrorResult(response, String.format(ApplicationMessage.MISS_MATCHING_GSM_KEY, headerGsmKey),
                             HttpStatus.FORBIDDEN);
                     return false;
                 }
                 //헤더의 GSM 이 맞는경우
                 return true;
             } 
             else if( headerGsmKey == null) {
                 //SuperVisor고 GSM 이 없는경우는 그냥 수행
                 return true;
             }
             startTran = false;
             
             if( handlerMethod.getMethod().getAnnotation(InterceptPre.class) != null) {
                System.out.printf( "제어기 연동이 필요 합니다.");
                //TODO : 제어기에 데이터를 보낸다.
                Integer gsmKey = Integer.valueOf(headerGsmKey);

                ResponseEntity<ResponseResult> result = sendProxyRequest(gsmKey, InterceptPre.class, request, response);
                boolean callResult = isSuccessResult(result);
                if(  callResult == false  ) {
                    //System.out.printf( "데이터 롤백을 진행 합니다.");
                    response.setContentType("application/json");
                    if( result != null ) {
                        response.sendError(result.getStatusCode().value(), new ObjectMapper().writeValueAsString(result.getBody()));
                    }
                    //TODO: 더이상 진행하지 않고 오류를 Response에 셋팅합니다.
                    return false;                    
                }
            } else if( handlerMethod.getMethod().getAnnotation(InterceptPost.class) != null ) {
                System.out.printf( "DB 트렌젝션을 시작하세요. 롤백을 할 수 있어야 합니다.");
                //TODO : DB 트렌젝션을 시작하세요. 롤백을 할 수 있어야 합니다.
                startTran =true;
            }
        }
        return true;
    }


    public void setErrorResult(HttpServletResponse response, String message, HttpStatus code) {
        response.setContentType("application/json");
        try {
            response.sendError(HttpStatus.OK.value(), new ObjectMapper().writeValueAsString(new Result(message, code, message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws  Exception {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        HttpStatus responseStatus = HttpStatus.valueOf(response.getStatus());
        try {
            if( responseStatus.is4xxClientError() || responseStatus.is5xxServerError() ) {
                return;
            }
            String headerGsmKey = request.getHeader(X_HEADER_GSM_KEY);
            if(headerGsmKey == null){
            	headerGsmKey = response.getHeader(X_HEADER_GSM_KEY);	
            }
            if( SYSTEM_TYPE_SMARTFARM.equalsIgnoreCase(systemType) ) {
                //스마트팜에서 Post 필터가 필요 없음
                return;
            } else if( headerGsmKey == null) {
                //헤더가 없는경우 제어기로 내릴 수 없음
                return;
            }

            if (handler instanceof HandlerMethod) {
                // there are cases where this handler isn't an instance of HandlerMethod, so the cast fails.
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                if (handlerMethod.getMethod().getAnnotation(InterceptPost.class) != null) {
                    System.out.printf("제어기에 데이터를 보냅니다.");
                    Integer gsmKey = Integer.valueOf(headerGsmKey);
                    ResponseEntity<ResponseResult> result = sendProxyRequest(gsmKey, InterceptPost.class, request, wrapper);
                    boolean callResult = isSuccessResult(result);
                    //TODO : 제어기에 데이터를 보냅니다.
                    if (!callResult && startTran) {
                        System.out.printf("데이터 롤백을 진행 합니다.");
                        //TODO: 데이터 롤백을 진행 합니다.
                    } else {
                        //Commit()
                    }
                }

            }
        } catch(Exception e){
        	e.printStackTrace();
        }finally {

            wrapper.copyBodyToResponse();
        }

    }

    private HttpEntity getHttpEntity(Class annotationClass, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if( annotationClass.equals(InterceptPre.class) ) {
            return getHttpPreEntity(request, response);
        } else {
            return getHttpPostEntity(request, response);
        }
    }
    private ObjectMapper objMapper =  new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private HttpEntity getHttpPostEntity(HttpServletRequest request, HttpServletResponse respone) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }
        ContentCachingResponseWrapper wrapper = (ContentCachingResponseWrapper)respone;
        if( wrapper != null ) {
            byte [] responseData = wrapper.getContentAsByteArray();
            ResponseResult result = objMapper.readValue(responseData, ResponseResult.class);
            if( result.status == HttpStatus.OK.value() ) {
                String data  = objMapper.writeValueAsString(result.data);
                return new HttpEntity<String>(data, headers);
            } else  {
                return null;
            }
        }
        return null;
    }


    private HttpEntity getHttpPreEntity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }

        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if( wrapper != null ) {
            byte [] requestData = wrapper.getContentAsByteArray();
            return new HttpEntity<byte[]>(requestData, headers);
        }
        return null;
    }

    public boolean isSuccessResult(ResponseEntity<ResponseResult> result) {

        if( result == null || result.getStatusCode().is5xxServerError() || result.getStatusCode().is4xxClientError() ) {
            return false;
        }
        final ResponseResult body = result.getBody();
        if( body != null ) {
            final HttpStatus resultStatus = HttpStatus.valueOf(body.status);
            if( resultStatus.is4xxClientError() || resultStatus.is5xxServerError() ) {
                return false;
            }
        }
        return true;
    }

    public ResponseEntity<ResponseResult> sendProxyRequest(Integer gsmKey, Class annotationClass, HttpServletRequest request, HttpServletResponse response) throws URISyntaxException,
            IOException,
            HttpStatusCodeException {

        final GsmEnvVO gsmEnvVO = this.gsmEnvMapper.get(gsmKey);
        if( gsmEnvVO == null ) {
            throw new HttpClientErrorException( HttpStatus.NOT_FOUND, NOT_FOUND_GSM_INFO);
        }
        String server  = gsmEnvVO.getSystemHost();
        Integer port = gsmEnvVO.getSystemPort();
        String requestUrl = request.getRequestURI();
        URI uri = new URI("http", null, server, port, null, null, null);
        uri = UriComponentsBuilder.fromUri(uri).path(requestUrl)
                .query(request.getQueryString()).build(true).toUri();        

        HttpEntity httpEntity = getHttpEntity(annotationClass, request, response);
        if( httpEntity == null ) {
               return null;
        }
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        final ResponseEntity<ResponseResult> returnValue = restTemplate.exchange(uri, HttpMethod.valueOf(request.getMethod()), httpEntity, ResponseResult.class);
        return returnValue;
    }
}
