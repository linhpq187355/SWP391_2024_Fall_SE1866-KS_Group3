package com.homesharing.service.impl;

import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.service.ResetPasswordService;

public class ResetPasswordServiceImpl implements ResetPasswordService {
    private final UserDAO userDao;

    public ResetPasswordServiceImpl() {
        this.userDao = new UserDAOImpl();
    }

    @Override
    public int resetPassword(int userId, String newPassword) {
        return userDao.resetPassword(newPassword, userId);
    }
}
