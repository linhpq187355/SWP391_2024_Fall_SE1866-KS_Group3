/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-01      1.0              Pham Quang Linh     First Implement
 */

package com.homesharing.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Home {
    private int id;
    private String name;
    private String address;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String orientation;
    private BigDecimal area;
    private int leaseDuration;
    private LocalDate moveInDate;
    private int numOfBedroom;
    private int numOfBath;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String homeDescription;
    private String tenantDescription;
    private int wardId;
    private int homeTypeId; // One-to-one relationship with HomeType
    private int createdBy;
    private int priceId;
    private List<String> images;
    private int price;
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public int getLeaseDuration() {
        return leaseDuration;
    }

    public void setLeaseDuration(int leaseDuration) {
        this.leaseDuration = leaseDuration;
    }

    public LocalDate getMoveInDate() {
        return moveInDate;
    }

    public void setMoveInDate(LocalDate moveInDate) {
        this.moveInDate = moveInDate;
    }

    public int getNumOfBedroom() {
        return numOfBedroom;
    }

    public void setNumOfBedroom(int numOfBedroom) {
        this.numOfBedroom = numOfBedroom;
    }

    public int getNumOfBath() {
        return numOfBath;
    }

    public void setNumOfBath(int numOfBath) {
        this.numOfBath = numOfBath;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getHomeDescription() {
        return homeDescription;
    }

    public void setHomeDescription(String homeDescription) {
        this.homeDescription = homeDescription;
    }

    public String getTenantDescription() {
        return tenantDescription;
    }

    public void setTenantDescription(String tenantDescription) {
        this.tenantDescription = tenantDescription;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public int getHomeTypeId() {
        return homeTypeId;
    }

    public void setHomeTypeId(int homeTypeId) {
        this.homeTypeId = homeTypeId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setPriceId(int priceId) { this.priceId = priceId; }

    public int getPriceId() { return priceId; }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

}
