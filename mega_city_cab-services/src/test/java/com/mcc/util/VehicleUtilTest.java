package com.mcc.util;

import com.mcc.object.classes.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VehicleUtilTest {
    private Vehicle testVehicle;
    
    public VehicleUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        // Initialize a test Vehicle object
        testVehicle = new Vehicle();
        
        testVehicle.setModel("Test vehicle model");
        testVehicle.setCapacity(3);
        testVehicle.setAvailability(true);
        testVehicle.setLicensePlate("WP XXX-0000");
    }
    
    @After
    public void tearDown() {
    }

    
    // Test of addVehicle method, of class VehicleUtil.
    @Test
    public void test_01_AddVehicle() {
        System.out.println("Test Insert Vehicle - addVehicle()");
        
        boolean expResult = true;
        boolean result = VehicleUtil.addVehicle(testVehicle);
        assertEquals(expResult, result);
    }
    
    
        
    // Test of getVehicle method, of class VehicleUtil.
    @Test
    public void test_02_GetVehicle() {
        System.out.println("Test Get Vehicle - getVehicle()");
        
        setVehicleID(testVehicle.getLicensePlate());
        Vehicle resultVehicle = VehicleUtil.getVehicle(testVehicle.getVehicleId());
        
        assertEquals(testVehicle.getVehicleId(), resultVehicle.getVehicleId());
        assertEquals(testVehicle.getModel(), resultVehicle.getModel());
        assertEquals(testVehicle.getCapacity(), resultVehicle.getCapacity());
        assertEquals(testVehicle.isAvailability(), resultVehicle.isAvailability());
        assertEquals(testVehicle.getLicensePlate(), resultVehicle.getLicensePlate());
    }
    
    
    // Test of getAllVehicles method, of class VehicleUtil.
    @Test
    public void test_03_GetAllVehicles() {
        System.out.println("Test Get All Vehicles - getAllVehicles()");
        
        Vehicle resultVehicle = new Vehicle();
        setVehicleID(testVehicle.getLicensePlate());
        
        // Retrieve all students
        List<Vehicle> allVehicles = VehicleUtil.getAllVehicles();
        for (Vehicle st : allVehicles) {
            if (st.getVehicleId().equals(testVehicle.getVehicleId())) {
                resultVehicle = st;
            }
        }
        assertEquals(testVehicle.getVehicleId(), resultVehicle.getVehicleId());
        assertEquals(testVehicle.getModel(), resultVehicle.getModel());
        assertEquals(testVehicle.getCapacity(), resultVehicle.getCapacity());
        assertEquals(testVehicle.isAvailability(), resultVehicle.isAvailability());
        assertEquals(testVehicle.getLicensePlate(), resultVehicle.getLicensePlate());
    }
    
    
    // Test of updateVehicle method, of class VehicleUtil.
    @Test
    public void test_04_UpdateVehicle() {
        System.out.println("Test Update Vehicle - updateVehicle()");
        
        setVehicleID(testVehicle.getLicensePlate());
        testVehicle.setModel("Updated Test vehicle model");
        testVehicle.setCapacity(2);
        testVehicle.setAvailability(false);
        testVehicle.setLicensePlate("WP XXX-0000"); // Can't update. Use for get vehicle id.
        
        boolean expResult = true;
        boolean result = VehicleUtil.updateVehicle(testVehicle);
        assertEquals(expResult, result);
        
        // Check each field update properly.
        System.out.println("Check updated Vehicle");
        Vehicle checkResultVehicle = VehicleUtil.getVehicle(testVehicle.getVehicleId());
        
        assertEquals(testVehicle.getVehicleId(), checkResultVehicle.getVehicleId());
        assertEquals(testVehicle.getModel(), checkResultVehicle.getModel());
        assertEquals(testVehicle.getCapacity(), checkResultVehicle.getCapacity());
        assertEquals(testVehicle.isAvailability(), checkResultVehicle.isAvailability());
        assertEquals(testVehicle.getLicensePlate(), checkResultVehicle.getLicensePlate());
    }

    
    // Test of deleteVehicle method, of class VehicleUtil.
    @Test
    public void test_05_DeleteVehicle() {
        System.out.println("Test Delete Vehicle - deleteVehicle()");
        
        setVehicleID(testVehicle.getLicensePlate());
        boolean expResult = true;
        boolean result = VehicleUtil.deleteVehicle(testVehicle.getVehicleId());
        assertEquals(expResult, result);
    }
    
    
    
    // Set value to testVehicle instant's vehicleId variable.
    public void setVehicleID(String licensePlate) {
        String query = "SELECT vehicle_id FROM vehicle WHERE license_plate = ?";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, licensePlate);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                testVehicle.setVehicleId(rs.getString("Vehicle_id"));
            }
        } catch (SQLException e) {
            System.err.println("Error checking username existence: " + e.getMessage());
        }
    }
    
    
}
