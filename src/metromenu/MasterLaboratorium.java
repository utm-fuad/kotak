/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelMasterLaboratorium;
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
public class MasterLaboratorium extends javax.swing.JPanel {
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
    
    private JTextField kodejenjangpendidikan;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo1;
    private JTextField kodeprogramstudi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    private JTextField kepemilikanlab;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    private JTextField lokasilab;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo4;
    private JTextField fungsiselainpraktikum;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo5;
    private JTextField penggunaanlab;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo6;
    private JTextField statusvalidasi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo7;
    Model.ModelMasterLaboratorium model;
    private int kodelab, idlogaudit;
    private String kueri,kodept;

    /**
     * Creates new form PanelHalaman1
     */
    public MasterLaboratorium() {    
        initComponents();
    }
    
    public MasterLaboratorium(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        model = new ModelMasterLaboratorium();
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        kodept = homePage.dapatkanKodePT();
        kodeperguruantinggiField.setText(kodept);
        inisialisasiData();
        initAutoComplete();
        //enable button
        buttonBaru.setEnabled(false);
        buttonInsert.setEnabled(false);
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
    }
    
    private void initPKAutoIncrement() {
        query = "select \"Kode_lab\" " +
                " from pdpt_dev.\"TMST_LABORATORIUM\" " +
                " order by \"Kode_lab desc\"";
        kon.selectData(query);     
        String [][] data = kon.dapatkanData();
        kodelab = Integer.parseInt(data[0][0]);
        kodelab++;
        System.out.println("kodelab: " + kodelab);
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
                kodejenjangpendidikanField, kodejenjangpendidikan);        
        query = "select \"Kode_jenjang_pendidikan\", \"Deskripsi\" " +
                " from pdpt_dev.\"TREF_JENJANG_PENDIDIKAN\""; 
        kompletTextFieldCombo1.initDataUtkAutoComplete(query);        
        kodejenjangpendidikan = kompletTextFieldCombo1.dapatkanTextField();
        kodejenjangpendidikanField = kompletTextFieldCombo1.dapatkanComboBox();
        
        kompletTextFieldCombo2 = new KompletTextFieldCombo(kon, 
                kodeprogramstudiField, kodeprogramstudi);        
        query = "select \"Kode_program_studi\", \"Nama_program_studi\" " +
                " from pdpt_dev.\"TMST_PROGRAM_STUDI\""; 
        kompletTextFieldCombo2.initDataUtkAutoComplete(query);        
        kodeprogramstudi = kompletTextFieldCombo2.dapatkanTextField();
        kodeprogramstudiField = kompletTextFieldCombo2.dapatkanComboBox();
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                kepemilikanlabField, kepemilikanlab);        
        String [][] datanya3 = {{"A", "Miliki Institusi Sendiri"},
            {"B","Kerjasama dengan PT Lain"}, 
            {"C","Kerjasama dengan Instansi Lain"}}; 
        kompletTextFieldCombo3.initDataUtkAutoComplete(datanya3);          
        kepemilikanlab = kompletTextFieldCombo3.dapatkanTextField();
        kepemilikanlabField = kompletTextFieldCombo3.dapatkanComboBox();                
        
        kompletTextFieldCombo4 = new KompletTextFieldCombo(kon, 
                lokasilabField, lokasilab);        
        String [][] datanya4 = {{"A", "Di Kampus Sendiri"},
            {"B","Di Luar Instansi Dalam Kota"}, 
            {"C","Di Luar Instansi Luar Kota"}}; 
        kompletTextFieldCombo4.initDataUtkAutoComplete(datanya4);          
        lokasilab = kompletTextFieldCombo4.dapatkanTextField();
        lokasilabField = kompletTextFieldCombo4.dapatkanComboBox();
        
        kompletTextFieldCombo5 = new KompletTextFieldCombo(kon, 
                fungsiselainpraktikumField, fungsiselainpraktikum);        
        String [][] datanya5 = {{"A", "Praktikum saja"},
            {"B","Praktikum + Penelitian"},{"C","Pengabdian Masyarakat"}, 
            {"D","Kerjasama dengan Industri"},{"E","Untuk Jasa"}}; 
        kompletTextFieldCombo5.initDataUtkAutoComplete(datanya5);
        fungsiselainpraktikum = kompletTextFieldCombo5.dapatkanTextField();
        fungsiselainpraktikumField = kompletTextFieldCombo5.dapatkanComboBox();
        
        kompletTextFieldCombo6 = new KompletTextFieldCombo(kon, 
                penggunaanlabField, penggunaanlab);        
        String [][] datanya6 = {{"A", "Praktikum PS Sendiri"},
            {"B","Praktikum PS Sendiri + Lain"},
            {"C","Praktikum PS Sendiri + PT Lain"}}; 
        kompletTextFieldCombo6.initDataUtkAutoComplete(datanya6);
        penggunaanlab = kompletTextFieldCombo6.dapatkanTextField();
        penggunaanlabField = kompletTextFieldCombo6.dapatkanComboBox();
        
        kompletTextFieldCombo7 = new KompletTextFieldCombo(kon, 
                statusvalidasiField, statusvalidasi);        
        String [][] datanya7 = {{"1", "Belum Diverifikasi"},
            {"2","Sudah diverifikasi namun masih terdapat data yang invalid"},
            {"3","Sudah diverifikasi dan data sudah valid"}}; 
        kompletTextFieldCombo7.initDataUtkAutoComplete(datanya7);
        statusvalidasi = kompletTextFieldCombo7.dapatkanTextField();
        statusvalidasiField = kompletTextFieldCombo7.dapatkanComboBox();
    }
    
    private void inisialisasiData() {
        namaTabel = "pdpt_dev.\"TMST_LABORATORIUM\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select * from " + namaTabel;
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public MasterLaboratorium(String [][] data, String [] kolom) {
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
        initPKAutoIncrement();
        kodelabField.setText("" + kodelab);
        nourutlabField.setValue("");
        namalabField.setText("");
        //kodeperguruantinggiField.setText("" + homePage.dapatkanKodePT());//utk pdpt pt
        kodeperguruantinggiField.setText(""); //utk pdpt dikti
        kodejenjangpendidikan.setText("");
        kodeprogramstudi.setText("");
        tahunpelaporanField.setText("");
        semesterpelaporanField.setText("");
        kepemilikanlab.setText("");
        lokasilab.setText("");
        luaslabField.setText("");
        kapasitaspraktikumsatushiftField.setText("");
        labpenggunaanmhsField.setText("");
        labpenggunaanjamField.setText("");
        jumlahPSpenggunaField.setText("");
        jumlahmodulpraktikumsendiriField.setText("");
        jumlahmodulpraktikumlainField.setText("");
        fungsiselainpraktikum.setText("");
        penggunaanlab.setText("");
        statusvalidasi.setText("");
        IDlogauditField.setText("");
        
        kodelabField.requestFocus();
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
        kodelabField.setText(tabel.getValueAt(row, 0).toString());
        nourutlabField.setText(tabel.getValueAt(row, 1).toString());
        namalabField.setText(tabel.getValueAt(row, 2).toString());
        kodeperguruantinggiField.setText(tabel.getValueAt(row, 3).toString());
        kodejenjangpendidikan.setText(tabel.getValueAt(row, 4).toString());
        kodeprogramstudi.setText(tabel.getValueAt(row, 5).toString());
        tahunpelaporanField.setText(tabel.getValueAt(row, 6).toString());
        semesterpelaporanField.setText(tabel.getValueAt(row, 7).toString());
        kepemilikanlab.setText(tabel.getValueAt(row, 8).toString());
        lokasilab.setText(tabel.getValueAt(row, 9).toString());
        luaslabField.setText(tabel.getValueAt(row, 10).toString());
        kapasitaspraktikumsatushiftField.setText(tabel.getValueAt(row, 11).toString());
        labpenggunaanmhsField.setText(tabel.getValueAt(row, 12).toString());
        labpenggunaanjamField.setText(tabel.getValueAt(row, 13).toString());
        jumlahPSpenggunaField.setText(tabel.getValueAt(row, 14).toString());
        jumlahmodulpraktikumsendiriField.setText(tabel.getValueAt(row, 15).toString());        
        jumlahmodulpraktikumlainField.setText(tabel.getValueAt(row, 16).toString());
        fungsiselainpraktikum.setText(tabel.getValueAt(row, 17).toString());
        penggunaanlab.setText(tabel.getValueAt(row, 18).toString());        
        statusvalidasi.setText(tabel.getValueAt(row, 19).toString());
        //IDlogauditField.setText(tabel.getValueAt(row, 20).toString());
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
        kodelabLabel = new javax.swing.JLabel();
        nourutlabLabel = new javax.swing.JLabel();
        namalabLabel = new javax.swing.JLabel();
        kodeperguruantinggiLabel = new javax.swing.JLabel();
        kodejenjangpendidikanLabel = new javax.swing.JLabel();
        kodeprogramstudiLabel = new javax.swing.JLabel();
        tahunpelaporanLabel = new javax.swing.JLabel();
        semesterpelaporanLabel = new javax.swing.JLabel();
        kepemilikanlabLabel = new javax.swing.JLabel();
        lokasilabLabel = new javax.swing.JLabel();
        luaslabLabel = new javax.swing.JLabel();
        kapasitaspraktikumsatushiftLabel = new javax.swing.JLabel();
        labpenggunaanmhsLabel = new javax.swing.JLabel();
        labpenggunaanjamLabel = new javax.swing.JLabel();
        jumlahPSpenggunaLabel = new javax.swing.JLabel();
        jumlahmodulpraktikumsendiriLabel = new javax.swing.JLabel();
        jumlahmodulpraktikumlainLabel = new javax.swing.JLabel();
        fungsiselainpraktikumLabel = new javax.swing.JLabel();
        penggunaanlabLabel = new javax.swing.JLabel();
        statusvalidasiLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodelabField = new javax.swing.JFormattedTextField();
        nourutlabField = new javax.swing.JFormattedTextField();
        namalabField = new javax.swing.JTextField();
        kodeperguruantinggiField = new javax.swing.JFormattedTextField();
        kodejenjangpendidikanField = new javax.swing.JComboBox();
        kodeprogramstudiField = new javax.swing.JComboBox();
        tahunpelaporanField = new javax.swing.JFormattedTextField();
        semesterpelaporanField = new javax.swing.JFormattedTextField();
        kepemilikanlabField = new javax.swing.JComboBox();
        lokasilabField = new javax.swing.JComboBox();
        luaslabField = new javax.swing.JFormattedTextField();
        kapasitaspraktikumsatushiftField = new javax.swing.JFormattedTextField();
        labpenggunaanmhsField = new javax.swing.JFormattedTextField();
        labpenggunaanjamField = new javax.swing.JFormattedTextField();
        jumlahPSpenggunaField = new javax.swing.JFormattedTextField();
        jumlahmodulpraktikumsendiriField = new javax.swing.JFormattedTextField();
        jumlahmodulpraktikumlainField = new javax.swing.JFormattedTextField();
        fungsiselainpraktikumField = new javax.swing.JComboBox();
        penggunaanlabField = new javax.swing.JComboBox();
        statusvalidasiField = new javax.swing.JComboBox();
        IDlogauditField = new javax.swing.JFormattedTextField();
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

        panelLabel.setLayout(new java.awt.GridLayout(21, 0));

        kodelabLabel.setText("Kode Lab:");
        panelLabel.add(kodelabLabel);

        nourutlabLabel.setText("No Urut Lab:");
        panelLabel.add(nourutlabLabel);

        namalabLabel.setText("Nama Lab:");
        panelLabel.add(namalabLabel);

        kodeperguruantinggiLabel.setText("Kode Perguruan Tinggi:");
        panelLabel.add(kodeperguruantinggiLabel);

        kodejenjangpendidikanLabel.setText("Kode Jenjang Pendidikan:");
        panelLabel.add(kodejenjangpendidikanLabel);

        kodeprogramstudiLabel.setText("Kode Program Studi:");
        panelLabel.add(kodeprogramstudiLabel);

        tahunpelaporanLabel.setText("Tahun Pelaporan:");
        panelLabel.add(tahunpelaporanLabel);

        semesterpelaporanLabel.setText("Semester Pelaporan:");
        panelLabel.add(semesterpelaporanLabel);

        kepemilikanlabLabel.setText("Kepemilikan Lab:");
        panelLabel.add(kepemilikanlabLabel);

        lokasilabLabel.setText("Lokasi Lab:");
        panelLabel.add(lokasilabLabel);

        luaslabLabel.setText("Luas Lab:");
        panelLabel.add(luaslabLabel);

        kapasitaspraktikumsatushiftLabel.setText("Kapasitas Praktikum Satu Shift:");
        panelLabel.add(kapasitaspraktikumsatushiftLabel);

        labpenggunaanmhsLabel.setText("Lab Penggunaan Mhs:");
        panelLabel.add(labpenggunaanmhsLabel);

        labpenggunaanjamLabel.setText("Lab Penggunaan Jam:");
        panelLabel.add(labpenggunaanjamLabel);

        jumlahPSpenggunaLabel.setText("Jumlah PS Pengguna:");
        panelLabel.add(jumlahPSpenggunaLabel);

        jumlahmodulpraktikumsendiriLabel.setText("Jumlah Modul Praktikum Sendiri:");
        panelLabel.add(jumlahmodulpraktikumsendiriLabel);

        jumlahmodulpraktikumlainLabel.setText("Jumlah Modul Praktikum Lain:");
        panelLabel.add(jumlahmodulpraktikumlainLabel);

        fungsiselainpraktikumLabel.setText("Fungsi Selain Praktikum:");
        panelLabel.add(fungsiselainpraktikumLabel);

        penggunaanlabLabel.setText("Penggunaan Lab:");
        panelLabel.add(penggunaanlabLabel);

        statusvalidasiLabel.setText("Status Validasi:");
        panelLabel.add(statusvalidasiLabel);

        IDlogauditLabel.setText("ID Log Audit:");
        panelLabel.add(IDlogauditLabel);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(21, 0));

        kodelabField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(kodelabField);

        try {
            nourutlabField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(nourutlabField);
        panelTextField.add(namalabField);

        try {
            kodeperguruantinggiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodeperguruantinggiField);

        panelTextField.add(kodejenjangpendidikanField);

        panelTextField.add(kodeprogramstudiField);

        try {
            tahunpelaporanField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(tahunpelaporanField);

        try {
            semesterpelaporanField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(semesterpelaporanField);

        panelTextField.add(kepemilikanlabField);

        panelTextField.add(lokasilabField);

        luaslabField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("####"))));
        panelTextField.add(luaslabField);

        kapasitaspraktikumsatushiftField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        panelTextField.add(kapasitaspraktikumsatushiftField);

        labpenggunaanmhsField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        panelTextField.add(labpenggunaanmhsField);

        labpenggunaanjamField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##"))));
        panelTextField.add(labpenggunaanjamField);

        jumlahPSpenggunaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##"))));
        panelTextField.add(jumlahPSpenggunaField);

        jumlahmodulpraktikumsendiriField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##"))));
        panelTextField.add(jumlahmodulpraktikumsendiriField);

        jumlahmodulpraktikumlainField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##"))));
        panelTextField.add(jumlahmodulpraktikumlainField);

        panelTextField.add(fungsiselainpraktikumField);

        panelTextField.add(penggunaanlabField);

        panelTextField.add(statusvalidasiField);

        IDlogauditField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        panelTextField.add(IDlogauditField);

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
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
        data[0] = kodelabField.getText();
        data[1] = (!nourutlabField.getText().equals("  ")?nourutlabField.getText():"0");
        data[2] = namalabField.getText();
        data[3] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");
        
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[4] = datakodejenjangpendidikan[0];
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[5] = datakodeprogramstudi[0];
        
        data[6] = (!tahunpelaporanField.getText().equals("    ")?tahunpelaporanField.getText():"0");
        data[7] = (!semesterpelaporanField.getText().equals(" ")?semesterpelaporanField.getText():"0");
        
        String datakepemilikanlabmentah = kepemilikanlab.getText();
        System.out.println("data kepemilikanlab mentah: " + datakepemilikanlabmentah);
        String [] datakepemilikanlab = datakepemilikanlabmentah.split(" ");
        System.out.println("data kepemilikanlab yg dipilih: " + datakepemilikanlab[0]);
        data[8] = datakepemilikanlab[0];
        
        String datalokasilabmentah = lokasilab.getText();
        System.out.println("data lokasilab mentah: " + datakepemilikanlabmentah);
        String [] datalokasilab = datalokasilabmentah.split(" ");
        System.out.println("data lokasilab yg dipilih: " + datalokasilab[0]);
        data[9] = datalokasilab[0];
        
        data[10] = (!luaslabField.getText().equals("     ")?luaslabField.getText():"0");        
        data[11] = kapasitaspraktikumsatushiftField.getText();
        data[12] = (!labpenggunaanmhsField.getText().equals(" ")?labpenggunaanmhsField.getText():"0");
        data[13] = (!labpenggunaanjamField.getText().equals("   ")?labpenggunaanjamField.getText():"0");
        data[14] = jumlahPSpenggunaField.getText();
        data[15] = jumlahmodulpraktikumsendiriField.getText();
        data[16] = (!jumlahmodulpraktikumlainField.getText().equals("     ")?jumlahmodulpraktikumlainField.getText():"0");        
        
        String datafungsiselainpraktikummentah = fungsiselainpraktikum.getText();
        System.out.println("data fungsiselainpraktikum mentah: " + datafungsiselainpraktikummentah);
        String [] datafungsiselainpraktikum = datafungsiselainpraktikummentah.split(" ");
        System.out.println("data fungsiselainpraktikum yg dipilih: " + datafungsiselainpraktikum[0]);
        data[17] = datafungsiselainpraktikum[0];
        
        String datapenggunaanlabmentah = penggunaanlab.getText();
        System.out.println("data penggunaanlab mentah: " + datapenggunaanlabmentah);
        String [] datapenggunaanlab = datapenggunaanlabmentah.split(" ");
        System.out.println("data penggunaanlab yg dipilih: " + datapenggunaanlab[0]);
        data[18] = datapenggunaanlab[0];
        
        String datastatusvalidasimentah = statusvalidasi.getText();
        System.out.println("data statusvalidasi mentah: " + datastatusvalidasimentah);
        String [] datastatusvalidasi = datastatusvalidasimentah.split(" ");
        System.out.println("data statusvalidasi yg dipilih: " + datastatusvalidasi[0]);
        data[19] = datastatusvalidasi[0];
        
        data[20] = "" + idlogaudit;
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];                
        data[0] = nourutlabField.getText();
        data[1] = namalabField.getText();
        data[2] = kodeperguruantinggiField.getText();
        data[3] = kodejenjangpendidikan.getText();
        data[4] = kodeprogramstudi.getText();
        data[5] = tahunpelaporanField.getText();
        data[6] = semesterpelaporanField.getText();
        data[7] = kepemilikanlab.getText();
        data[8] = lokasilab.getText();
        data[9] = luaslabField.getText();
        data[10] = kapasitaspraktikumsatushiftField.getText();
        data[11] = labpenggunaanmhsField.getText();
        data[12] = labpenggunaanjamField.getText();
        data[13] = jumlahPSpenggunaField.getText();
        data[14] = jumlahmodulpraktikumsendiriField.getText();
        data[15] = jumlahmodulpraktikumlainField.getText();
        data[16] = fungsiselainpraktikum.getText();
        data[17] = penggunaanlab.getText();
        data[18] = statusvalidasi.getText();
        data[19] = "" + idlogaudit;
        data[20] = kodelabField.getText();
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[1];
        data[0] = kodelabField.getText();
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
        "?)";
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
        " SET \"No_urut_lab\"=?, " +
        "\"Nama_lab\"=?, " +
        "\"Kode_perguruan_tinggi\"=?, " +
        "\"Kode_jenjang_pendidikan\"=?, " +
        "\"Kode_program_studi\"=?, " +
        "\"Tahun_pelaporan\"=?, " +
        "\"Semester_pelaporan\"=?, " +
        "\"Kepemilikan_lab\"=?, " +
        "\"Lokasi_lab\" = ?, " +
        "\"Luas_lab\" = ?, " +
        "\"Kapasitas_praktikum_satu_shift\" = ?, " +
        "\"Lab_penggunaan_mhs\" = ?, " +
        "\"Lab_penggunaan_jam\" = ?, " +
        "\"Jumlah_PS_pengguna\" = ?, " +
        "\"Jumlah_modul_praktikum_sendiri\" = ?, " +
        "\"Jumlah_modul_praktikum_lain\" = ?, " +
        "\"Fungsi_selain_praktikum\" = ?, " +
        "\"Penggunaan_Lab\" = ?, " +
        "\"Status_validasi\"=?, " +
        "\"ID_log_audit\"=? " +
        " WHERE \"Kode_lab\"=? ";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukUpdate() );
         int hasil = model.updateAktifitasDOsen(query,
            dapatkanNilaiUntukUpdate() );
        if (hasil == 1) {
            query = "select * from " + namaTabel;
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
        " Kode_lab=? ";
        
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
    private Widget.Button buttonBaru;
    private Widget.Button buttonDelete;
    private Widget.Button buttonFirst;
    private Widget.Button buttonInsert;
    private Widget.Button buttonLast;
    private Widget.Button buttonNext;
    private Widget.Button buttonPrev;
    private Widget.Button buttonUpdate;
    private javax.swing.JComboBox fungsiselainpraktikumField;
    private javax.swing.JLabel fungsiselainpraktikumLabel;
    private javax.swing.JScrollPane gulungPanelAtribut;
    private javax.swing.JScrollPane gulungTabel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JFormattedTextField jumlahPSpenggunaField;
    private javax.swing.JLabel jumlahPSpenggunaLabel;
    private javax.swing.JFormattedTextField jumlahmodulpraktikumlainField;
    private javax.swing.JLabel jumlahmodulpraktikumlainLabel;
    private javax.swing.JFormattedTextField jumlahmodulpraktikumsendiriField;
    private javax.swing.JLabel jumlahmodulpraktikumsendiriLabel;
    private javax.swing.JFormattedTextField kapasitaspraktikumsatushiftField;
    private javax.swing.JLabel kapasitaspraktikumsatushiftLabel;
    private javax.swing.JComboBox kepemilikanlabField;
    private javax.swing.JLabel kepemilikanlabLabel;
    private javax.swing.JComboBox kodejenjangpendidikanField;
    private javax.swing.JLabel kodejenjangpendidikanLabel;
    private javax.swing.JFormattedTextField kodelabField;
    private javax.swing.JLabel kodelabLabel;
    private javax.swing.JFormattedTextField kodeperguruantinggiField;
    private javax.swing.JLabel kodeperguruantinggiLabel;
    private javax.swing.JComboBox kodeprogramstudiField;
    private javax.swing.JLabel kodeprogramstudiLabel;
    private javax.swing.JFormattedTextField labpenggunaanjamField;
    private javax.swing.JLabel labpenggunaanjamLabel;
    private javax.swing.JFormattedTextField labpenggunaanmhsField;
    private javax.swing.JLabel labpenggunaanmhsLabel;
    private javax.swing.JComboBox lokasilabField;
    private javax.swing.JLabel lokasilabLabel;
    private javax.swing.JFormattedTextField luaslabField;
    private javax.swing.JLabel luaslabLabel;
    private javax.swing.JTextField namalabField;
    private javax.swing.JLabel namalabLabel;
    private javax.swing.JFormattedTextField nourutlabField;
    private javax.swing.JLabel nourutlabLabel;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JComboBox penggunaanlabField;
    private javax.swing.JLabel penggunaanlabLabel;
    private javax.swing.JFormattedTextField semesterpelaporanField;
    private javax.swing.JLabel semesterpelaporanLabel;
    private javax.swing.JComboBox statusvalidasiField;
    private javax.swing.JLabel statusvalidasiLabel;
    private javax.swing.JTable tabel;
    private javax.swing.JFormattedTextField tahunpelaporanField;
    private javax.swing.JLabel tahunpelaporanLabel;
    // End of variables declaration//GEN-END:variables
}
