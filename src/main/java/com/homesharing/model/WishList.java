package com.homesharing.model;

import java.time.LocalDateTime;

public class WishList {
    private int homeId;
    private int userId;
    private LocalDateTime  createdDate;
    private String status;

    public WishList() {
    }

    public WishList(int homeId, String status, LocalDateTime createdDate, int userId) {
        this.homeId = homeId;
        this.status = status;
        this.createdDate = createdDate;
        this.userId = userId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
