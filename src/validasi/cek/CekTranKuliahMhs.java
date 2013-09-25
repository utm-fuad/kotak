/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validasi.cek;

import java.util.Calendar;
import validasi.StatusValidasi;
import validasi.utilisasi.Function;

/**
 *
 * @author faheem
 */
public class CekTranKuliahMhs {
    
    private Function fn = new Function();

    public String cekKolom(int baris, String sKolom, Object value) {
        String message = "";
        if (sKolom.equalsIgnoreCase("Kode_kuliah_mhs") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }        
        if (sKolom.equalsIgnoreCase("Tahun_pelaporan") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Tahun_pelaporan")) {
            int iTahunLapor = Integer.parseInt(value.toString());
            int iTahunIni = Calendar.getInstance().get(Calendar.YEAR);
            if (iTahunLapor > iTahunIni) {
                message = "Baris ke : " + baris + " Kolom <i>'" + sKolom + "'</i> Diisi dengan tahun sebelum tahun ini!";
                return message;
            }
        }
        if (sKolom.equalsIgnoreCase("Semester_pelaporan") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Semester_pelaporan")) {
            int iSemesterLapor = Integer.parseInt(value.toString());
            System.out.println("semester lapor: " + iSemesterLapor);
            if (iSemesterLapor!=1 && iSemesterLapor!=2) {
                message = "Baris ke : " + baris + " Kolom <i>'" + sKolom + "'</i> Diisi dengan 1 untuk ganjil dan 2 untuk genap!";
                return message;
            }
        }
        if (sKolom.equalsIgnoreCase("Kode_perguruan_tinggi") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Kode_program_studi") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Kode_jenjang_pendidikan") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Kode_jenjang_pendidikan")){
            if(!value.toString().equalsIgnoreCase("A") && !value.toString().equalsIgnoreCase("B") &&
                    !value.toString().equalsIgnoreCase("C") && !value.toString().equalsIgnoreCase("D") &&
                    !value.toString().equalsIgnoreCase("E") && !value.toString().equalsIgnoreCase("F") &&
                    !value.toString().equalsIgnoreCase("G") && !value.toString().equalsIgnoreCase("H") &&
                    !value.toString().equalsIgnoreCase("I") && !value.toString().equalsIgnoreCase("J") &&
                    !value.toString().equalsIgnoreCase("K") && !value.toString().equalsIgnoreCase("L") &&
                    !value.toString().equalsIgnoreCase("M") && !value.toString().equalsIgnoreCase("N") &&
                    !value.toString().equalsIgnoreCase("X")){
                message = "Baris ke : " + baris + " Kolom <i>'" + sKolom + "'</i> Harus diisi dengan huruf A, B, C, D, E, F, G, H, I, J, K, L, M, N, atau X saja";
                return message;
            }            
        }
        if (sKolom.equalsIgnoreCase("NIM") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Status_mahasiswa") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Status_mahasiswa ") && (value == null || value.toString().equalsIgnoreCase(""))) {
            if (!value.toString().equalsIgnoreCase("A") && !value.toString().equalsIgnoreCase("C") &&
                !value.toString().equalsIgnoreCase("D") && !value.toString().equalsIgnoreCase("G") &&
                !value.toString().equalsIgnoreCase("K") && !value.toString().equalsIgnoreCase("L") &&
                !value.toString().equalsIgnoreCase("N")) {
                message = "<li> Kolom <i>'" + sKolom + "'</i> diisi dengan Aktif (A), Cuti (C), Drop-out (D), doubledeGree (G), Keluar (K), Lulus (L), atau Nonaktif (N)! </li>";
                return message;
            }
        }
        if (sKolom.equalsIgnoreCase("IPS") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("IPS")) {
            int iIPS = Integer.parseInt(value.toString());
            if (iIPS<0 && iIPS>4) {
                message = "Baris ke : " + baris + " Kolom <i>'" + sKolom + "'</i> Diisi dengan nilai antara 1 hingga 4!";
                return message;
            }
        }
        if (sKolom.equalsIgnoreCase("SKS_semester") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
//        Nomor_SK_BN TEXT
//	Tanggal_SK_BN
        return message;
    }
}
