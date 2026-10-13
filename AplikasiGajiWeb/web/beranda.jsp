<%-- 
    Document   : beranda
    Created on : Oct 12, 2026, 6:59:57 PM
    Author     : farha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("userLogin") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String sebelumnya = (String) session.getAttribute("loginSebelumnya");
    String teksSebelumnya = "Ini login pertama Anda";
    if (sebelumnya != null) {
        try {
            teksSebelumnya = new java.util.Date(Long.parseLong(sebelumnya)).toString();
        } catch (NumberFormatException e) { }
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Beranda</title>
    </head>
    <body>
        <h1>Selamat datang, <%= session.getAttribute("userLogin") %></h1>
        <p>KTP: <%= session.getAttribute("ktpLogin") %></p>
        <p>Login terakhir (dari cookie): <%= teksSebelumnya %></p>
        <p>
            <a href="cari.jsp">Cari Karyawan</a> |
            <a href="logout.jsp">Logout</a>
        </p>
    </body>
</html>
