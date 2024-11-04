package com.homesharing.controller;

import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.WardDAO;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.dao.impl.WardDAOImpl;
import com.homesharing.model.*;
import com.homesharing.service.HomeDetailService;
import com.homesharing.service.HomePageService;
import com.homesharing.service.WishListService;
import com.homesharing.service.impl.HomeDetailServiceImpl;
import com.homesharing.service.impl.HomePageServiceImpl;
import com.homesharing.service.impl.WishListServiceImpl;
import com.homesharing.util.CookieUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "HomePostDetailServlet", value = "/home-post-detail")
public class HomePostDetailServlet extends HttpServlet {
    private HomePageService homePageService;  // Service layer for home page logic
    private HomeDAO homeDAO;  // Data Access Object for accessing home data
    private PriceDAO priceDAO;
    private UserDAO userDAO;
    private WardDAO wardDAO;
    private HomeDetailService homeDetailService;
    private WishListService wishListService;

    /**
     * Initializes the HomeDetailServlet by creating an instance of the HomeDetailService.
     * This method is called once when the servlet is first loaded.
     */
    @Override
    public void init() throws ServletException {
        homeDAO = new HomeDAOImpl();
        priceDAO = new PriceDAOImpl();
        wardDAO = new WardDAOImpl();
        homePageService = new HomePageServiceImpl(homeDAO, priceDAO,userDAO, wardDAO);
        this.homeDetailService = new HomeDetailServiceImpl();
        this.wishListService = new WishListServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String homeIdParam = request.getParameter("id");
        String userIdStr = CookieUtil.getCookie(request, "id");

        // Check if the home ID exists
        if (homeIdParam == null || homeIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Home ID is required");
            return;
        }

        int homeId;
        try {
            homeId = Integer.parseInt(homeIdParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Home ID format");
            return;
        }

        // Retrieve home details
        Home home = homeDetailService.getHomeById(homeId);
        if (home == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Home not found");
            return;
        }

        // Retrieve message from request attribute if available
        String message = (String) request.getAttribute("message");
        request.setAttribute("message", message); // Pass the message to JSP
        // Retrieve additional home details
        List<Price> prices = homeDetailService.getHomePricesByHomeId(homeId);
        User creator = homeDetailService.getCreatorByHomeId(homeId);
        List<HomeType> homeTypes = homeDetailService.getHomeTypesByHomeId(homeId);
        List<Amentity> amenities = homeDetailService.getHomeAmenitiesByHomeId(homeId);
        List<FireEquipment> fireEquipments = homeDetailService.getHomeFireEquipmentsByHomeId(homeId);
        List<HomeImage> homeImages = homeDetailService.getHomeImagesByHomeId(homeId);

        // Format the move-in date
        LocalDate moveInDate = home.getMoveInDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedMoveInDate = (moveInDate != null) ? moveInDate.format(formatter) : "No information available";

        // Set attributes for the request to pass to the JSP
        request.setAttribute("homeTypes", homeTypes.get(0));
        request.setAttribute("home", home);
        request.setAttribute("prices", prices.get(0));
        request.setAttribute("creator", creator);
        request.setAttribute("amenities", amenities);
        request.setAttribute("fireEquipments", fireEquipments);
        request.setAttribute("formattedMoveInDate", formattedMoveInDate);
        request.setAttribute("homeImages", homeImages);

//            // Forward the request to the JSP page
        request.getRequestDispatcher("home-post-detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}