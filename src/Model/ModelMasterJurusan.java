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
public class ModelMasterJurusan {

    KoneksiOracleThinClient koneksi;
    Connection connection;
    PreparedStatement prep;
    ResultSet RS;
    Statement stat;
    String stringKoneksi, url, user, password;

    public ModelMasterJurusan() {
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
            prep.setString(3, data[2]);
            prep.setString(4, data[3]);
            prep.setString(5, data[4]);
            prep.setDate(6, java.sql.Date.valueOf(data[5]));
            prep.setString(7, data[6]);
            prep.setDate(8, java.sql.Date.valueOf(data[7]));
            prep.setInt(9, Integer.parseInt(data[8]));
            prep.setInt(10, Integer.parseInt(data[9]));
            status = prep.executeUpdate();
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelMasterJurusan.class.getName()).log(Level.SEVERE, null, ex);
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
            prep.setDate(3, java.sql.Date.valueOf(data[9]));
            prep.setString(4, data[3]);
            prep.setDate(5, java.sql.Date.valueOf(data[9]));
            prep.setInt(6, Integer.parseInt(data[5]));
            prep.setInt(7, Integer.parseInt(data[6]));
           prep.setString(8, data[7]);
           prep.setString(9, data[8]);
           prep.setString(10, data[9]);
            status = prep.executeUpdate();
            System.out.println("" + prep);
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelMasterJurusan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    public int delete(String queri, String[] data) {
        System.out.println("Aktifitas dosen inisiasi");
        int status = 0;
        try {
            prep = connection.prepareStatement(queri);
           prep.setString(1, data[0]);
           prep.setString(2, data[1]);
           prep.setString(3, data[2]);
            status = prep.executeUpdate();
            System.out.println("" + prep);
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelMasterJurusan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
}
