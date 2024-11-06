package com.homesharing.controller;

import com.homesharing.dao.AppointmentDAO;
import com.homesharing.dao.NotificationDAO;
import com.homesharing.dao.impl.AppointmentDAOImpl;
import com.homesharing.dao.impl.NotificationDAOImpl;
import com.homesharing.model.Appointment;
import com.homesharing.model.Price;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet("/edit-appointment")
public class EditAppointmentServlet extends HttpServlet {
    private AppointmentService appointmentService;
    private static final Logger LOGGER = Logger.getLogger(EditAppointmentServlet.class.getName());

    @Override
    public void init() throws ServletException {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        this.appointmentService = new AppointmentServiceImpl(appointmentDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String tenantId = CookieUtil.getCookie(req, "id");
        Appointment appointment = appointmentService.getAppointmentById(id);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedStartTime = appointment.getStartDate().format(timeFormatter);
        String formattedEndTime = appointment.getEndDate().format(timeFormatter);

        List<Appointment> hostAppointment = appointmentService.getAppointments(String.valueOf(appointment.getHostId()));
        List<Appointment> tenantAppointment = appointmentService.getAppointmentsByTenant(tenantId);
        req.setAttribute("appointmentMonth", appointment.getStartDate().getMonthValue());
        req.setAttribute("hostAppointment", hostAppointment);
        req.setAttribute("tenantAppointment", tenantAppointment);
        req.setAttribute("appointmentYear", appointment.getStartDate().getYear());
        req.setAttribute("appointmentDay", appointment.getStartDate().getDayOfMonth());
        req.setAttribute("appointmentTime", formattedStartTime + " - " + formattedEndTime);
        req.setAttribute("appointment", appointment);
        req.getRequestDispatcher("update-appointment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String selectedDate = null;
        String selectedTime = null;
        String selectedMonth = null;
        String selectedYear = null;
        String note = null;
        String aptmId = null;
        Appointment appointment = null;


        try {
            // Lấy dữ liệu từ request
            selectedDate = req.getParameter("selectedDate");
            selectedTime = req.getParameter("selectedTime");
            selectedMonth = req.getParameter("selectedMonth");
            selectedYear = req.getParameter("selectedYear");
            aptmId = req.getParameter("aptmId");
            note = req.getParameter("note");

            appointment = appointmentService.getAppointmentById(aptmId);


            // Kiểm tra dữ liệu cần thiết để gọi insertAppointment
            if (selectedDate == null || selectedTime == null || selectedMonth == null || selectedYear == null) {
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
            int rowsUpdated = appointmentService.updateAppointment(selectedDate, selectedMonth, selectedYear, selectedTime,note,"hostPending", aptmId,"0");

            if(rowsUpdated>0){
                req.setAttribute("message","Sửa lịch thành công!");
                req.setAttribute("appointmentMonth", selectedMonth+1);
                req.setAttribute("appointmentYear", selectedYear);
                req.setAttribute("appointmentDay", selectedDate);
                req.setAttribute("appointmentTime", selectedTime);
                req.getRequestDispatcher("update-appointment.jsp").forward(req, resp);
            } else {
                LOGGER.warning("Failed to insert appointment.");


                req.setAttribute("appointmentMonth", selectedMonth+1);
                req.setAttribute("appointmentYear", selectedYear);
                req.setAttribute("appointmentDay", selectedDate);
                req.setAttribute("appointmentTime", selectedTime);
                req.setAttribute("appointment", appointment);
                req.setAttribute("error","Sửa lịch thất bại!");
                req.getRequestDispatcher("update-appointment.jsp").forward(req, resp);
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
