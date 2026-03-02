package Panels;

import Config.Config;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReceiptPanel extends JPanel {
    private Dashboard dashboard;

    public ReceiptPanel(Dashboard dashboard) {
        this.dashboard = dashboard;
        initComponents();
        loadReceipts();
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
        lblTitle.setText("Receipts");
        lblTitle.setBounds(30, 20, 200, 40);
        add(lblTitle);

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(255, 215, 0));
        separator.setBounds(30, 65, 840, 2);
        add(separator);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBounds(30, 80, 500, 50);
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(searchPanel);

        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSearch.setBounds(10, 15, 60, 25);
        searchPanel.add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(70, 12, 250, 30);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchPanel.add(txtSearch);

        btnSearch = new JButton("SEARCH");
        btnSearch.setBounds(330, 12, 80, 30);
        btnSearch.setBackground(new Color(255, 215, 0));
        btnSearch.setForeground(new Color(40, 40, 40));
        btnSearch.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnSearch.setFocusPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.addActionListener(e -> searchReceipts());
        addButtonHoverEffect(btnSearch);
        searchPanel.add(btnSearch);

        btnRefresh = new JButton("REFRESH");
        btnRefresh.setBounds(415, 12, 80, 30);
        btnRefresh.setBackground(new Color(100, 100, 100));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> loadReceipts());
        addButtonHoverEffect(btnRefresh);
        searchPanel.add(btnRefresh);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBounds(550, 80, 320, 50);
        buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(buttonPanel);

        btnViewReceipt = new JButton("VIEW RECEIPT");
        btnViewReceipt.setBounds(20, 12, 140, 30);
        btnViewReceipt.setBackground(new Color(255, 150, 0));
        btnViewReceipt.setForeground(Color.WHITE);
        btnViewReceipt.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnViewReceipt.setFocusPainted(false);
        btnViewReceipt.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnViewReceipt.addActionListener(e -> viewReceipt());
        buttonPanel.add(btnViewReceipt);

        btnPrint = new JButton("PRINT");
        btnPrint.setBounds(170, 12, 100, 30);
        btnPrint.setBackground(new Color(0, 150, 0));
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnPrint.setFocusPainted(false);
        btnPrint.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPrint.addActionListener(e -> printReceipt());
        buttonPanel.add(btnPrint);

        // Table
        String[] columns = {"Transaction ID", "Customer", "Date", "Amount", "Payment Method", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        receiptTable = new JTable(model);
        receiptTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        receiptTable.setRowHeight(30);
        receiptTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        receiptTable.getTableHeader().setBackground(new Color(20, 40, 60));
        receiptTable.getTableHeader().setForeground(Color.WHITE);
        receiptTable.setSelectionBackground(new Color(255, 215, 0, 100));
        receiptTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewReceipt();
                }
            }
        });

        scrollPane = new JScrollPane(receiptTable);
        scrollPane.setBounds(30, 150, 840, 400);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(scrollPane);

        JLabel lblHint = new JLabel("Double-click on a row to view receipt");
        lblHint.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lblHint.setForeground(new Color(100, 100, 100));
        lblHint.setBounds(30, 560, 300, 20);
        add(lblHint);
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

    private void loadReceipts() {
        try {
            ResultSet rs = Config.getAllTransactions();
            DefaultTableModel model = (DefaultTableModel) receiptTable.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                // Only show Paid transactions
                if (rs.getString("Status").equals("Paid")) {
                    Object[] row = {
                        "VWT" + rs.getInt("Transaction_ID"),
                        rs.getString("First_Name") + " " + rs.getString("Last_Name"),
                        rs.getString("Order_Date"),
                        String.format("$%.2f", rs.getDouble("Total_Amount")),
                        rs.getString("Payment_Method"),
                        rs.getString("Status")
                    };
                    model.addRow(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchReceipts() {
        String searchTerm = txtSearch.getText().trim().toLowerCase();
        if (searchTerm.isEmpty()) {
            loadReceipts();
            return;
        }

        try {
            DefaultTableModel model = (DefaultTableModel) receiptTable.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                boolean match = false;
                for (int j = 0; j < model.getColumnCount(); j++) {
                    String value = model.getValueAt(i, j).toString().toLowerCase();
                    if (value.contains(searchTerm)) {
                        match = true;
                        break;
                    }
                }
                if (!match) {
                    model.removeRow(i);
                    i--;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewReceipt() {
        int selectedRow = receiptTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a transaction");
            return;
        }

        String transId = receiptTable.getValueAt(selectedRow, 0).toString().replace("VWT", "");
        int id = Integer.parseInt(transId);
        showReceiptDialog(id);
    }

    private void printReceipt() {
        int selectedRow = receiptTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a transaction");
            return;
        }

        // In a real application, you would implement actual printing here
        JOptionPane.showMessageDialog(this, 
            "Printing receipt...\n(This would send to printer in production)",
            "Print",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void showReceiptDialog(int transactionId) {
        JDialog receiptDialog = new JDialog();
        receiptDialog.setTitle("Receipt - Transaction VWT" + transactionId);
        receiptDialog.setSize(400, 550);
        receiptDialog.setLocationRelativeTo(this);
        receiptDialog.setModal(true);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        try {
            ResultSet rs = Config.getReceiptData(transactionId);
            if (rs.next()) {
                int y = 20;

                // Header
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

                // Receipt info
                addReceiptRow(panel, "Receipt No:", "VWT" + transactionId, 20, y);
                y += 25;
                addReceiptRow(panel, "Date:", rs.getString("Order_Date"), 20, y);
                y += 25;
                addReceiptRow(panel, "Customer:", rs.getString("First_Name") + " " + rs.getString("Last_Name"), 20, y);
                y += 25;
                addReceiptRow(panel, "Phone:", rs.getString("Phone_Number"), 20, y);
                y += 30;

                // Separator
                JSeparator sep1 = new JSeparator();
                sep1.setBounds(20, y, 350, 2);
                sep1.setForeground(Color.BLACK);
                panel.add(sep1);
                y += 20;

                // Items header
                JLabel lblItems = new JLabel("ITEM");
                lblItems.setFont(new Font("SansSerif", Font.BOLD, 12));
                lblItems.setBounds(20, y, 150, 20);
                panel.add(lblItems);

                JLabel lblQty = new JLabel("QTY");
                lblQty.setFont(new Font("SansSerif", Font.BOLD, 12));
                lblQty.setBounds(180, y, 40, 20);
                panel.add(lblQty);

                JLabel lblPrice = new JLabel("PRICE");
                lblPrice.setFont(new Font("SansSerif", Font.BOLD, 12));
                lblPrice.setBounds(230, y, 60, 20);
                panel.add(lblPrice);

                JLabel lblSubtotal = new JLabel("SUBTOTAL");
                lblSubtotal.setFont(new Font("SansSerif", Font.BOLD, 12));
                lblSubtotal.setBounds(300, y, 70, 20);
                panel.add(lblSubtotal);
                y += 25;

                // Items
                ResultSet itemsRs = Config.getOrderItems(transactionId);
                while (itemsRs.next()) {
                    String itemName = itemsRs.getString("Model_Name");
                    if (itemName.length() > 15) itemName = itemName.substring(0, 15) + "...";
                    
                    JLabel lblItem = new JLabel(itemName);
                    lblItem.setBounds(20, y, 150, 20);
                    panel.add(lblItem);

                    int qty = itemsRs.getInt("Quantity");
                    JLabel lblItemQty = new JLabel(String.valueOf(qty));
                    lblItemQty.setBounds(180, y, 40, 20);
                    panel.add(lblItemQty);

                    double price = itemsRs.getDouble("Price_At_Purchase");
                    JLabel lblItemPrice = new JLabel(String.format("$%.2f", price));
                    lblItemPrice.setBounds(230, y, 60, 20);
                    panel.add(lblItemPrice);

                    double subtotal = qty * price;
                    JLabel lblItemSubtotal = new JLabel(String.format("$%.2f", subtotal));
                    lblItemSubtotal.setBounds(300, y, 70, 20);
                    panel.add(lblItemSubtotal);

                    y += 25;
                }

                y += 10;
                JSeparator sep2 = new JSeparator();
                sep2.setBounds(20, y, 350, 2);
                sep2.setForeground(Color.BLACK);
                panel.add(sep2);
                y += 20;

                // Totals
                addReceiptRow(panel, "Total Amount:", String.format("$%.2f", rs.getDouble("Total_Amount")), 20, y);
                y += 25;
                addReceiptRow(panel, "Payment Method:", rs.getString("Payment_Method"), 20, y);
                y += 25;
                addReceiptRow(panel, "Processed By:", rs.getString("Processed_By"), 20, y);
                y += 40;

                // Footer
                JLabel lblThankYou = new JLabel("Thank you for your purchase!");
                lblThankYou.setFont(new Font("SansSerif", Font.ITALIC, 12));
                lblThankYou.setForeground(new Color(255, 215, 0));
                lblThankYou.setBounds(100, y, 200, 20);
                panel.add(lblThankYou);
                y += 30;

                JLabel lblDate = new JLabel(new java.util.Date().toString());
                lblDate.setFont(new Font("SansSerif", Font.PLAIN, 8));
                lblDate.setForeground(Color.GRAY);
                lblDate.setBounds(120, y, 200, 15);
                panel.add(lblDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JButton btnClose = new JButton("CLOSE");
        btnClose.setBounds(150, 470, 100, 30);
        btnClose.setBackground(new Color(100, 100, 100));
        btnClose.setForeground(Color.WHITE);
        btnClose.addActionListener(e -> receiptDialog.dispose());
        panel.add(btnClose);

        receiptDialog.add(panel);
        receiptDialog.setVisible(true);
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
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnViewReceipt;
    private javax.swing.JTable receiptTable;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}