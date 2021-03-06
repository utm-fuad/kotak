/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validasi.form;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import metromenu.HomePage;
import validasi.SQLiteConnection;
import validasi.TabelImport;

/**
 *
 * @author asus
 */
public class SettingTableKey extends javax.swing.JPanel {
    private Connection conn;
    private HomePage homePage;

    /**
     * Creates new form SettingTableKey
     */
    public SettingTableKey(HomePage _homePage, String _namaTabel) {
        this();
        this.homePage = _homePage;
    }
    
    public SettingTableKey() {
        initComponents();
        DefaultListModel defModel = new DefaultListModel();
        jList2.setModel(defModel);
        
        cmbTable.removeAllItems();
        conn = new SQLiteConnection().getConn();
        List<String> a = new TabelImport().getTableList();
        for (int i = 0; i < a.size(); i++) {
            cmbTable.addItem(a.get(i));
        }
        jList1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                btnRight.setEnabled(jList1.getSelectedIndex()>=0);
            }
        });
        jList2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                btnLeft.setEnabled(jList2.getSelectedIndex()>=0);
            }
        });
    }

    private void addTableKey(String k){
        try{
            int i=conn.createStatement().executeUpdate("insert into c_table_key (table_name, column_name) "
                    + "values('"+cmbTable.getSelectedItem().toString()+"', '"+k+"')");
            if(i>0){
                showTableKey(k);
            }
        }catch(SQLException se){
            JOptionPane.showMessageDialog(this, se.getMessage());
        }
    }
    
    private void showTableKey(String s){
        try{
            DefaultListModel defModel = new DefaultListModel();
            jList2.setModel(defModel);
            ResultSet rs=conn.createStatement().executeQuery("select table_name, column_name from c_table_key "
                    + "where table_name='"+cmbTable.getSelectedItem().toString()+"'");
            int i=-1;
            while(rs.next()){
                if(s.equalsIgnoreCase(rs.getString("column_name"))){
                    i=defModel.getSize()-1;
                }
                defModel.addElement(rs.getString("column_name"));
            }
            
            rs.close();
            if(i>=0){
                jList2.setSelectedIndex(i);
            }
        }catch(SQLException se){
            JOptionPane.showMessageDialog(this, se.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbTable = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        btnLeft = new javax.swing.JButton();
        btnRight = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel1.setText("Daftar Kolom :");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 40, 265, 25));

        cmbTable.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbTable.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTableItemStateChanged(evt);
            }
        });
        jPanel1.add(cmbTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 10, 365, -1));

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 65, 265, 275));

        jLabel2.setText("Nama Tabel : ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 10, 100, 25));

        jLabel3.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel3.setText("Kolom Kunci :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 45, 265, 25));

        jScrollPane2.setViewportView(jList2);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 70, 265, 275));

        btnLeft.setText("<");
        btnLeft.setEnabled(false);
        jPanel1.add(btnLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(285, 175, 45, -1));

        btnRight.setText(">");
        btnRight.setEnabled(false);
        btnRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRightActionPerformed(evt);
            }
        });
        jPanel1.add(btnRight, new org.netbeans.lib.awtextra.AbsoluteConstraints(285, 145, 45, -1));

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void cmbTableItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTableItemStateChanged
        if(cmbTable.getSelectedIndex()<0)
        return;
        try{
            ResultSet rs=conn.createStatement().executeQuery("select * from "+cmbTable.getSelectedItem().toString());
            DefaultListModel defModel = new DefaultListModel();
            for(int i=0; i<rs.getMetaData().getColumnCount(); i++){
                defModel.addElement(rs.getMetaData().getColumnName(i+1));
            }
            jList1.setModel(defModel);
            showTableKey("");
            if(jList1.getModel().getSize()>0){
                jList1.setSelectedIndex(0);
            }
        }catch(SQLException se){
            JOptionPane.showMessageDialog(this, se.getMessage());
        }
    }//GEN-LAST:event_cmbTableItemStateChanged

    private void btnRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRightActionPerformed
        int iRow=jList1.getSelectedIndex();
        String colName=((DefaultListModel)jList1.getModel()).get(iRow).toString();
        ((DefaultListModel)jList2.getModel()).addElement(colName);
        addTableKey(colName);
        ((DefaultListModel)jList1.getModel()).remove(iRow);
        if(iRow < jList1.getModel().getSize()){
            jList1.setSelectedIndex(iRow);
        }else{
            if(iRow >= jList1.getModel().getSize()){
                jList1.setSelectedIndex(iRow-1);
            }
        }
        jList2.setSelectedIndex(jList2.getModel().getSize()-1);
    }//GEN-LAST:event_btnRightActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLeft;
    private javax.swing.JButton btnRight;
    private javax.swing.JComboBox cmbTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
