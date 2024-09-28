package com.homesharing.service;

import com.homesharing.model.Home;

import java.util.List;

public interface SearchSevice {
    List<Home> searchHomesByName(String name);
}
