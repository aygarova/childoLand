package com.kindergarten.kindergarten.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserLoginDto {

    @NotBlank(message = "Email can't be empty")
    @Size(min = 4, max = 29, message = "Username must be between 4 and 29 elements")
    private String email;

    @NotBlank(message = "Password can't be empty")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 elements")
    private String password;

    private String role;

    public UserLoginDto() {
    }

    public UserLoginDto(String username, String password, String role) {
        this.email = username;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
