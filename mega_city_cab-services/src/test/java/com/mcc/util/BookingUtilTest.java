package com.mcc.util;

import com.mcc.object.classes.Booking;

import java.sql.Timestamp;
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
public class BookingUtilTest {
    private Booking testBooking;
    
    public BookingUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        // Initialize a test Booking object
        testBooking = new Booking();
        
        testBooking.setBookingId("B-TEST0001");
        testBooking.setCustomerId("CUS-EXAM01");
        testBooking.setVehicleId(null);
        testBooking.setDriverId(null);
        testBooking.setCity_distance_id(1);
        testBooking.setBooking_date(Timestamp.valueOf("2011-11-11 11:11:11"));
        testBooking.setStatus("Pending");
        testBooking.setFare(100.00);
        testBooking.setPayment_status("Unpaid");
    }
    
    @After
    public void tearDown() {
    }

    
    // Test of addBooking method, of class BookingUtil.
    @Test
    public void test_01_AddBooking() {
        System.out.println("Test Insert Booking - addBooking()");
        
        boolean expResult = true;
        boolean result = BookingUtil.addBooking(testBooking);
        assertEquals(expResult, result);
    }
    
    
        
    // Test of getBooking method, of class BookingUtil.
    @Test
    public void test_02_GetBooking() {
        System.out.println("Test Get Booking - getBooking()");
        
        Booking resultBooking = BookingUtil.getBooking(testBooking.getBookingId());
        
        assertEquals(testBooking.getBookingId(), resultBooking.getBookingId());
        assertEquals(testBooking.getCustomerId(), resultBooking.getCustomerId());
        assertEquals(testBooking.getVehicleId(), resultBooking.getVehicleId());
        assertEquals(testBooking.getDriverId(), resultBooking.getDriverId());
        assertEquals(testBooking.getCity_distance_id(), resultBooking.getCity_distance_id());
        assertEquals(testBooking.getBooking_date(), resultBooking.getBooking_date());
        assertEquals(testBooking.getStatus(), resultBooking.getStatus());
        assertEquals(testBooking.getFare(), resultBooking.getFare());
        assertEquals(testBooking.getPayment_status(), resultBooking.getPayment_status());
    }
    
    
    // Test of getAllBookings method, of class BookingUtil.
    @Test
    public void test_03_GetAllBookings() {
        System.out.println("Test Get All Bookings - getAllBookings()");
        
        Booking resultBooking = new Booking();
        
        // Retrieve all students
        List<Booking> allBookings = BookingUtil.getAllBookings();
        for (Booking st : allBookings) {
            if (st.getBookingId().equals(testBooking.getBookingId())) {
                resultBooking = st;
            }
        }
        
        assertEquals(testBooking.getBookingId(), resultBooking.getBookingId());
        assertEquals(testBooking.getCustomerId(), resultBooking.getCustomerId());
        assertEquals(testBooking.getVehicleId(), resultBooking.getVehicleId());
        assertEquals(testBooking.getDriverId(), resultBooking.getDriverId());
        assertEquals(testBooking.getCity_distance_id(), resultBooking.getCity_distance_id());
        assertEquals(testBooking.getBooking_date(), resultBooking.getBooking_date());
        assertEquals(testBooking.getStatus(), resultBooking.getStatus());
        assertEquals(testBooking.getFare(), resultBooking.getFare());
        assertEquals(testBooking.getPayment_status(), resultBooking.getPayment_status());
    }
    
    
    // Test of updateBooking method, of class BookingUtil.
    @Test
    public void test_04_UpdateBooking() {
        System.out.println("Test Update Booking - updateBooking()");
        
        testBooking.setVehicleId("VEH-EXAM01");
        testBooking.setDriverId("DRV-EXAM01");
        testBooking.setStatus("Completed");
        testBooking.setPayment_status("Paid");
        
        boolean expResult = true;
        boolean result = BookingUtil.updateBooking(testBooking);
        assertEquals(expResult, result);
        
        // Check each field update properly.
        System.out.println("Check updated Booking");
        Booking checkResultBooking = BookingUtil.getBooking(testBooking.getBookingId());
        
        assertEquals(testBooking.getBookingId(), checkResultBooking.getBookingId());
        assertEquals(testBooking.getCustomerId(), checkResultBooking.getCustomerId());
        assertEquals(testBooking.getVehicleId(), checkResultBooking.getVehicleId());
        assertEquals(testBooking.getDriverId(), checkResultBooking.getDriverId());
        assertEquals(testBooking.getCity_distance_id(), checkResultBooking.getCity_distance_id());
        assertEquals(testBooking.getBooking_date(), checkResultBooking.getBooking_date());
        assertEquals(testBooking.getStatus(), checkResultBooking.getStatus());
        assertEquals(testBooking.getFare(), checkResultBooking.getFare());
        assertEquals(testBooking.getPayment_status(), checkResultBooking.getPayment_status());
    }

    
    // Test of deleteBooking method, of class BookingUtil.
    @Test
    public void test_05_DeleteBooking() {
        System.out.println("Test Delete Booking - deleteBooking()");
        
        boolean expResult = true;
        boolean result = BookingUtil.deleteBooking(testBooking.getBookingId());
        assertEquals(expResult, result);
    }
    
    
}
