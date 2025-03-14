package com.mcc.util;

import com.mcc.object.classes.Driver;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DriverUtil {
    
    // Method to check if a username exists
    public static boolean isUsernameExists(String username) {
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";
        try (Connection conn = DBConn.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) return rs.getInt(1) > 0;
            else return false;
        } catch (SQLException e) {
            System.err.println("Error checking username existence: " + e.getMessage());
        }
        return false;
    }
    // Method to register a customer
    public static boolean addDriver(Driver driver) {
        // Validate input fields
        if (!ValidationUtil.isValidUsername(driver.getUsername())) {
            System.out.println("Invalid username format.");
            return false;
        }
        if (!ValidationUtil.isValidEmail(driver.getEmail())) {
            System.out.println("Invalid email format.");
            return false;
        }
        if (!ValidationUtil.isValidNIC(driver.getNic())) {
            System.out.println("Invalid NIC format.");
            return false;
        }
        if (!ValidationUtil.isValidPhoneNumber(driver.getPhoneNumber())) {
            System.out.println("Invalid phone number format.");
            return false;
        }
        if (!ValidationUtil.isValidDrivingLicense(driver.getLicenseNumber())) {
            System.out.println("Invalid driving license format.");
            return false;
        }

        // Check if username already exists
        if (isUsernameExists(driver.getUsername())) {
            System.out.println("Username already exists.");
            return false;
        }
        
        // Proceed with database insertion
        String userQuery = "INSERT INTO user (username, password, role, email) VALUES (?, ?, 'Driver', ?)";
        String driverQuery = "INSERT INTO driver (driver_id, user_id, name, address, nic, phone_number, license_number) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userQuery, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement driverStmt = conn.prepareStatement(driverQuery)) {

            // Start transaction
            conn.setAutoCommit(false);

            // Insert into user table
            userStmt.setString(1, driver.getUsername());
            userStmt.setString(2, driver.getPassword());
            userStmt.setString(3, driver.getEmail());
            userStmt.executeUpdate();

            // Get the generated user_id
            ResultSet rs = userStmt.getGeneratedKeys();
            int userId = 0;
            if (rs.next()) {
                userId = rs.getInt(1);
            }

            // Generate driver_id (e.g., DRV-A1B2C3)
            String driverId = "DRV-" + generateRandomAlphanumeric(6);

            // Insert into driver table
            driverStmt.setString(1, driverId);
            driverStmt.setInt(2, userId);
            driverStmt.setString(3, driver.getName());
            driverStmt.setString(4, driver.getAddress());
            driverStmt.setString(5, driver.getNic());
            driverStmt.setString(6, driver.getPhoneNumber());
            driverStmt.setString(7, driver.getLicenseNumber());
            driverStmt.executeUpdate();

            // Commit transaction
            conn.commit();
            return true; // Registration successful
        } catch (SQLException e) {
            System.err.println("Error adding driver: " + e.getMessage());
            try (Connection conn = DBConn.getInstance().getConnection()) {
                conn.rollback(); // Rollback transaction in case of error
            } catch (SQLException ex) {
                System.err.println("Error rolling back transaction: " + ex.getMessage());
            }
            return false; // Registration failed
        }
    }
    // Helper method to generate random alphanumeric IDs
    private static String generateRandomAlphanumeric(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
    
    
    // Method to get a customer
    public static Driver getDriver(String driverId) {
        Driver driver = null;
        String query = "SELECT * FROM driver WHERE driver_id = ?";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, driverId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                driver = new Driver();
                driver.setDriverId(rs.getString("driver_id"));
                driver.setName(rs.getString("name"));
                driver.setAddress(rs.getString("address"));
                driver.setNic(rs.getString("nic"));
                driver.setPhoneNumber(rs.getString("phone_number"));
                driver.setLicensenNumber(rs.getString("license_number"));
                driver.setAvailability(rs.getString("availability"));
                driver.setRating(new BigDecimal(rs.getString("rating")));
            }
        } catch (SQLException e) {
            System.err.println("Error checking username existence: " + e.getMessage());
        }
        return driver;
    }
    
    
    // Method to get a all customers
    public static List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM driver";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Driver driver = new Driver();
                driver.setDriverId(rs.getString("driver_id"));
                driver.setName(rs.getString("name"));
                driver.setAddress(rs.getString("address"));
                driver.setNic(rs.getString("nic"));
                driver.setPhoneNumber(rs.getString("phone_number"));
                driver.setLicensenNumber(rs.getString("license_number"));
                driver.setAvailability(rs.getString("availability"));
                driver.setRating(new BigDecimal(rs.getString("rating")));

                drivers.add(driver);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return drivers;
    }

    
    // Method to update customer details
    public static boolean updateDriver(Driver driver) {
        // Validate input fields
        if (!ValidationUtil.isValidPhoneNumber(driver.getPhoneNumber())) {
            System.out.println("Invalid phone number format.");
            return false;
        }
        if (!ValidationUtil.isValidDrivingLicense(driver.getLicenseNumber())) {
            System.out.println("Invalid driving license format.");
            return false;
        }

        // Proceed with database update
        String query = "UPDATE driver SET name = ?, address = ?, phone_number = ?, license_number = ?, "
                + "availability = ? WHERE driver_id = ?";

        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, driver.getName());
            pstmt.setString(2, driver.getAddress());
            pstmt.setString(3, driver.getPhoneNumber());
            pstmt.setString(4, driver.getLicenseNumber());
            pstmt.setBoolean(5, driver.isAvailability());
            pstmt.setString(6, driver.getDriverId());
            int rowsUpdated = pstmt.executeUpdate();

            return rowsUpdated > 0; // Returns true if at least one row was updated
        } catch (SQLException e) {
            System.err.println("Error updating driver: " + e.getMessage());
            return false;
        }
    }

    
    // Method to delete a customer
    public static boolean deleteDriver(String driverId) {
        String getUserQuery = "SELECT user_id FROM driver WHERE driver_id = ?";
        String deleteDriverQuery = "DELETE FROM driver WHERE driver_id = ?";
        String deleteUserQuery = "DELETE FROM user WHERE user_id = ?";

        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement getUserStmt = conn.prepareStatement(getUserQuery);
             PreparedStatement deleteDriverStmt = conn.prepareStatement(deleteDriverQuery);
             PreparedStatement deleteUserStmt = conn.prepareStatement(deleteUserQuery)) {

            // Start transaction
            conn.setAutoCommit(false);

            // Step 1: Get the user_id associated with the driver_id
            getUserStmt.setString(1, driverId);
            ResultSet rs = getUserStmt.executeQuery();
            if (!rs.next()) {
                // No driver found with the given driver_id
                return false;
            }
            int userId = rs.getInt("user_id");

            // Step 2: Delete from driver table
            deleteDriverStmt.setString(1, driverId);
            int driverRowsDeleted = deleteDriverStmt.executeUpdate();

            // Step 3: Delete from user table
            deleteUserStmt.setInt(1, userId);
            int userRowsDeleted = deleteUserStmt.executeUpdate();

            // Commit transaction if both deletions are successful
            if (driverRowsDeleted > 0 && userRowsDeleted > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback(); // Rollback if any deletion fails
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting driver: " + e.getMessage());
            return false;
        }
    }

    
    // Method to get a available all drivers
    public static List<String> getAllAvailableDrivers() {
        List<String> driver = new ArrayList<>();
        String query = "SELECT driver_id FROM driver WHERE availability = 1";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                driver.add(rs.getString("driver_id"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return driver;
    }
}



//            driver.setAvailability(rs.getInt("availability")); // Convert TINYINT(1) to boolean
//            driver.setAvailability(rs.getString("availability")); // Convert TINYINT(1) to boolean
//            driverStmt.setInt(X, driver.isAvailability() ? 1 : 0); // Convert boolean to TINYINT(1)
