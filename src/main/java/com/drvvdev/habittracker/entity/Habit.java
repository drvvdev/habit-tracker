package com.drvvdev.habittracker.entity;

public class Habit {
    private Long id;
    private String name;
    private String description;
    public Habit() {
    }
    public String getName() {
        return name;
    }
    public Long getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
