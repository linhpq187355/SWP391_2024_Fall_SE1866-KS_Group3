package com.homesharing.service;

import com.homesharing.model.District;
import com.homesharing.model.HomeType;
import com.homesharing.model.Province;
import com.homesharing.model.Ward;

import java.util.List;

public interface SubmissionFormService {
    List<Province> getProvinces();
    List<District> getDistricts();
    List<Ward> getWards();
    List<HomeType> getHomeTypes();
}
