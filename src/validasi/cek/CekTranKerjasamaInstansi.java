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
public class CekTranKerjasamaInstansi {
    
    private Function fn = new Function();

    public String cekKolom(int baris, String sKolom, Object value) {
        String message = "";
        if (sKolom.equalsIgnoreCase("Kode_kerjasama_instansi") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Nama_kerjasama") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Nama_kegiatan") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Mulai_kerjasama")) {
            message = fn.cekKolomTanggal(baris, sKolom, value);
            if (message.length() > 0) {
                return message;
            }
        }
        if (sKolom.equalsIgnoreCase("Akhir_kerjasama")) {
            message = fn.cekKolomTanggal(baris, sKolom, value);
            if (message.length() > 0) {
                return message;
            }
        }
        if (sKolom.equalsIgnoreCase("Kode_program_studi") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Kode_perguruan_tinggi") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        
//        Nomor_SK_BN TEXT
//	Tanggal_SK_BN
        return message;
    }
}
