package com.homesharing.dao;

import com.homesharing.model.PageVisit;

import java.util.List;

public interface PageVisitDAO {
    void incrementTotalVisit(String url);
    void incrementMemberVisit(String url);
    List<PageVisit> getPageVisits();
}
