package com.homesharing.controller;

import com.homesharing.dao.AmentityHomeDAO;
import com.homesharing.dao.DistrictDAO;
import com.homesharing.dao.FireEquipHomeDAO;
import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.HomeImageDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.ProvinceDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.WardDAO;
import com.homesharing.dao.impl.AmentityHomeDAOImpl;
import com.homesharing.dao.DistrictDAOImpl;
import com.homesharing.dao.impl.FireEquipHomeImpl;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.HomeImageDAOImp;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.dao.impl.ProvinceDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.dao.impl.WardDAOImpl;
import com.homesharing.service.HomeDetailService;
import com.homesharing.service.HomeMgtService;
import com.homesharing.service.LocationService;
import com.homesharing.service.SubmissionFormService;
import com.homesharing.service.impl.HomeDetailServiceImpl;
import com.homesharing.service.impl.HomeMgtServiceImpl;
import com.homesharing.service.impl.LocationServiceImpl;
import com.homesharing.service.impl.SubmissonFormServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/delete-home")
public class DeleteHomeServlet extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "assets/img/home-images";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final Logger logger = LoggerFactory.getLogger(CreateHomeServlet.class);
    private UserServiceImpl userService;
    private HomeMgtService homeMgtService;
    private HomeDetailService homeDetailService;
    private LocationService locationService;
    private SubmissionFormService submissionFormService;

    /**
     * Initializes services and DAOs used by the servlet.
     * This method is called when the servlet is first loaded.
     *
     * @throws ServletException if an error occurs during initialization
     */
    @Override
    public void init() throws ServletException {
        HomeDAO homeDAO = new HomeDAOImpl();
        PriceDAO priceDAO = new PriceDAOImpl();
        ProvinceDAO provinceDAO = new ProvinceDAOImpl();
        DistrictDAO districtDAO = new DistrictDAOImpl();
        WardDAO wardDAO = new WardDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        HomeImageDAO homeImgDAO = new HomeImageDAOImp();
        AmentityHomeDAO amentityDAO = new AmentityHomeDAOImpl();
        FireEquipHomeDAO fireEquipDAO = new FireEquipHomeImpl();
        this.homeMgtService = new HomeMgtServiceImpl(homeDAO, priceDAO, homeImgDAO, amentityDAO, fireEquipDAO);
        this.locationService = new LocationServiceImpl(provinceDAO, districtDAO, wardDAO);
        this.homeDetailService = new HomeDetailServiceImpl();
        this.submissionFormService = new SubmissonFormServiceImpl();
        userService = new UserServiceImpl(userDAO, null, null, null);
    }

    /**
     * Handles HTTP GET requests to retrieve user and preference data.
     * Retrieves the user ID from a cookie, fetches user and preference
     * details, and forwards the data to the JSP page for display.
     * Redirects to the login page if the user ID is not available.
     *
     * @param req  the HttpServletRequest object containing client request data
     * @param resp the HttpServletResponse object for sending the response
     * @throws ServletException if an error occurs while processing the request
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int homeId = Integer.parseInt(req.getParameter("id"));
        homeMgtService.deletePersonalHome(homeId);
        req.getRequestDispatcher("/my-home").forward(req, resp);
    }
}
