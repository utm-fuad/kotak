/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelKinerjaDosen;
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
public class KinerjaDosen extends javax.swing.JPanel {
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
    Model.ModelKinerjaDosen model;
    private JTextField kodeprogramstudi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo1;
    private JTextField kategorievaluasi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    private JTextField statusvalidasi;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;    
    private JTextField nidnnuk;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo4;
    private JTextField nidnasesor1;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo5;
    private JTextField nidnasesor2;  
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo6;
    
    private int idlogaudit;
    private String kueri;
    String kodept;
    /**
     * Creates new form PanelHalaman1
     */
    public KinerjaDosen() {    
        initComponents();
    }
    
    public KinerjaDosen(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        model = new ModelKinerjaDosen();
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        kodept = this.homePage.dapatkanKodePT();
        inisialisasiData();
        initAutoComplete();
        kodeperguruantinggiField.setText(kodept);
    }
    
    private void initIDLogAudit() {
        query = "\"select ID_log_audit\" " +
                " pdpt_dev.\"from TREF_LOG_AUDIT\" " +
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
                kategorievaluasiField, kategorievaluasi);        
        String [][] datanya2 = {{"1","Bidang Pendidikan"},
            {"2","Penelitian dan Pengembangan Ilmu"},
            {"3","Pengabdian kepada Masyarakat"},
            {"4","Penunjang Tridharma Perguruan Tinggi"},
            {"5","Kewajiban Khusus Profesor"}}; 
        kompletTextFieldCombo2.initDataUtkAutoComplete(datanya2);
        kategorievaluasi = kompletTextFieldCombo2.dapatkanTextField();
        kategorievaluasiField = kompletTextFieldCombo2.dapatkanComboBox();
        
        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon, 
                statusvalidasiField, statusvalidasi);        
        String [][] datanya3 = {{"1", "Belum diverifikasi"},
            {"2","Sudah diverifikasi namun masih terdapat data yang invalid"},
            {"3", "Sudah diverifikasi dan data sudah valid"}}; 
        kompletTextFieldCombo3.initDataUtkAutoComplete(datanya3);        
        statusvalidasi = kompletTextFieldCombo3.dapatkanTextField();
        statusvalidasiField = kompletTextFieldCombo3.dapatkanComboBox();
        
        kompletTextFieldCombo4 = new KompletTextFieldCombo(kon, 
                nidnnukField, nidnnuk);
        query = "select \"NIDN\", \"Nama_dosen\" from pdpt_dev.\"TMST_DOSEN\" where \"Kode_perguruan_tinggi\"='"+kodept+"'"; 
        kompletTextFieldCombo4.initDataUtkAutoComplete(query);        
        nidnnuk = kompletTextFieldCombo4.dapatkanTextField();
        nidnnukField = kompletTextFieldCombo4.dapatkanComboBox();
        
        kompletTextFieldCombo5 = new KompletTextFieldCombo(kon, 
                NIDNassesor1Field, nidnasesor1);
        query = "select \"NIDN\", \"Nama_dosen\" from pdpt_dev.\"TMST_DOSEN\" where \"Kode_perguruan_tinggi\"='"+kodept+"'"; 
        kompletTextFieldCombo5.initDataUtkAutoComplete(query);        
        nidnasesor1 = kompletTextFieldCombo5.dapatkanTextField();
        NIDNassesor1Field = kompletTextFieldCombo5.dapatkanComboBox();
        
        kompletTextFieldCombo6 = new KompletTextFieldCombo(kon, 
                NIDNassesor2Field, nidnasesor2);
        query = "select \"NIDN\", \"Nama_dosen\" from pdpt_dev.\"TMST_DOSEN\" where \"Kode_perguruan_tinggi\"='"+kodept+"'"; 
        kompletTextFieldCombo6.initDataUtkAutoComplete(query);        
        nidnasesor2 = kompletTextFieldCombo6.dapatkanTextField();
        NIDNassesor2Field = kompletTextFieldCombo6.dapatkanComboBox();
    }
    
    private void inisialisasiData() {
        namaTabel = "pdpt_dev.\"TRAN_KINERJA_DOSEN\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select * from " + namaTabel;
        kon.selectData(query);     
        jmlKolom = kon.dapatkanJumlahKolom();
        String [][] data = kon.dapatkanData();
        String [] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
    }
    
    public KinerjaDosen(String [][] data, String [] kolom) {
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
        kodekinerjadosenField.setText("");
        nidnnuk.setText("");
        NIPlamaField.setText("");
        NIPbaruField.setText("");        
        nomordosenField.setText("");
        kodeperguruantinggiField.setText("" + homePage.dapatkanKodePT());
        kodeprogramstudi.setText("");
        tahunpelaporanField.setText("");        
        semesterpelaporanField.setText("");
        nidnasesor1.setText("");
        nidnasesor2.setText("");
        kategorievaluasi.setText("");
        jeniskegiatanField.setText("");
        buktipenugasanField.setText("");
        SKSbebankerjaField.setText("");
        masapelaksanaantugasawalField.setDate(null);
        masapelaksanaantugasakhirField.setDate(null);
        buktidokumenkinerjaField.setText("");
        persentasecapaiankinerjaField.setText("");
        SKScapaiankinerjaField.setText("");
        penilaianasesorField.setText("");        
        rekomendasiasesorField.setText("");        
        statusvalidasi.setText("");
        IDlogauditField.setText("");
        
        kodekinerjadosenField.requestFocus();
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
        kodekinerjadosenField.setText(tabel.getValueAt(row, 0).toString());
        nidnnuk.setText(tabel.getValueAt(row, 1).toString());
        NIPlamaField.setText(tabel.getValueAt(row, 2).toString());
        NIPbaruField.setText(tabel.getValueAt(row, 3).toString());        
        nomordosenField.setText(tabel.getValueAt(row, 4).toString());
        kodeperguruantinggiField.setText(tabel.getValueAt(row, 5).toString());
        kodeprogramstudi.setText(tabel.getValueAt(row, 6).toString());
        tahunpelaporanField.setText(tabel.getValueAt(row, 7).toString());
        semesterpelaporanField.setText(tabel.getValueAt(row, 8).toString());
        nidnasesor1.setText(tabel.getValueAt(row, 9).toString());
        nidnasesor1.setText(tabel.getValueAt(row, 10).toString());
        kategorievaluasi.setText(tabel.getValueAt(row, 11).toString());
        jeniskegiatanField.setText(tabel.getValueAt(row, 12).toString());
        buktipenugasanField.setText(tabel.getValueAt(row, 13).toString());
        SKSbebankerjaField.setText(tabel.getValueAt(row, 14).toString());
                
        String strKalendar = tabel.getValueAt(row, 15).toString();
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
        masapelaksanaantugasawalField.setCalendar(kalendar);        
        
        strKalendar = tabel.getValueAt(row, 16).toString();
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
        masapelaksanaantugasakhirField.setCalendar(kalendar);
        
        buktidokumenkinerjaField.setText(tabel.getValueAt(row, 17).toString()); //Conversion to String failed
        persentasecapaiankinerjaField.setText(tabel.getValueAt(row, 18).toString());
        SKScapaiankinerjaField.setText(tabel.getValueAt(row, 19).toString());
        penilaianasesorField.setText(tabel.getValueAt(row, 20).toString());
        rekomendasiasesorField.setText(tabel.getValueAt(row, 21).toString());
        statusvalidasi.setText(tabel.getValueAt(row, 22).toString());
        //IDlogauditField.setText(tabel.getValueAt(row, 23).toString());
    }
    
    public String [] dapatkanNilaiUntukInsert() {
        String [] data = new String[jmlKolom];
        data[0] = (!kodekinerjadosenField.getText().equals("")?kodekinerjadosenField.getText():"0");
        
        String datanidnnukmentah = nidnnuk.getText();
        System.out.println("data nidnnuk mentah: " + datanidnnukmentah);
        String [] datanidnnuk = datanidnnukmentah.split(" ");
        System.out.println("data nidnnuk yg dipilih: " + datanidnnuk[0]);
        data[1] = datanidnnuk[0];
        
        data[2] = (!NIPlamaField.getText().equals("")?NIPlamaField.getText():"0");
        data[3] = (!NIPbaruField.getText().equals("")?NIPbaruField.getText():"0");
        data[4] = (!nomordosenField.getText().equals("")?nomordosenField.getText():"0");
        data[5] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[6] = datakodeprogramstudi[0];
        
        data[7] = (!tahunpelaporanField.getText().equals("    ")?tahunpelaporanField.getText():"0000");
        data[8] = (!semesterpelaporanField.getText().equals(" ")?semesterpelaporanField.getText():"0");
        
        String datanidnasesor1mentah = nidnasesor1.getText();
        System.out.println("data nidnasesor1 mentah: " + datanidnasesor1mentah);
        String [] datanidnasesor1 = datanidnasesor1mentah.split(" ");
        System.out.println("data nidnasesor1 yg dipilih: " + datanidnasesor1[0]);
        data[9] = datanidnasesor1[0];
        
        String datanidnasesor2mentah = nidnasesor2.getText();
        System.out.println("data nidnasesor2 mentah: " + datanidnasesor2mentah);
        String [] datanidnasesor2 = datanidnasesor2mentah.split(" ");
        System.out.println("data nidnasesor2 yg dipilih: " + datanidnasesor2[0]);
        data[10] = datanidnasesor2[0];
        
        String datakategorievaluasimentah = kategorievaluasi.getText();
        System.out.println("data kategorievaluasi mentah: " + datakategorievaluasimentah);
        String [] datakategorievaluasi = datakategorievaluasimentah.split(" ");
        System.out.println("data kategorievaluasi yg dipilih: " + datakategorievaluasi[0]);
        data[11] = datakategorievaluasi[0];
        
        data[12] = (!jeniskegiatanField.getText().equals("")?jeniskegiatanField.getText():"X");
        data[13] = (!buktipenugasanField.getText().equals("")?buktipenugasanField.getText():"X");
        data[14] = (!SKSbebankerjaField.getText().equals("")?SKSbebankerjaField.getText():"0");
        
        kalendar = masapelaksanaantugasawalField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data7 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[15] = (data7!=null?data7:"");
        
        kalendar = masapelaksanaantugasakhirField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data16 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[16] = (data16!=null?data16:"");
        
        data[17] = (!buktidokumenkinerjaField.getText().equals("")?buktidokumenkinerjaField.getText():null);
        System.out.println("persentase capaian kinerja: " + persentasecapaiankinerjaField.getText());
        data[18] = (!persentasecapaiankinerjaField.getText().equals("")?persentasecapaiankinerjaField.getText():"0");
        data[19] = (!SKScapaiankinerjaField.getText().equals("")?SKScapaiankinerjaField.getText():"0");
        System.out.println("penilaian asesor: " + penilaianasesorField.getText());
        data[20] = (!penilaianasesorField.getText().equals("")?penilaianasesorField.getText():"0");
        data[21] = (!rekomendasiasesorField.getText().equals("")?rekomendasiasesorField.getText():"X");
        
        String datastatusvalidasimentah = statusvalidasi.getText();
        System.out.println("data status validasi mentah: " + datastatusvalidasimentah);
        String [] datastatusvalidasi = datastatusvalidasimentah.split(" ");
        System.out.println("data status validasi yg dipilih: " + datastatusvalidasi[0]);
        data[22] = datastatusvalidasi[0];
        
        data[23] = "" + idlogaudit;
        data[24] = UUID_assessor2Field.getText();
        data[25] = UUID_assessor1Field.getText();
        data[26] = UUID_assessorField.getText();
        return data;
    }
    
    public String[] dapatkanNilaiUntukUpdate(){
        String [] data = new String[jmlKolom];
        
        String datanidnnukmentah = nidnnuk.getText();
        System.out.println("data nidnnuk mentah: " + datanidnnukmentah);
        String [] datanidnnuk = datanidnnukmentah.split(" ");
        System.out.println("data nidnnuk yg dipilih: " + datanidnnuk[0]);
        data[0] = datanidnnuk[0];
        
        data[1] = (!NIPlamaField.getText().equals("")?NIPlamaField.getText():"0");
        data[2] = (!NIPbaruField.getText().equals("")?NIPbaruField.getText():"0");
        data[3] = (!nomordosenField.getText().equals("")?nomordosenField.getText():"0");
        data[4] = (!kodeperguruantinggiField.getText().equals("      ")?kodeperguruantinggiField.getText():"0");
        
        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String [] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[5] = datakodeprogramstudi[0];
        
        data[6] = (!tahunpelaporanField.getText().equals("    ")?tahunpelaporanField.getText():"0000");
        data[7] = (!semesterpelaporanField.getText().equals(" ")?semesterpelaporanField.getText():"0");
        
        String datanidnasesor1mentah = nidnasesor1.getText();
        System.out.println("data nidnasesor1 mentah: " + datanidnasesor1mentah);
        String [] datanidnasesor1 = datanidnasesor1mentah.split(" ");
        System.out.println("data nidnasesor1 yg dipilih: " + datanidnasesor1[0]);
        data[8] = datanidnasesor1[0];
        
        String datanidnasesor2mentah = nidnasesor2.getText();
        System.out.println("data nidnasesor2 mentah: " + datanidnasesor2mentah);
        String [] datanidnasesor2 = datanidnasesor2mentah.split(" ");
        System.out.println("data nidnasesor2 yg dipilih: " + datanidnasesor2[0]);
        data[9] = datanidnasesor2[0];
        
        String datakategorievaluasimentah = kategorievaluasi.getText();
        System.out.println("data kategorievaluasi mentah: " + datakategorievaluasimentah);
        String [] datakategorievaluasi = datakategorievaluasimentah.split(" ");
        System.out.println("data kategorievaluasi yg dipilih: " + datakategorievaluasi[0]);
        data[10] = datakategorievaluasi[0];
        
        data[11] = (!jeniskegiatanField.getText().equals("")?jeniskegiatanField.getText():"X");
        data[12] = (!buktipenugasanField.getText().equals("")?buktipenugasanField.getText():"X");
        data[13] = (!SKSbebankerjaField.getText().equals("")?SKSbebankerjaField.getText():"0");
        
        kalendar = masapelaksanaantugasawalField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data7 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[14] = (data7!=null?data7:"");
        
        kalendar = masapelaksanaantugasakhirField.getCalendar();
        if (kalendar==null) {kalendar = Calendar.getInstance();}
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);        
        String data16 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[15] = (data16!=null?data16:"");
        
        data[16] = (!buktidokumenkinerjaField.getText().equals("")?buktidokumenkinerjaField.getText():null);
        System.out.println("persentase capaian kinerja: " + persentasecapaiankinerjaField.getText());
        data[17] = (!persentasecapaiankinerjaField.getText().equals("   .  ")?persentasecapaiankinerjaField.getText():"000.00");
        data[18] = (!SKScapaiankinerjaField.getText().equals("")?SKScapaiankinerjaField.getText():"0");
        System.out.println("penilaian asesor: " + penilaianasesorField.getText());
        data[19] = (!penilaianasesorField.getText().equals("   .  ")?penilaianasesorField.getText():"000.00");
        data[20] = (!rekomendasiasesorField.getText().equals("")?rekomendasiasesorField.getText():"X");
        
        String datastatusvalidasimentah = statusvalidasi.getText();
        System.out.println("data status validasi mentah: " + datastatusvalidasimentah);
        String [] datastatusvalidasi = datastatusvalidasimentah.split(" ");
        System.out.println("data status validasi yg dipilih: " + datastatusvalidasi[0]);
        data[21] = datastatusvalidasi[0];
        
        data[22] = "" + idlogaudit;
        data[23] = UUID_assessor2Field.getText();
        data[24] = UUID_assessor1Field.getText();
        data[25] = UUID_assessorField.getText();
        data[26] = (!kodekinerjadosenField.getText().equals("")?kodekinerjadosenField.getText():"0");
        return data;
    }
    
    public String[] dapatkanNilaiUntukDelete(){
        String [] data = new String[1];
        data[0] = kodekinerjadosenField.getText();
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
        kodekinerjadosenLabel = new javax.swing.JLabel();
        NIDNNUKLabel = new javax.swing.JLabel();
        NIPlamaLabel = new javax.swing.JLabel();
        NIPbaruLabel = new javax.swing.JLabel();
        nomordosenLabel = new javax.swing.JLabel();
        kodeperguruantinggiLabel = new javax.swing.JLabel();
        kodeprogramstudiLabel = new javax.swing.JLabel();
        tahunpelaporanLabel = new javax.swing.JLabel();
        semesterpelaporanLabel = new javax.swing.JLabel();
        NIDNassesor1Label = new javax.swing.JLabel();
        NIDNassesor2Label = new javax.swing.JLabel();
        kategorievaluasiLabel = new javax.swing.JLabel();
        jeniskegiatanLabel = new javax.swing.JLabel();
        buktipenugasanLabel = new javax.swing.JLabel();
        SKSbebankerjaLabel = new javax.swing.JLabel();
        masapelaksanaantugasawalLabel = new javax.swing.JLabel();
        masapelaksanaantugasakhirLabel = new javax.swing.JLabel();
        buktidokumenkinerjaLabel = new javax.swing.JLabel();
        persentasecapaiankinerjaLabel = new javax.swing.JLabel();
        SKScapaiankinerjaLabel = new javax.swing.JLabel();
        penilaianasesorLabel = new javax.swing.JLabel();
        rekomendasiasesorLabel = new javax.swing.JLabel();
        statusvalidasiLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        IDlogauditLabel1 = new javax.swing.JLabel();
        IDlogauditLabel2 = new javax.swing.JLabel();
        IDlogauditLabel3 = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        kodekinerjadosenField = new javax.swing.JFormattedTextField();
        nidnnukField = new javax.swing.JComboBox();
        NIPlamaField = new javax.swing.JFormattedTextField();
        NIPbaruField = new javax.swing.JFormattedTextField();
        nomordosenField = new javax.swing.JFormattedTextField();
        kodeperguruantinggiField = new javax.swing.JFormattedTextField();
        kodeprogramstudiField = new javax.swing.JComboBox();
        tahunpelaporanField = new javax.swing.JFormattedTextField();
        semesterpelaporanField = new javax.swing.JFormattedTextField();
        NIDNassesor1Field = new javax.swing.JComboBox();
        NIDNassesor2Field = new javax.swing.JComboBox();
        kategorievaluasiField = new javax.swing.JComboBox();
        jeniskegiatanField = new javax.swing.JTextField();
        buktipenugasanField = new javax.swing.JTextField();
        SKSbebankerjaField = new javax.swing.JFormattedTextField();
        masapelaksanaantugasawalField = new com.toedter.calendar.JDateChooser();
        masapelaksanaantugasakhirField = new com.toedter.calendar.JDateChooser();
        buktidokumenkinerjaScrollPane = new javax.swing.JScrollPane();
        buktidokumenkinerjaField = new javax.swing.JTextArea();
        persentasecapaiankinerjaField = new javax.swing.JFormattedTextField();
        SKScapaiankinerjaField = new javax.swing.JFormattedTextField();
        penilaianasesorField = new javax.swing.JFormattedTextField();
        rekomendasiasesorField = new javax.swing.JTextField();
        statusvalidasiField = new javax.swing.JComboBox();
        IDlogauditField = new javax.swing.JFormattedTextField();
        UUID_assessor2Field = new javax.swing.JTextField();
        UUID_assessor1Field = new javax.swing.JTextField();
        UUID_assessorField = new javax.swing.JTextField();
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

        panelLabel.setLayout(new java.awt.GridLayout(27, 0));

        kodekinerjadosenLabel.setText("Kode Kinerja Dosen:");
        panelLabel.add(kodekinerjadosenLabel);

        NIDNNUKLabel.setText("NIDN / NUK:");
        panelLabel.add(NIDNNUKLabel);

        NIPlamaLabel.setText("NIP Lama:");
        panelLabel.add(NIPlamaLabel);

        NIPbaruLabel.setText("NIP Baru:");
        panelLabel.add(NIPbaruLabel);

        nomordosenLabel.setText("Nomor Dosen:");
        panelLabel.add(nomordosenLabel);

        kodeperguruantinggiLabel.setText("Kode Perguruan Tinggi:");
        panelLabel.add(kodeperguruantinggiLabel);

        kodeprogramstudiLabel.setText("Kode Program Studi:");
        panelLabel.add(kodeprogramstudiLabel);

        tahunpelaporanLabel.setText("Tahun Pelaporan:");
        panelLabel.add(tahunpelaporanLabel);

        semesterpelaporanLabel.setText("Semester Pelaporan:");
        panelLabel.add(semesterpelaporanLabel);

        NIDNassesor1Label.setText("NIDN Assesor1:");
        panelLabel.add(NIDNassesor1Label);

        NIDNassesor2Label.setText("NIDN Assesor2:");
        panelLabel.add(NIDNassesor2Label);

        kategorievaluasiLabel.setText("Kategori Evaluasi:");
        panelLabel.add(kategorievaluasiLabel);

        jeniskegiatanLabel.setText("Jenis Kegiatan:");
        panelLabel.add(jeniskegiatanLabel);

        buktipenugasanLabel.setText("Bukti Penugasan:");
        panelLabel.add(buktipenugasanLabel);

        SKSbebankerjaLabel.setText("SKS Beban Kerja:");
        panelLabel.add(SKSbebankerjaLabel);

        masapelaksanaantugasawalLabel.setText("Masa Pelaksanaan Tugas Awal:");
        panelLabel.add(masapelaksanaantugasawalLabel);

        masapelaksanaantugasakhirLabel.setText("Masa Pelaksanaan Tugas Akhir:");
        panelLabel.add(masapelaksanaantugasakhirLabel);

        buktidokumenkinerjaLabel.setText("Bukti Dokumen Kinerja:");
        panelLabel.add(buktidokumenkinerjaLabel);

        persentasecapaiankinerjaLabel.setText("Persentase Capaian Kinerja:");
        panelLabel.add(persentasecapaiankinerjaLabel);

        SKScapaiankinerjaLabel.setText("SKS Capaian Kinerja:");
        panelLabel.add(SKScapaiankinerjaLabel);

        penilaianasesorLabel.setText("Penilaian Asesor:");
        panelLabel.add(penilaianasesorLabel);

        rekomendasiasesorLabel.setText("Rekomendasi Asesor:");
        panelLabel.add(rekomendasiasesorLabel);

        statusvalidasiLabel.setText("Status Validasi:");
        panelLabel.add(statusvalidasiLabel);

        IDlogauditLabel.setText("ID Log Audit:");
        panelLabel.add(IDlogauditLabel);

        IDlogauditLabel1.setText("UUID Assessor 2:");
        panelLabel.add(IDlogauditLabel1);

        IDlogauditLabel2.setText("UUID Assessor 3:");
        panelLabel.add(IDlogauditLabel2);

        IDlogauditLabel3.setText("UUID :");
        panelLabel.add(IDlogauditLabel3);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(27, 0));

        kodekinerjadosenField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(kodekinerjadosenField);

        nidnnukField.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        panelTextField.add(nidnnukField);

        NIPlamaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(NIPlamaField);

        NIPbaruField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(NIPbaruField);

        nomordosenField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(nomordosenField);

        try {
            kodeperguruantinggiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodeperguruantinggiField);

        panelTextField.add(kodeprogramstudiField);

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

        NIDNassesor1Field.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        panelTextField.add(NIDNassesor1Field);

        NIDNassesor2Field.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        panelTextField.add(NIDNassesor2Field);

        panelTextField.add(kategorievaluasiField);
        panelTextField.add(jeniskegiatanField);
        panelTextField.add(buktipenugasanField);

        SKSbebankerjaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        panelTextField.add(SKSbebankerjaField);
        panelTextField.add(masapelaksanaantugasawalField);
        panelTextField.add(masapelaksanaantugasakhirField);

        buktidokumenkinerjaField.setColumns(20);
        buktidokumenkinerjaField.setRows(1);
        buktidokumenkinerjaScrollPane.setViewportView(buktidokumenkinerjaField);

        panelTextField.add(buktidokumenkinerjaScrollPane);

        persentasecapaiankinerjaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        panelTextField.add(persentasecapaiankinerjaField);

        SKScapaiankinerjaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        panelTextField.add(SKScapaiankinerjaField);

        penilaianasesorField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        panelTextField.add(penilaianasesorField);
        panelTextField.add(rekomendasiasesorField);

        panelTextField.add(statusvalidasiField);

        IDlogauditField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        panelTextField.add(IDlogauditField);
        panelTextField.add(UUID_assessor2Field);
        panelTextField.add(UUID_assessor1Field);
        panelTextField.add(UUID_assessorField);

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
                "?,?,?,?,?,?,?)";
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
        " SET \"NIDN/NUK\"=?, " +
        "\"NIP_Lama\"=?, " +
        "\"NIP_baru\"=?, " +
        "\"Nomor_dosen\"=?, " +
        "\"Kode_perguruan_tinggi\"=?, " +
        "\"Kode_program_studi\"=?, " +
        "\"Tahun_pelaporan\"=?, " +
        "\"Semester_pelaporan\"=?, " +
        "\"tempat_lahir\"=?, " +
        "\"NIDN_assesor_1\"=?, " +
        "\"NIDN_assesor_2\"=?, " +
        "\"Kategori_evaluasi\"=?, " +
        "\"Jenis_kegiatan\"=?, " +
        "\"Bukti_penugasan\"=?, " +
        "\"SKS_beban_kerja\"=?, " +
        "\"Masa_pelaksanaan_tugas_awal\"=?, " +
        "\"Masa_pelaksanaan_tugas_akhir\"=?, " +
        "\"Bukti_dokumen_kinerja\"=?, " +
        "\"Persentase_capaian_kinerja\"=?, " +
        "\"SKS_capaian_kinerja\"=?, " +
        "\"Penilaian_asesor\"=?, " +
        "\"Rekomendasi_asesor\"=?, " +
        "\"Status_validasi\"=?, " +
        "\"ID_log_audit\"=?, " +
        "\"UUID_assesor2\"=?, " +
        "\"UUID_assesor1\"=?, " +
        "\"UUID\"=? " +
        " WHERE \"Kode_kinerja_dosen\"=? ";
       int hasil = model.updateAktifitasDOsen(query,
            dapatkanNilaiUntukInsert() );
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
        " \"Kode_kinerja_dosen\"=? ";
        
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
    private javax.swing.JLabel IDlogauditLabel2;
    private javax.swing.JLabel IDlogauditLabel3;
    private javax.swing.JLabel NIDNNUKLabel;
    private javax.swing.JComboBox NIDNassesor1Field;
    private javax.swing.JLabel NIDNassesor1Label;
    private javax.swing.JComboBox NIDNassesor2Field;
    private javax.swing.JLabel NIDNassesor2Label;
    private javax.swing.JFormattedTextField NIPbaruField;
    private javax.swing.JLabel NIPbaruLabel;
    private javax.swing.JFormattedTextField NIPlamaField;
    private javax.swing.JLabel NIPlamaLabel;
    private javax.swing.JFormattedTextField SKSbebankerjaField;
    private javax.swing.JLabel SKSbebankerjaLabel;
    private javax.swing.JFormattedTextField SKScapaiankinerjaField;
    private javax.swing.JLabel SKScapaiankinerjaLabel;
    private javax.swing.JTextField UUID_assessor1Field;
    private javax.swing.JTextField UUID_assessor2Field;
    private javax.swing.JTextField UUID_assessorField;
    private javax.swing.JTextArea buktidokumenkinerjaField;
    private javax.swing.JLabel buktidokumenkinerjaLabel;
    private javax.swing.JScrollPane buktidokumenkinerjaScrollPane;
    private javax.swing.JTextField buktipenugasanField;
    private javax.swing.JLabel buktipenugasanLabel;
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
    private javax.swing.JTextField jeniskegiatanField;
    private javax.swing.JLabel jeniskegiatanLabel;
    private javax.swing.JComboBox kategorievaluasiField;
    private javax.swing.JLabel kategorievaluasiLabel;
    private javax.swing.JFormattedTextField kodekinerjadosenField;
    private javax.swing.JLabel kodekinerjadosenLabel;
    private javax.swing.JFormattedTextField kodeperguruantinggiField;
    private javax.swing.JLabel kodeperguruantinggiLabel;
    private javax.swing.JComboBox kodeprogramstudiField;
    private javax.swing.JLabel kodeprogramstudiLabel;
    private com.toedter.calendar.JDateChooser masapelaksanaantugasakhirField;
    private javax.swing.JLabel masapelaksanaantugasakhirLabel;
    private com.toedter.calendar.JDateChooser masapelaksanaantugasawalField;
    private javax.swing.JLabel masapelaksanaantugasawalLabel;
    private javax.swing.JComboBox nidnnukField;
    private javax.swing.JFormattedTextField nomordosenField;
    private javax.swing.JLabel nomordosenLabel;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JFormattedTextField penilaianasesorField;
    private javax.swing.JLabel penilaianasesorLabel;
    private javax.swing.JFormattedTextField persentasecapaiankinerjaField;
    private javax.swing.JLabel persentasecapaiankinerjaLabel;
    private javax.swing.JTextField rekomendasiasesorField;
    private javax.swing.JLabel rekomendasiasesorLabel;
    private javax.swing.JFormattedTextField semesterpelaporanField;
    private javax.swing.JLabel semesterpelaporanLabel;
    private javax.swing.JComboBox statusvalidasiField;
    private javax.swing.JLabel statusvalidasiLabel;
    private javax.swing.JTable tabel;
    private javax.swing.JFormattedTextField tahunpelaporanField;
    private javax.swing.JLabel tahunpelaporanLabel;
    // End of variables declaration//GEN-END:variables
}
