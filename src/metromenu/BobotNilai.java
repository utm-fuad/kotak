/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelBobotNilai;
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
public class BobotNilai extends javax.swing.JPanel {
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
    
    private JTextField kodeprogramstudi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo1;
    private JTextField kodejenjangstudi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    Model.ModelBobotNilai model;
    private int kodebobotnilai, idlogaudit;
    private String kueri,KodePT;

    /**
     * Creates new form PanelHalaman1
     */
    public BobotNilai() {    
        initComponents();
    }
    
    public BobotNilai(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        model = new ModelBobotNilai();
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        KodePT = homePage.dapatkanKodePT();
        inisialisasiData();
        initAutoComplete();
         //disable all button
         buttonBaru.setEnabled(false);
        buttonInsert.setEnabled(false);
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
        kodebobotnilaiField.setVisible(false);
        kodebobotnilaiLabel.setVisible(false);
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
    }
    
    private void initAutoComplete() {
        kompletTextFieldCombo1 = new KompletTextFieldCombo(kon, 
                kodeprogramstudiField, kodeprogramstudi);        
        query = "select \"Kode_program_studi\", \"Nama_program_studi\" " +
                " from pdpt_dev.\"TMST_PROGRAM_STUDI\" where \"Kode_perguruan_tinggi\"="+KodePT;
        kompletTextFieldCombo1.initDataUtkAutoComplete(query);        
        kodeprogramstudi = kompletTextFieldCombo1.dapatkanTextField();
        kodeprogramstudiField = kompletTextFieldCombo1.dapatkanComboBox();        
        
        kompletTextFieldCombo2 = new KompletTextFieldCombo(kon, 
                kodejenjangstudiField, kodejenjangstudi);        
        query = "select \"Kode_jenjang_pendidikan\", \"Deskripsi\" " +
                " from pdpt_dev.\"TREF_JENJANG_PENDIDIKAN\""; 
        kompletTextFieldCombo2.initDataUtkAutoComplete(query);        
        kodejenjangstudi = kompletTextFieldCombo2.dapatkanTextField();
        kodejenjangstudiField = kompletTextFieldCombo2.dapatkanComboBox();
    }
    
    private void initPKAutoIncrement() {
        query = "select \"Kode_bobot_nilai\" " +
                " from pdpt_dev.\"TRAN_BOBOT_NILAI\" " +
                " order by \"Kode_bobot_nilai\" desc";
        kon.selectData(query);     
        String [][] data = kon.dapatkanData();
        kodebobotnilai = Integer.parseInt(data[0][0]);
        kodebobotnilai++;
        System.out.println("kodebobotnilai: " + kodebobotnilai);
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
    
    private void inisialisasiData() {
        namaTabel = "pdpt_dev.\"TRAN_BOBOT_NILAI\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select \"Kode_perguruan_tinggi\",\"Kode_program_studi\",\"Kode_jenjang_studi\","
                + "\"Tahun_pelaporan\",\"Semester_pelaporan\",\"Bobot_nilai_min\","
                + "\"Bobot_nilai_max\",\"Nilai\",\"Tgl_mulai_efektif\",\"Tgl_akhir_efektif\" from " + namaTabel + "where \"Kode_perguruan_tinggi\"="+KodePT +" order by \"Kode_bobot_nilai\" desc";
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public BobotNilai(String [][] data, String [] kolom) {
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
//        kodebobotnilaiField.setText("" + kodebobotnilai);
        kodeperguruantinggiField.setText("" + homePage.dapatkanKodePT());
        kodeprogramstudi.setText("");
        kodejenjangstudi.setText("");
        tahunpelaporanField.setText("");
        semesterpelaporanField.setText("");
        bobotnilaiminField.setText("");
        bobotnilaimaxField.setText("");        
        nilaiField.setText("");
        tglmulaiefektifField.setDate(null);
        tglakhirefektifField.setDate(null);        
        IDlogauditField.setText("");
        
        kodebobotnilaiField.requestFocus();
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
        kodebobotnilaiField.setText(tabel.getValueAt(row, 0).toString());
        kodeperguruantinggiField.setText(tabel.getValueAt(row, 1).toString());
        kodeprogramstudi.setText(tabel.getValueAt(row, 2).toString());
        kodejenjangstudi.setText(tabel.getValueAt(row, 3).toString());
        tahunpelaporanField.setText(tabel.getValueAt(row, 4).toString());
        semesterpelaporanField.setText(tabel.getValueAt(row, 5).toString());
        
        String strBobotnilaiMin = tabel.getValueAt(row, 6).toString();
        double bobotnilaiMin = Double.parseDouble(strBobotnilaiMin);
        System.out.println("bobot nilai " + bobotnilaiMin);
        if (bobotnilaiMin < 100) {
            bobotnilaiminField.setText("0" + bobotnilaiMin);
        } 
        else {
            bobotnilaiminField.setText("" + bobotnilaiMin);
        }
        
        String strBobotnilaiMax = tabel.getValueAt(row, 7).toString();
        double bobotnilaiMax = Double.parseDouble(strBobotnilaiMax);
        System.out.println("bobot nilai " + bobotnilaiMax);
        if (bobotnilaiMax < 100) {
            bobotnilaimaxField.setText("0" + bobotnilaiMax);
        } 
        else {
            bobotnilaimaxField.setText("" + bobotnilaiMax);
        }
        
        nilaiField.setText(tabel.getValueAt(row, 8).toString());
        
        String strKalendar = tabel.getValueAt(row, 9).toString();
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
        tglmulaiefektifField.setCalendar(kalendar);        
        
        strKalendar = tabel.getValueAt(row, 10).toString();
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
        tglakhirefektifField.setCalendar(kalendar);     
        
        //IDlogauditField.setText(tabel.getValueAt(row, 11).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
        data[0] = (!kodebobotnilaiField.getText().equals("")?kodebobotnilaiField.getText():"0");
        data[1] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[2] = datakodeprogramstudi[0];
        
        String datakodejenjangpendidikanmentah = kodejenjangstudi.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[3] = datakodejenjangpendidikan[0];
        
        data[4] = (!tahunpelaporanField.getText().equals("    ")?tahunpelaporanField.getText():"0");
        data[5] = (!semesterpelaporanField.getText().equals(" ")?semesterpelaporanField.getText():"0");
        data[6] = (!bobotnilaiminField.getText().equals("  .  ")?bobotnilaiminField.getText():"0");
        data[7] = (!bobotnilaimaxField.getText().equals("   .  ")?bobotnilaimaxField.getText():"0");
        data[8] = (!nilaiField.getText().equals("")?nilaiField.getText():"X");
        
        kalendar = tglmulaiefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data7 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[9] = (data7!=null?data7:"");
                
        kalendar = tglakhirefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data18 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[10] = (data18!=null?data18:"");
        
        data[11] = "" + idlogaudit;
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];                        
        data[0] = kodeperguruantinggiField.getText();
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[1] = datakodeprogramstudi[0];
        
        String datakodejenjangpendidikanmentah = kodejenjangstudi.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[2] = datakodejenjangpendidikan[0];
        
        data[3] = tahunpelaporanField.getText();
        data[4] = semesterpelaporanField.getText();
        data[5] = bobotnilaiminField.getText();        
        data[6] = bobotnilaimaxField.getText();        
        data[7] = nilaiField.getText();        
        
        kalendar = tglmulaiefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data6 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[8] = (data6!=null?data6:"");        
        
        kalendar = tglakhirefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data17 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[9] = (data17!=null?data17:"");        
        
        data[10] = "" + idlogaudit;
        data[11] = kodebobotnilaiField.getText();
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[1];
        data[0] = kodebobotnilaiField.getText();
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
        kodebobotnilaiLabel = new javax.swing.JLabel();
        kodeperguruantinggiLabel = new javax.swing.JLabel();
        kodeProgramStudiLabel = new javax.swing.JLabel();
        kodejenjangstudiLabel = new javax.swing.JLabel();
        tahunpelaporanLabel = new javax.swing.JLabel();
        semesterpelaporanLabel = new javax.swing.JLabel();
        bobotnilaiminLabel = new javax.swing.JLabel();
        bobotnilaimaxLabel = new javax.swing.JLabel();
        nilaiLabel = new javax.swing.JLabel();
        tglmulaiefektifLabel = new javax.swing.JLabel();
        tglakhirefektifLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodebobotnilaiField = new javax.swing.JFormattedTextField();
        kodeperguruantinggiField = new javax.swing.JFormattedTextField();
        kodeprogramstudiField = new javax.swing.JComboBox();
        kodejenjangstudiField = new javax.swing.JComboBox();
        tahunpelaporanField = new javax.swing.JFormattedTextField();
        semesterpelaporanField = new javax.swing.JFormattedTextField();
        bobotnilaiminField = new javax.swing.JFormattedTextField();
        bobotnilaimaxField = new javax.swing.JFormattedTextField();
        nilaiField = new javax.swing.JTextField();
        tglmulaiefektifField = new com.toedter.calendar.JDateChooser();
        tglakhirefektifField = new com.toedter.calendar.JDateChooser();
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

        panelLabel.setLayout(new java.awt.GridLayout(12, 0));

        kodebobotnilaiLabel.setText("Kode Bobot Nilai:");
        panelLabel.add(kodebobotnilaiLabel);

        kodeperguruantinggiLabel.setText("Kode Perguruan Tinggi:");
        panelLabel.add(kodeperguruantinggiLabel);

        kodeProgramStudiLabel.setText("Kode Program Studi:");
        panelLabel.add(kodeProgramStudiLabel);

        kodejenjangstudiLabel.setText("Kode Jenjang Studi:");
        panelLabel.add(kodejenjangstudiLabel);

        tahunpelaporanLabel.setText("Tahun Pelaporan:");
        panelLabel.add(tahunpelaporanLabel);

        semesterpelaporanLabel.setText("Semester Pelaporan:");
        panelLabel.add(semesterpelaporanLabel);

        bobotnilaiminLabel.setText("Bobot Nilai Min:");
        panelLabel.add(bobotnilaiminLabel);

        bobotnilaimaxLabel.setText("Bobot Nilai Max:");
        panelLabel.add(bobotnilaimaxLabel);

        nilaiLabel.setText("Nilai:");
        panelLabel.add(nilaiLabel);

        tglmulaiefektifLabel.setText("Tgl Mulai Efektif:");
        panelLabel.add(tglmulaiefektifLabel);

        tglakhirefektifLabel.setText("Tgl Akhir Efektif:");
        panelLabel.add(tglakhirefektifLabel);

        IDlogauditLabel.setText("ID Log Audit:");
        panelLabel.add(IDlogauditLabel);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(12, 0));

        kodebobotnilaiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(kodebobotnilaiField);

        try {
            kodeperguruantinggiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodeperguruantinggiField);

        panelTextField.add(kodeprogramstudiField);

        panelTextField.add(kodejenjangstudiField);

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

        try {
            bobotnilaiminField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(bobotnilaiminField);

        try {
            bobotnilaimaxField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(bobotnilaimaxField);
        panelTextField.add(nilaiField);
        panelTextField.add(tglmulaiefektifField);
        panelTextField.add(tglakhirefektifField);

        IDlogauditField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
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

    private void buttonBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBaruActionPerformed
        // TODO add your handling code here:
        persiapanEntriDataBaru();
    }//GEN-LAST:event_buttonBaruActionPerformed

    private void buttonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInsertActionPerformed
        // TODO add your handling code here:
        initIDLogAudit();
        String query = "insert into " + namaTabel + " " +
        " values(?,?,?,?,?,?,?,?,?,?,?,?)";
        int hasil = model.insertAktifitasDOsen(query, dapatkanNilaiUntukInsert());
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukInsert() );
        if (hasil == 1) {
            query = "select * from " + namaTabel + "where \"Kode_perguruan_tinggi\"="+KodePT+" order by \"Kode_bobot_nilai\" desc";
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

            //insert ke SQLite dan XML
            kon.insertKeSQLiteDanXML(query, dapatkanNilaiUntukInsert(),
                namaTabel, kon.dapatkanKolom(), kon.dapatkanData());
        }
    }//GEN-LAST:event_buttonInsertActionPerformed

    private void buttonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUpdateActionPerformed
        // TODO add your handling code here:
        initIDLogAudit();
        String query = "update " + namaTabel + " " +
        " SET \"Kode_perguruan_tinggi\"=?, " +
        "\"kode_program_studi\"=?, " +
        "\"Kode_jenjang_studi\"=?, " +
        "\"Tahun_pelaporan\"=?, " +
        "\"Semester_pelaporan\"=?, " +
        "\"Bobot_nilai_min\"=?, " +
        "\"Bobot_nilai_max\"=?, " +
        "\"Nilai\"=?, " +
        "\"Tgl_mulai_efektif\"=?, " +
        "\"Tgl_akhir_efektif\"=?, " +
        "\"ID_log_audit\"=? " +
        " WHERE \"Kode_bobot_nilai\"=? ";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukUpdate() );
        int hasil = model.updateAktifitasDOsen(query, dapatkanNilaiUntukUpdate());
        if (hasil == 1) {
            query = "select * from " + namaTabel +"where \"Kode_perguruan_tinggi\"="+KodePT+"  order by \"Kode_bobot_nilai\" desc";
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
        " \"Kode_bobot_nilai\"=? ";
        
        String [] pkDataYgDihapus = dapatkanNilaiUntukDelete();

        int hasil =model.delete(query,
            dapatkanNilaiUntukDelete() );
        if (hasil == 1) {
            query = "select * from " + namaTabel + "where \"Kode_perguruan_tinggi\"="+KodePT+" order by \"Kode_bobot_nilai\" desc";
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
    private javax.swing.JFormattedTextField bobotnilaimaxField;
    private javax.swing.JLabel bobotnilaimaxLabel;
    private javax.swing.JFormattedTextField bobotnilaiminField;
    private javax.swing.JLabel bobotnilaiminLabel;
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
    private javax.swing.JLabel kodeProgramStudiLabel;
    private javax.swing.JFormattedTextField kodebobotnilaiField;
    private javax.swing.JLabel kodebobotnilaiLabel;
    private javax.swing.JComboBox kodejenjangstudiField;
    private javax.swing.JLabel kodejenjangstudiLabel;
    private javax.swing.JFormattedTextField kodeperguruantinggiField;
    private javax.swing.JLabel kodeperguruantinggiLabel;
    private javax.swing.JComboBox kodeprogramstudiField;
    private javax.swing.JTextField nilaiField;
    private javax.swing.JLabel nilaiLabel;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JFormattedTextField semesterpelaporanField;
    private javax.swing.JLabel semesterpelaporanLabel;
    private javax.swing.JTable tabel;
    private javax.swing.JFormattedTextField tahunpelaporanField;
    private javax.swing.JLabel tahunpelaporanLabel;
    private com.toedter.calendar.JDateChooser tglakhirefektifField;
    private javax.swing.JLabel tglakhirefektifLabel;
    private com.toedter.calendar.JDateChooser tglmulaiefektifField;
    private javax.swing.JLabel tglmulaiefektifLabel;
    // End of variables declaration//GEN-END:variables
}
