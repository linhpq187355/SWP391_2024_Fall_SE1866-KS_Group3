package com.homesharing.controller;

import com.homesharing.dao.PreferenceDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.*;
import com.homesharing.service.impl.PreferenceServiceImpl;
import com.homesharing.service.impl.SubmissonFormServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.CookieUtil;
import com.homesharing.util.ImageUtil;
import com.homesharing.util.ServletUtils;
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

@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50) // 50 MB
@WebServlet("/submit-home")
public class CreateHomeServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CreateHomeServlet.class);
    private static final String UPLOAD_DIRECTORY = "assets/img/home-images";
    private static final String ERROR_ATTRIBUTE = "error";
    private SubmissonFormServiceImpl submissonFormService;
    private UserServiceImpl userService;
    private PreferenceServiceImpl preferenceService;

    /**
     * Initializes the servlet by creating instances of the submission form service and user profile service.
     *
     * @throws ServletException if an error occurs during initialization
     */
    @Override
    public void init() throws ServletException {
        UserDAO userDAO = new UserDAOImpl();
        PreferenceDAO preferenceDAO = new PreferenceDAOImpl();
        submissonFormService = new SubmissonFormServiceImpl();
        userService = new UserServiceImpl(userDAO,null,null,null);
        preferenceService = new PreferenceServiceImpl(preferenceDAO);
    }

    /**
     * Handles HTTP GET requests by retrieving the user's information from the cookie and displaying the submission form.
     *
     * @param req  the HTTP request
     * @param resp the HTTP response
     * @throws ServletException if an error occurs during the request
     * @throws IOException      if an I/O error occurs during the request
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cookieValue = CookieUtil.getCookie(req, "id");
        if (cookieValue != null) {
            User user = null;

                user = userService.getUser(Integer.parseInt(cookieValue));
                Preference preference = preferenceService.getPreference(Integer.parseInt(cookieValue));

            if (user.getRolesId() != 2) {
                List<HomeType> homeTypes = submissonFormService.getHomeTypes();
                List<Amentity> amentities = submissonFormService.getAmentities();
                List<FireEquipment> fireEquipments = submissonFormService.getFireEquipments();
                List<Province> provinces = submissonFormService.getProvinces();

                req.setAttribute("homeTypes", homeTypes);
                req.setAttribute("amentities", amentities);
                req.setAttribute("fireEquipments", fireEquipments);
                req.setAttribute("provinces", provinces);
                req.setAttribute("preference", preference);
                req.getRequestDispatcher("submit-home.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/home-page");
            }
        } else {
            // Handle the case where the cookie is null
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

    /**
     * Handles HTTP POST requests by processing the submission form data and saving the home information to the database.
     *
     * @param req  the HTTP request
     * @param resp the HTTP response
     * @throws IOException if an I/O error occurs during the request
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String cookieValue = CookieUtil.getCookie(req, "id");
            if (cookieValue != null) {
                //Fetch user info from cookie
                User user = userService.getUser(Integer.parseInt(cookieValue));

                // Process the submission form data
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

                // Attempt to save the home
                int homeId = submissonFormService.saveHome(home);
                if (homeId > 0) {
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
                    submissonFormService.savePrice(price);

                    for (String imageLocation : imageLocations) {
                        HomeImage homeImage = new HomeImage();
                        homeImage.setImgUrl(imageLocation);
                        homeImage.setHomeId(homeId);
                        submissonFormService.saveHomeImages(homeImage);
                    }

                    for(String amenityId : amenityIds) {
                        AmentityHome amentityHome = new AmentityHome();
                        amentityHome.setAmenityId(Integer.parseInt(amenityId));
                        amentityHome.setHomesId(homeId); // Replace homeId with the actual ID of the home
                        // Save the AmentityHome object to the database
                        submissonFormService.saveAmentityHome(amentityHome);
                    }

                    for (String fireEquipId : fireEquipIds) {
                        FireEquipmentsHome fireEquipmentsHome = new FireEquipmentsHome();
                        fireEquipmentsHome.setFireEquipmentsId(Integer.parseInt(fireEquipId));
                        fireEquipmentsHome.setHomesId(homeId);
                        submissonFormService.saveFireEquipHome(fireEquipmentsHome);
                    }

                    req.getSession().setAttribute("message", "Đăng tin thành công!");
                    req.getSession().setAttribute("messageType", "success");
                    resp.sendRedirect(req.getContextPath() + "/home-page");
                } else {
                    // Set error message if saving home fails
                    req.setAttribute(ERROR_ATTRIBUTE, "Có lỗi trong quá trình đăng bài");
                    ServletUtils.forwardToErrorPage(req, resp);
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred during the request: " + e.getMessage(), e);
            // Redirect to an error page
            resp.sendRedirect("404.jsp");
        }
    }
}
