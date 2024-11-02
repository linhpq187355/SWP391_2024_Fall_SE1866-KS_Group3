package com.homesharing.dao;

import com.homesharing.model.Permission;

import java.util.List;

public interface PermissionDAO {
    List<Permission> getAll();
    Permission fetchByName(String name);
    Permission fetchById(int id);
}
