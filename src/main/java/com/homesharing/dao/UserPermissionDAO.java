package com.homesharing.dao;

import com.homesharing.model.Permission;
import com.homesharing.model.UserPermission;

import java.util.List;

public interface UserPermissionDAO {
    /**
     * Fetch the list of permission given by user id
     * @param userId
     * @return list of permission
     */
    List<Permission> getPermissionsByUserId(int userId);

    /**
     * Save the user permission info
     * @param permission
     * @return operation status is successful or not
     */
    int save(UserPermission permission);

    /**
     * Clear all the permission via user id
     * @param userId
     * @return operation status is successful or not
     */
    int clear(int userId);

    /**
     * Check if the given permission is collision or not
     * @param permission
     * @return true or false
     */
    boolean exists(UserPermission permission);
}
