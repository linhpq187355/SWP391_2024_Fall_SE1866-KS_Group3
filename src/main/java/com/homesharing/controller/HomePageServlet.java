/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-01      1.0              Pham Quang Linh     First Implement
 */

package com.homesharing.controller;

import com.homesharing.dao.*;
import com.homesharing.dao.impl.*;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.*;
import com.homesharing.service.*;
import com.homesharing.service.impl.*;
import com.homesharing.util.CookieUtil;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HomePageServlet class handles HTTP requests to the "/home-page" endpoint.
 * This servlet retrieves new homes and their prices, setting them as request attributes
 * and forwards the request to the home page JSP for rendering.
 */
@WebServlet("/home-page")
public class HomePageServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(HomePageServlet.class); // Logger instance

    private HomePageService homePageService;  // Service layer for home page logic
    private HomeDAO homeDAO;  // Data Access Object for accessing home data
    private PriceDAO priceDAO;  // Data Access Object for accessing price data
    private UserDAO userDAO;
    private WardDAO wardDAO;
    private UserService userService;
    private ReplyDAO replyDAO;
    private NotificationDAO notificationDAO;
    private PreferenceService preferenceService;
    private ConversationDAO conversationDAO;
    private ConversationService conversationService;
    private NotificationService notificationService;

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
        userDAO = new UserDAOImpl();
        replyDAO = new ReplyDAOImpl();
        notificationDAO = new NotificationDAOImpl();
        conversationDAO = new ConversationDAOImpl();
        notificationService = new NotificationServiceImpl(notificationDAO);
        conversationService = new ConversationServiceImpl(userDAO, conversationDAO, replyDAO);
        wardDAO = new WardDAOImpl();
        PreferenceDAO preferenceDAO = new PreferenceDAOImpl();
        // Initialize the home page service with the required DAOs
        homePageService = new HomePageServiceImpl(homeDAO, priceDAO,userDAO,wardDAO);
        this.preferenceService = new PreferenceServiceImpl(preferenceDAO);
        userService = new UserServiceImpl(userDAO,null,null,null);
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
        String userId = CookieUtil.getCookie(req, "id");
        // Retrieve the list of new homes
        List<Home> homes = homePageService.getNewHomes();
        int[] matchingHost = new int[50];
        List<Home> matchingHomes = new ArrayList<>();
        // Retrieve the corresponding price data for the homes
        List<Price> prices = homePageService.getHomePrice(homes);
        List<Price> matchingHomePrice = new ArrayList<>();
        int totalUser = userService.getNumberOfUsers();
        int totalHomes = homeDAO.getNumberHomes();
        Preference preference = new Preference();
        if(userId!=null){
            User user = userDAO.getUser(Integer.parseInt(userId));
            try {
                int uId = Integer.parseInt(userId);
                int countNewMessage = replyDAO.countNewMessages(uId);
                Map<User, Reply> listUserConversation = conversationService.getListUserConversation(uId);
                req.setAttribute("listUserConversation", listUserConversation);
                req.setAttribute("countNewMessage", countNewMessage);
                List<Notification> notifications = notificationService.getUnReadNotifications(uId);
                int unreadCount = notifications.size();
                req.setAttribute("unreadCount", unreadCount);
                req.setAttribute("notifications", notifications);
            } catch (SQLException | GeneralException e) {
                logger.error("Error home-page: {}", e.getMessage(), e); // Log the exception with stack trace
                // Handle invalid user ID format
                ServletUtils.forwardWithMessage(req, resp, "Có lỗi xảy ra, vui lòng đăng nhập lại.");

            }
            if(user.getRolesId() == 3){
                preference = preferenceService.getPreference(Integer.parseInt(userId));
                matchingHost = preferenceService.listMatchingPreferences(Integer.parseInt(userId));
                if(matchingHost != null){
                    matchingHomes = homePageService.getMatchingHome(matchingHost, Integer.parseInt(userId));

                    matchingHomePrice = homePageService.getHomePrice(matchingHomes);

                }
            }

        }

        // Set the home and price data as request attributes for use in the JSP
        req.setAttribute("homes", homes);
        req.setAttribute("totalUser", totalUser);
        req.setAttribute("prices", prices);
        req.setAttribute("totalHomes", totalHomes);
        req.setAttribute("preference", preference);
        req.setAttribute("matchingHomes", matchingHomes);
        req.setAttribute("matchingHomePrice", matchingHomePrice);

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