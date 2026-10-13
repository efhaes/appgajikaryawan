/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author farha
 */

public interface LayananKaryawan extends Remote{
    String sapa(String nama) throws RemoteException;
    String [] cariKaryawan(String ktp) throws RemoteException;
}
