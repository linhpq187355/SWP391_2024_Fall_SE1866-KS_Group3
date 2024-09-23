package org.example.homesharingwebsite.model;

public class Role {
    private int id;
    private String name;

    // Constructors
    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    // Getters and setters
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
}
