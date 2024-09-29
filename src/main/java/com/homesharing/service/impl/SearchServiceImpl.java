package com.homesharing.service.impl;

import com.homesharing.dao.SearchDAO;
import com.homesharing.dao.impl.SearchDAOimpl;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.service.SearchSevice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SearchServiceImpl implements SearchSevice {
    private final SearchDAO searchDAO;

    // Constructor that initializes the SearchDAO implementation.
    public SearchServiceImpl() {
        this.searchDAO = new SearchDAOimpl();
    }

    /**
     * Searches for homes using the provided name.
     *
     * @param name The name or keyword to search for.
     * @return A list of Home objects that match the search criteria.
     */
    @Override
    public List<Home> searchHomesByName(String name) {
        return searchDAO.searchHomesByName(name);
    }

    /**
     * Searches for homes within a specified price range.
     *
     * @param minPrice The minimum price of the homes to find.
     * @param maxPrice The maximum price of the homes to find.
     * @return A list of Home objects that fall within the specified price range.
     */
    @Override
    public List<Home> searchByPriceRange(int minPrice, int maxPrice) {
        return searchDAO.searchByPriceRange(minPrice, maxPrice);
    }

    /**
     * Gets the maximum price of homes available in the database.
     *
     * @return The highest price of homes or -1 if no homes are available.
     * @throws IOException If there’s an input/output error.
     * @throws SQLException If there’s an issue with the database access.
     * @throws ClassNotFoundException If the database driver class isn’t found.
     */
    @Override
    public int getMaxPrice() throws IOException, SQLException, ClassNotFoundException {
        return searchDAO.getMaxPrice();
    }

    /**
     * Retrieves the price of a specific home by its ID.
     *
     * @param homeId The ID of the home whose price you want to fetch.
     * @return The Price object associated with the specified home ID, or null if not found.
     * @throws SQLException If there’s an issue with the database access.
     * @throws ClassNotFoundException If the database driver class isn’t found.
     */
    @Override
    public Price getPriceByHomeId(int homeId) throws SQLException, ClassNotFoundException {
        return searchDAO.getPriceByHomeId(homeId);
    }
}

