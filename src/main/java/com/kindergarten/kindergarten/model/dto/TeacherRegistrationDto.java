package com.kindergarten.kindergarten.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TeacherRegistrationDto {
    @NotBlank(message = "First name can't be empty")
    @Size(min = 3, message = "First name must be more than 3 elements")
    private String firstName;

    @NotBlank(message = "Last name can't be empty")
    @Size(min = 3, message = "Last name must be more than 3 elements")
    private String lastName;

    @NotBlank(message = "Password can't be empty")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 elements")
    private String password;

    @NotBlank(message = "Password can't be empty")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 elements")
    private String confirmPassword;

    @NotBlank(message = "Phone number can't be empty")
    @Size(min = 10, max = 10)
    private String phoneNumber;

    @Email(message = "Invalid email")
    private String email;

    public TeacherRegistrationDto() {
    }

    public TeacherRegistrationDto(String firstName, String lastName, String password, String confirmPassword, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
