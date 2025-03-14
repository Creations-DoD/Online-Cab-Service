package com.mcc.services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.mcc.object.classes.Driver;
import com.mcc.util.DriverUtil;
import com.mcc.util.ValidationUtil;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/driver")
public class DriverService {

    // Handle preflight OPTIONS requests
    @OPTIONS
    public Response handlePreflight() {
        return CORSFilter.handlePreflight();
    }
    
    // Handle preflight OPTIONS requests for the specific path
    @OPTIONS
    @Path("/{driverId}")
    public Response handlePreflight(@PathParam("driverId") String driverId) {
        return CORSFilter.handlePreflight();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDrivers(){
        Gson gson = new GsonBuilder().create();
        String jsonResponse =  gson.toJson(DriverUtil.getAllDrivers());
        return CORSFilter.addCorsHeaders(Response.ok(jsonResponse)).build();
    }
    
    @GET
    @Path("/{driverId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDriver(@PathParam("driverId") String driverId){
        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(DriverUtil.getDriver(driverId));
        return CORSFilter.addCorsHeaders(Response.ok(jsonResponse)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDriver(String json) {
        // Convert the JSON string into a Driver object using Gson
        Gson gson = new GsonBuilder().create();
        Driver driver = gson.fromJson(json, Driver.class);
        
        // Validate input fields
        if (!ValidationUtil.isValidUsername(driver.getUsername())) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Invalid username format.\"}")
                    .build();
        }
        if (!ValidationUtil.isValidEmail(driver.getEmail())) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Invalid email format.\"}")
                    .build();
        }
        if (!ValidationUtil.isValidNIC(driver.getNic())) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Invalid NIC format.\"}")
                    .build();
        }
        if (!ValidationUtil.isValidPhoneNumber(driver.getPhoneNumber())) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Invalid phone number format.\"}")
                    .build();
        }
        if (!ValidationUtil.isValidDrivingLicense(driver.getLicenseNumber())) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Invalid driving license number format.\"}")
                    .build();
        }

        // Check if username already exists
        if (DriverUtil.isUsernameExists(driver.getUsername())) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Username already exists.\"}")
                    .build();
        }

        // Add the driver
        boolean isAdded = DriverUtil.addDriver(driver);

        if (isAdded) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.CREATED))
                    .entity("{\"message\": \"Driver added successfully!\"}")
                    .build();
        } else {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.NOT_IMPLEMENTED))
                    .entity("{\"message\": \"Failed to add driver.\"}")
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDriver(String json) {
        // Convert the JSON string into a Driver object using Gson
        Gson gson = new GsonBuilder().create();
        Driver driver = gson.fromJson(json, Driver.class);
        
        // Validate phone number
        if (!ValidationUtil.isValidPhoneNumber(driver.getPhoneNumber())) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Invalid phone number format.\"}")
                    .build();
        }
        if (!ValidationUtil.isValidDrivingLicense(driver.getLicenseNumber())) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Invalid driving license number format.\"}")
                    .build();
        }

        // Update the driver
        boolean isUpdated = DriverUtil.updateDriver(driver);

        if (isUpdated) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.OK))
                    .entity("{\"message\": \"Driver updated successfully!\"}")
                    .build();
        } else {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.NOT_IMPLEMENTED))
                    .entity("{\"message\": \"Failed to update driver.\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{driverId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDriver(@PathParam("driverId") String driverId) {
        boolean isDeleted = DriverUtil.deleteDriver(driverId);
        if (isDeleted) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.OK))
                    .entity("{\"message\": \"Driver deleted successfully!\"}")
                    .build();
        } else {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Failed to delete driver.\"}")
                    .build();
        }
    }
    
    
    @GET
    @Path("/available")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAvailableDrivers(){
        Gson gson = new GsonBuilder().create();
        String jsonResponse =  gson.toJson(DriverUtil.getAllAvailableDrivers());
        return CORSFilter.addCorsHeaders(Response.ok(jsonResponse)).build();
    }
    
}
