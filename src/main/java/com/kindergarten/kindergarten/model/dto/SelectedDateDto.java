package com.kindergarten.kindergarten.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;

public class SelectedDateDto {
    private String name;
    private String description;
    private String dateTime;

    public SelectedDateDto(String name, String description, String dateTime) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
    }

    // Getters and setters
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
