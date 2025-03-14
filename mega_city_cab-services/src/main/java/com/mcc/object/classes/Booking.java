package com.mcc.object.classes;

import java.sql.Timestamp;


public class Booking {
    private String bookingId;
    private String customerId;
    private String vehicleId;
    private String driverId;
    private int city_distance_id;
    private Timestamp booking_date;
    private String status;
    private Double fare;
    private String payment_status;

    public Booking() {
        this.bookingId = null;
        this.customerId = null;
        this.vehicleId = null;
        this.driverId = null;
        this.city_distance_id = 0;
        this.booking_date = null;
        this.status = null;
        this.fare = 0.00;
        this.payment_status = null;
    }
    
    public Booking(String bookingId, String customerId, String vehicleId, String driverId, int city_distance_id, Timestamp booking_date, String status, Double fare, String payment_status) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.city_distance_id = city_distance_id;
        this.booking_date = booking_date;
        this.status = status;
        this.fare = fare;
        this.payment_status = payment_status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public int getCity_distance_id() {
        return city_distance_id;
    }

    public void setCity_distance_id(int city_distance_id) {
        this.city_distance_id = city_distance_id;
    }

    public Timestamp getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(Timestamp booking_date) {
        this.booking_date = booking_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }
    
    
}
