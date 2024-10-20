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
import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private int id;
    private String googleId;
    private int rolesId;
    private String email;
    private String hashedPassword;
    private String phoneNumber;
    private String userName;
    private String firstName;
    private String lastName;
    private String avatar;
    private LocalDate dob;
    private String address;
    private String gender;
    private String citizenNumber;
    private String status;
    private boolean isVerified;
    private LocalDateTime lastModified;
    private LocalDateTime createdAt;
    private String wardsId;
    private String duration;
    private int minBudget;
    private int maxBudget;
    private LocalDate earliestMoveIn;
    private LocalDate latestMoveIn;
    private int prefProv;

    // Constructors
    public User() {}

    public User(String firstName, String lastName,String email, String hashedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.status = "active";
        this.isVerified = false;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRolesId() {
        return rolesId;
    }

    public void setRolesId(int rolesId) {
        this.rolesId = rolesId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public String getVerify(){
        return isVerified?"Verified":"Not Verified";
    }
    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCitizenNumber() {
        return citizenNumber;
    }

    public void setCitizenNumber(String citizenNumber) {
        this.citizenNumber = citizenNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getWardsId() {
        return wardsId;
    }

    public void setWardsId(String wardsId) {
        this.wardsId = wardsId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getMinBudget() {
        return minBudget;
    }

    public void setMinBudget(int minBudget) {
        this.minBudget = minBudget;
    }

    public int getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(int maxBudget) {
        this.maxBudget = maxBudget;
    }

    public LocalDate getEarliestMoveIn() {
        return earliestMoveIn;
    }

    public void setEarliestMoveIn(LocalDate earliestMoveIn) {
        this.earliestMoveIn = earliestMoveIn;
    }

    public LocalDate getLatestMoveIn() {
        return latestMoveIn;
    }

    public void setLatestMoveIn(LocalDate latestMoveIn) {
        this.latestMoveIn = latestMoveIn;
    }

    public int getPrefProv() {
        return prefProv;
    }

    public void setPrefProv(int prefProv) {
        this.prefProv = prefProv;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", rolesId=" + rolesId +
                ", email='" + email + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", username='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", dob=" + dob +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", citizenNumber='" + citizenNumber + '\'' +
                ", status='" + status + '\'' +
                ", isVerified=" + isVerified +
                ", lastModified=" + lastModified +
                ", createdAt=" + createdAt +
                '}';
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
}
