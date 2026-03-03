package Panels;

import Config.Config;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.ResultSet;
import javax.swing.*;

public class Dashboard extends JFrame {
    private Timer slideTimer;
    private boolean sidebarExpanded = true;
    private int sidebarWidth = 200;

public Dashboard() {
        initComponents();
        setupButtonActions(); // ADD THIS LINE
        initCustomEffects();
        setLocationRelativeTo(null);
        loadUserInfo();
        showPanel(new ProfilePanel(this));
        
    setSize(1000, 490);
    setPreferredSize(new Dimension(1000, 490));
    setMinimumSize(new Dimension(1000, 490));
    setMaximumSize(new Dimension(1000, 490));
    }

    private void initCustomEffects() {

    addHoverEffect(btnProfile);
    addHoverEffect(btnUsers);
    addHoverEffect(btnCustomers);
    addHoverEffect(btnProducts);
    addHoverEffect(btnTransactions);
    addHoverEffect(btnAuthorization);
    addHoverEffect(btnShipping);
    addLogoutHoverEffect(btnLogout);
}
    private void setupButtonActions() {
    
    btnProfile.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            showPanel(new ProfilePanel(Dashboard.this));
        }
    });
    
    btnUsers.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if (Config.isAdmin()) {
                showPanel(new UserManagementPanel(Dashboard.this));
            } else {
                JOptionPane.showMessageDialog(Dashboard.this, "Access denied. Admin only.");
            }
        }
    });
    
    btnCustomers.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            showPanel(new CustomerManagementPanel(Dashboard.this));
        }
    });
    
    btnProducts.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            showPanel(new ProductManagementPanel(Dashboard.this));
        }
    });
    
    btnTransactions.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            showPanel(new TransactionPanel(Dashboard.this));
        }
    });
    
    btnAuthorization.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if (Config.isAdmin()) {
                showPanel(new AdminAuthorizationPanel(Dashboard.this));
            } else {
                JOptionPane.showMessageDialog(Dashboard.this, "Access denied. Admin only.");
            }
        }
    });
    
    btnShipping.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            showPanel(new ShippingPanel(Dashboard.this));
        }
    });
    
    btnLogout.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            logout();
        }
    });
}

private void addHoverEffect(JButton button) {
    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(new Color(255, 215, 0));
            button.setForeground(Color.BLACK);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(new Color(64, 64, 80));
            button.setForeground(Color.WHITE);
        }
    });
}

private void addLogoutHoverEffect(JButton button) {
    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(new Color(200, 80, 80));
            button.setForeground(Color.BLACK);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(new Color(150, 50, 50));
            button.setForeground(Color.WHITE);
        }
    });
}
    /**
     * NetBeans generated code
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        lblWelcome1 = new javax.swing.JLabel();
        sidebarPanel = new javax.swing.JPanel();
        btnProfile = new javax.swing.JButton();
        btnUsers = new javax.swing.JButton();
        btnCustomers = new javax.swing.JButton();
        btnProducts = new javax.swing.JButton();
        btnTransactions = new javax.swing.JButton();
        btnAuthorization = new javax.swing.JButton();
        btnShipping = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        lblUserIcon = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Velocity Watch Corporation - Dashboard");
        setMaximumSize(new java.awt.Dimension(1000, 500));
        setMinimumSize(new java.awt.Dimension(1000, 500));
        setPreferredSize(new java.awt.Dimension(1000, 500));

        headerPanel.setBackground(new java.awt.Color(32, 64, 96));
        headerPanel.setPreferredSize(new java.awt.Dimension(1200, 60));
        headerPanel.setLayout(null);

        lblWelcome1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblWelcome1.setForeground(new java.awt.Color(255, 153, 0));
        lblWelcome1.setText("Velocity Watch Corporation ");
        headerPanel.add(lblWelcome1);
        lblWelcome1.setBounds(20, 10, 310, 40);

        getContentPane().add(headerPanel, java.awt.BorderLayout.NORTH);

        sidebarPanel.setBackground(new java.awt.Color(48, 48, 64));
        sidebarPanel.setPreferredSize(new java.awt.Dimension(200, 600));
        sidebarPanel.setLayout(null);

        btnProfile.setBackground(new java.awt.Color(64, 64, 80));
        btnProfile.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnProfile.setForeground(new java.awt.Color(255, 255, 255));
        btnProfile.setText("MY PROFILE");
        btnProfile.setBorderPainted(false);
        btnProfile.setFocusPainted(false);
        sidebarPanel.add(btnProfile);
        btnProfile.setBounds(20, 70, 160, 30);

        btnUsers.setBackground(new java.awt.Color(64, 64, 80));
        btnUsers.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnUsers.setForeground(new java.awt.Color(255, 255, 255));
        btnUsers.setText("MASTERLIST");
        btnUsers.setBorderPainted(false);
        btnUsers.setFocusPainted(false);
        btnUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnUserMouseExited(evt);
            }
        });
        sidebarPanel.add(btnUsers);
        btnUsers.setBounds(20, 110, 160, 30);

        btnCustomers.setBackground(new java.awt.Color(64, 64, 80));
        btnCustomers.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnCustomers.setForeground(new java.awt.Color(255, 255, 255));
        btnCustomers.setText("CUSTOMERS");
        btnCustomers.setBorderPainted(false);
        btnCustomers.setFocusPainted(false);
        sidebarPanel.add(btnCustomers);
        btnCustomers.setBounds(20, 150, 160, 30);

        btnProducts.setBackground(new java.awt.Color(64, 64, 80));
        btnProducts.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnProducts.setForeground(new java.awt.Color(255, 255, 255));
        btnProducts.setText("PRODUCTS");
        btnProducts.setBorderPainted(false);
        btnProducts.setFocusPainted(false);
        sidebarPanel.add(btnProducts);
        btnProducts.setBounds(20, 230, 160, 30);

        btnTransactions.setBackground(new java.awt.Color(64, 64, 80));
        btnTransactions.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnTransactions.setForeground(new java.awt.Color(255, 255, 255));
        btnTransactions.setText("TRANSACTIONS");
        btnTransactions.setBorderPainted(false);
        btnTransactions.setFocusPainted(false);
        sidebarPanel.add(btnTransactions);
        btnTransactions.setBounds(20, 270, 160, 30);

        btnAuthorization.setBackground(new java.awt.Color(64, 64, 80));
        btnAuthorization.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnAuthorization.setForeground(new java.awt.Color(255, 255, 255));
        btnAuthorization.setText("PENDING");
        btnAuthorization.setBorderPainted(false);
        btnAuthorization.setFocusPainted(false);
        sidebarPanel.add(btnAuthorization);
        btnAuthorization.setBounds(90, 310, 90, 30);

        btnShipping.setBackground(new java.awt.Color(64, 64, 80));
        btnShipping.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnShipping.setForeground(new java.awt.Color(255, 255, 255));
        btnShipping.setText("SHIPPING");
        btnShipping.setBorderPainted(false);
        btnShipping.setFocusPainted(false);
        btnShipping.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShippingActionPerformed(evt);
            }
        });
        sidebarPanel.add(btnShipping);
        btnShipping.setBounds(20, 190, 160, 30);

        btnLogout.setBackground(new java.awt.Color(150, 50, 50));
        btnLogout.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("LOGOUT");
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        sidebarPanel.add(btnLogout);
        btnLogout.setBounds(20, 360, 160, 30);

        lblUserIcon.setBackground(new java.awt.Color(48, 48, 64));
        lblUserIcon.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblUserIcon.setForeground(new java.awt.Color(20, 40, 60));
        lblUserIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserIcon.setOpaque(true);
        sidebarPanel.add(lblUserIcon);
        lblUserIcon.setBounds(20, 10, 60, 50);

        lblWelcome.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(255, 255, 255));
        lblWelcome.setText("Welcome");
        sidebarPanel.add(lblWelcome);
        lblWelcome.setBounds(80, 10, 100, 20);

        lblRole.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblRole.setForeground(new java.awt.Color(255, 215, 0));
        lblRole.setText("Loading...");
        sidebarPanel.add(lblRole);
        lblRole.setBounds(80, 40, 100, 15);

        getContentPane().add(sidebarPanel, java.awt.BorderLayout.WEST);

        contentPanel.setBackground(new java.awt.Color(240, 240, 245));
        contentPanel.setLayout(new java.awt.BorderLayout());
        getContentPane().add(contentPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnShippingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShippingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnShippingActionPerformed

    /**
     * Helper to apply hover and style to NetBeans generated buttons
     */
    private void setupButton(JButton button, String text, int yPos) {
        button.setText(text);
        button.setBounds(20, yPos, 160, 40);
        button.setFont(new java.awt.Font("SansSerif", 1, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(64, 64, 80));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button != btnLogout) {
                    button.setBackground(new Color(255, 215, 0));
                    button.setForeground(Color.BLACK);
                } else {
                    button.setBackground(new Color(200, 60, 60));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button != btnLogout) {
                    button.setBackground(new Color(64, 64, 80));
                    button.setForeground(Color.WHITE);
                } else {
                    button.setBackground(new Color(150, 50, 50));
                }
            }
        });
    }

    private void loadUserInfo() {
        String username = Config.getCurrentUsername();
        String role = Config.getCurrentUserRole();
        
        lblWelcome.setText("Hi, " + username);
        lblRole.setText(role);
        
        boolean isAdmin = Config.isAdmin();
        btnUsers.setVisible(isAdmin);
        btnAuthorization.setVisible(isAdmin);
        loadProfilePicture();

        if (username != null && !username.isEmpty()) {
            lblUserIcon.setText(String.valueOf(username.charAt(0)).toUpperCase());
        }
    }
    
private void loadProfilePicture() {
    try {
        ResultSet rs = Config.getUserProfile();
        if (rs != null && rs.next()) {
            String imagePath = rs.getString("Profile_Pic");
            
            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    // Use ImageIO for better quality and reliable loading
                    Image img = javax.imageio.ImageIO.read(imageFile);
                    
                    // Scale to exactly 50x50
                    Image scaledImage = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    
                    // Force the label to fit exactly
                    lblUserIcon.setIcon(scaledIcon);
                    lblUserIcon.setText(""); 
                    lblUserIcon.setBorder(null); // Remove border so image fills everything
                    lblUserIcon.setPreferredSize(new Dimension(50, 50));
                } else {
                    setDefaultUserIcon();
                }
            } else {
                setDefaultUserIcon();
            }
        }
    } catch (Exception e) {
        System.err.println("Error loading image: " + e.getMessage());
        setDefaultUserIcon();
    }
}

private void setDefaultUserIcon() {
    String username = Config.getCurrentUsername();
    if (username != null && !username.isEmpty()) {
        lblUserIcon.setIcon(null); 
        lblUserIcon.setText(String.valueOf(username.charAt(0)).toUpperCase());
        lblUserIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblUserIcon.setVerticalAlignment(SwingConstants.CENTER);
        
        // Restore border for the "Letter" view so it looks like a circle/square
        lblUserIcon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
    }
}

    public void showPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new Login().setVisible(true);
            dispose();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAuthorization;
    private javax.swing.JButton btnCustomers;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnProducts;
    private javax.swing.JButton btnProfile;
    private javax.swing.JButton btnShipping;
    private javax.swing.JButton btnTransactions;
    private javax.swing.JButton btnUsers;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblUserIcon;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JLabel lblWelcome1;
    private javax.swing.JPanel sidebarPanel;
    // End of variables declaration//GEN-END:variables
    
    private void btnUserMouseExited(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
    }
}