package com.drvvdev.habittracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
@Entity
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer target;
    private LocalDateTime createdAt;
    public Habit() {
    }
    public String getName() {
        return name;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
