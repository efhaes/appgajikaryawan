<%-- 
    Document   : validasi
    Created on : Oct 12, 2026, 6:59:38 PM
    Author     : farha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.Cookie, model.Karyawan, model.Enkripsi"%>
<%
    request.setCharacterEncoding("UTF-8");
    String ktp = request.getParameter("ktp");
    String password = request.getParameter("password");
    boolean valid = false;
    String nama = "";

    if (ktp != null && password != null && !ktp.trim().isEmpty()) {
        try {
            Karyawan k = new Karyawan();
            if (k.baca(ktp.trim())
                    && new Enkripsi().hashMD5(password).equals(k.getPassword())) {
                valid = true;
                nama = k.getNama();
            }
        } catch (Exception e) {
            valid = false;
        }
    }

    if (valid) {
        // baca cookie login sebelumnya, simpan ke session untuk ditampilkan
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("lastLogin")) {
                    session.setAttribute("loginSebelumnya", c.getValue());
                }
            }
        }

        // session
        session.setAttribute("userLogin", nama);
        session.setAttribute("ktpLogin", ktp.trim());
        session.setMaxInactiveInterval(30 * 60); // 30 menit

        // cookie: waktu login terakhir, berlaku 1 hari
        Cookie lastLogin = new Cookie("lastLogin", String.valueOf(System.currentTimeMillis()));
        lastLogin.setMaxAge(60 * 60 * 24);
        response.addCookie(lastLogin);

        response.sendRedirect("beranda.jsp");
    } else {
        response.sendRedirect("login.jsp?error=1");
    }
%>
