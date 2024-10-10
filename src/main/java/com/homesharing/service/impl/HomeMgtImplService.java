package com.homesharing.service.impl;

import com.homesharing.dao.HomeDAO;
import com.homesharing.model.Home;
import com.homesharing.service.HomeMgtService;
import java.util.List;

public class HomeMgtImplService implements HomeMgtService {
    private final HomeDAO homeDAO;

    public HomeMgtImplService(HomeDAO homeDAO) {
        this.homeDAO = homeDAO;
    }

    @Override
    public List<Home> getPersonalHome(int userId) {
        return homeDAO.getHomesByUserId(userId);
    }

    @Override
    public int updatePersonalHome(Home home) {
        return homeDAO.updateHome(home);
    }

    @Override
    public int deletePersonalHome(Home home) {
        return homeDAO.changeStatus(home.getId(),"inactive");
    }
}
