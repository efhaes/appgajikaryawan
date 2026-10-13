/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.controller;

import com.unpam.model.Gaji;
import com.unpam.view.MainForm;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LaporanGajiController", urlPatterns = {"/LaporanGajiController"})
public class LaporanGajiController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String[][] formatTypeData = {
            {"PDF (Portable Document Format)", "pdf", "application/pdf"},
            {"XLSX (Microsoft Excel)", "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {"XLS (Microsoft Excel 97-2003)", "xls", "application/vnd.ms-excel"},
            {"DOCX (Microsoft Word)", "docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {"ODT (OpenDocument Text)", "odt", "application/vnd.oasis.opendocument.text"},
            {"RTF (Rich Text Format)", "rtf", "text/rtf"}};

        HttpSession session = request.getSession(true);
        String userName = "";

        String tombol = request.getParameter("tombol");
        String opsi = request.getParameter("opsi");
        String ktp = request.getParameter("ktp");
        String ruang = request.getParameter("ruang");
        String formatType = request.getParameter("formatType");

        if (tombol == null) tombol = "";
        if (ktp == null) ktp = "";
        if (opsi == null) opsi = "";
        if (ruang == null) ruang = "0";

        String keterangan = "<br>";
        int noType = 0;

        for (int i = 0; i < formatTypeData.length; i++) {
            if (formatTypeData[i][0].equals(formatType)) {
                noType = i;
            }
        }

        try {
            userName = session.getAttribute("userName").toString();
        } catch (Exception ex) {}

        if (!((userName == null) || userName.equals(""))) {
            boolean opsiSelected = false;

            if (tombol.equals("Cetak")) {
                Gaji gaji = new Gaji();

                int ruangDipilih = 0;
                try {
                    ruangDipilih = Integer.parseInt(ruang);
                } catch (NumberFormatException ex) {}

                if (gaji.cetakLaporan(opsi, ktp, ruangDipilih, formatTypeData[noType][1],
                        getServletConfig().getServletContext().getRealPath("reports/GajiReport.jrxml"))) {
                    byte[] pdfasbytes = gaji.getPdfasbytes();
                    try (OutputStream outStream = response.getOutputStream()) {
                        response.setHeader("Content-Disposition",
                                "inline; filename=GajiReport." + formatTypeData[noType][1]);
                        response.setContentType(formatTypeData[noType][2]);

                        response.setContentLength(pdfasbytes.length);
                        outStream.write(pdfasbytes, 0, pdfasbytes.length);

                        outStream.flush();
                        outStream.close();
                    }
                    return; // file sudah terkirim, jangan tulis HTML lagi
                } else {
                    keterangan += gaji.getPesan();
                }
            }

            String konten = "<h2>Mencetak Gaji</h2>";
            konten += "<form action='LaporanGajiController' method='post'>";
            konten += "<table>";

            konten += "<tr>";
            if (opsi.equalsIgnoreCase("KTP")) {
                konten += "<td align='right'><input type='radio' checked name='opsi' value='KTP'></td>";
                opsiSelected = true;
            } else {
                konten += "<td align='right'><input type='radio' name='opsi' value='KTP'></td>";
            }
            konten += "<td align='left'>KTP</td>";
            konten += "<td align='left'><input type='text' value='" + ktp + "' name='ktp' maxlength='15' size='15'></td>";
            konten += "</tr>";

            konten += "<tr>";
            if (opsi.equals("ruang")) {
                konten += "<td align='right'><input type='radio' checked name='opsi' value='ruang'></td>";
                opsiSelected = true;
            } else {
                konten += "<td align='right'><input type='radio' name='opsi' value='ruang'></td>";
            }
            konten += "<td align='left'>Ruang</td>";
            konten += "<td align='left'>";
            konten += "<select name='ruang'>";
            konten += "<option selected value=0>Semua</option>";
            for (int i = 1; i <= 14; i++) {
                if (i == Integer.parseInt(ruang)) {
                    konten += "<option selected value=" + i + ">" + i + "</option>";
                } else {
                    konten += "<option value=" + i + ">" + i + "</option>";
                }
            }
            konten += "</select>";
            konten += "</td>";
            konten += "</tr>";

            konten += "<tr>";
            if (!opsiSelected) {
                konten += "<td align='right'><input type='radio' checked name='opsi' value='Semua'></td>";
            } else {
                konten += "<td align='right'><input type='radio' name='opsi' value='Semua'></td>";
            }
            konten += "<td align='left'>Semua</td>";
            konten += "<td><br></td>";
            konten += "</tr>";

            konten += "<tr>";
            konten += "<td colspan='3'><br></td>";
            konten += "</tr>";

            konten += "<tr>";
            konten += "<td>Format Laporan</td>";
            konten += "<td colspan=2>";
            konten += "<select name='formatType'>";
            for (String[] formatLaporan : formatTypeData) {
                if (formatLaporan[0].equals(formatType)) {
                    konten += "<option selected value='" + formatLaporan[0] + "'>" + formatLaporan[0] + "</option>";
                } else {
                    konten += "<option value='" + formatLaporan[0] + "'>" + formatLaporan[0] + "</option>";
                }
            }
            konten += "</select>";
            konten += "</td>";
            konten += "</tr>";

            konten += "<tr>";
            konten += "<td colspan='3'><b>" + keterangan.replaceAll("\n", "<br>").replaceAll(";", ",") + "</b></td>";
            konten += "</tr>";

            konten += "<tr>";
            konten += "<td colspan='3' align='center'><input type='submit' name='tombol' value='Cetak' style='width: 100px'></td>";
            konten += "</tr>";

            konten += "</table>";
            konten += "</form>";

            new MainForm().tampilkan(konten, request, response);
        } else {
            response.sendRedirect(".");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet LaporanGajiController";
    }
}