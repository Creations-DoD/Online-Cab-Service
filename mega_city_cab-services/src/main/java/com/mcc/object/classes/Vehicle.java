package com.mcc.object.classes;


public class Vehicle {
    private String vehicleId;
    private int modelId;
    private boolean availability;
    private String licensePlate;
    private String vehicleType;
    private String model;
    private String condition;
    private int capacity;
    private String baggagers;
    private Double price;
    

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
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

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getBaggagers() {
        return baggagers;
    }

    public void setBaggagers(String baggagers) {
        this.baggagers = baggagers;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    
    // Method to set availability from a String or Int
    public void setAvailability(String availability) {
        this.availability = "1".equals(availability);
    }
    
    public void setAvailability(int availability) {
        this.availability = availability == 1;
    }
}
