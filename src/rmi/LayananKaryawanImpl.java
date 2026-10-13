/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rmi;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import model.Karyawan;

/**
 *
 * @author farha
 */
public class LayananKaryawanImpl extends UnicastRemoteObject implements LayananKaryawan {
    public LayananKaryawanImpl() throws RemoteException {
        super();
    }
    @Override
    public String sapa(String nama) throws RemoteException {
        System.out.println("Client memanggil sapa(" + nama + ")");
        return "Halo, " + nama + "!";
    }
    @Override
    public String[] cariKaryawan(String ktp) throws RemoteException {
        System.out.println("Client mencari KTP " + ktp);
        Karyawan karyawan = new Karyawan();
        if (karyawan.baca(ktp)) {
            return new String[]{
                karyawan.getNama(),
                Integer.toString(karyawan.getRuang())
            };
        }
        return null;
    }
    
}
