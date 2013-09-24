package security;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTable;
import koneksi.koneksiSqlite;
/**
 *
 * @author asus
 */
public class KoneksiOracleThinClient {
    private static Connection con;
    private static Statement stat;
    private static PreparedStatement prep;
    private static ResultSet rs;
    private static ResultSetMetaData rsmd;
    private String stringKoneksi, url, user, password;
    
    public void putusKoneksi() {
        try {
            if (prep != null) {
                prep.close();
            }
            if (stat != null) {                
                stat.close();
            }
            if (con != null) {                
                con.close();
            }
        } catch (SQLException ex) {
                Logger.getLogger(KoneksiOracleThinClient.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ada masalah saat memutuskan koneksi " + ex);
            }
    }
    
    public KoneksiOracleThinClient() {
        //stringKoneksi = "oracle.jdbc.driver.OracleDriver";
        koneksiSqlite koSqlite = new koneksiSqlite();
        stringKoneksi = "com.mysql.jdbc.Driver";
        //url = "jdbc:oracle:thin:@118.98.235.245:1521:pdptdikti"; 					
        //url = "jdbc:oracle:thin:@118.98.235.43:1523:pdpt12";percobaan jumat di jakarta 					
        //url = "jdbc:oracle:thin:@127.0.0.1:1521:ORCL";
        url = "jdbc:mysql://localhost/apotik";
        user = "root";
        password = "";
        try {
            Class.forName(stringKoneksi);
            System.out.println("Class.forname berhasil dibuat");
        }catch (ClassNotFoundException e) {
            //
        }
        try {
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);                        
            System.out.println("con, stat berhasil dibuat dari Class.forname");
        } catch (SQLException ex) {
            Logger.getLogger(KoneksiOracleThinClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String dapatkanStatusKoneksi() throws SQLException {
        if (con != null) {
            return "Terkoneksi dengan basis data";
        }
        return "Koneksi terputus";
    }
    
    public void selectData(String query) {
        try {
            rs = stat.executeQuery(query);
            rsmd = rs.getMetaData();
        } catch (SQLException ex) {
            Logger.getLogger(KoneksiOracleThinClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void prepareSelectData(String query, String [] data) {
        try {
            prep = con.prepareStatement(query, 
                    ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);            
            int indeks = 1;
            for (String datum: data) {
                prep.setString(indeks, datum);
                System.out.println("indeks: " + indeks + ", data: " + datum);
                indeks++;
            }
            System.out.println("query: " + query);
            rs = prep.executeQuery();            
            rsmd = rs.getMetaData();
            while (rs.next()) {
                System.out.println("hasil: " + Integer.parseInt(rs.getString(1)));
            }
            rs.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(KoneksiOracleThinClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int dapatkanJumlahRecordYangDicari() {
        int hasil = -1;
        try {            
            while (rs.next()) {
               hasil = Integer.parseInt(rs.getString(1));
               return hasil; 
            }
        } catch (SQLException ex) {
            Logger.getLogger(KoneksiOracleThinClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hasil;
    }

    /*
     * contoh insert
     */
    //    insert into BFR_ILN_TMST_PENDAFTAR 
    //(KEY_ID, NO_REGISTER, NIDN, NO_TELEPON, NAMA_PT1, 
    // NOMOR_SK, TIM_PENILAI, ID_PENGUSUL, STATUS, NO_BERITA_ACARA, STATUS_SK)
    //values(66, 4705, 197802112008121001, 081703097027, 'Universitas Trunojoyo', 
    //      2529, 'Prof. Paulina Pannen', 10591, 'Diterima', 3146, 3);
    
    public int insertUpdateDeleteData(String queryBuatInsert, String [] data) {
        System.out.println("jumlah data: "  + data.length);
        int status = 0;
        try {
            prep = con.prepareStatement(queryBuatInsert);            
            int indeks = 1;
            for (String datum: data) {
                prep.setString(indeks, datum);
                System.out.println("indeks: " + indeks + ", data: " + datum);
                indeks++;
            }
            status = prep.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KoneksiOracleThinClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int dapatkanJumlahKolom (){
        int jumlah = 0;
        try {
            jumlah = rsmd.getColumnCount();            
        } catch(SQLException s) {}
        return jumlah;
    }
    
    public int dapatkanJumlahBaris(){
        int jumlah = 0;
        try {
            while (rs.next()) {
                jumlah++;
            }            
            rs.beforeFirst();
        } catch(SQLException s) {}                
        return jumlah;
    }
    
    public String [] dapatkanKolom (){
        int jmlKolom = dapatkanJumlahKolom();
        String [] kolom = new String [jmlKolom];
        try {
            for (int a=0; a<jmlKolom; a++) {
                kolom[a] = new String();
                kolom[a] = rsmd.getColumnLabel(a + 1);
            }
        } catch(SQLException s) {
            System.out.println(s);
        }
        return kolom;
    }
    
    public String [][] dapatkanData() {        
        int jmlBaris = dapatkanJumlahBaris();
        int jmlKolom = dapatkanJumlahKolom();
        String [][] data = new String [jmlBaris+1][jmlKolom+1];
        try {               
            int barisKe = 0, kolomKe = 0;
            
            while(rs.next()) {
                //System.out.println("test");
                kolomKe = 0;
                for (int a=0; a<jmlKolom; a++) {
                    //System.out.println("test lho");
                    //data[barisKe][kolomKe] = new String();
                    String dataku = rs.getString(a+1);
                    //System.out.println("baris:" + barisKe + ", kolom:" + kolomKe);
                    System.out.print("" + dataku + "\t");
                    //data[barisKe][kolomKe] = rs.getString(a + 1);
                    data[barisKe][kolomKe] = dataku;
                    kolomKe++;
                }
                barisKe++;
                System.out.println("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(KoneksiOracleThinClient.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return data;
    }
    
    public void cetak (String deskripsi, int data) {
        System.out.println(deskripsi + " : " + data);
    }
    
    public static void main(String [] args) {
        KoneksiOracleThinClient coba = new KoneksiOracleThinClient();
        coba.selectData("select * from tbl_obat");        
        coba.cetak("jumlah kolom", coba.dapatkanJumlahKolom());
        coba.cetak("jumlah baris", coba.dapatkanJumlahBaris());   
        int jumlahKolom = coba.dapatkanJumlahKolom();
        int jumlahBaris = coba.dapatkanJumlahBaris();
        String data[][] = coba.dapatkanData();
        String kolom[] = coba.dapatkanKolom();
        XMLparser xml = new XMLparser();
        xml.parserXMLDB("tbl_obat");
        for(int i=0;i<=jumlahBaris-1;i++){
            xml.setAttributID(data[i][i]);
            for(int j=0;j<=jumlahKolom-1;j++){
                System.out.println(""+kolom[j]);
                xml.ParserXMLcontent( kolom[j], data[i][j]);
            }
        }
        xml.tulisDatakeXML();
        /*
        String [] kolom = coba.dapatkanKolom();        
        for (int a=0; a<kolom.length; a++) {
            System.out.println(kolom[a]);
        }
        * */
        
        try {
          coba.dapatkanData();        
        } catch (Exception e) {
            System.out.println("masalah di " + e.toString());
        }
        //System.out.println("" + data[-1][-1]);
        /*
        for (int a=0; a<data.length; a++) {
            for (int b=0; b<data[a].length; b++) {
                System.out.print(data[a][b]);                
            }
            System.out.println();
        }
        */
    }
}
