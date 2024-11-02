package com.homesharing.service.impl;

import com.homesharing.dao.PermissionDAO;
import com.homesharing.dao.RoleDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.UserPermissionDAO;
import com.homesharing.dao.impl.PermissionDAOImpl;
import com.homesharing.dao.impl.RoleDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.dao.impl.UserPermDAOImpl;
import com.homesharing.model.Permission;
import com.homesharing.model.Role;
import com.homesharing.model.User;
import com.homesharing.model.UserPermission;
import com.homesharing.service.UserManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserManagementServiceImpl implements UserManagementService {
    private static final Logger logger = LoggerFactory.getLogger(UserManagementServiceImpl.class);
    private final UserDAO userDao;
    private final RoleDAO roleDao;
    private final PermissionDAO permDAO;
    private final UserPermissionDAO userPermissionDAO;

    public UserManagementServiceImpl() {
        this.userDao = new UserDAOImpl();
        this.roleDao = new RoleDAOImpl();
        this.permDAO = new PermissionDAOImpl();
        this.userPermissionDAO = new UserPermDAOImpl();
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

    /**
     * Fetch permissions via user id
     *
     * @param userId
     * @return the permission list
     */
    @Override
    public List<Permission> fetchUserPermissions(int userId) {
        return userPermissionDAO.getPermissionsByUserId(userId);
    }

    /**
     * Fetch all the permission in the system
     *
     * @return the permission list
     */
    @Override
    public List<Permission> fetchAllPermisison() {
        return permDAO.getAll();
    }

    /**
     * Fetch the permission via name
     *
     * @param permissionName
     * @return the permission obj
     */
    @Override
    public Permission fetchPermissionByName(String permissionName) {
        return permDAO.fetchByName(permissionName);
    }

    /**
     * Update permissions of a user given by their id
     *
     * @param permissions
     * @param userId
     */
    @Override
    public void updateUserPermission(List<Permission> permissions, int userId) {
        if (permissions == null || permissions.isEmpty()) {
            logger.warn("No permissions provided for user ID: {}", userId);
            return; // Early exit if no permissions are provided
        }
        try {
            if(!userPermissionDAO.getPermissionsByUserId(userId).isEmpty()) {
                userPermissionDAO.clear(userId);
            }
            for (Permission permission : permissions) {
                if (permission == null) {
                    logger.warn("Skipping null permission for user ID: {}", userId);
                    continue;
                }
                UserPermission userPermission = new UserPermission();
                userPermission.setUserId(userId);
                userPermission.setPermissonId(permission.getId());

                try {
                    userPermissionDAO.save(userPermission);
                } catch (Exception e) {
                    logger.error("Failed to save permission ID: {} for user ID: {}. Error: {}", permission.getId(), userId, e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to update permissions for user ID: {}. Error: {}", userId, e.getMessage(), e);
        }
    }
}
