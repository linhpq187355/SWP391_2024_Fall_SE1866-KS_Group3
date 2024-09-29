package com.homesharing.service.impl;


import com.homesharing.dao.PreferenceDAO;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.Preference;
import com.homesharing.model.User;
import com.homesharing.dao.UserDAO;
import com.homesharing.service.UserUpdateProfileService;

public class UserUpdateProfileServiceImpl implements UserUpdateProfileService {

    private UserDAO userDAO;
    private PreferenceDAO preferenceDAO;

    public UserUpdateProfileServiceImpl() {
        this.userDAO = new UserDAOImpl();
        this.preferenceDAO = new PreferenceDAOImpl();
    }

    @Override
    public int updateUserProfile(User user) {
        return userDAO.updateUserProfile(user);
    }

    @Override
    public int updateUserPreference(Preference preference) {
        return preferenceDAO.updatePreference(preference);
    }

    @Override
    public int insertUserPreference(Preference preference) {
        return preferenceDAO.insertPreference(preference);
    }

    @Override
    public String getUserAvatar(int id) {
        return userDAO.getUserAvatar(id);
    }


}
