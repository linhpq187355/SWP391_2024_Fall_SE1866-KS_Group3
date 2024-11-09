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
import com.homesharing.dao.NotificationDAO;
import com.homesharing.dao.impl.AppointmentDAOImpl;
import com.homesharing.dao.impl.NotificationDAOImpl;
import com.homesharing.service.AppointmentService;
import com.homesharing.service.impl.AppointmentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Servlet to handle cancellation of appointments.
 * This servlet processes requests to cancel appointments based on the user role (host or tenant).
 */
@WebServlet("/cancel-appointment")
public class CancelAppointmentServlet extends HttpServlet {
    // Appointment service for managing appointment-related operations
    private AppointmentService appointmentService;

    // Logger for logging error messages
    private static final Logger LOGGER = Logger.getLogger(CancelAppointmentServlet.class.getName());

    /**
     * Initializes the servlet and sets up the appointment service.
     *
     * @throws ServletException if an error occurs during initialization
     */
    @Override
    public void init() throws ServletException {
        // Initialize the Appointment DAO and service implementation
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        this.appointmentService = new AppointmentServiceImpl(appointmentDAO);
    }

    /**
     * Handles GET requests to cancel an appointment.
     *
     * @param req  the HttpServletRequest object that contains the request
     * @param resp the HttpServletResponse object for sending the response
     * @throws ServletException if an error occurs during the request handling
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve appointment ID, cancellation reason, and user role from request parameters
        String appointmentId = req.getParameter("appointmentId");
        String cancelReason = req.getParameter("reason");
        String host = req.getParameter("host");

        try {
            // Check if the request is from the host
            if (host.equals("0")) {
                // Attempt to cancel the appointment as a host
                int rowsUpdated = appointmentService.cancelAppointment(appointmentId, cancelReason, "host");
                if (rowsUpdated > 0) {
                    // Redirect to tenant appointment list with success message
                    resp.sendRedirect("appointment-tenant-list?cancelMessage=1");
                } else {
                    // Log warning and redirect with error message
                    LOGGER.warning("Failed to cancel appointment.");
                    resp.sendRedirect("appointment-tenant-list?cancelError=1");
                }
            } else {
                // Attempt to cancel the appointment as a tenant
                int rowsUpdated = appointmentService.cancelAppointment(appointmentId, cancelReason, "tenant");
                if (rowsUpdated > 0) {
                    // Redirect to host management with success message
                    resp.sendRedirect("appointment-host-manage?cMessage=1");
                } else {
                    // Log warning and redirect with error message
                    LOGGER.warning("Failed to cancel appointment.");
                    resp.sendRedirect("appointment-host-manage?cError=1");
                }
            }

        } catch (RuntimeException e) {
            // Log a severe error if a runtime exception occurs
            LOGGER.log(Level.SEVERE, "Error while saving appointment data: {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cannot save appointment data.");
        } catch (Exception e) {
            // Log a severe error for any unexpected exceptions
            LOGGER.log(Level.SEVERE, "Unexpected error while saving appointment data: {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred while saving appointment data.");
        }
    }
}
