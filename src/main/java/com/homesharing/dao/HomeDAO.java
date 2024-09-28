package com.homesharing.dao;

import com.homesharing.model.Home;

import java.util.List;

public interface HomeDAO {
    /**
     * Save home's info to the database
     * @param home Home object that need to be saved
     * @return homeId
     */
    int saveHome(Home home);

    /**
     * Retrieve new homes' info from the database.
     * @return List of Home objects.
     */

    List<Home> getNewHomes();
}
