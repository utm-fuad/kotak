/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelMasterPegawai;
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
public class MasterPegawai extends javax.swing.JPanel {
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
    
    private JTextField jeniskelamin;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo1;
    private JTextField kodekota;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    private JTextField kodeprovinsi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    private JTextField kodejabatanakademik;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo4;
    private JTextField kodedirektoratjenderal;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo5;
    private JTextField kodedirektorat;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo6;
    private JTextField kodebagian;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo7;
    private JTextField kodeseksi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo8;
    private JTextField pangkat;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo9;
    private JTextField golongan;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo10;
    private JTextField status;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo11;
    private JTextField kodejenjangpendidikan;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo12;
    Model.ModelMasterPegawai model;
    private int idlogaudit;
    private String kueri;

    /**
     * Creates new form PanelHalaman1
     */
    public MasterPegawai() {    
        initComponents();
    }
    
    public MasterPegawai(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        model = new ModelMasterPegawai();
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        
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
                jeniskelaminField, jeniskelamin);        
        String [][] datanya1 = {{"L", "Laki-laki"},
            {"P","Perempuan"}}; 
        kompletTextFieldCombo1.initDataUtkAutoComplete(datanya1);
        jeniskelamin = kompletTextFieldCombo1.dapatkanTextField();
        jeniskelaminField = kompletTextFieldCombo1.dapatkanComboBox();
        
        kompletTextFieldCombo2 = new KompletTextFieldCombo(kon, 
                kodekotaField, kodekota);        
        query = "SELECT \"Kode_kota\", \"Nama_kabupaten\" " +
                " from pdpt_dev.\"TREF_KOTA\""; 
        kompletTextFieldCombo2.initDataUtkAutoComplete(query);        
        kodekota = kompletTextFieldCombo2.dapatkanTextField();
        kodekotaField = kompletTextFieldCombo2.dapatkanComboBox();                
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                kodeprovinsiField, kodeprovinsi);        
        query = "SELECT \"Kode_provinsi\", \"Nama_provinsi\" " +
                " FROM pdpt_dev.\"TREF_PROVINSI\" " +
                " WHERE \"Kode_negara\"='1'"; 
        kompletTextFieldCombo3.initDataUtkAutoComplete(query);        
        kodeprovinsi = kompletTextFieldCombo3.dapatkanTextField();
        kodeprovinsiField = kompletTextFieldCombo3.dapatkanComboBox();
        
        kompletTextFieldCombo4 = new KompletTextFieldCombo(kon, 
                kodejabatanakademikField, kodejabatanakademik);        
        query = "select \"Kode_jabatan_akademik\", \"Deskripsi\" " +
                " from pdpt_dev.\"TREF_JABATAN_AKADEMIK\""; 
        kompletTextFieldCombo4.initDataUtkAutoComplete(query);        
        kodejabatanakademik = kompletTextFieldCombo4.dapatkanTextField();
        kodejabatanakademikField = kompletTextFieldCombo4.dapatkanComboBox();
        
        kompletTextFieldCombo5 = new KompletTextFieldCombo(kon, 
                kodedirektoratjenderalField, kodedirektoratjenderal);        
        query = "select \"Kode_direktorat_jenderal\", \"Nama_direktorat_jenderal\" " +
                " from pdpt_dev.\"TREF_DIREKTORAT_JENDERAL\""; 
        kompletTextFieldCombo5.initDataUtkAutoComplete(query);        
        kodedirektoratjenderal = kompletTextFieldCombo5.dapatkanTextField();
        kodedirektoratjenderalField = kompletTextFieldCombo5.dapatkanComboBox();
        
        kompletTextFieldCombo6 = new KompletTextFieldCombo(kon, 
                kodedirektoratField, kodedirektorat);        
        query = "select \"Kode_direktorat\", \"Nama_direktorat\" " +
                " from pdpt_dev.\"TREF_DIREKTORAT\""; 
        kompletTextFieldCombo6.initDataUtkAutoComplete(query);        
        kodedirektorat = kompletTextFieldCombo6.dapatkanTextField();
        kodedirektoratField = kompletTextFieldCombo6.dapatkanComboBox();
        
        kompletTextFieldCombo7 = new KompletTextFieldCombo(kon, 
                kodebagianField, kodebagian);        
        query = "select \"Kode_bagian\", \"Nama_bagian\" " +
                " from pdpt_dev.\"TREF_BAGIAN\""; 
        kompletTextFieldCombo7.initDataUtkAutoComplete(query);        
        kodebagian = kompletTextFieldCombo7.dapatkanTextField();
        kodebagianField = kompletTextFieldCombo7.dapatkanComboBox();
        
        kompletTextFieldCombo8 = new KompletTextFieldCombo(kon, 
                kodeseksiField, kodeseksi);        
        query = "select \"Kode_seksi\", \"Nama_seksi\" " +
                " from pdpt_dev.\"TREF_SEKSI\""; 
        kompletTextFieldCombo8.initDataUtkAutoComplete(query);        
        kodeseksi = kompletTextFieldCombo8.dapatkanTextField();
        kodeseksiField = kompletTextFieldCombo8.dapatkanComboBox();
        
        kompletTextFieldCombo9 = new KompletTextFieldCombo(kon, 
                pangkatField, pangkat);        
        String [][] datanya9 = {{"A",""},{"B",""},{"C",""},{"D", ""}}; 
        kompletTextFieldCombo9.initDataUtkAutoComplete(datanya9);
        pangkat = kompletTextFieldCombo9.dapatkanTextField();
        pangkatField = kompletTextFieldCombo9.dapatkanComboBox();
        
        kompletTextFieldCombo10 = new KompletTextFieldCombo(kon, 
                golonganField, golongan);        
        String [][] datanya10 = {{"I", ""},{"II", ""},{"III", ""},{"IV", ""}}; 
        kompletTextFieldCombo10.initDataUtkAutoComplete(datanya10);
        golongan = kompletTextFieldCombo10.dapatkanTextField();
        golonganField = kompletTextFieldCombo10.dapatkanComboBox();
        
        kompletTextFieldCombo11 = new KompletTextFieldCombo(kon, 
                statusField, status);        
        String [][] datanya11 = {{"1", "Tetap"},{"2", "Kontrak"}}; 
        kompletTextFieldCombo11.initDataUtkAutoComplete(datanya11);
        status = kompletTextFieldCombo11.dapatkanTextField();
        statusField = kompletTextFieldCombo11.dapatkanComboBox();
                
        kompletTextFieldCombo12 = new KompletTextFieldCombo(kon, 
                kodejenjangpendidikanField, kodejenjangpendidikan);        
        query = "select \"Kode_jenjang_pendidikan\", \"Deskripsi\" " +
                " from \"TREF_JENJANG_PENDIDIKAN\""; 
        kompletTextFieldCombo12.initDataUtkAutoComplete(query);        
        kodejenjangpendidikan = kompletTextFieldCombo12.dapatkanTextField();
        kodejenjangpendidikanField = kompletTextFieldCombo12.dapatkanComboBox();
    }
    
    private void inisialisasiData() {
        kon = homePage.dapatkanKoneksiDB();
        query = "select * from pdpt_dev.\"TMST_PEGAWAI\"";
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public MasterPegawai(String [][] data, String [] kolom) {
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
        kodepegawaiLabel = new javax.swing.JLabel();
        NIPlamaLabel = new javax.swing.JLabel();
        NIPbaruLabel = new javax.swing.JLabel();
        namapegawaiLabel = new javax.swing.JLabel();
        jeniskelaminLabel = new javax.swing.JLabel();
        tempatlahirLabel = new javax.swing.JLabel();
        tanggallahirLabel = new javax.swing.JLabel();
        alamatLabel = new javax.swing.JLabel();
        kodekotaLabel = new javax.swing.JLabel();
        kodeprovinsiLabel = new javax.swing.JLabel();
        kodeposLabel = new javax.swing.JLabel();
        kodejabatanfungsionalLabel = new javax.swing.JLabel();
        kodedirektoratjendralLabel = new javax.swing.JLabel();
        kodedirektoratLabel = new javax.swing.JLabel();
        kodebagianLabel = new javax.swing.JLabel();
        kodeseksiLabel = new javax.swing.JLabel();
        pangkatLabel = new javax.swing.JLabel();
        golonganLabel = new javax.swing.JLabel();
        teleponrumahLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        handphoneLabel = new javax.swing.JLabel();
        noSKpengangkatanLabel = new javax.swing.JLabel();
        tanggalmasukLabel = new javax.swing.JLabel();
        tanggalberhentiLabel = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        kodejenjangpendidikanLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        IDlogauditLabel1 = new javax.swing.JLabel();
        IDlogauditLabel2 = new javax.swing.JLabel();
        IDlogauditLabel3 = new javax.swing.JLabel();
        IDlogauditLabel4 = new javax.swing.JLabel();
        IDlogauditLabel5 = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodepegawaiField = new javax.swing.JFormattedTextField();
        NIPlamaField = new javax.swing.JFormattedTextField();
        NIPbaruField = new javax.swing.JFormattedTextField();
        namapegawaiField = new javax.swing.JTextField();
        jeniskelaminField = new javax.swing.JComboBox();
        tempatlahirField = new javax.swing.JTextField();
        tanggallahirField = new com.toedter.calendar.JDateChooser();
        alamatField = new javax.swing.JTextField();
        kodekotaField = new javax.swing.JComboBox();
        kodeprovinsiField = new javax.swing.JComboBox();
        kodeposField = new javax.swing.JFormattedTextField();
        kodejabatanakademikField = new javax.swing.JComboBox();
        kodedirektoratjenderalField = new javax.swing.JComboBox();
        kodedirektoratField = new javax.swing.JComboBox();
        kodebagianField = new javax.swing.JComboBox();
        kodeseksiField = new javax.swing.JComboBox();
        pangkatField = new javax.swing.JComboBox();
        golonganField = new javax.swing.JComboBox();
        teleponrumahField = new javax.swing.JFormattedTextField();
        emailField = new javax.swing.JTextField();
        handphoneField = new javax.swing.JFormattedTextField();
        noSKpengangkatanField = new javax.swing.JTextField();
        tanggalmasukField = new com.toedter.calendar.JDateChooser();
        tanggalberhentiField = new com.toedter.calendar.JDateChooser();
        statusField = new javax.swing.JComboBox();
        kodejenjangpendidikanField = new javax.swing.JComboBox();
        IDlogauditField = new javax.swing.JTextField();
        TMTField = new javax.swing.JTextField();
        tanggalMasukTMTiField1 = new com.toedter.calendar.JDateChooser();
        tanggalberhentiTMTField = new com.toedter.calendar.JDateChooser();
        gelardepanField = new javax.swing.JTextField();
        gelarbelakangField = new javax.swing.JTextField();
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

        panelLabel.setLayout(new java.awt.GridLayout(32, 0));

        kodepegawaiLabel.setText("Kode Pegawai:");
        panelLabel.add(kodepegawaiLabel);

        NIPlamaLabel.setText("NIP Lama:");
        panelLabel.add(NIPlamaLabel);

        NIPbaruLabel.setText("NIP Baru:");
        panelLabel.add(NIPbaruLabel);

        namapegawaiLabel.setText("Nama Pegawai:");
        panelLabel.add(namapegawaiLabel);

        jeniskelaminLabel.setText("Jenis Kelamin:");
        panelLabel.add(jeniskelaminLabel);

        tempatlahirLabel.setText("Tempat Lahir:");
        panelLabel.add(tempatlahirLabel);

        tanggallahirLabel.setText("Tanggal Lahir:");
        panelLabel.add(tanggallahirLabel);

        alamatLabel.setText("Alamat:");
        panelLabel.add(alamatLabel);

        kodekotaLabel.setText("Kode Kota:");
        panelLabel.add(kodekotaLabel);

        kodeprovinsiLabel.setText("Kode Provinsi:");
        panelLabel.add(kodeprovinsiLabel);

        kodeposLabel.setText("Kode Pos:");
        panelLabel.add(kodeposLabel);

        kodejabatanfungsionalLabel.setText("Kode Jabatan Fungsional:");
        panelLabel.add(kodejabatanfungsionalLabel);

        kodedirektoratjendralLabel.setText("Kode Direktorat Jendral:");
        panelLabel.add(kodedirektoratjendralLabel);

        kodedirektoratLabel.setText("Kode Direktorat:");
        panelLabel.add(kodedirektoratLabel);

        kodebagianLabel.setText("Kode Bagian:");
        panelLabel.add(kodebagianLabel);

        kodeseksiLabel.setText("Kode Seksi:");
        panelLabel.add(kodeseksiLabel);

        pangkatLabel.setText("Pangkat:");
        panelLabel.add(pangkatLabel);

        golonganLabel.setText("Golongan:");
        panelLabel.add(golonganLabel);

        teleponrumahLabel.setText("Telepon Rumah:");
        panelLabel.add(teleponrumahLabel);

        emailLabel.setText("Email:");
        panelLabel.add(emailLabel);

        handphoneLabel.setText("Handphone:");
        panelLabel.add(handphoneLabel);

        noSKpengangkatanLabel.setText("No SK Pengangkatan:");
        panelLabel.add(noSKpengangkatanLabel);

        tanggalmasukLabel.setText("Tanggal Masuk:");
        panelLabel.add(tanggalmasukLabel);

        tanggalberhentiLabel.setText("Tanggal Berhenti:");
        panelLabel.add(tanggalberhentiLabel);

        statusLabel.setText("Status:");
        panelLabel.add(statusLabel);

        kodejenjangpendidikanLabel.setText("Kode Jenjang Pendidikan:");
        panelLabel.add(kodejenjangpendidikanLabel);

        IDlogauditLabel.setText("ID Log Audit:");
        panelLabel.add(IDlogauditLabel);

        IDlogauditLabel1.setText("TEXT TMT :");
        panelLabel.add(IDlogauditLabel1);

        IDlogauditLabel2.setText("Tanggal Masuk :");
        panelLabel.add(IDlogauditLabel2);

        IDlogauditLabel3.setText("Tanggal Berhenti :");
        panelLabel.add(IDlogauditLabel3);

        IDlogauditLabel4.setText("Gelar Depan :");
        panelLabel.add(IDlogauditLabel4);

        IDlogauditLabel5.setText("Gelar Belakang :");
        panelLabel.add(IDlogauditLabel5);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(32, 0));

        kodepegawaiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("######"))));
        panelTextField.add(kodepegawaiField);

        NIPlamaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#########"))));
        NIPlamaField.setToolTipText("9 digit NP Lama");
        panelTextField.add(NIPlamaField);

        NIPbaruField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##################"))));
        NIPbaruField.setToolTipText("30 digit NIP Baru");
        panelTextField.add(NIPbaruField);
        panelTextField.add(namapegawaiField);

        panelTextField.add(jeniskelaminField);
        panelTextField.add(tempatlahirField);
        panelTextField.add(tanggallahirField);
        panelTextField.add(alamatField);

        panelTextField.add(kodekotaField);

        panelTextField.add(kodeprovinsiField);

        try {
            kodeposField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodeposField);

        panelTextField.add(kodejabatanakademikField);

        panelTextField.add(kodedirektoratjenderalField);

        panelTextField.add(kodedirektoratField);

        panelTextField.add(kodebagianField);

        panelTextField.add(kodeseksiField);

        panelTextField.add(pangkatField);

        panelTextField.add(golonganField);

        teleponrumahField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("####################"))));
        panelTextField.add(teleponrumahField);
        panelTextField.add(emailField);

        handphoneField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#####################"))));
        panelTextField.add(handphoneField);
        panelTextField.add(noSKpengangkatanField);
        panelTextField.add(tanggalmasukField);
        panelTextField.add(tanggalberhentiField);

        panelTextField.add(statusField);

        panelTextField.add(kodejenjangpendidikanField);
        panelTextField.add(IDlogauditField);
        panelTextField.add(TMTField);
        panelTextField.add(tanggalMasukTMTiField1);
        panelTextField.add(tanggalberhentiTMTField);
        panelTextField.add(gelardepanField);
        panelTextField.add(gelarbelakangField);

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
        kodepegawaiField.setText("");
        NIPlamaField.setText("");
        NIPbaruField.setText("");
        namapegawaiField.setText("");
        jeniskelamin.setText("");
        tempatlahirField.setText("");
        tanggallahirField.setDate(null);
        alamatField.setText("");
        kodekota.setText("");
        kodeprovinsi.setText("");
        kodeposField.setText("");
        kodejabatanakademik.setText("");
        kodedirektoratjenderal.setText("");
        kodedirektorat.setText("");
        kodebagian.setText("");
        kodeseksi.setText("");
        pangkat.setText("");
        golongan.setText("");
        teleponrumahField.setText("");
        emailField.setText("");
        handphoneField.setText("");
        noSKpengangkatanField.setText("");
        tanggalmasukField.setDate(null);
        tanggalberhentiField.setDate(null);
        status.setText("");
        kodejenjangpendidikan.setText("");        
        IDlogauditField.setText("");
        TMTField.setText("");
        gelarbelakangField.setText("");
        gelardepanField.setText("");
        kodepegawaiField.requestFocus();
    }
    
    private void tampilkanData(int row) {
        System.out.println("baris yg dipilih utk diUPDATE: "  + row);
        persiapanEntriDataBaru();
        kodepegawaiField.setText(tabel.getValueAt(row, 0).toString());
        NIPlamaField.setText(tabel.getValueAt(row, 1).toString());
        NIPbaruField.setText(tabel.getValueAt(row, 2).toString());
        namapegawaiField.setText(tabel.getValueAt(row, 3).toString());
        jeniskelamin.setText(tabel.getValueAt(row, 4).toString());
        tempatlahirField.setText(tabel.getValueAt(row, 5).toString());
        
        String strKalendar = tabel.getValueAt(row, 6).toString();
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
        
        alamatField.setText(tabel.getValueAt(row, 7).toString());
        kodekota.setText(tabel.getValueAt(row, 8).toString());
        kodeprovinsi.setText(tabel.getValueAt(row, 9).toString());
        kodeposField.setText(tabel.getValueAt(row, 10).toString());
        kodejabatanakademik.setText(tabel.getValueAt(row, 11).toString());
        kodedirektoratjenderal.setText(tabel.getValueAt(row, 12).toString());
        kodedirektorat.setText(tabel.getValueAt(row, 13).toString());
        kodebagian.setText(tabel.getValueAt(row, 14).toString());
        kodeseksi.setText(tabel.getValueAt(row, 15).toString());
        pangkat.setText(tabel.getValueAt(row, 16).toString());
        golongan.setText(tabel.getValueAt(row, 17).toString());
        teleponrumahField.setText(tabel.getValueAt(row, 18).toString());
        emailField.setText(tabel.getValueAt(row, 19).toString());
        handphoneField.setText(tabel.getValueAt(row, 20).toString());
        noSKpengangkatanField.setText(tabel.getValueAt(row, 21).toString());
        
        strKalendar = tabel.getValueAt(row, 22).toString();
        tahun = Integer.parseInt( strKalendar.substring(0, 4) );
        System.out.println("" + strKalendar.substring(0, 4)  + " tahun: " + tahun);
        intBulan = Integer.parseInt( strKalendar.substring(5, 7) );
        System.out.println("" + strKalendar.substring(5, 7)  + " intBulan: " + intBulan);
        hari = Integer.parseInt( strKalendar.substring(8, 10) );
        System.out.println("" + strKalendar.substring(8, 10)  + " hari: " + hari);
        kalendar.set(tahun, intBulan - 1, hari); 
        tanggalmasukField.setCalendar(kalendar);
        
        strKalendar = tabel.getValueAt(row, 23).toString();
        tahun = Integer.parseInt( strKalendar.substring(0, 4) );
        System.out.println("" + strKalendar.substring(0, 4)  + " tahun: " + tahun);
        intBulan = Integer.parseInt( strKalendar.substring(5, 7) );
        System.out.println("" + strKalendar.substring(5, 7)  + " intBulan: " + intBulan);
        hari = Integer.parseInt( strKalendar.substring(8, 10) );
        System.out.println("" + strKalendar.substring(8, 10)  + " hari: " + hari);
        kalendar.set(tahun, intBulan - 1, hari); 
        tanggalberhentiField.setCalendar(kalendar);
        
        status.setText(tabel.getValueAt(row, 24).toString());
        kodejenjangpendidikan.setText(tabel.getValueAt(row, 25).toString());
        //IDlogauditField.setText(tabel.getValueAt(row, 26).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
        data[0] = kodepegawaiField.getText();
        
        data[1] = (!NIPlamaField.getText().equals("")?NIPlamaField.getText():"0");
        data[2] = (!NIPbaruField.getText().equals("")?NIPbaruField.getText():"0");
        data[3] = namapegawaiField.getText();
        
        String datajeniskelaminmentah = jeniskelamin.getText();
        System.out.println("data jeniskelamin mentah: " + datajeniskelaminmentah);
        String [] datajeniskelamin = datajeniskelaminmentah.split(" ");
        System.out.println("data jeniskelamin yg dipilih: " + datajeniskelamin[0]);
        data[4] = datajeniskelamin[0];
        
        data[5] = tempatlahirField.getText();
        
        kalendar = tanggallahirField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data6 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[6] = (data6!=null?data6:"");
        
        data[7] = alamatField.getText();
        
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
        
        data[10] = (!kodeposField.getText().equals("     ")?kodeposField.getText():"0");
        
        String datakodejabatanakademikmentah = kodejabatanakademik.getText();
        System.out.println("data kodejabatanakademik mentah: " + datakodejabatanakademikmentah);
        String [] datakodejabatanakademik = datakodejabatanakademikmentah.split(" ");
        System.out.println("data kodejabatanakademik yg dipilih: " + datakodejabatanakademik[0]);
        data[11] = datakodejabatanakademik[0];
        
        String datakodedirektoratjenderalmentah = kodedirektoratjenderal.getText();
        System.out.println("data kodedirektoratjenderal mentah: " + datakodedirektoratjenderalmentah);
        String [] datakodedirektoratjenderal = datakodedirektoratjenderalmentah.split(" ");
        System.out.println("data kodedirektoratjenderal yg dipilih: " + datakodedirektoratjenderal[0]);
        data[12] = datakodedirektoratjenderal[0];
        
        String datakodedirektoratmentah = kodedirektorat.getText();
        System.out.println("data kodedirektorat mentah: " + datakodedirektoratmentah);
        String [] datakodedirektorat = datakodedirektoratmentah.split(" ");
        System.out.println("data kodedirektorat yg dipilih: " + datakodedirektorat[0]);
        data[13] = datakodedirektorat[0];
        
        String datakodebagianmentah = kodebagian.getText();
        System.out.println("data kodebagian mentah: " + datakodebagianmentah);
        String [] datakodebagian = datakodebagianmentah.split(" ");
        System.out.println("data kodebagian yg dipilih: " + datakodebagian[0]);
        data[14] = datakodebagian[0];
        
        String datakodeseksimentah = kodeseksi.getText();
        System.out.println("data kodeseksi mentah: " + datakodeseksimentah);
        String [] datakodeseksi = datakodeseksimentah.split(" ");
        System.out.println("data kodeseksi yg dipilih: " + datakodeseksi[0]);
        data[15] = datakodeseksi[0];
        
        String datapangkatmentah = pangkat.getText();
        System.out.println("data pangkat mentah: " + datapangkatmentah);
        String [] datapangkat = datapangkatmentah.split(" ");
        System.out.println("data pangkat yg dipilih: " + datapangkat[0]);
        data[16] = datapangkat[0];
                
        String datagolonganmentah = golongan.getText();
        System.out.println("data golongan mentah: " + datagolonganmentah);
        String [] datagolongan = datagolonganmentah.split(" ");
        System.out.println("data golongan yg dipilih: " + datagolongan[0]);
        data[17] = datagolongan[0];
        
        data[18] = (!teleponrumahField.getText().equals("")?teleponrumahField.getText():"0");
        
        data[19] = emailField.getText();
        
        data[20] = (!handphoneField.getText().equals("")?handphoneField.getText():"0");
        
        data[21] = noSKpengangkatanField.getText();
        
        kalendar = tanggalmasukField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data22 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[22] = (data22!=null?data22:"");
        
        kalendar = tanggalberhentiField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data23 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[23] = (data23!=null?data23:"");
        
        String datastatusmentah = status.getText();
        System.out.println("data status mentah: " + datastatusmentah);
        String [] datastatus = datastatusmentah.split(" ");
        System.out.println("data status yg dipilih: " + datastatus[0]);
        data[24] = datastatus[0];
        
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[25] = datakodejenjangpendidikan[0];
        
        data[26] = "" + idlogaudit;
        data[27] = TMTField.getText();
         kalendar = tanggalMasukTMTiField1.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data28 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[28] = (data28!=null?data28:"");
         kalendar = tanggalberhentiTMTField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data29 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[29] = (data29!=null?data29:"");
        data[30] = gelardepanField.getText();
        data[31] = gelarbelakangField.getText();
        
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];        
        
        data[0] = (!NIPlamaField.getText().equals("")?NIPlamaField.getText():"0");
        data[1] = (!NIPbaruField.getText().equals("")?NIPbaruField.getText():"0");
        
        data[2] = namapegawaiField.getText();
        
        String datajeniskelaminmentah = jeniskelamin.getText();
        System.out.println("data jeniskelamin mentah: " + datajeniskelaminmentah);
        String [] datajeniskelamin = datajeniskelaminmentah.split(" ");
        System.out.println("data jeniskelamin yg dipilih: " + datajeniskelamin[0]);
        data[3] = datajeniskelamin[0];
        
        data[4] = tempatlahirField.getText();
        
        kalendar = tanggallahirField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data6 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[5] = (data6!=null?data6:"");
        
        data[6] = alamatField.getText();
        
        String datakodekotamentah = kodekota.getText();
        System.out.println("data kode kota mentah: " + datakodekotamentah);
        String [] datakodekota = datakodekotamentah.split(" ");
        System.out.println("data kode kota yg dipilih: " + datakodekota[0]);
        data[7] = datakodekota[0];
        
        String datakodeprovinsimentah = kodeprovinsi.getText();
        System.out.println("data kode provinsi mentah: " + datakodeprovinsimentah);
        String [] datakodeprovinsi = datakodeprovinsimentah.split(" ");
        System.out.println("data kode provinsi yg dipilih: " + datakodeprovinsi[0]);
        data[8] = datakodeprovinsi[0];
        
        data[9] = (!kodeposField.getText().equals("     ")?kodeposField.getText():"0");
        
        String datakodejabatanakademikmentah = kodejabatanakademik.getText();
        System.out.println("data kodejabatanakademik mentah: " + datakodejabatanakademikmentah);
        String [] datakodejabatanakademik = datakodejabatanakademikmentah.split(" ");
        System.out.println("data kodejabatanakademik yg dipilih: " + datakodejabatanakademik[0]);
        data[10] = datakodejabatanakademik[0];
        
        String datakodedirektoratjenderalmentah = kodedirektoratjenderal.getText();
        System.out.println("data kodedirektoratjenderal mentah: " + datakodedirektoratjenderalmentah);
        String [] datakodedirektoratjenderal = datakodedirektoratjenderalmentah.split(" ");
        System.out.println("data kodedirektoratjenderal yg dipilih: " + datakodedirektoratjenderal[0]);
        data[11] = datakodedirektoratjenderal[0];
        
        String datakodedirektoratmentah = kodedirektorat.getText();
        System.out.println("data kodedirektorat mentah: " + datakodedirektoratmentah);
        String [] datakodedirektorat = datakodedirektoratmentah.split(" ");
        System.out.println("data kodedirektorat yg dipilih: " + datakodedirektorat[0]);
        data[12] = datakodedirektorat[0];
        
        String datakodebagianmentah = kodebagian.getText();
        System.out.println("data kodebagian mentah: " + datakodebagianmentah);
        String [] datakodebagian = datakodebagianmentah.split(" ");
        System.out.println("data kodebagian yg dipilih: " + datakodebagian[0]);
        data[13] = datakodebagian[0];
        
        String datakodeseksimentah = kodeseksi.getText();
        System.out.println("data kodeseksi mentah: " + datakodeseksimentah);
        String [] datakodeseksi = datakodeseksimentah.split(" ");
        System.out.println("data kodeseksi yg dipilih: " + datakodeseksi[0]);
        data[14] = datakodeseksi[0];
        
        String datapangkatmentah = pangkat.getText();
        System.out.println("data pangkat mentah: " + datapangkatmentah);
        String [] datapangkat = datapangkatmentah.split(" ");
        System.out.println("data pangkat yg dipilih: " + datapangkat[0]);
        data[15] = datapangkat[0];
                
        String datagolonganmentah = golongan.getText();
        System.out.println("data golongan mentah: " + datagolonganmentah);
        String [] datagolongan = datagolonganmentah.split(" ");
        System.out.println("data golongan yg dipilih: " + datagolongan[0]);
        data[16] = datagolongan[0];
        
        data[17] = (!teleponrumahField.getText().equals("")?teleponrumahField.getText():"0");
                
        data[18] = emailField.getText();
        
        data[19] = (!handphoneField.getText().equals("")?handphoneField.getText():"0");
        
        data[20] = noSKpengangkatanField.getText();
        
        kalendar = tanggalmasukField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data22 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[21] = (data22!=null?data22:"");
        
        kalendar = tanggalberhentiField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data23 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[22] = (data23!=null?data23:"");
        
        String datastatusmentah = status.getText();
        System.out.println("data status mentah: " + datastatusmentah);
        String [] datastatus = datastatusmentah.split(" ");
        System.out.println("data status yg dipilih: " + datastatus[0]);
        data[23] = datastatus[0];
        
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[24] = datakodejenjangpendidikan[0];
        
        data[25] = "" + idlogaudit;
        data[26] = TMTField.getText();
         kalendar = tanggalMasukTMTiField1.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data27 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[27] = (data27!=null?data27:"");
         kalendar = tanggalberhentiTMTField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data28 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[28] = (data28!=null?data28:"");
        data[29] = gelardepanField.getText();
        data[30] = gelarbelakangField.getText();
        
        
        data[31] = kodepegawaiField.getText();
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[1];
        data[0] = kodepegawaiField.getText();
        return data;   
    }
    
    private void buttonBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBaruActionPerformed
        // TODO add your handling code here:
        persiapanEntriDataBaru();
    }//GEN-LAST:event_buttonBaruActionPerformed

    private void buttonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInsertActionPerformed
        // TODO add your handling code here:
        initIDLogAudit();
        String query = "insert into pdpt_dev.\"TMST_PEGAWAI\" " +
        " values(?,?,?,?,?,?,?,?,?,?, " +
                "?,?,?,?,?,?,?,?,?,?, " +
                "?,?,?,?,?,?,?,?,?,?,?,?)";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukInsert() );
        int hasil = model.insertAktifitasDOsen(query,
            dapatkanNilaiUntukInsert() );
        if (hasil == 1) {
            query = "select * from pdpt_dev.\"TMST_PEGAWAI\"";
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
        String query = "update pdpt_dev.\"TMST_PEGAWAI\" " +
        " SET \"NIP_lama\"=?, " +
        "\"NIP_baru\"=?, " +
        "\"Nama_pegawai\"=?, " +
        "\"Jenis_kelamin\"=?, " +
        "\"Tempat_lahir\"=?, " +
        "\"Tanggal_lahir\"=?, " +
        "\"Alamat\"=?, " +
        "\"Kode_kota\" = ?, " +
        "\"Kode_provinsi\"=?, " +
        "\"Kode_pos\"=?, " +
        "\"Kode_jabatan_fungsional\"=?, " +
        "\"Kode_direktorat_jenderal\"=?, " +
        "\"Kode_direktorat\"=?, " +
        "\"Kode_bagian\"=?, " +
        "\"Kode_seksi\"=?, " +
        "\"Pangkat\"=?, " +
        "\"Golongan\"=?, " +
        "\"Telepon_rumah\"=?, " +
        "\"Email\"=?, " +
        "\"Handphone\"=?, " +
        "\"No_SK_pengangkatan\"=?, " +
        "\"Tanggal_masuk\"=?, " +
        "\"Tanggal_berhenti\"=?, " +
        "\"Status\"=?, " +
        "\"Kode_jenjang_pendidikan\"=?, " +
        "\"ID_log_audit\"=?, " +
        "\"TEXT_TMT\"=?, " +
        "\"TEXT_TGL_Masuk\"=?, " +
        "\"TEXT_TGL_Berhenti\"=?, " +
        "\"Gelar_Depan\"=?, " +
        "\"Gelar_Belakang\"=? " +
        " WHERE \"Kode_pegawai\"=? ";
        
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukUpdate() );
        int hasil = model.updateAktifitasDOsen(query,
            dapatkanNilaiUntukUpdate() );
        
        if (hasil == 1) {
            query = "select * from pdpt_dev.\"TMST_PEGAWAI\"";
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
        String query = "delete from pdpt_dev.\"TMST_PEGAWAI\" WHERE \"Kode_pegawai\"=?";
        
        String [] pkDataYgDihapus = dapatkanNilaiUntukDelete();
        
        int hasil = model.delete(query,
            dapatkanNilaiUntukDelete() );
        if (hasil == 1) {
            query = "select * from pdpt_dev.\"TMST_PEGAWAI\" ";
            kon.selectData(query);
            tampilkanDataKeTabel(kon.dapatkanData(), ubahLabelKeSentenceCase(kon.dapatkanKolom()));
            
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
    private javax.swing.JTextField IDlogauditField;
    private javax.swing.JLabel IDlogauditLabel;
    private javax.swing.JLabel IDlogauditLabel1;
    private javax.swing.JLabel IDlogauditLabel2;
    private javax.swing.JLabel IDlogauditLabel3;
    private javax.swing.JLabel IDlogauditLabel4;
    private javax.swing.JLabel IDlogauditLabel5;
    private javax.swing.JFormattedTextField NIPbaruField;
    private javax.swing.JLabel NIPbaruLabel;
    private javax.swing.JFormattedTextField NIPlamaField;
    private javax.swing.JLabel NIPlamaLabel;
    private javax.swing.JTextField TMTField;
    private javax.swing.JTextField alamatField;
    private javax.swing.JLabel alamatLabel;
    private Widget.Button buttonBaru;
    private Widget.Button buttonDelete;
    private Widget.Button buttonFirst;
    private Widget.Button buttonInsert;
    private Widget.Button buttonLast;
    private Widget.Button buttonNext;
    private Widget.Button buttonPrev;
    private Widget.Button buttonUpdate;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField gelarbelakangField;
    private javax.swing.JTextField gelardepanField;
    private javax.swing.JComboBox golonganField;
    private javax.swing.JLabel golonganLabel;
    private javax.swing.JScrollPane gulungPanelAtribut;
    private javax.swing.JScrollPane gulungTabel;
    private javax.swing.JFormattedTextField handphoneField;
    private javax.swing.JLabel handphoneLabel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JComboBox jeniskelaminField;
    private javax.swing.JLabel jeniskelaminLabel;
    private javax.swing.JComboBox kodebagianField;
    private javax.swing.JLabel kodebagianLabel;
    private javax.swing.JComboBox kodedirektoratField;
    private javax.swing.JLabel kodedirektoratLabel;
    private javax.swing.JComboBox kodedirektoratjenderalField;
    private javax.swing.JLabel kodedirektoratjendralLabel;
    private javax.swing.JComboBox kodejabatanakademikField;
    private javax.swing.JLabel kodejabatanfungsionalLabel;
    private javax.swing.JComboBox kodejenjangpendidikanField;
    private javax.swing.JLabel kodejenjangpendidikanLabel;
    private javax.swing.JComboBox kodekotaField;
    private javax.swing.JLabel kodekotaLabel;
    private javax.swing.JFormattedTextField kodepegawaiField;
    private javax.swing.JLabel kodepegawaiLabel;
    private javax.swing.JFormattedTextField kodeposField;
    private javax.swing.JLabel kodeposLabel;
    private javax.swing.JComboBox kodeprovinsiField;
    private javax.swing.JLabel kodeprovinsiLabel;
    private javax.swing.JComboBox kodeseksiField;
    private javax.swing.JLabel kodeseksiLabel;
    private javax.swing.JTextField namapegawaiField;
    private javax.swing.JLabel namapegawaiLabel;
    private javax.swing.JTextField noSKpengangkatanField;
    private javax.swing.JLabel noSKpengangkatanLabel;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JComboBox pangkatField;
    private javax.swing.JLabel pangkatLabel;
    private javax.swing.JComboBox statusField;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JTable tabel;
    private com.toedter.calendar.JDateChooser tanggalMasukTMTiField1;
    private com.toedter.calendar.JDateChooser tanggalberhentiField;
    private javax.swing.JLabel tanggalberhentiLabel;
    private com.toedter.calendar.JDateChooser tanggalberhentiTMTField;
    private com.toedter.calendar.JDateChooser tanggallahirField;
    private javax.swing.JLabel tanggallahirLabel;
    private com.toedter.calendar.JDateChooser tanggalmasukField;
    private javax.swing.JLabel tanggalmasukLabel;
    private javax.swing.JFormattedTextField teleponrumahField;
    private javax.swing.JLabel teleponrumahLabel;
    private javax.swing.JTextField tempatlahirField;
    private javax.swing.JLabel tempatlahirLabel;
    // End of variables declaration//GEN-END:variables
}
