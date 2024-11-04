package com.homesharing.controller;

import com.homesharing.dao.*;
import com.homesharing.dao.impl.*;
import com.homesharing.model.Report;
import com.homesharing.service.HomePageService;
import com.homesharing.service.ReportService;
import com.homesharing.service.impl.HomePageServiceImpl;
import com.homesharing.service.impl.ReportServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomePostApproveServlet", value = "/approve")
public class HomePostApproveServlet extends HttpServlet {
    private HomeDAO homeDAO;
    private PriceDAO priceDAO;
    private UserDAO userDAO;
    private WardDAO wardDAO;
    private HomePageService homePageService;
    public void init() throws ServletException {
        this.homeDAO = new HomeDAOImpl();
        this.priceDAO = new PriceDAOImpl();
        this.userDAO = new UserDAOImpl();
        this.wardDAO = new WardDAOImpl();
        this.homePageService = new HomePageServiceImpl(homeDAO, priceDAO, userDAO, wardDAO);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String homeId = request.getParameter("homeId");
        try {
            int hid = Integer.parseInt(homeId);
            homePageService.updateStatusHome(hid, "active");
            response.sendRedirect("home-post-manage");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}