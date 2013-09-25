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
public class CekTranAktivitasMengajarDosen {
    
    private Function fn = new Function();

    public String cekKolom(int baris, String sKolom, Object value) {
        String message = "";
        if (sKolom.equalsIgnoreCase("Kode_mengajar_dosen") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("NIDN_NUK") && (value == null || value.toString().equalsIgnoreCase(""))) {
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
        if (sKolom.equalsIgnoreCase("Jml_tatap_muka_realisasi")) {
            int ijmlTatapMukaRealisasi = Integer.parseInt(value.toString());
            if (ijmlTatapMukaRealisasi<0 || ijmlTatapMukaRealisasi>17) {
                message = "Baris ke : " + baris + " Kolom <i>'" + sKolom + "'</i> Diisi dengan nilai antara 1 hingga 14!";
                return message;
            }
        }
        if (sKolom.equalsIgnoreCase("Jml_tatap_muka_rencana")) {
            int ijmlTatapMukaRencana = Integer.parseInt(value.toString());
            if (ijmlTatapMukaRencana<0 || ijmlTatapMukaRencana>17) {
                message = "Baris ke : " + baris + " Kolom <i>'" + sKolom + "'</i> Diisi dengan nilai antara 1 hingga 14!";
                return message;
            }
        }
        if (sKolom.equalsIgnoreCase("Kode_mata_kuliah") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Kode_kelas") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Jml_tatap_muka_rencana") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Jml_tatap_muka_realisasi") && (value == null || value.toString().equalsIgnoreCase(""))) {
            message = "Baris ke : " + baris + " Kolom " + sKolom + " Wajib diisi!";
            return message;
        }
        if (sKolom.equalsIgnoreCase("Jenis_evaluasi")){
            if(!value.toString().equalsIgnoreCase("A") && !value.toString().equalsIgnoreCase("B") &&
                    !value.toString().equalsIgnoreCase("C") && !value.toString().equalsIgnoreCase("D") &&
                    !value.toString().equalsIgnoreCase("E") && !value.toString().equalsIgnoreCase("F") &&
                    !value.toString().equalsIgnoreCase("G")){
                message = "Baris ke : " + baris + " Kolom <i>'" + sKolom + "'</i> Harus diisi dengan huruf A, B, C, D, E, F, atau G saja";
                return message;
            }            
        }
        if (sKolom.equalsIgnoreCase("Cara_evaluasi")){
            if(!value.toString().equalsIgnoreCase("1") && !value.toString().equalsIgnoreCase("2") &&
                    !value.toString().equalsIgnoreCase("3") && !value.toString().equalsIgnoreCase("4")  &&
                    !value.toString().equalsIgnoreCase("5")){
                message = "Baris ke : " + baris + " Kolom <i>'" + sKolom + "'</i> Harus diisi dengan angka 1, 2, 3, 4, atau 5 saja";
                return message;
            }            
        }
//        Nomor_SK_BN TEXT
//	Tanggal_SK_BN
        return message;
    }
}
