package com.homesharing.dao;

import com.homesharing.model.Home;
import com.homesharing.model.Price;

import java.util.List;

/**
 * PriceDAO interface provides the methods to interact with the price data related to homes.
 */
public interface PriceDAO {

    /**
     * Retrieves a list of prices for the given list of homes.
     *
     * @param homes a list of Home objects for which the prices are to be retrieved
     * @return a list of Price objects corresponding to the given homes
     */
    List<Price> getPrices(List<Home> homes);
    int savePrice(Price price);

    int getMinPrice();
    int getMaxPrice();

}