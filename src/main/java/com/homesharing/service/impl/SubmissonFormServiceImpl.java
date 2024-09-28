package com.homesharing.service.impl;

import com.homesharing.dao.DistrictDAO;
import com.homesharing.dao.HomeTypeDAO;
import com.homesharing.dao.ProvinceDAO;
import com.homesharing.dao.WardDAO;
import com.homesharing.dao.impl.*;
import com.homesharing.model.District;
import com.homesharing.model.HomeType;
import com.homesharing.model.Province;
import com.homesharing.model.Ward;
import com.homesharing.service.SubmissionFormService;

import java.util.List;

public class SubmissonFormServiceImpl implements SubmissionFormService {
    private final ProvinceDAO provinceDAO;
    private final DistrictDAO districtDAO;
    private final WardDAO wardDAO;
    private final HomeTypeDAO homeTypeDAO;

    public SubmissonFormServiceImpl() {
        this.provinceDAO = new ProvinceDAOImpl();
        this.districtDAO = new DistrictDAOImpl();
        this.wardDAO = new WardDAOImpl();
        this.homeTypeDAO = new HomeTypeDAOImpl();
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
}
