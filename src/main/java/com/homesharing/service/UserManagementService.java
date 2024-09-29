package com.homesharing.service;

import com.homesharing.model.Role;
import com.homesharing.model.User;

import java.util.List;

public interface UserManagementService {
    List<User> getAllUsers();
    List<Role> getAllRoles();
}
