package com.homesharing.service.impl;

import com.homesharing.dao.PreferenceDAO;
import com.homesharing.dao.UserDao;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.dao.impl.UserDaoImpl;
import com.homesharing.model.Preference;
import com.homesharing.model.User;
import com.homesharing.service.UserProfileService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class UserProfileServiceImpl implements UserProfileService {
    private PreferenceDAO preferenceDAO;
    private UserDao userDao;

    public UserProfileServiceImpl() {
        this.preferenceDAO = new PreferenceDAOImpl();
        this.userDao = new UserDaoImpl();
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
