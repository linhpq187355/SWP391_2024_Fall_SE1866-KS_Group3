package com.homesharing.dao;

import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.model.User;

import java.util.List;

public interface HomeDetailDAO {
    Home getHomeById(int id);
    List<Price> getHomePricesByHomeId(int homeId);
    User getCreatorByHomeId(int homeId);
}
