package com.homesharing.service;

import java.io.UnsupportedEncodingException;

public interface TokenService {
    boolean checkToken(String token, int userId);

    boolean sendToken(String email, int userId);

}
