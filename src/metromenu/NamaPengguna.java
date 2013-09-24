/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelMasterNamaPengguna;
import java.awt.Component;
import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class NamaPengguna extends javax.swing.JPanel {
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
    Model.ModelMasterNamaPengguna model;
    private JTextField flagstatususer;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo1;
    private JTextField nidnnuk;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    private JTextField kodeprogramstudi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    
    
    private int kodeusername, idlogaudit;
    private String kueri;

    /**
     * Creates new form PanelHalaman1
     */
    public NamaPengguna() {    
        initComponents();
    }
    
    public NamaPengguna(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        model = new ModelMasterNamaPengguna();
        inisialisasiData();
        initAutoComplete();
    }
    
    private void initPKAutoIncrement() {
        query = "select \"Kode_username\" " +
                " from \"TMST_USERNAME\" " +
                " order by \"Kode_username\" desc";
        kon.selectData(query);     
        String [][] data = kon.dapatkanData();
        if (data[0][0] == null) {
            kodeusername = 1;
        } else {
            kodeusername = Integer.parseInt(data[0][0]);
            kodeusername++;            
        }
        System.out.println("kodeusername: " + kodeusername);
    }
    
    private void initIDLogAudit() {
        query = "select \"ID_log_audit\" " +
                " from \"TREF_LOG_AUDIT\" " +
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
                flagstatususerField, flagstatususer);        
        query = "select \"Kode_role_user\", \"Nama_role\" from \"TREF_ROLE_USER\""; 
        kompletTextFieldCombo1.initDataUtkAutoComplete(query);        
        flagstatususer = kompletTextFieldCombo1.dapatkanTextField();
        flagstatususerField = kompletTextFieldCombo1.dapatkanComboBox();  
        
        kompletTextFieldCombo2 = new KompletTextFieldCombo(kon, 
                nidnnukField, nidnnuk);
        query = "select \"NIDN/NUP\", \"Nama_dosen\" from \"TMST_DOSEN\" ";
        kompletTextFieldCombo2.initDataUtkAutoComplete(query);        
        nidnnuk = kompletTextFieldCombo2.dapatkanTextField();
        nidnnukField = kompletTextFieldCombo2.dapatkanComboBox();
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                kodeprogramstudiField, kodeprogramstudi);        
        query = "select \"Kode_program_studi\", \"Nama_program_studi\" " +
                " from \"TMST_PROGRAM_STUDI\""; 
        kompletTextFieldCombo3.initDataUtkAutoComplete(query);        
        kodeprogramstudi = kompletTextFieldCombo3.dapatkanTextField();
        kodeprogramstudiField = kompletTextFieldCombo3.dapatkanComboBox();
    }
    
    private void inisialisasiData() {
        namaTabel = "\"TMST_USERNAME\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select * from " + namaTabel;
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public NamaPengguna(String [][] data, String [] kolom) {
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
        kodeusernameField.setText("" + kodeusername);
        flagstatususer.setText("");
        nidnnuk.setText("");
        NIPlamaField.setText("");
        NIPbaruField.setText("");
        KodePerguruantinggiField.setText("");
        kodeprogramstudi.setText("");
        kodepegawaiField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        emailField.setText("");
        handphoneField.setText("");
        tanggalmulaiefektifField.setDate(null);
        tanggalakhirefektifField.setDate(null);
        IDlogauditField.setText("");
        uuidField.setText("");
        flagstatususer.requestFocus();
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
        kodeusernameField.setText(tabel.getValueAt(row, 0).toString());
        flagstatususer.setText(tabel.getValueAt(row, 1).toString());
        nidnnuk.setText(tabel.getValueAt(row, 2).toString());
        NIPlamaField.setText(tabel.getValueAt(row, 3).toString());
        NIPbaruField.setText(tabel.getValueAt(row, 4).toString());        
        KodePerguruantinggiField.setText(tabel.getValueAt(row, 5).toString());        
        kodeprogramstudi.setText(tabel.getValueAt(row, 6).toString());        
        kodepegawaiField.setText(tabel.getValueAt(row, 7).toString());        
        usernameField.setText(tabel.getValueAt(row, 8).toString());        
        passwordField.setText(tabel.getValueAt(row, 9).toString());        
        emailField.setText(tabel.getValueAt(row, 10).toString());
        handphoneField.setText(tabel.getValueAt(row, 11).toString());
        
        String strKalendar = tabel.getValueAt(row, 12).toString();
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
        tanggalmulaiefektifField.setCalendar(kalendar);        
        
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
        kalendar.set(tahun, intBulan-1, hari); 
        tanggalakhirefektifField.setCalendar(kalendar);        
        
        //IDlogauditField.setText(tabel.getValueAt(row, 14).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
        data[0] = (!kodeusernameField.getText().equals("")?kodeusernameField.getText():"0");
        
        String datakoderoleusermentah = flagstatususer.getText();
        System.out.println("data koderoleuser mentah: " + datakoderoleusermentah);
        String [] datakoderoleuser = datakoderoleusermentah.split(" ");
        System.out.println("data koderoleuser yg dipilih: " + datakoderoleuser[0]);
        data[1] = datakoderoleuser[0];
        
        String datanidnnukmentah = nidnnuk.getText();
        System.out.println("data nidnnuk mentah: " + datanidnnukmentah);
        String [] datanidnnuk = datanidnnukmentah.split(" ");
        System.out.println("data nidnnuk yg dipilih: " + datanidnnuk[0]);
        data[2] = datanidnnuk[0];
        
        data[3] = (!NIPlamaField.getText().equals("")?NIPlamaField.getText():"0");
        data[4] = (!NIPbaruField.getText().equals("")?NIPbaruField.getText():"0");
        data[5] = (!KodePerguruantinggiField.getText().equals("      ")?KodePerguruantinggiField.getText():"0");
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[6] = datakodeprogramstudi[0];
        
        data[7] = (!kodepegawaiField.getText().equals("")?kodepegawaiField.getText():"0");
        data[8] = (!usernameField.getText().equals("")?usernameField.getText():"");
        
        String dataPassword = new String(passwordField.getPassword());
        System.out.println("dataPassword: " + dataPassword + " || " + passwordField.getPassword().toString()); 
        data[9] = dataPassword;
        
        data[10] = (!emailField.getText().equals("")?emailField.getText():"X");
        data[11] = (!handphoneField.getText().equals("")?handphoneField.getText():"X");
        
        kalendar = tanggalmulaiefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data7 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[12] = (data7!=null?data7:"");
        
        kalendar = tanggalakhirefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data6 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[13] = (data6!=null?data6:"");
        data[14] = "" + idlogaudit;
        data[15] = "" + uuidField.getText();
        
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];                        
                
        String datakoderoleusermentah = flagstatususer.getText();
        System.out.println("data koderoleuser mentah: " + datakoderoleusermentah);
        String [] datakoderoleuser = datakoderoleusermentah.split(" ");
        System.out.println("data koderoleuser yg dipilih: " + datakoderoleuser[0]);
        data[0] = datakoderoleuser[0];
        
        String datanidnnukmentah = nidnnuk.getText();
        System.out.println("data nidnnuk mentah: " + datanidnnukmentah);
        String [] datanidnnuk = datanidnnukmentah.split(" ");
        System.out.println("data nidnnuk yg dipilih: " + datanidnnuk[0]);
        data[1] = datanidnnuk[0];
        
        data[2] = (!NIPlamaField.getText().equals("")?NIPlamaField.getText():"0");
        data[3] = (!NIPbaruField.getText().equals("")?NIPbaruField.getText():"0");
        data[4] = (!KodePerguruantinggiField.getText().equals("      ")?KodePerguruantinggiField.getText():"0");
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[5] = datakodeprogramstudi[0];
        
        data[6] = (!kodepegawaiField.getText().equals("")?kodepegawaiField.getText():"0");
        data[7] = (!usernameField.getText().equals("")?usernameField.getText():"");
        
        String dataPassword = new String(passwordField.getPassword());
        System.out.println("dataPassword: " + dataPassword + " || " + passwordField.getPassword().toString()); 
        data[8] = dataPassword;
        data[9] = (!emailField.getText().equals("")?emailField.getText():"0");
        data[10] = (!handphoneField.getText().equals("")?handphoneField.getText():"X");
        
        kalendar = tanggalmulaiefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data7 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[11] = (data7!=null?data7:"");
        
        kalendar = tanggalakhirefektifField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data6 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[12] = (data6!=null?data6:"");
        data[13] = "" + idlogaudit;
        data[14] = "" + uuidField.getText();
        data[15] = (!kodeusernameField.getText().equals("")?kodeusernameField.getText():"0");
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[1];
        data[0] = kodeusernameField.getText();
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
        kodemengajardosenLabel = new javax.swing.JLabel();
        nidnnukLabel = new javax.swing.JLabel();
        tahunpelaporanLabel = new javax.swing.JLabel();
        semesterpelaporanLabel = new javax.swing.JLabel();
        kodeperguruantinggiLabel = new javax.swing.JLabel();
        kodeprogramstudiLabel = new javax.swing.JLabel();
        kodejenjangstudiLabel = new javax.swing.JLabel();
        kodematakuliahLabel = new javax.swing.JLabel();
        kodekelasLabel = new javax.swing.JLabel();
        jmltatapmukarencanaLabel = new javax.swing.JLabel();
        jmltatapmukarealisasiLabel = new javax.swing.JLabel();
        jenisevaluasiLabel = new javax.swing.JLabel();
        tglmulaiefektifLabel = new javax.swing.JLabel();
        tglakhirefektifLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        IDlogauditLabel1 = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodeusernameField = new javax.swing.JFormattedTextField();
        flagstatususerField = new javax.swing.JComboBox();
        nidnnukField = new javax.swing.JComboBox();
        NIPlamaField = new javax.swing.JFormattedTextField();
        NIPbaruField = new javax.swing.JFormattedTextField();
        KodePerguruantinggiField = new javax.swing.JFormattedTextField();
        kodeprogramstudiField = new javax.swing.JComboBox();
        kodepegawaiField = new javax.swing.JFormattedTextField();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        emailField = new javax.swing.JTextField();
        handphoneField = new javax.swing.JTextField();
        tanggalmulaiefektifField = new com.toedter.calendar.JDateChooser();
        tanggalakhirefektifField = new com.toedter.calendar.JDateChooser();
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

        panelLabel.setLayout(new java.awt.GridLayout(17, 0));

        kodemengajardosenLabel.setText("Kode username :");
        panelLabel.add(kodemengajardosenLabel);

        nidnnukLabel.setText("Flag Status User");
        panelLabel.add(nidnnukLabel);

        tahunpelaporanLabel.setText("NIDN");
        panelLabel.add(tahunpelaporanLabel);

        semesterpelaporanLabel.setText("NIP Lama :");
        panelLabel.add(semesterpelaporanLabel);

        kodeperguruantinggiLabel.setText("NIP Baru :");
        panelLabel.add(kodeperguruantinggiLabel);

        kodeprogramstudiLabel.setText("Kode Perguruan Tinggi:");
        panelLabel.add(kodeprogramstudiLabel);

        kodejenjangstudiLabel.setText("Kode Program Studi:");
        panelLabel.add(kodejenjangstudiLabel);

        kodematakuliahLabel.setText("Kode Pegawai :");
        panelLabel.add(kodematakuliahLabel);

        kodekelasLabel.setText("Username :");
        panelLabel.add(kodekelasLabel);

        jmltatapmukarencanaLabel.setText("Password :");
        panelLabel.add(jmltatapmukarencanaLabel);

        jmltatapmukarealisasiLabel.setText("Email :");
        panelLabel.add(jmltatapmukarealisasiLabel);

        jenisevaluasiLabel.setText("Handphone :");
        panelLabel.add(jenisevaluasiLabel);

        tglmulaiefektifLabel.setText("Tgl Mulai Efektif:");
        panelLabel.add(tglmulaiefektifLabel);

        tglakhirefektifLabel.setText("Tgl Akhir Efektif:");
        panelLabel.add(tglakhirefektifLabel);

        IDlogauditLabel.setText("ID log audit:");
        panelLabel.add(IDlogauditLabel);

        IDlogauditLabel1.setText("UUID :");
        panelLabel.add(IDlogauditLabel1);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(17, 0));

        kodeusernameField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(kodeusernameField);

        panelTextField.add(flagstatususerField);

        nidnnukField.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        panelTextField.add(nidnnukField);

        NIPlamaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        panelTextField.add(NIPlamaField);

        NIPbaruField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        panelTextField.add(NIPbaruField);

        try {
            KodePerguruantinggiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(KodePerguruantinggiField);

        panelTextField.add(kodeprogramstudiField);

        kodepegawaiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(kodepegawaiField);
        panelTextField.add(usernameField);
        panelTextField.add(passwordField);
        panelTextField.add(emailField);
        panelTextField.add(handphoneField);
        panelTextField.add(tanggalmulaiefektifField);
        panelTextField.add(tanggalakhirefektifField);

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
        " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukInsert() );
        int hasil = model.insertAktifitasDOsen(query,
            dapatkanNilaiUntukInsert() );
        if (hasil == 1) {
            query = "select * from " + namaTabel;
            kon.selectData(query);
            tampilkanDataKeTabel(kon.dapatkanData(), ubahLabelKeSentenceCase(kon.dapatkanKolom()));
            
            //insert data ke TREF_LOG_AUDIT
            query = "insert into \"TREF_LOG_AUDIT\" " +
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
        " SET \"Flag_status_user\"=?, " +
        " \"NIDN\"=?, " +
        " \"NIP_lama\"=?, " +
        " \"NIP_baru\"=?, " +
        " \"Kode_perguruan_tinggi\"=?, " +
        " \"Kode_program_studi\"=?, " +
        " \"Kode_pegawai\"=?, " +
        " \"Username\"=?, " +
        " \"Password\"=?, " +
        " \"Email\"=?, " +
        " \"Handphone\"=?, " +
        " \"Tgl_mulai_efektif\"=?, " +        
        " \"Tgl_akhir_efektif\"=?, " +
        " \"ID_log_audit\"=?, " +        
        " \"UUID\"=? " +        
        " WHERE \"Kode_username\"=? ";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukUpdate() );
        int hasil = model.updateAktifitasDOsen(query,
            dapatkanNilaiUntukUpdate() );
        if (hasil == 1) {
            query = "select * from " + namaTabel;
            kon.selectData(query);
            tampilkanDataKeTabel(kon.dapatkanData(), ubahLabelKeSentenceCase(kon.dapatkanKolom()));
            
            //insert data ke TREF_LOG_AUDIT
            query = "insert into \"TREF_LOG_AUDIT\" " +
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
        " \"Kode_username\"=? ";
        
        String [] pkDataYgDihapus = dapatkanNilaiUntukDelete();

        int hasil = model.delete(query,
            dapatkanNilaiUntukDelete() );
        if (hasil == 1) {
            query = "select * from " + namaTabel;
            kon.selectData(query);
            tampilkanDataKeTabel(kon.dapatkanData(), ubahLabelKeSentenceCase(kon.dapatkanKolom()));
            persiapanEntriDataBaru();
            
            //insert data ke TREF_LOG_AUDIT
            query = "insert into \"TREF_LOG_AUDIT\" " +
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
    private javax.swing.JFormattedTextField KodePerguruantinggiField;
    private javax.swing.JFormattedTextField NIPbaruField;
    private javax.swing.JFormattedTextField NIPlamaField;
    private Widget.Button buttonBaru;
    private Widget.Button buttonDelete;
    private Widget.Button buttonFirst;
    private Widget.Button buttonInsert;
    private Widget.Button buttonLast;
    private Widget.Button buttonNext;
    private Widget.Button buttonPrev;
    private Widget.Button buttonUpdate;
    private javax.swing.JTextField emailField;
    private javax.swing.JComboBox flagstatususerField;
    private javax.swing.JScrollPane gulungPanelAtribut;
    private javax.swing.JScrollPane gulungTabel;
    private javax.swing.JTextField handphoneField;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JLabel jenisevaluasiLabel;
    private javax.swing.JLabel jmltatapmukarealisasiLabel;
    private javax.swing.JLabel jmltatapmukarencanaLabel;
    private javax.swing.JLabel kodejenjangstudiLabel;
    private javax.swing.JLabel kodekelasLabel;
    private javax.swing.JLabel kodematakuliahLabel;
    private javax.swing.JLabel kodemengajardosenLabel;
    private javax.swing.JFormattedTextField kodepegawaiField;
    private javax.swing.JLabel kodeperguruantinggiLabel;
    private javax.swing.JComboBox kodeprogramstudiField;
    private javax.swing.JLabel kodeprogramstudiLabel;
    private javax.swing.JFormattedTextField kodeusernameField;
    private javax.swing.JComboBox nidnnukField;
    private javax.swing.JLabel nidnnukLabel;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel semesterpelaporanLabel;
    private javax.swing.JTable tabel;
    private javax.swing.JLabel tahunpelaporanLabel;
    private com.toedter.calendar.JDateChooser tanggalakhirefektifField;
    private com.toedter.calendar.JDateChooser tanggalmulaiefektifField;
    private javax.swing.JLabel tglakhirefektifLabel;
    private javax.swing.JLabel tglmulaiefektifLabel;
    private javax.swing.JTextField usernameField;
    private javax.swing.JFormattedTextField uuidField;
    // End of variables declaration//GEN-END:variables
}
