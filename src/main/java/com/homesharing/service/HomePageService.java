package com.homesharing.service;

import com.homesharing.model.*;

import java.util.List;

public interface HomePageService {
    List<Home> getNewHomes();
    List<Home> getHomes();
    List<Price> getHomePrice(List<Home> homes);
    void addHome(Home home);
    Home getHomeById(int id);
}
