package com.homesharing.service.impl;

import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.User;
import com.homesharing.service.UserManagementService;

import java.util.List;

public class UserManagementServiceImpl implements UserManagementService {

    private UserDAO userDao;

    public UserManagementServiceImpl() {
        this.userDao = new UserDAOImpl();
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
