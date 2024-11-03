package com.homesharing.model;

import java.time.LocalDateTime;
import java.util.List;

public class BlogPost {
    private int id;
    private String title;
    private String content;
    private int authorId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedDate;
    private String status;
    private String authorName;
    private String imagePath;
    private String shortDescription;
    private List<Category> categories;
    public String getShortDescription() {
        return shortDescription;
    }
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    public BlogPost(int id, String title, String content, int authorId, LocalDateTime createdAt, LocalDateTime modifiedDate, String status, String authorName, String imagePath, String shortDescription) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.modifiedDate = modifiedDate;
        this.status = status;
        this.authorName = authorName;
        this.imagePath = imagePath;
        this.shortDescription = shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public BlogPost(int id, String title, LocalDateTime createdAt, int authorId, String content, LocalDateTime modifiedDate, String status, String authorName, String imagePath) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.content = content;
        this.modifiedDate = modifiedDate;
        this.status = status;
        this.authorName = authorName;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imageUrl) {
        this.imagePath = imageUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }


    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Constructors
    public BlogPost() {
    }


    public BlogPost(String title, String content, int authorId) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.createdAt = LocalDateTime.now(); // Đặt thời gian tạo mặc định là thời gian hiện tại
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAuthorId() {
        return authorId;
    }
}
