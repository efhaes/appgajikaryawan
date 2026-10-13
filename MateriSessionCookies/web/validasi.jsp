<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");
    String userId = request.getParameter("userId");
    String password = request.getParameter("password");

    if (userId != null && !userId.trim().isEmpty()
            && password != null && !password.isEmpty()) {

        long sekarang = System.currentTimeMillis();

        // cookie: waktu login terakhir sebelum login ini dibaca di index.jsp,
        // lalu cookie diperbarui dengan waktu login yang sekarang
        Cookie lastLogin = new Cookie("lastLogin", String.valueOf(sekarang));
        lastLogin.setMaxAge(60 * 60 * 24); // 1 hari
        response.addCookie(lastLogin);

        // session
        session.setAttribute("userId", userId.trim());
        session.setAttribute("password", password);
        session.setAttribute("waktuLogin", sekarang);
        session.setMaxInactiveInterval(30 * 60); // 30 menit

        response.sendRedirect("index.jsp");
    } else {
        response.sendRedirect("index.jsp?error=1");
    }
%>
