package com.homesharing.service.impl;

import com.homesharing.dao.AmentityDAO;
import com.homesharing.dao.AmentityHomeDAO;
import com.homesharing.dao.DistrictDAO;
import com.homesharing.dao.FireEquipHomeDAO;
import com.homesharing.dao.FireEquipmentDAO;
import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.HomeImageDAO;
import com.homesharing.dao.HomeTypeDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.ProvinceDAO;
import com.homesharing.dao.WardDAO;
import com.homesharing.dao.impl.AmentityDAOImpl;
import com.homesharing.dao.impl.AmentityHomeDAOImpl;
import com.homesharing.dao.impl.DistrictDAOImpl;
import com.homesharing.dao.impl.FireEquipHomeImpl;
import com.homesharing.dao.impl.FireEquipmentDAOImpl;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.HomeImageDAOImp;
import com.homesharing.dao.impl.HomeTypeDAOImpl;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.dao.impl.ProvinceDAOImpl;
import com.homesharing.dao.impl.WardDAOImpl;
import com.homesharing.model.Amentity;
import com.homesharing.model.AmentityHome;
import com.homesharing.model.District;
import com.homesharing.model.FireEquipment;
import com.homesharing.model.FireEquipmentsHome;
import com.homesharing.model.Home;
import com.homesharing.model.HomeImage;
import com.homesharing.model.HomeType;
import com.homesharing.model.Price;
import com.homesharing.model.Province;
import com.homesharing.model.Ward;
import com.homesharing.service.SubmissionFormService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SubmissonFormServiceImpl implements SubmissionFormService {
    private final HomeDAO homeDAO;
    private final ProvinceDAO provinceDAO;
    private final DistrictDAO districtDAO;
    private final WardDAO wardDAO;
    private final HomeTypeDAO homeTypeDAO;
    private final AmentityDAO amentityDAO;
    private final FireEquipmentDAO fireEquipmentDAO;
    private final HomeImageDAO homeImageDAO;
    private final AmentityHomeDAO amentityHomeDAO;
    private final FireEquipHomeDAO fireEquipHomeDAO;
    private final PriceDAO priceDAO;

    public SubmissonFormServiceImpl() {
        this.provinceDAO = new ProvinceDAOImpl();
        this.districtDAO = new DistrictDAOImpl();
        this.wardDAO = new WardDAOImpl();
        this.homeTypeDAO = new HomeTypeDAOImpl();
        this.amentityDAO = new AmentityDAOImpl();
        this.fireEquipmentDAO = new FireEquipmentDAOImpl();
        this.homeImageDAO = new HomeImageDAOImp();
        this.homeDAO = new HomeDAOImpl();
        this.amentityHomeDAO = new AmentityHomeDAOImpl();
        this.fireEquipHomeDAO = new FireEquipHomeImpl();
        this.priceDAO = new PriceDAOImpl();
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
    public List<District> getDistrictByProvince(int provinceId) {
        return districtDAO.getDistrictByProvinceId(provinceId);
    }

    @Override
    public int saveHome(Home home) throws SQLException, IOException, ClassNotFoundException {
        return homeDAO.saveHome(home);
    }

    @Override
    public int savePrice(Price price) {
        return priceDAO.savePrice(price);
    }

    @Override
    public int saveHomeImages(HomeImage homeImage) {
        return homeImageDAO.save(homeImage);
    }

    @Override
    public int saveAmentityHome(AmentityHome amentityHome) {
        return amentityHomeDAO.save(amentityHome);
    }

    @Override
    public int saveFireEquipHome(FireEquipmentsHome fireEquipmentsHome) {
        return fireEquipHomeDAO.save(fireEquipmentsHome);
    }
}
