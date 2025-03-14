package com.mcc.util;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CityUtil {
    
    // Use Pair or Tuple Class to send the data.
    
    
    // Method to get a all cities.
    public static List<Pair<Integer, String>> getAllCities() {
        List<Pair<Integer, String>> cities = new ArrayList<>();
        
        String query = "SELECT * FROM city";
        try (Connection conn = DBConn.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                cities.add(new Pair<>(rs.getInt("city_id"), rs.getString("city_name")));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return cities;
    }

//////////    // User Pair List data type. - Third-Party Library
//////////
//////////
//////////    public static void main(String[] args) {
//////////        List<Pair<Integer, String>> cities = new ArrayList<>();
//////////
//////////        // Adding cities: new Pair<>(cityId, cityName)
//////////        cities.add(new Pair<>(1, "New York"));
//////////        cities.add(new Pair<>(2, "Los Angeles"));
//////////        cities.add(new Pair<>(3, "Chicago"));
//////////
//////////        // Displaying the list of cities
//////////        for (Pair<Integer, String> city : cities) {
//////////            System.out.println("City ID: " + city.getKey() + ", City Name: " + city.getValue());
//////////        }
//////////    }
//////////
//////////
//////////
//////////
//////////    // User 'Triplet' List data type. - Third-Party Library (Can not do using pair)
//////////
//////////
//////////        import org.javatuples.Triplet;
//////////
//////////        Triplet<Integer, String, String> cityTriplet = Triplet.with(1, "New York", "USA");
//////////
//////////        List<Triplet<Integer, String, String>> cities = new ArrayList<>();
//////////        cities.add(cityTriplet);
//////////        cities.add(Triplet.with(2, "Los Angeles", "USA"));
    
    
    
    
    // 2nd way to do it.
    
//    public static List<Map<String, String>> getAllCities() {
//        List<Map<String, String>> cities = new ArrayList<>();
//        
//        String query = "SELECT * FROM city";
//        try (Connection conn = DBConn.getInstance().getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                Map<String, String> cityDetails = new HashMap<>();
//                cityDetails.put("id", rs.getString("city_id"));
//                cityDetails.put("city", rs.getString("city_name"));
//                cities.add(cityDetails);
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex);
//        }
//        return cities;
//    }

}
