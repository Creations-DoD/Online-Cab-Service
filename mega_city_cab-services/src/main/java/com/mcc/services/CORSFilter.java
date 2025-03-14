package com.mcc.services;

import javax.ws.rs.core.Response;

public class CORSFilter {

    // Add CORS headers to a Response.ResponseBuilder
    public static Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder responseBuilder) {
        return responseBuilder
                .header("Access-Control-Allow-Origin", "http://127.0.0.1:5500") // Allow requests from this origin
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS") // Allow these methods
                .header("Access-Control-Allow-Headers", "Content-Type"); // Allow the Content-Type header
    }

    // Handle preflight OPTIONS requests
    public static Response handlePreflight() {
        return addCorsHeaders(Response.ok()).build();
    }
}