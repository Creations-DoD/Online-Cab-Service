package com.mcc.services;

import com.cmc.calculator.CostCalculator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mcc.object.classes.TripCost;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tripCost")
public class CalculatorService {
    
    // Handle preflight OPTIONS requests
    @OPTIONS
    public Response handlePreflight() {
        return CORSFilter.handlePreflight();
    }

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getTripCost(String json) {
//        // Convert the JSON string into a Customer object using Gson
//        Gson gson = new GsonBuilder().create();
//        TripCost tripCost = gson.fromJson(json, TripCost.class);
//        
//        Double cost = CostCalculator.calculateTripCost(tripCost.getPickup(), tripCost.getDrop(), tripCost.getVehicleType());
//        
//        // Return the response
//        if (cost != null) {
//            return CORSFilter.addCorsHeaders(Response.status(Response.Status.OK))
//                    .entity("{\"cost\": " + cost + "}")
//                    .build();
//        } else {
//            return CORSFilter.addCorsHeaders(Response.status(Response.Status.NOT_IMPLEMENTED))
//                    .entity("{\"message\": \"Failed to calculate cost.\"}")
//                    .build();
//        }
//    }
//    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTripCost(String json) {
        // Parse JSON directly into variables
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        int pickup = jsonObject.get("pickup").getAsInt();
        int drop = jsonObject.get("drop").getAsInt();
        String vehicleType = jsonObject.get("vehicleType").getAsString();

        // Calculate the trip cost
        Map<String, Object> result = CostCalculator.calculateTripCost(pickup, drop, vehicleType);

        // Check if both values exist
        if (!result.isEmpty()) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.OK))
                    .entity(new Gson().toJson(result)) // Convert Map to JSON
                    .build();
        } else {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.NOT_IMPLEMENTED))
                    .entity("{\"message\": \"Failed to calculate cost.\"}")
                    .build();
        }
    }

    
    
    
////    @POST
////    @Consumes(MediaType.APPLICATION_JSON)
////    @Produces(MediaType.APPLICATION_JSON)
////    public Response getTripCost(String json) {
////        // Parse JSON directly into variables
////        JsonParser parser = new JsonParser();
////        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
////        int pickup = jsonObject.get("pickup").getAsInt();
////        int drop = jsonObject.get("drop").getAsInt();
////        String vehicleType = jsonObject.get("vehicleType").getAsString();
////
////        // Calculate the trip cost
////        Double cost = CostCalculator.calculateTripCost(pickup, drop, vehicleType);
////
////        // Return the response
////        if (cost != null) {
////            return CORSFilter.addCorsHeaders(Response.status(Response.Status.OK))
////                    .entity("{\"cost\": " + cost + "}")
////                    .build();
////        } else {
////            return CORSFilter.addCorsHeaders(Response.status(Response.Status.NOT_IMPLEMENTED))
////                    .entity("{\"message\": \"Failed to calculate cost.\"}")
////                    .build();
////        }
////    }
    
    
    
    
    
    
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response login(String json) {
//        // Convert the JSON string into a Customer object using Gson
//        Gson gson = new GsonBuilder().create();
//        User user = gson.fromJson(json, User.class);
//        
//        // Validate user credentials and get role
//        Double userDetails = CostCalculator.calculateTripCost(username, password, as);
//
//        if (userDetails != null) {
//            // Login successful
//            return CORSFilter.addCorsHeaders(Response.status(Response.Status.OK))
//                    .entity("{\"message\": \"Login successful!\", \"role\": \"" + userDetails.get("role") + "\", "
//                            + "\"username\": \"" + userDetails.get("username") + "\", "
//                                + "\"userID\": \"" + userDetails.get("userID") + "\"}")
//                    .build();
//        } else {
//            // Login failed
//            return CORSFilter.addCorsHeaders(Response.status(Response.Status.UNAUTHORIZED))
//                    .entity("{\"message\": \"Invalid username or password.\"}")
//                    .build();
//        }
//    }
    
}