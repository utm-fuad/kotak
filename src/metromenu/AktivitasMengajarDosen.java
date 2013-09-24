/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import metromini.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;
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
import Model.ModelAktifitasDosen;

/**
 *
 * @author asus
 */
public class AktivitasMengajarDosen extends javax.swing.JPanel {
    private JPanel panel;
    private koneksi.KoneksiOracleThinClient kon;
    private Model.ModelAktifitasDosen model;
    private String judul;
    private HomePage homePage;
    private String namaTabel;
    private String query;
    private int jmlKolom;
    private int barisYgDipilih = 0;
    
    private JTextField kodeprogramstudi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo1;
    private JTextField kodejenjangstudi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    private JTextField kodematakuliah;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    private JTextField jenisevaluasi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo4;
    private JTextField caraevaluasi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo5;
    
    private int idlogaudit;
    private String kueri,KodePT;

    /**
     * Creates new form PanelHalaman1
     */
    public AktivitasMengajarDosen() {    
        initComponents();
        model = new ModelAktifitasDosen();
    }
    
    public AktivitasMengajarDosen(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        
        IDlogauditLabel.setVisible(true);
        IDlogauditField.setVisible(true);
        KodePT = homePage.dapatkanKodePT();
        kodeperguruantinggiField.setText(KodePT);
        inisialisasiData();
        initAutoComplete();
        kodemengajardosenField.setVisible(false);
        kodemengajardosenLabel.setVisible(false);
        IDlogauditLabel1.setText("SKS : ");
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
    
    private void initAutoComplete() {
        kompletTextFieldCombo1 = new KompletTextFieldCombo(kon, 
                kodeprogramstudiField, kodeprogramstudi);        
        query = "select \"Kode_program_studi\", \"Nama_program_studi\" " +
                " from pdpt_dev.\"TMST_PROGRAM_STUDI\" where \"Kode_perguruan_tinggi\"='"+KodePT+"'"; 
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
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                kodematakuliahField, kodematakuliah);        
        query = "select \"Kode_mata_kuliah\", \"Nama_mata_kuliah\" " +
                " from pdpt_dev.\"TMST_MATA_KULIAH\" where \"Kode_perguruan_tinggi\"='"+KodePT+"'"; 
        kompletTextFieldCombo3.initDataUtkAutoComplete(query);        
        kodematakuliah = kompletTextFieldCombo3.dapatkanTextField();
        kodematakuliahField = kompletTextFieldCombo3.dapatkanComboBox();
        
        kompletTextFieldCombo4 = new KompletTextFieldCombo(kon, 
                jenisevaluasiField, jenisevaluasi);        
        String [][] datanya4 = {{"A","Kesiapan Awal Kuliah"},
            {"B","Kuis"},{"C","Tugas Rumah"}}; 
        kompletTextFieldCombo4.initDataUtkAutoComplete(datanya4);
        jenisevaluasi = kompletTextFieldCombo4.dapatkanTextField();
        jenisevaluasiField = kompletTextFieldCombo4.dapatkanComboBox();
        
        kompletTextFieldCombo5 = new KompletTextFieldCombo(kon, 
                caraevaluasiField, caraevaluasi);        
        String [][] datanya5 = {{"1","Ujian Tertulis"},
            {"2","Ujian Lisan"},{"3","Penyusunan atau penyajian makalah/laporan"},
            {"4","Penyajian makalah"},{"5","Lain-lain"}}; 
        kompletTextFieldCombo5.initDataUtkAutoComplete(datanya5);
        caraevaluasi = kompletTextFieldCombo5.dapatkanTextField();
        caraevaluasiField = kompletTextFieldCombo5.dapatkanComboBox();        
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
    
    private void inisialisasiData() {
        
        namaTabel = "pdpt_dev.\"TRAN_AKTIVITAS_MENGAJAR_DOSEN\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select \"NIDN/NUK\",\"Tahun_pelaporan\",\"Semester_pelaporan\",\"Kode_perguruan_tinggi\","
                + "\"Kode_program_studi\",\"Kode_jenjang_studi\",\"Kode_mata_kuliah\",\"Kode_kelas\",\"Jml_tatap_muka_rencana\",\"Jml_tatap_muka_realisasi\","
                + "\"Jenis_evaluasi\",\"Cara_evaluasi\",\"Sks\" from " + namaTabel + "where \"Kode_perguruan_tinggi\"='"+KodePT+"'";
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public AktivitasMengajarDosen(String [][] data, String [] kolom) {
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
        kodemengajardosenField.setText("");
        nidnnuktField.setText("");
        tahunpelaporanField.setText("");
        semesterpelaporanField.setText("");
        kodeperguruantinggiField.setText("" + homePage.dapatkanKodePT());
        kodeprogramstudi.setText("");
        kodejenjangstudi.setText("");
        kodematakuliah.setText("");
        kodekelasField.setText("");
        jmltatapmukarencanaField.setText("");
        jmltatapmukarealisasiField.setText("");
        jenisevaluasi.setText("");
        caraevaluasi.setText("");
        IDlogauditField.setText("");
        uuildField.setText("");
        
        kodemengajardosenField.requestFocus();
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
        kodemengajardosenField.setText(tabel.getValueAt(row, 0).toString());
        nidnnuktField.setText(tabel.getValueAt(row, 0).toString());
        tahunpelaporanField.setText(tabel.getValueAt(row, 1).toString());
//        semesterpelaporanField.setText(tabel.getValueAt(row, 2).toString());
        kodeperguruantinggiField.setText(tabel.getValueAt(row, 3).toString());        
        kodeprogramstudi.setText(tabel.getValueAt(row, 4).toString());        
        kodejenjangstudi.setText(tabel.getValueAt(row, 5).toString());        
        kodematakuliah.setText(tabel.getValueAt(row, 6).toString());        
        kodekelasField.setText(tabel.getValueAt(row, 7).toString());        
        jmltatapmukarencanaField.setText(tabel.getValueAt(row, 8).toString());        
        jmltatapmukarealisasiField.setText(tabel.getValueAt(row, 9).toString());
        jenisevaluasi.setText(tabel.getValueAt(row, 10).toString());
        caraevaluasi.setText(tabel.getValueAt(row, 11).toString());
        //IDlogauditField.setText(tabel.getValueAt(row, 13).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
//        data[0] = (!kodemengajardosenField.getText().equals("")?kodemengajardosenField.getText():"0");
        data[0] = (!nidnnuktField.getText().equals("          ")?nidnnuktField.getText():"0");
        data[1] = (!tahunpelaporanField.getText().equals("    ")?tahunpelaporanField.getText():"0");
        data[2] = (!semesterpelaporanField.getText().equals(" ")?semesterpelaporanField.getText():"0");
        data[3] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[4] = datakodeprogramstudi[0];
        
        String datakodejenjangstudimentah = kodejenjangstudi.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangstudimentah);
        String [] datakodejenjangstudi = datakodejenjangstudimentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangstudi[0]);
        data[5] = datakodejenjangstudi[0];
        
        String datakodematakuliahmentah = kodematakuliah.getText();
        System.out.println("data kodematakuliah mentah: " + datakodematakuliahmentah);
        String [] datakodematakuliah = datakodematakuliahmentah.split(" ");
        System.out.println("data kodematakuliah yg dipilih: " + datakodematakuliah[0]);
        data[6] = datakodematakuliah[0];
        
        data[7] = (!kodekelasField.getText().equals("")?kodekelasField.getText():"X");
        data[8] = (!jmltatapmukarencanaField.getText().equals("")?jmltatapmukarencanaField.getText():"0");
        data[9] = (!jmltatapmukarealisasiField.getText().equals("")?jmltatapmukarealisasiField.getText():"0");
        
        String datajenisevaluasimentah = jenisevaluasi.getText();
        System.out.println("data jenisevaluasi mentah: " + datajenisevaluasimentah);
        String [] datajenisevaluasi = datajenisevaluasimentah.split(" ");
        System.out.println("data jenisevaluasi yg dipilih: " + datajenisevaluasi[0]);
        data[10] = datajenisevaluasi[0];
        
        String datacaraevaluasimentah = caraevaluasi.getText();
        System.out.println("data caraevaluasi mentah: " + datacaraevaluasimentah);
        String [] datacaraevaluasi = datacaraevaluasimentah.split(" ");
        System.out.println("data caraevaluasi yg dipilih: " + datacaraevaluasi[0]);
        data[11] = datacaraevaluasi[0];
        
        data[12] = "" + idlogaudit;
        data[13] = KodePT+""+kodematakuliah.getText();
        data[14] = (!uuildField.getText().equals("  ")?uuildField.getText():"0");
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];                        
        data[0] = semesterpelaporanField.getText();
        data[1] = kodejenjangstudi.getText();
        data[2] = jmltatapmukarencanaField.getText();
        data[3] = jmltatapmukarealisasiField.getText();
        data[4] = jenisevaluasi.getText();
        data[5] = caraevaluasi.getText();
        data[6] = idlogaudit+"";
        data[7] = KodePT+""+kodematakuliah.getText();
        data[8] = uuildField.getText();
        data[9] = nidnnuktField.getText();
        data[10] = tahunpelaporanField.getText();
        data[11] = kodeperguruantinggiField.getText();        
        data[12] = kodeprogramstudi.getText();
        data[14] = kodematakuliah.getText();
        data[15] = kodekelasField.getText();
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[1];
        data[0] = nidnnuktField.getText();
        data[1] = tahunpelaporanField.getText();
        data[2] = kodeperguruantinggiField.getText();
        data[3] = kodeprogramstudi.getText();
        data[4] = kodematakuliah.getText();
        data[5] = kodekelasField.getText();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jSplitPane2 = new javax.swing.JSplitPane();
        gulungPanelAtribut = new javax.swing.JScrollPane();
        panelAtribut = new javax.swing.JPanel();
        panelLabel = new javax.swing.JPanel();
        kodemengajardosenLabel = new javax.swing.JLabel();
        nidnnukLabel = new javax.swing.JLabel();
        tahunpelaporanLabel = new javax.swing.JLabel();
        semesterpelaporanLabel = new javax.swing.JLabel();
        kodeperguruantinggiLabel = new javax.swing.JLabel();
        kodeprogramstudiLabel = new javax.swing.JLabel();
        kodejenjangstudiLabel = new javax.swing.JLabel();
        kodematakuliahLabel = new javax.swing.JLabel();
        kodekelasLabel = new javax.swing.JLabel();
        jmltatapmukarencanaLabel = new javax.swing.JLabel();
        jmltatapmukarealisasiLabel = new javax.swing.JLabel();
        jenisevaluasiLabel = new javax.swing.JLabel();
        caraevaluasiLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        IDlogauditLabel1 = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodemengajardosenField = new javax.swing.JFormattedTextField();
        nidnnuktField = new javax.swing.JFormattedTextField();
        tahunpelaporanField = new javax.swing.JFormattedTextField();
        semesterpelaporanField = new javax.swing.JFormattedTextField();
        kodeperguruantinggiField = new javax.swing.JFormattedTextField();
        kodeprogramstudiField = new javax.swing.JComboBox();
        kodejenjangstudiField = new javax.swing.JComboBox();
        kodematakuliahField = new javax.swing.JComboBox();
        kodekelasField = new javax.swing.JTextField();
        jmltatapmukarencanaField = new javax.swing.JFormattedTextField();
        jmltatapmukarealisasiField = new javax.swing.JFormattedTextField();
        jenisevaluasiField = new javax.swing.JComboBox();
        caraevaluasiField = new javax.swing.JComboBox();
        IDlogauditField = new javax.swing.JFormattedTextField();
        uuildField = new javax.swing.JFormattedTextField();
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

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jSplitPane2.setDividerLocation(700);
        jSplitPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        panelAtribut.setLayout(new java.awt.BorderLayout());

        panelLabel.setLayout(new java.awt.GridLayout(15, 0, 0, 5));

        kodemengajardosenLabel.setText("Kode Mengajar Dosen:");
        panelLabel.add(kodemengajardosenLabel);

        nidnnukLabel.setText("NIDN/NUK:");
        panelLabel.add(nidnnukLabel);

        tahunpelaporanLabel.setText("Tahun Pelaporan:");
        panelLabel.add(tahunpelaporanLabel);

        semesterpelaporanLabel.setText("Semester Pelaporan:");
        panelLabel.add(semesterpelaporanLabel);

        kodeperguruantinggiLabel.setText("Kode Perguruan Tinggi:");
        panelLabel.add(kodeperguruantinggiLabel);

        kodeprogramstudiLabel.setText("Kode Program Studi:");
        panelLabel.add(kodeprogramstudiLabel);

        kodejenjangstudiLabel.setText("Kode Jenjang Studi:");
        panelLabel.add(kodejenjangstudiLabel);

        kodematakuliahLabel.setText("Kode Mata Kuliah:");
        panelLabel.add(kodematakuliahLabel);

        kodekelasLabel.setText("Kode Kelas:");
        panelLabel.add(kodekelasLabel);

        jmltatapmukarencanaLabel.setText("Jml Tatap Muka Rencana:");
        panelLabel.add(jmltatapmukarencanaLabel);

        jmltatapmukarealisasiLabel.setText("Jml Tatap Muka Realisasi:");
        panelLabel.add(jmltatapmukarealisasiLabel);

        jenisevaluasiLabel.setText("Jenis Evaluasi:");
        panelLabel.add(jenisevaluasiLabel);

        caraevaluasiLabel.setText("Cara Evaluasi:");
        panelLabel.add(caraevaluasiLabel);

        IDlogauditLabel.setText("IDlogaudit:");
        panelLabel.add(IDlogauditLabel);

        IDlogauditLabel1.setText("UUID");
        panelLabel.add(IDlogauditLabel1);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(15, 0, 0, 5));

        kodemengajardosenField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(kodemengajardosenField);

        try {
            nidnnuktField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(nidnnuktField);

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
            kodeperguruantinggiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodeperguruantinggiField);

        panelTextField.add(kodeprogramstudiField);

        panelTextField.add(kodejenjangstudiField);

        panelTextField.add(kodematakuliahField);
        panelTextField.add(kodekelasField);

        jmltatapmukarencanaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##"))));
        panelTextField.add(jmltatapmukarencanaField);

        jmltatapmukarealisasiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##"))));
        panelTextField.add(jmltatapmukarealisasiField);

        panelTextField.add(jenisevaluasiField);

        panelTextField.add(caraevaluasiField);

        IDlogauditField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        IDlogauditField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDlogauditFieldActionPerformed(evt);
            }
        });
        panelTextField.add(IDlogauditField);

        uuildField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        uuildField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uuildFieldActionPerformed(evt);
            }
        });
        panelTextField.add(uuildField);

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
        " values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukInsert() );
        int hasil = model.insertAktifitasDOsen(query, dapatkanNilaiUntukInsert());
        if (hasil == 1) {
            query = "select * from " + namaTabel + "where \"Kode_perguruan_tinggi\"="+KodePT;
            kon.selectData(query);
            tampilkanDataKeTabel(kon.dapatkanData(), ubahLabelKeSentenceCase(kon.dapatkanKolom()));
            
            //insert data ke TREF_LOG_AUDIT
            query = "insert into \"TREF_LOG_AUDIT\" " +
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
       "SET \"Semester_pelaporan\"=?,"
                + "\"Kode_jenjang_studi\"=?, "
                + "\"Jml_tatap_muka_rencana\"=?, "
                + "\"Jml_tatap_muka_realisasi\"=?, "
                + "\"Jenis_evaluasi\"=?, "
                + "\"Cara_evaluasi\"=?, "
                + "\"ID_log_audit\"=?, "
                + "\"UUID\"=?, "
                + "\"SKS\"=? "
                + " WHERE \"NIDN/NUK\"=? and "
                + "\"Tahun_pelaporan\"=? and "
                + "\"Kode_perguruan_tinggi\"=? and "
                + "\"Kode_program_studi\"=? and "
                + "\"Kode_mata_kuliah\"=? and "
                + "\"Kode_kelas\"=?";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukUpdate() );
         int hasil = model.updateAktifitasDOsen(query, dapatkanNilaiUntukUpdate());
        if (hasil == 1) {
            query = "select * from " + namaTabel+ "where \"Kode_perguruan_tinggi\"="+KodePT;
            kon.selectData(query);
            tampilkanDataKeTabel(kon.dapatkanData(), ubahLabelKeSentenceCase(kon.dapatkanKolom()));
            
            //insert data ke TREF_LOG_AUDIT
            query = "insert into \"TREF_LOG_AUDIT\" " +
                    " values(?,?,?,?,?,?)";
            hasil = kon.insertUpdateDeleteData(query,
                            dapatkanNilaiUntukInsertKeLogAudit("UPDATE", 
                                namaTabel, 
                                kon.dapatkanQueryUtkUpdateStatement(namaTabel, 
                                    dapatkanNilaiUntukUpdate())) );
            persiapanEntriDataBaru();
        }
    }//GEN-LAST:event_buttonUpdateActionPerformed

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        // TODO add your handling code here:
        initIDLogAudit();
        String query = "delete from " + namaTabel + " WHERE " +
        " \"NIDN/NUK\"=? and \"Tahun_pelaporan\"=? and \"Kode_perguruan_tinggi\"=? and \"Kode_program_studi\"=? "
                + "and \"Kode_mata_kuliah\"=? and \"Kode_kelas\"=?";
        
        String [] pkDataYgDihapus = dapatkanNilaiUntukDelete();

        int hasil = model.Delete(query,
            dapatkanNilaiUntukDelete() );
        if (hasil == 1) {
            query = "select * from " + namaTabel +"where \"Kode_perguruan_tinggi\"="+KodePT;
            kon.selectData(query);
            tampilkanDataKeTabel(kon.dapatkanData(), ubahLabelKeSentenceCase(kon.dapatkanKolom()));
            persiapanEntriDataBaru();
            
            //insert data ke TREF_LOG_AUDIT
            query = "insert into \"TREF_LOG_AUDIT\" " +
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

    private void IDlogauditFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDlogauditFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IDlogauditFieldActionPerformed

    private void uuildFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uuildFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_uuildFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField IDlogauditField;
    private javax.swing.JLabel IDlogauditLabel;
    private javax.swing.JLabel IDlogauditLabel1;
    private Widget.Button buttonBaru;
    private Widget.Button buttonDelete;
    private Widget.Button buttonFirst;
    private Widget.Button buttonInsert;
    private Widget.Button buttonLast;
    private Widget.Button buttonNext;
    private Widget.Button buttonPrev;
    private Widget.Button buttonUpdate;
    private javax.swing.JComboBox caraevaluasiField;
    private javax.swing.JLabel caraevaluasiLabel;
    private javax.swing.JScrollPane gulungPanelAtribut;
    private javax.swing.JScrollPane gulungTabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JComboBox jenisevaluasiField;
    private javax.swing.JLabel jenisevaluasiLabel;
    private javax.swing.JFormattedTextField jmltatapmukarealisasiField;
    private javax.swing.JLabel jmltatapmukarealisasiLabel;
    private javax.swing.JFormattedTextField jmltatapmukarencanaField;
    private javax.swing.JLabel jmltatapmukarencanaLabel;
    private javax.swing.JComboBox kodejenjangstudiField;
    private javax.swing.JLabel kodejenjangstudiLabel;
    private javax.swing.JTextField kodekelasField;
    private javax.swing.JLabel kodekelasLabel;
    private javax.swing.JComboBox kodematakuliahField;
    private javax.swing.JLabel kodematakuliahLabel;
    private javax.swing.JFormattedTextField kodemengajardosenField;
    private javax.swing.JLabel kodemengajardosenLabel;
    private javax.swing.JFormattedTextField kodeperguruantinggiField;
    private javax.swing.JLabel kodeperguruantinggiLabel;
    private javax.swing.JComboBox kodeprogramstudiField;
    private javax.swing.JLabel kodeprogramstudiLabel;
    private javax.swing.JLabel nidnnukLabel;
    private javax.swing.JFormattedTextField nidnnuktField;
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
    private javax.swing.JFormattedTextField uuildField;
    // End of variables declaration//GEN-END:variables
}
