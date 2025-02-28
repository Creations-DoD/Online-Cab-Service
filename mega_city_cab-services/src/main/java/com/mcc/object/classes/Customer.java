package com.mcc.object.classes;


public class Customer extends User {
    private String customerId;
    private String name;
    private String address;
    private String nic;
    private String phoneNumber;
    
    // Getters and setters
    public String getCustomerId() { 
        return customerId; 
    }

    public void setCustomerId(String CustomerId) {
        this.customerId = CustomerId;
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

}
