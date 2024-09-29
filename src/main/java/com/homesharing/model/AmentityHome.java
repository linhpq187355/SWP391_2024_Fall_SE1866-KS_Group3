package com.homesharing.model;

public class AmentityHome {
    private int amenityId;
    private int homesId;

    public AmentityHome() {
    }

    public AmentityHome(int amenityId, int homesId) {
        this.amenityId = amenityId;
        this.homesId = homesId;
    }

    public int getAmenityId() {
        return amenityId;
    }
    public void setAmenityId(int amenityId) {
        this.amenityId = amenityId;
    }
    public int getHomesId() {
        return homesId;
    }
    public void setHomesId(int homesId) {
        this.homesId = homesId;
    }
}
