package com.homesharing.controller;

import com.homesharing.dao.impl.StatisticServiceImpl;
import com.homesharing.model.Home;
import com.homesharing.model.PageVisit;
import com.homesharing.model.ProvinceHomeStat;
import com.homesharing.model.User;
import com.homesharing.service.HomePageService;
import com.homesharing.service.impl.HomePageServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ViewDashboardServlet", value = "/dashboard")
public class ViewDashboardServlet extends HttpServlet {
    StatisticServiceImpl statisticService = new StatisticServiceImpl();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int totalHomes = statisticService.getTotalHomes();
        int activeHome = statisticService.countHomesByStatus("active");
        int pendingHome = statisticService.countHomesByStatus("pending");
        int inactiveHome = statisticService.countHomesByStatus("inactive");
        float avgLeaseDuration = statisticService.avgLeaseDuration();
        int homesInMonth = statisticService.countHomesInMonth();
        int homesMoveInDate = statisticService.countMoveInDateInMonth();
        String popularHomeType = statisticService.popularHomeType();
        List<Home> latestHomes = statisticService.getLatestHomes();

        List<PageVisit> pageVisits = statisticService.getPageVisit();

        Map<String, Double> avgHostPreference  = statisticService.avgPreferenceByHost();
        Map<String, Double> avgTenantPreference  = statisticService.avgPreferenceByTenant();

        String hostAvgJson = new Gson().toJson(avgHostPreference);
        String tenantAvgJson = new Gson().toJson(avgTenantPreference);

        List<ProvinceHomeStat> provinceRank = statisticService.provinceRanking();

        List<User> latestUsers = statisticService.getLatestUsers();

        request.setAttribute("hostAvg", hostAvgJson);
        request.setAttribute("tenantAvg", tenantAvgJson);

        request.setAttribute("totalHomes", totalHomes);
        request.setAttribute("activeHome", activeHome);
        request.setAttribute("inactiveHome", inactiveHome);
        request.setAttribute("pendingHome", pendingHome);
        request.setAttribute("avgLeaseDuration", avgLeaseDuration);
        request.setAttribute("homesInMonth", homesInMonth);
        request.setAttribute("homesMoveInDate", homesMoveInDate);
        request.setAttribute("popularHomeType", popularHomeType);
        request.setAttribute("latestHomes", latestHomes);
        request.setAttribute("provinceRank", provinceRank);
        request.setAttribute("latestUsers", latestUsers);

        request.setAttribute("pageVisits", pageVisits);
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}
