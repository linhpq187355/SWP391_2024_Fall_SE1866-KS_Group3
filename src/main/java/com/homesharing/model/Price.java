package com.homesharing.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Price {
    private int id;
    private int price;
    private LocalDateTime createdDate;
    private int homesId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public int getHomesId() {
        return homesId;
    }

    public void setHomesId(int homesId) {
        this.homesId = homesId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
