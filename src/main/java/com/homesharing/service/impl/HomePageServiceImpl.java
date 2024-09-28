package com.homesharing.service.impl;
import com.homesharing.dao.*;
import com.homesharing.dao.impl.*;
import com.homesharing.model.*;
import com.homesharing.service.HomePageService;

import java.util.List;

public class HomePageServiceImpl implements HomePageService {
    private final HomeDAO homeDAO;
    private final PriceDAO priceDAO;
    private final ProvinceDAO provinceDAO;
    private final DistrictDAO districtDAO;
    private final WardDAO wardDAO;

    public HomePageServiceImpl() {
        this.homeDAO = new HomeDAOImpl();
        this.priceDAO = new PriceDAOImpl();
        this.provinceDAO = new ProvinceDAOImpl();
        this.districtDAO = new DistrictDAOImpl();
        this.wardDAO = new WardDAOImpl();
    }

    @Override
    public List<Home> getHomes() {
        return homeDAO.getAllHomes();
    }

    @Override
    public List<Province> getProvinces() {
        return provinceDAO.getAllProvinces();
    }

    @Override
    public List<District> getDistricts() {
        return districtDAO.getAllDistricts();
    }

    @Override
    public List<Ward> getWards() {
        return wardDAO.getAllWards();
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
