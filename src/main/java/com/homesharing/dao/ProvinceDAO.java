package com.homesharing.dao;

import com.homesharing.model.Province;
import java.util.List;

public interface ProvinceDAO {
    List<Province> getAllProvinces();
    Province getProvinceById(int id);
}
