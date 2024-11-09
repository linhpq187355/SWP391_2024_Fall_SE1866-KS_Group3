package com.homesharing.dao;

import com.homesharing.model.Province;
import com.homesharing.model.ProvinceHomeStat;

import java.util.List;

public interface ProvinceDAO {
    List<Province> getAllProvinces();
    Province getProvinceById(int id);
    List<ProvinceHomeStat> getProvincesWithHomeStats();
}
