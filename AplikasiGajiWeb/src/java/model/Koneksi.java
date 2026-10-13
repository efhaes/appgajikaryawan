/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author farha
 */
public class Koneksi {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String database = "jdbc:mysql://localhost/dbaplikasigajikaryawan";
    private static final String user = "root";
    private static final String password = "";
    private Connection connection;
    private String pesanKesalahan;
    
    public String getPesanKesalahan() {
        return pesanKesalahan;
    }
    
    public Connection getConnection() {
        connection = null;
        pesanKesalahan = "";
        
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            pesanKesalahan = "JDBC Driver tidak ditemukan atau rusak\n" + ex;
            return null;
        }
        
        try {
            connection = DriverManager.getConnection(database, user, password);
        } catch (SQLException ex) {
            pesanKesalahan = "Tidak dapat melakukan koneksi ke server\n" + ex;
        }
        
        return connection;
    }
}
