package com.homesharing.service;

import com.homesharing.model.District;
import com.homesharing.model.Province;
import com.homesharing.model.Ward;

import java.util.List;

public interface LocationService {
    Province getProvince(int id);
    District getDistrict(int id);
    Ward getWard(int id);
    List<Province> getProvinces();
}
