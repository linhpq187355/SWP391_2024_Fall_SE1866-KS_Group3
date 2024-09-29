package com.homesharing.controller;

import com.google.gson.Gson;
import com.homesharing.model.*;
import com.homesharing.service.HomePageService;
import com.homesharing.service.impl.HomePageServiceImpl;
import com.homesharing.service.impl.SubmissonFormServiceImpl;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/submit-home")
public class CreateHomeServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CreateHomeServlet.class);
    private SubmissonFormServiceImpl submissonFormService;

    @Override
    public void init() throws ServletException {
        submissonFormService = new SubmissonFormServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            List<HomeType> homeTypes = submissonFormService.getHomeTypes();
            List<Amentity> amentities = submissonFormService.getAmentities();
            List<FireEquipment> fireEquipments = submissonFormService.getFireEquipments();

            req.setAttribute("homeTypes", homeTypes);
            req.setAttribute("amentities", amentities);
            req.setAttribute("fireEquipments", fireEquipments);
            req.getRequestDispatcher("submit-home.jsp").forward(req,resp);
    }

}
