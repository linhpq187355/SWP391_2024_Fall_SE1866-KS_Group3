package com.homesharing.service;

import com.homesharing.model.*;

import java.util.List;

public interface SubmissionFormService {
    List<Province> getProvinces();
    List<District> getDistricts();
    List<Ward> getWards();
    List<HomeType> getHomeTypes();
    List<Amentity> getAmentities();
    List<FireEquipment> getFireEquipments();
}
