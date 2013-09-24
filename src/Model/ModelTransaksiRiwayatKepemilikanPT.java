/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import koneksi.*;

/**
 *
 * @author tomatech
 */
public class ModelTransaksiRiwayatKepemilikanPT {

    KoneksiOracleThinClient koneksi;
    Connection connection;
    PreparedStatement prep;
    ResultSet RS;
    Statement stat;
    String stringKoneksi, url, user, password;

    public ModelTransaksiRiwayatKepemilikanPT() {
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
        Date date;
        Date date2;
        try {
            prep = connection.prepareStatement(queri);
//            prep.setInt(1, Integer.parseInt(data[0]));
            prep.setString(1, data[0]);
            prep.setInt(2, Integer.parseInt(data[1]));
            prep.setString(3, data[2]);
            prep.setString(4, data[3]);
            DateFormat formatter;
            date = Date.valueOf(data[4]);
            prep.setDate(5, date);
            prep.setDate(6, Date.valueOf(data[5]));
            prep.setInt(7, Integer.parseInt(data[6]));

            status = prep.executeUpdate();
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        }  catch (SQLException ex) {
            Logger.getLogger(ModelTransaksiRiwayatKepemilikanPT.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public int updateAktifitasDOsen(String queri, String[] data) {
        System.out.println("Aktifitas dosen inisiasi");
        int status = 0;
        try {
            prep = connection.prepareStatement(queri);
            prep.setString(1, data[0]);
            prep.setDate(2, Date.valueOf(data[1]));
            prep.setDate(3, Date.valueOf(data[2]));
            prep.setInt(4, Integer.parseInt(data[3]));
            //where
             prep.setString(5, data[4]);
            prep.setInt(6, Integer.parseInt(data[5]));
            prep.setString(7, data[6]);
            status = prep.executeUpdate();
            System.out.println("" + prep);
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelTransaksiRiwayatKepemilikanPT.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
     public int Delete(String queri, String[] data) {
        System.out.println("Aktifitas dosen inisiasi");
        int status = 0;
        try {
            prep = connection.prepareStatement(queri);
          
             prep.setString(1, data[0]);
            prep.setInt(2, Integer.parseInt(data[1]));
            prep.setString(3, data[2]);
            status = prep.executeUpdate();
            System.out.println("" + prep);
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelTransaksiRiwayatKepemilikanPT.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
}
