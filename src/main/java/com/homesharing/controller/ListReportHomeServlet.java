package com.homesharing.controller;

import com.homesharing.dao.*;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.dao.impl.RoleDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.Home;
import com.homesharing.model.Report;
import com.homesharing.model.ReportType;
import com.homesharing.model.User;
import com.homesharing.service.HomePageService;
import com.homesharing.service.ReportService;
import com.homesharing.service.UserManagementService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.HomePageServiceImpl;
import com.homesharing.service.impl.ReportServiceImpl;
import com.homesharing.service.impl.UserManagementServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ListReportHomeServlet", value = "/report-home-manage")
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

    public void init() throws ServletException {
        this.reportService = new ReportServiceImpl(reportDAO, reportTypeDAO);
        homeDAO = new HomeDAOImpl();
        priceDAO = new PriceDAOImpl();
        this.homePageService = new HomePageServiceImpl(homeDAO, priceDAO);
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}