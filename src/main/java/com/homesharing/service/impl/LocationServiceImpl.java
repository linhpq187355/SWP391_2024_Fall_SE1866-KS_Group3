package com.homesharing.service.impl;

import com.homesharing.dao.DistrictDAO;
import com.homesharing.dao.ProvinceDAO;
import com.homesharing.dao.WardDAO;
import com.homesharing.model.District;
import com.homesharing.model.Province;
import com.homesharing.model.Ward;
import com.homesharing.service.LocationService;

import java.util.List;

public class LocationServiceImpl implements LocationService {
    private final ProvinceDAO provinceDAO;
    private final DistrictDAO districtDAO;
    private final WardDAO wardDAO;

    public LocationServiceImpl(ProvinceDAO provinceDAO, DistrictDAO districtDAO, WardDAO wardDAO) {
        this.provinceDAO = provinceDAO;
        this.districtDAO = districtDAO;
        this.wardDAO = wardDAO;
    }

    @Override
    public Province getProvince(int id) {
        return provinceDAO.getProvinceById(id);
    }

    @Override
    public District getDistrict(int id) {
        return districtDAO.getDistrictById(id);
    }

    @Override
    public Ward getWard(int id) {
        return wardDAO.getWardById(id);
    }

    @Override
    public List<Province> getProvinces() {
        return provinceDAO.getAllProvinces();
    }
}
