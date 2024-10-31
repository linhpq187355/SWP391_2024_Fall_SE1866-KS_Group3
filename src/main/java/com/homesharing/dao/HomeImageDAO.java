package com.homesharing.dao;

import com.homesharing.model.Home;
import com.homesharing.model.HomeImage;

import java.util.List;

public interface HomeImageDAO {
    /**
     * get all images via home id
     * @param homeId
     * @return image list
     */
    List<HomeImage> getImgByHomeId(int homeId);

    /**
     * Get the images via the given homes
     * @param homes
     * @return image list
     */
    List<HomeImage> getImgByHomes(List<Home> homes);

    /**
     * save the given image
     * @param image
     * @return home img id indicate that the task is successful or not
     */
    int save(HomeImage image);

    /**
     * Clear all the home image based on the home id
     * @param HomeId
     * @return int status indicate that the task is successful or not
     */
    int clear(int HomeId);
}
