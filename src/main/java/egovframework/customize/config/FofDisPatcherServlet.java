//package egovframework.customize.config;
//
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.DispatcherServlet;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//import org.springframework.web.util.ContentCachingRequestWrapper;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component("dispatcherServlet")
//public class FofDisPatcherServlet extends DispatcherServlet {
//    @Override
//    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        try {        	
//            super.doDispatch(new ContentCachingRequestWrapper(request), new ContentCachingResponseWrapper(response));
//        } catch (Exception e) {
//            super.doDispatch(request, response);
//        }
//    }
//
//}
//
