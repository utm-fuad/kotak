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
public class CekTranPindahanMhsAsing {
    
    private Function fn = new Function();

    public String cekKolom(int baris, String sKolom, Object value) {
        String message = "";
        if (sKolom.equalsIgnoreCase("Kode_perguruan_tinggi") && (value == null || value.toString().equalsIgnoreCase(""))) {
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
        if (sKolom.equalsIgnoreCase("Kode_program_studi") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("NIM") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }        
        if (sKolom.equalsIgnoreCase("Kode_PT_asal") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }        
        
//        Nomor_SK_BN TEXT
//	Tanggal_SK_BN
        return message;
    }
}
