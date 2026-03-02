package Panels;

import Config.Config;
import Utils.TableUtils;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UserManagementPanel extends JPanel {
    private Dashboard dashboard;

    public UserManagementPanel(Dashboard dashboard) {
        this.dashboard = dashboard;
        initComponents();
        loadUserData();
        
        // Add action listeners for buttons (without modifying initComponents)
        setupButtonListeners();
        
        // Add hover effects to all buttons
        setupButtonHoverEffects();
        
        // Add mouse listener for double-click on table
        userTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewUserDetails();
                }
            }
        });
        
        // Add enter key listener for search
        txtSearch.addActionListener(e -> searchUsers());
    }

    /**
     * NetBeans generated code - MODIFIED with proper bounds
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
        userTable = new javax.swing.JTable();
        lblHint = new javax.swing.JLabel();

        setBackground(new java.awt.Color(240, 240, 245));
        setPreferredSize(new java.awt.Dimension(900, 600));
        setLayout(null);

        lblTitle.setFont(new java.awt.Font("Serif", 1, 28)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(20, 40, 60));
        lblTitle.setText("User Management");
        add(lblTitle);
        lblTitle.setBounds(10, 20, 217, 37);

        separator.setForeground(new java.awt.Color(255, 215, 0));
        add(separator);
        separator.setBounds(0, 0, 0, 2);

        searchPanel.setBackground(new java.awt.Color(240, 240, 245));
        searchPanel.setLayout(null);

        lblSearch.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblSearch.setText("Search:");
        searchPanel.add(lblSearch);
        lblSearch.setBounds(0, 0, 49, 19);
        searchPanel.add(txtSearch);
        txtSearch.setBounds(60, 0, 130, 30);

        btnSearch.setBackground(new java.awt.Color(255, 215, 0));
        btnSearch.setText("SEARCH");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        searchPanel.add(btnSearch);
        btnSearch.setBounds(210, 0, 110, 30);

        btnRefresh.setBackground(new java.awt.Color(100, 100, 100));
        btnRefresh.setText("REFRESH");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        searchPanel.add(btnRefresh);
        btnRefresh.setBounds(340, 0, 100, 30);

        add(searchPanel);
        searchPanel.setBounds(10, 80, 450, 30);

        buttonPanel.setBackground(new java.awt.Color(240, 240, 245));
        buttonPanel.setLayout(null);

        btnAdd.setBackground(new java.awt.Color(0, 150, 0));
        btnAdd.setText("ADD");
        buttonPanel.add(btnAdd);
        btnAdd.setBounds(10, 0, 90, 30);

        btnEdit.setBackground(new java.awt.Color(255, 150, 0));
        btnEdit.setText("EDIT");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        buttonPanel.add(btnEdit);
        btnEdit.setBounds(130, 0, 90, 30);

        btnDelete.setBackground(new java.awt.Color(200, 0, 0));
        btnDelete.setText("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        buttonPanel.add(btnDelete);
        btnDelete.setBounds(240, 0, 100, 30);

        add(buttonPanel);
        buttonPanel.setBounds(10, 360, 350, 30);

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User ID", "Username", "Email", "Role", "First Name", "Last Name", "Phone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane.setViewportView(userTable);

        add(scrollPane);
        scrollPane.setBounds(10, 120, 760, 230);

        lblHint.setFont(new java.awt.Font("SansSerif", 2, 11)); // NOI18N
        lblHint.setText("Double-click on a row to view full details");
        add(lblHint);
        lblHint.setBounds(10, 60, 198, 15);
    }// </editor-fold>//GEN-END:initComponents

    // ==================== EMPTY EVENT HANDLERS (KEPT FOR COMPATIBILITY) ====================
    
    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // Handled by listener in constructor
    }                                         

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // Handled by listener in constructor
    }                                          

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // Handled by listener in constructor
    }                                       

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // Handled by listener in constructor
    }          
    
        // ==================== SETUP METHODS ====================
    
    private void setupButtonListeners() {
        // Remove existing action listeners first
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
        
        // Add our own listeners
        btnSearch.addActionListener(e -> searchUsers());
        btnRefresh.addActionListener(e -> loadUserData());
        btnAdd.addActionListener(e -> addUser());
        btnEdit.addActionListener(e -> editUser());
        btnDelete.addActionListener(e -> deleteUser());
    }
    
    private void setupButtonHoverEffects() {
        addButtonHoverEffect(btnSearch, new Color(255, 215, 0), new Color(255, 240, 150));
        addButtonHoverEffect(btnRefresh, new Color(100, 100, 100), new Color(130, 130, 130));
        addButtonHoverEffect(btnAdd, new Color(0, 150, 0), new Color(0, 180, 0));
        addButtonHoverEffect(btnEdit, new Color(255, 150, 0), new Color(255, 180, 0));
        addButtonHoverEffect(btnDelete, new Color(200, 0, 0), new Color(230, 0, 0));
    }

    // ==================== BUTTON HOVER EFFECT ====================
    
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

    // ==================== DATA LOADING ====================
    
    private void loadUserData() {
        try {
            ResultSet rs = Config.getAllUsers();
            DefaultTableModel model = (DefaultTableModel) userTable.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    "VW" + rs.getInt("User_ID"),
                    rs.getString("Username"),
                    rs.getString("Email"),
                    rs.getString("Role"),
                    rs.getString("First_Name"),
                    rs.getString("Last_Name"),
                    rs.getString("Phone_Number")
                };
                model.addRow(row);
            }
            
            TableUtils.setColumnWidths(userTable, 80, 120, 180, 80, 120, 120, 120);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading user data: " + e.getMessage());
        }
    }

    // ==================== SEARCH USERS ====================
    
    private void searchUsers() {
        String searchTerm = txtSearch.getText().trim();
        if (searchTerm.isEmpty()) {
            loadUserData();
            return;
        }
        
        try {
            ResultSet rs = Config.searchUsers(searchTerm);
            DefaultTableModel model = (DefaultTableModel) userTable.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    "VW" + rs.getInt("User_ID"),
                    rs.getString("Username"),
                    rs.getString("Email"),
                    rs.getString("Role"),
                    rs.getString("First_Name"),
                    rs.getString("Last_Name"),
                    rs.getString("Phone_Number")
                };
                model.addRow(row);
            }
            
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No users found matching: " + searchTerm);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching users: " + e.getMessage());
        }
    }

    // ==================== CRUD OPERATIONS ====================
    
    private void addUser() {
        UserDialog dialog = new UserDialog(null);
        dialog.setVisible(true);
        loadUserData();
    }

    private void editUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit");
            return;
        }
        
        String userIdStr = userTable.getValueAt(selectedRow, 0).toString().replace("VW", "");
        int userId = Integer.parseInt(userIdStr);
        UserDialog dialog = new UserDialog(userId);
        dialog.setVisible(true);
        loadUserData();
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete");
            return;
        }
        
        String username = userTable.getValueAt(selectedRow, 1).toString();
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete user: " + username + "?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            String userIdStr = userTable.getValueAt(selectedRow, 0).toString().replace("VW", "");
            int userId = Integer.parseInt(userIdStr);
            if (Config.deleteUser(userId)) {
                JOptionPane.showMessageDialog(this, "User deleted successfully!");
                loadUserData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete user!");
            }
        }
    }

    private void viewUserDetails() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            String userIdStr = userTable.getValueAt(selectedRow, 0).toString().replace("VW", "");
            int userId = Integer.parseInt(userIdStr);
            String username = userTable.getValueAt(selectedRow, 1).toString();
            String email = userTable.getValueAt(selectedRow, 2).toString();
            String role = userTable.getValueAt(selectedRow, 3).toString();
            String firstName = userTable.getValueAt(selectedRow, 4).toString();
            String lastName = userTable.getValueAt(selectedRow, 5).toString();
            String phone = userTable.getValueAt(selectedRow, 6).toString();
            
            UserDetailDialog dialog = new UserDetailDialog(userId, username, email, role, firstName, lastName, phone);
            dialog.setVisible(true);
        }
    }

    // ==================== INNER CLASSES ====================

    // Inner class for User Dialog
    private class UserDialog extends JDialog {
        private JTextField txtUsername, txtEmail, txtFirstName, txtLastName, txtPhone;
        private JPasswordField txtPassword;
        private JComboBox<String> cmbRole;
        private Integer userId;

        public UserDialog(Integer userId) {
            this.userId = userId;
            initDialog();
            if (userId != null) {
                loadUserData();
            }
        }

        private void initDialog() {
            setTitle(userId == null ? "Add User" : "Edit User");
            setSize(450, 550);
            setLocationRelativeTo(UserManagementPanel.this);
            setModal(true);
            setLayout(null);
            getContentPane().setBackground(new Color(240, 240, 245));

            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBounds(20, 20, 400, 470);
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            add(panel);

            int y = 30;
            int labelWidth = 100;
            int fieldWidth = 250;

            // Username
            JLabel lblUsername = new JLabel("Username:");
            lblUsername.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblUsername.setBounds(30, y, labelWidth, 25);
            panel.add(lblUsername);

            txtUsername = new JTextField();
            txtUsername.setBounds(140, y, fieldWidth, 30);
            styleTextField(txtUsername);
            panel.add(txtUsername);
            y += 40;

            // Email
            JLabel lblEmail = new JLabel("Email:");
            lblEmail.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblEmail.setBounds(30, y, labelWidth, 25);
            panel.add(lblEmail);

            txtEmail = new JTextField();
            txtEmail.setBounds(140, y, fieldWidth, 30);
            styleTextField(txtEmail);
            panel.add(txtEmail);
            y += 40;

            // Password (only for add)
            if (userId == null) {
                JLabel lblPassword = new JLabel("Password:");
                lblPassword.setFont(new Font("SansSerif", Font.BOLD, 12));
                lblPassword.setBounds(30, y, labelWidth, 25);
                panel.add(lblPassword);

                txtPassword = new JPasswordField();
                txtPassword.setBounds(140, y, fieldWidth, 30);
                stylePasswordField(txtPassword);
                panel.add(txtPassword);
                y += 40;
            }

            // Role
            JLabel lblRole = new JLabel("Role:");
            lblRole.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblRole.setBounds(30, y, labelWidth, 25);
            panel.add(lblRole);

            cmbRole = new JComboBox<>(new String[]{"User", "Admin"});
            cmbRole.setBackground(Color.WHITE);
            cmbRole.setBounds(140, y, fieldWidth, 30);
            cmbRole.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
            panel.add(cmbRole);
            y += 40;

            // First Name
            JLabel lblFirstName = new JLabel("First Name:");
            lblFirstName.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblFirstName.setBounds(30, y, labelWidth, 25);
            panel.add(lblFirstName);

            txtFirstName = new JTextField();
            txtFirstName.setBounds(140, y, fieldWidth, 30);
            styleTextField(txtFirstName);
            panel.add(txtFirstName);
            y += 40;

            // Last Name
            JLabel lblLastName = new JLabel("Last Name:");
            lblLastName.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblLastName.setBounds(30, y, labelWidth, 25);
            panel.add(lblLastName);

            txtLastName = new JTextField();
            txtLastName.setBounds(140, y, fieldWidth, 30);
            styleTextField(txtLastName);
            panel.add(txtLastName);
            y += 40;

            // Phone
            JLabel lblPhone = new JLabel("Phone:");
            lblPhone.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblPhone.setBounds(30, y, labelWidth, 25);
            panel.add(lblPhone);

            txtPhone = new JTextField();
            txtPhone.setBounds(140, y, fieldWidth, 30);
            styleTextField(txtPhone);
            panel.add(txtPhone);
            y += 40;

            // Buttons
            JButton btnSave = new JButton("SAVE");
            btnSave.setBackground(new Color(255, 215, 0));
            btnSave.setFont(new Font("SansSerif", Font.BOLD, 14));
            btnSave.setForeground(new Color(40, 40, 40));
            btnSave.setBounds(120, y, 100, 35);
            btnSave.setBorderPainted(false);
            btnSave.setFocusPainted(false);
            btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnSave.addActionListener(e -> saveUser());
            addButtonHoverEffect(btnSave, new Color(255, 215, 0), new Color(255, 240, 150));
            panel.add(btnSave);

            JButton btnCancel = new JButton("CANCEL");
            btnCancel.setBackground(new Color(100, 100, 100));
            btnCancel.setFont(new Font("SansSerif", Font.BOLD, 14));
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setBounds(230, y, 100, 35);
            btnCancel.setBorderPainted(false);
            btnCancel.setFocusPainted(false);
            btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnCancel.addActionListener(e -> dispose());
            addButtonHoverEffect(btnCancel, new Color(100, 100, 100), new Color(130, 130, 130));
            panel.add(btnCancel);
        }

        private void styleTextField(JTextField field) {
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }

        private void stylePasswordField(JPasswordField field) {
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }

        private void loadUserData() {
            try {
                ResultSet rs = Config.getAllUsers();
                while (rs.next()) {
                    if (rs.getInt("User_ID") == userId) {
                        txtUsername.setText(rs.getString("Username"));
                        txtEmail.setText(rs.getString("Email"));
                        txtFirstName.setText(rs.getString("First_Name"));
                        txtLastName.setText(rs.getString("Last_Name"));
                        txtPhone.setText(rs.getString("Phone_Number"));
                        cmbRole.setSelectedItem(rs.getString("Role"));
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void saveUser() {
            String username = txtUsername.getText().trim();
            String email = txtEmail.getText().trim();
            String firstName = txtFirstName.getText().trim();
            String lastName = txtLastName.getText().trim();
            String phone = txtPhone.getText().trim();
            String role = (String) cmbRole.getSelectedItem();

            if (username.isEmpty() || email.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields");
                return;
            }

            boolean success;
            if (userId == null) {
                String password = new String(txtPassword.getPassword());
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Password is required");
                    return;
                }
                success = Config.addUser(username, email, password, firstName, lastName, phone);
            } else {
                success = Config.updateUser(userId, username, email, role, firstName, lastName, phone);
            }

            if (success) {
                JOptionPane.showMessageDialog(this, "User saved successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save user!");
            }
        }
    }

    // Inner class for User Detail Dialog
    // Inner class for User Detail Dialog - ENHANCED with Profile Picture
private class UserDetailDialog extends JDialog {
    private JLabel lblProfileImage;
    private String imagePath;

    public UserDetailDialog(int userId, String username, String email, String role, 
                           String firstName, String lastName, String phone) {
        setTitle("User Details - " + username);
        setSize(500, 400);
        setLocationRelativeTo(UserManagementPanel.this);
        setModal(true);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 240, 245));
        
        // Load profile picture path from database
        loadUserProfilePicture(userId);

        // Main panel with border
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(20, 20, 450, 320);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        add(panel);

        // Title
        JLabel lblTitle = new JLabel("USER INFORMATION");
        lblTitle.setFont(new Font("Serif", Font.BOLD, 18));
        lblTitle.setForeground(new Color(20, 40, 60));
        lblTitle.setBounds(150, 15, 200, 25);
        panel.add(lblTitle);

        // Profile Picture Section
        JLabel lblProfileLabel = new JLabel("Profile Picture:");
        lblProfileLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblProfileLabel.setBounds(30, 50, 100, 20);
        panel.add(lblProfileLabel);

        // Profile Image
        lblProfileImage = new JLabel();
        lblProfileImage.setBounds(30, 75, 100, 100);
        lblProfileImage.setBackground(new Color(230, 230, 230));
        lblProfileImage.setOpaque(true);
        lblProfileImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblProfileImage.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2));
        
        // Set default or loaded image
        setProfileImage();
        panel.add(lblProfileImage);

        // User Info Section
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(null);
        infoPanel.setBounds(150, 60, 280, 200);
        infoPanel.setBackground(new Color(250, 250, 250));
        infoPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(infoPanel);

        int yPos = 15;
        int labelWidth = 90;
        int valueWidth = 160;

        // User ID
        addInfoRow(infoPanel, "User ID:", "VW" + userId, 20, yPos, labelWidth, valueWidth);
        yPos += 30;

        // Username
        addInfoRow(infoPanel, "Username:", username, 20, yPos, labelWidth, valueWidth);
        yPos += 30;

        // Email
        addInfoRow(infoPanel, "Email:", email, 20, yPos, labelWidth, valueWidth);
        yPos += 30;

        // Role
        JLabel lblRoleLabel = new JLabel("Role:");
        lblRoleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblRoleLabel.setBounds(20, yPos, labelWidth, 20);
        infoPanel.add(lblRoleLabel);

        JLabel lblRoleValue = new JLabel(role);
        lblRoleValue.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblRoleValue.setBounds(120, yPos, valueWidth, 20);
        lblRoleValue.setForeground(new Color(255, 215, 0));
        infoPanel.add(lblRoleValue);
        yPos += 30;

        // Full Name
        addInfoRow(infoPanel, "Name:", firstName + " " + lastName, 20, yPos, labelWidth, valueWidth);
        yPos += 30;

        // Phone
        addInfoRow(infoPanel, "Phone:", phone, 20, yPos, labelWidth, valueWidth);

        // Close Button
        JButton btnClose = new JButton("CLOSE");
        btnClose.setBackground(new Color(100, 100, 100));
        btnClose.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnClose.setForeground(Color.WHITE);
        btnClose.setBounds(180, 280, 100, 35);
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.addActionListener(e -> dispose());
        
        // Add hover effect to close button
        btnClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btnClose.setBackground(new Color(130, 130, 130));
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                btnClose.setBackground(new Color(100, 100, 100));
            }
        });
        
        panel.add(btnClose);
    }

    private void addInfoRow(JPanel panel, String label, String value, int x, int y, int labelWidth, int valueWidth) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblLabel.setBounds(x, y, labelWidth, 20);
        panel.add(lblLabel);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblValue.setBounds(x + labelWidth, y, valueWidth, 20);
        panel.add(lblValue);
    }

    private void loadUserProfilePicture(int userId) {
    imagePath = Config.getUserProfilePic(userId);
    System.out.println("Loaded profile picture path: " + imagePath); // Debug line
}

    private void setProfileImage() {
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                ImageIcon icon = new ImageIcon(imagePath);
                Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lblProfileImage.setIcon(new ImageIcon(scaledImage));
                lblProfileImage.setText("");
            } else {
                setDefaultProfileImage();
            }
        } else {
            setDefaultProfileImage();
        }
    }

    private void setDefaultProfileImage() {
        // Create a default user icon
        lblProfileImage.setIcon(null);
        lblProfileImage.setText("👤");
        lblProfileImage.setFont(new Font("SansSerif", Font.PLAIN, 40));
        lblProfileImage.setForeground(new Color(150, 150, 150));
    }
}

    // ==================== VARIABLES DECLARATION ====================

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JLabel lblHint;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JSeparator separator;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}