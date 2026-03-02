package Panels;

import Config.Config;
import Utils.TableUtils;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShippingPanel extends JPanel {
    private Dashboard dashboard;


    public ShippingPanel(Dashboard dashboard) {
        this.dashboard = dashboard;
        initComponents();
        loadShippingData();
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
        lblTitle.setText("Shipping Management");
        lblTitle.setBounds(30, 20, 400, 40);
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
        btnSearch.addActionListener(e -> searchShipping());
        addButtonHoverEffect(btnSearch);
        searchPanel.add(btnSearch);

        btnRefresh = new JButton("REFRESH");
        btnRefresh.setBounds(415, 12, 80, 30);
        btnRefresh.setBackground(new Color(100, 100, 100));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> loadShippingData());
        addButtonHoverEffect(btnRefresh);
        searchPanel.add(btnRefresh);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBounds(550, 80, 320, 50);
        buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(buttonPanel);

        btnUpdateStatus = new JButton("UPDATE STATUS");
        btnUpdateStatus.setBounds(20, 12, 140, 30);
        btnUpdateStatus.setBackground(new Color(255, 150, 0));
        btnUpdateStatus.setForeground(Color.WHITE);
        btnUpdateStatus.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnUpdateStatus.setFocusPainted(false);
        btnUpdateStatus.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUpdateStatus.addActionListener(e -> updateStatus());
        buttonPanel.add(btnUpdateStatus);

        btnTrack = new JButton("TRACK");
        btnTrack.setBounds(170, 12, 100, 30);
        btnTrack.setBackground(new Color(0, 150, 0));
        btnTrack.setForeground(Color.WHITE);
        btnTrack.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnTrack.setFocusPainted(false);
        btnTrack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTrack.addActionListener(e -> trackShipment());
        buttonPanel.add(btnTrack);

        // Table
        String[] columns = {"Ship ID", "Transaction ID", "Courier", "Tracking #", "Status", "Address", "Est. Delivery"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        shippingTable = new JTable(model);
        shippingTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        shippingTable.setRowHeight(30);
        shippingTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        shippingTable.getTableHeader().setBackground(new Color(20, 40, 60));
        shippingTable.getTableHeader().setForeground(Color.WHITE);
        shippingTable.setSelectionBackground(new Color(255, 215, 0, 100));
        shippingTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewShippingDetails();
                }
            }
        });

        scrollPane = new JScrollPane(shippingTable);
        scrollPane.setBounds(30, 150, 840, 400);
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

    private void loadShippingData() {
        try {
            ResultSet rs = Config.getAllTransactions();
            DefaultTableModel model = (DefaultTableModel) shippingTable.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                int transactionId = rs.getInt("Transaction_ID");
                if (rs.getString("Status").equals("Paid")) {
                    ResultSet shippingRs = Config.getShippingInfo(transactionId);
                    if (shippingRs != null && shippingRs.next()) {
                        Object[] row = {
                            "SHP" + shippingRs.getInt("Ship_ID"),
                            "VWT" + transactionId,
                            shippingRs.getString("Courier_Name") != null ? shippingRs.getString("Courier_Name") : "Not Assigned",
                            shippingRs.getString("Tracking_Number"),
                            shippingRs.getString("Shipping_Status"),
                            shippingRs.getString("Shipping_Address"),
                            shippingRs.getString("Estimated_Delivery")
                        };
                        model.addRow(row);
                    }
                }
            }
            
            TableUtils.setColumnWidths(shippingTable, 80, 100, 120, 150, 100, 200, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchShipping() {
        String searchTerm = txtSearch.getText().trim().toLowerCase();
        if (searchTerm.isEmpty()) {
            loadShippingData();
            return;
        }

        try {
            DefaultTableModel model = (DefaultTableModel) shippingTable.getModel();
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

    private void updateStatus() {
        int selectedRow = shippingTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a shipment");
            return;
        }

        String shipId = shippingTable.getValueAt(selectedRow, 0).toString().replace("SHP", "");
        String currentStatus = shippingTable.getValueAt(selectedRow, 4).toString();

        String[] options = {"Processing", "In Transit", "Out for Delivery", "Delivered", "Failed Delivery"};
        String newStatus = (String) JOptionPane.showInputDialog(
            this,
            "Select new status:",
            "Update Shipping Status",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            currentStatus
        );

        if (newStatus != null && !newStatus.equals(currentStatus)) {
            if (Config.updateShippingStatus(Integer.parseInt(shipId), newStatus)) {
                JOptionPane.showMessageDialog(this, "Status updated successfully!");
                loadShippingData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update status");
            }
        }
    }

    private void trackShipment() {
        int selectedRow = shippingTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a shipment");
            return;
        }

        String tracking = shippingTable.getValueAt(selectedRow, 3).toString();
        String courier = shippingTable.getValueAt(selectedRow, 2).toString();
        String status = shippingTable.getValueAt(selectedRow, 4).toString();

        String message = "Tracking Information:\n\n" +
                        "Courier: " + courier + "\n" +
                        "Tracking Number: " + tracking + "\n" +
                        "Current Status: " + status + "\n\n" +
                        "Track your package at: " + getTrackingUrl(courier, tracking);

        JOptionPane.showMessageDialog(this, message, "Shipment Tracking", JOptionPane.INFORMATION_MESSAGE);
    }

    private String getTrackingUrl(String courier, String tracking) {
        switch (courier) {
            case "DHL":
                return "https://www.dhl.com/en/express/tracking.html?AWB=" + tracking;
            case "FedEx":
                return "https://www.fedex.com/apps/fedextrack/?tracknumbers=" + tracking;
            case "UPS":
                return "https://www.ups.com/track?tracknum=" + tracking;
            default:
                return "Please check carrier website";
        }
    }

    private void viewShippingDetails() {
        int selectedRow = shippingTable.getSelectedRow();
        if (selectedRow >= 0) {
            String shipId = shippingTable.getValueAt(selectedRow, 0).toString();
            String transId = shippingTable.getValueAt(selectedRow, 1).toString();
            String tracking = shippingTable.getValueAt(selectedRow, 3).toString();
            String status = shippingTable.getValueAt(selectedRow, 4).toString();
            String address = shippingTable.getValueAt(selectedRow, 5).toString();
            String delivery = shippingTable.getValueAt(selectedRow, 6).toString();

            JOptionPane.showMessageDialog(this,
                "Shipping Details:\n\n" +
                "Shipment ID: " + shipId + "\n" +
                "Transaction: " + transId + "\n" +
                "Tracking #: " + tracking + "\n" +
                "Status: " + status + "\n" +
                "Address: " + address + "\n" +
                "Est. Delivery: " + delivery,
                "Shipping Information",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnTrack;
    private javax.swing.JButton btnUpdateStatus;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable shippingTable;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}