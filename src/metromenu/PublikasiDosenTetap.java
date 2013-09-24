/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelTransaksiPublikasiDosenTetap;
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
public class PublikasiDosenTetap extends javax.swing.JPanel {
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
    private JTextField jenispenelitian;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    private JTextField kodepengarang;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    private JTextField hasilpenelitian;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo4;
    private JTextField mediapublikasi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo5;
    private JTextField penelitiandilaksanakansecara;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo6;
    private JTextField jenispembiayaan;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo7;
    private JTextField statusvalidasi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo8;
    private JTextField nidnnuk;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo9;
    Model.ModelTransaksiPublikasiDosenTetap model;
    private int kodepublikasidosentetap, idlogaudit;
    private String kueri;

    /**
     * Creates new form PanelHalaman1
     */
    public PublikasiDosenTetap() {    
        initComponents();
    }
    
    public PublikasiDosenTetap(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        model = new ModelTransaksiPublikasiDosenTetap();
        inisialisasiData();
        initAutoComplete();
        IDlogauditField.setVisible(false);
        IDlogauditLabel.setVisible(false);
        IDlogauditLabel1.setVisible(false);
        uuidField.setVisible(false);
    }
    
    private void initPKAutoIncrement() {
        query = "select \"Kode_publikasi_dosen_tetap\" " +
                " from pdpt_dev.\"TRAN_PUBLIKASI_DOSEN_TETAP\" " +
                " order by \"Kode_publikasi_dosen_tetap\" desc";
        kon.selectData(query);     
        String [][] data = kon.dapatkanData();
        if (data[0][0] == null) {
            kodepublikasidosentetap = 1;
        } else {
            kodepublikasidosentetap = Integer.parseInt(data[0][0]);
            kodepublikasidosentetap++;            
        }
        System.out.println("kodepublikasidosentetap: " + kodepublikasidosentetap);
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
                jenispenelitianField, jenispenelitian);        
        String [][] datanya2 = {{"A","Hasil Penelitian"},
            {"B","Non-penelitian"}}; 
        kompletTextFieldCombo2.initDataUtkAutoComplete(datanya2);
        jenispenelitian = kompletTextFieldCombo2.dapatkanTextField();
        jenispenelitianField = kompletTextFieldCombo2.dapatkanComboBox();
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                kodepengarangField, kodepengarang);        
        String [][] datanya3 = {{"A","Penulis Anggota"},
            {"B","Penulis Utama"},
            {"C","Penulis Mandiri"}
        };
        kompletTextFieldCombo3.initDataUtkAutoComplete(datanya3);          
        kodepengarang = kompletTextFieldCombo3.dapatkanTextField();
        kodepengarangField = kompletTextFieldCombo3.dapatkanComboBox();                
        
        kompletTextFieldCombo4 = new KompletTextFieldCombo(kon, 
                hasilpenelitianField, hasilpenelitian);        
        String [][] datanya4 = {{"1","Paper/makalah"},{"2","Buku"},{"3","HKI"},
            {"4","HKI Komersialisasi"}
        };
        kompletTextFieldCombo4.initDataUtkAutoComplete(datanya4);          
        hasilpenelitian = kompletTextFieldCombo4.dapatkanTextField();
        hasilpenelitianField = kompletTextFieldCombo4.dapatkanComboBox();
        
        kompletTextFieldCombo5 = new KompletTextFieldCombo(kon, 
                mediapublikasiField, mediapublikasi);        
        String [][] datanya5 = {{"A","Majalah Populer/Koran"},
            {"B","Seminar Nasional"},{"C","Seminar Internasional"},
            {"D","Prosiding ISBN"},{"E","Jurnal Nasional belum terakreditasi"},
            {"F","Jurnal Nasional terakreditasi"},{"G","Jurnal Internasional"}
        };
        kompletTextFieldCombo5.initDataUtkAutoComplete(datanya5);          
        mediapublikasi = kompletTextFieldCombo5.dapatkanTextField();
        mediapublikasiField = kompletTextFieldCombo5.dapatkanComboBox();
        
        kompletTextFieldCombo6 = new KompletTextFieldCombo(kon, 
                penelitiandilaksanakansecaraField, penelitiandilaksanakansecara);        
        String [][] datanya6 = {{"M","Mandiri"},{"K","Kelompok"}};
        kompletTextFieldCombo6.initDataUtkAutoComplete(datanya6);          
        penelitiandilaksanakansecara = kompletTextFieldCombo6.dapatkanTextField();
        penelitiandilaksanakansecaraField = kompletTextFieldCombo6.dapatkanComboBox();
        
        kompletTextFieldCombo7 = new KompletTextFieldCombo(kon, 
                jenisPembiayaanField, jenispembiayaan);        
        String [][] datanya7 = {{"A", "Biaya Sendiri"},
            {"B","Biaya Instansi Sendiri"},{"C","Lembaga Swasta Kerjasama"},
            {"D","Lembaga Swasta Kompetisi"},
            {"E","Lembaga Pemerintah Kerjasama"},
            {"F","Lembaga Pemerintah Kompetisi"},{"G","Lembaga Internasional"}
        }; 
        kompletTextFieldCombo7.initDataUtkAutoComplete(datanya7);
        jenispembiayaan = kompletTextFieldCombo7.dapatkanTextField();
        jenisPembiayaanField = kompletTextFieldCombo7.dapatkanComboBox();
        
        kompletTextFieldCombo8 = new KompletTextFieldCombo(kon, 
                statusvalidasiField, statusvalidasi);        
        String [][] datanya8 = {{"1", "Belum diverifikasi"},
            {"2","Sudah diverifikasi namun masih terdapat data yang invalid"},
            {"3", "Sudah diverifikasi dan data sudah valid"}}; 
        kompletTextFieldCombo8.initDataUtkAutoComplete(datanya8);        
        statusvalidasi = kompletTextFieldCombo8.dapatkanTextField();
        statusvalidasiField = kompletTextFieldCombo8.dapatkanComboBox();
        
        kompletTextFieldCombo9 = new KompletTextFieldCombo(kon, 
                nidnnukField, nidnnuk);
        query = "select \"NIDN/NUP\", \"Nama_dosen\" from pdpt_dev.\"TMST_DOSEN\" where \"Kode_perguruan_tinggi\"='"+homePage.dapatkanKodePT()+"'";
        kompletTextFieldCombo9.initDataUtkAutoComplete(query);        
        nidnnuk = kompletTextFieldCombo9.dapatkanTextField();
        nidnnukField = kompletTextFieldCombo9.dapatkanComboBox();
    }
    
    private void inisialisasiData() {
        namaTabel = "pdpt_dev.\"TRAN_PUBLIKASI_DOSEN_TETAP\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select \"Tahun_pelaporan\",\"Semester_pelaporan\",\"NIDN/NUP\","
                + "\"Kode_jenjang_pendidikan\",\"Kode_penelitian\",\"Jenis_penelitian\","
                + "\"Kode_pengarang\",\"Hasil_penelitian\",\"Media_publikasi\",\"Penelitian_dilaksanaka_secara\","
                + "\"Jumlah_pembiayaan_thn_1\",\"Jumlah_pembiayaan_thn_2\",\"Jumlah_pembiayaan_thn_3\",\"Jenis_pembiayaan\","
                + "\"Periode_penelitian\",\"judul_penelitian\",\"Kata_kunci\",\"Abstrak\","
                + "\"Waktu_pelaksanaan_penelitian\","
                + "\"Lokasi_penelitian\",\"Status_validasi\" from " + namaTabel;
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
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
        kodepublikasidosentetapField.setText("" + kodepublikasidosentetap);        
        tahunpelaporanField.setText("");
        semesterpelaporanField.setText("");
        nidnnuk.setText("");
        kodejenjangpendidikan.setText("");
        nopenelitianField.setText("");
        jenispenelitian.setText("");
        kodepengarang.setText("");
        hasilpenelitian.setText("");
        mediapublikasi.setText("");
        penelitiandilaksanakansecara.setText("");
        jumlahpembiayaanthn1Field.setText("");
        jumlahpembiayaanthn2Field.setText("");
        jumlahpembiayaanthn3Field.setText("");
        jenispembiayaan.setText("");
        periodepenelitianField.setText("");
        judulPenelitianField.setText("");
        katakunciField.setText("");
        abstrakField.setText("");
        waktupelaksanaanpenelitianField.setText("");
        lokasipenelitianField.setText("");
        statusvalidasi.setText("");
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
//        kodepublikasidosentetapField.setText(tabel.getValueAt(row, 0).toString());        
        tahunpelaporanField.setText(tabel.getValueAt(row, 0).toString());
        semesterpelaporanField.setText(tabel.getValueAt(row, 1).toString());
        nidnnuk.setText(tabel.getValueAt(row, 2).toString());
        kodejenjangpendidikan.setText(tabel.getValueAt(row, 3).toString());        
        nopenelitianField.setText(tabel.getValueAt(row, 4).toString());        
        jenispenelitian.setText(tabel.getValueAt(row, 5).toString());        
        kodepengarang.setText(tabel.getValueAt(row, 6).toString());        
        hasilpenelitian.setText(tabel.getValueAt(row, 7).toString());        
        mediapublikasi.setText(tabel.getValueAt(row, 8).toString());        
        penelitiandilaksanakansecara.setText(tabel.getValueAt(row, 9).toString());
        jumlahpembiayaanthn1Field.setText(tabel.getValueAt(row, 10).toString());
        jumlahpembiayaanthn2Field.setText(tabel.getValueAt(row, 11).toString());
        jumlahpembiayaanthn3Field.setText(tabel.getValueAt(row, 12).toString());
        jenispembiayaan.setText(tabel.getValueAt(row, 13).toString());
        periodepenelitianField.setText(tabel.getValueAt(row, 14).toString());
        judulPenelitianField.setText(tabel.getValueAt(row, 15).toString());
        katakunciField.setText(tabel.getValueAt(row, 16).toString());
        abstrakField.setText(tabel.getValueAt(row, 17).toString());
        waktupelaksanaanpenelitianField.setText(tabel.getValueAt(row, 18).toString());
        lokasipenelitianField.setText(tabel.getValueAt(row, 19).toString());
        statusvalidasi.setText(tabel.getValueAt(row, 20).toString());
        //IDlogauditField.setText(tabel.getValueAt(row, 22).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
//        data[0] = (!kodepublikasidosentetapField.getText().equals("")?kodepublikasidosentetapField.getText():"0");        
        data[0] = (!tahunpelaporanField.getText().equals("    ")?tahunpelaporanField.getText():"0");
        data[1] = (!semesterpelaporanField.getText().equals(" ")?semesterpelaporanField.getText():"0");
        
        String datanidnnukmentah = nidnnuk.getText();
        System.out.println("data nidnnuk mentah: " + datanidnnukmentah);
        String [] datanidnnuk = datanidnnukmentah.split(" ");
        System.out.println("data nidnnuk yg dipilih: " + datanidnnuk[0]);
        data[2] = datanidnnuk[0];
        
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String [] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[3] = datakodejenjangpendidikan[0];
        
        data[4] = (!nopenelitianField.getText().equals("")?nopenelitianField.getText():"0");
        
        String datajenispenelitianmentah = jenispenelitian.getText();
        System.out.println("data jenispenelitian mentah: " + datajenispenelitianmentah);
        String [] datajenispenelitian = datajenispenelitianmentah.split(" ");
        System.out.println("data jenispenelitian yg dipilih: " + datajenispenelitian[0]);
        data[5] = datajenispenelitian[0];
        
        String datakodepengarangmentah = kodepengarang.getText();
        System.out.println("data kodepengarang mentah: " + datakodepengarangmentah);
        String [] datakodepengarang = datakodepengarangmentah.split(" ");
        System.out.println("data kodepengarang yg dipilih: " + datakodepengarang[0]);
        data[6] = datakodepengarang[0];
        
        String datahasilpenelitianmentah = hasilpenelitian.getText();
        System.out.println("data hasilpenelitian mentah: " + datahasilpenelitianmentah);
        String [] datahasilpenelitian = datahasilpenelitianmentah.split(" ");
        System.out.println("data hasilpenelitian yg dipilih: " + datahasilpenelitian[0]);
        data[7] = datahasilpenelitian[0];
        
        String datamediapublikasimentah = mediapublikasi.getText();
        System.out.println("data mediapublikasi mentah: " + datamediapublikasimentah);
        String [] datamediapublikasi = datamediapublikasimentah.split(" ");
        System.out.println("data mediapublikasi yg dipilih: " + datamediapublikasi[0]);
        data[8] = datamediapublikasi[0];
        
        String datapenelitiandilaksanakansecaramentah = penelitiandilaksanakansecara.getText();
        System.out.println("data penelitiandilaksanakansecara mentah: " + datapenelitiandilaksanakansecaramentah);
        String [] datapenelitiandilaksanakansecara = datapenelitiandilaksanakansecaramentah.split(" ");
        System.out.println("data penelitiandilaksanakansecara yg dipilih: " + datapenelitiandilaksanakansecara[0]);
        data[9] = datapenelitiandilaksanakansecara[0];
        
        data[10] = (!jumlahpembiayaanthn1Field.getText().equals("")?jumlahpembiayaanthn1Field.getText():"0");
        data[11] = (!jumlahpembiayaanthn2Field.getText().equals("")?jumlahpembiayaanthn2Field.getText():"0");
        data[12] = (!jumlahpembiayaanthn3Field.getText().equals("")?jumlahpembiayaanthn3Field.getText():"0");
        
        String datajenispembiayaanmentah = jenispembiayaan.getText();
        System.out.println("data jenispembiayaan mentah: " + datajenispembiayaanmentah);
        String [] datajenispembiayaan = datajenispembiayaanmentah.split(" ");
        System.out.println("data jenispembiayaan yg dipilih: " + datajenispembiayaan[0]);
        data[13] = datajenispembiayaan[0];
        
        data[14] = (!periodepenelitianField.getText().equals("      ")?periodepenelitianField.getText():"0");
        data[15] = (!judulPenelitianField.getText().equals("")?judulPenelitianField.getText():"X");
        data[16] = (!katakunciField.getText().equals("")?katakunciField.getText():"X");
        data[17] = (!abstrakField.getText().equals("")?abstrakField.getText():"X");
        data[18] = (!waktupelaksanaanpenelitianField.getText().equals("")?waktupelaksanaanpenelitianField.getText():"0");
        data[19] = (!lokasipenelitianField.getText().equals("")?lokasipenelitianField.getText():"X");        
        
        String datastatusvalidasimentah = statusvalidasi.getText();
        System.out.println("data statusvalidasi mentah: " + datastatusvalidasimentah);
        String [] datastatusvalidasi = datastatusvalidasimentah.split(" ");
        System.out.println("data jenispembiayaan yg dipilih: " + datastatusvalidasi[0]);
        data[20] = datastatusvalidasi[0];
        
        data[21] = "" + idlogaudit;
        data[22] = nidnnuk.getText().charAt(0)+homePage.dapatkanKodePT()+nidnnuk.getText().charAt(1);
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];
        
        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String[] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[0] = datakodejenjangpendidikan[0];

        String datajenispenelitianmentah = jenispenelitian.getText();
        System.out.println("data jenispenelitian mentah: " + datajenispenelitianmentah);
        String[] datajenispenelitian = datajenispenelitianmentah.split(" ");
        System.out.println("data jenispenelitian yg dipilih: " + datajenispenelitian[0]);
        data[1] = datajenispenelitian[0];

        String datakodepengarangmentah = kodepengarang.getText();
        System.out.println("data kodepengarang mentah: " + datakodepengarangmentah);
        String[] datakodepengarang = datakodepengarangmentah.split(" ");
        System.out.println("data kodepengarang yg dipilih: " + datakodepengarang[0]);
        data[2] = datakodepengarang[0];

        String datahasilpenelitianmentah = hasilpenelitian.getText();
        System.out.println("data hasilpenelitian mentah: " + datahasilpenelitianmentah);
        String[] datahasilpenelitian = datahasilpenelitianmentah.split(" ");
        System.out.println("data hasilpenelitian yg dipilih: " + datahasilpenelitian[0]);
        data[3] = datahasilpenelitian[0];

        String datamediapublikasimentah = mediapublikasi.getText();
        System.out.println("data mediapublikasi mentah: " + datamediapublikasimentah);
        String[] datamediapublikasi = datamediapublikasimentah.split(" ");
        System.out.println("data mediapublikasi yg dipilih: " + datamediapublikasi[0]);
        data[4] = datamediapublikasi[0];

        String datapenelitiandilaksanakansecaramentah = penelitiandilaksanakansecara.getText();
        System.out.println("data penelitiandilaksanakansecara mentah: " + datapenelitiandilaksanakansecaramentah);
        String[] datapenelitiandilaksanakansecara = datapenelitiandilaksanakansecaramentah.split(" ");
        System.out.println("data penelitiandilaksanakansecara yg dipilih: " + datapenelitiandilaksanakansecara[0]);
        data[5] = datapenelitiandilaksanakansecara[0];

        data[6] = (!jumlahpembiayaanthn1Field.getText().equals("") ? jumlahpembiayaanthn1Field.getText() : "0");
        data[7] = (!jumlahpembiayaanthn2Field.getText().equals("") ? jumlahpembiayaanthn2Field.getText() : "0");
        data[8] = (!jumlahpembiayaanthn3Field.getText().equals("") ? jumlahpembiayaanthn3Field.getText() : "0");

        String datajenispembiayaanmentah = jenispembiayaan.getText();
        System.out.println("data jenispembiayaan mentah: " + datajenispembiayaanmentah);
        String[] datajenispembiayaan = datajenispembiayaanmentah.split(" ");
        System.out.println("data jenispembiayaan yg dipilih: " + datajenispembiayaan[0]);
        data[9] = datajenispembiayaan[0];

        data[10] = (!periodepenelitianField.getText().equals("      ") ? periodepenelitianField.getText() : "0");
        data[11] = (!judulPenelitianField.getText().equals("") ? judulPenelitianField.getText() : "X");
        data[12] = (!katakunciField.getText().equals("") ? katakunciField.getText() : "X");
        data[13] = (!abstrakField.getText().equals("") ? abstrakField.getText() : "X");
        data[14] = (!waktupelaksanaanpenelitianField.getText().equals("") ? waktupelaksanaanpenelitianField.getText() : "0");
        data[15] = (!lokasipenelitianField.getText().equals("") ? lokasipenelitianField.getText() : "X");
        String datastatusvalidasimentah = statusvalidasi.getText();
        System.out.println("data statusvalidasi mentah: " + datastatusvalidasimentah);
        String[] datastatusvalidasi = datastatusvalidasimentah.split(" ");
        System.out.println("data jenispembiayaan yg dipilih: " + datastatusvalidasi[0]);
        data[16] = datastatusvalidasi[0];

        data[17] = "" + idlogaudit;
        data[18] = uuidField.getText();

        data[19] = (!tahunpelaporanField.getText().equals("    ") ? tahunpelaporanField.getText() : "0");
        data[20] = (!semesterpelaporanField.getText().equals(" ") ? semesterpelaporanField.getText() : "0");

        String datanidnnukmentah = nidnnuk.getText();
        System.out.println("data nidnnuk mentah: " + datanidnnukmentah);
        String[] datanidnnuk = datanidnnukmentah.split(" ");
        System.out.println("data nidnnuk yg dipilih: " + datanidnnuk[0]);
        data[21] = datanidnnuk[0];

        data[22] = (!nopenelitianField.getText().equals("") ? nopenelitianField.getText() : "0");
        
//        data[23] = (!kodepublikasidosentetapField.getText().equals("")?kodepublikasidosentetapField.getText():"0");        
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[1];
        data[0] = (!tahunpelaporanField.getText().equals("    ") ? tahunpelaporanField.getText() : "0");
        data[1] = (!semesterpelaporanField.getText().equals(" ") ? semesterpelaporanField.getText() : "0");

        String datanidnnukmentah = nidnnuk.getText();
        System.out.println("data nidnnuk mentah: " + datanidnnukmentah);
        String[] datanidnnuk = datanidnnukmentah.split(" ");
        System.out.println("data nidnnuk yg dipilih: " + datanidnnuk[0]);
        data[2] = datanidnnuk[0];

        data[3] = (!nopenelitianField.getText().equals("") ? nopenelitianField.getText() : "0");
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
        kodepublikasidosentetapLabel = new javax.swing.JLabel();
        tahunpelaporanLabel = new javax.swing.JLabel();
        semesterpelaporanLabel = new javax.swing.JLabel();
        nidnnupLabel = new javax.swing.JLabel();
        kodejenjangpendidikanLabel = new javax.swing.JLabel();
        nopenelitianLabel = new javax.swing.JLabel();
        jenispenelitianLabel = new javax.swing.JLabel();
        kodepengarangLabel = new javax.swing.JLabel();
        hasilpenelitianLabel = new javax.swing.JLabel();
        mediapublikasiLabel = new javax.swing.JLabel();
        penelitiandilaksanakansecaraLabel = new javax.swing.JLabel();
        jumlahpembiayaanthn1Label = new javax.swing.JLabel();
        jumlahpembiayaanthn2Label = new javax.swing.JLabel();
        jumlahpembiayaanthn3Label = new javax.swing.JLabel();
        jenisPembiayaanLabel = new javax.swing.JLabel();
        periodepenelitianLabel = new javax.swing.JLabel();
        judulPenelitianLabel = new javax.swing.JLabel();
        katakunciLabel = new javax.swing.JLabel();
        abstrakLabel = new javax.swing.JLabel();
        waktupelaksanaanpenelitianLabel = new javax.swing.JLabel();
        lokasipenelitianLabel = new javax.swing.JLabel();
        statusvalidasiLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        IDlogauditLabel1 = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodepublikasidosentetapField = new javax.swing.JFormattedTextField();
        tahunpelaporanField = new javax.swing.JFormattedTextField();
        semesterpelaporanField = new javax.swing.JFormattedTextField();
        nidnnukField = new javax.swing.JComboBox();
        kodejenjangpendidikanField = new javax.swing.JComboBox();
        nopenelitianField = new javax.swing.JFormattedTextField();
        jenispenelitianField = new javax.swing.JComboBox();
        kodepengarangField = new javax.swing.JComboBox();
        hasilpenelitianField = new javax.swing.JComboBox();
        mediapublikasiField = new javax.swing.JComboBox();
        penelitiandilaksanakansecaraField = new javax.swing.JComboBox();
        jumlahpembiayaanthn1Field = new javax.swing.JFormattedTextField();
        jumlahpembiayaanthn2Field = new javax.swing.JFormattedTextField();
        jumlahpembiayaanthn3Field = new javax.swing.JFormattedTextField();
        jenisPembiayaanField = new javax.swing.JComboBox();
        periodepenelitianField = new javax.swing.JFormattedTextField();
        judulPenelitianScrollPane = new javax.swing.JScrollPane();
        judulPenelitianField = new javax.swing.JTextArea();
        katakunciScrollPane = new javax.swing.JScrollPane();
        katakunciField = new javax.swing.JTextArea();
        abstrakScrollPane = new javax.swing.JScrollPane();
        abstrakField = new javax.swing.JTextArea();
        waktupelaksanaanpenelitianField = new javax.swing.JFormattedTextField();
        lokasipenelitianField = new javax.swing.JTextField();
        statusvalidasiField = new javax.swing.JComboBox();
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

        panelLabel.setLayout(new java.awt.GridLayout(24, 0));

        kodepublikasidosentetapLabel.setText("Kode Publikasi Dosen Tetap:");
        panelLabel.add(kodepublikasidosentetapLabel);

        tahunpelaporanLabel.setText("Tahun Pelaporan:");
        panelLabel.add(tahunpelaporanLabel);

        semesterpelaporanLabel.setText("Semester Pelaporan:");
        panelLabel.add(semesterpelaporanLabel);

        nidnnupLabel.setText("NIDN/NUP:");
        panelLabel.add(nidnnupLabel);

        kodejenjangpendidikanLabel.setText("Kode Jenjang Pendidikan:");
        panelLabel.add(kodejenjangpendidikanLabel);

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

        jumlahpembiayaanthn1Label.setText("Jumlah Pembiayaan Thn 1:");
        panelLabel.add(jumlahpembiayaanthn1Label);

        jumlahpembiayaanthn2Label.setText("Jumlah Pembiayaan Thn 2:");
        panelLabel.add(jumlahpembiayaanthn2Label);

        jumlahpembiayaanthn3Label.setText("Jumlah Pembiayaan Thn 3:");
        panelLabel.add(jumlahpembiayaanthn3Label);

        jenisPembiayaanLabel.setText("Jenis Pembiayaan:");
        panelLabel.add(jenisPembiayaanLabel);

        periodepenelitianLabel.setText("Periode Penelitian:");
        panelLabel.add(periodepenelitianLabel);

        judulPenelitianLabel.setText("Judul Penelitian:");
        panelLabel.add(judulPenelitianLabel);

        katakunciLabel.setText("Kata Kunci:");
        panelLabel.add(katakunciLabel);

        abstrakLabel.setText("Abstrak:");
        panelLabel.add(abstrakLabel);

        waktupelaksanaanpenelitianLabel.setText("Waktu Pelaksanaan Penelitian:");
        panelLabel.add(waktupelaksanaanpenelitianLabel);

        lokasipenelitianLabel.setText("Lokasi Penelitian:");
        panelLabel.add(lokasipenelitianLabel);

        statusvalidasiLabel.setText("Status Validasi:");
        panelLabel.add(statusvalidasiLabel);

        IDlogauditLabel.setText("ID Log Audit:");
        panelLabel.add(IDlogauditLabel);

        IDlogauditLabel1.setText("UUID:");
        panelLabel.add(IDlogauditLabel1);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(24, 0));

        kodepublikasidosentetapField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        kodepublikasidosentetapField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodepublikasidosentetapFieldActionPerformed(evt);
            }
        });
        panelTextField.add(kodepublikasidosentetapField);

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

        panelTextField.add(kodejenjangpendidikanField);

        nopenelitianField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(nopenelitianField);

        panelTextField.add(jenispenelitianField);

        panelTextField.add(kodepengarangField);

        panelTextField.add(hasilpenelitianField);

        panelTextField.add(mediapublikasiField);

        panelTextField.add(penelitiandilaksanakansecaraField);

        jumlahpembiayaanthn1Field.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(jumlahpembiayaanthn1Field);

        jumlahpembiayaanthn2Field.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(jumlahpembiayaanthn2Field);

        jumlahpembiayaanthn3Field.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(jumlahpembiayaanthn3Field);

        panelTextField.add(jenisPembiayaanField);

        try {
            periodepenelitianField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(periodepenelitianField);

        judulPenelitianField.setColumns(20);
        judulPenelitianField.setRows(1);
        judulPenelitianScrollPane.setViewportView(judulPenelitianField);

        panelTextField.add(judulPenelitianScrollPane);

        katakunciField.setColumns(20);
        katakunciField.setRows(1);
        katakunciScrollPane.setViewportView(katakunciField);

        panelTextField.add(katakunciScrollPane);

        abstrakField.setColumns(20);
        abstrakField.setRows(1);
        abstrakScrollPane.setViewportView(abstrakField);

        panelTextField.add(abstrakScrollPane);

        waktupelaksanaanpenelitianField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(waktupelaksanaanpenelitianField);
        panelTextField.add(lokasipenelitianField);

        panelTextField.add(statusvalidasiField);

        IDlogauditField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(IDlogauditField);

        uuidField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
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
        " values(null,?,?,?,?,?,?,?,?,?, " + 
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
        "\"Kode_jenjang_pendidikan\"=?, " +
        "\"Jenis_penelitian\"=?, " +
        "\"Kode_pengarang\"=?, " +
        "\"Hasil_penelitian\"=?, " +
        "\"Media_publikasi\"=?, " +
        "\"Penelitian_dilaksanakan_secara\"=?, " +
        "\"Jumlah_pembiayaan_thn_1\"=?, " +
        "\"Jumlah_pembiayaan_thn_2\"=?, " +
        "\"Jumlah_pembiayaan_thn_3\"=?, " +        
        "\"jenis_pembiayaan\"=?, " +
        "\"Periode_penelitian\"=?, " +
        "\"judul_penelitian\"=?, " +
        "\"Kata_kunci\"=?, " +
        "\"Abstrak\"=?, " +
        "\"Waktu_pelaksanaan_penelitian\"=?, " +
        "\"Lokasi_penelitian\"=?, " +        
        "\"Status_validasi\"=?, " +
        "\"ID_log_audit\"=?, " +
        "\"UUID\"=? " +
        " WHERE \"Tahun_pelaporan\"=? and " +
        "\"Semester_pelaporan\"=? and " +
        "\"NIDN/NUP\"=?and  "+
        "\"No_penelitian\"=? "  ;
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
        String query = "delete from " + namaTabel +" WHERE \"Tahun_pelaporan\"=? and " +
        "\"Semester_pelaporan\"=? and " +
        "\"NIDN/NUP\"=?and  "+
        "\"No_penelitian\"=? "  ;
        
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

    private void kodepublikasidosentetapFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodepublikasidosentetapFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kodepublikasidosentetapFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField IDlogauditField;
    private javax.swing.JLabel IDlogauditLabel;
    private javax.swing.JLabel IDlogauditLabel1;
    private javax.swing.JTextArea abstrakField;
    private javax.swing.JLabel abstrakLabel;
    private javax.swing.JScrollPane abstrakScrollPane;
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
    private javax.swing.JComboBox jenisPembiayaanField;
    private javax.swing.JLabel jenisPembiayaanLabel;
    private javax.swing.JComboBox jenispenelitianField;
    private javax.swing.JLabel jenispenelitianLabel;
    private javax.swing.JTextArea judulPenelitianField;
    private javax.swing.JLabel judulPenelitianLabel;
    private javax.swing.JScrollPane judulPenelitianScrollPane;
    private javax.swing.JFormattedTextField jumlahpembiayaanthn1Field;
    private javax.swing.JLabel jumlahpembiayaanthn1Label;
    private javax.swing.JFormattedTextField jumlahpembiayaanthn2Field;
    private javax.swing.JLabel jumlahpembiayaanthn2Label;
    private javax.swing.JFormattedTextField jumlahpembiayaanthn3Field;
    private javax.swing.JLabel jumlahpembiayaanthn3Label;
    private javax.swing.JTextArea katakunciField;
    private javax.swing.JLabel katakunciLabel;
    private javax.swing.JScrollPane katakunciScrollPane;
    private javax.swing.JComboBox kodejenjangpendidikanField;
    private javax.swing.JLabel kodejenjangpendidikanLabel;
    private javax.swing.JComboBox kodepengarangField;
    private javax.swing.JLabel kodepengarangLabel;
    private javax.swing.JFormattedTextField kodepublikasidosentetapField;
    private javax.swing.JLabel kodepublikasidosentetapLabel;
    private javax.swing.JTextField lokasipenelitianField;
    private javax.swing.JLabel lokasipenelitianLabel;
    private javax.swing.JComboBox mediapublikasiField;
    private javax.swing.JLabel mediapublikasiLabel;
    private javax.swing.JComboBox nidnnukField;
    private javax.swing.JLabel nidnnupLabel;
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
    private javax.swing.JComboBox statusvalidasiField;
    private javax.swing.JLabel statusvalidasiLabel;
    private javax.swing.JTable tabel;
    private javax.swing.JFormattedTextField tahunpelaporanField;
    private javax.swing.JLabel tahunpelaporanLabel;
    private javax.swing.JFormattedTextField uuidField;
    private javax.swing.JFormattedTextField waktupelaksanaanpenelitianField;
    private javax.swing.JLabel waktupelaksanaanpenelitianLabel;
    // End of variables declaration//GEN-END:variables
}
