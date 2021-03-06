/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelTransaksiPrestasiMahasiswa;
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
public class PrestasiMahasiswa extends javax.swing.JPanel {
    private JPanel panel;
    private koneksi.KoneksiOracleThinClient kon;
    private String judul;
    private HomePage homePage;
    private String namaTabel;
    private String query;
    private int jmlKolom;
    private int barisYgDipilih = 0;
    
    private JTextField kodejenjangpendidikan;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo1;
    private JTextField kodeprogramstudi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    private JTextField jenispenelitian;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    private JTextField kodepengarang;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo4;
    private JTextField hasilpenelitian;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo5;
    private JTextField mediapublikasi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo6;
    private JTextField penelitiandilaksanakansecara;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo7;
    private JTextField jenispembiayaan;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo8;
    private JTextField nim;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo9;
    Model.ModelTransaksiPrestasiMahasiswa model;
    private int kodeprestasimahasiswa, idlogaudit;
    private String kueri,kodept;
    

    /**
     * Creates new form PanelHalaman1
     */
    public PrestasiMahasiswa() {    
        initComponents();
    }
    
    public PrestasiMahasiswa(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        kodept = homePage.dapatkanKodePT();
        kodeperguruantinggiField.setText(kodept);
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        model = new ModelTransaksiPrestasiMahasiswa();
        inisialisasiData();
        initAutoComplete();
        initIDLogAudit();
        kodeprestasimhsField.setVisible(false);
        kodeprestasimhsField.setVisible(false);
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
                kodejenjangpendidikanField, kodejenjangpendidikan);        
        query = "select \"Kode_jenjang_pendidikan\", \"Deskripsi\" " +
                " from pdpt_dev.\"TREF_JENJANG_PENDIDIKAN\""; 
        kompletTextFieldCombo1.initDataUtkAutoComplete(query);        
        kodejenjangpendidikan = kompletTextFieldCombo1.dapatkanTextField();
        kodejenjangpendidikanField = kompletTextFieldCombo1.dapatkanComboBox();
        
        kompletTextFieldCombo2 = new KompletTextFieldCombo(kon, 
                kodeprogramstudiField, kodeprogramstudi);        
        query = "select \"Kode_program_studi\", \"Nama_program_studi\" " +
                " from pdpt_dev.\"TMST_PROGRAM_STUDI\" where \"Kode_perguruan_tinggi\"='"+kodept+"'"; 
        kompletTextFieldCombo2.initDataUtkAutoComplete(query);        
        kodeprogramstudi = kompletTextFieldCombo2.dapatkanTextField();
        kodeprogramstudiField = kompletTextFieldCombo2.dapatkanComboBox();
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                jenispenelitianField, jenispenelitian);        
        String [][] datanya3 = {{"A","Hasil Penelitian"},
            {"B","Non-penelitian"}}; 
        kompletTextFieldCombo3.initDataUtkAutoComplete(datanya3);
        jenispenelitian = kompletTextFieldCombo3.dapatkanTextField();
        jenispenelitianField = kompletTextFieldCombo3.dapatkanComboBox();
                
        kompletTextFieldCombo4 = new KompletTextFieldCombo(kon, 
                kodepengarangField, kodepengarang);        
        String [][] datanya4 = {{"A","Peneliti Anggota"},
            {"B","Penulis Utama"},{"C","Penulis Mandiri"}}; 
        kompletTextFieldCombo4.initDataUtkAutoComplete(datanya4);
        kodepengarang = kompletTextFieldCombo4.dapatkanTextField();
        kodepengarangField = kompletTextFieldCombo4.dapatkanComboBox();
        
        kompletTextFieldCombo5 = new KompletTextFieldCombo(kon, 
                hasilpenelitianField, hasilpenelitian);        
        String [][] datanya5 = {{"1","Paper/makalah"},
            {"2","Buku"},{"3","HKI"},{"4","HKI Komersialisasi"}}; 
        kompletTextFieldCombo5.initDataUtkAutoComplete(datanya5);
        hasilpenelitian = kompletTextFieldCombo5.dapatkanTextField();
        hasilpenelitianField = kompletTextFieldCombo5.dapatkanComboBox();
        
        kompletTextFieldCombo6 = new KompletTextFieldCombo(kon, 
                mediapublikasiField, mediapublikasi);        
        String [][] datanya6 = {{"A","Majalah Populer/Koran"},
            {"B","Seminar Nasional"},{"C","Seminar Internasional"},
            {"D","Prosiding (ISBN)"},{"E","Jurnal Nasional Belum Terakreditasi"},
            {"F","Jurnal Nasional Terakreditasi"},{"G","Jurnal Internasional"}
        }; 
        kompletTextFieldCombo6.initDataUtkAutoComplete(datanya6);
        mediapublikasi = kompletTextFieldCombo6.dapatkanTextField();
        mediapublikasiField = kompletTextFieldCombo6.dapatkanComboBox();
        
        kompletTextFieldCombo7 = new KompletTextFieldCombo(kon, 
                penelitiandilaksanakansecaraField, penelitiandilaksanakansecara);        
        String [][] datanya7 = {{"M","Mandiri"},{"K","Kelompok"}}; 
        kompletTextFieldCombo7.initDataUtkAutoComplete(datanya7);
        penelitiandilaksanakansecara = kompletTextFieldCombo7.dapatkanTextField();
        penelitiandilaksanakansecaraField = kompletTextFieldCombo7.dapatkanComboBox();
        
        kompletTextFieldCombo8 = new KompletTextFieldCombo(kon, 
                jenispembiayaanField, jenispembiayaan);        
        String [][] datanya8 = {{"A","Biaya Sendiri"},
            {"B","Biaya Instansi Sendiri"},{"C","Lembaga Swasta Kerjasama"},
            {"D","Lembaga Swasta Kompetisi"},{"E","Lembaga Pemerintah Kerjasama"},
            {"F","Lembaga Pemerintah Kompetisi"},{"G","Lembaga Internasional"}
        }; 
        kompletTextFieldCombo8.initDataUtkAutoComplete(datanya8);
        jenispembiayaan = kompletTextFieldCombo8.dapatkanTextField();
        jenispembiayaanField = kompletTextFieldCombo8.dapatkanComboBox();
        
        kompletTextFieldCombo9 = new KompletTextFieldCombo(kon, 
                nimField, nim);
        query = "select \"NIM\", \"Nama_mahasiswa\", \"Kode_program_studi\" " +
                " from pdpt_dev.\"TMST_MAHASISWA\" where \"Kode_perguruan_tinggi\"='"+kodept+"'";/* +
                " where Kode_perguruan_tinggi='" +  + " '" + 
                " and Kode_program_studi=' " +  + "'";*/
        kompletTextFieldCombo9.initDataUtkAutoComplete(query);        
        nim = kompletTextFieldCombo9.dapatkanTextField();
        nimField = kompletTextFieldCombo9.dapatkanComboBox();
    }
    
    private void initIDLogAudit() {
        
        query = "select \"Kode_prestasi_mhs\" " +
                " from pdpt_dev.\"TRAN_PRESTASI_MHS\" " +
                " order by \"Kode_prestasi_mhs\" desc";
        kon.selectData(query);     
        String [][] data = kon.dapatkanData();
        try{
        kodeprestasimahasiswa = Integer.parseInt(data[0][0]);
        }catch(Exception e){
            System.out.println(""+e);
        }
        kodeprestasimahasiswa++;
        System.out.println("kodeprestasimahasiswa: " + kodeprestasimahasiswa);
        
        
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
    
    private void inisialisasiData() {
        namaTabel = "pdpt_dev.\"TRAN_PRESTASI_MHS\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select \"Tahun_pelaporan\",\"Semester_pelaporan\",\"NIM\","
                + "\"Kode_jenjang_pendidikan\",\"Kode_perguruan_tinggi\",\"Kode_program_studi\","
                + "\"No_penelitian\",\"Jenis_penelitian\",\"Kode_pengarang\",\"Hasil_penelitian\","
                + "\"Media_publikasi\",\"Penelitian_dilaksanakan_secara\",\"Jumlah_pembiayaan\","
                + "\"Jenis_pembiayaan\",\"Periode_penelitian\",\"Judul_penelitian\",\"Waktu_pelaksanaan\""
                + " from " + namaTabel+" where \"Kode_perguruan_tinggi\"='"+kodept+"'";
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public PrestasiMahasiswa(String [][] data, String [] kolom) {
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
        kodeprestasimhsField.setText("" + kodeprestasimahasiswa);        
        tahunpelaporanField.setText("");
        semesterpelaporanField.setText("");
        nim.setText("");
        kodejenjangpendidikan.setText("");
        kodeperguruantinggiField.setText("" + homePage.dapatkanKodePT());        
        kodeprogramstudi.setText("");
        nopenelitianField.setText("");
        jenispenelitian.setText("");
        
        kodepengarang.setText("");
        hasilpenelitian.setText("");        
        mediapublikasi.setText("");
        penelitiandilaksanakansecara.setText("");
        
        jumlahpembiayaanField.setText("");
        jenispembiayaan.setText("");        
        periodepenelitianField.setText("");
        judulpenelitianField.setText("");
        waktupelaksanaanField.setText("");
        
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
        kodeprestasimhsField.setText(tabel.getValueAt(row, 0).toString());        
        tahunpelaporanField.setText(tabel.getValueAt(row, 1).toString());
        semesterpelaporanField.setText(tabel.getValueAt(row, 2).toString());
        nim.setText(tabel.getValueAt(row, 3).toString());
        kodejenjangpendidikan.setText(tabel.getValueAt(row, 4).toString());
        kodeperguruantinggiField.setText(tabel.getValueAt(row, 5).toString());
        kodeprogramstudi.setText(tabel.getValueAt(row, 6).toString());
        nopenelitianField.setText(tabel.getValueAt(row, 7).toString());
        jenispenelitian.setText(tabel.getValueAt(row, 8).toString());
        kodepengarang.setText(tabel.getValueAt(row, 9).toString());        
        hasilpenelitian.setText(tabel.getValueAt(row, 10).toString());
        mediapublikasi.setText(tabel.getValueAt(row, 11).toString());
        penelitiandilaksanakansecara.setText(tabel.getValueAt(row, 12).toString());
        jumlahpembiayaanField.setText(tabel.getValueAt(row, 13).toString());        
        jenispembiayaan.setText(tabel.getValueAt(row, 14).toString());
        periodepenelitianField.setText(tabel.getValueAt(row, 15).toString());
        judulpenelitianField.setText(tabel.getValueAt(row, 16).toString());
        waktupelaksanaanField.setText(tabel.getValueAt(row, 17).toString());  
        IDlogauditField.setText(tabel.getValueAt(row, 18).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
//        data[0] = (!kodeprestasimhsField.getText().equals("")?kodeprestasimhsField.getText():"0");        
        data[0] = (!tahunpelaporanField.getText().equals("    ")?tahunpelaporanField.getText():"0000");
        data[1] = (!semesterpelaporanField.getText().equals(" ")?semesterpelaporanField.getText():"0");
        
        String datanimmentah = nim.getText();
        System.out.println("data nim mentah: " + datanimmentah);
        String [] datanim = datanimmentah.split(" ");
        System.out.println("data nim yg dipilih: " + datanim[0]);
        data[2] = datanim[0];
        
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[3] = datakodejenjangpendidikan[0];
        
        data[4] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"000000");        
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[5] = datakodeprogramstudi[0];
        
        data[6] = (!nopenelitianField.getText().equals("")?nopenelitianField.getText():"0");                
        
        String datajenispenelitianmentah = jenispenelitian.getText();
        System.out.println("data jenispenelitian mentah: " + datajenispenelitianmentah);
        String [] datajenispenelitian = datajenispenelitianmentah.split(" ");
        System.out.println("data jenispenelitian yg dipilih: " + datajenispenelitian[0]);
        data[7] = datajenispenelitian[0];
        
        String datakodepengarangmentah = kodepengarang.getText();
        System.out.println("data kodepengarang mentah: " + datakodepengarangmentah);
        String [] datakodepengarang = datakodepengarangmentah.split(" ");
        System.out.println("data kodepengarang yg dipilih: " + datakodepengarang[0]);
        data[8] = datakodepengarang[0];
        
        String datahasilpenelitianmentah = hasilpenelitian.getText();
        System.out.println("data hasilpenelitian mentah: " + datahasilpenelitianmentah);
        String [] datahasilpenelitian = datahasilpenelitianmentah.split(" ");
        System.out.println("data hasilpenelitian yg dipilih: " + datahasilpenelitian[0]);
        data[9] = datahasilpenelitian[0];
        
        String datamediapublikasimentah = mediapublikasi.getText();
        System.out.println("data mediapublikasi mentah: " + datamediapublikasimentah);
        String [] datamediapublikasi = datamediapublikasimentah.split(" ");
        System.out.println("data mediapublikasi yg dipilih: " + datamediapublikasi[0]);
        data[10] = datamediapublikasi[0];
        
        String datapenelitiandilaksanakansecaramentah = penelitiandilaksanakansecara.getText();
        System.out.println("data penelitiandilaksanakansecara mentah: " + datapenelitiandilaksanakansecaramentah);
        String [] datapenelitiandilaksanakansecara = datapenelitiandilaksanakansecaramentah.split(" ");
        System.out.println("data penelitiandilaksanakansecara yg dipilih: " + datapenelitiandilaksanakansecara[0]);
        data[11] = datapenelitiandilaksanakansecara[0];
        
        data[12] = (!jumlahpembiayaanField.getText().equals("")?jumlahpembiayaanField.getText():"0");
        
        String datajenispembiayaanmentah = jenispembiayaan.getText();
        System.out.println("data jenispembiayaan mentah: " + datajenispembiayaanmentah);
        String [] datajenispembiayaan = datajenispembiayaanmentah.split(" ");
        System.out.println("data jenispembiayaan yg dipilih: " + datajenispembiayaan[0]);
        data[13] = datajenispembiayaan[0];
        
        data[14] = (!periodepenelitianField.getText().equals("      ")?periodepenelitianField.getText():"000000");
        data[15] = (!judulpenelitianField.getText().equals("")?judulpenelitianField.getText():"X");
        data[16] = (!waktupelaksanaanField.getText().equals("")?waktupelaksanaanField.getText():"0");
//        data[17] = (!IDlogauditField.getText().equals("") ?IDlogauditField.getText():"0");
        data[17] = String.valueOf(idlogaudit);
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];  
        
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[0] = datakodejenjangpendidikan[0];
        data[1] = nopenelitianField.getText(); 
         String datajenispenelitianmentah = jenispenelitian.getText();
        System.out.println("data jenispenelitian mentah: " + datajenispenelitianmentah);
        String [] datajenispenelitian = datajenispenelitianmentah.split(" ");
        System.out.println("data jenispenelitian yg dipilih: " + datajenispenelitian[0]);
        data[2] = datajenispenelitian[0];
        
        String datakodepengarangmentah = kodepengarang.getText();
        System.out.println("data kodepengarang mentah: " + datakodepengarangmentah);
        String [] datakodepengarang = datakodepengarangmentah.split(" ");
        System.out.println("data kodepengarang yg dipilih: " + datakodepengarang[0]);
        data[3] = datakodepengarang[0];
        
        String datahasilpenelitianmentah = hasilpenelitian.getText();
        System.out.println("data hasilpenelitian mentah: " + datahasilpenelitianmentah);
        String [] datahasilpenelitian = datahasilpenelitianmentah.split(" ");
        System.out.println("data hasilpenelitian yg dipilih: " + datahasilpenelitian[0]);
        data[4] = datahasilpenelitian[0];
        
        String datamediapublikasimentah = mediapublikasi.getText();
        System.out.println("data mediapublikasi mentah: " + datamediapublikasimentah);
        String [] datamediapublikasi = datamediapublikasimentah.split(" ");
        System.out.println("data mediapublikasi yg dipilih: " + datamediapublikasi[0]);
        data[5] = datamediapublikasi[0];
        
        String datapenelitiandilaksanakansecaramentah = penelitiandilaksanakansecara.getText();
        System.out.println("data penelitiandilaksanakansecara mentah: " + datapenelitiandilaksanakansecaramentah);
        String [] datapenelitiandilaksanakansecara = datapenelitiandilaksanakansecaramentah.split(" ");
        System.out.println("data penelitiandilaksanakansecara yg dipilih: " + datapenelitiandilaksanakansecara[0]);
        data[6] = datapenelitiandilaksanakansecara[0];        
        
        data[7] = jumlahpembiayaanField.getText();                
        
        String datajenispembiayaanmentah = jenispembiayaan.getText();
        System.out.println("data jenispembiayaan mentah: " + datajenispembiayaanmentah);
        String [] datajenispembiayaan = datajenispembiayaanmentah.split(" ");
        System.out.println("data jenispembiayaan yg dipilih: " + datajenispembiayaan[0]);
        data[8] = datajenispembiayaan[0];
        
        data[9] = periodepenelitianField.getText();
        data[10] = judulpenelitianField.getText();
        data[11] = waktupelaksanaanField.getText();        
        
        data[12] = IDlogauditField.getText();
        
        data[13] = tahunpelaporanField.getText();        
        data[14] = semesterpelaporanField.getText();     
        
        
        String datanimmentah = nim.getText();
        System.out.println("data nim mentah: " + datanimmentah);
        String [] datanim = datanimmentah.split(" ");
        System.out.println("data nim yg dipilih: " + datanim[0]);
        data[15] = datanim[0];
        
        
        data[16] = kodeperguruantinggiField.getText();
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[17] = datakodeprogramstudi[0];
                 
  
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[5];
        data[0] = tahunpelaporanField.getText();        
        data[1] = semesterpelaporanField.getText();     
        
        String datanimmentah = nim.getText();
        System.out.println("data nim mentah: " + datanimmentah);
        String [] datanim = datanimmentah.split(" ");
        System.out.println("data nim yg dipilih: " + datanim[0]);
        data[2] = datanim[0];
        
        data[3] = kodeperguruantinggiField.getText();
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[4] = datakodeprogramstudi[0];
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
        kodeprestasimhsLabel = new javax.swing.JLabel();
        tahunpelaporanLabel = new javax.swing.JLabel();
        semesterpelaporanLabel = new javax.swing.JLabel();
        nimLabel = new javax.swing.JLabel();
        kodejenjangpendidikanLabel = new javax.swing.JLabel();
        kodeperguruantinggiLabel = new javax.swing.JLabel();
        kodeprogramstudiLabel = new javax.swing.JLabel();
        nopenelitianLabel = new javax.swing.JLabel();
        jenispenelitianLabel = new javax.swing.JLabel();
        kodepengarangLabel = new javax.swing.JLabel();
        hasilpenelitianLabel = new javax.swing.JLabel();
        mediapublikasiLabel = new javax.swing.JLabel();
        penelitiandilaksanakansecaraLabel = new javax.swing.JLabel();
        jumlahpembiayaanLabel = new javax.swing.JLabel();
        jenispembiayaanLabel = new javax.swing.JLabel();
        periodepenelitianLabel = new javax.swing.JLabel();
        judulpenelitianLabel = new javax.swing.JLabel();
        waktupelaksanaanLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodeprestasimhsField = new javax.swing.JFormattedTextField();
        tahunpelaporanField = new javax.swing.JFormattedTextField();
        semesterpelaporanField = new javax.swing.JFormattedTextField();
        nimField = new javax.swing.JComboBox();
        kodejenjangpendidikanField = new javax.swing.JComboBox();
        kodeperguruantinggiField = new javax.swing.JFormattedTextField();
        kodeprogramstudiField = new javax.swing.JComboBox();
        nopenelitianField = new javax.swing.JFormattedTextField();
        jenispenelitianField = new javax.swing.JComboBox();
        kodepengarangField = new javax.swing.JComboBox();
        hasilpenelitianField = new javax.swing.JComboBox();
        mediapublikasiField = new javax.swing.JComboBox();
        penelitiandilaksanakansecaraField = new javax.swing.JComboBox();
        jumlahpembiayaanField = new javax.swing.JFormattedTextField();
        jenispembiayaanField = new javax.swing.JComboBox();
        periodepenelitianField = new javax.swing.JFormattedTextField();
        judulpenelitianField = new javax.swing.JTextField();
        waktupelaksanaanField = new javax.swing.JFormattedTextField();
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

        panelLabel.setLayout(new java.awt.GridLayout(19, 0));

        kodeprestasimhsLabel.setText("Kode Prestasi Mhs:");
        panelLabel.add(kodeprestasimhsLabel);

        tahunpelaporanLabel.setText("Tahun Pelaporan:");
        panelLabel.add(tahunpelaporanLabel);

        semesterpelaporanLabel.setText("Semester Pelaporan:");
        panelLabel.add(semesterpelaporanLabel);

        nimLabel.setText("NIM:");
        panelLabel.add(nimLabel);

        kodejenjangpendidikanLabel.setText("Kode Jenjang Pendidikan:");
        panelLabel.add(kodejenjangpendidikanLabel);

        kodeperguruantinggiLabel.setText("Kode Perguruan Tinggi:");
        panelLabel.add(kodeperguruantinggiLabel);

        kodeprogramstudiLabel.setText("Kode Program Studi:");
        panelLabel.add(kodeprogramstudiLabel);

        nopenelitianLabel.setText("No Penelitian:");
        panelLabel.add(nopenelitianLabel);

        jenispenelitianLabel.setText("Jenis Penelitian:");
        panelLabel.add(jenispenelitianLabel);

        kodepengarangLabel.setText("Kode Pengarang:");
        panelLabel.add(kodepengarangLabel);

        hasilpenelitianLabel.setText("Hasil Penelitian:");
        panelLabel.add(hasilpenelitianLabel);

        mediapublikasiLabel.setText("Media Publikasi:");
        panelLabel.add(mediapublikasiLabel);

        penelitiandilaksanakansecaraLabel.setText("Penelitian Dilaksanakan Secara:");
        panelLabel.add(penelitiandilaksanakansecaraLabel);

        jumlahpembiayaanLabel.setText("Jumlah Pembiayaan:");
        panelLabel.add(jumlahpembiayaanLabel);

        jenispembiayaanLabel.setText("Jenis Pembiayaan:");
        panelLabel.add(jenispembiayaanLabel);

        periodepenelitianLabel.setText("Periode Penelitian:");
        panelLabel.add(periodepenelitianLabel);

        judulpenelitianLabel.setText("Judul Penelitian:");
        panelLabel.add(judulpenelitianLabel);

        waktupelaksanaanLabel.setText("Waktu Pelaksanaan:");
        panelLabel.add(waktupelaksanaanLabel);

        IDlogauditLabel.setText("ID Log Audit:");
        panelLabel.add(IDlogauditLabel);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(19, 0));

        kodeprestasimhsField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(kodeprestasimhsField);

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

        nimField.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        panelTextField.add(nimField);

        panelTextField.add(kodejenjangpendidikanField);

        try {
            kodeperguruantinggiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodeperguruantinggiField);

        panelTextField.add(kodeprogramstudiField);

        nopenelitianField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(nopenelitianField);

        panelTextField.add(jenispenelitianField);

        panelTextField.add(kodepengarangField);

        panelTextField.add(hasilpenelitianField);

        panelTextField.add(mediapublikasiField);

        panelTextField.add(penelitiandilaksanakansecaraField);

        jumlahpembiayaanField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(jumlahpembiayaanField);

        panelTextField.add(jenispembiayaanField);

        try {
            periodepenelitianField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(periodepenelitianField);
        panelTextField.add(judulpenelitianField);

        waktupelaksanaanField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(waktupelaksanaanField);

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
        String query = "insert into " + namaTabel + " " +
        " values(0,?,?,?,?,?,?,?,?,?, " +
                "?,?,?,?,?,?,?,?,?)";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukInsert() );
         int hasil = model.insertAktifitasDOsen(query,
            dapatkanNilaiUntukInsert() );
        if (hasil == 1) {
            query = "select \"Tahun_pelaporan\",\"Semester_pelaporan\",\"NIM\","
                + "\"Kode_jenjang_pendidikan\",\"Kode_perguruan_tinggi\",\"Kode_program_studi\","
                + "\"No_penelitian\",\"Jenis_penelitian\",\"Kode_pengarang\",\"Hasil_penelitian\","
                + "\"Media_publikasi\",\"Penelitian_dilaksanakan_secara\",\"Jumlah_pembiayaan\","
                + "\"Jenis_pembiayaan\",\"Periode_penelitian\",\"Judul_penelitian\",\"Waktu_pelaksanaan\""
                + " from " + namaTabel+" where \"Kode_perguruan_tinggi\"='"+kodept+"'";
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
        String query = "update " + namaTabel + " " +
        " SET " +
        "\"Kode_jenjang_pendidikan\"=?, " +     
        "\"No_penelitian\"=?, " +        
        "\"Jenis_penelitian\"=?, " +
        "\"Kode_pengarang\"=?, " +
        "\"Hasil_penelitian\"=?, " +
        "\"Media_publikasi\"=?, " +
        "\"Penelitian_dilaksanakan_secara\"=?, " +
        "\"Jumlah_pembiayaan\"=?, " +        
        "\"Jenis_pembiayaan\"=?, " +
        "\"Periode_penelitian\"=?, " +
        "\"Judul_penelitian\"=?, " +
        "\"Waktu_pelaksanaan\"=?, " +        
        "\"ID_log_audit\"=? " +
        " WHERE \"Tahun_pelaporan\"=? and " +
        "\"Semester_pelaporan\"=? and " +
        "\"NIM\"=? and "
        + "\"Kode_perguruan_tinggi\"=? and" +
        " \"Kode_program_studi\"=? ";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukUpdate() );
        int hasil = model.updateAktifitasDOsen(query,
            dapatkanNilaiUntukUpdate() );
        if (hasil == 1) {
            query = "select \"Tahun_pelaporan\",\"Semester_pelaporan\",\"NIM\","
                + "\"Kode_jenjang_pendidikan\",\"Kode_perguruan_tinggi\",\"Kode_program_studi\","
                + "\"No_penelitian\",\"Jenis_penelitian\",\"Kode_pengarang\",\"Hasil_penelitian\","
                + "\"Media_publikasi\",\"Penelitian_dilaksanakan_secara\",\"Jumlah_pembiayaan\","
                + "\"Jenis_pembiayaan\",\"Periode_penelitian\",\"Judul_penelitian\",\"Waktu_pelaksanaan\""
                + " from " + namaTabel+" where \"Kode_perguruan_tinggi\"='"+kodept+"'";
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
        String query = "delete from " + namaTabel +  " WHERE \"Tahun_pelaporan\"=? and " +
        "\"Semester_pelaporan\"=? and " +
        "\"NIM\"=? and "
        + "\"Kode_perguruan_tinggi\"=? and" +
        " \"Kode_program_studi\"=? ";
        
        String [] pkDataYgDihapus = dapatkanNilaiUntukDelete();

        int hasil = model.delete(query,
            dapatkanNilaiUntukDelete() );
        if (hasil == 1) {
            query = "select \"Tahun_pelaporan\",\"Semester_pelaporan\",\"NIM\","
                + "\"Kode_jenjang_pendidikan\",\"Kode_perguruan_tinggi\",\"Kode_program_studi\","
                + "\"No_penelitian\",\"Jenis_penelitian\",\"Kode_pengarang\",\"Hasil_penelitian\","
                + "\"Media_publikasi\",\"Penelitian_dilaksanakan_secara\",\"Jumlah_pembiayaan\","
                + "\"Jenis_pembiayaan\",\"Periode_penelitian\",\"Judul_penelitian\",\"Waktu_pelaksanaan\""
                + " from " + namaTabel+" where \"Kode_perguruan_tinggi\"='"+kodept+"'";
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
    private javax.swing.JScrollPane gulungPanelAtribut;
    private javax.swing.JScrollPane gulungTabel;
    private javax.swing.JComboBox hasilpenelitianField;
    private javax.swing.JLabel hasilpenelitianLabel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JComboBox jenispembiayaanField;
    private javax.swing.JLabel jenispembiayaanLabel;
    private javax.swing.JComboBox jenispenelitianField;
    private javax.swing.JLabel jenispenelitianLabel;
    private javax.swing.JTextField judulpenelitianField;
    private javax.swing.JLabel judulpenelitianLabel;
    private javax.swing.JFormattedTextField jumlahpembiayaanField;
    private javax.swing.JLabel jumlahpembiayaanLabel;
    private javax.swing.JComboBox kodejenjangpendidikanField;
    private javax.swing.JLabel kodejenjangpendidikanLabel;
    private javax.swing.JComboBox kodepengarangField;
    private javax.swing.JLabel kodepengarangLabel;
    private javax.swing.JFormattedTextField kodeperguruantinggiField;
    private javax.swing.JLabel kodeperguruantinggiLabel;
    private javax.swing.JFormattedTextField kodeprestasimhsField;
    private javax.swing.JLabel kodeprestasimhsLabel;
    private javax.swing.JComboBox kodeprogramstudiField;
    private javax.swing.JLabel kodeprogramstudiLabel;
    private javax.swing.JComboBox mediapublikasiField;
    private javax.swing.JLabel mediapublikasiLabel;
    private javax.swing.JComboBox nimField;
    private javax.swing.JLabel nimLabel;
    private javax.swing.JFormattedTextField nopenelitianField;
    private javax.swing.JLabel nopenelitianLabel;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JComboBox penelitiandilaksanakansecaraField;
    private javax.swing.JLabel penelitiandilaksanakansecaraLabel;
    private javax.swing.JFormattedTextField periodepenelitianField;
    private javax.swing.JLabel periodepenelitianLabel;
    private javax.swing.JFormattedTextField semesterpelaporanField;
    private javax.swing.JLabel semesterpelaporanLabel;
    private javax.swing.JTable tabel;
    private javax.swing.JFormattedTextField tahunpelaporanField;
    private javax.swing.JLabel tahunpelaporanLabel;
    private javax.swing.JFormattedTextField waktupelaksanaanField;
    private javax.swing.JLabel waktupelaksanaanLabel;
    // End of variables declaration//GEN-END:variables
}
