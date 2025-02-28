package com.mcc.util;

import com.mcc.object.classes.Vehicle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VehicleUtil {
    
    // Method to register a vehicle
    public static boolean addVehicle(Vehicle vehicle) {
        // Validate input fields
        if (!ValidationUtil.isValidVehicleLicensePlate(vehicle.getLicensePlate())) {
            System.out.println("Invalid vehicle license plate format.");
            return false;
        }
        
        // Proceed with database insertion
        String query = "INSERT INTO vehicle (vehicle_id, model, capacity, license_plate) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {


            // Generate Vehicle_id (e.g., VEH-A1B2C3)
            String VehicleId = "VEH-" + generateRandomAlphanumeric(6);

            // Insert into vehicle table
            pstmt.setString(1, VehicleId);
            pstmt.setString(2, vehicle.getModel());
            pstmt.setInt(3, vehicle.getCapacity());
            pstmt.setString(4, vehicle.getLicensePlate());
            
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
            return false;
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
    
    
    // Method to get a vehicle
    public static Vehicle getVehicle(String vehicleId) {
        Vehicle vehicle = null;
        String query = "SELECT * FROM vehicle WHERE vehicle_id = ?";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, vehicleId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                vehicle = new Vehicle();
                vehicle.setVehicleId(rs.getString("vehicle_id"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setCapacity(rs.getInt("capacity"));
                vehicle.setAvailability(rs.getBoolean("availability"));
                vehicle.setLicensePlate(rs.getString("license_plate"));
            }
        } catch (SQLException e) {
            System.err.println("Error checking vehicle existence: " + e.getMessage());
        }
        return vehicle;
    }
    
    
    // Method to get a all vehicles
    public static List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM Vehicle";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleId(rs.getString("vehicle_id"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setCapacity(rs.getInt("capacity"));
                vehicle.setAvailability(rs.getInt("availability"));
                vehicle.setLicensePlate(rs.getString("license_plate"));

                vehicles.add(vehicle);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return vehicles;
    }

    
    // Method to update vehicle details
    public static boolean updateVehicle(Vehicle vehicle) {
        // Validate input fields
        if (!ValidationUtil.isValidVehicleLicensePlate(vehicle.getLicensePlate())) {
            System.out.println("Invalid vehicle license plate format.");
            return false;
        }

        // Proceed with database update
        String query = "UPDATE vehicle SET model = ?, capacity = ?, availability = ?, license_plate = ? WHERE vehicle_id = ?";

        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, vehicle.getModel());
            pstmt.setInt(2, vehicle.getCapacity());
            pstmt.setInt(3, vehicle.isAvailability() ? 1 : 0);
//            pstmt.setBoolean(3, vehicle.isAvailability());
            pstmt.setString(4, vehicle.getLicensePlate());
            pstmt.setString(5, vehicle.getVehicleId());
            int rowsUpdated = pstmt.executeUpdate();

            return rowsUpdated > 0; // Returns true if at least one row was updated
        } catch (SQLException e) {
            System.err.println("Error updating vehicle: " + e.getMessage());
            return false;
        }
    }

    
    // Method to delete a vehicle
    public static boolean deleteVehicle(String vehicleId) {
        String query = "DELETE FROM vehicle WHERE vehicle_id = ?";

        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);) {

            pstmt.setString(1, vehicleId);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error deleting vehicle: " + e.getMessage());
            return false;
        }
    }
    
}
