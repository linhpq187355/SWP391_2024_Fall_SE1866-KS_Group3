package com.homesharing.service;

import com.homesharing.model.Home;
import com.homesharing.model.Price;

import java.util.List;

public interface HomeDetailService {
    Home getHomeById(int homeId); // Fetch a single home by ID
    List<Price> getHomePrice(List<Home> homes); // Fetch prices for a list of homes
}