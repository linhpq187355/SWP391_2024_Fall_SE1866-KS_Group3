package com.homesharing.dao;

import com.homesharing.model.Home;

import java.util.List;

public interface SearchDAO {
    // Method to search homes by name
    List<Home> searchHomesByName(String name);
}
