package com.mcc.util;

import com.mcc.object.classes.Discount;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DiscountUtil {
    
    
    // Method to generate the next discount_id
    public static String generateNextDiscountId() {
        String query = "SELECT MAX(CAST(SUBSTRING(discount_id, 5) AS UNSIGNED)) FROM discount";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int lastId = rs.getInt(1);
                int nextId = lastId + 1;
                return "DSC-" + String.format("%03d", nextId); // Format as DSC-001, DSC-002, etc.
            } else {
                return "DSC-001"; // If no discounts exist, start with DSC-001
            }
        } catch (SQLException e) {
            System.err.println("Error generating discount ID: " + e.getMessage());
            return null;
        }
    }
    // Method to add a discount
    public static boolean addDiscount(Discount discount) {
        // check if a discount code already exists
        if (ValidationUtil.isDiscountCodeExists(discount.getCode())){
            System.out.println("Discount code already exists.");
            return false;
        }
        
        // Generate the next discount_id
        String discountId = generateNextDiscountId();
        if (discountId == null) {
            System.out.println("Discount id Failed to generate.");
            return false;
        }
        discount.setDiscountId(discountId);
        
        // Proceed with database insertion
        String query = "INSERT INTO discount (discount_id, code, description, valid_from, valid_to, discount_percentage) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, discount.getDiscountId());
            pstmt.setString(2, discount.getCode());
            pstmt.setString(3, discount.getDescription());
            pstmt.setDate(4, discount.getValid_from());
            pstmt.setDate(5, discount.getValid_to());
            pstmt.setDouble(6, discount.getDiscount_percentage());
            
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error adding discount: " + e.getMessage());
            return false;
        }
    }
    
    
    
    // Method to get a discount
    public static Discount getDiscount(String serachValue) {
        Discount discount = null;
        String query = "SELECT * FROM discount WHERE discount_id = ? OR code = ?";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, serachValue);
            pstmt.setString(2, serachValue);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                discount = new Discount();
                discount.setDiscountId(rs.getString("discount_id"));
                discount.setCode(rs.getString("code"));
                discount.setDescription(rs.getString("description"));
                discount.setValid_from(rs.getDate("valid_from"));
                discount.setValid_to(rs.getDate("valid_to"));
                discount.setDiscount_percentage(rs.getDouble("discount_percentage"));
                return discount;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving discount: " + e.getMessage());
        }
        return discount;
    }
    
    
    // Method to get a all discounts
    public static List<Discount> getAllDiscounts() {
        List<Discount> discounts = new ArrayList<>();
        String query = "SELECT * FROM discount";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Discount discount = new Discount();
                discount.setDiscountId(rs.getString("discount_id"));
                discount.setCode(rs.getString("code"));
                discount.setDescription(rs.getString("description"));
                discount.setValid_from(rs.getDate("valid_from"));
                discount.setValid_to(rs.getDate("valid_to"));
                discount.setDiscount_percentage(rs.getDouble("discount_percentage"));
                
                discounts.add(discount);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all discounts: " + e.getMessage());
        }
        return discounts;
    }
    
    
    // Method to update discount details
    public static boolean updateDiscount(Discount discount) {
        String query = "UPDATE discount SET description = ?, valid_from = ?, valid_to = ? WHERE discount_id = ? OR code = ?";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, discount.getDescription());
            pstmt.setDate(2, discount.getValid_from());
            pstmt.setDate(3, discount.getValid_to());
            pstmt.setString(4, discount.getDiscountId());
            pstmt.setString(5, discount.getCode());
            
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating discount: " + e.getMessage());
            return false;
        }
    }
    
    
    // Method to delete a discount
    public static boolean deleteDiscount(String serachValue) {
        String query = "DELETE FROM discount WHERE discount_id = ? OR code = ?";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, serachValue);
            pstmt.setString(2, serachValue);
            
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting discount: " + e.getMessage());
            return false;
        }
    }
    
    
}
