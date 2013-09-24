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
public class ModelMasterPegawai {

    KoneksiOracleThinClient koneksi;
    Connection connection;
    PreparedStatement prep;
    ResultSet RS;
    Statement stat;
    String stringKoneksi, url, user, password;

    public ModelMasterPegawai() {
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
            prep.setDate(7, java.sql.Date.valueOf(data[6]));
            prep.setString(8, data[7]);
            prep.setString(9, data[8]);
            prep.setString(10, data[9]);
            prep.setString(11, data[10]);
            prep.setString(12, data[11]);
           prep.setInt(13, Integer.parseInt(data[12]));
           prep.setInt(14, Integer.parseInt(data[13]));
           prep.setInt(15, Integer.parseInt(data[14]));
           prep.setInt(16, Integer.parseInt(data[15]));
           prep.setString(17, data[16]);
           prep.setString(18, data[17]);
           prep.setString(19, data[18]);
           prep.setString(20, data[19]);
           prep.setString(21, data[20]);
           prep.setString(22, data[21]);
           prep.setDate(23, java.sql.Date.valueOf(data[22]));
           prep.setDate(24, java.sql.Date.valueOf(data[23]));
           prep.setInt(25, Integer.parseInt(data[24]));
           prep.setString(26, data[25]);
           prep.setInt(27, Integer.parseInt(data[26]));
           prep.setString(28, data[27]);
           prep.setDate(29, java.sql.Date.valueOf(data[28]));
           prep.setDate(30, java.sql.Date.valueOf(data[29]));
           prep.setString(31, data[30]);
           prep.setString(32, data[31]);
            status = prep.executeUpdate();
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelMasterPegawai.class.getName()).log(Level.SEVERE, null, ex);
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
            prep.setDate(6, java.sql.Date.valueOf(data[5]));
            prep.setString(7, data[6]);
            prep.setString(8, data[7]);
            prep.setString(9, data[8]);
            prep.setString(10, data[9]);
            prep.setString(11, data[10]);
           prep.setInt(12, Integer.parseInt(data[11]));
           prep.setInt(13, Integer.parseInt(data[12]));
           prep.setInt(14, Integer.parseInt(data[13]));
           prep.setInt(15, Integer.parseInt(data[14]));
           prep.setString(16, data[15]);
           prep.setString(17, data[16]);
           prep.setString(18, data[17]);
           prep.setString(19, data[18]);
           prep.setString(20, data[19]);
           prep.setString(21, data[20]);
           prep.setDate(22, java.sql.Date.valueOf(data[21]));
           prep.setDate(23, java.sql.Date.valueOf(data[22]));
           prep.setInt(24, Integer.parseInt(data[23]));
           prep.setString(25, data[24]);
           prep.setInt(26, Integer.parseInt(data[25]));
           prep.setString(27, data[26]);
           prep.setDate(28, java.sql.Date.valueOf(data[27]));
           prep.setDate(29, java.sql.Date.valueOf(data[28]));
           prep.setString(30, data[29]);
           prep.setString(31, data[30]);
           prep.setInt(32, Integer.parseInt(data[31]));
            status = prep.executeUpdate();
            System.out.println("" + prep);
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelMasterPegawai.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
     public int delete(String queri, String[] data) {
        System.out.println("Aktifitas dosen inisiasi");
        int status = 0;
        try {
            prep = connection.prepareStatement(queri);
           prep.setInt(1, Integer.parseInt(data[0]));
            status = prep.executeUpdate();
            System.out.println("" + prep);
            System.out.println("Status " + status);
            koneksi.prepareSqlite(queri, data);
        } catch (SQLException ex) {
            Logger.getLogger(ModelMasterPegawai.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
}
