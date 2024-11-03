/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version              AUTHOR           DESCRIPTION
 * 2024-10-15      1.0                  ThangLT      First Implement
 */

package com.homesharing.controller;

import com.homesharing.dao.*;
import com.homesharing.dao.impl.*;
import com.google.gson.Gson;
import com.homesharing.dao.*;
import com.homesharing.dao.impl.*;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.model.User;
import com.homesharing.service.HomeMgtService;
import com.homesharing.service.HomePageService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.HomeMgtServiceImpl;
import com.homesharing.service.impl.HomePageServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.CookieUtil;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/my-home")
public class MyHomeListServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(MyHomeListServlet.class.getName());
    private HomeMgtService homeMgtService;
    private HomePageService homePageService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        HomeDAO homeDAO = new HomeDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        ProvinceDAO provinceDAO = new ProvinceDAOImpl();
        DistrictDAO districtDAO = new DistrictDAOImpl();
        HomeImageDAO homeImgDAO = new HomeImageDAOImpl();
        WardDAO wardDAO = new WardDAOImpl();
        AmentityHomeDAO amentityDAO = new AmentityHomeDAOImpl();
        FireEquipHomeDAO fireEquipDAO = new FireEquipHomeImpl();

        PriceDAO priceDAO = new PriceDAOImpl();
        this.homeMgtService = new HomeMgtServiceImpl(homeDAO, priceDAO, homeImgDAO, amentityDAO, fireEquipDAO);
        this.homePageService = new HomePageServiceImpl(homeDAO, priceDAO,userDAO,wardDAO);
        this.userService = new UserServiceImpl(userDAO, null, null, null);
    }

    /**
     * Handles GET request to display host's home list.
     *
     * @param req  HttpServletRequest object
     * @param resp HttpServletResponse object
     * @throws ServletException if an error occurs during request processing
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> searchParams = new HashMap<>();
        String status = "active";
        String orderBy = req.getParameter("orderby");
        String order = req.getParameter("order");
        String perPage = req.getParameter("per_page");
        String currentPage = req.getParameter("currentPage");
        String targetPage = req.getParameter("targetPage");

        if (currentPage != null && !currentPage.isEmpty()) {
            searchParams.put("currentPage", currentPage);
        }
        if (targetPage != null && !targetPage.isEmpty()) {
            searchParams.put("targetPage", targetPage);
        }
        if (perPage != null && !perPage.isEmpty()) {
            searchParams.put("per_page", perPage);
        }
        if(order != null && !order.isEmpty()) {
            searchParams.put("order", order);
        }
        if(orderBy != null && !orderBy.isEmpty()) {
            searchParams.put("orderby", orderBy);
        }
        if(status != null && !status.isEmpty()) {
            searchParams.put("status", status);
        }


        int totalHomes = 0;
        try {
            totalHomes = homePageService.countSearchHome(searchParams);
            homePageService.updateTargetPage(searchParams, totalHomes);
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.severe("Error while fetching new homes: " + e.getMessage());
            ServletUtils.forwardWithMessage(req, resp, e.getMessage());
        }

        String cookieValue = CookieUtil.getCookie(req, "id");
        if (cookieValue != null) {
            User user = null;
            user = userService.getUser(Integer.parseInt(cookieValue));

            if(user != null) {
                searchParams.put("createdBy", user.getId());
                List<Home> homes = new ArrayList<>(homePageService.searchHome(searchParams));
                req.setAttribute("totalHomes", totalHomes);
                System.out.println("total homes: " + totalHomes);
                List<Price> prices = new ArrayList<>(homePageService.getHomePrice(homes));

                req.setAttribute("homes", homes);
                System.out.println(homes);
                req.setAttribute("prices", prices);
                req.setAttribute("searchParams", searchParams);
            }

            req.getRequestDispatcher("/personal-homes.jsp").forward(req, resp);

        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            resp.sendRedirect("login.jsp");
        }
    }
}
