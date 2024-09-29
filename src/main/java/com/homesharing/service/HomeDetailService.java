package com.homesharing.service;

import com.homesharing.model.Home;
import com.homesharing.model.HomeType;
import com.homesharing.model.Price;
import com.homesharing.model.User;

import java.util.List;

public interface HomeDetailService {
    Home getHomeById(int id);
    List<Price> getHomePricesByHomeId(int homeId);
    User getCreatorByHomeId(int homeId);
    List<HomeType> getHomeTypesByHomeId(int homeId);
}