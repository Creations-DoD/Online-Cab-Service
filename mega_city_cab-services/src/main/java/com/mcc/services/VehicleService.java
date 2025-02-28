package com.mcc.services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.mcc.object.classes.Vehicle;
import com.mcc.util.VehicleUtil;
import com.mcc.util.ValidationUtil;

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


@Path("/vehicle")
public class VehicleService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllVehicles(){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(VehicleUtil.getAllVehicles());
    }
    
    @GET
    @Path("/{vehicleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getVehicle(@PathParam("vehicleId") String vehicleId){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(VehicleUtil.getVehicle(vehicleId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addVehicle(String json) {
        // Convert the JSON string into a Vehicle object using Gson
        Gson gson = new GsonBuilder().create();
        Vehicle vehicle = gson.fromJson(json, Vehicle.class);
        
        // Validate input fields
        if (!ValidationUtil.isValidVehicleLicensePlate(vehicle.getLicensePlate())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Invalid vehicle license plate number format.\"}")
                    .build();
        }

        // Add the vehicle
        boolean isAdded = VehicleUtil.addVehicle(vehicle);

        if (isAdded) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Vehicle added successfully!\"}")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_IMPLEMENTED)
                    .entity("{\"message\": \"Failed to add vehicle.\"}")
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateVehicle(String json) {
        // Convert the JSON string into a Vehicle object using Gson
        Gson gson = new GsonBuilder().create();
        Vehicle vehicle = gson.fromJson(json, Vehicle.class);
        
        // Validate phone number
        if (!ValidationUtil.isValidVehicleLicensePlate(vehicle.getLicensePlate())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Invalid vehicle license plate number format.\"}")
                    .build();
        }

        // Update the vehicle
        boolean isUpdated = VehicleUtil.updateVehicle(vehicle);

        if (isUpdated) {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"Vehicle updated successfully!\"}")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_IMPLEMENTED)
                    .entity("{\"message\": \"Failed to update vehicle.\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{vehicleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteVehicle(@PathParam("vehicleId") String vehicleId) {
        boolean isDeleted = VehicleUtil.deleteVehicle(vehicleId);
        if (isDeleted) {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"Vehicle deleted successfully!\"}")
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Failed to delete vehicle.\"}")
                    .build();
        }
    }
    
    
}
