package Config;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Config {
    private static final String URL = "jdbc:sqlite:VelocityWatch.db";
    private static String currentUsername = "";
    private static String currentUserRole = "Guest";
    private static int currentUserId = 0;
    
    // Track open connections to ensure they're closed
    private static final Set<Connection> activeConnections = new HashSet<>();

    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            // Set busy timeout to 30 seconds and enable WAL mode for better concurrency
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA busy_timeout = 30000;");
                stmt.execute("PRAGMA journal_mode = WAL;");
                stmt.execute("PRAGMA foreign_keys = ON;");
            }
            synchronized (activeConnections) {
                activeConnections.add(conn);
            }
            return conn;
        } catch (SQLException e) {
            System.err.println("Connection Failed: " + e.getMessage());
            return null;
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                synchronized (activeConnections) {
                    activeConnections.remove(conn);
                }
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                if (!stmt.isClosed()) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                if (!rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }
    }

    public static void closeResultSetAndParent(ResultSet rs) {
        if (rs != null) {
            try {
                Statement stmt = rs.getStatement();
                Connection conn = stmt != null ? stmt.getConnection() : null;
                closeResultSet(rs);
                closeStatement(stmt);
                closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing ResultSet and parents: " + e.getMessage());
            }
        }
    }

    // Close all connections on shutdown
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            synchronized (activeConnections) {
                for (Connection conn : activeConnections) {
                    try {
                        if (!conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        System.err.println("Error closing connection on shutdown: " + e.getMessage());
                    }
                }
                activeConnections.clear();
            }
        }));
    }

    public static void initializeDB() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = connect();
            if (conn == null) return;
            
            stmt = conn.createStatement();
            
            String usersTable = "CREATE TABLE IF NOT EXISTS Users ("
                + "User_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Username TEXT NOT NULL UNIQUE,"
                + "Email TEXT NOT NULL UNIQUE,"
                + "Password_Hash TEXT NOT NULL,"
                + "Role TEXT DEFAULT 'User',"
                + "Profile_Pic TEXT,"
                + "Created_Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

            String customerTable = "CREATE TABLE IF NOT EXISTS Customer ("
                + "Customer_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "User_ID INTEGER UNIQUE,"
                + "First_Name TEXT NOT NULL,"
                + "Last_Name TEXT NOT NULL,"
                + "Phone_Number TEXT,"
                + "Default_Address TEXT,"
                + "Join_Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY (User_ID) REFERENCES Users(User_ID) ON DELETE CASCADE);";

            String productTable = "CREATE TABLE IF NOT EXISTS Product ("
                + "Watch_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Model_Name TEXT NOT NULL,"
                + "Brand TEXT NOT NULL,"
                + "Price REAL NOT NULL,"
                + "Stock_Quantity INTEGER DEFAULT 0,"
                + "Description TEXT,"
                + "Image_Path TEXT);";

            String transactionTable = "CREATE TABLE IF NOT EXISTS `Transaction` ("
                + "Transaction_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Customer_ID INTEGER NOT NULL,"
                + "Total_Amount REAL NOT NULL,"
                + "Order_Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "Payment_Method TEXT,"
                + "Status TEXT DEFAULT 'Pending',"
                + "Created_By INTEGER,"
                + "FOREIGN KEY (Customer_ID) REFERENCES Customer(Customer_ID) ON DELETE CASCADE,"
                + "FOREIGN KEY (Created_By) REFERENCES Users(User_ID) ON DELETE SET NULL);";

            String orderItemsTable = "CREATE TABLE IF NOT EXISTS Order_Items ("
                + "Item_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Transaction_ID INTEGER NOT NULL,"
                + "Watch_ID INTEGER NOT NULL,"
                + "Quantity INTEGER NOT NULL,"
                + "Price_At_Purchase REAL NOT NULL,"
                + "FOREIGN KEY (Transaction_ID) REFERENCES `Transaction`(Transaction_ID) ON DELETE CASCADE,"
                + "FOREIGN KEY (Watch_ID) REFERENCES Product(Watch_ID) ON DELETE CASCADE);";

            String shippingTable = "CREATE TABLE IF NOT EXISTS Shipping ("
                + "Ship_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Transaction_ID INTEGER NOT NULL UNIQUE,"
                + "Courier_Name TEXT,"
                + "Tracking_Number TEXT UNIQUE,"
                + "Shipping_Status TEXT DEFAULT 'Processing',"
                + "Shipping_Address TEXT,"
                + "Shipped_Date TIMESTAMP,"
                + "Estimated_Delivery DATE,"
                + "FOREIGN KEY (Transaction_ID) REFERENCES `Transaction`(Transaction_ID) ON DELETE CASCADE);";

            stmt.execute(usersTable);
            stmt.execute(customerTable);
            stmt.execute(productTable);
            stmt.execute(transactionTable);
            stmt.execute(orderItemsTable);
            stmt.execute(shippingTable);
            
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_transaction_customer ON `Transaction`(Customer_ID);");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_transaction_status ON `Transaction`(Status);");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_order_items_transaction ON Order_Items(Transaction_ID);");
            
            // Set sequence to start from 1000 for Users
            stmt.execute("UPDATE sqlite_sequence SET seq = 999 WHERE name = 'Users';");
            
            insertSampleData(conn);
            
        } catch (SQLException e) {
            System.err.println("DB Init Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeStatement(stmt);
            closeConnection(conn);
        }
    }

    private static void insertSampleData(Connection conn) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            
            // Check if we have products
            rs = stmt.executeQuery("SELECT COUNT(*) FROM Product");
            if (rs.next() && rs.getInt(1) == 0) {
                String insertProducts = "INSERT INTO Product (Model_Name, Brand, Price, Stock_Quantity, Description) VALUES " +
                    "('Submariner Date', 'Rolex', 8950.00, 5, '40mm, Oystersteel, Black dial')," +
                    "('Speedmaster Professional', 'Omega', 6250.00, 8, '42mm, Stainless steel, Moonwatch')," +
                    "('Navitimer B01', 'Breitling', 8450.00, 3, '43mm, Stainless steel, Chronograph')," +
                    "('Portugieser Chronograph', 'IWC', 6950.00, 4, '41mm, Stainless steel, Blue dial')," +
                    "('Grand Seiko Snowflake', 'Seiko', 5800.00, 6, '41mm, Titanium, Spring Drive')," +
                    "('Royal Oak', 'Audemars Piguet', 23500.00, 2, '41mm, Stainless steel, Luxury sports')," +
                    "('Nautilus', 'Patek Philippe', 32500.00, 1, '40mm, Stainless steel, Iconic')," +
                    "('Sea-Dweller', 'Rolex', 12500.00, 3, '43mm, Oystersteel, Deep sea')";
                stmt.executeUpdate(insertProducts);
            }
            
            closeResultSet(rs);
            
            // Check if we have an admin
            rs = stmt.executeQuery("SELECT COUNT(*) FROM Users WHERE Role = 'Admin'");
            if (rs.next() && rs.getInt(1) == 0) {
                String insertAdmin = "INSERT INTO Users (Username, Email, Password_Hash, Role) VALUES " +
                    "('admin', 'admin@velocitywatch.com', 'admin123', 'Admin')";
                stmt.executeUpdate(insertAdmin);
                
                // Get the admin ID
                ResultSet keys = stmt.executeQuery("SELECT last_insert_rowid()");
                if (keys.next()) {
                    int adminId = keys.getInt(1);
                    String insertAdminCustomer = "INSERT INTO Customer (User_ID, First_Name, Last_Name, Phone_Number, Default_Address) VALUES " +
                        "(" + adminId + ", 'System', 'Administrator', '123-456-7890', 'Admin Office')";
                    stmt.executeUpdate(insertAdminCustomer);
                }
                closeResultSet(keys);
            }
            
        } catch (SQLException e) {
            System.err.println("Sample Data Error: " + e.getMessage());
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    // UPDATED: Removed role and address parameters
    // UPDATED: Removed role and address parameters with debugging
public static boolean registerUser(String username, String email, String password,
                                  String firstName, String lastName, String phone) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try {
        conn = connect();
        if (conn == null) {
            System.err.println("Failed to connect to database");
            return false;
        }
        
        conn.setAutoCommit(false);
        
        // First check if username or email already exists
        if (isUsernameExists(username)) {
            System.err.println("Username already exists: " + username);
            return false;
        }
        if (isEmailExists(email)) {
            System.err.println("Email already exists: " + email);
            return false;
        }
        
        // Insert into Users table with default role 'User'
        String userSql = "INSERT INTO Users (Username, Email, Password_Hash, Role) VALUES (?, ?, ?, ?)";
        pstmt = conn.prepareStatement(userSql);
        pstmt.setString(1, username);
        pstmt.setString(2, email);
        pstmt.setString(3, password);
        pstmt.setString(4, "User");
        int userResult = pstmt.executeUpdate();
        
        if (userResult == 0) {
            conn.rollback();
            return false;
        }
        
        closeStatement(pstmt);
        
        // Get the last insert ID
        pstmt = conn.prepareStatement("SELECT last_insert_rowid()");
        rs = pstmt.executeQuery();
        int userId = -1;
        if (rs.next()) {
            userId = rs.getInt(1);
        }
        closeResultSet(rs);
        closeStatement(pstmt);
        
        if (userId == -1) {
            conn.rollback();
            return false;
        }
        
        // Insert into Customer table with empty address
        String customerSql = "INSERT INTO Customer (User_ID, First_Name, Last_Name, Phone_Number, Default_Address, Join_Date) VALUES (?, ?, ?, ?, ?, datetime('now'))";
        pstmt = conn.prepareStatement(customerSql);
        pstmt.setInt(1, userId);
        pstmt.setString(2, firstName);
        pstmt.setString(3, lastName);
        pstmt.setString(4, phone);
        pstmt.setString(5, "");
        int customerResult = pstmt.executeUpdate();
        
        if (customerResult == 0) {
            conn.rollback();
            return false;
        }
        
        conn.commit();
        return true;
        
    } catch (SQLException e) {
        System.err.println("Registration Error: " + e.getMessage());
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback Error: " + ex.getMessage());
            }
        }
        return false;
    } finally {
        closeResultSet(rs);
        closeStatement(pstmt);
        closeConnection(conn);
    }
}
// Add this method to fix any corrupted data in the database
public static void fixDatabaseConstraints() {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = connect();
        if (conn == null) return;
        
        stmt = conn.createStatement();
        
        System.out.println("\n=== FIXING DATABASE CONSTRAINTS ===");
        
        // Find customers without matching users
        rs = stmt.executeQuery(
            "SELECT c.Customer_ID, c.User_ID FROM Customer c " +
            "LEFT JOIN Users u ON c.User_ID = u.User_ID " +
            "WHERE u.User_ID IS NULL"
        );
        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            System.out.println("Found orphaned customer: CustomerID=" + customerId + ", User_ID=" + userId);
            
            // Delete orphaned customer records
            int deleted = stmt.executeUpdate("DELETE FROM Customer WHERE Customer_ID = " + customerId);
            System.out.println("Deleted orphaned customer: " + deleted + " row(s) affected");
        }
        closeResultSet(rs);
        
        // Find users without customer records and create them
        rs = stmt.executeQuery(
            "SELECT u.User_ID, u.Username FROM Users u " +
            "LEFT JOIN Customer c ON u.User_ID = c.User_ID " +
            "WHERE c.User_ID IS NULL"
        );
        while (rs.next()) {
            int userId = rs.getInt("User_ID");
            String username = rs.getString("Username");
            System.out.println("Found user without customer: ID=" + userId + ", Username=" + username);
            
            // Create default customer record
            String insertCustomer = "INSERT INTO Customer (User_ID, First_Name, Last_Name, Phone_Number, Default_Address, Join_Date) " +
                                   "VALUES (" + userId + ", 'System', 'Generated', '', '', datetime('now'))";
            int inserted = stmt.executeUpdate(insertCustomer);
            System.out.println("Created default customer: " + inserted + " row(s) affected");
        }
        
        // Reset the auto-increment sequence
        rs = stmt.executeQuery("SELECT MAX(User_ID) FROM Users");
        if (rs.next()) {
            int maxId = rs.getInt(1);
            System.out.println("Max User_ID in Users table: " + maxId);
            
            // Update sqlite_sequence
            stmt.executeUpdate("UPDATE sqlite_sequence SET seq = " + maxId + " WHERE name = 'Users'");
            System.out.println("Updated sqlite_sequence to " + maxId);
        }
        
        System.out.println("=== DATABASE FIX COMPLETE ===\n");
        
    } catch (SQLException e) {
        System.err.println("Fix Database Error: " + e.getMessage());
    } finally {
        closeResultSet(rs);
        closeStatement(stmt);
        closeConnection(conn);
    }
}

    public static boolean authenticateUser(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = connect();
            if (conn == null) return false;
            
            String sql = "SELECT User_ID, Username, Role FROM Users WHERE (Username = ? OR Email = ?) AND Password_Hash = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            
            rs = pstmt.executeQuery();
            if (rs.next()) {
                currentUserId = rs.getInt("User_ID");
                currentUsername = rs.getString("Username");
                currentUserRole = rs.getString("Role");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Auth Error: " + e.getMessage());
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
        return false;
    }

    public static ResultSet getUserProfile() {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT u.User_ID, u.Username, u.Email, u.Role, u.Profile_Pic, " +
                        "c.First_Name, c.Last_Name, c.Phone_Number, c.Default_Address, c.Join_Date " +
                        "FROM Users u LEFT JOIN Customer c ON u.User_ID = c.User_ID " +
                        "WHERE u.User_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, currentUserId);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Profile Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static boolean updateProfile(String firstName, String lastName, String phone, String address, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = connect();
            if (conn == null) return false;
            
            conn.setAutoCommit(false);
            
            String sql = "UPDATE Customer SET First_Name = ?, Last_Name = ?, Phone_Number = ?, Default_Address = ? WHERE User_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, phone);
            pstmt.setString(4, address);
            pstmt.setInt(5, currentUserId);
            pstmt.executeUpdate();
            
            closeStatement(pstmt);
            
            if (password != null && !password.isEmpty()) {
                String sql2 = "UPDATE Users SET Password_Hash = ? WHERE User_ID = ?";
                pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1, password);
                pstmt.setInt(2, currentUserId);
                pstmt.executeUpdate();
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Update Error: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Rollback Error: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    public static ResultSet getAllUsers() {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT u.User_ID, u.Username, u.Email, u.Role, " +
                        "c.First_Name, c.Last_Name, c.Phone_Number " +
                        "FROM Users u LEFT JOIN Customer c ON u.User_ID = c.User_ID " +
                        "ORDER BY u.User_ID";
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Get Users Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static ResultSet searchUsers(String searchTerm) {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT u.User_ID, u.Username, u.Email, u.Role, " +
                        "c.First_Name, c.Last_Name, c.Phone_Number " +
                        "FROM Users u LEFT JOIN Customer c ON u.User_ID = c.User_ID " +
                        "WHERE u.Username LIKE ? OR u.Email LIKE ? OR c.First_Name LIKE ? OR c.Last_Name LIKE ? " +
                        "ORDER BY u.User_ID";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            String pattern = "%" + searchTerm + "%";
            pstmt.setString(1, pattern);
            pstmt.setString(2, pattern);
            pstmt.setString(3, pattern);
            pstmt.setString(4, pattern);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Search Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    // UPDATED: Removed role and address parameters
    public static boolean addUser(String username, String email, String password,
                                 String firstName, String lastName, String phone) {
        return registerUser(username, email, password, firstName, lastName, phone);
    }

    public static boolean updateUser(int userId, String username, String email, String role,
                                    String firstName, String lastName, String phone) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = connect();
            if (conn == null) return false;
            
            conn.setAutoCommit(false);
            
            String userSql = "UPDATE Users SET Username = ?, Email = ?, Role = ? WHERE User_ID = ?";
            pstmt = conn.prepareStatement(userSql);
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, role);
            pstmt.setInt(4, userId);
            pstmt.executeUpdate();
            
            closeStatement(pstmt);
            
            String customerSql = "UPDATE Customer SET First_Name = ?, Last_Name = ?, Phone_Number = ?, WHERE User_ID = ?";
            pstmt = conn.prepareStatement(customerSql);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, phone);
            pstmt.setInt(4, userId);
            pstmt.executeUpdate();
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Update User Error: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Rollback Error: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    public static boolean deleteUser(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = connect();
            if (conn == null) return false;
            
            conn.setAutoCommit(false);
            
            String sql1 = "DELETE FROM Customer WHERE User_ID = ?";
            pstmt = conn.prepareStatement(sql1);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            
            closeStatement(pstmt);
            
            String sql2 = "DELETE FROM Users WHERE User_ID = ?";
            pstmt = conn.prepareStatement(sql2);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Delete User Error: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Rollback Error: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    public static ResultSet getAllCustomers() {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT c.*, u.Username, u.Email FROM Customer c " +
                        "LEFT JOIN Users u ON c.User_ID = u.User_ID ORDER BY c.Customer_ID";
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Get Customers Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static ResultSet getAllProducts() {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT * FROM Product ORDER BY Brand, Model_Name";
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Get Products Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static boolean addProduct(String model, String brand, double price, int stock, String description) {
        String sql = "INSERT INTO Product (Model_Name, Brand, Price, Stock_Quantity, Description) VALUES (?, ?, ?, ?, ?)";
        return executeUpdate(sql, model, brand, price, stock, description);
    }

    public static boolean updateProduct(int watchId, String model, String brand, double price, int stock, String description) {
        String sql = "UPDATE Product SET Model_Name = ?, Brand = ?, Price = ?, Stock_Quantity = ?, Description = ? WHERE Watch_ID = ?";
        return executeUpdate(sql, model, brand, price, stock, description, watchId);
    }

    public static boolean deleteProduct(int watchId) {
        String sql = "DELETE FROM Product WHERE Watch_ID = ?";
        return executeUpdate(sql, watchId);
    }

    public static ResultSet getAllTransactions() {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT t.*, c.First_Name, c.Last_Name FROM `Transaction` t " +
                        "LEFT JOIN Customer c ON t.Customer_ID = c.Customer_ID " +
                        "ORDER BY t.Order_Date DESC";
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Get Transactions Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static ResultSet getPendingTransactions() {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT t.*, c.First_Name, c.Last_Name FROM `Transaction` t " +
                        "LEFT JOIN Customer c ON t.Customer_ID = c.Customer_ID " +
                        "WHERE t.Status = 'Pending' ORDER BY t.Order_Date";
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Get Pending Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static int createTransaction(int customerId, double total, String paymentMethod, String status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = connect();
            if (conn == null) return -1;
            
            String sql = "INSERT INTO `Transaction` (Customer_ID, Total_Amount, Payment_Method, Status, Created_By, Order_Date) VALUES (?, ?, ?, ?, ?, datetime('now'))";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            pstmt.setDouble(2, total);
            pstmt.setString(3, paymentMethod);
            pstmt.setString(4, status);
            pstmt.setInt(5, currentUserId);
            pstmt.executeUpdate();
            
            closeStatement(pstmt);
            
            pstmt = conn.prepareStatement("SELECT last_insert_rowid()");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Create Transaction Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
        return -1;
    }

    public static boolean updateTransactionStatus(int transactionId, String status) {
        String sql = "UPDATE `Transaction` SET Status = ? WHERE Transaction_ID = ?";
        boolean success = executeUpdate(sql, status, transactionId);
        
        if (success && status.equals("Paid")) {
            createShipping(transactionId);
        }
        
        return success;
    }

    public static boolean addOrderItem(int transactionId, int watchId, int quantity, double price) {
        String sql = "INSERT INTO Order_Items (Transaction_ID, Watch_ID, Quantity, Price_At_Purchase) VALUES (?, ?, ?, ?)";
        return executeUpdate(sql, transactionId, watchId, quantity, price);
    }

    public static ResultSet getOrderItems(int transactionId) {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT oi.*, p.Model_Name, p.Brand FROM Order_Items oi " +
                        "LEFT JOIN Product p ON oi.Watch_ID = p.Watch_ID " +
                        "WHERE oi.Transaction_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, transactionId);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Get Order Items Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static boolean createShipping(int transactionId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = connect();
            if (conn == null) return false;
            
            String trackingNumber = "VW" + System.currentTimeMillis() + transactionId;
            
            String addressSql = "SELECT c.Default_Address FROM `Transaction` t " +
                               "LEFT JOIN Customer c ON t.Customer_ID = c.Customer_ID " +
                               "WHERE t.Transaction_ID = ?";
            pstmt = conn.prepareStatement(addressSql);
            pstmt.setInt(1, transactionId);
            rs = pstmt.executeQuery();
            
            String address = rs.next() ? rs.getString("Default_Address") : "Address not specified";
            
            closeResultSet(rs);
            closeStatement(pstmt);
            
            String sql = "INSERT INTO Shipping (Transaction_ID, Tracking_Number, Shipping_Address, Shipping_Status, Estimated_Delivery) " +
                        "VALUES (?, ?, ?, 'Processing', date('now', '+7 days'))";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, transactionId);
            pstmt.setString(2, trackingNumber);
            pstmt.setString(3, address);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Create Shipping Error: " + e.getMessage());
            return false;
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    public static ResultSet getShippingInfo(int transactionId) {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT * FROM Shipping WHERE Transaction_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, transactionId);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Get Shipping Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static boolean updateShippingStatus(int shipId, String status) {
        String sql = "UPDATE Shipping SET Shipping_Status = ? WHERE Ship_ID = ?";
        return executeUpdate(sql, status, shipId);
    }

    public static ResultSet getReceiptData(int transactionId) {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT t.*, c.First_Name, c.Last_Name, c.Phone_Number, c.Default_Address, " +
                        "u.Username as Processed_By " +
                        "FROM `Transaction` t " +
                        "LEFT JOIN Customer c ON t.Customer_ID = c.Customer_ID " +
                        "LEFT JOIN Users u ON t.Created_By = u.User_ID " +
                        "WHERE t.Transaction_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, transactionId);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Get Receipt Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static boolean addCustomer(String firstName, String lastName, String phone, String address) {
        String sql = "INSERT INTO Customer (First_Name, Last_Name, Phone_Number, Default_Address, Join_Date) VALUES (?, ?, ?, ?, datetime('now'))";
        return executeUpdate(sql, firstName, lastName, phone, address);
    }

    public static boolean updateCustomer(int customerId, String firstName, String lastName, String phone, String address) {
        String sql = "UPDATE Customer SET First_Name = ?, Last_Name = ?, Phone_Number = ?, Default_Address = ? WHERE Customer_ID = ?";
        return executeUpdate(sql, firstName, lastName, phone, address, customerId);
    }

    public static boolean deleteCustomer(int customerId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = connect();
            if (conn == null) return false;
            
            // First check if this customer has any transactions
            String checkSql = "SELECT COUNT(*) as count FROM `Transaction` WHERE Customer_ID = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setInt(1, customerId);
            rs = pstmt.executeQuery();
            
            if (rs.next() && rs.getInt("count") > 0) {
                System.err.println("Cannot delete customer with existing transactions");
                return false;
            }
            
            closeResultSet(rs);
            closeStatement(pstmt);
            
            String deleteSql = "DELETE FROM Customer WHERE Customer_ID = ?";
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1, customerId);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Delete Customer Error: " + e.getMessage());
            return false;
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    public static ResultSet searchCustomers(String searchTerm) {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT c.*, u.Username, u.Email FROM Customer c " +
                        "LEFT JOIN Users u ON c.User_ID = u.User_ID " +
                        "WHERE c.First_Name LIKE ? OR c.Last_Name LIKE ? OR c.Phone_Number LIKE ? OR u.Username LIKE ? OR u.Email LIKE ? " +
                        "ORDER BY c.Customer_ID";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            String pattern = "%" + searchTerm + "%";
            pstmt.setString(1, pattern);
            pstmt.setString(2, pattern);
            pstmt.setString(3, pattern);
            pstmt.setString(4, pattern);
            pstmt.setString(5, pattern);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Search Customers Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static ResultSet getCustomerById(int customerId) {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT c.*, u.Username, u.Email FROM Customer c " +
                        "LEFT JOIN Users u ON c.User_ID = u.User_ID " +
                        "WHERE c.Customer_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Get Customer Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static ResultSet searchProducts(String searchTerm) {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT * FROM Product WHERE Model_Name LIKE ? OR Brand LIKE ? OR Description LIKE ? ORDER BY Brand, Model_Name";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            String pattern = "%" + searchTerm + "%";
            pstmt.setString(1, pattern);
            pstmt.setString(2, pattern);
            pstmt.setString(3, pattern);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Search Products Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static ResultSet getProductById(int watchId) {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT * FROM Product WHERE Watch_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, watchId);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Get Product Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static boolean updateProfilePic(String imagePath) {
        String sql = "UPDATE Users SET Profile_Pic = ? WHERE User_ID = ?";
        return executeUpdate(sql, imagePath, currentUserId);
    }

    public static ResultSet getAllShipping() {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT s.*, t.Customer_ID, c.First_Name, c.Last_Name FROM Shipping s " +
                        "LEFT JOIN `Transaction` t ON s.Transaction_ID = t.Transaction_ID " +
                        "LEFT JOIN Customer c ON t.Customer_ID = c.Customer_ID " +
                        "ORDER BY s.Ship_ID DESC";
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Get Shipping Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static ResultSet searchShipping(String searchTerm) {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT s.*, t.Customer_ID, c.First_Name, c.Last_Name FROM Shipping s " +
                        "LEFT JOIN `Transaction` t ON s.Transaction_ID = t.Transaction_ID " +
                        "LEFT JOIN Customer c ON t.Customer_ID = c.Customer_ID " +
                        "WHERE s.Tracking_Number LIKE ? OR s.Shipping_Status LIKE ? OR c.First_Name LIKE ? OR c.Last_Name LIKE ? " +
                        "ORDER BY s.Ship_ID DESC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            String pattern = "%" + searchTerm + "%";
            pstmt.setString(1, pattern);
            pstmt.setString(2, pattern);
            pstmt.setString(3, pattern);
            pstmt.setString(4, pattern);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Search Shipping Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static boolean updateShippingCourier(int shipId, String courierName) {
        String sql = "UPDATE Shipping SET Courier_Name = ? WHERE Ship_ID = ?";
        return executeUpdate(sql, courierName, shipId);
    }

    public static ResultSet getTransactionById(int transactionId) {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT t.*, c.First_Name, c.Last_Name, c.Phone_Number, c.Default_Address FROM `Transaction` t " +
                        "LEFT JOIN Customer c ON t.Customer_ID = c.Customer_ID " +
                        "WHERE t.Transaction_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, transactionId);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Get Transaction Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static ResultSet getCustomerTransactions(int customerId) {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT COUNT(*) as count FROM `Transaction` WHERE Customer_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Get Customer Transactions Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static boolean isUsernameExists(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = connect();
            if (conn == null) return false;
            
            String sql = "SELECT COUNT(*) FROM Users WHERE Username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Check Username Error: " + e.getMessage());
            return false;
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    public static boolean isEmailExists(String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = connect();
            if (conn == null) return false;
            
            String sql = "SELECT COUNT(*) FROM Users WHERE Email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Check Email Error: " + e.getMessage());
            return false;
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    public static int[] getDashboardStats() {
        int[] stats = new int[4];
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = connect();
            if (conn == null) return stats;
            
            stmt = conn.createStatement();
            
            rs = stmt.executeQuery("SELECT COUNT(*) FROM Users");
            if (rs.next()) stats[0] = rs.getInt(1);
            closeResultSet(rs);
            
            rs = stmt.executeQuery("SELECT COUNT(*) FROM Customer");
            if (rs.next()) stats[1] = rs.getInt(1);
            closeResultSet(rs);
            
            rs = stmt.executeQuery("SELECT COUNT(*) FROM Product");
            if (rs.next()) stats[2] = rs.getInt(1);
            closeResultSet(rs);
            
            rs = stmt.executeQuery("SELECT COUNT(*) FROM `Transaction`");
            if (rs.next()) stats[3] = rs.getInt(1);
            
        } catch (SQLException e) {
            System.err.println("Get Stats Error: " + e.getMessage());
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(conn);
        }
        
        return stats;
    }

    public static ResultSet getRecentTransactions(int limit) {
        Connection conn = connect();
        if (conn == null) return null;
        
        try {
            String sql = "SELECT t.*, c.First_Name, c.Last_Name FROM `Transaction` t " +
                        "LEFT JOIN Customer c ON t.Customer_ID = c.Customer_ID " +
                        "ORDER BY t.Order_Date DESC LIMIT ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, limit);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Get Recent Transactions Error: " + e.getMessage());
            closeConnection(conn);
            return null;
        }
    }

    public static boolean executeUpdate(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = connect();
            if (conn == null) return false;
            
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static String getCurrentUserRole() {
        return currentUserRole;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static boolean isAdmin() {
        return "Admin".equals(currentUserRole);
    }
    
    public static boolean updateProductImage(int watchId, String imagePath) {
        String sql = "UPDATE Product SET Image_Path = ? WHERE Watch_ID = ?";
        return executeUpdate(sql, imagePath, watchId);
    }
    public static String getUserProfilePic(int userId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String profilePic = null;
    
    try {
        conn = connect();
        if (conn == null) return null;
        
        String sql = "SELECT Profile_Pic FROM Users WHERE User_ID = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, userId);
        rs = pstmt.executeQuery();
        
        if (rs.next()) {
            profilePic = rs.getString("Profile_Pic");
        }
    } catch (SQLException e) {
        System.err.println("Error getting user profile pic: " + e.getMessage());
    } finally {
        closeResultSet(rs);
        closeStatement(pstmt);
        closeConnection(conn);
    }
    
    return profilePic;
}
    
    public static boolean createShipping(int transactionId, String courierName, String trackingNumber, String address) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    try {
        conn = connect();
        if (conn == null) return false;
        
        String sql = "INSERT INTO Shipping (Transaction_ID, Courier_Name, Tracking_Number, Shipping_Address, Shipping_Status, Estimated_Delivery) " +
                    "VALUES (?, ?, ?, ?, 'In Transit', date('now', '+7 days'))";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, transactionId);
        pstmt.setString(2, courierName);
        pstmt.setString(3, trackingNumber);
        pstmt.setString(4, address);
        
        return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Create Shipping Error: " + e.getMessage());
        return false;
    } finally {
        closeStatement(pstmt);
        closeConnection(conn);
    }
}

public static boolean deleteShipping(int shipId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    try {
        conn = connect();
        if (conn == null) return false;
        
        String sql = "DELETE FROM Shipping WHERE Ship_ID = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, shipId);
        
        return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Delete Shipping Error: " + e.getMessage());
        return false;
    } finally {
        closeStatement(pstmt);
        closeConnection(conn);
    }
}
}