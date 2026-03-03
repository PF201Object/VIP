package Panels;

import Config.Config;
import Utils.TableUtils;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminAuthorizationPanel extends JPanel {
    private Dashboard dashboard;

    public AdminAuthorizationPanel(Dashboard dashboard) {
        this.dashboard = dashboard;
        initComponents();
        loadPendingTransactions();
        
        // Setup button listeners
        setupButtonListeners();
        
        // Add hover effects
        setupButtonHoverEffects();
        
        // Add mouse listener for double-click on table
        pendingTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewDetails();
                }
            }
        });
    }

    // ==================== SETUP METHODS ====================
    
    private void setupButtonListeners() {
        // Remove existing listeners
        for (ActionListener al : btnApprove.getActionListeners()) {
            btnApprove.removeActionListener(al);
        }
        for (ActionListener al : btnReject.getActionListeners()) {
            btnReject.removeActionListener(al);
        }
        for (ActionListener al : btnViewDetails.getActionListeners()) {
            btnViewDetails.removeActionListener(al);
        }
        for (ActionListener al : btnRefresh.getActionListeners()) {
            btnRefresh.removeActionListener(al);
        }
        
        // Add new listeners
        btnApprove.addActionListener(e -> approveTransaction());
        btnReject.addActionListener(e -> rejectTransaction());
        btnViewDetails.addActionListener(e -> viewDetails());
        btnRefresh.addActionListener(e -> loadPendingTransactions());
    }
    
    private void setupButtonHoverEffects() {
        addButtonHoverEffect(btnApprove, new Color(0, 150, 0), new Color(0, 180, 0));
        addButtonHoverEffect(btnReject, new Color(200, 0, 0), new Color(230, 0, 0));
        addButtonHoverEffect(btnViewDetails, new Color(255, 150, 0), new Color(255, 180, 0));
        addButtonHoverEffect(btnRefresh, new Color(100, 100, 100), new Color(130, 130, 130));
    }

    private void addButtonHoverEffect(JButton button, Color baseColor, Color hoverColor) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
    }

    /**
     * NetBeans generated code
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();
        countPanel = new javax.swing.JPanel();
        lblPending = new javax.swing.JLabel();
        lblCount = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        btnApprove = new javax.swing.JButton();
        btnReject = new javax.swing.JButton();
        btnViewDetails = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        pendingTable = new javax.swing.JTable();
        lblHint = new javax.swing.JLabel();

        setBackground(new java.awt.Color(240, 240, 245));
        setPreferredSize(new java.awt.Dimension(900, 600));
        setLayout(null);

        lblTitle.setFont(new java.awt.Font("Serif", 1, 28)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(20, 40, 60));
        lblTitle.setText("Pending Authorizations");
        add(lblTitle);
        lblTitle.setBounds(10, 20, 286, 37);

        separator.setForeground(new java.awt.Color(255, 215, 0));
        add(separator);
        separator.setBounds(10, 60, 290, 20);

        countPanel.setBackground(new java.awt.Color(255, 215, 0));
        countPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        countPanel.setLayout(null);

        lblPending.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblPending.setForeground(new java.awt.Color(20, 40, 60));
        lblPending.setText("PENDING:");
        countPanel.add(lblPending);
        lblPending.setBounds(10, 0, 60, 30);

        lblCount.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblCount.setForeground(new java.awt.Color(20, 40, 60));
        lblCount.setText("0");
        countPanel.add(lblCount);
        lblCount.setBounds(80, 0, 13, 32);

        add(countPanel);
        countPanel.setBounds(10, 80, 140, 30);

        buttonPanel.setBackground(new java.awt.Color(255, 255, 255));
        buttonPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        buttonPanel.setLayout(null);

        btnApprove.setBackground(new java.awt.Color(0, 150, 0));
        btnApprove.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnApprove.setForeground(new java.awt.Color(255, 255, 255));
        btnApprove.setText("APPROVE");
        buttonPanel.add(btnApprove);

        btnReject.setBackground(new java.awt.Color(200, 0, 0));
        btnReject.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnReject.setForeground(new java.awt.Color(255, 255, 255));
        btnReject.setText("REJECT");
        buttonPanel.add(btnReject);

        btnViewDetails.setBackground(new java.awt.Color(255, 150, 0));
        btnViewDetails.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnViewDetails.setForeground(new java.awt.Color(255, 255, 255));
        btnViewDetails.setText("VIEW");
        buttonPanel.add(btnViewDetails);

        add(buttonPanel);
        buttonPanel.setBounds(0, 0, 0, 0);

        btnRefresh.setBackground(new java.awt.Color(100, 100, 100));
        btnRefresh.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("REFRESH");
        add(btnRefresh);
        btnRefresh.setBounds(10, 360, 100, 30);

        scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        pendingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Trans ID", "Customer", "Date", "Amount", "Payment Method", "Created By"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane.setViewportView(pendingTable);

        add(scrollPane);
        scrollPane.setBounds(10, 120, 760, 230);

        lblHint.setFont(new java.awt.Font("SansSerif", 2, 11)); // NOI18N
        lblHint.setForeground(new java.awt.Color(100, 100, 100));
        lblHint.setText("Double-click on a row to view full transaction details");
        add(lblHint);
        lblHint.setBounds(10, 60, 256, 15);
    }// </editor-fold>//GEN-END:initComponents

      private void loadPendingTransactions() {
        try {
            ResultSet rs = Config.getPendingTransactions();
            DefaultTableModel model = (DefaultTableModel) pendingTable.getModel();
            model.setRowCount(0);
            
            int count = 0;
            
            while (rs.next()) {
                Object[] row = {
                    "VWT" + rs.getInt("Transaction_ID"),
                    rs.getString("First_Name") + " " + rs.getString("Last_Name"),
                    rs.getString("Order_Date"),
                    String.format("$%.2f", rs.getDouble("Total_Amount")),
                    rs.getString("Payment_Method"),
                    "User" + rs.getInt("Created_By")
                };
                model.addRow(row);
                count++;
            }
            
            lblCount.setText(String.valueOf(count));
            TableUtils.setColumnWidths(pendingTable, 100, 150, 150, 100, 120, 100);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading pending transactions: " + e.getMessage());
        }
    }

    private void approveTransaction() {
        int selectedRow = pendingTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a transaction to approve");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to approve this transaction?\n\n" +
            "This will mark it as PAID and create a shipping record.",
            "Confirm Approval",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String transId = pendingTable.getValueAt(selectedRow, 0).toString().replace("VWT", "");
            int id = Integer.parseInt(transId);

            if (Config.updateTransactionStatus(id, "Paid")) {
                JOptionPane.showMessageDialog(this, "Transaction approved successfully!");
                loadPendingTransactions();
                
                JOptionPane.showMessageDialog(this,
                    "Shipping record has been created.\nTracking number has been generated.",
                    "Shipping Created",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to approve transaction");
            }
        }
    }

    private void rejectTransaction() {
        int selectedRow = pendingTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a transaction to reject");
            return;
        }

        String reason = JOptionPane.showInputDialog(this,
            "Please enter reason for rejection:",
            "Reject Transaction",
            JOptionPane.WARNING_MESSAGE);

        if (reason != null && !reason.trim().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to reject this transaction?\n\nReason: " + reason,
                "Confirm Rejection",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String transId = pendingTable.getValueAt(selectedRow, 0).toString().replace("VWT", "");
                int id = Integer.parseInt(transId);

                if (Config.updateTransactionStatus(id, "Rejected")) {
                    JOptionPane.showMessageDialog(this, "Transaction rejected successfully!");
                    loadPendingTransactions();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to reject transaction");
                }
            }
        }
    }

    private void viewDetails() {
    int selectedRow = pendingTable.getSelectedRow();
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this, "Please select a transaction to view");
        return;
    }

    String transId = pendingTable.getValueAt(selectedRow, 0).toString();
    String customer = pendingTable.getValueAt(selectedRow, 1).toString();
    String date = pendingTable.getValueAt(selectedRow, 2).toString();
    String amount = pendingTable.getValueAt(selectedRow, 3).toString();
    String payment = pendingTable.getValueAt(selectedRow, 4).toString();
    String createdBy = pendingTable.getValueAt(selectedRow, 5).toString();
    String transIdNum = transId.replace("VWT", "");

    JDialog detailDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Transaction Details", true);
    detailDialog.setTitle("Transaction Details - " + transId);
    detailDialog.setSize(500, 450);
    detailDialog.setLocationRelativeTo(this);
    detailDialog.setLayout(null);
    detailDialog.getContentPane().setBackground(new Color(240, 240, 245));

    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setBounds(20, 20, 450, 370);
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
    detailDialog.add(panel);

    int y = 30;

    JLabel lblHeader = new JLabel("TRANSACTION DETAILS");
    lblHeader.setFont(new Font("SansSerif", Font.BOLD, 18));
    lblHeader.setForeground(new Color(20, 40, 60));
    lblHeader.setBounds(120, y, 250, 30);
    panel.add(lblHeader);
    y += 45;

    addDetailRow(panel, "Transaction ID:", transId, 50, y);
    y += 30;
    addDetailRow(panel, "Customer:", customer, 50, y);
    y += 30;
    addDetailRow(panel, "Date:", date, 50, y);
    y += 30;
    addDetailRow(panel, "Amount:", amount, 50, y);
    y += 30;
    addDetailRow(panel, "Payment Method:", payment, 50, y);
    y += 30;
    addDetailRow(panel, "Created By:", createdBy, 50, y);
    y += 45;

    JLabel lblStatus = new JLabel("Status: PENDING APPROVAL");
    lblStatus.setFont(new Font("SansSerif", Font.BOLD, 14));
    lblStatus.setForeground(new Color(255, 150, 0));
    lblStatus.setBounds(140, y, 200, 25);
    panel.add(lblStatus);
    y += 45;

    // Buttons Panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
    buttonPanel.setBounds(50, y, 350, 40);
    buttonPanel.setBackground(Color.WHITE);
    panel.add(buttonPanel);

    // Approve Button
    JButton btnApprove = new JButton("APPROVE");
    btnApprove.setBackground(new Color(0, 150, 0));
    btnApprove.setFont(new Font("SansSerif", Font.BOLD, 12));
    btnApprove.setForeground(Color.WHITE);
    btnApprove.setPreferredSize(new Dimension(100, 35));
    btnApprove.setBorderPainted(false);
    btnApprove.setFocusPainted(false);
    btnApprove.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btnApprove.addActionListener(e -> {
        int confirm = JOptionPane.showConfirmDialog(detailDialog,
            "Approve this transaction?\nThis will mark it as PAID.",
            "Confirm Approval",
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(transIdNum);
            if (Config.updateTransactionStatus(id, "Paid")) {
                JOptionPane.showMessageDialog(detailDialog, "Transaction approved!");
                detailDialog.dispose();
                loadPendingTransactions();
            }
        }
    });
    buttonPanel.add(btnApprove);

    // Reject Button
    JButton btnReject = new JButton("REJECT");
    btnReject.setBackground(new Color(200, 0, 0));
    btnReject.setFont(new Font("SansSerif", Font.BOLD, 12));
    btnReject.setForeground(Color.WHITE);
    btnReject.setPreferredSize(new Dimension(100, 35));
    btnReject.setBorderPainted(false);
    btnReject.setFocusPainted(false);
    btnReject.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btnReject.addActionListener(e -> {
        String reason = JOptionPane.showInputDialog(detailDialog, "Reason for rejection:");
        if (reason != null && !reason.trim().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(detailDialog,
                "Reject this transaction?\nReason: " + reason,
                "Confirm Rejection",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(transIdNum);
                if (Config.updateTransactionStatus(id, "Rejected")) {
                    JOptionPane.showMessageDialog(detailDialog, "Transaction rejected!");
                    detailDialog.dispose();
                    loadPendingTransactions();
                }
            }
        }
    });
    buttonPanel.add(btnReject);

    // Close Button
    JButton btnClose = new JButton("CLOSE");
    btnClose.setBackground(new Color(100, 100, 100));
    btnClose.setFont(new Font("SansSerif", Font.BOLD, 12));
    btnClose.setForeground(Color.WHITE);
    btnClose.setPreferredSize(new Dimension(100, 35));
    btnClose.setBorderPainted(false);
    btnClose.setFocusPainted(false);
    btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btnClose.addActionListener(e -> detailDialog.dispose());
    buttonPanel.add(btnClose);

    detailDialog.setVisible(true);
}

    private void addDetailRow(JPanel panel, String label, String value, int x, int y) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblLabel.setBounds(x, y, 120, 25);
        panel.add(lblLabel);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblValue.setBounds(x + 130, y, 220, 25);
        panel.add(lblValue);
    }   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApprove;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReject;
    private javax.swing.JButton btnViewDetails;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel countPanel;
    private javax.swing.JLabel lblCount;
    private javax.swing.JLabel lblHint;
    private javax.swing.JLabel lblPending;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable pendingTable;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JSeparator separator;
    // End of variables declaration//GEN-END:variables
}