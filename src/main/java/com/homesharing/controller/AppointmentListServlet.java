package com.homesharing.controller;

import com.homesharing.dao.AppointmentDAO;
import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.Home;
import com.homesharing.dao.impl.AppointmentDAOImpl;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.model.Appointment;
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

import java.io.PrintWriter;
import java.util.List;

import java.io.IOException;

@WebServlet("/appointment-tenant-list")
public class AppointmentListServlet extends HttpServlet {

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
        String cancelMess = req.getParameter("cancelMessage");
        String cancelError = req.getParameter("cancelError");
        String aMess = req.getParameter("aMessage");
        String aError = req.getParameter("aError");
        String tenantId = CookieUtil.getCookie(req, "id");
        List<Appointment> appointmentList = appointmentService.getAppointmentsByTenant(tenantId);
        List<Home> homeList = homePageService.getHomesByAppoinment(appointmentList);
        List<User> hostList = userService.getHostByAppointment(appointmentList);
        req.setAttribute("appointmentList", appointmentList);
        req.setAttribute("homeList", homeList);
        req.setAttribute("hostList", hostList);
        req.setAttribute("aMess", aMess);
        req.setAttribute("aError", aError);
        req.setAttribute("cancelError", cancelError);
        req.setAttribute("cancelMessage", cancelMess);
        req.getRequestDispatcher("appointment-listing.jsp").forward(req, resp);
    }
}
