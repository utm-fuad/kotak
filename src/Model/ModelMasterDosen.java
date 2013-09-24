/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import koneksi.*;

/**
 *
 * @author tomatech
 */
public class ModelMasterDosen {

    KoneksiOracleThinClient koneksi;
    Connection connection;
    PreparedStatement prep;
    ResultSet RS;
    Statement stat;
    String stringKoneksi, url, user, password;
    UUID uuid;

    public ModelMasterDosen() {
        koneksi = new KoneksiOracleThinClient();
        stringKoneksi = "org.postgresql.Driver"; //postgresql
        uuid = UUID.randomUUID();
         url = "jdbc:postgresql://10.1.99.93:5432/pdpt-rev";//postgre
        //user = "fuad";
        user = "pdptclient";
        //password = "fuad123";
        password = "pdptclient123";
        try {
            Class.forName(stringKoneksi);
            System.out.println("Class.forname berhasil dibuat di model");
        } catch (ClassNotFoundException e) {
            //
        }
        try {
            connection = DriverManager.getConnection(url, user, password);
            stat = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("con, stat berhasil dibuat dari Class.forname dari model");
//            koneksiSqlite kSqlite = new koneksiSqlite();
        } catch (SQLException ex) {
            Logger.getLogger(KoneksiOracleThinClient.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "cek koneksi anda", "Koneksi Bermasalah", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int insertAktifitasDOsen(String queri, String[] data) {
        System.out.println("Aktifitas peneliti inisiasi");
        
        int status = 0;
        try {
            prep = connection.prepareStatement(queri);
            prep.setString(1, data[0]);
            prep.setString(2, data[1]);
            prep.setString(3, data[2]);
            prep.setString(4, data[3]);
            prep.setString(5, data[4]);
            prep.setString(6, data[5]);
            prep.setString(7, data[6]);
            prep.setString(8, data[7]);
            prep.setString(9, data[8]);
            prep.setString(10, data[9]);
            prep.setString(11, data[10]);
            prep.setString(12, data[11]);
            prep.setString(13, data[12]);
            prep.setString(14, data[13]);
            prep.setString(15, data[14]);
            prep.setString(16, data[15]);
            prep.setString(17, data[16]);
            prep.setDate(18, java.sql.Date.valueOf(data[17]));
            prep.setString(19, data[18]);
            prep.setDate(20, java.sql.Date.valueOf(data[19]));
            prep.setString(21, data[20]);
            prep.setString(22, data[21]);
            prep.setString(23, data[22]);
            prep.setString(24, data[23]);
            prep.setString(25, data[24]);
            prep.setString(26, data[25]);
            prep.setInt(27, Integer.parseInt(data[26]));
            prep.setString(28, data[27]);
            prep.setString(29, data[28]);
            prep.setString(30, data[29]);
            prep.setString(31, data[30]);
            prep.setDate(32, java.sql.Date.valueOf(data[31]));
            prep.setString(33, data[32]);
            prep.setString(34, data[33]);
            prep.setString(35, data[34]);
            prep.setString(36, data[35]);
            prep.setInt(37, Integer.parseInt(data[36]));
            prep.setString(38, data[37]);
            prep.setString(39, data[38]);
            prep.setString(40, data[39]);
            prep.setString(41, data[40]);
            prep.setString(42, data[41]);
            prep.setString(43, data[42]);
            prep.setString(44, data[43]);
            prep.setString(45, data[44]);
            prep.setString(46, data[45]);
            prep.setString(47, data[46]);
            prep.setString(48, data[47]);
            prep.setString(49, data[48]);
//            prep.setInt(50, Integer.parseInt(data[49]));
            status = prep.executeUpdate();
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelMasterDosen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public int updateAktifitasDOsen(String queri, String[] data) {
        System.out.println("Aktifitas dosen inisiasi");
        int status = 0;
        try {
            prep = connection.prepareStatement(queri);
            prep.setString(1, data[0]);
            prep.setString(2, data[1]);
            prep.setString(3, data[2]);
            prep.setString(4, data[3]);
            prep.setString(5, data[4]);
            prep.setString(6, data[5]);
            prep.setString(7, data[6]);
            prep.setString(8, data[7]);
            prep.setString(9, data[8]);
            prep.setString(10, data[9]);
            prep.setString(11, data[10]);
            prep.setString(12, data[11]);
            prep.setString(13, data[12]);
            prep.setString(14, data[13]);
            prep.setString(15, data[14]);
            prep.setString(16, data[15]);
            prep.setDate(17,java.sql.Date.valueOf(data[16]));
            prep.setString(18, data[17]);
            prep.setDate(19, java.sql.Date.valueOf(data[18]));
            prep.setString(20, data[19]);
            prep.setString(21, data[20]);
            prep.setString(22, data[21]);
            prep.setString(23, data[22]);
            prep.setString(24, data[23]);
            prep.setString(25, data[24]);
            prep.setInt(26, Integer.parseInt(data[25]));
            prep.setString(27, data[26]);
            prep.setString(28, data[27]);
            prep.setString(29, data[28]);
            prep.setString(30, data[29]);
            prep.setDate(31, java.sql.Date.valueOf(data[30]));
            prep.setString(32, data[31]);
            prep.setString(33, data[32]);
            prep.setString(34, data[33]);
            prep.setString(35, data[34]);
            prep.setInt(36, Integer.parseInt(data[35]));
            prep.setString(37, data[36]);
            prep.setString(38, data[37]);
            prep.setString(39, data[38]);
            prep.setString(40, data[39]);
            prep.setString(41, data[40]);
            prep.setString(42, data[41]);
            prep.setString(43, data[42]);
            prep.setString(44, data[43]);
            prep.setString(45, data[44]);
            prep.setString(46, data[45]);
            prep.setString(47, data[46]);
            prep.setString(48, data[47]);
//            prep.setInt(49, Integer.parseInt(data[48]));
            prep.setString(49, data[48]);
            status = prep.executeUpdate();
            System.out.println("" + prep);
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelMasterDosen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
      public int delete(String queri, String[] data) {
        System.out.println("Aktifitas dosen inisiasi");
        int status = 0;
        try {
            prep = connection.prepareStatement(queri);
            
            prep.setString(1, data[0]);
            status = prep.executeUpdate();
            System.out.println("" + prep);
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelMasterDosen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
}
