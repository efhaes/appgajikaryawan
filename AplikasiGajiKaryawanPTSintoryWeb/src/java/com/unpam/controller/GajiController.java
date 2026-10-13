/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.controller;

import com.unpam.model.Gaji;
import com.unpam.model.Karyawan;
import com.unpam.model.Pekerjaan;
import com.unpam.view.MainForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "GajiController", urlPatterns = {"/GajiController"})
public class GajiController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(true);
        Karyawan karyawan = new Karyawan();
        Pekerjaan pekerjaan = new Pekerjaan();
        Gaji gaji = new Gaji();
        String userName = "";

        String tombol = request.getParameter("tombol");
        String tombolKaryawan = request.getParameter("tombolKaryawan");
        String ktp = request.getParameter("ktp");
        String namaKaryawan = request.getParameter("namaKaryawan");
        String ruang = request.getParameter("ruang");
        String mulaiParameter = request.getParameter("mulai");
        String jumlahParameter = request.getParameter("jumlah");
        String ktpDipilih = request.getParameter("ktpDipilih");
        String tombolPekerjaan = request.getParameter("tombolPekerjaan");
        String kodePekerjaan = request.getParameter("kodePekerjaan");
        String namaPekerjaan = request.getParameter("namaPekerjaan");
        String jumlahTugas = request.getParameter("jumlahTugas");
        String kodePekerjaanDipilih = request.getParameter("kodePekerjaanDipilih");
        String gajibersih = request.getParameter("gajibersih");
        String gajikotor = request.getParameter("gajikotor");
        String tunjangan = request.getParameter("tunjangan");

        if (tombol == null) tombol = "";
        if (tombolKaryawan == null) tombolKaryawan = "";
        if (ktp == null) ktp = "";
        if (namaKaryawan == null) namaKaryawan = "";
        if (ruang == null) ruang = "";
        if (ktpDipilih == null) ktpDipilih = "";
        if (tombolPekerjaan == null) tombolPekerjaan = "";
        if (kodePekerjaan == null) kodePekerjaan = "";
        if (namaPekerjaan == null) namaPekerjaan = "";
        if (jumlahTugas == null) jumlahTugas = "";
        if (kodePekerjaanDipilih == null) kodePekerjaanDipilih = "";
        if (gajibersih == null) gajibersih = "";
        if (gajikotor == null) gajikotor = "";
        if (tunjangan == null) tunjangan = "";

        int mulai = 0, jumlah = 10;

        try {
            mulai = Integer.parseInt(mulaiParameter);
        } catch (NumberFormatException ex) {}

        try {
            jumlah = Integer.parseInt(jumlahParameter);
        } catch (NumberFormatException ex) {}

        String keterangan = "<br>";

        try {
            userName = session.getAttribute("userName").toString();
        } catch (Exception ex) {}

        if (!((userName == null) || userName.equals(""))) {

            // ================= PROSES KARYAWAN =================
            if (tombolKaryawan.equals("Cari")) {
                if (!ktp.equals("")) {
                    if (karyawan.baca(ktp)) {
                        ktp = karyawan.getKtp();
                        namaKaryawan = karyawan.getNama();
                        ruang = Integer.toString(karyawan.getRuang());
                        keterangan = "<br>";
                    } else {
                        namaKaryawan = "";
                        ruang = "1";
                        keterangan = "KTP " + ktp + " tidak ada";
                    }
                } else {
                    keterangan = "KTP harus diisi";
                }
            } else if (tombolKaryawan.equals("Pilih")) {
                ktp = ktpDipilih;
                namaKaryawan = "";
                ruang = "1";
                if (!ktpDipilih.equals("")) {
                    if (karyawan.baca(ktpDipilih)) {
                        ktp = karyawan.getKtp();
                        namaKaryawan = karyawan.getNama();
                        ruang = Integer.toString(karyawan.getRuang());
                        keterangan = "<br>";
                    } else {
                        keterangan = "KTP " + ktp + " tidak ada";
                    }
                } else {
                    keterangan = "Tidak ada yang dipilih";
                }
            }

            String kontenLihat = "";
            if (tombolKaryawan.equals("Lihat") || tombolKaryawan.equals("Sebelumnya")
                    || tombolKaryawan.equals("Berikutnya") || tombolKaryawan.equals("Tampilkan")) {
                kontenLihat = "<tr>";
                kontenLihat += "<td colspan='2' align='center'>";
                kontenLihat += "<table>";

                if (tombolKaryawan.equals("Sebelumnya")) {
                    mulai -= jumlah;
                    if (mulai < 0) mulai = 0;
                }

                if (tombolKaryawan.equals("Berikutnya")) {
                    mulai += jumlah;
                }

                Object[][] listKaryawan = null;
                if (karyawan.bacaData(mulai, jumlah)) {
                    listKaryawan = karyawan.getList();
                } else {
                    keterangan = karyawan.getPesan();
                }

                if (listKaryawan != null) {
                    for (int i = 0; i < listKaryawan.length; i++) {
                        kontenLihat += "<tr>";
                        kontenLihat += "<td>";
                        if (i == 0) {
                            kontenLihat += "<input type='radio' checked name='ktpDipilih' value='" + listKaryawan[i][0].toString() + "'>";
                        } else {
                            kontenLihat += "<input type='radio' name='ktpDipilih' value='" + listKaryawan[i][0].toString() + "'>";
                        }
                        kontenLihat += "</td>";
                        kontenLihat += "<td>";
                        kontenLihat += listKaryawan[i][0].toString();
                        kontenLihat += "</td>";
                        kontenLihat += "<td>";
                        kontenLihat += listKaryawan[i][1].toString();
                        kontenLihat += "</td>";
                        kontenLihat += "</tr>";
                    }
                }

                kontenLihat += "</table>";
                kontenLihat += "</td>";
                kontenLihat += "</tr>";

                kontenLihat += "<tr>";
                kontenLihat += "<td colspan='2' align='center'>";
                kontenLihat += "<table>";
                kontenLihat += "<tr>";
                kontenLihat += "<td align='center'><input type='submit' name='tombolKaryawan' value='Sebelumnya' style='width: 100px'></td>";
                kontenLihat += "<td align='center'><input type='submit' name='tombolKaryawan' value='Pilih' style='width: 60px'></td>";
                kontenLihat += "<td align='center'><input type='submit' name='tombolKaryawan' value='Berikutnya' style='width: 100px'></td>";
                kontenLihat += "</tr>";
                kontenLihat += "<tr>";
                kontenLihat += "<td align='center'>Mulai <input type='text' name='mulai' value=" + mulai + " style='width: 40px'></td>";
                kontenLihat += "<td>Jumlah";
                kontenLihat += "<select name='jumlah'>";
                for (int i = 1; i <= 10; i++) {
                    if (jumlah == (i * 10)) {
                        kontenLihat += "<option selected value=" + i * 10 + ">" + i * 10 + "</option>";
                    } else {
                        kontenLihat += "<option value=" + i * 10 + ">" + i * 10 + "</option>";
                    }
                }
                kontenLihat += "</select>";
                kontenLihat += "</td>";
                kontenLihat += "<td align='center'><input type='submit' name='tombolKaryawan' value='Tampilkan' style='width: 90px'></td>";
                kontenLihat += "</tr>";
                kontenLihat += "</table>";
                kontenLihat += "</td>";
                kontenLihat += "</tr>";
                kontenLihat += "<tr>";
                kontenLihat += "<td colspan='2' align='center'><br></td>";
                kontenLihat += "</tr>";
            }

            // ================= PROSES PEKERJAAN =================
            if (tombolPekerjaan.equals("Cari")) {
                if (!kodePekerjaan.equals("")) {
                    if (pekerjaan.baca(kodePekerjaan)) {
                        kodePekerjaan = pekerjaan.getKodePekerjaan();
                        namaPekerjaan = pekerjaan.getNamaPekerjaan();
                        jumlahTugas = Integer.toString(pekerjaan.getJumlahTugas());
                        keterangan = "<br>";
                    } else {
                        keterangan = "Kode pekerjaan tersebut tidak ada";
                    }
                } else {
                    keterangan = "Kode pekerjaan masih kosong";
                }
            } else if (tombolPekerjaan.equals("Pilih")) {
                kodePekerjaan = kodePekerjaanDipilih;
                namaPekerjaan = "";
                jumlahTugas = "2";
                if (!kodePekerjaanDipilih.equals("")) {
                    if (pekerjaan.baca(kodePekerjaanDipilih)) {
                        kodePekerjaan = pekerjaan.getKodePekerjaan();
                        namaPekerjaan = pekerjaan.getNamaPekerjaan();
                        jumlahTugas = Integer.toString(pekerjaan.getJumlahTugas());
                        keterangan = "<br>";
                    } else {
                        keterangan = "Kode pekerjaan tersebut tidak ada";
                    }
                } else {
                    keterangan = "Tidak ada yang dipilih";
                }
            }

            if (tombolPekerjaan.equals("Lihat") || tombolPekerjaan.equals("Sebelumnya")
                    || tombolPekerjaan.equals("Berikutnya") || tombolPekerjaan.equals("Tampilkan")) {
                kontenLihat += "<tr>";
                kontenLihat += "<td colspan='2' align='center'>";
                kontenLihat += "<table>";

                if (tombolPekerjaan.equals("Sebelumnya")) {
                    mulai -= jumlah;
                    if (mulai < 0) mulai = 0;
                }

                if (tombolPekerjaan.equals("Berikutnya")) {
                    mulai += jumlah;
                }

                Object[][] listPekerjaan = null;
                if (pekerjaan.bacaData(mulai, jumlah)) {
                    listPekerjaan = pekerjaan.getList();
                } else {
                    keterangan = pekerjaan.getPesan();
                }

                if (listPekerjaan != null) {
                    for (int i = 0; i < listPekerjaan.length; i++) {
                        kontenLihat += "<tr>";
                        kontenLihat += "<td>";
                        if (i == 0) {
                            kontenLihat += "<input type='radio' checked name='kodePekerjaanDipilih' value='" + listPekerjaan[i][0].toString() + "'>";
                        } else {
                            kontenLihat += "<input type='radio' name='kodePekerjaanDipilih' value='" + listPekerjaan[i][0].toString() + "'>";
                        }
                        kontenLihat += "</td>";
                        kontenLihat += "<td>";
                        kontenLihat += listPekerjaan[i][0].toString();
                        kontenLihat += "</td>";
                        kontenLihat += "<td>";
                        kontenLihat += listPekerjaan[i][1].toString();
                        kontenLihat += "</td>";
                        kontenLihat += "</tr>";
                    }
                }

                kontenLihat += "</table>";
                kontenLihat += "</td>";
                kontenLihat += "</tr>";

                kontenLihat += "<tr>";
                kontenLihat += "<td colspan='2' align='center'>";
                kontenLihat += "<table>";
                kontenLihat += "<tr>";
                kontenLihat += "<td align='center'><input type='submit' name='tombolPekerjaan' value='Sebelumnya' style='width: 100px'></td>";
                kontenLihat += "<td align='center'><input type='submit' name='tombolPekerjaan' value='Pilih' style='width: 60px'></td>";
                kontenLihat += "<td align='center'><input type='submit' name='tombolPekerjaan' value='Berikutnya' style='width: 100px'></td>";
                kontenLihat += "</tr>";
                kontenLihat += "<tr>";
                kontenLihat += "<td align='center'>Mulai <input type='text' name='mulai' value=" + mulai + " style='width: 40px'></td>";
                kontenLihat += "<td>Jumlah";
                kontenLihat += "<select name='jumlah'>";

                for (int i = 1; i <= 10; i++) {
                    if (jumlah == (i * 10)) {
                        kontenLihat += "<option selected value=" + i * 10 + ">" + i * 10 + "</option>";
                    } else {
                        kontenLihat += "<option value=" + i * 10 + ">" + i * 10 + "</option>";
                    }
                }

                kontenLihat += "</select>";
                kontenLihat += "</td>";
                kontenLihat += "<td align='center'><input type='submit' name='tombolPekerjaan' value='Tampilkan' style='width: 90px'></td>";
                kontenLihat += "</tr>";
                kontenLihat += "</table>";
                kontenLihat += "</td>";
                kontenLihat += "</tr>";
                kontenLihat += "<tr>";
                kontenLihat += "<td colspan='2' align='center'><br></td>";
                kontenLihat += "</tr>";
            }

            // ================= SIMPAN & HAPUS GAJI =================
            if (!tombol.equals("")) {
                if (tombol.equals("Simpan")) {
                    if (!ktp.equals("") && !kodePekerjaan.equals("")) {
                        gaji.setKtp(ktp);
                        gaji.setListGaji(new Object[][]{{kodePekerjaan, gajibersih, gajikotor, tunjangan}});
                        if (gaji.simpan()) {
                            ktp = "";
                            namaKaryawan = "";
                            ruang = "";
                            kodePekerjaan = "";
                            namaPekerjaan = "";
                            jumlahTugas = "";
                            gajibersih = "";
                            gajikotor = "";
                            tunjangan = "";
                            keterangan = "Sudah disimpan";
                        } else {
                            keterangan = "Gagal menyimpan:\n" + gaji.getPesan();
                        }
                    } else {
                        keterangan = "KTP dan kode pekerjaan tidak boleh kosong";
                    }
                } else if (tombol.equals("Hapus")) {
                    if (!ktp.equals("") && !kodePekerjaan.equals("")) {
                        if (gaji.hapus(ktp, kodePekerjaan)) {
                            ktp = "";
                            namaKaryawan = "";
                            ruang = "";
                            kodePekerjaan = "";
                            namaPekerjaan = "";
                            jumlahTugas = "";
                            gajibersih = "";
                            gajikotor = "";
                            tunjangan = "";
                            keterangan = "Sudah dihapus";
                        } else {
                            keterangan = "Gagal menghapus:\n" + gaji.getPesan();
                        }
                    } else {
                        keterangan = "KTP dan kode pekerjaan tidak boleh kosong";
                    }
                }
            }

            // ================= TAMPILAN FORM =================
            String konten = "<h2>Input Gaji Karyawan</h2>";
            konten += "<form action='GajiController' method='post'>";
            konten += "<table>";

            konten += "<tr>";
            konten += "<td align='right'>KTP</td>";
            konten += "<td align='left'><input type='text' value='" + ktp + "' name='ktp' maxlength='15' style='width: 120px'>";
            konten += "<input type='submit' name='tombolKaryawan' value='Cari'><input type='submit' name='tombolKaryawan' value='Lihat'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>Nama</td>";
            konten += "<td align='left'><input type='text' readonly value='" + namaKaryawan + "' name='namaKaryawan' style='width: 220px'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>Ruang</td>";
            konten += "<td align='left'><input type='text' readonly value='" + ruang + "' name='ruang' style='width: 20px'></td>";
            konten += "</tr>";

            if (!tombolKaryawan.equals("")) {
                if (!keterangan.equals("<br>")) {
                    konten += "<tr>";
                    konten += "<td colspan='2'><b>" + keterangan.replaceAll("\n", "<br>").replaceAll(";", ",") + "</b></td>";
                    konten += "</tr>";
                }
                konten += kontenLihat;
            }

            konten += "<tr>";
            konten += "<td align='right'>Kode Pekerjaan</td>";
            konten += "<td align='left'><input type='text' value='" + kodePekerjaan + "' name='kodePekerjaan' maxlength='15' style='width: 120px'>";
            konten += "<input type='submit' name='tombolPekerjaan' value='Cari'><input type='submit' name='tombolPekerjaan' value='Lihat'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>Nama Pekerjaan</td>";
            konten += "<td align='left'><input type='text' readonly value='" + namaPekerjaan + "' name='namaPekerjaan' style='width: 220px'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>Jumlah Tugas</td>";
            konten += "<td align='left'><input type='text' readonly value='" + jumlahTugas + "' name='jumlahTugas' style='width: 20px'></td>";
            konten += "</tr>";

            if (!tombolPekerjaan.equals("")) {
                if (!keterangan.equals("<br>")) {
                    konten += "<tr>";
                    konten += "<td colspan='2'><b>" + keterangan.replaceAll("\n", "<br>").replaceAll(";", ",") + "</b></td>";
                    konten += "</tr>";
                }
                konten += kontenLihat;
            }

            konten += "<tr>";
            konten += "<td align='right'>Gaji GajiBersih</td>";
            konten += "<td align='left'><input type='text' value='" + gajibersih + "' name='gajibersih' style='width: 50px'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>Gaji GajiKotor</td>";
            konten += "<td align='left'><input type='text' value='" + gajikotor + "' name='gajikotor' style='width: 50px'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>Gaji Tunjangan</td>";
            konten += "<td align='left'><input type='text' value='" + tunjangan + "' name='tunjangan' style='width: 50px'></td>";
            konten += "</tr>";

            konten += "<tr>";
            konten += "<td colspan='2' align='center'>";
            konten += "<table>";
            konten += "<tr>";
            konten += "<td align='center'><input type='submit' name='tombol' value='Simpan' style='width: 100px'></td>";
            konten += "<td align='center'><input type='submit' name='tombol' value='Hapus' style='width: 100px'></td>";
            konten += "</tr>";
            konten += "</table>";
            konten += "</td>";
            konten += "</tr>";

            if (!tombol.equals("") && !keterangan.equals("<br>")) {
                konten += "<tr>";
                konten += "<td colspan='2'><b>" + keterangan.replaceAll("\n", "<br>").replaceAll(";", ",") + "</b></td>";
                konten += "</tr>";
            }

            konten += "</table>";
            konten += "</form>";
            konten += "<br>";

            new MainForm().tampilkan(konten, request, response);
        } else {
            response.sendRedirect(".");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet GajiController";
    }// </editor-fold>

}