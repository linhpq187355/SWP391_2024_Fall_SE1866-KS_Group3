package com.homesharing.dao.impl;

import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.HomeTypeDAO;
import com.homesharing.dao.PageVisitDAO;
import com.homesharing.dao.ProvinceDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Home;
import com.homesharing.model.PageVisit;
import com.homesharing.model.ProvinceHomeStat;
import com.homesharing.model.User;
import com.homesharing.service.StatisticService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class StatisticServiceImpl implements StatisticService {
    private static final Logger logger = Logger.getLogger(StatisticServiceImpl.class.getName());
    private final HomeDAO homeDAO;
    private final HomeTypeDAO homeTypeDAO;
    private final PageVisitDAO pageVisitDAO;
    private final UserDAO userDAO;
    private final ProvinceDAO provinceDAO;

    public StatisticServiceImpl() {
        this.homeDAO = new HomeDAOImpl();
        this.homeTypeDAO = new HomeTypeDAOImpl();
        this.pageVisitDAO = new PageVisitDAOImpl();
        this.userDAO = new UserDAOImpl();
        this.provinceDAO = new ProvinceDAOImpl();
    }

    @Override
    public int getTotalHomes() {
        return homeDAO.getTotalHome();
    }

    @Override
    public int countHomesByStatus(String status) {
        return homeDAO.countHomesByStatus(status);
    }

    @Override
    public int countHomesInMonth() {
        return homeDAO.countHomesInMonth();
    }

    @Override
    public float avgLeaseDuration() {
        return homeDAO.avgLeaseDuration();
    }

    @Override
    public int countMoveInDateInMonth() {
        return homeDAO.countMoveInDateInMonth();
    }

    @Override
    public String popularHomeType() {
        return homeTypeDAO.countPopularHomeType();
    }

    @Override
    public List<Home> getLatestHomes() {
        List<Home> homeList = new ArrayList<>();

        try {
            // Call the DAO method to retrieve new homes
            homeList = homeDAO.getLatestHomes(8);
            for (Home h : homeList) {
                List<String> image = new ArrayList<>();
                image.add(homeDAO.fetchFirstImage(h.getId()));
                h.setImages(image);
            }
        } catch (GeneralException e) {
            logger.severe("Error while fetching new homes: " + e.getMessage());
            // Optionally, you can throw a custom exception or handle it as needed
            throw new GeneralException("Service layer: Unable to retrieve new homes", e);
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            logger.severe("Unexpected error in service layer: " + e.getMessage());
            throw new GeneralException("Service layer: Unexpected error", e);
        }

        return homeList;
    }

    @Override
    public List<PageVisit> getPageVisit() {
        return pageVisitDAO.getPageVisits();
    }

    @Override
    public Map<String, Double> avgPreferenceByHost() {
        return userDAO.calculateAveragePreferences("host");
    }

    @Override
    public Map<String, Double> avgPreferenceByTenant() {
        return userDAO.calculateAveragePreferences("tenant");
    }

    @Override
    public List<ProvinceHomeStat> provinceRanking() {
        return provinceDAO.getProvincesWithHomeStats();
    }

    @Override
    public List<User> getLatestUsers() {
        return userDAO.getLatestUser();
    }


    @Override
    public int getTotalAccounts() {
        return 0;
    }

    @Override
    public int getTotalBlogs() {
        return 0;
    }

    @Override
    public int getTotalAppointments() {
        return 0;
    }
}
