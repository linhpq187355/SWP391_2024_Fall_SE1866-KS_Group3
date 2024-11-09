package com.homesharing.model;

public class AnnouncementType {
    private int id;
    private String typeName;
    private String status;
    //create constructor
    public AnnouncementType() {}
    public AnnouncementType(int id, String typeName, String status) {
        this.id = id;
        this.typeName = typeName;
        this.status = status;
    }
    //create getter, setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
