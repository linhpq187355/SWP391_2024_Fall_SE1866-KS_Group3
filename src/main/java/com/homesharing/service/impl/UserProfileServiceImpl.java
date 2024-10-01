package com.homesharing.service.impl;

import com.homesharing.dao.PreferenceDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.Preference;
import com.homesharing.model.User;
import com.homesharing.service.UserProfileService;

public class UserProfileServiceImpl implements UserProfileService {
    private PreferenceDAO preferenceDAO;
    private UserDAO userDao;

    public UserProfileServiceImpl() {
        this.preferenceDAO = new PreferenceDAOImpl();
        this.userDao = new UserDAOImpl();
    }

    @Override
    public User getUser(int userId) {
        return userDao.getUser(userId);
    }

    @Override
    public Preference getPreference(int userId) {
        return preferenceDAO.getPreference(userId);
    }
}
