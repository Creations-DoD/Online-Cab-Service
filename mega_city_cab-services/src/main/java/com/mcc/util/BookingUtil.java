package com.mcc.util;

import com.mcc.object.classes.Booking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookingUtil {
    
    
    // Method to register a booking
        public static boolean addBooking(Booking booking) {
        // Proceed with database insertion
        String query = "INSERT INTO booking (booking_id, customer_id, city_distance_id, booking_date, fare) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Generate Booking_id (e.g., B-A1B2C3)
            if (booking.getBookingId() == null) {
                booking.setBookingId("B-" + generateRandomAlphanumeric(8));
            }
            // Insert into booking table
            pstmt.setString(1, booking.getBookingId());
            pstmt.setString(2, booking.getCustomerId());
            pstmt.setInt(3, booking.getCity_distance_id());
            pstmt.setTimestamp(4, booking.getBooking_date());
            pstmt.setDouble(5, booking.getFare());
            
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error adding booking: " + e.getMessage());
            return false;
        }
    }
//    public static boolean addBooking(Booking booking) {
//        // Proceed with database insertion
//        String query = "INSERT INTO booking (booking_id, customer_id, vehicle_id, driver_id, "
//                + "city_distance_id, booking_date, status, fare, payment_status) VALUES "
//                + "(?, ?, ?, ?, ?, ?, 'Pending', ?, 'Unpaid')";
//
//        try (Connection conn = DBConn.getInstance().getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//
//            // Generate Booking_id (e.g., B-A1B2C3)
//            if (booking.getBookingId() == null) {
//                booking.setBookingId("B-" + generateRandomAlphanumeric(8));
//            }
//            // Insert into booking table
//            pstmt.setString(1, booking.getBookingId());
//            pstmt.setString(2, booking.getCustomerId());
//            pstmt.setString(3, booking.getVehicleId());
//            pstmt.setString(4, booking.getDriverId());
//            pstmt.setInt(5, booking.getCity_distance_id());
//            pstmt.setTimestamp(6, booking.getBooking_date());
//            pstmt.setDouble(7, booking.getFare());
//            
//            int rowsInserted = pstmt.executeUpdate();
//            return rowsInserted > 0;
//        } catch (SQLException e) {
//            System.err.println("Error adding booking: " + e.getMessage());
//            return false;
//        }
//    }
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
    
    
    // Method to get a booking
    public static Booking getBooking(String bookingId) {
        Booking booking = null;
        
        String query = "SELECT * FROM Booking WHERE booking_id = ?";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, bookingId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                booking = new Booking();
                booking.setBookingId(rs.getString("booking_id"));
                booking.setCustomerId(rs.getString("customer_id"));
                booking.setVehicleId(rs.getString("vehicle_id"));
                booking.setDriverId(rs.getString("driver_id"));
                booking.setCity_distance_id(rs.getInt("city_distance_id"));
                booking.setBooking_date(rs.getTimestamp("booking_date"));
                booking.setStatus(rs.getString("status"));
                booking.setFare(rs.getDouble("fare"));
                booking.setPayment_status(rs.getString("payment_status"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return booking;
    }
    
    
    // Method to get a all bookings
    public static List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM Booking";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getString("booking_id"));
                booking.setCustomerId(rs.getString("customer_id"));
                booking.setVehicleId(rs.getString("vehicle_id"));
                booking.setDriverId(rs.getString("driver_id"));
                booking.setCity_distance_id(rs.getInt("city_distance_id"));
                booking.setBooking_date(rs.getTimestamp("booking_date"));
                booking.setStatus(rs.getString("status"));
                booking.setFare(rs.getDouble("fare"));
                booking.setPayment_status(rs.getString("payment_status"));

                bookings.add(booking);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return bookings;
    }

    
    // Method to update booking details
    public static boolean updateBooking(Booking booking) {
        // Proceed with database update
        String query = "UPDATE booking SET vehicle_id = ?, driver_id = ?, status = ?, payment_status = ? "
                + "WHERE booking_id = ?";

        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Uodate into booking table
            pstmt.setString(1, booking.getVehicleId());
            pstmt.setString(2, booking.getDriverId());
            pstmt.setString(3, booking.getStatus());
            pstmt.setString(4, booking.getPayment_status());
            pstmt.setString(5, booking.getBookingId());
            
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0; // Returns true if at least one row was updated
        } catch (SQLException e) {
            System.err.println("Error updating booking: " + e.getMessage());
            return false;
        }
    }

    
    // Method to delete a booking
    public static boolean deleteBooking(String bookingId) {
        String query = "DELETE FROM booking WHERE booking_id = ?";

        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);) {

            pstmt.setString(1, bookingId);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error deleting booking: " + e.getMessage());
            return false;
        }
    }
    
}
