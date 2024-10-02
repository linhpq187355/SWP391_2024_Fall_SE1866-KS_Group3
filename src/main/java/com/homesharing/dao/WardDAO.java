package com.homesharing.dao;

import com.homesharing.model.Ward;

import java.util.List;

public interface WardDAO {
    List<Ward> getAllWards();

    /**
     * Get ward list based the given district id
     * @param districtId
     * @return the ward list
     */
    List<Ward> getWardsByDistrictId(int districtId);

    /**
     * Get a ward info based on given id
     * @param id
     * @return ward that you want to find
     */
    Ward getWardById(int id);
}
