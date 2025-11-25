package helper_classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:gateo_users.db";
    
    static {
        initializeDatabase();
    }

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT UNIQUE NOT NULL, " +
                    "email TEXT UNIQUE NOT NULL, " +
                    "password TEXT NOT NULL, " +
                    "created_at DATETIME DEFAULT CURRENT_TIMESTAMP)";
            
            stmt.execute(createTableSQL);
            System.out.println("Database initialized successfully!");
            
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    public static boolean registerUser(String username, String email, String password) {
        String sql = "INSERT INTO users(username, email, password) VALUES(?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password); // In production, hash this password!
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    public static boolean validateUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, password); // In production, compare hashed passwords!
            
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            System.err.println("Error validating user: " + e.getMessage());
            return false;
        }
    }

    public static boolean emailExists(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            System.err.println("Error checking email: " + e.getMessage());
            return false;
        }
    }

    public static boolean usernameExists(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
            return false;
        }
    }
}