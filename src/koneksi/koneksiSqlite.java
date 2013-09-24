package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author jullev
 */
public class koneksiSqlite {

    public static void main(String[] args) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection conn =
                DriverManager.getConnection("jdbc:sqlite:PDPT.db");
        System.out.println("Execute sqlite");
        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists BFR_TMST_MAHASISWA;");
        stat.executeUpdate("CREATE TABLE TMST_BADAN_HUKUM (Kode_badan_hukum CHAR(7) NOT NULL ,Nama_badan_hukum CHAR(50 ) NOT NULL ,Tanggal_awal_berdiri DATE NOT NULL ,Alamat CHAR(100 ) NOT NULL ,Kode_kota NUMBER(20) NOT NULL ,Kode_provinsi NUMBER NOT NULL "
                + ",Kode_negara NUMBER NOT NULL ,kode_pos NUMBER(5) NOT NULL ,Telephone CHAR(20 ) NULL ,Faximili CHAR(20 ) NULL ,Email CHAR(40 ) NULL ,Website CHAR(40 ) NULL ,Nomor_akta_terakhir CHAR(30 ) NOT NULL "
                + ",Tanggal_akta_terakhir DATE NOT NULL ,Nomor_pengesahan_PN_LN CHAR(30 ) NOT NULL ,status_validasi NUMBER(1) NOT NULL ,ID_log_audit NUMBER NULL,Nomor_SK_awal_berdiri TEXT,Tanggal_SK_awal_berdiri TEXT,Nomor_SK_BN TEXT,Tanggal_SK_BN TEXT);");
        stat.executeUpdate("CREATE TABLE TMST_DOSEN(NIDN_NUP CHAR(10 ) NULL ,NIP_lama CHAR(9 ) NULL ,NIP_baru CHAR(30 ) NULL ,Golongan CHAR(5 ) NULL ,Pangkat CHAR(1 ) NULL,Jabatan_fungsional CHAR(50 ) NULL "
                + ",Jabatan_struktural CHAR(50 ) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_program_studi CHAR(5 ) NULL ,Kode_fakultas CHAR(2 ) NULL ,Kode_jurusan CHAR(5 ) NULL,"
                + "Nama_dosen CHAR(30 ) NULL ,Nomor_KTP CHAR(25 ) NULL ,Gelar_akademik_depan CHAR(30 ) NULL ,Gelar_akademik_belakang CHAR(30 ) NULL ,Jenis_kelamin CHAR(1 ) NULL,Tempat_lahir CHAR(20 ) NULL"
                + ",Tanggal_lahir DATE NULL ,Status_aktif CHAR(1 ) NULL ,Mulai_masuk_dosen DATE NULL ,Mulai_semester CHAR(5 ) NULL ,Kode_jenjang_pendidikan NUMBER NULL ,Ikatan_kerja CHAR(1 ) NULL "
                + ",Akta_dan_ijin_mengajar CHAR(1 ) NULL ,Alamat CHAR(100 ) NULL ,Kode_kota NUMBER NULL ,Kode_provinsi CHAR(3 ) NULL ,Kode_pos NUMBER(5) NULL ,Kode_negara CHAR(3 ) NULL"
                + ",No_sertifikasi_dosen NUMBER(11) NULL ,Tgl_keluar_sertifikasi_dosen DATE NULL ,NIRA CHAR(30 ) NULL ,Status_validasi NUMBER(1) NULL ,Telepon CHAR(20 ) NULL ,HP CHAR(20 ) NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TMST_FAKULTAS(Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_fakultas CHAR(2 ) NULL ,Singkatan CHAR(50 ) NULL ,Nama_fakultas CHAR(50 ) NULL ,Alamat CHAR(50 ) NULL,Kode_provinsi NUMBER NULL "
                + ",Kode_kota NUMBER NULL ,Kode_pos NUMBER(5) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,Status_validasi NUMBER NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TMST_FAS_PENUNJANG_AKADEMIK (Kode_fas_akademik NUMBER NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Jenis_sarana CHAR(50 ) NULL"
                + ",Status_sarana CHAR(50 ) NULL ,Luas_sarana NUMBER NULL ,Luas_yang_diperlukan NUMBER NULL ,Status_validasi NUMBER(1) NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TMST_JURUSAN(Kode_jurusan CHAR(5 ) NULL ,Nama_jurusan CHAR(50 ) NULL ,Kode_fakultas CHAR(2 ) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Program_studi_yg_diasuh CHAR(5 ) NULL"
                + ",Tgl_awal_berdiri DATE NULL ,No_SK_penyelenggara CHAR(40 ) NULL ,Tgl_SK_penyelenggara DATE NULL ,Status_validasi NUMBER(1) NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TMST_KERJASAMA_PT_LN(Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_perguruan_tinggi_asing CHAR(6 ) NULL ,Kode_kerjasama_PTLN NUMBER NULL ,Uraian_bentuk_kerjasama CHAR(100 ) NULL ,ID_log_audit NUMBER NULL );");
        stat.executeUpdate("CREATE TABLE TMST_LABORATORIUM(Kode_lab NUMBER NULL ,No_urut_lab CHAR(2 ) NULL ,Nama_lab CHAR(40 ) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_jenjang_pendidikan NUMBER NULL ,Kode_program_studi CHAR(5 ) NULL"
                + ",Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,Kepemilikan_lab CHAR(1 ) NULL ,Lokasi_lab CHAR(1 ) NULL ,Luas_lab NUMBER NULL ,Kapasitas_praktikum_satu_shift NUMBER NULL,Lab_penggunaan_mhs NUMBER NULL,Lab_penggunaan_jam NUMBER NULL"
                + ",Jumlah_PS_pengguna NUMBER NULL ,Jumlah_modul_praktikum_sendiri NUMBER NULL ,Jumlah_modul_praktikum_lain NUMBER NULL ,Fungsi_selain_praktikum CHAR(1 ) NULL ,Status_validasi NUMBER(1) NULL ,ID_log_audit NUMBER NULL );");
        stat.executeUpdate("CREATE TABLE TMST_MAHASISWA(Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_program_studi CHAR(5 ) NULL ,NIM CHAR(15 ) NULL ,Nama_mahasiswa CHAR(30 ) NULL,Kelas CHAR(1 ) NULL,Tempat_lahir CHAR(20 ) NULL ,Kode_jenjang_pendidikan NUMBER NULL"
                + ",Tanggal_lahir DATE NULL ,Jenis_kelamin CHAR(1 ) NULL ,Alamat CHAR(100 ) NULL ,Kode_kota NUMBER NULL ,Kode_provinsi NUMBER NULL ,Kode_pos NUMBER NULL,Kode_negara NUMBER NULL,Status_mahasiswa CHAR(1 ) NULL"
                + ",Tahun_masuk NUMBER(4) NULL ,Mulai_semester NUMBER(5) NULL ,Batas_studi CHAR(5 ) NULL ,Tanggal_masuk DATE NULL ,Tanggal_lulus DATE NULL ,IPK_akhir FLOAT(126) NULL ,Nomor_SK_yudisium CHAR(30 ) NULL,Tanggal_SK_yudisium DATE NULL,Nomor_seri_ijazah CHAR(40 ) NULL "
                + ",Kode_prov_asal_pendidikan CHAR(3 ) NULL ,Status_awal_mahasiswa CHAR(1 ) NULL ,SKS_diakui NUMBER(3) NULL ,Kode_perguruan_tinggi_asal CHAR(6 ) NULL ,Kode_program_studi_asal CHAR(5 ) NULL ,Kode_beasiswa NUMBER NULL,Kode_jenjang_pendidikan_sblm NUMBER NULL"
                + ",NIM_asal CHAR(15 ) NULL ,Kode_biaya_studi CHAR(1 ) NULL ,Kode_pekerjaan CHAR(1 ) NULL ,Nama_tempat_bekerja CHAR(40 ) NULL ,Kode_PT_bekerja CHAR(6 ) NULL ,Kode_PS_bekerja CHAR(5 ) NULL,NIDN_promotor CHAR(10 ) NULL,NIDN_kopromotor_1 CHAR(10 ) NULL "
                + ",IDN_kopromotor_2 CHAR(10 ) NULL ,NIDN_kopromotor_3 CHAR(10 ) NULL ,NIDN_kopromotor_4 CHAR(10 ) NULL ,Jalur_skripsi NUMBER(1) NULL ,Judul_skripsi CHAR(100 ) NULL,Bulan_awal_bimbingan NUMBER(6) NULL ,Bulan_akhir_bimbingan NUMBER(6) NULL "
                + ",NISN NUMBER(10) NULL ,Kode_kategori_penghasilan NUMBER NULL ,ID_log_audit NUMBER NULL,UUID_KOPROMOTOR4 INT,UUID_KOPROMOTOR3 INT,UUID_KOPROMOTOR2 INT,UUID_PROMOTOR INT);");
        stat.executeUpdate("CREATE TABLE TMST_MATA_KULIAH(Kode_mata_kuliah CHAR(10 ) NOT NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL,Kode_program_studi CHAR(5 ) NULL,Kode_jenjang_pendidikan NUMBER NULL,Nama_mata_kuliah CHAR(40 ) NULL"
                + ",Jenis_mata_kuliah CHAR(1 ) NULL ,Semester NUMBER(1) NULL ,Kelompok_mata_kuliah CHAR(1 ) NULL ,SKS_mata_kuliah NUMBER(1) NULL ,SKS_tatap_muka NUMBER(1) NULL ,SKS_praktikum NUMBER(1) NULL ,SKS_praktek_lapangan NUMBER(1) NULL ,SKS_simulasi NUMBER(1) NULL "
                + ",Metode_pelaksanaan_kuliah CHAR(50 ) NULL ,Status_mata_kuliah CHAR(50 ) NULL ,Kode_kurikulum CHAR(1 ) NULL ,Kode_kelas CHAR(2 ) NULL ,SAP CHAR(1 ) NULL ,Silabus CHAR(1 ) NULL ,Bahan_ajar CHAR(1 ) NULL,Acara_praktek NUMBER(1) NULL,Diktat CHAR(1 ) NULL"
                + ",NIDN CHAR(10 ) NULL ,TGL_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL,UUID INT);");
        stat.executeUpdate("CREATE TABLE TMST_PEGAWAI(Kode_pegawai NUMBER NULL ,NIP_lama CHAR(9 ) NULL ,NIP_baru CHAR(30 ) NULL ,Nama_pegawai CHAR(50 ) NULL ,Jenis_kelamin CHAR(1 ) NULL ,Tempat_lahir CHAR(50 ) NULL ,Tanggal_lahir DATE NULL ,Alamat CHAR(50 ) NULL "
                + ",Kode_kota NUMBER NULL ,Kode_provinsi NUMBER NULL ,Kode_pos NUMBER(5) NULL ,Kode_jabatan_fungsional NUMBER NULL ,Kode_direktorat_jendral NUMBER NULL ,Kode_direktorat NUMBER NULL ,Kode_bagian NUMBER NULL ,Kode_seksi NUMBER NULL ,Pangkat CHAR(1 ) NULL ,Golongan CHAR(5 ) NULL"
                + ",Telepon_rumah CHAR(20 ) NULL ,Email CHAR(20 ) NULL ,Handphone CHAR(20 ) NULL ,No_SK_pengangkatan CHAR(50 ) NULL ,Tanggal_masuk DATE NULL ,Tanggal_berhenti DATE NULL ,Status NUMBER NULL ,Kode_jenjang_pendidikan NUMBER NULL ,ID_log_audit NUMBER NULL,TEXT_TMT TEXT,Text_Tgl_Masuk TEXT,"
                + "Text_Tgl_Berhenti TEXT,Gelar_Depan TEXT,Gelar_Belakang TEXT);");
        stat.executeUpdate("CREATE TABLE TMST_PERGURUAN_TINGGI(KODE_PERGURUAN_TINGGI CHAR(6 ) NULL ,KODE_BADAN_HUKUM CHAR(7 ) NULL ,NAMA_PT CHAR(6 ) NULL ,Singkatan CHAR(50 ) NULL ,Jenis_PT CHAR(1 ) NULL ,Kategori_PT NUMBER(1) NULL ,Status_PT CHAR(1 ) NULL"
                + ",Tgl_awal_berdiri DATE NULL ,Alamat CHAR(100 ) NULL ,Kode_kota NUMBER NULL ,Kode_provinsi NUMBER NULL ,Kode_negara NUMBER NULL ,Kode_pos NUMBER NULL ,Telepone CHAR(20 ) NULL ,Fax CHAR(20 ) NULL ,Email CHAR(40 ) NULL,Website CHAR(40 ) NULL,No_akta_sk_pendirian CHAR(30 ) NULL"
                + ",Tanggal_akta DATE NULL ,Kode_akreditasi NUMBER NULL ,No_SK_BAN CHAR(50 ) NULL ,TGL_SK_BAN DATE NULL ,visi CLOB NULL ,misi CLOB NULL ,Tujuan CLOB NULL ,Sasaran CLOB NULL ,Kode_kopertis NUMBER NULL ,Kode_wilayah NUMBER NULL ,Seleksi_penerimaan CLOB NULL "
                + ",Pola_kepemimpinan CLOB NULL ,Sistem_pengelolaan CLOB NULL ,Sistem_penjaminan_mutu CLOB NULL ,Alasan_transfer_mahasiswa CLOB NULL ,Peran_d_pembelajaran CLOB NULL ,Peran_d_penyusunan_kurikulum CLOB NULL ,Peran_d_suasana_akademik CLOB NULL ,pemanfaatan_TIK CLOB NULL ,Penyebaran_informasi CLOB NULL,Rencana_pengembangan_SI CLOB NULL "
                + ",Evaluasi_lulusan CLOB NULL ,Mekanisme_evaluasi_lulusan CLOB NULL ,Kode_kementrian NUMBER NULL ,TGL_mulai_efektif DATE NULL ,Tgl_Akhir_Efektif DATE NULL ,Status_validasi NUMBER(1) NULL ,Id_log_audit NUMBER NULL"
                + ",File_Logo TEXT,Nama_Rektor TEXT,Nip_Rektor TEXT,Nama_Wakil_1 TEXT,Nip_Wakil_1 TEXT,Nama_Wakil_2 TEXT,Nip_Wakil_2 TEXT,Nama_Wakil_3 TEXT,Nip_Wakil_3 TEXT,"
                + "Nama_Wakil_4 TEXT,Nip_Wakil_4 TEXT,Nama_Sekertaris TEXT,Deskripsi_Singkat Text,User Text,Created_At Text,Uploaded_At TEXT);");
        stat.executeUpdate("CREATE TABLE TMST_PROGRAM_STUDI(Kode_program_studi CHAR(5 ) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_fakultas CHAR(2 ) NULL ,Kode_jurusan CHAR(5 ) NULL ,Nama_program_studi CHAR(50 ) NULL ,Kode_jenjang_pendidikan NUMBER NULL ,Alamat CHAR(100 ) NULL ,Kode_kota NUMBER NULL,Kode_provinsi NUMBER NULL"
                + ",Kode_negara NUMBER NULL ,kode_pos NUMBER(5) NULL ,Tanggal_berdiri DATE NULL ,Email CHAR(40 ) NULL ,Bidang_ilmu CHAR(1 ) NULL ,SKS_lulus NUMBER(3) NULL ,Status_program_studi CHAR(1 ) NULL,No_SK_DIKTI CHAR(50 ) NULL ,Tgl_SK_DIKTI DATE NULL ,Tgl_akhir_SK_DIKTI DATE NULL "
                + ",Mulai_semester CHAR(5 ) NULL ,NIDN_NUP CHAR(40 ) NULL ,NIK CHAR(20 ) NULL ,HP CHAR(20 ) NULL ,Telepon_kantor CHAR(20 ) NULL ,Fax CHAR(20 ) NULL ,Nama_operator CHAR(40 ) NULL,Frekuensi_kurikulum CHAR(1 ) NULL,Pelaksanaan_kurikulum CHAR(1 ) NULL,Kode_akreditasi NUMBER NULL,No_SK_BAN CHAR(50 ) NULL "
                + ",Tanggal_SK_BAN DATE NULL ,Tanggil_akhir_SK_BAN DATE NULL ,kapasitas_mahasiswa NUMBER NULL ,Visi CLOB NULL ,Misi CLOB NULL ,Tujuan CLOB NULL ,Sasaran CLOB NULL ,Upaya_penyebaran CLOB NULL ,Keberlanjutan CLOB NULL ,Himpunan_alumni CLOB NULL ,Tgl_mulai_efektif DATE NULL "
                + ",Tgl_akhir_efektif DATE NULL ,Status_validasi NUMBER(1) NULL ,ID_log_audit NUMBER NULL,UUID INT,Nama_Ketua TEXT"
                + ",Nama_Sekertaris TEXT,Rumpun_ilmu TEXT,Gelar_Lulusan TEXT,Deskripsi_Singkat TEXT,Kompetensi_Prodi TEXT,Capaian_Pembelajaran TEXT,Website TEXT,"
                + "User TEXT,Created_At TEXT,Updated_at TEXT);");
        stat.executeUpdate("CREATE TABLE TMST_PUSTAKA_PT(Kode_pustaka_pt NUMBER NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelapporan NUMBER(1) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL,Kode_program_studi CHAR(5 ) NULL,Kode_jenjang_pendidikan NUMBER NULL "
                + ",Jml_judul_pustaka NUMBER NULL ,Jml_judul_pustaka_digunakan NUMBER NULL ,Jenis_pustaka CHAR(50 ) NULL ,Nama_pustaka CHAR(50 ) NULL ,Tahun_terbit_pustaka NUMBER(4) NULL ,Fungsi_pustaka CHAR(50 ) NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TMST_SARANA_PT (Kode_sarana_pt NUMBER NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_program_studi CHAR(5 ) NULL ,Kode_jenjang_pendidikan NUMBER NULL ,Nama_sarana CHAR(50 ) NULL ,Fungsi_sarana CHAR(50 ) NULL ,Kepemilikan_sarana CHAR(2 ) NULL ,Jml_sarana NUMBER NULL "
                + ",Luas_sarana NUMBER NULL ,Flag_pengguna_sarana NUMBER(1) NULL ,Kondisi CHAR(1 ) NULL ,Penggunaan_sarana_mhs NUMBER NULL ,Penggunaan_jam_sarana NUMBER NULL ,Sarana_dosen NUMBER(1) NULL ,Kapasitas_ruang_dosen NUMBER(1) NULL ,Status_validasi NUMBER(1) NULL,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TMST_USERNAME(Kode_username NUMBER NULL ,Flag_status_user NUMBER(1) NULL ,NIDN CHAR(10 ) NULL ,NIP_lama CHAR(9 ) NULL ,NIP_baru CHAR(30 ) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_program_studi CHAR(5 ) NULL ,Kode_pegawai NUMBER NULL,Username CHAR(50 ) NULL "
                + ",Password CHAR(250 ) NULL ,Email CHAR(50 ) NULL ,Handphone CHAR(50 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL,UUID INT );");
        stat.executeUpdate("CREATE TABLE TRAN_AKTIVITAS_MENGAJAR_DOSEN(Kode_mengajar_dosen NUMBER NULL ,NIDN_NUK CHAR(10 ) NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_program_studi CHAR(5 ) NULL ,Kode_jenjang_studi NUMBER NULL ,Kode_mata_kuliah CHAR(10 ) NULL "
                + ",Kode_kelas CHAR(2 ) NULL ,Jml_tatap_muka_realisasi NUMBER(2) NULL ,Jenis_evaluasi CHAR(1 ) NULL ,Cara_evaluasi NUMBER(1) NULL ,ID_log_audit NUMBER NULL,UUID INT );");
        stat.executeUpdate("CREATE TABLE TRAN_ANGGOTA_PENELITI_AHLI(Kode_anggota_peneliti_ahli NUMBER NULL ,Kode_publikasi_dosen_tetap NUMBER NULL ,Kode_tenaga_ahli NUMBER NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_ANGGOTA_PENELITI_DUKUNG (Kode_anggota_peneliti_dukung NUMBER NULL ,Kode_publikasi_dosen_tetap NUMBER NULL ,Kode_tenaga_pendukung NUMBER NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_ANGGOTA_PENELITIAN_DOSEN(Kode_anggota_penelitian NUMBER NULL ,Kode_publikasi_dosen_tetap NUMBER NULL ,NIDN CHAR(10 ) NULL ,ID_log_audit NUMBER NULL,UUID INT);");
        stat.executeUpdate("CREATE TABLE TRAN_BOBOT_NILAI(Kode_bobot_nilai NUMBER NOT NULL ,Kode_perguruan_tinggi CHAR(6 ) NOT NULL ,kode_program_studi CHAR(5 ) NOT NULL ,Kode_jenjang_studi NUMBER NOT NULL ,Tahun_pelaporan NUMBER(4) NOT NULL,Semester_pelaporan NUMBER(1) NOT NULL "
                + ",Bobot_nilai_min FLOAT(126) NOT NULL ,Bobot_nilai_max FLOAT(126) NOT NULL ,Nilai CHAR(3 ) NOT NULL ,Tgl_mulai_efektif DATE NOT NULL ,Tgl_akhir_efektif DATE NOT NULL ,ID_log_audit NUMBER NOT NULL );");
        stat.executeUpdate("CREATE TABLE TRAN_DAYA_TAMPUNG(Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_jenjang_pendidikan NUMBER NULL,Kode_program_studi CHAR(5 ) NULL ,Target_mhs_baru NUMBER NULL ,Calon_ikut_seleksi NUMBER NULL "
                + ",Calon_lulus_seleksi NUMBER NULL ,Mendaftar_sebagai_mahasiswa NUMBER NULL ,Peserta_mengundurkan_diri NUMBER NULL,Tgl_awal_kuliah_ganjil DATE NULL,Tgl_akhir_kuliah_ganjil DATE NULL ,Jml_minggu_kuliah_ganjil NUMBER NULL "
                + ",Tgl_awal_kuliah_genap DATE NULL ,Tgl_akhir_kuliah_genap DATE NULL ,Jml_minggu_kuliah_genap NUMBER NULL ,Metode_kuliah CHAR(1 ) NULL ,Metode_kuliah_ekstensi CHAR(1 ) NULL ,Jml_SP_setahun NUMBER NULL ,Metode_SP CHAR(1 ) NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_FTE_DOSEN (Kode_FTE_dosen NUMBER NOT NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,NIDN_NUK CHAR(10 ) NULL ,Jenis_FTE NUMBER(1) NULL "
                + ",Jumlah_FTE NUMBER NULL ,ID_log_audit NUMBER NULL,UUID INT );");
        stat.executeUpdate("CREATE TABLE TRAN_GROUP_USER_PRIVILEDGE(Kode_group_user_priviledge NUMBER NULL ,Kode_role_user NUMBER NULL ,Kode_menu NUMBER NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_HKI(Kode_HKI NUMBER NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,NIDN_NUK CHAR(10 ) NULL ,Nama_HKI CHAR(50 ) NULL ,Nomor_registrasi_HKI CHAR(50 ) NULL "
                + ",Nomor_HKI CHAR(50 ) NULL ,Kategori_HKI NUMBER(1) NULL ,ID_log_audit NUMBER NULL,UUID INT);");
        stat.executeUpdate("CREATE TABLE TRAN_KERJASAMA_INSTANSI(Kode_kerjasama_instansi NUMBER NULL ,Nama_kerjasama CHAR(50 ) NULL ,Nama_kegiatan CHAR(50 ) NULL ,Mulai_kerjasama DATE NULL ,Akhir_kerjasama DATE NULL ,Kode_program_studi CHAR(5 ) NULL "
                + ",Kode_perguruan_tinggi CHAR(6 ) NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_KINERJA_DOSEN(Kode_kinerja_dosen NUMBER NULL ,NIDN_NUK CHAR(10 ) NULL ,NIP_lama CHAR(9 ) NULL ,NIP_baru CHAR(30 ) NULL ,Nomor_dosen CHAR(30 ) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,"
                + "Kode_program_studi CHAR(5 ) NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,NIDN_assesor_1 CHAR(10 ) NULL ,NIDN_assesor_2 CHAR(10 ) NULL ,Kategori_evaluasi NUMBER(1) NULL ,Jenis_kegiatan CHAR(50 ) NULL "
                + ",Bukti_penugasan CHAR(50 ) NULL ,SKS_beban_kerja NUMBER NULL ,Masa_pelaksanaan_tugas_awal DATE NULL ,Masa_pelaksanaan_tugas_akhir DATE NULL ,Bukti_dokumen_kinerja BLOB NULL ,Persentase_capaian_kinerja FLOAT(126) NULL ,SKS_capaian_kinerja NUMBER NULL "
                + ",Penilaian_asesor FLOAT(126) NULL ,Rekomendasi_asesor CHAR(50 ) NULL ,Status_validasi NUMBER NULL ,ID_log_audit NUMBER NULL );");
        stat.executeUpdate("CREATE TABLE TRAN_KULIAH_MHS(Kode_kuliah_mhs NUMBER NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_program_studi CHAR(5 ) NULL ,Kode_jenjang_pendidikan NUMBER NULL ,NIM CHAR(15 ) NOT NULL "
                + ",Status_mahasiswa CHAR(1 ) NULL ,IPS FLOAT(126) NULL ,SKS_semester NUMBER(1) NULL ,IPK FLOAT(126) NULL ,SKS_total NUMBER(3) NULL ,ID_Log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_MESSAGE(ID_Message NUMBER NOT NULL ,Pesan CLOB NULL ,TGL_BERLAKU DATE NULL ,TGL_AKHIR DATE NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_MESSAGE_ERROR (Kode_message_error NUMBER NOT NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_program_studi CHAR(5 ) NULL ,Nama_file CHAR(50 ) NULL "
                + ",Nama_table CHAR(50 ) NULL ,Catatan_error CLOB NULL ,Nomor_urut_data NUMBER NULL ,Nilai_data CLOB NULL ,Tanggal_error DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_MODULE_MENU (Kode_module_menu NUMBER NULL ,Kode_menu NUMBER NULL ,Kode_module NUMBER NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_NILAI_SEMESTER_MHS (Kode_nilai_mhs NUMBER NOT NULL ,Kode_kuliah_mhs NUMBER NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,Kode_perguruan_tinggi CHAR(6 ) NOT NULL ,Kode_program_studi CHAR(5 ) NOT NULL "
                + ",Kode_jenjang_pendidikan NUMBER NOT NULL ,NIM CHAR(15 ) NOT NULL ,Kode_mata_kuliah CHAR(10 ) NULL ,Bobot_nilai FLOAT(126) NULL ,Kode_kelas CHAR(2 ) NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_PINDAHAN_MHS_ASING(Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_jenjang_pendidikan NUMBER NULL ,Kode_program_studi CHAR(15 ) NULL ,NIM CHAR(15 ) NULL ,Kode_PT_asal CHAR(6 ) NULL ,Nama_pt_asal CHAR(50 ) NULL "
                + ",kode_PS_asal CHAR(5 ) NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_PRESTASI_MHS(Kode_prestasi_mhs NUMBER NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,NIM CHAR(10 ) NULL ,Kode_jenjang_pendidikan NUMBER NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,No_penelitian NUMBER NULL ,Jenis_penelitian CHAR(1 ) NULL "
                + ",Kode_pengarang CHAR(1 ) NULL ,Hasil_penelitian NUMBER(1) NULL ,Media_publikasi CHAR(1 ) NULL ,Penelitian_dilaksanakan_secara CHAR(1 ) NULL ,Jumlah_pembiayaan NUMBER NULL ,Jenis_pembiayaan CHAR(1 ) NULL ,Periode_penelitian CHAR(6 ) NULL "
                + ",Judul_penelitian CHAR(100 ) NULL ,Waktu_pelaksanaan NUMBER NULL ,ID_log_audit NUMBER NULL );");
        stat.executeUpdate("CREATE TABLE TRAN_PUBLIKASI_DOSEN_TETAP(Kode_publikasi_dosen_tetap NUMBER NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,NIDN_NUP CHAR(10 ) NULL ,Kode_jenjang_pendidikan NUMBER NULL ,No_penelitian NUMBER NULL ,Jenis_penelitian CHAR(1 ) NULL ,Kode_pengarang CHAR(1 ) NULL ,Hasil_penelitian NUMBER(1) NULL ,Media_publikasi CHAR(1 ) NULL "
                + ",Penelitian_dilaksanakan_secara CHAR(1 ) NULL ,Jumlah_pembiayaan_thn_1 NUMBER NULL ,Jumlah_pembiayaan_thn_2 NUMBER NULL ,Jumlah_pembiayaan_thn_3 NUMBER NULL ,jenis_pembiayaan CHAR(1 ) NULL ,Periode_penelitian CHAR(6 ) NULL ,judul_penelitian CHAR(100 ) NULL ,Kata_kunci CHAR(100 ) NULL ,Abstrak CLOB NULL ,Waktu_pelaksanaan_penelitian NUMBER NULL ,Lokasi_penelitian CHAR(50 ) NULL "
                + ",Status_validasi NUMBER(1) NULL ,ID_log_audit NUMBER NULL,UUID INT);");
        stat.executeUpdate("CREATE TABLE TRAN_RIWAYAT_AKREDITASI_PRODI(Kode_riwayat_akreditasi_prodi NUMBER NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,Kode_program_studi CHAR(5 ) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_akreditasi CHAR(1 ) NULL ,No_SK_BAN CHAR(50 ) NULL "
                + ",tgl_SK_BAN DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_RIWAYAT_AKREDITASI_PT(Kode_riwayat_akreditasi_pt NUMBER NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,Kode_akreditasi CHAR(1 ) NULL ,No_SK_BAN CHAR(50 ) NULL "
                + ",Tgl_SK_BAN DATE NULL ,ID_log_audit NUMBER NULL );");
        stat.executeUpdate("CREATE TABLE TRAN_RIWAYAT_MHS_BEASISWA(Kode_riwayat_mhs_beasiswa NUMBER NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_program_studi CHAR(5 ) NULL ,Kode_jenjang_pendidikan NUMBER NULL ,NIM CHAR(15 ) NULL ,Kode_beasiswa NUMBER NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_RIWAYAT_PENDIDIKAN_DOSEN(Kode_riwayat_pend_dosen NUMBER NOT NULL ,Tahun_pelaporan NUMBER(4) NOT NULL ,Semester_pelaporan NUMBER(1) NOT NULL ,Kode_perguruan_tinggi CHAR(6 ) NOT NULL ,Kode_program_studi CHAR(5 ) NOT NULL ,Kode_jenjang_pendidikan NUMBER NOT NULL ,NIDN CHAR(10 ) NOT NULL ,Nomor_urut_dosen CHAR(1 ) NOT NULL ,Gelar_akademik CHAR(30 ) NOT NULL ,Kode_kelompok_bidang_ilmu NUMBER NOT NULL"
                + ",Tanggal_ijazah DATE NOT NULL ,SKS_lulus NUMBER(3) NOT NULL ,IPK_akhir NUMBER(1,2) NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_RIWAYAT_SK_PRODI(Kode_riwayat_sk_prodi NUMBER NULL ,Kode_program_studi CHAR(5 ) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,No_SK_berdiri CHAR(50 ) NULL "
                + ",Tgl_SK_berdiri DATE NULL ,Tgl_akhir_SK_DIKTI DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_RIWAYAT_SK_PT(Kode_riwayat_sk_pt NUMBER NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,No_SK_Berdiri CHAR(50 ) NULL ,Tgl_SK_berdiri DATE NULL,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_RIWAYAT_STATUS_DOSEN(Kode_riwayat_status_dosen NUMBER NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,NIDN_NUK CHAR(10 ) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_program_studi CHAR(5 ) NULL ,Kode_jenjang_pendidikan NUMBER NULL ,Nodos_PT CHAR(10 ) NULL "
                + ",Status_aktif CHAR(1 ) NULL ,ID_log_audit NUMBER NULL,UUID INT);");
        stat.executeUpdate("CREATE TABLE TRAN_RIWAYAT_STATUS_MAHASISWA(Kode_riwayat_status_mhs NUMBER NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_program_studi CHAR(5 ) NULL ,NIM CHAR(15 ) NULL ,Kode_jenjang_pendidikan NUMBER NULL ,Status_mahasiswa CHAR(1 ) NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_SHOUTBOX(ID_SHOUTBOX NUMBER NOT NULL ,Pesan CLOB NULL ,TGL_MULAI_AKTIF DATE NULL ,TGL_AKHIR_EFEKTIF DATE NULL ,KODE_PERGURUAN_TINGGI CHAR(1 ) NULL ,Kode_Kopertis NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_TENAGA_AHLI(Kode_tenaga_ahli NUMBER NULL ,Nama_tenaga_ahli CHAR(50 ) NULL ,Posisi NUMBER NULL ,Profesi CHAR(50 ) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_program_studi CHAR(5 ) NULL "
                + ",Kode_mata_kuliah CHAR(10 ) NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_TENAGA_PENDUKUNG(Kode_tenaga_pendukung NUMBER NULL ,NIP_baru CHAR(30 ) NULL ,NIP_Lama CHAR(9 ) NULL ,Nomor_tenaga_pendukung CHAR(50 ) NULL ,Nama_tenaga_pendukung CHAR(50 ) NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Kode_program_studi CHAR(5 ) NULL ,Kode_fakultas CHAR(2 ) NULL ,Nomor_KTP CHAR(25 ) NULL "
                + ",tempat_lahir CHAR(50 ) NULL ,Tanggal_lahir DATE NULL ,Jenis_kelamin CHAR(1 ) NULL ,Jenis_tenaga_pendukung CHAR(50 ) NULL ,Kode_enjang_studi NUMBER NULL ,Jabatan CHAR(50 ) NULL ,Jenis_SK CHAR(50 ) NULL ,Nomor_SK CHAR(50 ) NULL ,Tanggal_SK DATE NULL ,Tanggal_akhir_SK DATE NULL "
                + ",ID_log_audit NUMBER NULL );");
        stat.executeUpdate("CREATE TABLE TRAN_USER_PRIVILEDGE(Kode_user_priviledge NUMBER NULL ,Username CHAR(50 ) NULL ,Kode_group_menu CHAR(250 ) NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TRAN_VALIDASI_PT(Kode_validasi_total_pt NUMBER NOT NULL ,Kode_perguruan_tinggi CHAR(6 ) NULL ,Tahun_pelaporan NUMBER(4) NULL ,Semester_pelaporan NUMBER(1) NULL ,Flag_validasi NUMBER(1) NULL ,Jumlah_total_validasi_pt NUMBER NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_AGAMA(Kode_agama CHAR(1 ) NULL ,Nama_agama CHAR(50 ) NULL ,ID_log_audit NUMBER NULL); ");
        stat.executeUpdate("CREATE TABLE TREF_AKREDITASI(Kode_akreditasi NUMBER NULL ,Status_akreditasi CHAR(1 ) NULL ,Keterangan_akreditasi CHAR(50 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_BAGIAN(Kode_bagian NUMBER NULL ,Kode_direktorat NUMBER NULL ,Nama_bagian CHAR(150 ) NULL ,Singkatan CHAR(20 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_BEASISWA(Kode_beasiswa NUMBER NULL ,Jenis_beasiswa CHAR(50 ) NULL ,Provider NUMBER(1) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_DIREKTORAT(Kode_direktorat NUMBER NULL ,Kode_direktorat_jenderal NUMBER NULL ,Nama_direktorat CHAR(50 ) NULL ,Singkatan CHAR(50 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_DIREKTORAT_JENDERAL(Kode_direktorat_jenderal NUMBER NULL ,Nama_direktorat_jenderal CHAR(150 ) NULL ,Singkatan CHAR(20 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_FREK_KURIKULUM(Kode_frek_kurikulum CHAR(1 ) NULL ,Nama_frek_kurikulum CHAR(50 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_FUNGSI_LAB(Kode_fungsi_lab CHAR(1 ) NULL ,Fungsi_lab CHAR(100 ) NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_IKATAN_KERJA_DOSEN(Kode_ikatan_kerja NUMBER NULL ,Ikatan_kerja CHAR(1 ) NULL ,Deskripsi CHAR(50 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_JABATAN_AKADEMIK(Kode_jabatan_akademik NUMBER NULL ,Deskripsi CHAR(50 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_JENIS_PERGURUAN_TINGGI(Kode_jenis_pt CHAR(1 ) NULL ,Nama_jenis_pt CHAR(50 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_JENJANG_PENDIDIKAN(Kode_jenjang_pendidikan NUMBER NULL ,Deskripsi CHAR(50 ) NULL ,Waktu_pelaksanaan_jenjang_pend CHAR(40 ) NULL ,Group_jejang_studi CHAR(50 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_KATEGORI_PENGHASILAN(Kode_kategori_penghasilan NUMBER NULL ,Range_penghasilan_min NUMBER NULL ,Range_penghasilan_max NUMBER NULL ,Sumber_penghasilan CHAR(50 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_KELOMPOK_BIDANG_ILMU(Kode_kelompok_bidang_ilmu CHAR(1 ) NULL ,Nama_bidang_ilmu CHAR(50 ) NULL ,Status_validasi NUMBER(1) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_KEMENTRIAN(Kode_kementrian NUMBER NOT NULL ,Nama_kementrian CHAR(100 ) NULL ,Singkatan CHAR(50 ) NULL ,Tanggal_mulai_efektif DATE NULL ,Tanggal_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_KOPERTIS(Kode_kopertis NUMBER NULL ,Deskripsi CHAR(100 ) NULL ,Group_kopertis CHAR(50 ) NULL ,Alamat CHAR(100 ) NULL ,Kode_kota NUMBER NULL ,Kode_provinsi NUMBER NULL ,Kode_pos NUMBER(5) NULL "
                + ",Telepon CHAR(20 ) NULL ,Website CHAR(40 ) NULL ,Fax CHAR(20 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL );");
        stat.executeUpdate("CREATE TABLE TREF_KOTA(Kode_kota NUMBER NULL ,Nama_kabupaten CHAR(50 ) NULL ,Kode_provinsi NUMBER NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_LOG_AUDIT(ID_log_audit NUMBER NOT NULL ,Username CHAR(50 ) NULL ,Aksi CHAR(10 ) NULL ,Nama_tabel CHAR(50 ) NULL ,Kueri CHAR(50 ) NULL ,Waktu_modifikasi DATE NULL);");
        stat.executeUpdate("CREATE TABLE TREF_MENU(Kode_menu NUMBER NOT NULL ,Nama_menu CHAR(50 ) NULL ,URL CHAR(250 ) NULL ,Level_menu NUMBER NULL ,Root_menu NUMBER NULL ,Deskripsi CHAR(250 ) NULL ,Urutan_menu NUMBER NULL ,Flag_menu NUMBER NULL ,Menu_icon CHAR(250 ) NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_MODULE(Kode_module NUMBER NULL ,Nama_module CHAR(50 ) NULL ,Deskripsi CHAR(250 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_NEGARA(Kode_negara NUMBER NULL ,Nama_negara CHAR(50 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL,Kode_Negara2 Int,Kode_Negara3 Int,Latitude TEXT,Longitude TEXT,Zoom Int);");
        stat.executeUpdate("CREATE TABLE TREF_PENDUDUK(Kode_penduduk NUMBER NULL ,Jumlah_penduduk NUMBER NULL ,Tahun NUMBER(4) NULL ,Kategori_penduduk CHAR(50 ) NULL ,Nilai_kategori_penduduk CHAR(50 ) NULL ,Kode_provinsi NUMBER NULL ,Jenis_kelamin CHAR(1 ) NULL ,Referensi CHAR(50 ) NULL ,Tgl_mulai_efektif DATE NULL "
                + ",Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_PROVINSI(Kode_provinsi NUMBER NULL ,Nama_provinsi CHAR(50 ) NULL ,Kode_negara NUMBER NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,Kode_pulau NUMBER NULL ,ID_log_audit NUMBER NULL );");
        stat.executeUpdate("CREATE TABLE TREF_PULAU(Kode_pulau NUMBER NULL ,Nama_pulau CHAR(50 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_ROLE_USER(Kode_role_user NUMBER NULL ,Nama_role CHAR(50 ) NULL ,Deskripsi CHAR(50 ) NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_SEKSI(Kode_seksi NUMBER NULL ,Kode_bagian NUMBER NULL ,Nama_seksi CHAR(150 ) NULL ,Singakatan CHAR(20 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_STATUS_AKTIVITAS_DOSEN(Kode_status_aktivitas_dosen CHAR(1 ) NULL ,Nama_status CHAR(50 ) NULL ,Keterangan CHAR(50 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_STATUS_MAHASISWA(Kode_status_mhs CHAR(1 ) NULL ,Nama_status CHAR(50 ) NULL ,Keterangan CHAR(50 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TREF_WILAYAH(Kode_wilayah NUMBER NULL ,Nama_wilayah CHAR(50 ) NULL ,Deskripsi CHAR(50 ) NULL ,Tgl_mulai_efektif DATE NULL ,Tgl_akhir_efektif DATE NULL ,ID_log_audit NUMBER NULL);");
        stat.executeUpdate("CREATE TABLE TMST_FOTODIRI(NIDN TEXT,File_Foto TEXT,Dipakai TEXT,UUID INT);");
        stat.executeUpdate("CREATE TABLE TMST_FOTO_PERGURUAN_TINGGI(ID INT,Kode_Perguruan_Tinggi TEXT,File_Foto TEXT,Caption TEXT,Tanggal_Upload TEXT,DIPAKAI TEXT);");
        stat.executeUpdate("CREATE TABLE TMST_SAS(ID INT,Kode_Perguruan_Tinggi TEXT,Kode_Program_Studi TEXT,Persentase TEXT);");
        stat.executeUpdate("CREATE TABLE TMST_SMS(Kode_Perguruan_Tinggi Text,Kode_SMS TEXT,Nama_SMS TEXT,Jenis_SMS INT,Nomor_SK TEXT,Tanggal_SK TEXT,Nama_Pimpinan TEXT,Alamat TEXT,Website TEXT);");
        stat.executeUpdate("CREATE TABLE TMST_SMS_PRODI(Kode_SMS Text,Kode_Perguruan_Tinggi TEXT,Kode_Program_Studi TEXT);");
        stat.executeUpdate("CREATE TABLE TRAN_LAPORAN_PDPT(Kode_Perguruan_Tinggi TEXT,Kode_Program_Studi TEXT,Semester TEXT);");
        stat.executeUpdate("CREATE TABLE TREF_JENIS_SMS(Kode_Jenis_SMS TEXT,Nama_Jenis_SMS TEXT);");
//PreparedStatement prep = conn.prepareStatement( 
//"insert into school values (?, ?);"); 
//prep.setString(1, "UTD"); 
//prep.setString(2, "texas"); 
//prep.addBatch();  
//prep.setString(1, "USC"); 
//prep.setString(2, "california"); 
//prep.addBatch(); 
//prep.setString(1, "MIT"); 
//prep.setString(2, "massachusetts"); 
//prep.addBatch(); 
        conn.setAutoCommit(false);
//prep.executeBatch(); 
        conn.setAutoCommit(true);

        conn.close();
    }
}
