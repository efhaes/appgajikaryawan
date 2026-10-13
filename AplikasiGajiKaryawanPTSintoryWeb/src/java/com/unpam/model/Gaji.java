package com.unpam.model;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

public class Gaji {

    private String ktp = "";
    private Object[][] listGaji = null;
    private String pesan = "";
    private byte[] pdfasbytes = null;

    // ===== Getter & Setter =====
    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public Object[][] getListGaji() {
        return listGaji;
    }

    public void setListGaji(Object[][] listGaji) {
        this.listGaji = listGaji;
    }

    public String getPesan() {
        return pesan;
    }

    public byte[] getPdfasbytes() {
        return pdfasbytes;
    }

    // ===== Simpan =====
    public boolean simpan() {
        boolean adaKesalahan = false;
        Connection connection = null;
        PreparedStatement ps = null;

        // SESUAIKAN: cara dapat koneksi, ikuti yang dipakai di Karyawan.java
        connection = new Koneksi().getConnection();

        if (connection == null) {
            pesan = "Tidak bisa terkoneksi ke database";
            return false;
        }

        try {
            connection.setAutoCommit(false);

            String sql = "INSERT INTO tbgaji (ktp, kodepekerjaan, gajibersih, gajikotor, tunjangan) "
                       + "VALUES (?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(sql);

            for (int i = 0; i < listGaji.length; i++) {
                ps.setString(1, ktp);
                ps.setString(2, listGaji[i][0].toString());
                ps.setDouble(3, Double.parseDouble(listGaji[i][1].toString()));
                ps.setDouble(4, Double.parseDouble(listGaji[i][2].toString()));
                ps.setDouble(5, Double.parseDouble(listGaji[i][3].toString()));
                ps.executeUpdate();
            }

            connection.commit();
        } catch (SQLException | NumberFormatException ex) {
            adaKesalahan = true;
            pesan = ex.getMessage();
            try {
                connection.rollback();
            } catch (SQLException ex2) {
                pesan += "\n" + ex2.getMessage();
            }
        } finally {
            try {
                if (ps != null) ps.close();
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException ex) {
            }
        }

        return !adaKesalahan;
    }

    // ===== Hapus =====
    public boolean hapus(String ktp, String kodePekerjaan) {
        boolean adaKesalahan = false;
        Connection connection = null;
        PreparedStatement ps = null;

        // SESUAIKAN: cara dapat koneksi
        connection = new Koneksi().getConnection();

        if (connection == null) {
            pesan = "Tidak bisa terkoneksi ke database";
            return false;
        }

        try {
            String sql = "DELETE FROM tbgaji WHERE ktp = ? AND kodepekerjaan = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, ktp);
            ps.setString(2, kodePekerjaan);
            ps.executeUpdate();
        } catch (SQLException ex) {
            adaKesalahan = true;
            pesan = ex.getMessage();
        } finally {
            try {
                if (ps != null) ps.close();
                connection.close();
            } catch (SQLException ex) {
            }
        }

        return !adaKesalahan;
    }

    // ===== Cetak Laporan (JasperReports 6.x) =====
    // opsi: "KTP", "ruang", atau "Semua"
    // formatType: pdf, xlsx, xls, docx, odt, rtf
    // fileLaporan: path lengkap ke GajiReport.jrxml
    @SuppressWarnings({"rawtypes", "unchecked"})
    public boolean cetakLaporan(String opsi, String ktp, int ruang,
            String formatType, String fileLaporan) {
        boolean adaKesalahan = false;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        // SESUAIKAN: cara dapat koneksi
        connection = new Koneksi().getConnection();

        if (connection == null) {
            pesan = "Tidak bisa terkoneksi ke database";
            return false;
        }

        try {
            // Alias harus sama dengan <field name="..."> di GajiReport.jrxml
            String sql = "SELECT k.ruang AS ruang, k.ktp AS ktp, k.nama AS nama, "
                       + "p.kodepekerjaan AS kodepekerjaan, p.namapekerjaan AS namapekerjaan, "
                       + "p.jumlahtugas AS jumlahtugas, "
                       + "g.gajibersih AS gajibersih, g.gajikotor AS gajikotor, g.tunjangan AS tunjangan "
                       + "FROM tbgaji g "
                       + "JOIN tbkaryawan k ON g.ktp = k.ktp "
                       + "JOIN tbpekerjaan p ON g.kodepekerjaan = p.kodepekerjaan ";

            if (opsi.equalsIgnoreCase("KTP")) {
                sql += "WHERE g.ktp = ? ";
            } else if (opsi.equalsIgnoreCase("ruang") && ruang > 0) {
                sql += "WHERE k.ruang = ? ";
            }
            sql += "ORDER BY k.ruang, k.ktp, p.kodepekerjaan";

            ps = connection.prepareStatement(sql);
            if (opsi.equalsIgnoreCase("KTP")) {
                ps.setString(1, ktp);
            } else if (opsi.equalsIgnoreCase("ruang") && ruang > 0) {
                ps.setInt(1, ruang);
            }
            rs = ps.executeQuery();

            JasperDesign jasperDesign = JRXmlLoader.load(fileLaporan);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> parameters = new HashMap<String, Object>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, parameters, new JRResultSetDataSource(rs));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Exporter exporter;

            if (formatType.equals("xlsx")) {
                exporter = new JRXlsxExporter();
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
            } else if (formatType.equals("xls")) {
                exporter = new JRXlsExporter();
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
            } else if (formatType.equals("docx")) {
                exporter = new JRDocxExporter();
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
            } else if (formatType.equals("odt")) {
                exporter = new JROdtExporter();
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
            } else if (formatType.equals("rtf")) {
                exporter = new JRRtfExporter();
                exporter.setExporterOutput(new SimpleWriterExporterOutput(baos));
            } else {
                exporter = new JRPdfExporter();
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
            }

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.exportReport();

            pdfasbytes = baos.toByteArray();
        } catch (SQLException | JRException ex) {
            adaKesalahan = true;
            pesan = ex.getMessage();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                connection.close();
            } catch (SQLException ex) {
            }
        }

        return !adaKesalahan;
    }
}