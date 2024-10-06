package com.homesharing.controller;

import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.service.impl.HomePageServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.homesharing.service.HomePageService;

import java.io.IOException;
import java.util.List;

/**
 * HomePageServlet class handles HTTP requests to the "/home-page" endpoint.
 * This servlet retrieves new homes and their prices, setting them as request attributes
 * and forwards the request to the home page JSP for rendering.
 */
@WebServlet("/home-page")
public class HomePageServlet extends HttpServlet {

    private HomePageService homePageService;  // Service layer for home page logic
    private HomeDAO homeDAO;  // Data Access Object for accessing home data
    private PriceDAO priceDAO;  // Data Access Object for accessing price data

    /**
     * Initializes the servlet by creating an instance of HomePageService.
     * The init method is called once when the servlet is first loaded into memory.
     *
     * @throws ServletException if servlet initialization fails
     */
    @Override
    public void init() throws ServletException {
        // Initialize the DAOs
        homeDAO = new HomeDAOImpl();
        priceDAO = new PriceDAOImpl();
        // Initialize the home page service with the required DAOs
        homePageService = new HomePageServiceImpl(homeDAO, priceDAO);
    }

    /**
     * Handles HTTP GET requests.
     * This method retrieves new homes and their prices, sets them as attributes, and forwards
     * the request to the home page JSP for rendering.
     *
     * @param req  the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if an input or output error is detected when the servlet handles the GET request
     * @throws IOException      if the request for the GET could not be handled
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve the list of new homes
        List<Home> homes = homePageService.getNewHomes();
        // Retrieve the corresponding price data for the homes
        List<Price> prices = homePageService.getHomePrice(homes);

        // Set the home and price data as request attributes for use in the JSP
        req.setAttribute("homes", homes);
        req.setAttribute("prices", prices);

        // Forward the request to the JSP page for rendering
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = (String) req.getSession().getAttribute("message");
        if (message != null) {
            req.setAttribute("message", message);
            req.getSession().removeAttribute("message");
        }
        String messageType = (String) req.getSession().getAttribute("messageType");
        if (messageType != null) {
            req.setAttribute("messageType", messageType);
            req.getSession().removeAttribute("messageType");
        }
        List<Home> homes = homePageService.getNewHomes();
        List<Price> prices = homePageService.getHomePrice(homes);
        req.setAttribute("homes", homes);
        req.setAttribute("prices", prices);
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }
}