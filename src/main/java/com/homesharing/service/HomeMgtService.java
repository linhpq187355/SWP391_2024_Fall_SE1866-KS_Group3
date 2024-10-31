package com.homesharing.service;

import com.homesharing.model.District;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.model.Province;
import com.homesharing.model.Ward;

import java.util.List;
import java.util.Map;

public interface HomeMgtService {
    List<Home> getPersonalHome(int userId);
    int updatePersonalHome(Home home);
    int deletePersonalHome(int id);
    int clearImageByHomeId(int homeId);
    int clearAmentityByHomeId(int homeId);
    int clearFireEquipByHomeId(int homeId);
    Map<Integer, Price> getHomePrice(List<Home> homes);

}
