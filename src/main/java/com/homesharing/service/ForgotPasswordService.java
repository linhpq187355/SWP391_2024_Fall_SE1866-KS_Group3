package com.homesharing.service;

public interface ForgotPasswordService {
    boolean sendResetPasswordToken(String email);
}
