package com.homesharing.service;

public interface TokenService {
    boolean checkToken(String token, int userId);

    boolean sendToken(String email, int userId);

}
