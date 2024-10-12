package com.homesharing.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.homesharing.model.Home;

public interface HomeDAO {
    /**
     *
     * @return the list of homes
     */
    List<Home> getAllHomes();

    /**
     * Save home's info to the database
     * @param home Home object that need to be saved
     * @return home id
     */
    int saveHome(Home home);

    /**
     * Update home info
     * @param home
     * @return home id indicate the task is success or not
     */
    int updateHome(Home home);

    /**
     * Change home status
     * @param status
     * @return home id indicate the success of the task or not
     */
    int changeStatus(int homeId, String status);

    /**
     * Retrieve new homes' info from the database.
     * @return List of Home objects.
     */
    List<Home> getNewHomes() throws SQLException, IOException, ClassNotFoundException;

    /**
     * Get home object via id
     * @param id
     * @return the home that need to be found
     */
    Home getHomeById(int id);

    /**
     * Get home object via author id
     * @param userId
     * @return
     */
    List<Home> getHomesByUserId(int userId);
}
