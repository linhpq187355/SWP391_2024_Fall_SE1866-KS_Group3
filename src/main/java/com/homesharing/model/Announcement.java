package com.homesharing.model;

import java.time.LocalDateTime;

public class Announcement {
    private int id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String status;
    private int createdBy;
    private int announcementTypeId;
    //create constructor
    public Announcement() {}
    public Announcement(int id, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate, String status, int createdBy, int announcementTypeId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.status = status;
        this.createdBy = createdBy;
        this.announcementTypeId = announcementTypeId;
    }
    //create getter, setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public LocalDateTime getModifiedDate() { return modifiedDate; }
    public void setModifiedDate(LocalDateTime modifiedDate) { this.modifiedDate = modifiedDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
    public int getAnnouncementTypeId() { return announcementTypeId; }
    public  void setAnnouncementTypeId(int announcementTypeId) { this.announcementTypeId = announcementTypeId; }
}
