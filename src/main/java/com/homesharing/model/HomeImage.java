package com.homesharing.model;

public class HomeImage {
    private String imgUrl;
    private String type;
    private String status;
    private int homeId;

    public HomeImage() {
    }

    public HomeImage(String imgUrl, String type, String status, int homeId) {
        this.imgUrl = imgUrl;
        this.type = type;
        this.status = status;
        this.homeId = homeId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }
}
