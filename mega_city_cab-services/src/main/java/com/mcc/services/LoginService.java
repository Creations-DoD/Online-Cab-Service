package com.mcc.services;

import com.mcc.object.classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mcc.util.CustomerUtil;
import java.util.Map;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.OPTIONS;

@Path("/login")
public class LoginService {
    
    // Handle preflight OPTIONS requests
    @OPTIONS
    public Response handlePreflight() {
        return CORSFilter.handlePreflight();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String json) {
        // Convert the JSON string into a Customer object using Gson
        Gson gson = new GsonBuilder().create();
        User user = gson.fromJson(json, User.class);

        String username = user.getUsername();
        String password = user.getPassword();

        // Validate input fields
        if (username == null || username.trim().isEmpty()) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Username is required.\"}")
                    .build();
        }
        if (password == null || password.trim().isEmpty()) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Password is required.\"}")
                    .build();
        }

        // Validate user credentials and get role
        Map<String, String> userDetails = CustomerUtil.validateUser(username, password);

        if (userDetails != null) {
            // Login successful
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.OK))
                    .entity("{\"message\": \"Login successful!\", \"role\": \"" + userDetails.get("role") + "\", "
                            + "\"username\": \"" + userDetails.get("username") + "\", "
                                + "\"userID\": \"" + userDetails.get("userID") + "\"}")
                    .build();
        } else {
            // Login failed
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.UNAUTHORIZED))
                    .entity("{\"message\": \"Invalid username or password.\"}")
                    .build();
        }
    }
}
// 1st way



//package com.mcc.services;
//
//import com.mcc.object.classes.User;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.mcc.util.CustomerUtil;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//@Path("/login")
//public class LoginService {
//
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response login(String json) {
//        // Convert the JSON string into a Customer object using Gson
//        Gson gson = new GsonBuilder().create();
//        User user = gson.fromJson(json, User.class);
//        
//        String username = user.getUsername();
//        String password = user.getPassword();
//
//        // Validate input fields
//        if (username == null || username.trim().isEmpty()) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("{\"message\": \"Username is required.\"}")
//                    .build();
//        }
//        if (password == null || password.trim().isEmpty()) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("{\"message\": \"Password is required.\"}")
//                    .build();
//        }
//
//        // Validate user credentials and get role
//        String role = CustomerUtil.validateUser(username, password);
//
//        if (role != null) {
//            // Login successful
//            return Response.status(Response.Status.OK)
//                    .entity("{\"message\": \"Login successful!\", \"role\": \"" + role + "\"}")
//                    .build();
//        } else {
//            // Login failed
//            return Response.status(Response.Status.UNAUTHORIZED)
//                    .entity("{\"message\": \"Invalid username or password.\"}")
//                    .build();
//        }
//    }
//    
//}