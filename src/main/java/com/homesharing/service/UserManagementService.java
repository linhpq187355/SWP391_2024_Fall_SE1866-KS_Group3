package com.homesharing.service;

import com.homesharing.model.Permission;
import com.homesharing.model.Role;
import com.homesharing.model.User;

import java.util.List;

public interface UserManagementService {
    /**
     * Fetch the info of all user
     * @return
     */
    List<User> getAllUsers();

    /**
     * Fetch all current role
     * @return
     */
    List<Role> getAllRoles();

    /**
     * Update the user status based on user id and status
     * @param userId
     * @param status
     */
    void updateUserStatus(int userId, String status);

    /**
     * Fetch a user info by id
     * @param userId
     * @return User object
     */
    User getUserById(int userId);

    /**
     * Fetch permissions via user id
     * @param userId
     * @return the permission list
     */
    List<Permission> fetchUserPermissions(int userId);
    

    /**
     * Fetch permissions via email
     * @param email
     * @return permission list
     */
    List<Permission> fetchPermissionsByEmail(String email);

    /**
     * Fetch all the permission in the system
     * @return the permission list
     */
    List<Permission> fetchAllPermisison();

    /**
     * Fetch the permission via name
     * @param permissionName
     * @return the permission obj
     */
    Permission fetchPermissionByName(String permissionName);


    /**
     * Update permissions of a user given by a id
     * @param permissions
     * @param userId
     */
    void updateUserPermission(List<Permission> permissions, int userId);
}
