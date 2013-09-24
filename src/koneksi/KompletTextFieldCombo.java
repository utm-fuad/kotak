/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author asus
 */
public class KompletTextFieldCombo{
    private koneksi.KoneksiOracleThinClient kon;
    private boolean hide_flag;
    private  JTextField text;
    private  JComboBox combo;
    private  Vector<String> vps;
        
    public KompletTextFieldCombo (koneksi.KoneksiOracleThinClient _kon,
            JComboBox _combo, JTextField _text) {
        System.out.println("konstruktor jcombobox utk auto complete");
        this.kon = _kon;
        this.combo = _combo;
        this.text = _text;
        vps = new Vector<String>();
        hide_flag = false;
    }
    
    private void setModel(DefaultComboBoxModel mdl, String str) {
        combo.setModel(mdl);
        combo.setSelectedIndex(-1);
        text.setText(str);
    }
       
    private  DefaultComboBoxModel getSuggestedModel(java.util.List<String> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for(String s: list) {
            if(s.startsWith(text)) m.addElement(s);
        }
        return m;
    }
    
    public void initDataUtkAutoComplete(String query) {
        System.out.println("inisialisasi data utk auto complete");
        kon.selectData(query);     
        String [][] data = kon.dapatkanData();
        
        /*
        try {
            text = new JFormattedTextField();
            text.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }*/
        
        combo.setEditable(true);
        text = (JTextField) combo.getEditor().getEditorComponent();
        
        // <editor-fold defaultstate="collapsed" desc="listener">
        text.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    System.out.println("menambah event keyTyped");
                    //javax.swing.JOptionPane.showMessageDialog(null, "keyTyped");//sabtu,12jan2013utkdinamicAutoComplete
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            String texts = text.getText();
                            if(texts.length()==0) {                                
                                combo.hidePopup();
                                setModel(new DefaultComboBoxModel(vps), "");
                            } else{
                                DefaultComboBoxModel m = getSuggestedModel(vps, texts);
                                if(m.getSize()==0 || hide_flag) {
                                    combo.hidePopup();
                                    hide_flag = false;
                                }else{
                                    setModel(m, texts);
                                    combo.showPopup();
                                }
                            }
                        }
                    });
                }
                public void keyPressed(KeyEvent e) {
                    System.out.println("menambah event keyPressed");
                    //javax.swing.JOptionPane.showMessageDialog(null, "keyPressed");//sabtu,12jan2013utkdinamicAutoComplete
                    String texts = text.getText();
                    int code = e.getKeyCode();
                    if(code==KeyEvent.VK_ENTER) {
                        if(!vps.contains(texts)) {
                            vps.addElement(texts);
                            Collections.sort(vps);
                            setModel(getSuggestedModel(vps, texts), texts);
                        }
                        hide_flag = true; 
                    }else if(code==KeyEvent.VK_ESCAPE) {
                        hide_flag = true; 
                    }else if(code==KeyEvent.VK_RIGHT) {
                        for(int i=0;i<vps.size();i++) {
                            String str = vps.elementAt(i);
                            if(str.startsWith(texts)) {
                                combo.setSelectedIndex(-1);
                                text.setText(str);
                                return;
                            }
                        }
                    }
                }
        });// </editor-fold>
        
        for(int i=0;i<data.length-1;i++){
            //System.out.println("data di combo [" + i + "]:" + data[i][0] + " dan " + data[i][1]);
            String datas = "";
            for (int j=0; j<data[i].length-1; j++) {
                datas += data[i][j] + " ";
            }
            //vps.addElement(data[i][0] + " " + data[i][1]);
            vps.addElement(datas);
        }
        setModel(new DefaultComboBoxModel(vps), "");
    }
    
    public void initDataUtkAutoComplete(String [][] data) {
        System.out.println("inisialisasi data utk auto complete dgn data");
        
        combo.setEditable(true);
        text = (JTextField) combo.getEditor().getEditorComponent();
        
        // <editor-fold defaultstate="collapsed" desc="listener">
        text.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    System.out.println("menambah event keyTyped");
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            String texts = text.getText();
                            if(texts.length()==0) {                                
                                combo.hidePopup();
                                setModel(new DefaultComboBoxModel(vps), "");
                            } else{
                                DefaultComboBoxModel m = getSuggestedModel(vps, texts);
                                if(m.getSize()==0 || hide_flag) {
                                    combo.hidePopup();
                                    hide_flag = false;
                                }else{
                                    setModel(m, texts);
                                    combo.showPopup();
                                }
                            }
                        }
                    });
                }
                public void keyPressed(KeyEvent e) {
                    System.out.println("menambah event keyPressed");
                    String texts = text.getText();
                    int code = e.getKeyCode();
                    if(code==KeyEvent.VK_ENTER) {
                        if(!vps.contains(texts)) {
                            vps.addElement(texts);
                            Collections.sort(vps);
                            setModel(getSuggestedModel(vps, texts), texts);
                        }
                        hide_flag = true; 
                    }else if(code==KeyEvent.VK_ESCAPE) {
                        hide_flag = true; 
                    }else if(code==KeyEvent.VK_RIGHT) {
                        for(int i=0;i<vps.size();i++) {
                            String str = vps.elementAt(i);
                            if(str.startsWith(texts)) {
                                combo.setSelectedIndex(-1);
                                text.setText(str);
                                return;
                            }
                        }
                    }
                }
        });// </editor-fold>
        
        for(int i=0;i<data.length;i++){
            //System.out.println("data di combo [" + i + "]:" + data[i][0] + " dan " + data[i][1]);
            vps.addElement(data[i][0] + " " + data[i][1]);
        }
        setModel(new DefaultComboBoxModel(vps), "");
    }
    
    public JTextField dapatkanTextField() {
        System.out.println("menghasilkan JTextField");
        return text;
    }
    
    public JComboBox dapatkanComboBox() {
        System.out.println("menghasilkan JComboBox");
        return combo;
    }
}
