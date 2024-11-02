package com.homesharing.dao;
import com.homesharing.model.AmentityHome;
public interface AmentityHomeDAO {
    /**
     * Save the ids of home and amentity
     * @param amentityHome
     * @return result by number
     */
    int save(AmentityHome amentityHome);

    /**
     * Delete the certain amentityHome data based on home id
     * @param homeId
     * @return
     */
    int clear(int homeId);
}