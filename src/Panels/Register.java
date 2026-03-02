package Panels;

import Config.Config;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Register extends JFrame {

    public Register() {
        initComponents();
        
        // Apply custom styling and hover effects
        styleTextField(txtUsername);
        styleTextField(txtEmail);
        styleTextField(txtFirstName);
        styleTextField(txtLastName);
        styleTextField(txtPhone);
        styleTextField(txtPassword);
        styleTextField(txtConfirmPassword);
        
        // Initialize button colors and hover effects
        btnRegister.setBackground(new Color(255, 215, 0)); // Gold
        btnCancel.setBackground(new Color(100, 100, 100));  // Gray
        
        addButtonHoverEffect(btnRegister, new Color(255, 215, 0), new Color(255, 240, 150));
        addButtonHoverEffect(btnCancel, new Color(100, 100, 100), new Color(150, 150, 150));
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * NetBeans generated code - MODIFIED for larger size
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                Color color1 = new Color(20, 40, 60);
                Color color2 = new Color(40, 80, 120);
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        lblTitle = new javax.swing.JLabel();
        registerPanel = new javax.swing.JPanel();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblConfirm = new javax.swing.JLabel();
        txtConfirmPassword = new javax.swing.JPasswordField();
        lblFirstName = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        lblLastName = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        lblPhone = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        btnRegister = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Velocity Watch - Register");
        setMinimumSize(new java.awt.Dimension(550, 650));
        setPreferredSize(new java.awt.Dimension(550, 650));
        setResizable(false);

        mainPanel.setLayout(null);

        lblTitle.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 204, 0));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Create Account");
        mainPanel.add(lblTitle);
        lblTitle.setBounds(100, 30, 350, 50);

        registerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registration Form", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 204, 0))); // NOI18N
        registerPanel.setForeground(new java.awt.Color(255, 255, 255));
        registerPanel.setOpaque(false);
        registerPanel.setLayout(null);

        lblUsername.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(255, 204, 0));
        lblUsername.setText("Username:");
        registerPanel.add(lblUsername);
        lblUsername.setBounds(40, 40, 120, 25);

        txtUsername.setBackground(new java.awt.Color(48, 48, 48));
        txtUsername.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(255, 255, 255));
        txtUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsername.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        registerPanel.add(txtUsername);
        txtUsername.setBounds(170, 35, 270, 35);

        lblEmail.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(255, 204, 0));
        lblEmail.setText("Email:");
        registerPanel.add(lblEmail);
        lblEmail.setBounds(40, 85, 120, 25);

        txtEmail.setBackground(new java.awt.Color(48, 48, 48));
        txtEmail.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(255, 255, 255));
        txtEmail.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmail.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        registerPanel.add(txtEmail);
        txtEmail.setBounds(170, 80, 270, 35);

        lblPassword.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(255, 204, 0));
        lblPassword.setText("Password:");
        registerPanel.add(lblPassword);
        lblPassword.setBounds(40, 130, 120, 25);

        txtPassword.setBackground(new java.awt.Color(48, 48, 48));
        txtPassword.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(255, 255, 255));
        txtPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPassword.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        registerPanel.add(txtPassword);
        txtPassword.setBounds(170, 125, 270, 35);

        lblConfirm.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblConfirm.setForeground(new java.awt.Color(255, 204, 0));
        lblConfirm.setText("Confirm:");
        registerPanel.add(lblConfirm);
        lblConfirm.setBounds(40, 175, 120, 25);

        txtConfirmPassword.setBackground(new java.awt.Color(48, 48, 48));
        txtConfirmPassword.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txtConfirmPassword.setForeground(new java.awt.Color(255, 255, 255));
        txtConfirmPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtConfirmPassword.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        registerPanel.add(txtConfirmPassword);
        txtConfirmPassword.setBounds(170, 170, 270, 35);

        lblFirstName.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblFirstName.setForeground(new java.awt.Color(255, 204, 0));
        lblFirstName.setText("First Name:");
        registerPanel.add(lblFirstName);
        lblFirstName.setBounds(40, 220, 120, 25);

        txtFirstName.setBackground(new java.awt.Color(48, 48, 48));
        txtFirstName.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txtFirstName.setForeground(new java.awt.Color(255, 255, 255));
        txtFirstName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFirstName.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        registerPanel.add(txtFirstName);
        txtFirstName.setBounds(170, 215, 270, 35);

        lblLastName.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblLastName.setForeground(new java.awt.Color(255, 204, 0));
        lblLastName.setText("Last Name:");
        registerPanel.add(lblLastName);
        lblLastName.setBounds(40, 265, 120, 25);

        txtLastName.setBackground(new java.awt.Color(48, 48, 48));
        txtLastName.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txtLastName.setForeground(new java.awt.Color(255, 255, 255));
        txtLastName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLastName.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        registerPanel.add(txtLastName);
        txtLastName.setBounds(170, 260, 270, 35);

        lblPhone.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblPhone.setForeground(new java.awt.Color(255, 204, 0));
        lblPhone.setText("Phone:");
        registerPanel.add(lblPhone);
        lblPhone.setBounds(40, 310, 120, 25);

        txtPhone.setBackground(new java.awt.Color(48, 48, 48));
        txtPhone.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txtPhone.setForeground(new java.awt.Color(255, 255, 255));
        txtPhone.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPhone.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        registerPanel.add(txtPhone);
        txtPhone.setBounds(170, 305, 270, 35);

        lblStatus.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        registerPanel.add(lblStatus);
        lblStatus.setBounds(40, 445, 400, 25);

        btnRegister.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        btnRegister.setForeground(new java.awt.Color(64, 64, 64));
        btnRegister.setText("REGISTER");
        btnRegister.setBorderPainted(false);
        btnRegister.setFocusPainted(false);
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });
        registerPanel.add(btnRegister);
        btnRegister.setBounds(80, 410, 150, 45);

        btnCancel.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        btnCancel.setText("CANCEL");
        btnCancel.setBorderPainted(false);
        btnCancel.setFocusPainted(false);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        registerPanel.add(btnCancel);
        btnCancel.setBounds(250, 410, 150, 45);

        mainPanel.add(registerPanel);
        registerPanel.setBounds(50, 100, 450, 490);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void styleTextField(JTextField field) {
        field.setBackground(new Color(30, 30, 30));
        field.setForeground(Color.WHITE);
        field.setCaretColor(new Color(255, 215, 0));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }

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

    private void btnRegisterActionPerformed(ActionEvent evt) {
    String username = txtUsername.getText().trim();
    String email = txtEmail.getText().trim();
    String password = new String(txtPassword.getPassword());
    String confirm = new String(txtConfirmPassword.getPassword());
    String firstName = txtFirstName.getText().trim();
    String lastName = txtLastName.getText().trim();
    String phone = txtPhone.getText().trim();

    if (username.isEmpty() || email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
        lblStatus.setText("Please fill all required fields");
        return;
    }

    if (!password.equals(confirm)) {
        lblStatus.setText("Passwords do not match");
        return;
    }

    if (password.length() < 6) {
        lblStatus.setText("Password must be at least 6 characters");
        return;
    }

    if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
        lblStatus.setText("Invalid email format");
        return;
    }

    // Using the simplified registerUser method
    if (Config.registerUser(username, email, password, firstName, lastName, phone)) {
        JOptionPane.showMessageDialog(this, 
            "Registration successful! Please login.", 
            "Success", 
            JOptionPane.INFORMATION_MESSAGE);
        dispose();
        new Login().setVisible(true);
    } else {
        if (Config.isUsernameExists(username)) {
            lblStatus.setText("Username already exists");
        } else if (Config.isEmailExists(email)) {
            lblStatus.setText("Email already exists");
        } else {
            lblStatus.setText("Registration failed - Please try again");
        }
    }
}

    private void btnCancelActionPerformed(ActionEvent evt) {
        dispose();
        new Login().setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnRegister;
    private javax.swing.JLabel lblConfirm;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel registerPanel;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}