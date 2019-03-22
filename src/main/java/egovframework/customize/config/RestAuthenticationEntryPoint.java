package egovframework.customize.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import sun.misc.CharacterEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint
{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            Map<String, Object> data = new HashMap<>();
            data.put("timestamp", Calendar.getInstance().getTime());
            data.put("reason", "오류가 발생했습니다. 관리자에게 문의해 주세요");
            response.getOutputStream().write(objectMapper.writeValueAsString(data).getBytes("UTF-8"));
        } catch(Exception e) {
            response.getOutputStream().print("오류가 발생했습니다. 관리자에게 문의해 주세요");
        }
//
//        PrintWriter writer = response.getWriter();
//        writer.println("오류가 발생했습니다. 관리자에게 문의하세요");
    }
}

