package com.cmc.calculator;

import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CostCalculatorTest {
    
    public CostCalculatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // Test of properly calculate trip cost method, of class CostCalculator.
    @Test
    public void testCalculateTripCost() {
        System.out.println("Test calculate Trip Cost - calculateTripCost()");

        int pickup = 1;
        int drop = 2;
        String vehicleType = "Budget";
        Double expectedTripCost = 16240.00; // Expected trip cost
        int expectedCityDistanceId = 1; // Example expected city_distance_id

        Map<String, Object> result = CostCalculator.calculateTripCost(pickup, drop, vehicleType);

        // Check if result is not empty
        assertNotNull(result);
        assertTrue(result.containsKey("tripCost"));
        assertTrue(result.containsKey("cityDistanceId"));

        // Verify the trip cost and city distance ID
        assertEquals(expectedTripCost, (Double) result.get("tripCost"));
        assertEquals(expectedCityDistanceId, result.get("cityDistanceId"));

//        System.out.println("Trip Cost: " + result.get("tripCost"));
//        System.out.println("City Distance ID: " + result.get("cityDistanceId"));
    }



////    @Test
////    public void testCalculateTripCost() {
////        System.out.println("Test calculate Trip Cost - calculateTripCost()");
////        int pickup = 1;
////        int drop = 2;
////        String vehicleType = "Budget";
////        Double expResult = 16240.00;
////        Double result = CostCalculator.calculateTripCost(pickup, drop, vehicleType);
////        assertEquals(expResult, result);
////        System.out.print(result);
////    }
    
}
