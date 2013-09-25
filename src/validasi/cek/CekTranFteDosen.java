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
public class CekTranFteDosen {
    
    private Function fn = new Function();

    public String cekKolom(int baris, String sKolom, Object value) {
        String message = "";
        if (sKolom.equalsIgnoreCase("Kode_FTE_dosen") && (value == null || value.toString().equalsIgnoreCase(""))) {
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
        if (sKolom.equalsIgnoreCase("NIDN_NUK") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Jenis_FTE")){
            if(value ==null ||value.toString().length()==0){
                message = " Kolom '" + sKolom + "' Wajib diisi! ";
                return message;
            }
            else if(!(value.toString().equalsIgnoreCase("1") || value.toString().equalsIgnoreCase("2") || value.toString().equalsIgnoreCase("3")
                    )){
                message = " Kolom '" + sKolom + "' Harus diisi dengan angka 1 (aktivitas mengajar), 2 (penelitian/publikasi) atau 3 (jabatan struktural) saja";
                return message;
            }
            
        }
        if (sKolom.equalsIgnoreCase("Jumlah_FTE") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
//        Nomor_SK_BN TEXT
//	Tanggal_SK_BN
        return message;
    }
}
