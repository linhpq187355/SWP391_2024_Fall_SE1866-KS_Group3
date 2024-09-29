package com.homesharing.dao;

import com.homesharing.model.Home;
import com.homesharing.model.Price;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface SearchDAO {
    List<Home> searchHomesByName(String name);

    List<Home> searchByPriceRange(int minPrice, int maxPrice);


    int getMaxPrice() throws SQLException, IOException, ClassNotFoundException;

    Price getPriceByHomeId(int homeId) throws SQLException, ClassNotFoundException;


}
