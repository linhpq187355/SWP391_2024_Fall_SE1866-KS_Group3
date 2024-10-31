package com.homesharing.model;

public class UserPermisson {
    private int userId;
    private int permissonId;

    public UserPermisson() {
    }

    public UserPermisson(int userId, int permissonId) {
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
