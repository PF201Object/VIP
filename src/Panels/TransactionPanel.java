package Panels;

import Config.Config;
import Utils.TableUtils;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TransactionPanel extends JPanel {
    private Dashboard dashboard;
    private DefaultTableModel cartModel;
    private double grandTotal = 0;
    private int selectedCustomerId = -1;

    public TransactionPanel(Dashboard dashboard) {
        this.dashboard = dashboard;
        initComponents();
        loadCustomers();
        loadProducts();
        loadTransactions();
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
        lblTitle.setText("Transaction Processing");
        lblTitle.setBounds(30, 20, 400, 40);
        add(lblTitle);

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(255, 215, 0));
        separator.setBounds(30, 65, 840, 2);
        add(separator);

        // Left Panel - Transaction Form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(30, 80, 400, 250);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "New Transaction",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 12),
            new Color(20, 40, 60)
        ));
        add(formPanel);

        JLabel lblCustomer = new JLabel("Customer:");
        lblCustomer.setBounds(20, 30, 80, 25);
        formPanel.add(lblCustomer);

        cmbCustomer = new JComboBox<>();
        cmbCustomer.setBounds(110, 30, 260, 30);
        cmbCustomer.addActionListener(e -> customerSelected());
        formPanel.add(cmbCustomer);

        JLabel lblProduct = new JLabel("Product:");
        lblProduct.setBounds(20, 70, 80, 25);
        formPanel.add(lblProduct);

        cmbProduct = new JComboBox<>();
        cmbProduct.setBounds(110, 70, 260, 30);
        cmbProduct.addActionListener(e -> productSelected());
        formPanel.add(cmbProduct);

        JLabel lblQty = new JLabel("Quantity:");
        lblQty.setBounds(20, 110, 80, 25);
        formPanel.add(lblQty);

        txtQuantity = new JTextField("1");
        txtQuantity.setBounds(110, 110, 100, 30);
        txtQuantity.setHorizontalAlignment(JTextField.RIGHT);
        formPanel.add(txtQuantity);

        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setBounds(20, 150, 80, 25);
        formPanel.add(lblPrice);

        txtPrice = new JTextField();
        txtPrice.setBounds(110, 150, 100, 30);
        txtPrice.setEditable(false);
        txtPrice.setHorizontalAlignment(JTextField.RIGHT);
        formPanel.add(txtPrice);

        btnAddItem = new JButton("ADD TO CART");
        btnAddItem.setBounds(220, 140, 150, 40);
        btnAddItem.setBackground(new Color(255, 215, 0));
        btnAddItem.setForeground(new Color(40, 40, 40));
        btnAddItem.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnAddItem.setFocusPainted(false);
        btnAddItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddItem.addActionListener(e -> addToCart());
        addButtonHoverEffect(btnAddItem);
        formPanel.add(btnAddItem);

        JLabel lblPayment = new JLabel("Payment:");
        lblPayment.setBounds(20, 190, 80, 25);
        formPanel.add(lblPayment);

        cmbPaymentMethod = new JComboBox<>(new String[]{"Credit Card", "PayPal", "Gcash", "Bank Transfer"});
        cmbPaymentMethod.setBounds(110, 190, 150, 30);
        formPanel.add(cmbPaymentMethod);

        // Cart Panel
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(null);
        cartPanel.setBackground(Color.WHITE);
        cartPanel.setBounds(450, 80, 420, 250);
        cartPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Shopping Cart",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 12),
            new Color(20, 40, 60)
        ));
        add(cartPanel);

        String[] cartColumns = {"Product", "Qty", "Price", "Subtotal"};
        cartModel = new DefaultTableModel(cartColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        cartTable = new JTable(cartModel);
        cartTable.setFont(new Font("SansSerif", Font.PLAIN, 11));
        cartTable.setRowHeight(25);
        
        JScrollPane cartScroll = new JScrollPane(cartTable);
        cartScroll.setBounds(10, 30, 400, 150);
        cartPanel.add(cartScroll);

        JLabel lblGrandTotal = new JLabel("Grand Total:");
        lblGrandTotal.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblGrandTotal.setBounds(10, 190, 100, 25);
        cartPanel.add(lblGrandTotal);

        txtTotal = new JTextField("$0.00");
        txtTotal.setBounds(120, 190, 120, 30);
        txtTotal.setEditable(false);
        txtTotal.setFont(new Font("SansSerif", Font.BOLD, 14));
        txtTotal.setHorizontalAlignment(JTextField.RIGHT);
        cartPanel.add(txtTotal);

        btnCreateTransaction = new JButton("COMPLETE TRANSACTION");
        btnCreateTransaction.setBounds(250, 190, 160, 30);
        btnCreateTransaction.setBackground(new Color(0, 150, 0));
        btnCreateTransaction.setForeground(Color.WHITE);
        btnCreateTransaction.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnCreateTransaction.setFocusPainted(false);
        btnCreateTransaction.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCreateTransaction.addActionListener(e -> createTransaction());
        addButtonHoverEffect(btnCreateTransaction);
        cartPanel.add(btnCreateTransaction);

        // Transaction History
        JLabel lblHistory = new JLabel("Transaction History");
        lblHistory.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblHistory.setForeground(new Color(20, 40, 60));
        lblHistory.setBounds(30, 350, 200, 25);
        add(lblHistory);

        btnRefresh = new JButton("REFRESH");
        btnRefresh.setBounds(700, 350, 100, 25);
        btnRefresh.setBackground(new Color(100, 100, 100));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> loadTransactions());
        addButtonHoverEffect(btnRefresh);
        add(btnRefresh);

        btnViewReceipt = new JButton("VIEW RECEIPT");
        btnViewReceipt.setBounds(590, 350, 110, 25);
        btnViewReceipt.setBackground(new Color(255, 150, 0));
        btnViewReceipt.setForeground(Color.WHITE);
        btnViewReceipt.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnViewReceipt.setFocusPainted(false);
        btnViewReceipt.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnViewReceipt.addActionListener(e -> viewReceipt());
        addButtonHoverEffect(btnViewReceipt);
        add(btnViewReceipt);

        // Transactions Table
        String[] columns = {"Trans ID", "Customer", "Date", "Total", "Payment", "Status", "Created By"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        transactionTable = new JTable(model);
        transactionTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        transactionTable.setRowHeight(30);
        transactionTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        transactionTable.getTableHeader().setBackground(new Color(20, 40, 60));
        transactionTable.getTableHeader().setForeground(Color.WHITE);
        transactionTable.setSelectionBackground(new Color(255, 215, 0, 100));

        scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBounds(30, 390, 840, 200);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(scrollPane);
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

    private void loadCustomers() {
        try {
            ResultSet rs = Config.getAllCustomers();
            cmbCustomer.removeAllItems();
            cmbCustomer.addItem("Select Customer");
            
            while (rs.next()) {
                String item = rs.getInt("Customer_ID") + " - " + rs.getString("First_Name") + " " + rs.getString("Last_Name");
                cmbCustomer.addItem(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProducts() {
        try {
            ResultSet rs = Config.getAllProducts();
            cmbProduct.removeAllItems();
            cmbProduct.addItem("Select Product");
            
            while (rs.next()) {
                String item = rs.getInt("Watch_ID") + " - " + rs.getString("Brand") + " " + rs.getString("Model_Name") + " ($" + rs.getDouble("Price") + ")";
                cmbProduct.addItem(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTransactions() {
        try {
            ResultSet rs = Config.getAllTransactions();
            DefaultTableModel model = (DefaultTableModel) transactionTable.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    "VWT" + rs.getInt("Transaction_ID"),
                    rs.getString("First_Name") + " " + rs.getString("Last_Name"),
                    rs.getString("Order_Date"),
                    String.format("$%.2f", rs.getDouble("Total_Amount")),
                    rs.getString("Payment_Method"),
                    rs.getString("Status"),
                    "User" + rs.getInt("Created_By")
                };
                model.addRow(row);
            }
            
            TableUtils.setColumnWidths(transactionTable, 100, 150, 150, 100, 120, 100, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void customerSelected() {
        int index = cmbCustomer.getSelectedIndex();
        if (index > 0) {
            String selected = cmbCustomer.getSelectedItem().toString();
            selectedCustomerId = Integer.parseInt(selected.split(" - ")[0]);
        } else {
            selectedCustomerId = -1;
        }
    }

    private void productSelected() {
        int index = cmbProduct.getSelectedIndex();
        if (index > 0) {
            String selected = cmbProduct.getSelectedItem().toString();
            String priceStr = selected.substring(selected.lastIndexOf("$") + 1, selected.lastIndexOf(")"));
            txtPrice.setText(priceStr);
        } else {
            txtPrice.setText("");
        }
    }

    private void addToCart() {
        if (cmbProduct.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Please select a product");
            return;
        }

        String qtyStr = txtQuantity.getText().trim();
        if (qtyStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter quantity");
            return;
        }

        int qty;
        try {
            qty = Integer.parseInt(qtyStr);
            if (qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity");
            return;
        }

        String selected = cmbProduct.getSelectedItem().toString();
                String productName = selected.substring(selected.indexOf("-") + 2, selected.lastIndexOf(" ($")).trim();
        double price = Double.parseDouble(txtPrice.getText());
        double subtotal = price * qty;

        Object[] row = {
            productName,
            qty,
            String.format("$%.2f", price),
            String.format("$%.2f", subtotal)
        };
        cartModel.addRow(row);

        grandTotal += subtotal;
        txtTotal.setText(String.format("$%.2f", grandTotal));

        // Reset product selection
        cmbProduct.setSelectedIndex(0);
        txtPrice.setText("");
        txtQuantity.setText("1");
    }

    private void createTransaction() {
        if (selectedCustomerId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer");
            return;
        }

        if (cartModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Cart is empty");
            return;
        }

        String paymentMethod = (String) cmbPaymentMethod.getSelectedItem();
        String status = Config.isAdmin() ? "Paid" : "Pending";

        int transactionId = Config.createTransaction(selectedCustomerId, grandTotal, paymentMethod, status);

        if (transactionId != -1) {
            // Add order items
            for (int i = 0; i < cartModel.getRowCount(); i++) {
                String productName = cartModel.getValueAt(i, 0).toString();
                int qty = Integer.parseInt(cartModel.getValueAt(i, 1).toString());
                double price = Double.parseDouble(cartModel.getValueAt(i, 2).toString().replace("$", ""));

                // Get watch ID from product name (simplified - in real app you'd store IDs)
                int watchId = 1; // This should be looked up from database

                Config.addOrderItem(transactionId, watchId, qty, price);
            }

            String message = status.equals("Paid") ? 
                "Transaction completed successfully!" : 
                "Transaction submitted for approval. Waiting for admin authorization.";

            JOptionPane.showMessageDialog(this, message);

            // Clear cart
            cartModel.setRowCount(0);
            grandTotal = 0;
            txtTotal.setText("$0.00");
            selectedCustomerId = -1;
            cmbCustomer.setSelectedIndex(0);

            loadTransactions();

            if (status.equals("Paid")) {
                // Show receipt
                showReceipt(transactionId);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create transaction");
        }
    }

    private void viewReceipt() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a transaction");
            return;
        }

        String transId = transactionTable.getValueAt(selectedRow, 0).toString();
        int id = Integer.parseInt(transId.replace("VWT", ""));
        showReceipt(id);
    }

    private void showReceipt(int transactionId) {
        JDialog receiptDialog = new JDialog();
        receiptDialog.setTitle("Receipt - Transaction VWT" + transactionId);
        receiptDialog.setSize(400, 500);
        receiptDialog.setLocationRelativeTo(this);
        receiptDialog.setModal(true);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        try {
            ResultSet rs = Config.getReceiptData(transactionId);
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

                addReceiptRow(panel, "Receipt No:", "VWT" + transactionId, 20, y);
                y += 25;
                addReceiptRow(panel, "Date:", rs.getString("Order_Date"), 20, y);
                y += 25;
                addReceiptRow(panel, "Customer:", rs.getString("First_Name") + " " + rs.getString("Last_Name"), 20, y);
                y += 25;
                addReceiptRow(panel, "Phone:", rs.getString("Phone_Number"), 20, y);
                y += 25;
                addReceiptRow(panel, "Address:", rs.getString("Default_Address"), 20, y);
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

                // Get order items
                ResultSet itemsRs = Config.getOrderItems(transactionId);
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
                y += 25;
                addReceiptRow(panel, "Processed By:", rs.getString("Processed_By"), 20, y);
                y += 40;

                JLabel lblThankYou = new JLabel("Thank you for your purchase!");
                lblThankYou.setFont(new Font("SansSerif", Font.ITALIC, 12));
                lblThankYou.setForeground(new Color(255, 215, 0));
                lblThankYou.setBounds(100, y, 200, 20);
                panel.add(lblThankYou);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JButton btnPrint = new JButton("PRINT");
        btnPrint.setBounds(150, 420, 100, 30);
        btnPrint.setBackground(new Color(255, 215, 0));
        btnPrint.addActionListener(e -> {
            // Print functionality would go here
            JOptionPane.showMessageDialog(receiptDialog, "Printing receipt...");
        });
        panel.add(btnPrint);

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
    private javax.swing.JButton btnAddItem;
    private javax.swing.JButton btnCreateTransaction;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnViewReceipt;
    private javax.swing.JTable cartTable;
    private javax.swing.JComboBox<String> cmbCustomer;
    private javax.swing.JComboBox<String> cmbPaymentMethod;
    private javax.swing.JComboBox<String> cmbProduct;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable transactionTable;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}