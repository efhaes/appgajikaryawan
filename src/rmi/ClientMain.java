/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rmi;
import java.rmi.Naming;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ClientMain extends JFrame {

    private static final String ALAMAT_SERVER = "rmi://localhost:1099/layananKaryawan";

    private final JTextField ktpTextField = new JTextField(15);
    private final JButton cariButton = new JButton("Cari");
    private final JTextArea hasilTextArea = new JTextArea(6, 30);

    public ClientMain() {
        setTitle("Client Karyawan (RMI)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel atas = new JPanel();
        atas.add(new JLabel("KTP"));
        atas.add(ktpTextField);
        atas.add(cariButton);

        hasilTextArea.setEditable(false);

        add(atas, java.awt.BorderLayout.NORTH);
        add(new JScrollPane(hasilTextArea), java.awt.BorderLayout.CENTER);

        cariButton.addActionListener(evt -> cari());
        ktpTextField.addActionListener(evt -> cari());

        pack();
        setLocationRelativeTo(null);
    }

    private void cari() {
        String ktp = ktpTextField.getText().trim();
        if (ktp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "KTP tidak boleh kosong",
                "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            LayananKaryawan layanan = (LayananKaryawan) Naming.lookup(ALAMAT_SERVER);
            String[] data = layanan.cariKaryawan(ktp);
            if (data == null) {
                hasilTextArea.setText("Karyawan dengan KTP " + ktp + " tidak ditemukan");
            } else {
                hasilTextArea.setText(layanan.sapa(data[0]) + "\n"
                    + "Nama  : " + data[0] + "\n"
                    + "Ruang : " + data[1]);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Tidak bisa terhubung ke server. Pastikan ServerMain sudah dijalankan.\n"
                + ex.getMessage(),
                "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientMain().setVisible(true));
    }
}
/**
 *
 * @author farha
 */

