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
    List<Ward> getWardByDistrict(int districtId);
    List<District> getDistrictByProvince(int provinceId);
    int saveHome(Home home);
    int savePrice(Price price);
    int saveHomeImages(HomeImage homeImage);
    int saveAmentityHome(AmentityHome amentityHome);
    int saveFireEquipHome(FireEquipmentsHome fireEquipmentsHome);
}
