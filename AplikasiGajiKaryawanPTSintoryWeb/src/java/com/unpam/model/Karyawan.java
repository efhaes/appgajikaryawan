package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.unpam.view.PesanDialog;

public class Karyawan {
    private String ktp, nama, password;
    private int ruang;
    private String pesan;
    private Object[][] list;

    private final Koneksi koneksi = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();

    public String getKtp() { return ktp; }
    public void setKtp(String ktp) { this.ktp = ktp; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public int getRuang() { return ruang; }
    public void setRuang(int ruang) { this.ruang = ruang; }

    public String getPesan() { return pesan; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Object[][] getList() { return list; }
    public void setList(Object[][] list) { this.list = list; }

    public boolean simpan() {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            int jumlahSimpan = 0;
            String SQLStatemen = "";
            PreparedStatement preparedStatement;

            try {
                SQLStatemen = "insert into tbkaryawan(ktp, nama, ruang, password) "
                            + "values (?,?,?,?)";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, ktp);
                preparedStatement.setString(2, nama);
                preparedStatement.setInt(3, ruang);
                preparedStatement.setString(4, password);

                jumlahSimpan = preparedStatement.executeUpdate();

                if (jumlahSimpan < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menyimpan data karyawan";
                }

                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbkaryawan\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n"
                  + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
     
    public boolean baca(String ktp) {
    boolean adaKesalahan = false;
    Connection connection;
    this.ktp = ktp;

    if ((connection = koneksi.getConnection()) != null) {
        String SQLStatemen = "";
        PreparedStatement preparedStatement;
        ResultSet rset;

        try {
            SQLStatemen = "select ktp, nama, ruang, password from tbkaryawan where ktp = ?";
            preparedStatement = connection.prepareStatement(SQLStatemen);
            preparedStatement.setString(1, ktp);
            rset = preparedStatement.executeQuery();

            if (rset.next()) {
                this.ktp = rset.getString("ktp");
                this.nama = rset.getString("nama");
                this.ruang = rset.getInt("ruang");
                this.password = rset.getString("password");
            } else {
                adaKesalahan = true;
                pesan = "KTP " + ktp + " tidak ada";
            }

            rset.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            adaKesalahan = true;
            pesan = "Tidak dapat membaca tabel tbkaryawan\n" + ex;
        }
    } else {
        adaKesalahan = true;
        pesan = "Tidak dapat melakukan koneksi ke server\n"
              + koneksi.getPesanKesalahan();
    }
    return !adaKesalahan;
}

public boolean hapus(String ktp) {
    boolean adaKesalahan = false;
    Connection connection;

    if ((connection = koneksi.getConnection()) != null) {
        String SQLStatemen = "";
        PreparedStatement preparedStatement;

        try {
            SQLStatemen = "delete from tbkaryawan where ktp = ?";
            preparedStatement = connection.prepareStatement(SQLStatemen);
            preparedStatement.setString(1, ktp);

            int jumlahHapus = preparedStatement.executeUpdate();
            if (jumlahHapus < 1) {
                adaKesalahan = true;
                pesan = "KTP " + ktp + " tidak ditemukan";
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            adaKesalahan = true;
            pesan = "Tidak dapat menghapus data karyawan\n" + ex;
        }
    } else {
        adaKesalahan = true;
        pesan = "Tidak dapat melakukan koneksi ke server\n"
              + koneksi.getPesanKesalahan();
    }
    return !adaKesalahan;
}
 
    public boolean bacaData(int mulai, int jumlah) {
        boolean adaKesalahan = false;
        Connection connection;
        list = new Object[0][0];

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatemen;
            PreparedStatement preparedStatement;
            ResultSet rset;

            try {
                SQLStatemen = "select ktp, nama from tbkaryawan"
                            + " limit " + mulai + "," + jumlah;
                preparedStatement = connection.prepareStatement(SQLStatemen);
                rset = preparedStatement.executeQuery();

                ArrayList<Object[]> data = new ArrayList<>();
                while (rset.next()) {
                    data.add(new Object[]{rset.getString("ktp"), rset.getString("nama")});
                }
                list = data.toArray(new Object[0][0]);

                rset.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membaca tabel tbkaryawan\n" + ex;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n"
                  + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
}