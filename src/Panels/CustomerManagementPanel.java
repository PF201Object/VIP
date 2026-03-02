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
        lblTitle.setText("Customer Management");
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
        btnSearch.addActionListener(e -> searchCustomers());
        addButtonHoverEffect(btnSearch);
        searchPanel.add(btnSearch);

        btnRefresh = new JButton("REFRESH");
        btnRefresh.setBounds(415, 12, 80, 30);
        btnRefresh.setBackground(new Color(100, 100, 100));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> loadCustomerData());
        addButtonHoverEffect(btnRefresh);
        searchPanel.add(btnRefresh);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBounds(550, 80, 320, 50);
        buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(buttonPanel);

        btnAdd = new JButton("ADD");
        btnAdd.setBounds(20, 12, 80, 30);
        btnAdd.setBackground(new Color(0, 150, 0));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnAdd.setFocusPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addActionListener(e -> addCustomer());
        buttonPanel.add(btnAdd);

        btnEdit = new JButton("EDIT");
        btnEdit.setBounds(110, 12, 80, 30);
        btnEdit.setBackground(new Color(255, 150, 0));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnEdit.setFocusPainted(false);
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.addActionListener(e -> editCustomer());
        buttonPanel.add(btnEdit);

        btnDelete = new JButton("DELETE");
        btnDelete.setBounds(200, 12, 80, 30);
        btnDelete.setBackground(new Color(200, 0, 0));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnDelete.setFocusPainted(false);
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.addActionListener(e -> deleteCustomer());
        buttonPanel.add(btnDelete);

        // Table
        String[] columns = {"Customer ID", "Username", "Email", "First Name", "Last Name", "Phone", "Join Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        customerTable = new JTable(model);
        customerTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        customerTable.setRowHeight(30);
        customerTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        customerTable.getTableHeader().setBackground(new Color(20, 40, 60));
        customerTable.getTableHeader().setForeground(Color.WHITE);
        customerTable.setSelectionBackground(new Color(255, 215, 0, 100));
        customerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewCustomerDetails();
                }
            }
        });

        scrollPane = new JScrollPane(customerTable);
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

    private void loadCustomerData() {
        try {
            ResultSet rs = Config.getAllCustomers();
            DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("Customer_ID"),
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
        }
    }

    private void searchCustomers() {
        String searchTerm = txtSearch.getText().trim();
        loadCustomerData(); // For simplicity, just reload all
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
        
        int customerId = Integer.parseInt(customerTable.getValueAt(selectedRow, 0).toString());
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
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this customer?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            int customerId = Integer.parseInt(customerTable.getValueAt(selectedRow, 0).toString());
            if (Config.deleteCustomer(customerId)) {
                JOptionPane.showMessageDialog(this, "Customer deleted successfully!");
                loadCustomerData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete customer!");
            }
        }
    }

    private void viewCustomerDetails() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow >= 0) {
            int customerId = Integer.parseInt(customerTable.getValueAt(selectedRow, 0).toString());
            String firstName = customerTable.getValueAt(selectedRow, 3).toString();
            String lastName = customerTable.getValueAt(selectedRow, 4).toString();
            String phone = customerTable.getValueAt(selectedRow, 5).toString();
            
            JOptionPane.showMessageDialog(this, 
                "Customer ID: VWC" + customerId + "\n" +
                "Name: " + firstName + " " + lastName + "\n" +
                "Phone: " + phone,
                "Customer Details",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Inner class for Customer Dialog
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
            setSize(350, 300);
            setLocationRelativeTo(CustomerManagementPanel.this);
            setModal(true);
            setLayout(null);
            getContentPane().setBackground(new Color(240, 240, 245));

            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBounds(20, 20, 310, 220);
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            add(panel);

            int y = 30;
            int labelWidth = 80;
            int fieldWidth = 180;

            // First Name
            JLabel lblFirstName = new JLabel("First Name:");
            lblFirstName.setBounds(20, y, labelWidth, 25);
            panel.add(lblFirstName);

            txtFirstName = new JTextField();
            txtFirstName.setBounds(110, y, fieldWidth, 30);
            panel.add(txtFirstName);
            y += 40;

            // Last Name
            JLabel lblLastName = new JLabel("Last Name:");
            lblLastName.setBounds(20, y, labelWidth, 25);
            panel.add(lblLastName);

            txtLastName = new JTextField();
            txtLastName.setBounds(110, y, fieldWidth, 30);
            panel.add(txtLastName);
            y += 40;

            // Phone
            JLabel lblPhone = new JLabel("Phone:");
            lblPhone.setBounds(20, y, labelWidth, 25);
            panel.add(lblPhone);

            txtPhone = new JTextField();
            txtPhone.setBounds(110, y, fieldWidth, 30);
            panel.add(txtPhone);
            y += 40;

            // Address
            JLabel lblAddress = new JLabel("Address:");
            lblAddress.setBounds(20, y, labelWidth, 25);
            panel.add(lblAddress);

            txtAddress = new JTextField();
            txtAddress.setBounds(110, y, fieldWidth, 30);
            panel.add(txtAddress);
            y += 50;

            // Buttons
            JButton btnSave = new JButton("SAVE");
            btnSave.setBounds(70, y, 80, 35);
            btnSave.setBackground(new Color(255, 215, 0));
            btnSave.addActionListener(e -> saveCustomer());
            panel.add(btnSave);

            JButton btnCancel = new JButton("CANCEL");
            btnCancel.setBounds(160, y, 80, 35);
            btnCancel.setBackground(new Color(100, 100, 100));
            btnCancel.setForeground(Color.WHITE);
            btnCancel.addActionListener(e -> dispose());
            panel.add(btnCancel);
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
    private javax.swing.JTable customerTable;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}