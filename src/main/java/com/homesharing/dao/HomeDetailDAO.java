package com.homesharing.dao;

import com.homesharing.model.Home;

public interface HomeDetailDAO {
    Home getHomeById(int homeId);
}
