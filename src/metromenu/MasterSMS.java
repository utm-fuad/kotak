/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelMasterSMS;
import metromini.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MasterSMS extends javax.swing.JPanel {
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
    Model.ModelMasterSMS model;
    private JTextField kodeprogramstudi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo1;
    private JTextField kodejenjangpendidikan;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    
    private int idlogaudit;
    private String kueri;

    /**
     * Creates new form PanelHalaman1
     */
    public MasterSMS() {    
        initComponents();
    }
    
    public MasterSMS(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        model = new ModelMasterSMS();
        
        inisialisasiData();
//        initAutoComplete();
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
//        kompletTextFieldCombo1 = new KompletTextFieldCombo(kon, 
//                kodeprogramstudiField, kodeprogramstudi);        
//        query = "select Kode_program_studi, Nama_program_studi " +
//                " from TMST_PROGRAM_STUDI"; 
//        kompletTextFieldCombo1.initDataUtkAutoComplete(query);        
//        kodeprogramstudi = kompletTextFieldCombo1.dapatkanTextField();
//        kodeprogramstudiField = kompletTextFieldCombo1.dapatkanComboBox();
//        
//        kompletTextFieldCombo2 = new KompletTextFieldCombo(kon, 
//                kodejenjangpendidikanField, kodejenjangpendidikan);        
//        query = "select Kode_jenjang_pendidikan, Deskripsi " +
//                " from TREF_JENJANG_PENDIDIKAN"; 
//        kompletTextFieldCombo2.initDataUtkAutoComplete(query);        
//        kodejenjangpendidikan = kompletTextFieldCombo2.dapatkanTextField();
//        kodejenjangpendidikanField = kompletTextFieldCombo2.dapatkanComboBox();
    }
    
    private void inisialisasiData() {
        namaTabel = "pdpt_dev.\"TMST_SMS\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select * from " + namaTabel;
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public MasterSMS(String [][] data, String [] kolom) {
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
        KodePTField.setText("");
        KodeSMSField.setText("");
        NamaSMSField.setText("");
        KodePTField.setText("" + homePage.dapatkanKodePT());
        kodeprogramstudi.setText("");
        kodejenjangpendidikan.setText("");
        NomorSkField.setText("");
        WebsiteField.setText("");
        namaPimpinanField.setText("");
        AlamatField.setText("");
        
        KodePTField.requestFocus();
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
        KodePTField.setText(tabel.getValueAt(row, 0).toString());
        KodeSMSField.setText(tabel.getValueAt(row, 1).toString());
        NamaSMSField.setText(tabel.getValueAt(row, 2).toString());
        JenisSMSField.setText(tabel.getValueAt(row, 3).toString());
        kodeprogramstudi.setText(tabel.getValueAt(row, 4).toString());
        kodejenjangpendidikan.setText(tabel.getValueAt(row, 5).toString());
        NomorSkField.setText(tabel.getValueAt(row, 6).toString());
        WebsiteField.setText(tabel.getValueAt(row, 7).toString());
        namaPimpinanField.setText(tabel.getValueAt(row, 8).toString());
        AlamatField.setText(tabel.getValueAt(row, 9).toString());
        //IDlogauditField.setText(tabel.getValueAt(row, 12).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
       data[0] = KodePTField.getText();
       data[1] = KodeSMSField.getText();
       data[2] = NamaSMSField.getText();
       data[3] = JenisSMSField.getText();
       data[4] = NomorSkField.getText();
       kalendar = tglSKField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
//        String data7 = hari + " " + bulan + " " + tahun;
        String data5 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        System.out.println("date "+data5);
        data[5] = (data5!=null?data5:"");
        data[6] = namaPimpinanField.getText();
        data[7] = AlamatField.getText();
        data[8] = WebsiteField.getText();
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];                
        data[0] = KodeSMSField.getText();
        data[1] = NamaSMSField.getText();
        data[2] = JenisSMSField.getText();
        data[3] = NomorSkField.getText();
        kalendar = tglSKField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
//        String data7 = hari + " " + bulan + " " + tahun;
        String data4 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        System.out.println("date "+data4);
        data[4] = (data4!=null?data4:"");
        data[5] = namaPimpinanField.getText();
        data[6] = AlamatField.getText();
        data[7] = WebsiteField.getText();
        data[8] = KodePTField.getText();
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[1];
        data[0] = KodePTField.getText();
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
        kodepustakaptLabel = new javax.swing.JLabel();
        tahunpelaporanLabel = new javax.swing.JLabel();
        semesterpelapporanLabel = new javax.swing.JLabel();
        kodeperguruantinggiLabel = new javax.swing.JLabel();
        kodeprogramstudiLabel = new javax.swing.JLabel();
        kodejenjangpendidikanLabel = new javax.swing.JLabel();
        jmljudulpustakaLabel = new javax.swing.JLabel();
        jmljudulpustakadigunakanLabel = new javax.swing.JLabel();
        jenispustakaLabel = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        KodePTField = new javax.swing.JFormattedTextField();
        KodeSMSField = new javax.swing.JFormattedTextField();
        NamaSMSField = new javax.swing.JFormattedTextField();
        JenisSMSField = new javax.swing.JFormattedTextField();
        NomorSkField = new javax.swing.JFormattedTextField();
        tglSKField = new com.toedter.calendar.JDateChooser();
        namaPimpinanField = new javax.swing.JTextField();
        AlamatField = new javax.swing.JFormattedTextField();
        WebsiteField = new javax.swing.JTextField();
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

        panelLabel.setLayout(new java.awt.GridLayout(13, 0));

        kodepustakaptLabel.setText("Kode Perguruan Tinggi");
        panelLabel.add(kodepustakaptLabel);

        tahunpelaporanLabel.setText("Kode SMS :");
        panelLabel.add(tahunpelaporanLabel);

        semesterpelapporanLabel.setText("Nama SMS:");
        panelLabel.add(semesterpelapporanLabel);

        kodeperguruantinggiLabel.setText("Jenis SMS:");
        panelLabel.add(kodeperguruantinggiLabel);

        kodeprogramstudiLabel.setText("Nomor SK:");
        panelLabel.add(kodeprogramstudiLabel);

        kodejenjangpendidikanLabel.setText("Tanggal SK:");
        panelLabel.add(kodejenjangpendidikanLabel);

        jmljudulpustakaLabel.setText("Nama Pimpinan:");
        panelLabel.add(jmljudulpustakaLabel);

        jmljudulpustakadigunakanLabel.setText("Alamat:");
        panelLabel.add(jmljudulpustakadigunakanLabel);

        jenispustakaLabel.setText("Website:");
        panelLabel.add(jenispustakaLabel);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(13, 0));

        KodePTField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##########"))));
        panelTextField.add(KodePTField);

        try {
            KodeSMSField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(KodeSMSField);

        try {
            NamaSMSField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(NamaSMSField);

        try {
            JenisSMSField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(JenisSMSField);

        NomorSkField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#######"))));
        panelTextField.add(NomorSkField);
        panelTextField.add(tglSKField);
        panelTextField.add(namaPimpinanField);

        try {
            AlamatField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(AlamatField);
        panelTextField.add(WebsiteField);

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
        " values(?,?,?,?,?,?,?,?,?)";
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
        " SET \"Kode_SMS\"=?, " +
        "\"Nama_SMS\"=?, " +
        "\"Jenis_SMS\"=?, " +
        "\"Nomor_SK\"=?, " +
        "\"Tanggal_SK\"=?, " +
        "\"Nama_Pimpinan\"=?, " +
        "\"Alamat\"=?, " +
        "\"Website\"=? " +
        " WHERE \"Kode_Perguruan_Tinggi\"=? ";
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
                                kon.dapatkanQueryUtkStatement(namaTabel, 
                                    dapatkanNilaiUntukUpdate())) );
        }
    }//GEN-LAST:event_buttonUpdateActionPerformed

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        // TODO add your handling code here:
        initIDLogAudit();
        String query = "delete from " + namaTabel + " WHERE " +
        " \"Kode_Perguruan_Tinggi\"=? ";
        
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
    private javax.swing.JFormattedTextField AlamatField;
    private javax.swing.JFormattedTextField JenisSMSField;
    private javax.swing.JFormattedTextField KodePTField;
    private javax.swing.JFormattedTextField KodeSMSField;
    private javax.swing.JFormattedTextField NamaSMSField;
    private javax.swing.JFormattedTextField NomorSkField;
    private javax.swing.JTextField WebsiteField;
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
    private javax.swing.JLabel jenispustakaLabel;
    private javax.swing.JLabel jmljudulpustakaLabel;
    private javax.swing.JLabel jmljudulpustakadigunakanLabel;
    private javax.swing.JLabel kodejenjangpendidikanLabel;
    private javax.swing.JLabel kodeperguruantinggiLabel;
    private javax.swing.JLabel kodeprogramstudiLabel;
    private javax.swing.JLabel kodepustakaptLabel;
    private javax.swing.JTextField namaPimpinanField;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JLabel semesterpelapporanLabel;
    private javax.swing.JTable tabel;
    private javax.swing.JLabel tahunpelaporanLabel;
    private com.toedter.calendar.JDateChooser tglSKField;
    // End of variables declaration//GEN-END:variables
}
