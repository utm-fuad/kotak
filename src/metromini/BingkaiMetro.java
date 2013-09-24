/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metromini;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.KeyStroke;

/**
 *
 * @author asus
 */
public class BingkaiMetro extends JFrame {
    private String nama, password;
    private TanpaBingkai3 utama;

    /**
     * Creates new form PintuGerbang
     */
    public BingkaiMetro() {        
        super.setUndecorated(true); //tanpa title bar
        super.setExtendedState(this.MAXIMIZED_BOTH); //resolusi maksimal dari display        
        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //disable Alt + F4
        super.setAlwaysOnTop(true); //disable Alt+Tab        
        
        initComponents();        
        utama = new TanpaBingkai3();             
        
        //setSize(1366, 768); //resolusi maksimal dari display
        
        
        setLocationRelativeTo(null);
        setVisible(true);        
        jTextField1.requestFocus();   
        
        
        jTextField1.getInputMap().put(
                KeyStroke.getKeyStroke(
                  KeyEvent.VK_DELETE,
                  java.awt.event.InputEvent.CTRL_DOWN_MASK | 
                  java.awt.event.InputEvent.ALT_DOWN_MASK                  
                ), "test");
                
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        panelBesar = new javax.swing.JPanel();
        panelBanner = new javax.swing.JPanel();
        panelAreaLogin = new javax.swing.JPanel();
        panelFrameLogin = new javax.swing.JPanel();
        panelLogin = new javax.swing.JPanel();
        label1 = new Widget.Label();
        jTextField1 = new javax.swing.JTextField();
        label2 = new Widget.Label();
        jPasswordField1 = new javax.swing.JPasswordField();
        button1 = new Widget.Button();
        button2 = new Widget.Button();
        panelTembelanKiri = new javax.swing.JPanel();
        panelTembelanKanan = new javax.swing.JPanel();
        panelTembelanAtas = new javax.swing.JPanel();
        panelTembelanBawah = new javax.swing.JPanel();

        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowIconified(java.awt.event.WindowEvent evt) {
                formWindowIconified(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                formFocusLost(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout(0, 10));

        jDesktopPane1.setPreferredSize(new java.awt.Dimension(1366, 768));

        panelBesar.setOpaque(false);
        panelBesar.setPreferredSize(new java.awt.Dimension(1366, 768));
        panelBesar.setLayout(new java.awt.GridLayout(1, 2));

        panelBanner.setOpaque(false);
        panelBesar.add(panelBanner);

        panelAreaLogin.setOpaque(false);
        panelAreaLogin.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 500));

        panelFrameLogin.setLayout(new java.awt.BorderLayout());

        panelLogin.setBackground(new java.awt.Color(227, 227, 227));
        panelLogin.setLayout(new java.awt.GridLayout(3, 2, 20, 10));

        label1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label1.setText("Nama Operator");
        label1.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 24)); // NOI18N
        panelLogin.add(label1);

        jTextField1.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 24)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });
        panelLogin.add(jTextField1);

        label2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label2.setText("Kata Sandi");
        label2.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 24)); // NOI18N
        panelLogin.add(label2);

        jPasswordField1.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 24)); // NOI18N
        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordField1KeyPressed(evt);
            }
        });
        panelLogin.add(jPasswordField1);

        button1.setBackground(new java.awt.Color(27, 172, 255));
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setMnemonic('B');
        button1.setText("Batal");
        button1.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 24)); // NOI18N
        button1.setOpaque(true);
        button1.setRoundRect(false);
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        panelLogin.add(button1);

        button2.setBackground(new java.awt.Color(27, 172, 255));
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setMnemonic('L');
        button2.setText("Login");
        button2.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 24)); // NOI18N
        button2.setOpaque(true);
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        panelLogin.add(button2);

        panelFrameLogin.add(panelLogin, java.awt.BorderLayout.CENTER);
        panelFrameLogin.add(panelTembelanKiri, java.awt.BorderLayout.WEST);
        panelFrameLogin.add(panelTembelanKanan, java.awt.BorderLayout.EAST);
        panelFrameLogin.add(panelTembelanAtas, java.awt.BorderLayout.NORTH);
        panelFrameLogin.add(panelTembelanBawah, java.awt.BorderLayout.SOUTH);

        panelAreaLogin.add(panelFrameLogin);

        panelBesar.add(panelAreaLogin);

        panelBesar.setBounds(0, 0, 1366, 768);
        jDesktopPane1.add(panelBesar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(jDesktopPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_button1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
        nama = jTextField1.getText();
        password = new String(jPasswordField1.getPassword());
        if (nama.equals("operator") && password.equals("rahasia")) {
            utama.setVisible(true);
            this.setVisible(false);
            utama.setNamaOperator(nama);
        }
    }//GEN-LAST:event_button2ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
         //JOptionPane.showMessageDialog(null, "" + evt.getKeyCode()); 
        if (evt.getKeyCode() == 10) {
            jPasswordField1.requestFocus();
        }
        
        int kodeTombol = evt.getKeyCode();         
        if (//kodeTombol == KeyEvent.VK_WINDOWS ||
                (
                  evt.isControlDown() 
                  && 
                  evt.isAltDown()
                  && 
                  kodeTombol == KeyEvent.VK_DELETE
                )
           ) {
            //JOptionPane.showMessageDialog(null, "ctrl-alt-del ditekan"); 
            System.out.println("ctrl-alt-del ditekan");
            this.setExtendedState(this.MAXIMIZED_BOTH);
            this.requestFocus();
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jPasswordField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            button2ActionPerformed(null);
        }
    }//GEN-LAST:event_jPasswordField1KeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:        
        int kodeTombol = evt.getKeyCode();         
        if (//kodeTombol == KeyEvent.VK_WINDOWS ||
                (kodeTombol == KeyEvent.VK_D
                   //&& kodeTombol == KeyEvent.VK_DELETE
                )
           ) {
            //JOptionPane.showMessageDialog(null, "ctrl-alt-del ditekan"); 
            System.out.println("ctrl-alt ditekan");
            this.setExtendedState(this.MAXIMIZED_BOTH);
            this.requestFocus();
        }
    }//GEN-LAST:event_formKeyPressed

    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
        // TODO add your handling code here: mencegah Windows + D, show desktop
        this.toFront();
        this.repaint();
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.requestFocus();
    }//GEN-LAST:event_formWindowIconified

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
        // TODO add your handling code here:
        this.toFront();
        this.repaint();
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.requestFocus();
    }//GEN-LAST:event_formWindowLostFocus

    private void formFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusLost
        // TODO add your handling code here:
        this.toFront();
        this.repaint();
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.requestFocus();
    }//GEN-LAST:event_formFocusLost

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        // TODO add your handling code here:
        this.toFront();
        this.repaint();
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.requestFocus();
    }//GEN-LAST:event_formComponentHidden

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
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            BingkaiMetro coba;
            public void run() {
                coba = new BingkaiMetro();        
                coba.toFront();
                coba.repaint();
            }
        });
        
        /*
        PintuGerbang2 coba = new PintuGerbang2();
        coba.setVisible(true);
        * */
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Widget.Button button1;
    private Widget.Button button2;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private Widget.Label label1;
    private Widget.Label label2;
    private javax.swing.JPanel panelAreaLogin;
    private javax.swing.JPanel panelBanner;
    private javax.swing.JPanel panelBesar;
    private javax.swing.JPanel panelFrameLogin;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelTembelanAtas;
    private javax.swing.JPanel panelTembelanBawah;
    private javax.swing.JPanel panelTembelanKanan;
    private javax.swing.JPanel panelTembelanKiri;
    // End of variables declaration//GEN-END:variables
}
