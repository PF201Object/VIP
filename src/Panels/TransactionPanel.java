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
        
        // Setup button listeners
        setupButtonListeners();
        
        // Add hover effects
        setupButtonHoverEffects();
        
        // Add mouse listener for double-click on table
        transactionTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewReceipt();
                }
            }
        });
    }

    // ==================== SETUP METHODS ====================
    
    private void setupButtonListeners() {
        btnNewTransaction.addActionListener(e -> openNewTransactionDialog());
        btnViewCart.addActionListener(e -> openCartDialog());
        btnRefresh.addActionListener(e -> loadTransactions());
        btnViewReceipt.addActionListener(e -> viewReceipt());
    }
    
    private void setupButtonHoverEffects() {
        addButtonHoverEffect(btnNewTransaction, new Color(255, 215, 0), new Color(255, 240, 150));
        addButtonHoverEffect(btnViewCart, new Color(70, 130, 180), new Color(100, 160, 210));
        addButtonHoverEffect(btnRefresh, new Color(100, 100, 100), new Color(130, 130, 130));
        addButtonHoverEffect(btnViewReceipt, new Color(255, 150, 0), new Color(255, 180, 0));
    }

    private void addButtonHoverEffect(JButton button, Color baseColor, Color hoverColor) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(hoverColor);
                if (baseColor.equals(new Color(255, 215, 0))) {
                    button.setForeground(Color.BLACK);
                }
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(baseColor);
                if (baseColor.equals(new Color(255, 215, 0))) {
                    button.setForeground(new Color(40, 40, 40));
                } else {
                    button.setForeground(Color.WHITE);
                }
            }
        });
    }

    /**
     * NetBeans generated code - UPDATED with new buttons
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();
        actionPanel = new javax.swing.JPanel();
        btnNewTransaction = new javax.swing.JButton();
        btnViewCart = new javax.swing.JButton();
        lblHistory = new javax.swing.JLabel();
        btnViewReceipt = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        transactionTable = new javax.swing.JTable();
        lblHint = new javax.swing.JLabel();

        setBackground(new java.awt.Color(240, 240, 245));
        setPreferredSize(new java.awt.Dimension(900, 600));
        setLayout(null);

        lblTitle.setFont(new java.awt.Font("Serif", 1, 28)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(20, 40, 60));
        lblTitle.setText("Transaction Processing");
        add(lblTitle);
        lblTitle.setBounds(10, 20, 279, 37);

        separator.setForeground(new java.awt.Color(255, 215, 0));
        add(separator);
        separator.setBounds(10, 60, 290, 10);

        actionPanel.setBackground(new java.awt.Color(240, 240, 245));
        actionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Transaction Actions"));
        actionPanel.setLayout(null);

        btnNewTransaction.setBackground(new java.awt.Color(255, 215, 0));
        btnNewTransaction.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnNewTransaction.setForeground(new java.awt.Color(40, 40, 40));
        btnNewTransaction.setText("NEW TRANSACTION");
        actionPanel.add(btnNewTransaction);
        btnNewTransaction.setBounds(20, 20, 180, 30);

        btnViewCart.setBackground(new java.awt.Color(70, 130, 180));
        btnViewCart.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnViewCart.setForeground(new java.awt.Color(255, 255, 255));
        btnViewCart.setText("VIEW CART");
        actionPanel.add(btnViewCart);
        btnViewCart.setBounds(260, 20, 110, 30);

        add(actionPanel);
        actionPanel.setBounds(350, 50, 420, 60);

        lblHistory.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        lblHistory.setForeground(new java.awt.Color(20, 40, 60));
        lblHistory.setText("Transaction History");
        add(lblHistory);
        lblHistory.setBounds(10, 90, 150, 21);

        btnViewReceipt.setBackground(new java.awt.Color(255, 150, 0));
        btnViewReceipt.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnViewReceipt.setForeground(new java.awt.Color(255, 255, 255));
        btnViewReceipt.setText("VIEW RECEIPT");
        add(btnViewReceipt);
        btnViewReceipt.setBounds(10, 360, 120, 30);

        btnRefresh.setBackground(new java.awt.Color(100, 100, 100));
        btnRefresh.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("REFRESH");
        add(btnRefresh);
        btnRefresh.setBounds(180, 360, 100, 30);

        scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        transactionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Trans ID", "Customer", "Date", "Total", "Payment", "Status", "Created By"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane.setViewportView(transactionTable);

        add(scrollPane);
        scrollPane.setBounds(10, 120, 760, 230);

        lblHint.setFont(new java.awt.Font("SansSerif", 2, 11)); // NOI18N
        lblHint.setForeground(new java.awt.Color(100, 100, 100));
        lblHint.setText("Double-click on a row to view receipt");
        add(lblHint);
        lblHint.setBounds(10, 70, 181, 15);
    }// </editor-fold>//GEN-END:initComponents

    // ==================== DIALOG WINDOWS ====================
    
    private void openNewTransactionDialog() {
    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "New Transaction", true);
    dialog.setSize(500, 350); // Reduced height since payment method removed
    dialog.setLocationRelativeTo(this);
    dialog.setLayout(null);
    dialog.getContentPane().setBackground(new Color(240, 240, 245));

    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setBounds(20, 20, 450, 270);
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
    dialog.add(panel);

    JLabel lblTitle = new JLabel("CREATE NEW TRANSACTION");
    lblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
    lblTitle.setForeground(new Color(20, 40, 60));
    lblTitle.setBounds(120, 15, 250, 25);
    panel.add(lblTitle);

    // Customer Selection
    JLabel lblCustomer = new JLabel("Customer:");
    lblCustomer.setFont(new Font("SansSerif", Font.BOLD, 12));
    lblCustomer.setBounds(30, 60, 80, 25);
    panel.add(lblCustomer);

    JComboBox<String> cmbCustomerDialog = new JComboBox<>();
    cmbCustomerDialog.setBounds(120, 60, 280, 30);
    cmbCustomerDialog.setBackground(Color.WHITE);
    cmbCustomerDialog.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
    loadCustomersToCombo(cmbCustomerDialog);
    panel.add(cmbCustomerDialog);

    // Product Selection
    JLabel lblProduct = new JLabel("Product:");
    lblProduct.setFont(new Font("SansSerif", Font.BOLD, 12));
    lblProduct.setBounds(30, 105, 80, 25);
    panel.add(lblProduct);

    JComboBox<String> cmbProductDialog = new JComboBox<>();
    cmbProductDialog.setBounds(120, 105, 280, 30);
    cmbProductDialog.setBackground(Color.WHITE);
    cmbProductDialog.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
    loadProductsToCombo(cmbProductDialog);
    panel.add(cmbProductDialog);

    // Price (fixed, non-editable)
    JLabel lblPrice = new JLabel("Price:");
    lblPrice.setFont(new Font("SansSerif", Font.BOLD, 12));
    lblPrice.setBounds(30, 150, 80, 25);
    panel.add(lblPrice);

    JTextField txtPriceDialog = new JTextField();
    txtPriceDialog.setBounds(120, 150, 120, 30);
    txtPriceDialog.setEditable(false);
    txtPriceDialog.setBackground(new Color(240, 240, 240));
    txtPriceDialog.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
    panel.add(txtPriceDialog);

    // Quantity
    JLabel lblQty = new JLabel("Quantity:");
    lblQty.setFont(new Font("SansSerif", Font.BOLD, 12));
    lblQty.setBounds(260, 150, 70, 25);
    panel.add(lblQty);

    JTextField txtQuantityDialog = new JTextField("1");
    txtQuantityDialog.setBounds(330, 150, 70, 30);
    txtQuantityDialog.setHorizontalAlignment(JTextField.RIGHT);
    txtQuantityDialog.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
    panel.add(txtQuantityDialog);

    // Product selection listener
    cmbProductDialog.addActionListener(e -> {
        int index = cmbProductDialog.getSelectedIndex();
        if (index > 0) {
            String selected = cmbProductDialog.getSelectedItem().toString();
            String priceStr = selected.substring(selected.lastIndexOf("$") + 1, selected.lastIndexOf(")"));
            txtPriceDialog.setText(priceStr);
        } else {
            txtPriceDialog.setText("");
        }
    });

    // Store customer selection in a local array to modify inside listener
    int[] tempCustomerId = {-1};
    
    cmbCustomerDialog.addActionListener(e -> {
        int index = cmbCustomerDialog.getSelectedIndex();
        if (index > 0) {
            String selected = cmbCustomerDialog.getSelectedItem().toString();
            tempCustomerId[0] = Integer.parseInt(selected.split(" - ")[0]);
        } else {
            tempCustomerId[0] = -1;
        }
    });

    // Buttons
    JButton btnAddToCart = new JButton("ADD TO CART");
    btnAddToCart.setBounds(120, 200, 200, 40);
    btnAddToCart.setBackground(new Color(255, 215, 0));
    btnAddToCart.setFont(new Font("SansSerif", Font.BOLD, 14));
    btnAddToCart.setForeground(new Color(40, 40, 40));
    btnAddToCart.setBorderPainted(false);
    btnAddToCart.setFocusPainted(false);
    btnAddToCart.setCursor(new Cursor(Cursor.HAND_CURSOR));

    btnAddToCart.addActionListener(e -> {
        if (tempCustomerId[0] == -1) {
            JOptionPane.showMessageDialog(dialog, "Please select a customer");
            return;
        }
        if (cmbProductDialog.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(dialog, "Please select a product");
            return;
        }

        String qtyStr = txtQuantityDialog.getText().trim();
        if (qtyStr.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Please enter quantity");
            return;
        }

        int qty;
        try {
            qty = Integer.parseInt(qtyStr);
            if (qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Invalid quantity");
            return;
        }

        String selected = cmbProductDialog.getSelectedItem().toString();
        String[] parts = selected.split(" - ");
        int watchId = Integer.parseInt(parts[0].trim());
        String productName = selected.substring(selected.indexOf("-") + 2, selected.lastIndexOf(" ($")).trim();
        double price = Double.parseDouble(txtPriceDialog.getText());
        double subtotal = price * qty;

        // Set the selected customer ID in the main panel
        selectedCustomerId = tempCustomerId[0];

        // Store in cart
        Object[] row = {
            productName,
            qty,
            String.format("$%.2f", price),
            String.format("$%.2f", subtotal),
            watchId
        };
        
        if (cartModel == null) {
            String[] cartColumns = {"Product", "Qty", "Price", "Subtotal", "WatchID"};
            cartModel = new DefaultTableModel(cartColumns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        }
        
        cartModel.addRow(row);
        grandTotal += subtotal;
        
        JOptionPane.showMessageDialog(dialog, "Item added to cart!\nCustomer ID: " + selectedCustomerId);
        dialog.dispose();
    });

    panel.add(btnAddToCart);

    JButton btnCancel = new JButton("CANCEL");
    btnCancel.setBounds(330, 200, 100, 40);
    btnCancel.setBackground(new Color(100, 100, 100));
    btnCancel.setFont(new Font("SansSerif", Font.BOLD, 12));
    btnCancel.setForeground(Color.WHITE);
    btnCancel.setBorderPainted(false);
    btnCancel.setFocusPainted(false);
    btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btnCancel.addActionListener(e -> dialog.dispose());
    panel.add(btnCancel);

    dialog.setVisible(true);
}

   private void openCartDialog() {
    if (cartModel == null || cartModel.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Cart is empty");
        return;
    }

    // Check if customer is selected
    if (selectedCustomerId == -1) {
        JOptionPane.showMessageDialog(this, "Please select a customer first");
        return;
    }

    // Get customer name for display
    String customerName = "Unknown";
    try {
        ResultSet rs = Config.getCustomerById(selectedCustomerId);
        if (rs != null && rs.next()) {
            customerName = rs.getString("First_Name") + " " + rs.getString("Last_Name");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Shopping Cart", true);
    dialog.setSize(600, 550);
    dialog.setLocationRelativeTo(this);
    dialog.setLayout(null);
    dialog.getContentPane().setBackground(new Color(240, 240, 245));

    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setBounds(20, 20, 550, 470);
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
    dialog.add(panel);

    JLabel lblTitle = new JLabel("SHOPPING CART");
    lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
    lblTitle.setForeground(new Color(20, 40, 60));
    lblTitle.setBounds(200, 15, 200, 25);
    panel.add(lblTitle);

    // Customer Info Display
    JLabel lblCustomerInfo = new JLabel("Customer: " + customerName + " (ID: " + selectedCustomerId + ")");
    lblCustomerInfo.setFont(new Font("SansSerif", Font.BOLD, 12));
    lblCustomerInfo.setForeground(new Color(255, 215, 0));
    lblCustomerInfo.setBounds(30, 45, 400, 20);
    panel.add(lblCustomerInfo);

    // Cart Table
    JTable cartTableDialog = new JTable(cartModel);
    cartTableDialog.setFont(new Font("SansSerif", Font.PLAIN, 12));
    cartTableDialog.setRowHeight(30);
    cartTableDialog.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
    cartTableDialog.getTableHeader().setBackground(new Color(20, 40, 60));
    cartTableDialog.getTableHeader().setForeground(Color.WHITE);
    
    JScrollPane cartScroll = new JScrollPane(cartTableDialog);
    cartScroll.setBounds(30, 70, 490, 200);
    cartScroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
    panel.add(cartScroll);

    // Grand Total
    JLabel lblGrandTotal = new JLabel("GRAND TOTAL:");
    lblGrandTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
    lblGrandTotal.setForeground(new Color(20, 40, 60));
    lblGrandTotal.setBounds(30, 290, 120, 30);
    panel.add(lblGrandTotal);

    JTextField txtGrandTotal = new JTextField(String.format("$%.2f", grandTotal));
    txtGrandTotal.setBounds(160, 290, 150, 30);
    txtGrandTotal.setEditable(false);
    txtGrandTotal.setFont(new Font("SansSerif", Font.BOLD, 14));
    txtGrandTotal.setHorizontalAlignment(JTextField.RIGHT);
    txtGrandTotal.setBackground(new Color(240, 240, 240));
    txtGrandTotal.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
    panel.add(txtGrandTotal);

    // Payment Method
    JLabel lblPayment = new JLabel("Payment Method:");
    lblPayment.setFont(new Font("SansSerif", Font.BOLD, 14));
    lblPayment.setBounds(30, 340, 130, 25);
    panel.add(lblPayment);

    JComboBox<String> cmbPaymentDialog = new JComboBox<>(new String[]{"Credit Card", "PayPal", "Gcash", "Bank Transfer"});
    cmbPaymentDialog.setBounds(170, 340, 200, 30);
    cmbPaymentDialog.setBackground(Color.WHITE);
    cmbPaymentDialog.setFont(new Font("SansSerif", Font.PLAIN, 12));
    cmbPaymentDialog.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
    panel.add(cmbPaymentDialog);

    // Buttons
    JButton btnComplete = new JButton("COMPLETE TRANSACTION");
    btnComplete.setBounds(120, 400, 200, 35);
    btnComplete.setBackground(new Color(0, 150, 0));
    btnComplete.setFont(new Font("SansSerif", Font.BOLD, 12));
    btnComplete.setForeground(Color.WHITE);
    btnComplete.setBorderPainted(false);
    btnComplete.setFocusPainted(false);
    btnComplete.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
    btnComplete.addActionListener(e -> {
        String paymentMethod = (String) cmbPaymentDialog.getSelectedItem();
        String status = Config.isAdmin() ? "Paid" : "Pending";

        int transactionId = Config.createTransaction(selectedCustomerId, grandTotal, paymentMethod, status);

        if (transactionId != -1) {
            // Add order items
            boolean allItemsAdded = true;
            for (int i = 0; i < cartModel.getRowCount(); i++) {
                int watchId = Integer.parseInt(cartModel.getValueAt(i, 4).toString());
                int qty = Integer.parseInt(cartModel.getValueAt(i, 1).toString());
                double price = Double.parseDouble(cartModel.getValueAt(i, 2).toString().replace("$", ""));
                
                if (!Config.addOrderItem(transactionId, watchId, qty, price)) {
                    allItemsAdded = false;
                    System.err.println("Failed to add item: " + cartModel.getValueAt(i, 0));
                }
            }

            if (allItemsAdded) {
                String message = status.equals("Paid") ? 
                    "Transaction completed successfully!" : 
                    "Transaction submitted for approval. Waiting for admin authorization.";

                JOptionPane.showMessageDialog(dialog, message);

                // Clear cart
                cartModel.setRowCount(0);
                grandTotal = 0;
                selectedCustomerId = -1;

                dialog.dispose();
                loadTransactions();

                if (status.equals("Paid")) {
                    showReceipt(transactionId);
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Some items failed to add to the transaction.");
            }
        } else {
            JOptionPane.showMessageDialog(dialog, "Failed to create transaction. Please check customer ID and try again.");
        }
    });
    
    panel.add(btnComplete);

    JButton btnCancel = new JButton("CANCEL");
    btnCancel.setBounds(330, 400, 100, 35);
    btnCancel.setBackground(new Color(100, 100, 100));
    btnCancel.setFont(new Font("SansSerif", Font.BOLD, 12));
    btnCancel.setForeground(Color.WHITE);
    btnCancel.setBorderPainted(false);
    btnCancel.setFocusPainted(false);
    btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btnCancel.addActionListener(e -> dialog.dispose());
    panel.add(btnCancel);

    dialog.setVisible(true);
}

    // ==================== HELPER METHODS ====================

    private void loadCustomersToCombo(JComboBox<String> combo) {
        try {
            ResultSet rs = Config.getAllCustomers();
            combo.removeAllItems();
            combo.addItem("Select Customer");
            
            while (rs.next()) {
                String item = rs.getInt("Customer_ID") + " - " + rs.getString("First_Name") + " " + rs.getString("Last_Name");
                combo.addItem(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProductsToCombo(JComboBox<String> combo) {
        try {
            ResultSet rs = Config.getAllProducts();
            combo.removeAllItems();
            combo.addItem("Select Product");
            
            while (rs.next()) {
                String item = rs.getInt("Watch_ID") + " - " + rs.getString("Brand") + " " + rs.getString("Model_Name") + " ($" + rs.getDouble("Price") + ")";
                combo.addItem(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCustomers() {
        // Kept for compatibility
    }

    private void loadProducts() {
        // Kept for compatibility
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
        // Keep your existing showReceipt method
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
                lblThankYou.setBounds(100, 430, 200, 20);
                panel.add(lblThankYou);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JButton btnPrint = new JButton("PRINT");
        btnPrint.setBounds(150, 460, 100, 30);
        btnPrint.setBackground(new Color(255, 215, 0));
        btnPrint.addActionListener(e -> {
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

    // ==================== VARIABLES DECLARATION ====================

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton btnNewTransaction;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnViewCart;
    private javax.swing.JButton btnViewReceipt;
    private javax.swing.JLabel lblHint;
    private javax.swing.JLabel lblHistory;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JSeparator separator;
    private javax.swing.JTable transactionTable;
    // End of variables declaration//GEN-END:variables
}