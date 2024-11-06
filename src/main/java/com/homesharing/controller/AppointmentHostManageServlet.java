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

import com.homesharing.dao.AppointmentDAO;
import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.NotificationDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.AppointmentDAOImpl;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.NotificationDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.Appointment;
import com.homesharing.model.Home;
import com.homesharing.model.User;
import com.homesharing.service.AppointmentService;
import com.homesharing.service.HomePageService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.AppointmentServiceImpl;
import com.homesharing.service.impl.HomePageServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


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
