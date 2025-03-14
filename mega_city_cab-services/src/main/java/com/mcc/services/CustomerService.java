package com.mcc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.mcc.object.classes.Customer;
import com.mcc.util.CustomerUtil;
import com.mcc.util.ValidationUtil;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customer")
public class CustomerService {

    // Handle preflight OPTIONS requests for the base path
    @OPTIONS
    public Response handlePreflight() {
        return CORSFilter.handlePreflight();
    }

    // Handle preflight OPTIONS requests for the specific path
    @OPTIONS
    @Path("/{customerId}")
    public Response handlePreflight(@PathParam("customerId") String customerId) {
        return CORSFilter.handlePreflight();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(CustomerUtil.getAllCustomers());
        return CORSFilter.addCorsHeaders(Response.ok(jsonResponse)).build();
    }

    @GET
    @Path("/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("customerId") String customerId) {
        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(CustomerUtil.getCustomer(customerId));
        return CORSFilter.addCorsHeaders(Response.ok(jsonResponse)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCustomer(String json) {
        // Convert the JSON string into a Customer object using Gson
        Gson gson = new GsonBuilder().create();
        Customer customer = gson.fromJson(json, Customer.class);

        // Validate input fields
        if (!ValidationUtil.isValidUsername(customer.getUsername())) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Invalid username format.\"}")
                    .build();
        }
        if (!ValidationUtil.isValidEmail(customer.getEmail())) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Invalid email format.\"}")
                    .build();
        }
        if (!ValidationUtil.isValidNIC(customer.getNic())) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Invalid NIC format.\"}")
                    .build();
        }
        if (!ValidationUtil.isValidPhoneNumber(customer.getPhoneNumber())) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Invalid phone number format.\"}")
                    .build();
        }

        // Check if username already exists
        if (CustomerUtil.isUsernameExists(customer.getUsername())) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Username already exists.\"}")
                    .build();
        }

        // Add the customer
        boolean isAdded = CustomerUtil.addCustomer(customer);

        if (isAdded) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.CREATED))
                    .entity("{\"message\": \"Customer added successfully!\"}")
                    .build();
        } else {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.NOT_IMPLEMENTED))
                    .entity("{\"message\": \"Failed to add customer.\"}")
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(String json) {
        // Convert the JSON string into a Customer object using Gson
        Gson gson = new GsonBuilder().create();
        Customer customer = gson.fromJson(json, Customer.class);

        // Validate phone number
        if (!ValidationUtil.isValidPhoneNumber(customer.getPhoneNumber())) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
                    .entity("{\"message\": \"Invalid phone number format.\"}")
                    .build();
        }

        // Update the customer
        boolean isUpdated = CustomerUtil.updateCustomer(customer);

        if (isUpdated) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.OK))
                    .entity("{\"message\": \"Customer updated successfully!\"}")
                    .build();
        } else {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.NOT_IMPLEMENTED))
                    .entity("{\"message\": \"Failed to update customer.\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomer(@PathParam("customerId") String customerId) {
        boolean isDeleted = CustomerUtil.deleteCustomer(customerId);

        if (isDeleted) {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.OK))
                    .entity("{\"message\": \"Customer deleted successfully!\"}")
                    .build();
        } else {
            return CORSFilter.addCorsHeaders(Response.status(Response.Status.UNAUTHORIZED))
                    .entity("{\"message\": \"Failed to delete customer.\"}")
                    .build();
        }
    }
}






/////////////////package com.mcc.services;
//////////////
//////////////
//////////////import com.google.gson.Gson;
//////////////import com.google.gson.GsonBuilder;
//////////////
//////////////import com.mcc.object.classes.Customer;
//////////////import com.mcc.util.CustomerUtil;
//////////////import com.mcc.util.ValidationUtil;
//////////////
//////////////import javax.ws.rs.Path;
//////////////import javax.ws.rs.PathParam;
//////////////import javax.ws.rs.GET;
//////////////import javax.ws.rs.POST;
//////////////import javax.ws.rs.PUT;
//////////////import javax.ws.rs.DELETE;
//////////////import javax.ws.rs.Produces;
//////////////import javax.ws.rs.Consumes;
//////////////import javax.ws.rs.OPTIONS;
//////////////import javax.ws.rs.core.MediaType;
//////////////import javax.ws.rs.core.Response;
//////////////
//////////////
//////////////@Path("/customer")
//////////////public class CustomerService {
//////////////
//////////////    // Handle preflight OPTIONS requests
//////////////    @OPTIONS
//////////////    public Response handlePreflight() {
//////////////        return CORSFilter.handlePreflight();
//////////////    }
//////////////
//////////////    @GET
//////////////    @Produces(MediaType.APPLICATION_JSON)
//////////////    public Response getAllCustomers() {
//////////////        Gson gson = new GsonBuilder().create();
//////////////        String jsonResponse = gson.toJson(CustomerUtil.getAllCustomers());
//////////////        return CORSFilter.addCorsHeaders(Response.ok(jsonResponse)).build();
//////////////    }
//////////////
//////////////    @GET
//////////////    @Path("/{customerId}")
//////////////    @Produces(MediaType.APPLICATION_JSON)
//////////////    public Response getCustomer(@PathParam("customerId") String customerId) {
//////////////        Gson gson = new GsonBuilder().create();
//////////////        String jsonResponse = gson.toJson(CustomerUtil.getCustomer(customerId));
//////////////        return CORSFilter.addCorsHeaders(Response.ok(jsonResponse)).build();
//////////////    }
//////////////
//////////////    @POST
//////////////    @Consumes(MediaType.APPLICATION_JSON)
//////////////    @Produces(MediaType.APPLICATION_JSON)
//////////////    public Response addCustomer(String json) {
//////////////        // Convert the JSON string into a Customer object using Gson
//////////////        Gson gson = new GsonBuilder().create();
//////////////        Customer customer = gson.fromJson(json, Customer.class);
//////////////
//////////////        // Validate input fields
//////////////        if (!ValidationUtil.isValidUsername(customer.getUsername())) {
//////////////            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
//////////////                    .entity("{\"message\": \"Invalid username format.\"}")
//////////////                    .build();
//////////////        }
//////////////        if (!ValidationUtil.isValidEmail(customer.getEmail())) {
//////////////            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
//////////////                    .entity("{\"message\": \"Invalid email format.\"}")
//////////////                    .build();
//////////////        }
//////////////        if (!ValidationUtil.isValidNIC(customer.getNic())) {
//////////////            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
//////////////                    .entity("{\"message\": \"Invalid NIC format.\"}")
//////////////                    .build();
//////////////        }
//////////////        if (!ValidationUtil.isValidPhoneNumber(customer.getPhoneNumber())) {
//////////////            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
//////////////                    .entity("{\"message\": \"Invalid phone number format.\"}")
//////////////                    .build();
//////////////        }
//////////////
//////////////        // Check if username already exists
//////////////        if (CustomerUtil.isUsernameExists(customer.getUsername())) {
//////////////            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
//////////////                    .entity("{\"message\": \"Username already exists.\"}")
//////////////                    .build();
//////////////        }
//////////////
//////////////        // Add the customer
//////////////        boolean isAdded = CustomerUtil.addCustomer(customer);
//////////////
//////////////        if (isAdded) {
//////////////            return CORSFilter.addCorsHeaders(Response.status(Response.Status.CREATED))
//////////////                    .entity("{\"message\": \"Customer added successfully!\"}")
//////////////                    .build();
//////////////        } else {
//////////////            return CORSFilter.addCorsHeaders(Response.status(Response.Status.NOT_IMPLEMENTED))
//////////////                    .entity("{\"message\": \"Failed to add customer.\"}")
//////////////                    .build();
//////////////        }
//////////////    }
//////////////
//////////////    @PUT
//////////////    @Consumes(MediaType.APPLICATION_JSON)
//////////////    @Produces(MediaType.APPLICATION_JSON)
//////////////    public Response updateCustomer(String json) {
//////////////        // Convert the JSON string into a Customer object using Gson
//////////////        Gson gson = new GsonBuilder().create();
//////////////        Customer customer = gson.fromJson(json, Customer.class);
//////////////
//////////////        // Validate phone number
//////////////        if (!ValidationUtil.isValidPhoneNumber(customer.getPhoneNumber())) {
//////////////            return CORSFilter.addCorsHeaders(Response.status(Response.Status.BAD_REQUEST))
//////////////                    .entity("{\"message\": \"Invalid phone number format.\"}")
//////////////                    .build();
//////////////        }
//////////////
//////////////        // Update the customer
//////////////        boolean isUpdated = CustomerUtil.updateCustomer(customer);
//////////////
//////////////        if (isUpdated) {
//////////////            return CORSFilter.addCorsHeaders(Response.status(Response.Status.OK))
//////////////                    .entity("{\"message\": \"Customer updated successfully!\"}")
//////////////                    .build();
//////////////        } else {
//////////////            return CORSFilter.addCorsHeaders(Response.status(Response.Status.NOT_IMPLEMENTED))
//////////////                    .entity("{\"message\": \"Failed to update customer.\"}")
//////////////                    .build();
//////////////        }
//////////////    }
//////////////
//////////////    @DELETE
//////////////    @Path("/{customerId}")
//////////////    @Produces(MediaType.APPLICATION_JSON)
//////////////    public Response deleteCustomer(@PathParam("customerId") String customerId) {
//////////////        boolean isDeleted = CustomerUtil.deleteCustomer(customerId);
//////////////
//////////////        if (isDeleted) {
//////////////            return CORSFilter.addCorsHeaders(Response.status(Response.Status.OK))
//////////////                    .entity("{\"message\": \"Customer deleted successfully!\"}")
//////////////                    .build();
//////////////        } else {
//////////////            return CORSFilter.addCorsHeaders(Response.status(Response.Status.UNAUTHORIZED))
//////////////                    .entity("{\"message\": \"Failed to delete customer.\"}")
//////////////                    .build();
//////////////        }
//////////////    }
//////////////    
//////////////}






////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////










//@Path("/customer")
//public class CustomerService {
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getAllCustomers(){
//        Gson gson = new GsonBuilder().create();
//        return gson.toJson(CustomerUtil.getAllCustomers());
//    }
//    
//    @GET
//    @Path("/{customerId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getCustomer(@PathParam("customerId") String customerId){
////        DBUtil utils = new DBUtil();
////        Student student = utils.getStudent(id);
////        Gson gson = new GsonBuilder().create();
////        return gson.toJson(student);
//
//        Gson gson = new GsonBuilder().create();
//        return gson.toJson(CustomerUtil.getCustomer(customerId));
//    }
//    
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response addCustomer(String json) {
//        // Convert the JSON string into a Customer object using Gson
//        Gson gson = new GsonBuilder().create();
//        Customer customer = gson.fromJson(json, Customer.class);
//        
//        // Validate input fields
//        if (!ValidationUtil.isValidUsername(customer.getUsername())) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("{\"message\": \"Invalid username format.\"}")
//                    .build();
//        }
//        if (!ValidationUtil.isValidEmail(customer.getEmail())) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("{\"message\": \"Invalid email format.\"}")
//                    .build();
//        }
//        if (!ValidationUtil.isValidNIC(customer.getNic())) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("{\"message\": \"Invalid NIC format.\"}")
//                    .build();
//        }
//        if (!ValidationUtil.isValidPhoneNumber(customer.getPhoneNumber())) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("{\"message\": \"Invalid phone number format.\"}")
//                    .build();
//        }
//
//        // Check if username already exists
//        if (CustomerUtil.isUsernameExists(customer.getUsername())) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("{\"message\": \"Username already exists.\"}")
//                    .build();
//        }
//
//        // Add the customer
//        boolean isAdded = CustomerUtil.addCustomer(customer);
//
//        if (isAdded) {
//            return Response.status(Response.Status.CREATED)
//                    .entity("{\"message\": \"Customer added successfully!\"}")
//                    .build();
//        } else {
//            return Response.status(Response.Status.NOT_IMPLEMENTED)
//                    .entity("{\"message\": \"Failed to add customer.\"}")
//                    .build();
//        }
//    }
//    
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response updateCustomer(String json) {
//        // Convert the JSON string into a Customer object using Gson
//        Gson gson = new GsonBuilder().create();
//        Customer customer = gson.fromJson(json, Customer.class);
//        
//        // Validate phone number
//        if (!ValidationUtil.isValidPhoneNumber(customer.getPhoneNumber())) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("{\"message\": \"Invalid phone number format.\"}")
//                    .build();
//        }
//
//        // Update the customer
//        boolean isUpdated = CustomerUtil.updateCustomer(customer);
//
//        if (isUpdated) {
//            return Response.status(Response.Status.OK)
//                    .entity("{\"message\": \"Customer updated successfully!\"}")
//                    .build();
//        } else {
//            return Response.status(Response.Status.NOT_IMPLEMENTED)
//                    .entity("{\"message\": \"Failed to update customer.\"}")
//                    .build();
//        }
//    }
//    
//    @DELETE
//    @Path("/{customerId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response deleteCustomer(@PathParam("customerId") String customerId) {
//        boolean isDeleted = CustomerUtil.deleteCustomer(customerId);
//
//        if (isDeleted) {
//            return Response.status(Response.Status.OK)
//                    .entity("{\"message\": \"Customer deleted successfully!\"}")
//                    .build();
//        } else {
//            return Response.status(Response.Status.UNAUTHORIZED)
//                    .entity("{\"message\": \"Failed to delete customer.\"}")
//                    .build();
//        }
//    }
//    
//    
//}
