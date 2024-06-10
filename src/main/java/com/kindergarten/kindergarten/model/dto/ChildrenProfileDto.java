package com.kindergarten.kindergarten.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.List;

public class ChildrenProfileDto {

    private String firstName;
    private String lastName;
    private String egn;
    private int age;
    private String group;
    private String gender;
    private String teacherName;
    private List<String> states;
    private List<String> events;

    public ChildrenProfileDto() {
    }

    public ChildrenProfileDto(String firstName, String lastName, String egn, int age, String group, String gender, String teacherName, List<String> states, List<String> events) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.egn = egn;
        this.age = age;
        this.group = group;
        this.gender = gender;
        this.teacherName = teacherName;
        this.states = states;
        this.events = events;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
