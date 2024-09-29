package com.homesharing.service;

public interface ResetPasswordService {
    int resetPassword(int userId, String newPassword);
}
