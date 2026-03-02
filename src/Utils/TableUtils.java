package Utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class TableUtils {
    
    public static void populateTable(JTable table, ResultSet rs) {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            
            if (rs == null) return;
            
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            // Set columns if needed
            if (model.getColumnCount() == 0) {
                for (int i = 1; i <= columnCount; i++) {
                    model.addColumn(metaData.getColumnName(i));
                }
            }
            
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                model.addRow(row);
            }
            
        } catch (Exception e) {
            System.err.println("Error populating table: " + e.getMessage());
        }
    }
    
    public static void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }
    
    public static void setColumnWidths(JTable table, int... widths) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < widths.length && i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(widths[i]);
        }
    }
    
    public static int getSelectedId(JTable table, int columnIndex) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            return Integer.parseInt(table.getValueAt(selectedRow, columnIndex).toString());
        }
        return -1;
    }
}