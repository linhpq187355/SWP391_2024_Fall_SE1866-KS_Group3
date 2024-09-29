package com.homesharing.service.impl;
import com.homesharing.dao.*;
import com.homesharing.dao.impl.*;
import com.homesharing.model.*;
import com.homesharing.service.HomePageService;

import java.util.List;

public class HomePageServiceImpl implements HomePageService {
    private final HomeDAO homeDAO;
    private final PriceDAO priceDAO;


    public HomePageServiceImpl() {
        this.homeDAO = new HomeDAOImpl();
        this.priceDAO = new PriceDAOImpl();
    }

    @Override
    public List<Home> getHomes() {
        return homeDAO.getAllHomes();
    }


    @Override
    public List<Price> getHomePrice(List<Home> homes) {
        return priceDAO.getPrice(homes);
    }

    @Override
    public void addHome(Home home) { homeDAO.saveHome(home);}

    @Override
    public Home getHomeById(int id) { return homeDAO.getHomeById(id); }
}
