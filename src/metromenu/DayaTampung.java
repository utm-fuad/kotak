/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelDayaTampung;
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
public class DayaTampung extends javax.swing.JPanel {
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
    private JTextField metodekuliah;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    private JTextField metodekuliahekstensi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo4;
    private JTextField metodesp;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo5;
    Model.ModelDayaTampung model;
    private int idlogaudit;
    private String kueri,KodePT;

    /**
     * Creates new form PanelHalaman1
     */
    public DayaTampung() {    
        initComponents();
    }
    
    public DayaTampung(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        model = new ModelDayaTampung();
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
        IDlogauditField.setVisible(false);
        IDlogauditLabel.setVisible(false);
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
                " from pdpt_dev.\"TMST_PROGRAM_STUDI\" where \"Kode_perguruan_tinggi\"="+KodePT;
        kompletTextFieldCombo2.initDataUtkAutoComplete(query);        
        kodeprogramstudi = kompletTextFieldCombo2.dapatkanTextField();
        kodeprogramstudiField = kompletTextFieldCombo2.dapatkanComboBox();
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                metodekuliahField, metodekuliah);        
        String [][] datanya3 = {{"A", "Senin-Jumat/Sabtu"},
            {"B","Sabtu dan Minggu"}}; 
        kompletTextFieldCombo3.initDataUtkAutoComplete(datanya3);          
        metodekuliah = kompletTextFieldCombo3.dapatkanTextField();
        metodekuliahField = kompletTextFieldCombo3.dapatkanComboBox();                
        
        kompletTextFieldCombo4 = new KompletTextFieldCombo(kon, 
                metodekuliahekstensiField, metodekuliahekstensi);        
        String [][] datanya4 = {{"A", "Senin-Jumat/Sabtu"},
            {"B","Sabtu dan Minggu"}}; 
        kompletTextFieldCombo4.initDataUtkAutoComplete(datanya4);          
        metodekuliahekstensi = kompletTextFieldCombo4.dapatkanTextField();
        metodekuliahekstensiField = kompletTextFieldCombo4.dapatkanComboBox();
        
        kompletTextFieldCombo5 = new KompletTextFieldCombo(kon, 
                metodeSPField, metodesp);        
        String [][] datanya5 = {{"A", "Hanya Perbaikan Nilai"},
            {"B","Perbaikan Nilai dan Baru"}}; 
        kompletTextFieldCombo5.initDataUtkAutoComplete(datanya5);
        metodesp = kompletTextFieldCombo5.dapatkanTextField();
        metodeSPField = kompletTextFieldCombo5.dapatkanComboBox();
    }
    
    private void inisialisasiData() {
        namaTabel = "pdpt_dev.\"TRAN_DAYA_TAMPUNG\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select * from " + namaTabel + " where \"Kode_perguruan_tinggi\"="+KodePT;
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public DayaTampung(String [][] data, String [] kolom) {
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
        tahunpelaporanField.setText("");
        semesterpelaporanField.setText("");
        kodeperguruantinggiField.setText("" + homePage.dapatkanKodePT());
        kodejenjangpendidikan.setText("");
        kodeprogramstudi.setText("");
        targetmhsbaruField.setText("");
        calonikutseleksiField.setText("");
        calonlulusseleksiField.setText("");
        mendafatsebagaimahasiswaField.setText("");
        pesertamengundurkandiriField.setText("");        
        tglawalkuliahganjilField.setDate(null);
        tglakhirkuliahganjilField.setDate(null);        
        jmlminggukuliahganjilField.setText("");
        tglawalkuliahgenapField.setDate(null);
        tglakhirkuliahgenapField.setDate(null);
        jmlminggukuliahgenapField.setText("");
        metodekuliah.setText("");
        metodekuliahekstensi.setText("");
        jmlSPsetahunField.setText("");
        metodesp.setText("");        
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
        tahunpelaporanField.setText(tabel.getValueAt(row, 0).toString());
        semesterpelaporanField.setText(tabel.getValueAt(row, 1).toString());        
        kodeperguruantinggiField.setText(tabel.getValueAt(row, 2).toString());
        kodejenjangpendidikan.setText(tabel.getValueAt(row, 3).toString());
        kodeprogramstudi.setText(tabel.getValueAt(row, 4).toString());
        targetmhsbaruField.setText(tabel.getValueAt(row, 5).toString());        
        calonikutseleksiField.setText(tabel.getValueAt(row, 6).toString());
        calonlulusseleksiField.setText(tabel.getValueAt(row, 7).toString());
        mendafatsebagaimahasiswaField.setText(tabel.getValueAt(row, 8).toString());
        pesertamengundurkandiriField.setText(tabel.getValueAt(row, 9).toString());
        
        String strKalendar = tabel.getValueAt(row, 10).toString();
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
        tglawalkuliahganjilField.setCalendar(kalendar);        
        
        strKalendar = tabel.getValueAt(row, 11).toString();
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
        tglakhirkuliahganjilField.setCalendar(kalendar);     
        
        jmlminggukuliahganjilField.setText(tabel.getValueAt(row, 12).toString());
        
        strKalendar = tabel.getValueAt(row, 13).toString();
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
        tglawalkuliahgenapField.setCalendar(kalendar);     
        
        strKalendar = tabel.getValueAt(row, 14).toString();
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
        tglakhirkuliahgenapField.setCalendar(kalendar);     
        
        jmlminggukuliahgenapField.setText(tabel.getValueAt(row, 15).toString());
        metodekuliah.setText(tabel.getValueAt(row, 16).toString());
        metodekuliahekstensi.setText(tabel.getValueAt(row, 17).toString());
        jmlSPsetahunField.setText(tabel.getValueAt(row, 18).toString());
        metodesp.setText(tabel.getValueAt(row, 19).toString());
        
        //IDlogauditField.setText(tabel.getValueAt(row, 20).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
        data[0] = (!tahunpelaporanField.getText().equals("    ")?tahunpelaporanField.getText():"0");
        data[1] = (!semesterpelaporanField.getText().equals(" ")?semesterpelaporanField.getText():"0");
        data[2] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");
        
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[3] = datakodejenjangpendidikan[0];
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[4] = datakodeprogramstudi[0];
        
        data[5] = (!targetmhsbaruField.getText().equals("")?targetmhsbaruField.getText():"0");      
        data[6] = (!calonikutseleksiField.getText().equals("")?calonikutseleksiField.getText():"0");
        data[7] = (!calonlulusseleksiField.getText().equals("")?calonlulusseleksiField.getText():"0");
        data[8] = (!mendafatsebagaimahasiswaField.getText().equals("")?mendafatsebagaimahasiswaField.getText():"0");
        data[9] = (!pesertamengundurkandiriField.getText().equals("")?pesertamengundurkandiriField.getText():"0");
                
        kalendar = tglawalkuliahganjilField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data7 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[10] = (data7!=null?data7:"");
                
        kalendar = tglakhirkuliahganjilField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data18 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[11] = (data18!=null?data18:"");
        
        data[12] = (!jmlminggukuliahganjilField.getText().equals("")?jmlminggukuliahganjilField.getText():"0");
        
        kalendar = tglawalkuliahgenapField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data13 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[13] = (data13!=null?data13:"");
                
        kalendar = tglakhirkuliahgenapField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data14 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[14] = (data14!=null?data14:"");
        
        data[15] = (!jmlminggukuliahgenapField.getText().equals("")?jmlminggukuliahgenapField.getText():"0");
        
        String datametodekuliahmentah = metodekuliah.getText();
        System.out.println("data metodekuliah mentah: " + datametodekuliahmentah);
        String [] datametodekuliah = datametodekuliahmentah.split(" ");
        System.out.println("data metodekuliah yg dipilih: " + datametodekuliah[0]);
        data[16] = datametodekuliah[0];
        
        String datametodekuliahekstensimentah = metodekuliahekstensi.getText();
        System.out.println("data metodekuliahekstensi mentah: " + datametodekuliahmentah);
        String [] datametodekuliahekstensi = datametodekuliahekstensimentah.split(" ");
        System.out.println("data metodekuliahekstensi yg dipilih: " + datametodekuliahekstensi[0]);
        data[17] = datametodekuliahekstensi[0];
        
        data[18] = (!jmlSPsetahunField.getText().equals("")?jmlSPsetahunField.getText():"0");
        
        String datametodespmentah = metodesp.getText();
        System.out.println("data metode sp mentah: " + datametodespmentah);
        String [] datametodesp = datametodespmentah.split(" ");
        System.out.println("data metode sp yg dipilih: " + datametodesp[0]);
        data[19] = datametodesp[0];
        
        data[20] = "" + idlogaudit;
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];                        
        data[0] = targetmhsbaruField.getText();
        data[1] = calonikutseleksiField.getText();
        data[2] = calonlulusseleksiField.getText();
        data[3] = mendafatsebagaimahasiswaField.getText();
        data[4] = pesertamengundurkandiriField.getText();        
        
        kalendar = tglawalkuliahganjilField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data6 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[5] = (data6!=null?data6:"");        
        
        kalendar = tglakhirkuliahganjilField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data17 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[6] = (data17!=null?data17:"");        
        
        data[7] = jmlminggukuliahganjilField.getText();        
        
        kalendar = tglawalkuliahgenapField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data8 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[8] = (data8!=null?data8:"");        
        
        kalendar = tglakhirkuliahgenapField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data9 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[9] = (data9!=null?data9:"");        
        
        data[10] = jmlminggukuliahgenapField.getText();        
        data[11] = metodekuliah.getText();
        data[12] = metodekuliahekstensi.getText();
        data[13] = jmlSPsetahunField.getText();
        data[14] = metodesp.getText();
        
        data[15] = "" + idlogaudit;
        
        data[16] = tahunpelaporanField.getText();
        data[17] = semesterpelaporanField.getText();
        data[18] = kodeperguruantinggiField.getText();
                
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[19] = datakodejenjangpendidikan[0];
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[20] = datakodeprogramstudi[0];
        
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[5];
        data[0] = tahunpelaporanField.getText();
        data[1] = semesterpelaporanField.getText();
        data[2] = kodeperguruantinggiField.getText();
        data[3] = kodejenjangpendidikan.getText();
        data[4] = kodeprogramstudi.getText();
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
        tahunpelaporanLabel = new javax.swing.JLabel();
        semesterpelaporanLabel = new javax.swing.JLabel();
        kodeperguruantinggiLabel = new javax.swing.JLabel();
        kodejenjangpendidikanLabel = new javax.swing.JLabel();
        kodeprogramstudiLabel = new javax.swing.JLabel();
        targetmhsbaruLabel = new javax.swing.JLabel();
        calonikutseleksiLabel = new javax.swing.JLabel();
        calonlulusseleksiLabel = new javax.swing.JLabel();
        mendafatsebagaimahasiswaLabel = new javax.swing.JLabel();
        pesertamengundurkandiriLabel = new javax.swing.JLabel();
        tglawalkuliahganjilLabel = new javax.swing.JLabel();
        tglakhirkuliahganjilLabel = new javax.swing.JLabel();
        jmlminggukuliahganjilLabel = new javax.swing.JLabel();
        tglawalkuliahgenapLabel = new javax.swing.JLabel();
        tglakhirkuliahgenapLabel = new javax.swing.JLabel();
        jmlminggukuliahgenapLabel = new javax.swing.JLabel();
        metodekuliahLabel = new javax.swing.JLabel();
        metodekuliahekstensiLabel = new javax.swing.JLabel();
        jmlSPsetahunLabel = new javax.swing.JLabel();
        metodeSPLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        tahunpelaporanField = new javax.swing.JFormattedTextField();
        semesterpelaporanField = new javax.swing.JFormattedTextField();
        kodeperguruantinggiField = new javax.swing.JFormattedTextField();
        kodejenjangpendidikanField = new javax.swing.JComboBox();
        kodeprogramstudiField = new javax.swing.JComboBox();
        targetmhsbaruField = new javax.swing.JFormattedTextField();
        calonikutseleksiField = new javax.swing.JFormattedTextField();
        calonlulusseleksiField = new javax.swing.JFormattedTextField();
        mendafatsebagaimahasiswaField = new javax.swing.JFormattedTextField();
        pesertamengundurkandiriField = new javax.swing.JFormattedTextField();
        tglawalkuliahganjilField = new com.toedter.calendar.JDateChooser();
        tglakhirkuliahganjilField = new com.toedter.calendar.JDateChooser();
        jmlminggukuliahganjilField = new javax.swing.JFormattedTextField();
        tglawalkuliahgenapField = new com.toedter.calendar.JDateChooser();
        tglakhirkuliahgenapField = new com.toedter.calendar.JDateChooser();
        jmlminggukuliahgenapField = new javax.swing.JFormattedTextField();
        metodekuliahField = new javax.swing.JComboBox();
        metodekuliahekstensiField = new javax.swing.JComboBox();
        jmlSPsetahunField = new javax.swing.JFormattedTextField();
        metodeSPField = new javax.swing.JComboBox();
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

        tahunpelaporanLabel.setText("Tahun Pelaporan:");
        panelLabel.add(tahunpelaporanLabel);

        semesterpelaporanLabel.setText("Semester Pelaporan:");
        panelLabel.add(semesterpelaporanLabel);

        kodeperguruantinggiLabel.setText("Kode Perguruan Tinggi:");
        panelLabel.add(kodeperguruantinggiLabel);

        kodejenjangpendidikanLabel.setText("Kode Jenjang Pendidikan:");
        panelLabel.add(kodejenjangpendidikanLabel);

        kodeprogramstudiLabel.setText("Kode Program Studi:");
        panelLabel.add(kodeprogramstudiLabel);

        targetmhsbaruLabel.setText("Target Mhs Baru:");
        panelLabel.add(targetmhsbaruLabel);

        calonikutseleksiLabel.setText("Calon Ikut Seleksi:");
        panelLabel.add(calonikutseleksiLabel);

        calonlulusseleksiLabel.setText("Calon Lulus Seleksi:");
        panelLabel.add(calonlulusseleksiLabel);

        mendafatsebagaimahasiswaLabel.setText("Mendaftar Sebagai Mahasiswa:");
        panelLabel.add(mendafatsebagaimahasiswaLabel);

        pesertamengundurkandiriLabel.setText("Peserta Mengundurkan Diri:");
        panelLabel.add(pesertamengundurkandiriLabel);

        tglawalkuliahganjilLabel.setText("Tgl Awal Kuliah Ganjil:");
        panelLabel.add(tglawalkuliahganjilLabel);

        tglakhirkuliahganjilLabel.setText("Tgl Akhir Kuliah Ganjil:");
        panelLabel.add(tglakhirkuliahganjilLabel);

        jmlminggukuliahganjilLabel.setText("Jml Minggu Kuliah Ganjil:");
        panelLabel.add(jmlminggukuliahganjilLabel);

        tglawalkuliahgenapLabel.setText("Tgl Awal Kuliah Genap:");
        panelLabel.add(tglawalkuliahgenapLabel);

        tglakhirkuliahgenapLabel.setText("Tgl Akhir Kuliah Genap:");
        panelLabel.add(tglakhirkuliahgenapLabel);

        jmlminggukuliahgenapLabel.setText("Jml Minggu Kuliah Genap:");
        panelLabel.add(jmlminggukuliahgenapLabel);

        metodekuliahLabel.setText("Metode Kuliah:");
        panelLabel.add(metodekuliahLabel);

        metodekuliahekstensiLabel.setText("Metode Kuliah Ekstensi:");
        panelLabel.add(metodekuliahekstensiLabel);

        jmlSPsetahunLabel.setText("Jml SP Setahun:");
        panelLabel.add(jmlSPsetahunLabel);

        metodeSPLabel.setText("Metode SP:");
        panelLabel.add(metodeSPLabel);

        IDlogauditLabel.setText("ID Log Audit:");
        panelLabel.add(IDlogauditLabel);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(21, 0));

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

        panelTextField.add(kodejenjangpendidikanField);

        panelTextField.add(kodeprogramstudiField);

        targetmhsbaruField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(targetmhsbaruField);

        calonikutseleksiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(calonikutseleksiField);

        calonlulusseleksiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(calonlulusseleksiField);

        mendafatsebagaimahasiswaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(mendafatsebagaimahasiswaField);

        pesertamengundurkandiriField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(pesertamengundurkandiriField);
        panelTextField.add(tglawalkuliahganjilField);
        panelTextField.add(tglakhirkuliahganjilField);

        jmlminggukuliahganjilField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(jmlminggukuliahganjilField);
        panelTextField.add(tglawalkuliahgenapField);
        panelTextField.add(tglakhirkuliahgenapField);

        jmlminggukuliahgenapField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(jmlminggukuliahgenapField);

        panelTextField.add(metodekuliahField);

        panelTextField.add(metodekuliahekstensiField);

        jmlSPsetahunField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(jmlSPsetahunField);

        panelTextField.add(metodeSPField);

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
        " values(?,?,?,?,?,?,?,?,?,?," + 
                "?,?,?,?,?,?,?,?,?,?,?)";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukInsert() );
        int hasil = model.insertAktifitasDOsen(query,
            dapatkanNilaiUntukInsert() );
        if (hasil == 1) {
            query = "select * from " + namaTabel+" where \"Kode_perguruan_tinggi\"="+KodePT;
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
        " SET \"Target_mhs_baru\"=?, " +
        "\"Calon_ikut_seleksi\"=?, " +
        "\"Calon_lulus_seleksi\"=?, " +
        "\"Mendaftar_sebagai_mahasiswa\"=?, " +
        "\"Peserta_mengundurkan_diri\"=?, " +
        "\"Tgl_awal_kuliah_ganjil\"=?, " +
        "\"Tgl_akhir_kuliah_ganjil\"=?, " +
        "\"Jml_minggu_kuliah_ganjil\"=?, " +
        "\"Tgl_awal_kuliah_genap\"=?, " +
        "\"Tgl_akhir_kuliah_genap\"=?, " +
        "\"Jml_minggu_kuliah_genap\"=?, " +
        "\"Metode_kuliah\"=?, " +
        "\"Metode_kuliah_ekstensi\"=?, " +
        "\"Jml_SP_setahun\"=?, " +        
        "\"Metode_SP\"=?, " +                
        "\"ID_log_audit\"=? " +
        " WHERE \"Tahun_pelaporan\"=? AND " +
        "\"Semester_pelaporan\"=? AND " +
        "\"Kode_perguruan_tinggi\"=? AND " +
        "\"Kode_jenjang_pendidikan\"=? AND " +
        "\"Kode_program_studi\"=?";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukUpdate() );
        int hasil = model.updateAktifitasDOsen(query,
            dapatkanNilaiUntukInsert() );
        if (hasil == 1) {
            query = "select * from " + namaTabel+" where \"Kode_perguruan_tinggi\"="+KodePT;
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
        String query = "delete from " + namaTabel + 
        " WHERE \"Tahun_pelaporan\"=? AND " +
        "\"Semester_pelaporan\"=? AND " +
        "\"Kode_perguruan_tinggi\"=? AND " +
        "\"Kode_jenjang_pendidikan\"=? AND " +
        "\"Kode_program_studi\"=?";
        
        String [] pkDataYgDihapus = dapatkanNilaiUntukDelete();

        int hasil = model.delete(query,
            dapatkanNilaiUntukDelete() );
        if (hasil == 1) {
            query = "select * from " + namaTabel+" where \"Kode_perguruan_tinggi\"="+KodePT;
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
    private javax.swing.JFormattedTextField calonikutseleksiField;
    private javax.swing.JLabel calonikutseleksiLabel;
    private javax.swing.JFormattedTextField calonlulusseleksiField;
    private javax.swing.JLabel calonlulusseleksiLabel;
    private javax.swing.JScrollPane gulungPanelAtribut;
    private javax.swing.JScrollPane gulungTabel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JFormattedTextField jmlSPsetahunField;
    private javax.swing.JLabel jmlSPsetahunLabel;
    private javax.swing.JFormattedTextField jmlminggukuliahganjilField;
    private javax.swing.JLabel jmlminggukuliahganjilLabel;
    private javax.swing.JFormattedTextField jmlminggukuliahgenapField;
    private javax.swing.JLabel jmlminggukuliahgenapLabel;
    private javax.swing.JComboBox kodejenjangpendidikanField;
    private javax.swing.JLabel kodejenjangpendidikanLabel;
    private javax.swing.JFormattedTextField kodeperguruantinggiField;
    private javax.swing.JLabel kodeperguruantinggiLabel;
    private javax.swing.JComboBox kodeprogramstudiField;
    private javax.swing.JLabel kodeprogramstudiLabel;
    private javax.swing.JFormattedTextField mendafatsebagaimahasiswaField;
    private javax.swing.JLabel mendafatsebagaimahasiswaLabel;
    private javax.swing.JComboBox metodeSPField;
    private javax.swing.JLabel metodeSPLabel;
    private javax.swing.JComboBox metodekuliahField;
    private javax.swing.JLabel metodekuliahLabel;
    private javax.swing.JComboBox metodekuliahekstensiField;
    private javax.swing.JLabel metodekuliahekstensiLabel;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JFormattedTextField pesertamengundurkandiriField;
    private javax.swing.JLabel pesertamengundurkandiriLabel;
    private javax.swing.JFormattedTextField semesterpelaporanField;
    private javax.swing.JLabel semesterpelaporanLabel;
    private javax.swing.JTable tabel;
    private javax.swing.JFormattedTextField tahunpelaporanField;
    private javax.swing.JLabel tahunpelaporanLabel;
    private javax.swing.JFormattedTextField targetmhsbaruField;
    private javax.swing.JLabel targetmhsbaruLabel;
    private com.toedter.calendar.JDateChooser tglakhirkuliahganjilField;
    private javax.swing.JLabel tglakhirkuliahganjilLabel;
    private com.toedter.calendar.JDateChooser tglakhirkuliahgenapField;
    private javax.swing.JLabel tglakhirkuliahgenapLabel;
    private com.toedter.calendar.JDateChooser tglawalkuliahganjilField;
    private javax.swing.JLabel tglawalkuliahganjilLabel;
    private com.toedter.calendar.JDateChooser tglawalkuliahgenapField;
    private javax.swing.JLabel tglawalkuliahgenapLabel;
    // End of variables declaration//GEN-END:variables
}
