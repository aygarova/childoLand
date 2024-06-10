package com.kindergarten.kindergarten.enums;

public enum Gender {
    BOY("Момче"),
    GIRL("Момиче");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
