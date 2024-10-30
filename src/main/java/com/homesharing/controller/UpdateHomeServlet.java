/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-16      1.0              Lam Tien Thang      First Implement
 */

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
import com.homesharing.dao.impl.HomeImageDAOImpl;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.dao.impl.ProvinceDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.dao.impl.WardDAOImpl;
import com.homesharing.model.Amentity;
import com.homesharing.model.AmentityHome;
import com.homesharing.model.District;
import com.homesharing.model.FireEquipment;
import com.homesharing.model.FireEquipmentsHome;
import com.homesharing.model.Home;
import com.homesharing.model.HomeImage;
import com.homesharing.model.HomeType;
import com.homesharing.model.Price;
import com.homesharing.model.Province;
import com.homesharing.model.User;
import com.homesharing.model.Ward;
import com.homesharing.service.HomeDetailService;
import com.homesharing.service.HomeMgtService;
import com.homesharing.service.LocationService;
import com.homesharing.service.SubmissionFormService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.HomeDetailServiceImpl;
import com.homesharing.service.impl.HomeMgtServiceImpl;
import com.homesharing.service.impl.LocationServiceImpl;
import com.homesharing.service.impl.SubmissonFormServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.CookieUtil;
import com.homesharing.util.ImageUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50) // 50 MB
@WebServlet("/update-home")
public class UpdateHomeServlet extends HttpServlet {
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
        HomeImageDAO homeImgDAO = new HomeImageDAOImpl();
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
        Home home = homeDetailService.getHomeById(homeId);

        Ward ward = locationService.getWard(home.getWardId());
        District district = locationService.getDistrict(ward.getDistrictId());
        Province selectedProvince = locationService.getProvince(district.getProvinceId());

        HomeType selectedHomeType = homeDetailService.getHomeTypeByHomeId(homeId);
        List<FireEquipment> selectedFireEquip = homeDetailService.getHomeFireEquipmentsByHomeId(homeId);
        List<Integer> selectedFireEquipIds = selectedFireEquip.stream()
                .map(FireEquipment::getId)
                .collect(Collectors.toList());
        List<Amentity> selectedAmentity = homeDetailService.getHomeAmenitiesByHomeId(homeId);
        List<Integer> selectedAmentityIds = selectedAmentity.stream()
                .map(Amentity::getId)
                .collect(Collectors.toList());

        List<Price> prices = homeDetailService.getHomePricesByHomeId(homeId);
        List<HomeType> hometypes = submissionFormService.getHomeTypes();
        List<Amentity> amentities = submissionFormService.getAmentities();
        List<FireEquipment> fireEquipments = submissionFormService.getFireEquipments();

        List<HomeImage> images = homeDetailService.getHomeImagesByHomeId(homeId);
        List<Province> provinces = locationService.getProvinces();
        LocalDate moveInDate = home.getMoveInDate();

        req.setAttribute("selectedAmentityIds", selectedAmentityIds);
        req.setAttribute("selectedFireEquipIds", selectedFireEquipIds);
        req.setAttribute("selectedProvince", selectedProvince);
        req.setAttribute("selectedHomeType", selectedHomeType);
        req.setAttribute("selectedFireEquip", selectedFireEquip);
        req.setAttribute("selectedAmentity", selectedAmentity);

        req.setAttribute("hometypes", hometypes);
        req.setAttribute("home", home);
        req.setAttribute("provinces", provinces);
        req.setAttribute("district", district);
        req.setAttribute("prices", prices);
        req.setAttribute("amentities", amentities);
        req.setAttribute("fireEquipments", fireEquipments);
        req.setAttribute("movindate", moveInDate);
        req.setAttribute("images", images);
        req.getRequestDispatcher("/update-demo.jsp").forward(req, resp);
    }

    /**
     * Handles POST requests for user registration. Retrieves user input,
     * validates it using the {@link UserService}, and creates a new user account.
     * Redirects to the appropriate page upon successful registration or displays an error message.
     *
     * @param req  The HttpServletRequest object containing user input.
     * @param resp The HttpServletResponse object.
     * @throws ServletException If a servlet error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String cookieValue = CookieUtil.getCookie(req, "id");
            if (cookieValue != null) {
                //Fetch user info from cookie
                User user = userService.getUser(Integer.parseInt(cookieValue));

                // Process the submission form data
                int homeId = Integer.parseInt(req.getParameter("home-id"));
                int homeTypeId = Integer.parseInt(req.getParameter("home-type"));
                String addressDetail = req.getParameter("address-detail");
                String name = req.getParameter("home-name");
                String homeDescription = req.getParameter("home-description");
                String tenantDescription = req.getParameter("tenant-description");
                BigDecimal area = new BigDecimal(req.getParameter("area"));
                String direction = req.getParameter("direction"); // This is the house direction

                int leaseDuration = Integer.parseInt(req.getParameter("leaseDuration"));
                String moveInDateString = req.getParameter("moveInDate");
                LocalDate moveInDate = null;

                if (moveInDateString != null && !moveInDateString.isEmpty()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    moveInDate = LocalDate.parse(moveInDateString, formatter);
                }

                int numOfBedroom = Integer.parseInt(req.getParameter("numOfBedroom"));
                int numOfBath = Integer.parseInt(req.getParameter("numOfBath"));

                // Amenities and Fire Equipment
                String[] amenityIds = req.getParameterValues("amentityIds");
                String[] fireEquipIds = req.getParameterValues("fireEquipIds");

                int wardId = Integer.parseInt(req.getParameter("ward"));

                Home home = new Home();
                home.setId(homeId);
                home.setName(name);
                home.setHomeTypeId(homeTypeId);
                home.setAddress(addressDetail);
                home.setHomeDescription(homeDescription);
                home.setTenantDescription(tenantDescription);
                home.setArea(area);
                home.setOrientation(direction);
                home.setLeaseDuration(leaseDuration);
                home.setMoveInDate(moveInDate);
                home.setNumOfBedroom(numOfBedroom);
                home.setNumOfBath(numOfBath);
                home.setWardId(wardId);
                home.setCreatedBy(user.getId());

                String latitudeStr = req.getParameter("latitude");
                if (latitudeStr != null && !latitudeStr.trim().isEmpty()) {
                    BigDecimal latitude = new BigDecimal(latitudeStr);
                    home.setLatitude(latitude);
                }

                String longitudeStr = req.getParameter("longitude");
                if (longitudeStr != null && !longitudeStr.trim().isEmpty()) {
                    BigDecimal longitude = new BigDecimal(longitudeStr);
                    home.setLongitude(longitude);
                }

                // Attempt to update the home
                homeMgtService.updatePersonalHome(home);
                // Attempt to clear all the images
                homeMgtService.clearImageByHomeId(homeId);

                String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                List<String> imageLocations = new ArrayList<>();

                // Process the uploaded files
                Collection<Part> fileParts = req.getParts();
                for (Part part : fileParts) {
                    if (part.getName().equals("images") && part.getSize() > 0) {
                        String fileName = ImageUtil.getUniqueFileName(part.getSubmittedFileName());
                        String filePath = uploadPath + File.separator + fileName;
                        try {
                            part.write(filePath);
                        } catch (IOException e) {
                            logger.error("Failed to upload file: " + e.getMessage(), e);
                            // Handle the error appropriately
                        }
                        // Store the relative path
                        imageLocations.add(UPLOAD_DIRECTORY + File.separator + fileName);
                    }
                }

                Price price = new Price();
                price.setPrice(Integer.parseInt(req.getParameter("price")));
                price.setHomesId(homeId);
                submissionFormService.savePrice(price);

                for (String imageLocation : imageLocations) {
                    HomeImage homeImage = new HomeImage();
                    homeImage.setImgUrl(imageLocation);
                    homeImage.setHomeId(homeId);
                    submissionFormService.saveHomeImages(homeImage);
                }

                homeMgtService.clearAmentityByHomeId(homeId);
                for (String amenityId : amenityIds) {
                    AmentityHome amentityHome = new AmentityHome();
                    amentityHome.setAmenityId(Integer.parseInt(amenityId));
                    amentityHome.setHomesId(homeId); // Replace homeId with the actual ID of the home
                    // Save the AmentityHome object to the database
                    submissionFormService.saveAmentityHome(amentityHome);
                }

                homeMgtService.clearFireEquipByHomeId(homeId);
                for (String fireEquipId : fireEquipIds) {
                    FireEquipmentsHome fireEquipmentsHome = new FireEquipmentsHome();
                    fireEquipmentsHome.setFireEquipmentsId(Integer.parseInt(fireEquipId));
                    fireEquipmentsHome.setHomesId(homeId);
                    submissionFormService.saveFireEquipHome(fireEquipmentsHome);
                }

                req.getSession().setAttribute("message", "Cập nhật tin đăng thành công!");
                req.getSession().setAttribute("messageType", "success");
                resp.sendRedirect(req.getContextPath() + "/home-page");
            }
        } catch (Exception e) {
            logger.error("An error occurred during the request: " + e.getMessage(), e);
            // Redirect to an error page
            resp.sendRedirect("404.jsp");
        }
    }
}
