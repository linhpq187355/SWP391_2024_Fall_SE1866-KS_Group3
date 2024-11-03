package com.homesharing.controller;

import com.homesharing.dao.AppointmentDAO;
import com.homesharing.dao.NotificationDAO;
import com.homesharing.dao.impl.AppointmentDAOImpl;
import com.homesharing.dao.impl.NotificationDAOImpl;
import com.homesharing.model.Appointment;
import com.homesharing.service.AppointmentService;
import com.homesharing.service.impl.AppointmentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/accept-reject-appointment")
public class RejectAcceptAppointmentServlet extends HttpServlet {
    private AppointmentService appointmentService;
    private NotificationDAO notificationDAO;
    private static final Logger LOGGER = Logger.getLogger(EditAppointmentServlet.class.getName());

    @Override
    public void init() throws ServletException {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        this.appointmentService = new AppointmentServiceImpl(appointmentDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String status = req.getParameter("status");
        String reason = req.getParameter("reason");
        String host = req.getParameter("host");

        try{
            if(status.equals("accepted")){
                if(host.equals("1")){
                    int rowEffected = appointmentService.acceptAppointment(id,"tenant");
                    if(rowEffected>0){
                        resp.sendRedirect("appointment-host-manage?aMessage=1");
                    } else {
                        LOGGER.warning("Failed to accept appointment.");
                        resp.sendRedirect("appointment-host-manage?aError=1");
                    }
                } else {
                    if(host.equals("0")){
                        int rowEffected = appointmentService.acceptAppointment(id, "host");
                        if(rowEffected>0){
                            resp.sendRedirect("appointment-tenant-list?aMessage=1");
                        } else {
                            LOGGER.warning("Failed to accept appointment.");
                            resp.sendRedirect("appointment-tenant-list?aError=1");
                        }
                    }
                }

            } else {
                if(status.equals("rejected")){
                    int rowEffected = appointmentService.rejectAppointment(id,reason);
                    if(rowEffected>0){
                        resp.sendRedirect("appointment-host-manage?rMessage=1");
                    } else {
                        LOGGER.warning("Failed to reject appointment.");
                        resp.sendRedirect("appointment-host-manage?rError=1");
                    }
                }
            }

        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE,"Lỗi khi lưu dữ liệu hẹn: {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể lưu dữ liệu hẹn.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,"Lỗi không mong muốn khi lưu dữ liệu hẹn: {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi không mong muốn khi lưu dữ liệu hẹn.");
        }
    }
}