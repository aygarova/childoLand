package com.kindergarten.kindergarten.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class GroupDto {

    @NotBlank(message = "First name can't be empty")
    @Size(min = 3, message = "First name must be more than 3 elements")
    private String name;

    private String teacherEmail;

    public GroupDto() {
    }

    public GroupDto(String name, String teacherEmail) {
        this.name = name;
        this.teacherEmail = teacherEmail;
    }

    public GroupDto(String name) {
        this.name = name;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
