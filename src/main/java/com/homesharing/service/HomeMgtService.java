package com.homesharing.service;

import com.homesharing.model.Home;

import java.util.List;

public interface HomeMgtService {
    List<Home> getPersonalHome(int userId);
    int updatePersonalHome(Home home);
    int deletePersonalHome(Home home);
}
