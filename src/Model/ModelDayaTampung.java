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
public class ModelDayaTampung {

    KoneksiOracleThinClient koneksi;
    Connection connection;
    PreparedStatement prep;
    ResultSet RS;
    Statement stat;
    String stringKoneksi, url, user, password;

    public ModelDayaTampung() {
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
            prep.setString(1, data[0]);
            prep.setInt(2, Integer.parseInt(data[1]));
            prep.setString(3, data[2]);
            prep.setString(4, data[3]);
            prep.setString(5, data[4]);
            prep.setInt(6, Integer.parseInt(data[5]));
            prep.setInt(7, Integer.parseInt(data[6]));
            prep.setInt(8, Integer.parseInt(data[7]));
            prep.setInt(9, Integer.parseInt(data[8]));
            prep.setInt(10, Integer.parseInt(data[9]));
            prep.setDate(11, java.sql.Date.valueOf(data[10]));
            prep.setDate(12, java.sql.Date.valueOf(data[11]));
            prep.setInt(13, Integer.parseInt(data[12]));
            prep.setDate(14, java.sql.Date.valueOf(data[13]));
            prep.setDate(15, java.sql.Date.valueOf(data[14]));
            prep.setInt(16, Integer.parseInt(data[15]));
            prep.setString(17, data[16]);
            prep.setString(18, data[17]);
            prep.setInt(19, Integer.parseInt(data[18]));
            prep.setString(20, data[19]);
            prep.setInt(21, Integer.parseInt(data[20]));
            status = prep.executeUpdate();
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelDayaTampung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public int updateAktifitasDOsen(String queri, String[] data) {
        System.out.println("Aktifitas dosen inisiasi");
        int status = 0;
        try {
            prep = connection.prepareStatement(queri);
            prep.setInt(1, Integer.parseInt(data[0]));
            prep.setInt(2, Integer.parseInt(data[1]));
            prep.setInt(3, Integer.parseInt(data[2]));
            prep.setInt(4, Integer.parseInt(data[3]));
            prep.setInt(5, Integer.parseInt(data[4]));
            prep.setDate(6, java.sql.Date.valueOf(data[5]));
            prep.setDate(7, java.sql.Date.valueOf(data[6]));
            prep.setInt(8, Integer.parseInt(data[7]));
            prep.setDate(9, java.sql.Date.valueOf(data[8]));
            prep.setDate(10, java.sql.Date.valueOf(data[9]));
            prep.setInt(11, Integer.parseInt(data[10]));
            prep.setString(12, data[11]);
            prep.setString(13, data[12]);
             prep.setInt(14, Integer.parseInt(data[13]));
             prep.setString(15, data[14]);
             prep.setInt(16, Integer.parseInt(data[15]));
             prep.setString(17, data[16]);
               prep.setInt(18, Integer.parseInt(data[17]));
               prep.setString(19, data[18]);
               prep.setString(20, data[19]);
               prep.setString(21, data[20]);
            status = prep.executeUpdate();
            System.out.println("" + prep);
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelDayaTampung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    public int delete(String queri, String[] data) {
        System.out.println("Aktifitas peneliti inisiasi");
        int status = 0;
        try {
            prep = connection.prepareStatement(queri);
            prep.setString(1, data[0]);
            prep.setInt(2, Integer.parseInt(data[1]));
            prep.setString(3, data[2]);
            prep.setString(4, data[3]);
            prep.setString(5, data[4]);
           
            status = prep.executeUpdate();
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelDayaTampung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
}
