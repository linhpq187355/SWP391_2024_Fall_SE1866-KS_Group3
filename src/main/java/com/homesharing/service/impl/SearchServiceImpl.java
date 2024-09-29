package com.homesharing.service.impl;

import com.homesharing.dao.SearchDAO;
import com.homesharing.dao.impl.SearchDAOimpl;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.service.SearchSevice;

import java.util.List;

public class SearchServiceImpl implements SearchSevice {
    private final SearchDAO searchDAO;

    public SearchServiceImpl() {
        this.searchDAO = new SearchDAOimpl();
    }

    @Override
    public List<Home> searchHomesByName(String name) {
        return searchDAO.searchHomesByName(name);
    }
}
