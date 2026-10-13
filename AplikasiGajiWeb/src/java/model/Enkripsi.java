/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.security.MessageDigest;
/**
 *
 * @author farha
 */
public class Enkripsi {
    public String hashMD5( String teks) throws Exception {
        MessageDigest md = MessageDigest.getInstance ("MD5");
        byte [] hasil = md.digest( teks.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder ();
        for (byte b: hasil)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
