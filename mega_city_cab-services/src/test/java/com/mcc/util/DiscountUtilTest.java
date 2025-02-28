package com.mcc.util;

import com.mcc.object.classes.Discount;
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
public class DiscountUtilTest {
    private Discount testDiscount;

    public DiscountUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        // Initialize a test Discount object
        testDiscount = new Discount();
        testDiscount.setCode("TEST10");
        testDiscount.setDescription("This is test discount description.");
        testDiscount.setValid_from(java.sql.Date.valueOf("2024-10-01"));
        testDiscount.setValid_to(java.sql.Date.valueOf("2024-10-31"));
        testDiscount.setDiscount_percentage(10.0);
    }

    @After
    public void tearDown() {
    }

    // Test of addDiscount method, of class DiscountUtil.
    @Test
    public void test_01_AddDiscount() {
        System.out.println("Test Insert Discount - addDiscount()");

        boolean expResult = true;
        boolean result = DiscountUtil.addDiscount(testDiscount);
        assertEquals(expResult, result);
    }

    // Test of getDiscount method, of class DiscountUtil.
    @Test
    public void test_02_GetDiscount() {
        System.out.println("Test Get Discount - getDiscount()");

        // Retrieve the discount using its code
        Discount resultDiscount = DiscountUtil.getDiscount(testDiscount.getCode());

        assertNotNull(resultDiscount);
        assertEquals(testDiscount.getCode(), resultDiscount.getCode());
        assertEquals(testDiscount.getDescription(), resultDiscount.getDescription());
        assertEquals(testDiscount.getValid_from(), resultDiscount.getValid_from());
        assertEquals(testDiscount.getValid_to(), resultDiscount.getValid_to());
        assertEquals(testDiscount.getDiscount_percentage(), resultDiscount.getDiscount_percentage(), 0.001);
        
        
        // Retrieve the discount using its discount id
//////////        resultDiscount = DiscountUtil.getDiscount(testDiscount.getDiscountId());

    }

    // Test of getAllDiscounts method, of class DiscountUtil.
    @Test
    public void test_03_GetAllDiscounts() {
        System.out.println("Test Get All Discounts - getAllDiscounts()");

        // Retrieve all discounts
        List<Discount> allDiscounts = DiscountUtil.getAllDiscounts();
        assertFalse(allDiscounts.isEmpty());

        // Check if the test discount exists in the list
        boolean found = false;
        for (Discount discount : allDiscounts) {
            if (discount.getCode().equals(testDiscount.getCode())) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    // Test of updateDiscount method, of class DiscountUtil.
    @Test
    public void test_04_UpdateDiscount() {
        System.out.println("Test Update Discount - updateDiscount()");

        // Update the test discount
        testDiscount.setDescription("This is updated test discount description.");
        testDiscount.setValid_from(java.sql.Date.valueOf("2025-01-01"));
        testDiscount.setValid_to(java.sql.Date.valueOf("2025-02-28"));

        // Update the discount using its code
        boolean expResult = true;
        boolean result = DiscountUtil.updateDiscount(testDiscount);
        assertEquals(expResult, result);

        // Verify the update using its code
        Discount checkUpdDisResult = DiscountUtil.getDiscount(testDiscount.getCode());
        assertNotNull(checkUpdDisResult);
        assertEquals(testDiscount.getDescription(), checkUpdDisResult.getDescription());
        assertEquals(testDiscount.getValid_from(), checkUpdDisResult.getValid_from());
        assertEquals(testDiscount.getValid_to(), checkUpdDisResult.getValid_to());
    }

    // Test of deleteDiscount method, of class DiscountUtil.
    @Test
    public void test_05_DeleteDiscount() {
        System.out.println("Test Delete Discount - deleteDiscount()");

        boolean expResult = true;
        boolean result = DiscountUtil.deleteDiscount(testDiscount.getCode());
        assertEquals(expResult, result);

        // Verify the discount is deleted
        Discount deletedDiscount = DiscountUtil.getDiscount(testDiscount.getCode());
        assertNull(deletedDiscount);
    }

    // Helper method to set discount_id for the testDiscount object
    private void setDiscountId(String code) {
        String query = "SELECT discount_id FROM discount WHERE code = ?";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                testDiscount.setDiscountId(rs.getString("discount_id"));
            }
        } catch (SQLException e) {
            System.err.println("Error setting discount_id: " + e.getMessage());
        }
    }
}