package Panels;

import Config.Config;
import Utils.TableUtils;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CustomerManagementPanel extends JPanel {
    private Dashboard dashboard;

    public CustomerManagementPanel(Dashboard dashboard) {
        this.dashboard = dashboard;
        initComponents();
        loadCustomerData();
        
        // Setup button listeners
        setupButtonListeners();
        
        // Add hover effects
        setupButtonHoverEffects();
        
        // Add mouse listener for double-click
        customerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewCustomerDetails();
                }
            }
        });
        
        // Add enter key listener for search
        txtSearch.addActionListener(e -> searchCustomers());
    }
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
        for (ActionListener al : btnEdit.getActionListeners()) {
            btnEdit.removeActionListener(al);
        }
        for (ActionListener al : btnDelete.getActionListeners()) {
            btnDelete.removeActionListener(al);
        }
        
        // Add new listeners
        btnSearch.addActionListener(e -> searchCustomers());
        btnRefresh.addActionListener(e -> loadCustomerData());
        btnAdd.addActionListener(e -> addCustomer());
        btnEdit.addActionListener(e -> editCustomer());
        btnDelete.addActionListener(e -> deleteCustomer());
    }
       private void setupButtonHoverEffects() {
        addButtonHoverEffect(btnSearch, new Color(255, 215, 0), new Color(255, 240, 150));
        addButtonHoverEffect(btnRefresh, new Color(100, 100, 100), new Color(130, 130, 130));
        addButtonHoverEffect(btnAdd, new Color(0, 150, 0), new Color(0, 180, 0));
        addButtonHoverEffect(btnEdit, new Color(255, 150, 0), new Color(255, 180, 0));
        addButtonHoverEffect(btnDelete, new Color(200, 0, 0), new Color(230, 0, 0));
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
     * NetBeans generated code
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
        buttonPanel = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(240, 240, 245));
        setPreferredSize(new java.awt.Dimension(900, 600));
        setLayout(null);

        lblTitle.setFont(new java.awt.Font("Serif", 1, 28)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(20, 40, 60));
        lblTitle.setText("Customer Management");
        add(lblTitle);
        lblTitle.setBounds(10, 20, 278, 37);

        separator.setForeground(new java.awt.Color(255, 215, 0));
        add(separator);
        separator.setBounds(10, 60, 290, 20);

        searchPanel.setBackground(new java.awt.Color(240, 240, 245));
        searchPanel.setLayout(null);

        lblSearch.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblSearch.setText("Search:");
        searchPanel.add(lblSearch);
        lblSearch.setBounds(0, 0, 49, 20);
        searchPanel.add(txtSearch);
        txtSearch.setBounds(60, 0, 130, 30);

        btnSearch.setBackground(new java.awt.Color(255, 215, 0));
        btnSearch.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnSearch.setText("SEARCH");
        searchPanel.add(btnSearch);
        btnSearch.setBounds(210, 0, 110, 30);

        btnRefresh.setBackground(new java.awt.Color(100, 100, 100));
        btnRefresh.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnRefresh.setText("REFRESH");
        searchPanel.add(btnRefresh);
        btnRefresh.setBounds(340, 0, 100, 30);

        add(searchPanel);
        searchPanel.setBounds(10, 80, 450, 30);

        buttonPanel.setBackground(new java.awt.Color(240, 240, 245));
        buttonPanel.setLayout(null);

        btnAdd.setBackground(new java.awt.Color(0, 150, 0));
        btnAdd.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnAdd.setText("ADD");
        buttonPanel.add(btnAdd);
        btnAdd.setBounds(0, 0, 90, 30);

        btnEdit.setBackground(new java.awt.Color(255, 150, 0));
        btnEdit.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnEdit.setText("EDIT");
        buttonPanel.add(btnEdit);
        btnEdit.setBounds(120, 0, 90, 30);

        btnDelete.setBackground(new java.awt.Color(200, 0, 0));
        btnDelete.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnDelete.setText("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        buttonPanel.add(btnDelete);
        btnDelete.setBounds(240, 0, 90, 30);

        add(buttonPanel);
        buttonPanel.setBounds(10, 360, 350, 30);

        customerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer ID", "Username", "Email", "First Name", "Last Name", "Phone", "Join Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane.setViewportView(customerTable);

        add(scrollPane);
        scrollPane.setBounds(10, 120, 760, 230);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteActionPerformed

    // ==================== DATA OPERATIONS ====================
    
    private void loadCustomerData() {
        try {
            ResultSet rs = Config.getAllCustomers();
            DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    "CUS" + rs.getInt("Customer_ID"),
                    rs.getString("Username") != null ? rs.getString("Username") : "N/A",
                    rs.getString("Email") != null ? rs.getString("Email") : "N/A",
                    rs.getString("First_Name"),
                    rs.getString("Last_Name"),
                    rs.getString("Phone_Number"),
                    rs.getString("Join_Date")
                };
                model.addRow(row);
            }
            
            TableUtils.setColumnWidths(customerTable, 80, 100, 180, 120, 120, 120, 150);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading customer data: " + e.getMessage());
        }
    }

    private void searchCustomers() {
        String searchTerm = txtSearch.getText().trim();
        if (searchTerm.isEmpty()) {
            loadCustomerData();
            return;
        }
        
        try {
            ResultSet rs = Config.searchCustomers(searchTerm);
            DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    "CUS" + rs.getInt("Customer_ID"),
                    rs.getString("Username") != null ? rs.getString("Username") : "N/A",
                    rs.getString("Email") != null ? rs.getString("Email") : "N/A",
                    rs.getString("First_Name"),
                    rs.getString("Last_Name"),
                    rs.getString("Phone_Number"),
                    rs.getString("Join_Date")
                };
                model.addRow(row);
            }
            
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No customers found matching: " + searchTerm);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching customers: " + e.getMessage());
        }
    }

    private void addCustomer() {
        CustomerDialog dialog = new CustomerDialog(null);
        dialog.setVisible(true);
        loadCustomerData();
    }

    private void editCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a customer to edit");
            return;
        }
        
        String customerIdStr = customerTable.getValueAt(selectedRow, 0).toString().replace("CUS", "");
        int customerId = Integer.parseInt(customerIdStr);
        CustomerDialog dialog = new CustomerDialog(customerId);
        dialog.setVisible(true);
        loadCustomerData();
    }

    private void deleteCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete");
            return;
        }
        
        String customerName = customerTable.getValueAt(selectedRow, 3).toString() + " " + 
                              customerTable.getValueAt(selectedRow, 4).toString();
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete customer: " + customerName + "?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            String customerIdStr = customerTable.getValueAt(selectedRow, 0).toString().replace("CUS", "");
            int customerId = Integer.parseInt(customerIdStr);
            
            if (Config.deleteCustomer(customerId)) {
                JOptionPane.showMessageDialog(this, "Customer deleted successfully!");
                loadCustomerData();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to delete customer!\n\nThis customer may have existing transactions.",
                    "Delete Failed",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewCustomerDetails() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow >= 0) {
            String customerIdStr = customerTable.getValueAt(selectedRow, 0).toString();
            String username = customerTable.getValueAt(selectedRow, 1).toString();
            String email = customerTable.getValueAt(selectedRow, 2).toString();
            String firstName = customerTable.getValueAt(selectedRow, 3).toString();
            String lastName = customerTable.getValueAt(selectedRow, 4).toString();
            String phone = customerTable.getValueAt(selectedRow, 5).toString();
            String joinDate = customerTable.getValueAt(selectedRow, 6).toString();
            
            String message = String.format(
                "Customer ID: %s\n" +
                "Username: %s\n" +
                "Email: %s\n" +
                "Name: %s %s\n" +
                "Phone: %s\n" +
                "Join Date: %s",
                customerIdStr, username, email, firstName, lastName, phone, joinDate
            );
            
            JOptionPane.showMessageDialog(this, message, "Customer Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ==================== INNER CLASS ====================

    private class CustomerDialog extends JDialog {
        private JTextField txtFirstName, txtLastName, txtPhone, txtAddress;
        private Integer customerId;

        public CustomerDialog(Integer customerId) {
            this.customerId = customerId;
            initDialog();
            if (customerId != null) {
                loadCustomerData();
            }
        }

        private void initDialog() {
            setTitle(customerId == null ? "Add Customer" : "Edit Customer");
            setSize(400, 350);
            setLocationRelativeTo(CustomerManagementPanel.this);
            setModal(true);
            setLayout(null);
            getContentPane().setBackground(new Color(240, 240, 245));

            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBounds(20, 20, 350, 270);
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            add(panel);

            int y = 30;
            int labelWidth = 80;
            int fieldWidth = 220;

            // Title
            JLabel lblTitle = new JLabel(customerId == null ? "New Customer" : "Edit Customer");
            lblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
            lblTitle.setForeground(new Color(20, 40, 60));
            lblTitle.setBounds(120, 10, 200, 25);
            panel.add(lblTitle);

            // First Name
            JLabel lblFirstName = new JLabel("First Name:");
            lblFirstName.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblFirstName.setBounds(30, y, labelWidth, 25);
            panel.add(lblFirstName);

            txtFirstName = new JTextField();
            txtFirstName.setBounds(120, y, fieldWidth, 30);
            styleTextField(txtFirstName);
            panel.add(txtFirstName);
            y += 40;

            // Last Name
            JLabel lblLastName = new JLabel("Last Name:");
            lblLastName.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblLastName.setBounds(30, y, labelWidth, 25);
            panel.add(lblLastName);

            txtLastName = new JTextField();
            txtLastName.setBounds(120, y, fieldWidth, 30);
            styleTextField(txtLastName);
            panel.add(txtLastName);
            y += 40;

            // Phone
            JLabel lblPhone = new JLabel("Phone:");
            lblPhone.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblPhone.setBounds(30, y, labelWidth, 25);
            panel.add(lblPhone);

            txtPhone = new JTextField();
            txtPhone.setBounds(120, y, fieldWidth, 30);
            styleTextField(txtPhone);
            panel.add(txtPhone);
            y += 40;

            // Address
            JLabel lblAddress = new JLabel("Address:");
            lblAddress.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblAddress.setBounds(30, y, labelWidth, 25);
            panel.add(lblAddress);

            txtAddress = new JTextField();
            txtAddress.setBounds(120, y, fieldWidth, 30);
            styleTextField(txtAddress);
            panel.add(txtAddress);
            y += 50;

            // Buttons
            JButton btnSave = new JButton("SAVE");
            btnSave.setBackground(new Color(255, 215, 0));
            btnSave.setFont(new Font("SansSerif", Font.BOLD, 12));
            btnSave.setForeground(new Color(40, 40, 40));
            btnSave.setBounds(100, y, 90, 35);
            btnSave.setBorderPainted(false);
            btnSave.setFocusPainted(false);
            btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnSave.addActionListener(e -> saveCustomer());
            panel.add(btnSave);

            JButton btnCancel = new JButton("CANCEL");
            btnCancel.setBackground(new Color(100, 100, 100));
            btnCancel.setFont(new Font("SansSerif", Font.BOLD, 12));
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setBounds(200, y, 90, 35);
            btnCancel.setBorderPainted(false);
            btnCancel.setFocusPainted(false);
            btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnCancel.addActionListener(e -> dispose());
            panel.add(btnCancel);
            
            // Add hover effects to dialog buttons
            addButtonHoverEffect(btnSave, new Color(255, 215, 0), new Color(255, 240, 150));
            addButtonHoverEffect(btnCancel, new Color(100, 100, 100), new Color(130, 130, 130));
        }

        private void styleTextField(JTextField field) {
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }

        private void loadCustomerData() {
            try {
                ResultSet rs = Config.getAllCustomers();
                while (rs.next()) {
                    if (rs.getInt("Customer_ID") == customerId) {
                        txtFirstName.setText(rs.getString("First_Name"));
                        txtLastName.setText(rs.getString("Last_Name"));
                        txtPhone.setText(rs.getString("Phone_Number"));
                        txtAddress.setText(rs.getString("Default_Address"));
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void saveCustomer() {
            String firstName = txtFirstName.getText().trim();
            String lastName = txtLastName.getText().trim();
            String phone = txtPhone.getText().trim();
            String address = txtAddress.getText().trim();

            if (firstName.isEmpty() || lastName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "First name and last name are required");
                return;
            }

            boolean success;
            if (customerId == null) {
                success = Config.addCustomer(firstName, lastName, phone, address);
            } else {
                success = Config.updateCustomer(customerId, firstName, lastName, phone, address);
            }

            if (success) {
                JOptionPane.showMessageDialog(this, "Customer saved successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save customer!");
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JTable customerTable;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JSeparator separator;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}