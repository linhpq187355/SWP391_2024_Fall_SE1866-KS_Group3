package com.homesharing.service.impl;

import com.homesharing.dao.UserDao;
import com.homesharing.dao.impl.UserDaoImpl;
import com.homesharing.model.User;
import com.homesharing.service.UserManagementService;

import java.util.List;

public class UserManagementServiceImpl implements UserManagementService {

    private UserDao userDao;

    public UserManagementServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
