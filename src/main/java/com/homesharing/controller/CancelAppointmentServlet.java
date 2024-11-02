package com.homesharing.controller;

import com.homesharing.dao.AppointmentDAO;
import com.homesharing.dao.impl.AppointmentDAOImpl;
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

@WebServlet("/cancel-appointment")
public class CancelAppointmentServlet extends HttpServlet {
    private AppointmentService appointmentService;
    private static final Logger LOGGER = Logger.getLogger(CancelAppointmentServlet.class.getName());

    @Override
    public void init() throws ServletException {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        this.appointmentService = new AppointmentServiceImpl(appointmentDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String appointmentId = req.getParameter("appointmentId");
        String cancelReason = req.getParameter("reason");
        String host = req.getParameter("host");
        try{

            int rowsUpdated = appointmentService.cancelAppointment(appointmentId, cancelReason);
            if(rowsUpdated>0){
                if(host.equals("0")){
                    resp.sendRedirect("appointment-tenant-list?cancelMessage=1");
                } else {
                    resp.sendRedirect("appointment-host-manage?cMessage=1");
                }
            } else {

                LOGGER.warning("Failed to cancel appointment.");
                if(host.equals("0")){
                    resp.sendRedirect("appointment-tenant-list?cancelError=1");
                } else {
                    resp.sendRedirect("appointment-host-manage?cError=1");
                }

            }
        }  catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE,"Lỗi khi lưu dữ liệu hẹn: {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể lưu dữ liệu hẹn.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,"Lỗi không mong muốn khi lưu dữ liệu hẹn: {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi không mong muốn khi lưu dữ liệu hẹn.");
        }
    }
}
