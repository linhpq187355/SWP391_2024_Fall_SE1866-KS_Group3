package com.homesharing.dao;

import com.homesharing.model.Home;

public interface HomeDAO {
    /**
     * Save home's info to the database
     * @param home Home object that need to be saved
     * @return homeId
     */
    int saveHome(Home home);


}
