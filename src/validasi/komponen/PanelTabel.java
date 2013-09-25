package validasi.komponen;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import validasi.utilisasi.ColumnResizer;
import validasi.utilisasi.Function;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author faheem
 */
public class PanelTabel extends javax.swing.JPanel {
    private Connection conn;
    ColumnResizer colResizer = new ColumnResizer();
    private String[][] pesanErrors;
    private String namaTabelDb="";
    private Function fn=new Function();

    /**
     * Creates new form PanelTabel
     */
    public PanelTabel() {
        initComponents();
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setCellSelectionEnabled(true);
        jTable1.setRowHeight(22);
//        jTable1.getModel().addTableModelListener(new TableModelListener() {
//
//            @Override
//            public void tableChanged(TableModelEvent e) {
//                System.out.println("Conn "+conn);
//                if(e.getType()==TableModelEvent.UPDATE && conn!=null){
//                    String namaKolom=jTable1.getColumnName(e.getColumn());
//                    Object value=jTable1.getValueAt(e.getColumn(), jTable1.getSelectedRow());
//                    
//                    System.out.println("Update "+namaTabelDb+" set "+namaKolom+"= " +
//                            (value instanceof Number || value instanceof Double || value instanceof Integer? 
//                            fn.udfGetDouble(value): "'"+value.toString()+"' "
//                            + "whereee  ??????") );
//                }
//            }
//        });
    }

    public void setTableModel(DefaultTableModel model) {
        jTable1.setModel(model);
        if (jTable1.getRowCount() > 0) {
            colResizer.autoResizeColWidth(jTable1);
        }
    }
    
    public void setConn(Connection c){
        this.conn=c;
    }

    public JTable getTable() {
        return jTable1;
    }

    public int getColumnIndex(String namaKolom) {
        return jTable1.getColumnModel().getColumnIndex(namaKolom);
    }
    
    public void setNamaTabelDb(String s){
        this.namaTabelDb=s;
    }

    public String getNamaTabel() {
        return namaTabelDb;
    }

    class MyRowRender extends DefaultTableCellRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setFont(table.getFont());
            setValue(value);

            if (value instanceof Date) {
                setValue(new SimpleDateFormat("dd/MM/yyyy").format(value));
            }
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                if (pesanErrors[row] [column] !=null && pesanErrors[row][column].equalsIgnoreCase("error") ) {
                    setForeground(Color.RED);
                    setBackground(Color.YELLOW);
                }else{
                    setForeground(table.getForeground());
                    setBackground(table.getBackground());
                }
            }
            return this;
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public void setPesanError(String[][] error) {
        this.pesanErrors = error;
        for(int i=0; i<jTable1.getColumnCount(); i++){
            jTable1.getColumnModel().getColumn(i).setCellRenderer(new MyRowRender());
        }
    }
}