<%@ page contentType="application/json" pageEncoding="UTF-8" isErrorPage="true" %><%
    int status = response.getStatus();
    String addMessage = " 관리자에게 문의해 주세요";
    String message = "오류가 발생했습니다.";
    out.println(String.format("{\"reason\":\"%s\"}", addMessage + "," + message));
%>