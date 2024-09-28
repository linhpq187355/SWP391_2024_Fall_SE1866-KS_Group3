package com.homesharing.dao;

import com.homesharing.model.District;
import java.util.List;

public interface DistrictDAO {
    public List<District> getAllDistricts();
    /**
     * Return districts list via province id
     * @param provinceId
     * @return district list belong to a certain province
     */
    List<District> getDistrictByProvinceId(int provinceId);
}
