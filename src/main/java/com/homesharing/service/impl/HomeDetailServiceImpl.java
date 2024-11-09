package com.homesharing.service.impl;

import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.HomeDetailDAO;
import com.homesharing.dao.HomeImageDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.HomeDetailDAOImpl;
import com.homesharing.dao.impl.HomeImageDAOImpl;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.model.*;
import com.homesharing.service.HomeDetailService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashSet;

public class HomeDetailServiceImpl implements HomeDetailService {
    private final HomeDetailDAO homeDetailDAO;
    private final PriceDAO priceDAO;
    private final HomeImageDAO homeImageDAO;
    private final HomeDAO homeDAO;

    // Constructor that initializes the HomeDetailDAO implementation.
    public HomeDetailServiceImpl() {
        this.homeDetailDAO = new HomeDetailDAOImpl();
        this.priceDAO = new PriceDAOImpl();
        this.homeImageDAO = new HomeImageDAOImpl();
        this.homeDAO = new HomeDAOImpl();
    }

    /**
     * Retrieves a Home object based on its unique ID.
     *
     * @param id The unique identifier of the home you want to get.
     * @return The Home object that corresponds to the provided ID, or null if not found.
     */
    @Override
    public Home getHomeById(int id) {
        return homeDetailDAO.getHomeById(id);
    }

    /**
     * Gets a list of prices associated with a specific home, identified by its ID.
     *
     * @param homeId The ID of the home for which you need the price details.
     * @return A list of Price objects corresponding to the specified home ID.
     */
    @Override
    public List<Price> getHomePricesByHomeId(int homeId) {
        return homeDetailDAO.getHomePricesByHomeId(homeId);
    }

    /**
     * Finds the creator (User) of a home using the home’s ID.
     *
     * @param homeId The ID of the home whose creator you want to retrieve.
     * @return The User object representing the creator of the home, or null if not found.
     */
    @Override
    public User getCreatorByHomeId(int homeId) {
        return homeDetailDAO.getCreatorByHomeId(homeId);
    }

    /**
     * Retrieves a list of home types associated with a specific home, identified by its ID.
     *
     * @param homeId The ID of the home for which you want to get the types.
     * @return A list of HomeType objects linked to the specified home ID.
     */
    @Override
    public List<HomeType> getHomeTypesByHomeId(int homeId) {
        return homeDetailDAO.getHomeTypesByHomeId(homeId);
    }

    @Override
    public HomeType getHomeTypeByHomeId(int homeId) {
        return homeDetailDAO.getHomeTypeByHomeId(homeId);
    }

    /**
     * Retrieves the home image via home id
     *
     * @param homeId The ID of the home which you wanna retrieve its image
     * @return A list of home image of house you wanna retrieve
     */
    @Override
    public List<HomeImage> getHomeImagesByHomeId(int homeId) {
        return homeImageDAO.getImgByHomeId(homeId);
    }

    /**
     * Retrieves a list of amenity associated with  home, identified by its ID.
     *
     * @param homeId The ID of the home for which you want to get the amenity.
     * @return A list of HomeType objects linked to the specified home ID.
     */
    @Override
    public List<Amentity> getHomeAmenitiesByHomeId(int homeId) {
        return homeDetailDAO.getHomeAmenitiesByHomeId(homeId);
    }

    /**
     * Retrieves a list of FireEquipment associated with a specific home, identified by its ID.
     *
     * @param homeId The ID of the home for which you want to get the types.
     * @return A list of HomeType objects linked to the specified home ID.
     */
    @Override
    public List<FireEquipment> getHomeFireEquipmentsByHomeId(int homeId) {
        return homeDetailDAO.getHomeFireEquipmentsByHomeId(homeId);
    }

    @Override
    public List<Home> getSimilarHomes(int homeId) {
        return homeDetailDAO.getSimilarHomes(homeId);
    }
    @Override
    public List<Home> getHomesByWard(int homeId, int priceDifference) {
        return homeDetailDAO.getHomesByWard(homeId, priceDifference);
    }
    @Override
    public List<Home> getHomesByDistrict(int homeId, int priceDifference) {
        return homeDetailDAO.getHomesByDistrict(homeId, priceDifference);
    }
    @Override
    public List<Home> getSimilarHomess(int homeId, int priceDifference) {
        Set<Integer> addedHomeIds = new HashSet<>();
        List<Home> similarHomes = new ArrayList<>();

        List<Home> wardHomes = getHomesByWard(homeId, priceDifference);
        for (Home home : wardHomes) {
            if (home.getId() != homeId && !addedHomeIds.contains(home.getId())) {
                try {
                    // Lấy ảnh đầu tiên
                    List<String> images = new ArrayList<>();
                    String firstImage = homeDAO.fetchFirstImage(home.getId());
                    if (firstImage != null) {
                        images.add(firstImage);
                    }
                    home.setImages(images);

                    similarHomes.add(home);
                    addedHomeIds.add(home.getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if (similarHomes.size() < 4) {
            List<Home> districtHomes = getHomesByDistrict(homeId, priceDifference);

            for (Home home : districtHomes) {
                if (home.getId() != homeId && !addedHomeIds.contains(home.getId())) {
                    try {
                        // Lấy ảnh đầu tiên
                        List<String> images = new ArrayList<>();
                        String firstImage = homeDAO.fetchFirstImage(home.getId());
                        if (firstImage != null) {
                            images.add(firstImage);
                        }
                        home.setImages(images);

                        similarHomes.add(home);
                        addedHomeIds.add(home.getId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (similarHomes.size() >= 4) {
                    break;
                }
            }
        }

        return similarHomes;
    }



    @Override
    public List<Price> getSimilarHomePrices(List<Home> similarHomes) {
        return priceDAO.getPrices(similarHomes);
    }

}