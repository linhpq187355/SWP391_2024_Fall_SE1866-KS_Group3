package com.homesharing.model;

import java.time.LocalDateTime;

public class Price {
    private int id;
    private int price;
    private int homesId;
    private LocalDateTime createdDate;

    public Price() {
    }

    public Price(int id, int price, LocalDateTime createdDate) {
        this.id = id; this.price = price; this.createdDate = createdDate;
    }

    public int getHomesId() {
        return homesId;
    }

    public void setHomesId(int homesId) {
        this.homesId = homesId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}
