package com.homesharing.service;

import com.homesharing.model.Home;
import com.homesharing.model.PageVisit;
import com.homesharing.model.ProvinceHomeStat;
import com.homesharing.model.User;

import java.util.List;
import java.util.Map;

public interface StatisticService {
    int getTotalHomes();
    int countHomesByStatus(String status);
    int countHomesInMonth();
    float avgLeaseDuration();
    int countMoveInDateInMonth();
    String popularHomeType();
    List<Home> getLatestHomes();
    List<PageVisit> getPageVisit();

    Map<String,Double> avgPreferenceByHost();
    Map<String,Double> avgPreferenceByTenant();
    List<ProvinceHomeStat> provinceRanking();

    List<User> getLatestUsers();

    int getTotalAccounts();
    int getTotalBlogs();
    int getTotalAppointments();

}
