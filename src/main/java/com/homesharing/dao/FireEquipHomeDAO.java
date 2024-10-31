package com.homesharing.dao;

import com.homesharing.model.FireEquipmentsHome;

public interface FireEquipHomeDAO {
    int save(FireEquipmentsHome fireEquipmentsHome);
    int clear(int homeId);
}
