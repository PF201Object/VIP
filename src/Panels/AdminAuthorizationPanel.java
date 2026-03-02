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
        lblTitle.setText("Pending Authorizations");
        lblTitle.setBounds(30, 20, 400, 40);
        add(lblTitle);

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(255, 215, 0));
        separator.setBounds(30, 65, 840, 2);
        add(separator);

        // Count Panel
        JPanel countPanel = new JPanel();
        countPanel.setLayout(null);
        countPanel.setBackground(new Color(255, 215, 0));
        countPanel.setBounds(30, 80, 200, 60);
        countPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(countPanel);

        JLabel lblPending = new JLabel("PENDING");
        lblPending.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblPending.setForeground(new Color(20, 40, 60));
        lblPending.setBounds(20, 10, 100, 20);
        countPanel.add(lblPending);

        lblCount = new JLabel("0");
        lblCount.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblCount.setForeground(new Color(20, 40, 60));
        lblCount.setBounds(150, 10, 50, 40);
        countPanel.add(lblCount);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBounds(550, 80, 320, 60);
        buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(buttonPanel);

        btnApprove = new JButton("APPROVE");
        btnApprove.setBounds(20, 15, 100, 30);
        btnApprove.setBackground(new Color(0, 150, 0));
        btnApprove.setForeground(Color.WHITE);
        btnApprove.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnApprove.setFocusPainted(false);
        btnApprove.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnApprove.addActionListener(e -> approveTransaction());
        addButtonHoverEffect(btnApprove);
        buttonPanel.add(btnApprove);

        btnReject = new JButton("REJECT");
        btnReject.setBounds(130, 15, 100, 30);
        btnReject.setBackground(new Color(200, 0, 0));
        btnReject.setForeground(Color.WHITE);
        btnReject.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnReject.setFocusPainted(false);
        btnReject.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReject.addActionListener(e -> rejectTransaction());
        addButtonHoverEffect(btnReject);
        buttonPanel.add(btnReject);

        btnViewDetails = new JButton("VIEW");
        btnViewDetails.setBounds(240, 15, 70, 30);
        btnViewDetails.setBackground(new Color(255, 150, 0));
        btnViewDetails.setForeground(Color.WHITE);
        btnViewDetails.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnViewDetails.setFocusPainted(false);
        btnViewDetails.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnViewDetails.addActionListener(e -> viewDetails());
        addButtonHoverEffect(btnViewDetails);
        buttonPanel.add(btnViewDetails);

        btnRefresh = new JButton("REFRESH");
        btnRefresh.setBounds(740, 90, 100, 30);
        btnRefresh.setBackground(new Color(100, 100, 100));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> loadPendingTransactions());
        addButtonHoverEffect(btnRefresh);
        add(btnRefresh);

        // Table
        String[] columns = {"Trans ID", "Customer", "Date", "Amount", "Payment Method", "Created By"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        pendingTable = new JTable(model);
        pendingTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        pendingTable.setRowHeight(30);
        pendingTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        pendingTable.getTableHeader().setBackground(new Color(20, 40, 60));
        pendingTable.getTableHeader().setForeground(Color.WHITE);
        pendingTable.setSelectionBackground(new Color(255, 215, 0, 100));
        pendingTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewDetails();
                }
            }
        });

        scrollPane = new JScrollPane(pendingTable);
        scrollPane.setBounds(30, 160, 840, 350);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(scrollPane);

        JLabel lblHint = new JLabel("Double-click on a row to view full transaction details");
        lblHint.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lblHint.setForeground(new Color(100, 100, 100));
        lblHint.setBounds(30, 520, 300, 20);
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
        }
    }

    private void approveTransaction() {
        int selectedRow = pendingTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a transaction");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to approve this transaction?\n\n" +
            "This will mark it as PAID and create shipping record.",
            "Confirm Approval",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String transId = pendingTable.getValueAt(selectedRow, 0).toString().replace("VWT", "");
            int id = Integer.parseInt(transId);

            if (Config.updateTransactionStatus(id, "Paid")) {
                JOptionPane.showMessageDialog(this, "Transaction approved successfully!");
                loadPendingTransactions();
                
                // Show shipping created message
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
            JOptionPane.showMessageDialog(this, "Please select a transaction");
            return;
        }

        String reason = JOptionPane.showInputDialog(this,
            "Please enter reason for rejection:",
            "Reject Transaction",
            JOptionPane.WARNING_MESSAGE);

        if (reason != null && !reason.trim().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to reject this transaction?\nReason: " + reason,
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
        if (selectedRow < 0) return;

        String transId = pendingTable.getValueAt(selectedRow, 0).toString();
        String customer = pendingTable.getValueAt(selectedRow, 1).toString();
        String date = pendingTable.getValueAt(selectedRow, 2).toString();
        String amount = pendingTable.getValueAt(selectedRow, 3).toString();
        String payment = pendingTable.getValueAt(selectedRow, 4).toString();
        String createdBy = pendingTable.getValueAt(selectedRow, 5).toString();

        JDialog detailDialog = new JDialog();
        detailDialog.setTitle("Transaction Details - " + transId);
        detailDialog.setSize(400, 350);
        detailDialog.setLocationRelativeTo(this);
        detailDialog.setModal(true);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        int y = 30;

        JLabel lblHeader = new JLabel("TRANSACTION DETAILS");
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblHeader.setForeground(new Color(20, 40, 60));
        lblHeader.setBounds(100, y, 200, 25);
        panel.add(lblHeader);
        y += 40;

        addDetailRow(panel, "Transaction ID:", transId, 30, y);
        y += 30;
        addDetailRow(panel, "Customer:", customer, 30, y);
        y += 30;
        addDetailRow(panel, "Date:", date, 30, y);
        y += 30;
        addDetailRow(panel, "Amount:", amount, 30, y);
        y += 30;
        addDetailRow(panel, "Payment Method:", payment, 30, y);
        y += 30;
        addDetailRow(panel, "Created By:", createdBy, 30, y);
        y += 40;

        JLabel lblStatus = new JLabel("Status: PENDING APPROVAL");
        lblStatus.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblStatus.setForeground(new Color(255, 150, 0));
        lblStatus.setBounds(100, y, 200, 25);
        panel.add(lblStatus);

        JButton btnClose = new JButton("CLOSE");
        btnClose.setBounds(150, 260, 100, 30);
        btnClose.setBackground(new Color(100, 100, 100));
        btnClose.setForeground(Color.WHITE);
        btnClose.addActionListener(e -> detailDialog.dispose());
        panel.add(btnClose);

        detailDialog.add(panel);
        detailDialog.setVisible(true);
    }

    private void addDetailRow(JPanel panel, String label, String value, int x, int y) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblLabel.setBounds(x, y, 120, 20);
        panel.add(lblLabel);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblValue.setBounds(x + 130, y, 200, 20);
        panel.add(lblValue);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApprove;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReject;
    private javax.swing.JButton btnViewDetails;
    private javax.swing.JLabel lblCount;
    private javax.swing.JTable pendingTable;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}