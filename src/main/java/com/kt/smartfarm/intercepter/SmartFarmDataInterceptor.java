package com.kt.smartfarm.intercepter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.cmmn.util.*;
import com.kt.smartfarm.mapper.GsmEnvMapper;
import com.kt.smartfarm.message.ApplicationMessage;
import com.kt.smartfarm.service.GsmEnvVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Objects;

import static com.kt.smartfarm.message.ApplicationMessage.NOT_FOUND_GSM_INFO;

@Slf4j
public class SmartFarmDataInterceptor extends HandlerInterceptorAdapter {
    public static final String X_HEADER_GSM_KEY = "x-smartfarm-gsm-key";
    Boolean isSmartfarmSystem;
    String myGSMKey;
    String proxySubPath;
    GsmEnvMapper gsmEnvMapper;
    public boolean startTran = false;
    public SmartFarmDataInterceptor(Boolean isSmartfarmSystem, String proxySubPath, String myGSMKey, GsmEnvMapper gsmEnvMapper) {
        this.isSmartfarmSystem = isSmartfarmSystem;
        this.proxySubPath = proxySubPath;
        this.myGSMKey = myGSMKey;
        this.gsmEnvMapper = gsmEnvMapper;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            // there are cases where this handler isn't an instance of HandlerMethod, so the cast fails.
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            String headerGsmKey = request.getHeader(X_HEADER_GSM_KEY);
            if (headerGsmKey == null) {
                headerGsmKey = response.getHeader(X_HEADER_GSM_KEY);
            }
            //boolean preIntercept = handlerMethod.getMethod().getAnnotation(InterceptPre.class) != null;
            boolean postIntercept = handlerMethod.getMethod().getAnnotation(InterceptPost.class) != null;
//            boolean logIntercept = handlerMethod.getMethod().getAnnotation(InterceptLog.class) != null;
//            if (logIntercept) {
//                writeAPILog(request);
//            }
            if (isSmartfarmSystem) {
                if (handlerMethod.getMethod().getAnnotation(InterceptIgnoreGSMKey.class) == null && (headerGsmKey == null || !Objects.equals(headerGsmKey, myGSMKey))) {
                    setErrorResult(response, String.format(ApplicationMessage.MISS_MATCHING_GSM_KEY, headerGsmKey),
                            HttpStatus.FORBIDDEN);
                    return false;
                }
                //헤더의 GSM 이 맞는경우
                return true;
            } else if (headerGsmKey == null) {
                //SuperVisor고 GSM 이 없는경우는 그냥 수행
                return true;
            }
            startTran = false;
//             if( preIntercept) {
//                 AuthorityChecker authChecker = new AuthorityChecker();
////                System.out.printf( "제어기 연동이 필요 합니다.");
////                //TODO : 제어기에 데이터를 보낸다.
////                Long gsmKey = Integer.valueOf(headerGsmKey);
////
////                ResponseEntity<ResponseResult> result = sendProxyRequest(gsmKey, InterceptPre.class, request, response);
////                boolean callResult = isSuccessResult(result);
////                if(  callResult == false  ) {
////                    //System.out.printf( "데이터 롤백을 진행 합니다.");
////                    response.setContentType("application/json");
////                    if( result != null ) {
////                        response.sendError(result.getStatusCode().value(), new ObjectMapper().writeValueAsString(result.getBody()));
////                    }
////                    //TODO: 더이상 진행하지 않고 오류를 Response에 셋팅합니다.
////                    return false;
////                }
//            } else
            if (postIntercept) {
                AuthorityChecker authChecker = new AuthorityChecker();
                //System.out.printf( "DB 트렌젝션을 시작하세요. 롤백을 할 수 있어야 합니다.");
                //TODO : DB 트렌젝션을 시작하세요. 롤백을 할 수 있어야 합니다.
                startTran = true;
            }
        }
        return true;
    }
    private ContentCachingRequestWrapper getCachingRequest(HttpServletRequest request) {
       if( request instanceof SecurityContextHolderAwareRequestWrapper   ) {
           return getCachingRequest( (HttpServletRequest)((SecurityContextHolderAwareRequestWrapper)request).getRequest() );
       } else if( request instanceof  FirewalledRequest ) {
           return getCachingRequest( (HttpServletRequest)((FirewalledRequest)request).getRequest() );
       } else if( request instanceof  ContentCachingRequestWrapper ) {
           return (ContentCachingRequestWrapper) request;
       } else {
           return new ContentCachingRequestWrapper(request);
       }
    }
    private void writeAPILog(HttpServletRequest request) throws IOException {

        try {
            AuthorityChecker authChecker = new AuthorityChecker();
            //ring requestBody ="";
            ContentCachingRequestWrapper wrapper = getCachingRequest(request);
            //((ContentCachingRequestWrapper) request).getContentAsByteArray()
            String encoding = StringUtils.isBlank(request.getCharacterEncoding()) ? Charsets.toCharset("UTF-8").name() : Charsets.toCharset(request.getCharacterEncoding()).name();
            //ContentCachingRequestWrapper request = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
            String requestBody = IOUtils.toString(wrapper.getContentAsByteArray(), encoding);
            String requestPath = request.getRequestURI();
            log.info("{}/{} : {} - {}", authChecker.getName(), authChecker.getRemoteAddr(), requestPath, requestBody);
        } catch (Exception e) {
            log.warn("WriteApi Log Error", e);
        }
    }


    public void setErrorResult(HttpServletResponse response, String message, HttpStatus code) {
        response.setContentType("application/json");
        try {
            response.sendError(HttpStatus.OK.value(), new ObjectMapper().writeValueAsString(new Result(message, code, message)));
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws  Exception {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        HttpStatus responseStatus = HttpStatus.valueOf(response.getStatus());
        HandlerMethod handlerMethod = null;
        try {
            if( handler instanceof HandlerMethod ) {
                handlerMethod = (HandlerMethod) handler;
                boolean logIntercept = handlerMethod.getMethod().getAnnotation(InterceptLog.class) != null;
                if (logIntercept) {
                    writeAPILog(request);
                }
            }
            if( isSmartfarmSystem ) {
                //스마트팜에서 Post 필터가 필요 없음
                return;
            }
            if( responseStatus.is4xxClientError() || responseStatus.is5xxServerError() ) {
                return;
            }
            String headerGsmKey = request.getHeader(X_HEADER_GSM_KEY);
            if(headerGsmKey == null){
            	headerGsmKey = response.getHeader(X_HEADER_GSM_KEY);	
            }
            if (handler instanceof HandlerMethod) {
                // there are cases where this handler isn't an instance of HandlerMethod, so the cast fails.
                handlerMethod = (HandlerMethod) handler;
                if (handlerMethod.getMethod().getAnnotation(InterceptPost.class) != null ||
                        handlerMethod.getMethod().getAnnotation(InterceptPre.class) != null) {
                    if( headerGsmKey == null) {
                        if( (request.getMethod().equalsIgnoreCase(HttpMethod.POST.toString()) ||
                                request.getMethod().equalsIgnoreCase(HttpMethod.DELETE.toString()) ||
                                request.getMethod().equalsIgnoreCase(HttpMethod.PUT.toString()) )
                                && (handler instanceof HandlerMethod) )  {
                            log.warn("in HeaderGSMKEY NULL : {} , {}, {}, {}", headerGsmKey, request.getRequestURI(), request.getMethod(), handler);
                        }
                        //헤더가 없는경우 제어기로 내릴 수 없음
                        return;
                    }
                    log.info("제어기에 데이터를 보냅니다. , {}", handlerMethod.getMethod() );
                    Long gsmKey = Long.valueOf(headerGsmKey);
                    ResponseEntity<ResponseResult> result = null;
                    if( handlerMethod.getMethod().getAnnotation(InterceptPre.class) != null) {
                        result = sendProxyRequest(gsmKey, InterceptPre.class, request, wrapper);
                    } else {
                        result = sendProxyRequest(gsmKey, InterceptPost.class, request, wrapper);
                    }
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
            log.info(e.getMessage());
        }
//        finally {
//            if( handlerMethod != null) {
//                boolean logIntercept = handlerMethod.getMethod().getAnnotation(InterceptLog.class) != null;
//                if (logIntercept) {
//                    writeAPILog(request);
//                }
//            }
//        }
//        finally {
//
//            wrapper.copyBodyToResponse();
//        }

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

    private boolean isSuccessResult(ResponseEntity<ResponseResult> result) {

        if( result == null || result.getStatusCode().is5xxServerError() || result.getStatusCode().is4xxClientError() ) {
            return false;
        }
        final ResponseResult body = result.getBody();
        if( body != null ) {
            final HttpStatus resultStatus = HttpStatus.valueOf(body.status);
            return !resultStatus.is4xxClientError() && !resultStatus.is5xxServerError();
        }
        return true;
    }

    private ResponseEntity<ResponseResult> sendProxyRequest(Long gsmKey, Class annotationClass, HttpServletRequest request, HttpServletResponse response) throws URISyntaxException,
            IOException,
            HttpStatusCodeException {
        final GsmEnvVO gsmEnvVO = this.gsmEnvMapper.get(gsmKey);
        if( gsmEnvVO == null ) {
            throw new HttpClientErrorException( HttpStatus.NOT_FOUND, NOT_FOUND_GSM_INFO);
        }
        String server  = gsmEnvVO.getSystemHostWithoutSchema();
        Integer port = gsmEnvVO.getSystemPort();
        String httpSchema = gsmEnvVO.getHttpSchema();
        String requestUrl = request.getRequestURI();
        URI uri = new URI(httpSchema, null, server, port, null, null, null);
        uri = UriComponentsBuilder.fromUri(uri).path(proxySubPath + requestUrl)
                .query(request.getQueryString()).build(true).toUri();

        HttpEntity httpEntity = getHttpEntity(annotationClass, request, response);
        if( httpEntity == null ) {
            return null;
        }
        RestTemplate restTemplate = new RestTemplate();
        RestClientUtil.setIgnoreCertificateSSL(restTemplate);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        log.debug("Send Proxy Request : {} , {}, {}", gsmKey, uri, request.getMethod());
        final ResponseEntity<ResponseResult> returnValue = restTemplate.exchange(uri, HttpMethod.valueOf(request.getMethod()), httpEntity, ResponseResult.class);
        return returnValue;
    }
}
