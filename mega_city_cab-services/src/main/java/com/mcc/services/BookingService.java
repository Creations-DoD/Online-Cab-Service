package com.mcc.services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.mcc.object.classes.Booking;
import com.mcc.util.BookingUtil;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/booking")
public class BookingService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllBookings(){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(BookingUtil.getAllBookings());
    }
    
    @GET
    @Path("/{bookingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getBooking(@PathParam("bookingId") String bookingId){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(BookingUtil.getBooking(bookingId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBooking(String json) {
        // Convert the JSON string into a Booking object using Gson
        Gson gson = new GsonBuilder().create();
        Booking booking = gson.fromJson(json, Booking.class);

        // Add the booking
        boolean isAdded = BookingUtil.addBooking(booking);

        if (isAdded) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Booking added successfully!\"}")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_IMPLEMENTED)
                    .entity("{\"message\": \"Failed to add booking.\"}")
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBooking(String json) {
        // Convert the JSON string into a Booking object using Gson
        Gson gson = new GsonBuilder().create();
        Booking booking = gson.fromJson(json, Booking.class);
        
        // Update the booking
        boolean isUpdated = BookingUtil.updateBooking(booking);

        if (isUpdated) {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"Booking updated successfully!\"}")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_IMPLEMENTED)
                    .entity("{\"message\": \"Failed to update booking.\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{bookingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBooking(@PathParam("bookingId") String bookingId) {
        boolean isDeleted = BookingUtil.deleteBooking(bookingId);
        if (isDeleted) {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"Booking deleted successfully!\"}")
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Failed to delete booking.\"}")
                    .build();
        }
    }
    
    
}
