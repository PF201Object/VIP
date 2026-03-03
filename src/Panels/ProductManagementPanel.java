package Panels;

import Config.Config;
import Utils.ImageUtils;
import Utils.TableUtils;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class ProductManagementPanel extends JPanel {
    private Dashboard dashboard;
    
    // Watch Viewer variables
    private JDialog watchViewerDialog;
    private JPanel viewerCardPanel;
    private CardLayout cardLayout;
    private List<WatchData> watchList;
    private int currentWatchIndex = 0;
    private Timer slideTimer;
    private float slideOffset = 0;
    private boolean isSliding = false;
    private int slideDirection = 1;
    private static final int SLIDE_STEPS = 15;
    private int slideStep = 0;

        public ProductManagementPanel(Dashboard dashboard) {
        this.dashboard = dashboard;
        initComponents();
        loadProductData();
        
        // Setup button listeners
        setupButtonListeners();
        
        // Add hover effects
        setupButtonHoverEffects();
        
        // Add mouse listener for double-click on table
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewProductDetails();
                }
            }
        });
        
        // Add enter key listener for search
        txtSearch.addActionListener(e -> searchProducts());
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
        for (ActionListener al : btnEdit.getActionListeners()) {
            btnEdit.removeActionListener(al);
        }
        for (ActionListener al : btnDelete.getActionListeners()) {
            btnDelete.removeActionListener(al);
        }
        for (ActionListener al : btnViewWatches.getActionListeners()) {
            btnViewWatches.removeActionListener(al);
        }
        
        // Add new listeners
        btnSearch.addActionListener(e -> searchProducts());
        btnRefresh.addActionListener(e -> loadProductData());
        btnAdd.addActionListener(e -> addProduct());
        btnEdit.addActionListener(e -> editProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnViewWatches.addActionListener(e -> openWatchViewer());
    }
    
    private void setupButtonHoverEffects() {
        addButtonHoverEffect(btnSearch, new Color(255, 215, 0), new Color(255, 240, 150));
        addButtonHoverEffect(btnRefresh, new Color(100, 100, 100), new Color(130, 130, 130));
        addButtonHoverEffect(btnAdd, new Color(0, 150, 0), new Color(0, 180, 0));
        addButtonHoverEffect(btnEdit, new Color(255, 150, 0), new Color(255, 180, 0));
        addButtonHoverEffect(btnDelete, new Color(200, 0, 0), new Color(230, 0, 0));
        addButtonHoverEffect(btnViewWatches, new Color(20, 40, 60), new Color(40, 60, 80));
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
                } else if (baseColor.equals(new Color(20, 40, 60))) {
                    button.setForeground(new Color(255, 215, 0));
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
        btnViewWatches = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        productTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(240, 240, 245));
        setPreferredSize(new java.awt.Dimension(900, 600));
        setLayout(null);

        lblTitle.setFont(new java.awt.Font("Serif", 1, 28)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(20, 40, 60));
        lblTitle.setText("Product Inventory");
        add(lblTitle);
        lblTitle.setBounds(10, 20, 221, 37);

        separator.setForeground(new java.awt.Color(255, 215, 0));
        add(separator);
        separator.setBounds(10, 60, 230, 10);

        searchPanel.setBackground(new java.awt.Color(240, 240, 245));
        searchPanel.setLayout(null);

        lblSearch.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblSearch.setText("Search:");
        searchPanel.add(lblSearch);
        lblSearch.setBounds(0, 0, 49, 19);

        txtSearch.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        searchPanel.add(txtSearch);
        txtSearch.setBounds(50, 0, 130, 30);

        btnSearch.setBackground(new java.awt.Color(255, 215, 0));
        btnSearch.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(40, 40, 40));
        btnSearch.setText("SEARCH");
        searchPanel.add(btnSearch);
        btnSearch.setBounds(200, 0, 90, 30);

        btnRefresh.setBackground(new java.awt.Color(100, 100, 100));
        btnRefresh.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("REFRESH");
        searchPanel.add(btnRefresh);
        btnRefresh.setBounds(310, 0, 90, 30);

        add(searchPanel);
        searchPanel.setBounds(10, 80, 430, 30);

        buttonPanel.setBackground(new java.awt.Color(240, 240, 245));
        buttonPanel.setLayout(null);

        btnAdd.setBackground(new java.awt.Color(0, 150, 0));
        btnAdd.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("ADD");
        buttonPanel.add(btnAdd);
        btnAdd.setBounds(0, 0, 80, 30);

        btnEdit.setBackground(new java.awt.Color(255, 150, 0));
        btnEdit.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("EDIT");
        buttonPanel.add(btnEdit);
        btnEdit.setBounds(100, 0, 80, 30);

        btnDelete.setBackground(new java.awt.Color(200, 0, 0));
        btnDelete.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("DELETE");
        buttonPanel.add(btnDelete);
        btnDelete.setBounds(300, 0, 80, 30);

        btnViewWatches.setBackground(new java.awt.Color(20, 40, 60));
        btnViewWatches.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnViewWatches.setForeground(new java.awt.Color(255, 215, 0));
        btnViewWatches.setText("VIEW");
        buttonPanel.add(btnViewWatches);
        btnViewWatches.setBounds(200, 0, 80, 30);

        add(buttonPanel);
        buttonPanel.setBounds(10, 360, 450, 30);

        scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        productTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Watch ID", "Brand", "Model", "Price", "Stock", "Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane.setViewportView(productTable);

        add(scrollPane);
        scrollPane.setBounds(10, 120, 760, 230);
    }// </editor-fold>//GEN-END:initComponents


   private void loadProductData() {
        try {
            ResultSet rs = Config.getAllProducts();
            DefaultTableModel model = (DefaultTableModel) productTable.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    "PRD" + rs.getInt("Watch_ID"),
                    rs.getString("Brand"),
                    rs.getString("Model_Name"),
                    String.format("$%.2f", rs.getDouble("Price")),
                    rs.getInt("Stock_Quantity"),
                    rs.getString("Description")
                };
                model.addRow(row);
            }
            
            TableUtils.setColumnWidths(productTable, 80, 120, 150, 100, 80, 250);
            
            // Load watch list for viewer
            loadWatchList();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading product data: " + e.getMessage());
        }
    }

    private void loadWatchList() {
        watchList = new ArrayList<>();
        try {
            ResultSet rs = Config.getAllProducts();
            while (rs.next()) {
                watchList.add(new WatchData(
                    rs.getInt("Watch_ID"),
                    rs.getString("Brand"),
                    rs.getString("Model_Name"),
                    rs.getDouble("Price"),
                    rs.getInt("Stock_Quantity"),
                    rs.getString("Description"),
                    rs.getString("Image_Path")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchProducts() {
        String searchTerm = txtSearch.getText().trim();
        if (searchTerm.isEmpty()) {
            loadProductData();
            return;
        }
        
        try {
            ResultSet rs = Config.searchProducts(searchTerm);
            DefaultTableModel model = (DefaultTableModel) productTable.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    "PRD" + rs.getInt("Watch_ID"),
                    rs.getString("Brand"),
                    rs.getString("Model_Name"),
                    String.format("$%.2f", rs.getDouble("Price")),
                    rs.getInt("Stock_Quantity"),
                    rs.getString("Description")
                };
                model.addRow(row);
            }
            
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No products found matching: " + searchTerm);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching products: " + e.getMessage());
        }
    }

    private void addProduct() {
        ProductDialog dialog = new ProductDialog(null);
        dialog.setVisible(true);
        loadProductData();
    }

    private void editProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product to edit");
            return;
        }
        
        String watchIdStr = productTable.getValueAt(selectedRow, 0).toString().replace("PRD", "");
        int watchId = Integer.parseInt(watchIdStr);
        ProductDialog dialog = new ProductDialog(watchId);
        dialog.setVisible(true);
        loadProductData();
    }

    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product to delete");
            return;
        }
        
        String productName = productTable.getValueAt(selectedRow, 1).toString() + " " + 
                             productTable.getValueAt(selectedRow, 2).toString();
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete: " + productName + "?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            String watchIdStr = productTable.getValueAt(selectedRow, 0).toString().replace("PRD", "");
            int watchId = Integer.parseInt(watchIdStr);
            if (Config.deleteProduct(watchId)) {
                JOptionPane.showMessageDialog(this, "Product deleted successfully!");
                loadProductData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete product!");
            }
        }
    }

    private void viewProductDetails() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            String watchIdStr = productTable.getValueAt(selectedRow, 0).toString().replace("PRD", "");
            int watchId = Integer.parseInt(watchIdStr);
            showWatchDetails(watchId);
        }
    }

    private void openWatchViewer() {
        if (watchList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No watches available to view");
            return;
        }
        
        currentWatchIndex = 0;
        createWatchViewer();
        watchViewerDialog.setVisible(true);
    }

    private void createWatchViewer() {
        watchViewerDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Watch Collection Viewer", true);
        watchViewerDialog.setSize(500, 450); // Smaller size 500x450
        watchViewerDialog.setLocationRelativeTo(this);
        watchViewerDialog.setResizable(false);
        watchViewerDialog.setLayout(new BorderLayout());
        
        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                Color color1 = new Color(20, 40, 60);
                Color color2 = new Color(40, 60, 80);
                GradientPaint gp = new GradientPaint(0, 0, color1, w, 0, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        
        // Top panel with counter
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);
        
        JLabel lblCounter = new JLabel(String.format("Watch %d of %d", currentWatchIndex + 1, watchList.size()));
        lblCounter.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblCounter.setForeground(new Color(255, 215, 0));
        topPanel.add(lblCounter);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Card panel for watch display
        cardLayout = new CardLayout();
        viewerCardPanel = new JPanel(cardLayout) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (isSliding) {
                    Graphics2D g2d = (Graphics2D) g;
                    int width = getWidth();
                    int translateX = (int) (slideOffset * width * slideDirection);
                    g2d.translate(translateX, 0);
                }
            }
        };
        viewerCardPanel.setOpaque(false);
        viewerCardPanel.setPreferredSize(new Dimension(480, 300));
        
        // Create cards for each watch
        for (int i = 0; i < watchList.size(); i++) {
            
            viewerCardPanel.add(createWatchCard(watchList.get(i)), "card" + i);
        }
        
        mainPanel.add(viewerCardPanel, BorderLayout.CENTER);
        
        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlPanel.setOpaque(false);
        
        // Left button
        JButton btnLeft = createNavButton("◀", "Previous Watch");
        btnLeft.addActionListener(e -> slideToPrevious(lblCounter));
        
        // Right button
        JButton btnRight = createNavButton("▶", "Next Watch");
        btnRight.addActionListener(e -> slideToNext(lblCounter));
        
        // Close button
        JButton btnClose = createStyledButton("CLOSE", new Color(100, 100, 100), Color.WHITE);
        btnClose.addActionListener(e -> watchViewerDialog.dispose());
        
        controlPanel.add(btnLeft);
        controlPanel.add(btnClose);
        controlPanel.add(btnRight);
        
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        
        watchViewerDialog.add(mainPanel);
    }

    private JPanel createWatchCard(WatchData watch) {
    JPanel card = new JPanel(null) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw semi-transparent overlay
            g2d.setColor(new Color(255, 255, 255, 20));
            g2d.fillRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 20, 20);
            
            // Draw border
            g2d.setColor(new Color(255, 215, 0));
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 20, 20);
        }
    };
    card.setOpaque(false);
    card.setLayout(null);
    
    int centerX = 250;
    int y = 15;
    
    // Watch ID
    JLabel lblId = new JLabel("WATCH #" + watch.getId());
    lblId.setFont(new Font("Serif", Font.BOLD, 16));
    lblId.setForeground(new Color(255, 215, 0));
    lblId.setHorizontalAlignment(SwingConstants.CENTER);
    lblId.setBounds(centerX - 80, y, 160, 25);
    card.add(lblId);
    y += 30;
    
    // Image Panel
    JPanel imagePanel = new JPanel(new BorderLayout());
    imagePanel.setBounds(centerX - 100, y, 200, 120);
    imagePanel.setBackground(new Color(30, 30, 40));
    imagePanel.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2));
    
    JLabel lblImage = new JLabel();
    lblImage.setHorizontalAlignment(SwingConstants.CENTER);
    
    // Try to load the imported image
    boolean imageLoaded = false;
    String imagePath = watch.getImagePath();
    
    if (imagePath != null && !imagePath.isEmpty()) {
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            // Load and scale the image
            ImageIcon originalIcon = new ImageIcon(imagePath);
            Image scaledImage = originalIcon.getImage().getScaledInstance(180, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            lblImage.setIcon(scaledIcon);
            lblImage.setText("");
            imageLoaded = true;
            System.out.println("Image loaded successfully: " + imagePath); // Debug line
        } else {
            System.out.println("Image file not found: " + imagePath); // Debug line
        }
    } else {
        System.out.println("No image path for watch " + watch.getId()); // Debug line
    }
    
    // If no image loaded, draw the default watch face
    if (!imageLoaded) {
        drawDefaultWatchFace(lblImage);
    }
    
    imagePanel.add(lblImage, BorderLayout.CENTER);
    card.add(imagePanel);
    y += 135;
    
    // Details Panel (rest of the code remains the same)
    JPanel detailsPanel = new JPanel(new GridLayout(4, 2, 5, 5));
    detailsPanel.setBounds(centerX - 120, y, 240, 100);
    detailsPanel.setOpaque(false);
    
    // Brand
    addDetailLabel(detailsPanel, "Brand:", watch.getBrand(), new Color(255, 215, 0));
    
    // Model
    addDetailLabel(detailsPanel, "Model:", watch.getModel(), Color.WHITE);
    
    // Price
    JLabel lblPriceLabel = new JLabel("Price:");
    lblPriceLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
    lblPriceLabel.setForeground(new Color(255, 215, 0));
    detailsPanel.add(lblPriceLabel);
    
    JLabel lblPrice = new JLabel(String.format("$%,.2f", watch.getPrice()));
    lblPrice.setFont(new Font("SansSerif", Font.BOLD, 11));
    lblPrice.setForeground(new Color(0, 255, 0));
    detailsPanel.add(lblPrice);
    
    // Stock
    JLabel lblStockLabel = new JLabel("Stock:");
    lblStockLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
    lblStockLabel.setForeground(new Color(255, 215, 0));
    detailsPanel.add(lblStockLabel);
    
    String stockStatus = watch.getStock() > 0 ? "In Stock (" + watch.getStock() + ")" : "Out of Stock";
    Color stockColor = watch.getStock() > 0 ? new Color(0, 255, 0) : Color.RED;
    
    JLabel lblStock = new JLabel(stockStatus);
    lblStock.setFont(new Font("SansSerif", Font.BOLD, 11));
    lblStock.setForeground(stockColor);
    detailsPanel.add(lblStock);
    
    card.add(detailsPanel);
    
    return card;
}

    private void addDetailLabel(JPanel panel, String label, String value, Color valueColor) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblLabel.setForeground(new Color(255, 215, 0));
        panel.add(lblLabel);
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblValue.setForeground(valueColor);
        panel.add(lblValue);
    }

    private void drawDefaultWatchFace(JLabel label) {
    // Create a simple watch face
    BufferedImage img = new BufferedImage(180, 100, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = img.createGraphics();
    
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
    // Draw watch body
    g2d.setColor(new Color(30, 30, 30));
    g2d.fillRoundRect(40, 10, 100, 80, 20, 20);
    
    // Draw bezel
    g2d.setColor(new Color(255, 215, 0));
    g2d.setStroke(new BasicStroke(2));
    g2d.drawRoundRect(40, 10, 100, 80, 20, 20);
    
    // Draw crown
    g2d.fillRect(140, 40, 15, 20);
    
    // Draw dial
    g2d.setColor(Color.BLACK);
    g2d.fillOval(55, 20, 70, 60);
    
    // Draw hour markers
    g2d.setColor(new Color(255, 215, 0));
    for (int i = 0; i < 12; i++) {
        double angle = Math.toRadians(i * 30);
        int x1 = 90 + (int)(25 * Math.sin(angle));
        int y1 = 50 - (int)(25 * Math.cos(angle));
        int x2 = 90 + (int)(28 * Math.sin(angle));
        int y2 = 50 - (int)(28 * Math.cos(angle));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x1, y1, x2, y2);
    }
    
    // Draw hands
    g2d.setColor(Color.WHITE);
    g2d.setStroke(new BasicStroke(2));
    g2d.drawLine(90, 50, 70, 35); // Hour hand
    g2d.drawLine(90, 50, 110, 30); // Minute hand
    
    // Draw center
    g2d.setColor(new Color(255, 215, 0));
    g2d.fillOval(87, 47, 6, 6);
    
    g2d.dispose();
    
    label.setIcon(new ImageIcon(img));
    label.setText("");
}

    private JButton createNavButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(new Color(255, 215, 0));
        button.setForeground(new Color(20, 40, 60));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(45, 35));
        button.setToolTipText(tooltip);
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 235, 120));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 215, 0));
            }
        });
        
        return button;
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 11));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(70, 30));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    private void slideToNext(JLabel counter) {
        if (isSliding || currentWatchIndex >= watchList.size() - 1) return;
        slideDirection = -1;
        startSlideAnimation(currentWatchIndex + 1, counter);
    }

    private void slideToPrevious(JLabel counter) {
        if (isSliding || currentWatchIndex <= 0) return;
        slideDirection = 1;
        startSlideAnimation(currentWatchIndex - 1, counter);
    }

    private void startSlideAnimation(int targetIndex, JLabel counter) {
        isSliding = true;
        slideStep = 0;
        
        slideTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                slideStep++;
                slideOffset = (float) slideStep / SLIDE_STEPS;
                
                if (slideStep >= SLIDE_STEPS) {
                    slideTimer.stop();
                    isSliding = false;
                    slideOffset = 0;
                    
                    currentWatchIndex = targetIndex;
                    cardLayout.show(viewerCardPanel, "card" + currentWatchIndex);
                    counter.setText(String.format("Watch %d of %d", currentWatchIndex + 1, watchList.size()));
                }
                
                viewerCardPanel.repaint();
            }
        });
        
        slideTimer.start();
    }

    private void showWatchDetails(int watchId) {
        for (WatchData watch : watchList) {
            if (watch.getId() == watchId) {
                JOptionPane.showMessageDialog(this,
                    "Watch Details:\n\n" +
                    "ID: " + watch.getId() + "\n" +
                    "Brand: " + watch.getBrand() + "\n" +
                    "Model: " + watch.getModel() + "\n" +
                    "Price: $" + String.format("%,.2f", watch.getPrice()) + "\n" +
                    "Stock: " + watch.getStock() + "\n" +
                    "Description: " + watch.getDescription(),
                    "Watch Information",
                    JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
    }

    // Inner class for Product Dialog with Image Import
    private class ProductDialog extends JDialog {
        private JTextField txtModel, txtBrand, txtPrice, txtStock, txtDescription;
        private JLabel lblImagePreview;
        private JButton btnImportImage;
        private String imagePath;
        private Integer watchId;

        public ProductDialog(Integer watchId) {
            this.watchId = watchId;
            initDialog();
            if (watchId != null) {
                loadProductData();
            }
        }

        private void initDialog() {
            setTitle(watchId == null ? "Add Product" : "Edit Product");
            setSize(500, 500);
            setLocationRelativeTo(ProductManagementPanel.this);
            setModal(true);
            setLayout(null);
            getContentPane().setBackground(new Color(240, 240, 245));

            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBounds(10, 10, 465, 440);
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            add(panel);

            int y = 20;
            int labelWidth = 90;
            int fieldWidth = 250;

            // Brand
            JLabel lblBrand = new JLabel("Brand:");
            lblBrand.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblBrand.setBounds(30, y, labelWidth, 25);
            panel.add(lblBrand);

            txtBrand = new JTextField();
            txtBrand.setBounds(130, y, fieldWidth, 30);
            styleTextField(txtBrand);
            panel.add(txtBrand);
            y += 40;

            // Model
            JLabel lblModel = new JLabel("Model:");
            lblModel.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblModel.setBounds(30, y, labelWidth, 25);
            panel.add(lblModel);

            txtModel = new JTextField();
            txtModel.setBounds(130, y, fieldWidth, 30);
            styleTextField(txtModel);
            panel.add(txtModel);
            y += 40;

            // Price
            JLabel lblPrice = new JLabel("Price:");
            lblPrice.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblPrice.setBounds(30, y, labelWidth, 25);
            panel.add(lblPrice);

            txtPrice = new JTextField();
            txtPrice.setBounds(130, y, fieldWidth, 30);
            styleTextField(txtPrice);
            panel.add(txtPrice);
            y += 40;

            // Stock
            JLabel lblStock = new JLabel("Stock:");
            lblStock.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblStock.setBounds(30, y, labelWidth, 25);
            panel.add(lblStock);

            txtStock = new JTextField();
            txtStock.setBounds(130, y, fieldWidth, 30);
            styleTextField(txtStock);
            panel.add(txtStock);
            y += 40;

            // Description
            JLabel lblDesc = new JLabel("Description:");
            lblDesc.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblDesc.setBounds(30, y, labelWidth, 25);
            panel.add(lblDesc);

            txtDescription = new JTextField();
            txtDescription.setBounds(130, y, fieldWidth, 30);
            styleTextField(txtDescription);
            y += 45;

            // Image Preview
            JLabel lblImage = new JLabel("Product Image:");
            lblImage.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblImage.setBounds(30, y, 100, 25);
            panel.add(lblImage);

            lblImagePreview = new JLabel();
            lblImagePreview.setBounds(130, y, 100, 80);
            lblImagePreview.setBackground(new Color(240, 240, 240));
            lblImagePreview.setOpaque(true);
            lblImagePreview.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            lblImagePreview.setHorizontalAlignment(SwingConstants.CENTER);
            lblImagePreview.setText("No Image");
            panel.add(lblImagePreview);

            btnImportImage = new JButton("IMPORT");
            btnImportImage.setBounds(240, y + 25, 100, 30);
            btnImportImage.setBackground(new Color(20, 40, 60));
            btnImportImage.setForeground(new Color(255, 215, 0));
            btnImportImage.setFont(new Font("SansSerif", Font.BOLD, 11));
            btnImportImage.setFocusPainted(false);
            btnImportImage.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnImportImage.addActionListener(e -> importImage());
            panel.add(btnImportImage);
            y += 95;

            // Buttons
            JButton btnSave = new JButton("SAVE");
            btnSave.setBounds(130, y, 100, 35);
            btnSave.setBackground(new Color(255, 215, 0));
            btnSave.setFont(new Font("SansSerif", Font.BOLD, 12));
            btnSave.setFocusPainted(false);
            btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnSave.addActionListener(e -> saveProduct());
            panel.add(btnSave);

            JButton btnCancel = new JButton("CANCEL");
            btnCancel.setBounds(240, y, 100, 35);
            btnCancel.setBackground(new Color(100, 100, 100));
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setFont(new Font("SansSerif", Font.BOLD, 12));
            btnCancel.setFocusPainted(false);
            btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnCancel.addActionListener(e -> dispose());
            panel.add(btnCancel);
        }

        private void styleTextField(JTextField field) {
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }

        private void importImage() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Product Image");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif", "bmp"));
            
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePath = selectedFile.getAbsolutePath();
                
                // Display image in preview
                ImageIcon icon = ImageUtils.getScaledIcon(imagePath, 100, 80);
                if (icon != null) {
                    lblImagePreview.setIcon(icon);
                    lblImagePreview.setText("");
                } else {
                    lblImagePreview.setIcon(null);
                    lblImagePreview.setText("Error loading image");
                }
            }
        }

        private void loadProductData() {
    try {
        ResultSet rs = Config.getAllProducts();
        while (rs.next()) {
            if (rs.getInt("Watch_ID") == watchId) {
                txtBrand.setText(rs.getString("Brand"));
                txtModel.setText(rs.getString("Model_Name"));
                txtPrice.setText(String.valueOf(rs.getDouble("Price")));
                txtStock.setText(String.valueOf(rs.getInt("Stock_Quantity")));
                txtDescription.setText(rs.getString("Description"));
                
                imagePath = rs.getString("Image_Path");
                if (imagePath != null && !imagePath.isEmpty()) {
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        ImageIcon icon = ImageUtils.getScaledIcon(imagePath, 100, 80);
                        if (icon != null) {
                            lblImagePreview.setIcon(icon);
                            lblImagePreview.setText("");
                        } else {
                            lblImagePreview.setIcon(null);
                            lblImagePreview.setText("Error loading image");
                        }
                    } else {
                        lblImagePreview.setIcon(null);
                        lblImagePreview.setText("Image not found");
                    }
                } else {
                    lblImagePreview.setIcon(null);
                    lblImagePreview.setText("No Image");
                }
                break;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

        private void saveProduct() {
            String brand = txtBrand.getText().trim();
            String model = txtModel.getText().trim();
            String priceStr = txtPrice.getText().trim();
            String stockStr = txtStock.getText().trim();
            String desc = txtDescription.getText().trim();

            if (brand.isEmpty() || model.isEmpty() || priceStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Brand, Model, and Price are required");
                return;
            }

            double price;
            int stock = 0;

            try {
                price = Double.parseDouble(priceStr);
                if (!stockStr.isEmpty()) {
                    stock = Integer.parseInt(stockStr);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid price or stock format");
                return;
            }

            boolean success;
            if (watchId == null) {
                // For new product, first add it then update image if needed
                success = Config.addProduct(model, brand, price, stock, desc);
                if (success && imagePath != null && !imagePath.isEmpty()) {
                    // Get the newly created watch ID
                    int newWatchId = getLastInsertedWatchId();
                    if (newWatchId != -1) {
                        Config.updateProductImage(newWatchId, imagePath);
                    }
                }
            } else {
                // For existing product, update all fields including image
                success = Config.updateProduct(watchId, model, brand, price, stock, desc);
                if (success && imagePath != null && !imagePath.isEmpty()) {
                    Config.updateProductImage(watchId, imagePath);
                }
            }

            if (success) {
                JOptionPane.showMessageDialog(this, "Product saved successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save product!");
            }
        }
        private int getLastInsertedWatchId() {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
                conn = Config.connect();
                if (conn == null) return -1;
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT last_insert_rowid()");
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                Config.closeResultSet(rs);
                Config.closeStatement(stmt);
                Config.closeConnection(conn);
            }
            return -1;
        }

    }

    // Inner class to hold watch data
    private class WatchData {
        private int id;
        private String brand;
        private String model;
        private double price;
        private int stock;
        private String description;
        private String imagePath;

        public WatchData(int id, String brand, String model, double price, int stock, String description, String imagePath) {
            this.id = id;
            this.brand = brand;
            this.model = model;
            this.price = price;
            this.stock = stock;
            this.description = description;
            this.imagePath = imagePath;
        }

        public int getId() { return id; }
        public String getBrand() { return brand; }
        public String getModel() { return model; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public String getDescription() { return description; }
        public String getImagePath() { return imagePath; }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnViewWatches;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable productTable;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JSeparator separator;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}