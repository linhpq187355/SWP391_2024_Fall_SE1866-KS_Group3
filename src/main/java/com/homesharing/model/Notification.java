package com.homesharing.model;

import java.time.LocalDateTime;

public class Notification {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private int id;
    private int receiverId;
    private String content;
    private LocalDateTime createdDate;
    private String status;
    private String type;
    private String url;

    public Notification(int id, int receiverId, String content, LocalDateTime createdDate, String status, String type, String url) {
    this.id = id;
    this.receiverId = receiverId;
    this.content = content;
    this.createdDate = createdDate;
    this.status = status;
    this.type = type;
    this.url = url;
    }

}
