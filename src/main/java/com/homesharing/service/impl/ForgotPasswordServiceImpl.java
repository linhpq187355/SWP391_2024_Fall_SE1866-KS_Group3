package com.homesharing.service.impl;

import com.homesharing.conf.Config;
import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Token;
import com.homesharing.model.User;
import com.homesharing.service.ForgotPasswordService;
import com.homesharing.util.SendingEmail;
import jakarta.mail.MessagingException;

public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    private UserDAO userDao;
    private TokenDAO tokenDao;

    public ForgotPasswordServiceImpl(UserDAO userDao, TokenDAO tokenDao) {
        this.userDao = userDao;
        this.tokenDao = tokenDao;
    }


    @Override
    public boolean sendResetPasswordToken(String email) {
        User user = userDao.findUserByEmail(email);
        if (user != null) {
            Token oldToken = tokenDao.findToken(user.getId());
            String tokenCode = oldToken.getToken();

            // Get base URL from properties
            String baseUrl = Config.getBaseUrl();
            String subject = "Xác nhận email";
            String content = "Click vào link sau để đặt lại mật khẩu: "
                    + baseUrl + "/reset-password?code=" + tokenCode
                    + "&userId=" + user.getId();
            try {
                SendingEmail.sendMail(email, subject, content);
                return true;
            } catch (MessagingException e) {
                throw new GeneralException("Error while sending email");
            }
        }
        return false;
    }
}
