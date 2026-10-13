/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unpam.model;

/**
 *
 * @author farha
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.unpam.view.PesanDialog;
import java.sql.PreparedStatement;

public class Pekerjaan {
    private String kodePekerjaan, namaPekerjaan;
    private int jumlahTugas;
    private String pesan;
    private Object[][] list;

    private final Koneksi koneksi = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();

    public String getKodePekerjaan() { return kodePekerjaan; }
    public void setKodePekerjaan(String kodePekerjaan) { this.kodePekerjaan = kodePekerjaan; }

    public String getNamaPekerjaan() { return namaPekerjaan; }
    public void setNamaPekerjaan(String namaPekerjaan) { this.namaPekerjaan = namaPekerjaan; }

    public int getJumlahTugas() { return jumlahTugas; }
    public void setJumlahTugas(int jumlahTugas) { this.jumlahTugas = jumlahTugas; }

    public String getPesan() { return pesan; }

    public Object[][] getList() { return list; }
    public void setList(Object[][] list) { this.list = list; }

    public boolean simpan() {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            int jumlahSimpan = 0;
            String SQLStatemen;
            Statement sta;

            try {
                SQLStatemen = "insert into tbpekerjaan values ('"
                            + kodePekerjaan + "','" + namaPekerjaan + "','"
                            + jumlahTugas + "')";
                sta = connection.createStatement();
                jumlahSimpan = sta.executeUpdate(SQLStatemen);

                if (jumlahSimpan < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menyimpan data pekerjaan";
                }

                sta.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbpekerjaan\n" + ex;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n"
                  + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
	
    public boolean baca(String kodePekerjaan) {
    boolean adaKesalahan = false;
    Connection connection;
    this.kodePekerjaan = kodePekerjaan;

    if ((connection = koneksi.getConnection()) != null) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                "select kodepekerjaan, namapekerjaan, jumlahtugas from tbpekerjaan where kodepekerjaan = ?");
            ps.setString(1, kodePekerjaan);
            ResultSet rset = ps.executeQuery();

            if (rset.next()) {
                this.kodePekerjaan = rset.getString("kodepekerjaan");
                this.namaPekerjaan = rset.getString("namapekerjaan");
                this.jumlahTugas = rset.getInt("jumlahtugas");
            } else {
                adaKesalahan = true;
                pesan = "Kode pekerjaan " + kodePekerjaan + " tidak ada";
            }
            rset.close();
            ps.close();
            connection.close();
        } catch (SQLException ex) {
            adaKesalahan = true;
            pesan = "Tidak dapat membaca tabel tbpekerjaan\n" + ex;
        }
    } else {
        adaKesalahan = true;
        pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
    }
    return !adaKesalahan;
}

public boolean hapus(String kodePekerjaan) {
    boolean adaKesalahan = false;
    Connection connection;

    if ((connection = koneksi.getConnection()) != null) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                "delete from tbpekerjaan where kodepekerjaan = ?");
            ps.setString(1, kodePekerjaan);
            if (ps.executeUpdate() < 1) {
                adaKesalahan = true;
                pesan = "Kode pekerjaan tidak ditemukan";
            }
            ps.close();
            connection.close();
        } catch (SQLException ex) {
            adaKesalahan = true;
            pesan = "Tidak dapat menghapus data\n" + ex;
        }
    } else {
        adaKesalahan = true;
        pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
    }
    return !adaKesalahan;
}

    public boolean bacaData(int mulai, int jumlah) {
        boolean adaKesalahan = false;
        Connection connection;
        list = new Object[0][0];

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatemen;
            Statement sta;
            ResultSet rset;

            try {
                SQLStatemen = "select kodepekerjaan, namapekerjaan from tbpekerjaan"
                            + " limit " + mulai + "," + jumlah;
                sta = connection.createStatement();
                rset = sta.executeQuery(SQLStatemen);

                ArrayList<Object[]> data = new ArrayList<>();
                while (rset.next()) {
                    data.add(new Object[]{
                        rset.getString("kodepekerjaan"),
                        rset.getString("namapekerjaan")});
                }
                list = data.toArray(new Object[0][0]);

                rset.close();
                sta.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membaca tabel tbpekerjaan\n" + ex;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n"
                  + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
}
