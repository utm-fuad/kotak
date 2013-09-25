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
public class CekTranBobotNilai {
    
    private Function fn = new Function();

    public String cekKolom(int baris, String sKolom, Object value) {
        String message = "";
        if (sKolom.equalsIgnoreCase("Kode_bobot_nilai") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
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
        if (sKolom.equalsIgnoreCase("Bobot_nilai_min") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Bobot_nilai_max") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Nilai") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Tgl_mulai_efektif")) {
            message = fn.cekKolomTanggal(baris, sKolom, value);
            if (message.length() > 0) {
                return message;
            }
        }
        if (sKolom.equalsIgnoreCase("Tgl_akhir_efektif")) {
            message = fn.cekKolomTanggal(baris, sKolom, value);
            if (message.length() > 0) {
                return message;
            }
        }
//        Nomor_SK_BN TEXT
//	Tanggal_SK_BN
        return message;
    }
}
