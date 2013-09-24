/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author asus
 */
public class BacaFileTeks {

    private String driver, koneksiString, user, pass, namaFile;
    private File f;
    InetAddress ip;
    StringBuilder sb;
    byte[] mac;
    Connection konek;

    public BacaFileTeks() {
        try {
            //        driver = "oracle.jdbc.driver.OracleDrivernya";
            //        koneksiString = "jdbc:oracle:thin:@127.0.0.1:1521:ORCLnya";
            //        user = "usernamenya";
            //        pass = "passwordnya";
                    Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BacaFileTeks.class.getName()).log(Level.SEVERE, null, ex);
        }
//        driver = "com.mysql.jdbc.Driver";
        driver = "org.postgresql.Driver";
//        koneksiString = "jdbc:mysql://localhost/dikti";
        koneksiString = "jdbc:postgresql://127.0.0.1:5432/test";
        user = "jullev";
        pass = "jullev";
         try {
            konek = (Connection) DriverManager.getConnection(koneksiString, user, pass);
        } catch (SQLException ex) {
            Logger.getLogger(BacaFileTeks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BacaFileTeks(String _namaFile) {
        this();
        namaFile = _namaFile;
        f = new File(namaFile);
//        driver = "oracle.jdbc.driver.OracleDrivernya";
//        koneksiString = "jdbc:oracle:thin:@127.0.0.1:1521:ORCLnya";
//        user = "usernamenya";
//        pass = "passwordnya";
//           driver = "com.mysql.jdbc.Driver";
//        koneksiString = "jdbc:mysql://localhost/dikti";
//        user = "root";
//        pass = "";
//        try {
//            konek = (Connection) DriverManager.getConnection(koneksiString, user, pass);
//        } catch (SQLException ex) {
//            Logger.getLogger(BacaFileTeks.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public String dapatkanData() {
        String text = "";
        int read, N = 1024 * 1024;
        char[] buffer = new char[N];
        String mc = getMaccFromDB();
        int jumlah = getdatacounyter();
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            while (true) {
                read = br.read(buffer, 0, N);
                text += new String(buffer, 0, read);

                if (read < N) {
                    break;
                }
            }
            if(jumlah>70){
                JOptionPane.showMessageDialog(null, "sudah melebihi kota harap purchase", "quota limit exeed", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                System.out.println("Belum tercapai "+jumlah);
            }
            if(text.equals(mc)){
                text=mc;
                System.out.println("Macc sama"+mc);
            }
            else{
                JOptionPane.showMessageDialog(null, "mac address tidak sama");
            }
            
        } catch (Exception ex) {
            String username = JOptionPane.showInputDialog("Masukkan SN anda : ");
            if(username.equals("AGENCY")){
            try {
                //ex.printStackTrace();
                //buat file disini
                ip = InetAddress.getLocalHost();
            } catch (UnknownHostException ex1) {
                Logger.getLogger(BacaFileTeks.class.getName()).log(Level.SEVERE, null, ex1);
            }
            System.out.println("Current IP address : " + ip.getHostAddress());
            NetworkInterface network;
            try {
                network = NetworkInterface.getByInetAddress(ip);
                mac = network.getHardwareAddress();
            } catch (SocketException ex1) {
                Logger.getLogger(BacaFileTeks.class.getName()).log(Level.SEVERE, null, ex1);
            }
            sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println(sb.toString());
            File file = new File("ip.ip");
            try (FileOutputStream fop = new FileOutputStream(file)) {
                // if file doesn't exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                    System.out.println("Create");
                }
                // get the content in bytes
                String text2 = sb.toString();
                fop.write(text2.getBytes());
                storeMacAdd(text2);
                fop.flush();
                fop.close();

                System.out.println("Done");

            } catch (IOException e) {
                e.printStackTrace();
                //create file


            }


        }
        }
        return text;
    }
    public void storeMacAdd(String mac){
        try {
            String sql = "insert into macadd values (null,?)";
            PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
            stat.setString(1, mac);
            stat.executeUpdate();         
            
         } catch (SQLException ex) {
            Logger.getLogger(BacaFileTeks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String getMaccFromDB(){
        String mac = null;
          try {
            String sql = "SELECT mac_add from macadd";
            Statement stat = (Statement) konek.createStatement();
            ResultSet set = stat.executeQuery(sql);
            while (set.next()) {
                mac=set.getString("mac_add").toString(); 
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BacaFileTeks.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mac;
    }
    public int getdatacounyter(){
        int counter = 0;
        try {
            String sql = "select count(*) as jumlah from \"TMST_MAHASISWA\"";
            Statement stat = (Statement) konek.createStatement();
            ResultSet set = stat.executeQuery(sql);
            while (set.next()) {
                counter=set.getInt("jumlah");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BacaFileTeks.class.getName()).log(Level.SEVERE, null, ex);
        }
        return counter;
    }

//        return text;
    public static void main(String[] args) {
        BacaFileTeks b = new BacaFileTeks("ip.ip"
                + "");
        System.out.println(b.dapatkanData());

//        System.out.println("" + b.dapatkanData());
    }
}
