package com.homesharing.service.impl;

import com.homesharing.dao.RoleDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.RoleDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.Role;
import com.homesharing.model.User;
import com.homesharing.service.UserManagementService;

import java.util.List;

public class UserManagementServiceImpl implements UserManagementService {

    private final UserDAO userDao;
    private final RoleDAO roleDao;

    public UserManagementServiceImpl() {
        this.userDao = new UserDAOImpl();
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
}
