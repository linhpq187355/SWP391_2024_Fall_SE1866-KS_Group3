/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-25      1.0              Pham Quang Linh     First Implement
 */

package com.homesharing.controller;

import com.homesharing.dao.*;
import com.homesharing.dao.impl.*;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.*;
import com.homesharing.service.*;
import com.homesharing.service.impl.*;
import com.homesharing.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import java.io.IOException;
import java.util.Map;


/**
 * Servlet to manage appointments for tenants.
 * This servlet handles requests related to viewing and managing appointments for tenants.
 */
@WebServlet("/appointment-tenant-list")
public class AppointmentListServlet extends HttpServlet {

    // Services for handling home pages, appointments, and user information
    private HomePageService homePageService;
    private AppointmentService appointmentService;
    private UserService userService;
    private ReplyDAO replyDAO;
    private NotificationDAO notificationDAO;
    private PreferenceService preferenceService;
    private ConversationDAO conversationDAO;
    private ConversationService conversationService;
    private NotificationService notificationService;

    /**
     * Initializes the servlet, setting up necessary services.
     */
    @Override
    public void init() throws ServletException {
        // Instantiate DAO implementations for data access
        HomeDAO homeDAO = new HomeDAOImpl();
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        replyDAO = new ReplyDAOImpl();
        notificationDAO = new NotificationDAOImpl();
        conversationDAO = new ConversationDAOImpl();
        notificationService = new NotificationServiceImpl(notificationDAO);
        conversationService = new ConversationServiceImpl(userDAO, conversationDAO, replyDAO);

        // Initialize services with the corresponding DAOs
        this.homePageService = new HomePageServiceImpl(homeDAO, null, null, null);
        this.appointmentService = new AppointmentServiceImpl(appointmentDAO);
        this.userService = new UserServiceImpl(userDAO, null, null, null);
    }

    /**
     * Handles GET requests to display the appointment list for the tenant.
     *
     * @param req  the HttpServletRequest object that contains the request
     * @param resp the HttpServletResponse object for sending the response
     * @throws ServletException if an error occurs during the request handling
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve messages and errors from request parameters
        String cancelMess = req.getParameter("cancelMessage");
        String cancelError = req.getParameter("cancelError");
        String aMess = req.getParameter("aMessage");
        String aError = req.getParameter("aError");

        // Get tenant ID from cookies
        String tenantId = CookieUtil.getCookie(req, "id");
        String appointmentId = req.getParameter("appointmentId");

        try{
            int uId = Integer.parseInt(tenantId);
            int countNewMessage = replyDAO.countNewMessages(uId);
            Map<User, Reply> listUserConversation = conversationService.getListUserConversation(uId);
            req.setAttribute("listUserConversation", listUserConversation);
            req.setAttribute("countNewMessage", countNewMessage);
            List<Notification> notifications = notificationService.getUnReadNotifications(uId);
            int unreadCount = notifications.size();
            req.setAttribute("unreadCount", unreadCount);
            req.setAttribute("notifications", notifications);
        } catch(SQLException | GeneralException e){
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


        // Variables to hold appointment, home, and host information
        Appointment appointment = null;
        Home home = null;
        User host = null;

        // If an appointment ID is provided, fetch related details
        if (appointmentId != null && !appointmentId.isEmpty()) {
            appointment = appointmentService.getAppointmentById(appointmentId);
            home = homePageService.getHomeById(appointment.getHomeId());
            host = userService.getUser(appointment.getHostId());
        }

        // Retrieve lists of appointments and related data for the tenant
        List<Appointment> appointmentList = appointmentService.getAppointmentsByTenant(tenantId);
        List<Home> homeList = homePageService.getHomesByAppoinment(appointmentList);
        List<User> hostList = userService.getHostByAppointment(appointmentList);

        // Set attributes for the request to be used in the JSP
        req.setAttribute("appointmentList", appointmentList);
        req.setAttribute("home", home);
        req.setAttribute("host", host);
        req.setAttribute("appointment", appointment);
        req.setAttribute("homeList", homeList);
        req.setAttribute("hostList", hostList);
        req.setAttribute("aMess", aMess);
        req.setAttribute("aError", aError);
        req.setAttribute("cancelError", cancelError);
        req.setAttribute("cancelMessage", cancelMess);

        // Forward the request to the JSP for rendering the appointment list
        req.getRequestDispatcher("appointment-listing.jsp").forward(req, resp);
    }

}
