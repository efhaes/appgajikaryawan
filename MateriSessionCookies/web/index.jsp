<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    // escape sederhana supaya input user tidak bisa menyisipkan HTML/script
    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Latihan Session dan Cookies</title>
    </head>
    <body>
        <%
            // data dari session
            String userId = (String) session.getAttribute("userId");
            String pass = (String) session.getAttribute("password");
            Long loginMillis = (Long) session.getAttribute("waktuLogin");

            // data dari cookie (login terakhir sebelumnya)
            String loginSebelumnya = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("lastLogin")) {
                        try {
                            loginSebelumnya = new java.text.SimpleDateFormat("HH:mm:ss dd-MM-yyyy")
                                    .format(new java.util.Date(Long.parseLong(c.getValue())));
                        } catch (NumberFormatException e) {
                            loginSebelumnya = null;
                        }
                    }
                }
            }

            if (userId != null && loginMillis != null) {
                java.util.Date d = new java.util.Date(loginMillis);
                String waktu = new java.text.SimpleDateFormat("HH:mm:ss").format(d);
                String tanggal = new java.text.SimpleDateFormat("dd-MM-yyyy").format(d);
        %>
        <h2>Belajar Cookies dan Session</h2>
        <p>
            <b>ANDA SUDAH LOGIN</b> dengan User Id: <%= esc(userId) %>
            dan Password: <%= esc(pass) %>
        </p>
        <p>Login pada waktu <%= waktu %>. Tanggal <%= tanggal %></p>
        <% if (loginSebelumnya != null) { %>
        <p>Login sebelumnya (dari cookie): <%= loginSebelumnya %></p>
        <% } %>
        <p><a href="logout.jsp">Logout</a></p>
        <%
            } else {
        %>
        <h2 style="text-align:center">LOGIN</h2>
        <% if (request.getParameter("error") != null) { %>
        <p style="color:red">User ID dan Password harus diisi.</p>
        <% } %>
        <form action="validasi.jsp" method="post">
            <table>
                <tr>
                    <td>User ID</td>
                    <td><input type="text" name="userId"></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="LOGIN"></td>
                </tr>
            </table>
        </form>
        <% } %>
    </body>
</html>
