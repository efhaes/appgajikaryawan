/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author farha
 */
public class Karyawan {
    private String ktp;
    private String nama;
    private String password;
    
    private static final String URL = "jdbc:mysql://localhost:3306/db_aplikasigajikaryawan";    private static final String database = "jdbc:mysql://localhost/dbaplikasigajikaryawan";
    private static final String USER = "root";
    private static final String PASS = "";
    
    public boolean baca (String ktp ) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sql=" select ktp,nama,password from karyawan where ktp = ?";
        try (Connection con = DriverManager.getConnection(URL,USER,PASS);
           PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, ktp);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next ()){
                    this.ktp = rs.getString("ktp");
                    this.nama = rs.getString("nama");
                    this.password = rs.getString("password");
                    return true;
                }       
            }
        }
        return false;
    }
    
    public String getKtp(){ return ktp;}
    public String getNama() {return nama;}
    public String getPassword(){return password;}
    
}