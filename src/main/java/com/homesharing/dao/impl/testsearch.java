package com.homesharing.dao.impl;

import com.homesharing.model.Home;
import java.util.List;

public class testsearch {
    public static void main(String[] args) {
        SearchDAOimpl searchDAO = new SearchDAOimpl();

        // Set your test search parameters here
        String name = "cozy";  // Test for homes with 'cozy' in the name
        String location = null; // You can also set this to a specific location or keep it null
        Integer minPrice = null; // You can set a minimum price or keep it null
        Integer maxPrice = null; // You can set a maximum price or keep it null

        // Call the search method
        List<Home> homes = searchDAO.searchHomes(name, location, minPrice, maxPrice);

        // Print the results
        if (homes.isEmpty()) {
            System.out.println("No homes found.");
        } else {
            for (Home home : homes) {
                System.out.println("ID: " + home.getId());
                System.out.println("Name: " + home.getName());
                System.out.println("Address: " + home.getAddress());
                System.out.println("Price ID: " + home.getPriceId()); // Assuming priceId is stored
                // If you want to print more fields, do so here
                System.out.println("-----------------------------------");
            }
        }
    }
}
