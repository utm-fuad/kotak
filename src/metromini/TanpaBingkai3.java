/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromini;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.Timer;
import koneksi.KoneksiOracleThinClient;

/**
 *
 * @author asus
 */
public class TanpaBingkai3 extends JFrame {
    private KoneksiOracleThinClient coba;
    private PanelHalamanTabel panelHalamanTabel;
    private PanelPilihTabel panelPilihTabel;
    private Timer timer;

    /**
     * Creates new form TanpaBingkai
     */
    public TanpaBingkai3() {
        super.setExtendedState(this.MAXIMIZED_BOTH); //resolusi maksimal dari display
        super.setUndecorated(true); //tanpa title bar
        super.setAlwaysOnTop(true); //disable Alt+Tab
        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        setLocationRelativeTo(null);
        
        initComponents();
        inisialisasiLayarTengah();
        layarPenuh();        
        tampilkanJam();        
        //setSize(1366, 768);        
    }
    
    public void setNamaOperator(String nama) {
        labelLogin.setText(nama);
    }
    
    
    private void inisialisasiLayarTengah() {
        panelPilihTabel = new PanelPilihTabel(this);
        panelMetro.add(panelPilihTabel);
    }
    
    public void persiapanAmbilData(String query, String judul) {
        coba = new KoneksiOracleThinClient();
        coba.selectData(query);     
        String [][] data = coba.dapatkanData();
        String [] kolom = coba.dapatkanKolom();
        panelHalamanTabel = new PanelHalamanTabel(data, kolom);
        Component komponen = panelMetro.getComponent(0);
        panelMetro.remove(komponen);
        panelMetro.add(panelHalamanTabel);
        labelJudul.setText(judul);
        
        //tampilkan panel isian field
        if (judul.equals("Master Pendaftar")) {
            PanelMasterPendaftar panelMasterPendaftar = new PanelMasterPendaftar();            
            panelHalamanTabel.tampilkanPanelAtribut(panelMasterPendaftar);            
        }       
        if (judul.equals("Master MAHASISWA")) {
            PanelMasterMahasiswa panelMasterMahasiswa = new PanelMasterMahasiswa();            
            panelHalamanTabel.tampilkanPanelAtribut(panelMasterMahasiswa);
        }
        /*
        if (judul.equals("Master MATA KULIAH")) {
            PanelMasterMahasiswa panelMasterMahasiswa = new PanelMasterMahasiswa();            
            panelHalamanTabel.tampilkanPanelAtribut(panelMasterMahasiswa);
        }
        * */
        panelHalamanTabel.setKoneksi(coba, judul);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        panelBesar = new javax.swing.JPanel();
        panelBanner = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panelSedang = new javax.swing.JPanel();
        panelHome = new javax.swing.JPanel();
        panelLogin = new javax.swing.JPanel();
        labelLogin = new javax.swing.JLabel();
        labelAvatar = new javax.swing.JLabel();
        panelJudul = new javax.swing.JPanel();
        labelJudul = new javax.swing.JLabel();
        panelAikonHome = new javax.swing.JPanel();
        labelHome = new javax.swing.JLabel();
        panelMetro = new javax.swing.JPanel();
        panelBawah = new javax.swing.JPanel();
        panelWaktu = new javax.swing.JPanel();
        labelWaktu = new javax.swing.JLabel();
        panelStatus = new javax.swing.JPanel();
        panelTutupAplikasi = new javax.swing.JPanel();
        labelTutupAplikasi = new Widget.Label();

        getContentPane().setLayout(null);

        panelBesar.setOpaque(false);
        panelBesar.setPreferredSize(new java.awt.Dimension(1366, 758));
        panelBesar.setLayout(new java.awt.BorderLayout(50, 10));

        panelBanner.setOpaque(false);
        panelBanner.setLayout(new java.awt.GridLayout(1, 2));

        jLabel1.setBackground(new java.awt.Color(40, 123, 176));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/header_tutwuri.png"))); // NOI18N
        jLabel1.setOpaque(true);
        panelBanner.add(jLabel1);

        jLabel3.setBackground(new java.awt.Color(40, 123, 176));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/header_dashboardPDPT.png"))); // NOI18N
        jLabel3.setOpaque(true);
        panelBanner.add(jLabel3);

        panelBesar.add(panelBanner, java.awt.BorderLayout.NORTH);

        panelSedang.setOpaque(false);
        panelSedang.setLayout(new java.awt.BorderLayout(20, 20));

        panelHome.setOpaque(false);
        panelHome.setLayout(new java.awt.BorderLayout());

        panelLogin.setBackground(new java.awt.Color(255, 153, 0));
        panelLogin.setOpaque(false);

        labelLogin.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 26)); // NOI18N
        labelLogin.setForeground(new java.awt.Color(255, 153, 0));
        labelLogin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelLogin.setText("Nama yg Login");

        labelAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Supervisor-64.png"))); // NOI18N

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addComponent(labelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelAvatar)
                .addContainerGap())
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addComponent(labelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(labelAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelHome.add(panelLogin, java.awt.BorderLayout.EAST);

        panelJudul.setOpaque(false);
        panelJudul.setLayout(new java.awt.BorderLayout());

        labelJudul.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 48)); // NOI18N
        labelJudul.setForeground(new java.awt.Color(255, 153, 0));
        labelJudul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelJudul.add(labelJudul, java.awt.BorderLayout.CENTER);

        panelHome.add(panelJudul, java.awt.BorderLayout.CENTER);

        panelAikonHome.setOpaque(false);

        labelHome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Home-64.png"))); // NOI18N
        labelHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelHomeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelAikonHomeLayout = new javax.swing.GroupLayout(panelAikonHome);
        panelAikonHome.setLayout(panelAikonHomeLayout);
        panelAikonHomeLayout.setHorizontalGroup(
            panelAikonHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(panelAikonHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelAikonHomeLayout.createSequentialGroup()
                    .addGap(0, 18, Short.MAX_VALUE)
                    .addComponent(labelHome)
                    .addGap(0, 18, Short.MAX_VALUE)))
        );
        panelAikonHomeLayout.setVerticalGroup(
            panelAikonHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(panelAikonHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelAikonHomeLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(labelHome, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        panelHome.add(panelAikonHome, java.awt.BorderLayout.WEST);

        panelSedang.add(panelHome, java.awt.BorderLayout.NORTH);

        panelMetro.setOpaque(false);
        panelMetro.setPreferredSize(new java.awt.Dimension(1300, 600));
        panelMetro.setLayout(new java.awt.CardLayout());
        panelSedang.add(panelMetro, java.awt.BorderLayout.CENTER);

        panelBawah.setOpaque(false);
        panelBawah.setLayout(new java.awt.BorderLayout());

        panelWaktu.setBackground(new java.awt.Color(255, 153, 0));
        panelWaktu.setOpaque(false);
        panelWaktu.setPreferredSize(new java.awt.Dimension(294, 80));
        panelWaktu.setLayout(new java.awt.BorderLayout());

        labelWaktu.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 24)); // NOI18N
        labelWaktu.setForeground(new java.awt.Color(255, 153, 0));
        labelWaktu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelWaktu.setText("Waktu Saat Ini");
        panelWaktu.add(labelWaktu, java.awt.BorderLayout.CENTER);

        panelBawah.add(panelWaktu, java.awt.BorderLayout.EAST);

        panelStatus.setOpaque(false);

        javax.swing.GroupLayout panelStatusLayout = new javax.swing.GroupLayout(panelStatus);
        panelStatus.setLayout(panelStatusLayout);
        panelStatusLayout.setHorizontalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 967, Short.MAX_VALUE)
        );
        panelStatusLayout.setVerticalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );

        panelBawah.add(panelStatus, java.awt.BorderLayout.CENTER);

        panelTutupAplikasi.setOpaque(false);

        labelTutupAplikasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/exit-64.png"))); // NOI18N
        labelTutupAplikasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelTutupAplikasiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelTutupAplikasiLayout = new javax.swing.GroupLayout(panelTutupAplikasi);
        panelTutupAplikasi.setLayout(panelTutupAplikasiLayout);
        panelTutupAplikasiLayout.setHorizontalGroup(
            panelTutupAplikasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(panelTutupAplikasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelTutupAplikasiLayout.createSequentialGroup()
                    .addGap(0, 18, Short.MAX_VALUE)
                    .addComponent(labelTutupAplikasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 18, Short.MAX_VALUE)))
        );
        panelTutupAplikasiLayout.setVerticalGroup(
            panelTutupAplikasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
            .addGroup(panelTutupAplikasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelTutupAplikasiLayout.createSequentialGroup()
                    .addGap(0, 13, Short.MAX_VALUE)
                    .addComponent(labelTutupAplikasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 13, Short.MAX_VALUE)))
        );

        panelBawah.add(panelTutupAplikasi, java.awt.BorderLayout.WEST);

        panelSedang.add(panelBawah, java.awt.BorderLayout.SOUTH);

        panelBesar.add(panelSedang, java.awt.BorderLayout.LINE_START);

        panelBesar.setBounds(0, 0, 1360, 758);
        desktopPane.add(panelBesar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(desktopPane);
        desktopPane.setBounds(0, 0, 1370, 770);
    }// </editor-fold>//GEN-END:initComponents

    private void labelTutupAplikasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelTutupAplikasiMouseClicked
        // TODO add your handling code here:
        if (coba != null) coba.putusKoneksi();
        System.exit(0);
    }//GEN-LAST:event_labelTutupAplikasiMouseClicked

    private void labelHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelHomeMouseClicked
        // TODO add your handling code here:
        Component komponen = panelMetro.getComponent(0);
        panelMetro.remove(komponen);        
        inisialisasiLayarTengah();
        labelJudul.setText("");
    }//GEN-LAST:event_labelHomeMouseClicked

    private void tampilkanJam() {
        timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                labelWaktu.setText(new Date().toString());
            }
        });
        timer.start();
    }
    
    private void layarPenuh() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dimension = tk.getScreenSize();
        setPreferredSize(dimension);
    }
    
    public static void main(String [] args) {
                try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TerminalMetroMini3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TerminalMetroMini3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TerminalMetroMini3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TerminalMetroMini3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        
        TanpaBingkai3 coba = new TanpaBingkai3();
        coba.setVisible(true);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel labelAvatar;
    private javax.swing.JLabel labelHome;
    private javax.swing.JLabel labelJudul;
    private javax.swing.JLabel labelLogin;
    private Widget.Label labelTutupAplikasi;
    private javax.swing.JLabel labelWaktu;
    private javax.swing.JPanel panelAikonHome;
    private javax.swing.JPanel panelBanner;
    private javax.swing.JPanel panelBawah;
    private javax.swing.JPanel panelBesar;
    private javax.swing.JPanel panelHome;
    private javax.swing.JPanel panelJudul;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelMetro;
    private javax.swing.JPanel panelSedang;
    private javax.swing.JPanel panelStatus;
    private javax.swing.JPanel panelTutupAplikasi;
    private javax.swing.JPanel panelWaktu;
    // End of variables declaration//GEN-END:variables
}