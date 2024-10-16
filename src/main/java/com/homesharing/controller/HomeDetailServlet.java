package com.homesharing.controller;

import com.homesharing.model.*;
import com.homesharing.service.HomeDetailService;
import com.homesharing.service.impl.HomeDetailServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/home-detail")
public class HomeDetailServlet extends HttpServlet {

    private HomeDetailService homeDetailService;

    @Override
    public void init() throws ServletException {
        this.homeDetailService = new HomeDetailServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String homeIdParam = req.getParameter("id");

        if (homeIdParam == null || homeIdParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Home ID is required");
            return;
        }

        int homeId;
        try {
            homeId = Integer.parseInt(homeIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Home ID format");
            return;
        }

        // Fetch home details
        Home home = homeDetailService.getHomeById(homeId);
        if (home == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Home not found");
            return;
        }

        // Fetch prices , creator , hometypes , amenity details
        List<Price> prices = homeDetailService.getHomePricesByHomeId(homeId);
        User creator = homeDetailService.getCreatorByHomeId(homeId);
        List<HomeType> hometypes = homeDetailService.getHomeTypesByHomeId(homeId);
        List<Amentity> amentities = homeDetailService.getHomeAmenitiesByHomeId(homeId);
        List<FireEquipment> fireEquipments = homeDetailService.getHomeFireEquipmentsByHomeId(homeId);

        LocalDate moveInDate = home.getMoveInDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String formattedMoveInDate = (moveInDate != null) ? moveInDate.format(formatter) : "Chưa có thông tin";

        req.setAttribute("hometypes", hometypes);
        req.setAttribute("home", home);
        req.setAttribute("prices", prices);
        req.setAttribute("creator", creator);
        req.setAttribute("amentities", amentities);
        req.setAttribute("fireEquipments", fireEquipments);
        req.setAttribute("formattedMoveInDate", formattedMoveInDate);


        //Forward the request to the JSP
        req.getRequestDispatcher("home-detail.jsp").forward(req, resp);
    }
}
