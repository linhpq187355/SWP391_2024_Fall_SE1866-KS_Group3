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


@WebServlet("/tenant-calender")
public class TenantCalenderServlet extends HttpServlet {

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
        String tenantId = CookieUtil.getCookie(req, "id");
        List<Appointment> appointmentList = appointmentService.getAppointmentsByTenant(tenantId);
        List<Home> homeList = homePageService.getHomesByAppoinment(appointmentList);
        List<User> hostList = userService.getHostByAppointment(appointmentList);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        String appointmentJson = gson.toJson(appointmentList);
        String homeJson = gson.toJson(homeList);
        String hostJson = gson.toJson(hostList);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String combinedJson = String.format("{\"appointmentList\": %s, \"homeList\": %s, \"hostList\": %s}",
                appointmentJson, homeJson, hostJson);
        resp.getWriter().write(combinedJson);
    }
}
