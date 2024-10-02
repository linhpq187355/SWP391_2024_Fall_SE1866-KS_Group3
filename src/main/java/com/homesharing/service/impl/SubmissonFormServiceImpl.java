package com.homesharing.service.impl;

import com.homesharing.dao.*;
import com.homesharing.dao.impl.*;
import com.homesharing.model.*;
import com.homesharing.service.SubmissionFormService;

import java.util.List;

public class SubmissonFormServiceImpl implements SubmissionFormService {
    private final HomeDAO homeDAO;
    private final ProvinceDAO provinceDAO;
    private final DistrictDAO districtDAO;
    private final WardDAO wardDAO;
    private final HomeTypeDAO homeTypeDAO;
    private final AmentityDAO amentityDAO;
    private final FireEquipmentDAO fireEquipmentDAO;
//    private final HomeImageDAO homeImageDAO;
    private final AmentityHomeDAO amentityHomeDAO;

    public SubmissonFormServiceImpl() {
        this.provinceDAO = new ProvinceDAOImpl();
        this.districtDAO = new DistrictDAOImpl();
        this.wardDAO = new WardDAOImpl();
        this.homeTypeDAO = new HomeTypeDAOImpl();
        this.amentityDAO = new AmentityDAOImpl();
        this.fireEquipmentDAO = new FireEquipmentDAOImpl();
//        this.homeImageDAO = new HomeImageDAOImp();
        this.homeDAO = new HomeDAOImpl();
        this.amentityHomeDAO = new AmentityHomeDAOImpl();
    }

    @Override
    public List<Province> getProvinces() {
        return provinceDAO.getAllProvinces();
    }

    @Override
    public List<District> getDistricts() {
        return districtDAO.getAllDistricts();
    }

    @Override
    public List<Ward> getWards() {
        return wardDAO.getAllWards();
    }

    @Override
    public List<HomeType> getHomeTypes() {
        return homeTypeDAO.getAllHomeTypes();
    }

    @Override
    public List<Amentity> getAmentities() {
        return amentityDAO.getAllAmentities();
    }

    @Override
    public List<FireEquipment> getFireEquipments() {
        return fireEquipmentDAO.getAllFireEquipments();
    }

    @Override
    public List<Ward> getWardByDistrict(int districtId) {
        return wardDAO.getWardsByDistrictId(districtId);
    }

    @Override
    public List<District> getDistrictByWard(int wardId) {
        return districtDAO.getDistrictByProvinceId(wardId);
    }

    @Override
    public int saveHome(Home home) {
        return homeDAO.saveHome(home);
    }

//    @Override
//    public int saveHomeImages(HomeImage homeImage) {
//        return homeImageDAO.save(homeImage);
//    }

    @Override
    public int saveAmentityHome(AmentityHome amentityHome) {
        return amentityHomeDAO.save(amentityHome);
    }
}
