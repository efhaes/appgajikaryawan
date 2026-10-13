/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rmi;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/**
 *
 * @author farha
 */
public class ServerMain {
  private static Registry registry;

    public static void main(String[] args) {
        try {
            registry = LocateRegistry.createRegistry(1099);
            registry.rebind("layananKaryawan", new LayananKaryawanImpl());
            System.out.println("Server Telah Berjalan");
        } catch (Exception ex) {
            System.out.println("Server gagal berjalan: " + ex.getMessage());
            ex.printStackTrace();
        }
    }  
}
