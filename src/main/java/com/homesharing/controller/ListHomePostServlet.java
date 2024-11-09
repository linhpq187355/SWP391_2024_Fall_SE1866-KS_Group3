package com.homesharing.controller;

import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.WardDAO;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.dao.impl.WardDAOImpl;
import com.homesharing.model.Home;
import com.homesharing.model.HomeType;
import com.homesharing.model.User;
import com.homesharing.service.HomePageService;
import com.homesharing.service.SubmissionFormService;
import com.homesharing.service.UserManagementService;
import com.homesharing.service.impl.HomePageServiceImpl;
import com.homesharing.service.impl.SubmissonFormServiceImpl;
import com.homesharing.service.impl.UserManagementServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "ListHomePostServlet", value = "/home-post-manage")
public class ListHomePostServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(HomeListServlet.class.getName());
    private HomePageService homePageService;
    private UserManagementService userManagementServiceService;
    private SubmissionFormService submissionFormService;
    // Service layer for home post list logic
    private HomeDAO homeDAO;  // Data Access Object for accessing home data
    private PriceDAO priceDAO;  // Data Access Object for accessing price data
    private UserDAO userDAO;
    private WardDAO wardDAO;
    /**
     * Initializes the servlet by instantiating the required DAOs and services.
     */
    @Override
    public void init() throws ServletException {
        // Initialize the DAOs
        homeDAO = new HomeDAOImpl();
        priceDAO = new PriceDAOImpl();
        userDAO = new UserDAOImpl();
        wardDAO = new WardDAOImpl();
        // Initialize the home page service with the required DAOs
        homePageService = new HomePageServiceImpl(homeDAO, priceDAO, userDAO, wardDAO);
        this.userManagementServiceService = new UserManagementServiceImpl();
        this.submissionFormService = new SubmissonFormServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Home> homeList = homePageService.getHomes();
        List<User> userList = userManagementServiceService.getAllUsers();
        List<HomeType> homeTypeList = submissionFormService.getHomeTypes();
        request.setAttribute("homeList", homeList);
        request.setAttribute("userList", userList);
        request.setAttribute("homeTypeList", homeTypeList);
        request.getRequestDispatcher("home-post-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}