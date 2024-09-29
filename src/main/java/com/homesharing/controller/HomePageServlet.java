package com.homesharing.controller;

import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.service.impl.HomePageServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.homesharing.service.HomePageService;
import java.util.List;

import java.io.IOException;


@WebServlet("/home-page")
public class HomePageServlet extends HttpServlet {

    private HomePageService homePageService;

    @Override
    public void init() throws ServletException {
        homePageService = new HomePageServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Home> homes = homePageService.getNewHomes();
        List<Price> prices = homePageService.getHomePrice(homes);

        req.setAttribute("homes", homes);
        req.setAttribute("prices", prices);

        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }
}
