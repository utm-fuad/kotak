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
public class ModelBadanHukum {

    KoneksiOracleThinClient koneksi;
    Connection connection;
    PreparedStatement prep;
    ResultSet RS;
    Statement stat;
    String stringKoneksi, url, user, password;

    public ModelBadanHukum() {
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
            prep.setString(2, data[1]);
            prep.setDate(3, java.sql.Date.valueOf(data[2]));
            prep.setString(4, data[3]);
            prep.setInt(5, Integer.parseInt(data[4]));
            prep.setInt(6, Integer.parseInt(data[5]));
            prep.setString(7, data[6]);
            prep.setInt(8, Integer.parseInt(data[7]));
            prep.setString(9, data[8]);
            prep.setString(10, data[9]);
            prep.setString(11, data[10]);
            prep.setString(12, data[11]);
            prep.setString(13, data[12]);
            prep.setDate(14, java.sql.Date.valueOf(data[13]));
            prep.setString(15, data[14]);
            prep.setInt(16, Integer.parseInt(data[15]));
            prep.setInt(17, Integer.parseInt(data[16]));
            prep.setString(18, data[17]);
            prep.setDate(19, java.sql.Date.valueOf(data[18]));
            prep.setString(20, data[19]);
            prep.setDate(21, java.sql.Date.valueOf(data[20]));
            status = prep.executeUpdate();
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelBadanHukum.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public int updateAktifitasDOsen(String queri, String[] data) {
        System.out.println("Aktifitas dosen inisiasi");
        int status = 0;
        try {
            prep = connection.prepareStatement(queri);
            prep.setString(1, data[0]);
            prep.setDate(2, java.sql.Date.valueOf(data[1]));
            prep.setString(3, data[2]);
            prep.setInt(4, Integer.parseInt(data[3]));
            prep.setInt(5, Integer.parseInt(data[4]));
            prep.setString(6, data[5]);
            prep.setInt(7, Integer.parseInt(data[6]));
            prep.setString(8, data[7]);
            prep.setString(9, data[8]);
            prep.setString(10, data[9]);
            prep.setString(11, data[10]);
            prep.setString(12, data[11]);
            prep.setDate(13, java.sql.Date.valueOf(data[12]));
            prep.setString(14, data[13]);
            prep.setInt(15, Integer.parseInt(data[14]));
            prep.setInt(16, Integer.parseInt(data[15]));
            prep.setString(17, data[16]);
            prep.setDate(18, java.sql.Date.valueOf(data[17]));
            prep.setString(19, data[18]);
            prep.setDate(20, java.sql.Date.valueOf(data[19]));
            prep.setString(21, data[20]);
            status = prep.executeUpdate();
            System.out.println("" + prep);
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelBadanHukum.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ModelBadanHukum.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
}
