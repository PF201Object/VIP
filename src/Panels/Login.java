package Panels;

import Config.Config;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login extends JFrame {

    private Timer shakeTimer;
    private int shakeCount = 0;

    public Login() {
        initComponents();
        
        // Custom Styling to match Register
        styleTextField(txtUsername);
        styleTextField(txtPassword);
        
        // Button Colors
        btnLogin.setBackground(new Color(255, 215, 0)); // Gold
        btnRegister.setBackground(new Color(100, 100, 100)); // Gray
        btnExit.setBackground(new Color(180, 50, 50)); // Reddish
        
        // Hover Effects
        addButtonHoverEffect(btnLogin, new Color(255, 215, 0), new Color(255, 240, 150));
        addButtonHoverEffect(btnRegister, new Color(100, 100, 100), new Color(150, 150, 150));
        addButtonHoverEffect(btnExit, new Color(180, 50, 50), new Color(220, 70, 70));
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Helper method for consistent text field styling
    private void styleTextField(JTextField field) {
        field.setBackground(new Color(30, 30, 30));
        field.setForeground(Color.WHITE);
        field.setCaretColor(new Color(255, 215, 0));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    /**
     * NetBeans generated code - MODIFIED for proper sizing
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
        lblSubtitle = new javax.swing.JLabel();
        loginPanel = new javax.swing.JPanel();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblStatus = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        btnRegister = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Velocity Watch Corporation - Login");
        setMinimumSize(new java.awt.Dimension(450, 550));
        setResizable(false);

        mainPanel.setPreferredSize(new java.awt.Dimension(450, 550));
        mainPanel.setLayout(null);

        lblTitle.setFont(new java.awt.Font("Serif", 1, 32)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 153, 0));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("VELOCITY WATCH");
        mainPanel.add(lblTitle);
        lblTitle.setBounds(50, 60, 350, 50);

        lblSubtitle.setFont(new java.awt.Font("Serif", 2, 18)); // NOI18N
        lblSubtitle.setForeground(new java.awt.Color(255, 153, 0));
        lblSubtitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSubtitle.setText("Luxury Timepieces");
        mainPanel.add(lblSubtitle);
        lblSubtitle.setBounds(50, 110, 350, 30);

        loginPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Login", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 153, 0))); // NOI18N
        loginPanel.setForeground(new java.awt.Color(255, 153, 0));
        loginPanel.setOpaque(false);
        loginPanel.setLayout(null);

        lblUsername.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(255, 153, 0));
        lblUsername.setText("Username / Email:");
        loginPanel.add(lblUsername);
        lblUsername.setBounds(40, 40, 150, 25);

        txtUsername.setBackground(new java.awt.Color(48, 48, 48));
        txtUsername.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(255, 255, 255));
        txtUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsername.setBorder(null);
        loginPanel.add(txtUsername);
        txtUsername.setBounds(40, 70, 280, 40);

        lblPassword.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(255, 153, 0));
        lblPassword.setText("Password:");
        loginPanel.add(lblPassword);
        lblPassword.setBounds(40, 120, 120, 25);

        txtPassword.setBackground(new java.awt.Color(48, 48, 48));
        txtPassword.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(255, 255, 255));
        txtPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPassword.setBorder(null);
        loginPanel.add(txtPassword);
        txtPassword.setBounds(40, 150, 280, 40);

        lblStatus.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        loginPanel.add(lblStatus);
        lblStatus.setBounds(40, 200, 280, 10);

        btnLogin.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(64, 64, 64));
        btnLogin.setText("LOGIN");
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        loginPanel.add(btnLogin);
        btnLogin.setBounds(40, 210, 130, 40);

        btnRegister.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnRegister.setText("REGISTER");
        btnRegister.setBorderPainted(false);
        btnRegister.setFocusPainted(false);
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });
        loginPanel.add(btnRegister);
        btnRegister.setBounds(190, 210, 130, 40);

        mainPanel.add(loginPanel);
        loginPanel.setBounds(50, 160, 350, 270);

        btnExit.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnExit.setText("EXIT");
        btnExit.setBorderPainted(false);
        btnExit.setFocusPainted(false);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        mainPanel.add(btnExit);
        btnExit.setBounds(170, 450, 120, 40);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(ActionEvent evt) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            lblStatus.setText("Please enter username and password");
            return;
        }

        if (Config.authenticateUser(username, password)) {
            lblStatus.setText("");
            dispose(); // Close login
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new Dashboard().setVisible(true);
                }
            });
        } else {
            lblStatus.setText("Invalid username or password");
            txtPassword.setText("");
            shakePanel();
        }
    }

    private void btnRegisterActionPerformed(ActionEvent evt) {
        dispose();
        new Register().setVisible(true);
    }

    private void btnExitActionPerformed(ActionEvent evt) {
        System.exit(0);
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

    private void shakePanel() {
        if (shakeTimer != null && shakeTimer.isRunning()) return;
        
        final Point original = getLocation();
        shakeTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (shakeCount < 6) {
                    int delta = (shakeCount % 2 == 0) ? 10 : -10;
                    setLocation(original.x + delta, original.y);
                    shakeCount++;
                } else {
                    setLocation(original);
                    shakeTimer.stop();
                    shakeCount = 0;
                }
            }
        });
        shakeTimer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Config.initializeDB();
                Config.fixDatabaseConstraints(); // Run this once to fix any issues
                
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnRegister;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}