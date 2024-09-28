package com.homesharing.controller;

import com.google.gson.Gson;
import com.homesharing.model.District;
import com.homesharing.service.HomePageService;
import com.homesharing.service.impl.HomePageServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/location")
public class LocationServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CreateHomeServlet.class);
    private HomePageService homePageService;
    @Override
    public void init() throws ServletException {
        homePageService = new HomePageServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String provinceId = req.getParameter("provinceId");
        try {
            int provinceIndex = Integer.parseInt(provinceId);
            //List<District> districts = homePageService.getDistrictByProvinceId(provinceIndex);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            //out.write(new Gson().toJson(districts));
            out.flush();
        } catch(NumberFormatException e) {

        }
    }
}
