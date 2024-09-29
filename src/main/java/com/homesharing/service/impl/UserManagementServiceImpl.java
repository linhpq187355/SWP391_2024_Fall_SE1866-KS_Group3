package com.homesharing.service.impl;

import com.homesharing.dao.RoleDAO;
import com.homesharing.dao.UserDao;
import com.homesharing.dao.impl.RoleDAOImpl;
import com.homesharing.dao.impl.UserDaoImpl;
import com.homesharing.model.Role;
import com.homesharing.model.User;
import com.homesharing.service.UserManagementService;

import java.util.List;

public class UserManagementServiceImpl implements UserManagementService {

    private final UserDao userDao;
    private final RoleDAO roleDao;

    public UserManagementServiceImpl() {
        this.userDao = new UserDaoImpl();
        this.roleDao = new RoleDAOImpl();
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public void updateUserStatus(int userId, String status) {
        this.userDao.updateUserStatus(userId, status);
    }

    @Override
    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }
}
