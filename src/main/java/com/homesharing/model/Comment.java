package com.homesharing.model;

import java.time.LocalDateTime;

public class Comment {
    private int id;               // ID của bình luận
    private int postId;          // ID bài viết mà bình luận thuộc về
    private int userId;          // ID người dùng đã bình luận
    private String content;       // Nội dung bình luận
    private LocalDateTime createdAt; // Thời gian tạo bình luận
    private String status;        // Trạng thái bình luận (active, deleted, v.v.)
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Constructor
    public Comment() {
    }

    public Comment(int postId, int userId, String content, String status) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.status = status;
        this.createdAt = LocalDateTime.now(); // Gán thời gian tạo tại thời điểm tạo
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
