/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelMasterFakultas;
import java.awt.Component;
import java.util.Calendar;
import javax.swing.JPanel;
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
public class MasterFakultas extends javax.swing.JPanel {
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
    Model.ModelMasterFakultas model;    
    private JTextField kodeprovinsi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo1;
    private JTextField kodekota;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    private JTextField statusvalidasi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    
    private int idlogaudit;
    private String kueri,kodept;

    /**
     * Creates new form PanelHalaman1
     */
    public MasterFakultas() {    
        initComponents();
    }
    
    public MasterFakultas(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        model = new ModelMasterFakultas();
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        kodept = homePage.dapatkanKodePT();
        kodeperguruantinggiField.setText(kodept);
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
    
    private void initAutoComplete() {
        kompletTextFieldCombo1 = new KompletTextFieldCombo(kon, 
                kodeprovinsiField, kodeprovinsi);        
        query = "SELECT \"Kode_provinsi\", \"Nama_provinsi\" " +
                " FROM pdpt_dev.\"TREF_PROVINSI\" " +
                " WHERE \"Kode_negara\"='1'"; 
        kompletTextFieldCombo1.initDataUtkAutoComplete(query);        
        kodeprovinsi = kompletTextFieldCombo1.dapatkanTextField();
        kodeprovinsiField = kompletTextFieldCombo1.dapatkanComboBox();
        
        kompletTextFieldCombo2 = new KompletTextFieldCombo(kon, 
                kodekotaField, kodekota);        
        query = "SELECT \"Kode_kota\", \"Nama_kabupaten\" " +
                " from pdpt_dev.\"TREF_KOTA\""; 
        kompletTextFieldCombo2.initDataUtkAutoComplete(query);        
        kodekota = kompletTextFieldCombo2.dapatkanTextField();
        kodekotaField = kompletTextFieldCombo2.dapatkanComboBox();        
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                statusvalidasiField, statusvalidasi);        
        String [][] datanya11 = {{"1", "Belum diverifikasi"},
            {"2","Sudah diverifikasi namun masih terdapat data yang invalid"},
            {"3", "Sudah diverifikasi dan data sudah valid"}}; 
        kompletTextFieldCombo3.initDataUtkAutoComplete(datanya11);        
        statusvalidasi = kompletTextFieldCombo3.dapatkanTextField();
        statusvalidasiField = kompletTextFieldCombo3.dapatkanComboBox();
    }
    
    private void inisialisasiData() {
        kon = homePage.dapatkanKoneksiDB();
        query = "select * from pdpt_dev.\"TMST_FAKULTAS\" Where \"Kode_perguruan_tinggi\" = '"+kodept+"'";
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public MasterFakultas(String [][] data, String [] kolom) {
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
        kodeperguruantinggiField.setText("" + homePage.dapatkanKodePT());
        kodefakultasField.setText("");
        singkatanField.setText("");
        namafakultasField.setText("");
        alamatField.setText("");
        kodeprovinsi.setText("");
        kodekota.setText("");
        kodeposField.setText("");
        tglmulaiefektifField.setDate(null);
        tglakhirefektifField.setDate(null);
        statusvalidasi.setText("");
        IDlogauditField.setText("");
        
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
        kodefakultasLabel = new javax.swing.JLabel();
        singkatanLabel = new javax.swing.JLabel();
        namafakultasLabel = new javax.swing.JLabel();
        alamatLabel = new javax.swing.JLabel();
        kodeprovinsiLabel = new javax.swing.JLabel();
        kodekotaLabel = new javax.swing.JLabel();
        kodeposLabel = new javax.swing.JLabel();
        tglmulaiefektifLabel = new javax.swing.JLabel();
        tglakhirefektifLabel = new javax.swing.JLabel();
        statusvalidasiLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodeperguruantinggiField = new javax.swing.JFormattedTextField();
        kodefakultasField = new javax.swing.JFormattedTextField();
        singkatanField = new javax.swing.JTextField();
        namafakultasField = new javax.swing.JTextField();
        alamatField = new javax.swing.JTextField();
        kodeprovinsiField = new javax.swing.JComboBox();
        kodekotaField = new javax.swing.JComboBox();
        kodeposField = new javax.swing.JFormattedTextField();
        tglmulaiefektifField = new com.toedter.calendar.JDateChooser();
        tglakhirefektifField = new com.toedter.calendar.JDateChooser();
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

        panelLabel.setLayout(new java.awt.GridLayout(12, 0));

        kodeperguruantinggiLabel.setText("Kode Perguruan Tinggi:");
        panelLabel.add(kodeperguruantinggiLabel);

        kodefakultasLabel.setText("Kode Fakultas:");
        panelLabel.add(kodefakultasLabel);

        singkatanLabel.setText("Singkatan:");
        panelLabel.add(singkatanLabel);

        namafakultasLabel.setText("Nama Fakultas:");
        panelLabel.add(namafakultasLabel);

        alamatLabel.setText("Alamat:");
        panelLabel.add(alamatLabel);

        kodeprovinsiLabel.setText("Kode Provinsi:");
        panelLabel.add(kodeprovinsiLabel);

        kodekotaLabel.setText("Kode Kota:");
        panelLabel.add(kodekotaLabel);

        kodeposLabel.setText("Kodepos:");
        panelLabel.add(kodeposLabel);

        tglmulaiefektifLabel.setText("Tgl Mulai Efektif:");
        panelLabel.add(tglmulaiefektifLabel);

        tglakhirefektifLabel.setText("Tgl Akhir Efektif:");
        panelLabel.add(tglakhirefektifLabel);

        statusvalidasiLabel.setText("Status Validasi:");
        panelLabel.add(statusvalidasiLabel);

        IDlogauditLabel.setText("ID log audit:");
        panelLabel.add(IDlogauditLabel);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(12, 0));

        try {
            kodeperguruantinggiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodeperguruantinggiField);

        try {
            kodefakultasField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodefakultasField);
        panelTextField.add(singkatanField);
        panelTextField.add(namafakultasField);
        panelTextField.add(alamatField);

        panelTextField.add(kodeprovinsiField);

        panelTextField.add(kodekotaField);

        try {
            kodeposField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodeposField);
        panelTextField.add(tglmulaiefektifField);
        panelTextField.add(tglakhirefektifField);

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

    private void tampilkanData(int row) {
        System.out.println("baris yg dipilih utk diUPDATE: "  + row);
        persiapanEntriDataBaru();
        kodeperguruantinggiField.setText(tabel.getValueAt(row, 0).toString());
        kodefakultasField.setText(tabel.getValueAt(row, 1).toString());
        singkatanField.setText(tabel.getValueAt(row, 2).toString());
        namafakultasField.setText(tabel.getValueAt(row, 3).toString());
        alamatField.setText(tabel.getValueAt(row, 4).toString());
        kodeprovinsi.setText(tabel.getValueAt(row, 5).toString());
        kodekota.setText(tabel.getValueAt(row, 6).toString());
        kodeposField.setText(tabel.getValueAt(row, 7).toString());
        
        String strKalendar = tabel.getValueAt(row, 8).toString();
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
        
        strKalendar = tabel.getValueAt(row, 9).toString();
        tahun = Integer.parseInt( strKalendar.substring(0, 4) );
        System.out.println("" + strKalendar.substring(0, 4)  + " tahun: " + tahun);
        intBulan = Integer.parseInt( strKalendar.substring(5, 7) );
        System.out.println("" + strKalendar.substring(5, 7)  + " intBulan: " + intBulan);
        hari = Integer.parseInt( strKalendar.substring(8, 10) );
        System.out.println("" + strKalendar.substring(8, 10)  + " hari: " + hari);
        kalendar.set(tahun, intBulan - 1, hari); 
        tglakhirefektifField.setCalendar(kalendar);
        
        statusvalidasi.setText(tabel.getValueAt(row, 10).toString());
        //IDlogauditField.setText(tabel.getValueAt(row, 11).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
        data[0] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");
        data[1] = (!kodefakultasField.getText().equals("  ")?kodefakultasField.getText():"0");
        data[2] = (!singkatanField.getText().equals("")?singkatanField.getText():"X");
        data[3] = (!namafakultasField.getText().equals("")?namafakultasField.getText():"X");
        data[4] = (!alamatField.getText().equals("")?alamatField.getText():"X");
        
        String datakodeprovinsimentah = kodeprovinsi.getText();
        System.out.println("data kode provinsi mentah: " + datakodeprovinsimentah);
        String [] datakodeprovinsi = datakodeprovinsimentah.split(" ");
        System.out.println("data kode provinsi yg dipilih: " + datakodeprovinsi[0]);
        data[5] = datakodeprovinsi[0];
        
        String datakodekotamentah = kodekota.getText();
        System.out.println("data kode kota mentah: " + datakodekotamentah);
        String [] datakodekota = datakodekotamentah.split(" ");
        System.out.println("data kode kota yg dipilih: " + datakodekota[0]);
        data[6] = datakodekota[0];
        
        data[7] = (!kodeposField.getText().equals("     ")?kodeposField.getText():"0");
        
        kalendar = tglmulaiefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data8 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[8] = (data8!=null?data8:"");
        
        kalendar = tglakhirefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data9 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[9] = (data9!=null?data9:"");
        
        String datastatusvalidasimentah = statusvalidasi.getText();
        System.out.println("data status validasi mentah: " + datastatusvalidasimentah);
        String [] datastatusvalidasi = datastatusvalidasimentah.split(" ");
        System.out.println("data status validasi yg dipilih: " + datastatusvalidasi[0]);
        data[10] = datastatusvalidasi[0];
        
        data[11] = "" + idlogaudit;
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];                
        data[0] = singkatanField.getText();
        data[1] = namafakultasField.getText();
        data[2] = alamatField.getText();
        
        String datakodeprovinsimentah = kodeprovinsi.getText();
        System.out.println("data kode provinsi mentah: " + datakodeprovinsimentah);
        String [] datakodeprovinsi = datakodeprovinsimentah.split(" ");
        System.out.println("data kode provinsi yg dipilih: " + datakodeprovinsi[0]);
        data[3] = datakodeprovinsi[0];
        
        String datakodekotamentah = kodekota.getText();
        System.out.println("data kode kota mentah: " + datakodekotamentah);
        String [] datakodekota = datakodekotamentah.split(" ");
        System.out.println("data kode kota yg dipilih: " + datakodekota[0]);
        data[4] = datakodekota[0];
        
        data[5] = kodeposField.getText();
        
        kalendar = tglmulaiefektifField.getCalendar();
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data7 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[6] = data7;
        
        kalendar = tglakhirefektifField.getCalendar();
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data8 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[7] = data8;
        
        String datastatusvalidasimentah = statusvalidasi.getText();
        System.out.println("data status validasi mentah: " + datastatusvalidasimentah);
        String [] datastatusvalidasi = datastatusvalidasimentah.split(" ");
        System.out.println("data status validasi yg dipilih: " + datastatusvalidasi[0]);
        data[8] = datastatusvalidasi[0];
        
        data[9] = "" + idlogaudit;
        data[10] = kodefakultasField.getText();
        data[11] = kodeperguruantinggiField.getText();        
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[2];
        data[0] = kodefakultasField.getText();
        data[1] = kodeperguruantinggiField.getText();
        return data;   
    }
    
    private void buttonBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBaruActionPerformed
        // TODO add your handling code here:
        persiapanEntriDataBaru();
    }//GEN-LAST:event_buttonBaruActionPerformed

    private void buttonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInsertActionPerformed
        // TODO add your handling code here:
        initIDLogAudit();
        String query = "insert into pdpt_dev.\"TMST_FAKULTAS\" " +
        " values(?,?,?,?,?,?,?,?,?,?,?,?) ";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukInsert() );
        int hasil = model.insertAktifitasDOsen(query,
            dapatkanNilaiUntukInsert() );
        if (hasil == 1) {
            query = "select * from pdpt_dev.\"TMST_FAKULTAS\" ";
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
        String query = "update pdpt_dev.\"TMST_FAKULTAS\" " +
        " SET \"Singkatan\"=?, " +
        "\"Nama_fakultas\"=?, " +
        "\"Alamat\"=?, " +
        "\"Kode_provinsi\"=?, " +
        "\"Kode_kota\"=?, " +
        "\"Kode_pos\"=?, " +
        "\"Tgl_mulai_efektif\"=?, " +
        "\"Tgl_akhir_efektif\" = ?, " +
        "\"Status_validasi\"=?, " +
        "\"ID_log_audit\"=? " +
        " WHERE \"Kode_fakultas\"=? AND " +
        "\"Kode_perguruan_tinggi\"=? ";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukUpdate() );
        int hasil = model.updateAktifitasDOsen(query,
            dapatkanNilaiUntukUpdate() );
        if (hasil == 1) {
            query = "select * from pdpt_dev.\"TMST_FAKULTAS\" ";
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
        String query = "delete from pdpt_dev.\"TMST_FAKULTAS\" WHERE " + 
                " \"Kode_fakultas\"=? AND " + 
                " \"Kode_perguruan_tinggi\"=? "; 
        
        String [] pkDataYgDihapus = dapatkanNilaiUntukDelete();
                
        int hasil = model.delete(query,
            dapatkanNilaiUntukDelete() );
        if (hasil == 1) {
            query = "select * from pdpt_dev.\"TMST_FAKULTAS\" ";
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
    private javax.swing.JScrollPane gulungPanelAtribut;
    private javax.swing.JScrollPane gulungTabel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JFormattedTextField kodefakultasField;
    private javax.swing.JLabel kodefakultasLabel;
    private javax.swing.JComboBox kodekotaField;
    private javax.swing.JLabel kodekotaLabel;
    private javax.swing.JFormattedTextField kodeperguruantinggiField;
    private javax.swing.JLabel kodeperguruantinggiLabel;
    private javax.swing.JFormattedTextField kodeposField;
    private javax.swing.JLabel kodeposLabel;
    private javax.swing.JComboBox kodeprovinsiField;
    private javax.swing.JLabel kodeprovinsiLabel;
    private javax.swing.JTextField namafakultasField;
    private javax.swing.JLabel namafakultasLabel;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JTextField singkatanField;
    private javax.swing.JLabel singkatanLabel;
    private javax.swing.JComboBox statusvalidasiField;
    private javax.swing.JLabel statusvalidasiLabel;
    private javax.swing.JTable tabel;
    private com.toedter.calendar.JDateChooser tglakhirefektifField;
    private javax.swing.JLabel tglakhirefektifLabel;
    private com.toedter.calendar.JDateChooser tglmulaiefektifField;
    private javax.swing.JLabel tglmulaiefektifLabel;
    // End of variables declaration//GEN-END:variables
}
