/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import javax.swing.JOptionPane;
import model.Enkripsi;
import model.Karyawan;
import view.FormLihatKaryawan;
import view.FormUtama;

/**
 *
 * @author anandaanugrah
 */
public class KaryawanController {
    private final Karyawan karyawan = new Karyawan();
    private FormLihatKaryawan formLihatKaryawan;
    private final Enkripsi enkripsi = new Enkripsi();
    private boolean hashed = false;
    public void setHashed(boolean hashed ){
        this.hashed = hashed;
    }
    
    public void simpan(javax.swing.JTextField ktpTextField,
                       javax.swing.JTextField namaTextField,
                       javax.swing.JComboBox ruangComboBox,
                       javax.swing.JPasswordField passwordField) {
        if (!ktpTextField.getText().equals("")) {
            karyawan.setKtp(ktpTextField.getText());
            karyawan.setNama(namaTextField.getText());
            karyawan.setRuang(Integer.parseInt(ruangComboBox.getSelectedItem().toString()));
            
            try {
                String password = new String(passwordField.getPassword());
                if (!hashed){
                    password = enkripsi.hashMD5(password);
                    
            } 
            karyawan.setPassword(password);
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                    "Gagal hash password: " + ex.getMessage(), 
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (karyawan.simpan()) {
                FormUtama.formKaryawan.setKtp("");
                FormUtama.formKaryawan.setNama("");
                FormUtama.formKaryawan.setRuang(1);
                FormUtama.formKaryawan.setPassword("");
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
            } else {
                if (karyawan.getPesan().length() > 0) {
                    JOptionPane.showMessageDialog(null, 
                        karyawan.getPesan(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, 
                "KTP tidak boleh kosong", "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }
    
        public void cari(javax.swing.JTextField ktpTextField) {
        if (!ktpTextField.getText().equals("")) {
            if (karyawan.baca(ktpTextField.getText())) {
                FormUtama.formKaryawan.setNama(karyawan.getNama());
                FormUtama.formKaryawan.setRuang(karyawan.getRuang());
                FormUtama.formKaryawan.setPassword(karyawan.getPassword());
            } else {
                FormUtama.formKaryawan.setNama("");
                FormUtama.formKaryawan.setRuang(1);
                FormUtama.formKaryawan.setPassword("");
                javax.swing.JOptionPane.showMessageDialog(null, 
                    karyawan.getPesan(), "Kesalahan", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, 
                "KTP tidak boleh kosong", "Kesalahan", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    public void hapus(javax.swing.JTextField ktpTextField) {
        String ktp = ktpTextField.getText().trim();
        if (ktp.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Masukkan KTP yang ingin dihapus!");
            return;
        }

        int konfirmasi = javax.swing.JOptionPane.showConfirmDialog(null, 
            "Yakin hapus data dengan KTP " + ktp + "?", "Konfirmasi", 
            javax.swing.JOptionPane.YES_NO_OPTION);

        if (konfirmasi == javax.swing.JOptionPane.YES_OPTION) {
            if (karyawan.hapus(ktp)) {
                javax.swing.JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
                FormUtama.formKaryawan.setKtp("");
                FormUtama.formKaryawan.setNama("");
                FormUtama.formKaryawan.setPassword("");
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, karyawan.getPesan());
            }
        }
    }
    public void tampilkanFormLihatKaryawan() {
        formLihatKaryawan = new FormLihatKaryawan(null, true);
        if (karyawan.bacaData()) {
            formLihatKaryawan.tampilkanData(karyawan.getList());
            formLihatKaryawan.setVisible(true);
            
            if (!formLihatKaryawan.getKtpDipilih().equals("")) {
                if (karyawan.baca(formLihatKaryawan.getKtpDipilih())) {
                    FormUtama.formKaryawan.setKtp(karyawan.getKtp());
                    FormUtama.formKaryawan.setNama(karyawan.getNama());
                    FormUtama.formKaryawan.setRuang(karyawan.getRuang());
                    FormUtama.formKaryawan.setPassword(karyawan.getPassword());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, 
                karyawan.getPesan(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }
}