/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tomatech
 */
class Public {

    public static void main(String[] args) {
        FTPConnection connection = new FTPConnection();
//        boolean bool = connection.upload();
//        System.out.println(""+bool);
        File file = new File("filename.txt");
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            try {
                String text = reader.readLine();
                System.out.println("" + text);
                connection.download(text);
            } catch (IOException ex) {
                Logger.getLogger(Public.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
