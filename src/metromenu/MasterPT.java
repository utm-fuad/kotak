/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelMasterPT;
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
public class MasterPT extends javax.swing.JPanel {
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
    
    private JTextField jenispt;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo;
    private JTextField kategoript;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    private JTextField statuspt;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    private JTextField kodekota;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo4;
    private JTextField kodeprovinsi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo5;
    private JTextField kodenegara;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo6;
    private JTextField kodeakreditasi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo7;
    private JTextField kodekopertis;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo8;
    private JTextField kodewilayah;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo9;
    private JTextField kodekementrian;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo10;    
    private JTextField statusvalidasi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo11;
    private JTextField kodebadanhukum;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo12;
    Model.ModelMasterPT model;
    private int idlogaudit;
    private String kueri,kodept;

    /**
     * Creates new form PanelHalaman1
     */
    public MasterPT() {    
        initComponents();
    }
    
    public MasterPT(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        model = new ModelMasterPT();
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        kodept = homePage.dapatkanKodePT();
        kodePerguruanTinggiField.setText(kodept);
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
        
        data[5] = "now()";
        return data;
    }
    
    private void initAutoComplete() {
        kompletTextFieldCombo = new KompletTextFieldCombo(kon, 
                jenisPTField, jenispt);        
        String [][] datanya = {{"U", "Universitas"},
            {"I","Institut"},{"S", "Sekolah Tinggi"},
            {"A","Akademik"},{"P","Politeknik"}}; 
        kompletTextFieldCombo.initDataUtkAutoComplete(datanya);  
        jenispt = kompletTextFieldCombo.dapatkanTextField();
        jenisPTField = kompletTextFieldCombo.dapatkanComboBox();        
        
        kompletTextFieldCombo2 = new KompletTextFieldCombo(kon, 
                kategoriPTField, kategoript);        
        String [][] datanya2 = {{"1", "Negeri"},{"2","Swasta"}}; 
        kompletTextFieldCombo2.initDataUtkAutoComplete(datanya2);  
        kategoript = kompletTextFieldCombo2.dapatkanTextField();
        kategoriPTField = kompletTextFieldCombo2.dapatkanComboBox();        
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                statusPTField, statuspt);        
        String [][] datanya3 = {{"A", "Aktif"},{"H","Hapus/tutup"}}; 
        kompletTextFieldCombo3.initDataUtkAutoComplete(datanya3);  
        statuspt = kompletTextFieldCombo3.dapatkanTextField();
        statusPTField = kompletTextFieldCombo3.dapatkanComboBox();        
        
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
                kodeakreditasiField, kodeakreditasi);        
        query = "SELECT \"Kode_akreditasi\", \"Status_akreditasi\", \"Keterangan_akreditasi\" " +
                " from pdpt_dev.\"TREF_AKREDITASI\""; 
        kompletTextFieldCombo7.initDataUtkAutoComplete(query);        
        kodeakreditasi = kompletTextFieldCombo7.dapatkanTextField();
        kodeakreditasiField = kompletTextFieldCombo7.dapatkanComboBox();
        
        kompletTextFieldCombo8 = new KompletTextFieldCombo(kon, 
                kodekopertisField, kodekopertis);        
        query = "select \"Kode_kopertis\", \"Deskripsi\" " +
                " from pdpt_dev.\"TREF_KOPERTIS\""; 
        kompletTextFieldCombo8.initDataUtkAutoComplete(query);        
        kodekopertis = kompletTextFieldCombo8.dapatkanTextField();
        kodekopertisField = kompletTextFieldCombo8.dapatkanComboBox();
        
        kompletTextFieldCombo9 = new KompletTextFieldCombo(kon, 
                kodewilayahField, kodewilayah);        
        query = "select \"Kode_wilayah\", \"Nama_wilayah\" " +
                " from pdpt_dev.\"TREF_WILAYAH\""; 
        kompletTextFieldCombo9.initDataUtkAutoComplete(query);        
        kodewilayah = kompletTextFieldCombo9.dapatkanTextField();
        kodewilayahField = kompletTextFieldCombo9.dapatkanComboBox();    
        
        kompletTextFieldCombo10 = new KompletTextFieldCombo(kon, 
                kodekementrianField, kodekementrian);        
        query = "SELECT \"Kode_kementrian\", \"Nama_kementrian\" " +
                " from pdpt_dev.\"TREF_KEMENTRIAN\""; 
        kompletTextFieldCombo10.initDataUtkAutoComplete(query);        
        kodekementrian = kompletTextFieldCombo10.dapatkanTextField();
        kodekementrianField = kompletTextFieldCombo10.dapatkanComboBox();
        
        kompletTextFieldCombo11 = new KompletTextFieldCombo(kon, 
                statusvalidasiField, statusvalidasi);        
        String [][] datanya11 = {{"1", "Belum diverifikasi"},
            {"2","Sudah diverifikasi namun masih terdapat data yang invalid"},
            {"3", "Sudah diverifikasi dan data sudah valid"}}; 
        kompletTextFieldCombo11.initDataUtkAutoComplete(datanya11);        
        statusvalidasi = kompletTextFieldCombo11.dapatkanTextField();
        statusvalidasiField = kompletTextFieldCombo11.dapatkanComboBox();
        
        kompletTextFieldCombo12 = new KompletTextFieldCombo(kon, 
                kodeBadanHukumField, kodebadanhukum);        
        query = "SELECT \"Kode_badan_hukum\", \"Nama_badan_hukum\" " +
                " FROM pdpt_dev.\"TMST_BADAN_HUKUM\""; 
        kompletTextFieldCombo12.initDataUtkAutoComplete(query);        
        kodebadanhukum = kompletTextFieldCombo12.dapatkanTextField();
        kodeBadanHukumField = kompletTextFieldCombo12.dapatkanComboBox();
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
    
    private void inisialisasiData() {
        kon = homePage.dapatkanKoneksiDB();
        query = "select * from pdpt_dev.\"TMST_PERGURUAN_TINGGI\" where \"Kode_perguruan_tinggi\"='"+kodept+"'";
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public MasterPT(String [][] data, String [] kolom) {
        this();
        tampilkanDataKeTabel(data, kolom);
    }
     public void DapatDataDariTable(int row){
        
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
        kodePerguruanTinggiLabel = new javax.swing.JLabel();
        kodeBadanHukumLabel = new javax.swing.JLabel();
        namaPtLabel = new javax.swing.JLabel();
        singkatanLabel = new javax.swing.JLabel();
        jenisPTLabel = new javax.swing.JLabel();
        kategoriPTLabel = new javax.swing.JLabel();
        statusPTLabel = new javax.swing.JLabel();
        tglawalberdiriLabel = new javax.swing.JLabel();
        alamatLabel = new javax.swing.JLabel();
        kodekotaLabel = new javax.swing.JLabel();
        kodeprovinsiLabel = new javax.swing.JLabel();
        kodenegaraLabel = new javax.swing.JLabel();
        kodeposLabel = new javax.swing.JLabel();
        teleponeLabel = new javax.swing.JLabel();
        faxLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        websiteLabel = new javax.swing.JLabel();
        noaktaskpendirianLabel = new javax.swing.JLabel();
        tanggalaktaLabel = new javax.swing.JLabel();
        kodeakreditasiLabel = new javax.swing.JLabel();
        noSKBANLabel = new javax.swing.JLabel();
        tglSkBanLabel = new javax.swing.JLabel();
        visiLabel = new javax.swing.JLabel();
        misiLabel = new javax.swing.JLabel();
        tujuanLabel = new javax.swing.JLabel();
        sasaranLabel = new javax.swing.JLabel();
        kodekopertisLabel = new javax.swing.JLabel();
        kodewilayahLabel = new javax.swing.JLabel();
        seleksipenerimaanLabel = new javax.swing.JLabel();
        polakepemimpinanLabel = new javax.swing.JLabel();
        sistempengelolaanLabel = new javax.swing.JLabel();
        sistempenjaminanmutuLabel = new javax.swing.JLabel();
        alasantransfermahasiswaLabel = new javax.swing.JLabel();
        perandpembelajaranLabel = new javax.swing.JLabel();
        perandpenyusunankurikulumLabel = new javax.swing.JLabel();
        perandsuasanaakademikLabel = new javax.swing.JLabel();
        pemanfaatanTIKLabel = new javax.swing.JLabel();
        penyebaraninformasiLabel = new javax.swing.JLabel();
        rencanapengembanganSILabel = new javax.swing.JLabel();
        evaluasilulusanLabel = new javax.swing.JLabel();
        mekanismeevaluasilulusanLabel = new javax.swing.JLabel();
        kodekementrianLabel = new javax.swing.JLabel();
        TGLmulaiefektifLabel = new javax.swing.JLabel();
        tglAkhirEfektifLabel = new javax.swing.JLabel();
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
        IDlogauditLabel13 = new javax.swing.JLabel();
        IDlogauditLabel14 = new javax.swing.JLabel();
        IDlogauditLabel15 = new javax.swing.JLabel();
        IDlogauditLabel16 = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodePerguruanTinggiField = new javax.swing.JFormattedTextField();
        kodeBadanHukumField = new javax.swing.JComboBox();
        namaPtField = new javax.swing.JTextField();
        singkatanField = new javax.swing.JTextField();
        jenisPTField = new javax.swing.JComboBox();
        kategoriPTField = new javax.swing.JComboBox();
        statusPTField = new javax.swing.JComboBox();
        tglawalberdiriField = new com.toedter.calendar.JDateChooser();
        alamatField = new javax.swing.JTextField();
        kodekotaField = new javax.swing.JComboBox();
        kodeprovinsiField = new javax.swing.JComboBox();
        kodenegaraField = new javax.swing.JComboBox();
        kodeposField = new javax.swing.JFormattedTextField();
        teleponeField = new javax.swing.JFormattedTextField();
        faxField = new javax.swing.JFormattedTextField();
        emailField = new javax.swing.JTextField();
        websiteField = new javax.swing.JTextField();
        noaktaskpendirianField = new javax.swing.JTextField();
        tanggalaktaField = new com.toedter.calendar.JDateChooser();
        kodeakreditasiField = new javax.swing.JComboBox();
        noSKBANField = new javax.swing.JTextField();
        tglSkBanField = new com.toedter.calendar.JDateChooser();
        visiScrollPane = new javax.swing.JScrollPane();
        visiField = new javax.swing.JTextArea();
        misiScrollPane = new javax.swing.JScrollPane();
        misiField = new javax.swing.JTextArea();
        tujuanScrollPane = new javax.swing.JScrollPane();
        tujuanField = new javax.swing.JTextArea();
        sasaranScrollPane = new javax.swing.JScrollPane();
        sasaranField = new javax.swing.JTextArea();
        kodekopertisField = new javax.swing.JComboBox();
        kodewilayahField = new javax.swing.JComboBox();
        seleksipenerimaanScrollPane = new javax.swing.JScrollPane();
        seleksipenerimaanField = new javax.swing.JTextArea();
        polakepemimpinanScrollPane = new javax.swing.JScrollPane();
        polakepemimpinanField = new javax.swing.JTextArea();
        sistempengelolaanFieldScrollPane = new javax.swing.JScrollPane();
        sistempengelolaanField = new javax.swing.JTextArea();
        sistempenjaminanmutuFieldScrollPane = new javax.swing.JScrollPane();
        sistempenjaminanmutuField = new javax.swing.JTextArea();
        alasantransfermahasiswaFieldScrollPane = new javax.swing.JScrollPane();
        alasantransfermahasiswaField = new javax.swing.JTextArea();
        perandpembelajaranFieldScrollPane = new javax.swing.JScrollPane();
        perandpembelajaranField = new javax.swing.JTextArea();
        perandpenyusunankurikulumScrollPane = new javax.swing.JScrollPane();
        perandpenyusunankurikulumField = new javax.swing.JTextArea();
        perandsuasanaakademikScrollPane = new javax.swing.JScrollPane();
        perandsuasanaakademikField = new javax.swing.JTextArea();
        pemanfaatanTIKFieldScrollPane = new javax.swing.JScrollPane();
        pemanfaatanTIKField = new javax.swing.JTextArea();
        penyebaraninformasiFieldScrollPane = new javax.swing.JScrollPane();
        penyebaraninformasiField = new javax.swing.JTextArea();
        rencanapengembanganSIFieldScrollPane = new javax.swing.JScrollPane();
        rencanapengembanganSIField = new javax.swing.JTextArea();
        evaluasilulusanFieldScrollPane = new javax.swing.JScrollPane();
        evaluasilulusanField = new javax.swing.JTextArea();
        mekanismeevaluasilulusanFieldScrollPane = new javax.swing.JScrollPane();
        mekanismeevaluasilulusanField = new javax.swing.JTextArea();
        kodekementrianField = new javax.swing.JComboBox();
        TGLmulaiefektifField = new com.toedter.calendar.JDateChooser();
        tglAkhirEfektifField = new com.toedter.calendar.JDateChooser();
        statusvalidasiField = new javax.swing.JComboBox();
        IDlogauditField = new javax.swing.JFormattedTextField();
        logoField = new javax.swing.JTextField();
        rektorField = new javax.swing.JTextField();
        niprektorField = new javax.swing.JTextField();
        pr1Field = new javax.swing.JTextField();
        nippr1Field = new javax.swing.JTextField();
        pr2Field = new javax.swing.JTextField();
        nippr2Field = new javax.swing.JTextField();
        pr3Field = new javax.swing.JTextField();
        nippr3Field = new javax.swing.JTextField();
        pr4Field = new javax.swing.JTextField();
        nippr4Field = new javax.swing.JTextField();
        sekertarisField = new javax.swing.JTextField();
        deskripsiField = new javax.swing.JTextField();
        userField = new javax.swing.JTextField();
        createdField = new javax.swing.JTextField();
        uploadField = new javax.swing.JTextField();
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

        panelLabel.setLayout(new java.awt.GridLayout(62, 0));

        kodePerguruanTinggiLabel.setText("Kode Perguruan Tinggi:");
        panelLabel.add(kodePerguruanTinggiLabel);

        kodeBadanHukumLabel.setText("Kode Badan Hukum:");
        panelLabel.add(kodeBadanHukumLabel);

        namaPtLabel.setText("Nama Pt:");
        panelLabel.add(namaPtLabel);

        singkatanLabel.setText("Singkatan:");
        panelLabel.add(singkatanLabel);

        jenisPTLabel.setText("Jenis PT:");
        panelLabel.add(jenisPTLabel);

        kategoriPTLabel.setText("Kategori PT:");
        panelLabel.add(kategoriPTLabel);

        statusPTLabel.setText("Status PT:");
        panelLabel.add(statusPTLabel);

        tglawalberdiriLabel.setText("Tgl Awal Berdiri:");
        panelLabel.add(tglawalberdiriLabel);

        alamatLabel.setText("Alamat:");
        panelLabel.add(alamatLabel);

        kodekotaLabel.setText("Kode Kota:");
        panelLabel.add(kodekotaLabel);

        kodeprovinsiLabel.setText("Kode Provinsi:");
        panelLabel.add(kodeprovinsiLabel);

        kodenegaraLabel.setText("Kode Negara:");
        panelLabel.add(kodenegaraLabel);

        kodeposLabel.setText("Kode Pos:");
        panelLabel.add(kodeposLabel);

        teleponeLabel.setText("Telepone:");
        panelLabel.add(teleponeLabel);

        faxLabel.setText("Fax:");
        panelLabel.add(faxLabel);

        emailLabel.setText("Email:");
        panelLabel.add(emailLabel);

        websiteLabel.setText("Website:");
        panelLabel.add(websiteLabel);

        noaktaskpendirianLabel.setText("No Akta SK Pendirian:");
        panelLabel.add(noaktaskpendirianLabel);

        tanggalaktaLabel.setText("Tanggal Akta:");
        panelLabel.add(tanggalaktaLabel);

        kodeakreditasiLabel.setText("Kode Akreditasi:");
        panelLabel.add(kodeakreditasiLabel);

        noSKBANLabel.setText("No SKBAN:");
        panelLabel.add(noSKBANLabel);

        tglSkBanLabel.setText("Tgl Sk Ban:");
        panelLabel.add(tglSkBanLabel);

        visiLabel.setText("Visi:");
        panelLabel.add(visiLabel);

        misiLabel.setText("Misi:");
        panelLabel.add(misiLabel);

        tujuanLabel.setText("Tujuan:");
        panelLabel.add(tujuanLabel);

        sasaranLabel.setText("Sasaran:");
        panelLabel.add(sasaranLabel);

        kodekopertisLabel.setText("Kode Kopertis:");
        panelLabel.add(kodekopertisLabel);

        kodewilayahLabel.setText("Kode Wilayah:");
        panelLabel.add(kodewilayahLabel);

        seleksipenerimaanLabel.setText("Seleksi Penerimaan:");
        panelLabel.add(seleksipenerimaanLabel);

        polakepemimpinanLabel.setText("Pola Kepemimpinan:");
        panelLabel.add(polakepemimpinanLabel);

        sistempengelolaanLabel.setText("Sistem Pengelolaan:");
        panelLabel.add(sistempengelolaanLabel);

        sistempenjaminanmutuLabel.setText("Sistem Penjaminan Mutu:");
        panelLabel.add(sistempenjaminanmutuLabel);

        alasantransfermahasiswaLabel.setText("Alasan Transfer Mahasiswa:");
        panelLabel.add(alasantransfermahasiswaLabel);

        perandpembelajaranLabel.setText("Peran PT dalam Pembelajaran:");
        panelLabel.add(perandpembelajaranLabel);

        perandpenyusunankurikulumLabel.setText("Peran PT dalam Penyusunan Kurikulum:");
        panelLabel.add(perandpenyusunankurikulumLabel);

        perandsuasanaakademikLabel.setText("Peran PT dalam Suasana Akademik:");
        panelLabel.add(perandsuasanaakademikLabel);

        pemanfaatanTIKLabel.setText("Pemanfaatan TIK:");
        panelLabel.add(pemanfaatanTIKLabel);

        penyebaraninformasiLabel.setText("Penyebaran Informasi:");
        panelLabel.add(penyebaraninformasiLabel);

        rencanapengembanganSILabel.setText("Rencana Pengembangan SI:");
        panelLabel.add(rencanapengembanganSILabel);

        evaluasilulusanLabel.setText("Evaluasi Lulusan:");
        panelLabel.add(evaluasilulusanLabel);

        mekanismeevaluasilulusanLabel.setText("Mekanisme Evaluasi Lulusan:");
        panelLabel.add(mekanismeevaluasilulusanLabel);

        kodekementrianLabel.setText("Kode Kementrian:");
        panelLabel.add(kodekementrianLabel);

        TGLmulaiefektifLabel.setText("TGL Mulai Efektif:");
        panelLabel.add(TGLmulaiefektifLabel);

        tglAkhirEfektifLabel.setText("Tgl Akhir Efektif:");
        panelLabel.add(tglAkhirEfektifLabel);

        statusvalidasiLabel.setText("Status Validasi:");
        panelLabel.add(statusvalidasiLabel);

        IDlogauditLabel.setText("Idlogaudit:");
        panelLabel.add(IDlogauditLabel);

        IDlogauditLabel1.setText("File Logo :");
        panelLabel.add(IDlogauditLabel1);

        IDlogauditLabel2.setText("Nama Rektor:");
        panelLabel.add(IDlogauditLabel2);

        IDlogauditLabel3.setText("Nip Rektor");
        panelLabel.add(IDlogauditLabel3);

        IDlogauditLabel4.setText("Nama Wakil 1:");
        panelLabel.add(IDlogauditLabel4);

        IDlogauditLabel5.setText("Nip Wakil 1");
        panelLabel.add(IDlogauditLabel5);

        IDlogauditLabel6.setText("Nama Wakil 2:");
        panelLabel.add(IDlogauditLabel6);

        IDlogauditLabel7.setText("Nip Wakil 2 :");
        panelLabel.add(IDlogauditLabel7);

        IDlogauditLabel8.setText("Nama Wakil 3 :");
        panelLabel.add(IDlogauditLabel8);

        IDlogauditLabel9.setText("Nip Wakil 3");
        panelLabel.add(IDlogauditLabel9);

        IDlogauditLabel10.setText("Nama wakil 4 :");
        panelLabel.add(IDlogauditLabel10);

        IDlogauditLabel11.setText("Nip Wakil 4 :");
        panelLabel.add(IDlogauditLabel11);

        IDlogauditLabel12.setText("Nama Sekertaris :");
        panelLabel.add(IDlogauditLabel12);

        IDlogauditLabel13.setText("Deskripsi SIngkat");
        panelLabel.add(IDlogauditLabel13);

        IDlogauditLabel14.setText("User :");
        panelLabel.add(IDlogauditLabel14);

        IDlogauditLabel15.setText("Created At");
        panelLabel.add(IDlogauditLabel15);

        IDlogauditLabel16.setText("Uploaded At");
        panelLabel.add(IDlogauditLabel16);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(62, 0));

        try {
            kodePerguruanTinggiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodePerguruanTinggiField);

        panelTextField.add(kodeBadanHukumField);
        panelTextField.add(namaPtField);
        panelTextField.add(singkatanField);

        panelTextField.add(jenisPTField);

        panelTextField.add(kategoriPTField);

        panelTextField.add(statusPTField);
        panelTextField.add(tglawalberdiriField);
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

        teleponeField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("####################"))));
        panelTextField.add(teleponeField);

        faxField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("####################"))));
        panelTextField.add(faxField);
        panelTextField.add(emailField);
        panelTextField.add(websiteField);
        panelTextField.add(noaktaskpendirianField);
        panelTextField.add(tanggalaktaField);

        panelTextField.add(kodeakreditasiField);
        panelTextField.add(noSKBANField);
        panelTextField.add(tglSkBanField);

        visiField.setColumns(20);
        visiField.setRows(1);
        visiScrollPane.setViewportView(visiField);

        panelTextField.add(visiScrollPane);

        misiField.setColumns(20);
        misiField.setRows(1);
        misiScrollPane.setViewportView(misiField);

        panelTextField.add(misiScrollPane);

        tujuanField.setColumns(20);
        tujuanField.setRows(1);
        tujuanScrollPane.setViewportView(tujuanField);

        panelTextField.add(tujuanScrollPane);

        sasaranField.setColumns(20);
        sasaranField.setRows(1);
        sasaranScrollPane.setViewportView(sasaranField);

        panelTextField.add(sasaranScrollPane);

        panelTextField.add(kodekopertisField);

        panelTextField.add(kodewilayahField);

        seleksipenerimaanField.setColumns(20);
        seleksipenerimaanField.setRows(1);
        seleksipenerimaanScrollPane.setViewportView(seleksipenerimaanField);

        panelTextField.add(seleksipenerimaanScrollPane);

        polakepemimpinanField.setColumns(20);
        polakepemimpinanField.setRows(1);
        polakepemimpinanScrollPane.setViewportView(polakepemimpinanField);

        panelTextField.add(polakepemimpinanScrollPane);

        sistempengelolaanField.setColumns(20);
        sistempengelolaanField.setRows(1);
        sistempengelolaanFieldScrollPane.setViewportView(sistempengelolaanField);

        panelTextField.add(sistempengelolaanFieldScrollPane);

        sistempenjaminanmutuField.setColumns(20);
        sistempenjaminanmutuField.setRows(1);
        sistempenjaminanmutuFieldScrollPane.setViewportView(sistempenjaminanmutuField);

        panelTextField.add(sistempenjaminanmutuFieldScrollPane);

        alasantransfermahasiswaField.setColumns(20);
        alasantransfermahasiswaField.setRows(1);
        alasantransfermahasiswaFieldScrollPane.setViewportView(alasantransfermahasiswaField);

        panelTextField.add(alasantransfermahasiswaFieldScrollPane);

        perandpembelajaranField.setColumns(20);
        perandpembelajaranField.setRows(1);
        perandpembelajaranFieldScrollPane.setViewportView(perandpembelajaranField);

        panelTextField.add(perandpembelajaranFieldScrollPane);

        perandpenyusunankurikulumField.setColumns(20);
        perandpenyusunankurikulumField.setRows(1);
        perandpenyusunankurikulumScrollPane.setViewportView(perandpenyusunankurikulumField);

        panelTextField.add(perandpenyusunankurikulumScrollPane);

        perandsuasanaakademikField.setColumns(20);
        perandsuasanaakademikField.setRows(1);
        perandsuasanaakademikScrollPane.setViewportView(perandsuasanaakademikField);

        panelTextField.add(perandsuasanaakademikScrollPane);

        pemanfaatanTIKField.setColumns(20);
        pemanfaatanTIKField.setRows(1);
        pemanfaatanTIKFieldScrollPane.setViewportView(pemanfaatanTIKField);

        panelTextField.add(pemanfaatanTIKFieldScrollPane);

        penyebaraninformasiField.setColumns(20);
        penyebaraninformasiField.setRows(1);
        penyebaraninformasiFieldScrollPane.setViewportView(penyebaraninformasiField);

        panelTextField.add(penyebaraninformasiFieldScrollPane);

        rencanapengembanganSIField.setColumns(20);
        rencanapengembanganSIField.setRows(1);
        rencanapengembanganSIFieldScrollPane.setViewportView(rencanapengembanganSIField);

        panelTextField.add(rencanapengembanganSIFieldScrollPane);

        evaluasilulusanField.setColumns(20);
        evaluasilulusanField.setRows(1);
        evaluasilulusanFieldScrollPane.setViewportView(evaluasilulusanField);

        panelTextField.add(evaluasilulusanFieldScrollPane);

        mekanismeevaluasilulusanField.setColumns(20);
        mekanismeevaluasilulusanField.setRows(1);
        mekanismeevaluasilulusanFieldScrollPane.setViewportView(mekanismeevaluasilulusanField);

        panelTextField.add(mekanismeevaluasilulusanFieldScrollPane);

        panelTextField.add(kodekementrianField);
        panelTextField.add(TGLmulaiefektifField);
        panelTextField.add(tglAkhirEfektifField);

        panelTextField.add(statusvalidasiField);

        IDlogauditField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        panelTextField.add(IDlogauditField);
        panelTextField.add(logoField);
        panelTextField.add(rektorField);
        panelTextField.add(niprektorField);
        panelTextField.add(pr1Field);
        panelTextField.add(nippr1Field);
        panelTextField.add(pr2Field);
        panelTextField.add(nippr2Field);
        panelTextField.add(pr3Field);
        panelTextField.add(nippr3Field);
        panelTextField.add(pr4Field);
        panelTextField.add(nippr4Field);
        panelTextField.add(sekertarisField);
        panelTextField.add(deskripsiField);
        panelTextField.add(userField);
        panelTextField.add(createdField);
        panelTextField.add(uploadField);

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

    private void persiapanEntriDataBaru() {
        //kodePerguruanTinggiField.setText("" + homePage.dapatkanKodePT());//utk pdpt pt
        kodePerguruanTinggiField.setText("");//utk pdpt dikti
        kodebadanhukum.setText("");
        namaPtField.setText("");
        singkatanField.setText("");
        jenispt.setText("");
        kategoript.setText("");
        statuspt.setText("");                
        tglawalberdiriField.setDate(null);
        alamatField.setText("");
        kodekota.setText("");
        kodeprovinsi.setText("");
        kodenegara.setText("");
        kodeposField.setText("");
        teleponeField.setText("");
        faxField.setText("");
        emailField.setText("");
        websiteField.setText("");
        noaktaskpendirianField.setText("");        
        tanggalaktaField.setDate(null);
        kodeakreditasi.setText("");
        noSKBANField.setText("");
        tglSkBanField.setDate(null);
        visiField.setText("");
        misiField.setText("");
        tujuanField.setText("");
        sasaranField.setText("");
        kodekopertis.setText("");
        kodewilayah.setText("");
        seleksipenerimaanField.setText("");
        polakepemimpinanField.setText("");
        sistempengelolaanField.setText("");
        sistempenjaminanmutuField.setText("");
        alasantransfermahasiswaField.setText("");
        perandpembelajaranField.setText("");
        perandpenyusunankurikulumField.setText("");
        perandsuasanaakademikField.setText("");
        pemanfaatanTIKField.setText("");
        penyebaraninformasiField.setText("");
        rencanapengembanganSIField.setText("");
        evaluasilulusanField.setText("");
        mekanismeevaluasilulusanField.setText("");
        kodekementrian.setText("");
        TGLmulaiefektifField.setDate(null);
        tglAkhirEfektifField.setDate(null);
        statusvalidasi.setText("");        
        IDlogauditField.setText("");
        uploadField.setText("");
        rektorField.setText("");
        niprektorField.setText("");
        pr1Field.setText("");
        nippr1Field.setText("");
        pr2Field.setText("");
        nippr2Field.setText("");
        pr3Field.setText("");
        nippr3Field.setText("");
        nippr4Field.setText("");
        nippr4Field.setText("");
        sekertarisField.setText("");
        deskripsiField.setText("");
        createdField.setText("");
        uploadField.setText("");
        
        kodePerguruanTinggiField.requestFocus();
    }
    
    private void tampilkanData(int row) {
        System.out.println("baris yg dipilih utk diUPDATE: "  + row);
        persiapanEntriDataBaru();
        kodePerguruanTinggiField.setText(tabel.getValueAt(row, 0).toString());
        kodebadanhukum.setText(tabel.getValueAt(row, 1).toString());
        namaPtField.setText(tabel.getValueAt(row, 2).toString());
        singkatanField.setText(tabel.getValueAt(row, 3).toString());
        jenispt.setText(tabel.getValueAt(row, 4).toString());
        kategoript.setText(tabel.getValueAt(row, 5).toString());
        statuspt.setText(tabel.getValueAt(row, 6).toString());
        
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
        tglawalberdiriField.setCalendar(kalendar);
        
        alamatField.setText(tabel.getValueAt(row, 8).toString());
        kodekota.setText(tabel.getValueAt(row, 9).toString());
        kodeprovinsi.setText(tabel.getValueAt(row, 10).toString());
        kodenegara.setText(tabel.getValueAt(row, 11).toString());
        kodeposField.setText(tabel.getValueAt(row, 12).toString());
        teleponeField.setText(tabel.getValueAt(row, 13).toString());
        faxField.setText(tabel.getValueAt(row, 14).toString());
        emailField.setText(tabel.getValueAt(row, 15).toString());
        websiteField.setText(tabel.getValueAt(row, 16).toString());
        noaktaskpendirianField.setText(tabel.getValueAt(row, 17).toString());
        
        strKalendar = tabel.getValueAt(row, 18).toString();
        tahun = Integer.parseInt( strKalendar.substring(0, 4) );
        System.out.println("" + strKalendar.substring(0, 4)  + " tahun: " + tahun);
        intBulan = Integer.parseInt( strKalendar.substring(5, 7) );
        System.out.println("" + strKalendar.substring(5, 7)  + " intBulan: " + intBulan);
        hari = Integer.parseInt( strKalendar.substring(8, 10) );
        System.out.println("" + strKalendar.substring(8, 10)  + " hari: " + hari);
        kalendar.set(tahun, intBulan - 1, hari); 
        tanggalaktaField.setCalendar(kalendar);
        
        kodeakreditasi.setText(tabel.getValueAt(row, 19).toString());
        noSKBANField.setText(tabel.getValueAt(row, 20).toString());
        
        strKalendar = tabel.getValueAt(row, 21).toString();
        tahun = Integer.parseInt( strKalendar.substring(0, 4) );
        System.out.println("" + strKalendar.substring(0, 4)  + " tahun: " + tahun);
        intBulan = Integer.parseInt( strKalendar.substring(5, 7) );
        System.out.println("" + strKalendar.substring(5, 7)  + " intBulan: " + intBulan);
        hari = Integer.parseInt( strKalendar.substring(8, 10) );
        System.out.println("" + strKalendar.substring(8, 10)  + " hari: " + hari);
        kalendar.set(tahun, intBulan - 1, hari); 
        tglSkBanField.setCalendar(kalendar);
        
        visiField.setText(tabel.getValueAt(row, 22).toString());
        misiField.setText(tabel.getValueAt(row, 23).toString());
        tujuanField.setText(tabel.getValueAt(row, 24).toString());
        sasaranField.setText(tabel.getValueAt(row, 25).toString());
        kodekopertis.setText(tabel.getValueAt(row, 26).toString());
        kodewilayah.setText(tabel.getValueAt(row, 27).toString());
        seleksipenerimaanField.setText(tabel.getValueAt(row, 28).toString());
        polakepemimpinanField.setText(tabel.getValueAt(row, 29).toString());
        sistempengelolaanField.setText(tabel.getValueAt(row, 30).toString());
        sistempenjaminanmutuField.setText(tabel.getValueAt(row, 31).toString());
        alasantransfermahasiswaField.setText(tabel.getValueAt(row, 32).toString());
        perandpembelajaranField.setText(tabel.getValueAt(row, 33).toString());
        perandpenyusunankurikulumField.setText(tabel.getValueAt(row, 34).toString());
        perandsuasanaakademikField.setText(tabel.getValueAt(row, 35).toString());
        pemanfaatanTIKField.setText(tabel.getValueAt(row, 36).toString());
        penyebaraninformasiField.setText(tabel.getValueAt(row, 37).toString());
        rencanapengembanganSIField.setText(tabel.getValueAt(row, 38).toString());
        evaluasilulusanField.setText(tabel.getValueAt(row, 39).toString());
        mekanismeevaluasilulusanField.setText(tabel.getValueAt(row, 40).toString());
        kodekementrian.setText(tabel.getValueAt(row, 41).toString());
        
        strKalendar = tabel.getValueAt(row, 42).toString();
        tahun = Integer.parseInt( strKalendar.substring(0, 4) );
        System.out.println("" + strKalendar.substring(0, 4)  + " tahun: " + tahun);
        intBulan = Integer.parseInt( strKalendar.substring(5, 7) );
        System.out.println("" + strKalendar.substring(5, 7)  + " intBulan: " + intBulan);
        hari = Integer.parseInt( strKalendar.substring(8, 10) );
        System.out.println("" + strKalendar.substring(8, 10)  + " hari: " + hari);
        kalendar.set(tahun, intBulan - 1, hari); 
        TGLmulaiefektifField.setCalendar(kalendar);
        
        strKalendar = tabel.getValueAt(row, 43).toString();
        tahun = Integer.parseInt( strKalendar.substring(0, 4) );
        System.out.println("" + strKalendar.substring(0, 4)  + " tahun: " + tahun);
        intBulan = Integer.parseInt( strKalendar.substring(5, 7) );
        System.out.println("" + strKalendar.substring(5, 7)  + " intBulan: " + intBulan);
        hari = Integer.parseInt( strKalendar.substring(8, 10) );
        System.out.println("" + strKalendar.substring(8, 10)  + " hari: " + hari);
        kalendar.set(tahun, intBulan - 1, hari); 
        tglAkhirEfektifField.setCalendar(kalendar);
        
        statusvalidasi.setText(tabel.getValueAt(row, 44).toString());
        //IDlogauditField.setText(tabel.getValueAt(row, 45).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
        data[0] = kodePerguruanTinggiField.getText();
        
        String datakodebadanhukummentah = kodebadanhukum.getText();
        System.out.println("data kode badan hukum mentah: " + datakodebadanhukummentah);
        String [] datakodebadanhukum = datakodebadanhukummentah.split(" ");
        System.out.println("data kode badan hukum yg dipilih: " + datakodebadanhukum[0]);
        data[1] = datakodebadanhukum[0];
        
        data[2] = (namaPtField.getText()!=null?namaPtField.getText():"");
        data[3] = (singkatanField.getText()!=null?singkatanField.getText():"");
        
        String datajenisptmentah = jenispt.getText();
        System.out.println("data jenis pt mentah: " + datajenisptmentah);
        String [] datajenispt = datajenisptmentah.split(" ");
        System.out.println("data jenis pt yg dipilih: " + datajenispt[0]);
        data[4] = datajenispt[0];
        
        String datakategoriptmentah = kategoript.getText();
        System.out.println("data kategori pt mentah: " + datakategoriptmentah);
        String [] datakategoript = datakategoriptmentah.split(" ");
        System.out.println("data kategori pt yg dipilih: " + datakategoript[0]);
        data[5] = datakategoript[0];
        
        String datastatusptmentah = statuspt.getText();
        System.out.println("data status pt mentah: " + datastatusptmentah);
        String [] datastatuspt = datastatusptmentah.split(" ");
        System.out.println("data status pt yg dipilih: " + datastatuspt[0]);
        data[6] = datastatuspt[0];
        
        kalendar = tglawalberdiriField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
//        String data7 = hari + " " + bulan + " " + tahun;
        String data7 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        System.out.println("date "+data7);
        data[7] = (data7!=null?data7:"");
        data[8] = (alamatField.getText()!=null?alamatField.getText():"");
        
        String datakodekotamentah = kodekota.getText();
        System.out.println("data kode kota mentah: " + datakodekotamentah);
        String [] datakodekota = datakodekotamentah.split(" ");
        System.out.println("data kode kota yg dipilih: " + datakodekota[0]);
        data[9] = datakodekota[0];
        
        String datakodeprovinsimentah = kodeprovinsi.getText();
        System.out.println("data kode provinsi mentah: " + datakodeprovinsimentah);
        String [] datakodeprovinsi = datakodeprovinsimentah.split(" ");
        System.out.println("data kode provinsi yg dipilih: " + datakodeprovinsi[0]);
        data[10] = datakodeprovinsi[0];
        
        String datakodenegaramentah = kodenegara.getText();
        System.out.println("data kode negara mentah: " + datakodenegaramentah);
        String [] datakodenegara = datakodenegaramentah.split(" ");
        System.out.println("data kode negara yg dipilih: " + datakodenegara[0]);
        data[11] = datakodenegara[0];
        
        data[12] = (!kodeposField.getText().equals("     ")?kodeposField.getText():"0");
        data[13] = (!teleponeField.getText().equals("")?teleponeField.getText():"0");
        data[14] = (!faxField.getText().equals("")?faxField.getText():"0");
        data[15] = (emailField.getText()!=null?emailField.getText():"");
        data[16] = (websiteField.getText()!=null?websiteField.getText():"");
        data[17] = (noaktaskpendirianField.getText()!=null?noaktaskpendirianField.getText():"");
        kalendar = tanggalaktaField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
//        String data7 = hari + " " + bulan + " " + tahun;
        String data18 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        System.out.println("date "+data18);
        data[18] = (data18!=null?data18:"");
        
        String datakodeakreditasimentah = kodeakreditasi.getText();
        System.out.println("data kode akreditasi mentah: " + datakodeakreditasimentah);
        String [] datakodeakreditasi = datakodeakreditasimentah.split(" ");
        System.out.println("data kode akreditasi yg dipilih: " + datakodeakreditasi[0]);
        data[19] = datakodeakreditasi[0];
        
        data[20] = (noSKBANField.getText()!=null?noSKBANField.getText():"");
        kalendar = tglSkBanField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
//        String data7 = hari + " " + bulan + " " + tahun;
        String data21 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        System.out.println("date "+data21);
        data[21] = (data21!=null?data21:"");
        
        data[22] = (visiField.getText()!=null?visiField.getText():"");
        data[23] = (misiField.getText()!=null?misiField.getText():"");
        data[24] = (tujuanField.getText()!=null?tujuanField.getText():"");
        data[25] = (sasaranField.getText()!=null?sasaranField.getText():"");
        
        String datakodekopertismentah = kodekopertis.getText();
        System.out.println("data kode kopertis mentah: " + datakodekopertismentah);
        String [] datakodekopertis = datakodekopertismentah.split(" ");
        System.out.println("data kode kopertis yg dipilih: " + datakodekopertis[0]);
        data[26] = datakodekopertis[0];
        
        String datakodewilayahmentah = kodewilayah.getText();
        System.out.println("data kode wilayah mentah: " + datakodewilayahmentah);
        String [] datakodewilayah = datakodewilayahmentah.split(" ");
        System.out.println("data kode wilayah yg dipilih: " + datakodewilayah[0]);
        data[27] = datakodewilayah[0];
        
        data[28] = (seleksipenerimaanField.getText()!=null?seleksipenerimaanField.getText():"");
        data[29] = (polakepemimpinanField.getText()!=null?polakepemimpinanField.getText():"");
        data[30] = (sistempengelolaanField.getText()!=null?sistempengelolaanField.getText():"");
        data[31] = (sistempenjaminanmutuField.getText()!=null?sistempenjaminanmutuField.getText():"");
        data[32] = (alasantransfermahasiswaField.getText()!=null?alasantransfermahasiswaField.getText():"");
        data[33] = (perandpembelajaranField.getText()!=null?perandpembelajaranField.getText():"");
        data[34] = (perandpenyusunankurikulumField.getText()!=null?perandpenyusunankurikulumField.getText():"");
        data[35] = (perandsuasanaakademikField.getText()!=null?perandsuasanaakademikField.getText():"");
        data[36] = (pemanfaatanTIKField.getText()!=null?pemanfaatanTIKField.getText():"");
        data[37] = (penyebaraninformasiField.getText()!=null?penyebaraninformasiField.getText():"");
        data[38] = (rencanapengembanganSIField.getText()!=null?rencanapengembanganSIField.getText():"");
        data[39] = (evaluasilulusanField.getText()!=null?evaluasilulusanField.getText():"");
        data[40] = (mekanismeevaluasilulusanField.getText()!=null?mekanismeevaluasilulusanField.getText():"");
        
        String datakodekementrianmentah = kodekementrian.getText();
        System.out.println("data kode kementrian mentah: " + datakodekementrianmentah);
        String [] datakodekementrian = datakodekementrianmentah.split(" ");
        System.out.println("data kode kementrian yg dipilih: " + datakodekementrian[0]);
        data[41] = datakodekementrian[0];
        
        kalendar = TGLmulaiefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
//        String data7 = hari + " " + bulan + " " + tahun;
        String data42 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        System.out.println("date "+data42);
        data[42] = (data42!=null?data42:"");
        
        kalendar = tglAkhirEfektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data43 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        System.out.println("date "+data43);
        data[43] = (data43!=null?data43:"");
        
        String datastatusvalidasimentah = statusvalidasi.getText();
        System.out.println("data status validasi mentah: " + datastatusvalidasimentah);
        String [] datastatusvalidasi = datastatusvalidasimentah.split(" ");
        System.out.println("data status validasi yg dipilih: " + datastatusvalidasi[0]);
        data[44] = datastatusvalidasi[0];
        
        data[45] = "" + idlogaudit;
        data[46] = uploadField.getText();
        data[47] = rektorField.getText();
        data[48] = niprektorField.getText();
        data[49] = pr1Field.getText();
        data[50] = nippr1Field.getText();
        data[51] = pr2Field.getText();
        data[52] = nippr2Field.getText();
        data[53] = pr3Field.getText();
        data[54] = nippr3Field.getText();
        data[55] = pr4Field.getText();
        data[56] = nippr4Field.getText();
        data[57] = sekertarisField.getText();
        data[58] = deskripsiField.getText();
        data[59] = userField.getText();
        data[60] = createdField.getText();
        data[61] = uploadField.getText();
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];        
        
        String datakodebadanhukummentah = kodebadanhukum.getText();
        System.out.println("data kode badan hukum mentah: " + datakodebadanhukummentah);
        String [] datakodebadanhukum = datakodebadanhukummentah.split(" ");
        System.out.println("data kode badan hukum yg dipilih: " + datakodebadanhukum[0]);
        data[0] = datakodebadanhukum[0];
        
        data[1] = (namaPtField.getText()!=null?namaPtField.getText():"");
        data[2] = (singkatanField.getText()!=null?singkatanField.getText():"");
        
        String datajenisptmentah = jenispt.getText();
        System.out.println("data jenis pt mentah: " + datajenisptmentah);
        String [] datajenispt = datajenisptmentah.split(" ");
        System.out.println("data jenis pt yg dipilih: " + datajenispt[0]);
        data[3] = datajenispt[0];
        
        String datakategoriptmentah = kategoript.getText();
        System.out.println("data kategori pt mentah: " + datakategoriptmentah);
        String [] datakategoript = datakategoriptmentah.split(" ");
        System.out.println("data kategori pt yg dipilih: " + datakategoript[0]);
        data[4] = datakategoript[0];
        
        String datastatusptmentah = statuspt.getText();
        System.out.println("data status pt mentah: " + datastatusptmentah);
        String [] datastatuspt = datastatusptmentah.split(" ");
        System.out.println("data status pt yg dipilih: " + datastatuspt[0]);
        data[5] = datastatuspt[0];
        
        kalendar = tglawalberdiriField.getCalendar();
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
       String data6 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        System.out.println("date "+data6);
        data[6] = data6;
        
        data[7] = (alamatField.getText()!=null?alamatField.getText():"X");
        
        String datakodekotamentah = kodekota.getText();
        System.out.println("data kode kota mentah: " + datakodekotamentah);
        String [] datakodekota = datakodekotamentah.split(" ");
        System.out.println("data kode kota yg dipilih: " + datakodekota[0]);
        data[8] = datakodekota[0];
        
        String datakodeprovinsimentah = kodeprovinsi.getText();
        System.out.println("data kode provinsi mentah: " + datakodeprovinsimentah);
        String [] datakodeprovinsi = datakodeprovinsimentah.split(" ");
        System.out.println("data kode provinsi yg dipilih: " + datakodeprovinsi[0]);
        data[9] = datakodeprovinsi[0];
        
        String datakodenegaramentah = kodenegara.getText();
        System.out.println("data kode negara mentah: " + datakodenegaramentah);
        String [] datakodenegara = datakodenegaramentah.split(" ");
        System.out.println("data kode negara yg dipilih: " + datakodenegara[0]);
        data[10] = datakodenegara[0];
        
        data[11] = (!kodeposField.getText().equals("     ")?kodeposField.getText():"0");
        data[12] = (!teleponeField.getText().equals("")?teleponeField.getText():"0");
        data[13] = (!faxField.getText().equals("")?faxField.getText():"0");
        data[14] = (emailField.getText()!=null?emailField.getText():"X");
        data[15] = (websiteField.getText()!=null?websiteField.getText():"X");
        data[16] = (noaktaskpendirianField.getText()!=null?noaktaskpendirianField.getText():"X");
        
        kalendar = tanggalaktaField.getCalendar();
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data17 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        System.out.println("date "+data17);
        data[17] = data17;
        
        String datakodeakreditasimentah = kodeakreditasi.getText();
        System.out.println("data kode akreditasi mentah: " + datakodeakreditasimentah);
        String [] datakodeakreditasi = datakodeakreditasimentah.split(" ");
        System.out.println("data kode akreditasi yg dipilih: " + datakodeakreditasi[0]);
        data[18] = datakodeakreditasi[0];
        
        data[19] = (noSKBANField.getText()!=null?noSKBANField.getText():"X");
        
        kalendar = tglSkBanField.getCalendar();
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data20 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        System.out.println("date "+data20);
        data[20] = data20;
        
        data[21] = (visiField.getText()!=null?visiField.getText():"X");
        data[22] = (misiField.getText()!=null?misiField.getText():"X");
        data[23] = (tujuanField.getText()!=null?tujuanField.getText():"X");
        data[24] = (sasaranField.getText()!=null?sasaranField.getText():"X");
        
        String datakodekopertismentah = kodekopertis.getText();
        System.out.println("data kode kopertis mentah: " + datakodekopertismentah);
        String [] datakodekopertis = datakodekopertismentah.split(" ");
        System.out.println("data kode kopertis yg dipilih: " + datakodekopertis[0]);
        data[25] = datakodekopertis[0];
        data[25] = kodekopertis.getText();
        
        String datakodewilayahmentah = kodewilayah.getText();
        System.out.println("data kode wilayah mentah: " + datakodewilayahmentah);
        String [] datakodewilayah = datakodewilayahmentah.split(" ");
        System.out.println("data kode wilayah yg dipilih: " + datakodewilayah[0]);
        data[26] = datakodewilayah[0];
        
        data[27] = (seleksipenerimaanField.getText()!=null?seleksipenerimaanField.getText():"X");
        data[28] = (polakepemimpinanField.getText()!=null?polakepemimpinanField.getText():"X");
        data[29] = (sistempengelolaanField.getText()!=null?sistempengelolaanField.getText():"X");
        data[30] = (sistempenjaminanmutuField.getText()!=null?sistempenjaminanmutuField.getText():"X");
        data[31] = (alasantransfermahasiswaField.getText()!=null?alasantransfermahasiswaField.getText():"X");
        data[32] = (perandpembelajaranField.getText()!=null?perandpembelajaranField.getText():"X");
        data[33] = (perandpenyusunankurikulumField.getText()!=null?perandpenyusunankurikulumField.getText():"X");
        data[34] = (perandsuasanaakademikField.getText()!=null?perandsuasanaakademikField.getText():"X");
        data[35] = (pemanfaatanTIKField.getText()!=null?pemanfaatanTIKField.getText():"X");
        data[36] = (penyebaraninformasiField.getText()!=null?penyebaraninformasiField.getText():"X");
        data[37] = (rencanapengembanganSIField.getText()!=null?rencanapengembanganSIField.getText():"X");
        data[38] = (evaluasilulusanField.getText()!=null?evaluasilulusanField.getText():"X");
        data[39] = (mekanismeevaluasilulusanField.getText()!=null?mekanismeevaluasilulusanField.getText():"X");
        
        String datakodekementrianmentah = kodekementrian.getText();
        System.out.println("data kode kementrian mentah: " + datakodekementrianmentah);
        String [] datakodekementrian = datakodekementrianmentah.split(" ");
        System.out.println("data kode kementrian yg dipilih: " + datakodekementrian[0]);
        data[40] = datakodekementrian[0];
        
        kalendar = TGLmulaiefektifField.getCalendar();
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data41 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        System.out.println("date "+data41);
        data[41] = data41;
        kalendar = tglAkhirEfektifField.getCalendar();
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data42 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        System.out.println("date "+data42);
        data[42] = data42;
        
        String datastatusvalidasimentah = statusvalidasi.getText();
        System.out.println("data status validasi mentah: " + datastatusvalidasimentah);
        String [] datastatusvalidasi = datastatusvalidasimentah.split(" ");
        System.out.println("data status validasi yg dipilih: " + datastatusvalidasi[0]);
        data[43] = datastatusvalidasi[0];
        
        data[44] = "" + idlogaudit;
         data[45] = uploadField.getText();
        data[46] = rektorField.getText();
        data[47] = niprektorField.getText();
        data[48] = pr1Field.getText();
        data[49] = nippr1Field.getText();
        data[50] = pr2Field.getText();
        data[51] = nippr2Field.getText();
        data[52] = pr3Field.getText();
        data[53] = nippr3Field.getText();
        data[54] = pr4Field.getText();
        data[55] = nippr4Field.getText();
        data[56] = sekertarisField.getText();
        data[57] = deskripsiField.getText();
        data[58] = userField.getText();
        data[59] = createdField.getText();
        data[60] = uploadField.getText();
        data[62] = kodePerguruanTinggiField.getText();
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[1];
        data[0] = kodePerguruanTinggiField.getText();
        return data;   
    }
    
    private void buttonBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBaruActionPerformed
        // TODO add your handling code here:
        persiapanEntriDataBaru();
    }//GEN-LAST:event_buttonBaruActionPerformed

    private void buttonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInsertActionPerformed
        // TODO add your handling code here:
        initIDLogAudit();
        String query = "insert into pdpt_dev.\"TMST_PERGURUAN_TINGGI\" " +
        " values(?,?,?,?,?,?,?,?,?,?, " +
        "?,?,?,?,?,?,?,?,?,?, " +
        "?,?,?,?,?,?,?,?,?,?, " +
        "?,?,?,?,?,?,?,?,?,?, " +
        " ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukInsert() );
        int hasil = model.insertAktifitasDOsen(query,
            dapatkanNilaiUntukInsert() );
        if (hasil == 1) {
            query = "select * from pdpt_dev.\"TMST_PERGURUAN_TINGGI\" ";
            kon.selectData(query);
            tampilkanDataKeTabel(kon.dapatkanData(), kon.dapatkanKolom());
            
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
        String query = "update pdpt_dev.\"TMST_PERGURUAN_TINGGI\" " +
        " SET \"KODE_BADAN_HUKUM\"=?, " +
        " \"NAMA_PT\"=?, " +
        " \"Singkatan\"=?, " +
        " \"Jenis_PT\"=?, " +
        " \"Kategori_PT\"=?, " +
        " \"Status_PT\"=?, " +
        " \"Tgl_awal_berdiri\"=?, " +
        " \"Alamat\"=?, " +
        " \"Kode_kota\" = ?, " +
        " \"Kode_provinsi\"=?, " +
        " \"Kode_negara\"=?, " +
        " \"Kode_pos\"=?, " +
        " \"Telepone\"=?, " +
        " \"Fax\"=?, " +
        " \"Email\"=?, " +
        " \"Website\"=?, " +
        " \"No_akta_sk_pendirian\"=?, " +
        " \"Tanggal_akta\"=?, " +
        " \"Kode_akreditasi\"=?, " +
        " \"No_SK_BAN\"=?, " +
        " \"TGL_SK_BAN\"=?, " +
        " \"visi\"=?, " +
        " \"misi\"=?, " +
        " \"Tujuan\"=?, " +
        " \"Sasaran\"=?, " +
        " \"Kode_kopertis\"=?, " +
        " \"Kode_wilayah\"=?, " +
        " \"Seleksi_penerimaan\"=?, " +
        " \"Pola_kepemimpinan\"=?, " +
        " \"Sistem_pengelolaan\"=?, " +
        " \"Sistem_penjaminan_mutu\"=?, " +
        " \"Alasan_transfer_mahasiswa\"=?, " +
        " \"Peran_d_pembelajaran\"=?, " +
        " \"Peran_d_penyusunan_kurikulum\"=?, " +
        " \"Peran_d_suasana_akademik\"=?, " +
        " \"pemanfaatan_TIK\"=?, " +
        " \"Penyebaran_informasi\"=?, " +
        " \"Rencana_pengembangan_SI\"=?, " +
        " \"Evaluasi_lulusan\"=?, " +
        " \"Mekanisme_evaluasi_lulusan\"=?, " +
        " \"Kode_kementrian\"=?, " +
        " \"TGL_mulai_efektif\"=?, " +
        " \"Tgl_Akhir_Efektif\"=?, " +
        " \"Status_validasi\"=?, " +
        " \"file_logo\"=?, " +
        " \"Nama_rektor\"=?, " +
        " \"Nip_rektor\"=?, " +
        " \"Nama_wakil_1\"=?, " +
        " \"Nip_wakil_1\"=?, " +
        " \"Nama_wakil_2\"=?, " +
        " \"Nip_wakil_2\"=?, " +
        " \"Nama_wakil_3\"=?, " +
        " \"Nip_wakil_3\"=?, " +
        " \"Nama_wakil_4\"=?, " +
        " \"Nip_wakil_4\"=?, " +
        " \"Nama_sekertaris\"=?, " +
        " \"Deskripsi_singkat\"=?, " +
        " \"user\"=?, " +
        " \"created_at\"=?, " +
        " \"uploaded_at\"=?, " +
        " \"Id_log_audit\"=? " +
        " WHERE \"Kode_perguruan_tinggi\"=? ";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukUpdate() );
        int hasil = model.updateAktifitasDOsen(query,
            dapatkanNilaiUntukUpdate() );
        if (hasil == 1) {
            query = "select * from pdpt_dev.\"TMST_PERGURUAN_TINGGI\" ";
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
        String query = "delete from pdpt_dev.\"TMST_PERGURUAN_TINGGI\" WHERE \"KODE_PERGURUAN_TINGGI\"=?";
        
        String [] pkDataYgDihapus = dapatkanNilaiUntukDelete();
        
        int hasil = model.delete(query,
            dapatkanNilaiUntukDelete() );
        if (hasil == 1) {
            query = "select * from pdpt_dev.\"TMST_PERGURUAN_TINGGI\" ";
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
    private javax.swing.JLabel IDlogauditLabel13;
    private javax.swing.JLabel IDlogauditLabel14;
    private javax.swing.JLabel IDlogauditLabel15;
    private javax.swing.JLabel IDlogauditLabel16;
    private javax.swing.JLabel IDlogauditLabel2;
    private javax.swing.JLabel IDlogauditLabel3;
    private javax.swing.JLabel IDlogauditLabel4;
    private javax.swing.JLabel IDlogauditLabel5;
    private javax.swing.JLabel IDlogauditLabel6;
    private javax.swing.JLabel IDlogauditLabel7;
    private javax.swing.JLabel IDlogauditLabel8;
    private javax.swing.JLabel IDlogauditLabel9;
    private com.toedter.calendar.JDateChooser TGLmulaiefektifField;
    private javax.swing.JLabel TGLmulaiefektifLabel;
    private javax.swing.JTextField alamatField;
    private javax.swing.JLabel alamatLabel;
    private javax.swing.JTextArea alasantransfermahasiswaField;
    private javax.swing.JScrollPane alasantransfermahasiswaFieldScrollPane;
    private javax.swing.JLabel alasantransfermahasiswaLabel;
    private Widget.Button buttonBaru;
    private Widget.Button buttonDelete;
    private Widget.Button buttonFirst;
    private Widget.Button buttonInsert;
    private Widget.Button buttonLast;
    private Widget.Button buttonNext;
    private Widget.Button buttonPrev;
    private Widget.Button buttonUpdate;
    private javax.swing.JTextField createdField;
    private javax.swing.JTextField deskripsiField;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextArea evaluasilulusanField;
    private javax.swing.JScrollPane evaluasilulusanFieldScrollPane;
    private javax.swing.JLabel evaluasilulusanLabel;
    private javax.swing.JFormattedTextField faxField;
    private javax.swing.JLabel faxLabel;
    private javax.swing.JScrollPane gulungPanelAtribut;
    private javax.swing.JScrollPane gulungTabel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JComboBox jenisPTField;
    private javax.swing.JLabel jenisPTLabel;
    private javax.swing.JComboBox kategoriPTField;
    private javax.swing.JLabel kategoriPTLabel;
    private javax.swing.JComboBox kodeBadanHukumField;
    private javax.swing.JLabel kodeBadanHukumLabel;
    private javax.swing.JFormattedTextField kodePerguruanTinggiField;
    private javax.swing.JLabel kodePerguruanTinggiLabel;
    private javax.swing.JComboBox kodeakreditasiField;
    private javax.swing.JLabel kodeakreditasiLabel;
    private javax.swing.JComboBox kodekementrianField;
    private javax.swing.JLabel kodekementrianLabel;
    private javax.swing.JComboBox kodekopertisField;
    private javax.swing.JLabel kodekopertisLabel;
    private javax.swing.JComboBox kodekotaField;
    private javax.swing.JLabel kodekotaLabel;
    private javax.swing.JComboBox kodenegaraField;
    private javax.swing.JLabel kodenegaraLabel;
    private javax.swing.JFormattedTextField kodeposField;
    private javax.swing.JLabel kodeposLabel;
    private javax.swing.JComboBox kodeprovinsiField;
    private javax.swing.JLabel kodeprovinsiLabel;
    private javax.swing.JComboBox kodewilayahField;
    private javax.swing.JLabel kodewilayahLabel;
    private javax.swing.JTextField logoField;
    private javax.swing.JTextArea mekanismeevaluasilulusanField;
    private javax.swing.JScrollPane mekanismeevaluasilulusanFieldScrollPane;
    private javax.swing.JLabel mekanismeevaluasilulusanLabel;
    private javax.swing.JTextArea misiField;
    private javax.swing.JLabel misiLabel;
    private javax.swing.JScrollPane misiScrollPane;
    private javax.swing.JTextField namaPtField;
    private javax.swing.JLabel namaPtLabel;
    private javax.swing.JTextField nippr1Field;
    private javax.swing.JTextField nippr2Field;
    private javax.swing.JTextField nippr3Field;
    private javax.swing.JTextField nippr4Field;
    private javax.swing.JTextField niprektorField;
    private javax.swing.JTextField noSKBANField;
    private javax.swing.JLabel noSKBANLabel;
    private javax.swing.JTextField noaktaskpendirianField;
    private javax.swing.JLabel noaktaskpendirianLabel;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JTextArea pemanfaatanTIKField;
    private javax.swing.JScrollPane pemanfaatanTIKFieldScrollPane;
    private javax.swing.JLabel pemanfaatanTIKLabel;
    private javax.swing.JTextArea penyebaraninformasiField;
    private javax.swing.JScrollPane penyebaraninformasiFieldScrollPane;
    private javax.swing.JLabel penyebaraninformasiLabel;
    private javax.swing.JTextArea perandpembelajaranField;
    private javax.swing.JScrollPane perandpembelajaranFieldScrollPane;
    private javax.swing.JLabel perandpembelajaranLabel;
    private javax.swing.JTextArea perandpenyusunankurikulumField;
    private javax.swing.JLabel perandpenyusunankurikulumLabel;
    private javax.swing.JScrollPane perandpenyusunankurikulumScrollPane;
    private javax.swing.JTextArea perandsuasanaakademikField;
    private javax.swing.JLabel perandsuasanaakademikLabel;
    private javax.swing.JScrollPane perandsuasanaakademikScrollPane;
    private javax.swing.JTextArea polakepemimpinanField;
    private javax.swing.JLabel polakepemimpinanLabel;
    private javax.swing.JScrollPane polakepemimpinanScrollPane;
    private javax.swing.JTextField pr1Field;
    private javax.swing.JTextField pr2Field;
    private javax.swing.JTextField pr3Field;
    private javax.swing.JTextField pr4Field;
    private javax.swing.JTextField rektorField;
    private javax.swing.JTextArea rencanapengembanganSIField;
    private javax.swing.JScrollPane rencanapengembanganSIFieldScrollPane;
    private javax.swing.JLabel rencanapengembanganSILabel;
    private javax.swing.JTextArea sasaranField;
    private javax.swing.JLabel sasaranLabel;
    private javax.swing.JScrollPane sasaranScrollPane;
    private javax.swing.JTextField sekertarisField;
    private javax.swing.JTextArea seleksipenerimaanField;
    private javax.swing.JLabel seleksipenerimaanLabel;
    private javax.swing.JScrollPane seleksipenerimaanScrollPane;
    private javax.swing.JTextField singkatanField;
    private javax.swing.JLabel singkatanLabel;
    private javax.swing.JTextArea sistempengelolaanField;
    private javax.swing.JScrollPane sistempengelolaanFieldScrollPane;
    private javax.swing.JLabel sistempengelolaanLabel;
    private javax.swing.JTextArea sistempenjaminanmutuField;
    private javax.swing.JScrollPane sistempenjaminanmutuFieldScrollPane;
    private javax.swing.JLabel sistempenjaminanmutuLabel;
    private javax.swing.JComboBox statusPTField;
    private javax.swing.JLabel statusPTLabel;
    private javax.swing.JComboBox statusvalidasiField;
    private javax.swing.JLabel statusvalidasiLabel;
    private javax.swing.JTable tabel;
    private com.toedter.calendar.JDateChooser tanggalaktaField;
    private javax.swing.JLabel tanggalaktaLabel;
    private javax.swing.JFormattedTextField teleponeField;
    private javax.swing.JLabel teleponeLabel;
    private com.toedter.calendar.JDateChooser tglAkhirEfektifField;
    private javax.swing.JLabel tglAkhirEfektifLabel;
    private com.toedter.calendar.JDateChooser tglSkBanField;
    private javax.swing.JLabel tglSkBanLabel;
    private com.toedter.calendar.JDateChooser tglawalberdiriField;
    private javax.swing.JLabel tglawalberdiriLabel;
    private javax.swing.JTextArea tujuanField;
    private javax.swing.JLabel tujuanLabel;
    private javax.swing.JScrollPane tujuanScrollPane;
    private javax.swing.JTextField uploadField;
    private javax.swing.JTextField userField;
    private javax.swing.JTextArea visiField;
    private javax.swing.JLabel visiLabel;
    private javax.swing.JScrollPane visiScrollPane;
    private javax.swing.JTextField websiteField;
    private javax.swing.JLabel websiteLabel;
    // End of variables declaration//GEN-END:variables
}
