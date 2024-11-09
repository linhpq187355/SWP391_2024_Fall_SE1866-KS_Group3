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

    /**
     * Retrieves a home type associated with a specific home, identified by its ID.
     *
     * @param homeId The ID of the home for which you want to get the types.
     * @return A list of HomeType objects linked to the specified home ID.
     */
    public HomeType getHomeTypeByHomeId(int homeId);

    /**
     * Retrieves the amenities associated with a specific home by its ID.
     * These are the features or facilities available for that home.
     *
     * @param homeId The ID of the home for which you want to retrieve associated amenities.
     * @return A list of Amenity objects representing the amenities of the home.
     *         If no amenities are found, the list will be empty.
     */
    List<Amentity> getHomeAmenitiesByHomeId(int homeId);

    /**
     * Retrieves the fire safety equipment associated with a specific home by its ID.
     * This includes fire extinguishers, smoke detectors, or other related equipment.
     *
     * @param homeId The ID of the home for which you want to retrieve fire equipment.
     * @return A list of FireEquipment objects representing the fire safety equipment of the home.
     *         If no fire equipment is found, the list will be empty.
     */
    List<FireEquipment> getHomeFireEquipmentsByHomeId(int homeId);

    /**
     * Retrieves the home image via home id
     * @param homeId The ID of the home which you wanna retrieve its image
     * @return A list of home image of house you wanna retrieve
     */
    List<HomeImage> getHomeImagesByHomeId(int homeId);


    List<Home> getSimilarHomes(int homeId);

    List<Home> getHomesByWard(int homeId, int priceDifference);

    List<Home> getHomesByDistrict(int homeId, int priceDifference);


    List<Home> getHomesByProvince(int homeId);

    List<Home> getSimilarHomess(int homeId, int priceDifference);

    List<Price> getSimilarHomePrices(List<Home> similarHomes);
}