package com.homesharing.dao;

import com.homesharing.model.Home;
import com.homesharing.model.Price;

import java.util.List;

public interface PriceDAO {
    List<Price> getPrice(List<Home> homes);
}
