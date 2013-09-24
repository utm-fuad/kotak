/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelTransaksiTenagaPendukung;
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
public class TenagaPendukung extends javax.swing.JPanel {
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
    Model.ModelTransaksiTenagaPendukung model;
    private JTextField kodeprogramstudi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo1;
    private JTextField kodefakultas;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    private JTextField jeniskelamin;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    private JTextField kodejenjangpendidikan;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo4;
    
    private int kodetenagapendukung, idlogaudit;
    private String kueri,kodept;
    

    /**
     * Creates new form PanelHalaman1
     */
    public TenagaPendukung() {    
        initComponents();
    }
    
    public TenagaPendukung(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        model = new ModelTransaksiTenagaPendukung();
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        kodetenagapendukungField.setVisible(false);
        kodetenagapendukungLabel.setVisible(false);
        kodept = homePage.dapatkanKodePT();
        kodeperguruantinggiField.setText(kodept);
        inisialisasiData();
        initAutoComplete();
    }
    
    private void initPKAutoIncrement() {
        query = "select \"Kode_tenaga_pendukung\" " +
                " from pdpt_dev.\"TRAN_TENAGA_PENDUKUNG\" " +
                " order by \"Kode_tenaga_pendukung\" desc";
        kon.selectData(query);     
        String [][] data = kon.dapatkanData();
        if (data[0][0] == null) {
            kodetenagapendukung = 1;
        } else {
            kodetenagapendukung = Integer.parseInt(data[0][0]);
            kodetenagapendukung++;            
        }
        System.out.println("kodetenagapendukung: " + kodetenagapendukung);
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
                kodefakultasField, kodefakultas);        
        query = "SELECT \"Kode_fakultas\", \"Nama_fakultas\" " +
                " from pdpt_dev.\"TMST_FAKULTAS\" where \"Kode_perguruan_tinggi\"='"+kodept+"'"; 
        kompletTextFieldCombo2.initDataUtkAutoComplete(query);        
        kodefakultas = kompletTextFieldCombo2.dapatkanTextField();
        kodefakultasField = kompletTextFieldCombo2.dapatkanComboBox();        
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                jeniskelaminField, jeniskelamin);        
        String [][] datanya3 = {{"L", "Laki-laki"},
            {"P","Perempuan"}}; 
        kompletTextFieldCombo3.initDataUtkAutoComplete(datanya3);
        jeniskelamin = kompletTextFieldCombo3.dapatkanTextField();
        jeniskelaminField = kompletTextFieldCombo3.dapatkanComboBox();
        
        kompletTextFieldCombo4 = new KompletTextFieldCombo(kon, 
                kodejenjangpendidikanField, kodejenjangpendidikan);        
        query = "select \"Kode_jenjang_pendidikan\", \"Deskripsi\" " +
                " from pdpt_dev.\"TREF_JENJANG_PENDIDIKAN\""; 
        kompletTextFieldCombo4.initDataUtkAutoComplete(query);        
        kodejenjangpendidikan = kompletTextFieldCombo4.dapatkanTextField();
        kodejenjangpendidikanField = kompletTextFieldCombo4.dapatkanComboBox();        
    }
        
    private void inisialisasiData() {
        namaTabel = "pdpt_dev.\"TRAN_TENAGA_PENDUKUNG\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select \"NIP_baru\",\"NIP_Lama\",\"Nomor_tenaga_pendukung\",\"Nama_tenaga_pendukung\","
                + "\"Kode_perguruan_tinggi\",\"Kode_program_studi\",\"Kode_fakultas\",\"Nomor_KTP\","
                + "\"tempat_lahir\",\"Tanggal_lahir\",\"Jenis_kelamin\",\"Jenis_tenaga_pendukung\","
                + "\"Kode_jenjang_pendidikan\",\"Jabatan\",\"Jenis_SK\",\"Nomor_SK\",\"Tanggal_SK\","
                + "\"Tanggal_akhir_SK\" from " + namaTabel;
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public TenagaPendukung(String [][] data, String [] kolom) {
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
        kodetenagapendukungField.setText("" + kodetenagapendukung);
        NIPbaruField.setText("");
        NIPLamaField.setText("");
        nomortenagapendukungField.setText("");
        namatenagapendukungField.setText("");
        //kodeperguruantinggiField.setText("" + homePage.dapatkanKodePT());//pdpt pt
        kodeperguruantinggiField.setText("");//pdpt dikti
        kodeprogramstudi.setText("");
        kodefakultas.setText("");        
        nomorKTPField.setText("");
        
        tempatLahirField.setText("");
        tanggallahirField.setDate(null);
        jeniskelamin.setText("");
        jenistenagapendukungField.setText("");
        kodejenjangpendidikan.setText("");
        jabatanField.setText("");        
        jenisSKField.setText("");
        
        nomorSKField.setText("");
        tanggalSKField.setDate(null);        
        tanggalakhirSKField.setDate(null);
        IDlogauditField.setText("");
        
        NIPbaruField.requestFocus();
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
        kodetenagapendukungField.setText(tabel.getValueAt(row, 0).toString());
        NIPbaruField.setText(tabel.getValueAt(row, 1).toString());
        NIPLamaField.setText(tabel.getValueAt(row, 2).toString());
        nomortenagapendukungField.setText(tabel.getValueAt(row, 3).toString());
        namatenagapendukungField.setText(tabel.getValueAt(row, 4).toString());
        kodeperguruantinggiField.setText(tabel.getValueAt(row, 5).toString());
        System.out.println("kodept:" + tabel.getValueAt(row, 5).toString());
        kodeprogramstudi.setText(tabel.getValueAt(row, 6).toString());
        System.out.println("kodeps:" + tabel.getValueAt(row, 6).toString());
        kodefakultas.setText(tabel.getValueAt(row, 7).toString());
        System.out.println("kodefk:" + tabel.getValueAt(row, 7).toString());
        nomorKTPField.setText(tabel.getValueAt(row, 8).toString());
        tempatLahirField.setText(tabel.getValueAt(row, 9).toString());
        
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
        kalendar.set(tahun, intBulan-1, hari); 
        tanggallahirField.setCalendar(kalendar);        
        
        jeniskelamin.setText(tabel.getValueAt(row, 11).toString());
        jenistenagapendukungField.setText(tabel.getValueAt(row, 12).toString());
        kodejenjangpendidikan.setText(tabel.getValueAt(row, 13).toString());
        jabatanField.setText(tabel.getValueAt(row, 14).toString());
        jenisSKField.setText(tabel.getValueAt(row, 15).toString());
        nomorSKField.setText(tabel.getValueAt(row, 16).toString());
                
        strKalendar = tabel.getValueAt(row, 17).toString();
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
        kalendar.set(tahun, intBulan-1, hari); 
        tanggalSKField.setCalendar(kalendar);
        
        strKalendar = tabel.getValueAt(row, 18).toString();
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
        kalendar.set(tahun, intBulan-1, hari); 
        tanggalakhirSKField.setCalendar(kalendar);
        
        //IDlogauditField.setText(tabel.getValueAt(row, 19).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
//        data[0] = (!kodetenagapendukungField.getText().equals("")?kodetenagapendukungField.getText():"0");
        data[0] = (!NIPbaruField.getText().equals("")?NIPbaruField.getText():"0");
        data[1] = (!NIPLamaField.getText().equals("")?NIPLamaField.getText():"0");
        data[2] = (!nomortenagapendukungField.getText().equals("")?nomortenagapendukungField.getText():"X");
        data[3] = (!namatenagapendukungField.getText().equals("")?namatenagapendukungField.getText():"X");
        data[4] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[5] = datakodeprogramstudi[0];
        
        String datakodefakultasmentah = kodefakultas.getText();
        System.out.println("data kode fakultas mentah: " + datakodefakultasmentah);
        String [] datakodefakultas = datakodefakultasmentah.split(" ");
        System.out.println("data kode fakultas yg dipilih: " + datakodefakultas[0]);
        data[6] = datakodefakultas[0];
        
        data[7] = (!nomorKTPField.getText().equals("")?nomorKTPField.getText():"0");
        data[8] = (!tempatLahirField.getText().equals("")?tempatLahirField.getText():"X");
        
        kalendar = tanggallahirField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data7 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[9] = (data7!=null?data7:"");
        
        String datajeniskelaminmentah = jeniskelamin.getText();
        System.out.println("data jeniskelamin mentah: " + datajeniskelaminmentah);
        String [] datajeniskelamin = datajeniskelaminmentah.split(" ");
        System.out.println("data jeniskelamin yg dipilih: " + datajeniskelamin[0]);
        data[10] = datajeniskelamin[0];
        
        data[11] = (!jenistenagapendukungField.getText().equals("")?jenistenagapendukungField.getText():"X");
        
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[12] = datakodejenjangpendidikan[0];
        
        data[13] = (!jabatanField.getText().equals("")?jabatanField.getText():"X");
        data[14] = (!jenisSKField.getText().equals("")?jenisSKField.getText():"X");
        data[15] = (!nomorSKField.getText().equals("")?nomorSKField.getText():"X");
                
        kalendar = tanggalSKField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data17 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[16] = (data17!=null?data17:"");
        
        kalendar = tanggalakhirSKField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data18 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[17] = (data18!=null?data18:"");
        
        data[18] = "" + idlogaudit;
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];                                
        data[0] = (!NIPbaruField.getText().equals("")?NIPbaruField.getText():"0");
        data[1] = (!NIPLamaField.getText().equals("")?NIPLamaField.getText():"0");
        data[2] = (!nomortenagapendukungField.getText().equals("")?nomortenagapendukungField.getText():"X");
        data[3] = (!namatenagapendukungField.getText().equals("")?namatenagapendukungField.getText():"X");
        
        data[4] = (!tempatLahirField.getText().equals("")?tempatLahirField.getText():"X");
        
        kalendar = tanggallahirField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data7 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[5] = (data7!=null?data7:"");
        
        String datajeniskelaminmentah = jeniskelamin.getText();
        System.out.println("data jeniskelamin mentah: " + datajeniskelaminmentah);
        String [] datajeniskelamin = datajeniskelaminmentah.split(" ");
        System.out.println("data jeniskelamin yg dipilih: " + datajeniskelamin[0]);
        data[6] = datajeniskelamin[0];
        
        data[7] = (!jenistenagapendukungField.getText().equals("")?jenistenagapendukungField.getText():"X");
        
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[8] = datakodejenjangpendidikan[0];
        
        data[9] = (!jabatanField.getText().equals("")?jabatanField.getText():"X");
        data[10] = (!jenisSKField.getText().equals("")?jenisSKField.getText():"X");
        data[11] = (!nomorSKField.getText().equals("")?nomorSKField.getText():"X");
                
        kalendar = tanggalSKField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data17 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[12] = (data17!=null?data17:"");
        
        kalendar = tanggalakhirSKField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data18 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[13] = (data18!=null?data18:"");
        
        data[14] = "" + idlogaudit;
        //where
        data[15] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[16] = datakodeprogramstudi[0];
        
        String datakodefakultasmentah = kodefakultas.getText();
        System.out.println("data kode fakultas mentah: " + datakodefakultasmentah);
        String [] datakodefakultas = datakodefakultasmentah.split(" ");
        System.out.println("data kode fakultas yg dipilih: " + datakodefakultas[0]);
        data[17] = datakodefakultas[0];
        
        data[18] = (!nomorKTPField.getText().equals("")?nomorKTPField.getText():"0");
//        data[19] = (!kodetenagapendukungField.getText().equals("")?kodetenagapendukungField.getText():"0");
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[4];
        data[0] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[1] = datakodeprogramstudi[0];
        
        String datakodefakultasmentah = kodefakultas.getText();
        System.out.println("data kode fakultas mentah: " + datakodefakultasmentah);
        String [] datakodefakultas = datakodefakultasmentah.split(" ");
        System.out.println("data kode fakultas yg dipilih: " + datakodefakultas[0]);
        data[2] = datakodefakultas[0];
        
        data[3] = (!nomorKTPField.getText().equals("")?nomorKTPField.getText():"0");
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
        kodetenagapendukungLabel = new javax.swing.JLabel();
        NIPbaruLabel = new javax.swing.JLabel();
        NIPLamaLabel = new javax.swing.JLabel();
        nomortenagapendukungLabel = new javax.swing.JLabel();
        namatenagapendukungLabel = new javax.swing.JLabel();
        kodeperguruantinggiLabel = new javax.swing.JLabel();
        kodeprogramstudiLabel = new javax.swing.JLabel();
        kodefakultasLabel = new javax.swing.JLabel();
        nomorKTPLabel = new javax.swing.JLabel();
        tempatLahirLabel = new javax.swing.JLabel();
        tanggallahirLabel = new javax.swing.JLabel();
        jeniskelaminLabel = new javax.swing.JLabel();
        jenistenagapendukungLabel = new javax.swing.JLabel();
        kodejenjangstudiLabel = new javax.swing.JLabel();
        jabatanLabel = new javax.swing.JLabel();
        jenisSKLabel = new javax.swing.JLabel();
        nomorSKLabel = new javax.swing.JLabel();
        tanggalSKLabel = new javax.swing.JLabel();
        tanggalakhirSKLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodetenagapendukungField = new javax.swing.JFormattedTextField();
        NIPbaruField = new javax.swing.JFormattedTextField();
        NIPLamaField = new javax.swing.JFormattedTextField();
        nomortenagapendukungField = new javax.swing.JFormattedTextField();
        namatenagapendukungField = new javax.swing.JTextField();
        kodeperguruantinggiField = new javax.swing.JFormattedTextField();
        kodeprogramstudiField = new javax.swing.JComboBox();
        kodefakultasField = new javax.swing.JComboBox();
        nomorKTPField = new javax.swing.JFormattedTextField();
        tempatLahirField = new javax.swing.JTextField();
        tanggallahirField = new com.toedter.calendar.JDateChooser();
        jeniskelaminField = new javax.swing.JComboBox();
        jenistenagapendukungField = new javax.swing.JTextField();
        kodejenjangpendidikanField = new javax.swing.JComboBox();
        jabatanField = new javax.swing.JTextField();
        jenisSKField = new javax.swing.JTextField();
        nomorSKField = new javax.swing.JTextField();
        tanggalSKField = new com.toedter.calendar.JDateChooser();
        tanggalakhirSKField = new com.toedter.calendar.JDateChooser();
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

        panelLabel.setLayout(new java.awt.GridLayout(20, 0));

        kodetenagapendukungLabel.setText("Kode Tenaga Pendukung:");
        panelLabel.add(kodetenagapendukungLabel);

        NIPbaruLabel.setText("NIP Baru:");
        panelLabel.add(NIPbaruLabel);

        NIPLamaLabel.setText("NIP Lama:");
        panelLabel.add(NIPLamaLabel);

        nomortenagapendukungLabel.setText("Nomor Tenaga Pendukung:");
        panelLabel.add(nomortenagapendukungLabel);

        namatenagapendukungLabel.setText("Nama Tenaga Pendukung:");
        panelLabel.add(namatenagapendukungLabel);

        kodeperguruantinggiLabel.setText("Kode Perguruan Tinggi:");
        panelLabel.add(kodeperguruantinggiLabel);

        kodeprogramstudiLabel.setText("Kode Program Studi:");
        panelLabel.add(kodeprogramstudiLabel);

        kodefakultasLabel.setText("Kode Fakultas:");
        panelLabel.add(kodefakultasLabel);

        nomorKTPLabel.setText("Nomor KTP:");
        panelLabel.add(nomorKTPLabel);

        tempatLahirLabel.setText("Tempat Lahir:");
        panelLabel.add(tempatLahirLabel);

        tanggallahirLabel.setText("Tanggal Lahir:");
        panelLabel.add(tanggallahirLabel);

        jeniskelaminLabel.setText("Jenis Kelamin:");
        panelLabel.add(jeniskelaminLabel);

        jenistenagapendukungLabel.setText("Jenis Tenaga Pendukung:");
        panelLabel.add(jenistenagapendukungLabel);

        kodejenjangstudiLabel.setText("Kode Jenjang Studi:");
        panelLabel.add(kodejenjangstudiLabel);

        jabatanLabel.setText("Jabatan:");
        panelLabel.add(jabatanLabel);

        jenisSKLabel.setText("Jenis SK:");
        panelLabel.add(jenisSKLabel);

        nomorSKLabel.setText("Nomor SK:");
        panelLabel.add(nomorSKLabel);

        tanggalSKLabel.setText("Tanggal SK:");
        panelLabel.add(tanggalSKLabel);

        tanggalakhirSKLabel.setText("Tanggal Akhir SK:");
        panelLabel.add(tanggalakhirSKLabel);

        IDlogauditLabel.setText("ID Log Audit:");
        panelLabel.add(IDlogauditLabel);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(20, 0));

        kodetenagapendukungField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(kodetenagapendukungField);

        NIPbaruField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#############################"))));
        panelTextField.add(NIPbaruField);

        NIPLamaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#########"))));
        panelTextField.add(NIPLamaField);

        nomortenagapendukungField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##########"))));
        panelTextField.add(nomortenagapendukungField);
        panelTextField.add(namatenagapendukungField);

        try {
            kodeperguruantinggiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodeperguruantinggiField);

        panelTextField.add(kodeprogramstudiField);

        panelTextField.add(kodefakultasField);

        nomorKTPField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#################"))));
        panelTextField.add(nomorKTPField);
        panelTextField.add(tempatLahirField);
        panelTextField.add(tanggallahirField);

        panelTextField.add(jeniskelaminField);
        panelTextField.add(jenistenagapendukungField);

        panelTextField.add(kodejenjangpendidikanField);
        panelTextField.add(jabatanField);
        panelTextField.add(jenisSKField);
        panelTextField.add(nomorSKField);
        panelTextField.add(tanggalSKField);
        panelTextField.add(tanggalakhirSKField);

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
        " values(0,?,?,?,?,?,?,?,?,?, " + 
                "?,?,?,?,?,?,?,?,?,?)";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukInsert() );
        int hasil = model.insertAktifitasDOsen(query,
            dapatkanNilaiUntukInsert() );
        if (hasil == 1) {
            query = "select \"NIP_baru\",\"NIP_Lama\",\"Nomor_tenaga_pendukung\",\"Nama_tenaga_pendukung\","
                + "\"Kode_perguruan_tinggi\",\"Kode_program_studi\",\"Kode_fakultas\",\"Nomor_KTP\","
                + "\"tempat_lahir\",\"Tanggal_lahir\",\"Jenis_kelamin\",\"Jenis_tenaga_pendukung\","
                + "\"Kode_jenjang_pendidikan\",\"Jabatan\",\"Jenis_SK\",\"Nomor_SK\",\"Tanggal_SK\","
                + "\"Tanggal_akhir_SK\" from " + namaTabel;
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
        " SET \"NIP_baru\"=?, " +
        "\"NIP_Lama\"=?, " +
        "\"Nomor_tenaga_pendukung\"=?, " +
        "\"Nama_tenaga_pendukung\"=?, " +
        "\"tempat_lahir\"=?, " +
        "\"Tanggal_lahir\"=?, " +        
        "\"Jenis_kelamin\"=?, " +
        "\"Jenis_tenaga_pendukung\"=?, " +
        "\"Kode_jenjang_pendidikan\"=?, " +
        "\"Jabatan\"=?, " +
        "\"Jenis_SK\"=?, " +
        "\"Nomor_SK\"=?, " +
        "\"Tanggal_SK\"=?, " +
        "\"Tanggal_akhir_SK\"=?, " +                
        "\"ID_log_audit\"=? " +
        " WHERE "+
        "\"Kode_perguruan_tinggi\"=? and " +
        "\"Kode_program_studi\"=? and " +
        "\"Kode_fakultas\"=? and " +
        "\"Nomor_KTP\"=? ";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukUpdate() );
        int hasil = model.updateAktifitasDOsen(query,
            dapatkanNilaiUntukUpdate() );
        if (hasil == 1) {
            query = "select \"NIP_baru\",\"NIP_Lama\",\"Nomor_tenaga_pendukung\",\"Nama_tenaga_pendukung\","
                + "\"Kode_perguruan_tinggi\",\"Kode_program_studi\",\"Kode_fakultas\",\"Nomor_KTP\","
                + "\"tempat_lahir\",\"Tanggal_lahir\",\"Jenis_kelamin\",\"Jenis_tenaga_pendukung\","
                + "\"Kode_jenjang_pendidikan\",\"Jabatan\",\"Jenis_SK\",\"Nomor_SK\",\"Tanggal_SK\","
                + "\"Tanggal_akhir_SK\" from " + namaTabel;
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
        String query = "delete from " + namaTabel +  " WHERE "+
        "\"Kode_perguruan_tinggi\"=? and " +
        "\"Kode_program_studi\"=? and " +
        "\"Kode_fakultas\"=? and " +
        "\"Nomor_KTP\"=? ";
        
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
    private javax.swing.JFormattedTextField NIPLamaField;
    private javax.swing.JLabel NIPLamaLabel;
    private javax.swing.JFormattedTextField NIPbaruField;
    private javax.swing.JLabel NIPbaruLabel;
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
    private javax.swing.JTextField jabatanField;
    private javax.swing.JLabel jabatanLabel;
    private javax.swing.JTextField jenisSKField;
    private javax.swing.JLabel jenisSKLabel;
    private javax.swing.JComboBox jeniskelaminField;
    private javax.swing.JLabel jeniskelaminLabel;
    private javax.swing.JTextField jenistenagapendukungField;
    private javax.swing.JLabel jenistenagapendukungLabel;
    private javax.swing.JComboBox kodefakultasField;
    private javax.swing.JLabel kodefakultasLabel;
    private javax.swing.JComboBox kodejenjangpendidikanField;
    private javax.swing.JLabel kodejenjangstudiLabel;
    private javax.swing.JFormattedTextField kodeperguruantinggiField;
    private javax.swing.JLabel kodeperguruantinggiLabel;
    private javax.swing.JComboBox kodeprogramstudiField;
    private javax.swing.JLabel kodeprogramstudiLabel;
    private javax.swing.JFormattedTextField kodetenagapendukungField;
    private javax.swing.JLabel kodetenagapendukungLabel;
    private javax.swing.JTextField namatenagapendukungField;
    private javax.swing.JLabel namatenagapendukungLabel;
    private javax.swing.JFormattedTextField nomorKTPField;
    private javax.swing.JLabel nomorKTPLabel;
    private javax.swing.JTextField nomorSKField;
    private javax.swing.JLabel nomorSKLabel;
    private javax.swing.JFormattedTextField nomortenagapendukungField;
    private javax.swing.JLabel nomortenagapendukungLabel;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JTable tabel;
    private com.toedter.calendar.JDateChooser tanggalSKField;
    private javax.swing.JLabel tanggalSKLabel;
    private com.toedter.calendar.JDateChooser tanggalakhirSKField;
    private javax.swing.JLabel tanggalakhirSKLabel;
    private com.toedter.calendar.JDateChooser tanggallahirField;
    private javax.swing.JLabel tanggallahirLabel;
    private javax.swing.JTextField tempatLahirField;
    private javax.swing.JLabel tempatLahirLabel;
    // End of variables declaration//GEN-END:variables
}
