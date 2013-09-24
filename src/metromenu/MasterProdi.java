/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelMasterProdi;
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
public class MasterProdi extends javax.swing.JPanel {
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
    Model.ModelMasterProdi model;
    private JTextField kodefakultas;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo1;
    private JTextField kodejenjangpendidikan;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;    
    private JTextField kodekota;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    private JTextField kodeprovinsi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo4;
    private JTextField kodenegara;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo5;
    private JTextField bidangilmu;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo6;
    private JTextField statusprogramstudi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo7;
    private JTextField frekuensikurikulum;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo8;
    private JTextField pelaksanaankurikulum;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo9;
    private JTextField kodeakreditasi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo10;    
    private JTextField statusvalidasi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo11;
    private JTextField kodejurusan;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo12;
    
    private int idlogaudit;
    private String kueri,kodept;

    /**
     * Creates new form PanelHalaman1
     */
    public MasterProdi() {    
        initComponents();
    }
    
    public MasterProdi(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        kodept = homePage.dapatkanKodePT();
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        model = new ModelMasterProdi();
        kodeperguruantinggiField.setText(kodept);
        inisialisasiData();
        initAutoComplete();
        buttonBaru.setEnabled(false);
        buttonInsert.setEnabled(false);
        buttonDelete.setEnabled(false);
        buttonUpdate.setEnabled(false);
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
                kodefakultasField, kodefakultas);        
        query = "SELECT \"Kode_fakultas\", \"Nama_fakultas\" " +
                " from pdpt_dev.\"TMST_FAKULTAS\""; 
        kompletTextFieldCombo1.initDataUtkAutoComplete(query);        
        kodefakultas = kompletTextFieldCombo1.dapatkanTextField();
        kodefakultasField = kompletTextFieldCombo1.dapatkanComboBox();        
        
        kompletTextFieldCombo2 = new KompletTextFieldCombo(kon, 
                kodejenjangpendidikanField, kodejenjangpendidikan);        
        query = "select \"Kode_jenjang_pendidikan\", \"Deskripsi\" " +
                " from pdpt_dev.\"TREF_JENJANG_PENDIDIKAN\""; 
        kompletTextFieldCombo2.initDataUtkAutoComplete(query);        
        kodejenjangpendidikan = kompletTextFieldCombo2.dapatkanTextField();
        kodejenjangpendidikanField = kompletTextFieldCombo2.dapatkanComboBox();
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                kodekotaField, kodekota);        
        query = "SELECT \"Kode_kota\", \"Nama_kabupaten\" " +
                " from pdpt_dev.\"TREF_KOTA\""; 
        kompletTextFieldCombo3.initDataUtkAutoComplete(query);        
        kodekota = kompletTextFieldCombo3.dapatkanTextField();
        kodekotaField = kompletTextFieldCombo3.dapatkanComboBox();                
        
        kompletTextFieldCombo4 = new KompletTextFieldCombo(kon, 
                kodeprovinsiField, kodeprovinsi);        
        query = "SELECT \"Kode_provinsi\", \"Nama_provinsi\" " +
                " FROM pdpt_dev.\"TREF_PROVINSI\" " +
                " WHERE \"Kode_negara\"='1'"; 
        kompletTextFieldCombo4.initDataUtkAutoComplete(query);        
        kodeprovinsi = kompletTextFieldCombo4.dapatkanTextField();
        kodeprovinsiField = kompletTextFieldCombo4.dapatkanComboBox();
        
        kompletTextFieldCombo5 = new KompletTextFieldCombo(kon, 
                kodenegaraField, kodenegara);        
        query = "SELECT \"Kode_negara\", \"Nama_negara\" " +
                " FROM pdpt_dev.\"TREF_NEGARA\""; 
        kompletTextFieldCombo5.initDataUtkAutoComplete(query);        
        kodenegara = kompletTextFieldCombo5.dapatkanTextField();
        kodenegaraField = kompletTextFieldCombo5.dapatkanComboBox();
        
        kompletTextFieldCombo6 = new KompletTextFieldCombo(kon, 
                bidangilmuField, bidangilmu);        
        String [][] datanya6 = {{"A", "Kesehatan"},{"B","Teknik"},{"C","MIPA"},
            {"D","Pertanian"},{"E","Komputer"},{"F","Ekonomi"},{"G","Sosial"},
            {"H","Psikologi"},{"I","Hukum"},{"J","Agama dan Filsafat"},
            {"K","Budaya dan Sastra"},{"L","Pendidikan"},{"M","Seni"},
            {"N","Aneka Ilmu"},{"O","Kelautan dan Perikanan"},{"P","Peternakan"}}; 
        kompletTextFieldCombo6.initDataUtkAutoComplete(datanya6);  
        bidangilmu = kompletTextFieldCombo6.dapatkanTextField();
        bidangilmuField = kompletTextFieldCombo6.dapatkanComboBox();        
        
        kompletTextFieldCombo7 = new KompletTextFieldCombo(kon, 
                statusprogramstudiField, statusprogramstudi);        
        String [][] datanya7 = {{"A", "Aktif"},{"H","Hapus/tutup"}}; 
        kompletTextFieldCombo7.initDataUtkAutoComplete(datanya7);  
        statusprogramstudi = kompletTextFieldCombo7.dapatkanTextField();
        statusprogramstudiField = kompletTextFieldCombo7.dapatkanComboBox();
        
        kompletTextFieldCombo8 = new KompletTextFieldCombo(kon, 
                frekuensikurikulumField, frekuensikurikulum);        
        String [][] datanya8 = {{"A", "Setiap 1 tahun"},{"B","Setiap 2 tahun"},
            {"C","Setiap 3 tahun"},{"D","Setiap 4 tahun"},
            {"E","Sesuai Ketentuan Pemerintahan"},{"F","Sesuai Kebutuhan"}}; 
        kompletTextFieldCombo8.initDataUtkAutoComplete(datanya8);  
        frekuensikurikulum = kompletTextFieldCombo8.dapatkanTextField();
        frekuensikurikulumField = kompletTextFieldCombo8.dapatkanComboBox();
        
        kompletTextFieldCombo9 = new KompletTextFieldCombo(kon, 
                pelaksanaankurikulumField, pelaksanaankurikulum);        
        String [][] datanya9 = {{"A", "Oleh P.S. Sendiri"},
            {"B","Bersama Tim Dalam Perguruan Tinggi"},
            {"C","Orientasi Perguruan Tinggi Lain"},
            {"D","Orientasi Kebutuhan Pasar"},
            {"E","Bersama Stakeholder"}}; 
        kompletTextFieldCombo9.initDataUtkAutoComplete(datanya9);  
        pelaksanaankurikulum = kompletTextFieldCombo9.dapatkanTextField();
        pelaksanaankurikulumField = kompletTextFieldCombo9.dapatkanComboBox();
        
        kompletTextFieldCombo10 = new KompletTextFieldCombo(kon, 
                kodeakreditasiField, kodeakreditasi);        
        query = "SELECT \"Kode_akreditasi\", \"Status_akreditasi\", \"Keterangan_akreditasi\" " +
                " from pdpt_dev.\"TREF_AKREDITASI\""; 
        kompletTextFieldCombo10.initDataUtkAutoComplete(query);        
        kodeakreditasi = kompletTextFieldCombo10.dapatkanTextField();
        kodeakreditasiField = kompletTextFieldCombo10.dapatkanComboBox();
        
        kompletTextFieldCombo11 = new KompletTextFieldCombo(kon, 
                statusvalidasiField, statusvalidasi);        
        String [][] datanya11 = {{"1", "Belum diverifikasi"},
            {"2","Sudah diverifikasi namun masih terdapat data yang invalid"},
            {"3", "Sudah diverifikasi dan data sudah valid"}}; 
        kompletTextFieldCombo11.initDataUtkAutoComplete(datanya11);        
        statusvalidasi = kompletTextFieldCombo11.dapatkanTextField();
        statusvalidasiField = kompletTextFieldCombo11.dapatkanComboBox();
        
        kompletTextFieldCombo12 = new KompletTextFieldCombo(kon, 
                kodejurusanField, kodejurusan);        
        query = "SELECT \"Kode_jurusan\", \"Nama_jurusan\", \"Kode_fakultas\" " +
                " from pdpt_dev.\"TMST_JURUSAN\""; 
        kompletTextFieldCombo12.initDataUtkAutoComplete(query);        
        kodejurusan = kompletTextFieldCombo12.dapatkanTextField();
        kodejurusanField = kompletTextFieldCombo12.dapatkanComboBox();
    }
    
    private void inisialisasiData() {
        namaTabel = "pdpt_dev.\"TMST_PROGRAM_STUDI\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select * from " + namaTabel;
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public MasterProdi(String [][] data, String [] kolom) {
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
        kodeprogramstudiField.setText("");
        //kodeperguruantinggiField.setValue("" + homePage.dapatkanKodePT());//utk pdpt pt
        kodeperguruantinggiField.setValue("");//utk pdpt dikti
        kodefakultas.setText("");
        kodejurusan.setText("");
        namaprogramstudiField.setText("");
        kodejenjangpendidikan.setText("");
        alamatField.setText("");
        kodekota.setText("");
        kodeprovinsi.setText("");
        kodenegara.setText("");
        kodeposField.setText("");
        tanggalberdiriField.setDate(null);
        emailField.setText("");
        bidangilmu.setText("");
        SKSlulusField.setText("");
        statusprogramstudi.setText("");
        noSKDIKTIField.setText("");
        tglSKDIKTIField.setDate(null);
        tglakhirSKDIKTIField.setDate(null);
        mulaisemesterField.setText("");
        nidnnupField.setText("");
        nikField.setText("");
        hpField.setText("");
        teleponkantorField.setText("");
        faxField.setText("");
        namaoperatorField.setText("");
        frekuensikurikulum.setText("");
        pelaksanaankurikulum.setText("");
        kodeakreditasi.setText("");
        noSKBANField.setText("");
        tanggalSKBANField.setDate(null);
        tanggilakhirSKBANField.setDate(null);
        kapasitasMahasiswaField.setText("");
        visiField.setText("");
        misiField.setText("");
        tujuanField.setText("");
        sasaranField.setText("");
        upayapenyebaranField.setText("");
        keberlanjutanField.setText("");
        himpunanalumniField.setText("");
        tglmulaiefektifField.setDate(null);
        tglakhirefektifField.setDate(null);
        statusvalidasi.setText("");
        IDlogauditField.setText("");
        UUIDField.setText("");
        ketuaField.setText("");
        sekertarisField.setText("");
        rumpunilmuField.setText("");
        gelarField.setText("");
        deskripsiField.setText("");
        kompetensiField.setText("");
        capaianField.setText("");
        websiteField.setText("");
        userField.setText("");
        createdatField.setText("");
        updatedatField.setText("");
        kodeprogramstudiField.requestFocus();
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
        kodeprogramstudiLabel = new javax.swing.JLabel();
        kodeperguruantinggiLabel = new javax.swing.JLabel();
        kodefakultasLabel = new javax.swing.JLabel();
        kodejurusanLabel = new javax.swing.JLabel();
        namaprogramstudiLabel = new javax.swing.JLabel();
        kodejenjangpendidikanLabel = new javax.swing.JLabel();
        alamatLabel = new javax.swing.JLabel();
        kodekotaLabel = new javax.swing.JLabel();
        kodeprovinsiLabel = new javax.swing.JLabel();
        kodenegaraLabel = new javax.swing.JLabel();
        kodePosLabel = new javax.swing.JLabel();
        tanggalberdiriLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        bidangilmuLabel = new javax.swing.JLabel();
        SKSlulusLabel = new javax.swing.JLabel();
        statusprogramstudiLabel = new javax.swing.JLabel();
        noSKDIKTILabel = new javax.swing.JLabel();
        tglSKDIKTILabel = new javax.swing.JLabel();
        tglakhirSKDIKTILabel = new javax.swing.JLabel();
        mulaisemesterLabel = new javax.swing.JLabel();
        nidnnupLabel = new javax.swing.JLabel();
        nikLabel = new javax.swing.JLabel();
        hpLabel = new javax.swing.JLabel();
        teleponkantorLabel = new javax.swing.JLabel();
        faxLabel = new javax.swing.JLabel();
        namaoperatorLabel = new javax.swing.JLabel();
        frekuensikurikulumLabel = new javax.swing.JLabel();
        pelaksanaankurikulumLabel = new javax.swing.JLabel();
        kodeakreditasiLabel = new javax.swing.JLabel();
        noSKBANLabel = new javax.swing.JLabel();
        tanggalSKBANLabel = new javax.swing.JLabel();
        tanggilakhirSKBANLabel = new javax.swing.JLabel();
        kapasitasMahasiswaLabel = new javax.swing.JLabel();
        visiLabel = new javax.swing.JLabel();
        misiLabel = new javax.swing.JLabel();
        tujuanLabel = new javax.swing.JLabel();
        sasaranLabel = new javax.swing.JLabel();
        upayapenyebaranLabel = new javax.swing.JLabel();
        keberlanjutanLabel = new javax.swing.JLabel();
        himpunanalumniLabel = new javax.swing.JLabel();
        tglmulaiefektifLabel = new javax.swing.JLabel();
        tglakhirefektifLabel = new javax.swing.JLabel();
        statusvalidasiLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        IDlogauditLabel1 = new javax.swing.JLabel();
        IDlogauditLabel2 = new javax.swing.JLabel();
        IDlogauditLabel3 = new javax.swing.JLabel();
        IDlogauditLabel4 = new javax.swing.JLabel();
        IDlogauditLabel5 = new javax.swing.JLabel();
        IDlogauditLabel6 = new javax.swing.JLabel();
        IDlogauditLabel7 = new javax.swing.JLabel();
        IDlogauditLabel8 = new javax.swing.JLabel();
        IDlogauditLabel9 = new javax.swing.JLabel();
        IDlogauditLabel10 = new javax.swing.JLabel();
        IDlogauditLabel11 = new javax.swing.JLabel();
        IDlogauditLabel12 = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodeprogramstudiField = new javax.swing.JFormattedTextField();
        kodeperguruantinggiField = new javax.swing.JFormattedTextField();
        kodefakultasField = new javax.swing.JComboBox();
        kodejurusanField = new javax.swing.JComboBox();
        namaprogramstudiField = new javax.swing.JTextField();
        kodejenjangpendidikanField = new javax.swing.JComboBox();
        alamatField = new javax.swing.JTextField();
        kodekotaField = new javax.swing.JComboBox();
        kodeprovinsiField = new javax.swing.JComboBox();
        kodenegaraField = new javax.swing.JComboBox();
        kodeposField = new javax.swing.JFormattedTextField();
        tanggalberdiriField = new com.toedter.calendar.JDateChooser();
        emailField = new javax.swing.JTextField();
        bidangilmuField = new javax.swing.JComboBox();
        SKSlulusField = new javax.swing.JFormattedTextField();
        statusprogramstudiField = new javax.swing.JComboBox();
        noSKDIKTIField = new javax.swing.JTextField();
        tglSKDIKTIField = new com.toedter.calendar.JDateChooser();
        tglakhirSKDIKTIField = new com.toedter.calendar.JDateChooser();
        mulaisemesterField = new javax.swing.JFormattedTextField();
        nidnnupField = new javax.swing.JFormattedTextField();
        nikField = new javax.swing.JFormattedTextField();
        hpField = new javax.swing.JFormattedTextField();
        teleponkantorField = new javax.swing.JFormattedTextField();
        faxField = new javax.swing.JFormattedTextField();
        namaoperatorField = new javax.swing.JTextField();
        frekuensikurikulumField = new javax.swing.JComboBox();
        pelaksanaankurikulumField = new javax.swing.JComboBox();
        kodeakreditasiField = new javax.swing.JComboBox();
        noSKBANField = new javax.swing.JTextField();
        tanggalSKBANField = new com.toedter.calendar.JDateChooser();
        tanggilakhirSKBANField = new com.toedter.calendar.JDateChooser();
        kapasitasMahasiswaField = new javax.swing.JFormattedTextField();
        visiFieldScrollPane = new javax.swing.JScrollPane();
        visiField = new javax.swing.JTextArea();
        misiFieldScrollPane = new javax.swing.JScrollPane();
        misiField = new javax.swing.JTextArea();
        tujuanFieldScrollPane = new javax.swing.JScrollPane();
        tujuanField = new javax.swing.JTextArea();
        sasaranFieldScrollPane = new javax.swing.JScrollPane();
        sasaranField = new javax.swing.JTextArea();
        upayapenyebaranFieldScrollPane = new javax.swing.JScrollPane();
        upayapenyebaranField = new javax.swing.JTextArea();
        keberlanjutanFieldScrollPane = new javax.swing.JScrollPane();
        keberlanjutanField = new javax.swing.JTextArea();
        himpunanalumniFieldScrollPane = new javax.swing.JScrollPane();
        himpunanalumniField = new javax.swing.JTextArea();
        tglmulaiefektifField = new com.toedter.calendar.JDateChooser();
        tglakhirefektifField = new com.toedter.calendar.JDateChooser();
        statusvalidasiField = new javax.swing.JComboBox();
        IDlogauditField = new javax.swing.JFormattedTextField();
        UUIDField = new javax.swing.JTextField();
        ketuaField = new javax.swing.JTextField();
        sekertarisField = new javax.swing.JTextField();
        rumpunilmuField = new javax.swing.JTextField();
        gelarField = new javax.swing.JTextField();
        deskripsiField = new javax.swing.JTextField();
        kompetensiField = new javax.swing.JTextField();
        capaianField = new javax.swing.JTextField();
        websiteField = new javax.swing.JTextField();
        userField = new javax.swing.JTextField();
        createdatField = new javax.swing.JTextField();
        updatedatField = new javax.swing.JTextField();
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

        panelLabel.setLayout(new java.awt.GridLayout(56, 0));

        kodeprogramstudiLabel.setText("Kode Program Studi:");
        panelLabel.add(kodeprogramstudiLabel);

        kodeperguruantinggiLabel.setText("Kode Perguruan Tinggi:");
        panelLabel.add(kodeperguruantinggiLabel);

        kodefakultasLabel.setText("Kode Fakultas:");
        panelLabel.add(kodefakultasLabel);

        kodejurusanLabel.setText("Kode Jurusan:");
        panelLabel.add(kodejurusanLabel);

        namaprogramstudiLabel.setText("Nama Program Studi:");
        panelLabel.add(namaprogramstudiLabel);

        kodejenjangpendidikanLabel.setText("Kode Jenjang Pendidikan:");
        panelLabel.add(kodejenjangpendidikanLabel);

        alamatLabel.setText("Alamat:");
        panelLabel.add(alamatLabel);

        kodekotaLabel.setText("Kode Kota:");
        panelLabel.add(kodekotaLabel);

        kodeprovinsiLabel.setText("Kode Provinsi:");
        panelLabel.add(kodeprovinsiLabel);

        kodenegaraLabel.setText("Kode Negara:");
        panelLabel.add(kodenegaraLabel);

        kodePosLabel.setText("Kode Pos:");
        panelLabel.add(kodePosLabel);

        tanggalberdiriLabel.setText("Tanggal Berdiri:");
        panelLabel.add(tanggalberdiriLabel);

        emailLabel.setText("Email:");
        panelLabel.add(emailLabel);

        bidangilmuLabel.setText("Bidang Ilmu:");
        panelLabel.add(bidangilmuLabel);

        SKSlulusLabel.setText("SKS Lulus:");
        panelLabel.add(SKSlulusLabel);

        statusprogramstudiLabel.setText("Status Program Studi:");
        panelLabel.add(statusprogramstudiLabel);

        noSKDIKTILabel.setText("No SKDIKTI:");
        panelLabel.add(noSKDIKTILabel);

        tglSKDIKTILabel.setText("Tgl SKDIKTI:");
        panelLabel.add(tglSKDIKTILabel);

        tglakhirSKDIKTILabel.setText("Tgl Akhir SK DIKTI:");
        panelLabel.add(tglakhirSKDIKTILabel);

        mulaisemesterLabel.setText("Mulai Semester:");
        panelLabel.add(mulaisemesterLabel);

        nidnnupLabel.setText("NIDN/NUP:");
        panelLabel.add(nidnnupLabel);

        nikLabel.setText("Nik:");
        panelLabel.add(nikLabel);

        hpLabel.setText("Hp:");
        panelLabel.add(hpLabel);

        teleponkantorLabel.setText("Telepon Kantor:");
        panelLabel.add(teleponkantorLabel);

        faxLabel.setText("Fax:");
        panelLabel.add(faxLabel);

        namaoperatorLabel.setText("Nama Operator:");
        panelLabel.add(namaoperatorLabel);

        frekuensikurikulumLabel.setText("Frekuensi Kurikulum:");
        panelLabel.add(frekuensikurikulumLabel);

        pelaksanaankurikulumLabel.setText("Pelaksanaan Kurikulum:");
        panelLabel.add(pelaksanaankurikulumLabel);

        kodeakreditasiLabel.setText("Kode Akreditasi:");
        panelLabel.add(kodeakreditasiLabel);

        noSKBANLabel.setText("No SKBAN:");
        panelLabel.add(noSKBANLabel);

        tanggalSKBANLabel.setText("Tanggal SKBAN:");
        panelLabel.add(tanggalSKBANLabel);

        tanggilakhirSKBANLabel.setText("Tanggal Akhir SKBAN:");
        panelLabel.add(tanggilakhirSKBANLabel);

        kapasitasMahasiswaLabel.setText("Kapasitas Mahasiswa:");
        panelLabel.add(kapasitasMahasiswaLabel);

        visiLabel.setText("Visi:");
        panelLabel.add(visiLabel);

        misiLabel.setText("Misi:");
        panelLabel.add(misiLabel);

        tujuanLabel.setText("Tujuan:");
        panelLabel.add(tujuanLabel);

        sasaranLabel.setText("Sasaran:");
        panelLabel.add(sasaranLabel);

        upayapenyebaranLabel.setText("Upaya Penyebaran:");
        panelLabel.add(upayapenyebaranLabel);

        keberlanjutanLabel.setText("Keberlanjutan:");
        panelLabel.add(keberlanjutanLabel);

        himpunanalumniLabel.setText("Himpunan Alumni:");
        panelLabel.add(himpunanalumniLabel);

        tglmulaiefektifLabel.setText("Tgl Mulai Efektif:");
        panelLabel.add(tglmulaiefektifLabel);

        tglakhirefektifLabel.setText("Tgl Akhir Efektif:");
        panelLabel.add(tglakhirefektifLabel);

        statusvalidasiLabel.setText("Status Validasi:");
        panelLabel.add(statusvalidasiLabel);

        IDlogauditLabel.setText("ID Log Audit:");
        panelLabel.add(IDlogauditLabel);

        IDlogauditLabel1.setText("UUID:");
        panelLabel.add(IDlogauditLabel1);

        IDlogauditLabel2.setText("Nama Ketua:");
        panelLabel.add(IDlogauditLabel2);

        IDlogauditLabel3.setText("Nama Sekertaris:");
        panelLabel.add(IDlogauditLabel3);

        IDlogauditLabel4.setText("Rumpun Ilmu:");
        panelLabel.add(IDlogauditLabel4);

        IDlogauditLabel5.setText("Gelar Lulusan:");
        panelLabel.add(IDlogauditLabel5);

        IDlogauditLabel6.setText("Deskripsi Singkat");
        panelLabel.add(IDlogauditLabel6);

        IDlogauditLabel7.setText("Kompetensi Prodi:");
        panelLabel.add(IDlogauditLabel7);

        IDlogauditLabel8.setText("Capaian Pembelajaran");
        panelLabel.add(IDlogauditLabel8);

        IDlogauditLabel9.setText("Website");
        panelLabel.add(IDlogauditLabel9);

        IDlogauditLabel10.setText("User:");
        panelLabel.add(IDlogauditLabel10);

        IDlogauditLabel11.setText("Created at");
        panelLabel.add(IDlogauditLabel11);

        IDlogauditLabel12.setText("Updated at :");
        panelLabel.add(IDlogauditLabel12);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(56, 0));

        kodeprogramstudiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(kodeprogramstudiField);

        try {
            kodeperguruantinggiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodeperguruantinggiField);

        panelTextField.add(kodefakultasField);

        panelTextField.add(kodejurusanField);
        panelTextField.add(namaprogramstudiField);

        panelTextField.add(kodejenjangpendidikanField);
        panelTextField.add(alamatField);

        panelTextField.add(kodekotaField);

        panelTextField.add(kodeprovinsiField);

        panelTextField.add(kodenegaraField);

        try {
            kodeposField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodeposField);
        panelTextField.add(tanggalberdiriField);
        panelTextField.add(emailField);

        panelTextField.add(bidangilmuField);

        SKSlulusField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        panelTextField.add(SKSlulusField);

        panelTextField.add(statusprogramstudiField);
        panelTextField.add(noSKDIKTIField);
        panelTextField.add(tglSKDIKTIField);
        panelTextField.add(tglakhirSKDIKTIField);

        try {
            mulaisemesterField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(mulaisemesterField);

        try {
            nidnnupField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(nidnnupField);

        nikField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        panelTextField.add(nikField);

        hpField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#############"))));
        panelTextField.add(hpField);

        teleponkantorField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##############"))));
        panelTextField.add(teleponkantorField);

        faxField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###############"))));
        panelTextField.add(faxField);
        panelTextField.add(namaoperatorField);

        panelTextField.add(frekuensikurikulumField);

        panelTextField.add(pelaksanaankurikulumField);

        panelTextField.add(kodeakreditasiField);
        panelTextField.add(noSKBANField);
        panelTextField.add(tanggalSKBANField);
        panelTextField.add(tanggilakhirSKBANField);

        kapasitasMahasiswaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        panelTextField.add(kapasitasMahasiswaField);

        visiField.setColumns(20);
        visiField.setRows(1);
        visiFieldScrollPane.setViewportView(visiField);

        panelTextField.add(visiFieldScrollPane);

        misiField.setColumns(20);
        misiField.setRows(1);
        misiFieldScrollPane.setViewportView(misiField);

        panelTextField.add(misiFieldScrollPane);

        tujuanField.setColumns(20);
        tujuanField.setRows(1);
        tujuanFieldScrollPane.setViewportView(tujuanField);

        panelTextField.add(tujuanFieldScrollPane);

        sasaranField.setColumns(20);
        sasaranField.setRows(1);
        sasaranFieldScrollPane.setViewportView(sasaranField);

        panelTextField.add(sasaranFieldScrollPane);

        upayapenyebaranField.setColumns(20);
        upayapenyebaranField.setRows(1);
        upayapenyebaranFieldScrollPane.setViewportView(upayapenyebaranField);

        panelTextField.add(upayapenyebaranFieldScrollPane);

        keberlanjutanField.setColumns(20);
        keberlanjutanField.setRows(1);
        keberlanjutanFieldScrollPane.setViewportView(keberlanjutanField);

        panelTextField.add(keberlanjutanFieldScrollPane);

        himpunanalumniField.setColumns(20);
        himpunanalumniField.setRows(1);
        himpunanalumniFieldScrollPane.setViewportView(himpunanalumniField);

        panelTextField.add(himpunanalumniFieldScrollPane);
        panelTextField.add(tglmulaiefektifField);
        panelTextField.add(tglakhirefektifField);

        panelTextField.add(statusvalidasiField);

        IDlogauditField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        panelTextField.add(IDlogauditField);
        panelTextField.add(UUIDField);
        panelTextField.add(ketuaField);
        panelTextField.add(sekertarisField);
        panelTextField.add(rumpunilmuField);
        panelTextField.add(gelarField);
        panelTextField.add(deskripsiField);
        panelTextField.add(kompetensiField);
        panelTextField.add(capaianField);
        panelTextField.add(websiteField);
        panelTextField.add(userField);
        panelTextField.add(createdatField);
        panelTextField.add(updatedatField);

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

    private void tampilkanData(int row) {
        System.out.println("baris yg dipilih utk diUPDATE: "  + row);
        persiapanEntriDataBaru();
        kodeprogramstudiField.setText(tabel.getValueAt(row, 0).toString());
        System.out.println("kodeps: " + tabel.getValueAt(row, 0).toString());
        kodeperguruantinggiField.setText(tabel.getValueAt(row, 1).toString());
        kodefakultas.setText(tabel.getValueAt(row, 2).toString());
        kodejurusan.setText(tabel.getValueAt(row, 3).toString());
        namaprogramstudiField.setText(tabel.getValueAt(row, 4)==null?"":tabel.getValueAt(row, 4).toString());
        kodejenjangpendidikan.setText(tabel.getValueAt(row, 5)==null?"0":tabel.getValueAt(row, 5).toString());
        alamatField.setText(tabel.getValueAt(row, 6)==null?"":tabel.getValueAt(row, 6).toString());
        kodekota.setText(tabel.getValueAt(row, 7).toString());
        kodeprovinsi.setText(tabel.getValueAt(row, 8).toString());
        kodenegara.setText(tabel.getValueAt(row, 9).toString());
        kodeposField.setText(tabel.getValueAt(row, 10).toString());
        
        String strKalendar = tabel.getValueAt(row, 11).toString();
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
        kalendar.set(tahun, intBulan-1, hari); 
        tanggalberdiriField.setCalendar(kalendar);        
        
        emailField.setText(tabel.getValueAt(row, 12)==null?"":tabel.getValueAt(row, 12).toString());
        bidangilmu.setText(tabel.getValueAt(row, 13)==null?"":tabel.getValueAt(row, 13).toString());
        SKSlulusField.setText(tabel.getValueAt(row, 14)==null?"":tabel.getValueAt(row, 14).toString());
        statusprogramstudi.setText(tabel.getValueAt(row, 15)==null?"":tabel.getValueAt(row, 15).toString());
        noSKDIKTIField.setText(tabel.getValueAt(row, 16)==null?"":tabel.getValueAt(row, 16).toString());
        
        strKalendar = tabel.getValueAt(row, 17).toString();
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
        kalendar.set(tahun, intBulan-1, hari); 
        tglSKDIKTIField.setCalendar(kalendar);
        
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
        kalendar.set(tahun, intBulan-1, hari); 
        tglakhirSKDIKTIField.setCalendar(kalendar);
        
        mulaisemesterField.setText(tabel.getValueAt(row, 19).toString());
        nidnnupField.setText(tabel.getValueAt(row, 20).toString());
        System.out.println("nidn/nup : " + tabel.getValueAt(row, 20).toString());
        nikField.setText(tabel.getValueAt(row, 21)==null?"":tabel.getValueAt(row, 21).toString());
        hpField.setText(tabel.getValueAt(row, 22)==null?"":tabel.getValueAt(row, 22).toString());
        teleponkantorField.setText(tabel.getValueAt(row, 23)==null?"":tabel.getValueAt(row, 23).toString());
        faxField.setText(tabel.getValueAt(row, 24)==null?"":tabel.getValueAt(row, 24).toString());
        namaoperatorField.setText(tabel.getValueAt(row, 25)==null?"":tabel.getValueAt(row, 25).toString());
        frekuensikurikulum.setText(tabel.getValueAt(row, 26)==null?"":tabel.getValueAt(row, 26).toString());
        pelaksanaankurikulum.setText(tabel.getValueAt(row, 27)==null?"":tabel.getValueAt(row, 27).toString());
        kodeakreditasi.setText(tabel.getValueAt(row, 28).toString());
        noSKBANField.setText(tabel.getValueAt(row, 29)==null?"":tabel.getValueAt(row, 29).toString());
        
        strKalendar = tabel.getValueAt(row, 30).toString();
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
        kalendar.set(tahun, intBulan-1, hari); 
        tanggalSKBANField.setCalendar(kalendar);
        
        strKalendar = tabel.getValueAt(row, 31).toString();
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
        kalendar.set(tahun, intBulan-1, hari); 
        tanggilakhirSKBANField.setCalendar(kalendar);        
        
        kapasitasMahasiswaField.setText(tabel.getValueAt(row, 32)==null?"":tabel.getValueAt(row, 32).toString());
        visiField.setText(tabel.getValueAt(row, 33)==null?"":tabel.getValueAt(row, 33).toString());
        misiField.setText(tabel.getValueAt(row, 34)==null?"":tabel.getValueAt(row, 34).toString());
        tujuanField.setText(tabel.getValueAt(row, 35)==null?"":tabel.getValueAt(row, 35).toString());
        sasaranField.setText(tabel.getValueAt(row, 36)==null?"":tabel.getValueAt(row, 36).toString());
        upayapenyebaranField.setText(tabel.getValueAt(row, 37)==null?"":tabel.getValueAt(row, 37).toString());
        keberlanjutanField.setText(tabel.getValueAt(row, 38)==null?"":tabel.getValueAt(row, 38).toString());
        himpunanalumniField.setText(tabel.getValueAt(row, 39)==null?"":tabel.getValueAt(row, 39).toString());
        
        strKalendar = tabel.getValueAt(row, 40).toString();
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
        kalendar.set(tahun, intBulan-1, hari); 
        tglmulaiefektifField.setCalendar(kalendar);        
        
        strKalendar = tabel.getValueAt(row, 41).toString();
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
        kalendar.set(tahun, intBulan-1, hari); 
        tglakhirefektifField.setCalendar(kalendar);
        
        statusvalidasi.setText(tabel.getValueAt(row, 42).toString());
        //IDlogauditField.setText(tabel.getValueAt(row, 43).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
        data[0] = kodeprogramstudiField.getText();
        
        data[1] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");
        
        String datakodefakultasmentah = kodefakultas.getText();
        System.out.println("data kode fakultas mentah: " + datakodefakultasmentah);
        String [] datakodefakultas = datakodefakultasmentah.split(" ");
        System.out.println("data kode fakultas yg dipilih: " + datakodefakultas[0]);
        data[2] = datakodefakultas[0];
        
        String datakodejurusanmentah = kodejurusan.getText();
        System.out.println("data kode jurusan mentah: " + datakodejurusanmentah);
        String [] datakodejurusan = datakodejurusanmentah.split(" ");
        System.out.println("data kode jurusan yg dipilih: " + datakodejurusan[0]);
        data[3] = datakodejurusan[0];
        
        data[4] = (!namaprogramstudiField.getText().equals("")?namaprogramstudiField.getText():"X");
        
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[5] = (kodejenjangpendidikan.getText().equals("")?"0":datakodejenjangpendidikan[0]);
        
        data[6] = (!alamatField.getText().equals("")?alamatField.getText():"X");
        
        String datakodekotamentah = kodekota.getText();
        System.out.println("data kode kota mentah: " + datakodekotamentah);
        String [] datakodekota = datakodekotamentah.split(" ");
        System.out.println("data kode kota yg dipilih: " + datakodekota[0]);
        data[7] = (kodekota.getText().equals("")?"0":datakodekota[0]);
        
        String datakodeprovinsimentah = kodeprovinsi.getText();
        System.out.println("data kode provinsi mentah: " + datakodeprovinsimentah);
        String [] datakodeprovinsi = datakodeprovinsimentah.split(" ");
        System.out.println("data kode provinsi yg dipilih: " + datakodeprovinsi[0]);
        data[8] = (kodeprovinsi.getText().equals("")?"0":datakodeprovinsi[0]);
        
        String datakodenegaramentah = kodenegara.getText();
        System.out.println("data kode negara mentah: " + datakodenegaramentah);
        String [] datakodenegara = datakodenegaramentah.split(" ");
        System.out.println("data kode negara yg dipilih: " + datakodenegara[0]);
        data[9] = (kodenegara.getText().equals("")?"0":datakodenegara[0]);
        
        data[10] = (!kodeposField.getText().equals("     ")?kodeposField.getText():"0");        
        
        kalendar = tanggalberdiriField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data8 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[11] = (data8!=null?data8:"");
        
        data[12] = (!emailField.getText().equals("")?emailField.getText():"X");
        
        String databidangilmumentah = bidangilmu.getText();
        System.out.println("data bidangilmu mentah: " + databidangilmumentah);
        String [] databidangilmu = databidangilmumentah.split(" ");
        System.out.println("data bidangilmu yg dipilih: " + databidangilmu[0]);
        data[13] = (bidangilmu.getText().equals("")?"0":databidangilmu[0]);
        
//        String databidangilmumentah1 = bidangilmu.getText();
//        System.out.println("data bidangilmu mentah: " + databidangilmumentah);
//        String [] databidangilmu1 = databidangilmumentah1.split(" ");
//        System.out.println("data bidangilmu yg dipilih: " + databidangilmu[0]);
//        data[14] = (bidangilmu.getText().equals("")?"0":databidangilmu1[0]);
//        
        
        data[14] = (!SKSlulusField.getText().equals("")?SKSlulusField.getText():"0");
        
        String datastatusprogramstudimentah = statusprogramstudi.getText();
        System.out.println("data status program studi mentah: " + datastatusprogramstudimentah);
        String [] datastatusprogramstudi = datastatusprogramstudimentah.split(" ");
        System.out.println("data status program studi yg dipilih: " + datastatusprogramstudi[0]);
        data[15] = (statusprogramstudi.getText().equals("")?"X":datastatusprogramstudi[0]);
        
        data[16] = (!noSKDIKTIField.getText().equals("")?noSKDIKTIField.getText():"X");
        
        kalendar = tglSKDIKTIField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data17 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[17] = (data17!=null?data17:"");
        
        kalendar = tglakhirSKDIKTIField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data18 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[18] = (data18!=null?data18:"");
        
        data[19] = (!mulaisemesterField.getText().equals("     ")?mulaisemesterField.getText():"0");        
        data[20] = (!nidnnupField.getText().equals("          ")?nidnnupField.getText():"0");
        data[21] = (!nikField.getText().equals("")?nikField.getText():"0");
        
        data[22] = (!hpField.getText().equals("")?hpField.getText():"0");
        data[23] = (!teleponkantorField.getText().equals("")?teleponkantorField.getText():"0");
        data[24] = (!faxField.getText().equals("")?faxField.getText():"0");
        
        data[25] = (!namaoperatorField.getText().equals("")?namaoperatorField.getText():"X");
        
        String datafrekuensikurikulummentah = frekuensikurikulum.getText();
        System.out.println("data frekuensi kurikulum mentah: " + datafrekuensikurikulummentah);
        String [] datafrekuensikurikulum = datafrekuensikurikulummentah.split(" ");
        System.out.println("data frekuensi kurikulum  yg dipilih: " + datafrekuensikurikulum[0]);
        data[26] = (frekuensikurikulum.getText().equals("")?"X":datafrekuensikurikulum[0]);
        
        String datapelaksanaankurikulummentah = pelaksanaankurikulum.getText();
        System.out.println("data pelaksanaankurikulum mentah: " + datapelaksanaankurikulummentah);
        String [] datapelaksanaankurikulum = datapelaksanaankurikulummentah.split(" ");
        System.out.println("data pelaksanaankurikulum yg dipilih: " + datapelaksanaankurikulum[0]);
        data[27] = (pelaksanaankurikulum.getText().equals("")?"X":datapelaksanaankurikulum[0]);
        
        String datakodeakreditasimentah = kodeakreditasi.getText();
        System.out.println("data kode akreditasi mentah: " + datakodeakreditasimentah);
        String [] datakodeakreditasi = datakodeakreditasimentah.split(" ");
        System.out.println("data kode akreditasi yg dipilih: " + datakodeakreditasi[0]);
        data[28] = (kodeakreditasi.getText().equals("")?"0":datakodeakreditasi[0]);
        
        data[29] = (!noSKBANField.getText().equals("")?noSKBANField.getText():"X");
        
        kalendar = tanggalSKBANField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data30 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[30] = (data30!=null?data30:"");
        
        kalendar = tanggilakhirSKBANField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data31 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[31] = (data31!=null?data31:"");
        
        data[32] = (!kapasitasMahasiswaField.getText().equals("")?kapasitasMahasiswaField.getText():"0");
        
        data[33] = (!visiField.getText().equals("")?visiField.getText():"X");
        data[34] = (!misiField.getText().equals("")? misiField.getText():"X");
        data[35] = (!tujuanField.getText().equals("")? tujuanField.getText():"X");
        data[36] = (!sasaranField.getText().equals("")? sasaranField.getText():"X");
        data[37] = (!upayapenyebaranField.getText().equals("")? upayapenyebaranField.getText():"X");
        data[38] = (!keberlanjutanField.getText().equals("")? keberlanjutanField.getText():"X");
        data[39] = (!himpunanalumniField.getText().equals("")? himpunanalumniField.getText():"X");
        
        kalendar = tglmulaiefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data40 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[40] = (data40!=null?data40:"");
        
        kalendar = tglakhirefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data41 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[41] = (data41!=null?data41:"");
        
        String datastatusvalidasimentah = statusvalidasi.getText();
        System.out.println("data status validasi mentah: " + datastatusvalidasimentah);
        String [] datastatusvalidasi = datastatusvalidasimentah.split(" ");
        System.out.println("data status validasi yg dipilih: " + datastatusvalidasi[0]);
        data[42] = (statusvalidasi.getText().equals("")?"0":datastatusvalidasi[0]);
                
        data[43] = "" + idlogaudit;
        data[44] = UUIDField.getText();
        data[45] = ketuaField.getText();
        data[46] = sekertarisField.getText();
        data[47] = rumpunilmuField.getText();
        data[48] = gelarField.getText();
        data[49] = deskripsiField.getText();
        data[50] = kompetensiField.getText();
        data[51] = capaianField.getText();
        data[52] = websiteField.getText();
        data[53] = userField.getText();
        data[54] = createdatField.getText();
        data[55] = updatedatField.getText();
        return data;
    }
    
    private String [] dapatkanKolomUntukUpdate() {
        String [] kolomDgnUrutanAslinya = kon.dapatkanKolom();//utk keperluan query tdk boleh dibuat alias, sedangkan pada inisialisasi utk keperluan display ke tabel boleh pakai alias
        String [] kolom = new String[kolomDgnUrutanAslinya.length];
        for (int a=0; a<kolom.length - 4; a++) {
            kolom[a] = kolomDgnUrutanAslinya[a + 4];
        }
        //int counter = 0;
        for (int a=kolom.length-4, counter=0; a<kolom.length; a++, counter++) {
            kolom[a] = kolomDgnUrutanAslinya[counter];
            //counter++;
        }
        return kolom;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];                
        System.out.println("jumlah kolom dlm UPDATE: " + jmlKolom);
        data[0] = (!namaprogramstudiField.getText().equals("")?namaprogramstudiField.getText():"X");
                
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[1] = (kodejenjangpendidikan.getText().equals("")?"0":datakodejenjangpendidikan[0]);
        
        data[2] = (!alamatField.getText().equals("")?alamatField.getText():"X");
        
        String datakodekotamentah = kodekota.getText();
        System.out.println("data kode kota mentah: " + datakodekotamentah);
        String [] datakodekota = datakodekotamentah.split(" ");
        System.out.println("data kode kota yg dipilih: " + datakodekota[0]);
        data[3] = (kodekota.getText().equals("")?"0":datakodekota[0]);
        
        String datakodeprovinsimentah = kodeprovinsi.getText();
        System.out.println("data kode provinsi mentah: " + datakodeprovinsimentah);
        String [] datakodeprovinsi = datakodeprovinsimentah.split(" ");
        System.out.println("data kode provinsi yg dipilih: " + datakodeprovinsi[0]);
        data[4] = (kodeprovinsi.getText().equals("")?"0":datakodeprovinsi[0]);
        
        String datakodenegaramentah = kodenegara.getText();
        System.out.println("data kode negara mentah: " + datakodenegaramentah);
        String [] datakodenegara = datakodenegaramentah.split(" ");
        System.out.println("data kode negara yg dipilih: " + datakodenegara[0]);
        data[5] = (kodenegara.getText().equals("")?"0":datakodenegara[0]);
        
        data[6] = (!kodeposField.getText().equals("     ")?kodeposField.getText():"0");        
        
        kalendar = tanggalberdiriField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data8 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[7] = (data8!=null?data8:"");
        
        data[8] = (!emailField.getText().equals("")?emailField.getText():"X");
        
        String databidangilmumentah = kodenegara.getText();
        System.out.println("data bidangilmu mentah: " + databidangilmumentah);
        String [] databidangilmu = databidangilmumentah.split(" ");
        System.out.println("data bidangilmu yg dipilih: " + databidangilmu[0]);
        data[9] = (kodenegara.getText().equals("")?"0":databidangilmu[0]);
        
        data[10] = (!SKSlulusField.getText().equals("")?SKSlulusField.getText():"0");
        
        String datastatusprogramstudimentah = statusprogramstudi.getText();
        System.out.println("data status program studi mentah: " + datastatusprogramstudimentah);
        String [] datastatusprogramstudi = datastatusprogramstudimentah.split(" ");
        System.out.println("data status program studi yg dipilih: " + datastatusprogramstudi[0]);
        data[11] = (statusprogramstudi.getText().equals("")?"X":datastatusprogramstudi[0]);
        
        data[12] = (!noSKDIKTIField.getText().equals("")?noSKDIKTIField.getText():"X");
        
        kalendar = tglSKDIKTIField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data17 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[13] = (data17!=null?data17:"");
        
        kalendar = tglakhirSKDIKTIField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data18 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[14] = (data18!=null?data18:"");
        
        data[15] = (!mulaisemesterField.getText().equals("     ")?mulaisemesterField.getText():"0");        
        data[16] = (!nidnnupField.getText().equals("          ")?nidnnupField.getText():"0");
        data[17] = (!nikField.getText().equals("")?nikField.getText():"0");
        
        data[18] = (!hpField.getText().equals("")?hpField.getText():"0");
        data[19] = (!teleponkantorField.getText().equals("")?teleponkantorField.getText():"0");
        data[20] = (!faxField.getText().equals("")?faxField.getText():"0");
        
        data[21] = (!namaoperatorField.getText().equals("")?namaoperatorField.getText():"X");
        
        String datafrekuensikurikulummentah = frekuensikurikulum.getText();
        System.out.println("data frekuensi kurikulum mentah: " + datafrekuensikurikulummentah);
        String [] datafrekuensikurikulum = datafrekuensikurikulummentah.split(" ");
        System.out.println("data frekuensi kurikulum  yg dipilih: " + datafrekuensikurikulum[0]);
        data[22] = (frekuensikurikulum.getText().equals("")?"X":datafrekuensikurikulum[0]);
        
        String datapelaksanaankurikulummentah = pelaksanaankurikulum.getText();
        System.out.println("data pelaksanaankurikulum mentah: " + datapelaksanaankurikulummentah);
        String [] datapelaksanaankurikulum = datapelaksanaankurikulummentah.split(" ");
        System.out.println("data pelaksanaankurikulum yg dipilih: " + datapelaksanaankurikulum[0]);
        data[23] = (pelaksanaankurikulum.getText().equals("")?"X":datapelaksanaankurikulum[0]);
        
        String datakodeakreditasimentah = kodeakreditasi.getText();
        System.out.println("data kode akreditasi mentah: " + datakodeakreditasimentah);
        String [] datakodeakreditasi = datakodeakreditasimentah.split(" ");
        System.out.println("data kode akreditasi yg dipilih: " + datakodeakreditasi[0]);
        data[24] = (kodeakreditasi.getText().equals("")?"0":datakodeakreditasi[0]);
        
        data[25] = (!noSKBANField.getText().equals("")?noSKBANField.getText():"X");
        
        kalendar = tanggalSKBANField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data30 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[26] = (data30!=null?data30:"");
        
        kalendar = tanggilakhirSKBANField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data31 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[27] = (data31!=null?data31:"");
        
        data[28] = (!kapasitasMahasiswaField.getText().equals("")?kapasitasMahasiswaField.getText():"0");
        
        data[29] = (!visiField.getText().equals("")?visiField.getText():"X");
        data[30] = (!misiField.getText().equals("")? misiField.getText():"X");
        data[31] = (!tujuanField.getText().equals("")? tujuanField.getText():"X");
        data[32] = (!sasaranField.getText().equals("")? sasaranField.getText():"X");
        data[33] = (!upayapenyebaranField.getText().equals("")? upayapenyebaranField.getText():"X");
        data[34] = (!keberlanjutanField.getText().equals("")? keberlanjutanField.getText():"X");
        data[35] = (!himpunanalumniField.getText().equals("")? himpunanalumniField.getText():"X");
        
        kalendar = tglmulaiefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data40 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[36] = (data40!=null?data40:"");
        
        kalendar = tglakhirefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data41 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[37] = (data41!=null?data41:"");
        
        String datastatusvalidasimentah = statusvalidasi.getText();
        System.out.println("data status validasi mentah: " + datastatusvalidasimentah);
        String [] datastatusvalidasi = datastatusvalidasimentah.split(" ");
        System.out.println("data status validasi yg dipilih: " + datastatusvalidasi[0]);
        data[38] = (statusvalidasi.getText().equals("")?"0":datastatusvalidasi[0]);
                
        data[39] = "" + idlogaudit;
        data[40] = UUIDField.getText();
        data[41] = ketuaField.getText();
        data[42] = sekertarisField.getText();
        data[43] = rumpunilmuField.getText();
        data[44] = gelarField.getText();
        data[45] = deskripsiField.getText();
        data[46] = kompetensiField.getText();
        data[47] = capaianField.getText();
        data[48] = websiteField.getText();
        data[49] = userField.getText();
        data[50] = createdatField.getText();
        data[51] = updatedatField.getText();

        data[52] = kodeprogramstudiField.getText();
        
        data[53] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");
        
        String datakodefakultasmentah = kodefakultas.getText();
        System.out.println("data kode fakultas mentah: " + datakodefakultasmentah);
        String [] datakodefakultas = datakodefakultasmentah.split(" ");
        System.out.println("data kode fakultas yg dipilih: " + datakodefakultas[0]);
        data[54] = datakodefakultas[0];
        
        String datakodejurusanmentah = kodejurusan.getText();
        System.out.println("data kode jurusan mentah: " + datakodejurusanmentah);
        String [] datakodejurusan = datakodejurusanmentah.split(" ");
        System.out.println("data kode jurusan yg dipilih: " + datakodejurusan[0]);
        data[55] = datakodejurusan[0];
        
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[4];
        
        data[0] = kodeprogramstudiField.getText();
        
        data[1] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");
        
        String datakodefakultasmentah = kodefakultas.getText();
        System.out.println("data kode fakultas mentah: " + datakodefakultasmentah);
        String [] datakodefakultas = datakodefakultasmentah.split(" ");
        System.out.println("data kode fakultas yg dipilih: " + datakodefakultas[0]);
        data[2] = datakodefakultas[0];
        
        String datakodejurusanmentah = kodejurusan.getText();
        System.out.println("data kode jurusan mentah: " + datakodejurusanmentah);
        String [] datakodejurusan = datakodejurusanmentah.split(" ");
        System.out.println("data kode jurusan yg dipilih: " + datakodejurusan[0]);
        data[3] = datakodejurusan[0];
        return data;   
    }
    
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
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukInsert() );
         int hasil = model.insertAktifitasDOsen(query,
            dapatkanNilaiUntukInsert() );
        if (hasil == 1) {
            query = "select * from " + namaTabel;
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
        " SET \"Nama_program_studi\"=?, " +
        " \"Kode_jenjang_pendidikan\"=?, " +
        " \"Alamat\"=?, " +
        " \"Kode_kota\"=?, " +
        " \"Kode_provinsi\"=?, " +
        " \"Kode_negara\"=?, " +
        " \"kode_pos\"=?, " +
        " \"Tanggal_berdiri\"=?, " +
        " \"Email\"=?, " +
        " \"Bidang_ilmu\"=?, " +
        " \"SKS_lulus\"=?, " +
        " \"Status_program_studi\"=?, " +
        " \"no_SK_DIKTI\"=?, " +
        " \"Tgl_SK_DIKTI\"=?, " +
        " \"Tgl_akhir_SK_DIKTI\"=?, " +
        " \"Mulai_semester\"=?, " +
        " \"NIDN_NUP\"=?, " +
        " \"NIK\"=?, " +
        " \"HP\"=?, " +
        " \"Telepon_kantor\"=?, " +
        " \"Fax\"=?, " +
        " \"Nama_operator\"=?, " +
        " \"Frekuensi_kurikulum\"=?, " +
        " \"Pelaksanaan_kurikulum\"=?, " +
        " \"Kode_akreditasi\"=?, " +        
        " \"No_SK_BAN\"=?, " +                
        " \"Tanggal_SK_BAN\"=?, " +                
        " \"Tanggal_akhir_SK_BAN\"=?, " +                
        " \"kapasitas_mahasiswa\"=?, " +                
        " \"Visi\"=?, " +                        
        " \"Misi\"=?, " +                                
        " \"Tujuan\"=?, " +                        
        " \"Sasaran\"=?, " +
        " \"Upaya_penyebaran\"=?, " +
        " \"Keberlanjutan\"=?, " +        
        " \"Himpunan_alumni\"=?, " +
        " \"Tgl_mulai_efektif\"=?, " +
        " \"Tgl_akhir_efektif\"=?, " +
        " \"Status_validasi\"=?, " +
        " \"ID_log_audit\"=?, " +
        " \"UUID\"=?, " +
        " \"Nama_Ketua\"=?, " +
        " \"Nama_Sekertaris\"=?, " +
        " \"Rumpun_Ilmu\"=?, " +
        " \"Gelar_Lulusan\"=?, " +
        " \"Deskripsi_Singkat\"=?, " +
        " \"Kompetensi_Prodi\"=?, " +
        " \"Capaian_pembelajaran\"=?, " +
        " \"Website\"=?, " +
        " \"User\"=?, " +
        " \"Created_At\"=?, " +
        " \"Updated_at\"=?, " +
        " \"ID_log_audit\"=? " +
        " WHERE \"Kode_program_studi\"=? AND " +
        " \"Kode_perguruan_tinggi\"=? AND " + 
        " \"Kode_fakultas\"=? AND " +
        " \"Kode_jurusan\"=? ";
        
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukUpdate() );
        int hasil = model.updateAktifitasDOsen(query,
            dapatkanNilaiUntukUpdate() );
        if (hasil == 1) {
            query = "select * from " + namaTabel;
            kon.selectData(query);
            tampilkanDataKeTabel(kon.dapatkanData(), ubahLabelKeSentenceCase(kon.dapatkanKolom()));
            
            /*
             * //insert data ke TREF_LOG_AUDIT yg belum mengakomodasi multiple PK
            query = "insert into TREF_LOG_AUDIT " +
                    " values(?,?,?,?,?,?)";
            hasil = kon.insertUpdateDeleteData(query,
                            dapatkanNilaiUntukInsertKeLogAudit("UPDATE", 
                                namaTabel, 
                                kon.dapatkanQueryUtkUpdateStatement(namaTabel, 
                                    dapatkanNilaiUntukUpdate())) );
             * 
             */
            
            
            //insert data ke TREF_LOG_AUDIT
            query = "insert into pdpt_dev.\"TREF_LOG_AUDIT\" " +
                    " values(?,?,?,?,?,?)";
            hasil = kon.insertUpdateDeleteData(query,
                            dapatkanNilaiUntukInsertKeLogAudit("UPDATE", 
                                namaTabel, 
                                kon.dapatkanQueryUtkUpdateStatement(namaTabel, 
                                    dapatkanNilaiUntukUpdate(), dapatkanKolomUntukUpdate()
                                )
                            ) 
                    );
        } 
        
        else {
            System.out.println("update bermasalah dengan hasil bernilai " + hasil);
            
            //buat ngeTRACE kegagalan UPDATE
            System.out.println("buat ngeTRACE kegagalan UPDATE: " + kon.dapatkanQueryUtkUpdateStatement(namaTabel, 
                                    dapatkanNilaiUntukUpdate()) );
                                
        }
    }//GEN-LAST:event_buttonUpdateActionPerformed

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        // TODO add your handling code here:
        initIDLogAudit();
        String query = "delete from " + namaTabel + " WHERE " +
        " \"Kode_program_studi\"=? AND " +
        " \"Kode_perguruan_tinggi\"=? AND " +
        " \"Kode_fakultas\"=? AND " + 
        " \"Kode_jurusan\"=? ";
        
        String [] pkDataYgDihapus = dapatkanNilaiUntukDelete();

        int hasil = model.delete(query,
            dapatkanNilaiUntukDelete() );
        if (hasil == 1) {
            query = "select * from " + namaTabel;
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField IDlogauditField;
    private javax.swing.JLabel IDlogauditLabel;
    private javax.swing.JLabel IDlogauditLabel1;
    private javax.swing.JLabel IDlogauditLabel10;
    private javax.swing.JLabel IDlogauditLabel11;
    private javax.swing.JLabel IDlogauditLabel12;
    private javax.swing.JLabel IDlogauditLabel2;
    private javax.swing.JLabel IDlogauditLabel3;
    private javax.swing.JLabel IDlogauditLabel4;
    private javax.swing.JLabel IDlogauditLabel5;
    private javax.swing.JLabel IDlogauditLabel6;
    private javax.swing.JLabel IDlogauditLabel7;
    private javax.swing.JLabel IDlogauditLabel8;
    private javax.swing.JLabel IDlogauditLabel9;
    private javax.swing.JFormattedTextField SKSlulusField;
    private javax.swing.JLabel SKSlulusLabel;
    private javax.swing.JTextField UUIDField;
    private javax.swing.JTextField alamatField;
    private javax.swing.JLabel alamatLabel;
    private javax.swing.JComboBox bidangilmuField;
    private javax.swing.JLabel bidangilmuLabel;
    private Widget.Button buttonBaru;
    private Widget.Button buttonDelete;
    private Widget.Button buttonFirst;
    private Widget.Button buttonInsert;
    private Widget.Button buttonLast;
    private Widget.Button buttonNext;
    private Widget.Button buttonPrev;
    private Widget.Button buttonUpdate;
    private javax.swing.JTextField capaianField;
    private javax.swing.JTextField createdatField;
    private javax.swing.JTextField deskripsiField;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JFormattedTextField faxField;
    private javax.swing.JLabel faxLabel;
    private javax.swing.JComboBox frekuensikurikulumField;
    private javax.swing.JLabel frekuensikurikulumLabel;
    private javax.swing.JTextField gelarField;
    private javax.swing.JScrollPane gulungPanelAtribut;
    private javax.swing.JScrollPane gulungTabel;
    private javax.swing.JTextArea himpunanalumniField;
    private javax.swing.JScrollPane himpunanalumniFieldScrollPane;
    private javax.swing.JLabel himpunanalumniLabel;
    private javax.swing.JFormattedTextField hpField;
    private javax.swing.JLabel hpLabel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JFormattedTextField kapasitasMahasiswaField;
    private javax.swing.JLabel kapasitasMahasiswaLabel;
    private javax.swing.JTextArea keberlanjutanField;
    private javax.swing.JScrollPane keberlanjutanFieldScrollPane;
    private javax.swing.JLabel keberlanjutanLabel;
    private javax.swing.JTextField ketuaField;
    private javax.swing.JLabel kodePosLabel;
    private javax.swing.JComboBox kodeakreditasiField;
    private javax.swing.JLabel kodeakreditasiLabel;
    private javax.swing.JComboBox kodefakultasField;
    private javax.swing.JLabel kodefakultasLabel;
    private javax.swing.JComboBox kodejenjangpendidikanField;
    private javax.swing.JLabel kodejenjangpendidikanLabel;
    private javax.swing.JComboBox kodejurusanField;
    private javax.swing.JLabel kodejurusanLabel;
    private javax.swing.JComboBox kodekotaField;
    private javax.swing.JLabel kodekotaLabel;
    private javax.swing.JComboBox kodenegaraField;
    private javax.swing.JLabel kodenegaraLabel;
    private javax.swing.JFormattedTextField kodeperguruantinggiField;
    private javax.swing.JLabel kodeperguruantinggiLabel;
    private javax.swing.JFormattedTextField kodeposField;
    private javax.swing.JFormattedTextField kodeprogramstudiField;
    private javax.swing.JLabel kodeprogramstudiLabel;
    private javax.swing.JComboBox kodeprovinsiField;
    private javax.swing.JLabel kodeprovinsiLabel;
    private javax.swing.JTextField kompetensiField;
    private javax.swing.JTextArea misiField;
    private javax.swing.JScrollPane misiFieldScrollPane;
    private javax.swing.JLabel misiLabel;
    private javax.swing.JFormattedTextField mulaisemesterField;
    private javax.swing.JLabel mulaisemesterLabel;
    private javax.swing.JTextField namaoperatorField;
    private javax.swing.JLabel namaoperatorLabel;
    private javax.swing.JTextField namaprogramstudiField;
    private javax.swing.JLabel namaprogramstudiLabel;
    private javax.swing.JFormattedTextField nidnnupField;
    private javax.swing.JLabel nidnnupLabel;
    private javax.swing.JFormattedTextField nikField;
    private javax.swing.JLabel nikLabel;
    private javax.swing.JTextField noSKBANField;
    private javax.swing.JLabel noSKBANLabel;
    private javax.swing.JTextField noSKDIKTIField;
    private javax.swing.JLabel noSKDIKTILabel;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JComboBox pelaksanaankurikulumField;
    private javax.swing.JLabel pelaksanaankurikulumLabel;
    private javax.swing.JTextField rumpunilmuField;
    private javax.swing.JTextArea sasaranField;
    private javax.swing.JScrollPane sasaranFieldScrollPane;
    private javax.swing.JLabel sasaranLabel;
    private javax.swing.JTextField sekertarisField;
    private javax.swing.JComboBox statusprogramstudiField;
    private javax.swing.JLabel statusprogramstudiLabel;
    private javax.swing.JComboBox statusvalidasiField;
    private javax.swing.JLabel statusvalidasiLabel;
    private javax.swing.JTable tabel;
    private com.toedter.calendar.JDateChooser tanggalSKBANField;
    private javax.swing.JLabel tanggalSKBANLabel;
    private com.toedter.calendar.JDateChooser tanggalberdiriField;
    private javax.swing.JLabel tanggalberdiriLabel;
    private com.toedter.calendar.JDateChooser tanggilakhirSKBANField;
    private javax.swing.JLabel tanggilakhirSKBANLabel;
    private javax.swing.JFormattedTextField teleponkantorField;
    private javax.swing.JLabel teleponkantorLabel;
    private com.toedter.calendar.JDateChooser tglSKDIKTIField;
    private javax.swing.JLabel tglSKDIKTILabel;
    private com.toedter.calendar.JDateChooser tglakhirSKDIKTIField;
    private javax.swing.JLabel tglakhirSKDIKTILabel;
    private com.toedter.calendar.JDateChooser tglakhirefektifField;
    private javax.swing.JLabel tglakhirefektifLabel;
    private com.toedter.calendar.JDateChooser tglmulaiefektifField;
    private javax.swing.JLabel tglmulaiefektifLabel;
    private javax.swing.JTextArea tujuanField;
    private javax.swing.JScrollPane tujuanFieldScrollPane;
    private javax.swing.JLabel tujuanLabel;
    private javax.swing.JTextArea upayapenyebaranField;
    private javax.swing.JScrollPane upayapenyebaranFieldScrollPane;
    private javax.swing.JLabel upayapenyebaranLabel;
    private javax.swing.JTextField updatedatField;
    private javax.swing.JTextField userField;
    private javax.swing.JTextArea visiField;
    private javax.swing.JScrollPane visiFieldScrollPane;
    private javax.swing.JLabel visiLabel;
    private javax.swing.JTextField websiteField;
    // End of variables declaration//GEN-END:variables
}
