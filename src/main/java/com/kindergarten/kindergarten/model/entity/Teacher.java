package com.kindergarten.kindergarten.model.entity;

import com.kindergarten.kindergarten.enums.Role;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "teachers")
public class Teacher {

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

    @OneToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "teacher")
    private List<Child> children;

    @OneToMany(mappedBy = "teacher")
    private List<Event> events;

    public Teacher() {
    }

    public Teacher(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Teacher(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Teacher(String firstName, String lastName, String password, String phoneNumber, String email, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }

    public Teacher(long id, String firstName, String lastName, String password, String phoneNumber, String email, Group group, Role role, List<Child> children, List<Event> events) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.group = group;
        this.role = role;
        this.children = children;
        this.events = events;
    }

    public Teacher(long id, String firstName, String lastName, String password, String phoneNumber, String email, Role role, Group group) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.group = group;
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

    public Group getGroup() {
        return group;
    }

    public List<Child> getChildren() {
        return children;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public List<Event> getEvents() {
        return events;
    }

    public String getEmail() {
        return email;
    }
}
