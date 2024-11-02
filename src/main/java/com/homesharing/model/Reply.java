package com.homesharing.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Reply {
    private int id;
    private String text;
    private LocalDateTime time;
    private String status;
    private int conversationId;
    private int userId;
    private String timeAgo;
    private String contentType;
    private String contentUrl;

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }
    public Reply() {}
    public Reply(int id, String text, LocalDateTime time, String status, int conversationId, int userId) {
        this.id = id;
        this.text = text;
        this.time = time;
        this.status = status;
        this.conversationId = conversationId;
        this.userId = userId;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
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
    public int getConversationId() {
        return conversationId;
    }
    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void calculateTimeAgo() {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(time, now);

        long seconds = duration.getSeconds();
        if (seconds < 60) {
            this.timeAgo = seconds + " giây trước";
        } else if (seconds < 3600) {
            long minutes = duration.toMinutes();
            this.timeAgo = minutes + " phút trước";
        } else if (seconds < 86400) {
            long hours = duration.toHours();
            this.timeAgo = hours + " giờ trước";
        } else {
            long days = duration.toDays();
            this.timeAgo = days + " ngày trước";
        }
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
}
