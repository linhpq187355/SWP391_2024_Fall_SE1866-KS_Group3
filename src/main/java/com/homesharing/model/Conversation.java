package com.homesharing.model;

import java.time.LocalDateTime;


public class Conversation {
    private int id;
    private int userOne;
    private int userTwo;
    private LocalDateTime time;
    private String status;
    public Conversation() {

    }
    public Conversation(int id, int userOne, int userTwo, LocalDateTime time, String status) {
        this.id = id;
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.time = time;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserOne() {
        return userOne;
    }
    public void setUserOne(int userOne) {
        this.userOne = userOne;
    }
    public int getUserTwo() {
        return userTwo;
    }
    public void setUserTwo(int userTwo) {
        this.userTwo = userTwo;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
