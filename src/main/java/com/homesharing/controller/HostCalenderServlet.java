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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import com.homesharing.util.LocalDateAdapter;
import com.homesharing.util.LocalDateTimeAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Servlet to handle requests related to the host's calendar.
 * It retrieves the appointments, homes, and tenants associated with the host.
 */
@WebServlet("/host-calendar")
public class HostCalenderServlet extends HttpServlet {
    private HomePageService homePageService;   // Service for managing home page operations
    private AppointmentService appointmentService; // Service for managing appointments
    private UserService userService; // Service for managing user operations

    /**
     * Initializes the servlet and sets up the necessary services.
     *
     * @throws ServletException if an error occurs during servlet initialization
     */
    @Override
    public void init() throws ServletException {
        HomeDAO homeDAO = new HomeDAOImpl(); // Instantiate HomeDAO implementation
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl(); // Instantiate AppointmentDAO implementation
        UserDAO userDAO = new UserDAOImpl(); // Instantiate UserDAO implementation

        // Initialize services with respective DAO implementations
        this.homePageService = new HomePageServiceImpl(homeDAO, null, null, null);
        this.appointmentService = new AppointmentServiceImpl(appointmentDAO);
        this.userService = new UserServiceImpl(userDAO, null, null, null);
    }

    /**
     * Handles GET requests for the host's calendar.
     * Retrieves appointments, homes, and tenant information, then sends it as a JSON response.
     *
     * @param req  the HttpServletRequest object containing the request data
     * @param resp the HttpServletResponse object for sending responses
     * @throws ServletException if an error occurs during request handling
     * @throws IOException      if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve the host's ID from cookies
        String hostId = CookieUtil.getCookie(req, "id");

        // Get the list of appointments for the host
        List<Appointment> appointmentList = appointmentService.getAppointments(hostId);
        // Retrieve the list of homes associated with the appointments
        List<Home> homeList = homePageService.getHomesByAppoinment(appointmentList);
        // Get the list of tenants associated with the appointments
        List<User> tenantList = userService.getTenantByAppointment(appointmentList);

        // Create a Gson instance with custom date adapters
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Adapter for LocalDateTime
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Adapter for LocalDate
                .create();

        // Convert appointment, home, and tenant lists to JSON
        String appointmentJson = gson.toJson(appointmentList);
        String homeJson = gson.toJson(homeList);
        String hostJson = gson.toJson(tenantList);

        // Set the response content type to JSON
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Combine JSON strings into a single response
        String combinedJson = String.format("{\"appointmentList\": %s, \"homeList\": %s, \"tenantList\": %s}",
                appointmentJson, homeJson, hostJson);

        // Write the combined JSON response
        resp.getWriter().write(combinedJson);
    }
}
