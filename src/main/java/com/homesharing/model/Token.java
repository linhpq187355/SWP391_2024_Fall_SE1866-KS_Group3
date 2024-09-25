package com.homesharing.model;

import java.time.LocalDateTime;

public class Token {
    private int userId;
    private String token;
    private LocalDateTime requestedTime;
    private boolean isVerified;
    public Token() {
    }
    public Token(int userId, String token, LocalDateTime requestedTime, boolean isVerified) {
        this.userId = userId;
        this.token = token;
        this.requestedTime = requestedTime;
        this.isVerified = isVerified;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(LocalDateTime requestedTime) {
        this.requestedTime = requestedTime;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public boolean isTokenExpired(int timeLimitMinutes) {
        LocalDateTime currentTime = LocalDateTime.now();
        return requestedTime.plusMinutes(timeLimitMinutes).isBefore(currentTime);
    }


}
