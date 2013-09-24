/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelMasterMatakuliah;
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
public class MasterMatakuliah extends javax.swing.JPanel {
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
    Model.ModelMasterMatakuliah model;
    private JTextField kodeprogramstudi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo1;
    private JTextField kodejenjangpendidikan;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;    
    private JTextField jenismatakuliah;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    private JTextField semester;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo4;
    private JTextField kelompokmatakuliah;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo5;
    private JTextField sap;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo6;
    private JTextField silabus;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo7;
    private JTextField bahanajar;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo8;
    private JTextField acarapraktek;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo9;
    private JTextField diktat;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo10;
    
    private int idlogaudit;
    private String kueri,kodept;

    /**
     * Creates new form PanelHalaman1
     */
    public MasterMatakuliah() {    
        initComponents();
    }
    
    public MasterMatakuliah(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        model = new ModelMasterMatakuliah();
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        kodept = homePage.dapatkanKodePT();
        kodeperguruantinggiField.setText(kodept);
        inisialisasiData();
        initAutoComplete();
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
                kodeprogramstudiField, kodeprogramstudi);        
        query = "select \"Kode_program_studi\", \"Nama_program_studi\" " +
                " from pdpt_dev.\"TMST_PROGRAM_STUDI\" where \"Kode_perguruan_tinggi\"='"+kodept+"'"; 
        kompletTextFieldCombo1.initDataUtkAutoComplete(query);        
        kodeprogramstudi = kompletTextFieldCombo1.dapatkanTextField();
        kodeprogramstudiField = kompletTextFieldCombo1.dapatkanComboBox();        
        
        kompletTextFieldCombo2 = new KompletTextFieldCombo(kon, 
                kodejenjangpendidikanField, kodejenjangpendidikan);        
        query = "select \"Kode_jenjang_pendidikan\", \"Deskripsi\" " +
                " from pdpt_dev.\"TREF_JENJANG_PENDIDIKAN\""; 
        kompletTextFieldCombo2.initDataUtkAutoComplete(query);        
        kodejenjangpendidikan = kompletTextFieldCombo2.dapatkanTextField();
        kodejenjangpendidikanField = kompletTextFieldCombo2.dapatkanComboBox();
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                jenismatakuliahField, jenismatakuliah);        
        String [][] datanya3 = {{"A", "Wajib"},{"B","Pilihan"},
            {"C","Wajib Peminatan"},{"D","Pilihan Peminatan"},
            {"S","TA/Skripsi/Tesis/Disertasi"}}; 
        kompletTextFieldCombo3.initDataUtkAutoComplete(datanya3);          
        jenismatakuliah = kompletTextFieldCombo3.dapatkanTextField();
        jenismatakuliahField = kompletTextFieldCombo3.dapatkanComboBox();                
        
        kompletTextFieldCombo4 = new KompletTextFieldCombo(kon, 
                semesterField, semester);        
        String [][] datanya4 = {{"1", "Genap"},{"2","Ganjil"}}; 
        kompletTextFieldCombo4.initDataUtkAutoComplete(datanya4);        
        semester = kompletTextFieldCombo4.dapatkanTextField();
        semesterField = kompletTextFieldCombo4.dapatkanComboBox();
        
        kompletTextFieldCombo5 = new KompletTextFieldCombo(kon, 
                kelompokmatakuliahField, kelompokmatakuliah);        
        String [][] datanya5 = {{"A", "MPK-Pengembangan Kepribadian"},
            {"B","MKK-Keilmuan dan Ketrampilan"},
            {"C","MKB-Keahlian Berkarya"},{"D","MPB-Perilaku Berkarya"},
            {"E","MBB-Berkehidupan bermasyarakat"},
            {"F", "MKU/MKDU"}, {"G", "MKDK"}, {"H", "MKK"}}; 
        kompletTextFieldCombo5.initDataUtkAutoComplete(datanya5);
        kelompokmatakuliah = kompletTextFieldCombo5.dapatkanTextField();
        kelompokmatakuliahField = kompletTextFieldCombo5.dapatkanComboBox();
        
        kompletTextFieldCombo6 = new KompletTextFieldCombo(kon, 
                sapField, sap);        
        String [][] datanya6 = {{"1", "Mempunyai SAP"},
            {"2","Tidak mempunyai SAP"}}; 
        kompletTextFieldCombo6.initDataUtkAutoComplete(datanya6);  
        sap = kompletTextFieldCombo6.dapatkanTextField();
        sapField = kompletTextFieldCombo6.dapatkanComboBox();        
        
        kompletTextFieldCombo7 = new KompletTextFieldCombo(kon, 
                silabusField, silabus);        
        String [][] datanya7 = {{"1", "Mempunyai Silabus"},
            {"2","Tidak mempunyai silabus"}}; 
        kompletTextFieldCombo7.initDataUtkAutoComplete(datanya7);  
        silabus = kompletTextFieldCombo7.dapatkanTextField();
        silabusField = kompletTextFieldCombo7.dapatkanComboBox();
        
        kompletTextFieldCombo8 = new KompletTextFieldCombo(kon, 
                bahanajarField, bahanajar);        
        String [][] datanya8 = {{"1", "Mempunyai bahan ajar"},
            {"2","Tidak mempunyai bahan ajar"}}; 
        kompletTextFieldCombo8.initDataUtkAutoComplete(datanya8);  
        bahanajar = kompletTextFieldCombo8.dapatkanTextField();
        bahanajarField = kompletTextFieldCombo8.dapatkanComboBox();
        
        kompletTextFieldCombo9 = new KompletTextFieldCombo(kon, 
                acarapraktekField, acarapraktek);        
        String [][] datanya9 = {{"1", "Mempunyai Acara Praktek"},
            {"2","Tidak mempunyai Acara Praktek"}}; 
        kompletTextFieldCombo9.initDataUtkAutoComplete(datanya9);  
        acarapraktek = kompletTextFieldCombo9.dapatkanTextField();
        acarapraktekField = kompletTextFieldCombo9.dapatkanComboBox();
        
        kompletTextFieldCombo10 = new KompletTextFieldCombo(kon, 
                diktatField, diktat);        
        String [][] datanya10 = {{"1", "Mempunyai Diktat"},
            {"2","Tidak mempunyai Diktat"}}; 
        kompletTextFieldCombo10.initDataUtkAutoComplete(datanya10);        
        diktat = kompletTextFieldCombo10.dapatkanTextField();
        diktatField = kompletTextFieldCombo10.dapatkanComboBox();
    }
    
    private void inisialisasiData() {
        namaTabel = "pdpt_dev.\"TMST_MATA_KULIAH\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select * from " + namaTabel;
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public MasterMatakuliah(String [][] data, String [] kolom) {
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
        kodematakuliahField.setText("");
        tahunpelaporanField.setText("");
        semesterpelapporanField.setText("");
        kodeperguruantinggiField.setText("" + homePage.dapatkanKodePT());
        kodeprogramstudi.setText("");
        kodejenjangpendidikan.setText("");
        namamatakuliahField.setText("");
        jenismatakuliah.setText("");
        semester.setText("");
        kelompokmatakuliah.setText("");
        SKSmatakuliahField.setText("");
        SKStatapmukaField.setText("");
        SKSpraktikumField.setText("");
        SKSprakteklapanganField.setText("");
        SKSsimulasiField.setText("");
        metodepelaksanaankuliahField.setText("");
        statusmatakuliahField.setText("");
        kodekurikulumField.setText("");
        kodekelasField.setText("");
        sap.setText("");
        silabus.setText("");
        bahanajar.setText("");
        acarapraktek.setText("");
        diktat.setText("");
        nidnField.setText("");
        TGLmulaiefektifField.setDate(null);
        tglakhirefektifField.setDate(null);
        IDlogauditField.setText("");
        uuidField.setText("");
        kodematakuliahField.requestFocus();
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
        kodematakuliahField.setText(tabel.getValueAt(row, 0).toString());
        tahunpelaporanField.setText(tabel.getValueAt(row, 1).toString());
        semesterpelapporanField.setText(tabel.getValueAt(row, 2).toString());
        kodeperguruantinggiField.setText(tabel.getValueAt(row, 3).toString());
        kodeprogramstudi.setText(tabel.getValueAt(row, 4).toString());
        kodejenjangpendidikan.setText(tabel.getValueAt(row, 5).toString());
        namamatakuliahField.setText(tabel.getValueAt(row, 6).toString());
        jenismatakuliah.setText(tabel.getValueAt(row, 7).toString());
        semester.setText(tabel.getValueAt(row, 8).toString());
        kelompokmatakuliah.setText(tabel.getValueAt(row, 9).toString());
        SKSmatakuliahField.setText(tabel.getValueAt(row, 10).toString());
        SKStatapmukaField.setText(tabel.getValueAt(row, 11).toString());
        SKSpraktikumField.setText(tabel.getValueAt(row, 12).toString());
        SKSprakteklapanganField.setText(tabel.getValueAt(row, 13).toString());
        SKSsimulasiField.setText(tabel.getValueAt(row, 14).toString());
        metodepelaksanaankuliahField.setText(tabel.getValueAt(row, 15).toString());
        statusmatakuliahField.setText(tabel.getValueAt(row, 16).toString());
        kodekurikulumField.setText(tabel.getValueAt(row, 17).toString());
        kodekelasField.setText(tabel.getValueAt(row, 18).toString());
        sap.setText(tabel.getValueAt(row, 19).toString());
        silabus.setText(tabel.getValueAt(row, 20).toString());
        bahanajar.setText(tabel.getValueAt(row, 21).toString());
        acarapraktek.setText(tabel.getValueAt(row, 22).toString());
        diktat.setText(tabel.getValueAt(row, 23).toString());
        nidnField.setText(tabel.getValueAt(row, 24).toString());
        
        String strKalendar = tabel.getValueAt(row, 25).toString();
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
        TGLmulaiefektifField.setCalendar(kalendar);        
        
        strKalendar = tabel.getValueAt(row, 26).toString();
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
        
        //IDlogauditField.setText(tabel.getValueAt(row, 27).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
        data[0] = kodematakuliahField.getText();
        data[1] = (!tahunpelaporanField.getText().equals("    ")?tahunpelaporanField.getText():"0");
        data[2] = (!semesterpelapporanField.getText().equals(" ")?semesterpelapporanField.getText():"0");
        data[3] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");        
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[4] = datakodeprogramstudi[0];
        
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[5] = datakodejenjangpendidikan[0];
        
        data[6] = namamatakuliahField.getText();
        
        String datajenismatakuliahmentah = jenismatakuliah.getText();
        System.out.println("data jenismatakuliah mentah: " + datajenismatakuliahmentah);
        String [] datajenismatakuliah = datajenismatakuliahmentah.split(" ");
        System.out.println("data jenismatakuliah yg dipilih: " + datajenismatakuliah[0]);
        data[7] = datajenismatakuliah[0];
        
        String datasemestermentah = semester.getText();
        System.out.println("data semester mentah: " + datasemestermentah);
        String [] datasemester = datasemestermentah.split(" ");
        System.out.println("data jenismatakuliah yg dipilih: " + datasemester[0]);
        data[8] = datasemester[0];
        
        String datakelompokmatakuliahmentah = kelompokmatakuliah.getText();
        System.out.println("data kelompokmatakuliah mentah: " + datakelompokmatakuliahmentah);
        String [] datakelompokmatakuliah = datakelompokmatakuliahmentah.split(" ");
        System.out.println("data kelompokmatakuliah yg dipilih: " + datakelompokmatakuliah[0]);
        data[9] = datakelompokmatakuliah[0];
        
        data[10] = (!SKSmatakuliahField.getText().equals(" ")?SKSmatakuliahField.getText():"0");
        data[11] = (!SKStatapmukaField.getText().equals(" ")?SKStatapmukaField.getText():"0");
        data[12] = (!SKSpraktikumField.getText().equals(" ")?SKSpraktikumField.getText():"0");
        data[13] = (!SKSprakteklapanganField.getText().equals(" ")?SKSprakteklapanganField.getText():"0");
        data[14] = (!SKSsimulasiField.getText().equals(" ")?SKSsimulasiField.getText():"0");
        data[15] = metodepelaksanaankuliahField.getText();
        data[16] = statusmatakuliahField.getText();        
        data[17] = kodekurikulumField.getText();
        data[18] = kodekelasField.getText();
        
        String datasapmentah = sap.getText();
        System.out.println("data sap mentah: " + datasapmentah);
        String [] datasap = datasapmentah.split(" ");
        System.out.println("data sap yg dipilih: " + datasap[0]);
        data[19] = datasap[0];
        
        String datasilabusmentah = silabus.getText();
        System.out.println("data silabus mentah: " + datasilabusmentah);
        String [] datasilabus = datasilabusmentah.split(" ");
        System.out.println("data silabus yg dipilih: " + datasilabus[0]);
        data[20] = datasilabus[0];
        
        String databahanajarmentah = bahanajar.getText();
        System.out.println("data bahanajar mentah: " + databahanajarmentah);
        String [] databahanajar = databahanajarmentah.split(" ");
        System.out.println("data bahanajar yg dipilih: " + databahanajar[0]);
        data[21] = databahanajar[0];
        
        String dataacarapraktekmentah = acarapraktek.getText();
        System.out.println("data acarapraktek mentah: " + dataacarapraktekmentah);
        String [] dataacarapraktek = dataacarapraktekmentah.split(" ");
        System.out.println("data acarapraktek yg dipilih: " + dataacarapraktek[0]);
        data[22] = dataacarapraktek[0];
        
        String datadiktatmentah = diktat.getText();
        System.out.println("data diktat mentah: " + datadiktatmentah);
        String [] datadiktat = datadiktatmentah.split(" ");
        System.out.println("data diktat yg dipilih: " + datadiktat[0]);
        data[23] = datadiktat[0];
        
        data[24] = (!nidnField.getText().equals("          ")?nidnField.getText():"0");
        
        kalendar = TGLmulaiefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data8 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[25] = (data8!=null?data8:"");
        
        kalendar = tglakhirefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data26 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[26] = (data26!=null?data26:"");
        
        data[27] = "" + idlogaudit;
        data[28] = uuidField.getText();
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];                
        
        data[0] = (!tahunpelaporanField.getText().equals("    ")?tahunpelaporanField.getText():"0");
        data[1] = (!semesterpelapporanField.getText().equals(" ")?semesterpelapporanField.getText():"0");
        data[2] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");        
        
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
        
        data[5] = namamatakuliahField.getText();
        
        String datajenismatakuliahmentah = jenismatakuliah.getText();
        System.out.println("data jenismatakuliah mentah: " + datajenismatakuliahmentah);
        String [] datajenismatakuliah = datajenismatakuliahmentah.split(" ");
        System.out.println("data jenismatakuliah yg dipilih: " + datajenismatakuliah[0]);
        data[6] = datajenismatakuliah[0];
        
        String datasemestermentah = semester.getText();
        System.out.println("data semester mentah: " + datasemestermentah);
        String [] datasemester = datasemestermentah.split(" ");
        System.out.println("data jenismatakuliah yg dipilih: " + datasemester[0]);
        data[7] = datasemester[0];
        
        String datakelompokmatakuliahmentah = kelompokmatakuliah.getText();
        System.out.println("data kelompokmatakuliah mentah: " + datakelompokmatakuliahmentah);
        String [] datakelompokmatakuliah = datakelompokmatakuliahmentah.split(" ");
        System.out.println("data kelompokmatakuliah yg dipilih: " + datakelompokmatakuliah[0]);
        data[8] = datakelompokmatakuliah[0];
        
        data[9] = (!SKSmatakuliahField.getText().equals(" ")?SKSmatakuliahField.getText():"0");
        data[10] = (!SKStatapmukaField.getText().equals(" ")?SKStatapmukaField.getText():"0");
        data[11] = (!SKSpraktikumField.getText().equals(" ")?SKSpraktikumField.getText():"0");
        data[12] = (!SKSprakteklapanganField.getText().equals(" ")?SKSprakteklapanganField.getText():"0");
        data[13] = (!SKSsimulasiField.getText().equals(" ")?SKSsimulasiField.getText():"0");
        
        data[14] = metodepelaksanaankuliahField.getText();
        data[15] = statusmatakuliahField.getText();
        data[16] = kodekurikulumField.getText();
        data[17] = kodekelasField.getText();
        
        String datasapmentah = sap.getText();
        System.out.println("data sap mentah: " + datasapmentah);
        String [] datasap = datasapmentah.split(" ");
        System.out.println("data sap yg dipilih: " + datasap[0]);
        data[18] = datasap[0];
        
        String datasilabusmentah = silabus.getText();
        System.out.println("data silabus mentah: " + datasilabusmentah);
        String [] datasilabus = datasilabusmentah.split(" ");
        System.out.println("data silabus yg dipilih: " + datasilabus[0]);
        data[19] = datasilabus[0];
        
        String databahanajarmentah = bahanajar.getText();
        System.out.println("data bahanajar mentah: " + databahanajarmentah);
        String [] databahanajar = databahanajarmentah.split(" ");
        System.out.println("data bahanajar yg dipilih: " + databahanajar[0]);
        data[20] = databahanajar[0];
        
        String dataacarapraktekmentah = acarapraktek.getText();
        System.out.println("data acarapraktek mentah: " + dataacarapraktekmentah);
        String [] dataacarapraktek = dataacarapraktekmentah.split(" ");
        System.out.println("data acarapraktek yg dipilih: " + dataacarapraktek[0]);
        data[21] = dataacarapraktek[0];
        
        String datadiktatmentah = diktat.getText();
        System.out.println("data diktat mentah: " + datadiktatmentah);
        String [] datadiktat = datadiktatmentah.split(" ");
        System.out.println("data diktat yg dipilih: " + datadiktat[0]);
        data[22] = datadiktat[0];
        
        data[23] = (!nidnField.getText().equals("          ")?nidnField.getText():"0");
        
        kalendar = TGLmulaiefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data13 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[24] = (data13!=null?data13:"");
        
        kalendar = tglakhirefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data25 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[25] = (data25!=null?data25:"");
        
        data[26] = "" + idlogaudit;
        data[27] = "" + uuidField.getText();
        data[28] = kodematakuliahField.getText();
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[1];
        data[0] = kodematakuliahField.getText();
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
        kodematakuliahLabel = new javax.swing.JLabel();
        tahunpelaporanLabel = new javax.swing.JLabel();
        semesterpelaporanLabel = new javax.swing.JLabel();
        kodeperguruantinggiLabel = new javax.swing.JLabel();
        kodeprogramstudiLabel = new javax.swing.JLabel();
        kodejenjangpendidikanLabel = new javax.swing.JLabel();
        namamatakuliahLabel = new javax.swing.JLabel();
        jenismatakuliahLabel = new javax.swing.JLabel();
        semesterLabel = new javax.swing.JLabel();
        kelompokmatakuliahLabel = new javax.swing.JLabel();
        SKSmatakuliahLabel = new javax.swing.JLabel();
        SKStatapmukaLabel = new javax.swing.JLabel();
        SKSpraktikumLabel = new javax.swing.JLabel();
        SKSprakteklapanganLabel = new javax.swing.JLabel();
        SKSsimulasiLabel = new javax.swing.JLabel();
        metodepelaksanaankuliahLabel = new javax.swing.JLabel();
        statusmatakuliahLabel = new javax.swing.JLabel();
        kodekurikulumLabel = new javax.swing.JLabel();
        kodekelasLabel = new javax.swing.JLabel();
        sapLabel = new javax.swing.JLabel();
        silabusLabel = new javax.swing.JLabel();
        bahanajarLabel = new javax.swing.JLabel();
        acarapraktekLabel = new javax.swing.JLabel();
        diktatLabel = new javax.swing.JLabel();
        nidnLabel = new javax.swing.JLabel();
        TGLmulaiefektifLabel = new javax.swing.JLabel();
        tglakhirefektifLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        IDlogauditLabel1 = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodematakuliahField = new javax.swing.JTextField();
        tahunpelaporanField = new javax.swing.JFormattedTextField();
        semesterpelapporanField = new javax.swing.JFormattedTextField();
        kodeperguruantinggiField = new javax.swing.JFormattedTextField();
        kodeprogramstudiField = new javax.swing.JComboBox();
        kodejenjangpendidikanField = new javax.swing.JComboBox();
        namamatakuliahField = new javax.swing.JTextField();
        jenismatakuliahField = new javax.swing.JComboBox();
        semesterField = new javax.swing.JComboBox();
        kelompokmatakuliahField = new javax.swing.JComboBox();
        SKSmatakuliahField = new javax.swing.JFormattedTextField();
        SKStatapmukaField = new javax.swing.JFormattedTextField();
        SKSpraktikumField = new javax.swing.JFormattedTextField();
        SKSprakteklapanganField = new javax.swing.JFormattedTextField();
        SKSsimulasiField = new javax.swing.JFormattedTextField();
        metodepelaksanaankuliahField = new javax.swing.JTextField();
        statusmatakuliahField = new javax.swing.JTextField();
        kodekurikulumField = new javax.swing.JTextField();
        kodekelasField = new javax.swing.JTextField();
        sapField = new javax.swing.JComboBox();
        silabusField = new javax.swing.JComboBox();
        bahanajarField = new javax.swing.JComboBox();
        acarapraktekField = new javax.swing.JComboBox();
        diktatField = new javax.swing.JComboBox();
        nidnField = new javax.swing.JFormattedTextField();
        TGLmulaiefektifField = new com.toedter.calendar.JDateChooser();
        tglakhirefektifField = new com.toedter.calendar.JDateChooser();
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

        panelLabel.setLayout(new java.awt.GridLayout(29, 0));

        kodematakuliahLabel.setText("Kode Mata Kuliah:");
        panelLabel.add(kodematakuliahLabel);

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

        namamatakuliahLabel.setText("Nama Mata Kuliah:");
        panelLabel.add(namamatakuliahLabel);

        jenismatakuliahLabel.setText("Jenis Mata Kuliah:");
        panelLabel.add(jenismatakuliahLabel);

        semesterLabel.setText("Semester:");
        panelLabel.add(semesterLabel);

        kelompokmatakuliahLabel.setText("Kelompok Mata Kuliah:");
        panelLabel.add(kelompokmatakuliahLabel);

        SKSmatakuliahLabel.setText("SKS Mata Kuliah:");
        panelLabel.add(SKSmatakuliahLabel);

        SKStatapmukaLabel.setText("SKS Tatap Muka:");
        panelLabel.add(SKStatapmukaLabel);

        SKSpraktikumLabel.setText("SKS Praktikum:");
        panelLabel.add(SKSpraktikumLabel);

        SKSprakteklapanganLabel.setText("SKS Praktek Lapangan:");
        panelLabel.add(SKSprakteklapanganLabel);

        SKSsimulasiLabel.setText("SKS Simulasi:");
        panelLabel.add(SKSsimulasiLabel);

        metodepelaksanaankuliahLabel.setText("Metode Pelaksanaan Kuliah:");
        panelLabel.add(metodepelaksanaankuliahLabel);

        statusmatakuliahLabel.setText("Status Mata Kuliah:");
        panelLabel.add(statusmatakuliahLabel);

        kodekurikulumLabel.setText("Kode Kurikulum:");
        panelLabel.add(kodekurikulumLabel);

        kodekelasLabel.setText("Kode Kelas:");
        panelLabel.add(kodekelasLabel);

        sapLabel.setText("Sap:");
        panelLabel.add(sapLabel);

        silabusLabel.setText("Silabus:");
        panelLabel.add(silabusLabel);

        bahanajarLabel.setText("Bahan Ajar:");
        panelLabel.add(bahanajarLabel);

        acarapraktekLabel.setText("Acara Praktek:");
        panelLabel.add(acarapraktekLabel);

        diktatLabel.setText("Diktat:");
        panelLabel.add(diktatLabel);

        nidnLabel.setText("Nidn:");
        panelLabel.add(nidnLabel);

        TGLmulaiefektifLabel.setText("TGL Mulai Efektif:");
        panelLabel.add(TGLmulaiefektifLabel);

        tglakhirefektifLabel.setText("Tgl Akhir Efektif:");
        panelLabel.add(tglakhirefektifLabel);

        IDlogauditLabel.setText("ID Log Audit:");
        panelLabel.add(IDlogauditLabel);

        IDlogauditLabel1.setText("UUID :");
        panelLabel.add(IDlogauditLabel1);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(29, 0));
        panelTextField.add(kodematakuliahField);

        try {
            tahunpelaporanField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(tahunpelaporanField);

        try {
            semesterpelapporanField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(semesterpelapporanField);

        try {
            kodeperguruantinggiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodeperguruantinggiField);

        panelTextField.add(kodeprogramstudiField);

        panelTextField.add(kodejenjangpendidikanField);
        panelTextField.add(namamatakuliahField);

        panelTextField.add(jenismatakuliahField);

        panelTextField.add(semesterField);

        panelTextField.add(kelompokmatakuliahField);

        try {
            SKSmatakuliahField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(SKSmatakuliahField);

        try {
            SKStatapmukaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(SKStatapmukaField);

        try {
            SKSpraktikumField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(SKSpraktikumField);

        try {
            SKSprakteklapanganField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(SKSprakteklapanganField);

        try {
            SKSsimulasiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(SKSsimulasiField);
        panelTextField.add(metodepelaksanaankuliahField);
        panelTextField.add(statusmatakuliahField);

        kodekurikulumField.setToolTipText("1 Digit Karakter");
        panelTextField.add(kodekurikulumField);

        kodekelasField.setToolTipText("2 digit karakter");
        panelTextField.add(kodekelasField);

        panelTextField.add(sapField);

        panelTextField.add(silabusField);

        panelTextField.add(bahanajarField);

        panelTextField.add(acarapraktekField);

        panelTextField.add(diktatField);

        try {
            nidnField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        nidnField.setToolTipText("isi dengan 10 digit NIDN");
        panelTextField.add(nidnField);
        panelTextField.add(TGLmulaiefektifField);
        panelTextField.add(tglakhirefektifField);

        IDlogauditField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        panelTextField.add(IDlogauditField);

        uuidField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
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
        " values(?,?,?,?,?,?,?,?,?,?, " +
        "?,?,?,?,?,?,?,?,?,?, " +
        "?,?,?,?,?,?,?,?,?)";
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
        " SET \"Tahun_pelaporan\"=?, " +
        "\"Semester_pelaporan\"=?, " +
        "\"Kode_perguruan_tinggi\"=?, " +
        "\"Kode_program_studi\"=?, " +
        "\"Kode_jenjang_pendidikan\"=?, " +
        "\"Nama_mata_kuliah\"=?, " +
        "\"Jenis_mata_kuliah\"=?, " +
        "\"Semester\"=?, " +
        "\"Kelompok_mata_kuliah\"=?, " +
        "\"SKS_mata_kuliah\"=?, " +
        "\"SKS_tatap_muka\"=?, " +
        "\"SKS_praktikum\"=?, " +
        "\"SKS_praktek_lapangan\"=?, " +
        "\"SKS_simulasi\"=?, " +
        "\"Metode_pelaksanaan_kuliah\"=?, " +
        "\"Status_mata_kuliah\"=?, " +
        "\"Kode_kurikulum\"=?, " +
        "\"Kode_kelas\"=?, " +
        "\"SAP\"=?, " +
        "\"Silabus\"=?, " +
        "\"Bahan_ajar\"=?, " +
        "\"Acara_praktek\"=?, " +
        "\"Diktat\"=?, " +        
        "\"NIDN\"=?, " +        
        "\"TGL_mulai_efektif\"=?, " +        
        "\"Tgl_akhir_efektif\"=?, " +                
        "\"ID_log_audit\"=?, " +
        "\"UUID\"=? " +
        " WHERE \"Kode_mata_kuliah\"=? ";
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
        " \"Kode_mata_kuliah\"=? ";
        
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
    private javax.swing.JLabel IDlogauditLabel1;
    private javax.swing.JFormattedTextField SKSmatakuliahField;
    private javax.swing.JLabel SKSmatakuliahLabel;
    private javax.swing.JFormattedTextField SKSprakteklapanganField;
    private javax.swing.JLabel SKSprakteklapanganLabel;
    private javax.swing.JFormattedTextField SKSpraktikumField;
    private javax.swing.JLabel SKSpraktikumLabel;
    private javax.swing.JFormattedTextField SKSsimulasiField;
    private javax.swing.JLabel SKSsimulasiLabel;
    private javax.swing.JFormattedTextField SKStatapmukaField;
    private javax.swing.JLabel SKStatapmukaLabel;
    private com.toedter.calendar.JDateChooser TGLmulaiefektifField;
    private javax.swing.JLabel TGLmulaiefektifLabel;
    private javax.swing.JComboBox acarapraktekField;
    private javax.swing.JLabel acarapraktekLabel;
    private javax.swing.JComboBox bahanajarField;
    private javax.swing.JLabel bahanajarLabel;
    private Widget.Button buttonBaru;
    private Widget.Button buttonDelete;
    private Widget.Button buttonFirst;
    private Widget.Button buttonInsert;
    private Widget.Button buttonLast;
    private Widget.Button buttonNext;
    private Widget.Button buttonPrev;
    private Widget.Button buttonUpdate;
    private javax.swing.JComboBox diktatField;
    private javax.swing.JLabel diktatLabel;
    private javax.swing.JScrollPane gulungPanelAtribut;
    private javax.swing.JScrollPane gulungTabel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JComboBox jenismatakuliahField;
    private javax.swing.JLabel jenismatakuliahLabel;
    private javax.swing.JComboBox kelompokmatakuliahField;
    private javax.swing.JLabel kelompokmatakuliahLabel;
    private javax.swing.JComboBox kodejenjangpendidikanField;
    private javax.swing.JLabel kodejenjangpendidikanLabel;
    private javax.swing.JTextField kodekelasField;
    private javax.swing.JLabel kodekelasLabel;
    private javax.swing.JTextField kodekurikulumField;
    private javax.swing.JLabel kodekurikulumLabel;
    private javax.swing.JTextField kodematakuliahField;
    private javax.swing.JLabel kodematakuliahLabel;
    private javax.swing.JFormattedTextField kodeperguruantinggiField;
    private javax.swing.JLabel kodeperguruantinggiLabel;
    private javax.swing.JComboBox kodeprogramstudiField;
    private javax.swing.JLabel kodeprogramstudiLabel;
    private javax.swing.JTextField metodepelaksanaankuliahField;
    private javax.swing.JLabel metodepelaksanaankuliahLabel;
    private javax.swing.JTextField namamatakuliahField;
    private javax.swing.JLabel namamatakuliahLabel;
    private javax.swing.JFormattedTextField nidnField;
    private javax.swing.JLabel nidnLabel;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JComboBox sapField;
    private javax.swing.JLabel sapLabel;
    private javax.swing.JComboBox semesterField;
    private javax.swing.JLabel semesterLabel;
    private javax.swing.JLabel semesterpelaporanLabel;
    private javax.swing.JFormattedTextField semesterpelapporanField;
    private javax.swing.JComboBox silabusField;
    private javax.swing.JLabel silabusLabel;
    private javax.swing.JTextField statusmatakuliahField;
    private javax.swing.JLabel statusmatakuliahLabel;
    private javax.swing.JTable tabel;
    private javax.swing.JFormattedTextField tahunpelaporanField;
    private javax.swing.JLabel tahunpelaporanLabel;
    private com.toedter.calendar.JDateChooser tglakhirefektifField;
    private javax.swing.JLabel tglakhirefektifLabel;
    private javax.swing.JFormattedTextField uuidField;
    // End of variables declaration//GEN-END:variables
}

