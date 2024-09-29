package com.homesharing.model;

public class HomeType {
    private int id;
    private String name;
    private String description;
    private String status;
    private String icon;

    public HomeType() {
    }

    public HomeType(int id, String name, String description, String status, String icon) {
        this.id = id;
        this.name = name;        this.description = description;
        this.status = status;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
