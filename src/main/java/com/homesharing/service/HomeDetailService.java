package com.homesharing.service;

import com.homesharing.model.*;

import java.util.List;

public interface HomeDetailService {
    /**
     * Retrieves a Home object based on its unique ID.
     *
     * @param id The unique identifier of the home you want to get.
     * @return The Home object that corresponds to the provided ID, or null if not found.
     */
    Home getHomeById(int id);

    /**
     * Gets a list of prices associated with a specific home, identified by its ID.
     *
     * @param homeId The ID of the home for which you need the price details.
     * @return A list of Price objects corresponding to the specified home ID.
     */
    List<Price> getHomePricesByHomeId(int homeId);

    /**
     * Finds the creator (User) of a home using the home’s ID.
     *
     * @param homeId The ID of the home whose creator you want to retrieve.
     * @return The User object representing the creator of the home, or null if not found.
     */
    User getCreatorByHomeId(int homeId);

    /**
     * Retrieves a list of home types associated with a specific home, identified by its ID.
     *
     * @param homeId The ID of the home for which you want to get the types.
     * @return A list of HomeType objects linked to the specified home ID.
     */
    List<HomeType> getHomeTypesByHomeId(int homeId);

    List<Amentity> getHomeAmenitiesByHomeId(int homeId);

    List<FireEquipment> getHomeFireEquipmentsByHomeId(int homeId);


}