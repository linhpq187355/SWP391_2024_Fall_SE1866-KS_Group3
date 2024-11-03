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

@WebServlet("/appointment-host-manage")
public class AppointmentHostManageServlet extends HttpServlet {

    private HomePageService homePageService;
    private AppointmentService appointmentService;
    private UserService userService;


    @Override
    public void init() throws ServletException {
        HomeDAO homeDAO = new HomeDAOImpl();
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        this.homePageService = new HomePageServiceImpl(homeDAO,null,null,null);
        this.appointmentService = new AppointmentServiceImpl(appointmentDAO);
        this.userService = new UserServiceImpl(userDAO, null,null,null);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hostId = CookieUtil.getCookie(req, "id");
        String appointmentId = req.getParameter("appointmentId");
        String aMessage = req.getParameter("aMessage");
        String aError = req.getParameter("aError");
        String rMessage = req.getParameter("rMessage");
        String rError = req.getParameter("rError");
        String cMessage = req.getParameter("cMessage");
        String cError = req.getParameter("cError");
        List<Appointment> appointmentList = appointmentService.getAppointments(hostId);
        List<Home> homeList = homePageService.getHomesByAppoinment(appointmentList);
        List<User> tenantList = userService.getTenantByAppointment(appointmentList);
        Appointment appointment = null;
        Home home = null;
        User tenant = null;
        if(appointmentId != null && !appointmentId.isEmpty()) {
            appointment = appointmentService.getAppointmentById(appointmentId);
            home = homePageService.getHomeById(appointment.getHomeId());
            tenant = userService.getUser(appointment.getTenantId());
        }

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
        req.getRequestDispatcher("appointment-host-manage.jsp").forward(req, resp);
    }
}
