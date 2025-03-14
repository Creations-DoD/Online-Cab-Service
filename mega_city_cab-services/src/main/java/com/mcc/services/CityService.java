/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mcc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mcc.util.CityUtil;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/city")
public class CityService {
    
    // Handle preflight OPTIONS requests
    @OPTIONS
    public Response handlePreflight() {
        return CORSFilter.handlePreflight();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVehiclesModels(){
        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(CityUtil.getAllCities());
        return CORSFilter.addCorsHeaders(Response.ok(jsonResponse)).build();
    }
    
}
