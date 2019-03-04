<%@ page contentType="application/json" pageEncoding="UTF-8" isErrorPage="true" %><%
    int status = response.getStatus();
    String message = "오류가 발생했습니다.";
    if ( exception != null && exception.getMessage() != null ) {
        message = exception.getMessage();
    } else {
       if ( status == 400 )
           message = "Bad Request";
       else if ( status == 403 )
           message = "Forbidden";
       else if ( status == 404 )
           message = "리소스를 찾을 수 없습니다.";
    }
    out.println(String.format("{'status':'%s','reason':'%s'}", status, message));
    //request.getAttribute("javax.servlet.error.status_code")
    //request.getAttribute("javax.servlet.error.message")
%>