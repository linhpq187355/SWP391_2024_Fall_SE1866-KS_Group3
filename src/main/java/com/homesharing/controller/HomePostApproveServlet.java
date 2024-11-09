package com.homesharing.controller;

import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.WardDAO;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.dao.impl.WardDAOImpl;
import com.homesharing.service.HomePageService;
import com.homesharing.service.impl.HomePageServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "HomePostApproveServlet", value = "/dashboard/approve")
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
            response.sendRedirect(request.getContextPath() + "/dashboard/home-list");
        } catch (RuntimeException e) {
            request.setAttribute("errorMessage", "An error occurred while approving the home post.");
            request.getRequestDispatcher("/404.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}