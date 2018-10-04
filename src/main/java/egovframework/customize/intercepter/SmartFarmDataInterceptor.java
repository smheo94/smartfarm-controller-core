package egovframework.customize.intercepter;

import egovframework.cmmn.util.InterceptPre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SmartFarmDataInterceptor extends HandlerInterceptorAdapter {
    public  final String SYSTEM_TYPE_SMARTFARM = "Smartfarm";
    public final String X_HEADER_GSM_KEY = "x-smartfarm-gsm-key";
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
        if( SYSTEM_TYPE_SMARTFARM.equals(systemType) ) {
            if( headerGsmKey == null || headerGsmKey != myGSMKey) {
                return true;
            }
            return false;
        } else if( headerGsmKey == null) {
                return false;
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
                boolean callResult = true;
                if( callResult == false  ) {
                    System.out.printf( "데이터 롤백을 진행 합니다.");
                    //TODO: 더이상 진행하지 않고 오류를 Response에 셋팅합니다.
                    return true;
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
        return false;
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
                //TODO : 제어기에 데이터를 보냅니다.
                boolean callResult = true;
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



}
