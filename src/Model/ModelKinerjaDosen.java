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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import koneksi.*;

/**
 *
 * @author tomatech
 */
public class ModelKinerjaDosen {

    KoneksiOracleThinClient koneksi;
    Connection connection;
    PreparedStatement prep;
    ResultSet RS;
    Statement stat;
    String stringKoneksi, url, user, password;

    public ModelKinerjaDosen() {
        koneksi = new KoneksiOracleThinClient();
        stringKoneksi = "org.postgresql.Driver"; //postgresql

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
            prep.setInt(1, Integer.parseInt(data[0]));
            prep.setString(2, data[1]);
            prep.setString(3, data[2]);
            prep.setString(4, data[3]);
            prep.setString(5, data[4]);
            prep.setString(6, data[5]);
            prep.setString(7, data[6]);
            prep.setString(8, data[7]);
            prep.setInt(9, Integer.parseInt(data[8]));
            prep.setString(10, data[9]);
            prep.setString(11, data[10]);
            prep.setInt(12, Integer.parseInt(data[11]));
            prep.setString(13, data[12]);
            prep.setString(14, data[13]);
            prep.setInt(15, Integer.parseInt(data[14]));
            prep.setDate(16, java.sql.Date.valueOf(data[15]));
            prep.setDate(17, java.sql.Date.valueOf(data[16]));
            prep.setString(18, data[17]);
            prep.setFloat(19, Float.valueOf(data[18]));
            prep.setInt(20, Integer.parseInt(data[19]));
            prep.setFloat(21, Float.valueOf(data[20]));
            prep.setString(22, data[21]);
            prep.setInt(23, Integer.parseInt(data[22]));
            prep.setInt(24, Integer.parseInt(data[23]));
            prep.setInt(25, Integer.parseInt(data[24]));
            prep.setInt(26, Integer.parseInt(data[25]));
            prep.setInt(27, Integer.parseInt(data[26]));
            status = prep.executeUpdate();
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelKinerjaDosen.class.getName()).log(Level.SEVERE, null, ex);
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
            prep.setInt(8, Integer.parseInt(data[7]));
            prep.setString(9, data[8]);
            prep.setString(10, data[9]);
            prep.setInt(11, Integer.parseInt(data[10]));
            prep.setString(12, data[11]);
            prep.setString(13, data[12]);
            prep.setInt(14, Integer.parseInt(data[13]));
            prep.setDate(15,java.sql.Date.valueOf(data[14]));
            prep.setDate(16, java.sql.Date.valueOf(data[15]));
            prep.setString(17, data[16]);
            prep.setFloat(18, Float.valueOf(data[17]));
            prep.setInt(19, Integer.parseInt(data[18]));
            prep.setFloat(20, Float.valueOf(data[19]));
            prep.setString(21, data[20]);
            prep.setInt(22, Integer.parseInt(data[21]));
            prep.setInt(23, Integer.parseInt(data[22]));
            prep.setInt(24, Integer.parseInt(data[23]));
            prep.setInt(25, Integer.parseInt(data[24]));
            prep.setInt(26, Integer.parseInt(data[25]));
            prep.setInt(27, Integer.parseInt(data[26]));
            status = prep.executeUpdate();
            System.out.println("" + prep);
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelKinerjaDosen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
      public int delete(String queri, String[] data) {
        System.out.println("Aktifitas peneliti inisiasi");
        int status = 0;
        try {
            prep = connection.prepareStatement(queri);
            prep.setInt(1, Integer.parseInt(data[0]));
           
            status = prep.executeUpdate();
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelKinerjaDosen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
}
