package com.homesharing.service.impl;

import com.homesharing.dao.UserDao;
import com.homesharing.dao.impl.UserDaoImpl;
import com.homesharing.service.ResetPasswordService;

public class ResetPasswordServiceImpl implements ResetPasswordService {
    private final UserDao userDao;

    public ResetPasswordServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public int resetPassword(int userId, String newPassword) {
        return userDao.resetPassword(newPassword, userId);
    }
}
