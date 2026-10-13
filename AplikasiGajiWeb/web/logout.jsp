<%-- 
    Document   : logout
    Created on : Oct 12, 2026, 7:01:26 PM
    Author     : farha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session.invalidate();
    response.sendRedirect("login.jsp");
%>
