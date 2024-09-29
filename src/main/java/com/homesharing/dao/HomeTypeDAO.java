package com.homesharing.dao;

import com.homesharing.model.HomeType;

import java.util.List;

public interface HomeTypeDAO {
    /**
     * Get all home types from db
     * @return the list of home type
     */
    List<HomeType> getAllHomeTypes();
}
