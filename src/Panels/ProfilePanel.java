package Panels;

import Config.Config;
import Utils.ImageUtils;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;

public class ProfilePanel extends JPanel {
    private Dashboard dashboard;
    private String currentImagePath;

    public ProfilePanel(Dashboard dashboard) {
        this.dashboard = dashboard;
        initComponents();
        loadProfileData();
        
        // Add action listeners
        btnUploadPic.addActionListener(e -> uploadPicture());
        btnEditProfile.addActionListener(e -> openEditProfileDialog());
        btnChangePassword.addActionListener(e -> openChangePasswordDialog());        
        // Add hover effects
        addButtonHoverEffect(btnUploadPic, new Color(255, 215, 0), new Color(255, 240, 150));
        addButtonHoverEffect(btnEditProfile, new Color(70, 130, 180), new Color(100, 160, 210));
        addButtonHoverEffect(btnChangePassword, new Color(60, 60, 60), new Color(100, 100, 100));
    }

    /**
     * NetBeans generated code - MODIFIED with new buttons
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();
        lblProfilePic = new javax.swing.JLabel();
        btnUploadPic = new javax.swing.JButton();
        lblUserId = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        lblJoinDate = new javax.swing.JLabel();
        btnEditProfile = new javax.swing.JButton();
        btnChangePassword = new javax.swing.JButton();
        test = new javax.swing.JLabel();
        lblUserId3 = new javax.swing.JLabel();
        lblUserId4 = new javax.swing.JLabel();
        lblUserId5 = new javax.swing.JLabel();
        lblUserId6 = new javax.swing.JLabel();
        lblUserId7 = new javax.swing.JLabel();
        lblUserId8 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(240, 240, 245));
        setLayout(null);

        lblTitle.setFont(new java.awt.Font("Serif", 1, 28)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(20, 40, 60));
        lblTitle.setText("My Profile");
        add(lblTitle);
        lblTitle.setBounds(30, 20, 300, 40);

        separator.setForeground(new java.awt.Color(255, 215, 0));
        add(separator);
        separator.setBounds(30, 65, 840, 2);

        lblProfilePic.setBackground(new java.awt.Color(200, 200, 200));
        lblProfilePic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProfilePic.setText("No Image");
        lblProfilePic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblProfilePic.setOpaque(true);
        add(lblProfilePic);
        lblProfilePic.setBounds(340, 80, 150, 150);

        btnUploadPic.setBackground(new java.awt.Color(255, 215, 0));
        btnUploadPic.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnUploadPic.setForeground(new java.awt.Color(40, 40, 40));
        btnUploadPic.setText("UPLOAD");
        btnUploadPic.setBorderPainted(false);
        btnUploadPic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUploadPic.setFocusPainted(false);
        add(btnUploadPic);
        btnUploadPic.setBounds(510, 140, 120, 35);

        lblUserId.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblUserId.setForeground(new java.awt.Color(20, 40, 60));
        add(lblUserId);
        lblUserId.setBounds(120, 110, 200, 25);

        lblUsername.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(20, 40, 60));
        add(lblUsername);
        lblUsername.setBounds(120, 150, 200, 25);

        lblEmail.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(20, 40, 60));
        add(lblEmail);
        lblEmail.setBounds(120, 190, 200, 25);

        lblRole.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblRole.setForeground(new java.awt.Color(255, 215, 0));
        add(lblRole);
        lblRole.setBounds(120, 230, 200, 25);

        lblJoinDate.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblJoinDate.setForeground(new java.awt.Color(20, 40, 60));
        add(lblJoinDate);
        lblJoinDate.setBounds(120, 270, 200, 25);

        btnEditProfile.setBackground(new java.awt.Color(70, 130, 180));
        btnEditProfile.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnEditProfile.setForeground(new java.awt.Color(255, 255, 255));
        btnEditProfile.setText("EDIT PROFILE");
        btnEditProfile.setBorderPainted(false);
        btnEditProfile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditProfile.setFocusPainted(false);
        add(btnEditProfile);
        btnEditProfile.setBounds(30, 330, 120, 35);

        btnChangePassword.setBackground(new java.awt.Color(60, 60, 60));
        btnChangePassword.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnChangePassword.setForeground(new java.awt.Color(255, 255, 255));
        btnChangePassword.setText("CHANGE PWD");
        btnChangePassword.setBorderPainted(false);
        btnChangePassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChangePassword.setFocusPainted(false);
        add(btnChangePassword);
        btnChangePassword.setBounds(160, 330, 120, 35);

        test.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        test.setForeground(new java.awt.Color(20, 40, 60));
        add(test);
        test.setBounds(30, 300, 60, 25);

        lblUserId3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblUserId3.setForeground(new java.awt.Color(20, 40, 60));
        lblUserId3.setText("Account Information");
        add(lblUserId3);
        lblUserId3.setBounds(30, 70, 250, 25);

        lblUserId4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblUserId4.setForeground(new java.awt.Color(20, 40, 60));
        lblUserId4.setText("Username:");
        add(lblUserId4);
        lblUserId4.setBounds(30, 150, 80, 25);

        lblUserId5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblUserId5.setForeground(new java.awt.Color(20, 40, 60));
        lblUserId5.setText("Role:");
        add(lblUserId5);
        lblUserId5.setBounds(30, 230, 80, 25);

        lblUserId6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblUserId6.setForeground(new java.awt.Color(20, 40, 60));
        lblUserId6.setText("Email:");
        add(lblUserId6);
        lblUserId6.setBounds(30, 190, 80, 25);

        lblUserId7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblUserId7.setForeground(new java.awt.Color(20, 40, 60));
        lblUserId7.setText("ID:");
        add(lblUserId7);
        lblUserId7.setBounds(30, 110, 80, 25);

        lblUserId8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblUserId8.setForeground(new java.awt.Color(20, 40, 60));
        lblUserId8.setText("Join Date:");
        add(lblUserId8);
        lblUserId8.setBounds(30, 270, 80, 25);
    }// </editor-fold>//GEN-END:initComponents

    // ==================== BUTTON HOVER EFFECT ====================
    
    private void addButtonHoverEffect(JButton button, Color baseColor, Color hoverColor) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(hoverColor);
                button.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(baseColor);
                button.setForeground(baseColor.equals(new Color(255, 215, 0)) ? new Color(40, 40, 40) : Color.WHITE);
            }
        });
    }

    // ==================== LOAD PROFILE DATA ====================
    
    private void loadProfileData() {
        try {
            ResultSet rs = Config.getUserProfile();
            if (rs != null && rs.next()) {
                lblUserId.setText("VW" + rs.getInt("User_ID"));
                lblUsername.setText(rs.getString("Username"));
                lblEmail.setText(rs.getString("Email"));
                lblRole.setText(rs.getString("Role"));
                lblJoinDate.setText(rs.getString("Join_Date"));
                
                currentImagePath = rs.getString("Profile_Pic");
                if (currentImagePath != null && !currentImagePath.isEmpty()) {
                    ImageUtils.displayImage(lblProfilePic, currentImagePath, 150, 150);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================== UPLOAD PICTURE ====================
    
    private void uploadPicture() {
        String path = ImageUtils.selectImage(lblProfilePic);
        if (path != null) {
            currentImagePath = path;
        }
    }
    
    private void openEditProfileDialog() {
        EditProfileDialog dialog = new EditProfileDialog((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        // Refresh data after dialog closes
        loadProfileData();
    }

    private void openChangePasswordDialog() {
        ChangePasswordDialog dialog = new ChangePasswordDialog((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
    }

    // ==================== EDIT PROFILE DIALOG ====================
    
    private class EditProfileDialog extends JDialog {
        private JTextField txtFirstName, txtLastName, txtPhone, txtAddress;
        private JButton btnSave, btnCancel;

        public EditProfileDialog(JFrame parent) {
            super(parent, "Edit Profile", true);
            setSize(450, 380);
            setLocationRelativeTo(parent);
            setResizable(false);
            initDialog();
        }

        private void initDialog() {
            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBackground(new Color(240, 240, 245));
            
            // Title
            JLabel lblTitle = new JLabel("Edit Profile Information");
            lblTitle.setFont(new Font("Serif", Font.BOLD, 20));
            lblTitle.setForeground(new Color(20, 40, 60));
            lblTitle.setBounds(110, 20, 250, 30);
            panel.add(lblTitle);
            
            // First Name
            JLabel lblFirstNameLabel = new JLabel("First Name:");
            lblFirstNameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblFirstNameLabel.setBounds(50, 70, 100, 25);
            panel.add(lblFirstNameLabel);
            
            txtFirstName = new JTextField();
            txtFirstName.setBounds(160, 70, 220, 30);
            styleTextField(txtFirstName);
            panel.add(txtFirstName);
            
            // Last Name
            JLabel lblLastNameLabel = new JLabel("Last Name:");
            lblLastNameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblLastNameLabel.setBounds(50, 115, 100, 25);
            panel.add(lblLastNameLabel);
            
            txtLastName = new JTextField();
            txtLastName.setBounds(160, 115, 220, 30);
            styleTextField(txtLastName);
            panel.add(txtLastName);
            
            // Phone
            JLabel lblPhoneLabel = new JLabel("Phone:");
            lblPhoneLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblPhoneLabel.setBounds(50, 160, 100, 25);
            panel.add(lblPhoneLabel);
            
            txtPhone = new JTextField();
            txtPhone.setBounds(160, 160, 220, 30);
            styleTextField(txtPhone);
            panel.add(txtPhone);
            
            // Address
            JLabel lblAddressLabel = new JLabel("Address:");
            lblAddressLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblAddressLabel.setBounds(50, 205, 100, 25);
            panel.add(lblAddressLabel);
            
            txtAddress = new JTextField();
            txtAddress.setBounds(160, 205, 220, 30);
            styleTextField(txtAddress);
            panel.add(txtAddress);
            
            // Buttons
            btnSave = new JButton("SAVE");
            btnSave.setBackground(new Color(255, 215, 0));
            btnSave.setFont(new Font("SansSerif", Font.BOLD, 14));
            btnSave.setForeground(new Color(40, 40, 40));
            btnSave.setBounds(120, 280, 100, 35);
            btnSave.setBorderPainted(false);
            btnSave.setFocusPainted(false);
            btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
            addButtonHoverEffect(btnSave, new Color(255, 215, 0), new Color(255, 240, 150));
            panel.add(btnSave);
            
            btnCancel = new JButton("CANCEL");
            btnCancel.setBackground(new Color(100, 100, 100));
            btnCancel.setFont(new Font("SansSerif", Font.BOLD, 14));
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setBounds(230, 280, 100, 35);
            btnCancel.setBorderPainted(false);
            btnCancel.setFocusPainted(false);
            btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnCancel.addActionListener(e -> dispose());
            addButtonHoverEffect(btnCancel, new Color(100, 100, 100), new Color(130, 130, 130));
            panel.add(btnCancel);
            
            add(panel);
        }

        private void styleTextField(JTextField field) {
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }
    }

    // ==================== CHANGE PASSWORD DIALOG ====================
    
    private class ChangePasswordDialog extends JDialog {
        private JPasswordField txtCurrentPassword, txtNewPassword, txtConfirmPassword;
        private JButton btnChange, btnCancel;

        public ChangePasswordDialog(JFrame parent) {
            super(parent, "Change Password", true);
            setSize(400, 300);
            setLocationRelativeTo(parent);
            setResizable(false);
            initDialog();
        }

        private void initDialog() {
            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBackground(new Color(240, 240, 245));
            
            // Title
            JLabel lblTitle = new JLabel("Change Password");
            lblTitle.setFont(new Font("Serif", Font.BOLD, 20));
            lblTitle.setForeground(new Color(20, 40, 60));
            lblTitle.setBounds(120, 20, 200, 30);
            panel.add(lblTitle);
            
            // Current Password
            JLabel lblCurrent = new JLabel("Current:");
            lblCurrent.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblCurrent.setBounds(40, 70, 80, 25);
            panel.add(lblCurrent);
            
            txtCurrentPassword = new JPasswordField();
            txtCurrentPassword.setBounds(130, 70, 200, 30);
            stylePasswordField(txtCurrentPassword);
            panel.add(txtCurrentPassword);
            
            // New Password
            JLabel lblNew = new JLabel("New:");
            lblNew.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblNew.setBounds(40, 115, 80, 25);
            panel.add(lblNew);
            
            txtNewPassword = new JPasswordField();
            txtNewPassword.setBounds(130, 115, 200, 30);
            stylePasswordField(txtNewPassword);
            panel.add(txtNewPassword);
            
            // Confirm Password
            JLabel lblConfirm = new JLabel("Confirm:");
            lblConfirm.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblConfirm.setBounds(40, 160, 80, 25);
            panel.add(lblConfirm);
            
            txtConfirmPassword = new JPasswordField();
            txtConfirmPassword.setBounds(130, 160, 200, 30);
            stylePasswordField(txtConfirmPassword);
            panel.add(txtConfirmPassword);
            
            // Buttons
            btnChange = new JButton("CHANGE");
            btnChange.setBackground(new Color(255, 215, 0));
            btnChange.setFont(new Font("SansSerif", Font.BOLD, 14));
            btnChange.setForeground(new Color(40, 40, 40));
            btnChange.setBounds(100, 220, 100, 35);
            btnChange.setBorderPainted(false);
            btnChange.setFocusPainted(false);
            btnChange.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnChange.addActionListener(e -> changePassword());
            addButtonHoverEffect(btnChange, new Color(255, 215, 0), new Color(255, 240, 150));
            panel.add(btnChange);
            
            btnCancel = new JButton("CANCEL");
            btnCancel.setBackground(new Color(100, 100, 100));
            btnCancel.setFont(new Font("SansSerif", Font.BOLD, 14));
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setBounds(210, 220, 100, 35);
            btnCancel.setBorderPainted(false);
            btnCancel.setFocusPainted(false);
            btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnCancel.addActionListener(e -> dispose());
            addButtonHoverEffect(btnCancel, new Color(100, 100, 100), new Color(130, 130, 130));
            panel.add(btnCancel);
            
            add(panel);
        }

        private void stylePasswordField(JPasswordField field) {
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }

        private void changePassword() {
    String current = new String(txtCurrentPassword.getPassword());
    String newPass = new String(txtNewPassword.getPassword());
    String confirm = new String(txtConfirmPassword.getPassword());

    if (current.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
        JOptionPane.showMessageDialog(this, "All fields are required!");
        return;
    }

    if (!newPass.equals(confirm)) {
        JOptionPane.showMessageDialog(this, "New passwords do not match!");
        return;
    }

    if (newPass.length() < 6) {
        JOptionPane.showMessageDialog(this, "Password must be at least 6 characters!");
        return;
    }

    // Get current user data
    String username = Config.getCurrentUsername();
    
    // Verify current password
    if (!Config.authenticateUser(username, current)) {
        JOptionPane.showMessageDialog(this, "Current password is incorrect!");
        return;
    }

            // Get current profile data to preserve existing values
            String firstName = ProfilePanel.this.test.getText();
            String lastName = ProfilePanel.this.test.getText();
            String phone = ProfilePanel.this.test.getText();
            String address = ProfilePanel.this.test.getText();

    // Update password while preserving existing profile data
    if (Config.updateProfile(firstName, lastName, phone, address, newPass)) {
        JOptionPane.showMessageDialog(this, "Password changed successfully!");
        dispose();
    } else {
        JOptionPane.showMessageDialog(this, "Failed to change password!");
    }
}
    }

    // ==================== VARIABLES DECLARATION ====================

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangePassword;
    private javax.swing.JButton btnEditProfile;
    private javax.swing.JButton btnUploadPic;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblJoinDate;
    private javax.swing.JLabel lblProfilePic;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUserId;
    private javax.swing.JLabel lblUserId3;
    private javax.swing.JLabel lblUserId4;
    private javax.swing.JLabel lblUserId5;
    private javax.swing.JLabel lblUserId6;
    private javax.swing.JLabel lblUserId7;
    private javax.swing.JLabel lblUserId8;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JSeparator separator;
    private javax.swing.JLabel test;
    // End of variables declaration//GEN-END:variables
}