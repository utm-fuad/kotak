/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelMasterMahasiswa;
import metromini.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.util.Calendar;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import koneksi.KompletTextFieldCombo;

/**
 *
 * @author asus
 */
public class MasterMahasiswa extends javax.swing.JPanel {
    private JPanel panel;
    private koneksi.KoneksiOracleThinClient kon;
    private String judul;
    private HomePage homePage;
    private String namaTabel;
    private String query;
    private int jmlKolom;
    private Calendar kalendar;
    private int hari, tahun;
    private String bulan;
    private String [] namaBulan = {"Jan", "Feb", "Mar", "Apr", "May", 
        "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private int barisYgDipilih = 0;
    Model.ModelMasterMahasiswa model;
    private JTextField kodeprogramstudi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo1;
    private JTextField kodejenjangpendidikan;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    private JTextField jeniskelamin;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    private JTextField kodekota;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo4;
    private JTextField kodeprovinsi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo5;
    private JTextField kodenegara;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo6;
    private JTextField statusmahasiswa;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo7;
    private JTextField kodeprovasalpendidikan;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo8;
    private JTextField statusawalmahasiswa;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo9;
    private JTextField kodeperguruantinggiasal;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo10;
    private JTextField kodeprogramstudiasal;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo11;
    private JTextField kodebeasiswa;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo12;
    private JTextField kodejenjangpendidikansblm;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo13;
    private JTextField kodebiayastudi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo14;
    private JTextField kodepekerjaan;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo15;
    private JTextField kodeptbekerja;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo16;
    private JTextField kodepsbekerja;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo17;
    private JTextField jalurskripsi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo18;
    private JTextField kodekategoripenghasilan;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo19;
    
    private int idlogaudit;
    private String kueri,kodept;

    /**
     * Creates new form PanelHalaman1
     */
    public MasterMahasiswa() {    
        initComponents();
    }
    
    public MasterMahasiswa(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        model = new ModelMasterMahasiswa();
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        kodept = homePage.dapatkanKodePT();
        kodeperguruantinggiField.setText(kodept);
        inisialisasiData();
        initAutoComplete();
    }
    
    private void initIDLogAudit() {
        query = "select \"ID_log_audit\" " +
                " from pdpt_dev.\"TREF_LOG_AUDIT\" " +
                " order by \"ID_log_audit\" DESC";
        kon.selectData(query);     
        String [][] data = kon.dapatkanData();
        if (data[0][0] == null) {
            idlogaudit = 1;
        } else {
            idlogaudit = Integer.parseInt(data[0][0]);
            idlogaudit++;            
        }
        System.out.println("idlogaudit: " + idlogaudit);
    }
    
    private String [] ubahLabelKeSentenceCase(String [] namaKolom) {
        String [] data = new String[namaKolom.length-1]; //nilai length-1 agar id_log_audit diHIDDEN
        for (int a=0; a<data.length; a++) {
            String datum = "";
            String [] sukuKata = namaKolom[a].split("_");
            for (int b=0; b<sukuKata.length; b++) {
                String label = sukuKata[b];
                sukuKata[b] = label.substring(0, 1).toUpperCase() + label.substring(1);
                datum += sukuKata[b] + " ";
            }
            data[a] = datum;
        }
        return data;
    }
    
    private String [] dapatkanNilaiUntukInsertKeLogAudit(String _aksi, 
            String _tabel, String _kueri) {
        String [] data = new String[6];
        data[0] = "" + idlogaudit;
        System.out.println("idlogaudit: " + data[0]);
        data[1] = homePage.dapatkanUsername();
        System.out.println("username: " + data[1]);
        data[2] = _aksi;
        System.out.println("aksi: " + data[2]);
        data[3] = _tabel;
        System.out.println("tabel: " + data[3]);
        data[4] = _kueri;
        System.out.println("kueri: " + data[4]);
        
        data[5] = "sysdate";
        return data;
    }
    
    private void initAutoComplete() {
        kompletTextFieldCombo1 = new KompletTextFieldCombo(kon, 
                kodeprogramstudiField, kodeprogramstudi);        
        query = "select \"Kode_program_studi\", \"Nama_program_studi\" " +
                " from pdpt_dev.\"TMST_PROGRAM_STUDI\" where \"Kode_perguruan_tinggi\" = '"+kodept+"'";
        kompletTextFieldCombo1.initDataUtkAutoComplete(query);        
        kodeprogramstudi = kompletTextFieldCombo1.dapatkanTextField();
        kodeprogramstudiField = kompletTextFieldCombo1.dapatkanComboBox();
        
        kompletTextFieldCombo2 = new KompletTextFieldCombo(kon, 
                kodejenjangpendidikanField, kodejenjangpendidikan);        
        query = "select \"Kode_jenjang_pendidikan\", \"Deskripsi\" " +
                " from pdpt_dev.\"TREF_JENJANG_PENDIDIKAN\""; 
        kompletTextFieldCombo2.initDataUtkAutoComplete(query);        
        kodejenjangpendidikan = kompletTextFieldCombo2.dapatkanTextField();
        kodejenjangpendidikanField = kompletTextFieldCombo2.dapatkanComboBox();
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                jeniskelaminField, jeniskelamin);        
        String [][] datanya3 = {{"L", "Laki-laki"},
            {"P","Perempuan"}}; 
        kompletTextFieldCombo3.initDataUtkAutoComplete(datanya3);
        jeniskelamin = kompletTextFieldCombo3.dapatkanTextField();
        jeniskelaminField = kompletTextFieldCombo3.dapatkanComboBox();
        
        kompletTextFieldCombo4 = new KompletTextFieldCombo(kon, 
                kodekotaField, kodekota);        
        query = "SELECT \"Kode_kota\", \"Nama_kabupaten\" " +
                " from pdpt_dev.\"TREF_KOTA\""; 
        kompletTextFieldCombo4.initDataUtkAutoComplete(query);        
        kodekota = kompletTextFieldCombo4.dapatkanTextField();
        kodekotaField = kompletTextFieldCombo4.dapatkanComboBox();                
        
        kompletTextFieldCombo5 = new KompletTextFieldCombo(kon, 
                kodeprovinsiField, kodeprovinsi);        
        query = "SELECT \"Kode_provinsi\", \"Nama_provinsi\" " +
                " FROM pdpt_dev.\"TREF_PROVINSI\" " +
                " WHERE \"Kode_negara\"='1'"; 
        kompletTextFieldCombo5.initDataUtkAutoComplete(query);        
        kodeprovinsi = kompletTextFieldCombo5.dapatkanTextField();
        kodeprovinsiField = kompletTextFieldCombo5.dapatkanComboBox();
        
        kompletTextFieldCombo6 = new KompletTextFieldCombo(kon, 
                kodenegaraField, kodenegara);        
        query = "SELECT \"Kode_negara\", \"Nama_negara\" " +
                " FROM pdpt_dev.\"TREF_NEGARA\""; 
        kompletTextFieldCombo6.initDataUtkAutoComplete(query);        
        kodenegara = kompletTextFieldCombo6.dapatkanTextField();
        kodenegaraField = kompletTextFieldCombo6.dapatkanComboBox();
        
        kompletTextFieldCombo7 = new KompletTextFieldCombo(kon, 
                statusmahasiswaField, statusmahasiswa);        
        query = "SELECT \"Kode_status_mhs\", \"Nama_status\" " +
                " FROM pdpt_dev.\"TREF_STATUS_MAHASISWA\""; 
        kompletTextFieldCombo7.initDataUtkAutoComplete(query);        
        statusmahasiswa = kompletTextFieldCombo7.dapatkanTextField();
        statusmahasiswaField = kompletTextFieldCombo7.dapatkanComboBox();
        
        kompletTextFieldCombo8 = new KompletTextFieldCombo(kon, 
                kodeprovasalpendidikanField, kodeprovasalpendidikan);        
        query = "SELECT \"Kode_provinsi\", \"Nama_provinsi\" " +
                " FROM pdpt_dev.\"TREF_PROVINSI\" " +
                " WHERE \"Kode_negara\"='1'"; 
        kompletTextFieldCombo8.initDataUtkAutoComplete(query);        
        kodeprovasalpendidikan = kompletTextFieldCombo8.dapatkanTextField();
        kodeprovasalpendidikanField = kompletTextFieldCombo8.dapatkanComboBox();
        
        kompletTextFieldCombo9 = new KompletTextFieldCombo(kon, 
                statusawalmahasiswaField, statusawalmahasiswa);        
        String [][] datanya9 = {{"B", "Baru"},{"P", "Pindahan"}}; 
        kompletTextFieldCombo9.initDataUtkAutoComplete(datanya9);
        statusawalmahasiswa = kompletTextFieldCombo9.dapatkanTextField();
        statusawalmahasiswaField = kompletTextFieldCombo9.dapatkanComboBox();
        
        kompletTextFieldCombo10 = new KompletTextFieldCombo(kon, 
                kodeperguruantinggiasalField, kodeperguruantinggiasal);        
        query = "SELECT \"Kode_perguruan_tinggi\", \"Nama_PT\" " +
                " FROM pdpt_dev.\"TMST_PERGURUAN_TINGGI\" where \"Kode_perguruan_tinggi\" = '"+kodept+"'"; 
        kompletTextFieldCombo10.initDataUtkAutoComplete(query);        
        kodeperguruantinggiasal = kompletTextFieldCombo10.dapatkanTextField();
        kodeperguruantinggiasalField = kompletTextFieldCombo10.dapatkanComboBox();
        
        kompletTextFieldCombo11 = new KompletTextFieldCombo(kon, 
                kodeprogramstudiasalField, kodeprogramstudiasal);        
        query = "select \"Kode_program_studi\", \"Nama_program_studi\" " +
                " from pdpt_dev.\"TMST_PROGRAM_STUDI\" where \"Kode_perguruan_tinggi\" = '"+kodept+"'";
        kompletTextFieldCombo11.initDataUtkAutoComplete(query);        
        kodeprogramstudiasal = kompletTextFieldCombo11.dapatkanTextField();
        kodeprogramstudiasalField = kompletTextFieldCombo11.dapatkanComboBox();
        
        kompletTextFieldCombo12 = new KompletTextFieldCombo(kon, 
                kodebeasiswaField, kodebeasiswa);        
        query = "select \"Kode_beasiswa\", \"Jenis_beasiswa\" " +
                " from pdpt_dev.\"TREF_BEASISWA\""; 
        kompletTextFieldCombo12.initDataUtkAutoComplete(query);        
        kodebeasiswa = kompletTextFieldCombo12.dapatkanTextField();
        kodebeasiswaField = kompletTextFieldCombo12.dapatkanComboBox();
        
        kompletTextFieldCombo13 = new KompletTextFieldCombo(kon, 
                kodejenjangpendidikansblmField, kodejenjangpendidikansblm);        
        query = "select \"Kode_jenjang_pendidikan\", \"Deskripsi\" " +
                " from pdpt_dev.\"TREF_JENJANG_PENDIDIKAN\""; 
        kompletTextFieldCombo13.initDataUtkAutoComplete(query);        
        kodejenjangpendidikansblm = kompletTextFieldCombo13.dapatkanTextField();
        kodejenjangpendidikansblmField = kompletTextFieldCombo13.dapatkanComboBox();
        
        kompletTextFieldCombo14 = new KompletTextFieldCombo(kon, 
                kodebiayastudiField, kodebiayastudi);        
        String [][] datanya14 = {{"A", "Biaya APBN"},{"B", "Biaya APBD"},
            {"C", "Biaya PTN/BHMN"},{"D", "Biaya PTS"},
            {"E","Institusi Luar Negeri"},{"F","Institusi Dalam Negeri"},
            {"G","Biaya Tempat Bekerja"},{"H","Biaya Sendiri"}}; 
        kompletTextFieldCombo14.initDataUtkAutoComplete(datanya14);
        kodebiayastudi = kompletTextFieldCombo14.dapatkanTextField();
        kodebiayastudiField = kompletTextFieldCombo14.dapatkanComboBox();
        
        kompletTextFieldCombo15 = new KompletTextFieldCombo(kon, 
                kodepekerjaanField, kodepekerjaan);        
        String [][] datanya15 = {{"A","Dosen PNS-PTN/BHMN"},
            {"B","Dosen Kontrak PTN/BHMN"},{"C","Dosen DPK PTS"},
            {"D", "Dosen PTS"},{"E","PNS Lembaga Pemerintah"},
            {"F","TNI/Polri"},{"G","Pegawai Swasta"},{"H","LSM"},
            {"I","Wiraswasta"},{"J","Belum Bekerja"}}; 
        kompletTextFieldCombo15.initDataUtkAutoComplete(datanya15);
        kodepekerjaan = kompletTextFieldCombo15.dapatkanTextField();
        kodepekerjaanField = kompletTextFieldCombo15.dapatkanComboBox();
        
        kompletTextFieldCombo16 = new KompletTextFieldCombo(kon, 
                kodePTbekerjaField, kodeptbekerja);        
        query = "SELECT \"Kode_perguruan_tinggi\", \"Nama_PT\" " +
                " FROM pdpt_dev.\"TMST_PERGURUAN_TINGGI\" where \"Kode_perguruan_tinggi\" = '"+kodept+"'";
        kompletTextFieldCombo16.initDataUtkAutoComplete(query);        
        kodeptbekerja = kompletTextFieldCombo16.dapatkanTextField();
        kodePTbekerjaField = kompletTextFieldCombo16.dapatkanComboBox();
        
        kompletTextFieldCombo17 = new KompletTextFieldCombo(kon, 
                kodePSbekerjaField, kodepsbekerja);        
        query = "select \"Kode_program_studi\", \"Nama_program_studi\" " +
                " from pdpt_dev.\"TMST_PROGRAM_STUDI\" where \"Kode_perguruan_tinggi\" = '"+kodept+"'";
        kompletTextFieldCombo17.initDataUtkAutoComplete(query);        
        kodepsbekerja = kompletTextFieldCombo17.dapatkanTextField();
        kodePSbekerjaField = kompletTextFieldCombo17.dapatkanComboBox();
        
        kompletTextFieldCombo18 = new KompletTextFieldCombo(kon, 
                jalurskripsiField, jalurskripsi);        
        String [][] datanya18 = {{"1","Tugas Akhir/Skripsi"},
                {"2","Student Project"},{"3","Tesis"},{"4", "Disertasi"}}; 
        kompletTextFieldCombo18.initDataUtkAutoComplete(datanya18);
        jalurskripsi = kompletTextFieldCombo18.dapatkanTextField();
        jalurskripsiField = kompletTextFieldCombo18.dapatkanComboBox();
                
        kompletTextFieldCombo19 = new KompletTextFieldCombo(kon, 
                kodekategoripenghasilanField, kodekategoripenghasilan);        
        query = "select \"Kode_kategori_penghasilan\", " + 
                " \"Range_penghasilan_min\", \"Range_penghasilan_max\" " +
                " from pdpt_dev.\"TREF_KATEGORI_PENGHASILAN\""; 
        kompletTextFieldCombo19.initDataUtkAutoComplete(query);        
        kodekategoripenghasilan = kompletTextFieldCombo19.dapatkanTextField();
        kodekategoripenghasilanField = kompletTextFieldCombo19.dapatkanComboBox();
    }
    
    private void inisialisasiData() {
        namaTabel = "pdpt_dev.\"TMST_MAHASISWA\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select * from " + namaTabel +" where \"Kode_perguruan_tinggi\" = '"+kodept+"'";
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public MasterMahasiswa(String [][] data, String [] kolom) {
        this();
        tampilkanDataKeTabel(data, kolom);
    }
    
    public void autoResizeColWidth() {

        int margin = 5;

        for (int i = 0; i < tabel.getColumnCount(); i++) {
            int                     vColIndex = i;
            DefaultTableColumnModel colModel  = (DefaultTableColumnModel) tabel.getColumnModel();
            TableColumn             col       = colModel.getColumn(vColIndex);
            int                     width     = 0;

            // Get width of column header
            TableCellRenderer renderer = col.getHeaderRenderer();

            if (renderer == null) {
                renderer = tabel.getTableHeader().getDefaultRenderer();
            }

            Component comp = renderer.getTableCellRendererComponent(tabel, col.getHeaderValue(), false, false, 0, 0);

            width = comp.getPreferredSize().width;

            // Get maximum width of column data
            for (int r = 0; r < tabel.getRowCount(); r++) {
                renderer = tabel.getCellRenderer(r, vColIndex);
                comp     = renderer.getTableCellRendererComponent(tabel, tabel.getValueAt(r, vColIndex), false, false,
                        r, vColIndex);
                width = Math.max(width, comp.getPreferredSize().width);
            }

            // Add margin
            width += 2 * margin;

            // Set the width
            col.setPreferredWidth(width);
        }

        ((DefaultTableCellRenderer) tabel.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(
            SwingConstants.LEFT);

        // table.setAutoCreateRowSorter(true);
        tabel.getTableHeader().setReorderingAllowed(false);

    }
    
    private void persiapanEntriDataBaru() {
        //kodeperguruantinggiField.setText("" + homePage.dapatkanKodePT());//utk pdpt pt
        kodeperguruantinggiField.setText("");//utk pdpt dikti
        kodeprogramstudi.setText("");
        NIMField.setText("");
        namamahasiswaField.setText("");
        kelasField.setText("");
        tempatlahirField.setText("");
        kodejenjangpendidikan.setText("");        
        tanggallahirField.setDate(null);
        jeniskelamin.setText("");
        alamatField.setText("");        
        kodekota.setText("");
        kodeprovinsi.setText("");
        kodeposField.setText("");
        kodenegara.setText("");
        statusmahasiswa.setText("");
        tahunmasukField.setText("");
        mulaisemesterField.setText("");
        batasstudiField.setText("");
        tanggalmasukField.setDate(null);
        tanggallulusField.setDate(null);
        tanggallahirField.setDate(null);
        IPKakhirField.setText("");
        nomorSKyudisiumField.setText("");
        tanggalSKyudisiumField.setDate(null);
        nomorseriijazahField.setText("");
        kodeprovasalpendidikan.setText("");
        statusawalmahasiswa.setText("");
        SKSdiakuiField.setText("");
        kodeperguruantinggiasal.setText("");
        kodeprogramstudiasal.setText("");
        kodebeasiswa.setText("");
        kodejenjangpendidikansblm.setText("");
        NIMasalField.setText("");
        kodebiayastudi.setText("");
        kodepekerjaan.setText("");
        namatempatbekerjaField.setText("");
        kodeptbekerja.setText("");
        kodepsbekerja.setText("");
        NIDNpromotorField.setText("");
        NIDNkopromotor1Field.setText("");
        NIDNkopromotor2Field.setText("");
        NIDNkopromotor3Field.setText("");
        NIDNkopromotor4Field.setText("");
        jalurskripsi.setText("");
        bulanawalbimbinganField.setText("");
        bulanakhirbimbinganField.setText("");
        nisnField.setText("");
        kodekategoripenghasilan.setText("");
        IDlogauditField.setText("");
        UUIDKopromotor4Field.setText("");
        UUIDKopromotor3Field.setText("");
        UUIDKopromotor2Field.setText("");
        UUIDKopromotor1Field.setText("");
        UUIDPromotorField.setText("");
        kodeperguruantinggiField.requestFocus();
    }
     
    public void tampilkanDataKeTabel(String [][] data, String [] kolom) {
        //kosongkan tabel terlebih dulu
        int row = tabel.getRowCount();
        for (int i = 0; i < row; i++) {
            ((DefaultTableModel) tabel.getModel()).removeRow(0);
            
        }
        
        //kosongkan kolom 
        ((DefaultTableModel) tabel.getModel()).setColumnCount(0);
        
        //isi tabel dgn data baru
        for (int b=0; b<kolom.length; b++) {
            ((DefaultTableModel)tabel.getModel()).addColumn(kolom[b]);
        }
        for (int a=0; a<data.length; a++)  {
            ((DefaultTableModel)tabel.getModel()).addRow(data[a]);
        }
        
        autoResizeColWidth();
    }
    
    private void tampilkanData(int row) {
        System.out.println("baris yg dipilih utk diUPDATE: "  + row);
        persiapanEntriDataBaru();
        kodeperguruantinggiField.setText(tabel.getValueAt(row, 0).toString());
        kodeprogramstudi.setText(tabel.getValueAt(row, 1).toString());
        NIMField.setText(tabel.getValueAt(row, 2).toString());
        namamahasiswaField.setText(tabel.getValueAt(row, 3).toString());
        kelasField.setText(tabel.getValueAt(row, 4).toString());
        tempatlahirField.setText(tabel.getValueAt(row, 5).toString());
        kodejenjangpendidikan.setText(tabel.getValueAt(row, 6).toString());
        
        String strKalendar = tabel.getValueAt(row, 7).toString();
        System.out.println("kalendar: " + strKalendar);
        System.out.println("tahun: " + strKalendar.substring(0, 4));
        System.out.println("bulan: " + strKalendar.substring(5, 7));
        System.out.println("hari: " + strKalendar.substring(8, 10));
        tahun = Integer.parseInt( strKalendar.substring(0, 4) );
        System.out.println("" + strKalendar.substring(0, 4)  + " tahun: " + tahun);
        int intBulan = Integer.parseInt( strKalendar.substring(5, 7) );
        System.out.println("" + strKalendar.substring(5, 7)  + " intBulan: " + intBulan);
        hari = Integer.parseInt( strKalendar.substring(8, 10) );
        System.out.println("" + strKalendar.substring(8, 10)  + " hari: " + hari);
        kalendar = Calendar.getInstance();
        kalendar.set(tahun, intBulan - 1, hari); 
        tanggallahirField.setCalendar(kalendar);        
        
        jeniskelamin.setText(tabel.getValueAt(row, 8).toString());
        alamatField.setText(tabel.getValueAt(row, 9).toString());
        kodekota.setText(tabel.getValueAt(row, 10).toString());
        kodeprovinsi.setText(tabel.getValueAt(row, 11).toString());
        kodeposField.setText(tabel.getValueAt(row, 12).toString());
        kodenegara.setText(tabel.getValueAt(row, 13).toString());
        statusawalmahasiswa.setText(tabel.getValueAt(row, 14).toString());
        tahunmasukField.setText(tabel.getValueAt(row, 15).toString());
        mulaisemesterField.setText(tabel.getValueAt(row, 16).toString());
        batasstudiField.setText(tabel.getValueAt(row, 17).toString());
        
        strKalendar = tabel.getValueAt(row, 18).toString();
        System.out.println("kalendar: " + strKalendar);
        System.out.println("tahun: " + strKalendar.substring(0, 4));
        System.out.println("bulan: " + strKalendar.substring(5, 7));
        System.out.println("hari: " + strKalendar.substring(8, 10));
        tahun = Integer.parseInt( strKalendar.substring(0, 4) );
        System.out.println("" + strKalendar.substring(0, 4)  + " tahun: " + tahun);
        intBulan = Integer.parseInt( strKalendar.substring(5, 7) );
        System.out.println("" + strKalendar.substring(5, 7)  + " intBulan: " + intBulan);
        hari = Integer.parseInt( strKalendar.substring(8, 10) );
        System.out.println("" + strKalendar.substring(8, 10)  + " hari: " + hari);
        kalendar = Calendar.getInstance();
        kalendar.set(tahun, intBulan - 1, hari); 
        tanggalmasukField.setCalendar(kalendar);
        
        strKalendar = tabel.getValueAt(row, 19).toString();
        System.out.println("kalendar: " + strKalendar);
        System.out.println("tahun: " + strKalendar.substring(0, 4));
        System.out.println("bulan: " + strKalendar.substring(5, 7));
        System.out.println("hari: " + strKalendar.substring(8, 10));
        tahun = Integer.parseInt( strKalendar.substring(0, 4) );
        System.out.println("" + strKalendar.substring(0, 4)  + " tahun: " + tahun);
        intBulan = Integer.parseInt( strKalendar.substring(5, 7) );
        System.out.println("" + strKalendar.substring(5, 7)  + " intBulan: " + intBulan);
        hari = Integer.parseInt( strKalendar.substring(8, 10) );
        System.out.println("" + strKalendar.substring(8, 10)  + " hari: " + hari);
        kalendar = Calendar.getInstance();
        kalendar.set(tahun, intBulan - 1, hari); 
        tanggallulusField.setCalendar(kalendar);
        
        IPKakhirField.setText(tabel.getValueAt(row, 20).toString());        
        
        nomorSKyudisiumField.setText(tabel.getValueAt(row, 21).toString());
        
        strKalendar = tabel.getValueAt(row, 22).toString();
        System.out.println("kalendar: " + strKalendar);
        System.out.println("tahun: " + strKalendar.substring(0, 4));
        System.out.println("bulan: " + strKalendar.substring(5, 7));
        System.out.println("hari: " + strKalendar.substring(8, 10));
        tahun = Integer.parseInt( strKalendar.substring(0, 4) );
        System.out.println("" + strKalendar.substring(0, 4)  + " tahun: " + tahun);
        intBulan = Integer.parseInt( strKalendar.substring(5, 7) );
        System.out.println("" + strKalendar.substring(5, 7)  + " intBulan: " + intBulan);
        hari = Integer.parseInt( strKalendar.substring(8, 10) );
        System.out.println("" + strKalendar.substring(8, 10)  + " hari: " + hari);
        kalendar = Calendar.getInstance();
        kalendar.set(tahun, intBulan - 1, hari); 
        tanggalSKyudisiumField.setCalendar(kalendar);
        
        nomorseriijazahField.setText(tabel.getValueAt(row, 23).toString());
        kodeprovasalpendidikan.setText(tabel.getValueAt(row, 24).toString());
        statusawalmahasiswa.setText(tabel.getValueAt(row, 25).toString());
        SKSdiakuiField.setText(tabel.getValueAt(row, 26).toString());
        kodeperguruantinggiasal.setText(tabel.getValueAt(row, 27).toString());
        kodeprogramstudiasal.setText(tabel.getValueAt(row, 28).toString());
        kodebeasiswa.setText(tabel.getValueAt(row, 29).toString());
        kodejenjangpendidikansblm.setText(tabel.getValueAt(row, 30).toString());
        NIMasalField.setText(tabel.getValueAt(row, 31).toString());
        kodebiayastudi.setText(tabel.getValueAt(row, 32).toString());
        kodepekerjaan.setText(tabel.getValueAt(row, 33).toString());
        namatempatbekerjaField.setText(tabel.getValueAt(row, 34).toString());
        kodeptbekerja.setText(tabel.getValueAt(row, 35).toString());
        kodepsbekerja.setText(tabel.getValueAt(row, 36).toString());
        NIDNpromotorField.setText(tabel.getValueAt(row, 37).toString());
        NIDNkopromotor1Field.setText(tabel.getValueAt(row, 38).toString());
        NIDNkopromotor2Field.setText(tabel.getValueAt(row, 39).toString());
        NIDNkopromotor3Field.setText(tabel.getValueAt(row, 40).toString());
        NIDNkopromotor4Field.setText(tabel.getValueAt(row, 41).toString());
        jalurskripsi.setText(tabel.getValueAt(row, 42).toString());
        judulskripsiField.setText(tabel.getValueAt(row, 43).toString());
        bulanawalbimbinganField.setText(tabel.getValueAt(row, 44).toString());
        bulanakhirbimbinganField.setText(tabel.getValueAt(row, 45).toString());
        nisnField.setText(tabel.getValueAt(row, 46).toString());
        kodekategoripenghasilan.setText(tabel.getValueAt(row, 47).toString());
        //IDlogauditField.setText(tabel.getValueAt(row, 48).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
        data[0] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[1] = datakodeprogramstudi[0];
        
        data[2] = (!NIMField.getText().equals("")?NIMField.getText():"0");
        data[3] = namamahasiswaField.getText();
        data[4] = kelasField.getText();
        data[5] = tempatlahirField.getText();
        
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[6] = datakodejenjangpendidikan[0];
        
        kalendar = tanggallahirField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data7 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[7] = (data7!=null?data7:"");
        
        String datajeniskelaminmentah = jeniskelamin.getText();
        System.out.println("data jeniskelamin mentah: " + datajeniskelaminmentah);
        String [] datajeniskelamin = datajeniskelaminmentah.split(" ");
        System.out.println("data jeniskelamin yg dipilih: " + datajeniskelamin[0]);
        data[8] = datajeniskelamin[0];
        
        data[9] = alamatField.getText();
        
        String datakodekotamentah = kodekota.getText();
        System.out.println("data kode kota mentah: " + datakodekotamentah);
        String [] datakodekota = datakodekotamentah.split(" ");
        System.out.println("data kode kota yg dipilih: " + datakodekota[0]);
        data[10] = datakodekota[0];
        
        String datakodeprovinsimentah = kodeprovinsi.getText();
        System.out.println("data kode provinsi mentah: " + datakodeprovinsimentah);
        String [] datakodeprovinsi = datakodeprovinsimentah.split(" ");
        System.out.println("data kode provinsi yg dipilih: " + datakodeprovinsi[0]);
        data[11] = datakodeprovinsi[0];
        
        data[12] = (!kodeposField.getText().equals("")?kodeposField.getText():"0");
        
        String datakodenegaramentah = kodenegara.getText();
        System.out.println("data kode negara mentah: " + datakodenegaramentah);
        String [] datakodenegara = datakodenegaramentah.split(" ");
        System.out.println("data kode negara yg dipilih: " + datakodenegara[0]);
        data[13] = datakodenegara[0];
        
        String datastatusmahasiswamentah = statusmahasiswa.getText();
        System.out.println("data statusmahasiswa mentah: " + datastatusmahasiswamentah);
        String [] datastatusmahasiswa = datastatusmahasiswamentah.split(" ");
        System.out.println("data statusmahasiswa yg dipilih: " + datastatusmahasiswa[0]);
        data[14] = datastatusmahasiswa[0];
        
        data[15] = (!tahunmasukField.getText().equals("    ")?tahunmasukField.getText():"0");
        data[16] = (!mulaisemesterField.getText().equals("     ")?mulaisemesterField.getText():"0");
        data[17] = (!batasstudiField.getText().equals("     ")?batasstudiField.getText():"0");
        
        kalendar = tanggalmasukField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data18 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[18] = (data18!=null?data18:"");
        
        kalendar = tanggallulusField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data19 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[19] = (data19!=null?data19:"");

        data[20] = (!IPKakhirField.getText().equals(" .  ")?IPKakhirField.getText():"0.00");
        data[21] = nomorSKyudisiumField.getText();
        
        kalendar = tanggalSKyudisiumField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data22 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[22] = (data22!=null?data22:"");        
        
        data[23] = nomorseriijazahField.getText();
        
        String datakodeprovasalpendidikanmentah = kodeprovasalpendidikan.getText();
        System.out.println("data kodeprovasalpendidikan mentah: " + datakodeprovasalpendidikanmentah);
        String [] datakodeprovasalpendidikan = datakodeprovasalpendidikanmentah.split(" ");
        System.out.println("data kodeprovasalpendidikan yg dipilih: " + datakodeprovasalpendidikan[0]);
        data[24] = datakodeprovasalpendidikan[0];
        
        String datastatusawalmahasiswamentah = statusawalmahasiswa.getText();
        System.out.println("data statusawalmahasiswa mentah: " + datastatusawalmahasiswamentah);
        String [] datastatusawalmahasiswa = datastatusawalmahasiswamentah.split(" ");
        System.out.println("data statusawalmahasiswa yg dipilih: " + datastatusawalmahasiswa[0]);
        data[25] = datastatusawalmahasiswa[0];
        
        data[26] = (!SKSdiakuiField.getText().equals("")?SKSdiakuiField.getText():"0");
        
        String datakodeperguruantinggiasalmentah = kodeperguruantinggiasal.getText();
        System.out.println("data kodeperguruantinggiasal mentah: " + datakodeperguruantinggiasalmentah);
        String [] datakodeperguruantinggiasal = datakodeperguruantinggiasalmentah.split(" ");
        System.out.println("data kodeperguruantinggiasal yg dipilih: " + datakodeperguruantinggiasal[0]);
        data[27] = datakodeperguruantinggiasal[0];
        
        String datakodeprogramstudiasalmentah = kodeprogramstudiasal.getText();
        System.out.println("data kodeprogramstudiasal mentah: " + datakodeprogramstudiasalmentah);
        String [] datakodeprogramstudiasal = datakodeprogramstudiasalmentah.split(" ");
        System.out.println("data kodeprogramstudiasal yg dipilih: " + datakodeprogramstudiasal[0]);
        data[28] = datakodeprogramstudiasal[0];
        
        String datakodebeasiswamentah = kodebeasiswa.getText();
        System.out.println("data kodebeasiswa mentah: " + datakodebeasiswamentah);
        String [] datakodebeasiswa = datakodebeasiswamentah.split(" ");
        System.out.println("data kodebeasiswa yg dipilih: " + datakodebeasiswa[0]);
        data[29] = datakodebeasiswa[0];
        
        String datakodejenjangpendidikansblmmentah = kodejenjangpendidikansblm.getText();
        System.out.println("data kode jenjangpendidikan sblm mentah: " + datakodejenjangpendidikansblmmentah);
        String [] datakodejenjangpendidikansblm = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan sblm yg dipilih: " + datakodejenjangpendidikansblm[0]);
        data[30] = datakodejenjangpendidikansblm[0];
        
        data[31] = (!NIMasalField.getText().equals("               ")?NIMasalField.getText():"0");
        
        String datakodebiayastudimentah = kodebiayastudi.getText();
        System.out.println("data kodebiayastudi sblm mentah: " + datakodebiayastudimentah);
        String [] datakodebiayastudi = datakodebiayastudimentah.split(" ");
        System.out.println("data kodebiayastudi sblm yg dipilih: " + datakodebiayastudi[0]);
        data[32] = datakodebiayastudi[0];
        
        String datakodepekerjaanmentah = kodepekerjaan.getText();
        System.out.println("data kodepekerjaan mentah: " + datakodepekerjaanmentah);
        String [] datakodepekerjaan = datakodepekerjaanmentah.split(" ");
        System.out.println("data kodepekerjaan yg dipilih: " + datakodepekerjaan[0]);
        data[33] = datakodepekerjaan[0];
        
        data[34] = namatempatbekerjaField.getText();
        
        String datakodeptbekerjamentah = kodeptbekerja.getText();
        System.out.println("data kodeptbekerja mentah: " + datakodeptbekerjamentah);
        String [] datakodeptbekerja = datakodeptbekerjamentah.split(" ");
        System.out.println("data kodeptbekerja yg dipilih: " + datakodeptbekerja[0]);
        data[35] = datakodeptbekerja[0];
        
        String datakodepsbekerjamentah = kodepsbekerja.getText();
        System.out.println("data kodepsbekerja mentah: " + datakodepsbekerjamentah);
        String [] datakodepsbekerja = datakodepsbekerjamentah.split(" ");
        System.out.println("data kodepsbekerja yg dipilih: " + datakodepsbekerja[0]);
        data[36] = datakodepsbekerja[0];
        
        data[37] = (!NIDNpromotorField.getText().equals("          ")?NIDNpromotorField.getText():"0");
        data[38] = (!NIDNkopromotor1Field.getText().equals("          ")?NIDNkopromotor1Field.getText():"0");
        data[39] = (!NIDNkopromotor2Field.getText().equals("          ")?NIDNkopromotor2Field.getText():"0");
        data[40] = (!NIDNkopromotor3Field.getText().equals("          ")?NIDNkopromotor3Field.getText():"0");
        data[41] = (!NIDNkopromotor4Field.getText().equals("          ")?NIDNkopromotor4Field.getText():"0");
        
        String datajalurskripsimentah = jalurskripsi.getText();
        System.out.println("data jalurskripsi mentah: " + datajalurskripsimentah);
        String [] datajalurskripsi = datajalurskripsimentah.split(" ");
        System.out.println("data jalurskripsi yg dipilih: " + datajalurskripsi[0]);
        data[42] = datajalurskripsi[0];
        
        data[43] = judulskripsiField.getText();
        data[44] = (!bulanawalbimbinganField.getText().equals("      ")?bulanawalbimbinganField.getText():"0");
        data[45] = (!bulanakhirbimbinganField.getText().equals("    ")?bulanakhirbimbinganField.getText():"0");
        data[46] = (!nisnField.getText().equals("          ")?nisnField.getText():"0");
        
        String datakodekategoripenghasilanmentah = kodekategoripenghasilan.getText();
        System.out.println("data kodekategoripenghasilan mentah: " + datakodekategoripenghasilanmentah);
        String [] datakodekategoripenghasilan = datakodekategoripenghasilanmentah.split(" ");
        System.out.println("data kodekategoripenghasilan yg dipilih: " + datakodekategoripenghasilan[0]);
        data[47] = datakodekategoripenghasilan[0];
        
        data[48] = "" + idlogaudit;
        data[49] = UUIDKopromotor4Field.getText();
        data[50] = UUIDKopromotor3Field.getText();
        data[51] = UUIDKopromotor2Field.getText();
        data[52] = UUIDKopromotor1Field.getText();
        data[53] = UUIDPromotorField.getText();
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];                        
        data[0] = namamahasiswaField.getText();
        data[1] = kelasField.getText();
        data[2] = tempatlahirField.getText();        
        
        kalendar = tanggallahirField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data6 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[3] = (data6!=null?data6:"");        
        
        String datajeniskelaminmentah = jeniskelamin.getText();
        System.out.println("data jeniskelamin mentah: " + datajeniskelaminmentah);
        String [] datajeniskelamin = datajeniskelaminmentah.split(" ");
        System.out.println("data jeniskelamin yg dipilih: " + datajeniskelamin[0]);
        data[4] = datajeniskelamin[0];
        
        data[5] = alamatField.getText();
        
        String datakodekotamentah = kodekota.getText();
        System.out.println("data kode kota mentah: " + datakodekotamentah);
        String [] datakodekota = datakodekotamentah.split(" ");
        System.out.println("data kode kota yg dipilih: " + datakodekota[0]);
        data[6] = datakodekota[0];
        
        String datakodeprovinsimentah = kodeprovinsi.getText();
        System.out.println("data kode provinsi mentah: " + datakodeprovinsimentah);
        String [] datakodeprovinsi = datakodeprovinsimentah.split(" ");
        System.out.println("data kode provinsi yg dipilih: " + datakodeprovinsi[0]);
        data[7] = datakodeprovinsi[0];
        
        data[8] = (!kodeposField.getText().equals("")?kodeposField.getText():"0");
        
        String datakodenegaramentah = kodenegara.getText();
        System.out.println("data kode negara mentah: " + datakodenegaramentah);
        String [] datakodenegara = datakodenegaramentah.split(" ");
        System.out.println("data kode negara yg dipilih: " + datakodenegara[0]);
        data[9] = datakodenegara[0];
        
        String datastatusmahasiswamentah = statusmahasiswa.getText();
        System.out.println("data statusmahasiswa mentah: " + datastatusmahasiswamentah);
        String [] datastatusmahasiswa = datastatusmahasiswamentah.split(" ");
        System.out.println("data statusmahasiswa yg dipilih: " + datastatusmahasiswa[0]);
        data[10] = datastatusmahasiswa[0];
        
        data[11] = (!tahunmasukField.getText().equals("    ")?tahunmasukField.getText():"0");
        
        data[12] = (!mulaisemesterField.getText().equals("     ")?mulaisemesterField.getText():"0");
        
        data[13] = (!batasstudiField.getText().equals("     ")?batasstudiField.getText():"0");
        
        kalendar = tanggalmasukField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data17 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[14] = (data17!=null?data17:"");        
        
        kalendar = tanggallulusField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data18 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[15] = (data18!=null?data18:"");        
        
        data[16] = (!IPKakhirField.getText().equals(" .  ")?IPKakhirField.getText():"0.00");
        data[17] = nomorSKyudisiumField.getText();        
        
        kalendar = tanggalSKyudisiumField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data21 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[18] = (data21!=null?data21:"");        
        
        data[19] = nomorseriijazahField.getText();        
        
        String datakodeprovasalpendidikanmentah = kodeprovasalpendidikan.getText();
        System.out.println("data kodeprovasalpendidikan mentah: " + datakodeprovasalpendidikanmentah);
        String [] datakodeprovasalpendidikan = datakodeprovasalpendidikanmentah.split(" ");
        System.out.println("data kodeprovasalpendidikan yg dipilih: " + datakodeprovasalpendidikan[0]);
        data[20] = datakodeprovasalpendidikan[0];
        
        String datastatusawalmahasiswamentah = statusawalmahasiswa.getText();
        System.out.println("data statusawalmahasiswa mentah: " + datastatusawalmahasiswamentah);
        String [] datastatusawalmahasiswa = datastatusawalmahasiswamentah.split(" ");
        System.out.println("data statusawalmahasiswa yg dipilih: " + datastatusawalmahasiswa[0]);
        data[21] = datastatusawalmahasiswa[0];
        
        data[22] = (!SKSdiakuiField.getText().equals("")?SKSdiakuiField.getText():"0");
        
        String datakodeperguruantinggiasalmentah = kodeperguruantinggiasal.getText();
        System.out.println("data kodeperguruantinggiasal mentah: " + datakodeperguruantinggiasalmentah);
        String [] datakodeperguruantinggiasal = datakodeperguruantinggiasalmentah.split(" ");
        System.out.println("data kodeperguruantinggiasal yg dipilih: " + datakodeperguruantinggiasal[0]);
        data[23] = datakodeperguruantinggiasal[0];
        
        String datakodeprogramstudiasalmentah = kodeprogramstudiasal.getText();
        System.out.println("data kodeprogramstudiasal mentah: " + datakodeprogramstudiasalmentah);
        String [] datakodeprogramstudiasal = datakodeprogramstudiasalmentah.split(" ");
        System.out.println("data kodeprogramstudiasal yg dipilih: " + datakodeprogramstudiasal[0]);
        data[24] = datakodeprogramstudiasal[0];
        
        String datakodebeasiswamentah = kodebeasiswa.getText();
        System.out.println("data kodebeasiswa mentah: " + datakodebeasiswamentah);
        String [] datakodebeasiswa = datakodebeasiswamentah.split(" ");
        System.out.println("data kodebeasiswa yg dipilih: " + datakodebeasiswa[0]);
        data[25] = datakodebeasiswa[0];
        
        String datakodejenjangpendidikansblmmentah = kodejenjangpendidikansblm.getText();
        System.out.println("data kode jenjangpendidikan sblm mentah: " + datakodejenjangpendidikansblmmentah);
        String [] datakodejenjangpendidikansblm = datakodejenjangpendidikansblmmentah.split(" ");
        System.out.println("data kode jenjangpendidikan sblm yg dipilih: " + datakodejenjangpendidikansblm[0]);
        data[26] = datakodejenjangpendidikansblm[0];
        data[27] = (!NIMasalField.getText().equals("")?NIMasalField.getText():"0");
        
        String datakodebiayastudimentah = kodebiayastudi.getText();
        System.out.println("data kodebiayastudi sblm mentah: " + datakodebiayastudimentah);
        String [] datakodebiayastudi = datakodebiayastudimentah.split(" ");
        System.out.println("data kodebiayastudi sblm yg dipilih: " + datakodebiayastudi[0]);
        data[28] = datakodebiayastudi[0];
        
        String datakodepekerjaanmentah = kodepekerjaan.getText();
        System.out.println("data kodepekerjaan mentah: " + datakodepekerjaanmentah);
        String [] datakodepekerjaan = datakodepekerjaanmentah.split(" ");
        System.out.println("data kodepekerjaan yg dipilih: " + datakodepekerjaan[0]);
        data[29] = datakodepekerjaan[0];
        
        data[30] = namatempatbekerjaField.getText();
        
        String datakodeptbekerjamentah = kodeptbekerja.getText();
        System.out.println("data kodeptbekerja mentah: " + datakodeptbekerjamentah);
        String [] datakodeptbekerja = datakodeptbekerjamentah.split(" ");
        System.out.println("data kodeptbekerja yg dipilih: " + datakodeptbekerja[0]);
        data[31] = datakodeptbekerja[0];
        
        String datakodepsbekerjamentah = kodepsbekerja.getText();
        System.out.println("data kodepsbekerja mentah: " + datakodepsbekerjamentah);
        String [] datakodepsbekerja = datakodepsbekerjamentah.split(" ");
        System.out.println("data kodepsbekerja yg dipilih: " + datakodepsbekerja[0]);
        data[32] = datakodepsbekerja[0];
        
        data[33] = (!NIDNpromotorField.getText().equals("          ")?NIDNpromotorField.getText():"0");
        data[34] = (!NIDNkopromotor1Field.getText().equals("          ")?NIDNkopromotor1Field.getText():"0");
        data[35] = (!NIDNkopromotor2Field.getText().equals("          ")?NIDNkopromotor2Field.getText():"0");
        data[36] = (!NIDNkopromotor3Field.getText().equals("          ")?NIDNkopromotor3Field.getText():"0");
        data[37] = (!NIDNkopromotor4Field.getText().equals("          ")?NIDNkopromotor4Field.getText():"0");
            
        String datajalurskripsimentah = jalurskripsi.getText();
        System.out.println("data jalurskripsi mentah: " + datajalurskripsimentah);
        String [] datajalurskripsi = datajalurskripsimentah.split(" ");
        System.out.println("data jalurskripsi yg dipilih: " + datajalurskripsi[0]);
        data[38] = datajalurskripsi[0];
        
        data[39] = judulskripsiField.getText();
        data[40] = (!bulanawalbimbinganField.getText().equals("      ")?bulanawalbimbinganField.getText():"0");
        data[41] = (!bulanakhirbimbinganField.getText().equals("    ")?bulanakhirbimbinganField.getText():"0");
        data[42] = (!nisnField.getText().equals("          ")?nisnField.getText():"0");
        
        String datakodekategoripenghasilanmentah = kodekategoripenghasilan.getText();
        System.out.println("data kodekategoripenghasilan mentah: " + datakodekategoripenghasilanmentah);
        String [] datakodekategoripenghasilan = datakodekategoripenghasilanmentah.split(" ");
        System.out.println("data kodekategoripenghasilan yg dipilih: " + datakodekategoripenghasilan[0]);
        data[43] = datakodekategoripenghasilan[0];
        
        data[44] = "" + idlogaudit;
        data[45] = UUIDKopromotor4Field.getText();
        data[46] = UUIDKopromotor3Field.getText();
        data[47] = UUIDKopromotor2Field.getText();
        data[48] = UUIDKopromotor1Field.getText();
        data[49] = UUIDPromotorField.getText();
        
        data[50] = (!NIMField.getText().equals("               ")?NIMField.getText():"0");
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[51] = datakodeprogramstudi[0];
        
        data[52] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");
        
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[53] = datakodejenjangpendidikan[0];
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[4];
        data[0] = NIMField.getText();
                
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[1] = datakodeprogramstudi[0];
        
        data[2] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");
        
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[3] = datakodejenjangpendidikan[0];
        return data;   
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane2 = new javax.swing.JSplitPane();
        gulungPanelAtribut = new javax.swing.JScrollPane();
        panelAtribut = new javax.swing.JPanel();
        panelLabel = new javax.swing.JPanel();
        kodeperguruantinggiLabel = new javax.swing.JLabel();
        kodeprogramstudiLabel = new javax.swing.JLabel();
        NIMLabel = new javax.swing.JLabel();
        namamahasiswaLabel = new javax.swing.JLabel();
        kelasLabel = new javax.swing.JLabel();
        tempatlahirLabel = new javax.swing.JLabel();
        kodejenjangpendidikanLabel = new javax.swing.JLabel();
        tanggallahirLabel = new javax.swing.JLabel();
        jeniskelaminLabel = new javax.swing.JLabel();
        alamatLabel = new javax.swing.JLabel();
        kodekotaLabel = new javax.swing.JLabel();
        kodeprovinsiLabel = new javax.swing.JLabel();
        kodeposLabel = new javax.swing.JLabel();
        kodenegaraLabel = new javax.swing.JLabel();
        statusmahasiswaLabel = new javax.swing.JLabel();
        tahunmasukLabel = new javax.swing.JLabel();
        mulaisemesterLabel = new javax.swing.JLabel();
        batasstudiLabel = new javax.swing.JLabel();
        tanggalmasukLabel = new javax.swing.JLabel();
        tanggallulusLabel = new javax.swing.JLabel();
        IPKakhirLabel = new javax.swing.JLabel();
        nomorSKyudisiumLabel = new javax.swing.JLabel();
        tanggalSKyudisiumLabel = new javax.swing.JLabel();
        nomorseriijazahLabel = new javax.swing.JLabel();
        kodeprovasalpendidikanLabel = new javax.swing.JLabel();
        statusawalmahasiswaLabel = new javax.swing.JLabel();
        SKSdiakuiLabel = new javax.swing.JLabel();
        kodeperguruantinggiasalLabel = new javax.swing.JLabel();
        kodeprogramstudiasalLabel = new javax.swing.JLabel();
        kodebeasiswaLabel = new javax.swing.JLabel();
        kodejenjangpendidikansblmLabel = new javax.swing.JLabel();
        NIMasalLabel = new javax.swing.JLabel();
        kodebiayastudiLabel = new javax.swing.JLabel();
        kodepekerjaanLabel = new javax.swing.JLabel();
        namatempatbekerjaLabel = new javax.swing.JLabel();
        kodePTbekerjaLabel = new javax.swing.JLabel();
        kodePSbekerjaLabel = new javax.swing.JLabel();
        NIDNpromotorLabel = new javax.swing.JLabel();
        NIDNkopromotor1Label = new javax.swing.JLabel();
        NIDNkopromotor2Label = new javax.swing.JLabel();
        NIDNkopromotor3Label = new javax.swing.JLabel();
        NIDNkopromotor4Label = new javax.swing.JLabel();
        jalurskripsiLabel = new javax.swing.JLabel();
        judulskripsiLabel = new javax.swing.JLabel();
        bulanawalbimbinganLabel = new javax.swing.JLabel();
        bulanakhirbimbinganLabel = new javax.swing.JLabel();
        nisnLabel = new javax.swing.JLabel();
        kodekategoripenghasilanLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        IDlogauditLabel1 = new javax.swing.JLabel();
        IDlogauditLabel2 = new javax.swing.JLabel();
        IDlogauditLabel3 = new javax.swing.JLabel();
        IDlogauditLabel4 = new javax.swing.JLabel();
        IDlogauditLabel5 = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodeperguruantinggiField = new javax.swing.JFormattedTextField();
        kodeprogramstudiField = new javax.swing.JComboBox();
        NIMField = new javax.swing.JFormattedTextField();
        namamahasiswaField = new javax.swing.JTextField();
        kelasField = new javax.swing.JTextField();
        tempatlahirField = new javax.swing.JTextField();
        kodejenjangpendidikanField = new javax.swing.JComboBox();
        tanggallahirField = new com.toedter.calendar.JDateChooser();
        jeniskelaminField = new javax.swing.JComboBox();
        alamatField = new javax.swing.JTextField();
        kodekotaField = new javax.swing.JComboBox();
        kodeprovinsiField = new javax.swing.JComboBox();
        kodeposField = new javax.swing.JFormattedTextField();
        kodenegaraField = new javax.swing.JComboBox();
        statusmahasiswaField = new javax.swing.JComboBox();
        tahunmasukField = new javax.swing.JFormattedTextField();
        mulaisemesterField = new javax.swing.JFormattedTextField();
        batasstudiField = new javax.swing.JFormattedTextField();
        tanggalmasukField = new com.toedter.calendar.JDateChooser();
        tanggallulusField = new com.toedter.calendar.JDateChooser();
        IPKakhirField = new javax.swing.JFormattedTextField();
        nomorSKyudisiumField = new javax.swing.JTextField();
        tanggalSKyudisiumField = new com.toedter.calendar.JDateChooser();
        nomorseriijazahField = new javax.swing.JTextField();
        kodeprovasalpendidikanField = new javax.swing.JComboBox();
        statusawalmahasiswaField = new javax.swing.JComboBox();
        SKSdiakuiField = new javax.swing.JFormattedTextField();
        kodeperguruantinggiasalField = new javax.swing.JComboBox();
        kodeprogramstudiasalField = new javax.swing.JComboBox();
        kodebeasiswaField = new javax.swing.JComboBox();
        kodejenjangpendidikansblmField = new javax.swing.JComboBox();
        NIMasalField = new javax.swing.JFormattedTextField();
        kodebiayastudiField = new javax.swing.JComboBox();
        kodepekerjaanField = new javax.swing.JComboBox();
        namatempatbekerjaField = new javax.swing.JTextField();
        kodePTbekerjaField = new javax.swing.JComboBox();
        kodePSbekerjaField = new javax.swing.JComboBox();
        NIDNpromotorField = new javax.swing.JFormattedTextField();
        NIDNkopromotor1Field = new javax.swing.JFormattedTextField();
        NIDNkopromotor2Field = new javax.swing.JFormattedTextField();
        NIDNkopromotor3Field = new javax.swing.JFormattedTextField();
        NIDNkopromotor4Field = new javax.swing.JFormattedTextField();
        jalurskripsiField = new javax.swing.JComboBox();
        judulskripsiField = new javax.swing.JTextField();
        bulanawalbimbinganField = new javax.swing.JFormattedTextField();
        bulanakhirbimbinganField = new javax.swing.JFormattedTextField();
        nisnField = new javax.swing.JFormattedTextField();
        kodekategoripenghasilanField = new javax.swing.JComboBox();
        IDlogauditField = new javax.swing.JFormattedTextField();
        UUIDKopromotor4Field = new javax.swing.JFormattedTextField();
        UUIDKopromotor3Field = new javax.swing.JFormattedTextField();
        UUIDKopromotor2Field = new javax.swing.JFormattedTextField();
        UUIDKopromotor1Field = new javax.swing.JFormattedTextField();
        UUIDPromotorField = new javax.swing.JFormattedTextField();
        gulungTabel = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        panelKontrol = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        panelKontrolSIUD = new javax.swing.JPanel();
        buttonBaru = new Widget.Button();
        buttonInsert = new Widget.Button();
        buttonUpdate = new Widget.Button();
        buttonDelete = new Widget.Button();
        panelKontrolNavigasiRecord = new javax.swing.JPanel();
        buttonFirst = new Widget.Button();
        buttonPrev = new Widget.Button();
        buttonNext = new Widget.Button();
        buttonLast = new Widget.Button();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jSplitPane2.setDividerLocation(700);
        jSplitPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        panelAtribut.setLayout(new java.awt.BorderLayout());

        panelLabel.setLayout(new java.awt.GridLayout(54, 0));

        kodeperguruantinggiLabel.setText("Kode Perguruan Tinggi:");
        panelLabel.add(kodeperguruantinggiLabel);

        kodeprogramstudiLabel.setText("Kode Program Studi:");
        panelLabel.add(kodeprogramstudiLabel);

        NIMLabel.setText("NIM Asal:");
        panelLabel.add(NIMLabel);

        namamahasiswaLabel.setText("Nama Mahasiswa:");
        panelLabel.add(namamahasiswaLabel);

        kelasLabel.setText("Kelas:");
        panelLabel.add(kelasLabel);

        tempatlahirLabel.setText("Tempat Lahir:");
        panelLabel.add(tempatlahirLabel);

        kodejenjangpendidikanLabel.setText("Kode Jenjang Pendidikan:");
        panelLabel.add(kodejenjangpendidikanLabel);

        tanggallahirLabel.setText("Tanggal Lahir:");
        panelLabel.add(tanggallahirLabel);

        jeniskelaminLabel.setText("Jenis Kelamin:");
        panelLabel.add(jeniskelaminLabel);

        alamatLabel.setText("Alamat:");
        panelLabel.add(alamatLabel);

        kodekotaLabel.setText("Kode Kota:");
        panelLabel.add(kodekotaLabel);

        kodeprovinsiLabel.setText("Kode Provinsi:");
        panelLabel.add(kodeprovinsiLabel);

        kodeposLabel.setText("Kode Pos:");
        panelLabel.add(kodeposLabel);

        kodenegaraLabel.setText("Kode Negara:");
        panelLabel.add(kodenegaraLabel);

        statusmahasiswaLabel.setText("Status Mahasiswa:");
        panelLabel.add(statusmahasiswaLabel);

        tahunmasukLabel.setText("Tahun Masuk:");
        panelLabel.add(tahunmasukLabel);

        mulaisemesterLabel.setText("Mulai Semester:");
        panelLabel.add(mulaisemesterLabel);

        batasstudiLabel.setText("Batas Studi:");
        panelLabel.add(batasstudiLabel);

        tanggalmasukLabel.setText("Tanggal Masuk:");
        panelLabel.add(tanggalmasukLabel);

        tanggallulusLabel.setText("Tanggal Lulus:");
        panelLabel.add(tanggallulusLabel);

        IPKakhirLabel.setText("IPK Akhir:");
        panelLabel.add(IPKakhirLabel);

        nomorSKyudisiumLabel.setText("Nomor SK Yudisium:");
        panelLabel.add(nomorSKyudisiumLabel);

        tanggalSKyudisiumLabel.setText("Tanggal SK Yudisium:");
        panelLabel.add(tanggalSKyudisiumLabel);

        nomorseriijazahLabel.setText("Nomor Seri Ijazah:");
        panelLabel.add(nomorseriijazahLabel);

        kodeprovasalpendidikanLabel.setText("Kode Prov Asal Pendidikan:");
        panelLabel.add(kodeprovasalpendidikanLabel);

        statusawalmahasiswaLabel.setText("Status Awal Mahasiswa:");
        panelLabel.add(statusawalmahasiswaLabel);

        SKSdiakuiLabel.setText("SKS Diakui:");
        panelLabel.add(SKSdiakuiLabel);

        kodeperguruantinggiasalLabel.setText("Kode Perguruan Tinggi Asal:");
        panelLabel.add(kodeperguruantinggiasalLabel);

        kodeprogramstudiasalLabel.setText("Kode Program Studi Asal:");
        panelLabel.add(kodeprogramstudiasalLabel);

        kodebeasiswaLabel.setText("Kode Beasiswa:");
        panelLabel.add(kodebeasiswaLabel);

        kodejenjangpendidikansblmLabel.setText("Kode Jenjang Pendidikan Sblm:");
        panelLabel.add(kodejenjangpendidikansblmLabel);

        NIMasalLabel.setText("NIM Asal:");
        panelLabel.add(NIMasalLabel);

        kodebiayastudiLabel.setText("Kode Biaya Studi:");
        panelLabel.add(kodebiayastudiLabel);

        kodepekerjaanLabel.setText("Kode Pekerjaan:");
        panelLabel.add(kodepekerjaanLabel);

        namatempatbekerjaLabel.setText("Nama Tempat Bekerja:");
        panelLabel.add(namatempatbekerjaLabel);

        kodePTbekerjaLabel.setText("Kode PT Bekerja:");
        panelLabel.add(kodePTbekerjaLabel);

        kodePSbekerjaLabel.setText("Kode PS Bekerja:");
        panelLabel.add(kodePSbekerjaLabel);

        NIDNpromotorLabel.setText("NIDN Promotor:");
        panelLabel.add(NIDNpromotorLabel);

        NIDNkopromotor1Label.setText("NIDN Ko Promotor1:");
        panelLabel.add(NIDNkopromotor1Label);

        NIDNkopromotor2Label.setText("NIDN Ko Promotor2:");
        panelLabel.add(NIDNkopromotor2Label);

        NIDNkopromotor3Label.setText("NIDN Ko Promotor3:");
        panelLabel.add(NIDNkopromotor3Label);

        NIDNkopromotor4Label.setText("NIDN Ko Promotor4:");
        panelLabel.add(NIDNkopromotor4Label);

        jalurskripsiLabel.setText("Jalur Skripsi:");
        panelLabel.add(jalurskripsiLabel);

        judulskripsiLabel.setText("Judul Skripsi:");
        panelLabel.add(judulskripsiLabel);

        bulanawalbimbinganLabel.setText("Bulan Awal Bimbingan:");
        panelLabel.add(bulanawalbimbinganLabel);

        bulanakhirbimbinganLabel.setText("Bulan Akhir Bimbingan:");
        panelLabel.add(bulanakhirbimbinganLabel);

        nisnLabel.setText("Nisn:");
        panelLabel.add(nisnLabel);

        kodekategoripenghasilanLabel.setText("Kode Kategori Penghasilan:");
        panelLabel.add(kodekategoripenghasilanLabel);

        IDlogauditLabel.setText("ID Log Audit:");
        panelLabel.add(IDlogauditLabel);

        IDlogauditLabel1.setText("UUID Kopromotor 4:");
        panelLabel.add(IDlogauditLabel1);

        IDlogauditLabel2.setText("UUID Kopromotor 3:");
        panelLabel.add(IDlogauditLabel2);

        IDlogauditLabel3.setText("UUID Kopromotor 2:");
        panelLabel.add(IDlogauditLabel3);

        IDlogauditLabel4.setText("UUID Kopromotor 1:");
        panelLabel.add(IDlogauditLabel4);

        IDlogauditLabel5.setText("UUID Promotor:");
        panelLabel.add(IDlogauditLabel5);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(54, 0));

        try {
            kodeperguruantinggiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodeperguruantinggiField);

        panelTextField.add(kodeprogramstudiField);

        NIMField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(NIMField);
        panelTextField.add(namamahasiswaField);
        panelTextField.add(kelasField);
        panelTextField.add(tempatlahirField);

        panelTextField.add(kodejenjangpendidikanField);
        panelTextField.add(tanggallahirField);

        panelTextField.add(jeniskelaminField);
        panelTextField.add(alamatField);

        panelTextField.add(kodekotaField);

        panelTextField.add(kodeprovinsiField);

        kodeposField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(kodeposField);

        panelTextField.add(kodenegaraField);

        panelTextField.add(statusmahasiswaField);

        try {
            tahunmasukField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(tahunmasukField);

        try {
            mulaisemesterField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(mulaisemesterField);

        try {
            batasstudiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(batasstudiField);
        panelTextField.add(tanggalmasukField);
        panelTextField.add(tanggallulusField);

        try {
            IPKakhirField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#.##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        IPKakhirField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IPKakhirFieldActionPerformed(evt);
            }
        });
        panelTextField.add(IPKakhirField);
        panelTextField.add(nomorSKyudisiumField);
        panelTextField.add(tanggalSKyudisiumField);
        panelTextField.add(nomorseriijazahField);

        panelTextField.add(kodeprovasalpendidikanField);

        panelTextField.add(statusawalmahasiswaField);

        try {
            SKSdiakuiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(SKSdiakuiField);

        panelTextField.add(kodeperguruantinggiasalField);

        panelTextField.add(kodeprogramstudiasalField);

        panelTextField.add(kodebeasiswaField);

        panelTextField.add(kodejenjangpendidikansblmField);

        NIMasalField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(NIMasalField);

        panelTextField.add(kodebiayastudiField);

        panelTextField.add(kodepekerjaanField);
        panelTextField.add(namatempatbekerjaField);

        panelTextField.add(kodePTbekerjaField);

        panelTextField.add(kodePSbekerjaField);

        try {
            NIDNpromotorField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(NIDNpromotorField);

        try {
            NIDNkopromotor1Field.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(NIDNkopromotor1Field);

        try {
            NIDNkopromotor2Field.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(NIDNkopromotor2Field);

        try {
            NIDNkopromotor3Field.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(NIDNkopromotor3Field);

        try {
            NIDNkopromotor4Field.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(NIDNkopromotor4Field);

        panelTextField.add(jalurskripsiField);
        panelTextField.add(judulskripsiField);

        try {
            bulanawalbimbinganField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(bulanawalbimbinganField);

        try {
            bulanakhirbimbinganField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(bulanakhirbimbinganField);

        try {
            nisnField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(nisnField);

        panelTextField.add(kodekategoripenghasilanField);

        IDlogauditField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(IDlogauditField);

        UUIDKopromotor4Field.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(UUIDKopromotor4Field);

        UUIDKopromotor3Field.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(UUIDKopromotor3Field);

        UUIDKopromotor2Field.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        UUIDKopromotor2Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UUIDKopromotor2FieldActionPerformed(evt);
            }
        });
        panelTextField.add(UUIDKopromotor2Field);

        UUIDKopromotor1Field.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(UUIDKopromotor1Field);

        UUIDPromotorField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(UUIDPromotorField);

        panelAtribut.add(panelTextField, java.awt.BorderLayout.CENTER);

        gulungPanelAtribut.setViewportView(panelAtribut);

        jSplitPane2.setLeftComponent(gulungPanelAtribut);
        gulungPanelAtribut.getAccessibleContext().setAccessibleParent(jSplitPane2);

        gulungTabel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        gulungTabel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        gulungTabel.setOpaque(false);

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabel.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabel.setEnabled(false);
        gulungTabel.setViewportView(tabel);

        jSplitPane2.setRightComponent(gulungTabel);

        add(jSplitPane2, java.awt.BorderLayout.CENTER);

        panelKontrol.setOpaque(false);
        panelKontrol.setLayout(new java.awt.GridLayout(1, 1));

        jSplitPane1.setDividerLocation(700);

        panelKontrolSIUD.setLayout(new java.awt.GridLayout(1, 4));

        buttonBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Empty-Page-64.png"))); // NOI18N
        buttonBaru.setText("Baru");
        buttonBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBaruActionPerformed(evt);
            }
        });
        panelKontrolSIUD.add(buttonBaru);

        buttonInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Insert documents-64.png"))); // NOI18N
        buttonInsert.setText("Tambah");
        buttonInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonInsertActionPerformed(evt);
            }
        });
        panelKontrolSIUD.add(buttonInsert);

        buttonUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Update Document-64.png"))); // NOI18N
        buttonUpdate.setText("Perbarui");
        buttonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUpdateActionPerformed(evt);
            }
        });
        panelKontrolSIUD.add(buttonUpdate);

        buttonDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Delete Document Grey-64.png"))); // NOI18N
        buttonDelete.setText("Hapus");
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });
        panelKontrolSIUD.add(buttonDelete);

        jSplitPane1.setLeftComponent(panelKontrolSIUD);

        panelKontrolNavigasiRecord.setLayout(new java.awt.GridLayout(1, 4));

        buttonFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/First64.png"))); // NOI18N
        buttonFirst.setText("Awal");
        buttonFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFirstActionPerformed(evt);
            }
        });
        panelKontrolNavigasiRecord.add(buttonFirst);

        buttonPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/prev_mini.png"))); // NOI18N
        buttonPrev.setText("Sebelumnya");
        buttonPrev.setIconTextGap(0);
        buttonPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPrevActionPerformed(evt);
            }
        });
        panelKontrolNavigasiRecord.add(buttonPrev);

        buttonNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/next_mini.png"))); // NOI18N
        buttonNext.setText("Berikutnya");
        buttonNext.setIconTextGap(0);
        buttonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });
        panelKontrolNavigasiRecord.add(buttonNext);

        buttonLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Last64.png"))); // NOI18N
        buttonLast.setText("Akhir");
        buttonLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLastActionPerformed(evt);
            }
        });
        panelKontrolNavigasiRecord.add(buttonLast);

        jSplitPane1.setRightComponent(panelKontrolNavigasiRecord);

        panelKontrol.add(jSplitPane1);

        add(panelKontrol, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBaruActionPerformed
        // TODO add your handling code here:
        persiapanEntriDataBaru();
    }//GEN-LAST:event_buttonBaruActionPerformed

    private void buttonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInsertActionPerformed
        // TODO add your handling code here:
        initIDLogAudit();
        String query = "insert into " + namaTabel + " " +
        " values(?,?,?,?,?,?,?,?,?,?, " +
                "?,?,?,?,?,?,?,?,?,?, " +
                "?,?,?,?,?,?,?,?,?,?, " +
                "?,?,?,?,?,?,?,?,?,?, " +        
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukInsert() );
         int hasil = model.insertAktifitasDOsen(query,
            dapatkanNilaiUntukInsert() );
        if (hasil == 1) {
            query = "select * from " + namaTabel+" where \"Kode_perguruan_tinggi\" = "+kodept;
            kon.selectData(query);
            tampilkanDataKeTabel(kon.dapatkanData(), ubahLabelKeSentenceCase(kon.dapatkanKolom()));
            
            //insert data ke TREF_LOG_AUDIT
            query = "insert into pdpt_dev.\"TREF_LOG_AUDIT\" " +
                    " values(?,?,?,?,?,?)";
            hasil = kon.insertUpdateDeleteData(query,
                            dapatkanNilaiUntukInsertKeLogAudit("INSERT", 
                                namaTabel, 
                                kon.dapatkanQueryUtkStatement(namaTabel, 
                                    dapatkanNilaiUntukInsert())) );
        }
    }//GEN-LAST:event_buttonInsertActionPerformed

    private void buttonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUpdateActionPerformed
        // TODO add your handling code here:
        initIDLogAudit();
        String query = "update " + namaTabel + " " +
        " SET " +         
        "\"Nama_mahasiswa\"=?, " +
        "\"Kelas\"=?, " +
        "\"Tempat_lahir\"=?, " +        
        "\"Tanggal_lahir\"=?, " +
        "\"Jenis_kelamin\"=?, " +
        "\"Alamat\"=?, " +
        "\"Kode_kota\"=?, " +
        "\"Kode_provinsi\"=?, " +
        "\"Kode_pos\"=?, " +
        "\"Kode_negara\"=?, " +
        "\"Status_mahasiswa\"=?, " +
        "\"Tahun_masuk\"=?, " +
        "\"Mulai_semester\"=?, " +
        "\"Batas_studi\"=?, " +
        "\"Tanggal_masuk\"=?, " +
        "\"Tanggal_lulus\"=?, " +
        "\"IPK_akhir\"=?, " +
        "\"Nomor_SK_yudisium\"=?, " +
        "\"Tanggal_SK_yudisium\"=?, " +
        "\"Nomor_seri_ijazah\"=?, " +
        "\"Kode_prov_asal_pendidikan\"=?, " +
        "\"Status_awal_mahasiswa\"=?, " +
        "\"SKS_diakui\"=?, " +
        "\"Kode_perguruan_tinggi_asal\"=?, " +
        "\"Kode_program_studi_asal\"=?, " +
        "\"Kode_beasiswa\"=?, " +
        "\"Kode_jenjang_pendidikan_sblm\"=?, " +
        "\"NIM_asal\"=?, " +
        "\"Kode_biaya_studi\"=?, " +
        "\"Kode_pekerjaan\"=?, " +
        "\"Nama_tempat_bekerja\"=?, " +
        "\"Kode_PT_bekerja\"=?, " +        
        "\"Kode_PS_bekerja\"=?, " +        
        "\"NIDN_promotor\"=?, " +
        "\"NIDN_kopromotor 1\"=?, " +        
        "\"NIDN_kopromotor 2\"=?, " +                
        "\"NIDN_kopromotor 3\"=?, " +        
        "\"NIDN_kopromotor 4\"=?, " +        
        "\"Jalur_skripsi\"=?, " +                
        "\"Judul_skripsi\"=?, " +                
        "\"Bulan_awal_bimbingan\"=?, " +                
        "\"Bulan_akhir_bimbingan\"=?, " +                
        "\"NISN\"=?, " +
        "\"Kode_kategori_penghasilan\"=?, " +        
        "\"ID_log_audit\"=?, " +
        "\"UUID_Kopromotor4\"=?, " +
        "\"UUID_Kopromotor3\"=?, " +
        "\"UUID_Kopromotor2\"=?, " +
        "\"UUID_Kopromotor1\"=?, " +
        "\"UUID_Promotor=?\" " +
        " WHERE " +
        " \"NIM\"=? AND " + 
        " \"Kode_program_studi\"=? AND " + 
        " \"Kode_perguruan_tinggi\"=? AND " +         
        " \"Kode_jenjang_pendidikan\"=? ";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukUpdate() );
         int hasil = model.updateAktifitasDOsen(query,
            dapatkanNilaiUntukUpdate() );
        if (hasil == 1) {
            query = "select * from " + namaTabel+" where \"Kode_perguruan_tinggi\" = "+kodept;
            kon.selectData(query);
            tampilkanDataKeTabel(kon.dapatkanData(), ubahLabelKeSentenceCase(kon.dapatkanKolom()));
            
            //insert data ke TREF_LOG_AUDIT
            query = "insert into pdpt_dev.\"TREF_LOG_AUDIT\" " +
                    " values(?,?,?,?,?,?)";
            hasil = kon.insertUpdateDeleteData(query,
                            dapatkanNilaiUntukInsertKeLogAudit("UPDATE", 
                                namaTabel, 
                                kon.dapatkanQueryUtkUpdateStatement(namaTabel, 
                                    dapatkanNilaiUntukUpdate())) );
        }
    }//GEN-LAST:event_buttonUpdateActionPerformed

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        // TODO add your handling code here:
        initIDLogAudit();
        String query = "delete from " + namaTabel + " WHERE " +
        " NIM=? AND " + 
        " Kode_program_studi=? AND " + 
        " Kode_perguruan_tinggi=? AND " +         
        " Kode_jenjang_pendidikan=? ";
        
        String [] pkDataYgDihapus = dapatkanNilaiUntukDelete();

        int hasil = model.delete(query,
            dapatkanNilaiUntukDelete() );
        if (hasil == 1) {
            query = "select * from " + namaTabel+" where \"Kode_perguruan_tinggi\" = "+kodept;
            kon.selectData(query);
            tampilkanDataKeTabel(kon.dapatkanData(), ubahLabelKeSentenceCase(kon.dapatkanKolom()));
            persiapanEntriDataBaru();
            
            //insert data ke TREF_LOG_AUDIT
            query = "insert into pdpt_dev.\"TREF_LOG_AUDIT\" " +
                    " values(?,?,?,?,?,?)";
            hasil = kon.insertUpdateDeleteData(query,
                            dapatkanNilaiUntukInsertKeLogAudit("DELETE", 
                                namaTabel, 
                                kon.dapatkanQueryUtkDeleteStatement(namaTabel, 
                                    pkDataYgDihapus)) );
        }
    }//GEN-LAST:event_buttonDeleteActionPerformed

    private void buttonFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFirstActionPerformed
        // TODO add your handling code here:
        barisYgDipilih = 0;
        tabel.setRowSelectionInterval(barisYgDipilih, barisYgDipilih);
        tampilkanData(barisYgDipilih);
    }//GEN-LAST:event_buttonFirstActionPerformed

    private void buttonPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPrevActionPerformed
        // TODO add your handling code here:
        if (barisYgDipilih > 0 ) {
            barisYgDipilih--;
            tabel.setRowSelectionInterval(barisYgDipilih, barisYgDipilih);
            tampilkanData(barisYgDipilih);
        }
    }//GEN-LAST:event_buttonPrevActionPerformed

    private void buttonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextActionPerformed
        // TODO add your handling code here:
        if (barisYgDipilih < tabel.getRowCount() - 1) {
            barisYgDipilih++;
            tabel.setRowSelectionInterval(barisYgDipilih, barisYgDipilih);
            tampilkanData(barisYgDipilih);
        }
    }//GEN-LAST:event_buttonNextActionPerformed

    private void buttonLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLastActionPerformed
        // TODO add your handling code here:
        barisYgDipilih = tabel.getRowCount() - 1;
        tabel.setRowSelectionInterval(barisYgDipilih, barisYgDipilih);
        tampilkanData(barisYgDipilih);
    }//GEN-LAST:event_buttonLastActionPerformed

    private void UUIDKopromotor2FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UUIDKopromotor2FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UUIDKopromotor2FieldActionPerformed

    private void IPKakhirFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IPKakhirFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IPKakhirFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField IDlogauditField;
    private javax.swing.JLabel IDlogauditLabel;
    private javax.swing.JLabel IDlogauditLabel1;
    private javax.swing.JLabel IDlogauditLabel2;
    private javax.swing.JLabel IDlogauditLabel3;
    private javax.swing.JLabel IDlogauditLabel4;
    private javax.swing.JLabel IDlogauditLabel5;
    private javax.swing.JFormattedTextField IPKakhirField;
    private javax.swing.JLabel IPKakhirLabel;
    private javax.swing.JFormattedTextField NIDNkopromotor1Field;
    private javax.swing.JLabel NIDNkopromotor1Label;
    private javax.swing.JFormattedTextField NIDNkopromotor2Field;
    private javax.swing.JLabel NIDNkopromotor2Label;
    private javax.swing.JFormattedTextField NIDNkopromotor3Field;
    private javax.swing.JLabel NIDNkopromotor3Label;
    private javax.swing.JFormattedTextField NIDNkopromotor4Field;
    private javax.swing.JLabel NIDNkopromotor4Label;
    private javax.swing.JFormattedTextField NIDNpromotorField;
    private javax.swing.JLabel NIDNpromotorLabel;
    private javax.swing.JFormattedTextField NIMField;
    private javax.swing.JLabel NIMLabel;
    private javax.swing.JFormattedTextField NIMasalField;
    private javax.swing.JLabel NIMasalLabel;
    private javax.swing.JFormattedTextField SKSdiakuiField;
    private javax.swing.JLabel SKSdiakuiLabel;
    private javax.swing.JFormattedTextField UUIDKopromotor1Field;
    private javax.swing.JFormattedTextField UUIDKopromotor2Field;
    private javax.swing.JFormattedTextField UUIDKopromotor3Field;
    private javax.swing.JFormattedTextField UUIDKopromotor4Field;
    private javax.swing.JFormattedTextField UUIDPromotorField;
    private javax.swing.JTextField alamatField;
    private javax.swing.JLabel alamatLabel;
    private javax.swing.JFormattedTextField batasstudiField;
    private javax.swing.JLabel batasstudiLabel;
    private javax.swing.JFormattedTextField bulanakhirbimbinganField;
    private javax.swing.JLabel bulanakhirbimbinganLabel;
    private javax.swing.JFormattedTextField bulanawalbimbinganField;
    private javax.swing.JLabel bulanawalbimbinganLabel;
    private Widget.Button buttonBaru;
    private Widget.Button buttonDelete;
    private Widget.Button buttonFirst;
    private Widget.Button buttonInsert;
    private Widget.Button buttonLast;
    private Widget.Button buttonNext;
    private Widget.Button buttonPrev;
    private Widget.Button buttonUpdate;
    private javax.swing.JScrollPane gulungPanelAtribut;
    private javax.swing.JScrollPane gulungTabel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JComboBox jalurskripsiField;
    private javax.swing.JLabel jalurskripsiLabel;
    private javax.swing.JComboBox jeniskelaminField;
    private javax.swing.JLabel jeniskelaminLabel;
    private javax.swing.JTextField judulskripsiField;
    private javax.swing.JLabel judulskripsiLabel;
    private javax.swing.JTextField kelasField;
    private javax.swing.JLabel kelasLabel;
    private javax.swing.JComboBox kodePSbekerjaField;
    private javax.swing.JLabel kodePSbekerjaLabel;
    private javax.swing.JComboBox kodePTbekerjaField;
    private javax.swing.JLabel kodePTbekerjaLabel;
    private javax.swing.JComboBox kodebeasiswaField;
    private javax.swing.JLabel kodebeasiswaLabel;
    private javax.swing.JComboBox kodebiayastudiField;
    private javax.swing.JLabel kodebiayastudiLabel;
    private javax.swing.JComboBox kodejenjangpendidikanField;
    private javax.swing.JLabel kodejenjangpendidikanLabel;
    private javax.swing.JComboBox kodejenjangpendidikansblmField;
    private javax.swing.JLabel kodejenjangpendidikansblmLabel;
    private javax.swing.JComboBox kodekategoripenghasilanField;
    private javax.swing.JLabel kodekategoripenghasilanLabel;
    private javax.swing.JComboBox kodekotaField;
    private javax.swing.JLabel kodekotaLabel;
    private javax.swing.JComboBox kodenegaraField;
    private javax.swing.JLabel kodenegaraLabel;
    private javax.swing.JComboBox kodepekerjaanField;
    private javax.swing.JLabel kodepekerjaanLabel;
    private javax.swing.JFormattedTextField kodeperguruantinggiField;
    private javax.swing.JLabel kodeperguruantinggiLabel;
    private javax.swing.JComboBox kodeperguruantinggiasalField;
    private javax.swing.JLabel kodeperguruantinggiasalLabel;
    private javax.swing.JFormattedTextField kodeposField;
    private javax.swing.JLabel kodeposLabel;
    private javax.swing.JComboBox kodeprogramstudiField;
    private javax.swing.JLabel kodeprogramstudiLabel;
    private javax.swing.JComboBox kodeprogramstudiasalField;
    private javax.swing.JLabel kodeprogramstudiasalLabel;
    private javax.swing.JComboBox kodeprovasalpendidikanField;
    private javax.swing.JLabel kodeprovasalpendidikanLabel;
    private javax.swing.JComboBox kodeprovinsiField;
    private javax.swing.JLabel kodeprovinsiLabel;
    private javax.swing.JFormattedTextField mulaisemesterField;
    private javax.swing.JLabel mulaisemesterLabel;
    private javax.swing.JTextField namamahasiswaField;
    private javax.swing.JLabel namamahasiswaLabel;
    private javax.swing.JTextField namatempatbekerjaField;
    private javax.swing.JLabel namatempatbekerjaLabel;
    private javax.swing.JFormattedTextField nisnField;
    private javax.swing.JLabel nisnLabel;
    private javax.swing.JTextField nomorSKyudisiumField;
    private javax.swing.JLabel nomorSKyudisiumLabel;
    private javax.swing.JTextField nomorseriijazahField;
    private javax.swing.JLabel nomorseriijazahLabel;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JComboBox statusawalmahasiswaField;
    private javax.swing.JLabel statusawalmahasiswaLabel;
    private javax.swing.JComboBox statusmahasiswaField;
    private javax.swing.JLabel statusmahasiswaLabel;
    private javax.swing.JTable tabel;
    private javax.swing.JFormattedTextField tahunmasukField;
    private javax.swing.JLabel tahunmasukLabel;
    private com.toedter.calendar.JDateChooser tanggalSKyudisiumField;
    private javax.swing.JLabel tanggalSKyudisiumLabel;
    private com.toedter.calendar.JDateChooser tanggallahirField;
    private javax.swing.JLabel tanggallahirLabel;
    private com.toedter.calendar.JDateChooser tanggallulusField;
    private javax.swing.JLabel tanggallulusLabel;
    private com.toedter.calendar.JDateChooser tanggalmasukField;
    private javax.swing.JLabel tanggalmasukLabel;
    private javax.swing.JTextField tempatlahirField;
    private javax.swing.JLabel tempatlahirLabel;
    // End of variables declaration//GEN-END:variables
}
