package com.homesharing.service;

public interface EmailService {
    boolean checkToken(String token, int userId);
}
