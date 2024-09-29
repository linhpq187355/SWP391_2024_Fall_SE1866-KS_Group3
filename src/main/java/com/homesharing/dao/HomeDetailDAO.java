package com.homesharing.dao;

import com.homesharing.model.Home;
import com.homesharing.model.HomeType;
import com.homesharing.model.Price;
import com.homesharing.model.User;

import java.util.List;

public interface HomeDetailDAO {
    /**
     * Gets a specific Home object based on its unique ID.
     *
     * @param id The unique identifier of the home you want to retrieve.
     * @return The Home object that matches the provided ID, or null if not found.
     */
    Home getHomeById(int id);

    /**
     * Fetches all the prices related to a particular home, using its ID.
     *
     * @param homeId The ID of the home for which you need the price details.
     * @return A list of Price objects associated with the specified home ID.
     */
    List<Price> getHomePricesByHomeId(int homeId);

    /**
     * Finds out who created the home by fetching the User object associated with it.
     *
     * @param homeId The ID of the home to identify its creator.
     * @return The User object representing the creator of the home, or null if not found.
     */
    User getCreatorByHomeId(int homeId);

    /**
     * Retrieves the types of homes associated with a specific home by its ID.
     *
     * @param homeId The ID of the home you’re interested in.
     * @return A list of HomeType objects that are linked to the specified home.
     */
    List<HomeType> getHomeTypesByHomeId(int homeId);

}
