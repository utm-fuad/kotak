/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelMasterSaranaPT;
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
public class MasterSaranaPT extends javax.swing.JPanel {
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
    private JTextField kepemilikansarana;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    private JTextField flagpenggunasarana;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo4;
    private JTextField kondisi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo5;
    private JTextField saranadosen;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo6;
    private JTextField kapasitasruangdosen;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo7;
    private JTextField statusvalidasi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo8;
    Model.ModelMasterSaranaPT model;
    private int kodesaranapt, idlogaudit;
    private String kueri,kodept;

    /**
     * Creates new form PanelHalaman1
     */
    public MasterSaranaPT() {    
        initComponents();
    }
    
    public MasterSaranaPT(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        model = new ModelMasterSaranaPT();
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        kodept = homePage.dapatkanKodePT();
        inisialisasiData();
        initAutoComplete();
        buttonBaru.setEnabled(false);
        buttonInsert.setEnabled(false);
        buttonDelete.setEnabled(false);
        buttonUpdate.setEnabled(false);
    }
    
    private void initPKAutoIncrement() {
        query = "select \"Kode_sarana_pt\" " +
                " from pdpt_dev.\"TMST_SARANA_PT\" " +
                " order by \"Kode_sarana_pt\" desc";
        kon.selectData(query);     
        String [][] data = kon.dapatkanData();
        kodesaranapt = Integer.parseInt(data[0][0]);
        kodesaranapt++;
        System.out.println("kodesaranapt: " + kodesaranapt);
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
                " from pdpt_dev.\"TMST_PROGRAM_STUDI\" where \"Kode_perguruan_tinggi\"='"+kodept+"'"; 
        kompletTextFieldCombo2.initDataUtkAutoComplete(query);        
        kodeprogramstudi = kompletTextFieldCombo2.dapatkanTextField();
        kodeprogramstudiField = kompletTextFieldCombo2.dapatkanComboBox();
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                kepemilikansaranaField, kepemilikansarana);        
        String [][] datanya3 = {{"SD", "Milik Sendiri"},
            {"SW","Sewa/Kontrak"}, 
            {"BR","Milik perguruan tinggi untuk dipakai bersama"}}; 
        kompletTextFieldCombo3.initDataUtkAutoComplete(datanya3);          
        kepemilikansarana = kompletTextFieldCombo3.dapatkanTextField();
        kepemilikansaranaField = kompletTextFieldCombo3.dapatkanComboBox();                
        
        kompletTextFieldCombo4 = new KompletTextFieldCombo(kon, 
                flagpenggunasaranaField, flagpenggunasarana);        
        String [][] datanya4 = {{"1", "Sarana yang dimiliki"},
            {"2","Sarana yang digunakan"}}; 
        kompletTextFieldCombo4.initDataUtkAutoComplete(datanya4);          
        flagpenggunasarana = kompletTextFieldCombo4.dapatkanTextField();
        flagpenggunasaranaField = kompletTextFieldCombo4.dapatkanComboBox();
        
        kompletTextFieldCombo5 = new KompletTextFieldCombo(kon, 
                kondisiField, kondisi);        
        String [][] datanya5 = {{"A", "Rusak"},
            {"B","Tidak Rusak"},{"C","Baik"}}; 
        kompletTextFieldCombo5.initDataUtkAutoComplete(datanya5);
        kondisi = kompletTextFieldCombo5.dapatkanTextField();
        kondisiField = kompletTextFieldCombo5.dapatkanComboBox();
        
        kompletTextFieldCombo6 = new KompletTextFieldCombo(kon, 
                saranadosenField, saranadosen);        
        String [][] datanya6 = {{"1", "Sarana dosen tetap"},
            {"2","Sarana dosen tidak tetap"}}; 
        kompletTextFieldCombo6.initDataUtkAutoComplete(datanya6);
        saranadosen = kompletTextFieldCombo6.dapatkanTextField();
        saranadosenField = kompletTextFieldCombo6.dapatkanComboBox();
        
        kompletTextFieldCombo7 = new KompletTextFieldCombo(kon, 
                kapasitasruangdosenField, kapasitasruangdosen);        
        String [][] datanya7 = {{"1", "Satu ruangan untuk setiap dosen"},
            {"2","Satu ruangan untuk dua orang dosen"},
            {"3","Satu ruangan untuk dua atau tiga orang dosen"}, 
            {"4","Ruang bersama untuk dosen"}}; 
        kompletTextFieldCombo7.initDataUtkAutoComplete(datanya7);
        kapasitasruangdosen = kompletTextFieldCombo7.dapatkanTextField();
        kapasitasruangdosenField = kompletTextFieldCombo7.dapatkanComboBox();
        
        kompletTextFieldCombo8 = new KompletTextFieldCombo(kon, 
                statusvalidasiField, statusvalidasi);        
        String [][] datanya8 = {{"1", "Belum Diverifikasi"},
            {"2","Sudah diverifikasi namun masih terdapat data yang invalid"},
            {"3","Sudah diverifikasi dan data sudah valid"}}; 
        kompletTextFieldCombo8.initDataUtkAutoComplete(datanya8);
        statusvalidasi = kompletTextFieldCombo8.dapatkanTextField();
        statusvalidasiField = kompletTextFieldCombo8.dapatkanComboBox();
    }
    
    private void inisialisasiData() {
        namaTabel = "pdpt_dev.\"TMST_SARANA_PT\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select * from " + namaTabel+" where \"Kode_perguruan_tinggi\"='"+kodept+"'";
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public MasterSaranaPT(String [][] data, String [] kolom) {
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
        kodesaranaptField.setText("" + kodesaranapt);
        tahunpelaporanField.setText("");
        semesterpelapporanField.setText("");
        //kodeperguruantinggiField.setText("" + homePage.dapatkanKodePT());//utk pdpt pt
        kodeperguruantinggiField.setText("");//utk pdpt dikti
        kodeprogramstudi.setText("");
        kodejenjangpendidikan.setText("");
        namasaranaField.setText("");
        fungsisaranaField.setText("");
        kepemilikansarana.setText("");
        jmlsaranaField.setText("");
        luassaranaField.setText("");
        flagpenggunasarana.setText("");
        kondisi.setText("");
        penggunaansaranamhsField.setText("");
        penggunaanjamsaranaField.setText("");
        saranadosen.setText("");
        kapasitasruangdosen.setText("");
        statusvalidasi.setText("");
        IDlogauditField.setText("");
        
        tahunpelaporanField.requestFocus();
    }
     
    public void tampilkanDataKeTabel(String [][] data, String [] kolom) {
        //kosongkan tabel terlebih dulu
        int row = tabel.getRowCount();
        for (int i = 0; i < row; i++) {
            ((DefaultTableModel) tabel.getModel()).removeRow(0);
            
        }
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
        kodesaranaptField.setText(tabel.getValueAt(row, 0).toString());
        tahunpelaporanField.setText(tabel.getValueAt(row, 1).toString());
        semesterpelapporanField.setText(tabel.getValueAt(row, 2).toString());
        kodeperguruantinggiField.setText(tabel.getValueAt(row, 3).toString());
        kodeprogramstudi.setText(tabel.getValueAt(row, 4).toString());
        kodejenjangpendidikan.setText(tabel.getValueAt(row, 5).toString());
        namasaranaField.setText(tabel.getValueAt(row, 6).toString());
        fungsisaranaField.setText(tabel.getValueAt(row, 7).toString());
        kepemilikansarana.setText(tabel.getValueAt(row, 8).toString());
        jmlsaranaField.setText(tabel.getValueAt(row, 9).toString());
        luassaranaField.setText(tabel.getValueAt(row, 10).toString());
        flagpenggunasarana.setText(tabel.getValueAt(row, 11).toString());
        kondisi.setText(tabel.getValueAt(row, 12).toString());
        penggunaansaranamhsField.setText(tabel.getValueAt(row, 13).toString());
        penggunaanjamsaranaField.setText(tabel.getValueAt(row, 14).toString());
        saranadosen.setText(tabel.getValueAt(row, 15).toString());
        kapasitasruangdosen.setText(tabel.getValueAt(row, 16).toString());
        statusvalidasi.setText(tabel.getValueAt(row, 17).toString());
        //IDlogauditField.setText(tabel.getValueAt(row, 18).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
        data[0] = kodesaranaptField.getText();
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
        
        data[6] = namasaranaField.getText();
        data[7] = fungsisaranaField.getText();
        
        String datakepemilikansaranamentah = kepemilikansarana.getText();
        System.out.println("data kepemilikansarana mentah: " + datakepemilikansaranamentah);
        String [] datakepemilikansarana = datakepemilikansaranamentah.split(" ");
        System.out.println("data kepemilikansarana yg dipilih: " + datakepemilikansarana[0]);
        data[8] = datakepemilikansarana[0];
        
        data[9] = (!jmlsaranaField.getText().equals("")?jmlsaranaField.getText():"0");
        data[10] = (!luassaranaField.getText().equals("")?luassaranaField.getText():"0");
        
        String dataflagpenggunasaranamentah = flagpenggunasarana.getText();
        System.out.println("data flagpenggunasarana mentah: " + dataflagpenggunasaranamentah);
        String [] dataflagpenggunasarana = dataflagpenggunasaranamentah.split(" ");
        System.out.println("data flagpenggunasarana yg dipilih: " + dataflagpenggunasarana[0]);
        data[11] = dataflagpenggunasarana[0];
        
        String datakondisimentah = kondisi.getText();
        System.out.println("data kondisi mentah: " + datakondisimentah);
        String [] datakondisi = datakondisimentah.split(" ");
        System.out.println("data kondisi yg dipilih: " + datakondisi[0]);
        data[12] = datakondisi[0];
        
        data[13] = (!penggunaansaranamhsField.getText().equals("")?penggunaansaranamhsField.getText():"0");
        data[14] = (!penggunaanjamsaranaField.getText().equals("")?penggunaanjamsaranaField.getText():"0");
        
        String datasaranadosenmentah = saranadosen.getText();
        System.out.println("data saranadosen mentah: " + datasaranadosenmentah);
        String [] datasaranadosen = datasaranadosenmentah.split(" ");
        System.out.println("data saranadosen yg dipilih: " + datasaranadosen[0]);
        data[15] = datasaranadosen[0];
        
        String datakapasitasruangdosenmentah = kapasitasruangdosen.getText();
        System.out.println("data kapasitasruangdosen mentah: " + datakapasitasruangdosenmentah);
        String [] datakapasitasruangdosen = datakapasitasruangdosenmentah.split(" ");
        System.out.println("data kapasitasruangdosen yg dipilih: " + datakapasitasruangdosen[0]);
        data[16] = datakapasitasruangdosen[0];
        
        String datastatusvalidasimentah = statusvalidasi.getText();
        System.out.println("data statusvalidasi mentah: " + datastatusvalidasimentah);
        String [] datastatusvalidasi = datastatusvalidasimentah.split(" ");
        System.out.println("data statusvalidasi yg dipilih: " + datastatusvalidasi[0]);
        data[17] = datastatusvalidasi[0];
        
        data[18] = "" + idlogaudit;
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];                
        data[0] = tahunpelaporanField.getText();
        data[1] = semesterpelapporanField.getText();
        data[2] = kodeperguruantinggiField.getText();        
        data[3] = kodeprogramstudi.getText();
        data[4] = kodejenjangpendidikan.getText();
        data[5] = namasaranaField.getText();
        data[6] = fungsisaranaField.getText();
        data[7] = kepemilikansarana.getText();
        data[8] = jmlsaranaField.getText();
        data[9] = luassaranaField.getText();
        data[10] = flagpenggunasarana.getText();
        data[11] = kondisi.getText();
        data[12] = penggunaansaranamhsField.getText();
        data[13] = penggunaanjamsaranaField.getText();
        data[14] = saranadosen.getText();
        data[15] = kapasitasruangdosen.getText();
        data[16] = statusvalidasi.getText();        
        data[17] = "" + idlogaudit;
        data[18] = kodesaranaptField.getText();
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[1];
        data[0] = kodesaranaptField.getText();
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
        kodesaranaptLabel = new javax.swing.JLabel();
        tahunpelaporanLabel = new javax.swing.JLabel();
        semesterpelaporanLabel = new javax.swing.JLabel();
        kodeperguruantinggiLabel = new javax.swing.JLabel();
        kodeprogramstudiLabel = new javax.swing.JLabel();
        kodejenjangpendidikanLabel = new javax.swing.JLabel();
        namasaranaLabel = new javax.swing.JLabel();
        fungsisaranaLabel = new javax.swing.JLabel();
        kepemilikansaranaLabel = new javax.swing.JLabel();
        jmlsaranaLabel = new javax.swing.JLabel();
        luassaranaLabel = new javax.swing.JLabel();
        flagpenggunasaranaLabel = new javax.swing.JLabel();
        kondisiLabel = new javax.swing.JLabel();
        penggunaansaranamhsLabel = new javax.swing.JLabel();
        penggunaanjamsaranaLabel = new javax.swing.JLabel();
        saranadosenLabel = new javax.swing.JLabel();
        kapasitasruangdosenLabel = new javax.swing.JLabel();
        statusvalidasiLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodesaranaptField = new javax.swing.JFormattedTextField();
        tahunpelaporanField = new javax.swing.JFormattedTextField();
        semesterpelapporanField = new javax.swing.JFormattedTextField();
        kodeperguruantinggiField = new javax.swing.JFormattedTextField();
        kodeprogramstudiField = new javax.swing.JComboBox();
        kodejenjangpendidikanField = new javax.swing.JComboBox();
        namasaranaField = new javax.swing.JTextField();
        fungsisaranaField = new javax.swing.JTextField();
        kepemilikansaranaField = new javax.swing.JComboBox();
        jmlsaranaField = new javax.swing.JFormattedTextField();
        luassaranaField = new javax.swing.JFormattedTextField();
        flagpenggunasaranaField = new javax.swing.JComboBox();
        kondisiField = new javax.swing.JComboBox();
        penggunaansaranamhsField = new javax.swing.JFormattedTextField();
        penggunaanjamsaranaField = new javax.swing.JFormattedTextField();
        saranadosenField = new javax.swing.JComboBox();
        kapasitasruangdosenField = new javax.swing.JComboBox();
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

        panelLabel.setLayout(new java.awt.GridLayout(19, 0));

        kodesaranaptLabel.setText("Kode Sarana PT:");
        panelLabel.add(kodesaranaptLabel);

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

        namasaranaLabel.setText("Nama Sarana:");
        panelLabel.add(namasaranaLabel);

        fungsisaranaLabel.setText("Fungsi Sarana:");
        panelLabel.add(fungsisaranaLabel);

        kepemilikansaranaLabel.setText("Kepemilikan Sarana:");
        panelLabel.add(kepemilikansaranaLabel);

        jmlsaranaLabel.setText("Jml Sarana:");
        panelLabel.add(jmlsaranaLabel);

        luassaranaLabel.setText("Luas Sarana:");
        panelLabel.add(luassaranaLabel);

        flagpenggunasaranaLabel.setText("Flag Pengguna Sarana:");
        panelLabel.add(flagpenggunasaranaLabel);

        kondisiLabel.setText("Kondisi:");
        panelLabel.add(kondisiLabel);

        penggunaansaranamhsLabel.setText("Penggunaan Sarana Mhs:");
        panelLabel.add(penggunaansaranamhsLabel);

        penggunaanjamsaranaLabel.setText("Penggunaan Jam Sarana:");
        panelLabel.add(penggunaanjamsaranaLabel);

        saranadosenLabel.setText("Sarana Dosen:");
        panelLabel.add(saranadosenLabel);

        kapasitasruangdosenLabel.setText("Kapasitas Ruang Dosen:");
        panelLabel.add(kapasitasruangdosenLabel);

        statusvalidasiLabel.setText("Status Validasi:");
        panelLabel.add(statusvalidasiLabel);

        IDlogauditLabel.setText("ID Log Audit:");
        panelLabel.add(IDlogauditLabel);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(19, 0));

        kodesaranaptField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("########"))));
        panelTextField.add(kodesaranaptField);

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
        panelTextField.add(namasaranaField);
        panelTextField.add(fungsisaranaField);

        panelTextField.add(kepemilikansaranaField);

        jmlsaranaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        panelTextField.add(jmlsaranaField);

        luassaranaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        panelTextField.add(luassaranaField);

        panelTextField.add(flagpenggunasaranaField);

        panelTextField.add(kondisiField);

        penggunaansaranamhsField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        panelTextField.add(penggunaansaranamhsField);

        penggunaanjamsaranaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        panelTextField.add(penggunaanjamsaranaField);

        panelTextField.add(saranadosenField);

        panelTextField.add(kapasitasruangdosenField);

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
    
    private void buttonBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBaruActionPerformed
        // TODO add your handling code here:
        persiapanEntriDataBaru();
    }//GEN-LAST:event_buttonBaruActionPerformed

    private void buttonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInsertActionPerformed
        // TODO add your handling code here:
        initIDLogAudit();
        String query = "insert into " + namaTabel + " " +
        " values(?,?,?,?,?,?,?,?,?,?, " +
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
        "\"Nama_sarana\"=?, " +
        "\"Fungsi_sarana\"=?, " +
        "\"Kepemilikan_sarana\"=?, " +
        "\"Jml_sarana\" = ?, " +
        "\"Luas_sarana\" = ?, " +
        "\"Flag_pengguna_sarana\" = ?, " +
        "\"Kondisi\" = ?, " +
        "\"Penggunaan_sarana_mhs\" = ?, " +
        "\"Penggunaan_jam_sarana\" = ?, " +
        "\"Sarana_dosen\" = ?, " +
        "\"Kapasitas_ruang_dosen\" = ?, " +
        "\"Status_validasi\" = ?, " +        
        "\"ID_log_audit\"=? " +
        " WHERE \"Kode_sarana_pt\"=? ";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukUpdate() );
         int hasil = model.updateAktifitasDOsen(query,
            dapatkanNilaiUntukUpdate() );
        if (hasil == 1) {
            query = "select * from " + namaTabel;
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
        String query = "delete from " + namaTabel + " WHERE " +
        " \"Kode_sarana_pt\"=? ";
        
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
    private Widget.Button buttonBaru;
    private Widget.Button buttonDelete;
    private Widget.Button buttonFirst;
    private Widget.Button buttonInsert;
    private Widget.Button buttonLast;
    private Widget.Button buttonNext;
    private Widget.Button buttonPrev;
    private Widget.Button buttonUpdate;
    private javax.swing.JComboBox flagpenggunasaranaField;
    private javax.swing.JLabel flagpenggunasaranaLabel;
    private javax.swing.JTextField fungsisaranaField;
    private javax.swing.JLabel fungsisaranaLabel;
    private javax.swing.JScrollPane gulungPanelAtribut;
    private javax.swing.JScrollPane gulungTabel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JFormattedTextField jmlsaranaField;
    private javax.swing.JLabel jmlsaranaLabel;
    private javax.swing.JComboBox kapasitasruangdosenField;
    private javax.swing.JLabel kapasitasruangdosenLabel;
    private javax.swing.JComboBox kepemilikansaranaField;
    private javax.swing.JLabel kepemilikansaranaLabel;
    private javax.swing.JComboBox kodejenjangpendidikanField;
    private javax.swing.JLabel kodejenjangpendidikanLabel;
    private javax.swing.JFormattedTextField kodeperguruantinggiField;
    private javax.swing.JLabel kodeperguruantinggiLabel;
    private javax.swing.JComboBox kodeprogramstudiField;
    private javax.swing.JLabel kodeprogramstudiLabel;
    private javax.swing.JFormattedTextField kodesaranaptField;
    private javax.swing.JLabel kodesaranaptLabel;
    private javax.swing.JComboBox kondisiField;
    private javax.swing.JLabel kondisiLabel;
    private javax.swing.JFormattedTextField luassaranaField;
    private javax.swing.JLabel luassaranaLabel;
    private javax.swing.JTextField namasaranaField;
    private javax.swing.JLabel namasaranaLabel;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JFormattedTextField penggunaanjamsaranaField;
    private javax.swing.JLabel penggunaanjamsaranaLabel;
    private javax.swing.JFormattedTextField penggunaansaranamhsField;
    private javax.swing.JLabel penggunaansaranamhsLabel;
    private javax.swing.JComboBox saranadosenField;
    private javax.swing.JLabel saranadosenLabel;
    private javax.swing.JLabel semesterpelaporanLabel;
    private javax.swing.JFormattedTextField semesterpelapporanField;
    private javax.swing.JComboBox statusvalidasiField;
    private javax.swing.JLabel statusvalidasiLabel;
    private javax.swing.JTable tabel;
    private javax.swing.JFormattedTextField tahunpelaporanField;
    private javax.swing.JLabel tahunpelaporanLabel;
    // End of variables declaration//GEN-END:variables
}
