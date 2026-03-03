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
        
        // Setup button listeners
        setupButtonListeners();
        
        // Add hover effects
        setupButtonHoverEffects();
        
        // Add mouse listener for double-click on table
        shippingTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewShippingDetails();
                }
            }
        });
        
        // Add enter key listener for search
        txtSearch.addActionListener(e -> searchShipping());
    }

    // ==================== SETUP METHODS ====================
    
    private void setupButtonListeners() {
        // Remove existing listeners
        for (ActionListener al : btnSearch.getActionListeners()) {
            btnSearch.removeActionListener(al);
        }
        for (ActionListener al : btnRefresh.getActionListeners()) {
            btnRefresh.removeActionListener(al);
        }
        for (ActionListener al : btnAdd.getActionListeners()) {
            btnAdd.removeActionListener(al);
        }
        for (ActionListener al : btnUpdateStatus.getActionListeners()) {
            btnUpdateStatus.removeActionListener(al);
        }
        for (ActionListener al : btnDelete.getActionListeners()) {
            btnDelete.removeActionListener(al);
        }
        for (ActionListener al : btnTrack.getActionListeners()) {
            btnTrack.removeActionListener(al);
        }
        
        // Add new listeners
        btnSearch.addActionListener(e -> searchShipping());
        btnRefresh.addActionListener(e -> loadShippingData());
        btnAdd.addActionListener(e -> addShipment());
        btnUpdateStatus.addActionListener(e -> updateStatus());
        btnDelete.addActionListener(e -> deleteShipment());
        btnTrack.addActionListener(e -> trackShipment());
    }
    
    private void setupButtonHoverEffects() {
        addButtonHoverEffect(btnSearch, new Color(255, 215, 0), new Color(255, 240, 150));
        addButtonHoverEffect(btnRefresh, new Color(100, 100, 100), new Color(130, 130, 130));
        addButtonHoverEffect(btnAdd, new Color(0, 150, 0), new Color(0, 180, 0));
        addButtonHoverEffect(btnUpdateStatus, new Color(255, 150, 0), new Color(255, 180, 0));
        addButtonHoverEffect(btnDelete, new Color(200, 0, 0), new Color(230, 0, 0));
        addButtonHoverEffect(btnTrack, new Color(70, 130, 180), new Color(100, 160, 210));
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
        searchPanel = new javax.swing.JPanel();
        lblSearch = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        shippingTable = new javax.swing.JTable();
        buttonPanel = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnUpdateStatus = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnTrack = new javax.swing.JButton();
        lblHint = new javax.swing.JLabel();

        setBackground(new java.awt.Color(240, 240, 245));
        setPreferredSize(new java.awt.Dimension(900, 600));
        setLayout(null);

        lblTitle.setFont(new java.awt.Font("Serif", 1, 28)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(20, 40, 60));
        lblTitle.setText("Shipping Management");
        add(lblTitle);
        lblTitle.setBounds(10, 20, 272, 37);

        separator.setForeground(new java.awt.Color(255, 215, 0));
        add(separator);
        separator.setBounds(10, 60, 280, 10);

        searchPanel.setBackground(new java.awt.Color(240, 240, 245));
        searchPanel.setLayout(null);

        lblSearch.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblSearch.setText("Search:");
        searchPanel.add(lblSearch);
        lblSearch.setBounds(0, 0, 49, 19);

        txtSearch.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        searchPanel.add(txtSearch);
        txtSearch.setBounds(50, 0, 150, 30);

        btnSearch.setBackground(new java.awt.Color(255, 215, 0));
        btnSearch.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(40, 40, 40));
        btnSearch.setText("SEARCH");
        btnSearch.setBorderPainted(false);
        btnSearch.setFocusPainted(false);
        searchPanel.add(btnSearch);
        btnSearch.setBounds(210, 0, 120, 30);

        btnRefresh.setBackground(new java.awt.Color(100, 100, 100));
        btnRefresh.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("REFRESH");
        btnRefresh.setBorderPainted(false);
        btnRefresh.setFocusPainted(false);
        searchPanel.add(btnRefresh);
        btnRefresh.setBounds(360, 0, 110, 30);

        add(searchPanel);
        searchPanel.setBounds(10, 80, 470, 30);

        scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        shippingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ship ID", "Transaction ID", "Courier", "Tracking #", "Status", "Address", "Est. Delivery"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane.setViewportView(shippingTable);

        add(scrollPane);
        scrollPane.setBounds(10, 120, 760, 230);

        buttonPanel.setBackground(new java.awt.Color(240, 240, 245));
        buttonPanel.setLayout(null);

        btnAdd.setBackground(new java.awt.Color(0, 150, 0));
        btnAdd.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("ADD");
        btnAdd.setBorderPainted(false);
        btnAdd.setFocusPainted(false);
        buttonPanel.add(btnAdd);
        btnAdd.setBounds(0, 0, 80, 30);

        btnUpdateStatus.setBackground(new java.awt.Color(255, 150, 0));
        btnUpdateStatus.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnUpdateStatus.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateStatus.setText("UPDATE");
        btnUpdateStatus.setBorderPainted(false);
        btnUpdateStatus.setFocusPainted(false);
        buttonPanel.add(btnUpdateStatus);
        btnUpdateStatus.setBounds(110, 0, 90, 30);

        btnDelete.setBackground(new java.awt.Color(200, 0, 0));
        btnDelete.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("DELETE");
        btnDelete.setBorderPainted(false);
        btnDelete.setFocusPainted(false);
        buttonPanel.add(btnDelete);
        btnDelete.setBounds(230, 0, 90, 30);

        btnTrack.setBackground(new java.awt.Color(70, 130, 180));
        btnTrack.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnTrack.setForeground(new java.awt.Color(255, 255, 255));
        btnTrack.setText("TRACK");
        btnTrack.setBorderPainted(false);
        btnTrack.setFocusPainted(false);
        buttonPanel.add(btnTrack);
        btnTrack.setBounds(350, 0, 90, 30);

        add(buttonPanel);
        buttonPanel.setBounds(10, 360, 450, 30);

        lblHint.setFont(new java.awt.Font("SansSerif", 2, 11)); // NOI18N
        lblHint.setForeground(new java.awt.Color(100, 100, 100));
        lblHint.setText("Double-click on a row to view full details");
        add(lblHint);
        lblHint.setBounds(10, 60, 198, 15);
    }// </editor-fold>//GEN-END:initComponents

    // ==================== DATA OPERATIONS ====================

    private void loadShippingData() {
        try {
            ResultSet rs = Config.getAllShipping();
            DefaultTableModel model = (DefaultTableModel) shippingTable.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    "SHP" + rs.getInt("Ship_ID"),
                    "VWT" + rs.getInt("Transaction_ID"),
                    rs.getString("Courier_Name") != null ? rs.getString("Courier_Name") : "Not Assigned",
                    rs.getString("Tracking_Number"),
                    rs.getString("Shipping_Status"),
                    rs.getString("Shipping_Address"),
                    rs.getString("Estimated_Delivery")
                };
                model.addRow(row);
            }
            
            TableUtils.setColumnWidths(shippingTable, 80, 100, 120, 150, 100, 200, 100);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading shipping data: " + e.getMessage());
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
            
            java.util.List<Object[]> matchingRows = new java.util.ArrayList<>();
            
            for (int i = 0; i < model.getRowCount(); i++) {
                boolean match = false;
                for (int j = 0; j < model.getColumnCount(); j++) {
                    String value = model.getValueAt(i, j).toString().toLowerCase();
                    if (value.contains(searchTerm)) {
                        match = true;
                        break;
                    }
                }
                if (match) {
                    Object[] row = new Object[model.getColumnCount()];
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        row[j] = model.getValueAt(i, j);
                    }
                    matchingRows.add(row);
                }
            }
            
            model.setRowCount(0);
            for (Object[] row : matchingRows) {
                model.addRow(row);
            }
            
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No shipments found matching: " + searchTerm);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching shipments: " + e.getMessage());
        }
    }

    // ==================== ADD SHIPMENT ====================
    
    private void addShipment() {
        // Get all paid transactions without shipping
        java.util.List<String> availableTransactions = new java.util.ArrayList<>();
        java.util.List<Integer> transactionIds = new java.util.ArrayList<>();
        
        try {
            ResultSet rs = Config.getAllTransactions();
            while (rs.next()) {
                if (rs.getString("Status").equals("Paid")) {
                    int transId = rs.getInt("Transaction_ID");
                    // Check if shipping already exists
                    ResultSet shippingRs = Config.getShippingInfo(transId);
                    if (shippingRs == null || !shippingRs.next()) {
                        String display = "VWT" + transId + " - " + rs.getString("First_Name") + " " + rs.getString("Last_Name");
                        availableTransactions.add(display);
                        transactionIds.add(transId);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (availableTransactions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No paid transactions available for shipping.");
            return;
        }

        // Create dialog
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Create Shipment", true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);
        dialog.getContentPane().setBackground(new Color(240, 240, 245));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(20, 20, 400, 270);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        dialog.add(panel);

        JLabel lblTitle = new JLabel("CREATE NEW SHIPMENT");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitle.setForeground(new Color(20, 40, 60));
        lblTitle.setBounds(100, 15, 250, 25);
        panel.add(lblTitle);

        // Transaction Selection
        JLabel lblTransaction = new JLabel("Transaction:");
        lblTransaction.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblTransaction.setBounds(30, 60, 100, 25);
        panel.add(lblTransaction);

        JComboBox<String> cmbTransaction = new JComboBox<>(availableTransactions.toArray(new String[0]));
        cmbTransaction.setBounds(140, 60, 220, 30);
        cmbTransaction.setBackground(Color.WHITE);
        cmbTransaction.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        panel.add(cmbTransaction);

        // Courier Selection
        JLabel lblCourier = new JLabel("Courier:");
        lblCourier.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblCourier.setBounds(30, 110, 100, 25);
        panel.add(lblCourier);

        String[] couriers = {"LBC Express", "J&T Express", "Ninja Van", "JRS Express", "2GO Express"};
        JComboBox<String> cmbCourier = new JComboBox<>(couriers);
        cmbCourier.setBounds(140, 110, 220, 30);
        cmbCourier.setBackground(Color.WHITE);
        cmbCourier.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        panel.add(cmbCourier);

        // Status (Fixed as In Transit)
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblStatus.setBounds(30, 160, 100, 25);
        panel.add(lblStatus);

        JTextField txtStatus = new JTextField("In Transit");
        txtStatus.setBounds(140, 160, 220, 30);
        txtStatus.setEditable(false);
        txtStatus.setBackground(new Color(240, 240, 240));
        txtStatus.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        panel.add(txtStatus);

        // Buttons
        JButton btnCreate = new JButton("CREATE");
        btnCreate.setBackground(new Color(0, 150, 0));
        btnCreate.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnCreate.setForeground(Color.WHITE);
        btnCreate.setBounds(120, 210, 100, 35);
        btnCreate.setBorderPainted(false);
        btnCreate.setFocusPainted(false);
        btnCreate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnCreate.addActionListener(e -> {
            int selectedIndex = cmbTransaction.getSelectedIndex();
            if (selectedIndex >= 0) {
                int transId = transactionIds.get(selectedIndex);
                String courier = (String) cmbCourier.getSelectedItem();
                
                // Get customer address
                String address = "";
                try {
                    ResultSet transRs = Config.getTransactionById(transId);
                    if (transRs != null && transRs.next()) {
                        address = transRs.getString("Default_Address");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
                String trackingNumber = "VW" + System.currentTimeMillis() + transId;
                
                if (Config.createShipping(transId, courier, trackingNumber, address)) {
                    JOptionPane.showMessageDialog(dialog, "Shipment created successfully!\nTracking #: " + trackingNumber);
                    dialog.dispose();
                    loadShippingData();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to create shipment.");
                }
            }
        });
        
        panel.add(btnCreate);

        JButton btnCancel = new JButton("CANCEL");
        btnCancel.setBackground(new Color(100, 100, 100));
        btnCancel.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBounds(230, 210, 100, 35);
        btnCancel.setBorderPainted(false);
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> dialog.dispose());
        panel.add(btnCancel);

        dialog.setVisible(true);
    }

    // ==================== UPDATE STATUS ====================
    
    private void updateStatus() {
        int selectedRow = shippingTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a shipment to update");
            return;
        }

        String shipId = shippingTable.getValueAt(selectedRow, 0).toString().replace("SHP", "");
        String currentStatus = shippingTable.getValueAt(selectedRow, 4).toString();
        String transactionId = shippingTable.getValueAt(selectedRow, 1).toString();

        String[] options = {"In Transit", "Out for Delivery", "Delivered", "Failed Delivery"};
        String newStatus = (String) JOptionPane.showInputDialog(
            this,
            "Update status for shipment:\n" +
            "Transaction: " + transactionId + "\n" +
            "Current Status: " + currentStatus,
            "Update Shipping Status",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            currentStatus
        );

        if (newStatus != null && !newStatus.equals(currentStatus)) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Change status from '" + currentStatus + "' to '" + newStatus + "'?",
                "Confirm Status Update",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                if (Config.updateShippingStatus(Integer.parseInt(shipId), newStatus)) {
                    JOptionPane.showMessageDialog(this, "Status updated successfully!");
                    loadShippingData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update status");
                }
            }
        }
    }

    // ==================== DELETE SHIPMENT ====================
    
    private void deleteShipment() {
        int selectedRow = shippingTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a shipment to delete");
            return;
        }

        String shipId = shippingTable.getValueAt(selectedRow, 0).toString();
        String tracking = shippingTable.getValueAt(selectedRow, 3).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete shipment?\n" +
            "Shipment ID: " + shipId + "\n" +
            "Tracking #: " + tracking,
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String shipIdNum = shipId.replace("SHP", "");
            // You'll need to add this method to Config.java
            if (Config.deleteShipping(Integer.parseInt(shipIdNum))) {
                JOptionPane.showMessageDialog(this, "Shipment deleted successfully!");
                loadShippingData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete shipment!");
            }
        }
    }

    // ==================== TRACK SHIPMENT ====================
    
    private void trackShipment() {
        int selectedRow = shippingTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a shipment to track");
            return;
        }

        String shipId = shippingTable.getValueAt(selectedRow, 0).toString();
        String tracking = shippingTable.getValueAt(selectedRow, 3).toString();
        String courier = shippingTable.getValueAt(selectedRow, 2).toString();
        String status = shippingTable.getValueAt(selectedRow, 4).toString();
        String delivery = shippingTable.getValueAt(selectedRow, 6).toString();

        String message = String.format(
            "╔════════════════════════════════╗\n" +
            "║       SHIPMENT TRACKING        ║\n" +
            "╚════════════════════════════════╝\n\n" +
            "Shipment ID: %s\n" +
            "Courier: %s\n" +
            "Tracking Number: %s\n" +
            "Current Status: %s\n" +
            "Estimated Delivery: %s\n\n" +
            "Track online: %s",
            shipId, courier, tracking, status, delivery, getTrackingUrl(courier, tracking)
        );

        JTextArea textArea = new JTextArea(message);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setBackground(new Color(240, 240, 245));
        
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Shipment Tracking", JOptionPane.INFORMATION_MESSAGE);
    }

    private String getTrackingUrl(String courier, String tracking) {
        switch (courier) {
            case "LBC Express":
                return "https://www.lbcexpress.com/tracking/?tracking_number=" + tracking;
            case "J&T Express":
                return "https://www.jtexpress.ph/tracking?billcode=" + tracking;
            case "Ninja Van":
                return "https://www.ninjavan.co/en-ph/tracking?id=" + tracking;
            case "JRS Express":
                return "http://www.jrs.com.ph/tracking/?tracking_number=" + tracking;
            case "2GO Express":
                return "https://www.2go.com.ph/tracking/?tracking_number=" + tracking;
            default:
                return "Please check carrier website with tracking #: " + tracking;
        }
    }

    // ==================== VIEW DETAILS ====================
    
    private void viewShippingDetails() {
        int selectedRow = shippingTable.getSelectedRow();
        if (selectedRow >= 0) {
            String shipId = shippingTable.getValueAt(selectedRow, 0).toString();
            String transId = shippingTable.getValueAt(selectedRow, 1).toString();
            String courier = shippingTable.getValueAt(selectedRow, 2).toString();
            String tracking = shippingTable.getValueAt(selectedRow, 3).toString();
            String status = shippingTable.getValueAt(selectedRow, 4).toString();
            String address = shippingTable.getValueAt(selectedRow, 5).toString();
            String delivery = shippingTable.getValueAt(selectedRow, 6).toString();

            String message = String.format(
                "╔════════════════════════════════╗\n" +
                "║       SHIPPING DETAILS         ║\n" +
                "╚════════════════════════════════╝\n\n" +
                "Shipment ID: %s\n" +
                "Transaction: %s\n" +
                "Courier: %s\n" +
                "Tracking #: %s\n" +
                "Status: %s\n" +
                "Address: %s\n" +
                "Est. Delivery: %s",
                shipId, transId, courier, tracking, status, address, delivery
            );

            JTextArea textArea = new JTextArea(message);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            textArea.setEditable(false);
            textArea.setBackground(new Color(240, 240, 245));
            
            JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Shipping Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ==================== VARIABLES DECLARATION ====================

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnTrack;
    private javax.swing.JButton btnUpdateStatus;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JLabel lblHint;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JSeparator separator;
    private javax.swing.JTable shippingTable;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}