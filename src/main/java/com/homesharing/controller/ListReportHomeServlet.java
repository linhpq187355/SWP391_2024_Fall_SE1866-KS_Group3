package com.homesharing.controller;

import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.ReportDAO;
import com.homesharing.dao.ReportTypeDAO;
import com.homesharing.dao.RoleDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.WardDAO;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.dao.impl.RoleDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.dao.impl.WardDAOImpl;
import com.homesharing.model.Home;
import com.homesharing.model.Report;
import com.homesharing.model.ReportType;
import com.homesharing.model.User;
import com.homesharing.service.HomePageService;
import com.homesharing.service.ReportService;
import com.homesharing.service.UserManagementService;
import com.homesharing.service.impl.HomePageServiceImpl;
import com.homesharing.service.impl.ReportServiceImpl;
import com.homesharing.service.impl.UserManagementServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListReportHomeServlet", value = "/dashboard/report-list")
public class ListReportHomeServlet extends HttpServlet {
    private ReportService reportService;
    private HomePageService homePageService;
    private UserManagementService userManagementServiceService;
    private HomeDAO homeDAO;
    private PriceDAO priceDAO;
    private ReportDAO reportDAO;
    private ReportTypeDAO reportTypeDAO;
    private UserDAO userDao;
    private RoleDAO roleDao;
    private WardDAO wardDAO;

    public void init() throws ServletException {
        this.reportService = new ReportServiceImpl(reportDAO, reportTypeDAO);
        homeDAO = new HomeDAOImpl();
        priceDAO = new PriceDAOImpl();
        wardDAO = new WardDAOImpl();
        this.homePageService = new HomePageServiceImpl(homeDAO, priceDAO, userDao,wardDAO);
        userDao = new UserDAOImpl();
        roleDao = new RoleDAOImpl();
        this.userManagementServiceService = new UserManagementServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Report> reports = reportService.getAllReports();
        List<Home> homes = homePageService.getHomes();
        List<User> users = userManagementServiceService.getAllUsers();
        List<ReportType> reportTypes = reportService.getAllReportTypes();
        request.setAttribute("reports", reports);
        request.setAttribute("homes", homes);
        request.setAttribute("users", users);
        request.setAttribute("reportTypes", reportTypes);
        request.getRequestDispatcher("/report-home-list.jsp").forward(request, response);
    }

}