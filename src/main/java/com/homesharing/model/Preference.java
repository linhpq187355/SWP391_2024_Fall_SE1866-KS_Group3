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

public class Preference {
    private int id;
    private int userId;
    private int cleanliness;
    private int smoking;
    private int drinking;
    private int interaction;
    private int cooking;
    private int pet;
    private int guest;

    public Preference() {
    }

    public Preference(int cleanliness, int smoking, int drinking, int interaction, int cooking, int pet, int guest, int userId) {
        this.userId = userId;
        this.cleanliness = cleanliness;
        this.smoking = smoking;
        this.drinking = drinking;
        this.interaction = interaction;
        this.cooking = cooking;
        this.pet = pet;
        this.guest = guest;
    }

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

    public int getGuest() {
        return guest;
    }
    public void setGuest(int guest) {
        this.guest = guest;
    }
}
