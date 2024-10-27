package com.homesharing.model;

import java.util.Date;

public class Conversation {
    private int id;
    private int userOne;
    private int userTwo;
    private Date time;
    private String status;
    public Conversation() {

    }
    public Conversation(int id, int userOne, int userTwo, Date time, String status) {
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
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
