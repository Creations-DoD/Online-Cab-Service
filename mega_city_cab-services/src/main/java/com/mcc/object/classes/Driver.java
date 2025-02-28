package com.mcc.object.classes;


import java.math.BigDecimal;

public class Driver extends User {
    private String driverId;
    private String name;
    private String address;
    private String nic;
    private String phoneNumber;
    private String licensenNumber;
    private boolean availability;
    private BigDecimal rating;

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLicenseNumber() {
        return licensenNumber;
    }

    public void setLicensenNumber(String licensenNumber) {
        this.licensenNumber = licensenNumber;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }
    
    // Method to set availability from a String or Int
    public void setAvailability(String availability) {
        this.availability = "1".equals(availability);
    }
    
    public void setAvailability(int availability) {
        this.availability = availability == 1;
    }
    
}
