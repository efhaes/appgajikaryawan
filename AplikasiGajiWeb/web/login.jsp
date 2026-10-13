<%-- 
    Document   : login
    Created on : Oct 12, 2026, 6:59:14 PM
    Author     : farha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Login Aplikasi Gaji</h1>
        <% if (request.getParameter("error") != null) { %>
            <p style="color:red;">KTP atau password salah</p>
        <% } %>
        <form action="validasi.jsp" method="post">
            KTP <input type="text" name="ktp"><br/>
            Password <input type="password" name="password"><br/>
            <input type="submit" value="Login">
        </form>
    </body>
</html>