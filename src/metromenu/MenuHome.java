/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromenu;

import Widget.Button;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import koneksi.FTPConnection;

/**
 *
 * @author asus
 */
public class MenuHome extends javax.swing.JPanel {

    private HomePage homePage;
    private String[] judulMenuUtama = {"Dosen", "Mahasiswa", "Institusi", "Sistem", "Referensi", "Utility","Pilih Fakultas Dan Prodi"};
    private String[] aikonMenuUtama = {
        "/image/Dosen64.png",
        "/image/Mahasiswa64.png",
        "/image/Institusi64.png",
//        "/image/Upload.png",
//        "/image/Download_48.png",
        "/image/dikti/sistem64.png", //sistem
        "/image/dikti/referensi64.png", //referensi
        "/image/dikti/utiliti64.png" //utility
    };
    private int counterMenuUtama = 0; //item di root menu 
    private int counterSubMenu = 0;  //item di sub menu
    private int counterLevel = 0; //level 0=root menu, 1=sub menu
    private int counterPaging = -1; //-1=sebelum page 1, 0=page 1, 1=page 2
    private int menuYgDipilih = 0;
    private MemilihActionListener listener;

    class MemilihActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String aksi = e.getActionCommand();
            menuYgDipilih = Integer.parseInt("" + aksi.charAt(aksi.length() - 1));
            System.out.println("menu yg dipilih: " + menuYgDipilih);
            if (counterLevel == 0) { //jika root menu dipilih
                counterMenuUtama = menuYgDipilih - 1;
                if (counterMenuUtama == 0) { //jika menu Dosen dipilih
                    homePage.panelBerikutnya("Dosen1");
                }
                if (counterMenuUtama == 1) { //jika menu Mahasiswa dipilih
                    homePage.panelBerikutnya("Mhs1");
                }
                if (counterMenuUtama == 2) { //jika menu Lembaga dipilih
                    homePage.panelBerikutnya("Lembaga1");
                }
                if (counterMenuUtama == 3) { //jika menu Mahasiswa dipilih
//                    
                    homePage.panelBerikutnya("pilihfakultas");
                }
                if (counterMenuUtama == 5) { //jika menu Mahasiswa dipilih
//                    JOptionPane.showMessageDialog(homePage, "Download FIle");
//                    File file = new File("filename.txt");
//                    BufferedReader reader = null;
//                    FTPConnection connection = new FTPConnection();
//                    try {
//                        reader = new BufferedReader(new FileReader(file));
//                        try {
//                            String text = reader.readLine();
//                            System.out.println("" + text);
//                            connection.download(text);
//                        } catch (IOException ex) {
//                            Logger.getLogger(MenuHome.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    } catch (FileNotFoundException ex) {
//                        ex.printStackTrace();
//                    }
                }
                /*
                 if (counterMenuUtama == 3) { //jika menu Dosen dipilih
                 homePage.panelBerikutnya("Sistem");
                 }
                 if (counterMenuUtama == 4) { //jika menu Mahasiswa dipilih
                 homePage.panelBerikutnya("Referensi");
                 }
                 if (counterMenuUtama == 5) { //jika menu Lembaga dipilih
                 homePage.panelBerikutnya("Utiliti");
                 }
                 * */
            }
        }
    }

    private void inisialisasiAksi() {
        listener = new MemilihActionListener();
        Component[] komponens = panelMenu.getComponents();
        for (Component komponen : komponens) {
            if (komponen.getClass().getSimpleName().equals("Button")) {
                ((Button) komponen).addActionListener(listener);
                System.out.println("menambahkan listener ke "
                        + ((Button) komponen).getActionCommand());
            }
        }
    }

    /**
     * Creates new form MenuDosen
     */
    public MenuHome(HomePage _homePage) {
        this.homePage = _homePage;
        initComponents();
        inisialisasiAksi();
        updatePanel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMenu = new javax.swing.JPanel();
        buttonMenu1 = new Widget.Button();
        buttonMenu2 = new Widget.Button();
        buttonMenu3 = new Widget.Button();
        buttonMenu4 = new Widget.Button();
        buttonMenu5 = new Widget.Button();
        buttonMenu6 = new Widget.Button();
        panelNavigasi = new javax.swing.JPanel();
        panelPrev = new javax.swing.JPanel();
        buttonMenuPrev = new Widget.Button();
        panelKosong = new javax.swing.JPanel();
        panelNext = new javax.swing.JPanel();
        buttonMenuNext = new Widget.Button();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        panelMenu.setOpaque(false);
        panelMenu.setLayout(new java.awt.GridLayout(2, 3));

        buttonMenu1.setActionCommand("menu1");
        buttonMenu1.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        panelMenu.add(buttonMenu1);

        buttonMenu2.setActionCommand("menu2");
        buttonMenu2.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        panelMenu.add(buttonMenu2);

        buttonMenu3.setActionCommand("menu3");
        buttonMenu3.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        panelMenu.add(buttonMenu3);

        buttonMenu4.setActionCommand("menu4");
        buttonMenu4.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        panelMenu.add(buttonMenu4);

        buttonMenu5.setActionCommand("menu5");
        buttonMenu5.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        panelMenu.add(buttonMenu5);

        buttonMenu6.setActionCommand("menu6");
        buttonMenu6.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        panelMenu.add(buttonMenu6);

        add(panelMenu, java.awt.BorderLayout.CENTER);

        panelNavigasi.setOpaque(false);
        panelNavigasi.setLayout(new java.awt.BorderLayout());

        panelPrev.setOpaque(false);

        buttonMenuPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/prev_mini.png"))); // NOI18N
        buttonMenuPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMenuPrevActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPrevLayout = new javax.swing.GroupLayout(panelPrev);
        panelPrev.setLayout(panelPrevLayout);
        panelPrevLayout.setHorizontalGroup(
            panelPrevLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrevLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonMenuPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelPrevLayout.setVerticalGroup(
            panelPrevLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrevLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonMenuPrev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        panelNavigasi.add(panelPrev, java.awt.BorderLayout.WEST);

        panelKosong.setOpaque(false);

        javax.swing.GroupLayout panelKosongLayout = new javax.swing.GroupLayout(panelKosong);
        panelKosong.setLayout(panelKosongLayout);
        panelKosongLayout.setHorizontalGroup(
            panelKosongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1152, Short.MAX_VALUE)
        );
        panelKosongLayout.setVerticalGroup(
            panelKosongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        panelNavigasi.add(panelKosong, java.awt.BorderLayout.CENTER);

        panelNext.setOpaque(false);

        buttonMenuNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/next_mini.png"))); // NOI18N
        buttonMenuNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMenuNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelNextLayout = new javax.swing.GroupLayout(panelNext);
        panelNext.setLayout(panelNextLayout);
        panelNextLayout.setHorizontalGroup(
            panelNextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNextLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonMenuNext, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelNextLayout.setVerticalGroup(
            panelNextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonMenuNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        panelNavigasi.add(panelNext, java.awt.BorderLayout.EAST);

        add(panelNavigasi, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonMenuPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMenuPrevActionPerformed
        // TODO add your handling code here:tombol Prev
        if (counterLevel == 0) {
            if (counterMenuUtama > 0) {
                counterMenuUtama--;
                updatePanel();
            }
        } else if (counterLevel == 1) {
            if (counterPaging > 0) {
                counterPaging--;
                updatePanel();
            }
        }
    }//GEN-LAST:event_buttonMenuPrevActionPerformed

    private void buttonMenuNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMenuNextActionPerformed
        // TODO add your handling code here:tombol Next
    }//GEN-LAST:event_buttonMenuNextActionPerformed

    private void updatePanel() {
        //pengaturan tampilan menu
        if (counterLevel == 0) {
            buttonMenu1.setIcon(
                    new javax.swing.ImageIcon(getClass().getResource(
                    aikonMenuUtama[0]))); // NOI18N
            buttonMenu1.setText(judulMenuUtama[0]);
            buttonMenu2.setIcon(
                    new javax.swing.ImageIcon(getClass().getResource(
                    aikonMenuUtama[1]))); // NOI18N
            buttonMenu2.setText(judulMenuUtama[1]);
            buttonMenu3.setIcon(
                    new javax.swing.ImageIcon(getClass().getResource(
                    aikonMenuUtama[2]))); // NOI18N
            buttonMenu3.setText(judulMenuUtama[2]);

            //tambahan buat admin

            buttonMenu4.setIcon(
                    new javax.swing.ImageIcon(getClass().getResource(
                    aikonMenuUtama[3]))); // NOI18N
            buttonMenu4.setText(judulMenuUtama[6]);
            /* 
             buttonMenu5.setIcon(
             new javax.swing.ImageIcon(getClass().getResource(
             aikonMenuUtama[3]))); // NOI18N
             buttonMenu5.setText(judulMenuUtama[6]);
             */
//            buttonMenu6.setIcon(
//                    new javax.swing.ImageIcon(getClass().getResource(
//                    aikonMenuUtama[4]))); // NOI18N
//            buttonMenu6.setText(judulMenuUtama[7]);

        }

        //pengaturan enabelisitas tombol navigasi
        aturEnabelisitasTombolNavigasi();
    }

    private void aturEnabelisitasTombolNavigasi() {
        //pengaturan enabelisitas tombol navigasi
        if (counterLevel == 0) { //jika root menu dibuka
            buttonMenuNext.setEnabled(false); //Next
            buttonMenuPrev.setEnabled(false); //Prev
        }
    }

    private void UploadFileToServer() {
        try {
            Socket sock = new Socket("127.0.0.1", 123456);
            byte[] mybytearray = new byte[1024];
            InputStream is = sock.getInputStream();
            FileOutputStream fos = new FileOutputStream("s.pdf");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int bytesRead = is.read(mybytearray, 0, mybytearray.length);
            bos.write(mybytearray, 0, bytesRead);
            bos.close();
            sock.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(MenuHome.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MenuHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void downloadFile() {
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Widget.Button buttonMenu1;
    private Widget.Button buttonMenu2;
    private Widget.Button buttonMenu3;
    private Widget.Button buttonMenu4;
    private Widget.Button buttonMenu5;
    private Widget.Button buttonMenu6;
    private Widget.Button buttonMenuNext;
    private Widget.Button buttonMenuPrev;
    private javax.swing.JPanel panelKosong;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelNavigasi;
    private javax.swing.JPanel panelNext;
    private javax.swing.JPanel panelPrev;
    // End of variables declaration//GEN-END:variables
}
