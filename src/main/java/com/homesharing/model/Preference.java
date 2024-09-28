package com.homesharing.model;

public class Preference {
    private int id;
    private int userId;
    private int cleanliness;
    private int smoking;
    private int drinking;
    private int interaction;
    private int cooking;
    private int pet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(int cleanliness) {
        this.cleanliness = cleanliness;
    }

    public int getSmoking() {
        return smoking;
    }

    public void setSmoking(int smoking) {
        this.smoking = smoking;
    }

    public int getDrinking() {
        return drinking;
    }

    public void setDrinking(int drinking) {
        this.drinking = drinking;
    }

    public int getInteraction() {
        return interaction;
    }

    public void setInteraction(int interaction) {
        this.interaction = interaction;
    }

    public int getCooking() {
        return cooking;
    }

    public void setCooking(int cooking) {
        this.cooking = cooking;
    }

    public int getPet() {
        return pet;
    }

    public void setPet(int pet) {
        this.pet = pet;
    }
}
