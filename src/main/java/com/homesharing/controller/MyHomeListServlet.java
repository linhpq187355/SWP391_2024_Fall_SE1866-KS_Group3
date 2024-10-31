package com.homesharing.controller;

import com.google.gson.Gson;
import com.homesharing.dao.*;
import com.homesharing.dao.impl.*;
import com.homesharing.model.Home;
import com.homesharing.model.User;
import com.homesharing.service.HomeMgtService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.HomeMgtServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/my-home")
public class MyHomeListServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CreateHomeServlet.class);
    private HomeMgtService homeMgtService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        HomeDAO homeDAO = new HomeDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        PriceDAO priceDAO = new PriceDAOImpl();
        HomeImageDAO homeImgDAO = new HomeImageDAOImpl();
        AmentityHomeDAO amentityDAO = new AmentityHomeDAOImpl();
        FireEquipHomeDAO fireEquipDAO = new FireEquipHomeImpl();
        this.homeMgtService = new HomeMgtServiceImpl(homeDAO, priceDAO, homeImgDAO, amentityDAO, fireEquipDAO);
        this.userService = new UserServiceImpl(userDAO, null, null, null);
    }

    /**
     * Handles GET request to display host's home list.
     *
     * @param req  HttpServletRequest object
     * @param resp HttpServletResponse object
     * @throws ServletException if an error occurs during request processing
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cookieValue = CookieUtil.getCookie(req, "id");
        if (cookieValue != null) {
            User user = null;

            user = userService.getUser(Integer.parseInt(cookieValue));
            List<Home> homeList = homeMgtService.getPersonalHome(user.getId());

            // Convert homeList to JSON using Gson
            Gson gson = new Gson();
            String homeListJson = gson.toJson(homeList);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");

            // Send the JSON data in response
            req.setAttribute("homeList", homeListJson);
            req.getRequestDispatcher("personal-homes.jsp").forward(req, resp);

        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
        }
    }
}
