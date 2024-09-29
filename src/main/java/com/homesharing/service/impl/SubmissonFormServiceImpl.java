package com.homesharing.service.impl;

import com.homesharing.dao.*;
import com.homesharing.dao.impl.*;
import com.homesharing.model.*;
import com.homesharing.service.SubmissionFormService;

import java.util.List;

public class SubmissonFormServiceImpl implements SubmissionFormService {
    private final ProvinceDAO provinceDAO;
    private final DistrictDAO districtDAO;
    private final WardDAO wardDAO;
    private final HomeTypeDAO homeTypeDAO;
    private final AmentityDAO amentityDAO;
    private final FireEquipmentDAO fireEquipmentDAO;

    public SubmissonFormServiceImpl() {
        this.provinceDAO = new ProvinceDAOImpl();
        this.districtDAO = new DistrictDAOImpl();
        this.wardDAO = new WardDAOImpl();
        this.homeTypeDAO = new HomeTypeDAOImpl();
        this.amentityDAO = new AmentityDAOImpl();
        this.fireEquipmentDAO = new FireEquipmentDAOImpl();
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
}
