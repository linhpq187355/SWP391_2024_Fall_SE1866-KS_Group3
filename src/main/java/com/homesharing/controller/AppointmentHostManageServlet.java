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

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * AppointmentHostManageServlet handles requests for managing appointments by the host.
 * It interacts with various services to retrieve appointment, home, and tenant information
 * to display in the appointment management interface.
 */
@WebServlet("/appointment-host-manage")
public class AppointmentHostManageServlet extends HttpServlet {
    private HomePageService homePageService; // Service to manage home page data
    private AppointmentService appointmentService; // Service for appointment-related operations
    private UserService userService; // Service to manage user data
    private ReplyDAO replyDAO;
    private NotificationDAO notificationDAO;
    private ConversationDAO conversationDAO;
    private ConversationService conversationService;
    private NotificationService notificationService;

    /**
     * Initializes the servlet and prepares services for handling requests.
     *
     * @throws ServletException if an error occurs during servlet initialization
     */
    @Override
    public void init() throws ServletException {
        HomeDAO homeDAO = new HomeDAOImpl(); // Initialize DAO for Home
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl(); // Initialize DAO for Appointment
        UserDAO userDAO = new UserDAOImpl(); // Initialize DAO for User
        replyDAO = new ReplyDAOImpl();
        notificationDAO = new NotificationDAOImpl();
        conversationDAO = new ConversationDAOImpl();
        notificationService = new NotificationServiceImpl(notificationDAO);
        conversationService = new ConversationServiceImpl(userDAO, conversationDAO, replyDAO);

        this.homePageService = new HomePageServiceImpl(homeDAO, null, null, null);
        this.appointmentService = new AppointmentServiceImpl(appointmentDAO);
        this.userService = new UserServiceImpl(userDAO, null, null, null);
    }

    /**
     * Handles GET requests for managing appointments.
     *
     * @param req  the HttpServletRequest object that contains the request data
     * @param resp the HttpServletResponse object that will contain the response data
     * @throws ServletException if an error occurs during request handling
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve host ID from cookies
        String hostId = CookieUtil.getCookie(req, "id");
        String appointmentId = req.getParameter("appointmentId");
        String aMessage = req.getParameter("aMessage");
        String aError = req.getParameter("aError");
        String rMessage = req.getParameter("rMessage");
        String rError = req.getParameter("rError");
        String cMessage = req.getParameter("cMessage");
        String cError = req.getParameter("cError");

        // Retrieve lists of appointments, homes, and tenants associated with the host
        List<Appointment> appointmentList = appointmentService.getAppointments(hostId);
        List<Home> homeList = homePageService.getHomesByAppoinment(appointmentList);
        List<User> tenantList = userService.getTenantByAppointment(appointmentList);

        try{
            int uId = Integer.parseInt(hostId);
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

        Appointment appointment = null; // To hold appointment details
        Home home = null; // To hold home details
        User tenant = null; // To hold tenant details


        // If an appointment ID is provided, fetch the corresponding appointment, home, and tenant details
        if (appointmentId != null && !appointmentId.isEmpty()) {
            appointment = appointmentService.getAppointmentById(appointmentId);
            home = homePageService.getHomeById(appointment.getHomeId());
            tenant = userService.getUser(appointment.getTenantId());
        }

        // Set attributes to the request for forwarding to the JSP page
        req.setAttribute("home", home);
        req.setAttribute("tenant", tenant);
        req.setAttribute("tenantList", tenantList);
        req.setAttribute("appointment", appointment);
        req.setAttribute("appointmentList", appointmentList);
        req.setAttribute("homeList", homeList);
        req.setAttribute("aError", aError);
        req.setAttribute("aMessage", aMessage);
        req.setAttribute("rMessage", rMessage);
        req.setAttribute("rError", rError);
        req.setAttribute("cMessage", cMessage);
        req.setAttribute("cError", cError);

        // Forward the request to the appointment management JSP page
        req.getRequestDispatcher("appointment-host-manage.jsp").forward(req, resp);
    }
}
