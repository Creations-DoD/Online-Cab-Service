package com.cmc.calculator;

import com.mcc.util.DBConn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;


public class CostCalculator {
    
    public static Map<String, Object> calculateTripCost(int pickup, int drop, String vehicleType) {
        Map<String, Object> result = new HashMap<>();
        String distanceQuery = "SELECT city_distance_id, distance_km FROM city_distance "
                + "WHERE (city1_id = ? AND city2_id = ?) OR (city1_id = ? AND city2_id = ?)";
        String priceQuery = "SELECT price FROM vehicle_model WHERE vehicle_type = ?";

        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement distancePStmt = conn.prepareStatement(distanceQuery)) {

            // Get the distance between pickup and drop cities
            Integer cityDistanceId = null;
            Double distance = null;
            distancePStmt.setInt(1, pickup);
            distancePStmt.setInt(2, drop);
            distancePStmt.setInt(3, drop);
            distancePStmt.setInt(4, pickup);
            ResultSet rs = distancePStmt.executeQuery();
            if (rs.next()) {
                cityDistanceId = rs.getInt("city_distance_id");
                distance = rs.getDouble("distance_km");
            }

            // Get the price per kilometer for the selected vehicle type
            Double pricePerKm = null;
            try (PreparedStatement pricePStmt = conn.prepareStatement(priceQuery)) {
                pricePStmt.setString(1, vehicleType);
                rs = pricePStmt.executeQuery();
                if (rs.next()) {
                    pricePerKm = rs.getDouble("price");
                }
            }

            // Calculate the trip cost
            if (cityDistanceId != null && distance != null && pricePerKm != null) {
                Double tripCost = distance * pricePerKm * 2;
                result.put("cityDistanceId", cityDistanceId);
                result.put("tripCost", tripCost);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    
    
    
    
////    public static Double calculateTripCost(int pickup, int drop, String vehicleType) {
////        Double tripCost = null;
////        String distanceQuery = "SELECT distance_km FROM city_distance "
////                + "WHERE (city1_id = ? AND city2_id = ?) OR (city1_id = ? AND city2_id = ?)";
////        String priceQuery = "SELECT price FROM vehicle_model WHERE vehicle_type = ?";
////        try (Connection conn = DBConn.getInstance().getConnection();
////             PreparedStatement distancePStmt = conn.prepareStatement(distanceQuery)) {
////            
////            // Get the distance between pickup and drop cities
////            Double distance = null;
////            distancePStmt.setInt(1, pickup);
////            distancePStmt.setInt(2, drop);
////            distancePStmt.setInt(3, drop);
////            distancePStmt.setInt(4, pickup);
////            ResultSet rs = distancePStmt.executeQuery();
////            if (rs.next()) {
////                distance = rs.getDouble("distance_km");
////            }
////
////            // Step 2: Get the price per kilometer for the selected vehicle type
////            Double pricePerKm = null;
////            try (PreparedStatement pricePStmt = conn.prepareStatement(priceQuery)) {
////                pricePStmt.setString(1, vehicleType);
////                rs = pricePStmt.executeQuery();
////                if (rs.next()) {
////                    pricePerKm = rs.getDouble("price");
////                }
////            }
////
////            // Calculate the trip cost
////            if (distance != null && pricePerKm != null) {
////                tripCost = distance * pricePerKm * 2;
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////        return tripCost;
////    }

}
