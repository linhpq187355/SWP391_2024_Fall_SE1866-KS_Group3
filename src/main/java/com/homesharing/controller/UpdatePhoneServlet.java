package com.homesharing.controller;

import com.homesharing.dao.PreferenceDAO;
import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.dao.impl.TokenDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.service.PreferenceService;
import com.homesharing.service.TokenService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.PreferenceServiceImpl;
import com.homesharing.service.impl.TokenServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/update-phone")
public class UpdatePhoneServlet extends HttpServlet {
    private transient UserService userService;// Mark userService as transient
    private static final Logger logger = LoggerFactory.getLogger(UpdatePhoneServlet.class); // Logger instance

    /**
     * Initializes the VerifyEmailServlet by creating an instance of the TokenService.
     * This method is called once when the servlet is first loaded.
     */
    @Override
    public void init() {
        // Create instances of UserDao and TokenDao
        UserDAO userDao = new UserDAOImpl();
        TokenDAO tokenDao = new TokenDAOImpl();
        PreferenceDAO preferenceDao = new PreferenceDAOImpl();
        TokenService tokenService = new TokenServiceImpl(tokenDao);
        PreferenceService preferenceService = new PreferenceServiceImpl(preferenceDao);
        // Inject UserDao into UserServiceImpl
        userService = new UserServiceImpl(userDao, tokenDao, tokenService,preferenceService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy danh sách cookie từ request
            Cookie[] cookies = request.getCookies();
            String userId = null;

            // Kiểm tra từng cookie để tìm cookie có tên là "id"
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("id".equals(cookie.getName())) {
                        userId = cookie.getValue();  // Lấy giá trị của cookie "id"
                        break;
                    }
                }
            }

            // Kiểm tra nếu userId không tồn tại trong cookie (người dùng chưa đăng nhập)
            if (userId == null) {
                request.setAttribute("error", "User is not logged in or session has expired.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);  // Chuyển hướng về trang đăng nhập
                return;
            }
            // Đưa userId vào request attribute để truyền sang JSP
            request.getSession().setAttribute("userId", userId);
            request.getRequestDispatcher("/update-phone.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            // Handle any runtime exceptions thrown by the service or servlet
            request.setAttribute("error", "An error occurred during registration: " + e.getMessage());
            ServletUtils.forwardToErrorPage(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phone = request.getParameter("phone");
        String userId = request.getSession().getAttribute("userId").toString();
        try {
            int result = userService.updatePhone(userId, phone);
            // Reset số lần thử OTP
            request.getSession().setAttribute("otpAttempts", 0);

            if(result == 1){
                // Thiết lập thông báo thành công
                request.getSession().setAttribute("message", "Cập nhật số điện thoại thành công!");
                request.getSession().setAttribute("messageType", "success");

                response.sendRedirect(request.getContextPath() + "/home-page");

            } else {
                ServletUtils.forwardWithMessage(request,response,"Có lỗi xảy ra, vui lòng đăng nhập lại.");
            }
        } catch (Exception e) {
            logger.error("Error updating phone number: ", e);
            // Thiết lập thông báo lỗi
            request.setAttribute("error", "Cập nhật số điện thoại thất bại: " + e.getMessage());
            ServletUtils.forwardToErrorPage(request, response);
        }


    }

}
