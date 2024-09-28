package com.homesharing.service.impl;
import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.service.HomePageService;

import java.util.List;

public class HomePageServiceImpl implements HomePageService {
    private HomeDAO homeDAO;
    private PriceDAO PriceDAO;

    public HomePageServiceImpl() {
        this.homeDAO = new HomeDAOImpl();
        this.PriceDAO = new PriceDAOImpl();
    }

    @Override
    public List<Home> getHomes() {
        return homeDAO.getNewHomes();
    }

    @Override
    public List<Price> getHomePrice(List<Home> homes) {
        return PriceDAO.getPrice(homes);
    }
}
