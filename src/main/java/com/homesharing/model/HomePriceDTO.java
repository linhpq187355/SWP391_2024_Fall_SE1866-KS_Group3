package com.homesharing.model;

public class HomePriceDTO {
    private Home home;
    private int price;

    public HomePriceDTO(Home home, int price) {
        this.home = home;
        this.price = price;
    }

    public Home getHome() {
        return home;
    }

    public int getPrice() {
        return price;
    }
}

