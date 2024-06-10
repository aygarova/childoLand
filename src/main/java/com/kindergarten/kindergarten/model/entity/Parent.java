package com.kindergarten.kindergarten.model.entity;

import com.kindergarten.kindergarten.enums.Role;
import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "parents")
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "child_egn", nullable = false)
    private String childEGN;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "parents")
    private List<Child> children;

    public Parent() {
    }

    public Parent(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Parent(long id, String firstName, String lastName, String password, String phoneNumber, String email, boolean isActive, String childEGN, Role role, List<Child> children) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isActive = isActive;
        this.childEGN = childEGN;
        this.role = role;
        this.children = children;
    }

    public Parent(String firstName, String lastName, String password, String phoneNumber, String email, boolean active, String childEGN, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isActive = active;
        this.childEGN = childEGN;
        this.role = role;
    }

    public long getId() {
        return id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getChildEGN() {
        return childEGN;
    }

    public Role getRole() {
        return role;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildEGN(String childEGN) {
        this.childEGN = childEGN;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
