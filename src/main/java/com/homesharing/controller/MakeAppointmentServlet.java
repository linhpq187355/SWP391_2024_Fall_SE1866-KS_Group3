package com.homesharing.controller;

import com.homesharing.dao.AppointmentDAO;
import com.homesharing.dao.NotificationDAO;
import com.homesharing.dao.impl.AppointmentDAOImpl;
import com.homesharing.dao.impl.NotificationDAOImpl;
import com.homesharing.model.Appointment;
import com.homesharing.service.AppointmentService;
import com.homesharing.service.impl.AppointmentServiceImpl;
import com.homesharing.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/make-appointment")
public class MakeAppointmentServlet extends HttpServlet {

    private AppointmentService appointmentService;
    private static final Logger LOGGER = Logger.getLogger(MakeAppointmentServlet.class.getName());

    @Override
    public void init() throws ServletException {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        this.appointmentService = new AppointmentServiceImpl(appointmentDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hostId = req.getParameter("hostId");
        String homeId = req.getParameter("homeId");
        String tenantId = CookieUtil.getCookie(req, "id");
        List<Appointment> hostAppointment = appointmentService.getAppointments(hostId);
        List<Appointment> tenantAppointment = appointmentService.getAppointmentsByTenant(tenantId);
        req.setAttribute("hostAppointment", hostAppointment);
        req.setAttribute("tenantAppointment", tenantAppointment);
        req.setAttribute("hostId", hostId);
        req.setAttribute("homeId", homeId);
        req.getRequestDispatcher("making-appointment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String selectedDate = null;
        String selectedTime = null;
        String selectedMonth = null;
        String selectedYear = null;
        String hostId = null;
        String homeId = null;
        String tenantId = null;
        String note = null;

        try {
            // Lấy dữ liệu từ request
            selectedDate = req.getParameter("selectedDate");
            selectedTime = req.getParameter("selectedTime");
            selectedMonth = req.getParameter("selectedMonth");
            selectedYear = req.getParameter("selectedYear");
            hostId = req.getParameter("hostId");
            homeId = req.getParameter("homeId");


            tenantId = CookieUtil.getCookie(req, "id");
            if (tenantId == null || tenantId.isEmpty()) {
                throw new IllegalArgumentException("Tenant ID không hợp lệ hoặc không tồn tại trong Cookie.");
            }

            note = req.getParameter("note");

            // Kiểm tra dữ liệu cần thiết để gọi insertAppointment
            if (selectedDate == null || selectedTime == null || selectedMonth == null || selectedYear == null || hostId == null) {
                throw new IllegalArgumentException("Dữ liệu bắt buộc không đầy đủ.");
            }

        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE,"Lỗi dữ liệu đầu vào: {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dữ liệu đầu vào không hợp lệ: " + e.getMessage());
            return;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,"Lỗi không mong muốn khi xử lý request: {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi trong quá trình xử lý request.");
            return;
        }

        try {
            int rowsUpdated = appointmentService.insertAppointment(selectedDate, selectedMonth, selectedYear, selectedTime,tenantId , hostId, note,homeId);

            if(rowsUpdated>0){
                req.setAttribute("message","Đặt lịch thành công!");
                req.getRequestDispatcher("making-appointment.jsp").forward(req, resp);
            } else {
                LOGGER.warning("Failed to insert appointment.");
                req.setAttribute("error","Đặt lịch thất bại!");
                req.getRequestDispatcher("making-appointment.jsp").forward(req, resp);
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