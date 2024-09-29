package com.homesharing.dao;

import com.homesharing.model.Home;

import java.util.List;

public interface SearchDAO {
    List<Home> searchHomesByName(String name);
}
