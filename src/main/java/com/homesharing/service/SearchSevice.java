package com.homesharing.service;

import com.homesharing.model.Home;
import com.homesharing.model.Price;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface SearchSevice {
    /**
     * Searches for homes based on the provided name.
     *
     * @param name The name or keyword to search for in home listings.
     * @return A list of Home objects that match the given name, or an empty list if none are found.
     */
    List<Home> searchHomesByName(String name);

    /**
     * Searches for homes within a specified price range.
     *
     * @param minPrice The minimum price of the homes you’re looking for.
     * @param maxPrice The maximum price of the homes you’re looking for.
     * @return A list of Home objects that fall within the specified price range.
     */
    List<Home> searchByPriceRange(int minPrice, int maxPrice);

    /**
     * Gets the maximum price of homes available in the database.
     *
     * @return The highest price of homes, or -1 if there are no homes available.
     * @throws IOException If there’s an input/output error.
     * @throws SQLException If there’s an issue with the database access.
     * @throws ClassNotFoundException If the database driver class isn’t found.
     */
    int getMaxPrice() throws IOException, SQLException, ClassNotFoundException;

    /**
     * Retrieves the price of a specific home using its ID.
     *
     * @param homeId The ID of the home whose price you want to fetch.
     * @return The Price object associated with the specified home ID, or null if not found.
     * @throws SQLException If there’s an issue with the database access.
     * @throws ClassNotFoundException If the database driver class isn’t found.
     */
    Price getPriceByHomeId(int homeId) throws SQLException, ClassNotFoundException;

}
