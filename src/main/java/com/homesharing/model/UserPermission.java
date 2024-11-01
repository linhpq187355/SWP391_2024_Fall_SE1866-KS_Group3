package com.homesharing.model;

public class UserPermission {
    private int userId;
    private int permissonId;

    public UserPermission() {
    }

    public UserPermission(int userId, int permissonId) {
        this.userId = userId;
        this.permissonId = permissonId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPermissonId() {
        return permissonId;
    }

    public void setPermissonId(int permissonId) {
        this.permissonId = permissonId;
    }
}
