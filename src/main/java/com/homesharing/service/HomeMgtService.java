package com.homesharing.service;

import com.homesharing.model.Home;
import com.homesharing.model.Price;

import java.util.List;
import java.util.Map;

public interface HomeMgtService {
    List<Home> getPersonalHome(int userId);
    int updatePersonalHome(Home home);
    int deletePersonalHome(Home home);
    Map<Integer, Price> getHomePrice(List<Home> homes);
}
