/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Model.ModelMasterDosen;
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
public class MasterDosen extends javax.swing.JPanel {

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
    private String[] namaBulan = {"Jan", "Feb", "Mar", "Apr", "May",
        "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private int barisYgDipilih = 0;
    Model.ModelMasterDosen model;
    private JTextField golongan;
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo1;
    private JTextField pangkat;
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo2;
    private JTextField jabatanstruktural;
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo3;
    private JTextField kodeprogramstudi;
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo4;
    private JTextField kodefakultas;
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo5;
    private JTextField kodejurusan;
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo6;
    private JTextField jeniskelamin;
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo7;
    private JTextField statusaktif;
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo8;
    private JTextField kodejenjangpendidikan;
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo9;
    private JTextField kodejabatanakademik;
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo10;
    private JTextField ikatankerja;
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo11;
    private JTextField aktadanijinmengajar;
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo12;
    private JTextField kodekota;
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo13;
    private JTextField kodeprovinsi;
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo14;
    private JTextField kodenegara;
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo15;
    private JTextField statusvalidasi;
    private koneksi.KompletTextFieldCombo kompletTextFieldCombo16;
    private int idlogaudit;
    private String kueri;
    String kodept,UUID="0",genUUID,Kodept;
    /**
     * Creates new form PanelHalaman1
     */
    public MasterDosen() {
        initComponents();
    }

    public MasterDosen(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
        this.namaTabel = _namaTabel;
        kodept = this.homePage.dapatkanKodePT();
        IDlogauditLabel.setVisible(false);
        IDlogauditField.setVisible(false);
        model = new ModelMasterDosen();
        Kodept = homePage.dapatkanKodePT();
        inisialisasiData();
        initAutoComplete();
        kodeperguruantinggiField.setText(kodept);
         //disable all button
         buttonBaru.setEnabled(false);
        buttonInsert.setEnabled(false);
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
    }

    private void initIDLogAudit() {
        query = "select \"ID_log_audit\" "
                + " from pdpt_dev.\"TREF_LOG_AUDIT\" "
                + " order by \"ID_log_audit\" DESC";
        kon.selectData(query);
        String[][] data = kon.dapatkanData();
        if (data[0][0] == null) {
            idlogaudit = 1;
        } else {
            idlogaudit = Integer.parseInt(data[0][0]);
            idlogaudit++;
        }
        System.out.println("idlogaudit: " + idlogaudit);
    }

    private String[] ubahLabelKeSentenceCase(String[] namaKolom) {
        String[] data = new String[namaKolom.length - 1]; //nilai length-1 agar id_log_audit diHIDDEN
        for (int a = 0; a < data.length; a++) {
            String datum = "";
            String[] sukuKata = namaKolom[a].split("_");
            for (int b = 0; b < sukuKata.length; b++) {
                String label = sukuKata[b];
                sukuKata[b] = label.substring(0, 1).toUpperCase() + label.substring(1);
                datum += sukuKata[b] + " ";
            }
            data[a] = datum;
        }
        return data;
    }

    private String[] dapatkanNilaiUntukInsertKeLogAudit(String _aksi,
            String _tabel, String _kueri) {
        String[] data = new String[6];
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
                golonganField, golongan);
        String[][] datanya1 = {{"I", ""}, {"II", ""}, {"III", ""}, {"IV", ""}};
        kompletTextFieldCombo1.initDataUtkAutoComplete(datanya1);
        golongan = kompletTextFieldCombo1.dapatkanTextField();
        golonganField = kompletTextFieldCombo1.dapatkanComboBox();

        kompletTextFieldCombo2 = new KompletTextFieldCombo(kon,
                pangkatField, pangkat);
        String[][] datanya2 = {{"A", ""}, {"B", ""}, {"C", ""}, {"D", ""}};
        kompletTextFieldCombo2.initDataUtkAutoComplete(datanya2);
        pangkat = kompletTextFieldCombo2.dapatkanTextField();
        pangkatField = kompletTextFieldCombo2.dapatkanComboBox();

        kompletTextFieldCombo3 = new KompletTextFieldCombo(kon,
                jabatanstrukturalField, jabatanstruktural);
        /*
         String [][] datanya3 = {{"Rektor"},
         {"Pembantu Rektor, Ketua Lembaga, Dekan Fakultas, Direktur Pascasarjana"}, 
         {"Pembantu Dekan, Ketua Sekolah Tinggi, Asdir PPs, Direktur Politeknik, Kapus Penelitian, Ketua/Sekretaris Senat Fakultas"},
         {"Direktur Akademi, Pembantu Ketua Sekolah Tinggi, Kapus Penelitian dan Pengabdian pada Masyarakat ST, Pembantu Direktur"},
         {"Pembantu Direktur Akademi, Ketua Jurusan/Bagian, Ketua/Sekretaris Program Studi, Ketua Unit Penelitian dan Pengabdian kepada Masyarakat"}}; 
         * */
        String[][] datanya3 = {{"Rektor", ""},
            {"Pemb.Rektor,KetuaLemb.,Dekan,Dir.PPs", ""},
            {"Pemb.Dekan,KetuaST,AsdirPPs,Dir.Poli,Kapuslit,Ket/SekSenatFak", ""},
            {"Dir.Akad,Pemb.KetuaST,KapusLPPMST,Pemb.Dir", ""},
            {"Pemb.Dir.Akad,Kajur/bag,Ket/SekProdi,Ket.LPPM", ""}
        };
        kompletTextFieldCombo3.initDataUtkAutoComplete(datanya3);
        jabatanstruktural = kompletTextFieldCombo3.dapatkanTextField();
        jabatanstrukturalField = kompletTextFieldCombo3.dapatkanComboBox();

        kompletTextFieldCombo4 = new KompletTextFieldCombo(kon,
                kodeprogramstudiField, kodeprogramstudi);
        query = "select \"Kode_program_studi\", \"Nama_program_studi\" "
                + " from pdpt_dev.\"TMST_PROGRAM_STUDI\" where \"Kode_perguruan_tinggi\" = '"+Kodept+"'";
        kompletTextFieldCombo4.initDataUtkAutoComplete(query);
        kodeprogramstudi = kompletTextFieldCombo4.dapatkanTextField();
        kodeprogramstudiField = kompletTextFieldCombo4.dapatkanComboBox();

        kompletTextFieldCombo5 = new KompletTextFieldCombo(kon,
                kodefakultasField, kodefakultas);
        query = "SELECT \"Kode_fakultas\", \"Nama_fakultas\" "
                + " from pdpt_dev.\"TMST_FAKULTAS\" where \"Kode_perguruan_tinggi\" = '"+Kodept+"'";
        kompletTextFieldCombo5.initDataUtkAutoComplete(query);
        kodefakultas = kompletTextFieldCombo5.dapatkanTextField();
        kodefakultasField = kompletTextFieldCombo5.dapatkanComboBox();

        kompletTextFieldCombo6 = new KompletTextFieldCombo(kon,
                kodejurusanField, kodejurusan);
        query = "SELECT \"Kode_jurusan\", \"Nama_jurusan\", \"Kode_fakultas\" "
                + " from pdpt_dev.\"TMST_JURUSAN\" where \"Kode_perguruan_tinggi\" = '"+Kodept+"'";
        kompletTextFieldCombo6.initDataUtkAutoComplete(query);
        kodejurusan = kompletTextFieldCombo6.dapatkanTextField();
        kodejurusanField = kompletTextFieldCombo6.dapatkanComboBox();

        kompletTextFieldCombo7 = new KompletTextFieldCombo(kon,
                jeniskelaminField, jeniskelamin);
        String[][] datanya7 = {{"L", "Laki-laki"},
            {"P", "Perempuan"}};
        kompletTextFieldCombo7.initDataUtkAutoComplete(datanya7);
        jeniskelamin = kompletTextFieldCombo7.dapatkanTextField();
        jeniskelaminField = kompletTextFieldCombo7.dapatkanComboBox();

        kompletTextFieldCombo8 = new KompletTextFieldCombo(kon,
                statusaktifField, statusaktif);
        query = "SELECT \"Kode_status_aktivitas_dosen\", \"Nama_status\" "
                + " from pdpt_dev.\"TREF_STATUS_AKTIVITAS_DOSEN\"";
        kompletTextFieldCombo8.initDataUtkAutoComplete(query);
        statusaktif = kompletTextFieldCombo8.dapatkanTextField();
        statusaktifField = kompletTextFieldCombo8.dapatkanComboBox();

        kompletTextFieldCombo9 = new KompletTextFieldCombo(kon,
                kodejenjangpendidikanField, kodejenjangpendidikan);
        query = "select \"Kode_jenjang_pendidikan\", \"Deskripsi\" "
                + " from pdpt_dev.\"TREF_JENJANG_PENDIDIKAN\"";
        kompletTextFieldCombo9.initDataUtkAutoComplete(query);
        kodejenjangpendidikan = kompletTextFieldCombo9.dapatkanTextField();
        kodejenjangpendidikanField = kompletTextFieldCombo9.dapatkanComboBox();

        kompletTextFieldCombo10 = new KompletTextFieldCombo(kon,
                kodejabatanakademikField, kodejabatanakademik);
        query = "select \"Kode_jabatan_akademik\", \"Deskripsi\" "
                + " from pdpt_dev.\"TREF_JABATAN_AKADEMIK\"";
        kompletTextFieldCombo10.initDataUtkAutoComplete(query);
        kodejabatanakademik = kompletTextFieldCombo10.dapatkanTextField();
        kodejabatanakademikField = kompletTextFieldCombo10.dapatkanComboBox();

        kompletTextFieldCombo11 = new KompletTextFieldCombo(kon,
                ikatankerjaField, ikatankerja);
        String[][] datanya11 = {{"A", "Dosen Tetap untuk PTN/PTS"},
            {"B", "Dosen PNS DPK yang bertugas di PTS"},
            {"C", "Dosen PNS PTN yang bertugas di PTS"},
            {"D", "Honorer non-PNS PTN"},
            {"E", "Kontrak/Tetap Kontrak (Khusus KEMENKES)"},
            {"F", "Dosen Tetap BHMN"}};
        kompletTextFieldCombo11.initDataUtkAutoComplete(datanya11);
        ikatankerja = kompletTextFieldCombo11.dapatkanTextField();
        ikatankerjaField = kompletTextFieldCombo11.dapatkanComboBox();

        kompletTextFieldCombo12 = new KompletTextFieldCombo(kon,
                aktadanijinmengajarField, aktadanijinmengajar);
        String[][] datanya12 = {{"Y", "Ada"},
            {"T", "Tidak Ada"}};
        kompletTextFieldCombo12.initDataUtkAutoComplete(datanya12);
        aktadanijinmengajar = kompletTextFieldCombo12.dapatkanTextField();
        aktadanijinmengajarField = kompletTextFieldCombo12.dapatkanComboBox();

        kompletTextFieldCombo13 = new KompletTextFieldCombo(kon,
                kodekotaField, kodekota);
        query = "SELECT \"Kode_kota\", \"Nama_kabupaten\" "
                + " from pdpt_dev.\"TREF_KOTA\"";
        kompletTextFieldCombo13.initDataUtkAutoComplete(query);
        kodekota = kompletTextFieldCombo13.dapatkanTextField();
        kodekotaField = kompletTextFieldCombo13.dapatkanComboBox();

        kompletTextFieldCombo14 = new KompletTextFieldCombo(kon,
                kodeprovinsiField, kodeprovinsi);
        query = "SELECT \"Kode_provinsi\", \"Nama_provinsi\" "
                + " FROM pdpt_dev.\"TREF_PROVINSI\" "
                + " WHERE \"Kode_negara\"='1'";
        kompletTextFieldCombo14.initDataUtkAutoComplete(query);
        kodeprovinsi = kompletTextFieldCombo14.dapatkanTextField();
        kodeprovinsiField = kompletTextFieldCombo14.dapatkanComboBox();

        kompletTextFieldCombo15 = new KompletTextFieldCombo(kon,
                kodenegaraField, kodenegara);
        query = "SELECT \"Kode_negara\", \"Nama_negara\" "
                + " FROM pdpt_dev.\"TREF_NEGARA\"";
        kompletTextFieldCombo15.initDataUtkAutoComplete(query);
        kodenegara = kompletTextFieldCombo15.dapatkanTextField();
        kodenegaraField = kompletTextFieldCombo15.dapatkanComboBox();

        kompletTextFieldCombo16 = new KompletTextFieldCombo(kon,
                statusvalidasiField, statusvalidasi);
        String[][] datanya16 = {{"1", "Belum diverifikasi"},
            {"2", "Sudah diverifikasi namun masih terdapat data yang invalid"},
            {"3", "Sudah diverifikasi dan data sudah valid"}};
        kompletTextFieldCombo16.initDataUtkAutoComplete(datanya16);
        statusvalidasi = kompletTextFieldCombo16.dapatkanTextField();
        statusvalidasiField = kompletTextFieldCombo16.dapatkanComboBox();
    }
    //ditambahkan pada 27 juli 2013 di marriot
    public void cekUUID(){
        System.out.println("Cek UUID");
         query = "select \"UUID\" "
                + " from pdpt_dev.\"TMST_DOSEN\" where \"Kode_perguruan_tinggi\" = '"+Kodept+"'";
         kon = homePage.dapatkanKoneksiDB();
         kon.selectData(query);
         String[][] data = kon.dapatkanData();
         UUID = data[0][0];
         if(Integer.parseInt(UUID)<=0){
             UUID="1";
         }
         System.out.println("UUID : "+UUID);
    }

    private void inisialisasiData() {
        namaTabel = "pdpt_dev.\"TMST_DOSEN\"";
        kon = homePage.dapatkanKoneksiDB();
        query = "select * from " + namaTabel+" where \"Kode_perguruan_tinggi\" = '"+Kodept+"'";
        kon.selectData(query);
        jmlKolom = kon.dapatkanJumlahKolom();
        String[][] data = kon.dapatkanData();
        String[] kolom = ubahLabelKeSentenceCase(kon.dapatkanKolom());
        tampilkanDataKeTabel(data, kolom);
        kodeperguruantinggiField.setText(kodept);//kode PT ditambahkan di mariot 27 juli 2013
    }

    public MasterDosen(String[][] data, String[] kolom) {
        this();
        tampilkanDataKeTabel(data, kolom);
    }

    public void autoResizeColWidth() {

        int margin = 5;

        for (int i = 0; i < tabel.getColumnCount(); i++) {
            int vColIndex = i;
            DefaultTableColumnModel colModel = (DefaultTableColumnModel) tabel.getColumnModel();
            TableColumn col = colModel.getColumn(vColIndex);
            int width = 0;

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
                comp = renderer.getTableCellRendererComponent(tabel, tabel.getValueAt(r, vColIndex), false, false,
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
        NIDNNUPField.setText("");
        NIPlamaField.setText("");
        NIPbaruField.setText("");
        golongan.setText("");
        pangkat.setText("");
        jabatanfungsionalField.setText("");
        jabatanstruktural.setText("");

        kodeperguruantinggiField.setText("" + homePage.dapatkanKodePT());
        kodeprogramstudi.setText("");
        kodefakultas.setText("");
        kodejurusan.setText("");
        namadosenField.setText("");
        nomorKTPField.setText("");
        gelarakademikdepanField.setText("");
        gelarakademikbelakangField.setText("");
        jeniskelamin.setText("");
        tempatlahirField.setText("");
        tanggallahirField.setDate(null);
        statusaktif.setText("");
        mulaimasukdosenField.setDate(null);
        mulaisemesterField.setText("");

        kodejenjangpendidikan.setText("");
        kodejabatanakademik.setText("");
        ikatankerja.setText("");
        aktadanijinmengajar.setText("");
        alamatField.setText("");
        kodekota.setText("");
        kodeprovinsi.setText("");
        kodeposField.setText("");
        kodenegara.setText("");
        nosertifikasidosenField.setText("");
        tglkeluarsertifikasidosenField.setDate(null);
        niraField.setText("");
        statusvalidasi.setText("");
        teleponField.setText("");
        hpField.setText("");
        IDlogauditField.setText("");
        EmailField.setText("");
        FileFotoField.setText("");
        KodePendidikanTinggiField.setText("");
        BidangIlmuField.setText("");
        NamaKotaField.setText("");
        FaximileField.setText("");
        NIDNLAMAField.setText("");
        NIDNHonorerField.setText("");
        LastUpdateField.setText("");
        LastEditorField.setText("");
        StatusPegawaiField.setText("");
        NIDNHapusField.setText("");
        NIDNNUPField.requestFocus();
    }

    public void tampilkanDataKeTabel(String[][] data, String[] kolom) {
        //kosongkan tabel terlebih dulu
        int row = tabel.getRowCount();
        for (int i = 0; i < row; i++) {
            ((DefaultTableModel) tabel.getModel()).removeRow(0);
        }

        //kosongkan kolom 
        ((DefaultTableModel) tabel.getModel()).setColumnCount(0);

        //isi tabel dgn data baru
        for (int b = 0; b < kolom.length; b++) {
            ((DefaultTableModel) tabel.getModel()).addColumn(kolom[b]);
        }
        for (int a = 0; a < data.length; a++) {
            ((DefaultTableModel) tabel.getModel()).addRow(data[a]);
        }

        autoResizeColWidth();
    }

    private void tampilkanData(int row) {
        System.out.println("baris yg dipilih utk diUPDATE: " + row);
        persiapanEntriDataBaru();
        NIDNNUPField.setText(tabel.getValueAt(row, 0).toString());
        NIPlamaField.setText(tabel.getValueAt(row, 1).toString());
        NIPbaruField.setText(tabel.getValueAt(row, 2).toString());
        golongan.setText(tabel.getValueAt(row, 3).toString());
        pangkat.setText(tabel.getValueAt(row, 4).toString());
        jabatanfungsionalField.setText(tabel.getValueAt(row, 5).toString());
        jabatanstruktural.setText(tabel.getValueAt(row, 6).toString());
        kodeperguruantinggiField.setText(tabel.getValueAt(row, 7).toString());
        kodeprogramstudi.setText(tabel.getValueAt(row, 8).toString());
        kodefakultas.setText(tabel.getValueAt(row, 9).toString());
        kodejurusan.setText(tabel.getValueAt(row, 10).toString());
        namadosenField.setText(tabel.getValueAt(row, 11).toString());
        nomorKTPField.setText(tabel.getValueAt(row, 12).toString());
        gelarakademikdepanField.setText(tabel.getValueAt(row, 13).toString());
        gelarakademikbelakangField.setText(tabel.getValueAt(row, 14).toString());
        jeniskelamin.setText(tabel.getValueAt(row, 15).toString());
        tempatlahirField.setText(tabel.getValueAt(row, 16).toString());

        String strKalendar = tabel.getValueAt(row, 17).toString();
        System.out.println("kalendar: " + strKalendar);
        System.out.println("tahun: " + strKalendar.substring(0, 4));
        System.out.println("bulan: " + strKalendar.substring(5, 7));
        System.out.println("hari: " + strKalendar.substring(8, 10));
        tahun = Integer.parseInt(strKalendar.substring(0, 4));
        System.out.println("" + strKalendar.substring(0, 4) + " tahun: " + tahun);
        int intBulan = Integer.parseInt(strKalendar.substring(5, 7));
        System.out.println("" + strKalendar.substring(5, 7) + " intBulan: " + intBulan);
        hari = Integer.parseInt(strKalendar.substring(8, 10));
        System.out.println("" + strKalendar.substring(8, 10) + " hari: " + hari);
        kalendar = Calendar.getInstance();
        kalendar.set(tahun, intBulan - 1, hari);
        tanggallahirField.setCalendar(kalendar);

        statusaktif.setText(tabel.getValueAt(row, 18).toString());

        strKalendar = tabel.getValueAt(row, 19).toString();
        System.out.println("kalendar: " + strKalendar);
        System.out.println("tahun: " + strKalendar.substring(0, 4));
        System.out.println("bulan: " + strKalendar.substring(5, 7));
        System.out.println("hari: " + strKalendar.substring(8, 10));
        tahun = Integer.parseInt(strKalendar.substring(0, 4));
        System.out.println("" + strKalendar.substring(0, 4) + " tahun: " + tahun);
        intBulan = Integer.parseInt(strKalendar.substring(5, 7));
        System.out.println("" + strKalendar.substring(5, 7) + " intBulan: " + intBulan);
        hari = Integer.parseInt(strKalendar.substring(8, 10));
        System.out.println("" + strKalendar.substring(8, 10) + " hari: " + hari);
        kalendar = Calendar.getInstance();
        kalendar.set(tahun, intBulan - 1, hari);
        mulaimasukdosenField.setCalendar(kalendar);

        mulaisemesterField.setText(tabel.getValueAt(row, 20).toString());

        kodejenjangpendidikan.setText(tabel.getValueAt(row, 21).toString());
        kodejabatanakademik.setText(tabel.getValueAt(row, 22).toString());
        ikatankerja.setText(tabel.getValueAt(row, 23).toString());
        aktadanijinmengajar.setText(tabel.getValueAt(row, 24).toString());
        alamatField.setText(tabel.getValueAt(row, 25).toString());
        kodekota.setText(tabel.getValueAt(row, 26).toString());
        kodeprovinsi.setText(tabel.getValueAt(row, 27).toString());
        kodeposField.setText(tabel.getValueAt(row, 28).toString());
        kodenegara.setText(tabel.getValueAt(row, 29).toString());
        nosertifikasidosenField.setText(tabel.getValueAt(row, 30).toString());

        strKalendar = tabel.getValueAt(row, 31).toString();
        System.out.println("kalendar: " + strKalendar);
        System.out.println("tahun: " + strKalendar.substring(0, 4));
        System.out.println("bulan: " + strKalendar.substring(5, 7));
        System.out.println("hari: " + strKalendar.substring(8, 10));
        tahun = Integer.parseInt(strKalendar.substring(0, 4));
        System.out.println("" + strKalendar.substring(0, 4) + " tahun: " + tahun);
        intBulan = Integer.parseInt(strKalendar.substring(5, 7));
        System.out.println("" + strKalendar.substring(5, 7) + " intBulan: " + intBulan);
        hari = Integer.parseInt(strKalendar.substring(8, 10));
        System.out.println("" + strKalendar.substring(8, 10) + " hari: " + hari);
        kalendar = Calendar.getInstance();
        kalendar.set(tahun, intBulan - 1, hari);
        tglkeluarsertifikasidosenField.setCalendar(kalendar);

        niraField.setText(tabel.getValueAt(row, 32).toString());
        statusvalidasi.setText(tabel.getValueAt(row, 33).toString());
        teleponField.setText(tabel.getValueAt(row, 34).toString());
        hpField.setText(tabel.getValueAt(row, 35).toString());
        //IDlogauditField.setText(tabel.getValueAt(row, 36).toString());
    }

    public String[] dapatkanNilaiUntukInsert() {
         //cekUUID();
         //genUUID = String.valueOf(UUID)+"0"+NIDNNUPField.getText();
        String[] data = new String[jmlKolom];
        data[0] = NIDNNUPField.getText();
        data[1] = (!NIPlamaField.getText().equals("         ") ? NIPlamaField.getText() : "0");
        data[2] = (!NIPbaruField.getText().equals("                  ") ? NIPbaruField.getText() : "0");

        String datagolonganmentah = golongan.getText();
        System.out.println("data golongan mentah: " + datagolonganmentah);
        String[] datagolongan = datagolonganmentah.split(" ");
        System.out.println("data golongan yg dipilih: " + datagolongan[0]);
        data[3] = datagolongan[0];

        String datapangkatmentah = pangkat.getText();
        System.out.println("data pangkat mentah: " + datapangkatmentah);
        String[] datapangkat = datapangkatmentah.split(" ");
        System.out.println("data pangkat yg dipilih: " + datapangkat[0]);
        data[4] = datapangkat[0];

        data[5] = jabatanfungsionalField.getText();

        String datajabatanstrukturalmentah = jabatanstruktural.getText();
        System.out.println("data jabatanstruktural mentah: " + datajabatanstrukturalmentah);
        String[] datajabatanstruktural = datajabatanstrukturalmentah.split(" ");
        System.out.println("data jabatanstruktural yg dipilih: " + datajabatanstruktural[0]);
        data[6] = datajabatanstruktural[0];

        data[7] = (!kodeperguruantinggiField.getText().equals("      ") ? kodeperguruantinggiField.getText() : "0");

        String datakodeprogramstudimentah = kodeprogramstudi.getText();
        System.out.println("data kodeprogramstudi mentah: " + datakodeprogramstudimentah);
        String[] datakodeprogramstudi = datakodeprogramstudimentah.split(" ");
        System.out.println("data kodeprogramstudi yg dipilih: " + datakodeprogramstudi[0]);
        data[8] = datakodeprogramstudi[0];

        String datakodefakultasmentah = kodefakultas.getText();
        System.out.println("data kode fakultas mentah: " + datakodefakultasmentah);
        String[] datakodefakultas = datakodefakultasmentah.split(" ");
        System.out.println("data kode fakultas yg dipilih: " + datakodefakultas[0]);
        data[9] = datakodefakultas[0];

        String datakodejurusanmentah = kodejurusan.getText();
        System.out.println("data kode jurusan mentah: " + datakodejurusanmentah);
        String[] datakodejurusan = datakodejurusanmentah.split(" ");
        System.out.println("data kode jurusan yg dipilih: " + datakodejurusan[0]);
        data[10] = datakodejurusan[0];

        data[11] = namadosenField.getText();
        data[12] = (!nomorKTPField.getText().equals("                 ") ? nomorKTPField.getText() : "0");
        data[13] = gelarakademikdepanField.getText();
        data[14] = gelarakademikbelakangField.getText();

        String datajeniskelaminmentah = jeniskelamin.getText();
        System.out.println("data jeniskelamin mentah: " + datajeniskelaminmentah);
        String[] datajeniskelamin = datajeniskelaminmentah.split(" ");
        System.out.println("data jeniskelamin yg dipilih: " + datajeniskelamin[0]);
        data[15] = datajeniskelamin[0];

        data[16] = tempatlahirField.getText();

        kalendar = tanggallahirField.getCalendar();
        if (kalendar == null) {
            kalendar = Calendar.getInstance();
        }
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data8 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[17] = (data8 != null ? data8 : "");

        String datastatusaktifmentah = statusaktif.getText();
        System.out.println("data statusaktif mentah: " + datastatusaktifmentah);
        String[] datastatusaktif = datastatusaktifmentah.split(" ");
        System.out.println("data statusaktif yg dipilih: " + datastatusaktif[0]);
        data[18] = datastatusaktif[0];

        kalendar = mulaimasukdosenField.getCalendar();
        if (kalendar == null) {
            kalendar = Calendar.getInstance();
        }
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data19 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[19] = (data19 != null ? data19 : "");

        data[20] = (!mulaisemesterField.getText().equals("     ") ? mulaisemesterField.getText() : "0");

        String datakodejenjangpendidikanmentah = kodejenjangpendidikan.getText();
        System.out.println("data kode jenjangpendidikan mentah: " + datakodejenjangpendidikanmentah);
        String[] datakodejenjangpendidikan = datakodejenjangpendidikanmentah.split(" ");
        System.out.println("data kode jenjangpendidikan yg dipilih: " + datakodejenjangpendidikan[0]);
        data[21] = datakodejenjangpendidikan[0];

        String datakodejabatanakademikmentah = kodejabatanakademik.getText();
        System.out.println("data kodejabatanakademik mentah: " + datakodejabatanakademikmentah);
        String[] datakodejabatanakademik = datakodejabatanakademikmentah.split(" ");
        System.out.println("data kodejabatanakademik yg dipilih: " + datakodejabatanakademik[0]);
        data[22] = datakodejabatanakademik[0];

        String dataikatankerjamentah = ikatankerja.getText();
        System.out.println("data ikatankerja mentah: " + dataikatankerjamentah);
        String[] dataikatankerja = dataikatankerjamentah.split(" ");
        System.out.println("data ikatankerja yg dipilih: " + dataikatankerja[0]);
        data[23] = dataikatankerja[0];

        String dataaktadanijinmengajarmentah = aktadanijinmengajar.getText();
        System.out.println("data aktadanijinmengajar mentah: " + dataaktadanijinmengajarmentah);
        String[] dataaktadanijinmengajar = dataaktadanijinmengajarmentah.split(" ");
        System.out.println("data aktadanijinmengajar yg dipilih: " + dataaktadanijinmengajar[0]);
        data[24] = dataaktadanijinmengajar[0];

        data[25] = alamatField.getText();

        String datakodekotamentah = kodekota.getText();
        System.out.println("data kode kota mentah: " + datakodekotamentah);
        String[] datakodekota = datakodekotamentah.split(" ");
        System.out.println("data kode kota yg dipilih: " + datakodekota[0]);
        data[26] = datakodekota[0];

        String datakodeprovinsimentah = kodeprovinsi.getText();
        System.out.println("data kode provinsi mentah: " + datakodeprovinsimentah);
        String[] datakodeprovinsi = datakodeprovinsimentah.split(" ");
        System.out.println("data kode provinsi yg dipilih: " + datakodeprovinsi[0]);
        data[27] = datakodeprovinsi[0];

        data[28] = (!kodeposField.getText().equals("     ") ? kodeposField.getText() : "0");

        String datakodenegaramentah = kodenegara.getText();
        System.out.println("data kode negara mentah: " + datakodenegaramentah);
        String[] datakodenegara = datakodenegaramentah.split(" ");
        System.out.println("data kode negara yg dipilih: " + datakodenegara[0]);
        data[29] = datakodenegara[0];

        data[30] = (!nosertifikasidosenField.getText().equals("           ") ? nosertifikasidosenField.getText() : "0");

        kalendar = tglkeluarsertifikasidosenField.getCalendar();
        if (kalendar == null) {
            kalendar = Calendar.getInstance();
        }
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data26 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[31] = (data26 != null ? data26 : "");

        data[32] = (!niraField.getText().equals("") ? niraField.getText() : "0");

        String datastatusvalidasimentah = statusvalidasi.getText();
        System.out.println("data status validasi mentah: " + datastatusvalidasimentah);
        String[] datastatusvalidasi = datastatusvalidasimentah.split(" ");
        System.out.println("data status validasi yg dipilih: " + datastatusvalidasi[0]);
        data[33] = datastatusvalidasi[0];

        data[34] = (!teleponField.getText().equals("") ? teleponField.getText() : "0");
        data[35] = (!hpField.getText().equals("") ? hpField.getText() : "0");
        data[36] = "" + idlogaudit;
        data[37] = EmailField.getText();
        data[38] = FileFotoField.getText();
        data[39] = KodePendidikanTinggiField.getText();
        data[40] = (!BidangIlmuField.getText().equals("        ") ? BidangIlmuField.getText() : "0");
        data[41] = NamaKotaField.getText();
        data[42] = FaximileField.getText();
        data[43] = (!NIDNLAMAField.getText().equals("     ") ? NIDNLAMAField.getText() : "0");
        data[44] = (!NIDNHonorerField.getText().equals("     ") ? NIDNHonorerField.getText() : "0");
        data[45] = LastUpdateField.getText();
        data[46] = LastEditorField.getText();
        data[47] = StatusPegawaiField.getText();
        data[48] = (!NIDNHapusField.getText().equals("              ") ? NIDNHapusField.getText() : "0");
        data[49] = genUUID;
        return data;
    }

    public String[] dapatkanNilaiUntukUpdate() {
        String[] data = new String[jmlKolom];
        data[0] = NIPlamaField.getText();
        data[1] = NIPbaruField.getText();

        String datagolonganmentah = golongan.getText();
        System.out.println("data golongan mentah: " + datagolonganmentah);
        String[] datagolongan = datagolonganmentah.split(" ");
        System.out.println("data golongan yg dipilih: " + datagolongan[0]);
        data[2] = datagolongan[0];

        String datapangkatmentah = pangkat.getText();
        System.out.println("data pangkat mentah: " + datapangkatmentah);
        String[] datapangkat = datapangkatmentah.split(" ");
        System.out.println("data pangkat yg dipilih: " + datapangkat[0]);
        data[3] = datapangkat[0];

        data[4] = jabatanfungsionalField.getText();

        String datajabatanstrukturalmentah = jabatanstruktural.getText();
        System.out.println("data jabatanstruktural mentah: " + datajabatanstrukturalmentah);
        String[] datajabatanstruktural = datajabatanstrukturalmentah.split(" ");
        System.out.println("data jabatanstruktural yg dipilih: " + datajabatanstruktural[0]);
        data[5] = datajabatanstruktural[0];

        data[6] = kodeperguruantinggiField.getText();
        data[7] = kodeprogramstudi.getText();
        data[8] = kodefakultas.getText();
        data[9] = kodejurusan.getText();
        data[10] = namadosenField.getText();
        data[11] = nomorKTPField.getText();
        data[12] = gelarakademikdepanField.getText();
        data[13] = gelarakademikbelakangField.getText();
        data[14] = jeniskelamin.getText();
        data[15] = tempatlahirField.getText();

        kalendar = tanggallahirField.getCalendar();
        if (kalendar == null) {
            kalendar = Calendar.getInstance();
        }
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data13 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[16] = (data13 != null ? data13 : "");

        data[17] = statusaktif.getText();
        kalendar = mulaimasukdosenField.getCalendar();
        if (kalendar == null) {
            kalendar = Calendar.getInstance();
        }
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data18 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[18] = (data18 != null ? data18 : "");
        data[19] = mulaisemesterField.getText();
        data[20] = kodejenjangpendidikan.getText();
        data[21] = kodejabatanakademik.getText();
        data[22] = ikatankerja.getText();
        data[23] = aktadanijinmengajar.getText();
        data[24] = alamatField.getText();
        data[25] = kodekota.getText();
        data[26] = kodeprovinsi.getText();
        data[27] = kodeposField.getText();
        data[28] = kodenegara.getText();
        data[29] = nosertifikasidosenField.getText();
        kalendar = tglkeluarsertifikasidosenField.getCalendar();
        if (kalendar == null) {
            kalendar = Calendar.getInstance();
        }
        hari = kalendar.get(Calendar.DAY_OF_MONTH);
        bulan = namaBulan[kalendar.get(Calendar.MONTH)];
        tahun = kalendar.get(Calendar.YEAR);
        String data25 = tahun + "-" + kalendar.get(Calendar.MONTH) + "-" + hari;
        data[30] = (data25 != null ? data25 : "");
        data[31] = niraField.getText();
        data[32] = statusvalidasi.getText();
        data[33] = teleponField.getText();
        data[34] = hpField.getText();
        data[35] = "" + idlogaudit;
         data[36] = EmailField.getText();
        data[37] = FileFotoField.getText();
        data[38] = KodePendidikanTinggiField.getText();
        data[39] = (!BidangIlmuField.getText().equals("        ") ? BidangIlmuField.getText() : "0");
        data[40] = NamaKotaField.getText();
        data[41] = FaximileField.getText();
        data[42] = (!NIDNLAMAField.getText().equals("     ") ? NIDNLAMAField.getText() : "0");
        data[43] = (!NIDNHonorerField.getText().equals("     ") ? NIDNHonorerField.getText() : "0");
        data[44] = LastUpdateField.getText();
        data[45] = LastEditorField.getText();
        data[46] = StatusPegawaiField.getText();
        data[47] = (!NIDNHapusField.getText().equals("              ") ? NIDNHapusField.getText() : "0");
       
        data[48] = NIDNNUPField.getText();
        data[49] = genUUID;
        
        return data;
    }

    public String[] dapatkanNilaiUntukDelete() {
        String[] data = new String[1];
        data[0] = NIDNNUPField.getText();
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
        NIDNNUPLabel = new javax.swing.JLabel();
        NIPlamaLabel = new javax.swing.JLabel();
        NIPbaruLabel = new javax.swing.JLabel();
        golonganLabel = new javax.swing.JLabel();
        pangkatLabel = new javax.swing.JLabel();
        jabatanfungsionalLabel = new javax.swing.JLabel();
        jabatanstrukturalLabel = new javax.swing.JLabel();
        kodeperguruantinggiLabel = new javax.swing.JLabel();
        kodeprogramstudiLabel = new javax.swing.JLabel();
        kodefakultasLabel = new javax.swing.JLabel();
        kodejurusanLabel = new javax.swing.JLabel();
        namadosenLabel = new javax.swing.JLabel();
        nomorKTPLabel = new javax.swing.JLabel();
        gelarakademikdepanLabel = new javax.swing.JLabel();
        gelarakademikbelakangLabel = new javax.swing.JLabel();
        jeniskelaminLabel = new javax.swing.JLabel();
        tempatlahirLabel = new javax.swing.JLabel();
        tanggallahirLabel = new javax.swing.JLabel();
        statusaktifLabel = new javax.swing.JLabel();
        mulaimasukdosenLabel = new javax.swing.JLabel();
        mulaisemesterLabel = new javax.swing.JLabel();
        kodejenjangpendidikanLabel = new javax.swing.JLabel();
        kodejabatanakademikLabel = new javax.swing.JLabel();
        ikatankerjaLabel = new javax.swing.JLabel();
        aktadanijinmengajarLabel = new javax.swing.JLabel();
        alamatLabel = new javax.swing.JLabel();
        kodekotaLabel = new javax.swing.JLabel();
        kodeprovinsiLabel = new javax.swing.JLabel();
        kodeposLabel = new javax.swing.JLabel();
        kodenegaraLabel = new javax.swing.JLabel();
        nosertifikasidosenLabel = new javax.swing.JLabel();
        tglkeluarsertifikasidosenLabel = new javax.swing.JLabel();
        niraLabel = new javax.swing.JLabel();
        statusvalidasiLabel = new javax.swing.JLabel();
        teleponLabel = new javax.swing.JLabel();
        hpLabel = new javax.swing.JLabel();
        IDlogauditLabel = new javax.swing.JLabel();
        IDlogauditLabel1 = new javax.swing.JLabel();
        IDlogauditLabel2 = new javax.swing.JLabel();
        IDlogauditLabel3 = new javax.swing.JLabel();
        IDlogauditLabel4 = new javax.swing.JLabel();
        IDlogauditLabel5 = new javax.swing.JLabel();
        IDlogauditLabel6 = new javax.swing.JLabel();
        IDlogauditLabel7 = new javax.swing.JLabel();
        IDlogauditLabel8 = new javax.swing.JLabel();
        IDlogauditLabel9 = new javax.swing.JLabel();
        IDlogauditLabel10 = new javax.swing.JLabel();
        IDlogauditLabel11 = new javax.swing.JLabel();
        IDlogauditLabel12 = new javax.swing.JLabel();
        IDlogauditLabel13 = new javax.swing.JLabel();
        panelTextField = new javax.swing.JPanel();
        NIDNNUPField = new javax.swing.JFormattedTextField();
        NIPlamaField = new javax.swing.JFormattedTextField();
        NIPbaruField = new javax.swing.JFormattedTextField();
        golonganField = new javax.swing.JComboBox();
        pangkatField = new javax.swing.JComboBox();
        jabatanfungsionalField = new javax.swing.JTextField();
        jabatanstrukturalField = new javax.swing.JComboBox();
        kodeperguruantinggiField = new javax.swing.JFormattedTextField();
        kodeprogramstudiField = new javax.swing.JComboBox();
        kodefakultasField = new javax.swing.JComboBox();
        kodejurusanField = new javax.swing.JComboBox();
        namadosenField = new javax.swing.JTextField();
        nomorKTPField = new javax.swing.JFormattedTextField();
        gelarakademikdepanField = new javax.swing.JTextField();
        gelarakademikbelakangField = new javax.swing.JTextField();
        jeniskelaminField = new javax.swing.JComboBox();
        tempatlahirField = new javax.swing.JTextField();
        tanggallahirField = new com.toedter.calendar.JDateChooser();
        statusaktifField = new javax.swing.JComboBox();
        mulaimasukdosenField = new com.toedter.calendar.JDateChooser();
        mulaisemesterField = new javax.swing.JFormattedTextField();
        kodejenjangpendidikanField = new javax.swing.JComboBox();
        kodejabatanakademikField = new javax.swing.JComboBox();
        ikatankerjaField = new javax.swing.JComboBox();
        aktadanijinmengajarField = new javax.swing.JComboBox();
        alamatField = new javax.swing.JTextField();
        kodekotaField = new javax.swing.JComboBox();
        kodeprovinsiField = new javax.swing.JComboBox();
        kodeposField = new javax.swing.JFormattedTextField();
        kodenegaraField = new javax.swing.JComboBox();
        nosertifikasidosenField = new javax.swing.JFormattedTextField();
        tglkeluarsertifikasidosenField = new com.toedter.calendar.JDateChooser();
        niraField = new javax.swing.JFormattedTextField();
        statusvalidasiField = new javax.swing.JComboBox();
        teleponField = new javax.swing.JFormattedTextField();
        hpField = new javax.swing.JFormattedTextField();
        IDlogauditField = new javax.swing.JFormattedTextField();
        EmailField = new javax.swing.JTextField();
        FileFotoField = new javax.swing.JTextField();
        KodePendidikanTinggiField = new javax.swing.JTextField();
        BidangIlmuField = new javax.swing.JTextField();
        NamaKotaField = new javax.swing.JTextField();
        FaximileField = new javax.swing.JTextField();
        NIDNLAMAField = new javax.swing.JTextField();
        NIDNHonorerField = new javax.swing.JTextField();
        LastUpdateField = new javax.swing.JTextField();
        LastEditorField = new javax.swing.JTextField();
        StatusPegawaiField = new javax.swing.JTextField();
        NIDNHapusField = new javax.swing.JTextField();
        gulungTabel = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        panelKontrol = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        panelKontrolSIUD = new javax.swing.JPanel();
        panelKontrolNavigasiRecord = new javax.swing.JPanel();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jSplitPane2.setDividerLocation(700);
        jSplitPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        panelAtribut.setLayout(new java.awt.BorderLayout());

        panelLabel.setLayout(new java.awt.GridLayout(50, 0));

        NIDNNUPLabel.setText("NIDN/NUP");
        panelLabel.add(NIDNNUPLabel);

        NIPlamaLabel.setText("NIP Lama:");
        panelLabel.add(NIPlamaLabel);

        NIPbaruLabel.setText("NIP Baru:");
        panelLabel.add(NIPbaruLabel);

        golonganLabel.setText("Golongan:");
        panelLabel.add(golonganLabel);

        pangkatLabel.setText("Pangkat:");
        panelLabel.add(pangkatLabel);

        jabatanfungsionalLabel.setText("Jabatan Fungsional:");
        panelLabel.add(jabatanfungsionalLabel);

        jabatanstrukturalLabel.setText("Jabatan Struktural:");
        panelLabel.add(jabatanstrukturalLabel);

        kodeperguruantinggiLabel.setText("Kode Perguruan Tinggi:");
        panelLabel.add(kodeperguruantinggiLabel);

        kodeprogramstudiLabel.setText("Kode Program Studi:");
        panelLabel.add(kodeprogramstudiLabel);

        kodefakultasLabel.setText("Kode Fakultas:");
        panelLabel.add(kodefakultasLabel);

        kodejurusanLabel.setText("Kode Jurusan:");
        panelLabel.add(kodejurusanLabel);

        namadosenLabel.setText("Nama Dosen:");
        panelLabel.add(namadosenLabel);

        nomorKTPLabel.setText("Nomor KTP:");
        panelLabel.add(nomorKTPLabel);

        gelarakademikdepanLabel.setText("Gelar Akademik Depan:");
        panelLabel.add(gelarakademikdepanLabel);

        gelarakademikbelakangLabel.setText("Gelar Akademik Belakang:");
        panelLabel.add(gelarakademikbelakangLabel);

        jeniskelaminLabel.setText("Jenis Kelamin:");
        panelLabel.add(jeniskelaminLabel);

        tempatlahirLabel.setText("Tempat Lahir:");
        panelLabel.add(tempatlahirLabel);

        tanggallahirLabel.setText("Tanggal Lahir:");
        panelLabel.add(tanggallahirLabel);

        statusaktifLabel.setText("Status Aktif:");
        panelLabel.add(statusaktifLabel);

        mulaimasukdosenLabel.setText("Mulai Masuk Dosen:");
        panelLabel.add(mulaimasukdosenLabel);

        mulaisemesterLabel.setText("Mulai Semester:");
        panelLabel.add(mulaisemesterLabel);

        kodejenjangpendidikanLabel.setText("Kode Jenjang Pendidikan:");
        panelLabel.add(kodejenjangpendidikanLabel);

        kodejabatanakademikLabel.setText("Kode Jabatan Akademik:");
        panelLabel.add(kodejabatanakademikLabel);

        ikatankerjaLabel.setText("Ikatan Kerja:");
        panelLabel.add(ikatankerjaLabel);

        aktadanijinmengajarLabel.setText("Akta dan Ijin Mengajar:");
        panelLabel.add(aktadanijinmengajarLabel);

        alamatLabel.setText("Alamat:");
        panelLabel.add(alamatLabel);

        kodekotaLabel.setText("Kode Kota:");
        panelLabel.add(kodekotaLabel);

        kodeprovinsiLabel.setText("Kode Provinsi:");
        panelLabel.add(kodeprovinsiLabel);

        kodeposLabel.setText("Kode Pos:");
        panelLabel.add(kodeposLabel);

        kodenegaraLabel.setText("Kode Negara:");
        panelLabel.add(kodenegaraLabel);

        nosertifikasidosenLabel.setText("No Sertifikasi Dosen:");
        panelLabel.add(nosertifikasidosenLabel);

        tglkeluarsertifikasidosenLabel.setText("Tgl Keluar Sertifikasi Dosen:");
        panelLabel.add(tglkeluarsertifikasidosenLabel);

        niraLabel.setText("Nira:");
        panelLabel.add(niraLabel);

        statusvalidasiLabel.setText("Status Validasi:");
        panelLabel.add(statusvalidasiLabel);

        teleponLabel.setText("Telepon:");
        panelLabel.add(teleponLabel);

        hpLabel.setText("Hp:");
        panelLabel.add(hpLabel);

        IDlogauditLabel.setText("IDlogaudit:");
        panelLabel.add(IDlogauditLabel);

        IDlogauditLabel1.setText("Email:");
        panelLabel.add(IDlogauditLabel1);

        IDlogauditLabel2.setText("File Foto:");
        panelLabel.add(IDlogauditLabel2);

        IDlogauditLabel3.setText("Kode Pendidikan Tinggi:");
        panelLabel.add(IDlogauditLabel3);

        IDlogauditLabel4.setText("Bidang Ilmu:");
        panelLabel.add(IDlogauditLabel4);

        IDlogauditLabel5.setText("Nama Kota:");
        panelLabel.add(IDlogauditLabel5);

        IDlogauditLabel6.setText("Faximile:");
        panelLabel.add(IDlogauditLabel6);

        IDlogauditLabel7.setText("NIDN Lama:");
        panelLabel.add(IDlogauditLabel7);

        IDlogauditLabel8.setText("NIDN Honorer:");
        panelLabel.add(IDlogauditLabel8);

        IDlogauditLabel9.setText("Last Update:");
        panelLabel.add(IDlogauditLabel9);

        IDlogauditLabel10.setText("Last Editor:");
        panelLabel.add(IDlogauditLabel10);

        IDlogauditLabel11.setText("Status Pegawai:");
        panelLabel.add(IDlogauditLabel11);

        IDlogauditLabel12.setText("NIDN Hapus:");
        panelLabel.add(IDlogauditLabel12);

        IDlogauditLabel13.setText("UUID:");
        panelLabel.add(IDlogauditLabel13);

        panelAtribut.add(panelLabel, java.awt.BorderLayout.WEST);

        panelTextField.setLayout(new java.awt.GridLayout(50, 0));

        try {
            NIDNNUPField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        NIDNNUPField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIDNNUPFieldKeyPressed(evt);
            }
        });
        panelTextField.add(NIDNNUPField);

        try {
            NIPlamaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(NIPlamaField);

        try {
            NIPbaruField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##################")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(NIPbaruField);

        panelTextField.add(golonganField);

        panelTextField.add(pangkatField);
        panelTextField.add(jabatanfungsionalField);

        panelTextField.add(jabatanstrukturalField);

        try {
            kodeperguruantinggiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodeperguruantinggiField);

        panelTextField.add(kodeprogramstudiField);

        panelTextField.add(kodefakultasField);

        panelTextField.add(kodejurusanField);
        panelTextField.add(namadosenField);

        try {
            nomorKTPField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#################")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(nomorKTPField);
        panelTextField.add(gelarakademikdepanField);
        panelTextField.add(gelarakademikbelakangField);

        panelTextField.add(jeniskelaminField);
        panelTextField.add(tempatlahirField);
        panelTextField.add(tanggallahirField);

        panelTextField.add(statusaktifField);
        panelTextField.add(mulaimasukdosenField);

        try {
            mulaisemesterField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(mulaisemesterField);

        panelTextField.add(kodejenjangpendidikanField);

        panelTextField.add(kodejabatanakademikField);

        panelTextField.add(ikatankerjaField);

        panelTextField.add(aktadanijinmengajarField);
        panelTextField.add(alamatField);

        panelTextField.add(kodekotaField);

        panelTextField.add(kodeprovinsiField);

        try {
            kodeposField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(kodeposField);

        panelTextField.add(kodenegaraField);

        try {
            nosertifikasidosenField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        panelTextField.add(nosertifikasidosenField);
        panelTextField.add(tglkeluarsertifikasidosenField);

        niraField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##############################"))));
        panelTextField.add(niraField);

        panelTextField.add(statusvalidasiField);

        teleponField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##############"))));
        panelTextField.add(teleponField);

        hpField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#############"))));
        panelTextField.add(hpField);

        IDlogauditField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        panelTextField.add(IDlogauditField);
        panelTextField.add(EmailField);
        panelTextField.add(FileFotoField);
        panelTextField.add(KodePendidikanTinggiField);
        panelTextField.add(BidangIlmuField);
        panelTextField.add(NamaKotaField);
        panelTextField.add(FaximileField);
        panelTextField.add(NIDNLAMAField);
        panelTextField.add(NIDNHonorerField);
        panelTextField.add(LastUpdateField);
        panelTextField.add(LastEditorField);
        panelTextField.add(StatusPegawaiField);
        panelTextField.add(NIDNHapusField);

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
        jSplitPane1.setLeftComponent(panelKontrolSIUD);

        panelKontrolNavigasiRecord.setLayout(new java.awt.GridLayout(1, 4));
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
        String query = "insert into " + namaTabel + " "
                + " values(?,?,?,?,?,?,?,?,?,?, "
                + "?,?,?,?,?,?,?,?,?,?, "
                + "?,?,?,?,?,?,?,?,?,?, "
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?)";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukInsert() );
        try{
        int hasil = model.insertAktifitasDOsen(query,
                dapatkanNilaiUntukInsert());
        if (hasil == 1) {
            query = "select * from " + namaTabel + " where \"Kode_perguruan_tinggi\" = "+Kodept;
            kon.selectData(query);
            tampilkanDataKeTabel(kon.dapatkanData(), ubahLabelKeSentenceCase(kon.dapatkanKolom()));

            //insert data ke TREF_LOG_AUDIT
            query = "insert into pdpt_dev.\"TREF_LOG_AUDIT\" "
                    + " values(?,?,?,?,?,?)";
            hasil = kon.insertUpdateDeleteData(query,
                    dapatkanNilaiUntukInsertKeLogAudit("INSERT",
                    namaTabel,
                    kon.dapatkanQueryUtkStatement(namaTabel,
                    dapatkanNilaiUntukInsert())));
            persiapanEntriDataBaru();
        }
        }catch(Exception e){
            System.out.println(""+e);
        }
    }//GEN-LAST:event_buttonInsertActionPerformed

    private void buttonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUpdateActionPerformed
        // TODO add your handling code here:
        initIDLogAudit();
        String query = "update " + namaTabel + " "
                + " SET \"NIP_lama\"=?, "
                + "\"NIP_baru\"=?, "
                + "\"Golongan\"=?, "
                + "\"Pangkat\"=?, "
                + "\"Jabatan_fungsional\"=?, "
                + "\"Jabatan_struktural\"=?, "
                + "\"Kode_perguruan_tinggi\"=?, "
                + "\"Kode_program_studi\"=?, "
                + "\"Kode_fakultas\"=?, "
                + "\"Kode_jurusan\"=?, "
                + "\"Nama_dosen\"=?, "
                + "\"Nomor_KTP\"=?, "
                + "\"Gelar_akademik_depan\"=?, "
                + "\"Gelar_akademik_belakang\"=?, "
                + "\"Jenis_kelamin\"=?, "
                + "\"Tempat_lahir\"=?, "
                + "\"Tanggal_lahir\"=?, "
                + "\"Status_aktif\"=?, "
                + "\"Mulai_masuk_dosen\"=?, "
                + "\"Mulai_semester\"=?, "
                + "\"Kode_jenjang_pendidikan\"=?, "
                + "\"Kode_jabatan_akademik\"=?, "
                + "\"Ikatan_kerja\"=?, "
                + "\"Akta_dan_ijin_mengajar\"=?, "
                + "\"Alamat\"=?, "
                + "\"Kode_kota\"=?, "
                + "\"Kode_provinsi\"=?, "
                + "\"Kode_pos\"=?, "
                + "\"Kode_negara\"=?, "
                + "\"No_sertifikasi_dosen\"=?, "
                + "\"Tgl_keluar_sertifikasi_dosen\"=?, "
                + "\"NIRA\"=?, "
                + "\"Status_validasi\"=?, "
                + "\"Telepon\"=?, "
                + "\"HP\"=?, "
                + "\"ID_log_audit\"=?, "
                + "\"Email\"=?, "
                + "\"File_foto\"=?, "
                + "\"Kode_pendidikan_tertinggi\"=?, "
                + "\"Bidang_ilmu\"=?, "
                + "\"Nama_kota\"=?, "
                + "\"Faximile\"=?, "
                + "\"NIDN_lama\"=?, "
                + "\"NIDN_honorer\"=?, "
                + "\"Last_update\"=?, "
                + "\"Last_editor\"=?, "
                + "\"Status_pegawai\"=?, "
                + "\"NIDN_hapus\"=? "
                + " WHERE \"NIDN\"=? ";
//        int hasil = kon.insertUpdateDeleteData(query,
//            dapatkanNilaiUntukUpdate() );
        int hasil = model.updateAktifitasDOsen(query,
                dapatkanNilaiUntukUpdate());
        if (hasil == 1) {
            query = "select * from " + namaTabel + " where \"Kode_perguruan_tinggi\" = "+Kodept;
            kon.selectData(query);
            tampilkanDataKeTabel(kon.dapatkanData(), ubahLabelKeSentenceCase(kon.dapatkanKolom()));

            //insert data ke TREF_LOG_AUDIT
            query = "insert into pdpt_dev.\"TREF_LOG_AUDIT\" "
                    + " values(?,?,?,?,?,?)";
            hasil = kon.insertUpdateDeleteData(query,
                    dapatkanNilaiUntukInsertKeLogAudit("UPDATE",
                    namaTabel,
                    kon.dapatkanQueryUtkUpdateStatement(namaTabel,
                    dapatkanNilaiUntukUpdate())));
        }
    }//GEN-LAST:event_buttonUpdateActionPerformed

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        // TODO add your handling code here:
        initIDLogAudit();
        String query = "delete from " + namaTabel + " WHERE "
                + " \"NIDN\"=? ";

        String[] pkDataYgDihapus = dapatkanNilaiUntukDelete();

        int hasil = model.delete(query,
                dapatkanNilaiUntukDelete());
        if (hasil == 1) {
            query = "select * from " + namaTabel+ " where \"Kode_perguruan_tinggi\" = "+Kodept;
            kon.selectData(query);
            tampilkanDataKeTabel(kon.dapatkanData(), ubahLabelKeSentenceCase(kon.dapatkanKolom()));
            persiapanEntriDataBaru();

            //insert data ke TREF_LOG_AUDIT
            query = "insert into pdpt_dev.\"TREF_LOG_AUDIT\" "
                    + " values(?,?,?,?,?,?)";
            hasil = kon.insertUpdateDeleteData(query,
                    dapatkanNilaiUntukInsertKeLogAudit("DELETE",
                    namaTabel,
                    kon.dapatkanQueryUtkDeleteStatement(namaTabel,
                    pkDataYgDihapus)));
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
        if (barisYgDipilih > 0) {
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

    private void NIDNNUPFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIDNNUPFieldKeyPressed
        // TODO add your handling code here:
        System.out.println("di enter dosen");
    }//GEN-LAST:event_NIDNNUPFieldKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BidangIlmuField;
    private javax.swing.JTextField EmailField;
    private javax.swing.JTextField FaximileField;
    private javax.swing.JTextField FileFotoField;
    private javax.swing.JFormattedTextField IDlogauditField;
    private javax.swing.JLabel IDlogauditLabel;
    private javax.swing.JLabel IDlogauditLabel1;
    private javax.swing.JLabel IDlogauditLabel10;
    private javax.swing.JLabel IDlogauditLabel11;
    private javax.swing.JLabel IDlogauditLabel12;
    private javax.swing.JLabel IDlogauditLabel13;
    private javax.swing.JLabel IDlogauditLabel2;
    private javax.swing.JLabel IDlogauditLabel3;
    private javax.swing.JLabel IDlogauditLabel4;
    private javax.swing.JLabel IDlogauditLabel5;
    private javax.swing.JLabel IDlogauditLabel6;
    private javax.swing.JLabel IDlogauditLabel7;
    private javax.swing.JLabel IDlogauditLabel8;
    private javax.swing.JLabel IDlogauditLabel9;
    private javax.swing.JTextField KodePendidikanTinggiField;
    private javax.swing.JTextField LastEditorField;
    private javax.swing.JTextField LastUpdateField;
    private javax.swing.JTextField NIDNHapusField;
    private javax.swing.JTextField NIDNHonorerField;
    private javax.swing.JTextField NIDNLAMAField;
    private javax.swing.JFormattedTextField NIDNNUPField;
    private javax.swing.JLabel NIDNNUPLabel;
    private javax.swing.JFormattedTextField NIPbaruField;
    private javax.swing.JLabel NIPbaruLabel;
    private javax.swing.JFormattedTextField NIPlamaField;
    private javax.swing.JLabel NIPlamaLabel;
    private javax.swing.JTextField NamaKotaField;
    private javax.swing.JTextField StatusPegawaiField;
    private javax.swing.JComboBox aktadanijinmengajarField;
    private javax.swing.JLabel aktadanijinmengajarLabel;
    private javax.swing.JTextField alamatField;
    private javax.swing.JLabel alamatLabel;
	 private Widget.Button buttonBaru;
    private Widget.Button buttonDelete;
    private Widget.Button buttonFirst;
    private Widget.Button buttonInsert;
    private Widget.Button buttonLast;
    private Widget.Button buttonNext;
    private Widget.Button buttonPrev;
    private Widget.Button buttonUpdate;
    private javax.swing.JTextField gelarakademikbelakangField;
    private javax.swing.JLabel gelarakademikbelakangLabel;
    private javax.swing.JTextField gelarakademikdepanField;
    private javax.swing.JLabel gelarakademikdepanLabel;
    private javax.swing.JComboBox golonganField;
    private javax.swing.JLabel golonganLabel;
    private javax.swing.JScrollPane gulungPanelAtribut;
    private javax.swing.JScrollPane gulungTabel;
    private javax.swing.JFormattedTextField hpField;
    private javax.swing.JLabel hpLabel;
    private javax.swing.JComboBox ikatankerjaField;
    private javax.swing.JLabel ikatankerjaLabel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTextField jabatanfungsionalField;
    private javax.swing.JLabel jabatanfungsionalLabel;
    private javax.swing.JComboBox jabatanstrukturalField;
    private javax.swing.JLabel jabatanstrukturalLabel;
    private javax.swing.JComboBox jeniskelaminField;
    private javax.swing.JLabel jeniskelaminLabel;
    private javax.swing.JComboBox kodefakultasField;
    private javax.swing.JLabel kodefakultasLabel;
    private javax.swing.JComboBox kodejabatanakademikField;
    private javax.swing.JLabel kodejabatanakademikLabel;
    private javax.swing.JComboBox kodejenjangpendidikanField;
    private javax.swing.JLabel kodejenjangpendidikanLabel;
    private javax.swing.JComboBox kodejurusanField;
    private javax.swing.JLabel kodejurusanLabel;
    private javax.swing.JComboBox kodekotaField;
    private javax.swing.JLabel kodekotaLabel;
    private javax.swing.JComboBox kodenegaraField;
    private javax.swing.JLabel kodenegaraLabel;
    private javax.swing.JFormattedTextField kodeperguruantinggiField;
    private javax.swing.JLabel kodeperguruantinggiLabel;
    private javax.swing.JFormattedTextField kodeposField;
    private javax.swing.JLabel kodeposLabel;
    private javax.swing.JComboBox kodeprogramstudiField;
    private javax.swing.JLabel kodeprogramstudiLabel;
    private javax.swing.JComboBox kodeprovinsiField;
    private javax.swing.JLabel kodeprovinsiLabel;
    private com.toedter.calendar.JDateChooser mulaimasukdosenField;
    private javax.swing.JLabel mulaimasukdosenLabel;
    private javax.swing.JFormattedTextField mulaisemesterField;
    private javax.swing.JLabel mulaisemesterLabel;
    private javax.swing.JTextField namadosenField;
    private javax.swing.JLabel namadosenLabel;
    private javax.swing.JFormattedTextField niraField;
    private javax.swing.JLabel niraLabel;
    private javax.swing.JFormattedTextField nomorKTPField;
    private javax.swing.JLabel nomorKTPLabel;
    private javax.swing.JFormattedTextField nosertifikasidosenField;
    private javax.swing.JLabel nosertifikasidosenLabel;
    private javax.swing.JPanel panelAtribut;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelKontrolNavigasiRecord;
    private javax.swing.JPanel panelKontrolSIUD;
    private javax.swing.JPanel panelLabel;
    private javax.swing.JPanel panelTextField;
    private javax.swing.JComboBox pangkatField;
    private javax.swing.JLabel pangkatLabel;
    private javax.swing.JComboBox statusaktifField;
    private javax.swing.JLabel statusaktifLabel;
    private javax.swing.JComboBox statusvalidasiField;
    private javax.swing.JLabel statusvalidasiLabel;
    private javax.swing.JTable tabel;
    private com.toedter.calendar.JDateChooser tanggallahirField;
    private javax.swing.JLabel tanggallahirLabel;
    private javax.swing.JFormattedTextField teleponField;
    private javax.swing.JLabel teleponLabel;
    private javax.swing.JTextField tempatlahirField;
    private javax.swing.JLabel tempatlahirLabel;
    private com.toedter.calendar.JDateChooser tglkeluarsertifikasidosenField;
    private javax.swing.JLabel tglkeluarsertifikasidosenLabel;
    // End of variables declaration//GEN-END:variables
}
