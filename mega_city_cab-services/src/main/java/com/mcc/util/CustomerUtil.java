package com.mcc.util;

import com.mcc.object.classes.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomerUtil {
    
    // Method to validate user login
    public static Map<String, String> validateUser(String usernameOrEmail, String password) {
//        String query = "SELECT role FROM user WHERE (username = ? OR email = ?) AND password = ?";
        String query = "SELECT u.role AS 'role', u.username As 'username', c.customer_id As 'cus_id', d.driver_id As 'drv_id' "
                + "FROM user u LEFT JOIN customer c ON u.user_id = c.user_id LEFT JOIN driver d ON u.user_id = d.user_id "
                + "WHERE (u.username = ? OR u.email = ?) AND u.password = ?";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, usernameOrEmail);
            pstmt.setString(2, usernameOrEmail);
            pstmt.setString(3, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Map<String, String> userDetails = new HashMap<>();
                userDetails.put("role", rs.getString("role"));
                userDetails.put("username", rs.getString("username"));

                if (rs.getString("role").equals("Admin")) {
                    userDetails.put("userID", "Admin");
                } else if (rs.getString("role").equals("Customer")) {
                    userDetails.put("userID", rs.getString("cus_id"));
                } else if (rs.getString("role").equals("Driver")) {
                    userDetails.put("userID", rs.getString("drv_id"));
                }
                return userDetails; // Return the user's Details HashMap
            }
            return null; // No matching user found
        } catch (SQLException e) {
            System.err.println("Error validating user: " + e.getMessage());
            e.printStackTrace(); // Log the stack trace for debugging
            return null;
        }
    }
    
    
    // Method to check if a username exists
    public static boolean isUsernameExists(String username) {
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if username exists
            }
        } catch (SQLException e) {
            System.err.println("Error checking username existence: " + e.getMessage());
        }
        return false;
    }  
    // Method to register a customer
    public static boolean addCustomer(Customer customer) {
        // Validate input fields
        if (!ValidationUtil.isValidUsername(customer.getUsername())) {
            System.out.println("Invalid username format.");
            return false;
        }
        if (!ValidationUtil.isValidEmail(customer.getEmail())) {
            System.out.println("Invalid email format.");
            return false;
        }
        if (!ValidationUtil.isValidNIC(customer.getNic())) {
            System.out.println("Invalid NIC format.");
            return false;
        }
        if (!ValidationUtil.isValidPhoneNumber(customer.getPhoneNumber())) {
            System.out.println("Invalid phone number format.");
            return false;
        }

        // Check if username already exists
        if (isUsernameExists(customer.getUsername())) {
            System.out.println("Username already exists.");
            return false;
        }

        // Proceed with database insertion
        String userQuery = "INSERT INTO user (username, password, role, email) VALUES (?, ?, 'Customer', ?)";
        String customerQuery = "INSERT INTO customer (customer_id, user_id, name, address, nic, phone_number) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userQuery, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement customerStmt = conn.prepareStatement(customerQuery)) {

            // Start transaction
            conn.setAutoCommit(false);

            // Insert into user table
            userStmt.setString(1, customer.getUsername());
            userStmt.setString(2, customer.getPassword());
            userStmt.setString(3, customer.getEmail());
            userStmt.executeUpdate();

            // Get the generated user_id
            ResultSet rs = userStmt.getGeneratedKeys();
            int userId = 0;
            if (rs.next()) {
                userId = rs.getInt(1);
            }

            // Generate customer_id (e.g., CUS-A1B2C3)
            customer.setCustomerId("CUS-" + generateRandomAlphanumeric(6));

            // Insert into customer table
            customerStmt.setString(1, customer.getCustomerId());
            customerStmt.setInt(2, userId);
            customerStmt.setString(3, customer.getName());
            customerStmt.setString(4, customer.getAddress());
            customerStmt.setString(5, customer.getNic());
            customerStmt.setString(6, customer.getPhoneNumber());
            customerStmt.executeUpdate();

            // Commit transaction
            conn.commit();
            return true; // Registration successful
        } catch (SQLException e) {
            System.err.println("Error adding customer: " + e.getMessage());
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
    public static Customer getCustomer(String customerId) {
        Customer customer = null;
        String query = "SELECT * FROM customer WHERE customer_id = ?";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                customer = new Customer();
                customer.setCustomerId(rs.getString("customer_id"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setNic(rs.getString("nic"));
                customer.setPhoneNumber(rs.getString("phone_number"));
            }
        } catch (SQLException e) {
            System.err.println("Error checking username existence: " + e.getMessage());
        }
        return customer;
    }
    
    
    // Method to get a all customer
    public static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customer";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getString("customer_id"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setNic(rs.getString("nic"));
                customer.setPhoneNumber(rs.getString("phone_number"));

                customers.add(customer);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return customers;
    }

    
    // Method to update customer details
    public static boolean updateCustomer(Customer customer) {
        // Validate phone number
        if (!ValidationUtil.isValidPhoneNumber(customer.getPhoneNumber())) {
            System.out.println("Invalid phone number format.");
            return false;
        }

        // Proceed with database update
        String query = "UPDATE customer SET name = ?, address = ?, phone_number = ? WHERE customer_id = ?";

        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getPhoneNumber());
            pstmt.setString(4, customer.getCustomerId());
            int rowsUpdated = pstmt.executeUpdate();

            return rowsUpdated > 0; // Returns true if at least one row was updated
        } catch (SQLException e) {
            System.err.println("Error updating customer: " + e.getMessage());
            return false;
        }
    }

    
    // Method to delete a customer
    public static boolean deleteCustomer(String customerId) {
        String getUserQuery = "SELECT user_id FROM customer WHERE customer_id = ?";
        String deleteCustomerQuery = "DELETE FROM customer WHERE customer_id = ?";
        String deleteUserQuery = "DELETE FROM user WHERE user_id = ?";

        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement getUserStmt = conn.prepareStatement(getUserQuery);
             PreparedStatement deleteCustomerStmt = conn.prepareStatement(deleteCustomerQuery);
             PreparedStatement deleteUserStmt = conn.prepareStatement(deleteUserQuery)) {

            // Start transaction
            conn.setAutoCommit(false);

            // Step 1: Get the user_id associated with the customer_id
            getUserStmt.setString(1, customerId);
            ResultSet rs = getUserStmt.executeQuery();
            if (!rs.next()) {
                // No customer found with the given customer_id
                return false;
            }
            int userId = rs.getInt("user_id");

            // Step 2: Delete from customer table
            deleteCustomerStmt.setString(1, customerId);
            int customerRowsDeleted = deleteCustomerStmt.executeUpdate();

            // Step 3: Delete from user table
            deleteUserStmt.setInt(1, userId);
            int userRowsDeleted = deleteUserStmt.executeUpdate();

            // Commit transaction if both deletions are successful
            if (customerRowsDeleted > 0 && userRowsDeleted > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback(); // Rollback if any deletion fails
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
            return false;
        }
    }

    
}
