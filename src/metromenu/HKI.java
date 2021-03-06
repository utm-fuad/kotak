/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelHKI;
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
public class HKI extends javax.swing.JPanel {
    private JPanel panel;
    private koneksi.KoneksiOracleThinClient kon;
    private String judul;
    private HomePage homePage;
    private String namaTabel;
    private String query;
    private int jmlKolom;
    private int barisYgDipilih = 0;
    Model.ModelHKI model;
    private JTextField kategoriHKI;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo1;
    private JTextField nidnnuk;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    
    private int kodehki, idlogaudit;
    private String kueri;

    /**
     * Creates new form PanelHalaman1
     */
    public HKI() {    
        initComponents();
    }
    
    public HKI(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        model = new ModelHKI();
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        
        inisialisasiData();
        initAutoComplete();
         //disable all button
         buttonBaru.setEnabled(false);
        buttonInsert.setEnabled(false);
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
        kodeHKIField.setVisible(false);
        kodeHKILabel.setVisible(false);
        IDlogauditField.setVisible(false);
        IDlogauditLabel.setVisible(false);
        IDlogauditLabel1.setVisible(false);
        uuidField.setVisible(false);
    }
    
    private void initPKAutoIncrement() {
        query = "select \"Kode_HKI\" " +
                " from pdpt_dev.\"TRAN_HKI\" " +
                " order by \"Kode_HKI\" desc";
        kon.selectData(query);     
        String [][] data = kon.dapatkanData();
        kodehki = Integer.parseInt(data[0][0]);
        kodehki++;
        System.out.println("kodehki: " + kodehki);
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
                kategoriHKIField, kategoriHKI);        
        String [][] datanya1 = {{"1","PATEN"},
            {"2","MEREK"},
            {"3","DESAIN INDUSTRI"},
            {"4","HAK CIPTA"}}; 
        kompletTextFieldCombo1.initDataUtkAutoComplete(datanya1);
        kategoriHKI = kompletTextFieldCombo1.dapatkanTextField();
        kategoriHKIField = kompletTextFieldCombo1.dapatkanComboBox();
        
        kompletTextFieldCombo2 = new KompletTextFieldCombo(kon, 
                nidnnukField, nidnnuk);
        query = "select \"NIDN\", \"Nama_dosen\" from pdpt_dev.\"TMST_DOSEN\" ";
        kompletTextFieldCombo2.initDataUtkAutoComplete(query);        
        nidnnuk = kompletTextFieldCombo2.dapatkanTextField();
        nidnnukField = kompletTextFieldCombo2.dapatkanComboBox();
    }
    
    private void inisialisasiData() {
        namaTabel = "pdpt_dev.\"TRAN_HKI\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select \"Tahun_pelaporan\",\"Semester_pelaporan\",\"NIDN/NUK\""
                + "\"Nama_HKI\",\"Nomor_registrasi_HKI\",\"Nomor_HKI\",\"Kategori_HKI\" from " + namaTabel + " order by \"Kode_HKI\" desc";
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public HKI(String [][] data, String [] kolom) {
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
        kodeHKIField.setText("" + kodehki);
        tahunpelaporanField.setText("");
        semesterpelaporanField.setText("");
        nidnnuk.setText("");
        namaHKIField.setText("");
        nomorregistrasiHKIField.setText("");
        nomorHKIField.setText("");
        kategoriHKI.setText("");
        IDlogauditField.setText("");
        uuidField.setText("");
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
        kodeHKIField.setText(tabel.getValueAt(row, 0).toString());
        tahunpelaporanField.setText(tabel.getValueAt(row, 1).toString());        
        semesterpelaporanField.setText(tabel.getValueAt(row, 2).toString());
        nidnnuk.setText(tabel.getValueAt(row, 3).toString());
        namaHKIField.setText(tabel.getValueAt(row, 4).toString());
        nomorregistrasiHKIField.setText(tabel.getValueAt(row, 5).toString());
        nomorHKIField.setText(tabel.getValueAt(row, 6).toString());
        kategoriHKI.setText(tabel.getValueAt(row, 7).toString());
        //IDlogauditField.setText(tabel.getValueAt(row, 8).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
//        data[0] = (!kodeHKIField.getText().equals("")?kodeHKIField.getText():"0");
        data[0] = (!tahunpelaporanField.getText().equals("    ")?tahunpelaporanField.getText():"0000");
        data[1] = (!semesterpelaporanField.getText().equals(" ")?semesterpelaporanField.getText():"0");
        
        String datanidnnukmentah = nidnnuk.getText();
        System.out.println("data nidnnuk mentah: " + datanidnnukmentah);
        String [] datanidnnuk = datanidnnukmentah.split(" ");
        System.out.println("data nidnnuk yg dipilih: " + datanidnnuk[0]);
        data[2] = datanidnnuk[0];
        
        data[3] = (!namaHKIField.getText().equals("")?namaHKIField.getText():"X");
        data[4] = (!nomorregistrasiHKIField.getText().equals("")?nomorregistrasiHKIField.getText():"X");
        data[5] = (!nomorHKIField.getText().equals("")?nomorHKIField.getText():"X");
        
        String datakategoriHKImentah = kategoriHKI.getText();
        System.out.println("data kategoriHKI mentah: " + datakategoriHKImentah);
        String [] datakategoriHKI = datakategoriHKImentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakategoriHKI[0]);
        data[6] = datakategoriHKI[0];
        
        data[7] = "" + idlogaudit;
        data[8] = nidnnuk.getText().charAt(0)+homePage.dapatkanKodePT()+nidnnuk.getText().charAt(1);
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];                
        
        data[0] = namaHKIField.getText();
        data[1] = nomorregistrasiHKIField.getText();
        data[2] = nomorHKIField.getText();
        
        String datakategoriHKImentah = kategoriHKI.getText();
        System.out.println("data kategoriHKI mentah: " + datakategoriHKImentah);
        String [] datakategoriHKI = datakategoriHKImentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakategoriHKI[0]);
        data[3] = datakategoriHKI[0];
        data[4] = "" + idlogaudit;
        String datanidnnukmentah = nidnnuk.getText();
        System.out.println("data nidnnuk mentah: " + datanidnnukmentah);
        String [] datanidnnuk = datanidnnukmentah.split(" ");
        System.out.println("data nidnnuk yg dipilih: " + datanidnnuk[0]);
        data[5] = datanidnnuk[0];
        data[6] = tahunpelaporanField.getText();
        data[7] = nidnnuk.getText().charAt(0)+homePage.dapatkanKodePT()+nidnnuk.getText().charAt(1);
        data[8] = semesterpelaporanField.getText();
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[3];
       String datanidnnukmentah = nidnnuk.getText();
        System.out.println("data nidnnuk mentah: " + datanidnnukmentah);
        String [] datanidnnuk = datanidnnukmentah.split(" ");
        System.out.println("data nidnnuk yg dipilih: " + datanidnnuk[0]);
        data[0] = datanidnnuk[0];
        data[1] = tahunpelaporanField.getText();
        data[2] = nidnnuk.getText().charAt(0)+homePage.dapatkanKodePT()+nidnnuk.getText().charAt(1);
        data[3] = semesterpelaporanField.getText();
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
        kodeHKILabel = new javax.swing.JLabel();
        tahunpelaporanLabel = new javax.swing.JLabel();
        semesterpelaporanLabel = new javax.swing.JLabel();
        nidnnukLabel = new javax.swing.JLabel();
        namaHKILabel = new javax.swing.JLabel();
        nomorregistrasiHKILabel = new javax.swing.JLabel();
        nomorHKILabel = new javax.swing.JLabel();
        kategoriHKILabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        IDlogauditLabel1 = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodeHKIField = new javax.swing.JFormattedTextField();
        tahunpelaporanField = new javax.swing.JFormattedTextField();
        semesterpelaporanField = new javax.swing.JFormattedTextField();
        nidnnukField = new javax.swing.JComboBox();
        namaHKIField = new javax.swing.JTextField();
        nomorregistrasiHKIField = new javax.swing.JTextField();
        nomorHKIField = new javax.swing.JTextField();
        kategoriHKIField = new javax.swing.JComboBox();
        IDlogauditField = new javax.swing.JFormattedTextField();
        uuidField = new javax.swing.JFormattedTextField();
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

        panelLabel.setLayout(new java.awt.GridLayout(10, 0));

        kodeHKILabel.setText("Kode HKI:");
        panelLabel.add(kodeHKILabel);

        tahunpelaporanLabel.setText("Tahun Pelaporan:");
        panelLabel.add(tahunpelaporanLabel);

        semesterpelaporanLabel.setText("Semester Pelaporan:");
        panelLabel.add(semesterpelaporanLabel);

        nidnnukLabel.setText("NIDN / NUK:");
        panelLabel.add(nidnnukLabel);

        namaHKILabel.setText("Nama HKI:");
        panelLabel.add(namaHKILabel);

        nomorregistrasiHKILabel.setText("Nomor Registrasi HKI:");
        panelLabel.add(nomorregistrasiHKILabel);

        nomorHKILabel.setText("Nomor HKI:");
        panelLabel.add(nomorHKILabel);

        kategoriHKILabel.setText("Kategori HKI:");
        panelLabel.add(kategoriHKILabel);

        IDlogauditLabel.setText("ID Log Audit:");
        panelLabel.add(IDlogauditLabel);

        IDlogauditLabel1.setText("UUID:");
        panelLabel.add(IDlogauditLabel1);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(10, 0));

        kodeHKIField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(kodeHKIField);

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

        nidnnukField.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        panelTextField.add(nidnnukField);
        panelTextField.add(namaHKIField);
        panelTextField.add(nomorregistrasiHKIField);
        panelTextField.add(nomorHKIField);

        panelTextField.add(kategoriHKIField);

        IDlogauditField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(IDlogauditField);

        uuidField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        uuidField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uuidFieldActionPerformed(evt);
            }
        });
        panelTextField.add(uuidField);

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
        " values(0,?,?,?,?,?,?,?,?,?)";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukInsert() );
        int hasil = model.insertAktifitasDOsen(query, dapatkanNilaiUntukInsert());
        if (hasil == 1) {
            query = "select \"Tahun_pelaporan\",\"Semester_pelaporan\",\"NIDN/NUK\""
                + "\"Nama_HKI\",\"Nomor_registrasi_HKI\",\"Nomor_HKI\",\"Kategori_HKI\" from " + namaTabel + " order by \"Kode_HKI\" desc";
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
        " SET " +         
        " \"Nama_HKI\"=?, " +
        " \"Nomor_registrasi_HKI\"=?, " +
        " \"Nomor_HKI\"=?, " +
        " \"Kategori_HKI\"=?, " +        
        " \"ID_log_audit\"=? " +
        " WHERE " + 
        " \"NIDN/NUK\"=? AND " +
        " \"Tahun_pelaporan\"=? AND " +
        " \"UUID\"=? AND " +
        " \"Semester_pelaporan\"=? ";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukUpdate() );
        int hasil = model.updateAktifitasDOsen(query,
            dapatkanNilaiUntukUpdate() );
        if (hasil == 1) {
            query = "select \"Tahun_pelaporan\",\"Semester_pelaporan\",\"NIDN/NUK\""
                + "\"Nama_HKI\",\"Nomor_registrasi_HKI\",\"Nomor_HKI\",\"Kategori_HKI\" from " + namaTabel + " order by \"Kode_HKI\" desc";
            kon.selectData(query);
            tampilkanDataKeTabel(kon.dapatkanData(), ubahLabelKeSentenceCase(kon.dapatkanKolom()));
            
            //insert data ke TREF_LOG_AUDIT yg belum mengakomodasi multiple PK
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
        String query = "delete from " + namaTabel +  " WHERE " + 
        " \"NIDN/NUK\"=? AND " +
        " \"Tahun_pelaporan\"=? AND " +
        " \"UUID\"=? AND " +
        " \"Semester_pelaporan\"=? ";
        
        String [] pkDataYgDihapus = dapatkanNilaiUntukDelete();

        int hasil = model.delete(query,
            dapatkanNilaiUntukDelete() );
        if (hasil == 1) {
            query = "select \"Tahun_pelaporan\",\"Semester_pelaporan\",\"NIDN/NUK\""
                + "\"Nama_HKI\",\"Nomor_registrasi_HKI\",\"Nomor_HKI\",\"Kategori_HKI\" from " + namaTabel + " order by \"Kode_HKI\" desc";
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

    private void uuidFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uuidFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_uuidFieldActionPerformed

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
    private javax.swing.JScrollPane gulungPanelAtribut;
    private javax.swing.JScrollPane gulungTabel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JComboBox kategoriHKIField;
    private javax.swing.JLabel kategoriHKILabel;
    private javax.swing.JFormattedTextField kodeHKIField;
    private javax.swing.JLabel kodeHKILabel;
    private javax.swing.JTextField namaHKIField;
    private javax.swing.JLabel namaHKILabel;
    private javax.swing.JComboBox nidnnukField;
    private javax.swing.JLabel nidnnukLabel;
    private javax.swing.JTextField nomorHKIField;
    private javax.swing.JLabel nomorHKILabel;
    private javax.swing.JTextField nomorregistrasiHKIField;
    private javax.swing.JLabel nomorregistrasiHKILabel;
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
    private javax.swing.JFormattedTextField uuidField;
    // End of variables declaration//GEN-END:variables
}
