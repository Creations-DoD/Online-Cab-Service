package com.mcc.util;

import com.mcc.object.classes.Customer;

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
public class CustomerUtilTest {
    
    private Customer testCustomer;
    
    public CustomerUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        // Initialize a test customer object
        testCustomer = new Customer();
        testCustomer.setUsername("testcustomer");
        testCustomer.setPassword("testcustomerpass");
        testCustomer.setEmail("testcustomer@example.com");
        testCustomer.setName("Test Customer");
        testCustomer.setAddress("123 Test Customer Street");
        testCustomer.setNic("000000000V");
        testCustomer.setPhoneNumber("0770000000");
    }
    
    @After
    public void tearDown() {
    }

    
    // Test of addCustomer method, of class CustomerUtil.
    @Test
    public void test_01_AddCustomer() {
        System.out.println("Test Insert Customer - addCustomer()");
        
        boolean expResult = true;
        boolean result = CustomerUtil.addCustomer(testCustomer);
        assertEquals(expResult, result);
    }
    
    
    // Test of getCustomer method, of class CustomerUtil.
    @Test
    public void test_02_GetCustomer() {
        System.out.println("Test Get Customer - getCustomer()");
        
        setCustomerID(testCustomer.getUsername());
        Customer resultCustomer = CustomerUtil.getCustomer(testCustomer.getCustomerId());
        
        assertEquals(testCustomer.getCustomerId(), resultCustomer.getCustomerId());
        assertEquals(testCustomer.getName(), resultCustomer.getName());
        assertEquals(testCustomer.getAddress(), resultCustomer.getAddress());
        assertEquals(testCustomer.getNic(), resultCustomer.getNic());
        assertEquals(testCustomer.getPhoneNumber(), resultCustomer.getPhoneNumber());
    }
    
    
    // Test of getAllCustomers method, of class CustomerUtil.
    @Test
    public void test_03_GetAllCustomers() {
        System.out.println("Test Get All Customers - getAllCustomers()");
        
        Customer resultCustomer = new Customer();
        setCustomerID(testCustomer.getUsername());
        
        // Retrieve all students
        List<Customer> allcustomers = CustomerUtil.getAllCustomers();
        for (Customer st : allcustomers) {
            if (st.getCustomerId().equals(testCustomer.getCustomerId())) {
                resultCustomer = st;
            }
        }
        assertEquals(testCustomer.getCustomerId(), resultCustomer.getCustomerId());
        assertEquals(testCustomer.getName(), resultCustomer.getName());
        assertEquals(testCustomer.getAddress(), resultCustomer.getAddress());
        assertEquals(testCustomer.getNic(), resultCustomer.getNic());
        assertEquals(testCustomer.getPhoneNumber(), resultCustomer.getPhoneNumber());
    }
    
    
    // Test of isUsernameExists method, of class CustomerUtil.
    @Test
    public void test_04_IsUsernameExists() {
        System.out.println("Test User Existence - isUsernameExists()");
        
        boolean expResult = true;
        boolean result = CustomerUtil.isUsernameExists(testCustomer.getUsername());
        assertEquals(expResult, result);
    }
    
    
    // Test of validateUser method, of class CustomerUtil.
    @Test
    public void test_05_ValidateUser() {
        System.out.println("Test Login - validateUser()");
        
        String invalidUsername = "testInvalidUser";
        String invalidPassword = "testInvalidPass";
        String expResult = "Customer";
        
        // Test valid login
        String result = CustomerUtil.validateUser(testCustomer.getUsername(), testCustomer.getPassword());
        assertEquals(expResult, result);

        // Test invalid login username
        result = CustomerUtil.validateUser(invalidUsername, testCustomer.getPassword());
        assertNull(result);
        
        // Test invalid login password
        result = CustomerUtil.validateUser(testCustomer.getUsername(), invalidPassword);
        assertNull(result);
    }
    

    // Test of updateCustomer method, of class CustomerUtil.
    @Test
    public void test_06_UpdateCustomer() {
        System.out.println("Test Update Customer - updateCustomer()");
        
        setCustomerID(testCustomer.getUsername());
        testCustomer.setName("Updated Customer Name");
        testCustomer.setAddress("456 Updated Customer Street");
        testCustomer.setPhoneNumber("+94777654321");
        
        boolean expResult = true;
        boolean result = CustomerUtil.updateCustomer(testCustomer);
        assertEquals(expResult, result);
        
        // Check each field update properly.
        System.out.println("Check updated customer");
        Customer checkResultCustomer = CustomerUtil.getCustomer(testCustomer.getCustomerId());
        assertEquals(testCustomer.getName(), checkResultCustomer.getName());
        assertEquals(testCustomer.getAddress(), checkResultCustomer.getAddress());
        assertEquals(testCustomer.getPhoneNumber(), checkResultCustomer.getPhoneNumber());
    }

    
    // Test of deleteCustomer method, of class CustomerUtil.
    @Test
    public void test_07_DeleteCustomer() {
        System.out.println("Test Delete Customer - deleteCustomer()");
        
        setCustomerID(testCustomer.getUsername());
        boolean expResult = true;
        boolean result = CustomerUtil.deleteCustomer(testCustomer.getCustomerId());
        assertEquals(expResult, result);
    }
    
    
    
    // Set value to testCustomer instant's CustomerId variable.
    public void setCustomerID(String username) {
        String query = "SELECT customer_id FROM customer WHERE user_id = (SELECT user_id FROM user WHERE username = ?)";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                testCustomer.setCustomerId(rs.getString("customer_id"));
            }
        } catch (SQLException e) {
            System.err.println("Error checking username existence: " + e.getMessage());
        }
    }
    
}
