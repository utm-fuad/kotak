/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validasi.form;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Window;
import java.awt.FocusTraversalPolicy;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;
import metromenu.HomePage;
import validasi.SQLiteConnection;
import validasi.TabelImport;
import validasi.cek.CekTmstBadanHukum;
import validasi.cek.CekTmstDosen;
import validasi.cek.CekTmstFakultas;
import validasi.cek.CekTmstFasPenunjangAkademik;
import validasi.cek.CekTmstJurusan;
import validasi.cek.CekTmstKerjasamaPTLN;
import validasi.cek.CekTmstLaboratorium;
import validasi.cek.CekTmstMahasiswa;
import validasi.cek.CekTmstMataKuliah;
import validasi.cek.CekTmstPegawai;
import validasi.cek.CekTmstPerguruanTinggi;
import validasi.cek.CekTmstProgramStudi;
import validasi.cek.CekTmstPustakaPT;
import validasi.cek.CekTmstSaranaPT;
import validasi.cek.CekTranAktivitasMengajarDosen;
import validasi.cek.CekTranAnggotaPenelitiAhli;
import validasi.cek.CekTranAnggotaPenelitiDukung;
import validasi.cek.CekTranAnggotaPenelitianDosen;
import validasi.cek.CekTranBobotNilai;
import validasi.cek.CekTranDayaTampung;
import validasi.cek.CekTranFteDosen;
import validasi.cek.CekTranHKI;
import validasi.cek.CekTranKerjasamaInstansi;
import validasi.cek.CekTranKuliahMhs;
import validasi.cek.CekTranNilaiSemesterMhs;
import validasi.cek.CekTranPindahanMhsAsing;
import validasi.cek.CekTranPrestasiMhs;
import validasi.komponen.PanelTabel;
import validasi.utilisasi.ColumnResizer;
import validasi.utilisasi.ResultSetToDefaultTableModel;

/**
 *
 * @author asus
 */
public class TestValidasi extends javax.swing.JPanel {
    private Connection conn;
    private CekTmstBadanHukum cekTmstBadanHukum = new CekTmstBadanHukum();
    private CekTmstDosen cekTmstDosen = new CekTmstDosen();
    private CekTmstFakultas cekTmstFakultas = new CekTmstFakultas();
    private CekTmstFasPenunjangAkademik cekTmstFasPenunjangAkademik = new CekTmstFasPenunjangAkademik();
    private CekTmstMataKuliah cekTmstMataKuliah = new CekTmstMataKuliah();
    private CekTmstPegawai cekTmstPegawai = new CekTmstPegawai();
    private CekTmstPerguruanTinggi cekTmstPerguruanTinggi =new CekTmstPerguruanTinggi();
    private CekTmstProgramStudi cekTmstProgramStudi =new CekTmstProgramStudi();
    private CekTmstPustakaPT cekTmstPustakaPT =new CekTmstPustakaPT();
    private CekTmstSaranaPT cekTmstSaranaPT =new CekTmstSaranaPT();
    
    private CekTmstJurusan cekTmstJurusan = new CekTmstJurusan();
    private CekTmstKerjasamaPTLN cekTmstKerjasamaPTLN = new CekTmstKerjasamaPTLN();
    private CekTmstLaboratorium cekTmstLaboratorium = new CekTmstLaboratorium();
    private CekTmstMahasiswa cekTmstMahasiswa = new CekTmstMahasiswa();
    private String tab;
    private MyKeyListener kListener=new MyKeyListener();
    private HomePage homePage;
    private CekTranAktivitasMengajarDosen cekTranAktivitasMengajarDosen = new CekTranAktivitasMengajarDosen();
    private CekTranKuliahMhs cekTranKuliahMhs = new CekTranKuliahMhs();
    private CekTranNilaiSemesterMhs cekTranNilaiSemesterMhs = new CekTranNilaiSemesterMhs();
    private CekTranAnggotaPenelitiAhli cekTranAnggotaPenelitiAhli = new CekTranAnggotaPenelitiAhli();
    private CekTranAnggotaPenelitiDukung cekTranAnggotaPenelitiDukung = new CekTranAnggotaPenelitiDukung();
    private CekTranAnggotaPenelitianDosen cekTranAnggotaPenelitianDosen = new CekTranAnggotaPenelitianDosen();
    private CekTranBobotNilai cekTranBobotNilai = new CekTranBobotNilai();
    private CekTranDayaTampung cekTranDayaTampung = new CekTranDayaTampung();
    private CekTranFteDosen cekTranFteDosen = new CekTranFteDosen();
    private CekTranHKI cekTranHKI = new CekTranHKI();
    private CekTranKerjasamaInstansi cekTranKerjasamaInstansi = new CekTranKerjasamaInstansi();
    private CekTranPindahanMhsAsing cekTranPindahanMhsAsing = new CekTranPindahanMhsAsing();
    private CekTranPrestasiMhs cekTranPrestasiMhs = new CekTranPrestasiMhs();

    /**
     * Creates new form TestValidasi
     */
    public TestValidasi(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
    }
    
    private void initDaftarTabel() {
        List<String> a = new TabelImport().getTableList();
//        DefaultListModel defModel = new DefaultListModel();
//        for(int i=0; i<a.size(); i++){
//            defModel.addElement(a.get(i));
//        }
//        jList1.setModel(defModel);
        for (int i = 0; i < a.size(); i++) {
            ((DefaultTableModel) jTable1.getModel()).addRow(new Object[]{
                a.get(i),
                false
            });
        }
        if (jTable1.getRowCount() > 0) {
            jTable1.setRowSelectionInterval(0, 0);
            ColumnResizer.adjustColumnPreferredWidths(jTable1);
        }
    }
    
    private String getUpdateCriteria(){
        String s="";
        try{
            JTable table=((PanelTabel) jTabbedPane1.getComponentAt(jTabbedPane1.getSelectedIndex())).getTable();
            int iRow=table.getSelectedRow();
            int iCol=table.getSelectedColumn();
            TableColumnModel colModel=table.getColumnModel();
            
            String namaTable=((PanelTabel) jTabbedPane1.getComponentAt(jTabbedPane1.getSelectedIndex())).getNamaTabel();
            ResultSet rs=conn.createStatement().executeQuery("select column_name from c_table_key "
                    + "where table_name='"+namaTable+"' ");
            
            String namaKolom="";
            while(rs.next()){
                namaKolom=rs.getString("column_name");
                if(table.getValueAt(iRow, iCol) instanceof String ||table.getValueAt(iRow, iCol) instanceof Date)
                    s=s.length()>0? " and ": " "+
                    " "+namaKolom+ "='"+table.getValueAt(iRow, colModel.getColumnIndex(namaKolom)).toString()+"' ";
                else if(table.getValueAt(iRow, iCol) instanceof Integer || table.getValueAt(iRow, iCol) instanceof Double || table.getValueAt(iRow, iCol) instanceof Float)
                    s=s.length()>0? " and ": " "+
                    " "+namaKolom+ "="+table.getValueAt(iRow, colModel.getColumnIndex(namaKolom)).toString()+" ";
            }
            return s.length()>0? " where "+s : " ";
        }catch(SQLException se){
            JOptionPane.showMessageDialog(this, se.getMessage());
        }
        return s;
    }
    
    public TestValidasi() {
        initComponents();
        initDaftarTabel();
        conn = new SQLiteConnection().getConn();
        jEditorPane1.setEditable(false);
        jEditorPane1.addHyperlinkListener(createHyperLinkListener());
        jEditorPane1.getDocument().putProperty("Ignore-Charset", "true");  // this line makes no difference either way
        jEditorPane1.setContentType("text/html");

        jTable1.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getColumn() == jTable1.getColumnModel().getColumnIndex("Pilih") && e.getType() == TableModelEvent.UPDATE) {
                    int iRow = jTable1.getSelectedRow();
//                    if (iRow < 0) {
//                        return;
//                    }

                    if ((Boolean) jTable1.getValueAt(iRow, jTable1.getColumnModel().getColumnIndex("Pilih")) == true) {
                        String namaTabel = jTable1.getValueAt(iRow, jTable1.getColumnModel().getColumnIndex("Tabel")).toString();
                        if (cekIndexTab(namaTabel) < 0) {
                            tambahTabPanel(namaTabel);
                        }
                    } else {
                        removePanel();
                    }
                }
            }
        });
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(TestValidasi.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            Logger.getLogger(TestValidasi.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(TestValidasi.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedLookAndFeelException ex) {
//            Logger.getLogger(TestValidasi.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private int cekIndexTab(String namaTab) {
        int k = -1;
        for (int i = 0; i < jTabbedPane1.getTabCount(); i++) {
            if (jTabbedPane1.getTitleAt(i).equalsIgnoreCase(namaTab)) {
                k = i;
                return k;
            }
        }
        return k;
    }

    private void removePanel() {
        for (int i = 0; i < jTabbedPane1.getTabCount(); i++) {
            String namaTabel = jTable1.getValueAt(jTable1.getSelectedRow(), jTable1.getColumnModel().getColumnIndex("Tabel")).toString();
            if (jTabbedPane1.getTitleAt(i).equalsIgnoreCase(namaTabel)) {
                jTabbedPane1.remove(i);
            }
        }
    }

    private void tambahTabPanel(String namaTabel) {
        try {
            PanelTabel panel = new PanelTabel();
            ResultSet rs = conn.createStatement().executeQuery("select * from " + namaTabel);
            panel.setTableModel(new ResultSetToDefaultTableModel(rs).getModel());
            panel.setNamaTabelDb(namaTabel);
            panel.setConn(conn);
            jTabbedPane1.addTab(namaTabel, panel);
            
            for(int i=0; i<panel.getTable().getColumnCount(); i++){
                panel.getTable().getColumnModel().getColumn(i).setCellEditor(new MyTableCellEditor());
            }
            jTabbedPane1.setSelectedIndex(jTabbedPane1.getTabCount() - 1);
            //repaint();

        } catch (SQLException ex) {
            Logger.getLogger(TestValidasi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jalankan() {
        try {
            if(jTabbedPane1.getTabCount() ==0){
                JOptionPane.showMessageDialog(this, "Tidak ada data yang akan divalidasi!");
            }
            String message = "<html>";
            //this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            homePage.jalankanGlassPanenya();
            
//            message += cekTabel("TMST_BADAN_HUKUM");
//            message += cekTabel("TMST_DOSEN");
//            message += cekTabel("TMST_FAKULTAS");
//            message += cekTabel("TMST_FAS_PENUNJANG_AKADEMIK");
            for (int i = 0; i < jTabbedPane1.getTabCount(); i++) {
                message += cekTabel(jTabbedPane1.getTitleAt(i));
            }

            message += "</html>";
            System.out.println("Message : " + message);
            jEditorPane1.setText(message);
            jEditorPane1.setCaretPosition(0);
            //this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            homePage.hentikanGlassPanenya();
        } catch (SQLException ex) {
            //this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            homePage.hentikanGlassPanenya();
            Logger.getLogger(TestValidasi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String cekTabel(String namaTabel) throws SQLException {
//        StatusValidasi statusValidasi=new StatusValidasi();
        String pesanAwal = "Tabel : <b>" + namaTabel + "</b> <br>";
        String [][] pesanError=new String[10000][100];
//        pesanError[0][1]="";
        String pesan = "";
        String cekPesan = "";
        int baris = 0;
        String namaKolom = "";
        String pesanPerBaris = "";
        ResultSet rs = conn.createStatement().executeQuery("select * from " + namaTabel);
        while (rs.next()) {
            baris++;
            pesanPerBaris = "";
            for (int col = 1; col <= rs.getMetaData().getColumnCount(); col++) {
                namaKolom = rs.getMetaData().getColumnName(col);
                System.out.println("Checking table : '" + namaTabel + "', baris " + baris + " column : '" + namaKolom + "'");
                if (namaTabel.equalsIgnoreCase("TMST_BADAN_HUKUM")) {
                    cekPesan = cekTmstBadanHukum.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TMST_DOSEN")) {
                    cekPesan = cekTmstDosen.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TMST_FAKULTAS")) {
                    cekPesan = cekTmstFakultas.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TMST_FAS_PENUNJANG_AKADEMIK")) {
                    cekPesan = cekTmstFasPenunjangAkademik.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TMST_MATA_KULIAH")) {
                    cekPesan = cekTmstMataKuliah.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TMST_PEGAWAI")) {
                    cekPesan = cekTmstPegawai.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TMST_PERGURUAN_TINGGI")) {
                    cekPesan = cekTmstPerguruanTinggi.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TMST_PROGRAM_STUDI")) {
                    cekPesan = cekTmstProgramStudi.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TMST_PUSTAKA_PT")) {
                    cekPesan = cekTmstPustakaPT.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TMST_SARANA_PT")) {
                    cekPesan = cekTmstSaranaPT.cekKolom(baris, namaKolom, rs.getObject(col));
                }  else if (namaTabel.equalsIgnoreCase("TMST_JURUSAN")) {
                    cekPesan = cekTmstJurusan.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TMST_KERJASAMA_PT_LN")) {
                    cekPesan = cekTmstKerjasamaPTLN.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TMST_LABORATORIUM")) {
                    cekPesan = cekTmstLaboratorium.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TMST_MAHASISWA")) {
                    cekPesan = cekTmstMahasiswa.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TRAN_AKTIVITAS_MENGAJAR_DOSEN")) {
                    cekPesan = cekTranAktivitasMengajarDosen.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TRAN_KULIAH_MHS")) {
                    cekPesan = cekTranKuliahMhs.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TRAN_NILAI_SEMESTER_MHS")) {
                    cekPesan = cekTranNilaiSemesterMhs.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TRAN_ANGGOTA_PENELITI_AHLI")) {
                    cekPesan = cekTranAnggotaPenelitiAhli.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TRAN_ANGGOTA_PENELITI_DUKUNG")) {
                    cekPesan = cekTranAnggotaPenelitiDukung.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TRAN_ANGGOTA_PENELITIAN_DOSEN")) {
                    cekPesan = cekTranAnggotaPenelitianDosen.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TRAN_BOBOT_NILAI")) {
                    cekPesan = cekTranBobotNilai.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TRAN_DAYA_TAMPUNG")) {
                    cekPesan = cekTranDayaTampung.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TRAN_FTE_DOSEN")) {
                    cekPesan = cekTranFteDosen.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TRAN_HKI")) {
                    cekPesan = cekTranHKI.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TRAN_KERJASAMA_INSTANSI")) {
                    cekPesan = cekTranKerjasamaInstansi.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TRAN_PINDAHAN_MHS_ASING")) {
                    cekPesan = cekTranPindahanMhsAsing.cekKolom(baris, namaKolom, rs.getObject(col));
                } else if (namaTabel.equalsIgnoreCase("TRAN_PRESTASI_MHS")) {
                    cekPesan = cekTranPindahanMhsAsing.cekKolom(baris, namaKolom, rs.getObject(col));
                }
                System.out.println("Panjang pesanError :"+pesanError.length);
                pesanError[baris-1][col-1]=cekPesan.length()> 0? "error": "";
                cekPesan = cekPesan.length() > 0 ? " <br>"
                        + "<font color=\"red\">"
                        + "<a href=tunjukan-tabke:"+cekIndexTab(namaTabel)
                        + ";row:" + (baris-1) + ";col:" + namaKolom + ";endCol>" + cekPesan + "</a></font>" : "";
                //cekPesan += (cekPesan.length() > 0 ? "<br>" : "");
                System.out.println("Cek Pesan :" + cekPesan);

                pesanPerBaris += cekPesan;
            }
            pesanPerBaris = (pesanPerBaris.length() > 0 ? "<p><b>Baris ke :" + baris + "</b>" : "") + pesanPerBaris;
            pesan += pesanPerBaris;
        }
        int panjang = pesan.length();
        pesan = pesanAwal + pesan;

        if (baris == 0) {
            pesan += "<b>TIDAK ADA DATA di tabel '" + namaTabel + "'</b><br>";
        } else {
           pesan += "<br>Jumlah data pada tabel <b>'"+namaTabel+"'</b> : <b>"+baris+ "</b>"+(panjang==0? ". Tidak ada kesalahan data": "")+"<br>";
        }

        rs.close();
        ((PanelTabel)jTabbedPane1.getComponentAt(cekIndexTab(namaTabel))).setPesanError(pesanError);
        
//        statusValidasi.setJmlBaris(baris);
//        statusValidasi.setErrorMessage(pesan);
        return pesan + "<br>";
    }

    public HyperlinkListener createHyperLinkListener() {
        return new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    System.err.println(e.getDescription());
                    String desc=e.getDescription();
                    if(desc.indexOf("tunjukan-")>=0){
                        String tab=desc.substring(desc.indexOf("tabke:")+6, desc.indexOf(";row"));
                        String row=desc.substring(desc.indexOf("row:")+4, desc.indexOf(";col"));
                        String col=desc.substring(desc.indexOf("col:")+4, desc.indexOf(";endCol"));
                        int colIdx=((PanelTabel)jTabbedPane1.getComponentAt(Integer.valueOf(tab))).getColumnIndex(col);
                        
//                        JOptionPane.showMessageDialog(null, 
//                                "Tab: "+tab+"\n"+
//                                "Row: "+row+"\n"+
//                                "Col : "+col+"\n"+
//                                "ColIdx: "+colIdx);
//                        
                        jTabbedPane1.setSelectedIndex(Integer.valueOf(tab));
                        ((PanelTabel)jTabbedPane1.getComponentAt(Integer.valueOf(tab))).getTable()
                                .changeSelection(Integer.valueOf(row), colIdx, false, false);
                    }
                }
            }
        };
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        chkCheckAll = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();

        setLayout(new java.awt.BorderLayout());

        jButton1.setText("Test Validasi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tabel", "Pilih"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane3.setViewportView(jTable1);

        chkCheckAll.setText("Pilih Semua");
        chkCheckAll.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkCheckAllItemStateChanged(evt);
            }
        });
        chkCheckAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCheckAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(chkCheckAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkCheckAll)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));
        jPanel2.add(jTabbedPane1);

        jLabel1.setText("Pesan Kesalahan :");

        jScrollPane2.setViewportView(jEditorPane1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(274, 274, 274)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(289, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1))
                .addGap(96, 96, 96))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(3, 3, 3))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap()))
        );

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jalankan();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void chkCheckAllItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkCheckAllItemStateChanged
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            jTable1.setValueAt(chkCheckAll.isSelected(), i, 1);
        }
    }//GEN-LAST:event_chkCheckAllItemStateChanged

    private void chkCheckAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCheckAllActionPerformed

    }//GEN-LAST:event_chkCheckAllActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkCheckAll;
    private javax.swing.JButton jButton1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    
    public class MyTableCellEditor extends AbstractCellEditor implements TableCellEditor {

        private Toolkit toolkit;
        JTextComponent text = new JTextField() {

//            @Override
//            public void setFont(Font f) {
//                super.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11)); // NOI18N
//            }
            
            protected boolean processKeyBinding(final KeyStroke ks, final KeyEvent e, final int condition, final boolean pressed) {
                if (hasFocus()) {
                    return super.processKeyBinding(ks, e, condition, pressed);
                } else {
                    this.requestFocus();

                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            processKeyBinding(ks, e, condition, pressed);
                        }
                    });
                    return true;
                }
            }
        };
        ;

        int col, row;
        
        private NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int rowIndex, int vColIndex) {
            col = vColIndex;
            row = rowIndex;
            text.setBackground(new Color(0, 255, 204));
            text.addFocusListener(txtFocusListener);
            text.addKeyListener(kListener);
            text.setFont(table.getFont());
            text.setName("textEditor");
            text.removeKeyListener(kListener);
//           AbstractDocument doc = (AbstractDocument)text.getDocument();
//           doc.setDocumentFilter(null);
//           doc.setDocumentFilter(new FixedSizeFilter(iText));

            text.removeKeyListener(kListener);
            text.addKeyListener(kListener);

            if (isSelected) {
            }
            text.setText(value == null ? "" : value.toString());
            return text;
        }

        public Object getCellEditorValue() {
            Object retVal = 0;
            try {
                retVal = ((JTextField) text).getText();
                int col=((PanelTabel)jTabbedPane1.getComponentAt(jTabbedPane1.getSelectedIndex())).getTable().getSelectedColumn();
                String namaTable=((PanelTabel) jTabbedPane1.getComponentAt(jTabbedPane1.getSelectedIndex())).getNamaTabel();
                String namaKolom= ((PanelTabel)jTabbedPane1.getComponentAt(jTabbedPane1.getSelectedIndex()))
                        .getTable().getColumnName(col);
                String criteria=getUpdateCriteria();
                String query="Update "+namaTable+" set "+namaKolom+"= " +
                            (retVal instanceof Number || retVal instanceof Double || retVal instanceof Integer? 
                            retVal: "'"+retVal.toString()+"' "
                            + criteria) ;
                System.out.println(query);
                if(criteria.length() >0){
                    try{
                        int i=conn.createStatement().executeUpdate(query);
                        if(i>0){
                            System.out.println("Update kolom sukses!");
                        }
                    }catch(SQLException se){
                        JOptionPane.showMessageDialog(null, se.getMessage());
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Setting kolom kunci di tabel "+namaTable+" belum diset!");
                }
                //table
                return retVal;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                toolkit.beep();
                retVal = 0;
            }
            return retVal;
        }
        
    }
    
    public class MyKeyListener extends KeyAdapter {
        @Override
        public void keyTyped(java.awt.event.KeyEvent evt) {
            
//            if(getTableSource()==null)
//                return;
            
//            if (evt.getSource() instanceof JTextField &&
//              ((JTextField)evt.getSource()).getName()!=null &&
//              ((JTextField)evt.getSource()).getName().equalsIgnoreCase("textEditor")) {
//                fn.keyTyped(evt);
//
//           }

        }
        
        public void keyPressed(KeyEvent evt) {
            Component ct = KeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner();
            int keyKode = evt.getKeyCode();
            switch(keyKode){
               case KeyEvent.VK_ENTER : {
                    if(!(ct instanceof JTable))                    {
                            Component c = findNextFocus();
                            if (c==null) return;
                            c.requestFocus();
                    }
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    if(!(ct instanceof JTable))
                        {
                                Component c = findNextFocus();
                                if (c==null) return;
                                c.requestFocus();
                            break;
                    }
                }

                case KeyEvent.VK_UP: {
                    if(!(evt.getSource() instanceof JTable)){
                        Component c = findPrevFocus();
                        c.requestFocus();
                    }
                    break;
                }
//                case KeyEvent.VK_F4:{
//                    udfClear();
//                    break;
//                }
//                case KeyEvent.VK_F5:{
//                    udfSave();
//                    
//                    break;
//                }
                case KeyEvent.VK_ESCAPE:{
                    //dispose();
                    break;
                }
                
            }
        }

//        @Override
//        public void keyReleased(KeyEvent evt){
//            if(evt.getSource().equals(txtDisc)||evt.getSource().equals(txtQty)||evt.getSource().equals(txtUnitPrice))
//                GeneralFunction.keyTyped(evt);
//        }

        public Component findNextFocus() {
            // Find focus owner
            Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
            Container root = c == null ? null : c.getFocusCycleRootAncestor();

            if (root != null) {
                FocusTraversalPolicy policy = root.getFocusTraversalPolicy();
                Component nextFocus = policy.getComponentAfter(root, c);
                if (nextFocus == null) {
                    nextFocus = policy.getDefaultComponent(root);
                }
                return nextFocus;
            }
            return null;
        }

        public Component findPrevFocus() {
            // Find focus owner
            Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
            Container root = c == null ? null : c.getFocusCycleRootAncestor();

            if (root != null) {
                FocusTraversalPolicy policy = root.getFocusTraversalPolicy();
                Component prevFocus = policy.getComponentBefore(root, c);
                if (prevFocus == null) {
                    prevFocus = policy.getDefaultComponent(root);
                }
                return prevFocus;
            }
            return null;
        }
    }
    
    private FocusListener txtFocusListener=new FocusListener() {
        public void focusGained(FocusEvent e) {
            if(e.getSource() instanceof JTextField || e.getSource() instanceof JFormattedTextField){
                ((JTextField)e.getSource()).setBackground(Color.CYAN);
                if( (e.getSource() instanceof JTextField && ((JTextField)e.getSource()).getName()!=null && ((JTextField)e.getSource()).getName().equalsIgnoreCase("textEditor"))){
                    ((JTextField)e.getSource()).setSelectionStart(0);
                    ((JTextField)e.getSource()).setSelectionEnd(((JTextField)e.getSource()).getText().length());

                }
            }
        }


        public void focusLost(FocusEvent e) {
            if(e.getSource().getClass().getSimpleName().equalsIgnoreCase("JTextField")||
                    e.getSource().getClass().getSimpleName().equalsIgnoreCase("JFormattedTextField")){
                ((JTextField)e.getSource()).setBackground(Color.WHITE);
           }
        }
    } ;

}
