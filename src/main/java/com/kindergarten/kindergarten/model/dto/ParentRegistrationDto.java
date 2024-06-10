package com.kindergarten.kindergarten.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ParentRegistrationDto {

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
    @Size(min = 6, max = 20, message = "Паралоата трябва да бъде между 6 и 20 символа")
    private String confirmPassword;

    @NotBlank(message = "Полето телефонен номер не може да бъде празно")
    @Size(min = 10, max = 10)
    private String phoneNumber;

    @Email(message = "Невалиден имейл адрес")
    private String email;

    private boolean isActive;

    @NotBlank(message = "ЕГН не може да бъде празно")
    private String childEGN;

    public ParentRegistrationDto() {
    }

    public ParentRegistrationDto(String firstName, String lastName, String password, String confirmPassword, String phoneNumber, String email, boolean isActive, String childEGN) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isActive = isActive;
        this.childEGN = childEGN;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getChildEGN() {
        return childEGN;
    }

    public void setChildEGN(String childEGN) {
        this.childEGN = childEGN;
    }
}
