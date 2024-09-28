package com.homesharing.service;

import com.homesharing.model.Home;
import com.homesharing.model.Price;

import java.util.List;

public interface HomePageService {
    List<Home> getHomes();
    List<Price> getHomePrice(List<Home> homes);
}
