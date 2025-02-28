package com.mcc.util;

import com.mcc.object.classes.Driver;
import java.math.BigDecimal;
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
public class DriverUtilTest {
    
    private Driver testDriver;
    
    public DriverUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        // Initialize a test driver object
        testDriver = new Driver();
        
        testDriver.setUsername("testdriver");
        testDriver.setPassword("testdriverpass");
        testDriver.setEmail("testdriver@example.com");
        testDriver.setName("Test Driver");
        testDriver.setAddress("123 Test Driver Street");
        testDriver.setNic("000000000V");
        testDriver.setPhoneNumber("0770000000");
        testDriver.setLicensenNumber("A0000000");   // or AB000000000
        testDriver.setAvailability(true);
        testDriver.setRating(new BigDecimal("0.0"));
    }
    
    @After
    public void tearDown() {
    }

    
    // Test of addDriver method, of class DriverUtil.
    @Test
    public void test_01_AddDriver() {
        System.out.println("Test Insert Driver - addDriver()");
        
        boolean expResult = true;
        boolean result = DriverUtil.addDriver(testDriver);
        assertEquals(expResult, result);
    }
    
    
    // Test of getDriver method, of class DriverUtil.
    @Test
    public void test_02_GetDriver() {
        System.out.println("Test Get Driver - getDriver()");
        
        setDriverID(testDriver.getUsername());
        Driver resultDriver = DriverUtil.getDriver(testDriver.getDriverId());
        
        assertEquals(testDriver.getDriverId(), resultDriver.getDriverId());
        assertEquals(testDriver.getName(), resultDriver.getName());
        assertEquals(testDriver.getAddress(), resultDriver.getAddress());
        assertEquals(testDriver.getNic(), resultDriver.getNic());
        assertEquals(testDriver.getPhoneNumber(), resultDriver.getPhoneNumber());
        assertEquals(testDriver.getLicenseNumber(), resultDriver.getLicenseNumber());
        assertEquals(testDriver.isAvailability(), resultDriver.isAvailability());
        assertEquals(testDriver.getRating(), resultDriver.getRating());
    }
    
    
    // Test of getAllDrivers method, of class DriverUtil.
    @Test
    public void test_03_GetAllDrivers() {
        System.out.println("Test Get All Drivers - getAllDrivers()");
        
        Driver resultDriver = new Driver();
        setDriverID(testDriver.getUsername());
        
        // Retrieve all students
        List<Driver> alldrivers = DriverUtil.getAllDrivers();
        for (Driver st : alldrivers) {
            if (st.getDriverId().equals(testDriver.getDriverId())) {
                resultDriver = st;
            }
        }
        assertEquals(testDriver.getDriverId(), resultDriver.getDriverId());
        assertEquals(testDriver.getName(), resultDriver.getName());
        assertEquals(testDriver.getAddress(), resultDriver.getAddress());
        assertEquals(testDriver.getNic(), resultDriver.getNic());
        assertEquals(testDriver.getPhoneNumber(), resultDriver.getPhoneNumber());
        assertEquals(testDriver.getLicenseNumber(), resultDriver.getLicenseNumber());
        assertEquals(testDriver.isAvailability(), resultDriver.isAvailability());
        assertEquals(testDriver.getRating(), resultDriver.getRating());
    }
    
    
    // Test of isUsernameExists method, of class DriverUtil.
    @Test
    public void test_04_IsUsernameExists() {
        System.out.println("Test User Existence - isUsernameExists()");
        
        boolean expResult = true;
        boolean result = DriverUtil.isUsernameExists(testDriver.getUsername());
        assertEquals(expResult, result);
    }
    

    // Test of updatedriver method, of class DriverUtil.
    @Test
    public void test_05_UpdateDriver() {
        System.out.println("Test Update Driver - updateDriver()");
        
        setDriverID(testDriver.getUsername());
        testDriver.setName("Updated Driver Name");
        testDriver.setAddress("456 Updated Driver Street");
        testDriver.setPhoneNumber("+94771111111");
        
        boolean expResult = true;
        boolean result = DriverUtil.updateDriver(testDriver);
        assertEquals(expResult, result);
        
        // Check each field update properly.
        System.out.println("Check updated Driver");
        Driver checkResultDriver = DriverUtil.getDriver(testDriver.getDriverId());
        
        assertEquals(testDriver.getName(), checkResultDriver.getName());
        assertEquals(testDriver.getAddress(), checkResultDriver.getAddress());
        assertEquals(testDriver.getPhoneNumber(), checkResultDriver.getPhoneNumber());
        assertEquals(testDriver.getLicenseNumber(), checkResultDriver.getLicenseNumber());
        assertEquals(testDriver.isAvailability(), checkResultDriver.isAvailability());
        assertEquals(testDriver.getRating(), checkResultDriver.getRating());
    }

    
    // Test of deleteDriver method, of class DriverUtil.
    @Test
    public void test_06_DeleteDriver() {
        System.out.println("Test Delete Driver - deleteDriver()");
        
        setDriverID(testDriver.getUsername());
        boolean expResult = true;
        boolean result = DriverUtil.deleteDriver(testDriver.getDriverId());
        assertEquals(expResult, result);
    }
    
    
    
    // Set value to testDriver instant's DriverId variable.
    public void setDriverID(String username) {
        String query = "SELECT driver_id FROM driver WHERE user_id = (SELECT user_id FROM user WHERE username = ?)";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                testDriver.setDriverId(rs.getString("driver_id"));
            }
        } catch (SQLException e) {
            System.err.println("Error checking username existence: " + e.getMessage());
        }
    }
    
}
