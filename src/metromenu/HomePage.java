/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.swing.Timer;

/**
 *
 * @author asus
 */
public class HomePage extends javax.swing.JPanel {
    private Bingkai bingkai;
    private Timer timer, timerTicker;
    private koneksi.KoneksiOracleThinClient koneksiDB;
    private MenuHome menuHome;
    private MenuDosen1 menuDosen1;
    private MenuDosen2 menuDosen2;    
    private MenuDosen3 menuDosen3;
    private MenuMhs1 menuMhs1;
    private MenuMhs2 menuMhs2;
    private MenuLembaga1 menuLembaga1;
    private MenuLembaga2 menuLembaga2;
    private MenuLembaga3 menuLembaga3;
    private MenuLembaga4 menuLembaga4;
    private MasterDosen masterDosen;
    private AktivitasMengajarDosen aktivitasMengajarDosen;
    private AnggotaPenelitiAhli anggotaPenelitiAhli;
    private AnggotaPenelitiPendukung anggotaPenelitiPendukung;
    private AnggotaPenelitianDosen anggotaPenelitianDosen;
    private FTEDosen fTEDosen;
    private HKI hki;
    private KinerjaDosen kinerjaDosen;
    private PublikasiDosenTetap publikasiDosenTetap;
    private RiwayatPendidikanDosen riwayatPendidikanDosen;
    private RiwayatStatusDosen riwayatStatusDosen;
    private TenagaAhli tenagaAhli; 
    private TenagaPendukung tenagaPendukung;
    private MasterMahasiswa masterMahasiswa;
    private KuliahMahasiswa kuliahMahasiswa;
    private NilaiSemesterMahasiswa nilaiSemesterMahasiswa;
    private PindahanMahasiswaAsing pindahanMahasiswaAsing;
    private PrestasiMahasiswa prestasiMahasiswa;
    private RiwayatBeasiswa riwayatBeasiswa;
    private RiwayatStatusMahasiswa riwayatStatusMahasiswa;
    private BobotNilai bobotNilai;
    private DayaTampung dayaTampung;
    private KerjasamaInstansi kerjasamaInstansi;
    private RiwayatAkreditasiProdi riwayatAkreditasiProdi;
    private RiwayatAkreditasiPT riwayatAkreditasiPT;
    private RiwayatKepemilikanPT riwayatKepemilikanPT;
    private RiwayatSKProdi riwayatSKProdi;
    private RiwayatSKPT riwayatSKPT;
    private ValidasiPT validasiPT;
    private MasterProdi masterProdi;
    private MasterJurusan masterJurusan;
    private MasterFakultas masterFakultas;
    private MasterPT masterPT;
    private MasterPustaka masterPustaka;
    private MasterSaranaPT masterSaranaPT;
    private MasterLaboratorium masterLaboratorium;
    private MasterFasilitasUmumPenunjangAkademik masterFasilitasUmumPenunjangAkademik;
    private MasterKerjasamaPTLN masterKerjasamaPTLN;
    private MasterMatakuliah masterMatakuliah;
    private MasterBadanHukum masterBadanHukum;
    private MasterPegawai masterPegawai;
    private String namaPanelAktif;
    private MenuSistem menuSistem; //ahad,30des2012@krembangan1:12
    private MenuUtiliti menuUtiliti;
    private MenuReferensi menuReferensi;
    private MenuReferensi2 menuReferensi2;
    private ReferensiWilayah referensiWilayah;
    private ReferensiStatusAktivitasDosen referensiStatusAktivitasDosen;
    private ReferensiStatusMahasiswa referensiStatusMahasiswa;
    private ReferensiSeksi referensiSeksi;
    private ReferensiPeranPengguna referensiPeranPengguna;
    private ReferensiPulau referensiPulau;
    private ReferensiProvinsi2 referensiProvinsi;
    private ReferensiPenduduk referensiPenduduk;
    private ReferensiNegara referensiNegara;
    private ReferensiModule referensiModule;
    private MenuReferensi3 menuReferensi3;
    private MenuReferensi4 menuReferensi4;
    private MenuReferensi5 menuReferensi5;
    private ReferensiMenu referensiMenu;
    private ReferensiKota referensiKota;
    private ReferensiKopertis referensiKopertis;
    private ReferensiKementrian referensiKementrian;
    private ReferensiKelompokBidangIlmu referensiKelompokBidangIlmu;
    private ReferensiKategoriPenghasilan referensiKategoriPenghasilan;
    private ReferensiJenjangPendidikan referensiJenjangPendidikan;
    private ReferensiJenisPerguruanTinggi referensiJenisPerguruanTinggi;
    private ReferensiJabatanAkademik referensiJabatanAkademik;
    private ReferensiIkatanKerjaDosen referensiIkatanKerjaDosen;    
    private ReferensiFungsiLab referensiFungsiLab;
    private ReferensiFrekKurikulum referensiFrekKurikulum;
    private ReferensiDirektoratJenderal referensiDirektoratJenderal;
    private ReferensiDirektorat referensiDirektorat;
    private ReferensiBeasiswa referensiBeasiswa;
    private ReferensiBagian referensiBagian;
    private ReferensiAkreditasi referensiAkreditasi;
    private ReferensiAgama referensiAgama;
    private ReferensiLogAudit referensiLogAudit;
    private KewenanganKelompokPengguna kewenanganKelompokPengguna; //ditambahkan Selasa22Jan2013
    private KewenanganPengguna kewenanganPengguna;
    private NamaPengguna namaPengguna;
    private PilihFakultas pilihfakultas;
    
    //ditambahkan pada sabtu, 22 des 2012@krembangan 22.52
    private String kodeperguruantinggi; 
    private String namarole;
    private String prodi,fakultas;
    private String query, str;
    private String [][] data;
    
    //ditambahkan pada Senin, 14 jan 2013@krembangan 14:46
    private String [] judulLama, judulBaru;

    //ditambahkan Selasa22Jan2013@mulyosari99 21:45
    private String [] namaHari = new String [] {"", "Minggu", "Senin", "Selasa", "Rabu",
            "Kamis", "Jumat", "Sabtu"};
    private String [] namaBulan = new String [] {"Januari", "Februari", "Maret",
            "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober",
            "November", "Desember"
    };

    /**
     * Creates new form HomePage
     */
    public HomePage(Bingkai _bingkai) throws SQLException {
        this.bingkai = _bingkai;
        koneksiDB = bingkai.dapatkanKoneksiDB();
        simpanKodePT(bingkai.dapatkanPT());
        System.out.println("Kode PT : "+kodeperguruantinggi);
        initComponents();
        System.out.println("panel HOME diinstansiasi");
        tampilkanJam();
        tampilkanTicker();
        //labelLogin.setText(bingkai.dapatkanUserPassword()[0]);//cara lama
        
        
        labelStatusKoneksi.setText(koneksiDB.dapatkanStatusKoneksi());
        
        menuHome = new MenuHome(this);
        panelMetro.add(menuHome, BorderLayout.CENTER);
        
        labelUpOneLevel.setVisible(false);
        //labelJudul.setText("Home");
        labelJudul.setText("Menu Utama");
        
        judulLama = new String [] {"Home", "Dosen1", "Dosen2", "Dosen3",
            "Mhs1", "Mhs2", "Lembaga1", "Lembaga2", "Lembaga3", "Lembaga4",
            "MasterDosen", "AktivitasMengajarDosen", "AnggotaPenelitiAhli",
            "AnggotaPenelitiPendukung", "AnggotaPenelitianDosen",
            "FTEDosen", "HKI", "KinerjaDosen", "PublikasiDosenTetap",
            "RiwayatPendidikanDosen", "RiwayatStatusDosen", "TenagaAhli",
            "TenagaPendukung", "MasterMahasiswa", "KuliahMahasiswa", 
            "NilaiSemesterMahasiswa", "PindahanMahasiswaAsing", 
            "PrestasiMahasiswa", "RiwayatBeasiswa", "RiwayatStatusMahasiswa",
            "BobotNilai", "DayaTampung", "KerjasamaInstansi", "RiwayatAkreditasiProdi",
            "RiwayatAkreditasiPT", "RiwayatKepemilikanPT", "RiwayatSKProdi", 
            "RiwayatSKPT", "ValidasiPT", "MasterProdi", "MasterJurusan",
            "MasterFakultas", "MasterPT", "MasterPustaka", "MasterSaranaPT",
            "MasterLaboratorium", "MasterFasilitasPenunjangAkademik", 
            "MasterKerjasamaPTLuarNegeri", "MasterMataKuliah", "MasterBadanHukum",
            "MasterPegawai", "Sistem", "Utiliti", "Referensi", "Referensi2",
            "Wilayah", "StatusAktivitasDosen", "StatusMahasiswa", "Seksi", 
            "PeranPengguna", "Pulau", "Provinsi", "Penduduk", "Negara", "Module",
            "Referensi3", "Referensi4", "Referensi5", "Menu", "Kota", "Kopertis",
            "Kementrian", "KelompokBidangIlmu", "KategoriPenghasilan", 
            "JenjangPendidikan", "JenisPerguruanTinggi", "JabatanAkademik", 
            "IkatanKerjaDosen", "FungsiLab", "FrekuensiKurikulum", "DirektoratJenderal",
            "Direktorat", "Beasiswa", "Bagian", "Akreditasi", "Agama", "LogAudit",
            "Sistem", "KewenanganKelompokPengguna", "KewenanganPengguna", 
            "NamaPengguna"
        };
        judulBaru = new String [] {"Menu Utama", "Menu Dosen 1", "Menu Dosen 2",
            "Menu Dosen 3", "Menu Mahasiswa 1", "Menu Mahasiswa 2", 
            "Menu Lembaga 1", "Menu Lembaga 2", "Menu Lembaga 3", 
            "Menu Lembaga 4", "Memasukkan Data Dosen", 
            "Memasukkan Aktivitas Mengajar Dosen", 
            "Memasukkan Anggota Peneliti Ahli", 
            "Memasukkan Anggota Peneliti Pendukung",
            "Memasukkan Anggota Penelitian Dosen", "Memasukkan FTE Dosen",
            "Memasukkan HKI", "Memasukkan Kinerja Dosen", 
            "Memasukkan Publikasi Dosen Tetap", "Memasukkan Riwayat Pendidikan Dosen",
            "Memasukkan Riwayat Status Dosen", "Memasukkan Tenaga Ahli",   
            "Memasukkan Tenaga Pendukung", "Memasukkan Master Mahasiswa",
            "Memasukkan Kuliah Mahasiswa", "Memasukkan Nilai Semester Mahasiswa",
            "Memasukkan Pindahan Mahasiswa Asing", "Memasukkan Prestasi Mahasiswa",
            "Memasukkan Riwayat Beasiswa", "Memasukkan Riwayat Status Mahasiswa",
            "Memasukkan Bobot Nilai", "Memasukkan Daya Tampung", 
            "Memasukkan Kerjasama Instansi", "Memasukkan Riwayat Akreditasi Prodi",
            "Memasukkan Riwayat Akreditasi PT", "Memasukkan Riwayat Kepemilikan PT",
            "Memasukkan Riwayat SK Prodi", "Memasukkan Riwayat SK PT",
            "Memasukkan Validasi PT", "Memasukkan Master Prodi", 
            "Memasukkan Master Jurusan", "Memasukkan Master Fakultas",
            "Memasukkan Master PT", "Memasukkan Master Pustaka", 
            "Memasukkan Master Sarana PT", "Memasukkan MasterLaboratorium",
            "Memasukkan Master Fasilitas Penunjang Akademik",
            "Memasukkan Master Kerjasama PT Luar Negeri", 
            "Memasukkan Master Mata Kuliah", "Memasukkan Master Badan Hukum",
            "Memasukkan Master Pegawai", "Menu Sistem", "Menu Utiliti", 
            "Menu Referensi", "Menu Referensi 2", "Memasukkan Wilayah",
            "Memasukkan Status Aktivitas Dosen", "Memasukkan Status Mahasiswa",
            "Memasukkan Seksi", "Memasukkan Peran Pengguna", 
            "Memasukkan Pulau", "Memasukkan Provinsi", "Memasukkan Penduduk",
            "Memasukkan Negara", "Memasukkan Module", "Menu Referensi 3", 
            "Menu Referensi 4", "Menu Referensi 5", "Memasukkan Menu", 
            "Memasukkan Kota", "Memasukkan Kopertis", "Memasukkan Kementrian",
            "Memasukkan Kelompok Bidang Ilmu", "Memasukkan Kategori Penghasilan",
            "Memasukkan Jenjang Pendidikan", "Memasukkan Jenis Perguruan Tinggi",
            "Memasukkan Jabatan Akademik", "Memasukkan Ikatan Kerja Dosen", 
            "Memasukkan Fungsi Lab", "Memasukkan Frekuensi Kurikulum", 
            "Memasukkan Direktorat Jenderal", "Memasukkan Direktorat", 
            "Memasukkan Beasiswa", "Memasukkan Bagian", "Memasukkan Akreditasi",
            "Memasukkan Agama", "Meninjau Log Audit", "Menu Sistem", 
            "Memasukkan Kewenangan Kelompok Pengguna", 
            "Memasukkan Kewenangan Pengguna", "Memasukkan Nama Pengguna"
        };
    }
    
    public void simpanKodePT(String kodept) {
        kodeperguruantinggi = kodept;
    }
    
    public void simpanNamaRole(String _namarole) {
        namarole = _namarole;
    }
     public void simpanFakultas(String _fakultas) {
        fakultas = _fakultas;
    }
      public void simpanProdi(String _prodi) {
        prodi = _prodi;
    }
    public void tampilkanDataUserDanRole() {
        labelLogin.setText(bingkai.dapatkanUserPassword()[0]);//cara baru
        jLabel2.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 24));
        jLabel2.setText("<html>Sistem Informasi Manajemen Pelaporan PDPT<br><center>SIMPEL PDPT<br><center>"+namarole+"<br><center>"+fakultas+"/"+prodi+"</html>");
        
    }
    
    public String dapatkanUsername() {
        return bingkai.dapatkanUser();
    }
    
    public String dapatkanKodePT() {
        return kodeperguruantinggi;
    }
    
    public String dapatkanNamaRole() {
        return namarole;
    }
    public String dapatkanProdi(){
        return prodi;
    }
    public String dapatkanFakultas(){
        return fakultas;
    }
    
    public void panelBerikutnya(String namaPanel) {
        //cara baru ditambahkan pada sabtu, 22 des 2012@krembangan 23.37
        labelLogin.setText(bingkai.dapatkanUserPassword()[0] + "(" + dapatkanNamaRole() + ")");
        
        namaPanelAktif = namaPanel;
        
        //labelJudul.setText(namaPanel);//ganti di sini utk mengubah judul setiap panel agar jelas dan mudah dibaca
        
        for (int a=0; a<judulLama.length; a++) {
            if (judulLama[a].equals(namaPanel)) {
                labelJudul.setText(judulBaru[a]);
            }
        }
        
        
        if (namaPanel.equals("Home") ) {
            panelMetro.removeAll();
            menuHome = new MenuHome(this);
            panelMetro.add(menuHome, BorderLayout.CENTER);
            labelUpOneLevel.setVisible(false);
        }
        else if (namaPanel.equals("pilihfakultas") ) {
                panelMetro.removeAll();
                pilihfakultas = new PilihFakultas(this);
                panelMetro.add(pilihfakultas, BorderLayout.CENTER);
                labelUpOneLevel.setVisible(false);
            } 
        else {
            if (namaPanel.equals("Dosen1") ) {
                panelMetro.removeAll();
                menuDosen1 = new MenuDosen1(this);
                panelMetro.add(menuDosen1, BorderLayout.CENTER);
            } 
             
            if (namaPanel.equals("Dosen2") ) {
                panelMetro.removeAll();
                menuDosen2 = new MenuDosen2(this);
                panelMetro.add(menuDosen2, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Dosen3") ) {
                panelMetro.removeAll();
                menuDosen3 = new MenuDosen3(this);
                panelMetro.add(menuDosen3, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Mhs1") ) {
                panelMetro.removeAll();
                menuMhs1 = new MenuMhs1(this);
                panelMetro.add(menuMhs1, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Mhs2") ) {
                panelMetro.removeAll();
                menuMhs2 = new MenuMhs2(this);
                panelMetro.add(menuMhs2, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Lembaga1") ) {
                panelMetro.removeAll();
                menuLembaga1 = new MenuLembaga1(this);
                panelMetro.add(menuLembaga1, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Lembaga2") ) {
                panelMetro.removeAll();
                menuLembaga2 = new MenuLembaga2(this);
                panelMetro.add(menuLembaga2, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Lembaga3") ) {
                panelMetro.removeAll();
                menuLembaga3 = new MenuLembaga3(this);
                panelMetro.add(menuLembaga3, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Lembaga4") ) {
                panelMetro.removeAll();
                menuLembaga4 = new MenuLembaga4(this);
                panelMetro.add(menuLembaga4, BorderLayout.CENTER);
            }
            if (namaPanel.equals("MasterDosen") ) {
                panelMetro.removeAll();
                masterDosen = new MasterDosen(this, "MasterDosen");
                panelMetro.add(masterDosen, BorderLayout.CENTER);
            }
            if (namaPanel.equals("AktivitasMengajarDosen") ) {
                panelMetro.removeAll();
                aktivitasMengajarDosen = new AktivitasMengajarDosen(this, "AktivitasMengajarDosen");
                panelMetro.add(aktivitasMengajarDosen, BorderLayout.CENTER);
            }
            if (namaPanel.equals("AnggotaPenelitiAhli") ) {
                panelMetro.removeAll();
                anggotaPenelitiAhli = new AnggotaPenelitiAhli(this, "AnggotaPenelitiAhli");
                panelMetro.add(anggotaPenelitiAhli, BorderLayout.CENTER);
            }
            if (namaPanel.equals("AnggotaPenelitiPendukung") ) {
                panelMetro.removeAll();
                anggotaPenelitiPendukung = new AnggotaPenelitiPendukung(this, "AnggotaPenelitiPendukung");
                panelMetro.add(anggotaPenelitiPendukung, BorderLayout.CENTER);
            }
            if (namaPanel.equals("AnggotaPenelitianDosen") ) {
                panelMetro.removeAll();
                anggotaPenelitianDosen = new AnggotaPenelitianDosen(this, "AnggotaPenelitianDosen");
                panelMetro.add(anggotaPenelitianDosen, BorderLayout.CENTER);
            }
            if (namaPanel.equals("FTEDosen") ) {
                panelMetro.removeAll();
                fTEDosen = new FTEDosen(this, "FTEDosen");
                panelMetro.add(fTEDosen, BorderLayout.CENTER);
            }
            if (namaPanel.equals("HKI") ) {
                panelMetro.removeAll();
                hki = new HKI(this, "HKI");
                panelMetro.add(hki, BorderLayout.CENTER);
            }
            if (namaPanel.equals("KinerjaDosen") ) {
                panelMetro.removeAll();
                kinerjaDosen = new KinerjaDosen(this, "KinerjaDosen");
                panelMetro.add(kinerjaDosen, BorderLayout.CENTER);
            }
            if (namaPanel.equals("PublikasiDosenTetap") ) {
                panelMetro.removeAll();
                publikasiDosenTetap = new PublikasiDosenTetap(this, "PublikasiDosenTetap");
                panelMetro.add(publikasiDosenTetap, BorderLayout.CENTER);
            }
            if (namaPanel.equals("RiwayatPendidikanDosen") ) {
                panelMetro.removeAll();
                riwayatPendidikanDosen = new RiwayatPendidikanDosen(this, "RiwayatPendidikanDosen");
                panelMetro.add(riwayatPendidikanDosen, BorderLayout.CENTER);
            }
            if (namaPanel.equals("RiwayatStatusDosen") ) {
                panelMetro.removeAll();
                riwayatStatusDosen = new RiwayatStatusDosen(this, "RiwayatStatusDosen");
                panelMetro.add(riwayatStatusDosen, BorderLayout.CENTER);
            }
            if (namaPanel.equals("TenagaAhli") ) {
                panelMetro.removeAll();
                tenagaAhli = new TenagaAhli(this, "TenagaAhli");
                panelMetro.add(tenagaAhli, BorderLayout.CENTER);
            }
            if (namaPanel.equals("TenagaPendukung") ) {
                panelMetro.removeAll();
                tenagaPendukung = new TenagaPendukung(this, "TenagaPendukung");
                panelMetro.add(tenagaPendukung, BorderLayout.CENTER);
            }
            if (namaPanel.equals("MasterMahasiswa") ) {
                panelMetro.removeAll();
                masterMahasiswa = new MasterMahasiswa(this, "MasterMahasiswa");
                panelMetro.add(masterMahasiswa, BorderLayout.CENTER);
            }
            if (namaPanel.equals("KuliahMahasiswa") ) {
                panelMetro.removeAll();
                kuliahMahasiswa = new KuliahMahasiswa(this, "KuliahMahasiswa");
                panelMetro.add(kuliahMahasiswa, BorderLayout.CENTER);
            }
            if (namaPanel.equals("NilaiSemesterMahasiswa") ) {
                panelMetro.removeAll();
                nilaiSemesterMahasiswa = new NilaiSemesterMahasiswa(this, "NilaiSemesterMahasiswa");
                panelMetro.add(nilaiSemesterMahasiswa, BorderLayout.CENTER);
            }
            if (namaPanel.equals("PindahanMahasiswaAsing") ) {
                panelMetro.removeAll();
                pindahanMahasiswaAsing = new PindahanMahasiswaAsing(this, "PindahanMahasiswaAsing");
                panelMetro.add(pindahanMahasiswaAsing, BorderLayout.CENTER);
            }
            if (namaPanel.equals("PrestasiMahasiswa") ) {
                panelMetro.removeAll();
                prestasiMahasiswa = new PrestasiMahasiswa(this, "PrestasiMahasiswa");
                panelMetro.add(prestasiMahasiswa, BorderLayout.CENTER);
            }
            if (namaPanel.equals("RiwayatBeasiswa") ) {
                panelMetro.removeAll();
                riwayatBeasiswa = new RiwayatBeasiswa(this, "RiwayatBeasiswa");
                panelMetro.add(riwayatBeasiswa, BorderLayout.CENTER);
            }
            if (namaPanel.equals("RiwayatStatusMahasiswa") ) {
                panelMetro.removeAll();
                riwayatStatusMahasiswa = new RiwayatStatusMahasiswa(this, "RiwayatStatusMahasiswa");
                panelMetro.add(riwayatStatusMahasiswa, BorderLayout.CENTER);
            }
            if (namaPanel.equals("BobotNilai") ) {
                panelMetro.removeAll();
                bobotNilai = new BobotNilai(this, "BobotNilai");
                panelMetro.add(bobotNilai, BorderLayout.CENTER);
            }
            if (namaPanel.equals("DayaTampung") ) {
                panelMetro.removeAll();
                dayaTampung = new DayaTampung(this, "DayaTampung");
                panelMetro.add(dayaTampung, BorderLayout.CENTER);
            }
            if (namaPanel.equals("KerjasamaInstansi") ) {
                panelMetro.removeAll();
                kerjasamaInstansi = new KerjasamaInstansi(this, "KerjasamaInstansi");
                panelMetro.add(kerjasamaInstansi, BorderLayout.CENTER);
            }
            if (namaPanel.equals("RiwayatAkreditasiProdi") ) {
                panelMetro.removeAll();
                riwayatAkreditasiProdi = new RiwayatAkreditasiProdi(this, "RiwayatAkreditasiProdi");
                panelMetro.add(riwayatAkreditasiProdi, BorderLayout.CENTER);
            }
            if (namaPanel.equals("RiwayatAkreditasiPT") ) {
                panelMetro.removeAll();
                riwayatAkreditasiPT = new RiwayatAkreditasiPT(this, "RiwayatAkreditasiPT");
                panelMetro.add(riwayatAkreditasiPT, BorderLayout.CENTER);
            }
            if (namaPanel.equals("RiwayatKepemilikanPT") ) {
                panelMetro.removeAll();
                riwayatKepemilikanPT = new RiwayatKepemilikanPT(this, "RiwayatKepemilikanPT");
                panelMetro.add(riwayatKepemilikanPT, BorderLayout.CENTER);
            }
            if (namaPanel.equals("RiwayatSKProdi") ) {
                panelMetro.removeAll();
                riwayatSKProdi = new RiwayatSKProdi(this, "RiwayatSKProdi");
                panelMetro.add(riwayatSKProdi, BorderLayout.CENTER);
            }
            if (namaPanel.equals("RiwayatSKPT") ) {
                panelMetro.removeAll();
                riwayatSKPT = new RiwayatSKPT(this, "RiwayatSKPT");
                panelMetro.add(riwayatSKPT, BorderLayout.CENTER);
            }
            if (namaPanel.equals("ValidasiPT") ) {
                panelMetro.removeAll();
                validasiPT = new ValidasiPT(this, "ValidasiPT");
                panelMetro.add(validasiPT, BorderLayout.CENTER);
            }
            if (namaPanel.equals("MasterProdi") ) {
                panelMetro.removeAll();
                masterProdi = new MasterProdi(this, "MasterProdi");
                panelMetro.add(masterProdi, BorderLayout.CENTER);
            }
            if (namaPanel.equals("MasterJurusan") ) {
                panelMetro.removeAll();
                masterJurusan = new MasterJurusan(this, "MasterJurusan");
                panelMetro.add(masterJurusan, BorderLayout.CENTER);
            }
            if (namaPanel.equals("MasterFakultas") ) {
                panelMetro.removeAll();
                masterFakultas = new MasterFakultas(this, "MasterFakultas");
                panelMetro.add(masterFakultas, BorderLayout.CENTER);
            }
            if (namaPanel.equals("MasterPT") ) {
                panelMetro.removeAll();
                masterPT = new MasterPT(this, "MasterPT");
                panelMetro.add(masterPT, BorderLayout.CENTER);
            }
            if (namaPanel.equals("MasterPustaka") ) {
                panelMetro.removeAll();
                masterPustaka = new MasterPustaka(this, "MasterPustaka");
                panelMetro.add(masterPustaka, BorderLayout.CENTER);
            }
            if (namaPanel.equals("MasterSaranaPT") ) {
                panelMetro.removeAll();
                masterSaranaPT = new MasterSaranaPT(this, "MasterSaranaPT");
                panelMetro.add(masterSaranaPT, BorderLayout.CENTER);
            }
            if (namaPanel.equals("MasterLaboratorium") ) {
                panelMetro.removeAll();
                masterLaboratorium = new MasterLaboratorium(this, "MasterLaboratorium");
                panelMetro.add(masterLaboratorium, BorderLayout.CENTER);
            }
            if (namaPanel.equals("MasterFasilitasPenunjangAkademik") ) {
                panelMetro.removeAll();
                masterFasilitasUmumPenunjangAkademik = 
                        new MasterFasilitasUmumPenunjangAkademik(this, 
                            "MasterFasilitasPenunjangAkademik");
                panelMetro.add(masterFasilitasUmumPenunjangAkademik, 
                        BorderLayout.CENTER);
            }
            if (namaPanel.equals("MasterKerjasamaPTLuarNegeri") ) {
                panelMetro.removeAll();
                masterKerjasamaPTLN = new MasterKerjasamaPTLN(this, "MasterKerjasamaPTLuarNegeri");
                panelMetro.add(masterKerjasamaPTLN, BorderLayout.CENTER);
            }
            if (namaPanel.equals("MasterMataKuliah") ) {
                panelMetro.removeAll();
                masterMatakuliah = new MasterMatakuliah(this, "MasterMataKuliah");
                panelMetro.add(masterMatakuliah, BorderLayout.CENTER);
            }
            if (namaPanel.equals("MasterBadanHukum") ) {
                panelMetro.removeAll();
                masterBadanHukum = new MasterBadanHukum(this, "MasterBadanHukum");
                panelMetro.add(masterBadanHukum, BorderLayout.CENTER);
            }
            if (namaPanel.equals("MasterPegawai") ) {
                panelMetro.removeAll();
                masterPegawai = new MasterPegawai(this, "MasterPegawai");
                panelMetro.add(masterPegawai, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Sistem") ) {//ahad,30des2012@krembangan1:11
                panelMetro.removeAll();
                menuSistem = new MenuSistem(this);
                panelMetro.add(menuSistem, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Utiliti") ) {//ahad,30des2012@krembangan1:11
                panelMetro.removeAll();
                menuUtiliti = new MenuUtiliti(this);
                panelMetro.add(menuUtiliti, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Referensi") ) {//ahad,30des2012@krembangan1:11
                panelMetro.removeAll();
                menuReferensi = new MenuReferensi(this);
                panelMetro.add(menuReferensi, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Referensi2") ) {//ahad,30des2012@krembangan1:11
                panelMetro.removeAll();
                menuReferensi2 = new MenuReferensi2(this);
                panelMetro.add(menuReferensi2, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Wilayah") ) {
                panelMetro.removeAll();
                referensiWilayah = new ReferensiWilayah(this, "Wilayah");
                panelMetro.add(referensiWilayah, BorderLayout.CENTER);
            }
            if (namaPanel.equals("StatusAktivitasDosen") ) {
                panelMetro.removeAll();
                referensiStatusAktivitasDosen = new ReferensiStatusAktivitasDosen(this, "Status Aktivitas Dosen");
                panelMetro.add(referensiStatusAktivitasDosen, BorderLayout.CENTER);
            }
            if (namaPanel.equals("StatusMahasiswa") ) {
                panelMetro.removeAll();
                referensiStatusMahasiswa = new ReferensiStatusMahasiswa(this, "StatusMahasiswa");
                panelMetro.add(referensiStatusMahasiswa, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Seksi") ) {
                panelMetro.removeAll();
                referensiSeksi = new ReferensiSeksi(this, "Seksi");
                panelMetro.add(referensiSeksi, BorderLayout.CENTER);
            }
            if (namaPanel.equals("PeranPengguna") ) {
                panelMetro.removeAll();
                referensiPeranPengguna = new ReferensiPeranPengguna(this, "PeranPengguna");
                panelMetro.add(referensiPeranPengguna, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Pulau") ) {
                panelMetro.removeAll();
                referensiPulau = new ReferensiPulau(this, "Pulau");
                panelMetro.add(referensiPulau, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Provinsi") ) {
                panelMetro.removeAll();
                referensiProvinsi = new ReferensiProvinsi2(this, "Provinsi");
                panelMetro.add(referensiProvinsi, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Penduduk") ) {
                panelMetro.removeAll();
                referensiPenduduk = new ReferensiPenduduk(this, "Penduduk");
                panelMetro.add(referensiPenduduk, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Negara") ) {
                panelMetro.removeAll();
                referensiNegara = new ReferensiNegara(this, "Negara");
                panelMetro.add(referensiNegara, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Module") ) {
                panelMetro.removeAll();
                referensiModule = new ReferensiModule(this, "Module");
                panelMetro.add(referensiModule, BorderLayout.CENTER);
            }            
            if (namaPanel.equals("Referensi3") ) {//senin,31des2012@masjidkemayoran20:28
                panelMetro.removeAll();
                menuReferensi3 = new MenuReferensi3(this);
                panelMetro.add(menuReferensi3, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Referensi4") ) {//senin,31des2012@masjidkemayoran20:28
                panelMetro.removeAll();
                menuReferensi4 = new MenuReferensi4(this);
                panelMetro.add(menuReferensi4, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Referensi5") ) {//senin,31des2012@masjidkemayoran20:28
                panelMetro.removeAll();
                menuReferensi5 = new MenuReferensi5(this);
                panelMetro.add(menuReferensi5, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Menu") ) {//selasa,1jan2013@krembangan0:27
                panelMetro.removeAll();
                referensiMenu = new ReferensiMenu(this,"Menu");
                panelMetro.add(referensiMenu, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Kota") ) {//selasa,1jan2013@krembangan2:48
                panelMetro.removeAll();
                referensiKota = new ReferensiKota(this,"Kota");
                panelMetro.add(referensiKota, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Kopertis") ) {//selasa,1jan2013@krembangan13:58
                panelMetro.removeAll();
                referensiKopertis = new ReferensiKopertis(this,"Kopertis");
                panelMetro.add(referensiKopertis, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Kementrian") ) {//selasa,1jan2013@krembangan13:58
                panelMetro.removeAll();
                referensiKementrian = new ReferensiKementrian(this,"Kementrian");
                panelMetro.add(referensiKementrian, BorderLayout.CENTER);
            }
            if (namaPanel.equals("KelompokBidangIlmu") ) {//selasa,1jan2013@krembangan16:43
                panelMetro.removeAll();
                referensiKelompokBidangIlmu = new ReferensiKelompokBidangIlmu(this,"KelompokBidangIlmu");
                panelMetro.add(referensiKelompokBidangIlmu, BorderLayout.CENTER);
            }
            if (namaPanel.equals("KategoriPenghasilan") ) {//selasa,1jan2013@krembangan16:43
                panelMetro.removeAll();
                referensiKategoriPenghasilan = new ReferensiKategoriPenghasilan(this,"KategoriPenghasilan");
                panelMetro.add(referensiKategoriPenghasilan, BorderLayout.CENTER);
            }
            if (namaPanel.equals("JenjangPendidikan") ) {//selasa,1jan2013@krembangan16:43
                panelMetro.removeAll();
                referensiJenjangPendidikan = new ReferensiJenjangPendidikan(this,"JenjangPendidikan");
                panelMetro.add(referensiJenjangPendidikan, BorderLayout.CENTER);
            }
            if (namaPanel.equals("JenisPerguruanTinggi") ) {//rabu,2jan2013@krembangan5:14
                panelMetro.removeAll();
                referensiJenisPerguruanTinggi = new ReferensiJenisPerguruanTinggi(this,"JenisPerguruanTinggi");
                panelMetro.add(referensiJenisPerguruanTinggi, BorderLayout.CENTER);
            }
            if (namaPanel.equals("JabatanAkademik") ) {//rabu,2jan2013@krembangan5:14
                panelMetro.removeAll();
                referensiJabatanAkademik = new ReferensiJabatanAkademik(this,"JabatanAkademik");
                panelMetro.add(referensiJabatanAkademik, BorderLayout.CENTER);
            }
            if (namaPanel.equals("IkatanKerjaDosen") ) {//rabu,2jan2013@krembangan5:14
                panelMetro.removeAll();
                referensiIkatanKerjaDosen = new ReferensiIkatanKerjaDosen(this,"IkatanKerjaDosen");
                panelMetro.add(referensiIkatanKerjaDosen, BorderLayout.CENTER);
            }
            if (namaPanel.equals("FungsiLab") ) {//rabu,2jan2013@krembangan5:14
                panelMetro.removeAll();
                referensiFungsiLab = new ReferensiFungsiLab(this,"FungsiLab");
                panelMetro.add(referensiFungsiLab, BorderLayout.CENTER);
            }
            if (namaPanel.equals("FrekuensiKurikulum") ) {//rabu,2jan2013@krembangan10:10
                panelMetro.removeAll();
                referensiFrekKurikulum = new ReferensiFrekKurikulum(this,"FrekuensiKurikulum");
                panelMetro.add(referensiFrekKurikulum, BorderLayout.CENTER);
            }
            if (namaPanel.equals("DirektoratJenderal") ) {//rabu,2jan2013@krembangan10:10
                panelMetro.removeAll();
                referensiDirektoratJenderal = new ReferensiDirektoratJenderal(this,"DirektoratJenderal");
                panelMetro.add(referensiDirektoratJenderal, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Direktorat") ) {//rabu,2jan2013@krembangan10:10
                panelMetro.removeAll();
                referensiDirektorat = new ReferensiDirektorat(this,"Direktorat");
                panelMetro.add(referensiDirektorat, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Beasiswa") ) {//rabu,2jan2013@krembangan12:18
                panelMetro.removeAll();
                referensiBeasiswa = new ReferensiBeasiswa(this,"Beasiswa");
                panelMetro.add(referensiBeasiswa, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Bagian") ) {//rabu,2jan2013@krembangan12:40
                panelMetro.removeAll();
                referensiBagian = new ReferensiBagian(this,"Bagian");
                panelMetro.add(referensiBagian, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Akreditasi") ) {//rabu,2jan2013@krembangan12:58
                panelMetro.removeAll();
                referensiAkreditasi = new ReferensiAkreditasi(this,"Akreditasi");
                panelMetro.add(referensiAkreditasi, BorderLayout.CENTER);
            }
            if (namaPanel.equals("Agama") ) {//rabu,2jan2013@krembangan13:11
                panelMetro.removeAll();
                referensiAgama = new ReferensiAgama(this,"Agama");
                panelMetro.add(referensiAgama, BorderLayout.CENTER);
            }
            if (namaPanel.equals("LogAudit") ) {//rabu,2jan2013@krembangan13:11
                panelMetro.removeAll();
                referensiLogAudit = new ReferensiLogAudit(this,"LogAudit");
                panelMetro.add(referensiLogAudit, BorderLayout.CENTER);
            }
            if (namaPanel.equals("KewenanganKelompokPengguna") ) {//Selasa,22Jan2013@krembangan15:08
                panelMetro.removeAll();
                kewenanganKelompokPengguna = new KewenanganKelompokPengguna(this,"KewenanganKelompokPengguna");
                panelMetro.add(kewenanganKelompokPengguna, BorderLayout.CENTER);
            }
            if (namaPanel.equals("KewenanganPengguna") ) {//Selasa,22Jan2013@krembangan15:08
                panelMetro.removeAll();
                kewenanganPengguna = new KewenanganPengguna(this,"KewenanganPengguna");
                panelMetro.add(kewenanganPengguna, BorderLayout.CENTER);
            }
            if (namaPanel.equals("NamaPengguna") ) {//Selasa,22Jan2013@krembangan15:08
                panelMetro.removeAll();
                namaPengguna = new NamaPengguna(this,"NamaPengguna");
                panelMetro.add(namaPengguna, BorderLayout.CENTER);
            }
            labelUpOneLevel.setVisible(true);
        }
    }
    
    public koneksi.KoneksiOracleThinClient dapatkanKoneksiDB() {
        return bingkai.dapatkanKoneksiDB();
    }
    
    private String dapatkanDataWaktuIndonesia() {        
        Calendar kalender = Calendar.getInstance();
        int hari = kalender.get(Calendar.DAY_OF_WEEK); 
        int tanggal = kalender.get(Calendar.DAY_OF_MONTH);
        int bulan = kalender.get(Calendar.MONTH); 
        int tahun = kalender.get(Calendar.YEAR);        
        int jam = kalender.get(Calendar.HOUR_OF_DAY);
        int menit = kalender.get(Calendar.MINUTE);
        int detik = kalender.get(Calendar.SECOND);
        return "" + namaHari[hari] + ", " + tanggal + " " + 
                namaBulan[bulan] + " " + tahun + 
                " " + jam + ":" + menit + ":" + detik;
    }
    
    private void tampilkanJam() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //labelWaktu.setText(new Date().toString());//dikomen Selasa22Jan2013
                labelWaktu.setText(dapatkanDataWaktuIndonesia());
            }
        });
        timer.start();
    }
    
    private void tampilkanTicker() {
        koneksiDB = dapatkanKoneksiDB();
        query = "select \"Pesan\" from pdpt_dev.\"Tran_Ticker\"";
        koneksiDB.selectData(query);     
        //jmlKolom = kon.dapatkanJumlahKolom();
        data = koneksiDB.dapatkanData();
        str = data[0][0];
        timerTicker = new Timer(700, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char c = str.charAt(0);
                String rest = str.substring(1);
                str = rest+c;
                labelStatus.setText(str);
            }
        });
        timerTicker.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSedang = new javax.swing.JPanel();
        panelHome = new javax.swing.JPanel();
        panelAikonHome = new javax.swing.JPanel();
        labelHome = new javax.swing.JLabel();
        labelUpOneLevel = new javax.swing.JLabel();
        panelJudul = new javax.swing.JPanel();
        labelJudul = new javax.swing.JLabel();
        panelShoutBox = new javax.swing.JPanel();
        labelShoutBox = new javax.swing.JLabel();
        panelMetro = new javax.swing.JPanel();
        panelBawah = new javax.swing.JPanel();
        panelStatus = new javax.swing.JPanel();
        labelStatus = new javax.swing.JLabel();
        panelTutupAplikasi = new javax.swing.JPanel();
        labelTutupAplikasi = new Widget.Label();
        panelBanner = new javax.swing.JPanel();
        panelLogo = new javax.swing.JPanel();
        labelLogo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panelLogin = new javax.swing.JPanel();
        labelAvatar = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        labelLogin = new javax.swing.JLabel();
        labelWaktu = new javax.swing.JLabel();
        labelStatusKoneksi = new javax.swing.JLabel();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        panelSedang.setOpaque(false);
        panelSedang.setLayout(new java.awt.BorderLayout(20, 20));

        panelHome.setOpaque(false);
        panelHome.setLayout(new java.awt.BorderLayout());

        panelAikonHome.setOpaque(false);

        labelHome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Home-64.png"))); // NOI18N
        labelHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelHomeMouseClicked(evt);
            }
        });

        labelUpOneLevel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelUpOneLevel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/upOneLevel_mini.png"))); // NOI18N
        labelUpOneLevel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelUpOneLevelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelAikonHomeLayout = new javax.swing.GroupLayout(panelAikonHome);
        panelAikonHome.setLayout(panelAikonHomeLayout);
        panelAikonHomeLayout.setHorizontalGroup(
            panelAikonHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAikonHomeLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(labelHome)
                .addGap(18, 18, 18)
                .addComponent(labelUpOneLevel)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        panelAikonHomeLayout.setVerticalGroup(
            panelAikonHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAikonHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAikonHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelUpOneLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelHome, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        panelHome.add(panelAikonHome, java.awt.BorderLayout.WEST);

        panelJudul.setOpaque(false);
        panelJudul.setLayout(new java.awt.BorderLayout());

        labelJudul.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 48)); // NOI18N
        labelJudul.setForeground(new java.awt.Color(255, 153, 0));
        labelJudul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelJudul.add(labelJudul, java.awt.BorderLayout.CENTER);

        panelHome.add(panelJudul, java.awt.BorderLayout.CENTER);

        panelShoutBox.setOpaque(false);
        panelShoutBox.setLayout(new java.awt.BorderLayout());

        labelShoutBox.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelShoutBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/page_flip2_40.png"))); // NOI18N
        labelShoutBox.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelShoutBox.add(labelShoutBox, java.awt.BorderLayout.EAST);

        panelHome.add(panelShoutBox, java.awt.BorderLayout.EAST);

        panelSedang.add(panelHome, java.awt.BorderLayout.NORTH);

        panelMetro.setOpaque(false);
        panelMetro.setPreferredSize(new java.awt.Dimension(1300, 600));
        panelMetro.setLayout(new java.awt.BorderLayout());
        panelSedang.add(panelMetro, java.awt.BorderLayout.CENTER);

        panelBawah.setOpaque(false);
        panelBawah.setLayout(new java.awt.BorderLayout());

        panelStatus.setOpaque(false);
        panelStatus.setLayout(new java.awt.BorderLayout());

        labelStatus.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 24)); // NOI18N
        labelStatus.setForeground(new java.awt.Color(255, 153, 0));
        labelStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        panelStatus.add(labelStatus, java.awt.BorderLayout.CENTER);

        panelBawah.add(panelStatus, java.awt.BorderLayout.CENTER);

        panelTutupAplikasi.setOpaque(false);

        labelTutupAplikasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/exit-64.png"))); // NOI18N
        labelTutupAplikasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelTutupAplikasiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelTutupAplikasiLayout = new javax.swing.GroupLayout(panelTutupAplikasi);
        panelTutupAplikasi.setLayout(panelTutupAplikasiLayout);
        panelTutupAplikasiLayout.setHorizontalGroup(
            panelTutupAplikasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(panelTutupAplikasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelTutupAplikasiLayout.createSequentialGroup()
                    .addGap(0, 18, Short.MAX_VALUE)
                    .addComponent(labelTutupAplikasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 18, Short.MAX_VALUE)))
        );
        panelTutupAplikasiLayout.setVerticalGroup(
            panelTutupAplikasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 64, Short.MAX_VALUE)
            .addGroup(panelTutupAplikasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelTutupAplikasiLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(labelTutupAplikasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        panelBawah.add(panelTutupAplikasi, java.awt.BorderLayout.WEST);

        panelSedang.add(panelBawah, java.awt.BorderLayout.SOUTH);

        add(panelSedang, java.awt.BorderLayout.CENTER);

        panelBanner.setOpaque(false);
        panelBanner.setLayout(new java.awt.BorderLayout());

        panelLogo.setOpaque(false);
        panelLogo.setLayout(new java.awt.BorderLayout());

        labelLogo.setBackground(new java.awt.Color(40, 123, 176));
        labelLogo.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 24)); // NOI18N
        labelLogo.setForeground(new java.awt.Color(255, 153, 0));
        labelLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        //labelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/tutwuri_handayani128.png"))); // NOI18N
		labelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/tutwuri_handayani128.png"))); // NOI18N
        labelLogo.setText("<HTML>\nKementrian Pendidikan Dan Kebudayaan<BR>\nDIREKTORAT JENDERAL PENDIDIKAN TINGGI\n<HTML>");
        panelLogo.add(labelLogo, java.awt.BorderLayout.CENTER);

        panelBanner.add(panelLogo, java.awt.BorderLayout.WEST);

        jLabel2.setBackground(new java.awt.Color(40, 123, 176));
        jLabel2.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 153, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("SIAK-PT");
        panelBanner.add(jLabel2, java.awt.BorderLayout.CENTER);

        panelLogin.setBackground(new java.awt.Color(40, 123, 176));
        panelLogin.setOpaque(false);
        panelLogin.setLayout(new java.awt.BorderLayout(20, 0));

        labelAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Supervisor-64.png"))); // NOI18N
        panelLogin.add(labelAvatar, java.awt.BorderLayout.EAST);

        jPanel1.setBackground(new java.awt.Color(40, 123, 176));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(3, 0));

        labelLogin.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 26)); // NOI18N
        labelLogin.setForeground(new java.awt.Color(255, 153, 0));
        labelLogin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelLogin.setText("Nama yg Login");
        jPanel1.add(labelLogin);

        labelWaktu.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 24)); // NOI18N
        labelWaktu.setForeground(new java.awt.Color(255, 153, 0));
        labelWaktu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelWaktu.setText("Waktu Saat Ini");
        jPanel1.add(labelWaktu);

        labelStatusKoneksi.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 24)); // NOI18N
        labelStatusKoneksi.setForeground(new java.awt.Color(255, 153, 0));
        labelStatusKoneksi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelStatusKoneksi.setText("Status Koneksi: ");
        jPanel1.add(labelStatusKoneksi);

        panelLogin.add(jPanel1, java.awt.BorderLayout.CENTER);

        panelBanner.add(panelLogin, java.awt.BorderLayout.EAST);

        add(panelBanner, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void labelHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelHomeMouseClicked
        // TODO add your handling code here:
        //Component komponen = panelMetro.getComponent(0);
        //panelMetro.remove(komponen);
        //inisialisasiLayarTengah();
        panelBerikutnya("Home");
    }//GEN-LAST:event_labelHomeMouseClicked

    private void labelTutupAplikasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelTutupAplikasiMouseClicked
        // TODO add your handling code here:
        //if (coba != null) coba.putusKoneksi();
        System.exit(0);
    }//GEN-LAST:event_labelTutupAplikasiMouseClicked

    private void labelUpOneLevelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelUpOneLevelMouseClicked
        // TODO add your handling code here:
        if (
                namaPanelAktif.equals("Dosen1") || 
                namaPanelAktif.equals("Dosen2") ||
                namaPanelAktif.equals("Dosen3") ||
                namaPanelAktif.equals("Mhs1")   ||
                namaPanelAktif.equals("Mhs2")   ||
                namaPanelAktif.equals("Lembaga1") ||
                namaPanelAktif.equals("Lembaga2") ||
                namaPanelAktif.equals("Lembaga3") ||
                namaPanelAktif.equals("Lembaga4") ||
                namaPanelAktif.equals("Sistem") ||
                namaPanelAktif.equals("Utiliti") ||
                namaPanelAktif.equals("Referensi") ||
                namaPanelAktif.equals("Referensi2") ||
                namaPanelAktif.equals("Referensi3") ||
                namaPanelAktif.equals("Referensi4") ||
                namaPanelAktif.equals("Referensi5") 
           ) {
            panelBerikutnya("Home");
        }
        if (
                namaPanelAktif.equals("MasterDosen") || 
                namaPanelAktif.equals("AktivitasMengajarDosen") ||
                namaPanelAktif.equals("AnggotaPenelitiAhli") ||
                namaPanelAktif.equals("AnggotaPenelitiPendukung")   ||
                namaPanelAktif.equals("AnggotaPenelitianDosen")   ||
                namaPanelAktif.equals("FTEDosen") 
           ) {
            panelBerikutnya("Dosen1");
        }
        if (
                namaPanelAktif.equals("HKI") || 
                namaPanelAktif.equals("KinerjaDosen") ||
                namaPanelAktif.equals("PublikasiDosenTetap") ||
                namaPanelAktif.equals("RiwayatPendidikanDosen")   ||
                namaPanelAktif.equals("RiwayatStatusDosen")   ||
                namaPanelAktif.equals("TenagaAhli") 
           ) {
            panelBerikutnya("Dosen2");
        }
        if (                
                namaPanelAktif.equals("TenagaPendukung") 
           ) {
            panelBerikutnya("Dosen3");
        }
        if (
                namaPanelAktif.equals("MasterMahasiswa") || 
                namaPanelAktif.equals("KuliahMahasiswa") ||
                namaPanelAktif.equals("NilaiSemesterMahasiswa") ||
                namaPanelAktif.equals("PindahanMahasiswaAsing")   ||
                namaPanelAktif.equals("PrestasiMahasiswa")   ||
                namaPanelAktif.equals("RiwayatBeasiswa") 
           ) {
            panelBerikutnya("Mhs1");
        }
        if (
                namaPanelAktif.equals("RiwayatStatusMahasiswa") 
           ) {
            panelBerikutnya("Mhs2");
        }
        if (
                namaPanelAktif.equals("BobotNilai") || 
                namaPanelAktif.equals("DayaTampung") ||
                namaPanelAktif.equals("KerjasamaInstansi") ||
                namaPanelAktif.equals("RiwayatAkreditasiProdi")   ||
                namaPanelAktif.equals("RiwayatAkreditasiPT")   ||
                namaPanelAktif.equals("RiwayatKepemilikanPT") 
           ) {
            panelBerikutnya("Lembaga1");
        }
        if (
                namaPanelAktif.equals("RiwayatSKProdi") || 
                namaPanelAktif.equals("RiwayatSKPT") ||
                namaPanelAktif.equals("ValidasiPT") ||
                namaPanelAktif.equals("MasterProdi")   ||
                namaPanelAktif.equals("MasterJurusan")   ||
                namaPanelAktif.equals("MasterFakultas") 
           ) {
            panelBerikutnya("Lembaga2");
        }
        if (
                namaPanelAktif.equals("MasterPT") || 
                namaPanelAktif.equals("MasterPustaka") ||
                namaPanelAktif.equals("MasterSaranaPT") ||
                namaPanelAktif.equals("MasterLaboratorium")   ||
                namaPanelAktif.equals("MasterFasilitasPenunjangAkademik")   ||
                namaPanelAktif.equals("MasterKerjasamaPTLuarNegeri") 
           ) {
            panelBerikutnya("Lembaga3");
        }
        if (
                namaPanelAktif.equals("MasterMataKuliah") ||
                namaPanelAktif.equals("MasterBadanHukum") ||
                namaPanelAktif.equals("MasterPegawai") 
           ) {
            panelBerikutnya("Lembaga4");
        }
        if (
                namaPanelAktif.equals("Wilayah") || 
                namaPanelAktif.equals("StatusAktivitasDosen") ||
                namaPanelAktif.equals("StatusMahasiswa") ||
                namaPanelAktif.equals("Seksi")   ||
                namaPanelAktif.equals("PeranPengguna")   ||
                namaPanelAktif.equals("Pulau") 
           ) {
            panelBerikutnya("Referensi");
        }
        if (
                namaPanelAktif.equals("Provinsi") || 
                namaPanelAktif.equals("Penduduk") ||
                namaPanelAktif.equals("Negara") ||
                namaPanelAktif.equals("Module") ||
                namaPanelAktif.equals("Menu") ||
                namaPanelAktif.equals("LogAudit") 
           ) {
            panelBerikutnya("Referensi2");
        }
        if (
                namaPanelAktif.equals("Kota") || 
                namaPanelAktif.equals("Kopertis") ||
                namaPanelAktif.equals("Kementrian") ||
                namaPanelAktif.equals("KelompokBidangIlmu") ||
                namaPanelAktif.equals("KategoriPenghasilan") ||
                namaPanelAktif.equals("JenjangPendidikan") 
           ) {
            panelBerikutnya("Referensi3");
        }
        if (
                namaPanelAktif.equals("JenisPerguruanTinggi") || 
                namaPanelAktif.equals("JabatanAkademik") ||
                namaPanelAktif.equals("IkatanKerjaDosen") ||
                namaPanelAktif.equals("FungsiLab") ||
                namaPanelAktif.equals("FrekuensiKurikulum") ||
                namaPanelAktif.equals("DirektoratJenderal") 
           ) {
            panelBerikutnya("Referensi4");
        }
        if (
                namaPanelAktif.equals("Direktorat") || 
                namaPanelAktif.equals("Beasiswa") ||
                namaPanelAktif.equals("Bagian") ||
                namaPanelAktif.equals("Akreditasi") ||
                namaPanelAktif.equals("Agama") 
           ) {
            panelBerikutnya("Referensi5");
        }
        if (
                namaPanelAktif.equals("KewenanganPengguna") ||
                namaPanelAktif.equals("NamaPengguna") ||
                namaPanelAktif.equals("KewenanganKelompokPengguna") 
           ) {
            panelBerikutnya("Sistem");
        }
    }//GEN-LAST:event_labelUpOneLevelMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelAvatar;
    private javax.swing.JLabel labelHome;
    private javax.swing.JLabel labelJudul;
    private javax.swing.JLabel labelLogin;
    private javax.swing.JLabel labelLogo;
    private javax.swing.JLabel labelShoutBox;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLabel labelStatusKoneksi;
    private Widget.Label labelTutupAplikasi;
    private javax.swing.JLabel labelUpOneLevel;
    private javax.swing.JLabel labelWaktu;
    private javax.swing.JPanel panelAikonHome;
    private javax.swing.JPanel panelBanner;
    private javax.swing.JPanel panelBawah;
    private javax.swing.JPanel panelHome;
    private javax.swing.JPanel panelJudul;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelLogo;
    private javax.swing.JPanel panelMetro;
    private javax.swing.JPanel panelSedang;
    private javax.swing.JPanel panelShoutBox;
    private javax.swing.JPanel panelStatus;
    private javax.swing.JPanel panelTutupAplikasi;
    // End of variables declaration//GEN-END:variables
}
