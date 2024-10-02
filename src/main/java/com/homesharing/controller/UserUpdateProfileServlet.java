package com.homesharing.controller;

import com.homesharing.model.Preference;
import com.homesharing.model.User;
import com.homesharing.service.UserProfileService;
import com.homesharing.service.UserUpdateProfileService;
import com.homesharing.service.impl.UserProfileServiceImpl;
import com.homesharing.service.impl.UserUpdateProfileServiceImpl;
import com.homesharing.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;

@MultipartConfig()
@WebServlet("/user-update-profile")
public class UserUpdateProfileServlet extends HttpServlet {

    private UserUpdateProfileService userUpdateProfileService;
    private UserProfileService userProfileService;
    private static final String UPLOAD_DIRECTORY = "assets/img/user_avatar";

    @Override
    public void init() throws ServletException {
        this.userUpdateProfileService = new UserUpdateProfileServiceImpl();
        this.userProfileService = new UserProfileServiceImpl();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = userProfileService.getUser(Integer.parseInt(Objects.requireNonNull(CookieUtil.getCookie(req, "id"))));
        Preference preference = userProfileService.getPreference(Integer.parseInt(Objects.requireNonNull(CookieUtil.getCookie(req, "id"))));

        req.setAttribute("user", user);
        req.setAttribute("preference", preference);

        req.getRequestDispatcher("/user-update-profile.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String dob = req.getParameter("dob");

        String r_cleanliness = req.getParameter("cleanliness");
        String r_smoking = req.getParameter("smoking");
        String r_drinking = req.getParameter("drinking");
        String r_interaction = req.getParameter("interaction");
        String r_cooking = req.getParameter("cooking");
        String r_pet = req.getParameter("pet");
        String preference = req.getParameter("preference");

        Part avatarPart = req.getPart("avatar");
        String avatarFileName = null;

        if (avatarPart != null && avatarPart.getSize() > 0) {
            avatarFileName = Path.of(avatarPart.getSubmittedFileName()).getFileName().toString();
            String uploadDir = "D:\\Java\\HomeSharingWebsite\\src\\main\\webapp\\assets\\img\\user-avatar";
            avatarPart.write(uploadDir + File.separator + avatarFileName);

            avatarFileName = "user-avatar" + "/" + avatarFileName;
        }

        String userId = CookieUtil.getCookie(req, "id");

        if (userId != null) {
            try {
                User user = new User();
                user.setId(Integer.parseInt(userId));
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPhoneNumber(phone);
                user.setDob(LocalDate.parse(dob));


                if (avatarFileName != null) {
                    user.setAvatar(avatarFileName);
                } else {
                    user.setAvatar(userUpdateProfileService.getUserAvatar(Integer.parseInt(userId)));
                }


                int rowsUpdated = userUpdateProfileService.updateUserProfile(user);

                if (rowsUpdated > 0) {

                    Preference pref = new Preference();
                    pref.setUserId(Integer.parseInt(userId));


                    pref.setCleanliness(r_cleanliness != null ? Integer.parseInt(r_cleanliness) : 0); // Giá trị mặc định là 0 nếu null
                    pref.setSmoking(r_smoking != null ? Integer.parseInt(r_smoking) : 0);
                    pref.setDrinking(r_drinking != null ? Integer.parseInt(r_drinking) : 0);
                    pref.setInteraction(r_interaction != null ? Integer.parseInt(r_interaction) : 0);
                    pref.setCooking(r_cooking != null ? Integer.parseInt(r_cooking) : 0);
                    pref.setPet(r_pet != null ? Integer.parseInt(r_pet) : 0);


                    int rowsUpdatedPref = 0;
                    if ("false".equals(preference)) {
                        rowsUpdatedPref = userUpdateProfileService.insertUserPreference(pref);
                    } else {
                        rowsUpdatedPref = userUpdateProfileService.updateUserPreference(pref);
                    }




                    if (rowsUpdatedPref > 0) {
                        resp.sendRedirect("user-profile");
                    } else {
                        req.setAttribute("errorMessage", "Không cập nhật được sở thích.");
                        req.getRequestDispatcher("user-update-profile.jsp").forward(req, resp);
                    }

                } else {
                    req.setAttribute("errorMessage", "Không có thay đổi nào được thực hiện.");
                    req.getRequestDispatcher("user-update-profile.jsp").forward(req, resp);
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID");
            }
        } else {
            resp.sendRedirect("login.jsp");
        }
    }
}
