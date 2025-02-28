package com.mcc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {

    private static final String URL = "jdbc:mysql://localhost:3306/mega_city_cab_service?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "mcc";
    private static final String PASSWORD = "123";

    private static DBConn instance;
    private Connection connection;

    private DBConn() {
        this.connection = createConnection();
    }

    public static synchronized DBConn getInstance() {
        if (instance == null) {
            instance = new DBConn();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            // Check if the connection is closed or invalid
            if (connection == null || connection.isClosed() || !connection.isValid(1)) {
                this.connection = createConnection(); // Recreate the connection
            }
        } catch (SQLException e) {
            System.err.println("Error checking connection validity: " + e.getMessage());
            this.connection = createConnection(); // Recreate the connection if there's an error
        }
        return this.connection;
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error creating database connection: " + e.getMessage());
            return null;
        }
    }
}