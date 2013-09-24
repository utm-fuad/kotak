/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelBadanHukum;
import metromini.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
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
public class MasterBadanHukum extends javax.swing.JPanel {
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
    Model.ModelBadanHukum model;
    private JTextField kodekota;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo;
    private JTextField kodeprovinsi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    private JTextField kodenegara;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    private JTextField statusvalidasi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo4;
    
    private int idlogaudit;
    private String kueri;

    /**
     * Creates new form PanelHalaman1
     */
    public MasterBadanHukum() {    
        initComponents();
    }
    
    public MasterBadanHukum(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        model = new ModelBadanHukum();
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        
        inisialisasiData();
        initAutoComplete();
         //disable all button
         buttonBaru.setEnabled(false);
        buttonInsert.setEnabled(false);
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
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
        kon = homePage.dapatkanKoneksiDB();
        query = "select * from pdpt_dev.\"TMST_BADAN_HUKUM\"";
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public MasterBadanHukum(String [][] data, String [] kolom) {
        this();
        tampilkanDataKeTabel(data, kolom);
    }
    
    private void initAutoComplete() {
        kompletTextFieldCombo = new KompletTextFieldCombo(kon, 
                kodekotaField, kodekota);        
        query = "SELECT \"Kode_kota\", \"Nama_kabupaten\" " +
                " from pdpt_dev.\"TREF_KOTA\""; 
        kompletTextFieldCombo.initDataUtkAutoComplete(query);        
        kodekota = kompletTextFieldCombo.dapatkanTextField();
        kodekotaField = kompletTextFieldCombo.dapatkanComboBox();        
        
        
        kompletTextFieldCombo2 = new KompletTextFieldCombo(kon, 
                kodeprovinsiField, kodeprovinsi);        
        query = "SELECT \"Kode_provinsi\", \"Nama_provinsi\" " +
                " FROM pdpt_dev.\"TREF_PROVINSI\" " +
                " WHERE \"Kode_negara\"='1'"; 
        kompletTextFieldCombo2.initDataUtkAutoComplete(query);        
        kodeprovinsi = kompletTextFieldCombo2.dapatkanTextField();
        kodeprovinsiField = kompletTextFieldCombo2.dapatkanComboBox();
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                kodenegaraField, kodenegara);        
        query = "SELECT \"Kode_negara\", \"Nama_negara\" " +
                " FROM pdpt_dev.\"TREF_NEGARA\""; 
        kompletTextFieldCombo3.initDataUtkAutoComplete(query);        
        kodenegara = kompletTextFieldCombo3.dapatkanTextField();
        kodenegaraField = kompletTextFieldCombo3.dapatkanComboBox();
        
        kompletTextFieldCombo4 = new KompletTextFieldCombo(kon, 
                statusvalidasiField, statusvalidasi);        
        String [][] datanya = {{"1", "Belum diverifikasi"},
            {"2","Sudah diverifikasi namun masih terdapat data yang invalid"},
            {"3", "Sudah diverifikasi dan data sudah valid"}}; 
        kompletTextFieldCombo4.initDataUtkAutoComplete(datanya);        
        statusvalidasi = kompletTextFieldCombo4.dapatkanTextField();
        statusvalidasiField = kompletTextFieldCombo4.dapatkanComboBox();
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
        kodebadanhukumLabel = new javax.swing.JLabel();
        namabadanhukumLabel = new javax.swing.JLabel();
        tanggalawalberdiriLabel = new javax.swing.JLabel();
        alamatLabel = new javax.swing.JLabel();
        kodekotaLabel = new javax.swing.JLabel();
        kodeprovinsiLabel = new javax.swing.JLabel();
        kodenegaraLabel = new javax.swing.JLabel();
        kodePosLabel = new javax.swing.JLabel();
        telephoneLabel = new javax.swing.JLabel();
        faximiliLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        websiteLabel = new javax.swing.JLabel();
        nomoraktaterakhirLabel = new javax.swing.JLabel();
        tanggalaktaterakhirLabel = new javax.swing.JLabel();
        nomorpengesahanPNLNLabel = new javax.swing.JLabel();
        statusValidasiLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        IDlogauditLabel1 = new javax.swing.JLabel();
        IDlogauditLabel2 = new javax.swing.JLabel();
        IDlogauditLabel3 = new javax.swing.JLabel();
        IDlogauditLabel4 = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodebadanhukumField = new javax.swing.JFormattedTextField();
        namabadanhukumField = new javax.swing.JTextField();
        tanggalAwalBerdiriField = new com.toedter.calendar.JDateChooser();
        alamatField = new javax.swing.JTextField();
        kodekotaField = new javax.swing.JComboBox();
        kodeprovinsiField = new javax.swing.JComboBox();
        kodenegaraField = new javax.swing.JComboBox();
        kodeposField = new javax.swing.JFormattedTextField();
        telephoneField = new javax.swing.JFormattedTextField();
        faximiliField = new javax.swing.JFormattedTextField();
        emailField = new javax.swing.JTextField();
        websiteField = new javax.swing.JTextField();
        nomoraktaterakhirField = new javax.swing.JTextField();
        tanggalAktaTerakhirField = new com.toedter.calendar.JDateChooser();
        nomorpengesahanPNLNField = new javax.swing.JTextField();
        statusvalidasiField = new javax.swing.JComboBox();
        IDlogauditField = new javax.swing.JTextField();
        noSkAwalField = new javax.swing.JTextField();
        tanggalAwalSKField = new com.toedter.calendar.JDateChooser();
        noSKField = new javax.swing.JTextField();
        tanggalSKField = new com.toedter.calendar.JDateChooser();
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

        kodebadanhukumLabel.setText("Kode Badan Hukum:");
        panelLabel.add(kodebadanhukumLabel);

        namabadanhukumLabel.setText("Nama Badan Hukum:");
        panelLabel.add(namabadanhukumLabel);

        tanggalawalberdiriLabel.setText("Tanggal Awal Berdiri:");
        panelLabel.add(tanggalawalberdiriLabel);

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

        telephoneLabel.setText("Telephone:");
        panelLabel.add(telephoneLabel);

        faximiliLabel.setText("Faximili:");
        panelLabel.add(faximiliLabel);

        emailLabel.setText("Email:");
        panelLabel.add(emailLabel);

        websiteLabel.setText("Website:");
        panelLabel.add(websiteLabel);

        nomoraktaterakhirLabel.setText("Nomor Akta Terakhir:");
        panelLabel.add(nomoraktaterakhirLabel);

        tanggalaktaterakhirLabel.setText("Tanggal Akta Terakhir:");
        panelLabel.add(tanggalaktaterakhirLabel);

        nomorpengesahanPNLNLabel.setText("Nomor Pengesahan PNLN:");
        panelLabel.add(nomorpengesahanPNLNLabel);

        statusValidasiLabel.setText("Status Validasi:");
        panelLabel.add(statusValidasiLabel);

        IDlogauditLabel.setText("IDlogaudit:");
        panelLabel.add(IDlogauditLabel);

        IDlogauditLabel1.setText("No SK Awal Berdiri");
        panelLabel.add(IDlogauditLabel1);

        IDlogauditLabel2.setText("Tanggal SK Awal");
        panelLabel.add(IDlogauditLabel2);

        IDlogauditLabel3.setText("No SK Ban PT");
        panelLabel.add(IDlogauditLabel3);

        IDlogauditLabel4.setText("Tanggal SK Ban PT");
        panelLabel.add(IDlogauditLabel4);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(21, 0));

        try {
            kodebadanhukumField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("1######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodebadanhukumField);
        panelTextField.add(namabadanhukumField);
        panelTextField.add(tanggalAwalBerdiriField);
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

        telephoneField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(telephoneField);

        faximiliField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(faximiliField);
        panelTextField.add(emailField);
        panelTextField.add(websiteField);
        panelTextField.add(nomoraktaterakhirField);
        panelTextField.add(tanggalAktaTerakhirField);
        panelTextField.add(nomorpengesahanPNLNField);

        panelTextField.add(statusvalidasiField);
        panelTextField.add(IDlogauditField);
        panelTextField.add(noSkAwalField);
        panelTextField.add(tanggalAwalSKField);
        panelTextField.add(noSKField);
        panelTextField.add(tanggalSKField);

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
        tabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabel.setEnabled(false);
        tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMouseClicked(evt);
            }
        });
        tabel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelKeyPressed(evt);
            }
        });
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
        //kodebadanhukumField.setText("1" + homePage.dapatkanKodePT());//kode ini utk PDPT PT
        kodebadanhukumField.setText("1");//kode ini utk PDPT DIKTI
        namabadanhukumField.setText("");
        tanggalAwalBerdiriField.setDate(null);                
        alamatField.setText("");                
        kodekota.setText("");
        kodeprovinsi.setText("");
        kodenegara.setText("");        
        kodeposField.setText("");
        telephoneField.setText("");
        faximiliField.setText("");
        emailField.setText("");
        websiteField.setText("");
        nomoraktaterakhirField.setText("");
        tanggalAktaTerakhirField.setDate(null);
        nomorpengesahanPNLNField.setText("");
        statusvalidasi.setText("");
        IDlogauditField.setText("");
        //namabadanhukumField.requestFocus();//kode ini utk PDPT PT
        noSkAwalField.setText("");
        noSKField.setText("");
        kodebadanhukumField.requestFocus();//kode ini utk PDPT DIKTI
    }
    
    private void tampilkanData(int row) {
        System.out.println("baris yg dipilih utk diUPDATE: "  + row);
        persiapanEntriDataBaru();
        kodebadanhukumField.setText(tabel.getValueAt(row, 0).toString());
        namabadanhukumField.setText(tabel.getValueAt(row, 1).toString());
        String strKalendar = tabel.getValueAt(row, 2).toString();
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
        tanggalAwalBerdiriField.setCalendar(kalendar);
        
        alamatField.setText(tabel.getValueAt(row, 3).toString());
        kodekota.setText(tabel.getValueAt(row, 4).toString());
        kodeprovinsi.setText(tabel.getValueAt(row, 5).toString());
        kodenegara.setText(tabel.getValueAt(row, 6).toString());
        
        kodeposField.setText(tabel.getValueAt(row, 7).toString());
        telephoneField.setText(tabel.getValueAt(row, 8).toString());
        faximiliField.setText(tabel.getValueAt(row, 9).toString());
        emailField.setText(tabel.getValueAt(row, 10).toString());
        websiteField.setText(tabel.getValueAt(row, 11).toString());
        nomoraktaterakhirField.setText(tabel.getValueAt(row, 12).toString());
        strKalendar = tabel.getValueAt(row, 13).toString();
        tahun = Integer.parseInt( strKalendar.substring(0, 4) );
        System.out.println("" + strKalendar.substring(0, 4)  + " tahun: " + tahun);
        intBulan = Integer.parseInt( strKalendar.substring(5, 7) );
        System.out.println("" + strKalendar.substring(5, 7)  + " intBulan: " + intBulan);
        hari = Integer.parseInt( strKalendar.substring(8, 10) );
        System.out.println("" + strKalendar.substring(8, 10)  + " hari: " + hari);
        kalendar.set(tahun, intBulan - 1, hari); 
        tanggalAktaTerakhirField.setCalendar(kalendar);
        nomorpengesahanPNLNField.setText(tabel.getValueAt(row, 14).toString());
        statusvalidasi.setText(tabel.getValueAt(row, 15).toString());
        //IDlogauditField.setText(tabel.getValueAt(row, 16).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
        data[0] = (!kodebadanhukumField.getText().equals("1      ")?kodebadanhukumField.getText():"1");
        data[1] = (!namabadanhukumField.getText().equals("")?namabadanhukumField.getText():"X");
               
        kalendar = tanggalAwalBerdiriField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data2 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[2] = (data2!=null?data2:"");
        
        data[3] = (!alamatField.getText().equals("")?alamatField.getText():"X");
        
        String datakodekotamentah = kodekota.getText();
        System.out.println("data kode kota mentah: " + datakodekotamentah);
        String [] datakodekota = datakodekotamentah.split(" ");
        System.out.println("data kode kota yg dipilih: " + datakodekota[0]);
        data[4] = datakodekota[0];
        
        String datakodeprovinsimentah = kodeprovinsi.getText();
        System.out.println("data kode provinsi mentah: " + datakodeprovinsimentah);
        String [] datakodeprovinsi = datakodeprovinsimentah.split(" ");
        System.out.println("data kode provinsi yg dipilih: " + datakodeprovinsi[0]);
        data[5] = datakodeprovinsi[0];
        
        String datakodenegaramentah = kodenegara.getText();
        System.out.println("data kode negara mentah: " + datakodenegaramentah);
        String [] datakodenegara = datakodenegaramentah.split(" ");
        System.out.println("data kode negara yg dipilih: " + datakodenegara[0]);
        data[6] = datakodenegara[0];
        
        data[7] = (!kodeposField.getText().equals("     ")?kodeposField.getText():"0");
        
        data[8] = (!telephoneField.getText().equals("")?telephoneField.getText():"X");
        
        data[9] = (!faximiliField.getText().equals("")?faximiliField.getText():"X");
        
        data[10] = (!emailField.getText().equals("")?emailField.getText():"X");
        data[11] = (!websiteField.getText().equals("")?websiteField.getText():"X");
        data[12] = (!nomoraktaterakhirField.getText().equals("")?nomoraktaterakhirField.getText():"X");
        
        kalendar = tanggalAktaTerakhirField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data13 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[13] = (data13!=null?data13:"");
        
        data[14] = (!nomorpengesahanPNLNField.getText().equals("")?nomorpengesahanPNLNField.getText():"X");
        
        String datastatusvalidasimentah = statusvalidasi.getText();
        System.out.println("data status validasi mentah: " + datastatusvalidasimentah);
        String [] datastatusvalidasi = datastatusvalidasimentah.split(" ");
        System.out.println("data status validasi yg dipilih: " + datastatusvalidasi[0]);
        data[15] = datastatusvalidasi[0];
        
        data[16] = "" + idlogaudit;
        data[17] = noSkAwalField.getText();
        
        kalendar = tanggalAwalSKField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data18 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[18] = (data18!=null?data18:"");
        
        data[19] = noSKField.getText();
        
        kalendar = tanggalSKField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data19 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[20] = (data19!=null?data19:"");
        
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];
        data[0] = namabadanhukumField.getText();
        kalendar = tanggalAwalBerdiriField.getCalendar();
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data2 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[1] = data2;                
        data[2] = alamatField.getText();
        
        String datakodekotamentah = kodekota.getText();
        System.out.println("data kode kota mentah: " + datakodekotamentah);
        String [] datakodekota = datakodekotamentah.split(" ");
        System.out.println("data kode kota yg dipilih: " + datakodekota[0]);
        data[3] = datakodekota[0];
        
        String datakodeprovinsimentah = kodeprovinsi.getText();
        System.out.println("data kode provinsi mentah: " + datakodeprovinsimentah);
        String [] datakodeprovinsi = datakodeprovinsimentah.split(" ");
        System.out.println("data kode provinsi yg dipilih: " + datakodeprovinsi[0]);
        data[4] = datakodeprovinsi[0];
        
        String datakodenegaramentah = kodenegara.getText();
        System.out.println("data kode negara mentah: " + datakodenegaramentah);
        String [] datakodenegara = datakodenegaramentah.split(" ");
        System.out.println("data kode negara yg dipilih: " + datakodenegara[0]);
        data[5] = datakodenegara[0];
        
        data[6] = kodeposField.getText();
        data[7] = telephoneField.getText();
        data[8] = faximiliField.getText();
        data[9] = emailField.getText();
        data[10] = websiteField.getText();
        data[11] = nomoraktaterakhirField.getText();
        kalendar = tanggalAktaTerakhirField.getCalendar();
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data13 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[12] = data13;                
        data[13] = nomorpengesahanPNLNField.getText();
        data[14] = statusvalidasi.getText();
        data[15] = "" + idlogaudit;
        data[16] = noSkAwalField.getText();
       kalendar = tanggalAwalSKField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data17 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[17] = (data17!=null?data17:"");
        data[18] = noSKField.getText();
        kalendar = tanggalSKField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data19 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[19] = (data19!=null?data19:"");
        data[20] = kodebadanhukumField.getText();
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[1];
        data[0] = kodebadanhukumField.getText();
        return data;   
    }
    
    private void buttonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInsertActionPerformed
        // TODO add your handling code here:
        initIDLogAudit();
        String query = "insert into pdpt_dev.\"TMST_BADAN_HUKUM\" " +
                       " values(?,?,?,?,?,?,?,?, " +
                       " ?,?,?,?,?,?,?,?,?,?,?,?,?)";
//        int hasil = kon.insertUpdateDeleteData(query, 
//                dapatkanNilaiUntukInsert() );
        int hasil = model.insertAktifitasDOsen(query, 
                dapatkanNilaiUntukInsert() );
        if (hasil == 1) {
           query = "select * from pdpt_dev.\"TMST_BADAN_HUKUM\""; 
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

    private void buttonBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBaruActionPerformed
        // TODO add your handling code here:
        persiapanEntriDataBaru();
    }//GEN-LAST:event_buttonBaruActionPerformed

    private void buttonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUpdateActionPerformed
        // TODO add your handling code here:
        initIDLogAudit();
        String query = "update pdpt_dev.\"TMST_BADAN_HUKUM\" " +
                       " SET \"Nama_badan_hukum\"=?, " + 
                       "\"Tanggal_awal_berdiri\"=?, " + 
                       "\"Alamat\"=?, " + 
                       "\"Kode_kota\" = ?, " +
                       "\"Kode_provinsi\"=?, " + 
                       "\"Kode_negara\"=?, " +
                       "\"kode_pos\"=?, " + 
                       "\"Telephone\"=?, " +
                       "\"Faximili\"=?, " +
                       "\"Email\"=?, " +
                       "\"Website\"=?, " +
                       "\"Nomor_akta_terakhir\"=?, " +
                       "\"Tanggal_akta_terakhir\"=?, " +
                       "\"Nomor_pengesahan_PN_LN\"=?, " +
                       "\"status_validasi\"=?, " + 
                       "\"ID_log_audit\"=?, " +
                       "\"No_SK_awal_berdiri\"=?, " +
                       "\"Tanggal_SK_awal_berdiri\"=?, " +
                       "\"No_Sk_BN\"=?, " +
                       "\"Tanggal_SK_BN\"=?, " +
                       "\"ID_log_audit\"=? " +
                       " WHERE \"Kode_badan_hukum\"=? ";
//        int hasil = kon.insertUpdateDeleteData(query, 
//                dapatkanNilaiUntukUpdate() );
        int hasil = model.updateAktifitasDOsen(query, 
                dapatkanNilaiUntukUpdate() );
        if (hasil == 1) {
           query = "select * from pdpt_dev.\"TMST_BADAN_HUKUM\""; 
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
        String query = "delete from pdpt_dev.\"TMST_BADAN_HUKUM\" WHERE \"Kode_badan_hukum\"=?";
        
        String [] pkDataYgDihapus = dapatkanNilaiUntukDelete();
        
        int hasil = model.delete(query, 
                    dapatkanNilaiUntukDelete() );
        if (hasil == 1) {
           query = "select * from pdpt_dev.\"TMST_BADAN_HUKUM\" "; 
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

    private void tabelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelKeyPressed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_tabelKeyPressed

    private void tabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMouseClicked
        // TODO add your handling code here:
        int row = tabel.getSelectedRow();
        tampilkanData(row);
    }//GEN-LAST:event_tabelMouseClicked

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
    private javax.swing.JFormattedTextField faximiliField;
    private javax.swing.JLabel faximiliLabel;
    private javax.swing.JScrollPane gulungPanelAtribut;
    private javax.swing.JScrollPane gulungTabel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JLabel kodePosLabel;
    private javax.swing.JFormattedTextField kodebadanhukumField;
    private javax.swing.JLabel kodebadanhukumLabel;
    private javax.swing.JComboBox kodekotaField;
    private javax.swing.JLabel kodekotaLabel;
    private javax.swing.JComboBox kodenegaraField;
    private javax.swing.JLabel kodenegaraLabel;
    private javax.swing.JFormattedTextField kodeposField;
    private javax.swing.JComboBox kodeprovinsiField;
    private javax.swing.JLabel kodeprovinsiLabel;
    private javax.swing.JTextField namabadanhukumField;
    private javax.swing.JLabel namabadanhukumLabel;
    private javax.swing.JTextField noSKField;
    private javax.swing.JTextField noSkAwalField;
    private javax.swing.JTextField nomoraktaterakhirField;
    private javax.swing.JLabel nomoraktaterakhirLabel;
    private javax.swing.JTextField nomorpengesahanPNLNField;
    private javax.swing.JLabel nomorpengesahanPNLNLabel;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JLabel statusValidasiLabel;
    private javax.swing.JComboBox statusvalidasiField;
    private javax.swing.JTable tabel;
    private com.toedter.calendar.JDateChooser tanggalAktaTerakhirField;
    private com.toedter.calendar.JDateChooser tanggalAwalBerdiriField;
    private com.toedter.calendar.JDateChooser tanggalAwalSKField;
    private com.toedter.calendar.JDateChooser tanggalSKField;
    private javax.swing.JLabel tanggalaktaterakhirLabel;
    private javax.swing.JLabel tanggalawalberdiriLabel;
    private javax.swing.JFormattedTextField telephoneField;
    private javax.swing.JLabel telephoneLabel;
    private javax.swing.JTextField websiteField;
    private javax.swing.JLabel websiteLabel;
    // End of variables declaration//GEN-END:variables
}
