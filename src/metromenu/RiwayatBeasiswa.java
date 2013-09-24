/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelTransaksiRiwayatBeasiswa;
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

/**
 *
 * @author asus
 */
public class RiwayatBeasiswa extends javax.swing.JPanel {
    private JPanel panel;
    private koneksi.KoneksiOracleThinClient kon;
    private String judul;
    private HomePage homePage;
    private String namaTabel;
    private String query;
    private int jmlKolom;
    private int barisYgDipilih = 0;
    
    private JTextField kodeprogramstudi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo1;
    private JTextField kodejenjangpendidikan;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    private JTextField kodebeasiswa;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    private JTextField nim;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo4;
    Model.ModelTransaksiRiwayatBeasiswa model;
    private int koderiwayatmahasiswabeasiswa, idlogaudit;
    private String kueri,kodept;

    /**
     * Creates new form PanelHalaman1
     */
    public RiwayatBeasiswa() {    
        initComponents();
    }
    
    public RiwayatBeasiswa(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        kodept = homePage.dapatkanKodePT();
        kodeperguruantinggiField.setText(kodept);
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        model = new ModelTransaksiRiwayatBeasiswa();
        inisialisasiData();
        initAutoComplete();        
        koderiwayatmhsbeasiswaField.setVisible(false);
        kodebeasiswaLabel.setVisible(false);
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
    }
    
    private void initPKAutoIncrement() {
        query = "select \"Kode_riwayat_mhs_beasiswa\" " +
                " from pdpt_dev.\"TRAN_RIWAYAT_MHS_BEASISWA\" " +
                " order by \"Kode_riwayat_mhs_beasiswa\" desc";
        kon.selectData(query);     
        String [][] data = kon.dapatkanData();
        if (data[0][0] == null) {
            koderiwayatmahasiswabeasiswa = 1;
        } else {
            koderiwayatmahasiswabeasiswa = Integer.parseInt(data[0][0]);
            koderiwayatmahasiswabeasiswa++;            
        }
        System.out.println("koderiwayatmahasiswabeasiswa: " + koderiwayatmahasiswabeasiswa);
    }
    
    private void inisialisasiData() {
        namaTabel = "pdpt_dev.\"TRAN_RIWAYAT_MHS_BEASISWA\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select \"Tahun_pelaporan\",\"Semester_pelaporan\",\"Kode_perguruan_tinggi\","
                + "\"Kode_program_studi\",\"Kode_jenjang_pendidikan\",\"NIM\",\"Kode_beasiswa\" from " + namaTabel + " "
                + "where by \"Kode_perguruan_tinggi\"='"+homePage.dapatkanKodePT()+"'";
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    private String [] ubahLabelKeSentenceCase(String [] namaKolom) {
        String [] data = new String[namaKolom.length-1];
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
    
    private void initAutoComplete() {
        kompletTextFieldCombo1 = new KompletTextFieldCombo(kon, 
                kodeprogramstudiField, kodeprogramstudi);        
        query = "select \"Kode_program_studi\", \"Nama_program_studi\" " +
                " from pdpt_dev.\"TMST_PROGRAM_STUDI\" where \"Kode_perguruan_tinggi\"='"+kodept+"'"; 
        kompletTextFieldCombo1.initDataUtkAutoComplete(query);        
        kodeprogramstudi = kompletTextFieldCombo1.dapatkanTextField();
        kodeprogramstudiField = kompletTextFieldCombo1.dapatkanComboBox();
        
        kodeprogramstudi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kodeprogramstudiKeyPressed(evt);
            }
        });
        
        kompletTextFieldCombo2 = new KompletTextFieldCombo(kon, 
                kodejenjangpendidikanField, kodejenjangpendidikan);        
        query = "select \"Kode_jenjang_pendidikan\", \"Deskripsi\" " +
                " from pdpt_dev.\"TREF_JENJANG_PENDIDIKAN\""; 
        kompletTextFieldCombo2.initDataUtkAutoComplete(query);        
        kodejenjangpendidikan = kompletTextFieldCombo2.dapatkanTextField();
        kodejenjangpendidikanField = kompletTextFieldCombo2.dapatkanComboBox();
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                kodebeasiswaField, kodebeasiswa);        
        query = "select \"Kode_beasiswa\", \"Jenis_beasiswa\" " +
                " from pdpt_dev.\"TREF_BEASISWA\""; 
        kompletTextFieldCombo3.initDataUtkAutoComplete(query);        
        kodebeasiswa = kompletTextFieldCombo3.dapatkanTextField();
        kodebeasiswaField = kompletTextFieldCombo3.dapatkanComboBox();
        
        kompletTextFieldCombo4 = new KompletTextFieldCombo(kon, 
                nimField, nim);
        query = "select \"NIM\", \"Nama_mahasiswa\" " +
                " from pdpt_dev.\"TMST_MAHASISWA\" where \"Kode_perguruan_tinggi\"='"+kodept+"'";/* +
                " where Kode_perguruan_tinggi='" +  + " '" + 
                " and Kode_program_studi=' " +  + "'";*/
        kompletTextFieldCombo4.initDataUtkAutoComplete(query);        
        nim = kompletTextFieldCombo4.dapatkanTextField();
        nimField = kompletTextFieldCombo4.dapatkanComboBox();
    }
    
    private void initIDLogAudit() {
        
        query = "select \"Kode_riwayat_mhs_beasiswa\" " +
                " from pdpt_dev.\"TRAN_RIWAYAT_MHS_BEASISWA\" " +
                " order by \"Kode_riwayat_mhs_beasiswa\" desc";
        kon.selectData(query);     
        String [][] data = kon.dapatkanData();
        try{
        koderiwayatmahasiswabeasiswa = Integer.parseInt(data[0][0]);
        koderiwayatmahasiswabeasiswa++;
        }catch(Exception e){
            koderiwayatmahasiswabeasiswa=1;
        }
        
        System.out.println("koderiwayatmahasiswabeasiswa: " + koderiwayatmahasiswabeasiswa);
        
        
        query = "select \"ID_log_audit\" " +
                " from pdpt_dev.\"TREF_LOG_AUDIT\" " +
                " order by \"ID_log_audit\" DESC";
        kon.selectData(query);     
        data = kon.dapatkanData();
        if (data[0][0] == null) {
            idlogaudit = 1;
        } else {
            idlogaudit = Integer.parseInt(data[0][0]);
            idlogaudit++;
        }
        System.out.println("idlogaudit: " + idlogaudit);
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
    
    public RiwayatBeasiswa(String [][] data, String [] kolom) {
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
        koderiwayatmhsbeasiswaField.setText("" + koderiwayatmahasiswabeasiswa);
        tahunpelaporanField.setText("");
        semesterpelaporanField.setText("");
        
        //kodeperguruantinggiField.setText("" + homePage.dapatkanKodePT());//utk pdpt pt        
        kodeperguruantinggiField.setText("");//utk pdpt dikti
        kodeprogramstudi.setText("");
        kodejenjangpendidikan.setText("");
        
        nim.setText("");        
        kodebeasiswa.setText("");
        IDlogauditField.setText("");
        
        tahunpelaporanField.requestFocus();
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
        koderiwayatmhsbeasiswaField.setText(tabel.getValueAt(row, 0).toString());
        tahunpelaporanField.setText(tabel.getValueAt(row, 1).toString());
        semesterpelaporanField.setText(tabel.getValueAt(row, 2).toString());        
        kodeperguruantinggiField.setText(tabel.getValueAt(row, 3).toString());
        kodeprogramstudi.setText(tabel.getValueAt(row, 4).toString());
        kodejenjangpendidikan.setText(tabel.getValueAt(row, 5).toString());        
        nim.setText(tabel.getValueAt(row, 6).toString());        
        kodebeasiswa.setText(tabel.getValueAt(row, 7).toString());
        //IDlogauditField.setText(tabel.getValueAt(row, 8).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
//        data[0] = (!koderiwayatmhsbeasiswaField.getText().equals("")?koderiwayatmhsbeasiswaField.getText():"0");
        data[0] = (!tahunpelaporanField.getText().equals("    ")?tahunpelaporanField.getText():"0000");
        data[1] = (!semesterpelaporanField.getText().equals(" ")?semesterpelaporanField.getText():"0");
        
        data[2] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"000000");
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[3] = datakodeprogramstudi[0];
        
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[4] = datakodejenjangpendidikan[0];
        
        String datanimmentah = nim.getText();
        System.out.println("data nim mentah: " + datanimmentah);
        String [] datanim = datanimmentah.split(" ");
        System.out.println("data nim yg dipilih: " + datanim[0]);
        data[5] = datanim[0];
        
        String datakodebeasiswamentah = kodebeasiswa.getText();
        System.out.println("data kodebeasiswa mentah: " + datakodebeasiswamentah);
        String [] datakodebeasiswa = datakodebeasiswamentah.split(" ");
        System.out.println("data kodebeasiswa yg dipilih: " + datakodebeasiswa[0]);
        data[6] = datakodebeasiswa[0];
        
        data[7] = "" + idlogaudit;
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];  
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[0] = datakodeprogramstudi[0];
        
         String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[1] = datakodejenjangpendidikan[0];
        
        String datakodebeasiswamentah = kodebeasiswa.getText();
        System.out.println("data kodebeasiswa mentah: " + datakodebeasiswamentah);
        String [] datakodebeasiswa = datakodebeasiswamentah.split(" ");
        System.out.println("data kodebeasiswa yg dipilih: " + datakodebeasiswa[0]);
        data[2] = datakodebeasiswa[0];
        data[3] = "" + idlogaudit;
        
        data[4] = (!tahunpelaporanField.getText().equals("    ")?tahunpelaporanField.getText():"0000");
        data[5] = (!semesterpelaporanField.getText().equals(" ")?semesterpelaporanField.getText():"0");
        
        data[6] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"000000");
        
        String datanimmentah = nim.getText();
        System.out.println("data nim mentah: " + datanimmentah);
        String [] datanim = datanimmentah.split(" ");
        System.out.println("data nim yg dipilih: " + datanim[0]);
        data[7] = datanim[0];
       
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[4];
        data[0] = (!tahunpelaporanField.getText().equals("    ")?tahunpelaporanField.getText():"0000");
        data[1] = (!semesterpelaporanField.getText().equals(" ")?semesterpelaporanField.getText():"0");
        
        data[2] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"000000");
        
        String datanimmentah = nim.getText();
        System.out.println("data nim mentah: " + datanimmentah);
        String [] datanim = datanimmentah.split(" ");
        System.out.println("data nim yg dipilih: " + datanim[0]);
        data[3] = datanim[0];
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
        koderiwayatmhsbeasiswaLabel = new javax.swing.JLabel();
        tahunpelaporanLabel = new javax.swing.JLabel();
        semesterpelaporanLabel = new javax.swing.JLabel();
        kodeperguruantinggiLabel = new javax.swing.JLabel();
        kodeprogramstudiLabel = new javax.swing.JLabel();
        kodejenjangpendidikanLabel = new javax.swing.JLabel();
        nimLabel = new javax.swing.JLabel();
        kodebeasiswaLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        koderiwayatmhsbeasiswaField = new javax.swing.JFormattedTextField();
        tahunpelaporanField = new javax.swing.JFormattedTextField();
        semesterpelaporanField = new javax.swing.JFormattedTextField();
        kodeperguruantinggiField = new javax.swing.JFormattedTextField();
        kodeprogramstudiField = new javax.swing.JComboBox();
        kodejenjangpendidikanField = new javax.swing.JComboBox();
        nimField = new javax.swing.JComboBox();
        kodebeasiswaField = new javax.swing.JComboBox();
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

        panelLabel.setLayout(new java.awt.GridLayout(9, 0));

        koderiwayatmhsbeasiswaLabel.setText("Kode Riwayat Mhs Beasiswa:");
        panelLabel.add(koderiwayatmhsbeasiswaLabel);

        tahunpelaporanLabel.setText("Tahun Pelaporan:");
        panelLabel.add(tahunpelaporanLabel);

        semesterpelaporanLabel.setText("Semester Pelaporan:");
        panelLabel.add(semesterpelaporanLabel);

        kodeperguruantinggiLabel.setText("Kode Perguruan Tinggi:");
        panelLabel.add(kodeperguruantinggiLabel);

        kodeprogramstudiLabel.setText("Kode Program Studi:");
        panelLabel.add(kodeprogramstudiLabel);

        kodejenjangpendidikanLabel.setText("Kode Jenjang Pendidikan:");
        panelLabel.add(kodejenjangpendidikanLabel);

        nimLabel.setText("NIM:");
        panelLabel.add(nimLabel);

        kodebeasiswaLabel.setText("Kode Beasiswa:");
        panelLabel.add(kodebeasiswaLabel);

        IDlogauditLabel.setText("ID Log Audit:");
        panelLabel.add(IDlogauditLabel);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(9, 0));

        koderiwayatmhsbeasiswaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(koderiwayatmhsbeasiswaField);

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

        panelTextField.add(kodejenjangpendidikanField);

        panelTextField.add(nimField);

        panelTextField.add(kodebeasiswaField);

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
        " values(0,?,?,?,?,?,?,?,?)";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukInsert() );
        int hasil = model.insertAktifitasDOsen(query,
            dapatkanNilaiUntukInsert() );
        if (hasil == 1) {
            query = "select \"Tahun_pelaporan\",\"Semester_pelaporan\",\"Kode_perguruan_tinggi\","
                + "\"Kode_program_studi\",\"Kode_jenjang_pendidikan\",\"NIM\",\"Kode_beasiswa\" from " + namaTabel + " "
                + "where by \"Kode_perguruan_tinggi\"='"+homePage.dapatkanKodePT()+"'";
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
        " SET "+
        "\"Kode_program_studi\"=?, " +
        "\"Kode_jenjang_pendidikan\"=?, " +
        "\"Kode_beasiswa\"=?, " +
        "\"ID_log_audit\"=? " +
        " WHERE \"Tahun_pelaporan\"=? and " +
        "\"Semester_pelaporan\"=? and " +        
        "\"Kode_perguruan_tinggi\"=? and " +
        "\"NIM\"=?" ;
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukUpdate() );
        int hasil = model.updateAktifitasDOsen(query,
            dapatkanNilaiUntukUpdate() );
        if (hasil == 1) {
            query = "select \"Tahun_pelaporan\",\"Semester_pelaporan\",\"Kode_perguruan_tinggi\","
                + "\"Kode_program_studi\",\"Kode_jenjang_pendidikan\",\"NIM\",\"Kode_beasiswa\" from " + namaTabel + " "
                + "where by \"Kode_perguruan_tinggi\"='"+homePage.dapatkanKodePT()+"'";
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
        String query = "delete from " + namaTabel + " WHERE \"Tahun_pelaporan\"=? and " +
        "\"Semester_pelaporan\"=? and " +        
        "\"Kode_perguruan_tinggi\"=? and " +
        "\"NIM\"=?" ;
        
        String [] pkDataYgDihapus = dapatkanNilaiUntukDelete();

        int hasil = kon.insertUpdateDeleteData(query,
            dapatkanNilaiUntukDelete() );
        if (hasil == 1) {
            query = "select \"Tahun_pelaporan\",\"Semester_pelaporan\",\"Kode_perguruan_tinggi\","
                + "\"Kode_program_studi\",\"Kode_jenjang_pendidikan\",\"NIM\",\"Kode_beasiswa\" from " + namaTabel + " "
                + "where by \"Kode_perguruan_tinggi\"='"+homePage.dapatkanKodePT()+"'";
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

    private void kodeprogramstudiKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) { //jika diENTER dimulai query NIM
            kompletTextFieldCombo4 = new KompletTextFieldCombo(kon, 
                nimField, nim);
            String datakodeprogramstudimentah = kodeprogramstudi.getText();
            System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
            String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
            System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
            //data[1] = datakodeprogramstudi[0];
            query = "select NIM, Nama_mahasiswa, Kode_program_studi " +
                    " from TMST_MAHASISWA " + 
                    " where Kode_perguruan_tinggi='" + kodeperguruantinggiField.getText() + "'" + 
                    " and Kode_program_studi='" + datakodeprogramstudi[0] + "'";
            kompletTextFieldCombo4.initDataUtkAutoComplete(query);        
            nim = kompletTextFieldCombo4.dapatkanTextField();
            nimField = kompletTextFieldCombo4.dapatkanComboBox();
            System.out.println("kodeps diENTER: " + query);
            nim.requestFocus();
        }
    }
    
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
    private javax.swing.JScrollPane gulungPanelAtribut;
    private javax.swing.JScrollPane gulungTabel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JComboBox kodebeasiswaField;
    private javax.swing.JLabel kodebeasiswaLabel;
    private javax.swing.JComboBox kodejenjangpendidikanField;
    private javax.swing.JLabel kodejenjangpendidikanLabel;
    private javax.swing.JFormattedTextField kodeperguruantinggiField;
    private javax.swing.JLabel kodeperguruantinggiLabel;
    private javax.swing.JComboBox kodeprogramstudiField;
    private javax.swing.JLabel kodeprogramstudiLabel;
    private javax.swing.JFormattedTextField koderiwayatmhsbeasiswaField;
    private javax.swing.JLabel koderiwayatmhsbeasiswaLabel;
    private javax.swing.JComboBox nimField;
    private javax.swing.JLabel nimLabel;
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
    // End of variables declaration//GEN-END:variables
}
