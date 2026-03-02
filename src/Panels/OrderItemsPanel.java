package Panels;

import Config.Config;
import Utils.TableUtils;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OrderItemsPanel extends JPanel {
    private Dashboard dashboard;


    public OrderItemsPanel(Dashboard dashboard) {
        this.dashboard = dashboard;
        initComponents();
    }

    /**
     * NetBeans generated code
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        setLayout(null);
        setBackground(new Color(240, 240, 245));

        JLabel lblTitle = new JLabel();
        lblTitle.setFont(new Font("Serif", Font.BOLD, 28));
        lblTitle.setForeground(new Color(20, 40, 60));
        lblTitle.setText("Order Items");
        lblTitle.setBounds(30, 20, 300, 40);
        add(lblTitle);

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(255, 215, 0));
        separator.setBounds(30, 65, 840, 2);
        add(separator);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBounds(30, 80, 400, 60);
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(searchPanel);

        JLabel lblTransId = new JLabel("Transaction ID:");
        lblTransId.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblTransId.setBounds(10, 20, 100, 25);
        searchPanel.add(lblTransId);

        txtTransactionId = new JTextField();
        txtTransactionId.setBounds(120, 17, 150, 30);
        txtTransactionId.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchPanel.add(txtTransactionId);

        btnLoad = new JButton("LOAD");
        btnLoad.setBounds(280, 17, 100, 30);
        btnLoad.setBackground(new Color(255, 215, 0));
        btnLoad.setForeground(new Color(40, 40, 40));
        btnLoad.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnLoad.setFocusPainted(false);
        btnLoad.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLoad.addActionListener(e -> loadItems());
        addButtonHoverEffect(btnLoad);
        searchPanel.add(btnLoad);

        btnPrint = new JButton("PRINT RECEIPT");
        btnPrint.setBounds(700, 90, 150, 30);
        btnPrint.setBackground(new Color(255, 150, 0));
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnPrint.setFocusPainted(false);
        btnPrint.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPrint.addActionListener(e -> printReceipt());
        addButtonHoverEffect(btnPrint);
        add(btnPrint);

        // Table
        String[] columns = {"Item ID", "Product", "Brand", "Quantity", "Unit Price", "Subtotal"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        itemsTable = new JTable(model);
        itemsTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        itemsTable.setRowHeight(30);
        itemsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        itemsTable.getTableHeader().setBackground(new Color(20, 40, 60));
        itemsTable.getTableHeader().setForeground(Color.WHITE);
        itemsTable.setSelectionBackground(new Color(255, 215, 0, 100));

        scrollPane = new JScrollPane(itemsTable);
        scrollPane.setBounds(30, 150, 840, 350);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(scrollPane);

        // Total Panel
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(null);
        totalPanel.setBackground(Color.WHITE);
        totalPanel.setBounds(650, 520, 220, 50);
        totalPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(totalPanel);

        JLabel lblTotalLabel = new JLabel("GRAND TOTAL:");
        lblTotalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTotalLabel.setBounds(20, 15, 120, 25);
        totalPanel.add(lblTotalLabel);

        lblTotal = new JLabel("$0.00");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTotal.setForeground(new Color(20, 40, 60));
        lblTotal.setBounds(150, 15, 100, 25);
        totalPanel.add(lblTotal);
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonHoverEffect(JButton button) {
        Color original = button.getBackground();
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(original.brighter());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(original);
            }
        });
    }

    private void loadItems() {
        String transId = txtTransactionId.getText().trim();
        if (transId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Transaction ID");
            return;
        }

        // Remove VWT prefix if present
        transId = transId.replace("VWT", "");
        
        try {
            int id = Integer.parseInt(transId);
            ResultSet rs = Config.getOrderItems(id);
            DefaultTableModel model = (DefaultTableModel) itemsTable.getModel();
            model.setRowCount(0);
            
            double grandTotal = 0;
            
            while (rs.next()) {
                double subtotal = rs.getInt("Quantity") * rs.getDouble("Price_At_Purchase");
                grandTotal += subtotal;
                
                Object[] row = {
                    rs.getInt("Item_ID"),
                    rs.getString("Model_Name"),
                    rs.getString("Brand"),
                    rs.getInt("Quantity"),
                    String.format("$%.2f", rs.getDouble("Price_At_Purchase")),
                    String.format("$%.2f", subtotal)
                };
                model.addRow(row);
            }
            
            lblTotal.setText(String.format("$%.2f", grandTotal));
            TableUtils.setColumnWidths(itemsTable, 80, 200, 150, 80, 100, 120);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Transaction ID");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading items");
        }
    }

    private void printReceipt() {
        String transId = txtTransactionId.getText().trim();
        if (transId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Transaction ID");
            return;
        }

        transId = transId.replace("VWT", "");
        
        try {
            int id = Integer.parseInt(transId);
            
            // Create receipt dialog
            JDialog receiptDialog = new JDialog();
            receiptDialog.setTitle("Receipt - Transaction VWT" + id);
            receiptDialog.setSize(400, 500);
            receiptDialog.setLocationRelativeTo(this);
            receiptDialog.setModal(true);

            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBackground(Color.WHITE);

            ResultSet rs = Config.getReceiptData(id);
            if (rs.next()) {
                int y = 20;

                JLabel lblHeader = new JLabel("VELOCITY WATCH CORPORATION");
                lblHeader.setFont(new Font("Serif", Font.BOLD, 16));
                lblHeader.setForeground(new Color(20, 40, 60));
                lblHeader.setBounds(80, y, 250, 25);
                panel.add(lblHeader);
                y += 30;

                JLabel lblSubHeader = new JLabel("Luxury Timepieces");
                lblSubHeader.setFont(new Font("Serif", Font.ITALIC, 12));
                lblSubHeader.setForeground(new Color(100, 100, 100));
                lblSubHeader.setBounds(130, y, 150, 20);
                panel.add(lblSubHeader);
                y += 30;

                JSeparator sep1 = new JSeparator();
                sep1.setBounds(20, y, 350, 2);
                sep1.setForeground(new Color(255, 215, 0));
                panel.add(sep1);
                y += 20;

                addReceiptRow(panel, "Receipt No:", "VWT" + id, 20, y);
                y += 25;
                addReceiptRow(panel, "Date:", rs.getString("Order_Date"), 20, y);
                y += 25;
                addReceiptRow(panel, "Customer:", rs.getString("First_Name") + " " + rs.getString("Last_Name"), 20, y);
                y += 25;
                addReceiptRow(panel, "Phone:", rs.getString("Phone_Number"), 20, y);
                y += 30;

                JSeparator sep2 = new JSeparator();
                sep2.setBounds(20, y, 350, 2);
                sep2.setForeground(Color.BLACK);
                panel.add(sep2);
                y += 20;

                // Items header
                JLabel lblItems = new JLabel("ITEMS");
                lblItems.setFont(new Font("SansSerif", Font.BOLD, 12));
                lblItems.setBounds(20, y, 100, 20);
                panel.add(lblItems);

                JLabel lblQty = new JLabel("QTY");
                lblQty.setFont(new Font("SansSerif", Font.BOLD, 12));
                lblQty.setBounds(150, y, 50, 20);
                panel.add(lblQty);

                JLabel lblPrice = new JLabel("PRICE");
                lblPrice.setFont(new Font("SansSerif", Font.BOLD, 12));
                lblPrice.setBounds(220, y, 70, 20);
                panel.add(lblPrice);

                JLabel lblSubtotal = new JLabel("SUBTOTAL");
                lblSubtotal.setFont(new Font("SansSerif", Font.BOLD, 12));
                lblSubtotal.setBounds(290, y, 80, 20);
                panel.add(lblSubtotal);
                y += 25;

                ResultSet itemsRs = Config.getOrderItems(id);
                while (itemsRs.next()) {
                    JLabel lblItem = new JLabel(itemsRs.getString("Model_Name"));
                    lblItem.setBounds(20, y, 130, 20);
                    panel.add(lblItem);

                    JLabel lblItemQty = new JLabel(String.valueOf(itemsRs.getInt("Quantity")));
                    lblItemQty.setBounds(150, y, 50, 20);
                    panel.add(lblItemQty);

                    JLabel lblItemPrice = new JLabel(String.format("$%.2f", itemsRs.getDouble("Price_At_Purchase")));
                    lblItemPrice.setBounds(220, y, 70, 20);
                    panel.add(lblItemPrice);

                    double subtotal = itemsRs.getInt("Quantity") * itemsRs.getDouble("Price_At_Purchase");
                    JLabel lblItemSubtotal = new JLabel(String.format("$%.2f", subtotal));
                    lblItemSubtotal.setBounds(290, y, 80, 20);
                    panel.add(lblItemSubtotal);

                    y += 25;
                }

                y += 10;
                JSeparator sep3 = new JSeparator();
                sep3.setBounds(20, y, 350, 2);
                sep3.setForeground(Color.BLACK);
                panel.add(sep3);
                y += 20;

                addReceiptRow(panel, "Total Amount:", String.format("$%.2f", rs.getDouble("Total_Amount")), 20, y);
                y += 25;
                addReceiptRow(panel, "Payment Method:", rs.getString("Payment_Method"), 20, y);
                y += 25;
                addReceiptRow(panel, "Status:", rs.getString("Status"), 20, y);
                y += 40;

                JLabel lblThankYou = new JLabel("Thank you for your purchase!");
                lblThankYou.setFont(new Font("SansSerif", Font.ITALIC, 12));
                lblThankYou.setForeground(new Color(255, 215, 0));
                lblThankYou.setBounds(100, y, 200, 20);
                panel.add(lblThankYou);
            }

            JButton btnClose = new JButton("CLOSE");
            btnClose.setBounds(150, 420, 100, 30);
            btnClose.setBackground(new Color(100, 100, 100));
            btnClose.setForeground(Color.WHITE);
            btnClose.addActionListener(e -> receiptDialog.dispose());
            panel.add(btnClose);

            receiptDialog.add(panel);
            receiptDialog.setVisible(true);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Transaction ID");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addReceiptRow(JPanel panel, String label, String value, int x, int y) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblLabel.setBounds(x, y, 120, 20);
        panel.add(lblLabel);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblValue.setBounds(x + 130, y, 220, 20);
        panel.add(lblValue);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnPrint;
    private javax.swing.JTable itemsTable;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTextField txtTransactionId;
    // End of variables declaration//GEN-END:variables
}