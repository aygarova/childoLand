package com.kindergarten.kindergarten.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChildRegisterDto {

    @NotBlank(message = "First name can't be empty")
    @Size(min = 3, message = "First name must be more than 3 elements")
    private String firstName;

    @NotBlank(message = "Last name can't be empty")
    @Size(min = 3, message = "Last name must be more than 3 elements")
    private String lastName;

    @NotBlank(message = "First name can't be empty")
    @Size(min = 10, message = "First name must be more than 3 elements")
    private String egn;

    @Min(value = 1, message = "Age must be at least 1")
    private int age;

    @NotBlank(message = "First name can't be empty")
    @Size(min = 1, message = "First name must be more than 3 elements")
    private String group;

    private String gender;

    @Email(message = "Невалиден имейл адрес")
    private String parentsEmails;

    public ChildRegisterDto() {
    }

    public ChildRegisterDto(String firstName, String lastName, String egn, int age, String group, String gender, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.egn = egn;
        this.age = age;
        this.group = group;
        this.gender = gender;
        this.parentsEmails = email;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getParentsEmails() {
        return parentsEmails;
    }

    public void setParentsEmails(String parentsEmails) {
        this.parentsEmails = parentsEmails;
    }
}
