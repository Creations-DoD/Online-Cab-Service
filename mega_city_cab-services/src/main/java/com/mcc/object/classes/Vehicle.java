package com.mcc.object.classes;


public class Vehicle {
    private String vehicleId;
    private String model;
    private int capacity;
    private boolean availability;
    private String licensePlate;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    
    // Method to set availability from a String or Int
    public void setAvailability(String availability) {
        this.availability = "1".equals(availability);
    }
    
    public void setAvailability(int availability) {
        this.availability = availability == 1;
    }
}
