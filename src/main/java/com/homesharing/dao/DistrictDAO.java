package com.homesharing.dao;

import com.homesharing.model.District;
import java.util.List;

public interface DistrictDAO {
    /**
     * Fetch all the districts in Vietnam
     * @return district list
     */
    List<District> getAllDistricts();

    /**
     * Fetch a district info based on the given id
     * @param id of the district you want to find
     * @return district that you want to find
     */
    District getDistrictById(int id);

    /**
     * Return districts list via province id
     * @param provinceId of the given province
     * @return district list belong to a certain province
     */
    List<District> getDistrictByProvinceId(int provinceId);
}
