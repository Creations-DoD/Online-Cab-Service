package com.mcc.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ValidationUtil {

    // Validate username
    public static boolean isValidUsername(String username) {
        return username != null && username.matches("^[a-zA-Z0-9_]{5,20}$");
    }

    // Validate email
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    // Validate NIC (Sri Lankan format)
    public static boolean isValidNIC(String nic) {
        if (nic == null) return false;
        // Old NIC: 9 digits + 'V' or 'X'
        if (nic.matches("^\\d{9}[VX]$")) return true;
        // New NIC: 12 digits
        if (nic.matches("^\\d{12}$")) return true;
        return false;
    }

    // Validate phone number (Sri Lankan format)
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^(0|\\+94)\\d{9}$");
    }
    
    // Validate driving license number (Sri Lankan format)
    public static boolean isValidDrivingLicense(String license) {
        if (license == null) return false;

        // Old format: 1 letter + 7 digits
        if (license.matches("^[A-Z]\\d{7}$")) return true;

        // New format: 2 letters + 9 digits
        if (license.matches("^[A-Z]{2}\\d{9}$")) return true;

        return false;
    }
    
    // Validate vehicle number number (Sri Lankan format)
    public static boolean isValidVehicleLicensePlate(String vehicleNumber) {
        if (vehicleNumber == null) return false;

        // Province prefixes
        String provincePrefixes = "WP|CP|NC|SP|NW|SG|EP|UP|NP";

        // Regex for vehicle numbers starting with province prefix
        String regexWithProvince = "^(" + provincePrefixes + ")[- ]([A-Z]{2,3}|\\d{1,3})[- ]\\d{4}$";

        // Regex for vehicle numbers starting with numbers (no province prefix)
        String regexWithoutProvince = "^\\d{1,3}[- ]?\\d{4}$";

        // Check if the vehicle number matches either format
        return vehicleNumber.matches(regexWithProvince) || vehicleNumber.matches(regexWithoutProvince);
    }
    
    // Method to check if a discount code already exists
    public static boolean isDiscountCodeExists(String code) {
        String query = "SELECT COUNT(*) FROM discount WHERE code = ?";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, code);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if code exists
            }
        } catch (SQLException e) {
            System.err.println("Error checking discount code existence: " + e.getMessage());
        }
        return false;
    }
    
}
