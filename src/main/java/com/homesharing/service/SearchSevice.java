package com.homesharing.service;

import com.homesharing.model.Home;
import com.homesharing.model.Price;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface SearchSevice {
    List<Home> searchHomesByName(String name);
    List<Home> searchByPriceRange(int minPrice, int maxPrice);
    int getMaxPrice() throws IOException, SQLException, ClassNotFoundException;
    Price getPriceByHomeId(int homeId) throws SQLException, ClassNotFoundException;

}
