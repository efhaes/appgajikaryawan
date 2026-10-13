<%-- 
    Document   : login
    Created on : Oct 12, 2026, 3:25:14 PM
    Author     : farha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login</h1>
            <from action="loginform.do" method="post">
                Username <input type="text" name="uname"/><br/>
                Password <input type="password" name="upass"/><br/>
                   <input type="submit" value="OK"/>
            </from>
    </body>
</html>
