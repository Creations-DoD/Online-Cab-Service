package com.mcc.services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.mcc.object.classes.Discount;
import com.mcc.util.DiscountUtil;
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


@Path("/discount")
public class DiscountService {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllDiscounts(){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(DiscountUtil.getAllDiscounts());
    }
    
    @GET
    @Path("/{searchValue}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDiscount(@PathParam("searchValue") String searchValue){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(DiscountUtil.getDiscount(searchValue));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDiscount(String json) {
        // Convert the JSON string into a Discount object using Gson
        Gson gson = new GsonBuilder().create();
        Discount discount = gson.fromJson(json, Discount.class);
        
        // Validate discount code
        if (ValidationUtil.isDiscountCodeExists(discount.getCode())){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Discount code already exists.\"}")
                    .build();
        }

        // Add the discount
        boolean isAdded = DiscountUtil.addDiscount(discount);

        if (isAdded) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Discount added successfully!\"}")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_IMPLEMENTED)
                    .entity("{\"message\": \"Failed to add discount.\"}")
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDiscount(String json) {
        // Convert the JSON string into a Discount object using Gson
        Gson gson = new GsonBuilder().create();
        Discount discount = gson.fromJson(json, Discount.class);

        // Update the discount
        boolean isUpdated = DiscountUtil.updateDiscount(discount);

        if (isUpdated) {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"Discount updated successfully!\"}")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_IMPLEMENTED)
                    .entity("{\"message\": \"Failed to update discount.\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{discountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDiscount(@PathParam("discountId") String discountId) {
        boolean isDeleted = DiscountUtil.deleteDiscount(discountId);
        if (isDeleted) {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"Discount deleted successfully!\"}")
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Failed to delete discount.\"}")
                    .build();
        }
    }
    
    
}
