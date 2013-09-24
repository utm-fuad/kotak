/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

/**
 *
 * @author asus
 */
public class MenjadiBerspasi {    
    private static String [] ubahMenjadiBerspasi(String [] namaPanel) {
        String [] namaPanelBaru = new String[namaPanel.length];
        for (int a=0; a<namaPanel.length; a++) {
            String kata = namaPanel[a];
            String kataBaru = "Memasukkan ";
            System.out.println("kata: " + kata);
            int indeksHurufBesar = 0;
            for (int b=1; b<kata.length(); b++) {
                if (kata.codePointAt(b) >= 65 && kata.codePointAt(b) <= 90) {
                    System.out.println("" + kata.substring(indeksHurufBesar, b));
                    kataBaru += kata.substring(indeksHurufBesar, b) + " ";
                    indeksHurufBesar = b;
                }              
            }     
            System.out.println("" + kata.substring(indeksHurufBesar, kata.length()));
            namaPanelBaru[a] = kataBaru;
        }
        return namaPanelBaru;
    }
    
    public static void main (String [] args) {
        /*
        String namaPanel = "azAZ";
        for (int a=0; a<namaPanel.length(); a++) {
            System.out.println("" + namaPanel.charAt(a) + "=" + namaPanel.codePointAt(a));            
        }*/
        
        String [] namaPanel = {"MasterDosen", "AnggotaPenelitiAhli"};
        /*
        for (int a=0; a<namaPanel.length; a++) {
            String kata = namaPanel[a];
            System.out.println("kata: " + kata);
            for (int b=0; b<kata.length(); b++) {
                if (kata.codePointAt(b) >= 65 && kata.codePointAt(b) <= 90) {
                    System.out.println("" + kata.charAt(b) + " huruf besar");
                }             
            }
        }
        * */
        
        /*
        for (int a=0; a<namaPanel.length; a++) {
            String kata = namaPanel[a];
            System.out.println("kata: " + kata);
            int indeksHurufBesar = 0;
            for (int b=1; b<kata.length(); b++) {
                if (kata.codePointAt(b) >= 65 && kata.codePointAt(b) <= 90) {
                    System.out.println("" + kata.substring(indeksHurufBesar, b));
                    indeksHurufBesar = b;
                }              
            }     
            System.out.println("" + kata.substring(indeksHurufBesar, kata.length()));
        }
        * */
        
        ubahMenjadiBerspasi(namaPanel);
    }
}
